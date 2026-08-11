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

**Why the A000088s answer nothing - settled 2026-08-11.** The silence pointed at
the channel, and the channel is what it turned out to be.

Measured, from run 31500427317 and its logs: the two SDES cameras (A001064,
A001513) answered `HASLISTEVENT` on the live session; all three A000088s
answered neither command, while the SAME sessions carried inbound AVIO fine -
851, 852, 5157 and 5377 all arrived and routed. So the DTLS inbound path works
on that model and these two commands specifically produce no frame at all, not
even one under an unexpected id. That rules out our routing and rules out a
mangled response layout.

Read from the vendor client (`IpcServiceImpl.handleData`), the reason: the app
does NOT ask for the recording list on the live channel. It keeps a SECOND
channel for SD work -

    sdWebRTCChannel = LdsChannelManager.getKVSWebRTCChannel(deviceId + "-SD")

- calls `getSDRecordList` on that when its data channel is connected, and falls
back to `LdsTutkChannel.getSDRecordList` (the out-of-scope TUTK path) when it is
not. The live channel is keyed on the bare device id.

So this probe asks the right question on the wrong channel, and a firmware that
serves SD only on the `-SD` channel answers exactly as observed. The SDES models
being more permissive about it is the anomaly, not the A000088s being broken.

What is NOT established, and cannot be from static reading: what that second
channel looks like on OUR transport. This library signals over MQTT `webrtcReq`
rather than a KVS channel-name registry, so there is no "-SD" string to set -
the mapping needs a capture of the app opening SD playback. Until then, an SD
listing on an A000088 is not reachable from here, and no amount of re-running
this probe against the live session will change that.
"""
import struct
import time
from typing import Optional

#: The event selector the app actually asks for. Read off KVSWebRTCChannel's
#: getSDRecordList: `const/16 v1, 0x12` passed as the `event` argument, with
#: status 0. The first version of this probe sent event=0 and got silence from
#: three cameras - a request the camera does not recognise answers exactly like
#: a firmware that never replies, which is why the request has to be the app's.
SD_EVENT_ALL = 0x12

#: From AVIOCTRLDEFs in the vendor's own client.
HASLISTEVENT_REQ = 0x4B5
HASLISTEVENT_RESP = 0x4B6
LISTEVENT_REQ = 0x318
LISTEVENT_RESP = 0x319


def stimeday(when: float) -> bytes:
    """The vendor's 8-byte STimeDay, from `AVIOCTRLDEFs$STimeDay`.

    `unsigned short year` then six single bytes - month, day, wday, hour,
    minute, second. UTC, because the field is named `startutctime`.
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
                         event: int = SD_EVENT_ALL) -> bytes:
    """22 bytes: channel, start, end, and a two-byte selector tail.

    The length is not a guess - `parseConent` allocates `const/16 v1, 0x16`,
    which is 22, and copies the channel at offset 0 and the first STimeDay at
    offset 4.
    """
    return (struct.pack("<I", channel)
            + stimeday(start) + stimeday(end)
            + struct.pack("<H", event & 0xFFFF))


def listevent_payload(start: float, end: float, channel: int = 0,
                      event: int = SD_EVENT_ALL, status: int = 0) -> bytes:
    """24 bytes, not 22 - and the difference is the whole point.

    `SMsgAVIoctrlListEventReq` has three `parseConent` overloads. The one whose
    field list this was first written from is not the one the WebRTC path uses:
    `KVSWebRTCChannel.getSDRecordList` calls the epoch-long overload, which
    allocates `const/16 v4, 0x18` - 24 bytes - where the other allocates 22.
    Four bytes of channel, two eight-byte STimeDays, the event and status
    selectors, then two bytes of tail.
    """
    return (struct.pack("<I", channel)
            + stimeday(start) + stimeday(end)
            + bytes((event & 0xFF, status & 0xFF))
            + b"\x00\x00")


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
    try:
        from aidot_cameras.camera.sd_events import decode_list_event_response
        page = decode_list_event_response(payload)
    except Exception:
        page = None
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
         haslistevent_payload(now - days * 86400, now)),
        ("listevent", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now)),
        # Same command with the selector the first attempt used, so a reply to
        # one and not the other localises the difference to the selector rather
        # than to the layout. It earned its place: on an A001064 the 0x12
        # variant was answered and this one was not.
        ("listevent_event0", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now, event=0)),
        # The SD-BEARING models (A000088) answer none of the above, while the
        # models with no card answer readily - the inversion item 6 is now stuck
        # on. These vary one thing at a time against the request that IS known
        # to work elsewhere, so a reply identifies which term mattered.
        ("haslistevent_ch1", HASLISTEVENT_REQ, HASLISTEVENT_RESP,
         haslistevent_payload(now - days * 86400, now, channel=1)),
        ("listevent_ch1", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now, channel=1)),
        ("listevent_status1", LISTEVENT_REQ, LISTEVENT_RESP,
         listevent_payload(now - days * 86400, now, status=1)),
        # One day rather than seven: HASLISTEVENT answers one byte per hour, so
        # a 24-byte answer would also confirm the map reading on a second range.
        ("haslistevent_1day", HASLISTEVENT_REQ, HASLISTEVENT_RESP,
         haslistevent_payload(now - 86400, now)),
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
    return out
