"""Publish RTP straight into go2rtc over RTSP - no ffmpeg in the media path.

See ``docs/DESIGN-direct-publish.md``.  Everything here is stdlib-only and
thread-based: the SDES bridge and the DTLS tap both live on threads already,
and the publish is a byte pump.

Pieces:

* :class:`RtspPublisher` - a blocking RTSP *publish* client (OPTIONS ->
  ANNOUNCE -> SETUP... -> RECORD, TCP-interleaved RTP, OPTIONS keepalive).
  Written against go2rtc 1.9.14's server: it accepts TCP-interleaved publish
  only, binds the i-th ``m=`` line to interleaved channel ``2*i``, uses the
  FIRST codec of each line, and drops a publisher idle for 15 s.
* :class:`RtpTimeline` - owns a track's outgoing sequence numbers and
  timestamps, repairing the camera's timestamp jumps.
* :func:`publish_sdp_from_serve_sdp` - turns the narrowed loopback SDP that the
  SDES serve ffmpeg reads today into an ANNOUNCE body.
* :class:`LoopbackRtpPublisher` - a drop-in for the SDES serve ffmpeg
  ``subprocess.Popen``: it reads the same loopback ports and exposes
  ``poll/wait/terminate/kill/returncode/pid/stderr``, so the SDES open's
  lifecycle code works unchanged.
* :func:`dtls_rtp_publish_run` - the DTLS serve's alternative to the PyAV mux
  thread when the destination is ``rtsp://``.
"""

from __future__ import annotations

import base64
import collections
import logging
import math
import os
import queue as _queue
import random
import re
import select
import socket
import struct
import subprocess
import threading
import time
from typing import Callable, Deque, List, Optional, Tuple
from urllib.parse import unquote, urlsplit, urlunsplit

_LOGGER = logging.getLogger(__name__)

#: Env switch for the whole feature. Default OFF until it has soaked live.
ENV_DIRECT_PUBLISH = "AIDOT_DIRECT_PUBLISH"
#: Timestamp policy: ``hybrid`` (default), ``arrival`` or ``camera``.
ENV_PUBLISH_TIMESTAMPS = "AIDOT_PUBLISH_TIMESTAMPS"

_TRUTHY = ("1", "true", "yes", "on")

#: go2rtc drops an idle publisher after 15 s; any OPTIONS resets that.
KEEPALIVE_S = 5.0
#: Per-request handshake timeout (go2rtc uses 5 s on its side too).
REQUEST_TIMEOUT_S = 5.0
#: Packets held while the RTSP handshake is still running, so the first
#: keyframe of a cold open is not lost. ~4 s of a 1 Mbit/s camera.
PREROLL_MAX_PACKETS = 1500
#: RTP payload budget for the DTLS H.264 packetizer.
H264_MTU = 1200

#: Exit codes this module reports through the Popen-compatible surface.
#: A requested stop reports like a signal death (negative), which is what
#: ``_classify_ffmpeg_exit`` already treats as the expected teardown exit.
EXIT_TERMINATED = -15
EXIT_KILLED = -9
EXIT_FAILED = 1


def direct_publish_enabled() -> bool:
    """True when ``AIDOT_DIRECT_PUBLISH`` is truthy (default off)."""
    return os.environ.get(ENV_DIRECT_PUBLISH, "").strip().lower() in _TRUTHY


def is_publishable_url(url: Optional[str]) -> bool:
    """Only ``rtsp://`` / ``rtsps://``-less plain RTSP destinations are published."""
    return bool(url) and str(url).lower().startswith("rtsp://")


def _publish_gap_warn_s() -> float:
    """Seconds without a frame to publish before saying so; 0 disables."""
    try:
        return max(0.0, float(os.environ.get("AIDOT_PUBLISH_GAP_WARN_S", "1.0")))
    except (TypeError, ValueError):
        return 1.0


def timestamp_policy() -> str:
    """The configured timestamp policy; unknown values fall back to hybrid."""
    val = os.environ.get(ENV_PUBLISH_TIMESTAMPS, "hybrid").strip().lower()
    return val if val in ("hybrid", "arrival", "camera") else "hybrid"


class RtspPublishError(RuntimeError):
    """The RTSP server refused or dropped the publish."""


# --------------------------------------------------------------------------- #
# SDP                                                                          #
# --------------------------------------------------------------------------- #


class PublishTrack:
    """One ``m=`` line of the publish: kind, payload type, clock rate."""

    __slots__ = ("clock_rate", "codec", "index", "kind", "pt")

    def __init__(self, kind: str, pt: int, clock_rate: int, codec: str, index: int):
        self.kind = kind
        self.pt = pt
        self.clock_rate = clock_rate
        self.codec = codec
        self.index = index

    @property
    def channel(self) -> int:
        """The interleaved RTP channel go2rtc binds this track to."""
        return 2 * self.index

    def __repr__(self) -> str:  # pragma: no cover - debugging aid
        return (
            f"PublishTrack({self.kind}, pt={self.pt}, {self.codec}/{self.clock_rate},"
            f" ch={self.channel})"
        )


_STATIC_PTS = {0: ("PCMU", 8000), 8: ("PCMA", 8000)}


