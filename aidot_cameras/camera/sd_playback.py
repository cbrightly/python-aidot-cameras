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
        peer_id = self.generate_webrtc_peer_id(
            live_type=PLAY_CMD_SDCARD,
            # Field 4, a literal 0 in the app - not the StreamID, which is
            # field 5 (``stream``).
            stream_id=0,
            sdes=bool(self.is_sdes_camera),
            device_id=self.device_id,
            client_class=PLAYBACK_CLIENT_CLASS,
            stream=PLAYBACK_STREAM,
        )
        return await self.async_open_webrtc_stream(
            on_frame,
            timeout=timeout,
            output_path=output_path,
            max_seconds=max_seconds,
            status_callback=status_callback,
            rtsp_push_url=rtsp_push_url,
            reuse_peer_id=peer_id,
            _sd_playback=start,
        )


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


class H265AccessUnits:
    """RTP payloads (RFC 7798) in, Annex B access units out.

    ``feed`` takes one packet's sequence number, RTP timestamp, marker bit and
    payload, and returns the access units it completed as
    ``(bytes, rtp_timestamp, is_keyframe)`` - the shape the DTLS recorder's
    video queue takes. A unit with a sequence gap in it is dropped rather than
    handed on damaged. ``parameter_sets`` (from the answer's fmtp) go in front
    of a keyframe that does not carry its own VPS.
    """

    def __init__(self, parameter_sets: "Optional[list[bytes]]" = None) -> None:
        self.parameter_sets = list(parameter_sets or [])
        self._ts: "Optional[int]" = None
        self._nals: "list[bytes]" = []
        self._fu: "Optional[bytearray]" = None
        self._last_seq: "Optional[int]" = None
        self._broken = False
        self.dropped = 0

    def _emit(self) -> "list[tuple[bytes, int, bool]]":
        out = []
        if self._fu is not None:
            self._broken = True  # a fragment never finished
        if self._nals and not self._broken and self._ts is not None:
            types = [(n[0] >> 1) & 0x3F for n in self._nals if n]
            kf = any(16 <= t <= 21 for t in types)
            nals = self._nals
            if kf and 32 not in types and self.parameter_sets:
                nals = self.parameter_sets + nals
            out.append((b"".join(_NAL_SC + n for n in nals), self._ts, kf))
        elif self._nals or self._broken:
            self.dropped += 1
        self._nals, self._fu, self._broken = [], None, False
        return out

    def feed(
        self, seq: int, ts: int, marker: bool, payload: bytes
    ) -> "list[tuple[bytes, int, bool]]":
        out = []
        gap = self._last_seq is not None and (seq - self._last_seq) & 0xFFFF != 1
        if self._ts is not None and ts != self._ts:
            # What went missing before this packet was the end of the unit
            # in hand (or whole units between): that unit is the damaged one.
            if gap:
                self._broken = True
            out += self._emit()
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
