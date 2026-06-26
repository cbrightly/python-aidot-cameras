"""Stateless camera protocol helpers, split out of client.py.

Pure(ish) functions for the camera wire protocols: STUN/ICE, SDP, sprop cache,
SCTP/DTLS mux, MQTT signaling sessions, RTP param-set extraction, etc. No
dependency on client.py state (CameraMixin, the background-task registry, the
concurrency gates, or ffmpeg helpers stay in client.py), so this module never
imports client.py -- the import edge is one-way (client -> protocol).
"""

import asyncio
import json
import logging
import os
import random
import select
import socket
import struct
import tempfile
import threading
import time
import zlib
from typing import Any, Callable, Optional

from .constants import TALK_PCM_FRAME_BYTES, TALK_PCM_RATE

_LOGGER = logging.getLogger(__name__)


def _mqtt_timestamp() -> str:
    t = time.time()
    return time.strftime("%Y-%m-%d %H:%M:%S.", time.localtime(t)) + f"{int(t * 1000) % 1000:03d}"


def _build_stun_binding_success_response(
    *,
    transaction_id: bytes,
    mapped_ip: str,
    mapped_port: int,
    mi_password: str,
    magic_cookie: bytes = b"\x21\x12\xa4\x42",
) -> bytes:
    """Build STUN Binding Success with MESSAGE-INTEGRITY and FINGERPRINT.

    Some camera firmwares keep retransmitting ICE checks when responses lack a
    valid fingerprinted STUN envelope. This helper emits:
      XOR-MAPPED-ADDRESS + MESSAGE-INTEGRITY + FINGERPRINT
    """
    import hmac as _hmac
    import hashlib as _hashlib

    ip_parts = [int(x) for x in mapped_ip.split(".")]
    xip = bytes(a ^ b for a, b in zip(struct.pack("!4B", *ip_parts), magic_cookie, strict=False))
    xport = (mapped_port ^ 0x2112) & 0xFFFF
    xma = b"\x00\x20\x00\x08\x00\x01" + struct.pack("!H", xport) + xip

    mi_attr_len = 24  # type(2)+len(2)+sha1(20)
    fp_attr_len = 8   # type(2)+len(2)+crc32(4)

    # Per RFC 5389/8445, MESSAGE-INTEGRITY is computed with length set to end
    # of MESSAGE-INTEGRITY (excluding any attributes that follow, e.g. FINGERPRINT).
    len_for_mi = len(xma) + mi_attr_len
    hdr_for_mi = b"\x01\x01" + struct.pack("!H", len_for_mi) + magic_cookie + transaction_id
    mi_val = _hmac.new(
        mi_password.encode(), hdr_for_mi + xma, _hashlib.sha1
    ).digest()
    mi_attr = b"\x00\x08\x00\x14" + mi_val

    # On-wire message length includes FINGERPRINT.
    len_with_fp = len_for_mi + fp_attr_len
    hdr_with_fp = b"\x01\x01" + struct.pack("!H", len_with_fp) + magic_cookie + transaction_id
    msg_wo_fp_attr = hdr_with_fp + xma + mi_attr
    fp_val = (zlib.crc32(msg_wo_fp_attr) ^ 0x5354554E) & 0xFFFFFFFF
    fp_attr = b"\x80\x28\x00\x04" + struct.pack("!I", fp_val)
    return msg_wo_fp_attr + fp_attr


_WEBRTC_TERMINAL_ACK_CODES = (-50002, -50015)


def _terminal_webrtc_ack(msg: dict):
    """Return ``(code, desc)`` if ``msg`` is a webrtcResp carrying a TERMINAL ack
    code, else ``None``. Pure/deterministic - unit-tested in tests/test_terminal_ack.py.

    A terminal ack means the camera refused this stream (e.g. it is already serving
    its max number of concurrent viewers). Retrying cannot help; the caller should
    surface the error instead of spending its retry budget.
    """
    if not isinstance(msg, dict):
        return None
    if (msg.get("method") or "") != "webrtcResp":
        return None
    ack = msg.get("ack")
    if not isinstance(ack, dict):
        return None
    code = ack.get("code")
    if code in _WEBRTC_TERMINAL_ACK_CODES:
        return (code, ack.get("desc") or "")
    return None


def _highport_nomination_decision(check_list, pair):
    """A000088 two-port DTLS nomination fix - return the USE-CANDIDATE decision for
    ``pair`` once more than one remote candidate is known, else ``None``.
    Pure/deterministic - unit-tested in tests/test_highport_nomination.py.

    Returns:
        ``True``  - FORCE USE-CANDIDATE: ``pair`` is the HIGHEST remote port.
        ``False`` - SUPPRESS USE-CANDIDATE: any non-highest remote port.
        ``None``  - fewer than two distinct remote ports known: do NOT override
                    aioice's own nomination (single-candidate peers untouched).

    A000088 cameras expose TWO consecutive succeeded host ICE ports ``[P, P+1]``
    and only proceed to DTLS if USE-CANDIDATE lands on the HIGHER port; aggressive
    nomination of BOTH makes them latch the lower port and withhold DTLS. They are
    also ICE-lite, so aioice may pass ``nominate=False`` - merely suppressing the
    lower port is not enough; we FORCE USE-CANDIDATE onto the highest port and off
    every other. This mirrors the validated force-high prototype: the decision is
    over ALL remote ports (not host-filtered, no consecutive requirement) - that
    breadth is essential, because a host-filtered view momentarily sees only the
    lower port and leaks USE-CANDIDATE onto it (the prototype connects ~50-87%, the
    host-filtered variant ~10%). Single-candidate peers (``< 2`` ports) are left to
    aioice; multi-candidate non-A000088 peers are covered by the live regression
    check (SDES camera + light) - never commit without it.
    """
    try:
        ports = {
            p.remote_candidate.port
            for p in check_list
            if p.remote_candidate is not None
        }
        if len(ports) < 2:
            return None
        return pair.remote_candidate.port == max(ports)
    except Exception:
        return None


def _install_highport_nomination_patch() -> bool:
    """Idempotently monkeypatch aioice so the controlling agent nominates only the
    higher of an A000088 consecutive ICE-port pair (see _highport_nomination_decision).
    Returns True if the patch is active, False if skipped (disabled, no aioice, or
    an incompatible aioice). FAIL-SAFE: any failure to install degrades to the
    unpatched upstream path - it never breaks the WebRTC connect.

    Disable with ``AIDOT_DISABLE_HIGHPORT_FIX=1`` (used to measure the baseline).
    Safe to call repeatedly and process-wide: the override self-gates on the
    consecutive-pair signature, so non-A000088 connects are untouched.
    """
    import os
    if os.environ.get("AIDOT_DISABLE_HIGHPORT_FIX"):
        return False
    try:
        from aioice import ice as _aioice
        if getattr(_aioice.Connection, "_aidot_highport_patched", False):
            return True
        _orig_build_request = _aioice.Connection.build_request

        def _build_request(self, pair, nominate):
            # Force nomination onto only the higher of an A000088 consecutive port
            # pair (works for ICE-lite remotes where aioice passes nominate=False).
            # SCOPED: acts ONLY on connections tagged `_aidot_highport` - set per
            # DTLS-camera connect in async_open_webrtc_stream. SDES cameras and all
            # non-camera devices are never tagged, so this is a strict no-op there.
            try:
                if (getattr(self, "_aidot_highport", False)
                        and getattr(self, "ice_controlling", False)):
                    decision = _highport_nomination_decision(
                        self._check_list, pair)
                    if decision is not None and decision != nominate:
                        _LOGGER.debug(
                            "highport-fix: %s USE-CANDIDATE on %s:%s "
                            "(aioice nominate=%s)",
                            "forcing" if decision else "suppressing",
                            getattr(pair.remote_candidate, "host", "?"),
                            getattr(pair.remote_candidate, "port", "?"),
                            nominate,
                        )
                        nominate = decision
            except Exception:
                pass  # never let the fix break a check
            return _orig_build_request(self, pair, nominate)

        _aioice.Connection.build_request = _build_request
        _aioice.Connection._aidot_highport_patched = True
        _LOGGER.debug("aioice nomination patched: A000088 high-port-only")
        return True
    except Exception:
        _LOGGER.debug(
            "highport nomination patch skipped (aioice incompatible)")
        return False


_CAMERA_ALARM_TYPES = {65: "motion", 66: "person"}


