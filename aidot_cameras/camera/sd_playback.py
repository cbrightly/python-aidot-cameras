"""Ask a camera to play a recording off its own SD card.

The control surface only - the request bytes and the reply decode. Opening a
session the camera will play into is ``async_open_sd_playback`` on the camera
client; this half is pure so it can be tested with no camera in the way.

The request is the vendor app's own encoder,
``AVIOCTRLDEFs$SMsgAVIoctrlPlayRecord.parseContent(IIIIJII)``, as called from
``KVSWebRTCChannel.sdRecordPlay``: 0x18 bytes, little-endian.

    off 0   u32   0 (always 0 in the app's call)
    off 4   u32   command (PLAY_START, PLAY_STOP, ...)
    off 8   u32   param: 0, except PLAY_SEEKTIME, where the app sends a seek
                  counter (1..255, one more per seek)
    off 12  8B    STimeDay, UTC: u16 year, month, day, wday, hour, minute,
                  second; wday is Calendar.DAY_OF_WEEK, Sunday = 1. The record's
                  start, except PLAY_SEEKTIME, where it is the seek target
                  (the record's start plus the offset; KVSWebRTCChannel W2)
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

import dataclasses
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

#: ``E_CMD_AVIO_CTRL_SESSION_MODE_REQ`` modes (KVSWebRTCChannel's q enum).
SESSION_MODE_LIVING = 1
SESSION_MODE_SD = 2


@dataclasses.dataclass(frozen=True)
class SdPlaybackStart:
    """What an open needs to play a recording instead of streaming live.

    ``request`` is the PLAYCONTROL payload the open sends itself once the camera
    has acknowledged ``session_mode`` on the data channel, before it waits for
    media - because in SD mode the camera sends nothing until it is asked to
    play.
    """

    request: bytes
    session_mode: int = SESSION_MODE_SD
    #: The record being played and its playback channel, for the session's
    #: pause, resume and seek (they name the same record and channel).
    record: Any = None
    sd_channel: int = 1


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
    command: int,
    record: Any,
    *,
    sd_channel: int = 1,
    param: int = 0,
    offset_s: float = 0,
) -> bytes:
    """The 24 bytes for one playback command against one record.

    ``record`` is an ``SdEvent`` (anything with the date fields and ``event``).
    ``offset_s`` moves the time field that many seconds past the record's
    start - the seek target, for PLAY_SEEKTIME. The field holds whole seconds.
    ``sd_channel`` must be 1..26, the range the app allocates; a value outside it
    is a caller bug and raises rather than being clamped, because a silently
    changed channel is a request the camera answers while sending nothing.
    """
    if not _MIN_SD_CHANNEL <= sd_channel <= _MAX_SD_CHANNEL:
        raise ValueError(
            f"sd_channel must be {_MIN_SD_CHANNEL}..{_MAX_SD_CHANNEL}, got {sd_channel}"
        )
    if offset_s < 0:
        raise ValueError(f"offset_s must not be negative, got {offset_s}")
    head = struct.pack("<III", 0, command, param)
    y, mo, d, h, mi, s = (
        record.year,
        record.month,
        record.day,
        record.hour,
        record.minute,
        record.second,
    )
    if offset_s:
        t = datetime.datetime(y, mo, d, h, mi, s) + datetime.timedelta(
            seconds=int(offset_s)
        )
        y, mo, d, h, mi, s = t.year, t.month, t.day, t.hour, t.minute, t.second
    when = struct.pack("<HBBBBBB", y, mo, d, _java_day_of_week(y, mo, d), h, mi, s)
    tail = struct.pack("<BB", sd_channel, int(getattr(record, "event", 0)) & 0xFF)
    return (
        head
        + when
        + tail
        + b"\x00" * (_PAYLOAD_LEN - len(head) - len(when) - len(tail))
    )


def answers(command: int):
    """A reply match for one playback command: the 0x31b reply echoes it in its
    first word, and every playback command shares that one reply id."""

    def _match(resp: Any) -> bool:
        data = bytes(getattr(resp, "payload", b"") or b"")
        return len(data) >= 4 and struct.unpack_from("<I", data)[0] == command

    return _match


def session_mode_of(payload: Optional[bytes]) -> Optional[int]:
    """The mode a SESSION_MODE_RESP (5377) payload reports, or None.

    The 12-byte reply carries it in the third word: 1 after LIVING, 2 after
    SD, on every A001064 run of 2026-10-26/27 and the A000088 of 2026-10-27.
    """
    data = bytes(payload or b"")
    if len(data) < 12:
        return None
    return struct.unpack_from("<I", data, 8)[0]


def session_mode_is(mode: int):
    """A reply match for SESSION_MODE_RESP (5377) reporting ``mode``."""

    def _match(resp: Any) -> bool:
        return session_mode_of(getattr(resp, "payload", None)) == mode

    return _match


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


# Peer id fields a playback open announces (generate_webrtc_peer_id).
#: Field 2's first character: the Android app. The firmware of every owned
#: model gives H265, and the second video transceiver recorded H265 is sent
#: to, only to client class 0 or 1; the library's live class (2) gets audio
#: alone from an H265-recording camera.
PLAYBACK_CLIENT_CLASS = 0
#: Field 3, the app's playCmd: LIVE=0, SDCARD=1, PRE_LINK=2.
PLAY_CMD_SDCARD = 1
#: Field 5, the StreamID the firmware logs: 0 is the main stream.
PLAYBACK_STREAM = 0

#: The DTLS open's refusal: there, a recording is taken raw into the MPEG-TS
#: recorder, so playback needs a .ts output_path and takes no on_frame or
#: rtsp_push_url (nothing decodes or serves the H265).
DTLS_PLAYBACK_UNSUPPORTED = (
    "SD playback on a DTLS camera records to a .ts output_path only"
    " (no on_frame, no rtsp_push_url)"
)


class _CameraSdPlaybackMixin:
    """Playing a card recording, mixed into CameraMixin."""

    async def async_open_sd_playback(
        self,
        record: Any,
        *,
        on_frame: Any = None,
        output_path: Optional[str] = None,
        max_seconds: Optional[float] = None,
        rtsp_push_url: Optional[str] = None,
        status_callback: Any = None,
        timeout: float = 45.0,
        sd_channel: int = 1,
    ) -> Any:
        """Open a session that plays ``record`` instead of the live view.

        ``record`` is an ``SdEvent`` from ``async_get_sd_recordings``. Returns
        the session carrying the recording - an ``SdesSession`` served the way
        a live one is (``output_path``, ``rtsp_push_url``, ``on_frame``), or on
        a DTLS camera a ``WebRTCSession`` recording to ``output_path`` - with the
        camera's answer to PLAY_START from ``await session.playback_reply()``.
        ``async_sd_pause()``, ``async_sd_resume()`` and ``async_sd_seek(s)`` on
        the session control it; ``session.stop()`` ends it.

        Raises ``AidotSdPlaybackUnavailable`` rather than returning the live
        view where it cannot play: an answer with no data channel, or a DTLS
        camera without a ``.ts`` ``output_path`` (over DTLS the recording is
        taken raw into the MPEG-TS recorder; there is no ``on_frame`` decode or
        serve). Played live on all three models (2026-10-27): the A001064 and
        the A001513 over SDES, the A000088 over DTLS.

        The session is not kept alive or reopened: a recording that ends or
        drops is reopened by calling this again. It needs the camera's one
        media slot, so it raises the same error while this client is streaming
        live; ``async_stop_streaming()`` frees it.
        """
        from ..exceptions import AidotSdPlaybackUnavailable

        stream_task = getattr(self, "_stream_task", None)
        if (stream_task is not None and not stream_task.done()) or getattr(
            self, "has_live_session", False
        ):
            # Two sessions on one camera fight for its single media slot: the
            # live view drops, its keepalive reopens it and pushes the
            # playback out, or the playback comes up empty.
            raise AidotSdPlaybackUnavailable(
                "a live stream holds this camera's media slot;"
                " stop it with async_stop_streaming() first"
            )
        start = SdPlaybackStart(
            request=playcontrol_payload(PLAY_START, record, sd_channel=sd_channel),
            record=record,
            sd_channel=sd_channel,
        )
        return await self.async_open_webrtc_stream(
            on_frame,
            timeout=timeout,
            output_path=output_path,
            max_seconds=max_seconds,
            status_callback=status_callback,
            rtsp_push_url=rtsp_push_url,
            reuse_peer_id=playback_peer_id(self, sdes=bool(self.is_sdes_camera)),
            _sd_playback=start,
        )


def playback_peer_id(camera: Any, *, sdes: bool) -> str:
    """The peer id a playback open announces, for the SDES or DTLS transport.

    The SDES-to-DTLS fallback builds a fresh one with ``sdes=False``: the
    SDES id's transport digit is wrong for DTLS, and a live id (class 2,
    PRE_LINK) would get audio alone from an H265-recording camera.
    """
    return camera.generate_webrtc_peer_id(
        live_type=PLAY_CMD_SDCARD,
        # Field 4, a literal 0 in the app - not the StreamID, which is
        # field 5 (``stream``).
        stream_id=0,
        sdes=sdes,
        device_id=camera.device_id,
        client_class=PLAYBACK_CLIENT_CLASS,
        stream=PLAYBACK_STREAM,
    )


# --- The recording's own clock (SDES) ---------------------------------------
#
# The camera stamps a recording's RTP as if it had been recorded at its live
# frame rate and sends it at that pace: an A001064 night recording made at
# 5 fps (200 ms a frame) went out at 20 fps (50 ms a frame), its 60 s in 15 s,
# and its sound - sent in real time - stopped when the video ran out
# (2026-10-29). The vendor app plays it at the right speed because every
# packet also carries the frame's time in the recording, in an RTP header
# extension the camera adds once the offer names transport-cc and
# rtp-stream-id (OfferUtils.processRtp keeps exactly those two extmap lines;
# the answer then says ``supportRtpExt: 1``). The 11-byte value is a 2-byte
# playback channel, a flag byte (0xf0) and the time in Unix milliseconds, all
# big-endian; all ones marks the end of the recording, on the packets the
# camera sends after its last frame.

#: The two header extensions the vendor app's offer keeps, with the ids this
#: library offers them under.
EXT_TRANSPORT_CC = (
    "http://www.ietf.org/id/draft-holmer-rmcat-transport-wide-cc-extensions-01"
)
EXT_FRAME_TIME = "urn:ietf:params:rtp-hdrext:sdes:rtp-stream-id"
EXT_TRANSPORT_CC_ID = 3
EXT_FRAME_TIME_ID = 4
#: The frame-time value the camera sends once the recording has run out.
FRAME_TIME_END = 0xFFFFFFFFFFFFFFFF

_AUDIO_CLOCKS = {0: 8000, 8: 8000}


def offer_with_frame_times(sdp: str) -> str:
    """``sdp`` asking for the per-frame recording time: both extmap lines in
    every audio and video section, after its mid, and transport-cc feedback
    on each video payload type."""
    extmaps = [
        f"a=extmap:{EXT_TRANSPORT_CC_ID} {EXT_TRANSPORT_CC}",
        f"a=extmap:{EXT_FRAME_TIME_ID} {EXT_FRAME_TIME}",
    ]
    out = []
    kind, pts = "", []
    for line in sdp.split("\r\n"):
        if line.startswith("m="):
            kind = line[2:].split(" ", 1)[0]
            pts = line.split()[3:]
        out.append(line)
        if line.startswith("a=mid:") and kind in ("audio", "video"):
            out.extend(extmaps)
            if kind == "video":
                out.extend(f"a=rtcp-fb:{pt} transport-cc" for pt in pts)
    return "\r\n".join(out)


def rtp_extension(pkt: bytes, ext_id: int) -> "Optional[bytes]":
    """The value of header extension ``ext_id`` in RTP packet ``pkt`` (RFC
    8285, one- or two-byte form), or None. The header is in the clear under
    SRTP, so this reads a packet still encrypted."""
    if len(pkt) < 12 or not pkt[0] & 0x10:
        return None
    off = 12 + 4 * (pkt[0] & 0x0F)
    if len(pkt) < off + 4:
        return None
    profile = int.from_bytes(pkt[off : off + 2], "big")
    end = off + 4 + 4 * int.from_bytes(pkt[off + 2 : off + 4], "big")
    if end > len(pkt):
        return None
    i = off + 4
    while i < end:
        if profile == 0xBEDE:
            b = pkt[i]
            if b == 0:
                i += 1
                continue
            eid, n, i = b >> 4, (b & 0x0F) + 1, i + 1
        elif profile >> 4 == 0x100 and i + 1 < end:
            if pkt[i] == 0:
                i += 1
                continue
            eid, n, i = pkt[i], pkt[i + 1], i + 2
        else:
            return None
        if eid == ext_id:
            return bytes(pkt[i : i + n])
        i += n
    return None


def frame_time_ms(value: "Optional[bytes]") -> "Optional[int]":
    """The recording time (Unix ms) in a frame-time extension value;
    ``FRAME_TIME_END`` at the end of the recording; None if absent."""
    if value is None or len(value) != 11:
        return None
    return int.from_bytes(value[3:11], "big")


class PlaybackClock:
    """Pairs each stream's RTP timestamps with the recording's own time.

    Fed every packet the playback forwards; keeps one (rtp timestamp, ms)
    pair per change of recording time, per payload type. ``ended`` turns
    True on the first end-of-recording mark. ``timing()`` is what
    ``retime_playback_ts`` needs.
    """

    def __init__(self) -> None:
        self._pairs: "dict[int, list[tuple[int, int]]]" = {}
        self.ended = False

    def observe(self, pkt: bytes) -> bool:
        """Note ``pkt``; True if it is an end-of-recording packet (forward
        nothing of those: the recording is over)."""
        ms = frame_time_ms(rtp_extension(pkt, EXT_FRAME_TIME_ID))
        if ms is None:
            return False
        if ms == FRAME_TIME_END:
            self.ended = True
            return True
        pairs = self._pairs.setdefault(pkt[1] & 0x7F, [])
        if not pairs or pairs[-1][1] != ms:
            pairs.append((int.from_bytes(pkt[4:8], "big"), ms))
        return False

    def timing(self) -> "dict[str, tuple[float, float]]":
        """Per stream kind (``"video"``, ``"audio"``): (scale, start_s) - each
        second of RTP time is ``scale`` seconds of recording, and the stream
        starts ``start_s`` after the earliest stream does. Empty when no
        frame times arrived."""
        # One stream per kind: the payload type with the most frame times
        # (a stray second video type would otherwise decide by dict order).
        best: "dict[str, int]" = {}
        for pt, pairs in self._pairs.items():
            kind = "audio" if pt in _AUDIO_CLOCKS else "video"
            if len(pairs) > len(self._pairs.get(best.get(kind, -1), ())):
                best[kind] = pt
        firsts = [self._pairs[pt][0][1] for pt in best.values()]
        if not firsts:
            return {}
        origin = min(firsts)
        out = {}
        for kind, pt in best.items():
            pairs = self._pairs[pt]
            clock = _AUDIO_CLOCKS.get(pt, 90000)
            (r0, m0), (r1, m1) = pairs[0], pairs[-1]
            rtp_s = ((r1 - r0) & 0xFFFFFFFF) / clock
            scale = (m1 - m0) / 1000 / rtp_s if rtp_s > 0 else 1.0
            out[kind] = (scale, (m0 - origin) / 1000)
        return out


def retime_argv(
    binary: str, src: str, dst: str, timing: "dict[str, tuple[float, float]]"
) -> "list[str]":
    """ffmpeg command that copies ``src`` to ``dst`` with each stream moved
    onto the recording's clock (``PlaybackClock.timing``). An SDES capture's
    G.711 rides a data stream, which takes the audio timing."""

    def setts(scale: float, start_s: float) -> str:
        def at(ts: str) -> str:
            return f"({ts}-START{ts})*{scale:.6f}+{start_s:.6f}/TB"

        return f"setts=pts={at('PTS')}:dts={at('DTS')}"

    argv = [binary, "-hide_banner", "-loglevel", "error", "-nostdin", "-y"]
    argv += ["-i", src, "-map", "0", "-c", "copy"]
    if "video" in timing:
        argv += ["-bsf:v", setts(*timing["video"])]
    if "audio" in timing:
        argv += ["-bsf:a", setts(*timing["audio"]), "-bsf:d", setts(*timing["audio"])]
    return [*argv, "-f", "mpegts", dst]


# --- DTLS (A000088): the recording's H265 is taken raw ----------------------
#
# aiortc has no H265, and the camera sends a recording it holds as H265 on a
# second video section that only a class 0/1 viewer whose offer names two
# codecs gets (the iOS app's capture against an A000088, 2026-05-02: audio,
# H264 PT 127, H265 PT 98, data channel). The offer names H265 next to H264,
# aiortc keeps its three sections, and the H265 packets - which it would
# discard as unrouted - are taken at its RTP router, assembled into access
# units here and recorded as HEVC. Played on an A000088 on 2026-10-27:
# 2560x1440 HEVC, 0 decode errors, audio continuous.

_NAL_SC = b"\x00\x00\x00\x01"


def offer_with_h265(sdp: str) -> "tuple[str, Optional[int]]":
    """``sdp`` with an H265 payload type added to its video section.

    The firmware's codec decision counts the codecs the offer names
    (u8VideoCodecCnt); only with two does a class 0/1 viewer get the H265
    transceiver. Returns the new SDP and the payload type used (None when
    the offer has no video section or no free dynamic payload type).
    """
    lines = sdp.split("\r\n")
    vi = next((i for i, ln in enumerate(lines) if ln.startswith("m=video ")), None)
    if vi is None:
        return sdp, None
    # Free in EVERY section: the sections share one BUNDLE transport, and the
    # audio section's opus takes 96 in aiortc's offer.
    used = {
        int(t)
        for ln in lines
        if ln.startswith("m=")
        for t in ln.split()[3:]
        if t.isdigit()
    }
    pt = next((p for p in range(96, 128) if p not in used), None)
    if pt is None:
        return sdp, None
    lines[vi] = f"{lines[vi]} {pt}"
    end = next(
        (i for i in range(vi + 1, len(lines)) if lines[i].startswith("m=")),
        len(lines),
    )
    # After the section's last rtpmap/fmtp, or at its end.
    at = max(
        (
            i + 1
            for i in range(vi, end)
            if lines[i].startswith(("a=rtpmap:", "a=fmtp:"))
        ),
        default=end,
    )
    while at > vi + 1 and lines[at - 1] == "":
        at -= 1
    lines.insert(at, f"a=rtpmap:{pt} H265/90000")
    return "\r\n".join(lines), pt


def answer_h265_pt(sdp: str) -> "Optional[int]":
    """The payload type the camera's answer gives H265, or None."""
    for ln in sdp.replace("\r\n", "\n").split("\n"):
        if ln.startswith("a=rtpmap:") and " H265/" in ln.upper().replace(
            "HEVC", "H265"
        ):
            head = ln[len("a=rtpmap:") :].split(" ", 1)[0]
            if head.isdigit():
                return int(head)
    return None


