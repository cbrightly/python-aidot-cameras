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
(AIDOT_USERNAME / AIDOT_PASSWORD / AIDOT_COUNTRY).
No secrets are stored in this file.

Set ``AIDOT_TOKEN_FILE`` to share one login with other processes. The AiDot
cloud keeps a single live token per account, so a second login invalidates the
first: a campaign that holds a client open and runs this script as a child
loses its own session the moment the first child logs in, and the refresh token
is rotated out with it, so there is no recovery. With the variable set, this
goes through ``aidot_cameras.cloud_auth`` - the same path the go2rtc CLI uses -
which reuses the stored token and writes rotations back. Unset, it logs in
directly, which is what the release gate does.
"""

import argparse
import asyncio
import inspect
import json
import logging
import os
import random as _random
import sys
import time

import aiohttp

from aidot_cameras.const import CONF_DEVICE_LIST, CONF_ID, CONF_NAME
from aidot_cameras.cloud_auth import _make_client
from aidot_cameras.credentials import load_credentials

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from feature_probe import probe_features
from sd_event_probe import probe_sd_events

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

# --- the in-session quality campaign -----------------------------------------
#
# The 2026-08-07 sweep sent SETSTREAMCTRL and then judged it by the profile the
# NEXT session came back with. The vendor-app capture of 2026-08-11 shows the
# app's own SD tap taking effect the other way entirely: no renegotiation (one
# media 5-tuple carried 12,701 packets across both rates), no cloud call at the
# HD tap, and the rate changing within seconds INSIDE the session. A sweep
# looking for a next-session change could therefore have been sending a command
# that worked perfectly and scoring it against the wrong observable.
#
# So this measures the rate before and after the command in ONE session, which
# nobody has done. The numbers below are sized against the camera, not against
# comfort:
#
# * an A001064 ends its own streaming session roughly every 60-85 s (see
#   docs/DESIGN-session-continuity.md), and a teardown landing inside the second
#   window is indistinguishable from a successful halving. 3+12+2+12 = 29 s of
#   media leaves the measurement finished well inside the shortest lifetime
#   observed (62 s);
# * the settle skips the encoder's opening burst, which would otherwise inflate
#   the first window on every arm including the control;
# * the gap absorbs the transition, so neither window straddles it.
#
# A 2:1 effect does not need long windows; it needs windows that are certainly
# inside a live session.
QUALITY_FIRST_MEDIA_S = 20.0
QUALITY_SETTLE_S = 3.0
QUALITY_WINDOW_S = 12.0
QUALITY_GAP_S = 2.0
# ffmpeg's -t has to cover the whole timeline plus the wait for first media,
# because the SDES bridge thread - and with it the command channel and the byte
# counter - lives and dies with that process. A -t that expires inside the
# second window freezes the counter and reports exactly the result this
# experiment is hoping for.
def _quality_max_seconds(window: float) -> int:
    """ffmpeg's -t for a campaign attempt, from the timeline it has to cover."""
    return int(QUALITY_FIRST_MEDIA_S + QUALITY_SETTLE_S + window
               + QUALITY_GAP_S + window + 8)


# A session that died, or delivered nothing, during the second window is VOID -
# it is not a measurement of anything and must not be counted toward the arm.
# Re-queued rather than dropped, up to this many times across the camera, so a
# void does not silently shrink an arm to fewer sessions than were asked for.
QUALITY_VOID_BUDGET = 3

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


