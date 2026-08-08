#!/usr/bin/env python3
"""Release-gating live validation against the real cameras on the LAN.

Streams every camera on the account and reports, per camera, whether real
media arrived - the single question a release must answer before it can be
published. Writes a machine-readable JSON report alongside the human log.

This is the CI-facing harness. ``scripts/smoke_stream.py`` (quick pass/fail)
and ``scripts/camera_diag.py`` (deep single-camera diagnostics) remain for
interactive use.

Why the policy below is shaped the way it is (all learned on hardware, see
docs/CAMERAS.md):

- **DTLS connects are probabilistic.** An A000088's per-attempt connect rate is
  ~75-87%, so a single failed attempt is not a release blocker. Each camera
  gets up to N attempts and passes if ANY attempt delivers media; the attempt
  count is reported so a degrading trend is visible.
- **A camera holds its viewer slot ~120 s** after a session, and reopening it
  too quickly causes camera-side flakiness unrelated to the code under test.
  Cameras are validated one at a time with a cooldown between attempts.
- **A busy ack (-50002/-50015) is terminal**: someone (probably Home
  Assistant, or a phone app) is watching. That is reported as BUSY, distinctly
  from a media failure, and still fails the gate - a release must not be
  published on an unvalidated camera.
- **Unvalidated models are advisory.** A001108/A001360 are recognized in code
  but have never been validated on the reference account, so they are reported
  and never gate.

Exit code is 0 only when every REQUIRED camera passed.

Credentials come from ``aidot_cameras.credentials.load_credentials``
(AIDOT_USERNAME / AIDOT_PASSWORD / AIDOT_COUNTRY, or AIDOT_TOKEN_FILE).
No secrets are stored in this file.
"""

import argparse
import asyncio
import inspect
import json
import logging
import os
import sys
import time

import aiohttp

from aidot_cameras.client import AidotClient
from aidot_cameras.const import CONF_DEVICE_LIST, CONF_ID, CONF_NAME
from aidot_cameras.credentials import load_credentials

# Models validated end-to-end on the reference account: these GATE the release.
REQUIRED_MODELS = ("A000088", "A001513", "A001064")
# Recognized in code but never validated on hardware: reported, never gating.
ADVISORY_MODELS = ("A001108", "A001360")

# Per-attempt connect is probabilistic for DTLS; give those cameras more tries.
ATTEMPTS_DTLS = 3
ATTEMPTS_SDES = 2

# A camera holds its viewer slot ~120 s after a session; leave room past that.
DEFAULT_COOLDOWN_S = 180.0

# Cooldown exists to let a camera release the viewer slot it holds for ~120 s
# after a session. A camera that never answered never opened one, so waiting on
# it buys nothing - and it is exactly the camera that burns the most wall clock,
# because it also spends its full retry budget. A fleet run with two dead
# cameras spent ~18 minutes of a 45 minute job ceiling asleep on their behalf.
#
# Verdicts that DID (or may have) taken a slot, so the wait still applies:
#   PASS      streamed - definitely held one
#   NO_MEDIA  signaling completed, so the camera opened a session its side
#   BUSY      something else holds the slot; backing off is the whole point
# ERROR is the no-session case: no webrtcResp at all, or the open raised before
# a session existed. Nothing to release.
_SLOTLESS_VERDICTS = frozenset({"ERROR"})
# Not zero: back-to-back cloud signaling on one account is its own contention,
# and a camera that is merely slow rather than dead deserves a breath.
SLOTLESS_COOLDOWN_S = 10.0

# Attempts to allow a camera that has produced NOTHING but slotless ERRORs.
#
# The retry budget exists because a DTLS connect is probabilistic - ICE and the
# media path can fail and then succeed. But a slotless ERROR is a different
# animal: no webrtcResp at all, i.e. the camera never answered cloud signaling,
# so there was nothing probabilistic to lose. Retrying that is a 45 s timeout
# spent re-asking a camera that is not on the network.
#
# One retry is kept, because a single missed webrtcResp is possible on a busy
# account. A third is not: a camera silent twice running is off, unplugged or
# out of range, and every further attempt is pure wall clock. Observed: a fleet
# camera absent all day burned 3 x 45 s on every run to reach the same verdict.
SLOTLESS_MAX_ATTEMPTS = 2


def _cooldown_after(verdict: str, full: float) -> float:
    """Seconds to wait after an attempt/camera that ended in ``verdict``.

    Never longer than the configured cooldown: this exists to shorten the wait,
    and `--cooldown 5` must not be lengthened to the slotless floor.
    """
    if verdict in _SLOTLESS_VERDICTS:
        return min(SLOTLESS_COOLDOWN_S, full)
    return full