def publish_sdp_from_serve_sdp(
    serve_sdp: str,
    kinds: Tuple[str, ...] = ("video", "audio"),
) -> Tuple[str, List[PublishTrack], List[int]]:
    """ANNOUNCE body + tracks from the loopback SDP the serve ffmpeg would read.

    Returns ``(sdp, tracks, loopback_ports)`` where ``loopback_ports[i]`` is the
    UDP port the bridge sends track ``i`` to.

    Per ``m=`` line only the first payload type is kept (go2rtc uses only the
    first codec anyway; the SDES open narrows to one before launch). Ports,
    ``c=``, ``a=crypto``, ``a=rtcp-mux`` and direction attributes are dropped -
    a ``sendonly`` media would be read by go2rtc as a backchannel - and an
    ``a=control:trackID=N`` is added for SETUP. Media whose kind is not in
    ``kinds`` are left out entirely (an audio line that was never narrowed to
    the payload type the camera sends must not be announced).
    """
    sessions: List[str] = []
    medias: List[dict] = []
    cur: Optional[dict] = None
    for raw in serve_sdp.replace("\r\n", "\n").split("\n"):
        line = raw.strip()
        if not line:
            continue
        if line.startswith("m="):
            parts = line[2:].split()
            if len(parts) < 4:
                raise ValueError(f"malformed m= line: {line!r}")
            cur = {
                "kind": parts[0],
                "port": int(parts[1]),
                "pt": int(parts[3]),
                "attrs": [],
            }
            if parts[0] in kinds:
                medias.append(cur)
            continue
        if cur is None:
            if line[:2] in ("v=", "o=", "s=", "t="):
                sessions.append(line)
            continue
        if line.startswith("a="):
            cur["attrs"].append(line)
    if not medias:
        raise ValueError("serve SDP has no media")

    out = ["v=0"]
    have = {s[:2] for s in sessions}
    out.append(
        next((s for s in sessions if s.startswith("o=")), "o=- 0 0 IN IP4 127.0.0.1")
    )
    out.append(next((s for s in sessions if s.startswith("s=")), "s=aidot"))
    out.append("c=IN IP4 127.0.0.1")
    if "t=" in have:
        out.append(next(s for s in sessions if s.startswith("t=")))
    else:
        out.append("t=0 0")

    tracks: List[PublishTrack] = []
    ports: List[int] = []
    for i, m in enumerate(medias):
        pt = m["pt"]
        rtpmap = None
        fmtp = None
        for a in m["attrs"]:
            mm = re.match(r"a=rtpmap:(\d+)\s+([^/\s]+)/(\d+)", a)
            if mm and int(mm.group(1)) == pt:
                rtpmap = (mm.group(2), int(mm.group(3)), a)
            mf = re.match(r"a=fmtp:(\d+)\s", a)
            if mf and int(mf.group(1)) == pt:
                fmtp = a
        if rtpmap is None:
            if pt not in _STATIC_PTS:
                raise ValueError(f"no rtpmap for dynamic payload type {pt}")
            codec, rate = _STATIC_PTS[pt]
            rtpmap_line = f"a=rtpmap:{pt} {codec}/{rate}"
        else:
            codec, rate, rtpmap_line = rtpmap
        out.append(f"m={m['kind']} 0 RTP/AVP {pt}")
        out.append(rtpmap_line)
        if fmtp:
            out.append(fmtp)
        out.append(f"a=control:trackID={i}")
        tracks.append(PublishTrack(m["kind"], pt, rate, codec.upper(), i))
        ports.append(m["port"])
    return "\r\n".join(out) + "\r\n", tracks, ports


def build_publish_sdp(tracks: List[PublishTrack], fmtp: Optional[dict] = None) -> str:
    """ANNOUNCE body for tracks built in-process (the DTLS path)."""
    fmtp = fmtp or {}
    now = int(time.time())
    out = [
        "v=0",
        f"o=- {now} {now} IN IP4 127.0.0.1",
        "s=aidot",
        "c=IN IP4 127.0.0.1",
        "t=0 0",
    ]
    for t in tracks:
        out.append(f"m={t.kind} 0 RTP/AVP {t.pt}")
        out.append(f"a=rtpmap:{t.pt} {t.codec}/{t.clock_rate}")
        if t.pt in fmtp:
            out.append(f"a=fmtp:{t.pt} {fmtp[t.pt]}")
        out.append(f"a=control:trackID={t.index}")
    return "\r\n".join(out) + "\r\n"


# --------------------------------------------------------------------------- #
# RTP                                                                          #
# --------------------------------------------------------------------------- #


def parse_rtp(pkt: bytes) -> Optional[Tuple[int, bool, int, int, bytes]]:
    """``(pt, marker, seq, ts, payload)`` or None for a non-RTP datagram.

    CSRCs, header extensions and padding are stripped: the publisher rebuilds
    a plain 12-byte header.
    """
    if len(pkt) < 12 or (pkt[0] >> 6) != 2:
        return None
    b0, b1, seq, ts = struct.unpack_from("!BBHI", pkt, 0)
    pt = b1 & 0x7F
    if 72 <= pt <= 79:
        return None  # RTCP (SR/RR/SDES/BYE/APP collide with PT 72-76)
    off = 12 + 4 * (b0 & 0x0F)
    if b0 & 0x10:
        if len(pkt) < off + 4:
            return None
        ext_words = struct.unpack_from("!H", pkt, off + 2)[0]
        off += 4 + 4 * ext_words
    end = len(pkt)
    if b0 & 0x20 and end > off:
        end -= pkt[-1]
    if end < off:
        return None
    return pt, bool(b1 & 0x80), seq, ts, pkt[off:end]


def build_rtp(pt: int, marker: bool, seq: int, ts: int, ssrc: int, payload) -> bytes:
    return struct.pack(
        "!BBHII",
        0x80,
        (0x80 if marker else 0) | (pt & 0x7F),
        seq & 0xFFFF,
        ts & 0xFFFFFFFF,
        ssrc & 0xFFFFFFFF,
    ) + bytes(payload)


class RtpTimeline:
    """Outgoing sequence/timestamp owner for one track.

    Packets that share an input timestamp share an output timestamp (one
    video frame = one timestamp). A new input timestamp advances the output:

    * ``camera``  - by the camera's delta, always (wrap-aware);
    * ``arrival`` - by the arrival-clock delta since the previous frame;
    * ``hybrid``  - by the camera's delta when ``0 < delta <= max_step_s``,
      else by the arrival-clock delta.  This keeps the camera's even frame
      spacing and still absorbs the A001513's ~1.7 s backward step every
      ~30 s (the reason the ffmpeg serve stamps by arrival today).

    The output never steps backward and always advances by at least one tick
    on a new input timestamp. ``repairs`` counts arrival substitutions.
    """

    def __init__(
        self,
        clock_rate: int,
        *,
        policy: str = "hybrid",
        max_step_s: float = 3.0,
        clock: Callable[[], float] = time.monotonic,
    ):
        self.clock_rate = clock_rate
        self.policy = policy
        self.max_step = int(max_step_s * clock_rate)
        self._clock = clock
        self.ssrc = random.getrandbits(32)
        self._seq = random.getrandbits(16)
        self._out_ts = random.getrandbits(31)
        self._last_in: Optional[int] = None
        self._last_arrival = 0.0
        self.repairs = 0
        self.packets = 0

    def stamp(self, in_ts: int, arrival: Optional[float] = None) -> Tuple[int, int]:
        """``(seq, ts)`` for the next packet carrying camera timestamp ``in_ts``."""
        now = self._clock() if arrival is None else arrival
        in_ts &= 0xFFFFFFFF
        if self._last_in is None:
            self._last_in, self._last_arrival = in_ts, now
        elif in_ts != self._last_in:
            d = (in_ts - self._last_in) & 0xFFFFFFFF
            if d >= 0x80000000:
                d -= 0x100000000
            by_arrival = max(1, round((now - self._last_arrival) * self.clock_rate))
            if self.policy == "camera":
                step = d if d > 0 else 1
            elif self.policy == "arrival":
                step = by_arrival
            elif 0 < d <= self.max_step:
                step = d
            else:
                step = by_arrival
                self.repairs += 1
            self._out_ts = (self._out_ts + step) & 0xFFFFFFFF
            self._last_in, self._last_arrival = in_ts, now
        self._seq = (self._seq + 1) & 0xFFFF
        self.packets += 1
        return self._seq, self._out_ts