async def _video_bitrate_series(path: str, timeout: float = 60.0) -> dict:
    """Per-second video-only kbps for the whole recording.

    The in-session counter (``media_stats``) is the primary measurement and it
    is unambiguous, but it counts audio too and it is our own bookkeeping. This
    is the independent second opinion: video only, straight out of the file the
    decode probe already reads, with no wall-clock-to-PTS arithmetic anywhere
    near a window boundary.

    Deliberately a SERIES rather than two aligned windows. Aligning window edges
    onto PTS needs the offset between "ffmpeg started" and "the first packet it
    wrote", which is unknown and would be load-bearing at exactly the moment the
    rate steps. A per-second series shows the step wherever it falls, and its
    position can then be checked against the tap offset without any boundary
    arithmetic being trusted.

    Returns ``{"video_kbps_by_second": [...]}`` or ``{"series_error": str}`` -
    a probe that could not run must never be reported as a flat rate.
    """
    if not path or not os.path.exists(path):
        return {"series_error": "no recording"}
    try:
        probe = await asyncio.create_subprocess_exec(
            "ffprobe", "-v", "error", "-select_streams", "v:0",
            "-show_entries", "packet=pts_time,size", "-of", "csv=p=0", path,
            stdout=asyncio.subprocess.PIPE, stderr=asyncio.subprocess.DEVNULL,
        )
        out, _err = await asyncio.wait_for(probe.communicate(), timeout)
    except FileNotFoundError:
        return {"series_error": "ffprobe not found"}
    except Exception as exc:  # report it, never raise out of a probe
        return {"series_error": f"{type(exc).__name__}: {exc}"[:120]}

    buckets: dict[int, int] = {}
    first_pts = None
    for line in (out or b"").decode("utf-8", "replace").splitlines():
        parts = line.strip().split(",")
        if len(parts) < 2:
            continue
        try:
            pts, size = float(parts[0]), int(parts[1])
        except ValueError:
            # "N/A" pts on a packet the container could not stamp. Skipping it
            # loses that packet's bytes rather than mis-placing them, which is
            # the right way round for a cross-check.
            continue
        if first_pts is None:
            first_pts = pts
        buckets[int(pts - first_pts)] = buckets.get(int(pts - first_pts), 0) + size

    if not buckets:
        return {"series_error": "no video packets"}
    series = [round(buckets.get(s, 0) * 8 / 1000, 1)
              for s in range(max(buckets) + 1)]
    # At one-second resolution this stream's PTS are too clumped to read: a
    # measured A001064 recording alternates 1550 / 3020 / 95 kbps second by
    # second around a true ~1700, because packets land unevenly either side of
    # a second boundary (the same stamping ffmpeg reports as "Non-monotonic
    # DTS" throughout the run). Four-second buckets average that out
    # while still being far finer than the 12 s windows, so a 2:1 step stays
    # obvious. Both are reported: the per-second series is what was measured,
    # the coarse one is what can be read.
    coarse = [round(sum(buckets.get(s, 0) for s in range(b, b + 4)) * 8 / 4000, 1)
              for b in range(0, len(series), 4)]
    return {"video_kbps_by_second": series, "video_kbps_by_4s": coarse}


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


def _media_sample(session, frames: dict) -> dict:
    """One reading of everything that counts media, with its own timestamp.

    ``media_stats`` is the SDES bridge's own byte counter, which is what makes
    an in-session bitrate measurable at all - it is bytes forwarded to ffmpeg,
    sampled on the wall clock, with no file and no PTS in the way. The DTLS path
    has no such counter (it decodes in process), so the frame count is carried
    too and the report says which signal a window was built from. Frames do not
    make a bitrate: on that transport the per-second video series from the
    recording is the measurement and this is only liveness.
    """
    sample = {"t": time.monotonic(), "frames": frames["n"],
              "bytes": None, "packets": None}
    stats_fn = getattr(session, "media_stats", None)
    if callable(stats_fn):
        try:
            stats = stats_fn() or {}
            sample["bytes"] = stats.get("bytes")
            sample["packets"] = stats.get("packets")
        except Exception:
            pass
    return sample


def _window(start: dict, end: dict) -> dict:
    """Bitrate across two samples, from the wall clock between them.

    The denominator is the time actually elapsed, never the configured window:
    an event loop that ran late gives a longer window, and dividing by the
    nominal 12 s would turn that into a higher bitrate.
    """
    seconds = end["t"] - start["t"]
    out: dict = {"seconds": round(seconds, 2),
                 "frames": end["frames"] - start["frames"]}
    if start["bytes"] is not None and end["bytes"] is not None:
        out["bytes"] = end["bytes"] - start["bytes"]
        out["packets"] = end["packets"] - start["packets"]
        if seconds > 0:
            out["kbps"] = round(out["bytes"] * 8 / seconds / 1000, 1)
    return out