def _is_camera(device_client) -> bool:
    model = getattr(getattr(device_client, "info", None), "model_id", "") or ""
    return "IPC" in model


def _model_of(dc) -> str:
    return getattr(getattr(dc, "info", None), "model_id", "") or ""


def _model_key(model_id: str) -> str:
    """'LK.IPC.A001513' -> 'A001513' (also matches the -1 hardware revisions)."""
    tail = model_id.rsplit(".", 1)[-1]
    return tail.split("-")[0]


def _classify(model_id: str) -> str:
    """"required" gates the release; anything else is reported but never gates.

    Advisory covers both the models recognized in code but never validated on
    the reference account (ADVISORY_MODELS) and any model nobody has seen yet -
    a camera we have no baseline for must not be able to block a release on its
    first appearance.
    """
    return "required" if _model_key(model_id) in REQUIRED_MODELS else "advisory"


async def _stop(session) -> None:
    stop = getattr(session, "stop", None)
    if stop is None:
        return
    result = stop()
    if inspect.isawaitable(result):
        await result


def _media_seen(session, frames: int, out_path: str | None) -> tuple[bool, dict]:
    """Did real media arrive? Returns (ok, evidence).

    The two transports report media differently and BOTH have to be handled:
    the DTLS path decodes in-process and calls on_frame, while the SDES path
    hands media to ffmpeg and never calls it - so for SDES the signal is the
    bridge's own counters (SdesSession.media_stats) plus recorded bytes.
    """
    evidence: dict = {"frames": frames}
    ok = frames > 0

    stats_fn = getattr(session, "media_stats", None)
    if callable(stats_fn):
        try:
            stats = stats_fn()
            evidence["media_stats"] = stats
            ok = ok or stats.get("packets", 0) > 0
        except Exception as exc:
            evidence["media_stats_error"] = str(exc)

    if out_path and os.path.exists(out_path):
        size = os.path.getsize(out_path)
        evidence["recorded_bytes"] = size
        ok = ok or size > 20_000

    return ok, evidence


def _recording_path(out_dir: str, device_id: str, attempt: int) -> str:
    """Where this attempt records, clearing a stale file we are allowed to clear.

    The default out_dir is /tmp, which is world-writable and sticky: a stale
    recording left by a *different* user cannot be removed, and os.remove raises
    PermissionError. That aborted a whole run on the self-hosted runner, because
    the runner user inherited /tmp files an earlier manual run had left behind.

    A stale file we cannot delete is not a reason to fail - fall back to a path
    this process definitely owns and carry on.
    """
    out = os.path.join(out_dir, f"live_{device_id[:8]}_{attempt}.ts")
    if os.path.exists(out):
        try:
            os.remove(out)
        except OSError:
            out = os.path.join(
                out_dir, f"live_{device_id[:8]}_{attempt}_{os.getpid()}.ts"
            )
    return out


async def _attempt(dc, hold: float, out_dir: str, attempt: int) -> dict:
    """One streaming attempt. Never raises; classifies the outcome."""
    from aidot_cameras.exceptions import AidotCameraBusy

    out = _recording_path(out_dir, dc.device_id, attempt)

    frames = {"n": 0}
    t0 = time.time()
    session = None
    result: dict = {"attempt": attempt}
    try:
        session = await dc.async_open_webrtc_stream(
            on_frame=lambda _f: frames.__setitem__("n", frames["n"] + 1),
            timeout=45.0,
            output_path=out,
            max_seconds=max(1, int(hold - 2)),
        )
        result["handshake_s"] = round(time.time() - t0, 1)
        result["transport"] = type(session).__name__
        await asyncio.sleep(hold)

        if hasattr(session, "get_stats"):
            try:
                stats = await session.get_stats()
                ice = (stats or {}).get("ice") or []
                if ice:
                    result["ice_pair"] = [
                        f"{p.get('local_type')}->{p.get('remote_type')}" for p in ice
                    ]
                result["rtp"] = [
                    {"kind": s.get("kind"), "recv": s.get("packets_received"),
                     "loss_pct": s.get("loss_pct"), "jitter": s.get("jitter")}
                    for s in (stats or {}).get("inbound", [])
                ]
            except Exception as exc:
                result["stats_error"] = str(exc)

        ok, evidence = _media_seen(session, frames["n"], out)
        result.update(evidence)
        result["verdict"] = "PASS" if ok else "NO_MEDIA"
    except AidotCameraBusy as exc:
        # Someone else is watching. Distinct from a media failure - but still
        # not a validated camera, so it does not pass the gate.
        result["verdict"] = "BUSY"
        result["error"] = f"{type(exc).__name__}: {exc}"
    except Exception as exc:
        result["verdict"] = "ERROR"
        result["error"] = f"{type(exc).__name__}: {exc}"
    finally:
        if session is not None:
            try:
                await _stop(session)
            except Exception:
                pass
    return result


