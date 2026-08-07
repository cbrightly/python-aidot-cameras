"""Controls report what the camera said, not that we wrote bytes to a socket.

`async_start_talk` returned `True` unconditionally: it sent SPEAKERSTART and
declared success whether or not the speaker ever opened. `async_set_resolution`
did the same for `SETSTREAMCTRL`. That is the defect that cost the resolution
select - a control that reports success without evidence is worse than no
control, because the report is what everyone reasons from afterwards.

The camera does answer. Measured 2026-08-07 on both transports:

    848 SPEAKERSTART -> 851, payload 0x0064   DTLS 0.01-0.38s, SDES 0.17-0.86s
    800 SETSTREAMCTRL -> 801                  DTLS 0.01-0.03s

**Silence still counts as success, and that is deliberate.** An A001064 answers
848 but does not implement 802 at all, so "this firmware has no response for
this command" is a real and common state. Failing on it would break talk on
every camera whose firmware is quieter than the one we measured. What changes is
that a camera which answers with a *refusal* is no longer reported as success -
that is the case we were blind to.
"""
import asyncio
import struct
from types import SimpleNamespace
from unittest.mock import MagicMock

import pytest

from aidot_cameras.camera.webrtc import WebRTCSession

_HDR = "<IIqII4x"

SPEAKERSTART_RESP = 851
SETSTREAMCTRL_RESP = 801


def _reply(cmd, payload=b"\x00\x64"):
    return struct.pack(_HDR, 1, cmd, 0, len(payload), 0) + payload


def _session(dc=None):
    holder = {"provider": None}
    return WebRTCSession(
        pc=MagicMock(), outgoing_q=MagicMock(), mqtt_fut=MagicMock(),
        recorder=None, track_tasks=[],
        dc=dc if dc is not None else SimpleNamespace(send=lambda d: None),
        audio_sender=MagicMock(), talk_track=MagicMock(), talk_holder=holder,
    )


async def _start_talk(session, reply=None, delay=0.01):
    """Start talk, optionally answering as the camera would, and return the verdict."""
    task = asyncio.create_task(session.async_start_talk(lambda: b""))
    await asyncio.sleep(delay)
    if reply is not None:
        session.dispatch_avio_frame(reply)
    return await task


async def test_talk_reports_the_speaker_ack_when_the_camera_sends_one():
    session = _session()
    assert await _start_talk(session, _reply(SPEAKERSTART_RESP)) is True
    assert session._talk_holder["was_active"] is True


async def test_talk_still_succeeds_when_the_camera_says_nothing(monkeypatch):
    """A quieter firmware must not lose two-way audio.

    An A001064 answers 848 but does not implement 802 at all - a camera that
    answers nothing is a real state, not a failure.
    """
    from aidot_cameras.camera import protocol as proto
    monkeypatch.setattr(proto, "SPEAKER_ACK_TIMEOUT_S", 0.05)
    session = _session()
    assert await session.async_start_talk(lambda: b"") is True


async def test_talk_reports_failure_when_the_camera_refuses():
    """The case we were blind to: an answer that is not an acceptance."""
    session = _session()
    assert await _start_talk(session, _reply(SPEAKERSTART_RESP, b"\x00\x01")) is False


async def test_a_refused_speaker_does_not_leave_the_pump_running():
    """Reporting failure while still streaming viewer audio would be worse than
    the bug: the caller stops, the microphone does not."""
    session = _session()
    await _start_talk(session, _reply(SPEAKERSTART_RESP, b"\x00\x01"))
    assert session._talk_holder["provider"] is None


async def test_talk_without_a_channel_is_unchanged():
    session = _session(dc=None)
    session._talk_track = None
    assert await session.async_start_talk(lambda: b"") is False


def _sdes_session():
    from aidot_cameras.camera.sdes import SdesSession

    return SdesSession(
        proc=MagicMock(), sdp_path="/tmp/nonexistent.sdp",
        outgoing_q=MagicMock(), mqtt_fut=MagicMock(),
        cmd_chan=[lambda cmd, payload: None],
        talk_state={"provider": None},
    )