def _parse_alarm_event(msg: dict):
    """Return a normalized alarm dict if ``msg`` is a setDevAttrNotif carrying a live
    motion/person ``alarmType``, else ``None``. Pure/deterministic - unit-tested in
    tests/test_alarm_event.py.

    Returns: ``{"device_id": str|None, "type": "motion"|"person", "alarm_type": int}``.
    """
    if not isinstance(msg, dict):
        return None
    if (msg.get("method") or "") != "setDevAttrNotif":
        return None
    payload = msg.get("payload")
    if not isinstance(payload, dict):
        return None
    attrs = payload.get("attr")
    if not isinstance(attrs, dict):
        attrs = payload.get("props") if isinstance(payload.get("props"), dict) else {}
    raw = attrs.get("alarmType")
    if raw is None:
        return None
    try:
        code = int(raw)
    except (TypeError, ValueError):
        return None
    kind = _CAMERA_ALARM_TYPES.get(code)
    if kind is None:
        return None
    dev = msg.get("id") or payload.get("id")
    if not dev:
        src = msg.get("srcAddr") or ""
        if src.startswith("0."):
            dev = src.split(".", 1)[1]
    return {"device_id": dev, "type": kind, "alarm_type": code}


def _compress_sdp_for_camera(sdp: str) -> str:
    """Selective SDP filter matching official Java client's g.b() behaviour.

    The Leedarson firmware (KVS-derived) parses ``wPayload.offer.sdp`` with
    ``encOffer=1`` expecting a compact SDP that includes only the lines its
    ICE/DTLS/codec stack needs.  Sending a full WebRTC-stack SDP (~5 KB+
    with extmap/rtcp-fb/ssrc/msid lines) frequently causes A001064-class
    cameras to drop the MQTT session immediately on receipt - the embedded
    MQTT buffer overflows.

    Keeps: v=, o=, s=, t=, m=, c=, a=group, a=msid-semantic, a=mid, direction
    (sendrecv/recvonly/sendonly), a=ice-ufrag, a=ice-pwd, a=ice-options,
    a=fingerprint, a=setup, a=crypto, a=ssrc cname (first per section),
    a=candidate (UDP only), a=rtpmap (PCMU/PCMA/AAC/opus only on audio,
    H264/H265/RTX-apt on video), a=fmtp profile-level-id, a=sctp-port.

    Drops: a=extmap, a=rtcp-fb, a=rtcp-mux, a=rtcp-rsize, a=msid, a=ssrc
    (non-cname), a=rtcp:9, redundant rtpmap entries.
    """
    out: list = []
    seen: dict = {}
    media_type = ""
    before_m = True

    def keep(ln: str, key: str = "") -> None:
        out.append(ln + "\r\n")
        if key:
            seen[key] = "1"

    for ln in sdp.splitlines():
        if ln.startswith("m="):
            before_m = False
            media_type = ln.split(" ")[0]
            keep(ln)
            continue
        if before_m:
            if (ln.startswith("v=") or ln.startswith("o=")
                    or ln.startswith("s=") or ln.startswith("t=")
                    or ln.startswith("a=group:")
                    or ln.startswith("a=msid-semantic")):
                keep(ln)
            continue
        if ln.startswith("c="):
            keep(ln)
            continue
        if ln.startswith("a=mid:"):
            keep(ln)
            continue
        if ln.startswith("a=ssrc") and "cname" in ln:
            if seen.get(f"ssrc-cname-{media_type}") is None:
                keep(ln, f"ssrc-cname-{media_type}")
            continue
        if any(d in ln for d in ("sendrecv", "recvonly", "sendonly")):
            keep(ln)
            continue
        for ak in ("ice-ufrag", "ice-pwd", "fingerprint", "setup",
                   "ice-options", "crypto"):
            if ak in ln:
                if seen.get(ak) is None:
                    keep(ln, ak)
                break
        else:
            if "candidate" in ln and " udp " in ln.lower():
                keep(ln)
                continue
            if media_type == "m=audio":
                if any(c in ln for c in ("opus", "PCMU", "PCMA", "AAC")):
                    keep(ln)
            elif media_type == "m=video":
                if "H264/90000" in ln and seen.get("H264/90000") is None:
                    keep(ln, "H264/90000")
                    try:
                        seen["H264/90000_pt"] = ln.split(":")[1].split(" ")[0]
                    except Exception:
                        _LOGGER.debug("swallowed exception in %s", 'keep', exc_info=True)
                elif "H265/90000" in ln and seen.get("H265/90000") is None:
                    keep(ln, "H265/90000")
                    try:
                        seen["H265/90000_pt"] = ln.split(":")[1].split(" ")[0]
                    except Exception:
                        _LOGGER.debug("swallowed exception in %s", 'keep', exc_info=True)
                elif "apt=" in ln:
                    try:
                        apt = ln.split("apt=")[1].strip()
                    except Exception:
                        apt = ""
                    if apt and apt in (
                        seen.get("H264/90000_pt", ""),
                        seen.get("H265/90000_pt", ""),
                    ):
                        keep(ln)
                elif "fmtp" in ln and "profile-level-id" in ln:
                    if seen.get("profile-level") is None:
                        keep(ln, "profile-level")
            elif media_type == "m=application":
                if "sctp-port" in ln:
                    keep(ln)
    return "".join(out)


def _make_talk_audio_track(pcm_provider: Callable[[], "Optional[bytes]"]):
    """Build an aiortc MediaStreamTrack that emits viewer→camera talk audio.

    Mirrors the official app (f0.java:695): a real audio track is present on the
    PeerConnection from the start, so the camera sees a genuine sender.  The
    track emits 20 ms (160-sample) frames of signed-16-bit PCM at 8 kHz mono;
    aiortc's RTCRtpSender encodes them to PCMA (PT=8) on the wire.

    ``pcm_provider`` is called once per 20 ms frame and returns either 320 bytes
    of s16le PCM (one frame) or ``None``/short data, in which case silence is
    emitted.  Emitting silence (rather than stopping) keeps the track "present
    but quiet" - the equivalent of the app's setEnabled(false) idle state.

    Returns ``None`` if the optional ``av``/``aiortc`` deps are unavailable.
    """
    try:
        import fractions
        import av
        from aiortc.mediastreams import MediaStreamTrack
    except ImportError:
        return None

    _RATE = TALK_PCM_RATE
    _SAMPLES = TALK_PCM_FRAME_BYTES // 2  # 160 samples, 20 ms @ 8 kHz
    _SILENCE = b"\x00" * TALK_PCM_FRAME_BYTES

    class _TalkAudioTrack(MediaStreamTrack):
        kind = "audio"

        def __init__(self) -> None:
            super().__init__()
            self._timestamp = 0

        async def recv(self):
            # Pace to real time: one 20 ms frame per 20 ms.
            if self._timestamp:
                await asyncio.sleep(_SAMPLES / _RATE)
            try:
                pcm = pcm_provider()
            except Exception:
                pcm = None
            if not pcm or len(pcm) < _SAMPLES * 2:
                pcm = _SILENCE
            else:
                pcm = pcm[: _SAMPLES * 2]

            frame = av.AudioFrame(format="s16", layout="mono", samples=_SAMPLES)
            frame.planes[0].update(pcm)
            frame.sample_rate = _RATE
            frame.pts = self._timestamp
            frame.time_base = fractions.Fraction(1, _RATE)
            self._timestamp += _SAMPLES
            return frame

    return _TalkAudioTrack()


async def _webrtc_consume_video(track: Any, on_frame: Callable) -> None:
    """Receive video frames from an aiortc VideoStreamTrack and call on_frame."""
    while True:
        try:
            frame = await track.recv()
            try:
                on_frame(frame)
            except Exception:
                _LOGGER.debug("on_frame callback raised", exc_info=True)
        except Exception:
            break


def next_backoff(
    attempt: int,
    *,
    base: float,
    cap: float,
    rand: Optional[Callable[[], float]] = None,
) -> float:
    """Jittered exponential backoff (AWS-style) with a hard floor at ``base``.

    Returns the retry delay for 0-based ``attempt``: an exponential ceiling
    ``base * 2**attempt`` (capped at ``cap``), with the actual delay drawn
    uniformly in ``[base, ceiling]``. The random spread de-synchronizes
    concurrent reconnects so a fleet of cameras (or repeated failures of one)
    recovering at the same moment doesn't stampede the cloud / MQTT broker into
    a reconnect storm or rate-limit -- plain ``delay *= 2`` keeps every retrier
    in lockstep, jitter breaks the lockstep while preserving the same average
    growth.

    ``attempt`` 0 always returns exactly ``base`` (preserving the loops'
    existing minimum spacing). ``rand`` defaults to ``random.random`` and is
    injectable so the policy is unit-testable without randomness.
    """
    if rand is None:
        rand = random.random
    # Clamp the exponent: a long-lived failing camera can drive ``attempt`` high,
    # and 2**attempt overflows float64 around attempt~1024. The ceiling saturates
    # at ``cap`` long before that, so 30 doublings is always enough headroom.
    ceiling = min(cap, base * (2 ** min(max(attempt, 0), 30)))
    if ceiling <= base:
        return base
    return base + rand() * (ceiling - base)