async def _wait_first_media(session, frames: dict, budget: float) -> float | None:
    """Monotonic time of the first media, or None if none arrived in ``budget``.

    The windows are anchored on first media rather than on the open, because the
    open returns when signaling finishes and media can be seconds behind it. An
    unanchored settle would spend itself waiting for the stream to start and the
    first window would then contain the encoder's opening burst - which is
    exactly the confound the settle exists to remove.
    """
    deadline = time.monotonic() + budget
    while time.monotonic() < deadline:
        sample = _media_sample(session, frames)
        if (sample["packets"] or 0) > 0 or sample["frames"] > 0:
            return sample["t"]
        await asyncio.sleep(0.2)
    return None


async def _quality_probe(dc, session, arm: str, frames: dict,
                         window: float = QUALITY_WINDOW_S) -> dict:
    """Measure the bitrate either side of a mid-session resolution command.

    One session, two windows, one command between them. ``arm`` is the quality
    to ask for ("sd"/"hd"); an empty arm is the CONTROL - it waits out the same
    gap and sends nothing, which is the only way to tell a command that works
    from a stream that simply settles downward on its own.

    The result is deliberately per-session and self-contained: window B is only
    ever compared with window A from the SAME session. This camera's own rate
    varies 839-3698 Kbps between sessions, so any comparison across sessions
    would measure that variance instead of the command.
    """
    out: dict = {
        "arm": arm or "control",
        "settle_s": QUALITY_SETTLE_S,
        "window_s": window,
        "gap_s": QUALITY_GAP_S,
    }

    t_first = await _wait_first_media(session, frames, QUALITY_FIRST_MEDIA_S)
    if t_first is None:
        out["verdict"] = "VOID"
        out["void_reason"] = f"no media within {QUALITY_FIRST_MEDIA_S:.0f}s"
        return out
    await asyncio.sleep(QUALITY_SETTLE_S)
    a0 = _media_sample(session, frames)
    await asyncio.sleep(window)
    a1 = _media_sample(session, frames)
    out["window_a"] = _window(a0, a1)
    # A first window with nothing in it is void for the same reason the second
    # one is: it is a denominator of zero wearing the clothes of a baseline.
    if not (out["window_a"].get("packets") or out["window_a"].get("frames")):
        out["verdict"] = "VOID"
        out["void_reason"] = "no media in window A"
        return out

    # The command, on the real call path. `async_set_resolution` reaches its
    # session through `dc._stream_session`, which only the library's own
    # keepalive/streaming/serve loops set - a bare `async_open_webrtc_stream`
    # leaves it None, and with it None the setter REMEMBERS the quality, sends
    # nothing, and returns True. Standing in for the loop that would own it is
    # the only way to exercise what Home Assistant exercises.
    _ACKS.drain()
    # Recorded on the control arm too: the control has to occupy the same point
    # in the same timeline, or it is not a control of this measurement.
    out["tap_after_first_media_s"] = round(time.monotonic() - t_first, 2)
    if arm:
        _prev_session = getattr(dc, "_stream_session", None)
        # Whether the SCTP command channel existed at the moment of the tap.
        # The setter's True does not distinguish "sent" from "remembered", and
        # a campaign that cannot tell those apart cannot interpret a null.
        try:
            out["cmd_channel_ready"] = bool(session._cmd_chan[0] is not None)
        except Exception:
            out["cmd_channel_ready"] = None
        try:
            dc._stream_session = session
            t_cmd = time.monotonic()
            out["set_resolution_returned"] = bool(
                await dc.async_set_resolution(arm))
            out["set_resolution_s"] = round(time.monotonic() - t_cmd, 2)
        except Exception as exc:
            out["set_resolution_error"] = f"{type(exc).__name__}: {exc}"[:120]
        finally:
            dc._stream_session = _prev_session
            # The setter remembers the quality and the library re-applies it
            # whenever a session next starts. Nothing in this harness triggers
            # that path today, but a control arm that inherited the previous
            # arm's setting would be a control in name only, and the cost of
            # ruling it out is one line.
            dc._desired_quality = None
        # The camera's own answer (801), which the setter logs at DEBUG and
        # returns nothing about. An ack proves the command was accepted, not
        # that it did anything - it is recorded so a null result cannot be
        # blamed on a command that never arrived.
        out["ack_log"] = _ACKS.drain()
    else:
        out["set_resolution_returned"] = None

    await asyncio.sleep(QUALITY_GAP_S)
    b0 = _media_sample(session, frames)
    await asyncio.sleep(window)
    b1 = _media_sample(session, frames)
    out["window_b"] = _window(b0, b1)

    # A session that ended inside window B produces a beautiful halving. Both
    # checks, and they are checks on the SECOND window specifically, because
    # that is the one whose collapse would be mistaken for the result.
    alive = getattr(session, "is_alive", None)
    out["alive_after"] = bool(alive) if isinstance(alive, bool) else None
    moved = out["window_b"].get("packets")
    if moved is None:
        moved = out["window_b"].get("frames")
    if out["alive_after"] is False or not moved:
        out["verdict"] = "VOID"
        out["void_reason"] = ("session ended during the measurement"
                              if out["alive_after"] is False
                              else "no media in window B")
        return out

    ka, kb = out["window_a"].get("kbps"), out["window_b"].get("kbps")
    if ka and kb:
        out["kbps_a"], out["kbps_b"] = ka, kb
        out["ratio_b_over_a"] = round(kb / ka, 3)
    else:
        # No byte counter on this transport. The per-second video series from
        # the recording is then the only bitrate evidence, and it is reported
        # per attempt either way.
        out["counter"] = "frames only - no media_stats on this transport"
    out["verdict"] = "OK"
    return out


