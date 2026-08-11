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

**The header is now measured, not inferred (2026-08-11, run 31497241870).** The
first version of this module used the published TUTK layout - two STimeDays then
the counters, 24 bytes - and refused to decode the real replies because they
were shorter than that. Refusing was correct, and it is how the true layout came
back readable instead of being forced into the wrong one.

Two live replies, and a 12-byte header fits both exactly:

    HASLISTEVENT_RESP  00000000 01000000 0001a800  + 168 bytes
    LISTEVENT_RESP     00000000 01000000 00010000  + 0 bytes

    channel  uint32 LE   total  uint32 LE   index  end_flag  count  reserved

`count` equals the body length in both - 168 and 0. And 168 is exactly the
7-day range that was requested, one byte per hour, which is what HASLISTEVENT
means: a per-hour occupancy map rather than a list. LISTEVENT carries 12-byte
records in the same body.
"""
import struct
from typing import List, NamedTuple, Optional

#: One record. The size is confirmed from the vendor's own arraycopy.
EVENT_RECORD_LEN = 12

#: Measured from two live replies, not taken from the published struct - the
#: published one is 24 bytes and neither reply is that long.
_HEADER_LEN = 12


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


#: Reply command ids, so a caller that knows which question it asked can say so.
LISTEVENT_RESP_CMD = 0x319
HASLISTEVENT_RESP_CMD = 0x4B6


def _consistent(count: int, body_len: int, command: Optional[int]) -> bool:
    """Does the declared entry count match the body, for THIS reply's entry size?

    Records are 12 bytes each; occupancy-map hours are one byte each. When the
    command is known the right rule is used. When it is not, either reading is
    accepted - reporting False for a well-formed page just because the decoder
    was not told what it was reading would be the check lying about the data.
    """
    if command == HASLISTEVENT_RESP_CMD:
        return count == body_len
    if command == LISTEVENT_RESP_CMD:
        return count * EVENT_RECORD_LEN == body_len
    return count == body_len or count * EVENT_RECORD_LEN == body_len


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


def decode_list_event_response(payload: bytes, *, command: Optional[int] = None) -> Optional[SdEventPage]:
    """Decode a 0x319 payload, or None if it cannot be one.

    None rather than an exception or a half-filled result: this runs against a
    reply nobody has seen yet, and "this does not fit" is the answer that keeps
    the next person honest.
    """
    if payload is None or len(payload) < _HEADER_LEN:
        return None
    channel, total = struct.unpack_from("<II", payload, 0)
    index, end_flag, count, _reserved = struct.unpack_from("<BBBB", payload, 8)
    body = payload[_HEADER_LEN:]
    usable = len(body) // EVENT_RECORD_LEN
    # `count` is a BYTE count on HASLISTEVENT (one byte per hour) and a RECORD
    # count on LISTEVENT. Only the record reading is meaningful here, so the
    # number of records decoded is bounded by what the body can actually hold
    # rather than by what the header claims.
    take = usable
    events = [decode_event_record(body, i * EVENT_RECORD_LEN)
              for i in range(take)]
    return SdEventPage(
        events=events,
        channel=channel,
        total=total,
        page_index=index,
        end_flag=end_flag,
        record_count=count,
        # `count` counts ENTRIES in the body, and an entry is not always a
        # byte. Measured 2026-08-11 against the first live replies carrying real
        # data: a LISTEVENT_RESP with four records is 12 header + 48 body and
        # declares count=4, while a HASLISTEVENT_RESP occupancy map declares
        # count=168 for 168 bytes because there an entry IS one hour-byte.
        #
        # So the entry size depends on WHICH reply this is, and the payload
        # alone cannot always say - a 24-byte body with count=24 is a valid
        # 24-hour map, and a 24-byte body with count=2 is a valid two-record
        # page. Pass `command` when it is known. Without it, accept either
        # reading rather than calling a good page inconsistent, and say in
        # `consistent` only what can honestly be said.
        consistent=_consistent(count, len(body), command),
        trailing=len(body) - usable * EVENT_RECORD_LEN,
    )