async def test_sdes_talk_waits_for_the_ack_without_sending_it_itself(monkeypatch):
    """On SDES the command must keep leaving from the bridge thread.

    All SCTP DATA stays on one thread or SPEAKERSTART races the heartbeat's TSN
    and the camera drops it - so this registers interest and lets the bridge
    send, rather than calling the request helper and moving the send onto the
    event loop.
    """
    import aidot_cameras.camera.sdes as sdes_mod

    monkeypatch.setattr(sdes_mod, "_run_sdes_talk_pump", lambda state: None)
    session = _sdes_session()
    session._avio_cmd = MagicMock(side_effect=AssertionError("must not send here"))

    # The bridge sends; here we only stand in for the camera's answer.
    task = asyncio.create_task(session.async_start_talk(lambda: b""))
    await asyncio.sleep(0.01)
    session.dispatch_avio_frame(_reply(SPEAKERSTART_RESP))

    assert await task is True
    assert session._talk_state["want_speaker"] is True


async def test_sdes_talk_reports_a_refusal(monkeypatch):
    import aidot_cameras.camera.sdes as sdes_mod

    monkeypatch.setattr(sdes_mod, "_run_sdes_talk_pump", lambda state: None)
    session = _sdes_session()

    task = asyncio.create_task(session.async_start_talk(lambda: b""))
    await asyncio.sleep(0.01)
    session.dispatch_avio_frame(_reply(SPEAKERSTART_RESP, b"\x00\x01"))

    assert await task is False
    # ...and the microphone is not left running against a closed speaker.
    assert session._talk_state["provider"] is None
    assert session._talk_state["want_speaker"] is False


@pytest.mark.parametrize("payload", [b"", b"\x00", b"\x00\x64\x00\x00"])
async def test_an_unreadable_ack_is_treated_as_acceptance(payload):
    """Do not invent a refusal out of a payload shape we have not seen.

    The one ack measured is 0x0064 on both transports. Anything else is unknown,
    and unknown must fall the same way as silence, or a firmware variation
    silently disables talk.
    """
    session = _session()
    assert await _start_talk(session, _reply(SPEAKERSTART_RESP, payload)) is True


async def test_resolution_asks_for_the_verdict_and_records_it():
    """800 is acked with 801 in 0.01-0.03s. Read it, so a refusal is visible.

    The RETURN value deliberately does not change. A001064 firmware answers 848
    but does not implement 802 at all, so "this camera never answers" is
    model-dependent - turning silence into a reported failure would break the
    call on exactly the cameras whose firmware is quieter. What we can do
    safely, and could not do before, is see the answer.
    """
    from aidot_cameras.camera.controls import _CameraControlsMixin

    class _Cam(_CameraControlsMixin):
        pass

    cam = _Cam()
    session = _session()
    seen = []
    session.async_avio_request = _record(seen)

    assert await cam_set(cam, session, "hd") is True
    assert seen and seen[0]["response_cmd"] == SETSTREAMCTRL_RESP


async def test_resolution_still_succeeds_when_the_camera_says_nothing():
    """The safety property: a quiet firmware must not lose the command."""
    from aidot_cameras.camera.controls import _CameraControlsMixin

    class _Cam(_CameraControlsMixin):
        pass

    cam = _Cam()
    session = _session()
    session.async_avio_request = _record([], reply=None)
    assert await cam_set(cam, session, "hd") is True


async def test_resolution_with_no_session_is_unchanged():
    """Remembering it for the next session is not a claim that it was applied."""
    from aidot_cameras.camera.controls import _CameraControlsMixin

    class _Cam(_CameraControlsMixin):
        pass

    cam = _Cam()
    cam._stream_session = None
    assert await cam.async_set_resolution("hd") is True
    assert cam._desired_quality == "hd"


async def test_an_unknown_quality_is_still_rejected():
    from aidot_cameras.camera.controls import _CameraControlsMixin

    class _Cam(_CameraControlsMixin):
        pass

    assert await _Cam().async_set_resolution("ultra") is False


def _record(seen, reply=SimpleNamespace(command=801, payload=b"\x00" * 8)):
    async def _req(cmd, payload=b"", **kwargs):
        seen.append(dict(kwargs, cmd=cmd, payload=payload))
        return reply
    return _req


async def cam_set(cam, session, quality):
    cam._stream_session = session
    return await cam.async_set_resolution(quality)
