"""Decode the camera's recording-list reply (``LISTEVENT_RESP``, 0x319).

Written from the vendor client's own decoder rather than from a capture, so it
exists before any camera here has answered. What is CONFIRMED from
``KVSWebRTCChannel``'s 0x319 handler:

  * the reply is matched by the SEQUENCE NUMBER of the request, not by the
    command alone - the handler's first act is ``if getListSeq != seq: return``;
  * a four-byte little-endian value is assembled and named ``listEventTotalTime``
    (its name says time, not count, and this module does not pretend otherwise);
  * a single byte is read as ``listEventEndFlag`` - the reply is PAGED, and a
    caller that stops at the first packet gets a truncated list;
  * a single byte is read as ``listEventCount`` - how many records this packet
    carries;
  * records are **twelve bytes each**, copied out with an explicit
    ``arraycopy(..., 12)``, and each begins with a two-byte year read via
    ``byteToShort`` - an ``STimeDay``.

What is NOT confirmed is where those header fields sit. The decompiled handler
addresses them through registers reassigned dozens of times across a
2000-line switch, and resolving which assignment is live at that point is
guesswork. So the header offsets below come from the published TUTK
``SMsgAVIoctrlListEventResp`` layout, which the confirmed facts fit exactly:
two STimeDays, then channel/total/index/endflag/count, then the records.

That distinction is load-bearing. `decode_list_event_response` reports which
reading it used and whether the payload's own length agrees with the record
count, so the first real reply either confirms the layout or shows precisely
where it diverges - rather than being silently forced into it.
"""
import struct
from typing import List, NamedTuple, Optional

#: One record. The size is confirmed from the vendor's own arraycopy.
EVENT_RECORD_LEN = 12

#: Offset of the first record, from the published TUTK response layout:
#: two 8-byte STimeDays, then channel, total, index, endflag, count, and three
#: reserved bytes.
_HEADER_LEN = 24


class SdEvent(NamedTuple):
    """One recording the camera says it holds."""

    #: UTC, from the record's STimeDay.
    year: int
    month: int
    day: int
    hour: int
    minute: int
    second: int
    channel: int
    event: int
    status: int

    def isoformat(self) -> str:
        return (f"{self.year:04d}-{self.month:02d}-{self.day:02d}"
                f"T{self.hour:02d}:{self.minute:02d}:{self.second:02d}Z")


class SdEventPage(NamedTuple):
    """One packet of the paged reply.

    ``end_flag`` is why this is a page and not a list: the camera answers a
    range in several packets and a caller that reads only the first silently
    truncates the recording list. Nothing here hides that.
    """

    events: List[SdEvent]
    channel: int
    total: int
    #: Named page_index / record_count, not index / count: a NamedTuple field
    #: called `index` or `count` shadows tuple.index() and tuple.count(), so
    #: `page.count` would stop being callable and read as a number instead.
    page_index: int
    end_flag: int
    record_count: int
    #: True when the payload length matches the count the header declared.
    consistent: bool
    #: Bytes left over after the declared records - non-zero means the layout
    #: read here is wrong, and says so rather than dropping them.
    trailing: int


def _stimeday(buf: bytes, off: int) -> tuple:
    year, month, day, _wday, hour, minute, second = struct.unpack_from(
        "<HBBBBBB", buf, off)
    return year, month, day, hour, minute, second


def decode_event_record(buf: bytes, off: int = 0) -> SdEvent:
    """One 12-byte record: an STimeDay then channel, event, status, reserved."""
    year, month, day, hour, minute, second = _stimeday(buf, off)
    channel, event, status = struct.unpack_from("<BBB", buf, off + 8)
    return SdEvent(year, month, day, hour, minute, second,
                   channel, event, status)


def decode_list_event_response(payload: bytes) -> Optional[SdEventPage]:
    """Decode a 0x319 payload, or None if it cannot be one.

    None rather than an exception or a half-filled result: this runs against a
    reply nobody has seen yet, and "this does not fit" is the answer that keeps
    the next person honest.
    """
    if payload is None or len(payload) < _HEADER_LEN:
        return None
    channel, total, index, end_flag, count = struct.unpack_from(
        "<BBBBB", payload, 16)
    body = payload[_HEADER_LEN:]
    usable = len(body) // EVENT_RECORD_LEN
    take = min(count, usable)
    events = [decode_event_record(body, i * EVENT_RECORD_LEN)
              for i in range(take)]
    return SdEventPage(
        events=events,
        channel=channel,
        total=total,
        page_index=index,
        end_flag=end_flag,
        record_count=count,
        consistent=(count == usable),
        trailing=len(body) - usable * EVENT_RECORD_LEN,
    )