class ReconnectPacer:
    """Reconnect-delay state machine shared by the camera reconnect loops.

    Centralizes the attempt counter, the jittered-backoff math (:func:`next_backoff`),
    and the escalate-on-failure / reset-on-healthy policy + increment ordering that
    the SDES keepalive, JPEG streaming, and DTLS serve loops would otherwise each
    re-implement.  Each loop keeps only its own liveness signal and feeds it in, so
    a loop's policy (does it escalate on no-media, or just reset on open?) is an
    explicit method call rather than hidden in copy-pasted counter bookkeeping.
    Not thread-safe; one instance per loop scope.
    """

    def __init__(
        self, base: float, cap: float,
        *, rand: Optional[Callable[[], float]] = None,
    ) -> None:
        self._base = base
        self._cap = cap
        self._rand = rand  # forwarded to next_backoff; injectable for tests
        self._attempt = 0

    def fail_delay(self) -> float:
        """Delay after a failed OPEN (no session established): the current attempt's
        backoff, then escalate - so the first failure waits exactly ``base``."""
        delay = next_backoff(self._attempt, base=self._base, cap=self._cap, rand=self._rand)
        self._attempt += 1
        return delay

    def session_end_delay(self, *, healthy: bool) -> float:
        """Delay after a session ENDED: reset to ``base`` if it delivered media
        (``healthy``), else escalate first, then return the delay."""
        self._attempt = 0 if healthy else self._attempt + 1
        return next_backoff(self._attempt, base=self._base, cap=self._cap, rand=self._rand)

    def reset(self) -> None:
        """Clear the failure count - e.g. after a successful open in a loop that has
        no end-of-session escalation (the DTLS serve loop, gated by its own spacing)."""
        self._attempt = 0


def _write_text_file(path: str, text: str) -> None:
    """Write ``text`` to ``path`` synchronously.

    Call this via ``loop.run_in_executor`` from async code so the (blocking)
    disk write happens off the event loop - Home Assistant flags any direct
    ``open()`` on the loop as a stability hazard.
    """
    with open(path, "w") as _f:
        _f.write(text)


def _make_sdp_tempfile(text: str) -> str:
    """Create a temp .sdp file holding ``text`` and return its path (off-loop)."""
    import os
    fd, path = tempfile.mkstemp(suffix=".sdp", prefix="aidot_sdes_")
    with os.fdopen(fd, "w") as _f:
        _f.write(text)
    return path


def _terminate_proc(proc) -> None:
    """Terminate a (possibly None / already-exited) subprocess without raising."""
    if proc is None:
        return
    try:
        if proc.returncode is None:
            proc.terminate()
    except Exception:
        _LOGGER.debug("swallowed exception in %s", '_terminate_proc', exc_info=True)


_XDG_CONFIG_HOME = os.environ.get("XDG_CONFIG_HOME") or os.path.join(
    os.path.expanduser("~"), ".config")


_SPROP_DIR = os.environ.get("AIDOT_SPROP_DIR") or os.path.join(
    _XDG_CONFIG_HOME, "aidot", "sprop")


def _extract_param_sets_from_rtp(pkt: bytes) -> dict:
    """Pull H.264 SPS (NAL type 7) / PPS (type 8) out of one RTP packet.

    Handles single-NAL-unit packets and STAP-A (type 24) aggregation - SPS/PPS
    are small and the camera sends them either as their own packets or in a
    STAP-A at the head of an IDR.  FU-A fragments are ignored (param sets are not
    fragmented in practice).  Returns ``{7: sps_bytes, 8: pps_bytes}`` for those
    found (each value is the NAL unit *including* its header byte).  Pure /
    unit-testable: parses the RTP header length defensively."""
    if len(pkt) < 13:
        return {}
    cc = pkt[0] & 0x0F
    ext = (pkt[0] >> 4) & 0x01
    off = 12 + 4 * cc
    if ext and len(pkt) >= off + 4:
        ext_words = int.from_bytes(pkt[off + 2:off + 4], "big")
        off += 4 + 4 * ext_words
    payload = pkt[off:]
    if not payload:
        return {}
    out: dict = {}
    ntype = payload[0] & 0x1F
    if ntype == 24:  # STAP-A: [hdr] then [ (size16)(nal) ]...
        i = 1
        while i + 2 <= len(payload):
            size = int.from_bytes(payload[i:i + 2], "big")
            i += 2
            nal = payload[i:i + size]
            i += size
            if nal:
                t = nal[0] & 0x1F
                if t in (7, 8):
                    out[t] = nal
    elif ntype in (7, 8):  # single NAL unit packet
        out[ntype] = payload
    return out


def _build_sprop(sps: bytes, pps: bytes) -> str:
    """``sprop-parameter-sets`` value: base64(SPS NAL),base64(PPS NAL)."""
    import base64 as _b64
    return _b64.b64encode(sps).decode() + "," + _b64.b64encode(pps).decode()


def _sprop_cache_path(devid: str) -> str:
    return os.path.join(_SPROP_DIR, f"{devid}.sprop")


def _load_sprop(devid: str) -> "Optional[str]":
    """Cached ``sprop-parameter-sets`` for ``devid`` (captured from a prior
    stream), or None.  Fail-safe: any error -> None (SDP omits sprop = today's
    behaviour)."""
    try:
        with open(_sprop_cache_path(devid)) as fh:
            s = fh.read().strip()
        return s or None
    except OSError:
        return None


def _save_sprop(devid: str, sprop: str) -> None:
    """Persist the ``sprop-parameter-sets`` string for ``devid`` (best-effort)."""
    try:
        os.makedirs(_SPROP_DIR, exist_ok=True)
        tmp = _sprop_cache_path(devid) + ".tmp"
        with open(tmp, "w") as fh:
            fh.write(sprop)
        os.replace(tmp, _sprop_cache_path(devid))
    except OSError as exc:
        # Surface (don't swallow): if the cache dir isn't writable the whole
        # sprop feature is silently inert.  AIDOT_SPROP_DIR can redirect it.
        _LOGGER.warning("sprop cache write failed (%s): %s - set AIDOT_SPROP_DIR "
                        "to a writable path", _SPROP_DIR, exc)


def _inject_sprop(sdp: str, devid: str) -> str:
    """Append the cached ``sprop-parameter-sets`` to the ffmpeg-input SDP's
    ``a=fmtp:96`` line so ffmpeg inits the H.264 decoder out-of-band (robust to
    in-band SPS loss).  No-op if nothing is cached yet or sprop is already
    present.  Applied at every ffmpeg-input SDP write so both the serve path and
    the snapshot/file path benefit."""
    sprop = _load_sprop(devid)
    if not sprop or "sprop-parameter-sets=" in sdp:
        return sdp
    out = []
    for ln in sdp.split("\r\n"):
        if ln.startswith("a=fmtp:96") and "sprop-parameter-sets" not in ln:
            ln = ln + ";sprop-parameter-sets=" + sprop
        out.append(ln)
    return "\r\n".join(out)


def _sdes_serve_port(url: "Optional[str]") -> "Optional[int]":
    """Extract the TCP port from an SDES serve URL (``http://127.0.0.1:PORT/x.ts``).

    Returns None if the URL is missing/malformed.  Pure (unit-testable)."""
    if not url:
        return None
    try:
        return int(url.rsplit(":", 1)[1].split("/", 1)[0])
    except (ValueError, IndexError):
        return None


