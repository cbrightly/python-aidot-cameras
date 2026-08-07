"""Ask the camera something and wait for its answer, on both transports.

The router (``test_avio_response_router.py``) knows how to match a reply to a
question. This is the layer that actually asks: it registers interest, sends the
command, and hands back what came back - or None if nothing did.

Two things are deliberate here:

*   **``_avio_cmd`` is untouched.** It is used by every camera on both
    transports - the speaker, the keepalive heartbeat, resolution - and the
    CHANGELOG records fleet-wide blackouts from changes to shared paths. It
    stays fire-and-forget and still returns a bool; asking for a reply is a
    separate, opt-in call.
*   **Registration happens before the send.** A camera on the LAN can answer
    faster than the sending call returns, and a waiter registered afterwards
    would miss the reply and report "no answer" from a camera that answered.
"""
import asyncio
import struct
from types import SimpleNamespace
from unittest.mock import MagicMock

import pytest

from aidot_cameras.camera.protocol import AvioResponseRouter
from aidot_cameras.camera.sdes import SdesSession
from aidot_cameras.camera.webrtc import WebRTCSession

_HDR = "<IIqII4x"

SETSTREAMCTRL = 800
SETSTREAMCTRL_RESP = 801


def _reply(cmd: int = SETSTREAMCTRL_RESP, payload: bytes = b"\x00\x05") -> bytes:
    return struct.pack(_HDR, 1, cmd, 0, len(payload), 0) + payload


def _dtls_session(dc):
    return WebRTCSession(
        pc=MagicMock(),
        outgoing_q=MagicMock(),
        mqtt_fut=MagicMock(),
        recorder=None,
        track_tasks=[],
        dc=dc,
    )


def _sdes_session(send):
    return SdesSession(
        proc=MagicMock(),
        sdp_path="/tmp/nonexistent.sdp",
        outgoing_q=MagicMock(),
        mqtt_fut=MagicMock(),
        cmd_chan=[send],
    )


async def _ask(session, **kwargs):
    """Start a request, let it send and settle into its wait, hand back the task."""
    task = asyncio.create_task(
        session.async_avio_request(SETSTREAMCTRL, **kwargs)
    )
    await asyncio.sleep(0.01)
    return task


async def test_a_dtls_request_returns_the_cameras_reply():
    sent = []
    session = _dtls_session(SimpleNamespace(send=sent.append))

    body = b"\x00\x00\x00\x00\x05\x00\x00\x00"
    task = await _ask(
        session, payload=body, response_cmd=SETSTREAMCTRL_RESP, timeout=2.0
    )
    assert session.dispatch_avio_frame(_reply()) is True

    got = await task
    assert got is not None
    assert got.command == SETSTREAMCTRL_RESP
    assert got.payload == b"\x00\x05"
    # The command really went out, and unchanged: same header layout, same body.
    assert len(sent) == 1
    assert struct.unpack_from(_HDR, sent[0])[1] == SETSTREAMCTRL
    assert sent[0][struct.calcsize(_HDR):] == body


async def test_an_sdes_request_returns_the_cameras_reply():
    """Same contract over SCTP, where the reply arrives on the bridge thread."""
    sent = []
    session = _sdes_session(lambda cmd, payload: sent.append((cmd, payload)))

    task = await _ask(
        session, payload=b"\x05", response_cmd=SETSTREAMCTRL_RESP, timeout=2.0
    )
    assert session.dispatch_avio_frame(_reply()) is True

    got = await task
    assert got is not None
    assert got.payload == b"\x00\x05"
    assert sent == [(SETSTREAMCTRL, b"\x05")]


async def test_the_wait_is_registered_before_the_command_goes_out():
    """A camera that answers inside the send call must still be heard.

    If interest were registered after sending, this reply would arrive with
    nobody listening and the caller would be told the camera said nothing.
    """
    session = None

    def _send_and_answer(data):
        session.dispatch_avio_frame(_reply())

    session = _dtls_session(SimpleNamespace(send=_send_and_answer))

    got = await session.async_avio_request(
        SETSTREAMCTRL, response_cmd=SETSTREAMCTRL_RESP, timeout=2.0
    )
    assert got is not None
    assert got.payload == b"\x00\x05"


