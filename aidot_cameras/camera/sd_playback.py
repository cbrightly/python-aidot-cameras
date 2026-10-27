"""Ask a camera to play a recording off its own SD card.

The control surface only - the request bytes and the reply decode. Opening a
session the camera will play into is ``async_open_sd_playback`` on the camera
client; this half is pure so it can be tested with no camera in the way.

The request is the vendor app's own encoder,
``AVIOCTRLDEFs$SMsgAVIoctrlPlayRecord.parseContent(IIIIJII)``, as called from
``KVSWebRTCChannel.sdRecordPlay``: 0x18 bytes, little-endian.

    off 0   u32   0 (always 0 in the app's call)
    off 4   u32   command (PLAY_START, PLAY_STOP, ...)
    off 8   u32   param (the seek target for PLAY_SEEKTIME, otherwise 0)
    off 12  8B    STimeDay of the record, UTC: u16 year, month, day, wday,
                  hour, minute, second; wday is Calendar.DAY_OF_WEEK, Sunday = 1
    off 20  u8    playback channel, allocated 1..26 by the app
    off 21  u8    the record's event type, as LISTEVENT reported it
    off 22  2B    0

The reply (0x31b) is read the way the app's handler reads it:

    off 0   u32   the command, echoed
    off 4   u32   result channel (the app logs it beside the channel it asked
                  for and acts on neither)
    off 8   u32   the clip length in seconds (the app's GetSDTotalTime)
    off 12  8B    the record's STimeDay, echoed
"""

import datetime
import struct
from typing import Any, NamedTuple, Optional

RECORD_PLAYCONTROL_REQ_CMD = 0x31A
RECORD_PLAYCONTROL_RESP_CMD = 0x31B

# Values from the vendor enum. START and STOP are the ones exercised on
# hardware (A001064, 2026-10-27); the others are listed so a caller does not
# invent them, not because they are proven.
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

_MIN_SD_CHANNEL = 1
_MAX_SD_CHANNEL = 26


class PlaycontrolReply(NamedTuple):
    """What the camera said back to one playback command."""

    command: int
    result_channel: int
    clip_seconds: int
    year: int
    month: int
    day: int
    hour: int
    minute: int
    second: int


def _java_day_of_week(year: int, month: int, day: int) -> int:
    """``Calendar.DAY_OF_WEEK`` for a date: Sunday is 1, Saturday is 7."""
    try:
        return datetime.date(year, month, day).isoweekday() % 7 + 1
    except ValueError:
        return 0


def playcontrol_payload(
    command: int, record: Any, *, sd_channel: int = 1, param: int = 0
) -> bytes:
    """The 24 bytes for one playback command against one record.

    ``record`` is an ``SdEvent`` (anything with the date fields and ``event``).
    ``sd_channel`` must be 1..26, the range the app allocates; a value outside it
    is a caller bug and raises rather than being clamped, because a silently
    changed channel is a request the camera answers while sending nothing.
    """
    if not _MIN_SD_CHANNEL <= sd_channel <= _MAX_SD_CHANNEL:
        raise ValueError(
            f"sd_channel must be {_MIN_SD_CHANNEL}..{_MAX_SD_CHANNEL}, got {sd_channel}"
        )
    head = struct.pack("<III", 0, command, param)
    when = struct.pack(
        "<HBBBBBB",
        record.year,
        record.month,
        record.day,
        _java_day_of_week(record.year, record.month, record.day),
        record.hour,
        record.minute,
        record.second,
    )
    tail = struct.pack("<BB", sd_channel, int(getattr(record, "event", 0)) & 0xFF)
    return (
        head
        + when
        + tail
        + b"\x00" * (_PAYLOAD_LEN - len(head) - len(when) - len(tail))
    )


def decode_playcontrol_reply(data: Optional[bytes]) -> Optional[PlaycontrolReply]:
    """Read a 0x31b reply, or None if it cannot be read.

    None rather than an exception, for the same reason the listing decode does
    it: a reply with an unexpected shape is ordinary and must not surface as a
    traceback in a media browser.
    """
    if not data or len(data) < _REPLY_LEN:
        return None
    command, result_channel, clip_seconds = struct.unpack("<III", data[:12])
    year, month, day, _wday, hour, minute, second = struct.unpack(
        "<HBBBBBB", data[12:20]
    )
    return PlaycontrolReply(
        command, result_channel, clip_seconds, year, month, day, hour, minute, second
    )
