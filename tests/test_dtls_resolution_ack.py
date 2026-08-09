"""The SETSTREAMCTRL ack read, over a real DTLS session end to end.

`async_set_resolution` asks the camera (800) and reads what comes back (801).
On SDES that whole round trip has been exercised on hardware - a 2026-08-07
sweep sent all six AVIOCTRL_QUALITY values to an A001064 across twelve sessions
and read every reply, acks landing in 0.01-0.19 s. On DTLS it has never been
run against a camera for this command.

What was missing here was not "a test", it was a test that joins the two halves.
Every existing resolution test replaces `async_avio_request` with a stub
(`test_resolution_persists.py`, `test_control_verdicts.py`), and every existing
transport test calls `async_avio_request` directly with a payload of its own
(`test_avio_request.py`). So nothing asserted that the bytes the setter builds
are the bytes a real `WebRTCSession` puts on the DataChannel, nor that a reply
arriving on that session's receive entry point is the one the setter reads.
These use the real session, the real router, and frames in the camera's own
header layout, and reach it through `async_set_resolution` rather than around
it.

Reading the reply is the only observable the call has: the return value is
deliberately True whether or not the camera answers, because an A001064 answers
SPEAKERSTART yet does not implement GETSTREAMCTRL at all, and turning silence
into a failure would break this call on exactly the quieter firmwares. So the
assertions below are about *what the read reports* - the response command id and
the ack payload - not about the return.

None of this changes the standing measurement that the setting has no effect on
the encode (see `test_no_resolution_select.py` in the integration): quality 1
and quality 5 both produce 1280x720 at the same bytes per frame. This is about
the ack read only.
"""
import asyncio
import logging
import struct
from types import SimpleNamespace
from unittest.mock import MagicMock

import pytest

from aidot_cameras.camera import controls as controls_mod
from aidot_cameras.camera.constants import _STREAM_QUALITY, SETSTREAMCTRL_CMD
from aidot_cameras.camera.controls import SETSTREAMCTRL_RESP_CMD, _CameraControlsMixin
from aidot_cameras.camera.protocol import parse_avio_response
from aidot_cameras.camera.webrtc import WebRTCSession

_HDR = "<IIqII4x"
_CONTROLS_LOGGER = "aidot_cameras.camera.controls"

#: Arrives unprompted when the camera switches track; nobody asked for it.
TRACK_SWITCH_NOTIFY = 804

#: A 801 ack body in the camera's own layout: an ack that is not all zeros, so
#: an assertion on its hex cannot pass on a frame built out of padding.
ACK_BODY = b"\x00\x05"


def _frame(cmd: int = SETSTREAMCTRL_RESP_CMD, payload: bytes = ACK_BODY) -> bytes:
    return struct.pack(_HDR, 7, cmd, 0, len(payload), 0) + payload


class _Cam(_CameraControlsMixin):
    """Just the control setters, on a session we can watch."""

    def __init__(self, session) -> None:
        self._stream_session = session
        self._desired_quality = None


def _dtls_session(on_send=None, *, dc_open: bool = True):
    """A real WebRTCSession over a DataChannel that records what it is given.

    `on_send` runs inside `dc.send`, which is how a camera on the LAN can answer
    before the sending call has returned.  `dc_open=False` is the session before
    its DataChannel opens, and after it closes.
    """
    sent: list = []
    holder: dict = {}

    def _send(data) -> None:
        sent.append(bytes(data))
        if on_send is not None:
            on_send(holder["session"])

    session = WebRTCSession(
        pc=MagicMock(),
        outgoing_q=MagicMock(),
        mqtt_fut=MagicMock(),
        recorder=None,
        track_tasks=[],
        dc=SimpleNamespace(send=_send) if dc_open else None,
    )
    holder["session"] = session
    return session, sent


def _read_lines(caplog) -> list:
    return [r.getMessage() for r in caplog.records if r.name == _CONTROLS_LOGGER]


@pytest.mark.parametrize("quality", ["hd", "sd"])
async def test_the_setter_puts_a_decodable_setstreamctrl_frame_on_the_wire(
    quality, monkeypatch
):
    """What the setter builds has to survive the session that sends it.

    The setter packs `<IB3x` and the session wraps it in the AVIO header; if
    either end of that join were wrong the camera would decode a different
    command or a different quality, and every existing test would still pass
    because they stub one side or supply their own payload for the other.
    """
    monkeypatch.setattr(controls_mod, "_SETSTREAMCTRL_ACK_S", 0.05)
    session, sent = _dtls_session()

    assert await _Cam(session).async_set_resolution(quality) is True

    assert len(sent) == 1
    frame = parse_avio_response(sent[0])
    assert frame is not None, "the camera could not decode what we sent it"
    assert frame.command == SETSTREAMCTRL_CMD
    assert len(frame.payload) == 8          # channel(0) + quality + 3 reserved
    assert frame.payload[4] == _STREAM_QUALITY[quality]


