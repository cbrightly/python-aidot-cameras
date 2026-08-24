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
    """Whether this camera's recordings live in the CLOUD, not on an SD card.

    Measured 2026-08-09: `IsSupportPlayback` is 1 on the A001064 and both
    A001513s and 0 on every A000088. So it is not a model capability.

    **Both halves of the reading that used to follow were wrong, and are
    corrected here rather than left for the next person to inherit.**

    It said an A000088's recordings exist and nothing here can fetch them.
    Measured 2026-08-11 on the owner account: the three live A000088s hold 75,
    1417 and 1517 cloud events over thirty days, both listing methods return
    them, and every one resolves to a playable URL. `IsSupportPlayback=0` does
    NOT mean this camera's recordings are unreachable. What the flag gates is
    not established; it is reported and no longer used to skip a probe.

    It also said the A000088s "report `SDcardStatus: 1`", taking that as a card
    being present. `SDcardStatus` is 1 on every A000088 including ones with no
    card at all: an A000088 reports `SDcardExistFlag: false` and
    `SDcardBaseInfo: [false,0,0,0,0]` - no card, zero capacity - and its owner
    confirms the slot is empty. The two A001513s, which DO answer the
    recording-list commands, report `SDcardStatus: 0`. So the flag does not mean
    "card present", and if anything the polarity runs the other way. Read
    `SDcardExistFlag` and `SDcardBaseInfo` instead.

    That correction matters beyond this function: no camera on the reference
    fleet currently HAS a card, so the A000088 silence on `HASLISTEVENT` and
    `LISTEVENT` has never been a fair test of those commands. The real gap is
    still real - the library has no SD-card retrieval path - but it is about the
    card, not the cloud, and it is not yet known to be about the firmware.
    """
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


def session_alive(session: Any) -> bool:
    """Whether the session can still carry a command. Unknown counts as alive.

    Only ``SdesSession`` publishes ``is_alive`` (it tracks the ffmpeg process the
    bridge thread lives and dies with); the DTLS session has no equivalent and
    keeps no ffmpeg, so absence must not be read as death.
    """
    alive = getattr(session, "is_alive", None)
    return True if alive is None else bool(alive)


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


#: SDES snapshot budget, in seconds. Set from measurement rather than from
#: reasoning, after two guesses were wrong in the same direction.
#:
#: Timed across the fleet once `snapshot_s` existed to report it:
#:
#:     SDES   A001064 23.6 s   A001513 17.5 s   A001513 17.2 s   (+1 >25 s)
#:     DTLS   A000088  2.8 s   A000088  3.0 s   A000088  2.9 s
#:
#: 10 s timed out every SDES camera. 25 s left the A001064 1.4 s of margin and
#: timed out an A001513 once in three runs - which is exactly what a budget set
#: just above the observed maximum does. 40 s is about 1.7x the slowest sample.
#: The cost of being generous here is only that a genuinely broken snapshot
#: takes longer to report, and this probe does not gate a release.
_SDES_SNAPSHOT_BUDGET_S = 40.0


def _snapshot_budget(device: dict, base: float) -> float:
    """SDES snapshot needs far longer than DTLS, and the paths differ by design.

    Per the library's own docstring: a DTLS camera waits for the first keyframe
    via on_frame, while an SDES camera streams ~5 s to a temp file and then
    shells out to ffmpeg to extract a JPEG. A single budget cannot serve both -
    the first fleet run at 10 s passed all three DTLS cameras and timed out both
    SDES ones, which was the budget being wrong rather than the feature.
    """
    if _model(device) and not any(m in _model(device) for m in ("A000088",)):
        return max(base, _SDES_SNAPSHOT_BUDGET_S)
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
    """Nudge and stop, and require every command to be accepted.

    A move with no stop would leave the head travelling, so each nudge is
    followed by one. The return values are what decides: ``async_ptz_move``
    does NOT raise when it cannot send - with no active stream session it logs a
    warning, returns False and nothing leaves the host. Catching only exceptions
    therefore scored four refused commands as a pass, which is how PTZ came to
    be reported working on hardware it had never reached.
    """
    move = getattr(dc, "async_ptz_move", None)
    stop = getattr(dc, "async_ptz_stop", None)
    if move is None or stop is None:
        return False, False, "no ptz methods"
    refused = []
    try:
        for label, call in (("move right", lambda: move("right")),
                            ("stop", lambda: stop()),
                            ("move left", lambda: move("left")),
                            ("stop", lambda: stop())):
            if await asyncio.wait_for(call(), timeout) is False:
                refused.append(label)
            await asyncio.sleep(0.4)
    except asyncio.CancelledError:
        raise
    except Exception as exc:
        try:
            await asyncio.wait_for(stop(), 5)
        except Exception:
            pass
        return True, False, f"{type(exc).__name__}: {exc}"[:120]
    if refused:
        return True, False, (
            f"camera refused {len(refused)}/4 commands ({', '.join(refused)}) "
            f"- the call returned False and sent nothing")
    return True, True, None