# --------------------------------------------------------------------------- #
# RTSP publish client                                                          #
# --------------------------------------------------------------------------- #


def redact_url(url: str) -> str:
    """The URL with any password replaced, for logs."""
    try:
        parts = urlsplit(url)
        if parts.password is None:
            return url
        netloc = f"{parts.username}:***@{parts.hostname}"
        if parts.port:
            netloc += f":{parts.port}"
        return urlunsplit((parts.scheme, netloc, parts.path, parts.query, ""))
    except Exception:
        return "<url>"


class RtspPublisher:
    """Blocking RTSP publish client. Not reconnecting: the owner decides.

    ``connect()`` runs the handshake; ``send_rtp()`` is thread-safe; any socket
    error marks the publisher dead (``error`` says why) and every later send
    raises :class:`RtspPublishError`.
    """

    def __init__(
        self,
        url: str,
        sdp: str,
        tracks: List[PublishTrack],
        *,
        timeout: float = REQUEST_TIMEOUT_S,
        keepalive_s: float = KEEPALIVE_S,
        user_agent: str = "python-aidot-cameras",
    ):
        parts = urlsplit(url)
        if parts.scheme.lower() != "rtsp" or not parts.hostname:
            raise ValueError(f"not an rtsp:// URL: {redact_url(url)}")
        self._host = parts.hostname
        self._port = parts.port or 554
        netloc = parts.hostname + (f":{parts.port}" if parts.port else "")
        # The request URI never carries credentials.
        self.url = urlunsplit(("rtsp", netloc, parts.path, parts.query, ""))
        self._auth = None
        if parts.username is not None:
            cred = f"{unquote(parts.username)}:{unquote(parts.password or '')}"
            self._auth = "Basic " + base64.b64encode(cred.encode()).decode()
        self.sdp = sdp
        self.tracks = tracks
        self._timeout = timeout
        self._keepalive_s = keepalive_s
        self._ua = user_agent
        self._sock: Optional[socket.socket] = None
        #: Set by close(), and checked by connect() at each step, so a close
        #: that lands while the handshake is still running wins: the handshake
        #: tears itself down instead of leaving an orphaned publish in go2rtc.
        self._closed = False
        self._cseq = 0
        self._session: Optional[str] = None
        self._lock = threading.Lock()
        self._rbuf = b""
        self._dead = threading.Event()
        self.error: Optional[str] = None
        self._last_tx = 0.0
        self._reader: Optional[threading.Thread] = None
        self.bytes_sent = 0
        self.packets_sent = 0

    # -- handshake ----------------------------------------------------------- #

    def connect(self) -> None:
        sock = socket.create_connection((self._host, self._port), timeout=self._timeout)
        sock.setsockopt(socket.IPPROTO_TCP, socket.TCP_NODELAY, 1)
        with self._lock:
            if self._closed:
                sock.close()
                raise RtspPublishError("publisher closed before it connected")
            self._sock = sock
        try:
            self._request("OPTIONS", self.url)
            self._request(
                "ANNOUNCE",
                self.url,
                {"Content-Type": "application/sdp"},
                self.sdp.encode(),
            )
            for t in self.tracks:
                hdrs = {
                    "Transport": "RTP/AVP/TCP;unicast;interleaved="
                    f"{t.channel}-{t.channel + 1};mode=record"
                }
                _st, rh, _b = self._request(
                    "SETUP", f"{self.url.rstrip('/')}/trackID={t.index}", hdrs
                )
                if self._session is None and "session" in rh:
                    self._session = rh["session"].split(";")[0].strip()
            self._request("RECORD", self.url, {"Range": "npt=0.000-"})
            if self._closed:
                raise RtspPublishError("publisher closed during the handshake")
        except Exception:
            self._close_sock()
            raise
        self._last_tx = time.monotonic()
        self._reader = threading.Thread(
            target=self._read_loop, name="aidot-rtsp-publish-rx", daemon=True
        )
        self._reader.start()

    def _request(self, method, uri, headers=None, body=b""):
        self._cseq += 1
        lines = [
            f"{method} {uri} RTSP/1.0",
            f"CSeq: {self._cseq}",
            f"User-Agent: {self._ua}",
        ]
        if self._auth:
            lines.append(f"Authorization: {self._auth}")
        if self._session:
            lines.append(f"Session: {self._session}")
        for k, v in (headers or {}).items():
            lines.append(f"{k}: {v}")
        if body:
            lines.append(f"Content-Length: {len(body)}")
        msg = ("\r\n".join(lines) + "\r\n\r\n").encode() + body
        assert self._sock is not None
        self._sock.sendall(msg)
        status, rh, rbody = self._read_response()
        if status != 200:
            raise RtspPublishError(f"{method} answered {status}")
        return status, rh, rbody

    def _read_response(self):
        """Read one RTSP response, skipping any interleaved frames before it."""
        assert self._sock is not None
        deadline = time.monotonic() + self._timeout
        while True:
            while self._rbuf[:1] == b"$":
                if len(self._rbuf) < 4:
                    break
                n = struct.unpack_from("!H", self._rbuf, 2)[0]
                if len(self._rbuf) < 4 + n:
                    break
                self._rbuf = self._rbuf[4 + n :]
            head, sep, rest = self._rbuf.partition(b"\r\n\r\n")
            if sep and not self._rbuf.startswith(b"$"):
                text = head.decode("latin-1").split("\r\n")
                m = re.match(r"RTSP/\d\.\d\s+(\d{3})", text[0])
                if not m:
                    raise RtspPublishError(f"bad RTSP status line {text[0]!r}")
                hdrs = {}
                for ln in text[1:]:
                    k, _, v = ln.partition(":")
                    hdrs[k.strip().lower()] = v.strip()
                clen = int(hdrs.get("content-length", "0") or 0)
                if len(rest) >= clen:
                    self._rbuf = rest[clen:]
                    return int(m.group(1)), hdrs, rest[:clen]
            left = deadline - time.monotonic()
            if left <= 0:
                raise RtspPublishError("timed out waiting for the RTSP server")
            self._sock.settimeout(left)
            chunk = self._sock.recv(65536)
            if not chunk:
                raise RtspPublishError("the RTSP server closed the connection")
            self._rbuf += chunk

    # -- media --------------------------------------------------------------- #

    @property
    def alive(self) -> bool:
        return self._sock is not None and not self._dead.is_set()

    def send_rtp(self, track: PublishTrack, pkt: bytes) -> None:
        frame = b"$" + bytes((track.channel,)) + struct.pack("!H", len(pkt)) + pkt
        self._send_raw(frame)
        self.packets_sent += 1
        self.bytes_sent += len(pkt)

    def keepalive_due(self, now: Optional[float] = None) -> bool:
        return ((now or time.monotonic()) - self._last_tx) >= self._keepalive_s

    def send_keepalive(self) -> None:
        """OPTIONS - go2rtc answers it and it resets the 15 s idle deadline.

        The reply is consumed by the reader thread; never block on it here.
        """
        self._cseq += 1
        lines = [f"OPTIONS {self.url} RTSP/1.0", f"CSeq: {self._cseq}"]
        if self._auth:
            lines.append(f"Authorization: {self._auth}")
        if self._session:
            lines.append(f"Session: {self._session}")
        self._send_raw(("\r\n".join(lines) + "\r\n\r\n").encode())

    def _send_raw(self, data: bytes) -> None:
        if self._dead.is_set() or self._sock is None:
            raise RtspPublishError(self.error or "publisher is closed")
        with self._lock:
            try:
                self._sock.settimeout(self._timeout)
                self._sock.sendall(data)
            except OSError as exc:
                self._mark_dead(f"send failed: {exc}")
                raise RtspPublishError(self.error) from exc
            self._last_tx = time.monotonic()

    def _read_loop(self) -> None:
        """Drain whatever the server sends; EOF or error marks us dead.

        go2rtc sends nothing but keepalive replies on a publish connection, and
        closes it when the target stream does not exist (after answering
        RECORD) or when go2rtc stops - both show up here as EOF.
        """
        sock = self._sock
        while sock is not None and not self._dead.is_set():
            try:
                r, _, _ = select.select([sock], [], [], 0.5)
                if not r:
                    continue
                data = sock.recv(65536)
            except (OSError, ValueError) as exc:
                self._mark_dead(f"receive failed: {exc}")
                return
            if not data:
                self._mark_dead(
                    "the RTSP server closed the publish (does the stream exist"
                    " in go2rtc?)"
                )
                return

    def _mark_dead(self, why: str) -> None:
        if not self._dead.is_set():
            self.error = why
            self._dead.set()

    def close(self, teardown: bool = True) -> None:
        """Idempotent and never raises: it runs in every owner's cleanup, and
        can race a handshake that is aborting on another thread (which clears
        ``_sock``) - so it works on ONE reference taken under the lock."""
        with self._lock:
            self._closed = True
            sock = self._sock
        if sock is None:
            self._mark_dead(self.error or "closed")
            return
        if teardown and not self._dead.is_set():
            try:
                self._cseq += 1
                msg = (
                    f"TEARDOWN {self.url} RTSP/1.0\r\nCSeq: {self._cseq}\r\n"
                    + (f"Session: {self._session}\r\n" if self._session else "")
                    + "\r\n"
                )
                with self._lock:
                    sock.settimeout(1.0)
                    sock.sendall(msg.encode())
            except (OSError, ValueError):
                pass
        self._mark_dead(self.error or "closed")
        self._close_sock()

    def _close_sock(self) -> None:
        with self._lock:
            sock, self._sock = self._sock, None
        if sock is not None:
            try:
                sock.shutdown(socket.SHUT_RDWR)
            except OSError:
                pass
            try:
                sock.close()
            except OSError:
                pass


