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
  So the cooldown is owed by the camera that just streamed, and is waited out
  only when that same camera is opened again. Cameras are still validated
  strictly one at a time - that is a separate constraint (account-wide cloud
  signaling contention) and it is not what the cooldown is for.
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

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from feature_probe import probe_features

# Models validated end-to-end on the reference account: these GATE the release.
REQUIRED_MODELS = ("A000088", "A001513", "A001064")
# Recognized in code but never validated on hardware: reported, never gating.
ADVISORY_MODELS = ("A001108", "A001360")

# Per-attempt connect is probabilistic for DTLS; give those cameras more tries.
ATTEMPTS_DTLS = 3
ATTEMPTS_SDES = 2

# Seconds of session life to leave beyond the recording window, so the probes
# that ride the OPEN session - talk and PTZ - still have one to ride.
#
# `max_seconds` is ffmpeg's -t, and on the SDES path the bridge thread that
# dispatches SPEAKERSTART and PTZ lives and dies with that process. It was set
# to hold-2, which put the session's death BEFORE `await asyncio.sleep(hold)`
# even returned, so every probe ran against a corpse. Run 31332008184 read that
# as three SDES cameras failing two-way audio; the run log has no SPEAKERSTART
# line in it at all, because there was nothing left to send one.
#
# 14 s covers the talk probe's worst case (2.6 s ack budget + a 6 s hold + stop)
# and the PTZ nudge, with margin. It buys a longer recording, which costs disk
# and nothing else.
LIVE_PROBE_BUDGET_S = 14

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


def _residual_wait(not_before: float, now: float) -> float:
    """Seconds still owed before a device may be opened, 0 if none are.

    The cooldown belongs to the DEVICE, not to the run. A camera holds its
    viewer slot ~120 s after a session, so the camera that just streamed is the
    one that has to be left alone; the next camera in the fleet is a different
    device whose slot was never taken and owes nothing.

    Waiting the full cooldown between EVERY pair of cameras spent that wait on
    devices that had nothing to release: a seven-camera run slept 15 minutes
    against roughly 3 minutes of real work. Recording a deadline per device and
    sleeping only what is left of it removes the idle time and nothing else -
    the opens stay strictly sequential, because the reason for THAT is cloud
    signaling contention, which is account-wide and is not what this wait is.
    """
    return max(0.0, not_before - now)


async def _wait_until(not_before: float, label: str) -> float:
    """Sleep out whatever this device still owes, and say so either way.

    Both branches print. A run whose log simply stopped mentioning cooldowns
    would read identically whether the wait was skipped deliberately or dropped
    by accident, and a release gate that got faster in a way nobody can check
    afterwards is not a gate anyone should trust.
    """
    wait = _residual_wait(not_before, time.monotonic())
    if wait <= 0:
        print(f"    no cooldown owed by {label} - it is not holding a viewer"
              " slot from this run")
        return 0.0
    print(f"    waiting {wait:.0f}s for {label}: it streamed recently and holds"
          " its own viewer slot ~120s")
    await asyncio.sleep(wait)
    return wait


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


def _passes(result: dict, media_ok: bool) -> bool:
    """Did this attempt deliver video a viewer could actually watch?

    ``media_ok`` is the older answer: a packet counter, and a byte count from a
    ``-c copy`` pipeline that never looks inside a packet. Both are satisfied by
    bytes of the right shape - which is not a hypothetical, it is the defect
    where undecryptable packets counted as delivered media and a black stream
    reported healthy indefinitely.

    So a pass now also requires a frame out of a decoder. Promoted from advisory
    on 2026-08-09 against 19 recorded attempts across three fleet runs: every
    PASS had decoded frames (46-262) and every NO_MEDIA had zero, so gating
    changes no historical verdict. It closes the hole rather than moving the bar.

    **A probe that could not RUN does not fail the camera.** If ffmpeg or ffprobe
    is missing on the runner, or the recording is absent, every camera would fail
    at once and the gate would be measuring its own environment instead of the
    fleet. That case reports ``decode_error`` and defers to ``media_ok``, and the
    distinction is why the probe reports the two separately.
    """
    if not media_ok:
        return False
    if result.get("decode_error"):
        return True          # probe unavailable - do not fail the camera for it
    if "decoded_frames" not in result:
        return True          # nothing to say; older behaviour
    return int(result.get("decoded_frames") or 0) > 0


