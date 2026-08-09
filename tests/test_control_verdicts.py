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


async def test_a_refused_speaker_would_not_leave_the_pump_running(monkeypatch):
    """No refusal payload is currently identifiable, so this drives the verdict
    directly rather than inventing one.

    The property is still worth holding: if a refusal is ever recognised, the
    caller stops and the microphone must stop with it. Reporting failure while
    still streaming viewer audio at a closed speaker would be worse than the
    original bug.
    """
    from aidot_cameras.camera import protocol as proto

    async def _refuse(session, cmd, resp_cmd, timeout):
        return False

    monkeypatch.setattr(proto, "_speaker_opened", _refuse)
    monkeypatch.setattr("aidot_cameras.camera.webrtc._speaker_opened", _refuse)
    session = _session()

    assert await session.async_start_talk(lambda: b"") is False
    assert session._talk_holder["provider"] is None
    enabled = [c for c in session._audio_sender.replaceTrack.call_args_list
               if c.args and c.args[0] is session._talk_track]
    assert not enabled


async def test_the_microphone_is_not_opened_before_the_speaker_answers():
    """Order matters: nothing may go out before the camera has answered.

    replaceTrack used to happen first, so the microphone was live and encoding
    for the whole round trip. Checked at the moment the ack lands rather than
    via a refusal, because there is no refusal payload to use.
    """
    session = _session()
    task = asyncio.create_task(session.async_start_talk(lambda: b""))
    await asyncio.sleep(0.01)

    def _mic_calls():
        return [c for c in session._audio_sender.replaceTrack.call_args_list
                if c.args and c.args[0] is session._talk_track]

    assert not _mic_calls(), "the microphone was enabled before the camera answered"
    session.dispatch_avio_frame(_reply(SPEAKERSTART_RESP))
    assert await task is True
    assert _mic_calls(), "an accepted speaker must still enable the microphone"


async def test_talk_without_a_channel_is_unchanged():
    session = _session(dc=None)
    session._talk_track = None
    assert await session.async_start_talk(lambda: b"") is False


def _sdes_session():
    from aidot_cameras.camera.sdes import SdesSession

    # poll() -> None is "ffmpeg still running". A bare MagicMock returns a
    # truthy mock, which now reads as an exited process and makes the session
    # correctly refuse talk - so the mock has to say which it is.
    proc = MagicMock()
    proc.poll.return_value = None
    return SdesSession(
        proc=proc, sdp_path="/tmp/nonexistent.sdp",
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

    # Stand in for the bridge thread: it dispatches SPEAKERSTART and sets
    # speaker_on, and the camera answers. Both halves are needed - a True answer
    # now requires that our own bridge actually sent the command, not just that
    # something acked, so that a session whose bridge has died reports False
    # instead of a speaker it never opened.
    task = asyncio.create_task(session.async_start_talk(lambda: b""))
    await asyncio.sleep(0.01)
    session._talk_state["speaker_on"] = True
    session.dispatch_avio_frame(_reply(SPEAKERSTART_RESP))

    assert await task is True
    assert session._talk_state["want_speaker"] is True


@pytest.mark.parametrize("payload", [b"", b"\x00", b"\x00\x64\x00\x00", b"\xff\xff"])
async def test_an_unfamiliar_ack_is_treated_as_acceptance(payload):
    """An ack is an ack. No payload has ever meant refusal.

    Every value observed - 0x0064 and 0x00c8, including both from one camera on
    consecutive sessions - came back from a speaker that opened. Reading an
    unfamiliar payload as refusal is what would have disabled talk on the L2.
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


async def test_the_sdes_budget_covers_the_bridge_delay():
    """The bridge waits SDES_SPEAKERSTART_DELAY before it sends 848 at all.

    If the ack budget did not account for that, the wait would expire before the
    command had even left, and the whole check would silently degrade to "the
    camera said nothing" while costing every press-to-talk the full timeout.
    """
    from aidot_cameras.camera.constants import SDES_SPEAKERSTART_DELAY
    from aidot_cameras.camera.sdes import SDES_SPEAKER_ACK_TIMEOUT_S
    from aidot_cameras.camera.protocol import SPEAKER_ACK_TIMEOUT_S

    assert SDES_SPEAKER_ACK_TIMEOUT_S >= SDES_SPEAKERSTART_DELAY + SPEAKER_ACK_TIMEOUT_S


@pytest.mark.parametrize("payload,camera", [
    (b"\x00\x64", "A000088 DTLS and A001064 SDES"),
    (b"\x00\xc8", "A001513 SDES"),
])
async def test_every_observed_speaker_ack_is_an_acceptance(payload, camera):
    """The ack payload differs per model, and both observed values are successes.

    Measured 2026-08-07 on live cameras: an M3 Pro and the PTZ answer 0x0064,
    an L2 answers 0x00c8. Both opened their speakers.

    The first cut of this check treated 0x0064 as "the" success value and any
    other two-byte payload as a refusal - a discriminator invented from one
    sample, which would have disabled two-way audio on the L2 entirely. No
    refusal payload has ever been observed, so there is nothing to discriminate
    against yet; an ack is an ack, and the payload is logged so that a real
    refusal can be recognised when one turns up.
    """
    session = _session()
    assert await _start_talk(session, _reply(SPEAKERSTART_RESP, payload)) is True, camera
