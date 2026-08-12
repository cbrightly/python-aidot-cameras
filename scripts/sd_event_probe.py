"""Ask a camera what recordings it holds, and write down exactly what it says.

SD-card retrieval is the one real feature gap left (ROAD-TO-1.0 item 6). Reading
the decompiled client answered everything about it except one thing:

  * the command ids are known - LISTEVENT 0x318 with response 0x319,
    HASLISTEVENT 0x4b5 with response 0x4b6;
  * the request layout is known, from `AVIOCTRLDEFs`;
  * it rides the ordinary AVIO control channel, not the out-of-scope TUTK
    stack - `getSDRecordList` is one `sendCtrl(0x318, ...)`;
  * and the vendor app demonstrably does it on this hardware.

What is NOT known is the RESPONSE layout. There is no `...ListEventResp` class
beside the request ones, so it has to be read off the wire. This reads it.

Deliberately read-only: HASLISTEVENT and LISTEVENT ask what exists. Nothing here
sends DELLISTEVENT or RECORD_PLAYCONTROL, so the probe cannot delete a recording
or start playback on a camera in someone's house.

A null answer is a result too. This firmware may implement a request without a
response, and silence points at the channel rather than at the layout - which is
why the request is sent twice with different time ranges before concluding
anything.

**Why the A000088s answered nothing: THE SLOT WAS EMPTY. Settled 2026-08-11.**

A card was inserted into a Bedroom M3 Pro and every command that had been silent
for months answered on the first try, in the same session, both modes:

    haslistevent          answered  cmd 1206 (0x4B6)  hex 000000000100000000010000
    listevent             answered  cmd  793 (0x319)  same
    listevent_event0      answered        listevent_ch1     answered
    haslistevent_ch1      answered        listevent_status1 answered
    haslistevent_1day     answered
    session mode -> SD    answered  cmd 5377
    haslistevent (SD)     answered        listevent (SD)    answered
    session mode -> LIVING answered

12 bytes: channel 0, total 1, index 0, end_flag 1, count 0 - a well-formed EMPTY
list, which is exactly right for a card with no recordings on it yet.

The firmware was never refusing these commands. It answers them the moment there
is a card to answer about, and `SDcardStatus` flipped 1 -> 0 as the card
registered, so the property that had been read as "has a card" actually means
the opposite.

Everything built on that silence was explaining a phenomenon that did not exist:
the `-SD` channel-name reading, the second-session theory, the session-mode
experiment, and the inference that SD retrieval might need the out-of-scope TUTK
stack. None of it was needed. The one lasting lesson is cheaper than any of
them: **check that the hardware has the thing you are asking about before
theorising about the protocol.** Three A000088s reported `SDcardExistFlag:
false` and `SDcardBaseInfo: [false,0,0,0,0]` the entire time, and nothing read
those fields.

Two things from the detour are worth keeping. `set_session_mode` works and is
real - the camera accepts IDLE/LIVING/SD on 5376 and echoes the mode back. And
the response layout below is now confirmed against an A000088 as well as the
SDES models.

**The record decode is now validated against real data (2026-08-11).** Once the
card had captured motion, a reply carried FOUR real records - 12 bytes of header
and 48 of body, count 4, the first stamped 2026-08-11T20:41:42Z. So
`EVENT_RECORD_LEN` and the 12-byte record layout are measured rather than
inferred, and the earlier note here saying no reply had ever carried a record is
superseded.

The same run turned up the two things still worth probing, both recorded below
rather than here: the event selector inverted - `listevent` with the app's 0x12
answered an EMPTY page while `listevent_event0` answered with the four records -
and the occupancy map came back ALL-ZERO for the window whose records were real,
which is what the `haslistevent_event0` variants exist to test.
"""
import struct
import time
from typing import Optional