# --------------------------------------------------------------------------- #
# Reordering                                                                   #
# --------------------------------------------------------------------------- #


class RtpReorderBuffer:
    """Put one track's RTP back into sequence order before it is published.

    The SDES bridge forwards packets in arrival order, and NACK retransmits
    arrive late by design. go2rtc does not reorder a publisher's packets (its
    H.264 depacketizer is marker-driven), so a late packet would corrupt the
    frame it belongs to. The serve ffmpeg reordered for us
    (``-reorder_queue_size 500 -max_delay 500000``); this is its replacement,
    with the same defaults.

    In-order packets pass straight through with no added latency. A gap holds
    later packets until it fills or the oldest held packet has waited
    ``max_delay_s``; then the gap is skipped. Packets older than what has
    already been released are dropped (``late``). A jump of more than
    ``reset_span`` resynchronises (a new sender, not a gap).
    """

    def __init__(
        self,
        max_delay_s: float = 0.5,
        max_packets: int = 500,
        reset_span: int = 3000,
        late_run_resync: int = 50,
    ):
        self.max_delay_s = max_delay_s
        self.max_packets = max_packets
        self.reset_span = reset_span
        self.late_run_resync = late_run_resync
        self._next: Optional[int] = None
        self._ssrc: Optional[int] = None
        self._late_run = 0
        self._held: dict = {}  # seq -> (item, arrival)
        self.late = 0
        self.skipped = 0
        self.resyncs = 0

    @staticmethod
    def _delta(a: int, b: int) -> int:
        d = (a - b) & 0xFFFF
        return d - 0x10000 if d >= 0x8000 else d

    def push(self, seq: int, item, arrival: float, ssrc: Optional[int] = None) -> list:
        """Queue one packet; return the items now releasable, in order.

        A new ``ssrc`` is a new sender with its own numbering: resync. (The
        SDES bridge does exactly this on one port - TUTK SFrames numbered from
        its own counter, then the camera's SRTP with random sequence numbers.)
        A long run of consecutive "late" packets can only be the same thing
        without an SSRC change, so it resyncs too.
        """
        if ssrc is not None and ssrc != self._ssrc:
            first = self._ssrc is None
            self._ssrc = ssrc
            if not first:
                return self._resync(seq, item, arrival)
        if self._next is None:
            self._next = seq
        d = self._delta(seq, self._next)
        if d < 0:
            if -d > self.reset_span:
                return self._resync(seq, item, arrival)
            self.late += 1
            self._late_run += 1
            if self._late_run >= self.late_run_resync:
                return self._resync(seq, item, arrival)
            return []
        self._late_run = 0
        if d > self.reset_span:
            return self._resync(seq, item, arrival)
        if seq in self._held:
            self.late += 1  # duplicate
            return []
        self._held[seq] = (item, arrival)
        out = self._drain()
        if len(self._held) > self.max_packets:
            out += self._skip_gap()
        return out

    def expire(self, now: float) -> list:
        """Release past a gap whose oldest held packet waited too long."""
        out: list = []
        while (
            self._held
            and now - min(a for _, a in self._held.values()) >= self.max_delay_s
        ):
            out += self._skip_gap()
        return out

    def _drain(self) -> list:
        out = []
        while self._next in self._held:
            out.append(self._held.pop(self._next)[0])
            self._next = (self._next + 1) & 0xFFFF
        return out

    def _skip_gap(self) -> list:
        nxt = self._next
        first = min(self._held, key=lambda sq: self._delta(sq, nxt))
        self.skipped += self._delta(first, nxt)
        self._next = first
        return self._drain()

    def _resync(self, seq, item, arrival) -> list:
        self.resyncs += 1
        self._late_run = 0
        out = [
            it
            for _, (it, _a) in sorted(
                self._held.items(), key=lambda kv: self._delta(kv[0], self._next)
            )
        ]
        self._held.clear()
        self._next = (seq + 1) & 0xFFFF
        return [*out, item]