async def _probe_talk(session, timeout: float, hold: float = 6.0
                     ) -> tuple[bool, bool, Optional[str]]:
    """Speak silence and require that our provider was actually polled.

    The hold is 6 s, not 1 s. On the SDES path `async_start_talk` returns as
    soon as the camera ACKs SPEAKERSTART (848 -> 851), but the pump that polls
    the provider runs on its own thread and, per its docstring, "waits until the
    camera audio address" is filled in by the bridge on first INBOUND audio. A
    one-second hold stopped it before that address existed, so every SDES camera
    reported "no PCM frames pulled" for a talk path that was working - the probe
    hanging up before the callee picked up.

    Passing on the ack alone would hide the opposite failure, so the pull is
    still what decides: the ack is the camera's, the pull is ours.
    """
    start = getattr(session, "async_start_talk", None)
    stop = getattr(session, "async_stop_talk", None)
    if start is None or stop is None:
        return False, False, "no talk methods"
    provider, sent = _pcm_provider(frames=400)
    try:
        accepted = await asyncio.wait_for(start(provider), timeout)
        if accepted is False:
            # False now means one of two things, and the library no longer
            # conflates them with success: the camera refused SPEAKERSTART, or
            # the command never left this process because the session's bridge
            # thread was gone. The pump requires `speaker_on` either way, so it
            # never polls the provider - the earlier "no PCM frames pulled" was
            # reporting a symptom the API had already been asked about. Read the
            # return value.
            try:
                await asyncio.wait_for(stop(), 5)
            except Exception:
                pass
            return True, False, (
                "start_talk False - the camera refused SPEAKERSTART, or the "
                "session could no longer send it")
        for _ in range(int(hold / 0.25)):
            await asyncio.sleep(0.25)
            if sent["n"]:
                break   # proven; no reason to keep the speaker open
        await asyncio.wait_for(stop(), timeout)
        # A talk path that accepted the call but never pulled a frame has not
        # been shown to work - the ack is the camera's, the pull is ours.
        return True, sent["n"] > 0, (
            None if sent["n"] else
            f"SPEAKERSTART accepted but the pump never polled us in {hold:.0f}s "
            f"- it also waits on the camera audio address and speaker_on")
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

    Account sensitivity, confirmed 2026-08-09 by running the same call from both
    accounts. From the OWNING account all six live cameras return a CloudFront
    URL and the seventh, which is offline, correctly returns None. From the
    shared-home member the CI runner uses, those same six return nothing - no
    error, just an empty answer.

    So a FAIL here on a fleet run is reporting the runner's account, not the
    feature, and the verdict is deliberately left as FAIL rather than quietly
    reclassified: the call really did fail for the identity that made it, and a
    probe that hides its own failures is the thing this module exists to stop.
    The error text names the alternative so nobody re-opens it as a defect.
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