def _interleave_arms(arms: list, repeats: int, seed=None) -> list:
    """The session order for a quality campaign: balanced blocks, shuffled.

    ["sd", ""] x3 gives three sd and three control sessions in some order -
    NOT three sd sessions followed by three controls. This camera's own rate
    varies 839-3698 Kbps between sessions, so a blocked campaign measures the
    time of day.

    Each repeat is one block containing every arm exactly once, shuffled within
    the block. That keeps the balance a strict cycle gave while removing its
    fixed period, which is the part that was wrong: the reference camera varies
    things of its own between sessions - measured 2026-09-04, 4 of 44 cold opens
    negotiated H.265 rather than H.264 - and anything of the camera's own that
    happens to share the cycle's period lands preferentially on one arm and
    reads as that arm's effect. The b=AS knob nearly produced a false positive
    from exactly this shape.

    ``seed`` makes a campaign reproducible, so a run that finds something can be
    re-run in the same order.
    """
    if not arms:
        return []
    rng = _random.Random(seed)
    order: list = []
    for _ in range(max(1, repeats)):
        block = list(arms)
        rng.shuffle(block)
        order.extend(block)
    return order


def _void_reason(res: dict) -> str | None:
    """Why this attempt measured nothing, or None if it measured something.

    A void is not a failed camera and not a result: the session ended inside
    the measurement, or delivered no media in the second window. Both produce a
    beautiful apparent halving, so they are named and re-run rather than
    averaged in.
    """
    quality = res.get("quality") or {}
    if quality.get("verdict") == "VOID":
        return quality.get("void_reason") or "void"
    if res.get("verdict") != "PASS":
        return res.get("verdict")
    return None


def _quality_summary(attempts: list) -> dict:
    """Per-arm collection of the per-session ratios. No verdict is computed.

    Every session's own numbers are kept, and the arm's spread is reported
    rather than only its mean: three control ratios that themselves scatter
    0.6-1.4 mean the windows are too short to conclude anything, and that is a
    finding rather than a failure - a mean alone would hide it.

    ``kbps_a`` is kept per session for a second reason. If the control sessions
    that FOLLOW an sd session show a depressed first window, the camera is
    remembering the setting across sessions; that would be a real result, and
    without the absolute numbers it would look like a noisy control instead.
    """
    per_arm: dict = {}
    for att in attempts:
        q = att.get("quality")
        if not q:
            continue
        bucket = per_arm.setdefault(
            q.get("arm") or "control", {"sessions": [], "void": 0})
        if q.get("verdict") != "OK":
            bucket["void"] += 1
            continue
        bucket["sessions"].append({
            "attempt": att.get("attempt"),
            "kbps_a": q.get("kbps_a"),
            "kbps_b": q.get("kbps_b"),
            "ratio": q.get("ratio_b_over_a"),
            "acked": bool(q.get("ack_log")),
        })
    for bucket in per_arm.values():
        ratios = [s["ratio"] for s in bucket["sessions"] if s["ratio"]]
        bucket["n"] = len(ratios)
        if ratios:
            bucket["ratio_mean"] = round(sum(ratios) / len(ratios), 3)
            bucket["ratio_min"] = min(ratios)
            bucket["ratio_max"] = max(ratios)
    return per_arm