def _h265_header_ok(b0: int, b1: int) -> bool:
    return b0 & 0x81 == 0 and b1 & 0x07 >= 1


def starts_h265_stream(payload: bytes) -> bool:
    """Whether an RTP payload (RFC 7798) opens an H265 stream: a VPS, SPS or
    PPS, or an IRAP slice - alone, first in an aggregation packet, or as the
    start of a fragmentation unit.

    Strict on purpose: it is what the DTLS tap learns an unnamed payload type
    from, and a two-byte header check alone also passes H264 FU-A packets.
    """
    if len(payload) < 3 or not _h265_header_ok(payload[0], payload[1]):
        return False
    t = (payload[0] >> 1) & 0x3F
    if 32 <= t <= 34 or 16 <= t <= 21:
        return True
    if t == 48 and len(payload) >= 6:
        return (
            _h265_header_ok(payload[4], payload[5])
            and 32 <= (payload[4] >> 1) & 0x3F <= 34
        )
    if t == 49:
        return bool(payload[2] & 0x80) and 16 <= payload[2] & 0x3F <= 21
    return False


def answer_h265_parameter_sets(sdp: str, pt: Optional[int]) -> "list[bytes]":
    """VPS, SPS and PPS the answer carries out of band for ``pt``
    (``a=fmtp:<pt> sprop-vps=...;sprop-sps=...;sprop-pps=...``), in that
    order; empty when it carries none."""
    import base64

    if pt is None:
        return []
    out = []
    for ln in sdp.replace("\r\n", "\n").split("\n"):
        if not ln.startswith(f"a=fmtp:{pt} "):
            continue
        params = dict(
            kv.strip().split("=", 1)
            for kv in ln.split(" ", 1)[1].split(";")
            if "=" in kv
        )
        for key in ("sprop-vps", "sprop-sps", "sprop-pps"):
            for b64 in filter(None, params.get(key, "").split(",")):
                try:
                    out.append(base64.b64decode(b64))
                except Exception:
                    continue
    return out


