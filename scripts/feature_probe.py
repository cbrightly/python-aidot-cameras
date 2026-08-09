#!/usr/bin/env python3
"""Exercise the camera features the streaming gate does not touch.

The release gate answers one question - does video arrive and decode. The
package also offers snapshots, PTZ, two-way audio, cloud recordings and
thumbnails, and until now nothing verified any of them on real hardware.

The distinction this module exists to preserve: a feature the camera does not
have, a feature that was not exercised, and a feature that was exercised and
failed are three different results. Collapsing any two of them is the defect
this project has now fixed four times - a probe that could not run, reported as
a zero result. `IsSupportPlayback` is the live example: every camera on the
reference account reports 0, so cloud playback returns no events forever. Scored
as FAIL that reads as a broken feature; scored as UNSUPPORTED it reads as the
truth.
"""

import asyncio
import os
import time
from typing import Any, Optional

PASS = "PASS"
FAIL = "FAIL"
UNSUPPORTED = "UNSUPPORTED"
NOT_RUN = "NOT_RUN"


def _verdict(supported: bool, attempted: bool, ok: bool, err: Optional[str]) -> str:
    """Four outcomes, deliberately not three.

    ``UNSUPPORTED`` is not a failure and must never gate. ``NOT_RUN`` is not a
    pass - it means nobody looked, which is the state this whole module exists
    to eliminate.
    """
    if not supported:
        return UNSUPPORTED
    if not attempted:
        return NOT_RUN
    return PASS if ok else FAIL


def _props(device: dict) -> dict:
    return (device.get("properties") or {}) if isinstance(device, dict) else {}


def _model(device: dict) -> str:
    return (device.get("modelId") or "") if isinstance(device, dict) else ""


def supports_ptz(device: dict) -> bool:
    """PTZ is an A001064 capability. Detect it; do not attempt-and-catch."""
    return "A001064" in _model(device)


def supports_playback(device: dict) -> bool:
    """The cameras on the reference account all report 0 - SD card, not cloud."""
    return str(_props(device).get("IsSupportPlayback", "0")) == "1"


def supports_talk(session: Any) -> bool:
    """The session says whether the camera negotiated a return audio track."""
    flag = getattr(session, "talk_supported", None)
    if callable(flag):
        try:
            return bool(flag())
        except Exception:
            return False
    return bool(flag)


def _pcm_provider(frames: int = 25):
    """320-byte s16le frames, 20 ms at 8 kHz - silence, so nothing is audible.

    Returns None when exhausted, which is how the talk path is told to stop.
    """
    sent = {"n": 0}

    def provider() -> Optional[bytes]:
        if sent["n"] >= frames:
            return None
        sent["n"] += 1
        return b"\x00" * 320

    return provider, sent


def _snapshot_budget(device: dict, base: float) -> float:
    """SDES snapshot needs far longer than DTLS, and the paths differ by design.

    Per the library's own docstring: a DTLS camera waits for the first keyframe
    via on_frame, while an SDES camera streams ~5 s to a temp file and then
    shells out to ffmpeg to extract a JPEG. A single budget cannot serve both -
    the first fleet run at 10 s passed all three DTLS cameras and timed out both
    SDES ones, which was the budget being wrong rather than the feature.
    """
    if _model(device) and not any(m in _model(device) for m in ("A000088",)):
        return max(base, 25.0)
    return base


async def _probe_snapshot(dc, timeout: float, out_dir: str = "/tmp"
                          ) -> tuple[bool, bool, Optional[str]]:
    """Capture a still and require a non-trivial file on disk.

    ``async_snapshot(output_path, timeout=...)`` takes a destination - the first
    version of this probe called it with no arguments and every camera reported
    FAIL with a TypeError. The bool it returns is the library's own claim; the
    file size is the independent check, and this asserts both. An empty or
    tiny file with a True return would be the same "bytes of the right shape"
    hole the decode gate exists to close.
    """
    fn = getattr(dc, "async_snapshot", None)
    if fn is None:
        return False, False, "no async_snapshot"
    dev = getattr(dc, "device_id", "cam") or "cam"
    path = os.path.join(out_dir, f"probe_snap_{str(dev)[:8]}.jpg")
    try:
        if os.path.exists(path):
            os.remove(path)
    except OSError:
        pass
    try:
        res = await asyncio.wait_for(fn(path, timeout=timeout), timeout + 10)
    except asyncio.CancelledError:
        raise
    except Exception as exc:
        return True, False, f"{type(exc).__name__}: {exc}"[:120]
    size = os.path.getsize(path) if os.path.exists(path) else 0
    if not res:
        return True, False, f"returned False (file {size}B)"
    if size < 1024:
        return True, False, f"claimed success but wrote {size}B"
    return True, True, None


async def _probe_ptz(dc, timeout: float) -> tuple[bool, bool, Optional[str]]:
    """Nudge and stop. A move with no stop would leave the head travelling."""
    move = getattr(dc, "async_ptz_move", None)
    stop = getattr(dc, "async_ptz_stop", None)
    if move is None or stop is None:
        return False, False, "no ptz methods"
    try:
        await asyncio.wait_for(move("right"), timeout)
        await asyncio.sleep(0.4)
        await asyncio.wait_for(stop(), timeout)
        await asyncio.sleep(0.4)
        await asyncio.wait_for(move("left"), timeout)
        await asyncio.sleep(0.4)
        await asyncio.wait_for(stop(), timeout)
        return True, True, None
    except asyncio.CancelledError:
        raise
    except Exception as exc:
        try:
            await asyncio.wait_for(stop(), 5)
        except Exception:
            pass
        return True, False, f"{type(exc).__name__}: {exc}"[:120]