# --------------------------------------------------------------------------- #
# Audio gain on A-law bytes                                                    #
# --------------------------------------------------------------------------- #


#: A-law byte -> linear sample, and the squares of those, precomputed. The
#: publish loop runs per 20 ms audio frame on every camera, so the decode and
#: the level measurement must not be per-sample Python.
_ALAW_TO_LINEAR: tuple = ()
_ALAW_SQUARES: tuple = ()


def _alaw_mean_square(payload: bytes) -> float:
    """Mean square of an A-law frame, by table lookup."""
    sq = _ALAW_SQUARES
    return sum(sq[b] for b in payload) / len(payload)


def _alaw_encode(sample: int) -> int:
    from ..g711 import linear2alaw

    return linear2alaw(sample)


def _db2amp(db: float) -> float:
    """Decibels (full scale) to a linear amplitude factor."""
    return 10.0 ** (db / 20.0)


def _alaw_to_linear(a: int) -> int:
    a ^= 0x55
    t = (a & 0x0F) << 4
    seg = (a & 0x70) >> 4
    if seg == 0:
        t += 8
    elif seg == 1:
        t += 0x108
    else:
        t = (t + 0x108) << (seg - 1)
    return t if (a & 0x80) else -t


def alaw_gain_table(gain_db: float) -> Optional[bytes]:
    """A 256-byte translate table applying ``gain_db`` to A-law, or None at 0 dB."""
    if not gain_db:
        return None
    from ..g711 import linear2alaw

    k = 10 ** (gain_db / 20.0)
    out = bytearray(256)
    for a in range(256):
        v = round(_alaw_to_linear(a) * k)
        out[a] = linear2alaw(max(-32768, min(32767, v)))
    return bytes(out)


_ALAW_TO_LINEAR = tuple(_alaw_to_linear(b) for b in range(256))
_ALAW_SQUARES = tuple(float(v) * v for v in _ALAW_TO_LINEAR)


class AlawAgc:
    """The DTLS mux's audio conditioning, applied to A-law payloads.

    The PyAV mux this publisher replaces did not send the camera's audio as it
    arrived: it decoded, ran a level tracker with a gain clamp, a noise gate
    and a tanh soft-limiter toward a target level, and re-encoded. Publishing
    raw A-law would quietly drop all of that, so the same conditioning runs
    here, reading the same environment variables:

    ``AIDOT_AUDIO_TARGET_DBFS`` (-15), ``AIDOT_AUDIO_MAXGAIN_DB`` (30),
    ``AIDOT_AUDIO_MINGAIN_DB`` (-12), ``AIDOT_AUDIO_GATE_DBFS`` (-45).
    ``AIDOT_AUDIO_AGC=0`` turns it off and sends the camera's bytes unchanged.

    The gate matters: below it the gain is scaled down quadratically rather
    than cranked toward maximum, which is what stops a quiet camera's A-law
    quantization floor being amplified into audible clicking.
    """

    def __init__(self, env=None):
        env = os.environ if env is None else env

        def _f(name, default):
            try:
                val = float(env.get(name, default))
            except (TypeError, ValueError):
                return float(default)
            # "inf"/"nan" parse as floats and then poison the gain: a
            # non-finite gain reaches math.log() when the translate table is
            # keyed and raises *inside the publish loop*, taking the stream
            # down. The per-sample path this replaced merely saturated.
            return val if math.isfinite(val) else float(default)

        self.enabled = str(env.get("AIDOT_AUDIO_AGC", "1")).strip().lower() not in (
            "0",
            "false",
            "no",
            "off",
        )
        self.target = _db2amp(_f("AIDOT_AUDIO_TARGET_DBFS", -15)) * 32767.0
        self.maxg = _db2amp(_f("AIDOT_AUDIO_MAXGAIN_DB", 30))
        self.ming = _db2amp(_f("AIDOT_AUDIO_MINGAIN_DB", -12))
        self.gate = _db2amp(_f("AIDOT_AUDIO_GATE_DBFS", -45)) * 32767.0
        self._ms = None  # smoothed mean square
        self._tables: dict = {}  # quantized gain -> 256-entry translate table

    #: Gain is quantized to this many dB before a table is built for it, so a
    #: slowly-moving level tracker reuses one table instead of rebuilding it
    #: per frame. 0.5 dB is well under audible.
    _GAIN_STEP_DB = 0.5

    def process(self, payload: bytes) -> bytes:
        """One A-law frame in, one conditioned A-law frame out.

        The conditioning is deterministic given the gain, so the gain, the
        limiter and the A-law round trip are baked into a 256-entry translate
        table and applied by ``bytes.translate`` at C speed. Measured on ARM
        before the table: 0.858 ms per 20 ms frame, 4.3% of a core per stream,
        all of it inside the publish loop.

        The level measurement below is still per-sample Python, and is now
        ~90% of what this costs; the conditioning itself is ~0.0007 ms. That
        is where to look if more headroom is ever wanted.
        """
        if not self.enabled or not payload:
            return payload
        ms = _alaw_mean_square(payload)
        self._ms = ms if self._ms is None else self._ms * 0.95 + ms * 0.05
        rms = (self._ms**0.5) + 1.0
        gain = self.target / rms
        if rms < self.gate:
            # Below the gate: fade the gain down with the square of how far
            # under it we are, instead of cranking toward maximum.
            gain *= (rms / self.gate) ** 2
        gain = max(self.ming, min(self.maxg, gain))
        return payload.translate(self._table(gain))

    def _table(self, gain: float) -> bytes:
        step = 10 ** (self._GAIN_STEP_DB / 20.0)
        key = round(math.log(max(gain, 1e-9), step))
        table = self._tables.get(key)
        if table is None:
            g = step**key
            table = bytes(
                _alaw_encode(
                    int(max(-32768, min(32767, math.tanh(x * (g / 32767.0)) * 32767.0)))
                )
                for x in _ALAW_TO_LINEAR
            )
            self._tables[key] = table
        return table


# --------------------------------------------------------------------------- #
# Popen-compatible SDES publisher                                              #
# --------------------------------------------------------------------------- #