class SeqReorder:
    """Puts one RTP stream back in sequence order, waiting a little for gaps.

    A packet the camera resends after a NACK arrives after the ones that
    followed it; the access-unit assembler takes packets in order, so they
    are held here until the gap fills or ``max_wait_s`` passes, then handed
    on in order (a gap given up on reaches the assembler as a gap, and its
    unit is handed on damaged). ``push`` returns what is ready; ``flush``
    returns the rest at the end.
    """

    #: Most packets held; past this the oldest gap is given up at once.
    MAX_HELD = 1500

    def __init__(self, max_wait_s: float = 0.3) -> None:
        self.max_wait_s = max_wait_s
        self._next: "Optional[int]" = None
        self._held: "dict[int, tuple[float, Any]]" = {}
        self.late = 0  # arrived after its gap was given up

    def push(self, seq: int, item: Any, now: float) -> "list":
        if self._next is None:
            self._next = seq
        if (seq - self._next) & 0x8000:  # before the next expected one
            self.late += 1
            return []
        self._held.setdefault(seq, (now, item))
        return self._release(now)

    def _release(self, now: float) -> "list":
        out = []
        while True:
            while self._next in self._held:
                out.append(self._held.pop(self._next)[1])
                self._next = (self._next + 1) & 0xFFFF
            if not self._held:
                return out
            oldest = min(t for t, _ in self._held.values())
            if now - oldest < self.max_wait_s and len(self._held) < self.MAX_HELD:
                return out
            # Give up on the gap: carry on from the earliest packet held.
            self._next = min(self._held, key=lambda s: (s - self._next) & 0xFFFF)

    def flush(self) -> "list":
        out = []
        while self._held:
            if self._next not in self._held:
                self._next = min(self._held, key=lambda s: (s - self._next) & 0xFFFF)
            out.append(self._held.pop(self._next)[1])
            self._next = (self._next + 1) & 0xFFFF
        return out