def _print_quality_attempt(res: dict) -> None:
    q = res.get("quality") or {}
    if not q:
        return
    if q.get("verdict") != "OK":
        print(f"       quality arm={q.get('arm')}: VOID - {q.get('void_reason')}")
        return
    ka, kb = q.get("kbps_a"), q.get("kbps_b")
    rate = (f"A={ka} kbps  B={kb} kbps  B/A={q.get('ratio_b_over_a')}"
            if ka and kb else f"no byte counter ({q.get('counter')})")
    print(f"       quality arm={q.get('arm')}  {rate}")
    if q.get("arm") != "control":
        print(f"       command: returned={q.get('set_resolution_returned')}"
              f"  channel_ready={q.get('cmd_channel_ready')}"
              f"  took={q.get('set_resolution_s')}s"
              f"  tap at +{q.get('tap_after_first_media_s')}s of media")
        for line in q.get("ack_log") or ["(no ack line logged)"]:
            print(f"       camera: {line}")


def _print_quality_summary(entry: dict) -> None:
    print(f"\n    quality campaign, {entry['name']!r} - each ratio is window B "
          "over window A of the SAME session:")
    for arm, bucket in entry["quality_summary"].items():
        ratios = ", ".join(f"{s['ratio']}" for s in bucket["sessions"]
                           if s["ratio"]) or "-"
        print(f"      {arm:8} n={bucket.get('n', 0)}  ratios: {ratios}"
              + (f"  mean={bucket['ratio_mean']}" if "ratio_mean" in bucket else "")
              + (f"  void={bucket['void']}" if bucket.get("void") else ""))
        for s in bucket["sessions"]:
            print(f"        attempt {s['attempt']}: {s['kbps_a']} -> {s['kbps_b']} kbps")
    print("      read it against the control arm: a stream that settles "
          "downward on its own does so in both arms.")