async def _validate_camera(client, device, args) -> dict:
    dc = client.get_device_client(device)
    model = _model_of(dc)
    tier = _classify(model)
    is_dtls = not getattr(dc, "is_sdes_camera", False)
    max_attempts = ATTEMPTS_DTLS if is_dtls else ATTEMPTS_SDES

    entry: dict = {
        "name": device.get(CONF_NAME),
        "device_id": device.get(CONF_ID),
        "model": model,
        "tier": tier,
        "transport": "DTLS" if is_dtls else "SDES",
        "battery": bool(getattr(dc, "is_battery_camera", False)),
        "attempts": [],
    }
    print(f"\n=== {entry['name']!r}  {model}  ({entry['transport']}"
          f"{', battery' if entry['battery'] else ''}, {tier})")

    for i in range(1, max_attempts + 1):
        if i > 1:
            prev = entry["attempts"][-1].get("verdict", "")
            wait = _cooldown_after(prev, args.cooldown)
            if wait < args.cooldown:
                print(f"    {prev} on attempt {i - 1} - no session was opened, "
                      f"so no slot to release; waiting {wait:.0f}s not "
                      f"{args.cooldown:.0f}s")
            else:
                print(f"    cooling down {wait:.0f}s before attempt {i} "
                      "(a camera holds its viewer slot ~120s)")
            await asyncio.sleep(wait)
        print(f"    attempt {i}/{max_attempts}...")
        res = await _attempt(dc, args.hold, args.out_dir, i)
        entry["attempts"].append(res)
        print(f"    -> {res['verdict']}"
              + (f"  handshake={res['handshake_s']}s" if "handshake_s" in res else "")
              + (f"  frames={res['frames']}" if "frames" in res else "")
              + (f"  bytes={res['recorded_bytes']}" if "recorded_bytes" in res else "")
              + (f"  {res['error']}" if "error" in res else ""))
        if res["verdict"] == "PASS":
            break
        # A camera that has only ever failed WITHOUT opening a session is not
        # being flaky, it is absent. Stop re-asking it; the verdict cannot change
        # and each further attempt costs a full signaling timeout.
        if (len(entry["attempts"]) >= SLOTLESS_MAX_ATTEMPTS
                and all(a["verdict"] in _SLOTLESS_VERDICTS
                        for a in entry["attempts"])
                and i < max_attempts):
            print(f"    no session opened on {len(entry['attempts'])} attempts - "
                  f"treating as absent, skipping the remaining "
                  f"{max_attempts - i} attempt(s)")
            break

    verdicts = [a["verdict"] for a in entry["attempts"]]
    entry["verdict"] = "PASS" if "PASS" in verdicts else (
        "BUSY" if "BUSY" in verdicts else
        ("NO_MEDIA" if "NO_MEDIA" in verdicts else "ERROR")
    )
    entry["attempts_used"] = len(entry["attempts"])
    return entry