class H265AccessUnits:
    """RTP payloads (RFC 7798) in, Annex B access units out.

    ``feed`` takes one packet's sequence number, RTP timestamp, marker bit and
    payload, and returns the access units it completed as
    ``(bytes, rtp_timestamp, is_keyframe)`` - the shape the DTLS recorder's
    video queue takes. A unit with a sequence gap in it is handed on with
    what arrived (``damaged`` counts them): every later frame up to the next
    keyframe refers to it, so dropping it made all of those undecodable - an
    A000088 playback lost 122 frames to one lost packet (2026-10-29), where
    the decoder conceals a damaged frame and carries on. ``keep_damaged=False``
    drops them instead (``dropped``). ``parameter_sets`` (from the answer's
    fmtp) go in front of a keyframe that does not carry its own VPS.
    """

    def __init__(
        self,
        parameter_sets: "Optional[list[bytes]]" = None,
        *,
        keep_damaged: bool = True,
    ) -> None:
        self.parameter_sets = list(parameter_sets or [])
        self.keep_damaged = keep_damaged
        self._ts: "Optional[int]" = None
        self._nals: "list[bytes]" = []
        self._fu: "Optional[bytearray]" = None
        self._last_seq: "Optional[int]" = None
        self._broken = False
        self.dropped = 0
        self.damaged = 0
        # The camera sends a recording's last frame twice, under a new
        # timestamp (A000088, 2026-10-30): a decoder then sees the same picture
        # order count twice. A unit identical to the one before is not handed
        # on (``repeats`` counts them).
        self.repeats = 0
        self._last_unit: "Optional[bytes]" = None

    def _emit(self) -> "list[tuple[bytes, int, bool]]":
        out = []
        if self._fu is not None:
            self._broken = True  # a fragment never finished
            if self.keep_damaged and len(self._fu) > 2:
                self._nals.append(bytes(self._fu))  # what arrived of it
        keep = not self._broken or self.keep_damaged
        if self._nals and keep and self._ts is not None:
            types = [(n[0] >> 1) & 0x3F for n in self._nals if n]
            kf = any(16 <= t <= 21 for t in types)
            nals = self._nals
            if kf and 32 not in types and self.parameter_sets:
                nals = self.parameter_sets + nals
            unit = b"".join(_NAL_SC + n for n in nals)
            if unit == self._last_unit:
                self.repeats += 1
            else:
                out.append((unit, self._ts, kf))
                self._last_unit = unit
                if self._broken:
                    self.damaged += 1
        elif self._nals or self._broken:
            self.dropped += 1
        self._nals, self._fu, self._broken = [], None, False
        return out

    def feed(
        self, seq: int, ts: int, marker: bool, payload: bytes
    ) -> "list[tuple[bytes, int, bool]]":
        out = []
        missing = 0
        if self._last_seq is not None:
            missing = (seq - self._last_seq - 1) & 0xFFFF
        gap = missing != 0
        if self._ts is not None and ts != self._ts:
            # A unit in hand that never saw its marker lost at least its last
            # packet; one already emitted at its marker lost nothing. Whatever
            # else went missing is the head of this new unit (its VPS/SPS, or
            # the first slice of a keyframe), so it is not handed on either.
            pending = bool(self._nals) or self._fu is not None
            if gap and pending:
                self._broken = True
            out += self._emit()
            if missing > (1 if pending else 0):
                self._broken = True
        elif gap:
            self._broken = True
            self._fu = None
        self._last_seq, self._ts = seq, ts
        if len(payload) >= 2:
            t = (payload[0] >> 1) & 0x3F
            if t < 48:
                self._nals.append(bytes(payload))
            elif t == 48:  # aggregation packet
                i = 2
                while i + 2 <= len(payload):
                    n = struct.unpack_from("!H", payload, i)[0]
                    if n == 0 or i + 2 + n > len(payload):
                        self._broken = True  # truncated or corrupt: the rest is lost
                        break
                    self._nals.append(bytes(payload[i + 2 : i + 2 + n]))
                    i += 2 + n
            elif t == 49 and len(payload) >= 3:  # fragmentation unit
                fu = payload[2]
                if fu & 0x80:
                    hdr = bytes([(payload[0] & 0x81) | ((fu & 0x3F) << 1), payload[1]])
                    self._fu = bytearray(hdr + payload[3:])
                elif self._fu is not None:
                    self._fu += payload[3:]
                else:
                    self._broken = True
                if fu & 0x40 and self._fu is not None:
                    self._nals.append(bytes(self._fu))
                    self._fu = None
        if marker:
            out += self._emit()
        return out