async def _probe_talk(session, timeout: float) -> tuple[bool, bool, Optional[str]]:
    start = getattr(session, "async_start_talk", None)
    stop = getattr(session, "async_stop_talk", None)
    if start is None or stop is None:
        return False, False, "no talk methods"
    provider, sent = _pcm_provider()
    try:
        await asyncio.wait_for(start(provider), timeout)
        await asyncio.sleep(1.0)
        await asyncio.wait_for(stop(), timeout)
        # A talk path that accepted the call but never pulled a frame has not
        # been shown to work - the ack is the camera's, the pull is ours.
        return True, sent["n"] > 0, None if sent["n"] else "no PCM frames pulled"
    except asyncio.CancelledError:
        raise
    except Exception as exc:
        try:
            await asyncio.wait_for(stop(), 5)
        except Exception:
            pass
        return True, False, f"{type(exc).__name__}: {exc}"[:120]


async def _probe_thumbnail(dc, timeout: float) -> tuple[bool, bool, Optional[str]]:
    """Fetch the latest thumbnail URL.

    Known account sensitivity, measured 2026-08-09: from the OWNING account all
    six live cameras return a CloudFront URL and the offline one returns None.
    From the shared-home member the CI runner uses, all six returned nothing -
    no error, just an empty answer. So a FAIL here on a fleet run may be
    reporting the runner's account rather than the feature, and that has to be
    ruled out before it is read as a defect. The error text says which was
    observed so the two cannot be confused later.
    """
    fn = getattr(dc, "async_get_latest_thumbnail", None)
    if fn is None:
        return False, False, "no thumbnail method"
    try:
        res = await asyncio.wait_for(fn(), timeout)
        return True, bool(res), None if res else (
            "cloud returned no thumbnail (verify the account before reading "
            "this as a defect - a shared-home member gets none)")
    except asyncio.CancelledError:
        raise
    except Exception as exc:
        return True, False, f"{type(exc).__name__}: {exc}"[:120]


async def _probe_recordings(dc, timeout: float, days: int = 7
                            ) -> tuple[bool, bool, Optional[str]]:
    fn = getattr(dc, "async_get_cloud_recordings", None)
    if fn is None:
        return False, False, "no recordings method"
    now = int(time.time())
    try:
        res = await asyncio.wait_for(fn(now - days * 86400, now), timeout)
        # Zero events is not a failure on its own - a camera can simply have had
        # no motion. The caller has already checked IsSupportPlayback, so
        # reaching here at all means cloud storage is on.
        return True, True, None if res else "listing returned 0 events"
    except asyncio.CancelledError:
        raise
    except Exception as exc:
        return True, False, f"{type(exc).__name__}: {exc}"[:120]


async def probe_features(device_client, device: dict, session=None,
                         *, timeout: float = 10.0, out_dir: str = "/tmp") -> dict:
    """Run every feature probe against one camera. Never raises.

    ``session`` is a live stream session where one is open; snapshot and talk
    need it. Without it they report NOT_RUN rather than FAIL, because not
    looking is not the same as looking and finding it broken.

    The default timeout is deliberately 10s, not 20s. Five probes across seven
    cameras at 20s worst case added ~21 minutes to a ~7 minute fleet run - the
    first run took 25 minutes. The measured latencies are far below either
    bound: PTZ acks land in 0.01-0.19s and talk's SPEAKERSTART in 0.01-0.38s.
    """
    out: dict = {}

    sup = getattr(device_client, "async_snapshot", None) is not None
    if sup and session is not None:
        a, ok, err = await _probe_snapshot(
                device_client, _snapshot_budget(device, timeout), out_dir)
    else:
        a, ok, err = False, False, None if sup else "unsupported"
    out["snapshot"] = _verdict(sup, a, ok, err)
    if err:
        out["snapshot_error"] = err

    sup = supports_ptz(device)
    a, ok, err = await _probe_ptz(device_client, timeout) if sup else (False, False, None)
    out["ptz"] = _verdict(sup, a, ok, err)
    if err:
        out["ptz_error"] = err

    sup = session is not None and supports_talk(session)
    a, ok, err = await _probe_talk(session, timeout) if sup else (False, False, None)
    out["talk"] = _verdict(sup, a, ok, err)
    if err:
        out["talk_error"] = err

    sup = getattr(device_client, "async_get_latest_thumbnail", None) is not None
    a, ok, err = await _probe_thumbnail(device_client, timeout) if sup else (False, False, None)
    out["thumbnail"] = _verdict(sup, a, ok, err)
    if err:
        out["thumbnail_error"] = err

    sup = supports_playback(device)
    a, ok, err = await _probe_recordings(device_client, timeout) if sup else (False, False, None)
    out["recordings"] = _verdict(sup, a, ok, err)
    if err:
        out["recordings_error"] = err

    return out
