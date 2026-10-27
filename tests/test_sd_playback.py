"""The playcontrol wire format, pinned before it meets a camera.

The request shape is the vendor app's
AVIOCTRLDEFs$SMsgAVIoctrlPlayRecord.parseContent(IIIIJII) - 24 bytes,
little-endian, with an 8-byte STimeDay at offset 12. The golden bytes below
are a request and reply an A001064 exchanged on 2026-10-27 in a session that
then delivered the recording.
"""

import struct

import pytest

from aidot_cameras.camera.sd_events import SdEvent
from aidot_cameras.camera.sd_playback import (
    PLAY_SEEKTIME,
    PLAY_START,
    PLAY_STOP,
    decode_playcontrol_reply,
    playcontrol_payload,
)


def _rec(**kw):
    base = dict(
        year=2026,
        month=10,
        day=21,
        hour=17,
        minute=24,
        second=44,
        channel=0,
        event=0,
        status=0,
    )
    base.update(kw)
    return SdEvent(**base)


def test_the_payload_matches_the_request_a_camera_played():
    expected = bytes.fromhex("000000001000000000000000ea070a150411182c01000000")
    assert playcontrol_payload(PLAY_START, _rec()) == expected


def test_the_payload_is_exactly_twenty_four_bytes():
    assert len(playcontrol_payload(PLAY_STOP, _rec())) == 24


def test_wday_is_java_day_of_week():
    # 2026-10-21 is a Wednesday: Calendar.DAY_OF_WEEK = 4 (Sunday is 1).
    assert playcontrol_payload(PLAY_START, _rec())[16] == 4
    # 2026-10-25 is a Sunday.
    assert playcontrol_payload(PLAY_START, _rec(day=25))[16] == 1


def test_an_impossible_date_still_encodes():
    # A malformed record must not raise out of a media browser; wday becomes 0.
    out = playcontrol_payload(PLAY_START, _rec(month=2, day=31))
    assert out[16] == 0


def test_byte_twenty_one_is_the_records_event_type():
    assert playcontrol_payload(PLAY_START, _rec(event=0x12))[21] == 0x12


def test_command_param_and_channel_land_where_the_struct_says():
    out = playcontrol_payload(PLAY_SEEKTIME, _rec(), sd_channel=3, param=30)
    command, param = struct.unpack("<II", out[4:12])
    assert (command, param, out[20]) == (PLAY_SEEKTIME, 30, 3)


def test_the_first_word_is_always_zero():
    assert playcontrol_payload(PLAY_STOP, _rec(), sd_channel=26)[:4] == b"\x00" * 4


@pytest.mark.parametrize("channel", [0, 27, -1])
def test_a_channel_outside_the_apps_range_raises(channel):
    with pytest.raises(ValueError):
        playcontrol_payload(PLAY_START, _rec(), sd_channel=channel)


def test_the_reply_a_camera_sent_decodes():
    reply = decode_playcontrol_reply(
        bytes.fromhex("10000000000000003c000000ea070a150411182c")
    )
    assert reply is not None
    assert (reply.command, reply.result_channel, reply.clip_seconds) == (
        PLAY_START,
        0,
        60,
    )
    assert (reply.year, reply.month, reply.day) == (2026, 10, 21)
    assert (reply.hour, reply.minute, reply.second) == (17, 24, 44)


@pytest.mark.parametrize("data", [None, b"", b"\x10\x00\x00\x00"])
def test_a_short_reply_is_none_not_an_exception(data):
    assert decode_playcontrol_reply(data) is None