async def test_a_request_that_could_not_be_sent_does_not_wait():
    """No channel means no answer is coming - do not burn the timeout on it."""
    session = _dtls_session(None)  # DataChannel not open

    loop = asyncio.get_running_loop()
    started = loop.time()
    got = await session.async_avio_request(
        SETSTREAMCTRL, response_cmd=SETSTREAMCTRL_RESP, timeout=30.0
    )
    assert got is None
    assert loop.time() - started < 1.0

    # ...and it left nothing registered behind: a reply that turns up late
    # belongs to nobody.
    assert session.dispatch_avio_frame(_reply()) is False


async def test_a_silent_camera_times_out_rather_than_hanging():
    session = _dtls_session(SimpleNamespace(send=lambda data: None))

    got = await session.async_avio_request(
        SETSTREAMCTRL, response_cmd=SETSTREAMCTRL_RESP, timeout=0.05
    )
    assert got is None


async def test_an_unrelated_frame_does_not_answer_the_request():
    """804 (track-switch notify) arrives unprompted; it is not our verdict."""
    session = _dtls_session(SimpleNamespace(send=lambda data: None))

    task = await _ask(session, response_cmd=SETSTREAMCTRL_RESP, timeout=0.3)
    assert session.dispatch_avio_frame(_reply(cmd=804, payload=b"\x01")) is False

    assert await task is None


@pytest.mark.parametrize("transport", ["dtls", "sdes"])
async def test_fire_and_forget_still_behaves_exactly_as_before(transport):
    """The shared path every camera uses is unchanged: sync, bool, no waiting."""
    if transport == "dtls":
        sent = []
        session = _dtls_session(SimpleNamespace(send=sent.append))
        assert session._avio_cmd(848, b"\x00" * 8) is True
        assert len(sent) == 1
        assert _dtls_session(None)._avio_cmd(848) is False
    else:
        sent = []
        session = _sdes_session(lambda cmd, payload: sent.append((cmd, payload)))
        assert session._avio_cmd(848, b"\x00" * 8) is True
        assert sent == [(848, b"\x00" * 8)]
        assert _sdes_session(None)._avio_cmd(848) is False


@pytest.mark.parametrize("transport", ["dtls", "sdes"])
async def test_a_session_listens_on_the_router_it_was_handed(transport):
    """The transports have to own the router: it exists before the session does.

    Both receive paths are set up inside the open sequence - the DataChannel
    message handler is registered while the peer connection is still being
    negotiated, and the SDES bridge starts before the session object is built.
    They capture a router and pass it in, so a reply that lands during those
    first moments has somewhere to go instead of being dropped.
    """
    router = AvioResponseRouter()
    if transport == "dtls":
        session = WebRTCSession(
            pc=MagicMock(),
            outgoing_q=MagicMock(),
            mqtt_fut=MagicMock(),
            recorder=None,
            track_tasks=[],
            dc=SimpleNamespace(send=lambda data: None),
            responses=router,
        )
    else:
        session = SdesSession(
            proc=MagicMock(),
            sdp_path="/tmp/nonexistent.sdp",
            outgoing_q=MagicMock(),
            mqtt_fut=MagicMock(),
            cmd_chan=[lambda cmd, payload: None],
            responses=router,
        )

    task = await _ask(session, response_cmd=SETSTREAMCTRL_RESP, timeout=2.0)
    assert router.dispatch(_reply()) is True

    got = await task
    assert got is not None
    assert got.payload == b"\x00\x05"


async def test_dispatching_a_frame_nobody_asked_for_is_harmless():
    """Every session sees unsolicited control traffic all session long."""
    session = _dtls_session(SimpleNamespace(send=lambda data: None))
    assert session.dispatch_avio_frame(_reply()) is False
    assert session.dispatch_avio_frame(b"junk") is False