async def _run(args) -> int:
    creds = load_credentials()
    report: dict = {
        "started_utc": time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime()),
        "ref": os.environ.get("GITHUB_SHA") or os.environ.get("AIDOT_VALIDATION_REF") or "",
        "cameras": [],
    }
    async with aiohttp.ClientSession() as http:
        client = AidotClient(
            http,
            country_code=creds.get("country", "US"),
            username=creds["username"],
            password=creds["password"],
        )
        try:
            await client.async_post_login()
            devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
            cameras = [d for d in devices if _is_camera(client.get_device_client(d))]
            print(f"found {len(cameras)} camera(s) of {len(devices)} device(s)")
            for cam in cameras:
                dc = client.get_device_client(cam)
                print(f"  - {cam.get(CONF_NAME)!r:32} {_model_of(dc):18} "
                      f"{_classify(_model_of(dc))}")

            if args.list:
                report["cameras"] = [
                    {"name": c.get(CONF_NAME), "device_id": c.get(CONF_ID),
                     "model": _model_of(client.get_device_client(c))}
                    for c in cameras
                ]
                # An enumeration that finds nothing is a failure, not a pass.
                # This used to return 0 unconditionally, so the workflow's
                # "List cameras" step could not fail - including against an
                # account that returns zero cameras, which is precisely the
                # condition it exists to catch.
                covered = {_model_key(c["model"]) for c in report["cameras"]}
                missing = [m for m in REQUIRED_MODELS if m not in covered]
                report["missing_required_models"] = missing
                report["verdict"] = "PASS" if cameras and not missing else "FAIL"
                _write_report(report, args)
                if not cameras:
                    print("\nFAIL: no cameras visible to this account."
                          " If it is a shared (non-owner) account, set"
                          " AIDOT_INCLUDE_SHARED_HOUSES=1.")
                    return 1
                if missing:
                    print("\nFAIL: required model(s) absent from this account:"
                          f" {', '.join(missing)}")
                    return 1
                return 0

            selected = cameras
            if args.name:
                wanted = [n.lower() for n in args.name]
                selected = [c for c in cameras
                            if any(w in (c.get(CONF_NAME) or "").lower() for w in wanted)]
            if args.model:
                wanted_m = [m.upper() for m in args.model]
                selected = [
                    c for c in selected
                    if _model_key(_model_of(client.get_device_client(c))) in wanted_m
                ]

            # Strictly one camera at a time: concurrent opens contend for the
            # cloud signaling channel and for the cameras' own stream slots.
            for idx, cam in enumerate(selected):
                if idx > 0:
                    # Space on the PREVIOUS camera's outcome: it is the one that
                    # may still be holding a slot, not the one about to open.
                    prev_v = report["cameras"][-1].get("verdict", "")
                    wait = _cooldown_after(prev_v, args.cooldown)
                    if wait < args.cooldown:
                        print(f"\n(previous camera {prev_v} with no session "
                              f"opened - spacing {wait:.0f}s, not "
                              f"{args.cooldown:.0f}s)")
                    else:
                        print(f"\n(spacing {wait:.0f}s between cameras)")
                    await asyncio.sleep(wait)
                report["cameras"].append(await _validate_camera(client, cam, args))
                # Write after every camera, not only at the end.  A fleet run
                # where everything fails takes far longer than a green one (each
                # dead camera burns the full first-media wait), so it is exactly
                # the failing run that hits the job timeout - and a report only
                # written at the end means the run that most needed diagnostics
                # uploads none at all.  Partial is worth more than nothing.
                report["partial"] = True
                _write_report(report, args, quiet=True)
        finally:
            await client.async_cleanup()

    return _summarize(report, args)


def _summarize(report: dict, args) -> int:
    required = [c for c in report["cameras"] if c["tier"] == "required"]
    advisory = [c for c in report["cameras"] if c["tier"] != "required"]
    failed = [c for c in required if c["verdict"] != "PASS"]

    print("\n==== SUMMARY ====")
    for c in report["cameras"]:
        tag = "" if c["tier"] == "required" else "  (advisory)"
        print(f"  {c['verdict']:9} {c['name']!r:32} {c['model']:18} "
              f"{c['transport']}  attempts={c['attempts_used']}{tag}")

    # The gate is about MODEL coverage, not fleet health: a release breaks a
    # transport/firmware path, and one camera of a model streaming proves that
    # path still works.  Individual cameras fail for reasons that have nothing
    # to do with the code under test - a flat battery, a unit that is powered
    # off, an L2 too deeply asleep to wake inside the window - and blocking
    # every release on those means the gate gets ignored or switched off, which
    # is worse than a slightly narrower gate.
    #
    # So: each required model needs at least ONE camera that streamed.  A model
    # with no passing camera still fails, and a model absent from the account
    # entirely still fails - validating a subset of the fleet and calling it
    # green is how a model-specific break ships.
    by_model: dict = {}
    for c in required:
        by_model.setdefault(_model_key(c["model"]), []).append(c)

    missing = [m for m in REQUIRED_MODELS if m not in by_model]
    models_failed = [
        m for m in REQUIRED_MODELS
        if m in by_model and not any(c["verdict"] == "PASS" for c in by_model[m])
    ]

    print("\n  required-model coverage:")
    for m in REQUIRED_MODELS:
        cams = by_model.get(m, [])
        passed = [c for c in cams if c["verdict"] == "PASS"]
        if not cams:
            state = "MISSING from this account"
        elif passed:
            state = f"ok - {len(passed)}/{len(cams)} streamed"
        else:
            state = f"FAIL - 0/{len(cams)} streamed"
        print(f"    {m:10} {state}")

    # Failures that did not gate are still printed: they are the early warning
    # that a model is degrading while one healthy camera masks it.
    tolerated = [c for c in failed
                 if _model_key(c["model"]) not in models_failed]
    if tolerated:
        print("\n  did not gate (their model is covered by another camera) -"
              " watch these:")
        for c in tolerated:
            print(f"    {c['verdict']:9} {c['name']!r} {c['model']}")

    if missing:
        print(f"\n  MISSING required model(s) on this account: {', '.join(missing)}")

    report["required_failed"] = [c["name"] for c in failed]
    report["required_models_failed"] = models_failed
    report["tolerated_failures"] = [c["name"] for c in tolerated]
    report["model_coverage"] = {
        m: {
            "cameras": len(by_model.get(m, [])),
            "passed": len([c for c in by_model.get(m, [])
                           if c["verdict"] == "PASS"]),
        }
        for m in REQUIRED_MODELS
    }
    report["missing_required_models"] = missing
    report["advisory_failed"] = [
        c["name"] for c in advisory if c["verdict"] != "PASS"
    ]
    ok = not models_failed and not missing and bool(required)
    # The run reached its own end, so the report is no longer a partial written
    # mid-loop.  A report still carrying partial=True was killed (job timeout,
    # cancellation) before every camera was attempted - read its verdict as
    # "unfinished", not as a fleet result.
    report["partial"] = False
    report["verdict"] = "PASS" if ok else "FAIL"
    _write_report(report, args)

    if not required:
        print("\nFAIL: no required-model cameras were validated")
    print(f"\noverall: {report['verdict']}")
    return 0 if ok else 1


