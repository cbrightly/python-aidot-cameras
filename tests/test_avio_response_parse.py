"""Decode the AVIO responses the camera sends back.

Every AVIO control we send today is fire-and-forget: the camera replies and the
library discards it. That is why three separate questions stayed open at once -
whether the resolution command is accepted or refused, what stream profile the
camera is actually running, and whether any control landed at all. The official
app does not work this way: its `setResolution` takes a
`SetResolutionRespListener` and it separately issues `GetStreamCtrlReq` (802) to
read the current value back.

Wire format, taken from the frames we already build (`<IIqII4x` + payload):

    offset  0  uint32   seq
    offset  4  uint32   command id
    offset  8  int64    timestamp (ms)
    offset 16  uint32   payload length
    offset 20  uint32   reserved
    offset 24  4 bytes  padding
    offset 28  payload
"""
import struct

import pytest

from aidot_cameras.camera.protocol import parse_avio_response

_HDR = "<IIqII4x"


def _frame(cmd: int, payload: bytes = b"", *, seq: int = 7, ts: int = 1234) -> bytes:
    return struct.pack(_HDR, seq, cmd, ts, len(payload), 0) + payload


def test_reads_the_command_id_and_payload():
    """The two things a caller needs: what answered, and what it said."""
    got = parse_avio_response(_frame(801, b"\x00\x05"))
    assert got is not None
    assert got.command == 801
    assert got.payload == b"\x00\x05"


def test_a_response_with_no_payload_is_still_a_response():
    """An ack with an empty body means "heard you" - not "nothing arrived"."""
    got = parse_avio_response(_frame(5157))
    assert got is not None
    assert got.command == 5157
    assert got.payload == b""


def test_a_truncated_frame_is_rejected():
    """Short reads must not be mistaken for a valid ack."""
    assert parse_avio_response(_frame(801, b"\x01")[:20]) is None


def test_a_payload_shorter_than_its_declared_length_is_rejected():
    """A length field that overruns the buffer is a corrupt frame, not an ack.

    Trusting it would hand callers a truncated payload and read a resolution out
    of bytes that were never sent.
    """
    bad = struct.pack(_HDR, 1, 801, 0, 64, 0) + b"\x00\x05"
    assert parse_avio_response(bad) is None


def test_trailing_bytes_beyond_the_declared_length_are_not_returned():
    """The length field decides the payload, not whatever else is in the buffer."""
    frame = struct.pack(_HDR, 1, 801, 0, 2, 0) + b"\x00\x05" + b"junkjunk"
    got = parse_avio_response(frame)
    assert got is not None
    assert got.payload == b"\x00\x05"


@pytest.mark.parametrize("blob", [b"", b"\x00" * 4, b"not an avio frame at all"])
def test_junk_is_rejected_rather_than_guessed_at(blob):
    assert parse_avio_response(blob) is None