# One definition of the wire format, and it is the package's - so this probe
# exercises the code that ships rather than a copy that can drift from it. These
# used to be defined here, which meant the library could not ask a camera
# anything at all: a package cannot import from scripts/.
from aidot_cameras.camera.sd_events import (
    HASLISTEVENT_REQ_CMD as HASLISTEVENT_REQ,
    HASLISTEVENT_RESP_CMD as HASLISTEVENT_RESP,
    LISTEVENT_REQ_CMD as LISTEVENT_REQ,
    LISTEVENT_RESP_CMD as LISTEVENT_RESP,
    SD_EVENT_APP,
    haslistevent_payload,
    listevent_payload,
)

# THE SELECTOR, and the trap in reading it.
#
# `SD_EVENT_APP` is 0x12, the selector the vendor's app sends, read off
# KVSWebRTCChannel's getSDRecordList: `const/16 v1, 0x12`, with status 0.
#
# **On an A000088 with a card, 0x12 returns NOTHING and event=0 returns the
# records.** Measured 2026-08-11: the same session, same range, same everything
# else - `listevent` (0x12) answered with an empty page while
# `listevent_event0` answered with four real records, the first stamped
# 2026-08-11T20:41:42Z.
#
# An earlier note here said the opposite - that event=0 drew silence from three
# cameras and the request "has to be the app's". Those three cameras had no SD
# card, so every variant answered empty and the comparison was between two
# empty answers. Do not read a selector result from a camera with nothing to
# list.
#
# Every request below states its selector explicitly rather than inheriting the
# package default, which is 0. That is what keeps each pair of variants a
# one-variable comparison.

#: The 12-byte reply header measured from live replies - see
#: aidot_cameras/camera/sd_events.py, which reads it the same way.
_MAP_HEADER_LEN = 12


def _describe(reply) -> dict:
    """Everything about the reply, without pretending to parse it yet.

    The point of this run is to LEARN the layout, so it records the raw bytes
    and a couple of cheap readings rather than asserting a structure. Guessing a
    parse here and reporting its output would be inventing the answer the probe
    exists to find.
    """
    if reply is None:
        return {"answered": False}
    payload = bytes(reply.payload or b"")
    out = {
        "answered": True,
        "command": reply.command,
        "len": len(payload),
        "hex": payload.hex(),
    }
    if len(payload) >= 4:
        out["first_u32_le"] = struct.unpack_from("<I", payload, 0)[0]
    # Try the layout read out of the vendor client, and report the FIT rather
    # than the result. `consistent` and `trailing` are what say whether the
    # header offsets - the part that could not be confirmed from the decompiled
    # handler - are right for this firmware. The raw hex is kept regardless, so
    # a wrong guess here cannot destroy the evidence.
    #
    # ONLY for LISTEVENT. A HASLISTEVENT body is a per-hour occupancy map - one
    # byte per hour - not records, and running the record decode over it invents
    # data: a formatted-but-empty card's 24-byte map decoded as "records: 2"
    # with a first timestamp of 0000-00-00T00:00:00Z, which is two records that
    # do not exist and a date that never happened. Measured 2026-08-11, the
    # first time a camera returned a real map. Exactly the failure a parser is
    # supposed to have in a probe rather than in a user's browser.
    page = None
    if reply.command == LISTEVENT_RESP:
        try:
            from aidot_cameras.camera.sd_events import decode_list_event_response
            page = decode_list_event_response(
                payload, command=reply.command)
        except Exception:
            page = None
    elif reply.command == HASLISTEVENT_RESP and len(payload) > _MAP_HEADER_LEN:
        hours = payload[_MAP_HEADER_LEN:]
        out["occupancy"] = {
            "hours_reported": len(hours),
            "hours_with_footage": sum(1 for h in hours if h),
            "first_hour_with_footage": next(
                (i for i, h in enumerate(hours) if h), None),
        }
    if page is not None:
        out["decoded"] = {
            "records": len(page.events),
            "record_count_claimed": page.record_count,
            "total": page.total,
            "end_flag": page.end_flag,
            "consistent": page.consistent,
            "trailing": page.trailing,
            "first": page.events[0].isoformat() if page.events else None,
        }
    return out