class _PublisherLog:
    """Stands in for ``Popen.stderr``: bounded, non-blocking, EOF-at-once.

    ``_start_serve_stderr_drain`` reads lines until EOF - it gets EOF
    immediately and exits. ``SdesSession.stop`` reads the whole thing for its
    exit log, which gets the publisher's own diagnostic lines.
    """

    def __init__(self, maxlines: int = 40):
        self._lines: Deque[str] = collections.deque(maxlen=maxlines)
        self._closed = False

    def add(self, line: str) -> None:
        self._lines.append(line)

    def tail(self) -> List[str]:
        return list(self._lines)

    def readline(self, *_a) -> bytes:
        return b""

    def __iter__(self):
        return iter(())

    def read(self, *_a) -> bytes:
        if self._closed:
            return b""
        return ("\n".join(self._lines) + ("\n" if self._lines else "")).encode()

    def close(self) -> None:
        self._closed = True


class LoopbackRtpPublisher:
    """Replaces the SDES serve ffmpeg: loopback RTP in, RTSP publish out.

    Binds the loopback ports named in the serve SDP (the ports the bridge
    already sends to) **in the constructor**, so the open's "has the serve
    bound its ports" wait passes at once. The RTSP handshake runs on the worker
    thread; packets arriving meanwhile are held (bounded) so a cold open's
    first keyframe is not lost.

    Exit semantics mirror the ffmpeg it replaces: ``poll()`` is None while
    running; a publish failure or ``input_timeout_s`` without any media exits
    ``1``; ``terminate()``/``kill()`` exit negative like a signal death.
    """

    def __init__(
        self,
        serve_sdp: str,
        url: str,
        *,
        input_timeout_s: Optional[float] = None,
        audio_gain_db: float = 0.0,
        device_id: str = "?",
        policy: Optional[str] = None,
        include_audio: bool = True,
        publisher_factory=RtspPublisher,
    ):
        self.args = ["<direct-publish>", redact_url(url)]
        self.pid = os.getpid()
        self.stdout = None
        self.stdin = None
        self.stderr = _PublisherLog()
        self.returncode: Optional[int] = None
        self.device_id = device_id
        self._url = url
        self._sdp, self._tracks, announced_ports = publish_sdp_from_serve_sdp(
            serve_sdp, ("video", "audio") if include_audio else ("video",)
        )
        # Bind EVERY port the serve SDP names, announced or not, exactly as the
        # ffmpeg it replaces did: the SDES open waits for both loopback ports to
        # be bound before it signals, and the bridge sends audio regardless.
        # Media on a port that is not announced is read and discarded.
        _, _all_tracks, ports = publish_sdp_from_serve_sdp(serve_sdp)
        self._port_track: List[Optional[int]] = [
            announced_ports.index(p) if p in announced_ports else None for p in ports
        ]
        self._reorder = [RtpReorderBuffer() for _ in self._tracks]
        self._input_timeout = input_timeout_s
        self._gain = alaw_gain_table(audio_gain_db)
        pol = policy or timestamp_policy()
        self._timelines = [RtpTimeline(t.clock_rate, policy=pol) for t in self._tracks]
        self._publisher_factory = publisher_factory
        self._publisher: Optional[RtspPublisher] = None
        self._stop = threading.Event()
        self._stop_code = EXIT_TERMINATED
        self._done = threading.Event()
        self._connect_done = threading.Event()
        self.dropped_pt = 0
        self.preroll_dropped = 0
        self.last_media = 0.0
        self._aidot_stderr_tail: List[str] = []
        self._aidot_stderr_notable: List[str] = []
        self._socks: List[socket.socket] = []
        try:
            for p in ports:
                s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
                s.setsockopt(socket.SOL_SOCKET, socket.SO_RCVBUF, 1 << 20)
                s.bind(("127.0.0.1", p))
                s.setblocking(False)
                self._socks.append(s)
        except OSError:
            self._close_socks()
            raise
        self._thread = threading.Thread(
            target=self._run, name=f"aidot-direct-publish-{device_id}", daemon=True
        )
        self._thread.start()

    # -- Popen surface ------------------------------------------------------- #

    def poll(self) -> Optional[int]:
        return self.returncode

    def wait(self, timeout: Optional[float] = None) -> int:
        if not self._done.wait(timeout):
            raise subprocess.TimeoutExpired(self.args, timeout)
        assert self.returncode is not None
        return self.returncode

    def terminate(self) -> None:
        self._request_stop(EXIT_TERMINATED)

    def kill(self) -> None:
        self._request_stop(EXIT_KILLED)
        # A kill must not wait on anything: release the sockets now.
        self._done.wait(1.0)

    def send_signal(self, _sig) -> None:
        self.terminate()

    def _request_stop(self, code: int) -> None:
        if not self._stop.is_set():
            self._stop_code = code
            self._stop.set()

    # -- diagnostics --------------------------------------------------------- #

    def publish_stats(self) -> dict:
        pub = self._publisher
        return {
            "packets": pub.packets_sent if pub else 0,
            "bytes": pub.bytes_sent if pub else 0,
            "timestamp_repairs": sum(t.repairs for t in self._timelines),
            "dropped_pt": self.dropped_pt,
            "preroll_dropped": self.preroll_dropped,
            "reorder_late": sum(b.late for b in self._reorder),
            "reorder_skipped": sum(b.skipped for b in self._reorder),
            "tracks": [f"{t.kind}:{t.codec}/{t.pt}" for t in self._tracks],
        }

    def _log(self, level: int, msg: str, *args) -> None:
        text = msg % args if args else msg
        self.stderr.add(text)
        self._aidot_stderr_tail = self.stderr.tail()
        if level >= logging.WARNING:
            self._aidot_stderr_notable = [*self._aidot_stderr_notable, text][-20:]
        _LOGGER.log(level, "camera %s: direct publish: %s", self.device_id, text)

    # -- worker -------------------------------------------------------------- #

    def _run(self) -> None:
        code = EXIT_FAILED
        preroll: Deque[Tuple[int, bytes, float]] = collections.deque()
        started = time.monotonic()
        try:
            pub = self._publisher_factory(self._url, self._sdp, self._tracks)
            self._publisher = pub
            connector = threading.Thread(
                target=self._connect,
                args=(pub,),
                name="aidot-rtsp-connect",
                daemon=True,
            )
            connector.start()
            connected = False
            while not self._stop.is_set():
                if not connected:
                    if self._connect_error is not None:
                        self._log(
                            logging.WARNING,
                            "publish to %s failed: %s",
                            redact_url(self._url),
                            self._connect_error,
                        )
                        return
                    if self._connect_done.is_set():
                        # Handshake finished. Test completion, not `alive`: a
                        # publish dropped straight after RECORD is never alive
                        # here, and must still end through the check below.
                        connected = True
                        self._log(
                            logging.INFO,
                            "publishing %s to %s",
                            ", ".join(f"{t.kind} {t.codec}" for t in self._tracks),
                            redact_url(self._url),
                        )
                        while preroll:
                            idx, pkt, arr = preroll.popleft()
                            self._forward(pub, idx, pkt, arr)
                if connected and not pub.alive:
                    self._log(logging.WARNING, "%s", pub.error or "publish ended")
                    return
                r, _, _ = select.select(self._socks, [], [], 0.25)
                now = time.monotonic()
                for s in r:
                    idx = self._socks.index(s)
                    while True:  # drain: the sockets are non-blocking
                        try:
                            pkt = s.recv(65536)
                        except OSError:  # incl. BlockingIOError - drained
                            break
                        if connected:
                            self._forward(pub, idx, pkt, now)
                        else:
                            if len(preroll) >= PREROLL_MAX_PACKETS:
                                preroll.popleft()
                                self.preroll_dropped += 1
                            preroll.append((idx, pkt, now))
                if connected:
                    self._expire(pub, now)
                if connected and pub.keepalive_due(now):
                    try:
                        pub.send_keepalive()
                    except RtspPublishError:
                        continue  # the alive check above reports it
                ref = self.last_media or started
                if self._input_timeout and now - ref > self._input_timeout:
                    self._log(
                        logging.WARNING,
                        "no media from the camera for %.0f s - ending the publish",
                        now - ref,
                    )
                    return
            code = self._stop_code
        except Exception as exc:  # never let the worker die silently
            self._log(logging.WARNING, "publisher failed: %r", exc)
        finally:
            if self._stop.is_set():
                code = self._stop_code
            pub = self._publisher
            if pub is not None:
                pub.close(teardown=True)
            self._close_socks()
            stats = self.publish_stats()
            self._log(
                logging.INFO,
                "publish ended: %d packets, %d timestamp repair(s), %d dropped"
                " (payload type), %d dropped (pre-roll), %d late, %d lost",
                stats["packets"],
                stats["timestamp_repairs"],
                stats["dropped_pt"],
                stats["preroll_dropped"],
                stats["reorder_late"],
                stats["reorder_skipped"],
            )
            self.returncode = code
            self._done.set()

    _connect_error: Optional[str] = None

    def _connect(self, pub: RtspPublisher) -> None:
        try:
            pub.connect()
        except Exception as exc:
            self._connect_error = str(exc) or repr(exc)
            return
        self._connect_done.set()

    def _forward(
        self, pub: RtspPublisher, sock_idx: int, pkt: bytes, arrival: float
    ) -> None:
        """One datagram from loopback socket ``sock_idx``: reorder, then send."""
        idx = self._port_track[sock_idx]
        if idx is None:
            self.last_media = arrival  # media is flowing, just not announced
            return
        parsed = parse_rtp(pkt)
        if parsed is None:
            return
        pt, marker, in_seq, ts, payload = parsed
        ssrc = struct.unpack_from("!I", pkt, 8)[0]
        if pt != self._tracks[idx].pt:
            self.dropped_pt += 1
            return
        self.last_media = arrival
        for item in self._reorder[idx].push(
            in_seq, (marker, ts, payload, arrival), arrival, ssrc
        ):
            self._send(pub, idx, item)

    def _expire(self, pub: RtspPublisher, now: float) -> None:
        """Release packets held behind a gap that has waited long enough."""
        for idx, buf in enumerate(self._reorder):
            for item in buf.expire(now):
                self._send(pub, idx, item)

    def _send(self, pub: RtspPublisher, idx: int, item) -> None:
        marker, ts, payload, arrival = item
        track = self._tracks[idx]
        if self._gain is not None and track.codec == "PCMA":
            payload = payload.translate(self._gain)
        seq, out_ts = self._timelines[idx].stamp(ts, arrival)
        try:
            pub.send_rtp(
                track,
                build_rtp(
                    track.pt, marker, seq, out_ts, self._timelines[idx].ssrc, payload
                ),
            )
        except RtspPublishError:
            pass  # the loop's alive check ends the publish with the reason

    def _close_socks(self) -> None:
        for s in self._socks:
            try:
                s.close()
            except OSError:
                pass