class _ServeRelay:
    """Keep a public TCP serve port continuously connectable while the real
    server (ffmpeg ``-listen 1``) comes and goes on an internal port.

    Why this exists: the library advertises the public serve port to go2rtc / HA
    the moment a view starts, but ffmpeg only binds its ``-listen`` socket AFTER
    the ~16-25 s WebRTC handshake delivers the first frames - ffmpeg probes its
    input before it opens any output (verified empirically: ``ffmpeg -i pipe:0
    ... -f mpegts -listen 1`` does not bind the port until input data flows).  An
    eager pull therefore hits ``ECONNREFUSED`` and go2rtc gives up within
    ~200 ms, so the camera card stays blank on the first (cold) view.

    This relay binds the public port up front and holds it for the whole
    streaming session.  An early pull CONNECTS and waits; the relay keeps
    redialing the current internal ffmpeg port (set via :meth:`set_backend`) and,
    once ffmpeg is listening, splices bytes both ways.  The public listener
    survives ffmpeg restarts (go2rtc reconnects), so the consumer never sees a
    refused connection mid-session.
    """

    def __init__(self, public_port: int, *, host: str = "127.0.0.1",
                 dial_timeout: float = 90.0, dial_interval: float = 0.1) -> None:
        self._public_port = public_port
        self._host = host
        self._dial_timeout = dial_timeout
        self._dial_interval = dial_interval
        self._backend_port: "Optional[int]" = None
        self._listen: "Optional[socket.socket]" = None
        self._accept_thread: "Optional[threading.Thread]" = None
        self._conns: "set[socket.socket]" = set()
        self._lock = threading.Lock()
        self._closed = threading.Event()

    @property
    def port(self) -> int:
        """The bound public port (resolved after :meth:`start` if 0 was passed)."""
        return self._public_port

    def start(self) -> None:
        """Bind the public port and begin accepting.  Raises OSError on bind."""
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        s.bind((self._host, self._public_port))
        s.listen(8)
        s.settimeout(0.5)
        self._public_port = s.getsockname()[1]
        self._listen = s
        self._accept_thread = threading.Thread(
            target=self._accept_loop,
            name=f"serve-relay-{self._public_port}",
            daemon=True,
        )
        self._accept_thread.start()

    def set_backend(self, port: "Optional[int]") -> None:
        """Point the relay at the current internal ffmpeg port (None = no backend
        running yet; connected pulls wait for the next backend)."""
        with self._lock:
            self._backend_port = port

    def close(self) -> None:
        """Tear down the listener, the accept thread, and any live connections."""
        self._closed.set()
        try:
            if self._listen is not None:
                self._listen.close()
        except OSError:
            pass
        with self._lock:
            conns = list(self._conns)
            self._conns.clear()
        for s in conns:
            try:
                s.close()
            except OSError:
                pass
        t = self._accept_thread
        if t is not None:
            t.join(timeout=2.0)

    def _accept_loop(self) -> None:
        while not self._closed.is_set():
            try:
                cli, _ = self._listen.accept()  # type: ignore[union-attr]
            except TimeoutError:
                continue
            except OSError:
                break
            threading.Thread(
                target=self._handle, args=(cli,), daemon=True
            ).start()

    def _handle(self, cli: "socket.socket") -> None:
        with self._lock:
            self._conns.add(cli)
        be: "Optional[socket.socket]" = None
        try:
            be = self._dial_backend()
            if be is not None:
                self._splice(cli, be)
        finally:
            for s in (cli, be):
                if s is not None:
                    try:
                        s.close()
                    except OSError:
                        pass
            with self._lock:
                self._conns.discard(cli)

    def _dial_backend(self) -> "Optional[socket.socket]":
        """Block until the backend is set + connectable (or timeout / close)."""
        deadline = time.monotonic() + self._dial_timeout
        while not self._closed.is_set() and time.monotonic() < deadline:
            with self._lock:
                port = self._backend_port
            if port:
                try:
                    return socket.create_connection(
                        (self._host, port), timeout=2.0)
                except OSError:
                    pass
            time.sleep(self._dial_interval)
        return None

    def _splice(self, a: "socket.socket", b: "socket.socket") -> None:
        a.setblocking(True)
        b.setblocking(True)
        socks = [a, b]
        while not self._closed.is_set():
            try:
                r, _, x = select.select(socks, [], socks, 1.0)
            except (OSError, ValueError):
                return
            if x:
                return
            for s in r:
                peer = b if s is a else a
                try:
                    data = s.recv(65536)
                except OSError:
                    return
                if not data:
                    return
                try:
                    peer.sendall(data)
                except OSError:
                    return


def _rewrite_serve_port(url: "Optional[str]", new_port: int) -> "Optional[str]":
    """Return ``url`` with its host port replaced by ``new_port``.

    Used to point ffmpeg at an internal _ServeRelay backend port while the public
    port (in the advertised URL) is held by the relay.  ``http://127.0.0.1:18989/
    x.ts`` -> ``http://127.0.0.1:<new_port>/x.ts``.  Pure (unit-testable)."""
    if not url:
        return url
    try:
        scheme, rest = url.split("://", 1)
        hostport, slash, path = rest.partition("/")
        host = hostport.rsplit(":", 1)[0]
        return f"{scheme}://{host}:{new_port}{slash}{path}"
    except (ValueError, IndexError):
        return url


def _grab_free_port(host: str = "127.0.0.1") -> int:
    """Bind an ephemeral TCP port, release it, and return the number.

    There's an inherent TOCTOU window before ffmpeg rebinds it, but it's a
    loopback-only internal port and ffmpeg's launch retries the serve cycle on
    failure, so a lost race is self-healing."""
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        s.bind((host, 0))
        return s.getsockname()[1]
    finally:
        s.close()


def _tcp_table_has_established_on_port(table_text: str, port: int) -> bool:
    """True if any row in a /proc/net/tcp[6] dump is ESTABLISHED on local ``port``.

    /proc/net/tcp columns: ``sl local_address rem_address st ...`` where
    local_address is ``HEXIP:HEXPORT`` (port is upper-hex, %04X) and ``st`` is the
    connection state (``01`` == ESTABLISHED, ``0A`` == LISTEN).  A LISTEN socket
    (ffmpeg ``-listen 1`` waiting) is NOT a consumer; only an ESTABLISHED peer is.
    Pure function so the no-viewer policy is unit-testable without a live host."""
    target = f"{port:04X}"
    for line in table_text.splitlines()[1:]:  # skip header row
        cols = line.split()
        if len(cols) < 4:
            continue
        local, st = cols[1], cols[3]
        if ":" not in local:
            continue
        if local.rsplit(":", 1)[1].upper() == target and st == "01":
            return True
    return False


def _idle_release_due(present, last_consumer: float, now: float,
                      idle_secs: float) -> bool:
    """Whether a viewerless SDES keepalive should release.

    ``present``: True (a TCP consumer is connected to the serve port), False
    (none), or None (the table was unreadable - unknown).  Release ONLY when
    we're certain there's no consumer (False) AND the idle window has elapsed;
    True keeps it alive and None never releases (fail-safe on non-Linux).  Pure
    so the policy is unit-testable without a live loop."""
    if present is not False:
        return False
    return (now - last_consumer) > idle_secs


