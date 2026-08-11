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
from typing import Any, Optional

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


class _CameraSdMixin:
    """The on-device recording query, mixed into CameraMixin."""

    async def _sd_ask(self, session: Any, cmd: int, payload: bytes,
                      response_cmd: int, timeout: float) -> Optional[Any]:
        """One request, with every failure flattened to None."""
        try:
            return await session.async_avio_request(
                cmd, payload, response_cmd=response_cmd, timeout=timeout)
        except Exception as exc:
            _LOGGER.debug("SD request %#x failed for %s: %s",
                          cmd, getattr(self, "device_id", "?"), exc)
            return None

    async def async_get_sd_recordings(
        self,
        *,
        session: Any = None,
        days: int = 7,
        channel: int = 0,
        timeout: float = _SD_LIST_TIMEOUT_S,
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

        Asks with event selector 0. The vendor's app sends 0x12 and on a
        card-bearing A000088 that returns an EMPTY page while 0 returns the
        records - measured 2026-08-11, same session, same window.

        One request each for the list and the occupancy map. The reply is paged
        and there is no known continuation request: the vendor's handler matches
        a reply to the sequence number of the single request it sent. So a reply
        whose end flag is not set comes back with ``complete=False`` rather than
        with a second request this code would have to invent.
        """
        session = session if session is not None else getattr(
            self, "_stream_session", None)
        if session is None:
            return None
        # A cheap early-out for a session already known to be torn down: there
        # is no point spending two timeouts on it. Only SDES sessions publish
        # `is_alive`, so this catches some dead sessions and not others - which
        # is fine, because it is NOT what keeps a dead session from being
        # reported as an empty card. `answered` does that, on every transport.
        if getattr(session, "is_alive", True) is False:
            return None

        end_ts = time.time()
        start_ts = end_ts - days * 86400

        records = []
        complete = True
        list_reply = await self._sd_ask(
            session, LISTEVENT_REQ_CMD,
            listevent_payload(start_ts, end_ts, channel=channel),
            LISTEVENT_RESP_CMD, timeout)
        if list_reply is not None:
            page = decode_list_event_response(
                bytes(list_reply.payload or b""), command=LISTEVENT_RESP_CMD)
            if page is None:
                # The decode is measured from a handful of live replies and a
                # reply with a different shape is entirely possible. It is an
                # empty list with a logged reason, never an exception - but not
                # a COMPLETE empty list: something came back and could not be
                # read, so there may well be recordings behind it.
                complete = False
                _LOGGER.debug(
                    "SD list reply for %s did not decode (%d bytes)",
                    getattr(self, "device_id", "?"),
                    len(bytes(list_reply.payload or b"")))
            else:
                records = page.events
                complete = page.end_flag == 1

        hours = None
        map_reply = await self._sd_ask(
            session, HASLISTEVENT_REQ_CMD,
            haslistevent_payload(start_ts, end_ts, channel=channel),
            HASLISTEVENT_RESP_CMD, timeout)
        if map_reply is not None:
            hours = decode_hour_map(
                bytes(map_reply.payload or b""), command=HASLISTEVENT_RESP_CMD)

        return SdRecordingList(
            records=records,
            hours=hours,
            # Either reply is enough to prove the camera is listening. Both
            # silent is the case that must never read as an empty card.
            answered=list_reply is not None or map_reply is not None,
            complete=complete,
            start_ts=start_ts,
            end_ts=end_ts,
        )
