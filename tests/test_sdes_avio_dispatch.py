"""Inbound AVIO on SDES arrives as an encrypted SCTP DATA chunk, not as audio.

The SDES bridge sees control traffic in two shapes and they are not the same
thing:

*   frames the camera sends **framed as TUTK audio** (0xC8), which the audio
    forward path already recognises by command id so it never reaches ffmpeg;
*   frames the camera sends as **SCTP DATA chunks** (PPID 53) inside the
    encrypted channel - the same channel we send LIVING, the heartbeat and
    SPEAKERSTART on.

Replies to our commands come back on the second one. Measured 2026-08-07 on an
A001064 with the full receive-path log on: sending SPEAKERSTART (848) produced

    SDES DC: enc DATA ppid=53 cmd=851 32B f8199c2853030000...0064

within a moment - alongside the session-mode notify (5377) and the heartbeat ack
(5157), both arriving on the same chunk type throughout the session. The first
attempt at wiring this dispatched from the audio path only, so every reply was
parsed, logged and dropped, and the camera looked silent on SDES while answering
in 0.4s on DTLS. That is precisely the failure the design warned about: "no
reply" reads as a firmware limitation when it is really a channel we never
listened to.

The bytes below are the real ones off the wire.
"""
import struct

import pytest

from aidot_cameras.camera.protocol import AvioResponseRouter
from aidot_cameras.camera.sdes_open import _dispatch_sctp_avio

#: An actual SPEAKERSTART ack captured from an A001064 (payload 0x0064).
REAL_851 = bytes.fromhex(
    "f8199c28" "53030000" "13c6756a00000000" "02000000" "00000000" "00000000"
    "0064"
)
#: The heartbeat ack, which arrives unprompted all session long.
REAL_5157 = bytes.fromhex(
    "d1c50942" "25140000" "e8a40ddc9f010000" "00000000" "00000000" "00000000"
)


def test_a_reply_on_the_sctp_channel_reaches_the_waiter():
    router = AvioResponseRouter()
    waiter = router.expect(851)

    assert _dispatch_sctp_avio(router, REAL_851) is True
    assert waiter._future.result(timeout=1).payload == b"\x00\x64"


def test_unprompted_traffic_on_that_channel_is_not_an_error():
    """5377 and 5157 arrive throughout the session with nobody waiting."""
    router = AvioResponseRouter()
    assert _dispatch_sctp_avio(router, REAL_5157) is False


def test_a_reply_for_a_different_command_does_not_answer_ours():
    router = AvioResponseRouter()
    router.expect(851)
    assert _dispatch_sctp_avio(router, REAL_5157) is False


@pytest.mark.parametrize("blob", [b"", b"\x00" * 8, b"short", None])
def test_junk_is_ignored_rather_than_raised(blob):
    """This runs inline on the bridge's receive loop; it may never throw."""
    router = AvioResponseRouter()
    router.expect(851)
    assert _dispatch_sctp_avio(router, blob) is False


def test_a_missing_router_is_survivable():
    """Belt and braces: the bridge must not care whether one was handed over."""
    assert _dispatch_sctp_avio(None, REAL_851) is False


def test_the_captured_frame_really_is_the_layout_we_decode():
    """Guards the fixture itself: a mistyped capture would make this vacuous."""
    seq, cmd, _ts, length, _res = struct.unpack_from("<IIqII4x", REAL_851, 0)
    assert cmd == 851
    assert length == 2
    assert len(REAL_851) == struct.calcsize("<IIqII4x") + length
    assert seq == 0x289C19F8