def _dtls_av_mux_run(vq, aq, out_fileobj, progress, stop_flag) -> None:
    """Mux tapped video (H.264 copy) + audio (PCMA->AAC) to ``out_fileobj`` as
    RTP-timestamped MPEG-TS.  Runs in a worker thread; the serve's ffmpeg reads
    the other end of the pipe and serves it over HTTP-listen.

    - Video is copied (no re-encode - Pi-friendly) with the camera's 90 kHz RTP
      timestamps, so DTS is clean/monotonic (fixes HA's "No dts" stutter).
    - MPEG-TS can't carry G.711 PCMA, so audio is transcoded to AAC (trivial CPU
      at 8 kHz mono).
    - ``progress[0]`` is bumped on every successful mux so the serve loop can
      detect "no consumer pulling" (the pipe fills and mux blocks -> stale).
    """
    import time as _t
    import queue as _q
    from fractions import Fraction
    try:
        import av
    except Exception as exc:  # pragma: no cover
        _LOGGER.warning("DTLS A/V mux: PyAV unavailable: %s", exc)
        return
    try:
        out = av.open(out_fileobj, "w", format="mpegts")
    except Exception as exc:
        _LOGGER.warning("DTLS A/V mux: av.open failed: %s", exc)
        return
    vs = out.add_stream("h264")
    vs.time_base = Fraction(1, 90000)
    try:
        adec = av.CodecContext.create("pcm_alaw", "r")
        adec.sample_rate = 8000
        adec.layout = "mono"
        # Resample 8 kHz PCMA -> 48 kHz AAC: 8 kHz AAC decodes but is commonly
        # silent in browser / iOS / go2rtc players; 48 kHz is universally played.
        aenc = out.add_stream("aac", rate=48000)
        aenc.layout = "mono"
        resampler = av.AudioResampler(format="fltp", layout="mono", rate=48000)
        fifo = av.AudioFifo()
        # AGC: track the audio level and adapt the gain toward a target of -15
        # dBFS - matching the official app's androidGain/iosGain = -15 - capped so
        # silent rooms aren't over-amplified.  Loud rooms read loud, quiet rooms
        # quiet, and nothing exceeds the target (a tanh limiter catches peaks).
        def _db2amp(_d):
            return 10.0 ** (_d / 20.0)
        try:
            _target = _db2amp(float(os.environ.get("AIDOT_AUDIO_TARGET_DBFS", "-15"))) * 32767.0
            _maxg = _db2amp(float(os.environ.get("AIDOT_AUDIO_MAXGAIN_DB", "30")))
            _ming = _db2amp(float(os.environ.get("AIDOT_AUDIO_MINGAIN_DB", "-12")))
            # Noise gate: below this RMS the input is (near) silence, so the AGC
            # must NOT crank toward +maxg - doing so amplifies the A-law
            # quantization floor into audible high-frequency clicking on a quiet
            # camera.  Gain is scaled down quadratically below the gate.  Set very
            # low (e.g. -120) to disable.
            _gate = _db2amp(float(os.environ.get("AIDOT_AUDIO_GATE_DBFS", "-45"))) * 32767.0
        except (ValueError, TypeError):
            _target, _maxg, _ming = _db2amp(-15) * 32767.0, _db2amp(30), _db2amp(-12)
            _gate = _db2amp(-45) * 32767.0
        _agc_ms = [None]   # smoothed mean-square (level tracker)
        try:
            import numpy as _np
        except Exception:
            _np = None
        have_audio = True
    except Exception as exc:
        _LOGGER.debug("DTLS A/V mux: audio disabled: %s", exc)
        have_audio = False
    v0 = [None]
    a_pts = [0]
    a_rtp0 = [None]      # first audio RTP timestamp (8 kHz units), for gap detection
    a_in = [0]           # 8 kHz samples emitted to the resampler so far (incl. concealed)
    vstarted = [False]

    def _flush_video():
        while True:
            try:
                data, ts, kf = vq.get_nowait()
            except _q.Empty:
                break
            if not vstarted[0]:
                if not kf:
                    continue  # begin on a keyframe so the GOP is decodable
                vstarted[0] = True
                v0[0] = ts
            pkt = av.Packet(data)
            pkt.stream = vs
            pkt.pts = pkt.dts = ts - v0[0]
            pkt.time_base = Fraction(1, 90000)
            try:
                out.mux(pkt)
                progress[0] = _t.monotonic()
            except Exception:
                _LOGGER.debug("swallowed exception in %s", '_flush_video', exc_info=True)

    def _flush_audio(drain=False):
        if not have_audio:
            try:
                while True:
                    aq.get_nowait()
            except _q.Empty:
                pass
            return
        try:
            while True:
                try:
                    data, _ts = aq.get_nowait()
                except _q.Empty:
                    break
                # Lock audio to its RTP clock.  The camera's PCMA RTP timestamp is
                # an 8 kHz sample count; on packet loss it jumps ahead of the
                # samples we've actually decoded.  Without filling that gap the
                # audio timeline silently COMPRESSES (the lost time vanishes), so
                # audio runs ahead of the RTP-timestamped video -> growing A/V
                # desync and player jitter-buffer resyncs, heard as choppiness.
                # Conceal the gap with silence (PLC) through the same stateful
                # resampler, mirroring what the video path does with its 90 kHz
                # timestamps.  Lossless streams have _gap == 0 (no-op).
                if _np is not None:
                    if a_rtp0[0] is None:
                        a_rtp0[0] = int(_ts)
                    _expected = (int(_ts) - a_rtp0[0]) & 0xFFFFFFFF
                    _gap = _expected - a_in[0]
                    if _gap < 0 or _gap > 8000 * 5:
                        a_in[0] = _expected  # wrap / stream reset -> resync, no fill
                    elif _gap >= 160:  # >= 20 ms missing: conceal with silence
                        try:
                            _sil = _np.zeros((1, int(_gap)), dtype=_np.int16)
                            _sfr = av.AudioFrame.from_ndarray(
                                _sil, format="s16", layout="mono")
                            _sfr.sample_rate = 8000
                            for _rfr in resampler.resample(_sfr):
                                fifo.write(_rfr)
                            a_in[0] += int(_gap)
                        except Exception:
                            _LOGGER.debug("swallowed exception in %s", '_flush_audio', exc_info=True)
                for fr in adec.decode(av.Packet(data)):
                    _ndec = fr.samples
                    fr.pts = None
                    if _np is not None:
                        try:
                            _x = fr.to_ndarray().astype(_np.float64)
                            # Track the level (smoothed mean-square ~0.4s) and aim
                            # the gain at the -15 dBFS target, clamped [min, max].
                            _ms = float(_np.mean(_x * _x))
                            if _agc_ms[0] is None:
                                _agc_ms[0] = _ms
                            else:
                                _agc_ms[0] = _agc_ms[0] * 0.95 + _ms * 0.05
                            _rms = (_agc_ms[0] ** 0.5) + 1.0
                            _gain = min(_maxg, max(_ming, _target / _rms))
                            # Noise gate: when the level is below the gate floor the
                            # signal is essentially silence; scale the gain down
                            # quadratically so the A-law quantization noise is not
                            # amplified into audible clicking.
                            if _rms < _gate:
                                _gain *= (_rms / _gate) ** 2
                            # tanh soft-limiter catches transient peaks past target.
                            _y = _np.tanh(_x * (_gain / 32767.0)) * 32767.0
                            _g = av.AudioFrame.from_ndarray(
                                _y.astype(_np.int16), format="s16", layout="mono")
                            _g.sample_rate = 8000
                            fr = _g
                        except Exception:
                            _LOGGER.debug("swallowed exception in %s", '_flush_audio', exc_info=True)
                    for rfr in resampler.resample(fr):  # 8k PCMA -> 48k fltp
                        fifo.write(rfr)
                    a_in[0] += _ndec
        except Exception:
            _LOGGER.debug("swallowed exception in %s", '_flush_audio', exc_info=True)
        while True:
            fr = fifo.read(1024)   # AAC wants 1024-sample frames
            if fr is None:
                # FIFO has < 1024 samples.  The audio source is continuous
                # (measured: 100% timeline coverage, no lost packets), so the
                # remainder must stay in the FIFO and be completed by the next
                # decoded packet - NOT padded with silence here.  Padding on
                # every 5 ms flush injected a micro-silence whenever the FIFO was
                # momentarily short (almost always, given ~40 ms packet spacing) -
                # that was the choppiness.  Only on the final drain do we pad the
                # last partial frame so the encoder can flush it.
                if not drain:
                    break
                s = getattr(fifo, "samples", 0)
                if s == 0:
                    break
                fr = fifo.read(s)
                if fr is None or fr.samples == 0:
                    break
                if _np is not None:
                    try:
                        arr = fr.to_ndarray()
                        pad_n = 1024 - arr.shape[1]
                        if pad_n > 0:
                            padded = _np.concatenate(
                                [arr, _np.zeros((arr.shape[0], pad_n), dtype=arr.dtype)],
                                axis=1,
                            )
                            pfr = av.AudioFrame.from_ndarray(
                                padded, format="fltp", layout="mono"
                            )
                            pfr.sample_rate = 48000
                            fr = pfr
                    except Exception:
                        break
            fr.pts = a_pts[0]
            fr.time_base = Fraction(1, 48000)
            a_pts[0] += fr.samples
            try:
                for opkt in aenc.encode(fr):
                    out.mux(opkt)
                    progress[0] = _t.monotonic()
            except Exception:
                _LOGGER.debug("swallowed exception in %s", '_flush_audio', exc_info=True)

    while not stop_flag.is_set():
        _flush_video()
        _flush_audio()
        _t.sleep(0.005)  # 5 ms - processes audio within 5 ms of arrival vs 20 ms
    _flush_video()
    _flush_audio(drain=True)  # final: pad the last partial frame so it flushes
    try:
        if have_audio:
            for opkt in aenc.encode(None):  # flush
                out.mux(opkt)
    except Exception:
        _LOGGER.debug("swallowed exception in %s", '_dtls_av_mux_run', exc_info=True)
    try:
        out.close()
    except Exception:
        _LOGGER.debug("swallowed exception in %s", '_dtls_av_mux_run', exc_info=True)


def _h264_has_keyframe(data: bytes) -> bool:
    """True if an Annex-B H.264 buffer starts a decodable GOP (contains an IDR
    or SPS NAL).  Used to begin a -c copy serve on a keyframe so ffmpeg/go2rtc
    can decode from the first byte.  NAL type 5 = IDR slice, 7 = SPS."""
    i, n = 0, len(data)
    while i < n - 4:
        if data[i] == 0 and data[i + 1] == 0:
            if data[i + 2] == 1:
                if (data[i + 3] & 0x1F) in (5, 7):
                    return True
                i += 4
                continue
            if data[i + 2] == 0 and i + 4 < n and data[i + 3] == 1:
                if (data[i + 4] & 0x1F) in (5, 7):
                    return True
                i += 5
                continue
        i += 1
    return False