#: `E_CMD_AVIO_CTRL_SESSION_MODE_REQ` - the command that puts a session into a
#: mode, and the one this package already sends as LIVING on every open.
SESSION_MODE_REQ = 5376
SESSION_MODE_RESP = 5377

#: `KVSWebRTCChannel$AVIO_CTRL_SESSION_MODE`, read out of the vendor client as
#: an enum rather than guessed: IDLE, LIVING, SD - ordinals 0, 1, 2, and
#: `changeStream()` sends `mode.ordinal()` on 0x1500 (= 5376).
#:
#: This package opens every session as LIVING and never leaves it. If the
#: A000088 firmware serves recording lists only to a session in SD mode, that
#: alone explains the silence - and it is one byte on a command we already
#: send, not a second channel and not a different transport.
SESSION_MODE_IDLE = 0
SESSION_MODE_LIVING = 1
SESSION_MODE_SD = 2


def session_mode_payload(mode: int, channel: int = 0) -> bytes:
    """`SMsgAVIoctrlSessionModeReq`: channel int32 LE, mode byte, 3 reserved.

    The same 8-byte shape this package already sends for LIVING, and the same
    shape `SETSTREAMCTRL` uses - which is what makes this cheap to try.
    """
    return struct.pack("<IB3x", channel, mode)


async def set_session_mode(session, mode: int, *, timeout: float = 2.5) -> dict:
    """Switch a live session into ``mode`` and report what came back.

    Returns a dict rather than a bool: "asked and got 5377", "asked and got
    silence", and "could not ask" are three different answers, and collapsing
    them is the mistake this harness keeps having to unlearn.
    """
    ask = getattr(session, "async_avio_request", None)
    if ask is None:
        return {"asked": False, "why": "no async_avio_request on this session"}
    alive = getattr(session, "is_alive", None)
    if alive is not None and not alive:
        return {"asked": False, "why": "session already closed"}
    payload = session_mode_payload(mode)
    try:
        reply = await ask(SESSION_MODE_REQ, payload,
                          response_cmd=SESSION_MODE_RESP, timeout=timeout)
    except Exception as exc:
        return {"asked": True, "error": f"{type(exc).__name__}: {exc}"[:120]}
    out = {"asked": True, "mode": mode, "sent_len": len(payload)}
    out.update(_describe(reply))
    return out