class _PlaybackControls:
    """Pause, resume, seek and the PLAY_START reply, on either session type.

    The session sets ``_sd_playback`` (the SdPlaybackStart it was opened with,
    or None for live), ``_playback_waiter`` and ``_sd_seek_count``, and has
    ``async_avio_request``.
    """

    _sd_playback: Any = None
    _playback_waiter: Any = None
    _sd_seek_count = 0

    async def playback_reply(self, timeout: float = 10.0):
        """The camera's RECORD_PLAYCONTROL reply to the open's PLAY_START.

        An ``AvioResponse`` (its payload reads with
        ``decode_playcontrol_reply``), or None for a live session or a camera
        that did not answer in time.

        It arrives after the recording has started: 6.1-6.2 s after PLAY_START
        on an A001064, in four of four runs on 2026-10-27, when the session
        opens in SD mode. A wait that times out keeps listening, so asking
        again later still gets a reply that came late.
        """
        if self._playback_waiter is None:
            return None
        return await self._playback_waiter.result(timeout, keep=True)

    async def _sd_control(self, command: int, *, param=0, offset_s=0, timeout=8.0):
        """Send one RECORD_PLAYCONTROL for this session's record; its reply.

        A ``PlaycontrolReply``, or None: a live session, or no answer in time.
        Replies are matched on the playback command they echo, so a late reply
        to PLAY_START or to another control is never taken for this one.
        """
        start = self._sd_playback
        if start is None or start.record is None:
            return None
        resp = await self.async_avio_request(  # type: ignore[attr-defined]
            RECORD_PLAYCONTROL_REQ_CMD,
            playcontrol_payload(
                command,
                start.record,
                sd_channel=start.sd_channel,
                param=param,
                offset_s=offset_s,
            ),
            response_cmd=RECORD_PLAYCONTROL_RESP_CMD,
            timeout=timeout,
            # Every playback command's reply has the one AVIO id; take only
            # the one that echoes this command.
            match=answers(command),
        )
        return decode_playcontrol_reply(bytes(resp.payload)) if resp else None

    async def async_sd_pause(self):
        """Pause the recording this session plays (PLAY_PAUSE)."""
        return await self._sd_control(PLAY_PAUSE)

    async def async_sd_resume(self):
        """Resume a paused recording (PLAY_RESUME)."""
        return await self._sd_control(PLAY_RESUME)

    async def async_sd_seek(self, offset_s: float):
        """Play from ``offset_s`` seconds into the recording (PLAY_SEEKTIME).

        As the app sends it: the time field is the record's start plus the
        offset, and ``param`` a counter that goes up by one per seek, 1..255.
        """
        if self._sd_playback is None:
            return None
        self._sd_seek_count = self._sd_seek_count % 255 + 1
        return await self._sd_control(
            PLAY_SEEKTIME, param=self._sd_seek_count, offset_s=offset_s
        )
