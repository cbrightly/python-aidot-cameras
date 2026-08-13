"""The playcontrol wire format, pinned before it meets a camera.

The request shape is not guesswork: it is
AVIOCTRLDEFs$SMsgAVIoctrlPlayRecord.parseContent(III[B) from the vendor app -
24 bytes, little-endian, with an 8-byte STimeDay at offset 12.
"""
import struct

import pytest

from aidot_cameras.camera.sd_events import SdEvent
from aidot_cameras.camera.sd_playback import (
    PLAY_START,
    PLAY_STOP,
    decode_playcontrol_reply,
    playcontrol_payload,
)


def _rec(**kw):
    base = dict(year=2026, month=8, day=11, hour=20, minute=41, second=42,
                channel=0, event=1, status=0)
    base.update(kw)
    return SdEvent(**base)


def test_the_payload_is_exactly_twenty_four_bytes():
    # The vendor allocates 0x18 and the camera rejects anything else outright.
    assert len(playcontrol_payload(PLAY_START, _rec())) == 24


def test_the_payload_matches_the_bytes_the_camera_accepted():
    # Captured 2026-08-13 from a request an A000088 answered in 0.05 s. The
    # first 20 bytes are that exact capture; the trailing byte is 01 because
    # the default sd_channel is 1, not the 0 this capture actually carried -
    # see test_the_channel_is_a_byte_at_offset_twenty_not_the_first_word for
    # why 0 is never sent.
    expected = bytes.fromhex("000000001000000000000000ea07080b0014292a01000000")
    assert playcontrol_payload(PLAY_START, _rec()) == expected


def test_the_command_param_and_channel_land_where_the_struct_says():
    out = playcontrol_payload(PLAY_STOP, _rec(), sd_channel=2, param=7)
    command = struct.unpack("<I", out[4:8])[0]
    assert (command, out[20], out[21]) == (PLAY_STOP, 2, 7)


def test_the_timestamp_is_the_records_own():
    out = playcontrol_payload(PLAY_START, _rec(year=2025, month=1, day=2,
                                               hour=3, minute=4, second=5))
    y, mo, d, _wd, h, mi, s = struct.unpack("<HBBBBBB", out[12:20])
    assert (y, mo, d, h, mi, s) == (2025, 1, 2, 3, 4, 5)


def test_a_real_reply_decodes():
    # The exact 20 bytes an A000088 returned, 2026-08-13.
    reply = decode_playcontrol_reply(
        bytes.fromhex("10000000010000003c000000ea07080b0014292a"))
    assert reply.command == PLAY_START
    assert reply.field1 == 1
    assert reply.field2 == 60
    assert (reply.year, reply.month, reply.day) == (2026, 8, 11)
    assert (reply.hour, reply.minute, reply.second) == (20, 41, 42)


def test_a_short_or_absent_reply_is_none_not_an_exception():
    # Same discipline as the listing decode: a reply that cannot be read is an
    # unknown, never a traceback out of the playback path.
    for junk in (b"", b"\x00", b"\x00" * 19, None):
        assert decode_playcontrol_reply(junk) is None


def test_the_channel_is_a_byte_at_offset_twenty_not_the_first_word():
    # The vendor app always sends 0 in the first u32 and puts the real channel
    # in byte 20. Writing a channel into offset 0 produces a request the camera
    # accepts and answers - while sending no media at all.
    out = playcontrol_payload(PLAY_START, _rec(), sd_channel=7)
    assert struct.unpack("<I", out[:4])[0] == 0
    assert out[20] == 7


def test_the_default_channel_is_one_because_zero_is_never_sent():
    # sdRecordPlay increments from 0 before its first use and wraps 26 -> 1, so
    # a real playback never carries channel 0.
    assert playcontrol_payload(PLAY_START, _rec())[20] == 1


def test_the_param_is_a_byte_at_offset_twenty_one():
    out = playcontrol_payload(PLAY_START, _rec(), param=3)
    assert out[21] == 3
    assert struct.unpack("<I", out[8:12])[0] == 0


def test_a_channel_outside_the_vendor_range_is_refused():
    # A caller bug, not a protocol surprise. Clamping it silently would send a
    # channel the caller did not choose and reproduce the exact failure this
    # change exists to fix.
    for bad in (0, -1, 27, 300):
        with pytest.raises(ValueError):
            playcontrol_payload(PLAY_START, _rec(), sd_channel=bad)


def test_the_first_twenty_bytes_still_match_what_the_camera_accepted():
    # The measured exchange is still the reference for everything before the
    # trailing field - only the last four bytes were ever in doubt.
    expected = bytes.fromhex("000000001000000000000000ea07080b0014292a")
    assert playcontrol_payload(PLAY_START, _rec(), sd_channel=1)[:20] == expected