async def _probe_recent_recordings(dc, timeout: float
                                   ) -> tuple[bool, bool, Optional[str]]:
    """The listing the vendor app actually uses.

    Separate from the range query below, and reported separately, because the
    two were once thought to disagree. They do not: on the owner account both
    return real events for the same cameras (measured 2026-08-11). The zeros
    this probe has always reported are the shared-home member the runner uses,
    the same account split already measured for cloud thumbnails - which is why
    the note below names the account rather than implying a fault.
    """
    fn = getattr(dc, "async_get_recent_recordings", None)
    if fn is None:
        return False, False, "no recent-recordings method"
    try:
        res = await asyncio.wait_for(fn(total=5), timeout)
    except asyncio.CancelledError:
        raise
    except Exception as exc:
        return True, False, f"{type(exc).__name__}: {exc}"[:120]
    # Zero is not a failure - a camera can simply have had no events - so this
    # passes either way and reports the count for the record.
    return True, True, (None if res else
                        "no recent events (verify the account before reading "
                        "this as a defect - a shared-home member gets none)")


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
        # reaching here at all means cloud storage is on. On the owner account
        # this returns a full page against server totals in the hundreds, so a
        # zero here is the account before it is anything else.
        return True, True, (None if res else
                            "listing returned 0 events (verify the account "
                            "first - a shared-home member sees none)")
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

    # Talk and PTZ go FIRST, and they go before the snapshot, because both ride
    # the session that is already open and the snapshot does not - on SDES it
    # opens a second session of its own and spends up to 25 s in it. Running it
    # first spent the live session's whole remaining lifetime before talk was
    # ever asked for, and on the SDES path the bridge thread that dispatches
    # SPEAKERSTART dies with that session's ffmpeg. Fleet run 31332008184
    # reported FAIL for all three SDES cameras on exactly that: the log contains
    # no SPEAKERSTART line at all, because there was no longer a bridge to send
    # one. Ordering alone is not the whole fix - live_validate also has to leave
    # the session running long enough - but a probe whose result depends on what
    # ran before it is not measuring the camera.
    alive = session is not None and session_alive(session)

    sup = session is not None and supports_talk(session)
    if session is not None and not alive:
        # Exercised-and-failed, never-exercised and unsupported are three
        # different results; this module exists to keep them apart. A closed
        # session is the second, and saying so names the harness bug instead of
        # accusing the camera.
        out["talk"] = NOT_RUN
        out["talk_error"] = "session closed before the talk probe ran"
    else:
        a, ok, err = await _probe_talk(session, timeout) if sup else (False, False, None)
        out["talk"] = _verdict(sup, a, ok, err)
        if err:
            out["talk_error"] = err

    sup = supports_ptz(device)
    if sup and session is not None and not alive:
        # PTZ rides `_stream_session._avio_cmd` too. Its sendto can still succeed
        # on a socket nobody is reading, so a dead session yields PASS on no
        # evidence - the quieter half of the same defect.
        out["ptz"] = NOT_RUN
        out["ptz_error"] = "session closed before the ptz probe ran"
    else:
        a, ok, err = await _probe_ptz(device_client, timeout) if sup else (False, False, None)
        out["ptz"] = _verdict(sup, a, ok, err)
        if err:
            out["ptz_error"] = err

    sup = getattr(device_client, "async_snapshot", None) is not None
    if sup and session is not None:
        _t0 = time.monotonic()
        a, ok, err = await _probe_snapshot(
                device_client, _snapshot_budget(device, timeout), out_dir)
        # Record how long it took, pass or fail. The SDES budget has been wrong
        # twice - 10 s timed out every SDES camera, and 25 s timed out an
        # A001513 in one run of three - and both times the only evidence was a
        # verdict, so the next budget was a guess. A number per run accumulates
        # the distribution instead.
        out["snapshot_s"] = round(time.monotonic() - _t0, 1)
    else:
        a, ok, err = False, False, None if sup else "unsupported"
    out["snapshot"] = _verdict(sup, a, ok, err)
    if err:
        out["snapshot_error"] = err

    sup = getattr(device_client, "async_get_latest_thumbnail", None) is not None
    a, ok, err = await _probe_thumbnail(device_client, timeout) if sup else (False, False, None)
    out["thumbnail"] = _verdict(sup, a, ok, err)
    if err:
        out["thumbnail_error"] = err

    # Asked of EVERY camera. This used to be gated on IsSupportPlayback, and
    # that flag does not mean what it was read to mean: measured 2026-08-11 on
    # the owner account, all three live A000088s report IsSupportPlayback=0 and
    # hold 75, 1417 and 1517 cloud events over thirty days, every one of which
    # resolves to a playable URL. Gating on it reported UNSUPPORTED for cameras
    # whose recordings were sitting there the whole time.
    a, ok, err = await _probe_recordings(device_client, timeout)
    out["recordings"] = _verdict(True, a, ok, err)
    if err:
        out["recordings_error"] = err
    # Kept for the record, because the flag is real and something gates on it -
    # just not this.
    out["IsSupportPlayback"] = supports_playback(device)

    # Asked of EVERY camera, not gated on IsSupportPlayback: the whole reason
    # this exists is that the app returns events for a camera whose recordings
    # this project had written off, so gating it on the same flag that wrote
    # them off would guarantee never finding out.
    sup = getattr(device_client, "async_get_recent_recordings", None) is not None
    a, ok, err = await _probe_recent_recordings(device_client, timeout) if sup else (False, False, None)
    out["recent_recordings"] = _verdict(sup, a, ok, err)
    if err:
        out["recent_recordings_error"] = err

    return out