async def _decode_probe(path: str, timeout: float = 60.0) -> dict:
    """Decode the recording and report what actually came out of the decoder.

    The gate's other media signals do not require a packet to have been
    decodable. ``media_stats.packets`` counts what the bridge forwarded, and
    ``recorded_bytes`` measures a file written by a ``-c copy`` pipeline that
    never looks inside a packet. Both are satisfied by bytes of the right shape.
    A camera streaming ciphertext, or a stream whose depacketizer is bound to
    the wrong payload type, can produce a healthy-looking number for either.

    This runs the file through a decoder and asks how many frames came out. It
    is the only signal in this harness that distinguishes "media arrived" from
    "media a viewer could watch".

    Returns ``{"decoded_frames": int, "decode_errors": int}``, plus ``error``
    when the probe itself could not run - a probe that cannot run must not be
    reported as zero frames, because those mean opposite things.
    """
    if not path or not os.path.exists(path):
        return {"decode_error": "no recording"}
    try:
        proc = await asyncio.create_subprocess_exec(
            "ffmpeg", "-v", "error", "-i", path, "-an", "-f", "null", "-",
            stdout=asyncio.subprocess.DEVNULL, stderr=asyncio.subprocess.PIPE,
        )
    except FileNotFoundError:
        return {"decode_error": "ffmpeg not found"}
    try:
        _out, err = await asyncio.wait_for(proc.communicate(), timeout)
    except TimeoutError:
        proc.kill()
        return {"decode_error": f"probe exceeded {timeout:.0f}s"}

    stderr = (err or b"").decode("utf-8", "replace")
    # -v error keeps this to real decode failures rather than the ordinary
    # "missed N packets" chatter a lossy live stream always produces.
    errors = [ln for ln in stderr.splitlines() if ln.strip()]

    frames = 0
    try:
        probe = await asyncio.create_subprocess_exec(
            "ffprobe", "-v", "error", "-select_streams", "v:0", "-count_frames",
            "-show_entries", "stream=nb_read_frames", "-of", "csv=p=0", path,
            stdout=asyncio.subprocess.PIPE, stderr=asyncio.subprocess.DEVNULL,
        )
        out, _ = await asyncio.wait_for(probe.communicate(), timeout)
        # ffprobe emits one line per matching stream, and an mpegts recording
        # routinely reports the same video stream twice, so this is "50\n\n50"
        # rather than "50". Parse the numeric lines and take the largest instead
        # of int()-ing the blob - which raised, and reported a perfectly good
        # stream as unprobed.
        counts = [int(ln) for ln in (out or b"").decode().split()
                  if ln.strip().isdigit()]
        frames = max(counts) if counts else 0
    except FileNotFoundError:
        return {"decode_error": "ffprobe not found", "decode_errors": len(errors)}
    except (TimeoutError, ValueError):
        probe_err = "ffprobe gave no frame count"
        return {"decode_error": probe_err, "decode_errors": len(errors)}

    return {"decoded_frames": frames, "decode_errors": len(errors),
            "decode_first_error": errors[0][:200] if errors else None}


