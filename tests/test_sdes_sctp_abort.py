"""Tell the camera the SCTP association is over, instead of walking away.

Measured 2026-08-07 on a mains A001064: close a session, reopen 2s later and the
camera answers `-50002` (session-exceed); reopen at 8s and it is fine. So the
camera holds the previous session for a few seconds after we stop talking - and
it has no reason not to, because we never say we are leaving.

Both of the other implementations do say so:

    the official app   dataChannel.dispose()  -> libwebrtc closes the association
    our DTLS path      aiortc RTCSctpTransport.stop() sends an ABORT chunk
    our SDES path      nothing at all - we build only INIT, INIT-ACK and DATA

This adds the missing chunk. Whether it actually shortens the refusal window is a
separate question that only the live before/after can answer: `-50002` is an
application-level ack on `webrtcReq`, and the camera may release the session on
heartbeat loss regardless of what the transport says. Closing the association we
opened is correct either way, which is why it is worth doing even if the window
does not move.
"""
import struct

from aidot_cameras.camera.sdes_open import _sctp_abort_chunk


def test_it_is_a_well_formed_abort_chunk():
    """Type 6, no flags, length 4, no parameters - RFC 4960 s3.3.7."""
    chunk = _sctp_abort_chunk()
    ctype, flags, length = struct.unpack("!BBH", chunk[:4])
    assert ctype == 6
    assert length == 4
    assert len(chunk) == 4


def test_the_t_bit_is_clear():
    """T=0 says we are sending our peer's verification tag, which we have.

    Setting it would tell the camera we are reflecting a tag we do not hold, and
    an endpoint that has the association must not claim that.
    """
    _, flags, _, = struct.unpack("!BBH", _sctp_abort_chunk()[:4])
    assert flags & 0x01 == 0


from unittest.mock import MagicMock

from aidot_cameras.camera.sdes import SdesSession


def _session(abort=None):
    s = SdesSession(
        proc=MagicMock(), sdp_path="/tmp/nonexistent.sdp",
        outgoing_q=MagicMock(), mqtt_fut=MagicMock(),
        abort_chan=[abort],
    )
    s._proc.poll.return_value = 0
    s._proc.stderr.read.return_value = b""
    return s


async def test_stop_aborts_before_killing_ffmpeg():
    """Order is the whole point.

    The socket the ABORT goes out on belongs to the bridge, which dies with
    ffmpeg - so an ABORT attempted after terminate() has nothing to send on, and
    the camera is left to time the association out exactly as before.
    """
    order = []
    session = _session(abort=lambda: order.append("abort") or True)
    session._proc.terminate.side_effect = lambda: order.append("terminate")

    await session.stop()

    assert order[:2] == ["abort", "terminate"], order


async def test_a_failing_abort_never_blocks_the_close():
    """Closing politely must not stop us closing at all."""
    def _boom():
        raise OSError("socket gone")

    session = _session(abort=_boom)
    await session.stop()
    session._proc.terminate.assert_called_once()


async def test_a_session_with_no_abort_sender_still_stops():
    """The bridge installs the sender once the command channel is up; a session
    torn down before that has none, and teardown must not depend on it."""
    session = _session(abort=None)
    await session.stop()
    session._proc.terminate.assert_called_once()


async def test_a_session_without_the_holder_at_all_still_stops():
    """Older call sites pass no holder."""
    s = SdesSession(proc=MagicMock(), sdp_path="/tmp/nonexistent.sdp",
                    outgoing_q=MagicMock(), mqtt_fut=MagicMock())
    s._proc.poll.return_value = 0
    s._proc.stderr.read.return_value = b""
    await s.stop()
    s._proc.terminate.assert_called_once()