def _mqtt_session_sync(
    mqtt_url: str,
    mqtt_user: str,
    mqtt_pwd: str,
    client_id: str,
    subscribe_topics: list,
    publish_items: list,
    duration: float,
    on_message=None,
    ws_path: str = "/mqtt",
    on_ready=None,
    outgoing_queue=None,
) -> tuple:
    """Synchronous paho MQTT session (runs in a thread executor).

    Returns (messages, status_dict) where:
      messages    = list of (topic, payload_str) tuples received
      status_dict = {"connected": bool, "rc": int, "rc_str": str,
                     "error": str|None, "log": [str, ...]}

    ws_path overrides the WebSocket endpoint path (default "/mqtt").
    Pass "" or "/" to try the root path.

    on_ready(status) - optional callback called after all subscribe/publish
    operations complete but before the receive loop starts.  If it blocks
    (e.g. waiting for user input) the paho background thread continues to
    buffer incoming messages.  The ``duration`` countdown starts only after
    on_ready returns, so use this hook to implement a "wait for ENTER before
    starting the capture window" pattern.
    """
    import paho.mqtt.client as _paho
    import ssl as _ssl
    import queue as _queue
    from urllib.parse import urlparse

    parsed   = urlparse(mqtt_url)
    hostname = parsed.hostname or mqtt_url
    port     = parsed.port or (8443 if parsed.scheme in ("wss", "https") else 1883)
    tls      = parsed.scheme in ("wss", "https", "mqtts")
    # ws_path parameter takes priority; fall back to URL path then "/mqtt"
    path     = ws_path if ws_path is not None else (parsed.path or "/mqtt")
    if path == "":
        path = "/"

    msg_q   = _queue.Queue()
    conn_ev = threading.Event()
    status  = {"connected": False, "rc": None, "rc_str": "", "error": None, "log": []}

    # Build client - handle paho ≥2.0 (VERSION2) and <2.0
    try:
        client = _paho.Client(
            callback_api_version=_paho.CallbackAPIVersion.VERSION2,
            client_id=client_id,
            transport="websockets",
        )
    except AttributeError:
        client = _paho.Client(client_id=client_id, transport="websockets")

    client.ws_set_options(path=path)
    if mqtt_user:
        client.username_pw_set(mqtt_user, mqtt_pwd or "")
    if tls:
        ctx = _ssl.create_default_context()
        client.tls_set_context(ctx)

    def _on_connect(c, ud, flags, reason_code, props=None):
        # paho ≥2 passes ReasonCode; paho <2 passes int
        try:
            rc = int(reason_code)
        except (TypeError, ValueError):
            rc = getattr(reason_code, "value", -1)
        status["connected"] = (rc == 0)
        status["rc"]        = rc
        status["rc_str"]    = str(reason_code)
        conn_ev.set()

    def _on_message(c, ud, msg):
        payload = (msg.payload.decode("utf-8", errors="replace")
                   if isinstance(msg.payload, (bytes, bytearray))
                   else str(msg.payload))
        msg_q.put((msg.topic, payload))

    def _on_disconnect(c, ud, disconnect_flags=None, reason_code=None, props=None):
        # If _on_connect was never fired (WebSocket upgrade failed, auth refused
        # at TCP level, etc.) signal conn_ev now so the caller doesn't time out.
        if not conn_ev.is_set():
            status["connected"] = False
            status["rc_str"]    = f"disconnect-before-connect rc={reason_code}"
            conn_ev.set()
        msg_q.put(None)   # sentinel to unblock the receive loop

    def _on_log(c, ud, level, buf):
        if len(status["log"]) < 500:
            status["log"].append(buf)
        if "assword" not in buf:
            _LOGGER.debug("paho: %s", buf)

    client.on_connect    = _on_connect
    client.on_message    = _on_message
    client.on_disconnect = _on_disconnect
    client.on_log        = _on_log

    import time as _time

    try:
        client.connect(hostname, port, keepalive=60)
    except Exception as exc:
        status["error"] = str(exc)
        _LOGGER.warning("_mqtt_session: connect() raised: %s", exc)
        return [], status

    client.loop_start()

    if not conn_ev.wait(timeout=15):
        status["error"] = f"connect timeout to {hostname}:{port}"
        _LOGGER.warning("_mqtt_session: %s", status["error"])
        client.loop_stop()
        try:
            client.disconnect()
        except Exception:
            _LOGGER.debug("swallowed exception in %s", '_on_log', exc_info=True)
        return [], status

    if not status["connected"]:
        _LOGGER.warning(
            "_mqtt_session: broker refused rc=%s (%s) for %s:%d",
            status["rc"], status["rc_str"], hostname, port,
        )
        client.loop_stop()
        try:
            client.disconnect()
        except Exception:
            _LOGGER.debug("swallowed exception in %s", '_on_log', exc_info=True)
        return [], status

    _LOGGER.info("_mqtt_session: connected to %s:%d clientId=%s", hostname, port, client_id)

    for topic in subscribe_topics:
        client.subscribe(topic)
        _LOGGER.debug("_mqtt_session: subscribed %s", topic)

    for pub_topic, pub_payload in publish_items:
        client.publish(pub_topic, pub_payload)
        _LOGGER.debug("_mqtt_session: published %s", pub_topic)

    if on_ready:
        try:
            on_ready(status)
        except Exception:
            _LOGGER.debug("swallowed exception in %s", '_on_log', exc_info=True)

    collected = []
    deadline  = _time.monotonic() + duration
    while True:
        remaining = deadline - _time.monotonic()
        if remaining <= 0:
            break
        try:
            item = msg_q.get(timeout=min(remaining, 0.1))
        except _queue.Empty:
            # Drain outgoing publish queue
            if outgoing_queue is not None:
                while True:
                    try:
                        out = outgoing_queue.get_nowait()
                    except _queue.Empty:
                        break
                    if out is None:   # stop sentinel
                        client.loop_stop()
                        try:
                            client.disconnect()
                        except Exception:
                            _LOGGER.debug("swallowed exception in %s", '_on_log', exc_info=True)
                        return collected, status
                    pub_topic, pub_payload = out
                    client.publish(pub_topic, pub_payload)
                    _LOGGER.debug("_mqtt_session: published %s", pub_topic)
            continue
        if item is None:   # disconnect sentinel
            break
        collected.append(item)
        if on_message:
            try:
                on_message(*item)
            except Exception:
                _LOGGER.debug("swallowed exception in %s", '_on_log', exc_info=True)

    client.loop_stop()
    try:
        client.disconnect()
    except Exception:
        _LOGGER.debug("swallowed exception in %s", '_mqtt_session_sync', exc_info=True)
    return collected, status


