"""Ask a camera what its own SD card holds.

Stateless by design: this asks a session it is handed and returns what came
back. It never opens a session, never caches and never decides that listing is
worth waking a camera for - those are policy, and policy belongs to the caller
that knows what a session costs. A cloud listing is one ~200 ms request; this
needs a WebRTC session, which is 15-21 s on DTLS and 25-70 s cold on SDES.

Nothing here raises at the caller. A camera with no card, a firmware that
implements a command without answering it, and a channel that died mid-request
are all ordinary, and each of them has to arrive in a media browser as an empty
list rather than as a traceback.
"""

import logging
import time
from datetime import datetime, UTC
from typing import Any, List, Optional

from .sd_events import (
    HASLISTEVENT_REQ_CMD,
    HASLISTEVENT_RESP_CMD,
    LISTEVENT_REQ_CMD,
    LISTEVENT_RESP_CMD,
    SdRecordingList,
    decode_hour_map,
    decode_list_event_response,
    haslistevent_payload,
    listevent_payload,
)

_LOGGER = logging.getLogger(__name__)

#: Long enough for a camera that answers slowly, short enough that two silent
#: requests cannot hold a session for more than a viewer would tolerate.
_SD_LIST_TIMEOUT_S = 8.0

#: How long a listing waits for its first page, and then for each next one.
#: The pages of one burst arrive together, but a burst can be slow to start:
#: a PTZ took 6.3 s to answer for a window three to six days back, and a wait
#: of 3 s between pages cut a 7-day reply off after two of its pages
#: (measured 2026-11-02). The occupancy map keeps the caller's timeout.
_SD_LIST_FIRST_PAGE_TIMEOUT_S = 15.0
_SD_MORE_PAGES_TIMEOUT_S = 10.0

#: 255 records a page, so 64 pages is over 16,000 recordings - far more than a
#: card holds in the 7 days a caller asks about.
_SD_MAX_PAGES = 64

#: The widest window one listing request asks for. An A000088 answers a
#: window of five days or more with page after page of records it never
#: filled in - all-zero dates and stray heap bytes, 7,800 slots for a card
#: holding one recording - and answers four days or less correctly (measured
#: 2026-11-02, windows of 1 to 6 days). Three days keeps a margin under that.
_SD_LIST_CHUNK_S = 3 * 86400.0

#: However the pages trickle in, a listing gives up collecting them after this
#: long, across all its windows, and reports what it has as incomplete. The
#: occupancy map is asked after it, with its own timeout.
#: Without it each page would restart the gap timer and a slow camera could
#: hold the session for minutes.
_SD_LIST_BUDGET_S = 60.0


class _ListPages:
    """The pages of one listing reply, each decoded once, as it arrives."""

    def __init__(self) -> None:
        self.pages: dict = {}
        self.undecodable = 0
        self._total: Optional[int] = None
        self._seen = 0

    def take(self, replies: List[Any]) -> None:
        """Decode whatever in ``replies`` has not been decoded yet."""
        for reply in replies[self._seen :]:
            self._add(reply)
        self._seen = len(replies)

    def done(self, replies: List[Any]) -> bool:
        """The ``done`` hook: stop collecting once the reply is whole."""
        self.take(replies)
        return self.covered()

    def _add(self, reply: Any) -> None:
        page = decode_list_event_response(
            bytes(reply.payload or b""), command=LISTEVENT_RESP_CMD
        )
        if page is None:
            # The decode is measured from a handful of live replies and a
            # reply with a different shape is entirely possible. It is an
            # empty list with a logged reason, never an exception - but not
            # a COMPLETE empty list: something came back and could not be
            # read, so there may well be recordings behind it.
            self.undecodable += 1
            _LOGGER.debug(
                "SD list reply did not decode (%d bytes)",
                len(bytes(reply.payload or b"")),
            )
            return
        if self._total is None:
            self._total = page.total
        elif page.total != self._total:
            # Every page of one reply declares the same page count. One that
            # does not is a late page of some earlier listing; mixing it in
            # would file another window's records under this one.
            _LOGGER.debug(
                "SD list page declares %d pages, this reply %d: not ours",
                page.total,
                self._total,
            )
            return
        # A page the camera sent twice is one page.
        self.pages.setdefault(page.page_index, page)

    def covered(self) -> bool:
        """The end flag has arrived and no page before it is missing.

        ``total`` is the page count the camera declared. A camera that declares
        none (0) is held to the pages up to its end-flag page instead.
        """
        ends = [i for i, p in self.pages.items() if p.end_flag == 1]
        if not ends:
            return False
        want = range(self._total) if self._total else range(max(ends) + 1)
        return set(self.pages) >= set(want)

    @property
    def records(self) -> list:
        return [e for i in sorted(self.pages) for e in self.pages[i].events]

    @property
    def complete(self) -> bool:
        if self.undecodable:
            return False
        return not self.pages or self.covered()