# --------------------------------------------------------------------------- #
# DTLS: encoded access units -> RTP                                            #
# --------------------------------------------------------------------------- #


def split_annexb(data: bytes) -> List[bytes]:
    """NAL units of an Annex-B byte stream (start codes removed)."""
    nals: List[bytes] = []
    i, n = 0, len(data)
    starts = []
    while i + 3 <= n:
        if data[i] == 0 and data[i + 1] == 0:
            if data[i + 2] == 1:
                starts.append((i, i + 3))
                i += 3
                continue
            if i + 4 <= n and data[i + 2] == 0 and data[i + 3] == 1:
                starts.append((i, i + 4))
                i += 4
                continue
        i += 1
    if not starts:
        return [data] if data else []
    for k, (_s, body) in enumerate(starts):
        end = starts[k + 1][0] if k + 1 < len(starts) else n
        nal = data[body:end]
        # Trailing zero bytes belong to the next start code, not this NAL.
        while nal.endswith(b"\x00") and len(nal) > 1:
            nal = nal[:-1]
        if nal:
            nals.append(nal)
    return nals


def packetize_h264(access_unit: bytes, mtu: int = H264_MTU) -> List[Tuple[bytes, bool]]:
    """``(payload, marker)`` RTP payloads for one access unit (RFC 6184 mode 1).

    Single NAL unit packets where they fit, FU-A otherwise; the marker is set
    on the last packet of the access unit.
    """
    out: List[Tuple[bytes, bool]] = []
    for nal in split_annexb(access_unit):
        if len(nal) <= mtu:
            out.append((nal, False))
            continue
        hdr = nal[0]
        fu_ind = (hdr & 0xE0) | 28
        typ = hdr & 0x1F
        body = nal[1:]
        step = mtu - 2
        for off in range(0, len(body), step):
            chunk = body[off : off + step]
            start = off == 0
            end = off + step >= len(body)
            fu_hdr = (0x80 if start else 0) | (0x40 if end else 0) | typ
            out.append((bytes((fu_ind, fu_hdr)) + chunk, False))
    if out:
        out[-1] = (out[-1][0], True)
    return out