async def _attempt(dc, hold: float, out_dir: str, attempt: int,
                   device: dict | None = None, pt_order=None,
                   sd_probe: bool = False, quality_arm=None,
                   max_seconds: int | None = None,
                   quality_window: float = QUALITY_WINDOW_S) -> dict:
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
    if quality_arm is not None:
        result["quality_arm"] = quality_arm or "control"
        _ACKS.drain()
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
            max_seconds=(max_seconds if max_seconds is not None
                         else max(1, int(hold - 2) + LIVE_PROBE_BUDGET_S)),
            talk=True,
        )
        result["handshake_s"] = round(time.time() - t0, 1)
        result["transport"] = type(session).__name__
        if quality_arm is None:
            await asyncio.sleep(hold)
        else:
            result["quality"] = await _quality_probe(
                dc, session, quality_arm, frames, quality_window)
            # Close the session before reading the recording. Everything the
            # campaign measures is already in hand (the counter is sampled on
            # the wall clock, in session), and the per-second video series is
            # read from a file ffmpeg would otherwise still be writing - so
            # without this the last seconds of window B, the ones that carry
            # the effect, are the ones most likely to be missing from the
            # cross-check. Stopping here also hands the camera back sooner.
            try:
                await _stop(session)
            except Exception:
                pass

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
        #
        # Not during a quality campaign. The PTZ nudge MOVES THE CAMERA, and a
        # scene change is a bitrate change - the probe would be varying the
        # thing being measured. Two-way audio adds an outbound stream for the
        # same reason. A campaign attempt therefore reports no features, which
        # is the honest outcome: it did not run them.
        if quality_arm is None:
            _prev_session = getattr(dc, "_stream_session", None)
            try:
                dc._stream_session = session
                # Read-only, opt-in: asks what recordings exist so the response
                # layout can be read off the wire. Never sends DELLISTEVENT or
                # RECORD_PLAYCONTROL, so it cannot delete anything or start
                # playback on a camera in someone's house.
                if sd_probe:
                    try:
                        result["sd_events"] = await probe_sd_events(session)
                    except Exception as exc:
                        result["sd_events_error"] = (
                            f"{type(exc).__name__}: {exc}"[:120])
                result["features"] = await probe_features(dc, device or {}, session)
            except Exception as exc:
                result["features_error"] = f"{type(exc).__name__}: {exc}"[:120]
            finally:
                dc._stream_session = _prev_session

        ok, evidence = _media_seen(session, frames["n"], out)
        result.update(evidence)
        result.update(await _decode_probe(out))
        if quality_arm is not None:
            # Video only, from the file, independent of our own counter.
            result.update(await _video_bitrate_series(out))
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
    # The quality campaign is the same design one level in: the arms alternate
    # per SESSION, and the comparison that matters happens INSIDE each session,
    # so this loop's job is only to run a balanced, interleaved set of them.
    quality_arms = _parse_arms(getattr(args, "quality_arms", "") or "")
    quality_window = float(getattr(args, "quality_window", QUALITY_WINDOW_S))
    repeats = max(1, int(getattr(args, "arm_repeats", 1)))
    pending = _interleave_arms(quality_arms, repeats,
                               seed=getattr(args, "arm_seed", None))
    if quality_arms:
        max_attempts = len(pending)
    elif campaigning:
        max_attempts = len(arms) * repeats
    else:
        max_attempts = ATTEMPTS_DTLS if is_dtls else ATTEMPTS_SDES
    voids = 0

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

    i = 0
    while True:
        if quality_arms:
            if not pending:
                break
            quality_arm = pending.pop(0)
        else:
            quality_arm = None
            if i >= max_attempts:
                break
        i += 1
        total = i + len(pending) if quality_arms else max_attempts
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
        print(f"    attempt {i}/{total}..."
              + (f"  quality arm: {quality_arm or 'control'}"
                 if quality_arms else ""))
        res = await _attempt(
            dc, args.hold, args.out_dir, i, device,
            pt_order=(arms[(i - 1) % len(arms)] if campaigning else None),
            sd_probe=bool(getattr(args, "sd_probe", False)),
            quality_arm=quality_arm, quality_window=quality_window,
            max_seconds=(_quality_max_seconds(quality_window)
                         if quality_arms else None))
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
        if quality_arms:
            _print_quality_attempt(res)
            # A void session measured nothing and must not stand in for one of
            # the sessions this arm was asked for. Re-queued at the back so the
            # arms stay interleaved rather than retried back-to-back.
            void = _void_reason(res)
            if void and voids < QUALITY_VOID_BUDGET:
                voids += 1
                pending.append(quality_arm)
                print(f"    void session ({void}) - re-queueing arm "
                      f"{quality_arm or 'control'} ({voids}/"
                      f"{QUALITY_VOID_BUDGET} of the void budget used)")
        if res["verdict"] == "PASS" and not campaigning and not quality_arms:
            break
        # A camera that has only ever failed WITHOUT opening a session is not
        # being flaky, it is absent. Stop re-asking it; the verdict cannot change
        # and each further attempt costs a full signaling timeout.
        more_left = bool(pending) if quality_arms else i < max_attempts
        if (len(entry["attempts"]) >= SLOTLESS_MAX_ATTEMPTS
                and all(a["verdict"] in _SLOTLESS_VERDICTS
                        for a in entry["attempts"])
                and more_left):
            print(f"    no session opened on {len(entry['attempts'])} attempts - "
                  f"treating as absent, skipping the remaining "
                  f"{len(pending) if quality_arms else max_attempts - i}"
                  " attempt(s)")
            break

    if quality_arms:
        entry["quality_summary"] = _quality_summary(entry["attempts"])
        _print_quality_summary(entry)

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
        # The cloud keeps ONE live token per account, so a login invalidates
        # whoever held the last one.  _make_client reuses a stored token when
        # AIDOT_TOKEN_FILE is set (writing rotations back, which is what lets a
        # campaign hold a client across sessions while this runs as its child)
        # and otherwise performs exactly the password login the gate always
        # did.  Credentials go in as PARAMETERS: an earlier shim published them
        # into os.environ for _make_client to re-read, which handed the
        # decrypted password to every spawned child and carried a dead country
        # assignment (the old module-level default bound at import time).
        client = await _make_client(
            http,
            username=creds["username"],
            password=creds["password"],
            country=creds.get("country", "US"),
        )
        try:
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
    """Same mechanism as the stall collector, different line AND level.

    The stall report is a WARNING; the offer receipt is an INFO. Inheriting the
    parent's WARNING level made this collect nothing at all, and the first real
    campaign proved how bad that is: six receipts in the run log, None in the
    artifact, and a null codec result that could not be told apart from a
    campaign whose arms never reached the SDP - the exact failure the receipt
    exists to rule out.
    """

    def __init__(self) -> None:
        super().__init__()
        self.setLevel(logging.NOTSET)

    def emit(self, record: logging.LogRecord) -> None:
        try:
            message = record.getMessage()
        except Exception:
            return
        if _ORDER_MARKER in message:
            self._seen.append(message.split(_ORDER_MARKER, 1)[1].strip())