async def probe_sd_events(session, *, days: int = 7,
                          timeout: float = 2.5) -> Optional[dict]:
    """Ask the event commands and return what came back. Never raises.

    The timeout is 2.5 s per request, not 8, and every request checks the
    session first. Seven requests at 8 s could spend 56 s against a session
    whose ffmpeg window is 28 s, and that is not hypothetical: it is what made
    run 31498856848 report silence from a camera that had answered the same
    requests minutes earlier. A probe whose result depends on how many
    questions it asks is measuring itself.

    A request skipped because the session had ended is recorded as
    ``session_closed`` rather than as ``answered: false`` - "we never asked" and
    "it did not reply" are the distinction this whole harness keeps having to
    relearn.
    """
    ask = getattr(session, "async_avio_request", None)
    if ask is None:
        return None
    now = time.time()
    out: dict = {}
    for label, cmd, resp, payload in (
        ("haslistevent", HASLISTEVENT_REQ, HASLISTEVENT_RESP,
         haslistevent_payload(now - days * 86400, now, event=SD_EVENT_APP)),
        ("listevent", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now, event=SD_EVENT_APP)),
        # Same command with the selector the first attempt used, so a reply to
        # one and not the other localises the difference to the selector rather
        # than to the layout. It earned its place: on an A001064 the 0x12
        # variant was answered and this one was not.
        ("listevent_event0", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now, event=0)),
        # These predate the empty-slot finding above: they were built when the
        # A000088s answered nothing, to vary one term at a time against the
        # request that was known to work elsewhere. With a card present all
        # three answer, so they are kept as a control rather than as a probe.
        ("haslistevent_ch1", HASLISTEVENT_REQ, HASLISTEVENT_RESP,
         haslistevent_payload(now - days * 86400, now, channel=1,
                              event=SD_EVENT_APP)),
        ("listevent_ch1", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now, channel=1,
                           event=SD_EVENT_APP)),
        ("listevent_status1", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now, status=1,
                           event=SD_EVENT_APP)),
        # One day rather than seven: HASLISTEVENT answers one byte per hour, so
        # a 24-byte answer would also confirm the map reading on a second range.
        ("haslistevent_1day", HASLISTEVENT_REQ, HASLISTEVENT_RESP,
         haslistevent_payload(now - 86400, now, event=SD_EVENT_APP)),
        # The map came back all-zero on a camera whose LISTEVENT answered with
        # four real records for the same window - and the map is asked with the
        # 0x12 selector, the one that returned an EMPTY record page. Same
        # selector, same suspicion: ask the map with event=0 too.
        ("haslistevent_event0", HASLISTEVENT_REQ, HASLISTEVENT_RESP,
         haslistevent_payload(now - days * 86400, now, event=0)),
        ("haslistevent_1day_event0", HASLISTEVENT_REQ, HASLISTEVENT_RESP,
         haslistevent_payload(now - 86400, now, event=0)),
    ):
        alive = getattr(session, "is_alive", None)
        if alive is not None and not alive:
            out[label] = {"session_closed": True}
            continue
        try:
            reply = await ask(cmd, payload, response_cmd=resp, timeout=timeout)
            out[label] = _describe(reply)
            out[label]["sent_len"] = len(payload)
        except Exception as exc:
            out[label] = {"error": f"{type(exc).__name__}: {exc}"[:120]}

    # Everything above was asked of a session in LIVING mode, which is the only
    # mode this package has ever put a session into. Now switch it to SD and ask
    # the two that matter again.
    #
    # This is the experiment the A000088 silence actually points at. The vendor
    # enum has three modes - IDLE, LIVING, SD - and `changeStream()` sends the
    # ordinal on the same 5376 this package already sends as LIVING. A firmware
    # that serves recording lists only in SD mode would answer exactly as
    # observed: byte-exact requests, acked heartbeats, and no reply.
    #
    # The before/after pair is the whole point. An answer only after the switch
    # identifies the mode as the missing term; silence in both says the mode was
    # never it, and the same session asked both, so nothing else moved. That is
    # why these two name the selector: `haslistevent` and `listevent` above send
    # 0x12, so these must send 0x12 too. Leaving them on the builders' default -
    # which is 0 - would move the mode AND the selector between the two halves
    # of the comparison, and a difference could no longer be attributed to
    # either.
    out["session_mode_sd"] = await set_session_mode(
        session, SESSION_MODE_SD, timeout=timeout)
    for label, cmd, resp, payload in (
        ("haslistevent_in_sd_mode", HASLISTEVENT_REQ, HASLISTEVENT_RESP,
         haslistevent_payload(now - days * 86400, now, event=SD_EVENT_APP)),
        ("listevent_in_sd_mode", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now, event=SD_EVENT_APP)),
    ):
        alive = getattr(session, "is_alive", None)
        if alive is not None and not alive:
            out[label] = {"session_closed": True}
            continue
        try:
            reply = await ask(cmd, payload, response_cmd=resp, timeout=timeout)
            out[label] = _describe(reply)
            out[label]["sent_len"] = len(payload)
        except Exception as exc:
            out[label] = {"error": f"{type(exc).__name__}: {exc}"[:120]}

    # Put it back. The caller's session keeps streaming after this returns, and
    # leaving a viewer's live view parked in SD mode to satisfy a probe would be
    # the probe breaking the thing it rode in on.
    out["session_mode_restore"] = await set_session_mode(
        session, SESSION_MODE_LIVING, timeout=timeout)
    return out
