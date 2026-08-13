"""Ask a camera to play a recording off its own SD card.

The control surface only - the request bytes and the reply decode. Opening a
session and delivering frames is `_CameraSdPlaybackMixin`, added separately, so
this half can be tested with no camera in the way.

The request shape comes from the vendor app rather than from guesswork - but
from a specific overload of it. The app calls
`AVIOCTRLDEFs$SMsgAVIoctrlPlayRecord.parseContent(IIIIJII)`, not the
`(III[B)` overload this module first assumed, and the two disagree about the
last four bytes. `parseContent(IIIIJII)` allocates 0x18 bytes and fills:
command at offset 4 and an 8-byte STimeDay at offset 12, both as first
assumed, but offset 0 and offset 8 are ALWAYS 0 (not a caller-chosen channel),
and the trailing two bytes at offsets 20-21 carry the real channel and a
second caller value. The channel comes from `KVSWebRTCChannel.sdRecordPlay`,
which allocates it as `sdChannelId += 1` wrapping 26 -> 1 before every play -
so a real request never carries channel 0.
"""
import struct
from typing import Any, NamedTuple, Optional

RECORD_PLAYCONTROL_REQ_CMD = 0x31A
RECORD_PLAYCONTROL_RESP_CMD = 0x31B

# Only START and STOP have been sent to a camera and answered (measured
# 2026-08-13, A000088). PAUSE, STEPFORWARD, STEPBACKWARD, FORWARD, BACKWARD,
# SEEKTIME, END and RESUME are real values from the same vendor enum, kept
# here for completeness, but nobody should assume any of them are proven
# against hardware.
PLAY_PAUSE = 0x00
PLAY_STOP = 0x01
PLAY_STEPFORWARD = 0x02
PLAY_STEPBACKWARD = 0x03
PLAY_FORWARD = 0x04
PLAY_BACKWARD = 0x05
PLAY_SEEKTIME = 0x06
PLAY_END = 0x07
PLAY_START = 0x10
PLAY_RESUME = 0x11

_PAYLOAD_LEN = 0x18
# Unlike _PAYLOAD_LEN, this is not read off the vendor encoder - it rests on
# one live capture, on an A000088, 2026-08-13. Treat it as less certain than
# the request shape above.
_REPLY_LEN = 20

_MIN_SD_CHANNEL = 1
_MAX_SD_CHANNEL = 26


class PlaycontrolReply(NamedTuple):
    """What the camera said back.

    ``field1`` and ``field2`` are deliberately not named. Measured 2026-08-13:
    ``field1`` read 0 with the camera free and 1 while another viewer held a
    live session, so it is NOT a success code; ``field2`` read 60 on every run,
    but every run used the same record. Naming either would publish a guess.
    """

    command: int
    field1: int
    field2: int
    year: int
    month: int
    day: int
    hour: int
    minute: int
    second: int


def playcontrol_payload(command: int, record: Any, *, sd_channel: int = 1,
                        param: int = 0) -> bytes:
    """The 24 bytes for one playback command against one record.

    ``sd_channel`` must be 1..26, the range `sdRecordPlay` actually allocates.
    A value outside it is a caller bug, not a protocol surprise, and is
    rejected rather than clamped: a silently changed channel would send a
    request the camera accepts and answers while returning no media - the
    exact symptom this module exists to avoid reproducing.
    """
    if not _MIN_SD_CHANNEL <= sd_channel <= _MAX_SD_CHANNEL:
        raise ValueError(
            f"sd_channel must be {_MIN_SD_CHANNEL}..{_MAX_SD_CHANNEL}, "
            f"got {sd_channel}")
    # Offsets 0 and 8 are always 0 in the vendor app's own call - neither is a
    # caller-chosen channel or param, whatever the (III[B) overload implies.
    head = struct.pack("<III", 0, command, 0)
    when = struct.pack(
        "<HBBBBBB",
        record.year, record.month, record.day,
        0,                       # wday - the camera fills its own
        record.hour, record.minute, record.second,
    )
    tail = struct.pack("<BB", sd_channel, param)
    return (head + when + tail
            + b"\x00" * (_PAYLOAD_LEN - len(head) - len(when) - len(tail)))


def decode_playcontrol_reply(data: Optional[bytes]) -> Optional[PlaycontrolReply]:
    """Read a 0x31b reply, or None if it cannot be read.

    None rather than an exception, for the same reason the listing decode does
    it: a reply with an unexpected shape is entirely ordinary and must not
    surface as a traceback in a media browser.
    """
    if not data or len(data) < _REPLY_LEN:
        return None
    command, field1, field2 = struct.unpack("<III", data[:12])
    year, month, day, _wday, hour, minute, second = struct.unpack(
        "<HBBBBBB", data[12:20])
    return PlaycontrolReply(command, field1, field2,
                            year, month, day, hour, minute, second)