#: The resolution setter's own account of what the camera said back. The setter
#: returns True whether the camera acked, stayed silent, or was never asked at
#: all (no session -> it remembers the value and reports success), so its return
#: value alone cannot tell a null result from a command that never went out.
_ACK_MARKER = "set resolution "


class _AckCollector(_ReceiptCollector):
    """Keeps the SETSTREAMCTRL ack lines, which are DEBUG.

    Same trap as the codec receipt one level worse: those are INFO and this is
    DEBUG, so it collects nothing unless the controls logger is actually at
    DEBUG. `_configure_logging` lowers that one logger when a quality campaign
    is running, rather than putting the whole library at DEBUG during a
    measurement.
    """

    def emit(self, record: logging.LogRecord) -> None:
        try:
            message = record.getMessage()
        except Exception:
            return
        if _ACK_MARKER in message:
            self._seen.append(message)


#: Installed by _configure_logging, read by _attempt.
_STALLS = _StallCollector()
_RECEIPTS = _ReceiptCollector()
_ACKS = _AckCollector()


def _configure_logging(level_name: str, quality_campaign: bool = False) -> None:
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
    logging.getLogger("aidot_cameras").addHandler(_ACKS)
    if quality_campaign:
        # One logger, not the library: the ack lines are DEBUG, and a campaign
        # measuring a bitrate should not also be paying for DEBUG on every
        # media path it is trying to measure. Raised here rather than asking
        # the operator to remember --log-level DEBUG, because a collector that
        # silently gathers nothing has already shipped once on this harness.
        logging.getLogger("aidot_cameras.camera.controls").setLevel(logging.DEBUG)


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
    p.add_argument("--quality-arms", default="",
                   help="in-session quality campaign: '|'-separated qualities "
                        "to apply MID-SESSION, one per session, e.g. 'sd|' for "
                        "sd-then-control. An empty arm is the control - it "
                        "waits the same gap and sends nothing. Each session is "
                        "measured against itself (bitrate before the command "
                        "vs after), which is the comparison the 2026-08-07 "
                        "sweep never made. Feature probes are skipped: the PTZ "
                        "nudge moves the camera, and a scene change is a "
                        "bitrate change. Use --arm-repeats 3 or more.")
    p.add_argument("--quality-window", type=float, default=QUALITY_WINDOW_S,
                   help="seconds per measurement window in a quality campaign "
                        f"(default {QUALITY_WINDOW_S:.0f}). Two of these plus "
                        "the settle and gap have to finish inside the session: "
                        "an A001064 recycles itself every 60-85s, and a "
                        "teardown inside the second window looks exactly like "
                        "a bitrate that halved.")
    p.add_argument("--arm-seed", type=int, default=None,
                   help="seed for the per-block arm shuffle. Omit for a fresh "
                        "random order; set it to re-run a campaign in the same "
                        "order it was first run in.")
    p.add_argument("--arm-repeats", type=int, default=1,
                   help="how many times to cycle the arms (default 1)")
    p.add_argument("--sd-probe", action="store_true",
                   help="ask each camera what recordings it holds "
                        "(HASLISTEVENT/LISTEVENT) and record the raw reply. "
                        "Read-only: never deletes and never starts playback.")
    p.add_argument("--out-dir", default="/tmp", help="where to write recordings")
    p.add_argument("--json-out", default="live-report.json",
                   help="machine-readable report path ('' to skip)")
    p.add_argument("--log-level", default="INFO",
                   help="level for the aidot loggers (default INFO; DEBUG for"
                        " protocol detail, WARNING for the old behaviour)")
    args = p.parse_args()
    _configure_logging(args.log_level, bool(args.quality_arms))
    return asyncio.run(_run(args))


if __name__ == "__main__":
    raise SystemExit(main())