class _PersistentMqtt:
    """One long-lived paho MQTT connection reused across many publish/collect
    round-trips - matching the official app's single persistent connection
    (LDSBaseMqttServiceImpl) instead of our historical connect-per-op.

    Thread-safe: the paho network loop runs in its own background thread and
    auto-reconnects; tracked subscriptions are replayed on every (re)connect.
    ``request()`` registers a transient collector, publishes, and gathers
    matching messages for ``timeout`` WITHOUT tearing the connection down, so
    N operations cost ONE connect instead of N. The client_id is account-level,
    so exactly one of these should exist per account (per AidotClient)."""

    def __init__(self, mqtt_url, mqtt_user, mqtt_pwd, client_id, ws_path="/mqtt"):
        self._url = mqtt_url
        self._user = mqtt_user
        self._pwd = mqtt_pwd
        self._cid = client_id
        self._ws_path = ws_path
        self._client = None
        self._host = None
        self._port = None
        self._connected = threading.Event()
        self._lock = threading.Lock()
        self._subs = set()         # topics to (re)subscribe on connect
        self._collectors = []      # transient queues, each receives every msg
        self._handlers = []        # persistent on_message callbacks (e.g. a stream)
        self._started = False
        self.connects = 0          # observability: how many times we connected

    def _build(self):
        import paho.mqtt.client as _paho
        import ssl as _ssl
        from urllib.parse import urlparse
        parsed = urlparse(self._url)
        self._host = parsed.hostname or self._url
        self._port = parsed.port or (8443 if parsed.scheme in ("wss", "https") else 1883)
        tls = parsed.scheme in ("wss", "https", "mqtts")
        path = self._ws_path if self._ws_path is not None else (parsed.path or "/mqtt")
        if path == "":
            path = "/"
        try:
            c = _paho.Client(callback_api_version=_paho.CallbackAPIVersion.VERSION2,
                             client_id=self._cid, transport="websockets")
        except AttributeError:
            c = _paho.Client(client_id=self._cid, transport="websockets")
        c.ws_set_options(path=path)
        if self._user:
            c.username_pw_set(self._user, self._pwd or "")
        if tls:
            c.tls_set_context(_ssl.create_default_context())
        try:
            c.reconnect_delay_set(min_delay=1, max_delay=30)
        except Exception:
            pass
        c.on_connect = self._on_connect
        c.on_disconnect = self._on_disconnect
        c.on_message = self._on_message
        return c

    def _on_connect(self, c, ud, flags, reason_code, props=None):
        try:
            rc = int(reason_code)
        except (TypeError, ValueError):
            rc = getattr(reason_code, "value", -1)
        if rc == 0:
            self.connects += 1
            with self._lock:
                subs = list(self._subs)
            for t in subs:                 # replay subscriptions after (re)connect
                try:
                    c.subscribe(t)
                except Exception:
                    _LOGGER.debug("persistent mqtt: resubscribe %s failed", t)
            self._connected.set()
        else:
            _LOGGER.warning("persistent mqtt: broker refused rc=%s", rc)

    def _on_disconnect(self, c, ud, *args, **kwargs):
        self._connected.clear()            # paho loop auto-reconnects; subs replay on_connect

    def _on_message(self, c, ud, msg):
        payload = (msg.payload.decode("utf-8", errors="replace")
                   if isinstance(msg.payload, (bytes, bytearray)) else str(msg.payload))
        item = (msg.topic, payload)
        with self._lock:
            cols = list(self._collectors)
            handlers = list(self._handlers)
        for q in cols:
            q.put(item)
        for h in handlers:                 # persistent subscribers (stream, etc.)
            try:
                h(msg.topic, payload)
            except Exception:
                _LOGGER.debug("persistent mqtt: handler raised", exc_info=True)

    def _ensure_started_sync(self, timeout=15.0):
        with self._lock:
            if self._client is None:
                self._client = self._build()
                try:
                    self._client.connect(self._host, self._port, keepalive=60)
                except Exception as exc:
                    _LOGGER.warning("persistent mqtt: connect() raised: %s", exc)
                    self._client = None
                    return False
                self._client.loop_start()  # drives keepalive + auto-reconnect
                self._started = True
        return self._connected.wait(timeout)

    def _subscribe_sync(self, topics):
        new = []
        with self._lock:
            for t in topics:
                if t not in self._subs:
                    self._subs.add(t)
                    new.append(t)
            c = self._client
        if c is not None and self._connected.is_set():
            for t in new:
                try:
                    c.subscribe(t)
                except Exception:
                    _LOGGER.debug("persistent mqtt: subscribe %s failed", t)

    def _request_sync(self, publish_items, subscribe_topics, match, timeout):
        import queue as _queue
        import time as _time
        if not self._ensure_started_sync():
            return [], {"error": "persistent mqtt connect timeout"}
        self._subscribe_sync(subscribe_topics or [])
        q = _queue.Queue()
        with self._lock:
            self._collectors.append(q)
            c = self._client   # snapshot: a concurrent close() may null self._client
        collected = []
        try:
            if c is None:
                return [], {"error": "persistent mqtt closed"}
            try:
                for pt, pp in (publish_items or []):
                    c.publish(pt, pp)
            except Exception as exc:
                return [], {"error": f"persistent mqtt publish failed: {exc}"}
            deadline = _time.monotonic() + timeout
            while True:
                remaining = deadline - _time.monotonic()
                if remaining <= 0:
                    break
                try:
                    item = q.get(timeout=min(remaining, 0.1))
                except _queue.Empty:
                    continue
                if match is None or match(item[0], item[1]):
                    collected.append(item)
        finally:
            with self._lock:
                try:
                    self._collectors.remove(q)
                except ValueError:
                    pass
        return collected, {"error": None}

    async def request(self, publish_items, subscribe_topics=None, match=None, timeout=5.0):
        """Publish ``publish_items`` and collect matching messages for ``timeout``
        on the shared persistent connection (one connect for the account, reused).
        Returns (messages, status)."""
        import functools
        loop = asyncio.get_running_loop()
        return await loop.run_in_executor(None, functools.partial(
            self._request_sync, publish_items, subscribe_topics, match, timeout))

    async def ensure_connected(self, timeout=15.0):
        import functools
        loop = asyncio.get_running_loop()
        return await loop.run_in_executor(
            None, functools.partial(self._ensure_started_sync, timeout))

    # --- persistent subscriber API (for the stream signaling, Phase 2) -------- #
    def add_handler(self, callback):
        """Register a persistent on_message callback ``callback(topic, payload)``
        that receives every message for the connection's lifetime (until removed).
        Use for a long-lived consumer like an open stream's signaling handler."""
        with self._lock:
            self._handlers.append(callback)
        return callback

    def remove_handler(self, callback):
        with self._lock:
            try:
                self._handlers.remove(callback)
            except ValueError:
                pass

    async def subscribe(self, topics):
        """Ensure the connection is up and subscribe ``topics`` (tracked for replay)."""
        import functools
        loop = asyncio.get_running_loop()
        await loop.run_in_executor(None, self._ensure_started_sync, 15.0)
        await loop.run_in_executor(None, functools.partial(self._subscribe_sync, topics))

    async def publish(self, topic, payload):
        """Publish on the shared connection (ensures it's up first)."""
        import functools

        def _pub():
            if not self._ensure_started_sync():
                return False
            try:
                self._client.publish(topic, payload)
                return True
            except Exception:
                return False
        loop = asyncio.get_running_loop()
        return await loop.run_in_executor(None, functools.partial(_pub))

    def close(self):
        c = self._client
        self._client = None
        self._started = False
        self._connected.clear()
        if c is not None:
            try:
                c.loop_stop()
            except Exception:
                pass
            try:
                c.disconnect()
            except Exception:
                pass


async def _mqtt_session(
    mqtt_url: str,
    mqtt_user: str,
    mqtt_pwd: str,
    client_id: str,
    subscribe_topics: list,
    publish_items: list,
    duration: float,
    on_message=None,
    ws_path: str = "/mqtt",
    on_ready=None,
) -> list:
    """Async wrapper: runs _mqtt_session_sync in a thread executor.

    Returns list of (topic, payload_str) tuples.
    """
    messages, status = await _mqtt_session_with_status(
        mqtt_url, mqtt_user, mqtt_pwd, client_id,
        subscribe_topics, publish_items, duration, on_message, ws_path, on_ready,
    )
    if status.get("error"):
        _LOGGER.warning("_mqtt_session failed: %s", status["error"])
    return messages


async def _mqtt_session_with_status(
    mqtt_url: str,
    mqtt_user: str,
    mqtt_pwd: str,
    client_id: str,
    subscribe_topics: list,
    publish_items: list,
    duration: float,
    on_message=None,
    ws_path: str = "/mqtt",
    on_ready=None,
) -> tuple:
    """Like _mqtt_session but also returns the status dict for diagnostics."""
    import functools
    loop = asyncio.get_running_loop()
    fn = functools.partial(
        _mqtt_session_sync,
        mqtt_url, mqtt_user, mqtt_pwd, client_id,
        subscribe_topics, publish_items, duration, on_message, ws_path, on_ready,
    )
    return await loop.run_in_executor(None, fn)


async def _mqtt_get_playback_server_info(
    mqtt_url: str,
    mqtt_user: str,
    mqtt_pwd: str,
    device_id: str,
    client_id: str,
    timeout: float = 15.0,
) -> Optional[dict]:
    """Publish getPlaybackServerInfoReq and return the payload dict, or None.

    MQTT topics from IConstants.java / MqttManage.java:
      publish : iot/v1/s/{userId}/{service}/{method}
      subscribe: iot/v1/cb/{deviceId}/#
    Response arrives on iot/v1/c/{userId}/PlayBack/getPlaybackServerInfoResp
    (or on the device callback topic).
    """
    user_id   = mqtt_user or "0"
    seq       = str(random.randint(100000, 999999))
    pub_topic = f"iot/v1/s/{user_id}/PlayBack/getPlaybackServerInfoReq"
    payload   = json.dumps({
        "method":  "getPlaybackServerInfoReq",
        "service": "PlayBack",
        "devId":   device_id,
        "srcAddr": f"0.{user_id}",
        "seq":     seq,
        "tst":     int(time.time() * 1000),
        "payload": {},
    })

    result_holder: list = []

    def _check(topic, raw):
        try:
            body   = json.loads(raw)
            method = body.get("method", "")
            if "PlaybackServerInfo" not in method and "getPlaybackServer" not in method:
                return
            pl = body.get("payload") or body.get("data") or {}
            if pl.get("serverIP") or pl.get("serverIp"):
                pl["serverIP"] = pl.get("serverIP") or pl.get("serverIp")
                result_holder.append(pl)
        except Exception:
            _LOGGER.debug("swallowed exception in %s", '_check', exc_info=True)

    await _mqtt_session(
        mqtt_url, mqtt_user, mqtt_pwd, client_id,
        subscribe_topics=[
            f"iot/v1/cb/{device_id}/#",
            f"iot/v1/c/{user_id}/#",
        ],
        publish_items=[(pub_topic, payload)],
        duration=timeout,
        on_message=_check,
    )
    return result_holder[0] if result_holder else None


async def _mqtt_listen(
    mqtt_url: str,
    mqtt_user: str,
    mqtt_pwd: str,
    client_id: str,
    device_id: str,
    duration: float = 60.0,
    on_message=None,
) -> list:
    """Subscribe to all device/user MQTT topics and collect messages for *duration* seconds.

    Returns a list of (topic, payload_str) tuples.
    *on_message(topic, payload_str)* is called for each message as it arrives.
    """
    user_id = mqtt_user or "0"
    return await _mqtt_session(
        mqtt_url, mqtt_user, mqtt_pwd, client_id,
        subscribe_topics=[
            f"iot/v1/cb/{device_id}/#",
            f"iot/v1/c/{user_id}/#",
            f"lds/v1/cb/{device_id}/#",
            f"lds/v1/c/{user_id}/#",
            f"iot/v1/s/{user_id}/#",
        ],
        publish_items=[],
        duration=duration,
        on_message=on_message,
    )