async def _recording_seconds(path: str, timeout: float = 30.0):
    """How many seconds of video the recording actually contains.

    Bitrate needs an honest denominator. `max_seconds` says how long ffmpeg was
    ALLOWED to record, and a session that stalls or is cut short writes fewer
    seconds rather than fewer bytes per second - so dividing bytes by the
    configured bound turns a short recording into a low bitrate. The A001064's
    own rate varies 839-3698 Kbps between sessions, which would swallow any
    effect being measured.

    Deliberately a second ffprobe rather than another field on the first: that
    one feeds `decoded_frames`, which gates the release, and its parser takes
    whitespace-separated digit tokens - adding a field makes every line
    "28.03,50" and it would silently count zero frames on every camera.
    """
    if not os.path.exists(path):
        return None
    try:
        probe = await asyncio.create_subprocess_exec(
            "ffprobe", "-v", "error", "-select_streams", "v:0",
            "-show_entries", "format=duration", "-of", "csv=p=0", path,
            stdout=asyncio.subprocess.PIPE, stderr=asyncio.subprocess.DEVNULL,
        )
        out, _ = await asyncio.wait_for(probe.communicate(), timeout)
    except Exception:
        return None
    for token in (out or b"").decode().split():
        try:
            seconds = float(token)
        except ValueError:
            continue
        if seconds > 0:
            return round(seconds, 2)
    return None


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


def _parse_arms(spec: str) -> list:
    """Split a campaign spec into arms.

    "|" separates arms because a single arm is itself a comma list ("97,96").
    An empty arm means "leave the offer alone", which is the control, so the
    empty string between separators is meaningful and must not be dropped.
    """
    if not spec:
        return []
    return [part.strip() for part in spec.split("|")]


def _apply_pt_order(arm):
    """Set the offer's codec order for this attempt, or clear it.

    The library reads AIDOT_SDES_VIDEO_PT_ORDER at offer-build time on every
    open - verified, not assumed: it is a plain os.environ.get with no cache -
    so alternating it BETWEEN attempts gives genuinely interleaved arms rather
    than blocked ones. That matters because this camera's bitrate varies
    839-3698 Kbps on its own, and blocked arms would measure time of day.
    """
    key = "AIDOT_SDES_VIDEO_PT_ORDER"
    if arm:
        os.environ[key] = arm
    else:
        os.environ.pop(key, None)