def _write_report(report: dict, args, quiet: bool = False) -> None:
    """Write the report.  ``quiet`` suppresses the notice for interim writes.

    Written atomically: the interim writes land mid-run, and a job killed by a
    timeout partway through a write would otherwise leave truncated JSON - which
    is worse than no artifact, because it looks like a parse bug rather than a
    kill.
    """
    if not args.json_out:
        return
    tmp = f"{args.json_out}.tmp"
    with open(tmp, "w") as fh:
        json.dump(report, fh, indent=2)
    os.replace(tmp, args.json_out)
    if not quiet:
        print(f"\nwrote {args.json_out}")


def _configure_logging(level_name: str) -> None:
    """Let the library's own log lines out of the process.

    Nothing here ever configured logging, so the root logger fell back to
    Python's lastResort handler: WARNING and above, to stderr, unformatted.
    Every INFO and DEBUG line the library emits was discarded - including the
    per-session video-profile lines that 0.17.1 shipped specifically so the
    bitrate question could be reopened from a record, and the SRTP
    decrypt-failure reports that are the only direct evidence for whether the
    bridge is forwarding ciphertext. Four runs after that instrumentation
    landed produced zero of those lines, and no corpus was ever going to
    accumulate from CI.

    The aidot loggers are raised, not the root: aiortc, asyncio and aiohttp at
    INFO bury the signal in a run that already prints tens of thousands of
    lines. Timestamps are included because most questions asked of these logs
    are about ordering and latency.
    """
    level = getattr(logging, level_name.upper(), None)
    if not isinstance(level, int):
        raise SystemExit(f"unknown --log-level {level_name!r}")
    logging.basicConfig(
        level=logging.WARNING,
        format="%(asctime)s %(levelname)s %(name)s: %(message)s",
        stream=sys.stderr,  # stdout carries the human-readable report
    )
    for name in ("aidot", "aidot_cameras"):
        logging.getLogger(name).setLevel(level)


def main() -> int:
    p = argparse.ArgumentParser(
        description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter
    )
    p.add_argument("--list", action="store_true", help="list cameras and exit")
    p.add_argument("--name", action="append", default=[],
                   help="only cameras whose name contains this (repeatable)")
    p.add_argument("--model", action="append", default=[],
                   help="only these model keys, e.g. A001513 (repeatable)")
    p.add_argument("--hold", type=float, default=16.0,
                   help="seconds to hold each stream (default 16)")
    p.add_argument("--cooldown", type=float, default=DEFAULT_COOLDOWN_S,
                   help=f"seconds between attempts/cameras (default {DEFAULT_COOLDOWN_S:.0f};"
                        " a camera holds its viewer slot ~120s)")
    p.add_argument("--out-dir", default="/tmp", help="where to write recordings")
    p.add_argument("--json-out", default="live-report.json",
                   help="machine-readable report path ('' to skip)")
    p.add_argument("--log-level", default="INFO",
                   help="level for the aidot loggers (default INFO; DEBUG for"
                        " protocol detail, WARNING for the old behaviour)")
    args = p.parse_args()
    _configure_logging(args.log_level)
    return asyncio.run(_run(args))


if __name__ == "__main__":
    raise SystemExit(main())
