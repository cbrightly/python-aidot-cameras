"""Pause, resume and seek on a playback session, as the app sends them.

KVSWebRTCChannel (jadx_2222 d0.java) T2 / V2 / W2: each a RECORD_PLAYCONTROL
on the playback channel and record the play started with. Pause is command 0,
resume 17; seek is 6 with the time field set to the record's start plus the
offset and ``param`` a counter, one more per seek, 1..255 (k5.java M0).
"""

import asyncio
import struct

from aidot_cameras.camera.sd_events import SdEvent
from aidot_cameras.camera.sd_playback import (
    PLAY_PAUSE,
    PLAY_RESUME,
    PLAY_SEEKTIME,
    PLAY_START,
    SdPlaybackStart,
    playcontrol_payload,
)
from aidot_cameras.camera.sdes import SdesSession

REC = SdEvent(2026, 10, 21, 17, 33, 51, 0, 0, 0)


def _when(payload):
    return struct.unpack_from("<HBBBBBB", payload, 12)


def test_the_offset_moves_the_time_field_across_minute_and_day():
    assert _when(playcontrol_payload(PLAY_SEEKTIME, REC, offset_s=40)) == (
        2026,
        10,
        21,
        4,
        17,
        34,
        31,
    )
    late = SdEvent(2026, 10, 31, 23, 59, 50, 0, 0, 0)
    # Into November 1st, a Sunday (Calendar.DAY_OF_WEEK 1).
    assert _when(playcontrol_payload(PLAY_SEEKTIME, late, offset_s=15)) == (
        2026,
        11,
        1,
        1,
        0,
        0,
        5,
    )
    assert playcontrol_payload(PLAY_START, REC, offset_s=0) == playcontrol_payload(
        PLAY_START, REC
    )


class _Session(SdesSession):
    """Answers each control at once, as the A001064 does (30-50 ms)."""

    def __init__(self, start, echo=None):
        super().__init__(
            proc=None, sdp_path="", outgoing_q=None, mqtt_fut=None, playback=start
        )
        self.sent = []
        self.echo = echo

    async def async_avio_request(self, cmd, payload, *, response_cmd, timeout, match):
        self.sent.append((cmd, payload, response_cmd))
        command = struct.unpack_from("<I", payload, 4)[0]
        reply = struct.pack("<III", self.echo or command, 0, 60) + payload[12:20]
        resp = type("R", (), {"payload": reply})()
        return resp if match(resp) else None


def _start(channel=3):
    return SdPlaybackStart(request=b"", record=REC, sd_channel=channel)


def test_pause_resume_and_seek_name_the_session_record_and_channel():
    s = _Session(_start())

    async def run():
        return (
            await s.async_sd_pause(),
            await s.async_sd_resume(),
            await s.async_sd_seek(40),
            await s.async_sd_seek(10),
        )

    pause, resume, seek1, seek2 = asyncio.run(run())
    assert [r.command for r in (pause, resume, seek1, seek2)] == [
        PLAY_PAUSE,
        PLAY_RESUME,
        PLAY_SEEKTIME,
        PLAY_SEEKTIME,
    ]
    payloads = [p for _, p, _ in s.sent]
    assert payloads[0] == playcontrol_payload(PLAY_PAUSE, REC, sd_channel=3)
    assert payloads[1] == playcontrol_payload(PLAY_RESUME, REC, sd_channel=3)
    assert payloads[2] == playcontrol_payload(
        PLAY_SEEKTIME, REC, sd_channel=3, param=1, offset_s=40
    )
    assert struct.unpack_from("<I", payloads[3], 8)[0] == 2
    assert {(c, r) for c, _, r in s.sent} == {(0x31A, 0x31B)}


def test_the_seek_counter_wraps_at_255():
    s = _Session(_start())
    s._sd_seek_count = 254

    async def run():
        await s.async_sd_seek(1)
        await s.async_sd_seek(1)

    asyncio.run(run())
    assert [struct.unpack_from("<I", p, 8)[0] for _, p, _ in s.sent] == [255, 1]


def test_a_reply_to_another_command_is_not_taken_for_this_one():
    s = _Session(_start(), echo=PLAY_START)
    assert asyncio.run(s.async_sd_pause()) is None


def test_a_live_session_sends_nothing():
    s = _Session(None)
    assert asyncio.run(s.async_sd_pause()) is None
    assert s.sent == []


def test_a_negative_seek_is_refused():
    import pytest

    with pytest.raises(ValueError):
        playcontrol_payload(PLAY_SEEKTIME, REC, offset_s=-5)


def test_replies_go_to_the_command_they_echo_whatever_the_order():
    # PLAY_START's reply comes ~6 s late; a pause answered in 40 ms meanwhile
    # must reach the pause, and the late start reply the start (2026-10-27).
    from aidot_cameras.camera.protocol import AvioResponseRouter, avio_header
    from aidot_cameras.camera.sd_playback import answers

    router = AvioResponseRouter()
    start = router.expect(0x31B, answers(PLAY_START))
    pause = router.expect(0x31B, answers(PLAY_PAUSE))

    def reply(command):
        body = struct.pack("<III", command, 0, 0) + bytes(8)
        return avio_header(1, 0x31B, 0, len(body), 0) + body

    assert router.dispatch(reply(PLAY_PAUSE))
    assert not router.dispatch(reply(PLAY_SEEKTIME))  # nobody asked
    assert router.dispatch(reply(PLAY_START))

    async def run():
        return await start.result(0.1), await pause.result(0.1)

    s, p = asyncio.run(run())
    assert struct.unpack_from("<I", s.payload)[0] == PLAY_START
    assert struct.unpack_from("<I", p.payload)[0] == PLAY_PAUSE


def test_a_seek_on_a_live_session_leaves_the_counter_alone():
    s = _Session(None)
    assert asyncio.run(s.async_sd_seek(5)) is None
    assert s._sd_seek_count == 0