async def _attempt(dc, hold: float, out_dir: str, attempt: int,
                   device: dict | None = None, pt_order=None) -> dict:
    """One streaming attempt. Never raises; classifies the outcome."""
    from aidot_cameras.exceptions import AidotCameraBusy

    out = _recording_path(out_dir, dc.device_id, attempt)
    if pt_order is not None:
        _apply_pt_order(pt_order)
        # Recorded whether or not the receipt comes back, so an arm that failed
        # to reach the SDP is visible as a mismatch rather than as a null result.
        result_arm = pt_order or "default"
    else:
        result_arm = None

    frames = {"n": 0}
    # Anything left over belongs to the previous attempt; reporting it here
    # would name a failure on a session that succeeded.
    _STALLS.drain()
    _RECEIPTS.drain()
    t0 = time.time()
    session = None
    result: dict = {"attempt": attempt}
    if result_arm is not None:
        result["pt_order_arm"] = result_arm
    try:
        # talk=True so the offer advertises sendrecv audio. Without it an SDES
        # session never negotiates a return track, `talk_supported` is False,
        # and a two-way audio probe reports UNSUPPORTED for a camera that
        # supports it perfectly well - the harness not asking, misread as the
        # camera not offering.
        session = await dc.async_open_webrtc_stream(
            on_frame=lambda _f: frames.__setitem__("n", frames["n"] + 1),
            timeout=45.0,
            output_path=out,
            max_seconds=max(1, int(hold - 2) + LIVE_PROBE_BUDGET_S),
            talk=True,
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

        # Advisory: reported, never gating. The same reasoning the decode probe
        # shipped under - a measurement that has never run against this fleet is
        # not one to start blocking releases with. Runs while the session is
        # still open, because snapshot and talk need one.
        #
        # PTZ and the resolution setter reach their session through
        # `dc._stream_session`, which the library sets from its keepalive,
        # streaming and serve loops - the paths Home Assistant goes through -
        # and NOT from a bare async_open_webrtc_stream, which is what this
        # harness calls. Left unset, every PTZ command returned False with "no
        # active stream session" and sent nothing; the probe used to score that
        # as a pass, so PTZ had never once been exercised on hardware. Standing
        # in for the loop that would own it is the only way to measure the real
        # call path, and it is restored immediately afterwards so nothing else
        # inherits a session this function is about to close.
        _prev_session = getattr(dc, "_stream_session", None)
        try:
            dc._stream_session = session
            result["features"] = await probe_features(dc, device or {}, session)
        except Exception as exc:
            result["features_error"] = f"{type(exc).__name__}: {exc}"[:120]
        finally:
            dc._stream_session = _prev_session

        ok, evidence = _media_seen(session, frames["n"], out)
        result.update(evidence)
        result.update(await _decode_probe(out))
        _secs = await _recording_seconds(out)
        if _secs:
            result["recorded_seconds"] = _secs
            _bytes = result.get("recorded_bytes") or 0
            # From the file's own duration, never from `max_seconds`.
            result["kbps"] = round(_bytes * 8 / _secs / 1000, 1)
        result["verdict"] = "PASS" if _passes(result, ok) else "NO_MEDIA"
    except AidotCameraBusy as exc:
        # Someone else is watching. Distinct from a media failure - but still
        # not a validated camera, so it does not pass the gate.
        result["verdict"] = "BUSY"
        result["error"] = f"{type(exc).__name__}: {exc}"
    except Exception as exc:
        result["verdict"] = "ERROR"
        result["error"] = f"{type(exc).__name__}: {exc}"
    finally:
        # In `finally`, not on the success path: the attempts that stall are
        # exactly the ones that leave by an exception or a timeout, so anywhere
        # else would collect the reports that matter least. A stalled snapshot
        # opens its own session, so one attempt can produce more than one.
        _stalls = _STALLS.drain()
        if _stalls:
            result["stall_reports"] = _stalls
        _receipts = _RECEIPTS.drain()
        if _receipts:
            # The offer's own account of the order it sent. Without it a null
            # campaign result cannot be told from a campaign that never varied.
            result["offer_pt_order"] = _receipts[-1]
        if session is not None:
            try:
                await _stop(session)
            except Exception:
                pass
    return result


async def _validate_camera(client, device, args, cooldown_until: dict) -> dict:
    """Validate one camera. ``cooldown_until`` is the fleet's per-device clock.

    It maps device id -> the monotonic time that device may next be opened, and
    is both read (before the first attempt) and written (after every attempt)
    here, so the key can never drift between writer and reader.
    """
    dc = client.get_device_client(device)
    model = _model_of(dc)
    tier = _classify(model)
    is_dtls = not getattr(dc, "is_sdes_camera", False)
    # A campaign runs a fixed, balanced number of attempts and does NOT stop on
    # success: the normal loop breaks on the first PASS, so with arms alternating
    # per attempt a passing camera would only ever see the first arm - blocked
    # arms wearing interleaved clothing, which is exactly what this design is
    # meant to avoid.
    arms = _parse_arms(getattr(args, "pt_order_arms", "") or "")
    campaigning = bool(arms) and not is_dtls
    if campaigning:
        max_attempts = len(arms) * max(1, int(getattr(args, "arm_repeats", 1)))
    else:
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

    # Only THIS camera's own deadline. Normally already past - a camera the run
    # has not touched yet is not holding a slot for anybody.
    await _wait_until(cooldown_until.get(dc.device_id, 0.0),
                      repr(entry["name"]))

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
        res = await _attempt(
            dc, args.hold, args.out_dir, i, device,
            pt_order=(arms[(i - 1) % len(arms)] if campaigning else None))
        entry["attempts"].append(res)
        # This device may now be holding a viewer slot, so record when it may
        # next be opened. Every exit from this loop passes through here,
        # including the breaks below - a camera whose deadline went unrecorded
        # would be reopenable immediately, which is the one thing the cooldown
        # exists to prevent.
        cooldown_until[dc.device_id] = time.monotonic() + _cooldown_after(
            res["verdict"], args.cooldown
        )
        print(f"    -> {res['verdict']}"
              + (f"  handshake={res['handshake_s']}s" if "handshake_s" in res else "")
              + (f"  frames={res['frames']}" if "frames" in res else "")
              + (f"  bytes={res['recorded_bytes']}" if "recorded_bytes" in res else "")
              + (f"  decoded={res['decoded_frames']}" if "decoded_frames" in res else "")
              + (f"  decode_err={res['decode_errors']}"
                 if res.get("decode_errors") else "")
              + (f"  decode_probe={res['decode_error']}" if "decode_error" in res else "")
              + (f"  {res['error']}" if "error" in res else ""))
        if res["verdict"] == "PASS" and not campaigning:
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
            # cloud signaling channel, and THAT contention is account-wide -
            # it is the root of this project's signature failure, concurrent
            # cold opens serializing through the library's open-gate past Home
            # Assistant's stream-worker deadline. Nothing below overlaps opens.
            #
            # The cooldown is a different constraint and is per-device (see
            # _residual_wait), so it is carried here as a deadline per camera
            # and paid only by the camera that owes it.
            cooldown_until: dict[str, float] = {}
            for cam in selected:
                report["cameras"].append(
                    await _validate_camera(client, cam, args, cooldown_until)
                )
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


#: The opening words of the library's first-media stall report. Matching the
#: text, not the logger or the level, so an unrelated WARNING from the same
#: module is never mistaken for a stall - and a rename of the report fails the
#: tests loudly instead of silently collecting nothing.
_STALL_MARKER = "SDES first media never arrived"


class _StallCollector(logging.Handler):
    """Keep the stall reports so they reach the artifact, not just the log.

    The report is the only line that says WHY a session delivered nothing, and
    it lived only in the CI log. Three consecutive runs on 2026-08-10/11 dropped
    the entire validate step from their logs - 375 lines against ~2200 on every
    earlier run - while a required camera returned no media six attempts in a
    row. A diagnostic that is present and unreadable is not a diagnostic.

    Drained per attempt: which attempt stalled is part of the finding, and a
    run-level list would lose that on a camera that fails once and then passes.
    """

    def __init__(self) -> None:
        super().__init__(level=logging.WARNING)
        self._seen: list = []

    def emit(self, record: logging.LogRecord) -> None:
        # A handler that raises takes down whatever logged, and here that is the
        # library's own stall path. Diagnosis must never become the failure.
        try:
            message = record.getMessage()
        except Exception:
            return
        if _STALL_MARKER in message:
            self._seen.append(message)

    def drain(self) -> list:
        out, self._seen = self._seen, []
        return out


#: The offer's codec-order receipt. The one time this project pinned the codec
#: order it "looked like a confirmed result for two sessions before a missing
#: receipt showed it had never reached the SDP at all", so a campaign that
#: varies the order must carry proof per attempt that the variation arrived.
_ORDER_MARKER = "offer video codec order="


class _ReceiptCollector(_StallCollector):
    """Same mechanism as the stall collector, different line."""

    def emit(self, record: logging.LogRecord) -> None:
        try:
            message = record.getMessage()
        except Exception:
            return
        if _ORDER_MARKER in message:
            self._seen.append(message.split(_ORDER_MARKER, 1)[1].strip())


#: Installed by _configure_logging, read by _attempt.
_STALLS = _StallCollector()
_RECEIPTS = _ReceiptCollector()


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
    # Attached to the library logger rather than the root: this only ever wants
    # the library's own reports, and the root carries aiortc/asyncio too.
    logging.getLogger("aidot_cameras").addHandler(_STALLS)
    logging.getLogger("aidot_cameras").addHandler(_RECEIPTS)


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
                   help="seconds a camera is left alone after a session before"
                        f" it is opened again (default {DEFAULT_COOLDOWN_S:.0f};"
                        " a camera holds its viewer slot ~120s)")
    p.add_argument("--pt-order-arms", default="",
                   help="campaign mode: '|'-separated video codec orders to "
                        "alternate per attempt on SDES cameras, e.g. "
                        "'|97,96' for default-then-H265-first. An empty arm "
                        "means leave the offer alone. Attempts do not stop on "
                        "success, so both arms are measured on every camera.")
    p.add_argument("--arm-repeats", type=int, default=1,
                   help="how many times to cycle the arms (default 1)")
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
