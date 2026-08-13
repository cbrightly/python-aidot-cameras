"""Ask a camera to play a recording off its own SD card.

The control surface only - the request bytes and the reply decode. Opening a
session and delivering frames is `_CameraSdPlaybackMixin`, added separately, so
this half can be tested with no camera in the way.

The request shape comes from the vendor app rather than from guesswork:
`AVIOCTRLDEFs$SMsgAVIoctrlPlayRecord.parseContent(III[B)` allocates 0x18 bytes
and fills channel, command, Param, then an 8-byte STimeDay at offset 12.
"""
import struct
from typing import Any, NamedTuple, Optional

RECORD_PLAYCONTROL_REQ_CMD = 0x31A
RECORD_PLAYCONTROL_RESP_CMD = 0x31B

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
_REPLY_LEN = 20


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


def playcontrol_payload(command: int, record: Any, *, channel: int = 0,
                        param: int = 0) -> bytes:
    """The 24 bytes for one playback command against one record."""
    head = struct.pack("<III", channel, command, param)
    when = struct.pack(
        "<HBBBBBB",
        record.year, record.month, record.day,
        0,                       # wday - the camera fills its own
        record.hour, record.minute, record.second,
    )
    return head + when + b"\x00" * (_PAYLOAD_LEN - len(head) - len(when))


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
