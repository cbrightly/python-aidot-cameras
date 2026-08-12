"""Build the camera's recording-list requests, and decode its replies.

Both halves live here because they are one wire format, and because the
selector lesson below belongs next to the code that sends it: the requests used
to exist only in ``scripts/sd_event_probe.py``, which the package cannot import,
so every caller either duplicated them or could not ask at all.

The decoder was written from the vendor client's own decoder rather than from a
capture. What is CONFIRMED from ``KVSWebRTCChannel``'s 0x319 handler:

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
import time
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


class SdRecordingList(NamedTuple):
    """What one camera says its card holds over one window.

    ``records`` is the list and is always the primary answer. ``hours`` is the
    HASLISTEVENT occupancy map when the camera gave one - optional, never
    load-bearing. Measured 2026-08-11: the camera that returned four real
    records returned an ALL-ZERO map for the same window, so a consumer that
    trusted the map over the records would have shown an empty card.

    ``start_ts``/``end_ts`` are the window that was asked for. The map is one
    byte per hour counting from ``start_ts``, so without them a byte cannot be
    placed on a clock.
    """

    records: List[SdEvent]
    hours: Optional[bytes]
    #: True when the camera replied to at least one of the two requests.
    #:
    #: Without this, a camera that says NOTHING and a camera that says "my card
    #: is empty" both arrive as an empty list, and a browser has no way to tell
    #: "we asked and got silence" from "there is nothing there" - so it prints
    #: the second when the first is true. That collapse is the exact failure
    #: this subsystem exists to prevent, and it is worth a field rather than a
    #: convention: an empty ``records`` is only a statement about the card when
    #: ``answered`` is True.
    answered: bool
    #: Whether the list that came back is the whole list. False when the
    #: reply's end flag never arrived, and False when a reply arrived that could
    #: not be decoded - in both cases there may be more than is shown.
    #:
    #: Only meaningful when ``answered`` is True. Silence carries no end flag,
    #: so it says nothing about completeness either way.
    complete: bool
    start_ts: float
    end_ts: float


#: Reply command ids, so a caller that knows which question it asked can say so.
LISTEVENT_RESP_CMD = 0x319
HASLISTEVENT_RESP_CMD = 0x4B6

#: Request command ids, from AVIOCTRLDEFs in the vendor's own client.
HASLISTEVENT_REQ_CMD = 0x4B5
LISTEVENT_REQ_CMD = 0x318

#: The event selector the vendor's app sends, read off
#: KVSWebRTCChannel.getSDRecordList: `const/16 v1, 0x12`.
#:
#: It is NOT the default here, because on a camera with a card it returns
#: nothing. Measured 2026-08-11: the same session, the same window, 0x12
#: answered an EMPTY page while 0 answered with four real records, the first
#: stamped 2026-08-11T20:41:42Z. The earlier note claiming the opposite was
#: measured on three cameras with no card, where every selector answered empty
#: and the comparison was between two empty answers.
SD_EVENT_APP = 0x12

#: The selector that returns records. The default, so a caller that does not
#: know about the above cannot accidentally ask the question that gets silence.
SD_EVENT_ANY = 0


def stimeday(when: float) -> bytes:
    """The vendor's 8-byte STimeDay, from ``AVIOCTRLDEFs$STimeDay``.

    ``unsigned short year`` then six single bytes - month, day, wday, hour,
    minute, second. UTC, because the field is named ``startutctime``.
    """
    t = time.gmtime(when)
    return struct.pack(
        "<HBBBBBB",
        t.tm_year,
        t.tm_mon,
        t.tm_mday,
        (t.tm_wday + 1) % 7,     # tm_wday is Mon=0; the vendor's wday is Sun=0
        t.tm_hour,
        t.tm_min,
        t.tm_sec,
    )


def haslistevent_payload(start: float, end: float, channel: int = 0,
                         event: int = SD_EVENT_ANY) -> bytes:
    """22 bytes: channel, start, end, and a two-byte selector tail.

    The length is not a guess - ``parseConent`` allocates ``const/16 v1, 0x16``,
    which is 22, and copies the channel at offset 0 and the first STimeDay at
    offset 4.
    """
    return (struct.pack("<I", channel)
            + stimeday(start) + stimeday(end)
            + struct.pack("<H", event & 0xFFFF))


def listevent_payload(start: float, end: float, channel: int = 0,
                      event: int = SD_EVENT_ANY, status: int = 0) -> bytes:
    """24 bytes, not 22 - and the difference is the whole point.

    ``SMsgAVIoctrlListEventReq`` has three ``parseConent`` overloads. The one
    whose field list this was first written from is not the one the WebRTC path
    uses: ``KVSWebRTCChannel.getSDRecordList`` calls the epoch-long overload,
    which allocates ``const/16 v4, 0x18`` - 24 bytes - where the other
    allocates 22. Four bytes of channel, two eight-byte STimeDays, the event and
    status selectors, then two bytes of tail.
    """
    return (struct.pack("<I", channel)
            + stimeday(start) + stimeday(end)
            + bytes((event & 0xFF, status & 0xFF))
            + b"\x00\x00")


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
    # A HASLISTEVENT body is a per-hour occupancy map, not records. Reading it
    # as records is not a near miss: the real 168-byte map is exactly fourteen
    # 12-byte records, so the decode SUCCEEDS and invents fourteen recordings
    # dated 0000-00-00. Measured 2026-08-11, when a formatted-but-empty card's
    # 24-byte map read as "records: 2". A caller that knows which reply it has
    # gets nothing rather than fiction; a caller that passes no command still
    # gets the old best-effort behaviour, because that is what the existing
    # tests and probe runs read.
    if command == HASLISTEVENT_RESP_CMD:
        return None
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


def decode_hour_map(payload: Optional[bytes], *,
                    command: Optional[int] = None) -> Optional[bytes]:
    """The per-hour occupancy bytes from a HASLISTEVENT reply, or None.

    One byte per hour of the window that was requested, starting at the request
    start: 168 bytes for seven days, 24 for one. A non-zero byte means the card
    holds something recorded in that hour. It says nothing about how many
    recordings, so nothing here counts them.

    None means "this is not a map", which is a different answer from an empty
    map and is why the return is not simply ``b""``.
    """
    if command == LISTEVENT_RESP_CMD:
        return None
    if payload is None or len(payload) <= _HEADER_LEN:
        return None
    return bytes(payload[_HEADER_LEN:])