def dtls_rtp_publish_run(
    vq,
    aq,
    url: str,
    progress: list,
    stop_flag: threading.Event,
    *,
    device_id: str = "?",
    publisher_factory=RtspPublisher,
    result: Optional[dict] = None,
) -> None:
    """Publish the DTLS tap's queues to ``url``. Thread target.

    Same contract as ``_dtls_av_mux_run``: consumes ``vq`` items
    ``(annexb_bytes, ts90k, is_keyframe)`` and ``aq`` items
    ``(pcma_bytes, ts8k)``; starts on a keyframe; drops frames whose
    presentation time was already served (``is_resent_video_frame``); updates
    ``progress[0]`` on every frame written; exits on ``stop_flag`` or on a
    publish failure (recorded in ``result['error']``).
    """
    from .protocol import is_resent_video_frame

    res = result if result is not None else {}
    video = PublishTrack("video", 96, 90000, "H264", 0)
    audio = PublishTrack("audio", 8, 8000, "PCMA", 1)
    tracks = [video, audio]
    sdp = build_publish_sdp(tracks, {96: "packetization-mode=1"})
    pol = timestamp_policy()
    vtl = RtpTimeline(90000, policy=pol)
    atl = RtpTimeline(8000, policy=pol)
    # The mux this replaces conditioned the camera's audio; keep that.
    agc = AlawAgc()
    # Set before anything can return: a failed connect leaves through an early
    # return, above the try/finally that fills these in, and a caller reading
    # res["dropped_resent"] should not get a KeyError because the publish never
    # started.
    res.setdefault("skipped_pre_keyframe", 0)
    res.setdefault("dropped_resent", 0)
    res.setdefault("max_frame_gap_s", 0.0)
    res.setdefault("packets", 0)
    pub = publisher_factory(url, sdp, tracks)
    try:
        pub.connect()
    except Exception as exc:
        res["error"] = f"publish to {redact_url(url)} failed: {exc}"
        _LOGGER.warning("camera %s: DTLS direct publish: %s", device_id, res["error"])
        return
    _LOGGER.info(
        "camera %s: DTLS direct publish: H264+PCMA to %s", device_id, redact_url(url)
    )
    vstarted = False
    v0 = None
    ts_state: dict = {}
    # A viewer sees a stall as a gap between frames. One 1.63 s gap was
    # measured in a 30 min soak and never explained; report the worst gap per
    # session, and name a notable one when it happens, so a repeat can be
    # attributed rather than guessed at.
    gap_warn_s = _publish_gap_warn_s()
    last_frame = None
    max_gap = 0.0
    # A gap has three quite different causes and the warning could not tell
    # them apart: nothing arrived from the camera, what arrived was dropped
    # (the wait for a decodable keyframe, or a presentation time already
    # served), or the publish itself blocked. So measure all three, and
    # measure them FOR THIS GAP:
    #
    # * ``idle``    - last publish -> the FIRST frame to arrive after it. Not
    #   the last frame before this one: this camera family re-sends runs of
    #   already-served timestamps (40.75% of frames, bursts of up to 41, about
    #   twice a second - see ``is_resent_video_frame``), so a silence normally
    #   ENDS in a resend burst. Timing to the last arrival would read that as
    #   "frames were arriving", which is the misattribution this exists to
    #   stop.
    # * ``blocked`` - time spent inside ``send_rtp`` since the last publish.
    #   Dequeue time is not arrival time: while a send blocks, frames pile up
    #   unread and their wait is charged to nobody.
    # * the two drop counters, as DELTAS over the gap. As session totals they
    #   carry no information about the gap in front of you - the keyframe wait
    #   can only happen before the first publish, so it would print a constant
    #   from startup forever, and the resend count reaches the thousands.
    skipped_pre_keyframe = 0
    dropped_resent = 0
    first_arrival = None
    send_blocked = 0.0
    gap_skipped = 0
    gap_dropped = 0
    try:
        while not stop_flag.is_set():
            if not pub.alive:
                res["error"] = pub.error or "publish ended"
                _LOGGER.warning(
                    "camera %s: DTLS direct publish: %s", device_id, res["error"]
                )
                return
            moved = False
            while True:
                try:
                    data, ts, kf = vq.get_nowait()
                except _queue.Empty:
                    break
                moved = True
                if first_arrival is None:
                    first_arrival = time.monotonic()
                if not vstarted:
                    if not kf:
                        skipped_pre_keyframe += 1
                        gap_skipped += 1
                        continue
                    vstarted, v0 = True, ts
                if is_resent_video_frame(ts_state, ts - v0):
                    dropped_resent += 1
                    gap_dropped += 1
                    continue
                now = time.monotonic()
                seq_ts = None
                _send_started = now
                for payload, marker in packetize_h264(data):
                    seq, out_ts = vtl.stamp(ts, now)
                    seq_ts = out_ts
                    pub.send_rtp(
                        video, build_rtp(96, marker, seq, out_ts, vtl.ssrc, payload)
                    )
                _sent_in = time.monotonic() - _send_started
                if seq_ts is not None:
                    if last_frame is not None:
                        gap = now - last_frame
                        if gap > max_gap:
                            max_gap = gap
                        if gap_warn_s and gap >= gap_warn_s:
                            idle = max(0.0, (first_arrival or now) - last_frame)
                            _LOGGER.warning(
                                "camera %s: DTLS direct publish: %.2f s without a"
                                " frame to publish (queue %d; %.2f s idle waiting"
                                " for one to arrive, %.2f s inside the publish,"
                                " %d skipped waiting for a keyframe, %d dropped"
                                " as already served; largest gap so far %.2f s)",
                                device_id,
                                gap,
                                vq.qsize(),
                                idle,
                                send_blocked,
                                gap_skipped,
                                gap_dropped,
                                max_gap,
                            )
                    last_frame = now
                    progress[0] = now
                    # This frame's own send belongs to the NEXT gap: the gap
                    # above is measured to `now`, which precedes it.
                    first_arrival = None
                    send_blocked = _sent_in
                    gap_skipped = 0
                    gap_dropped = 0
            while True:
                try:
                    adata, ats = aq.get_nowait()
                except _queue.Empty:
                    break
                moved = True
                if not vstarted:
                    continue  # no audio ahead of the first picture
                seq, out_ts = atl.stamp(ats)
                _a_started = time.monotonic()
                pub.send_rtp(
                    audio,
                    build_rtp(8, False, seq, out_ts, atl.ssrc, agc.process(adata)),
                )
                # Audio shares the publisher's lock, so a blocked audio send
                # holds up the next picture just as a video one does.
                send_blocked += time.monotonic() - _a_started
            if pub.keepalive_due():
                pub.send_keepalive()
            if not moved:
                time.sleep(0.01)
    except RtspPublishError as exc:
        res["error"] = str(exc)
        _LOGGER.warning("camera %s: DTLS direct publish: %s", device_id, exc)
    finally:
        res["timestamp_repairs"] = vtl.repairs + atl.repairs
        res["packets"] = pub.packets_sent
        res["max_frame_gap_s"] = round(max_gap, 2)
        res["skipped_pre_keyframe"] = skipped_pre_keyframe
        res["dropped_resent"] = dropped_resent
        pub.close(teardown=True)