async def test_the_cameras_ack_is_read_and_reported(caplog):
    """The point of the whole call: a reply that arrives is looked at.

    Before the read existed this was a fire-and-forget send, so a camera that
    refused was indistinguishable from one that complied.
    """
    session, _sent = _dtls_session()
    cam = _Cam(session)

    with caplog.at_level(logging.DEBUG, logger=_CONTROLS_LOGGER):
        task = asyncio.create_task(cam.async_set_resolution("sd"))
        await asyncio.sleep(0.01)
        assert session.dispatch_avio_frame(_frame()) is True
        assert await task is True

    reported = [
        line for line in _read_lines(caplog)
        if str(SETSTREAMCTRL_RESP_CMD) in line and ACK_BODY.hex() in line
    ]
    assert reported, f"the ack was never reported: {_read_lines(caplog)}"


async def test_a_camera_that_answers_inside_the_send_is_still_heard(caplog):
    """On DTLS the reply can be delivered before `dc.send` returns.

    Both DataChannel message handlers dispatch on the event loop the send was
    made from, so "the camera answered already" is not exotic here - and a wait
    registered after the send would report silence from a camera that answered.
    """
    session, _sent = _dtls_session(
        on_send=lambda s: s.dispatch_avio_frame(_frame())
    )

    with caplog.at_level(logging.DEBUG, logger=_CONTROLS_LOGGER):
        assert await _Cam(session).async_set_resolution("hd") is True

    assert any(
        str(SETSTREAMCTRL_RESP_CMD) in line and ACK_BODY.hex() in line
        for line in _read_lines(caplog)
    ), f"an answer that beat the send was lost: {_read_lines(caplog)}"


async def test_a_silent_camera_is_not_reported_as_having_acked(
    caplog, monkeypatch
):
    """Silence is a real answer on this firmware - it must not be dressed up.

    And the wait has to stop waiting: `_avio_cmd` is on the keepalive path, so a
    registration left behind on every unanswered command grows for the life of
    the session.
    """
    monkeypatch.setattr(controls_mod, "_SETSTREAMCTRL_ACK_S", 0.05)
    session, sent = _dtls_session()

    with caplog.at_level(logging.DEBUG, logger=_CONTROLS_LOGGER):
        assert await _Cam(session).async_set_resolution("sd") is True

    assert len(sent) == 1
    assert not [
        line for line in _read_lines(caplog)
        if str(SETSTREAMCTRL_RESP_CMD) in line
    ], "a camera that said nothing was reported as answering"
    # The router is asked directly rather than through a late reply: a stale
    # registration is skipped on dispatch anyway (its future is cancelled), so
    # dispatching would report "nobody was waiting" whether or not the entry was
    # ever removed, and the leak this guards against would go unnoticed.
    assert session._avio_responses._waiting == {}, (
        "the wait is still registered after it gave up; _avio_cmd is on the "
        "keepalive path, so this grows for the life of the session"
    )


async def test_unprompted_traffic_is_not_read_as_the_ack(caplog):
    """804 (track switch) arrives on this channel throughout a session.

    "The next frame in" is not the answer to the last question out, and a
    control channel that answered the wrong question would report a refusal, or
    a compliance, that the camera never expressed.
    """
    session, _sent = _dtls_session()
    cam = _Cam(session)

    with caplog.at_level(logging.DEBUG, logger=_CONTROLS_LOGGER):
        task = asyncio.create_task(cam.async_set_resolution("sd"))
        await asyncio.sleep(0.01)
        assert session.dispatch_avio_frame(
            _frame(cmd=TRACK_SWITCH_NOTIFY, payload=b"\x01\x02")) is False
        assert not _read_lines(caplog), "answered before the camera replied"

        assert session.dispatch_avio_frame(_frame()) is True
        assert await task is True

    assert any(
        str(SETSTREAMCTRL_RESP_CMD) in line and ACK_BODY.hex() in line
        for line in _read_lines(caplog)
    )
    assert not any("0102" in line for line in _read_lines(caplog))


async def test_a_closed_datachannel_does_not_burn_the_ack_budget(monkeypatch):
    """No channel means no answer is coming; do not hold the caller for it.

    Reachable on DTLS between the session being built and the DataChannel
    opening, and again once it closes. These calls run behind Home Assistant
    service calls, so waiting out a budget for a command that never left is a
    stall the user pays for.
    """
    monkeypatch.setattr(controls_mod, "_SETSTREAMCTRL_ACK_S", 5.0)
    session, sent = _dtls_session(dc_open=False)

    loop = asyncio.get_running_loop()
    started = loop.time()
    assert await _Cam(session).async_set_resolution("sd") is True
    assert loop.time() - started < 1.0
    assert sent == []