def _ip_looks_ascii_garbled(ip_str) -> bool:
    """True if ``ip_str`` is an AiDot-cloud ASCII-encoded IP, not a real one.

    The cloud sometimes stores a LAN IP's raw ASCII bytes as the octets - e.g.
    "192." -> bytes 49,57,50,46 -> the bogus dotted-quad "49.57.50.46".  Detect
    by checking all four octets are printable digit/dot ASCII that decode to a
    private/loopback prefix.  Used to keep such values out of both
    ``self._ip_address`` and the role-reversal synthetic-candidate source so we
    never STUN-probe a nonexistent address.
    """
    try:
        _parts = [int(p) for p in str(ip_str).split('.')]
    except Exception:
        return False
    if len(_parts) != 4 or not all(32 <= p <= 126 for p in _parts):
        return False
    if not all(chr(p) in '0123456789.' for p in _parts):
        return False
    _decoded = "".join(chr(p) for p in _parts)
    return any(_decoded.startswith(pfx)
               for pfx in ("192.", "10.", "172.", "169.", "127."))


# --- SDP transform helpers (lifted from _async_open_webrtc_stream_impl) ---

def _sdp_transport(sdp: str, kind: str) -> str:
    for line in sdp.splitlines():
        if line.startswith(f"m={kind} "):
            parts = line.split()
            return parts[2] if len(parts) > 2 else "?"
    return "absent"


def _upgrade_sctp(sdp: str) -> str:
    """Convert aiortc pre-RFC-8841 SCTP section to RFC 8841 format.

    aiortc generates the legacy format:
        m=application PORT DTLS/SCTP 5000
        a=sctpmap:5000 webrtc-datachannel 65535
        a=max-message-size:65536

    RFC 8841 (and cameras / modern browsers) expect:
        m=application 9 UDP/DTLS/SCTP webrtc-datachannel
        a=sctp-port:5000
        a=max-message-size:65536   ← required by RFC 8841 §4.3.1
    """
    import re as _re
    out = []
    for line in _re.split(r'\r?\n', sdp):
        if _re.match(r'^m=application \d+ DTLS/SCTP \d+$', line):
            out.append('m=application 9 UDP/DTLS/SCTP webrtc-datachannel')
        elif line.startswith('a=sctpmap:'):
            out.append('a=sctp-port:5000')
        else:
            out.append(line)   # includes a=max-message-size (keep as-is)
    return '\r\n'.join(out)


def _normalize_bundle_ice_credentials(sdp: str) -> str:
    """Unify all m-section ICE credentials to match the BUNDLE master (mid:0).

    RFC 8843 §7.1.3 requires all bundled m-sections to carry the same
    ice-ufrag and ice-pwd.  aiortc generates a separate ICETransport per
    transceiver, giving each a unique credential pair.  Cameras that
    validate this requirement silently reject offers with mismatched
    credentials.  We overwrite every m-section's credentials with those
    of the first m-section (the BUNDLE master, mid:0).

    This is safe because: after BUNDLE negotiation succeeds, aiortc uses
    only mid:0's ICETransport for all media; the camera's ICE checks go
    exclusively to mid:0, whose credentials remain unchanged.
    """
    import re as _re
    lines = _re.split(r'\r?\n', sdp)
    master_ufrag: str | None = None
    master_pwd:   str | None = None
    in_msection = False
    for ln in lines:
        if ln.startswith('m='):
            in_msection = True
        if in_msection:
            if ln.startswith('a=ice-ufrag:') and master_ufrag is None:
                master_ufrag = ln
            if ln.startswith('a=ice-pwd:') and master_pwd is None:
                master_pwd = ln
        if master_ufrag and master_pwd:
            break
    if not (master_ufrag and master_pwd):
        return sdp   # no ICE credentials found; leave SDP unchanged
    result = []
    for ln in lines:
        if ln.startswith('a=ice-ufrag:'):
            result.append(master_ufrag)
        elif ln.startswith('a=ice-pwd:'):
            result.append(master_pwd)
        else:
            result.append(ln)
    return '\r\n'.join(result)


def _reorder_m_section_ice_attrs(sdp: str) -> str:
    """Move transport attrs (ice-ufrag/pwd, fingerprint, setup) before candidates.

    aiortc places a=ice-ufrag, a=ice-pwd, a=fingerprint, and a=setup at
    the END of each m-section, after all a=candidate lines.  Camera
    firmware parsers (and Chrome/libwebrtc) expect these transport
    attributes to appear BEFORE the first a=candidate line.  A linear
    parser that encounters candidates before seeing ice-ufrag/pwd cannot
    validate them and silently rejects the offer - producing no webrtcResp.

    This function moves those four lines to immediately before the first
    a=candidate: in every m-section that contains candidates.  All other
    attribute ordering is preserved; no content is added or removed.
    """
    import re as _re
    _TRANSPORT = ('a=ice-ufrag:', 'a=ice-pwd:', 'a=fingerprint:', 'a=setup:')
    lines = _re.split(r'\r?\n', sdp)
    sections: list[list[str]] = []
    current: list[str] = []
    for ln in lines:
        if ln.startswith('m=') and current:
            sections.append(current)
            current = [ln]
        else:
            current.append(ln)
    if current:
        sections.append(current)
    result: list[str] = []
    for sec in sections:
        first_cand = next(
            (i for i, ln in enumerate(sec) if ln.startswith('a=candidate:')),
            None,
        )
        if first_cand is None:
            result.extend(sec)
            continue
        # Collect transport lines that appear at-or-after first candidate
        transport_after: list[str] = []
        kept: list[str] = []
        for ln in sec[first_cand:]:
            if any(ln.startswith(p) for p in _TRANSPORT):
                transport_after.append(ln)
            else:
                kept.append(ln)
        result.extend(sec[:first_cand])
        result.extend(transport_after)
        result.extend(kept)
    return '\r\n'.join(result)


def _filter_sdp_candidates(sdp: str) -> str:
    """Remove ICE candidates that are unreachable from remote cameras.

    Strips Docker-bridge (172.17.x), CGNAT/Tailscale (100.x.x.x), and
    all IPv6 addresses from the SDP.  These addresses are only reachable
    within the local host or VPN and waste ICE checking time; they also
    cause ICE-lite cameras to echo them back verbatim as their own
    candidates, polluting the remote candidate list.

    LAN (192.168.x / 10.x / 172.16-31 non-Docker), srflx, and TURN
    relay candidates are kept - they represent paths a remote camera
    can actually use.
    """
    import re as _re
    out = []
    for line in _re.split(r'\r?\n', sdp):
        if line.startswith('a=candidate:'):
            # Skip Docker bridge 172.17.x
            if _re.search(r'\b172\.17\.', line):
                continue
            # Skip CGNAT / Tailscale 100.x.x.x
            if _re.search(r'\b100\.\d+\.\d+\.\d+\b', line):
                continue
            # Skip IPv6 candidates (any colon-containing IP field)
            # Format: "a=candidate:... IP6-addr port ..."
            parts = line.split()
            # parts[4] is the IP address in standard candidate line
            if len(parts) > 4 and ':' in parts[4]:
                continue
        out.append(line)
    return '\r\n'.join(out)


def _dedup_bundle_candidates(sdp: str) -> str:
    """Remove a=candidate lines from non-BUNDLE-master m-sections.

    In a BUNDLE-d offer all m-sections share one ICE transport.
    Candidates only need to appear in the first m-section (mid:0,
    the BUNDLE master); listing them in mid:1 and mid:2 as well
    bloats the SDP by ~800-1200 bytes and can push the total
    webrtcReq MQTT payload over the camera's receive-buffer limit
    (~9-10 KB for A000088), causing a quickConn reset.  Stripping
    duplicate candidates from non-master sections is safe because
    the camera uses a single ICE transport for all BUNDLE'd media.
    """
    import re as _re_dc2
    lines = _re_dc2.split(r'\r?\n', sdp)
    sections: list[list[str]] = []
    current: list[str] = []
    for ln in lines:
        if ln.startswith('m=') and current:
            sections.append(current)
            current = [ln]
        else:
            current.append(ln)
    if current:
        sections.append(current)
    result: list[str] = []
    for i, sec in enumerate(sections):
        if i == 0:
            result.extend(sec)   # keep all in BUNDLE master (first m-section)
        else:
            result.extend(ln for ln in sec if not ln.startswith('a=candidate:'))
    return '\r\n'.join(result)