def _plausible(record: Any, lo: float, hi: float) -> bool:
    """Is this a record the camera filled in, for the window it was asked for?

    An A000088 pads a reply with slots it never wrote: all-zero dates, and
    stray heap bytes that decode to month 160. A date that cannot exist, or one
    outside the window (a second's slack either side for the camera's
    rounding), is not a recording.
    """
    try:
        when = datetime(
            record.year,
            record.month,
            record.day,
            record.hour,
            record.minute,
            record.second,
            tzinfo=UTC,
        ).timestamp()
    except (ValueError, OverflowError):
        return False
    return lo - 1 <= when <= hi + 1


def _session_is_live(session: Any) -> bool:
    """Is this session object one that could still carry a request?

    Only SDES sessions publish ``is_alive``, so a session already torn down is
    caught on that transport and not on DTLS. That asymmetry is fine here and
    must stay fine at every caller: this is a cheap early-out, never the thing
    that keeps a dead channel from being reported as an empty card. ``answered``
    does that, on every transport.
    """
    if session is None:
        return False
    return getattr(session, "is_alive", True) is not False


class _CameraSdMixin:
    """The on-device recording query, mixed into CameraMixin."""

    @property
    def has_live_session(self) -> bool:
        """Is a stream session up right now?

        Public because callers legitimately need to WAIT for a session without
        asking it anything. ``start_keepalive`` returns before the handshake it
        schedules has finished, so a caller that wants to ride the session it
        just asked for has no way to know when that session has arrived.
        Probing with a real request is not a substitute: once the session is up,
        the probe IS the request, and a caller that wanted to send one request
        has then sent two.

        Best-effort, in the direction that keeps callers honest: a torn-down
        session reads as live on a transport that does not say otherwise, so a
        listing taken on the strength of this can still fail and every caller
        must still handle that.
        """
        return _session_is_live(getattr(self, "_stream_session", None))

    async def _sd_ask(
        self, session: Any, cmd: int, payload: bytes, response_cmd: int, timeout: float
    ) -> Optional[Any]:
        """One request, with every failure flattened to None."""
        try:
            return await session.async_avio_request(
                cmd, payload, response_cmd=response_cmd, timeout=timeout
            )
        except Exception as exc:
            _LOGGER.debug(
                "SD request %#x failed for %s: %s",
                cmd,
                getattr(self, "device_id", "?"),
                exc,
            )
            return None

    async def _sd_ask_pages(
        self,
        session: Any,
        cmd: int,
        payload: bytes,
        response_cmd: int,
        timeout: float,
        budget: float = _SD_LIST_BUDGET_S,
    ) -> "tuple[_ListPages, bool]":
        """Every reply page to one request, and whether any reply came back.

        A session without ``async_avio_request_all`` (an older or a stand-in
        session) is asked the single-reply way, which is the first page only.

        Not safe to run two at once on one session: replies carry no id this
        layer can match on SDES, so concurrent listings could take each other's
        pages. Callers serialise listings per camera.
        """
        pages = _ListPages()
        ask_all = getattr(session, "async_avio_request_all", None)
        if ask_all is None:
            reply = await self._sd_ask(session, cmd, payload, response_cmd, timeout)
            replies = [] if reply is None else [reply]
        else:
            try:
                replies = list(
                    await ask_all(
                        cmd,
                        payload,
                        response_cmd=response_cmd,
                        timeout=timeout,
                        more_timeout=_SD_MORE_PAGES_TIMEOUT_S,
                        max_replies=_SD_MAX_PAGES,
                        budget=budget,
                        done=pages.done,
                    )
                )
            except Exception as exc:
                _LOGGER.debug(
                    "SD request %#x failed for %s: %s",
                    cmd,
                    getattr(self, "device_id", "?"),
                    exc,
                )
                replies = []
        pages.take(replies)
        return pages, bool(replies)

    async def _sd_list_chunks(
        self,
        session: Any,
        start_ts: float,
        end_ts: float,
        channel: int,
        first_page_timeout: float,
    ) -> "tuple[list, bool, bool]":
        """List ``start_ts``..``end_ts`` in chunks no wider than the camera handles.

        Returns (records oldest first, complete, answered). Chunks are asked
        newest first and the listing stops at the first chunk that did not come
        back whole: a camera that goes quiet part way still gives the
        recordings a user most wants, a dead channel costs one timeout rather
        than one per chunk, and a page still on its way for a chunk given up on
        cannot be read as the next chunk's answer - replies carry nothing this
        layer can match a request on.
        """
        chunks = []
        hi = end_ts
        while hi > start_ts:
            lo = max(start_ts, hi - _SD_LIST_CHUNK_S)
            chunks.append((lo, hi))
            hi = lo
        if not chunks:
            # An empty or inverted window is still asked, once: answering
            # "complete" for a listing never sent would read as an empty card.
            chunks.append((start_ts, end_ts))
        by_chunk = []
        complete = True
        answered = False
        give_up = time.monotonic() + _SD_LIST_BUDGET_S
        for lo, hi in chunks:
            left = give_up - time.monotonic()
            if left <= 0:
                complete = False
                break
            pages, chunk_answered = await self._sd_ask_pages(
                session,
                LISTEVENT_REQ_CMD,
                listevent_payload(lo, hi, channel=channel),
                LISTEVENT_RESP_CMD,
                min(first_page_timeout, left),
                budget=left,
            )
            if not chunk_answered:
                complete = False
                break
            answered = True
            records = pages.records
            kept = [r for r in records if _plausible(r, lo, hi)]
            by_chunk.append(kept)
            if len(kept) != len(records):
                complete = False
                _LOGGER.debug(
                    "SD list for %s: %d of %d records unfilled or outside the window",
                    getattr(self, "device_id", "?"),
                    len(records) - len(kept),
                    len(records),
                )
            if not pages.complete:
                complete = False
                break
        out: list = []
        # Newest chunk was asked first; hand them back oldest first. Two
        # neighbouring chunks share an edge second, so a record on it can come
        # back in both - and only there is a repeat not a record of its own.
        previous: set = set()
        for records in reversed(by_chunk):
            out.extend(r for r in records if r not in previous)
            previous = set(records)
        return out, complete, answered

    async def async_get_sd_recordings(
        self,
        *,
        session: Any = None,
        days: int = 7,
        channel: int = 0,
        timeout: Optional[float] = None,
    ) -> Optional[SdRecordingList]:
        """What the card holds over the last ``days``, or None if we could not ask.

        **Three outcomes, not two, and a caller has to be able to tell them
        apart.** A browser that collapses any two of them shows a user the same
        empty folder for opposite reasons, which is the failure this whole
        subsystem was built to stop:

          * ``None`` - there was no session to ask through. Nothing was sent.
          * ``answered=False`` - the requests went out and the camera said
            nothing at all. This is what a model that does not implement the
            commands looks like, and also what a channel that has just died
            looks like; it is NOT a statement about the card.
          * ``answered=True`` - the camera replied. Only now does an empty
            ``records`` mean the card holds nothing in this window.

        Asks with event selector 0. The vendor's app sends 0x12 in its event
        mode (1 otherwise; see sd_events.SD_EVENT_APP), and on a card-bearing
        A000088 that returns an EMPTY page while 0 returns the
        records - measured 2026-08-11, same session, same window.

        The list is asked in windows of at most three days, newest first: an
        A000088 answers a window of five days or more with records it never
        filled in. One request for the occupancy map. Each list reply is paged,
        255 records a page, oldest first, and the camera sends every page
        unprompted: there is no continuation request and none is needed. All of
        them are collected (measured 2026-11-01: a PTZ's 7-day window was four
        pages, 824 records, and reading only the first stopped six days short
        of today). A record whose date cannot exist or falls outside the window
        it was asked for is one the camera did not fill in, and is dropped. The
        listing stops at the first window that did not come back whole, and
        ``complete=False`` then, and when a record was dropped.
        """
        session = (
            session if session is not None else getattr(self, "_stream_session", None)
        )
        # No session is "could not ask", and so is a session already known to
        # be torn down: there is no point spending two timeouts on one. Shared
        # with `has_live_session` so a caller waiting for a session to exist and
        # this call deciding it has one cannot come to different answers.
        if not _session_is_live(session):
            return None

        end_ts = time.time()
        start_ts = end_ts - days * 86400

        # A caller's own timeout is used as given. Without one, a list page
        # gets the longer wait a slow card scan needs and the map the usual one.
        map_timeout = _SD_LIST_TIMEOUT_S if timeout is None else timeout
        records, complete, list_answered = await self._sd_list_chunks(
            session,
            start_ts,
            end_ts,
            channel,
            _SD_LIST_FIRST_PAGE_TIMEOUT_S if timeout is None else timeout,
        )

        hours = None
        map_reply = await self._sd_ask(
            session,
            HASLISTEVENT_REQ_CMD,
            haslistevent_payload(start_ts, end_ts, channel=channel),
            HASLISTEVENT_RESP_CMD,
            map_timeout,
        )
        if map_reply is not None:
            hours = decode_hour_map(
                bytes(map_reply.payload or b""), command=HASLISTEVENT_RESP_CMD
            )

        return SdRecordingList(
            records=records,
            hours=hours,
            # Either reply is enough to prove the camera is listening. Both
            # silent is the case that must never read as an empty card.
            answered=list_answered or map_reply is not None,
            complete=complete,
            start_ts=start_ts,
            end_ts=end_ts,
        )
