"""SDES-SRTP stream-open state machine, split out of client.py.

``CameraMixin._open_sdes_stream`` is the ~3.6k-line SDES open path (isDTLS=='0'
cameras: battery / PTZ).  It lives here as a mixin to keep client.py readable;
behaviour-preserving (the method body is unchanged except for a lazy import that
breaks the client<->sdes_open cycle).  CameraMixin inherits this mixin, so
``self`` is a full CameraMixin at runtime.
"""

import asyncio
import json
import logging
import os
import random
import time
from typing import Callable, Optional  # noqa: F401 - method annotations

from ..exceptions import AidotCameraBusy
from .constants import (
    _LIVE_PLAY_NOT_READY,
    SDES_SPEAKERSTART_DELAY,
    stun_server_uris,
)
from .models import VideoFrame  # noqa: F401 - forward-ref annotation
from .sdes import SdesSession
from .protocol import (
    AVIO_HDR_LEN,
    REMB_TARGET_BPS,
    AvioResponseRouter,
    build_remb,
    _build_sprop,
    parse_avio_response,
    _build_stun_binding_success_response,
    _extract_param_sets_from_rtp,
    _grab_free_port,
    _inject_sprop,
    _load_sprop,
    _make_sdp_tempfile,
    _rewrite_serve_port,
    _save_sprop,
    _write_text_file,
)

_LOGGER = logging.getLogger(__name__)


#: What we ask the kernel for on a media receive socket.  The kernel clamps to
#: ``net.core.rmem_max`` (4 MB on Home Assistant OS), and doubles what it grants
#: for bookkeeping, so asking for more than the cap is harmless.
_MEDIA_RCVBUF_BYTES = 4 * 1024 * 1024


def _sctp_abort_chunk() -> bytes:
    """An SCTP ABORT chunk (RFC 4960 s3.3.7): type 6, T=0, no parameters.

    Sent at teardown so the camera learns the association is over rather than
    timing it out. Measured 2026-08-07: reopening 2s after a close is refused
    with -50002 (session-exceed) while 8s is fine, and we were the only one of
    the three implementations saying nothing - the official app disposes its
    data channel and our own DTLS path (aiortc) sends this same chunk.

    T=0 because we hold the peer's verification tag and send it in the common
    header; the T bit is for an endpoint reflecting a tag it does not have.
    """
    import struct as _st_ab
    return _st_ab.pack("!BBH", 6, 0, 4)


def _dispatch_sctp_avio(responses, payload) -> bool:
    """Offer an inbound SCTP DATA payload to the AVIO response router.

    This is where the camera's replies actually arrive on SDES: an encrypted
    SCTP DATA chunk (PPID 53) on the same channel we send LIVING, the heartbeat
    and SPEAKERSTART on - not the TUTK-audio framing the audio forward path
    watches. Wiring only the latter is why a camera that answered SPEAKERSTART
    in moments looked silent.

    True if it answered something we were waiting for. Never raises and never
    blocks: it runs inline on the bridge receive loop, where a bad frame must
    not be able to take the media path down with it.
    """
    if responses is None or not payload:
        return False
    answered = False
    try:
        view = memoryview(payload)
        while len(view) >= AVIO_HDR_LEN:
            frame = parse_avio_response(bytes(view))
            if frame is None:
                break
            if responses.dispatch(bytes(view)):
                answered = True
            # One chunk can carry more than one frame: 5377 declares a 12-byte
            # payload and has been seen arriving in a 140-byte chunk.  Reading
            # only the first would lose a reply batched behind a notify, which
            # presents as the camera intermittently not answering.
            view = view[AVIO_HDR_LEN + len(frame.payload):]
    except Exception:
        return answered
    return answered


def _widen_media_rcvbuf(sock, kind: str, device_id: str = "?") -> int:
    """Ask for a large receive buffer on a camera media socket.

    These sockets ran on the OS default, which is 208 KB on Home Assistant OS.
    A keyframe from an A001064 is 146-190 KB and arrives as one burst of ~130
    packets, so a single keyframe nearly fills that buffer: any delay in the
    reader - the GIL while another camera's bridge runs, an ffmpeg write
    blocking - and the kernel drops the tail of the burst.  ffmpeg then reports
    ``RTP: missed N packets`` and backs up its input queue.

    Returns the buffer the kernel actually granted (it reports double what it
    reserves), or 0 if the option could not be set.  Best-effort by design: a
    platform that refuses the option must not stop a camera from streaming.
    """
    try:
        import socket as _sock_rb

        sock.setsockopt(_sock_rb.SOL_SOCKET, _sock_rb.SO_RCVBUF,
                        _MEDIA_RCVBUF_BYTES)
        got = sock.getsockopt(_sock_rb.SOL_SOCKET, _sock_rb.SO_RCVBUF)
    except Exception:
        _LOGGER.debug("camera %s: could not widen the %s receive buffer",
                      device_id, kind, exc_info=True)
        return 0
    _LOGGER.debug("camera %s: %s receive buffer %d bytes (asked %d)",
                  device_id, kind, got, _MEDIA_RCVBUF_BYTES)
    return got


def _start_serve_stderr_drain(proc, *, maxlines: int = 40) -> None:
    """Drain a serve ffmpeg's stderr continuously into a bounded tail on the proc.

    The serve is spawned with ``stderr=PIPE`` but the bridge loop only polls the
    process and reads its RTP sockets - it never reads stderr.  An un-drained
    ``PIPE`` fills its ~64KB kernel buffer and then blocks ffmpeg on its next
    stderr write, stalling the serve; and when ffmpeg exits non-zero the reason is
    otherwise lost (the RTSP-push ANNOUNCE error in particular).  Read it on a
    daemon thread that ends at EOF when the process exits, keeping the last
    ``maxlines`` lines on ``proc._aidot_stderr_tail`` for the exit logger.
    """
    import collections
    import threading

    tail = collections.deque(maxlen=maxlines)
    proc._aidot_stderr_tail = tail
    if proc.stderr is None:
        return

    def _drain() -> None:
        try:
            for _line in iter(proc.stderr.readline, b""):
                tail.append(_line.decode("utf-8", "replace").rstrip())
        except Exception:
            pass

    threading.Thread(
        target=_drain, daemon=True, name="aidot-sdes-serve-stderr"
    ).start()


# Strong references to detached per-session helper tasks, so the event loop
# cannot garbage-collect one mid-flight.  Entries remove themselves on done.
_SDES_BACKGROUND_TASKS: set = set()


# Payload types the ffmpeg SDP advertises per media line, and their alternates.
# The SDP offers both because the camera picks one per session.
# Video payloads the cameras negotiate: 96=H.264, 97/98=H.265 (two variants).
_SDP_VIDEO_PTS = (96, 97, 98)
_SDP_AUDIO_PTS = (0, 8)

# Which payload type OUR ffmpeg SDP carries for each video codec.  The template
# writes 96=H.264 and 97=H.265, so an answer that negotiates H.265 narrows to 97
# even when the camera numbers that codec differently on the wire - it is our own
# SDP being rewritten, not the camera's.
_SDP_VIDEO_PT_BY_CODEC = {"H264": 96, "H265": 97}
_SDP_CODEC_BY_VIDEO_PT = {pt: codec for codec, pt in _SDP_VIDEO_PT_BY_CODEC.items()}


def describe_video_profile(pt) -> str:
    """Name the video profile a session negotiated, for the log.

    An A001064 was measured serving two profiles for identical requests - H264
    1280x720 at 2.5-4.0 Mbps and H265 2560x1440 at ~1.1 Mbps - varying per
    session with nothing on our side asking for either.  What selects it is
    unknown, and it is unknown because nothing recorded it: bitrate figures
    gathered before 2026-08-07 cannot be compared, since the codec that produced
    each one was never written down.

    Instrumentation, not a fix.  It asserts no cause.

    Deliberately limited to the payload type and its codec name, which is what
    the bridge knows when the first video packet lands.  Frame dimensions would
    mean parsing H264/H265 parameter sets out of the stream - far more than the
    question needs, and on the camera measured codec and resolution moved
    together in 11 of 11 sessions, so the codec stratifies it today.

    Never raises: this runs on the media path, and nothing here may be able to
    break an otherwise healthy stream.  An unmapped payload type is reported as
    ``unknown``, which is a finding worth having rather than a reason to skip
    the record.
    """
    return f"pt={pt} codec={_SDP_CODEC_BY_VIDEO_PT.get(pt) or 'unknown'}"

# How long to wait for the FIRST media of a session before launching the serve.
# Nothing useful can happen before then: the payload types are unknown, so the SDP
# cannot be narrowed, and ffmpeg would bind the wrong depacketizers.  Sized to the
# documented cold-start window for a battery camera (25-70s).  Measured: a cold
# session started media at ~21s, which the previous 15s deadline missed entirely -
# it launched blind with BOTH payload types still unknown, which is what left the
# audio line advertising PCMU on a PCMA camera.
_FIRST_MEDIA_WAIT_S = 75.0

# How long to wait for the camera's webrtcResp before parsing it for the ICE
# credentials the nomination needs.  The STUN window ahead of it closes on a
# fixed schedule, and the answer lands about 2.4 s AFTER it does (measured on an
# A001513: webrtcReq -> answer 2.9 s), so the harvest below used to give the
# answer a single event-loop cycle and almost always read an empty string.
# Everything downstream then behaved as if the camera had never answered.
# Bounded so a camera that truly never answers still falls through to the
# existing no-answer/DTLS-fallback path on its own schedule.
_PRE_LAUNCH_ANSWER_WAIT_S = 8.0

# Extra time for the audio payload type once video is known.  Measured on an
# A001513: audio follows video by 40-70ms, because the camera answers BUNDLE and
# both share one 5-tuple - so audio is never "late" and this only absorbs jitter.
# Kept tight so a camera that genuinely sends no audio barely delays the picture.
_AUDIO_PT_GRACE_S = 1.0


def video_pt_from_answer_sdp(sdp_text: str) -> Optional[int]:
    """Which video payload type to narrow to, taken from the camera's answer SDP.

    This is the fallback for the case where no video RTP packet arrives before
    the serve launches.  Without it the ffmpeg SDP keeps advertising both codecs,
    and :func:`narrow_sdp_payload_types` documents what that costs: ffmpeg binds
    its depacketizer to the FIRST payload type and silently discards the rest,
    and the RTSP-push ANNOUNCE carries a parameterless H.265 stream that go2rtc
    rejects - so the publisher never attaches and every viewer gets a 404.

    The answer already states which codec the camera agreed to send, so this is a
    negotiated fact rather than a guess.  Only the *first* video m-line is read,
    and only ``a=rtpmap`` lines inside that section, so a payload number reused in
    the audio section cannot leak into the answer.

    Returns the payload type **our** template uses for that codec (96/97) - the
    camera's own numbering need not agree - or None when the answer names no video
    codec we write, which leaves the existing behaviour untouched.
    """
    pts: list = []
    rtpmap: dict = {}
    section = None
    for raw in (sdp_text or "").splitlines():
        line = raw.strip()
        if line.startswith("m="):
            if section == "video":
                break  # the first video section has been read in full
            section = "video" if line.startswith("m=video") else "other"
            if section == "video":
                # "m=video <port> <proto> <pt> [<pt> ...]"
                pts = line.split()[3:]
            continue
        if section == "video" and line.startswith("a=rtpmap:"):
            body = line[len("a=rtpmap:"):]
            pt, _, enc = body.partition(" ")
            if pt and enc:
                rtpmap[pt.strip()] = enc.strip().split("/", 1)[0].upper()
    for pt in pts:
        codec = rtpmap.get(pt)
        if codec in _SDP_VIDEO_PT_BY_CODEC:
            return _SDP_VIDEO_PT_BY_CODEC[codec]
    return None


#: Video payload types the SDES answer template advertises (H264, H265).
_SDES_ANSWER_VIDEO_PTS = (96, 97)


def _resolve_sdes_video_pt() -> Optional[int]:
    """EXPERIMENTAL (opt-in, default off): pin the OFFER to one video codec.

    The offer sent in webrtcReq advertises BOTH 96 (H264) and 97 (H265) and
    expresses no preference, so the camera chooses which to send in its answer.
    Measured on an A001064 across eleven sessions in one afternoon it chose H264
    nine times and H265 twice for an otherwise identical request, and the codec
    it chose determined the resolution (H264 -> 1280x720, H265 -> 2560x1440, 11
    of 11).  A consumer that cannot decode a sudden 2560x1440 H265 stream, or
    cannot absorb the bitrate change either way, has no means today of
    preventing the flip.  Pinning removes the choice: measured 2026-08-07, an
    offer pinned to 96 produced h264 1280x720 in 4 of 4 sessions.

    It is the OFFER that matters, not the answer.  Traced live with every status
    line printed: this path sends webrtcReq carrying our offer and then reports
    "Using camera's video SRTP key from answer" - the camera answers, we do not.
    An earlier version of this pinned the answer builder instead and changed
    nothing at all, while the arms still came out looking like it had worked.

    **Do not set this to 97.**  An H265-only offer returned NO VIDEO - audio
    only, no video stream in the recording - in 3 of 3 interleaved rounds
    against 3 of 3 successes for 96 in the same run.  The efficient H265 profile
    is real and reproducible, but only when BOTH codecs are offered; narrowing
    to it removes the option rather than selecting it.

    Left unset this returns None and the offer is byte-identical to today.  The
    SDES offer path is shared by every SDES camera, and this project's CHANGELOG
    records fleet-wide blackouts caused by changes to shared paths, so the
    default has to be inert.  Anything unparseable, or a payload type the
    template does not advertise, also returns None: narrowing to a payload type
    the camera was never offered would leave it nothing to send.
    """
    raw = (os.environ.get("AIDOT_SDES_VIDEO_PT", "") or "").strip()
    if not raw.isdigit():
        return None
    pt = int(raw)
    return pt if pt in _SDES_ANSWER_VIDEO_PTS else None


def narrow_sdp_payload_types(sdp_text: str, keep_video=None, keep_audio=None) -> str:
    """Rewrite an SDP to advertise a single payload type per media line.

    ffmpeg binds each RTP depacketizer to the FIRST payload type on the m-line and
    silently discards packets carrying any other one.  The SDP written for the
    bridge lists both candidates per line (``m=video ... 96 97`` for H.264/H.265,
    ``m=audio ... 0 8`` for PCMU/PCMA) because which one the camera uses varies per
    session, so whichever it actually sends has to be promoted before launch.

    Getting this wrong on the VIDEO line costs the picture.  Getting it wrong on the
    AUDIO line costs the picture too, and less obviously: the mpegts mux withholds
    its PAT/PMT until every mapped stream has produced a packet, so an audio stream
    whose packets are all discarded means the consumer receives zero bytes - video
    included - while every other signal looks healthy.

    ``keep_video`` / ``keep_audio`` of None leaves that line alone.

    Handles any payload list on the m-line (``96 97``, ``96 97 98``, ...): the
    kept line advertises ONLY the kept payload and the rtpmap/fmtp lines of
    every dropped payload are removed.  The old implementation replaced the
    literal ``" 96 97"``, so the day the SDP template grew a third video
    payload (98, the second H.265 variant) narrowing silently stopped working
    for it - and an un-narrowed multi-codec SDP makes the RTSP-push ANNOUNCE
    carry a parameterless H.265 stream that go2rtc rejects with 400.
    """
    drop: set = set()
    kept = []
    for line in sdp_text.splitlines(keepends=True):
        stripped = line.lstrip()
        m_kind = keep = None
        if stripped.startswith("m=video"):
            m_kind, keep = "video", keep_video
        elif stripped.startswith("m=audio"):
            m_kind, keep = "audio", keep_audio
        if m_kind is not None and keep is not None:
            head, _, pts = stripped.partition(" RTP/")
            proto, _, pt_list = pts.partition(" ")
            listed = pt_list.split()
            if str(keep) in listed:
                drop.update(p for p in listed if p != str(keep))
                line = (line[: len(line) - len(stripped)]
                        + f"{head} RTP/{proto} {keep}"
                        + ("\r\n" if line.endswith("\r\n") else "\n"))
        elif any(
            stripped.startswith(f"a={attr}:{pt}{sep}")
            for pt in drop
            for attr, sep in (("rtpmap", " "), ("fmtp", " "), ("fmtp", ";"))
        ):
            continue
        kept.append(line)
    return "".join(kept)


async def _sdes_await_answer_or_terminal(
    answer_fut, terminal_error_fut, timeout: float, _status=None
):
    """Await the camera's SDES answer, but give up at once on a terminal ack.

    A terminal webrtcResp ack (-50002 max-streams / -50015 SD-cap) means the
    camera REFUSED this stream; the whole point of classifying it as terminal
    is that neither waiting nor retrying can help.  The SDES path used to watch
    only ``answer_fut``, so a refusal cost the full answer budget and then a
    pointless DTLS-fallback offer on top.

    Raises ``AidotCameraBusy`` the moment the refusal lands, ``TimeoutError``
    if neither arrives in ``timeout`` (the caller's existing no-answer path).
    """
    if terminal_error_fut is None:
        return await asyncio.wait_for(answer_fut, timeout=timeout)

    _waits = {answer_fut, terminal_error_fut}
    _done, _pending = await asyncio.wait(
        _waits, timeout=timeout, return_when=asyncio.FIRST_COMPLETED
    )
    if terminal_error_fut in _done:
        code, desc = terminal_error_fut.result()
        if _status is not None:
            _status(f"camera refused: ack {code} {desc} - terminal, not retrying")
        raise AidotCameraBusy(code, desc)
    if answer_fut in _done:
        return answer_fut.result()
    # Timed out.  ``answer_fut`` MUST be cancelled here, exactly as the
    # ``asyncio.wait_for(answer_fut, ...)`` this replaced did.  The MQTT handler
    # routes a late webrtcResp to ``second_answer_fut`` only ``if
    # answer_fut.done()`` (webrtc_open.py), and the late-ICE-credential recovery
    # reads ``second_answer_fut``.  Leaving it pending swallows the late answer,
    # so USE-CANDIDATE is never sent, the camera stays in ICE "Checking" and the
    # view shows a black frame with no media - the 0.12.16 failure mode.
    #
    # ``terminal_error_fut`` is deliberately NOT cancelled: it is checked again
    # after the open returns (webrtc_open.py) to surface a refusal that landed
    # during the fallback.
    answer_fut.cancel()
    # Builtin TimeoutError (asyncio.TimeoutError is an alias since 3.11); this
    # is what the caller's `except TimeoutError` no-answer path already catches,
    # which is what triggers the DTLS fallback.
    raise TimeoutError()


def _sdes_echo_wait_timeout(skip_liveplay: bool) -> float:
    """Seconds to block on the camera's webrtcReq echo before proceeding.

    The echo only ever arrives for role-reversal models (e.g. A001064), which
    need the resulting webrtcResp and run with ``skip_liveplay`` False (they are
    hard-excluded from sdes_fast_liveplay) - they keep the full 2.0s wait.  For
    A001513-class cameras (``skip_liveplay`` True, the default) the echo never
    arrives and the webrtcResp-building branch is dead/redundant, so don't block
    on it (saves ~2s of cold-start dead time)."""
    return 0.0 if skip_liveplay else 2.0


# ffmpeg returns AVERROR(EPIPE) = -32 when its output consumer disappears, and a
# process exit status is truncated to an unsigned byte: -32 & 0xFF = 224.
_FFMPEG_EXIT_EPIPE = 224


def _classify_ffmpeg_exit(rc: int, teardown_requested: bool) -> int:
    """Log level for the bridge observe loop's "ffmpeg exited" line.

    Teardown here is SIGTERM-first with a SIGKILL fallback (SdesSession.stop(),
    the cold-open _reap(), the key-restart proc replace, and the DTLS-fallback
    abort). A battery camera's ffmpeg cannot exit promptly on a dead UDP input,
    so a normal locally-initiated stop routinely ends in a signal death (rc < 0,
    e.g. -9 SIGKILL / -15 SIGTERM) - expected, and should not warn.  Any other
    non-zero exit - a signal death with no teardown in flight, or a positive
    ffmpeg error code even during teardown - is unexpected and stays loud.
    Pure function so the policy is unit-testable without a live bridge thread.
    """
    if rc < 0 and teardown_requested:
        return logging.DEBUG
    if rc == _FFMPEG_EXIT_EPIPE:
        # Broken pipe: the consumer went away. That is the NORMAL end of a
        # `-listen 1` serve - go2rtc disconnects and ffmpeg exits - so warning
        # about it trains the reader to ignore real failures. A process exit
        # status is an unsigned byte, so AVERROR(EPIPE) = -32 surfaces as 224.
        return logging.DEBUG
    return logging.WARNING


def _bridge_should_break(rc, teardown_requested: bool) -> bool:
    """Whether the bridge observe loop should end on this poll() result.

    rc is the held proc's poll() result (None while it is still running).
    Ending the loop is only correct when ffmpeg has actually exited AND no
    locally-initiated teardown is in flight for it: a non-None rc seen while
    teardown_requested is True is the key-restart window (or any other
    flagged local kill) - the OLD proc's exit, not a reason to stop.  That
    window always resolves promptly one of two ways, so skipping the break
    here cannot hang the bridge thread: either the key-restart repoints
    _proc_holder[0] at the live new proc (next poll() is None again), or a
    genuine teardown closes the loopback sockets, which makes the loop's
    select() raise and exit via its own except-break.  Pure function so the
    policy is unit-testable without a live bridge thread.
    """
    return rc is not None and not teardown_requested


# Cap on peer-reflexive candidates learned from inbound probes.  A camera has
# one working path; a handful of source addresses is normal churn (NAT rebind,
# audio vs video socket), anything beyond that is noise and must not be allowed
# to grow the nomination set without bound.
_MAX_PRFLX_CANDS = 4


def _record_peer_reflexive(known, discovered, observed, is_self=None):
    """Learn the address a STUN probe actually arrived from.

    ICE calls this a peer-reflexive candidate (RFC 8445 s7.3.1.3): the camera
    is reachable at the source of its own connectivity check whether or not it
    ever advertised that address.  We were nominating only what the answer SDP
    listed, which fails whenever the listed address is not reachable from here
    - a camera on a foreign subnet advertises a private candidate we have no
    route to, the nomination goes into a black hole, and the session sits in
    ICE "Checking" while the camera's probes keep arriving from an address we
    never nominate.  Observed on the A001064 PTZ while it was on a separate
    192.168.100.0/24: it advertised 192.168.100.13 as its only candidate.

    Returns the new discovered list, or ``discovered`` unchanged when there is
    nothing to learn.  Callers must REBIND rather than mutate in place - the
    bridge thread iterates this list and must never see it change under it.

    Pure function so the policy is unit-testable without a camera.
    """
    if not observed:
        return discovered
    _ip, _port = observed
    if not _ip or not _port:
        return discovered
    if is_self is not None and is_self(_ip):
        return discovered
    if observed in known or observed in discovered:
        return discovered
    if len(discovered) >= _MAX_PRFLX_CANDS:
        return discovered
    return [*discovered, observed]


class _SdesOpenMixin:
    async def _open_sdes_stream(self, **kwargs) -> "SdesSession":
        """Allocate-and-hand-off wrapper around _open_sdes_stream_impl.

        The impl reserves two UDP sockets, starts a bridge thread, launches
        ffmpeg and writes a temp SDP, then hands them to the returned
        SdesSession (whose stop() releases them).  If the cold open is cancelled
        mid-handshake (25-70s) - or raises before that hand-off - none of that
        has an owner yet, so a plain call leaks the fds, the thread and a /tmp
        file on every cancelled attempt (eventually "Too many open files").
        The impl registers each resource on an ExitStack as it is allocated;
        close the stack unless the impl actually returned a session.
        """
        from contextlib import ExitStack

        _cleanup = ExitStack()
        _ok = False
        try:
            _session = await self._open_sdes_stream_impl(_cleanup=_cleanup, **kwargs)
            _ok = True
            return _session
        finally:
            if not _ok:
                # Closing the reservation sockets also unblocks the bridge
                # thread's recv so it exits; ffmpeg is killed and the SDP file
                # unlinked.  LIFO, and every callback swallows so this never
                # masks the original CancelledError/exception.
                _cleanup.close()

    async def _open_sdes_stream_impl(
        self,
        *,
        _cleanup=None,
        peer_id: str,
        user_id: str,
        device_id: str,
        outgoing_q,
        answer_fut,
        camera_offer_fut,
        webrtc_req_echo_fut=None,
        loop,
        timeout: float,
        output_path: Optional[str],
        max_seconds: Optional[float] = None,
        _status=None,
        mqtt_fut=None,
        liveplay_echo_ev=None,
        liveplay_resp_fut=None,
        numeric_uid_raw: Optional[str] = None,
        dtls_fallback_ok: bool = True,
        second_answer_fut=None,
        ice_config: "Optional[dict]" = None,
        camera_reconnect_ev=None,
        sdes_answer_timeout: Optional[float] = None,
        rtsp_push_url: Optional[str] = None,
        talk: bool = False,
        ice_cands_seen=None,
        # So the SDES answer-wait can abandon at once when the camera says
        # -50002/-50015 instead of waiting out its budget and then paying for a
        # DTLS-fallback offer that cannot succeed.
        terminal_error_fut=None,
    ) -> "SdesSession":
        """SDES-SRTP streaming path using a hand-crafted SDP offer and ffmpeg.

        SDES cameras negotiate SRTP keys inline in the SDP (``a=crypto:`` lines)
        rather than via a DTLS handshake.  aiortc does not support SDES-SRTP, so
        this path sends a manually constructed SDP offer, waits for the camera's
        SDP answer, writes it to a temp file, and launches ffmpeg to receive and
        record the SRTP stream.
        """
        from .client import CameraMixin, _build_sdes_serve_cmd, _ffmpeg_path, _spawn_bg  # lazy: break client<->sdes_open cycle
        import base64
        import subprocess

        # Stop the MQTT signaling thread on ANY abnormal exit from this impl.
        # The explicit `outgoing_q.put_nowait(None)` calls further down cover the
        # paths that existed when they were written, but an exception raised
        # before one of them is reached (AidotCameraBusy on a terminal ack, or a
        # CancelledError mid-handshake) skipped the sentinel entirely and left
        # `_mqtt_session_sync` running in an executor thread for its full 3600s.
        # Those orphans all reconnect with the same stable account clientId, so
        # they take the session away from later opens and signaling for other
        # cameras on the account stops being delivered until HA restarts.
        # The wrapper only closes this stack when the impl did NOT return a
        # session, so a live session is unaffected.  A duplicate sentinel on the
        # paths that already send one is harmless - the reader exits on the first.
        if outgoing_q is not None:
            def _stop_mqtt_thread() -> None:
                try:
                    outgoing_q.put_nowait(None)
                except Exception:
                    _LOGGER.debug("camera %s: could not signal MQTT thread to exit",
                                  getattr(self, "device_id", "?"), exc_info=True)
            _cleanup.callback(_stop_mqtt_thread)

        # SDES path: fast_connect's wait-skips / TURN-strip destabilise the SCTP
        # handshake (session churns ~every 60-90s -> live view drops to snapshot),
        # so the SDES path always uses the full, stable handshake.
        _fast_connect = False

        user_id = user_id or str(self.user_id)

        # Models confirmed (2026-05-02) to send TUTK-framed data instead of
        # standard SRTP: camera announces an ASCII-printable fake SDES key and
        # sends packets with byte0=0xC8 (TUTK audio SFrame header) and an SSRC
        # that differs from the one advertised in the SDP.  For these models:
        # (a) ffmpeg SDP uses plain RTP/AVP (no SRTP decryption attempt), and
        # (b) the bridge thread detects TUTK SFrame headers (0xC8=audio,
        #     0xC9=video), strips the 12-byte TUTK header, synthesizes a
        #     standard RTP header from the TUTK timestamp+SSRC fields, and
        #     forwards the reassembled plain-RTP packet to ffmpeg.
        # Substring-matched, NOT equality: a revision suffix ("LK.IPC.A001513-1",
        # the way A000088-1 exists on the DTLS side) is the same firmware and needs
        # the same framing.  An exact match silently read such a camera as a plain
        # SRTP model, so ffmpeg tried to decrypt TUTK frames with the announced
        # fake key and the bridge never stripped the TUTK header - a session that
        # negotiates and delivers nothing decodable.
        _model_id = getattr(getattr(self, "info", None), "model_id", None) or ""
        _use_plain_rtp = any(m in _model_id for m in self._PLAIN_RTP_MODELS)
        # powerType/p2pCache - IpcServiceImpl.java:B() returns 2 for battery
        # models; 1 for wired.  Derived from is_battery_camera (see live_power_type)
        # so the wire value and the battery guards cannot drift apart.  All tested
        # cameras report p2pCache=2 in their setDevAttrNotif device attributes.
        # App parity: LivePlayPaylodBean declares these as INT, not String - send
        # ints so a strict camera JSON parser accepts the livePlayReq (battery cams
        # appear stricter; a rejected livePlayReq leaves the cam un-armed).
        _live_power_type = self.live_power_type
        _live_p2p_cache = 2

        webrtc_req_topic = f"iot/v1/s/{user_id}/IPC/webrtcReq"

        def _seq() -> str:
            return f"ap{random.randint(1000000, 9999999)}"

        # Register a resource with the wrapper's cleanup stack so it is released
        # if the cold open is cancelled/raises before the SdesSession takes
        # ownership.  Each step swallows so ExitStack.close() can never mask the
        # original CancelledError/exception.  No-op if called without a stack.
        def _cl(fn, *a):
            if _cleanup is None:
                return
            def _run():
                try:
                    fn(*a)
                except Exception:
                    _LOGGER.debug(
                        "camera %s: swallowed sdes-open cleanup step",
                        getattr(self, "device_id", "?"), exc_info=True,
                    )
            _cleanup.callback(_run)

        def _reap(p):
            # This is a locally-initiated kill (cold open cancelled/raised
            # before hand-off) - flag it so the bridge observe loop treats the
            # resulting signal death as expected, not a WARNING-worthy crash.
            # Set first (before the kill/reap below), matching the other
            # kill sites, so the bridge thread can never observe the exit
            # code with the flag still False.
            try:
                _teardown_holder[0] = True
            except Exception:
                pass
            try:
                p.kill()
            except Exception:
                pass
            try:
                p.poll()   # reap the killed child so it does not linger as a zombie
            except Exception:
                pass

        # --- Allocate UDP ports and determine local IP ---------------------- #
        import socket as _socket

        _audio_sock = _socket.socket(_socket.AF_INET, _socket.SOCK_DGRAM)
        _widen_media_rcvbuf(_audio_sock, "audio", getattr(self, "device_id", "?"))
        _audio_sock.bind(("0.0.0.0", 0))
        audio_port = _audio_sock.getsockname()[1]
        _cl(_audio_sock.close)   # also unblocks the bridge thread's recv on cleanup

        _video_sock = _socket.socket(_socket.AF_INET, _socket.SOCK_DGRAM)
        _widen_media_rcvbuf(_video_sock, "video", getattr(self, "device_id", "?"))
        _video_sock.bind(("0.0.0.0", 0))
        video_port = _video_sock.getsockname()[1]
        _cl(_video_sock.close)

        # Use the outbound interface toward 8.8.8.8 to find our local IP.
        # connect() on a UDP socket does not send any packet.
        with _socket.socket(_socket.AF_INET, _socket.SOCK_DGRAM) as _s:
            _s.connect(("8.8.8.8", 80))
            local_ip = _s.getsockname()[0]

        # Determine server-reflexive (public) IP from cached getServerUrlConfig.
        # The Arnoo broker records our outbound IP in the "ip" field; this is the
        # same address that aiortc discovers via STUN and exposes as srflx
        # candidates.  Adding it to the SDES offer lets cameras that cannot route
        # to our LAN IP (WAN cameras, different subnet) send STUN probes to our
        # public address, which the router NATs to our reservation sockets.
        _public_ip: Optional[str] = None
        try:
            _raw_srv = (self._smarthome_auth or {}).get("raw") or {}
            _cand_pub = str(_raw_srv.get("ip") or "").strip()
            if _cand_pub and _cand_pub != local_ip:
                # Accept any valid IPv4 that differs from our LAN address.
                _p = _cand_pub.split(".")
                if len(_p) == 4 and all(x.isdigit() and 0 <= int(x) <= 255 for x in _p):
                    _public_ip = _cand_pub
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_open_sdes_stream', exc_info=True)

        # Build TURN server list for _sdes_ice_server_list from ice_config if
        # available.  The camera's ICE agent uses these to gather its own relay
        # candidates; a relay path is the last resort when direct and srflx
        # connectivity both fail (e.g. symmetric NAT or strict firewall).
        _sdes_turn_entries: list = []
        try:
            if ice_config:
                # Unwrap common envelope shapes (mirrors DTLS path normalisation).
                _ic = ice_config
                for _k in ("data", "payload", "result"):
                    if isinstance(_ic, dict) and _k in _ic and isinstance(_ic[_k], dict):
                        _ic = _ic[_k]
                        break
                # Arnoo format: {app: [{uris, id, token}], dev: [...]}
                for _sect in ("app", "dev"):
                    for _entry in (_ic.get(_sect) or []):
                        _uris = _entry.get("uris") or _entry.get("Uris") or []
                        _user = (_entry.get("id") or _entry.get("Username")
                                 or _entry.get("username") or "")
                        _cred = (_entry.get("token") or _entry.get("Password")
                                 or _entry.get("password") or "")
                        if any("turn:" in str(u) for u in _uris):
                            _sdes_turn_entries.append({
                                "Uris":     _uris,
                                "Username": _user,
                                "Password": str(_cred),
                            })
                # W3C format: {iceServers: [{urls, username, credential}]}
                for _entry in (_ic.get("iceServers") or []):
                    _uris = _entry.get("urls") or _entry.get("uris") or []
                    if isinstance(_uris, str):
                        _uris = [_uris]
                    if any("turn:" in str(u) for u in _uris):
                        _sdes_turn_entries.append({
                            "Uris":     _uris,
                            "Username": _entry.get("username") or "",
                            "Password": _entry.get("credential") or "",
                        })
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_open_sdes_stream', exc_info=True)

        # --- TURN relay allocation helper ------------------------------------ #
        # Defined BEFORE the offer so relay IP/port can be embedded in the
        # offer's c= and m= lines.  Pure-SDES cameras (no ICE) read the OFFER's
        # c= address and stream SRTP there directly - if we put the relay address
        # in the offer, the camera's SRTP reaches us through port-restricted NAT.
        # sock -> (relay_ip, relay_port, realm, nonce, t_host, t_port, key, user).
        # All eight fields are required: CreatePermission has to re-authenticate
        # with USERNAME/REALM/NONCE and the long-term-credential key, so any path
        # that stores a shorter tuple silently disables the relay permission.
        _relay_addrs: dict = {}

        def _turn_allocate_udp(_ta_sock, _ta_host, _ta_port, _ta_user, _ta_pass):
            """RFC 5766 TURN relay allocation with long-term credential auth.
            Returns (relay_ip, relay_port, realm, nonce) or None on failure."""
            import hashlib as _ha
            import hmac as _hm
            import struct as _st_ta
            import select as _sl_ta
            import time as _tm_ta

            _MAGIC_TA = b'\x21\x12\xa4\x42'

            def _a(_t, _v):
                _p = (-len(_v)) % 4
                return _st_ta.pack('!HH', _t, len(_v)) + _v + b'\x00' * _p

            def _stun_message_integrity(_k, _m):
                # Patch Length to include the MI attribute (4 hdr + 20 digest = 24)
                _patched = _m[:2] + _st_ta.pack('!H', len(_m) - 20 + 24) + _m[4:]
                return _hm.new(_k, _patched, _ha.sha1).digest()

            # Step 1: unauthenticated Allocate -> get REALM and NONCE from 401
            _tid1 = os.urandom(12)
            _b1 = _a(0x0019, b'\x11\x00\x00\x00')  # REQUESTED-TRANSPORT = UDP(17), RFC 5766 section 14.7 protocol in MSB
            _r1 = b'\x00\x03' + _st_ta.pack('!H', len(_b1)) + _MAGIC_TA + _tid1 + _b1
            try:
                _ta_sock.sendto(_r1, (_ta_host, _ta_port))
            except Exception:
                return None
            # Loop until we get a response whose TID matches our request (discard
            # stale packets from previous exchanges that may linger in the buffer).
            _rsp1 = None
            _dl1 = _tm_ta.time() + 2.0
            while _tm_ta.time() < _dl1:
                _rem = _dl1 - _tm_ta.time()
                _rs1, _, _ = _sl_ta.select([_ta_sock], [], [], min(_rem, 0.5))
                if not _rs1:
                    continue
                try:
                    _cand1, _ = _ta_sock.recvfrom(2048)
                except OSError:
                    break
                if len(_cand1) >= 20 and _cand1[8:20] == _tid1:
                    _rsp1 = _cand1
                    break
            if _rsp1 is None:
                return None
            _realm_ta = _nonce_ta = b''
            _o = 20
            while _o + 4 <= len(_rsp1):
                _at, _al = _st_ta.unpack_from('!HH', _rsp1, _o)
                _av = _rsp1[_o + 4:_o + 4 + _al]
                _o += 4 + _al + (-_al % 4)
                if _at == 0x0014:
                    _realm_ta = _av
                elif _at == 0x0015:
                    _nonce_ta = _av
            if not _realm_ta or not _nonce_ta:
                _LOGGER.debug("TURN alloc step1: no realm/nonce in response type=%s",
                              _rsp1[:2].hex())
                return None
            _LOGGER.debug("TURN alloc step1 challenge: realm=%r nonce_len=%d",
                          _realm_ta.decode(errors='replace'), len(_nonce_ta))

            # Step 2: authenticated Allocate
            _tid2 = os.urandom(12)
            _key_ta = _ha.md5(_ta_user + b':' + _realm_ta + b':' + _ta_pass).digest()
            _b2 = (
                _a(0x0006, _ta_user)                  # USERNAME
                + _a(0x0014, _realm_ta)               # REALM
                + _a(0x0015, _nonce_ta)               # NONCE
                + _a(0x0019, b'\x11\x00\x00\x00')     # REQUESTED-TRANSPORT = UDP, RFC 5766 section 14.7 protocol in MSB
            )
            _h2 = b'\x00\x03' + _st_ta.pack('!H', len(_b2) + 24) + _MAGIC_TA + _tid2
            _b2 += _a(0x0008, _stun_message_integrity(_key_ta, _h2 + _b2))  # MESSAGE-INTEGRITY
            _r2 = b'\x00\x03' + _st_ta.pack('!H', len(_b2)) + _MAGIC_TA + _tid2 + _b2
            try:
                _ta_sock.sendto(_r2, (_ta_host, _ta_port))
            except Exception:
                return None
            # Same TID-matching loop - if step 1's 401 was still in the buffer,
            # a bare recvfrom would consume it and report failure on a good alloc.
            _rsp2 = None
            _dl2 = _tm_ta.time() + 2.0
            while _tm_ta.time() < _dl2:
                _rem = _dl2 - _tm_ta.time()
                _rs2, _, _ = _sl_ta.select([_ta_sock], [], [], min(_rem, 0.5))
                if not _rs2:
                    continue
                try:
                    _cand2, _ = _ta_sock.recvfrom(2048)
                except OSError:
                    break
                if len(_cand2) >= 20 and _cand2[8:20] == _tid2:
                    _rsp2 = _cand2
                    break
            if _rsp2 is None:
                return None
            if _rsp2[:2] != b'\x01\x03':  # Allocate Success = 0x0103
                # Parse ERROR-CODE (0x0009) for diagnostics
                _ec2 = 0
                _o_ec = 20
                while _o_ec + 4 <= len(_rsp2):
                    _at_ec, _al_ec = _st_ta.unpack_from('!HH', _rsp2, _o_ec)
                    _av_ec = _rsp2[_o_ec + 4:_o_ec + 4 + _al_ec]
                    _o_ec += 4 + _al_ec + (-_al_ec % 4)
                    if _at_ec == 0x0009 and _al_ec >= 4:
                        _ec2 = (_av_ec[2] & 0x07) * 100 + _av_ec[3]
                _LOGGER.debug(
                    "TURN alloc step2 error_code=%d realm=%r response_type=%s",
                    _ec2, _realm_ta.decode(errors='replace'), _rsp2[:2].hex(),
                )
                return None

            # Parse XOR-RELAYED-ADDRESS (0x0016)
            _o = 20
            while _o + 4 <= len(_rsp2):
                _at, _al = _st_ta.unpack_from('!HH', _rsp2, _o)
                _av = _rsp2[_o + 4:_o + 4 + _al]
                _o += 4 + _al + (-_al % 4)
                if _at == 0x0016 and _al >= 8:  # XOR-RELAYED-ADDRESS
                    _xp = _st_ta.unpack_from('!H', _av, 2)[0] ^ 0x2112
                    _xb = bytes(a ^ b for a, b in zip(_av[4:8], _MAGIC_TA, strict=False))
                    _r_ip_ta = '.'.join(str(b) for b in _xb)
                    # Do NOT pre-create permissions for our own srflx IP or
                    # TURN server IP. That can cause TURN self-loop Data
                    # Indications and massive STUN echo storms.
                    return _r_ip_ta, _xp, _realm_ta, _nonce_ta
            return None

        # --- Early TURN relay allocation (before offer build) --------------- #
        # Allocate relay now so offer c= and m= carry relay IP/port.
        # Camera reads offer's c= to know where to send SRTP - relay address
        # here means SRTP reaches us even through port-restricted / hairpin NAT.
        # AIDOT_FAST_CONNECT skips this blocking pre-allocation (LAN-direct mode):
        # the offer goes out immediately with host/srflx candidates and the LAN
        # path connects without waiting on a cloud TURN Allocate round-trip.
        # AIDOT_SDES_SKIP_TURN_PREALLOC (experimental, opt-in) does the same skip
        # for SDES specifically, where _fast_connect is force-off (see
        # _resolve_sdes_skip_turn).  Either way the cost is instrumented below so
        # the saving is measurable: grep ``signaling-wait[`` for sdes-turn-prealloc.
        _skip_turn_prealloc = self._resolve_sdes_skip_turn()
        _turn_t0 = time.monotonic()
        _turn_did = False
        if _sdes_turn_entries and not _fast_connect and not _skip_turn_prealloc:
            _turn_did = True
            try:
                import re as _re_pre
                import hashlib as _hlk_pre
                _our_te_pre = next(
                    (e for e in _sdes_turn_entries if e.get("Username") == user_id),
                    _sdes_turn_entries[0],
                )
                _t_uri_pre = next(
                    (str(u) for u in (_our_te_pre.get("Uris") or []) if "turn:" in str(u)),
                    ""
                )
                _tm_pre = _re_pre.search(r'turns?:([^:?]+)(?::(\d+))?', _t_uri_pre)
                if _tm_pre:
                    _t_host_pre = _tm_pre.group(1)
                    _t_port_pre = int(_tm_pre.group(2) or 5349)
                    _t_user_pre = (_our_te_pre.get("Username") or "").encode()
                    _t_pass_pre = str(_our_te_pre.get("Password") or "").encode()
                    for _pre_sock, _pre_name in ((_audio_sock, "audio"), (_video_sock, "video")):
                        _pre_res = _turn_allocate_udp(
                            _pre_sock, _t_host_pre, _t_port_pre, _t_user_pre, _t_pass_pre,
                        )
                        if _pre_res:
                            _r_ip_pre, _r_port_pre, _r_realm_pre, _r_nonce_pre = _pre_res
                            _r_key_pre = _hlk_pre.md5(
                                _t_user_pre + b':' + _r_realm_pre + b':' + _t_pass_pre
                            ).digest()
                            _relay_addrs[_pre_sock] = (
                                _r_ip_pre, _r_port_pre, _r_realm_pre, _r_nonce_pre,
                                _t_host_pre, _t_port_pre, _r_key_pre, _t_user_pre,
                            )
                            _status(
                                f"TURN relay pre-allocated (offer): {_pre_name}"
                                f" -> {_r_ip_pre}:{_r_port_pre}"
                            )
            except Exception as _pre_exc:
                _LOGGER.warning("TURN pre-allocation error: %s", _pre_exc)
        if _skip_turn_prealloc and _sdes_turn_entries:
            _status(
                "AIDOT_SDES_SKIP_TURN_PREALLOC: skipping TURN relay"
                " pre-allocation (~2-3s) - host/srflx candidates only, LAN-direct"
            )
        _LOGGER.info(
            "signaling-wait[%s] sdes-turn-prealloc elapsed=%dms allocated=%d skipped=%s",
            self.device_id,
            int((time.monotonic() - _turn_t0) * 1000),
            len(_relay_addrs), bool(_skip_turn_prealloc))

        # --- DTLS certificate for m=application probe ----------------------- #
        # PreCon cameras (sptPreconn=1) need SESSION_MODE_REQ via SCTP datachannel.
        # Include m=application in offer so camera answers with its own DataChannel
        # section.  Use cryptography (already a dependency) - no aiortc needed.
        _dc_probe_fp = ""
        try:
            from cryptography import x509 as _cx509
            from cryptography.x509.oid import NameOID as _CNOID
            from cryptography.hazmat.primitives import hashes as _ch, serialization as _cser
            from cryptography.hazmat.primitives.asymmetric import ec as _cec
            from cryptography.hazmat.backends import default_backend as _cbd
            import datetime as _dt_dc
            import hashlib as _hs_dc
            _dc_key = _cec.generate_private_key(_cec.SECP256R1(), _cbd())
            _dc_name = _cx509.Name([_cx509.NameAttribute(_CNOID.COMMON_NAME, "aidot-dc")])
            _dc_cert = (
                _cx509.CertificateBuilder()
                .subject_name(_dc_name).issuer_name(_dc_name)
                .public_key(_dc_key.public_key())
                .serial_number(_cx509.random_serial_number())
                .not_valid_before(_dt_dc.datetime.utcnow())
                .not_valid_after(_dt_dc.datetime.utcnow() + _dt_dc.timedelta(days=365))
                .sign(_dc_key, _ch.SHA256(), _cbd())
            )
            _dc_der = _dc_cert.public_bytes(_cser.Encoding.DER)
            _dc_hex = _hs_dc.sha256(_dc_der).hexdigest().upper()
            _dc_probe_fp = "sha-256 " + ":".join(
                _dc_hex[i:i+2] for i in range(0, len(_dc_hex), 2)
            )
        except Exception as _cert_exc:
            _LOGGER.debug("DC probe: cert generation failed: %s", _cert_exc)

        # --- Generate PSK before SDP (must precede SDP building) ------------- #
        # PSK is injected into SDP as a=psk: and into wPayload.psk.
        # Use a CSPRNG: the PSK is media-keying material carried over signaling,
        # so it must not come from the predictable Mersenne-Twister (random).
        import secrets as _secrets_psk_early
        _psk_charset_req = "123456789abcdef"
        _psk_value_req = "".join(
            _secrets_psk_early.choice(_psk_charset_req)
            for _ in range(64)
        )

        # --- Build SDES SDP offer ------------------------------------------ #
        # AES_CM_128_HMAC_SHA1_80: 16-byte key + 14-byte salt = 30 bytes.
        srtp_key_audio = base64.b64encode(os.urandom(30)).decode()
        srtp_key_video = base64.b64encode(os.urandom(30)).decode()
        # Log SDES key material at DEBUG only - this is SRTP keying material and
        # should not appear in production-level logs.  Re-enable at debug when
        # verifying the AES counter derivation formula after Frida observation.
        _LOGGER.debug(
            "sdes: offer key=%s salt=%s psk=%s",
            base64.b64decode(srtp_key_audio)[:16].hex(),
            base64.b64decode(srtp_key_audio)[16:].hex(),
            _psk_value_req,
        )
        ts = int(time.time())
        # SDES-SRTP cameras use SIP-era plain SDP (RFC 3264 + RFC 3711).
        # Use RTP/SAVPF (RFC 4585) - Leedarson firmware expects the feedback
        # profile and silently ignores offers with plain RTP/SAVP.
        # Include per-m-section ICE credentials and a host candidate so that
        # newer PTZ firmware (e.g. LK.IPC.A001064) that requires ICE for
        # address discovery will respond.  Older cameras that don't understand
        # ICE ignore those attributes and use the port in the m= line directly.
        import secrets as _secrets
        _ufrag_a = _secrets.token_urlsafe(4)[:4]
        _pwd_a   = _secrets.token_urlsafe(24)[:22]
        _ufrag_v = _secrets.token_urlsafe(4)[:4]
        _pwd_v   = _secrets.token_urlsafe(24)[:22]
        # Use srflx (public) IP and direct port in c= and m= for the offer too,
        # for consistency with the answer.  TURN relay requires CreatePermission
        # for the camera's public IP which is unknown; relay in c= causes TURN to
        # drop every camera packet.  Relay is still in a=candidate: for ICE.
        # For LAN cameras (_public_ip is None) fall back to local_ip directly.
        _offer_audio_ip   = _public_ip or local_ip
        _offer_audio_port = audio_port
        _offer_video_ip   = _public_ip or local_ip
        _offer_video_port = video_port
        _bundle_hdr_line = (
            "a=group:BUNDLE 0 1 2\r\n" if _dc_probe_fp else "a=group:BUNDLE 0 1\r\n"
        )
        # Outbound talk: a talk-intended open advertises audio as sendrecv WITH an
        # a=ssrc so the camera builds a receive path for our PCMA (cname required -
        # _compress_sdp_req keeps a=ssrc lines only when "cname" is present).  Pure
        # streaming opens keep audio recvonly (the validated 72s/49s path, untouched).
        _talk_offer = talk
        _offer_audio_ssrc = int.from_bytes(os.urandom(4), "big") or 1
        _offer_audio_cname = _secrets.token_urlsafe(12)[:12]
        _audio_dir_line  = "a=sendrecv\r\n" if _talk_offer else "a=recvonly\r\n"
        _audio_ssrc_line = (
            f"a=ssrc:{_offer_audio_ssrc} cname:{_offer_audio_cname}\r\n"
            if _talk_offer else ""
        )
        # Shared talk state (only for talk-capable opens).  The bridge fills
        # src/sock on first inbound audio; SdesSession.async_start_talk sets
        # provider + sends SPEAKERSTART(848); the talk pump thread reads this to
        # emit outbound PCMA as SRTP at our offer SSRC.  srtp_key_audio here is
        # our offer key (captured before it is reassigned to the camera key after
        # the answer); the immutable str is safe to hold.
        _talk_state = {
            "provider": None,       # set by async_start_talk; cleared on stop/clip-end
            "src": None,            # camera media addr (bridge sets on first media)
            "sock": None,           # media socket (bridge)
            "ssrc": _offer_audio_ssrc,
            "key": srtp_key_audio,  # our offer key (immutable str; safe to hold)
            "want_speaker": False,    # async_start/stop_talk flips this
            "speaker_on": False,      # bridge sets after it sends SPEAKERSTART
            "spk_eligible_ts": None,  # bridge: first time SPEAKERSTART is eligible
            "stop": False,
        } if _talk_offer else None
        sdes_offer_sdp = (
            "v=0\r\n"
            f"o=- {ts} {ts} IN IP4 {local_ip}\r\n"
            "s=-\r\n"
            f"t=0 0\r\n{_bundle_hdr_line}"
            # audio m-section
            # a=crypto MUST precede ICE attributes (RFC 4568 section 9.1) so that
            # linear-parsing camera firmware recognises this as an SDES offer
            # rather than a pure-ICE offer and does not discard the key.
            f"m=audio {_offer_audio_port} RTP/SAVPF 0 8\r\n"
            f"c=IN IP4 {_offer_audio_ip}\r\n"
            + _audio_dir_line
            + "a=mid:0\r\n"
            f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}\r\n"
            "a=rtpmap:0 PCMU/8000\r\n"
            "a=rtpmap:8 PCMA/8000\r\n"
            # a=rtcp-mux: multiplexes RTCP onto the RTP port so the camera does
            # not send RTCP to audio_port+1 (which is never bound), and so that
            # ffmpeg does not try to open a separate RTCP socket.
            "a=rtcp-mux\r\n"
            + _audio_ssrc_line
            # PSK is sent in wPayload only - real app does NOT inject a=psk: into
            # the SDP body (confirmed from logcat ground truth 2026-05-22).
            + f"a=ice-ufrag:{_ufrag_a}\r\n"
            f"a=ice-pwd:{_pwd_a}\r\n"
            f"a=candidate:1 1 udp 2130706431 {local_ip} {audio_port} typ host\r\n"
            + (
                f"a=candidate:2 1 udp 1694498815 {_public_ip} {audio_port}"
                f" typ srflx raddr {local_ip} rport {audio_port}\r\n"
                if _public_ip else ""
            )
            + (
                f"a=candidate:3 1 udp 16777215 {_relay_addrs[_audio_sock][0]}"
                f" {_relay_addrs[_audio_sock][1]}"
                f" typ relay raddr {local_ip} rport {audio_port}\r\n"
                if _audio_sock in _relay_addrs else ""
            )
            # video m-section
            + f"m=video {_offer_video_port} RTP/SAVPF 96 97\r\n"
            f"c=IN IP4 {_offer_video_ip}\r\n"
            "a=recvonly\r\n"
            "a=mid:1\r\n"
            f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_video}\r\n"
            "a=rtpmap:96 H264/90000\r\n"
            "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\r\n"
            "a=rtpmap:97 H265/90000\r\n"
            "a=fmtp:97 level-id=93\r\n"
            "a=rtcp-mux\r\n"
            f"a=ice-ufrag:{_ufrag_v}\r\n"
            f"a=ice-pwd:{_pwd_v}\r\n"
            f"a=candidate:1 1 udp 2130706431 {local_ip} {video_port} typ host\r\n"
            + (
                f"a=candidate:2 1 udp 1694498815 {_public_ip} {video_port}"
                f" typ srflx raddr {local_ip} rport {video_port}\r\n"
                if _public_ip else ""
            )
            + (
                f"a=candidate:3 1 udp 16777215 {_relay_addrs[_video_sock][0]}"
                f" {_relay_addrs[_video_sock][1]}"
                f" typ relay raddr {local_ip} rport {video_port}\r\n"
                if _video_sock in _relay_addrs else ""
            )
            # m=application SCTP DataChannel section for SDES cameras.
            # Ground truth from real Leedarson app logcat (2026-05-22):
            #   m=application 9 SCTP webrtc-datachannel
            #   a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{key}
            # That is ALL - no a=setup, no a=sctp-port, no fingerprint.
            # Using UDP/DTLS/SCTP or a=setup:active triggers the SCTP deadlock;
            # plain SCTP lets the camera handle role negotiation internally.
            # The a=crypto line is required: GetSctpSdesKey0/1 in the camera
            # firmware parse inline: from this attribute to derive the AES key.
            + (
                "m=application 9 SCTP webrtc-datachannel\r\n"
                "c=IN IP4 0.0.0.0\r\n"
                "a=mid:2\r\n"
                f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}\r\n"
                if _dc_probe_fp else ""
            )
        )

        # Opt-in: express a video-codec preference in the OFFER rather than
        # advertising both 96/97 and letting the camera decide in its answer.
        #
        # The offer is the SDP that matters here.  Traced live 2026-08-07 with
        # every status line printed: this path sends webrtcReq carrying OUR
        # offer and then reports "Using camera's video SRTP key from answer" -
        # the camera answers, we do not.  The answer builder further down runs
        # only on the branch where the camera offers first, which this camera
        # did not take, so pinning there changed nothing at all while the
        # arms still came out looking like the pin had worked.  Inert unless set.
        _pin_video_pt = _resolve_sdes_video_pt()
        if _pin_video_pt is not None:
            sdes_offer_sdp = narrow_sdp_payload_types(
                sdes_offer_sdp, keep_video=_pin_video_pt)
            if _status:
                _status(f"SDES: offer pinned to video pt={_pin_video_pt}")

        _relay_str = (
            f"  relay-audio={_relay_addrs[_audio_sock][0]}:{_relay_addrs[_audio_sock][1]}"
            if _audio_sock in _relay_addrs else ""
        )
        _status(
            f"SDP offer (SDES)  local={local_ip}"
            + (f"  srflx={_public_ip}" if _public_ip else "")
            + f"  audio={audio_port}  video={video_port}"
            + _relay_str
        )

        # Send livePlayReq before the SDP offer to arm the camera's stream.
        import random as _random
        _live_req_sdes = json.dumps({
            "method":  "livePlayReq",
            "service": "IPC",
            "devId":   device_id,
            "srcAddr": f"0.{user_id}",
            "seq":     f"ap{_random.randint(1000000, 9999999)}",
            "tst":     int(time.time() * 1000),
            **( {"userId": numeric_uid_raw} if numeric_uid_raw is not None else {} ),
            "payload": {
                "peerid":  peer_id,
                "devId":   device_id,
                # Decompiled reference app (tyrus/o.java) sets payload.dstAddr
                # to the target deviceId for livePlayReq.
                "dstAddr": device_id,
                # App payload compatibility fields (decompiled live-play model).
                "livePlay": 1,
                "powerType": _live_power_type,
                "p2pCache": _live_p2p_cache,
                "dseq": self._next_dseq(),
            },
        })
        _live_play_topic_sdes = f"iot/v1/s/{user_id}/IPC/livePlayReq"
        outgoing_q.put_nowait((_live_play_topic_sdes, _live_req_sdes))
        _status(f"livePlayReq sent (SDES)  peerid={peer_id}")
        import asyncio as _asyncio
        # Wait for the livePlayReq echo from the broker/camera before sending
        # webrtcReq.  The echo confirms the MQTT pipeline to this device is live
        # and the broker session is registered.  Fall through after 5 s if it
        # never arrives (same safety as the old fixed 0.5 s sleep, but adaptive).
        # SDES fast-liveplay (sdes_fast_liveplay, DEFAULT ON since 0.7.32 - the
        # official app fire-and-forgets too): instrumentation showed the echo and
        # livePlayResp waits BOTH always time out for the SDES cameras measured
        # (echo/resp never arrive) yet streaming succeeds - i.e. ~6 s of dead
        # padding.  When on (the default), cap the echo wait short and skip the
        # livePlayResp wait; the full ICE/TURN/SCTP handshake is untouched.  This
        # is the SDES path's OWN livePlay waits (the DTLS gate above never runs for
        # SDES: use_sdes is True there).  VALIDATED in a 3 h live soak (15 SDES
        # opens across battery cameras, 0 churn / 0 fail, ~4.5 s signaling saved)
        # and shipped default-on across 0.8/0.9.x.  Role-reversal models
        # (_NO_FAST_LIVEPLAY_MODELS, e.g. A001064) are always excluded; disable
        # elsewhere via AIDOT_SDES_FAST_LIVEPLAY={0,false,no,off}.
        _skip_lp = self._resolve_sdes_fast_liveplay()
        _echo_timeout = 1.5 if _skip_lp else 5.0
        _echo_t0 = time.monotonic()
        try:
            await _asyncio.wait_for(liveplay_echo_ev.wait(), timeout=_echo_timeout)
            _status("livePlayReq echo received - sending webrtcReq, ICE, then launching ffmpeg")
        except TimeoutError:
            _status(f"no livePlayReq echo in {_echo_timeout:.1f}s - sending webrtcReq,"
                    " ICE, then launching ffmpeg anyway")
        _LOGGER.info(
            "signaling-wait[%s] livePlayReq-echo elapsed=%dms (timeout=%.1fs)",
            self.device_id, int((time.monotonic() - _echo_t0) * 1000), _echo_timeout)
        # livePlayResp: explicit camera accept/reject before SDP/ICE.
        if _skip_lp:
            _LOGGER.info(
                "signaling-wait[%s] livePlayResp skipped (sdes_fast_liveplay)",
                self.device_id)
        else:
            _lp_t0 = time.monotonic()
            _lp_arrived = False
            try:
                _lp_resp_sdes = await _asyncio.wait_for(
                    _asyncio.shield(liveplay_resp_fut), timeout=1.0
                )
                _lp_arrived = True
                _lp_code_sdes = int(_lp_resp_sdes.get("code", 200))
                _lp_on_sdes = int(_lp_resp_sdes.get("livePlay", 1))
                # Only an explicit livePlay=0 is an unambiguous refusal (fast-fail).
                # Numeric codes (e.g. -50019 "not ready" on a waking battery cam)
                # are transient - the camera recovers and streams - so log and
                # proceed rather than abort on a code we can't classify as terminal
                # (genuine terminal rejects are still caught on the webrtcResp ack).
                if _lp_on_sdes == 0:
                    # Match the sibling raises: close the reserved sockets and
                    # signal the MQTT thread before propagating.  (No sdp_path
                    # unlink here - it is not created until later, below.)
                    for _rsock in (_audio_sock, _video_sock):
                        try:
                            _rsock.close()
                        except Exception:
                            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                    outgoing_q.put_nowait(None)   # stop MQTT thread
                    raise RuntimeError(
                        f"livePlay refused by camera (livePlay=0, code={_lp_code_sdes})")
                elif _lp_code_sdes not in (0, 200):
                    _status(f"livePlayResp: non-OK code {_lp_code_sdes}"
                            f"{' (not ready, transient)' if _lp_code_sdes == _LIVE_PLAY_NOT_READY else ''}"
                            " - proceeding")
            except TimeoutError:
                pass
            _LOGGER.info(
                "signaling-wait[%s] livePlayResp elapsed=%dms arrived=%s",
                self.device_id, int((time.monotonic() - _lp_t0) * 1000), _lp_arrived)

        # --- Build local-receiver SDP for ffmpeg ----------------------------- #
        # Built BEFORE sending webrtcReq so ffmpeg is already listening on the
        # reserved ports when the camera starts streaming.  Launching ffmpeg
        # after webrtcReq means the first seconds of SRTP data land in the
        # Python reservation sockets (or trigger ICMP port-unreachable after
        # they are closed), causing 0-frame output.
        # ffmpeg_sdp uses only audio_port/video_port and srtp_key_* - all known
        # from the allocation step above; the camera's webrtcResp is not needed.
        # c=IN IP4 0.0.0.0 tells ffmpeg to bind locally (listen mode).
        # Use RTP/SAVP (plain SRTP, RFC 3711) rather than RTP/SAVPF for the
        # ffmpeg receiver SDP.  ffmpeg's SDP demuxer does not recognise the
        # SAVPF feedback profile (RFC 4585) as a valid SRTP profile and fails
        # with "Could not find codec parameters" when SAVPF is used.  The
        # camera-facing offer SDP still uses RTP/SAVPF as required by the
        # firmware; only this local file (read by ffmpeg) needs SAVP.
        ffmpeg_sdp = (
            "v=0\r\n"
            f"o=- {ts} {ts} IN IP4 0.0.0.0\r\n"
            "s=aidot-sdes-rx\r\n"
            "t=0 0\r\n"
            f"m=audio {audio_port} RTP/SAVP 0 8\r\n"
            "c=IN IP4 0.0.0.0\r\n"
            f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}\r\n"
            "a=rtpmap:0 PCMU/8000\r\n"
            "a=rtpmap:8 PCMA/8000\r\n"
            # a=rtcp-mux prevents ffmpeg from trying to bind audio_port+1 for
            # RTCP (a separate socket that is never needed here).
            "a=rtcp-mux\r\n"
            f"m=video {video_port} RTP/SAVP 96 97\r\n"
            "c=IN IP4 0.0.0.0\r\n"
            f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_video}\r\n"
            "a=rtpmap:96 H264/90000\r\n"
            "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;profile-level-id=42e01f\r\n"
            "a=rtpmap:97 H265/90000\r\n"
            "a=fmtp:97 level-id=93\r\n"
            "a=rtcp-mux\r\n"
        )

        sdp_path = await asyncio.get_running_loop().run_in_executor(
            # _inject_sprop reads the sprop cache from disk; keep it inside the
            # executor (not as an eagerly-evaluated arg) so the blocking open()
            # does not run on the event loop.
            None, lambda: _make_sdp_tempfile(_inject_sprop(ffmpeg_sdp, self.device_id)))
        _cl(os.unlink, sdp_path)   # released with the sockets on a cancelled open

        # --- Send webrtcReq BEFORE releasing reservation sockets ------------- #
        # ICE cameras (e.g. LK.IPC.A001064) send STUN binding requests to our
        # ICE candidates immediately after receiving webrtcReq.  We must respond
        # from the reservation sockets (which own those ports) BEFORE handing
        # the ports to ffmpeg.  Non-ICE cameras start streaming SRTP straight
        # away; any early SRTP packets landing on the reservation sockets are
        # discarded, but the camera keeps streaming once ffmpeg is bound.
        # IceServerList for SDES path.  Always include STUN so the camera's ICE
        # agent can gather its own srflx candidate.  Append any TURN entries from
        # ice_config so the camera can allocate a relay and probe our srflx/host
        # candidates when direct connectivity fails (e.g. symmetric NAT).
        # Advertise an ICE server to the camera only when we actually have a
        # URI for it.  With AIDOT_STUN_SERVERS="" this used to put
        # {"Uris": []} into webrtcReq - an entry the camera must parse and
        # that names no server.
        _sdes_stun_uris = stun_server_uris()
        _sdes_ice_server_list = [{"Uris": _sdes_stun_uris}] if _sdes_stun_uris else []
        _sdes_ice_server_list.extend(_sdes_turn_entries)
        # _psk_value_req was generated before the SDP offer (see above).
        # Reused here in webrtcReq and webrtcResp for consistency.
        def _compress_sdp_req(_sdp: str) -> str:
            """g.b() equivalent - selective SDP filter for wPayload."""
            _out: list = []
            _seen: dict = {}
            _media_type = ""
            _before_m = True

            def _k(_ln: str, _key: str = "") -> None:
                _out.append(_ln + "\r\n")
                if _key:
                    _seen[_key] = "1"

            for _ln in _sdp.splitlines():
                if _ln.startswith("m="):
                    _before_m = False
                    _media_type = _ln.split(" ")[0]
                    _k(_ln)
                    continue
                if _before_m:
                    if _ln.startswith("s="):
                        _k(_ln)
                    continue
                if _ln.startswith("a=ssrc") and "cname" in _ln:
                    _k(_ln)
                    continue
                if any(_d in _ln for _d in ("sendrecv", "recvonly", "sendonly")):
                    _k(_ln)
                    continue
                for _ak in ("ice-ufrag", "ice-pwd", "fingerprint", "setup",
                            "ice-options", "crypto", "psk"):
                    if _ak in _ln:
                        if _seen.get(_ak) is None:
                            _k(_ln, _ak)
                        break
                else:
                    if "candidate" in _ln and " udp " in _ln.lower():
                        _k(_ln)
                        continue
                    if _media_type == "m=audio":
                        if any(_c in _ln for _c in ("opus", "PCMU", "PCMA", "AAC")):
                            _k(_ln)
                    elif _media_type == "m=video":
                        if "H264/90000" in _ln and _seen.get("H264/90000") is None:
                            _k(_ln, "H264/90000")
                            try:
                                _seen["H264/90000_pt"] = _ln.split(":")[1].split(" ")[0]
                            except Exception:
                                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_k', exc_info=True)
                        elif "H265/90000" in _ln and _seen.get("H265/90000") is None:
                            _k(_ln, "H265/90000")
                            try:
                                _seen["H265/90000_pt"] = _ln.split(":")[1].split(" ")[0]
                            except Exception:
                                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_k', exc_info=True)
                        elif "apt=" in _ln:
                            try:
                                _apt = _ln.split("apt=")[1].strip()
                            except Exception:
                                _apt = ""
                            if _apt and _apt in (
                                _seen.get("H264/90000_pt", ""),
                                _seen.get("H265/90000_pt", ""),
                            ):
                                _k(_ln)
                        elif "fmtp" in _ln and "profile-level-id" in _ln:
                            if _seen.get("profile-level") is None:
                                _k(_ln, "profile-level")
                    elif _media_type == "m=application":
                        if "sctp-port" in _ln:
                            _k(_ln)
            return "".join(_out)

        _compressed_sdp_req = _compress_sdp_req(sdes_offer_sdp)
        _webrtc_req_sdes_payload = json.dumps({
            "method":  "webrtcReq",
            "service": "IPC",
            "devId":   device_id,
            "srcAddr": f"0.{user_id}",
            "seq":     _seq(),
            "tst":     int(time.time() * 1000),
            **( {"userId": numeric_uid_raw} if numeric_uid_raw is not None else {} ),
            "payload": {
                # Legacy flat fields - older firmware parses payload.peerid directly.
                "peerid":  peer_id,
                "devId":   device_id,
                "offer":   {"type": "offer", "sdp": sdes_offer_sdp},
                "trackId": 0,
                # Decompiled reference app (tyrus/o.java) sets dstAddr=deviceId
                # for webrtcReq.
                "dstAddr": device_id,
                "encOffer": 1,
                "liveMqtt": 1,
                # wPayload: newer firmware parses wPayload for ICE credentials
                # and PSK.  Fields match reference app o.java (signaling/tyrus).
                "wPayload": {
                    "peerid": peer_id,
                    "sts":    int(time.time() * 1000),
                    "psk":    _psk_value_req,
                    "offer":  {"type": "offer", "sdp": _compressed_sdp_req},
                },
                "IceServerList": _sdes_ice_server_list,
            },
        })
        outgoing_q.put_nowait((webrtc_req_topic, _webrtc_req_sdes_payload))
        self._cold_phase("webrtcReq (sdes)")
        _status(f"webrtcReq sent (SDES)  peerid={peer_id}")

        # --- Acknowledge camera's webrtcReq echo with webrtcResp ------------- #
        # LK.IPC.A001064 echoes our offer back as webrtcReq before doing ICE.
        # It will not start streaming until it receives a webrtcResp from us.
        # Check for the echo within 2 s so the webrtcResp is sent while our
        # reservation sockets are still open (camera's ICE may arrive next).
        #
        # NOTE: webrtc_req_echo_fut (not camera_offer_fut) is the correct future
        # here.  camera_offer_fut is only set for non-echo (role-reversal) messages
        # where is_echo=False.  The broker echo carries our own srcAddr prefix so
        # is_echo=True, which is exactly what webrtc_req_echo_fut signals.
        _echo_fut = webrtc_req_echo_fut if webrtc_req_echo_fut is not None else camera_offer_fut
        _cam_echo_received = False
        _webrtc_resp_sdes_topic: "Optional[str]" = None
        _webrtc_resp_sdes: "Optional[str]" = None
        _sdes_webrtcresp_sent = False   # True once we actually publish the SDES webrtcResp
        # Only role-reversal models (A001064, _skip_lp False) echo our webrtcReq
        # and need the webrtcResp built below; for A001513-class (_skip_lp True,
        # default) the echo never arrives, so don't block ~2s on it.
        try:
            await _asyncio.wait_for(
                _asyncio.shield(_echo_fut),
                timeout=_sdes_echo_wait_timeout(_skip_lp),
            )
            _cam_echo_received = True
            _status("camera webrtcReq echo received - building webrtcResp")
            # Seed _sdes_turn_entries from the echo's IceServerList if the HTTP
            # ice_config fetch returned nothing (empty list).  The echo carries
            # our userId TURN credentials - extract them so the hole-punch and
            # any future relay allocation use the correct server/port.
            if not _sdes_turn_entries:
                try:
                    _echo_payload = _echo_fut.result() if (_echo_fut is not None and _echo_fut.done()) else {}
                    for _e in (_echo_payload.get("IceServerList") or []):
                        _e_uris = _e.get("Uris") or []
                        if any("turn:" in str(u) for u in _e_uris):
                            _sdes_turn_entries.append({
                                "Uris":     _e_uris,
                                "Username": _e.get("Username") or "",
                                "Password": str(_e.get("Password") or ""),
                            })
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_k', exc_info=True)
            # Allocate TURN relay if not already done before offer build.
            # When ice_config provided TURN entries, pre-allocation already ran
            # and _relay_addrs is populated - skip to avoid double-allocation.
            if not _relay_addrs:
                try:
                    import re as _re_relay_e
                    import hashlib as _hlk_e
                    _our_te = next(
                        (e for e in _sdes_turn_entries if e.get("Username") == user_id),
                        _sdes_turn_entries[0] if _sdes_turn_entries else None,
                    )
                    if _our_te:
                        _t_uri_e = next(
                            (str(u) for u in (_our_te.get("Uris") or [])
                             if "turn:" in str(u)), ""
                        )
                        _tm_e = _re_relay_e.search(
                            r'turns?:([^:?]+)(?::(\d+))?', _t_uri_e
                        )
                        if _tm_e:
                            _t_host_e = _tm_e.group(1)
                            _t_port_e = int(_tm_e.group(2) or 5349)
                            _t_user_e = (_our_te.get("Username") or "").encode()
                            _t_pass_e = str(_our_te.get("Password") or "").encode()
                            for _alloc_sock_e in (_audio_sock, _video_sock):
                                _alloc_res_e = _turn_allocate_udp(
                                    _alloc_sock_e, _t_host_e, _t_port_e,
                                    _t_user_e, _t_pass_e,
                                )
                                if _alloc_res_e:
                                    _r_ip_e, _r_port_e, _r_realm_e, _r_nonce_e = _alloc_res_e
                                    _r_key_e = _hlk_e.md5(
                                        _t_user_e + b':' + _r_realm_e + b':' + _t_pass_e
                                    ).digest()
                                    _relay_addrs[_alloc_sock_e] = (
                                        _r_ip_e, _r_port_e, _r_realm_e, _r_nonce_e,
                                        _t_host_e, _t_port_e, _r_key_e, _t_user_e,
                                    )
                                    _status(
                                        f"TURN relay allocated (echo fallback): "
                                        f"{'audio' if _alloc_sock_e is _audio_sock else 'video'}"
                                        f" -> {_r_ip_e}:{_r_port_e}"
                                    )
                except Exception as _relay_early_exc:
                    _LOGGER.warning(
                        "TURN relay allocation error: %s", _relay_early_exc
                    )
            # Answer SDP c= and m= use the TURN relay address when available so
            # the camera sends SRTP to our relay port.  The TURN server then
            # wraps each SRTP packet in a Data Indication and delivers it to
            # audio_sock / video_sock; the bridge thread strips the wrapper and
            # forwards the inner SRTP to ffmpeg's loopback ports.
            # For cameras on the same LAN or when relay allocation failed, fall
            # back to the srflx (public) or local IP.
            _audio_relay = _relay_addrs.get(_audio_sock)
            _video_relay = _relay_addrs.get(_video_sock)
            _ans_audio_ip   = _audio_relay[0] if _audio_relay else (_public_ip or local_ip)
            _ans_audio_port = _audio_relay[1] if _audio_relay else audio_port
            _ans_video_ip   = _video_relay[0] if _video_relay else (_public_ip or local_ip)
            _ans_video_port = _video_relay[1] if _video_relay else video_port
            _relay_answer_sdp = (
                "v=0\r\n"
                f"o=- {ts} {ts} IN IP4 {local_ip}\r\n"
                "s=-\r\n"
                "t=0 0\r\n"
                f"m=audio {_ans_audio_port} RTP/SAVPF 0 8\r\n"
                f"c=IN IP4 {_ans_audio_ip}\r\n"
                "a=sendonly\r\n"
                "a=mid:0\r\n"
                f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}\r\n"
                "a=rtpmap:0 PCMU/8000\r\n"
                "a=rtpmap:8 PCMA/8000\r\n"
                "a=rtcp-mux\r\n"
                f"a=ice-ufrag:{_ufrag_a}\r\n"
                f"a=ice-pwd:{_pwd_a}\r\n"
                f"a=candidate:1 1 udp 2130706431 {local_ip} {audio_port} typ host\r\n"
                + (
                    f"a=candidate:2 1 udp 1694498815 {_public_ip} {audio_port}"
                    f" typ srflx raddr {local_ip} rport {audio_port}\r\n"
                    if _public_ip else ""
                )
                + (
                    f"a=candidate:3 1 udp 16777215 {_relay_addrs[_audio_sock][0]}"
                    f" {_relay_addrs[_audio_sock][1]}"
                    f" typ relay raddr {local_ip} rport {audio_port}\r\n"
                    if _audio_sock in _relay_addrs else ""
                )
                + f"m=video {_ans_video_port} RTP/SAVPF 96 97\r\n"
                f"c=IN IP4 {_ans_video_ip}\r\n"
                "a=sendonly\r\n"
                "a=mid:1\r\n"
                f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_video}\r\n"
                "a=rtpmap:96 H264/90000\r\n"
                "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
                "profile-level-id=42e01f\r\n"
                "a=rtpmap:97 H265/90000\r\n"
                "a=fmtp:97 level-id=93\r\n"
                "a=rtcp-mux\r\n"
                f"a=ice-ufrag:{_ufrag_v}\r\n"
                f"a=ice-pwd:{_pwd_v}\r\n"
                f"a=candidate:1 1 udp 2130706431 {local_ip} {video_port} typ host\r\n"
                + (
                    f"a=candidate:2 1 udp 1694498815 {_public_ip} {video_port}"
                    f" typ srflx raddr {local_ip} rport {video_port}\r\n"
                    if _public_ip else ""
                )
                + (
                    f"a=candidate:3 1 udp 16777215 {_relay_addrs[_video_sock][0]}"
                    f" {_relay_addrs[_video_sock][1]}"
                    f" typ relay raddr {local_ip} rport {video_port}\r\n"
                    if _video_sock in _relay_addrs else ""
                )
            )
            _compressed_sdp_ans = _compress_sdp_req(_relay_answer_sdp)

            _webrtc_resp_sdes_topic = f"iot/v1/s/{user_id}/IPC/webrtcResp"
            _webrtc_resp_sdes = json.dumps({
                "method":  "webrtcResp",
                "service": "IPC",
                "devId":   device_id,
                "srcAddr": f"0.{user_id}",
                "seq":     _seq(),
                "tst":     int(time.time() * 1000),
                **( {"userId": numeric_uid_raw} if numeric_uid_raw is not None else {} ),
                "payload": {
                    "peerid":  peer_id,
                    "devId":   device_id,
                    "answer":  {"type": "answer", "sdp": _relay_answer_sdp},
                    "trackId": 0,
                    "dstAddr": device_id,
                    "encOffer": 1,
                    "liveMqtt": 1,
                    # wPayload: newer firmware (e.g. LK.IPC.A001064) parses
                    # wPayload to extract ICE credentials and PSK.  Fields match
                    # reference app o.java (signaling/tyrus).
                    "wPayload": {
                        "peerid": peer_id,
                        "sts":    int(time.time() * 1000),
                        "psk":    _psk_value_req,
                        "answer": {"type": "answer", "sdp": _compressed_sdp_ans},
                    },
                },
            })
            # Send SDES webrtcResp: camera will send SRTP to our public IP/port
            # (srflx), which routes through NAT directly to our socket.
            outgoing_q.put_nowait((_webrtc_resp_sdes_topic, _webrtc_resp_sdes))
            _sdes_webrtcresp_sent = True
            _ans_via = "relay" if (_audio_relay or _video_relay) else "srflx"
            _status(
                f"webrtcResp sent (SDES, {_ans_via} answer:"
                f" audio={_ans_audio_ip}:{_ans_audio_port}"
                f" video={_ans_video_ip}:{_ans_video_port})"
            )
        except TimeoutError:
            pass  # no echo - camera uses a different signalling variant; proceed

        # --- Announce our ICE candidates via MQTT (iceCandidateReq) ----------- #
        # The iOS app always sends iceCandidateReq after webrtcReq/webrtcResp.
        # ICE-capable cameras (e.g. LK.IPC.A001064) wait for this trickle-ICE
        # message before initiating STUN connectivity checks, even when the same
        # candidates are already present in the SDP a=candidate lines.  Without
        # this step the camera sits idle and never sends STUN - resulting in 0
        # frames.  Non-ICE cameras (e.g. LK.IPC.A001513) ignore iceCandidateReq
        # and begin streaming immediately from the SDP exchange, so sending these
        # messages is safe for all camera models.
        _ice_cand_topic_sdes = f"iot/v1/s/{user_id}/IPC/iceCandidateReq"

        def _send_sdes_ice_cand(cand_str: str, mid: str) -> None:
            """Publish a single trickle-ICE candidate via MQTT."""
            _cand_obj = {
                "candidate":     cand_str,
                "sdpMid":        mid,
                "sdpMLineIndex": int(mid),
            }
            _msg = json.dumps({
                "method":  "iceCandidateReq",
                "service": "IPC",
                "devId":   device_id,
                "srcAddr": f"0.{user_id}",
                "seq":     _seq(),
                "tst":     int(time.time() * 1000),
                **( {"userId": numeric_uid_raw} if numeric_uid_raw is not None else {} ),
                "payload": {
                    # dstAddr routes to the camera device, not the user account.
                    # HAR captures confirm payload.dstAddr = deviceId on every
                    # iceCandidateReq; using user_id causes silent drops by firmware.
                    "dstAddr": device_id,
                    # wPayload is the nested format required by newer firmware
                    # (e.g. LK.IPC.A001064) that parses wPayload.candidate instead
                    # of the flat payload.candidate field.
                    "wPayload": {
                        "peerid":    peer_id,
                        "candidate": _cand_obj,
                    },
                    # Flat legacy fields retained for older firmware compatibility.
                    "peerid":    peer_id,
                    "devId":     device_id,
                    "candidate": _cand_obj,
                },
            })
            outgoing_q.put_nowait((_ice_cand_topic_sdes, _msg))

        # --- TURN relay allocation: fallback for cameras that skip the echo path #
        # Early allocation was done inside the echo handler above.  This block
        # handles cameras that did not produce an echo (non-echo-reversal path)
        # so _relay_addrs is still empty at this point.
        # Skipped in AIDOT_FAST_CONNECT (LAN-direct): no relay alloc, host/srflx
        # candidates only (the synchronous allocate would block the post-offer
        # candidate trickle on a cloud round-trip).
        if not _relay_addrs and not _fast_connect:
            try:
                import re as _re_relay
                import hashlib as _hlk
                _our_turn_entry = None
                for _te in _sdes_turn_entries:
                    if _te.get("Username") == user_id:
                        _our_turn_entry = _te
                        break
                if _our_turn_entry is None and _sdes_turn_entries:
                    _our_turn_entry = _sdes_turn_entries[0]
                if _our_turn_entry:
                    _t_uri_r = next(
                        (str(u) for u in (_our_turn_entry.get("Uris") or [])
                         if "turn:" in str(u)),
                        ""
                    )
                    _tm_r = _re_relay.search(r'turns?:([^:?]+)(?::(\d+))?', _t_uri_r)
                    if _tm_r:
                        _t_host_r = _tm_r.group(1)
                        _t_port_r = int(_tm_r.group(2) or 5349)
                        _t_user_r = (_our_turn_entry.get("Username") or "").encode()
                        _t_pass_r = str(_our_turn_entry.get("Password") or "").encode()
                        for _alloc_sock in (_audio_sock, _video_sock):
                            _alloc_res = _turn_allocate_udp(
                                _alloc_sock, _t_host_r, _t_port_r,
                                _t_user_r, _t_pass_r,
                            )
                            if _alloc_res:
                                _r_ip, _r_port, _r_realm, _r_nonce = _alloc_res
                                _r_key = _hlk.md5(
                                    _t_user_r + b':' + _r_realm + b':' + _t_pass_r
                                ).digest()
                                _relay_addrs[_alloc_sock] = (
                                    _r_ip, _r_port, _r_realm, _r_nonce,
                                    _t_host_r, _t_port_r, _r_key, _t_user_r,
                                )
                                _status(
                                    f"TURN relay allocated: "
                                    f"{'audio' if _alloc_sock is _audio_sock else 'video'}"
                                    f" -> {_r_ip}:{_r_port}"
                                )
                            else:
                                _LOGGER.warning(
                                    "TURN allocation failed for %s socket",
                                    "audio" if _alloc_sock is _audio_sock else "video",
                                )
            except Exception as _relay_exc:
                _LOGGER.warning("TURN relay allocation error: %s", _relay_exc)

        for _ice_mid, _ice_port in (("0", audio_port), ("1", video_port)):
            # Host candidate (LAN IP)
            _send_sdes_ice_cand(
                f"candidate:1 1 udp 2130706431 {local_ip} {_ice_port} typ host",
                _ice_mid,
            )
            # srflx candidate (public IP) - announced separately so the camera
            # triggers a new ICE check to our public address even if it already
            # processed the SDP host candidate.
            if _public_ip:
                _send_sdes_ice_cand(
                    f"candidate:2 1 udp 1694498815 {_public_ip} {_ice_port}"
                    f" typ srflx raddr {local_ip} rport {_ice_port}",
                    _ice_mid,
                )
            # relay candidate - advertised so the camera can probe us via TURN
            # when direct and srflx paths are blocked (port-restricted NAT).
            _relay_sock = _audio_sock if _ice_mid == "0" else _video_sock
            if _relay_sock in _relay_addrs:
                _ri = _relay_addrs[_relay_sock]
                _send_sdes_ice_cand(
                    f"candidate:3 1 udp 16777215 {_ri[0]} {_ri[1]}"
                    f" typ relay raddr {local_ip} rport {_ice_port}",
                    _ice_mid,
                )
        _status(
            f"iceCandidateReq sent  audio={audio_port}  video={video_port}"
            + (f"  srflx={_public_ip}" if _public_ip else "")
            + (f"  relay={_relay_addrs[_audio_sock][0]}"
               if _audio_sock in _relay_addrs else "")
        )

        # --- NAT hole-punch: create outbound UDP mapping before STUN window --- #
        # Without a prior outbound packet from each socket, most home-router NATs
        # have no mapping for those ports and silently drop the inbound STUN
        # binding requests from the camera.  Sending a minimal STUN binding-
        # request packet to an external host forces the router to create a NAT
        # entry so the camera's probes are forwarded to our sockets.
        _hp_host = None
        _hp_port = 3478
        try:
            if _sdes_turn_entries:
                import re as _re_hp
                # Find the first TURN URI in the entry (not just Uris[0], which
                # may be a stun: URI that the regex won't match).
                _hp_uri = next(
                    (str(u) for u in (_sdes_turn_entries[0].get("Uris") or [])
                     if "turn:" in str(u)),
                    ""
                )
                _m_hp = _re_hp.search(r'turns?:([^:?]+)(?::(\d+))?', _hp_uri)
                if _m_hp:
                    _hp_host = _m_hp.group(1)
                    _hp_port = int(_m_hp.group(2) or 3478)
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_open_sdes_stream', exc_info=True)
        # When the cloud supplied no TURN entry, fall back to the vendor's TURN
        # server only to open a NAT mapping.  This sends a STUN packet to a
        # hardcoded third-party host; AIDOT_SDES_HOLEPUNCH_HOST overrides it
        # (set it empty to disable the hardcoded fallback entirely).
        if not _hp_host:
            _hp_env = os.environ.get("AIDOT_SDES_HOLEPUNCH_HOST")
            if _hp_env is not None:
                _hp_host = _hp_env.strip() or None
            else:
                _hp_host = "3.230.182.123"   # fallback: Arnoo TURN server
                _LOGGER.warning(
                    "camera %s: no TURN entry from cloud; NAT hole-punch will send "
                    "a STUN packet to the hardcoded vendor host %s. Set "
                    "AIDOT_SDES_HOLEPUNCH_HOST to override (empty to disable).",
                    getattr(self, "device_id", "?"), _hp_host,
                )
        _hp_stun = b'\x00\x01\x00\x00\x21\x12\xa4\x42' + os.urandom(12)
        _hp_port2 = 5349
        if not _hp_host:
            _status("NAT hole-punch: skipped (no TURN host)")
        else:
            for _hp_sock in (_audio_sock, _video_sock):
                try:
                    _hp_sock.sendto(_hp_stun, (_hp_host, _hp_port))
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_send_sdes_ice_cand', exc_info=True)
            # Punch to TURN allocation port (5349) as well so port-restricted NAT
            # allows traffic from either TURN port (3478 STUN or 5349 allocation).
            if _hp_port != _hp_port2:
                for _hp_sock in (_audio_sock, _video_sock):
                    try:
                        _hp_sock.sendto(_hp_stun, (_hp_host, _hp_port2))
                    except Exception:
                        _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_send_sdes_ice_cand', exc_info=True)
            _status(
                f"NAT hole-punch: sent from audio={audio_port}"
                f" video={video_port} -> {_hp_host}:{_hp_port}"
                + (f" and :{_hp_port2}" if _hp_port != _hp_port2 else "")
            )

        def _is_self_peer_ip(_ip: "Optional[str]") -> bool:
            if not _ip:
                return False
            if _ip in {"127.0.0.1", "0.0.0.0", local_ip}:
                return True
            if _public_ip and _ip == _public_ip:
                return True
            return False

        _selfloop_drop_count = 0
        _bridge_selfloop_drop_count = 0
        _prefer_direct_stun = {_audio_sock: False, _video_sock: False}

        # --- ICE STUN responder (runs while reservation sockets are still open) #
        # Two-phase window:
        #   Normal (no echo-reversal): exit after 0.5 s idle, max 2.5 s total.
        #   Echo-reversal, SDES confirmed (webrtcResp sent): up to 20 s for ICE.
        #   Echo-reversal, dtls_fallback_ok (webrtcResp suppressed): 5 s max -
        #     camera won't stream without webrtcResp so no STUN/SRTP will arrive;
        #     exit quickly so the DTLS fallback starts sooner.
        #   SRTP early exit: if a non-STUN packet arrives (SRTP), ICE is done -
        #     close sockets immediately so ffmpeg can bind.
        import struct as _struct
        import select as _select
        _STUN_MAGIC = b'\x21\x12\xa4\x42'
        _stun_count = 0
        _stun_seen = False
        _srtp_detected = False
        _stun_window_pkt_count = 0
        _turn_only_pkt_count = 0
        _camera_side_pkt_count = 0
        if not _cam_echo_received:
            _stun_max = 2.5
        elif _sdes_webrtcresp_sent:
            _stun_max = 20.0   # webrtcResp sent - camera may do ICE; give it time
        else:
            _stun_max = 5.0    # webrtcResp suppressed - no STUN expected; exit fast
        _idle_limit = 1.5      # exit after ICE silence once first STUN seen
        _pre_stun_idle = 0.5   # exit early if no packet at all (non-ICE camera)
        _stun_deadline = time.monotonic() + _stun_max
        _last_pkt_t = time.monotonic()
        for _rsock in (_audio_sock, _video_sock):
            try:
                _rsock.setblocking(False)
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_is_self_peer_ip', exc_info=True)
        while time.monotonic() < _stun_deadline:
            # Idle-exit: threshold depends on whether we've seen any STUN yet
            idle = time.monotonic() - _last_pkt_t
            if _stun_seen:
                if idle > _idle_limit:
                    break   # ICE done (silence after STUN) - ffmpeg will pick up SRTP
            elif not _cam_echo_received and idle > _pre_stun_idle:
                break       # no STUN at all - non-ICE camera, skip window
            try:
                _rlist, _, _ = _select.select(
                    [_audio_sock, _video_sock], [], [], 0.1
                )
            except Exception:
                break
            for _sock in _rlist:
                _last_pkt_t = time.monotonic()
                try:
                    _pkt, _src = _sock.recvfrom(2048)
                except OSError:
                    continue
                _stun_window_pkt_count += 1
                if _stun_window_pkt_count <= 3 or _stun_window_pkt_count % 50 == 0:
                    _LOGGER.debug(
                        "STUN window: %d bytes from %s:%d, first4=%s (pkt#%d)",
                        len(_pkt), _src[0], _src[1], _pkt[:4].hex(),
                        _stun_window_pkt_count,
                    )
                # Track packets that are only TURN server control responses
                # (Allocate/permission challenge/success), which do not indicate
                # that the camera actually started ICE checks.
                if _src[0] == _hp_host:
                    _turn_only_pkt_count += 1
                else:
                    _camera_side_pkt_count += 1
                # --- TURN Data Indication (type 0x0017): strip wrapper --------- #
                # Camera ICE probes routed via TURN arrive as Data Indications from
                # 3.230.182.123:5349 (the control channel, already NAT-mapped).
                # Strip the TURN envelope to get the inner STUN Binding Request so
                # we can respond correctly via a TURN Send Indication.
                _turn_peer_ip_sw: "Optional[str]" = None
                _turn_peer_port_sw: "Optional[int]" = None
                if (len(_pkt) >= 20
                        and _pkt[:2] == b'\x00\x17'
                        and _pkt[4:8] == _STUN_MAGIC):
                    _sw_off = 20
                    _sw_inner = None
                    while _sw_off + 4 <= len(_pkt):
                        _sw_at, _sw_al = _struct.unpack_from('!HH', _pkt, _sw_off)
                        _sw_av = _pkt[_sw_off + 4:_sw_off + 4 + _sw_al]
                        _sw_off += 4 + _sw_al + (-_sw_al % 4)
                        if _sw_at == 0x0012 and _sw_al >= 8:  # XOR-PEER-ADDRESS
                            _sw_xp = _struct.unpack_from('!H', _sw_av, 2)[0] ^ 0x2112
                            _sw_xb = bytes(
                                a ^ b for a, b in zip(_sw_av[4:8], _STUN_MAGIC, strict=False)
                            )
                            _turn_peer_ip_sw = '.'.join(str(b) for b in _sw_xb)
                            _turn_peer_port_sw = _sw_xp
                        elif _sw_at == 0x0013:  # DATA
                            _sw_inner = _sw_av
                    if _sw_inner:
                        _pkt = _sw_inner  # process inner payload
                    # TURN Data Indication with XOR-PEER-ADDRESS means the
                    # camera (or its relay) is actively talking to us.
                    if _turn_peer_ip_sw:
                        _camera_side_pkt_count += 1

                if (len(_pkt) >= 20 and _pkt[4:8] == _STUN_MAGIC):
                    # STUN packet - only Binding Requests (0x0001) indicate that
                    # ICE is active.  Error/Success responses (e.g. hole-punch
                    # replies) must NOT set _stun_seen or they'd trigger the 1.5s
                    # idle-exit prematurely, before camera probes arrive.
                    if _pkt[:2] == b'\x00\x01':
                        # Binding Request - reply with Binding Success Response
                        _stun_seen = True
                        if _turn_peer_ip_sw is None and _src[0] != _hp_host:
                            _prefer_direct_stun[_sock] = True
                        _tid = _pkt[8:20]
                        try:
                            # Use TURN peer address for XOR-MAPPED-ADDRESS when
                            # request arrived via TURN Data Indication.
                            _resp_src_ip = _turn_peer_ip_sw or _src[0]
                            _resp_src_port = _turn_peer_port_sw or _src[1]
                            _resp = _build_stun_binding_success_response(
                                transaction_id=_tid,
                                mapped_ip=_resp_src_ip,
                                mapped_port=_resp_src_port,
                                mi_password=(
                                    _pwd_a if _sock is _audio_sock else _pwd_v
                                ),
                                magic_cookie=_STUN_MAGIC,
                            )
                            if _turn_peer_ip_sw and _prefer_direct_stun.get(_sock, False):
                                pass
                            elif (_turn_peer_ip_sw and _sock in _relay_addrs
                                    and not _is_self_peer_ip(_turn_peer_ip_sw)):
                                # Arrived via TURN - respond via Send Indication
                                _ri_sw = _relay_addrs[_sock]
                                _t_host_sw, _t_port_sw = _ri_sw[4], _ri_sw[5]
                                _si_pip = bytes(
                                    int(x) for x in _turn_peer_ip_sw.split('.')
                                )
                                _si_xip = bytes(
                                    a ^ b for a, b in zip(_si_pip, _STUN_MAGIC, strict=False)
                                )
                                _si_xport = (_turn_peer_port_sw ^ 0x2112) & 0xFFFF
                                _si_xpa = (b'\x00\x01'
                                           + _struct.pack('!H', _si_xport)
                                           + _si_xip)

                                def _build_stun_attr(_t, _v):
                                    _p = (-len(_v)) % 4
                                    return (_struct.pack('!HH', _t, len(_v))
                                            + _v + b'\x00' * _p)

                                _si_body = _build_stun_attr(0x0012, _si_xpa) + _build_stun_attr(0x0013, _resp)
                                _send_ind = (b'\x00\x16'
                                             + _struct.pack('!H', len(_si_body))
                                             + _STUN_MAGIC + os.urandom(12)
                                             + _si_body)
                                _sock.sendto(_send_ind, (_t_host_sw, _t_port_sw))
                            elif _turn_peer_ip_sw and _is_self_peer_ip(_turn_peer_ip_sw):
                                # Self-loop Data Indication (peer == our own
                                # local/srflx address). Responding via TURN
                                # creates an endless STUN echo loop and no media.
                                # Drop it and wait for real camera checks.
                                _selfloop_drop_count += 1
                                if _selfloop_drop_count <= 5 or _selfloop_drop_count % 50 == 0:
                                    _LOGGER.debug(
                                        "STUN window: drop TURN self-loop peer %s:%s"
                                        " (count=%d)",
                                        _turn_peer_ip_sw, _turn_peer_port_sw,
                                        _selfloop_drop_count,
                                    )
                            else:
                                _sock.sendto(_resp, _src)
                            _stun_count += 1
                        except Exception:
                            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_is_self_peer_ip', exc_info=True)
                else:
                    # Non-STUN packet = SRTP arriving - ICE is done, hand off to ffmpeg now
                    _srtp_detected = True
                    break   # inner per-socket loop
            if _srtp_detected:
                break       # outer while loop
        for _rsock in (_audio_sock, _video_sock):
            try:
                _rsock.setblocking(True)
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_is_self_peer_ip', exc_info=True)
        if _stun_count:
            _status(f"ICE: responded to {_stun_count} STUN binding request(s)")
        elif not _srtp_detected:
            if _stun_window_pkt_count and _turn_only_pkt_count == _stun_window_pkt_count:
                _status(
                    "ICE: no camera probes seen in STUN window"
                    f" (received {_stun_window_pkt_count} TURN-server control packet(s) only)"
                )
            else:
                _status(
                    "ICE: 0 camera STUN binding requests in window"
                    f" (total packets={_stun_window_pkt_count})"
                )
        if _srtp_detected:
            _status("SRTP detected - exiting STUN window, handing off to ffmpeg")

        # --- Reconnect retry: camera may quickConn during synchronous STUN window #
        # LK.IPC.A001064 performs an MQTT disconnect+reconnect (quickConn) after
        # receiving WebRTC signaling.  The synchronous select() loop above blocks
        # asyncio entirely, so camera_reconnect_ev.set() is queued but not
        # processed until asyncio.sleep(0) runs.  After flushing the queue, check
        # for the reconnect and re-send webrtcResp + ICE candidates so the camera
        # can restart its ICE agent.  Allow up to 2 retries.
        _sdes_retries = 0
        while (_cam_echo_received
               and _stun_count == 0
               and not _srtp_detected
               and camera_reconnect_ev is not None
               and _sdes_retries < 2):
            await asyncio.sleep(0)   # flush queued callbacks (reconnect_ev.set())
            if not camera_reconnect_ev.is_set():
                break
            camera_reconnect_ev.clear()
            _sdes_retries += 1
            _status(
                f"camera reconnected during SDES ICE window (retry {_sdes_retries})"
                " - re-sending webrtcResp + ICE candidates"
            )
            await asyncio.sleep(0.3)   # let camera re-subscribe before re-send
            if _webrtc_resp_sdes is not None and _sdes_webrtcresp_sent:
                # Only re-send webrtcResp if we sent it originally (isDTLS='0').
                # For dtls_fallback_ok cameras the SDES session was intentionally
                # suppressed - do not retroactively start one on reconnect.
                outgoing_q.put_nowait((_webrtc_resp_sdes_topic, _webrtc_resp_sdes))
            for _ice_mid_r, _ice_port_r in (("0", audio_port), ("1", video_port)):
                _send_sdes_ice_cand(
                    f"candidate:1 1 udp 2130706431 {local_ip} {_ice_port_r}"
                    " typ host",
                    _ice_mid_r,
                )
                if _public_ip:
                    _send_sdes_ice_cand(
                        f"candidate:2 1 udp 1694498815 {_public_ip} {_ice_port_r}"
                        f" typ srflx raddr {local_ip} rport {_ice_port_r}",
                        _ice_mid_r,
                    )
                _relay_sock_r = _audio_sock if _ice_mid_r == "0" else _video_sock
                if _relay_sock_r in _relay_addrs:
                    _ri_r = _relay_addrs[_relay_sock_r]
                    _send_sdes_ice_cand(
                        f"candidate:3 1 udp 16777215 {_ri_r[0]} {_ri_r[1]}"
                        f" typ relay raddr {local_ip} rport {_ice_port_r}",
                        _ice_mid_r,
                    )
            # Refresh NAT mapping so router allows new inbound STUN from camera
            for _hp_sock_r in (_audio_sock, _video_sock):
                try:
                    _hp_sock_r.sendto(_hp_stun, (_hp_host, _hp_port))
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_is_self_peer_ip', exc_info=True)
            # Retry STUN window (8 s)
            _stun_deadline = time.monotonic() + 8.0
            _last_pkt_t = time.monotonic()
            _stun_seen = False
            while time.monotonic() < _stun_deadline:
                if _stun_seen and time.monotonic() - _last_pkt_t > _idle_limit:
                    break
                try:
                    _rlist_r, _, _ = _select.select(
                        [_audio_sock, _video_sock], [], [], 0.1
                    )
                except Exception:
                    break
                for _sk_r in _rlist_r:
                    _last_pkt_t = time.monotonic()
                    try:
                        _pkt_r, _src_r = _sk_r.recvfrom(2048)
                    except OSError:
                        continue
                    if (len(_pkt_r) >= 20
                            and _pkt_r[4:8] == _STUN_MAGIC):
                        if _pkt_r[:2] != b'\x00\x01':
                            continue   # not a Binding Request; don't trigger idle-exit
                        _stun_seen = True
                        _tid_r = _pkt_r[8:20]
                        try:
                            _resp_r = _build_stun_binding_success_response(
                                transaction_id=_tid_r,
                                mapped_ip=_src_r[0],
                                mapped_port=_src_r[1],
                                mi_password=(
                                    _pwd_a if _sk_r is _audio_sock else _pwd_v
                                ),
                                magic_cookie=_STUN_MAGIC,
                            )
                            _sk_r.sendto(_resp_r, _src_r)
                            _stun_count += 1
                        except Exception:
                            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_is_self_peer_ip', exc_info=True)
                    else:
                        _srtp_detected = True
                        break
                if _srtp_detected:
                    break
            if _stun_count:
                _status(
                    f"ICE retry {_sdes_retries}:"
                    f" responded to {_stun_count} STUN binding request(s)"
                )
            if _srtp_detected:
                _status(
                    f"SRTP detected in retry {_sdes_retries}"
                    " - exiting STUN window, handing off to ffmpeg"
                )

        # --- Harvest camera's webrtcResp answer --- #
        # The asyncio event loop was blocked by the synchronous STUN loop.  Any
        # call_soon_threadsafe(answer_fut.set_result, ...) from the MQTT thread is
        # queued but hasn't fired yet.  One asyncio cycle resolves it.
        await asyncio.sleep(0)
        # ...but one cycle only helps if the answer had already ARRIVED, and it
        # has not: the STUN window above runs on a fixed schedule that closes
        # ~2.4 s before the camera answers.  Wait for it, because the ICE
        # credentials it carries are what the nomination below needs - without
        # them the camera is never nominated, stays in ICE "Checking", and never
        # sends SRTP no matter how long the first-media wait runs.
        # shield() so a timeout here cannot cancel the future the real answer
        # await (and the DTLS-fallback path) still consume further down.
        # A TERMINAL refusal races the harvest.  A camera at its viewer cap acks
        # -50002/-50015 about a second after webrtcReq and then, by definition,
        # never answers - so without this the refusal sat unread for the whole
        # _PRE_LAUNCH_ANSWER_WAIT_S before the later checks could see it, and we
        # launched a bridge for a stream the camera had already declined.
        if not answer_fut.done():
            _ans_wait_t0 = time.monotonic()
            try:
                _harvest_waits = [asyncio.shield(answer_fut)]
                if terminal_error_fut is not None:
                    _harvest_waits.append(asyncio.shield(terminal_error_fut))
                _h_done, _h_pending = await asyncio.wait(
                    _harvest_waits,
                    timeout=_PRE_LAUNCH_ANSWER_WAIT_S,
                    return_when=asyncio.FIRST_COMPLETED,
                )
                # Cancel the shield WRAPPERS only; the futures they guard are
                # owned by the MQTT handler and are still consumed below.
                for _h in _h_pending:
                    _h.cancel()
                if terminal_error_fut is not None and terminal_error_fut.done():
                    _code, _desc = terminal_error_fut.result()
                    _status(f"camera refused: ack {_code} {_desc}"
                            " - terminal, not launching the bridge")
                    raise AidotCameraBusy(_code, _desc)
                if answer_fut.done():
                    _status("webrtcResp answer harvested after %.2fs"
                            % (time.monotonic() - _ans_wait_t0))
                else:
                    _status(
                        "no webrtcResp answer within %.0fs - proceeding without ICE"
                        " credentials (nomination will retry during the first-media"
                        " wait)" % _PRE_LAUNCH_ANSWER_WAIT_S
                    )
            except AidotCameraBusy:
                raise
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception awaiting answer",
                              getattr(self, "device_id", "?"), exc_info=True)
        _pre_launch_answer_sdp: str = ""
        _our_tx_srtp_key_audio = srtp_key_audio  # our TX key; set early in case answer absent
        _cam_key_audio: str = ""   # camera's answer key; set in SDP parse block below
        _cam_key_video: str = ""   # camera's video SRTP key; set in SDP parse block below
        _dc_answer_has_app: bool = False  # set True if camera echoes m=application; init here so bridge closure never sees NameError on late-wake path
        _sctp: dict = {              # initialized here for the same reason - bridge closure
            'state': 'CLOSED', 'local_tag': 0, 'peer_tag': 0,
            'local_tsn': 0, 'stream_seq': 0,
        }
        if answer_fut.done():
            try:
                _pre_ans = answer_fut.result()
                _pre_launch_answer_sdp = (_pre_ans or {}).get("sdp", "")
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_is_self_peer_ip', exc_info=True)
        if _pre_launch_answer_sdp:
            _LOGGER.debug(
                "_open_sdes_stream: camera webrtcResp answer SDP (len=%d)",
                len(_pre_launch_answer_sdp),
            )
            import re as _re

            def _sdes_key_from_sdp(sdp, media):
                """Return the inline key from the first a=crypto line in the named m-section."""
                in_sec = False
                for ln in sdp.splitlines():
                    if ln.startswith(f"m={media}"):
                        in_sec = True
                    elif ln.startswith("m=") and in_sec:
                        break
                    elif in_sec and ln.startswith("a=crypto:"):
                        _m = _re.search(r"inline:([A-Za-z0-9+/=]+)", ln)
                        if _m:
                            return _m.group(1)
                return ""

            # Probe result: did camera echo back m=application?
            if _dc_probe_fp:
                _dc_ans_lines, _in_dc_sec = [], False
                for _aln in _pre_launch_answer_sdp.splitlines():
                    if _aln.startswith("m=application"):
                        _in_dc_sec = True
                    elif _aln.startswith("m=") and _in_dc_sec:
                        break
                    if _in_dc_sec:
                        _dc_ans_lines.append(_aln)
                if _dc_ans_lines:
                    _status(
                        "DC probe ACCEPTED: camera answered m=application"
                        f" ({len(_dc_ans_lines)} lines):\n"
                        + "\n".join(_dc_ans_lines)
                    )
                else:
                    _status(
                        "DC probe REJECTED: camera did not include m=application"
                        " in its answer - BUNDLE+DTLS unsupported on this firmware"
                    )

            # SCTP DataChannel for SDES cameras.
            # Source analysis (f0.java g2()) confirmed SESSION_MODE_REQ (5376)
            # must be sent via DataChannel.send(), NOT raw TUTK 0xC8 UDP.
            # Camera uses plain SCTP over UDP (same ICE port), no DTLS.
            # We are the SCTP client (proactive INIT), camera is server.
            _dc_answer_has_app = "m=application" in _pre_launch_answer_sdp
            _sctp = {
                'state': 'CLOSED',    # INIT_SENT -> COOKIE_ECHOED -> ESTABLISHED -> DONE
                'local_tag': 0,       # our verification tag (sent in INIT)
                'peer_tag': 0,        # camera's verification tag (from INIT-ACK)
                'local_tsn': 0,       # our TSN counter
                'stream_seq': 0,      # stream sequence number
            }

            _cam_key_audio = _sdes_key_from_sdp(_pre_launch_answer_sdp, "audio")
            _cam_key_video = _sdes_key_from_sdp(_pre_launch_answer_sdp, "video")
            if _cam_key_audio and _cam_key_audio != srtp_key_audio:
                _status("Using camera's audio SRTP key from answer (was our offer key)")
                srtp_key_audio = _cam_key_audio
            if _cam_key_video and _cam_key_video != srtp_key_video:
                _status("Using camera's video SRTP key from answer (was our offer key)")
                srtp_key_video = _cam_key_video
            # Rewrite SDP file on disk with the (potentially updated) keys.
            # TUTK cameras (A001064/A001513) probe with TUTK 0xC8 frames then
            # switch to SRTP after the stream trigger.  Use SAVPF + crypto so
            # ffmpeg can decrypt real SRTP.  Synthesized fake-RTP probe packets
            # are plain and will fail SRTP auth (ffmpeg skips them); real SRTP
            # audio/video will decode correctly.
            _ts = int(time.time())
            if _use_plain_rtp:
                # RTP/AVP (no crypto) because we decrypt SDES ourselves and
                # forward plain RTP to ffmpeg; ffmpeg must not expect SRTP auth.
                _updated_sdp = (
                    "v=0\r\n"
                    f"o=- {_ts} {_ts} IN IP4 0.0.0.0\r\n"
                    "s=aidot-tutk-rx\r\n"
                    "t=0 0\r\n"
                    f"m=audio {audio_port} RTP/AVP 0 8\r\n"
                    "c=IN IP4 0.0.0.0\r\n"
                    "a=rtpmap:0 PCMU/8000\r\n"
                    "a=rtpmap:8 PCMA/8000\r\n"
                    "a=rtcp-mux\r\n"
                    f"m=video {video_port} RTP/AVP 96 97 98\r\n"
                    "c=IN IP4 0.0.0.0\r\n"
                    "a=rtpmap:96 H264/90000\r\n"
                    "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
                    "profile-level-id=42e01f\r\n"
                    "a=rtpmap:97 H265/90000\r\n"
                    "a=fmtp:97 level-id=93\r\n"
                    "a=rtpmap:98 H265/90000\r\n"
                    "a=fmtp:98 level-id=93\r\n"
                    "a=rtcp-mux\r\n"
                )
            else:
                _updated_sdp = (
                    "v=0\r\n"
                    f"o=- {_ts} {_ts} IN IP4 0.0.0.0\r\n"
                    "s=aidot-sdes-rx\r\n"
                    "t=0 0\r\n"
                    f"m=audio {audio_port} RTP/SAVPF 0 8\r\n"
                    "c=IN IP4 0.0.0.0\r\n"
                    f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}\r\n"
                    "a=rtpmap:0 PCMU/8000\r\n"
                    "a=rtpmap:8 PCMA/8000\r\n"
                    "a=rtcp-mux\r\n"
                    f"m=video {video_port} RTP/SAVPF 96 97\r\n"
                    "c=IN IP4 0.0.0.0\r\n"
                    f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_video}\r\n"
                    "a=rtpmap:96 H264/90000\r\n"
                    "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
                    "profile-level-id=42e01f\r\n"
                    "a=rtpmap:97 H265/90000\r\n"
                    "a=fmtp:97 level-id=93\r\n"
                    "a=rtcp-mux\r\n"
                )
            try:
                await asyncio.get_running_loop().run_in_executor(
                    None, lambda: _write_text_file(sdp_path, _inject_sprop(_updated_sdp, self.device_id)))
            except Exception as _sdp_exc:
                _LOGGER.warning("_open_sdes_stream: could not rewrite SDP: %s", _sdp_exc)

        # --- SCTP helper functions (always defined - even when answer arrives late) --- #
        def _crc32c_fn(data):
            crc = 0xFFFFFFFF
            for b in data:
                crc ^= b
                for _ in range(8):
                    crc = (crc >> 1) ^ (0x82F63B78 if crc & 1 else 0)
            return crc ^ 0xFFFFFFFF

        def _sctp_pkt(vtag, *chunks):
            import struct as _st_sc
            base = _st_sc.pack('!HHII', 5000, 5000, vtag, 0) + b''.join(chunks)
            crc = _crc32c_fn(base)
            return base[:8] + _st_sc.pack('<I', crc) + base[12:]  # camera usrsctp uses LE CRC32c

        def _sctp_chunk(ctype, flags, data):
            import struct as _st_sc
            n = 4 + len(data)
            raw = _st_sc.pack('!BBH', ctype, flags, n) + data
            return raw + b'\x00' * ((-len(raw)) % 4)

        def _sctp_init():
            import struct as _st_sc
            import random as _r_sc
            if _sctp['local_tag'] == 0:
                _sctp['local_tag'] = _r_sc.randint(1, 0xFFFFFFFF)
                _sctp['local_tsn'] = _r_sc.randint(1, 0xFFFFFFFF)
            body = _st_sc.pack('!IIHHI', _sctp['local_tag'],
                               131072, 1024, 2048, _sctp['local_tsn'])
            return _sctp_pkt(0, _sctp_chunk(0x01, 0, body))

        def _sctp_parse_init_ack(pkt):
            import struct as _st_sc
            pos = 12
            while pos + 4 <= len(pkt):
                ctype, _, clen = _st_sc.unpack_from('!BBH', pkt, pos)
                if clen < 4:
                    break
                cdata = pkt[pos + 4:pos + clen]
                if ctype == 0x02 and len(cdata) >= 16:
                    peer_tag = _st_sc.unpack_from('!I', cdata)[0]
                    peer_tsn = _st_sc.unpack_from('!I', cdata, 12)[0]
                    _sctp['peer_tag'] = peer_tag
                    _sctp['local_tsn'] = peer_tsn
                    pp = 16
                    while pp + 4 <= len(cdata):
                        ptype, plen = _st_sc.unpack_from('!HH', cdata, pp)
                        if plen < 4:
                            break
                        if ptype == 7:  # State Cookie
                            return cdata[pp + 4:pp + plen]
                        pp += max(4, (plen + 3) & ~3)
                pos += max(4, (clen + 3) & ~3)
            return None

        def _sctp_parse_init(pkt):
            import struct as _st_sc
            pos = 12
            while pos + 4 <= len(pkt):
                ctype, _, clen = _st_sc.unpack_from('!BBH', pkt, pos)
                if clen < 4:
                    break
                cdata = pkt[pos + 4:pos + clen]
                if ctype == 0x01 and len(cdata) >= 16:
                    peer_tag = _st_sc.unpack_from('!I', cdata)[0]
                    peer_tsn = _st_sc.unpack_from('!I', cdata, 12)[0]
                    _sctp['peer_tag'] = peer_tag
                    _sctp['peer_tsn'] = peer_tsn
                    return peer_tag
                pos += max(4, (clen + 3) & ~3)
            return None

        def _sctp_init_ack_pkt():
            import struct as _st_sc
            import random as _r_sc
            # RFC 4960 section 5.2.1: reuse local_tag/tsn from our INIT in simultaneous open
            if _sctp['local_tag'] == 0:
                _sctp['local_tag'] = _r_sc.randint(1, 0xFFFFFFFF)
                _sctp['local_tsn'] = _r_sc.randint(1, 0xFFFFFFFF)
            cookie = _st_sc.pack('!II', _sctp['local_tag'], _sctp['peer_tag'])
            cookie_param = _st_sc.pack('!HH', 7, 4 + len(cookie)) + cookie
            body = (_st_sc.pack('!IIHHI', _sctp['local_tag'],
                                131072, 1024, 2048, _sctp['local_tsn'])
                    + cookie_param)
            return _sctp_pkt(_sctp['peer_tag'], _sctp_chunk(0x02, 0, body))

        def _sctp_cookie_echo(cookie):
            return _sctp_pkt(_sctp['peer_tag'], _sctp_chunk(0x0A, 0, cookie))

        def _sctp_data(ppid, payload):
            import struct as _st_sc
            tsn = _sctp['local_tsn']
            _sctp['local_tsn'] = (tsn + 1) & 0xFFFFFFFF
            seq = _sctp['stream_seq']
            _sctp['stream_seq'] = (seq + 1) & 0xFFFF
            body = _st_sc.pack('!IHHI', tsn, 0, seq, ppid) + payload
            return _sctp_chunk(0x00, 0x03, body)

        def _dcep_open_msg():
            import struct as _st_sc
            label = b'data'
            return (_st_sc.pack('!BBHIHH', 0x03, 0x00, 256, 0, len(label), 0)
                    + label)

        def _session_mode_req_msg():
            import struct as _st_sc
            import random as _r_sc
            import time as _t_sc
            seq = _r_sc.randint(0, 0x7FFFFFFF)
            ts  = int(_t_sc.time() * 1000)
            return (_st_sc.pack('<IIqII4x', seq, 5376, ts, 8, 0)
                    + _st_sc.pack('<IB3x', 0, 1))

        def _sctp_send_living(sock, addr):
            dcep   = _sctp_data(50, _dcep_open_msg())
            living = _sctp_data(53, _session_mode_req_msg())
            sock.sendto(_sctp_pkt(_sctp['peer_tag'], dcep), addr)
            sock.sendto(_sctp_pkt(_sctp['peer_tag'], living), addr)
            _sctp['state'] = 'DONE'
            _status("SDES DC: sent DATA_CHANNEL_OPEN + SESSION_MODE_REQ(5376) via SCTP")

        # --- ICE controlling: send STUN Binding Requests with USE-CANDIDATE --- #
        # The camera is a full-ICE controlled agent (RFC 8445).  It sends STUN
        # binding requests to our candidates (verified above), but it will NOT
        # send SRTP until the controlling agent (us) nominates an ICE pair by
        # sending a binding request with the USE-CANDIDATE attribute (0x0025).
        # Without this, the camera stays in ICE "Checking" state indefinitely
        # (hence the continuous duplicate STUN probe log lines) and never streams.
        import re as _re_ice

        # Parse camera's ICE credentials and UDP candidates from its answer SDP.
        _cam_ice_ufrag: str = ""
        _cam_ice_pwd:   str = ""
        _cam_ice_cands: list = []   # list of (ip, port) tuples

        _cam_ice_host: tuple = ()    # (ip, port) of typ host candidate for SCTP
        if _pre_launch_answer_sdp:
            for _ice_ln in _pre_launch_answer_sdp.splitlines():
                if _ice_ln.startswith("a=ice-ufrag:") and not _cam_ice_ufrag:
                    _cam_ice_ufrag = _ice_ln[len("a=ice-ufrag:"):].strip()
                elif _ice_ln.startswith("a=ice-pwd:") and not _cam_ice_pwd:
                    _cam_ice_pwd = _ice_ln[len("a=ice-pwd:"):].strip()
                elif _ice_ln.startswith("a=candidate:"):
                    _cand_m = _re_ice.match(
                        r"a=candidate:\S+ \d+ udp \d+ ([\d.]+) (\d+) typ (\w+)",
                        _ice_ln,
                    )
                    if _cand_m:
                        _cip, _cport = _cand_m.group(1), int(_cand_m.group(2))
                        _ctyp = _cand_m.group(3)
                        _cam_ice_cands.append((_cip, _cport))
                        if _ctyp == "host" and not _cam_ice_host:
                            _cam_ice_host = (_cip, _cport)

        def _send_use_candidate(sock, our_ufrag, our_pwd, cam_ufrag, cam_pwd, cam_addr):
            """Send a STUN Binding Request with ICE-CONTROLLING + USE-CANDIDATE."""
            import struct as _st_uc
            import os as _os_uc
            import hmac as _hm_uc
            import hashlib as _hs_uc
            _MAGIC_UC = b'\x21\x12\xa4\x42'
            _tid_uc = _os_uc.urandom(12)
            _user = f"{cam_ufrag}:{our_ufrag}".encode()
            _user_a = (
                _st_uc.pack('!HH', 0x0006, len(_user))
                + _user + b'\x00' * ((-len(_user)) % 4)
            )
            _tiebreaker = int.from_bytes(_os_uc.urandom(8), 'big')
            _ctrl_a = _st_uc.pack('!HHQ', 0x802a, 8, _tiebreaker)  # ICE-CONTROLLING
            _prio_a = _st_uc.pack('!HHI', 0x0024, 4, 1845493759)   # PRIORITY (prflx)
            _uc_a   = _st_uc.pack('!HH',  0x0025, 0)               # USE-CANDIDATE
            _attrs  = _user_a + _ctrl_a + _prio_a + _uc_a
            _mi_len = len(_attrs) + 24
            _mi_in  = _st_uc.pack('!HH', 0x0001, _mi_len) + _MAGIC_UC + _tid_uc + _attrs
            _mi     = _hm_uc.new(cam_pwd.encode(), _mi_in, _hs_uc.sha1).digest()
            _mi_a   = _st_uc.pack('!HH', 0x0008, 20) + _mi
            _total  = len(_attrs) + len(_mi_a)
            # FINGERPRINT (RFC 5389 section 15.5): CRC32 XOR 0x5354554E, after MI.
            # KVS SDK silently drops binding requests without a valid FINGERPRINT.
            # MI is computed with length=_total (end-of-MI); FINGERPRINT uses
            # length=_total+8.  The camera strips FINGERPRINT before verifying MI,
            # so the MI value is unchanged.
            import zlib as _zl_uc
            _fp_total = _total + 8
            _req_for_fp = (
                _st_uc.pack('!HH', 0x0001, _fp_total)
                + _MAGIC_UC + _tid_uc + _attrs + _mi_a
            )
            _fp_val = (_zl_uc.crc32(_req_for_fp) & 0xFFFFFFFF) ^ 0x5354554E
            _fp_a   = _st_uc.pack('!HHI', 0x8028, 4, _fp_val)
            _req    = _req_for_fp + _fp_a
            try:
                sock.sendto(_req, cam_addr)
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_send_use_candidate', exc_info=True)
            # Probe the same peer a second time, out of our relay allocation.
            # These are two different candidate pairs (host->peer and
            # relay->peer) and ICE validates pairs, not addresses: without the
            # relayed copy the camera never sees a check whose source is the
            # relay candidate we advertised, so it never nominates that pair,
            # never sends media to it, and nothing ever arrives - which also
            # means the reactive Send-indication path can never bootstrap.
            # Measured on a live A001513: permissions installed and CONFIRMED
            # by the TURN server for the camera's host, srflx and relay
            # addresses, and still ZERO relay-carried inbound packets.
            # The direct send above is untouched, so directly reachable
            # cameras behave exactly as before.
            if not _is_self_peer_ip(cam_addr[0]):
                _turn_send_indication(sock, cam_addr[0], cam_addr[1], _req)

        def _nominate_from_answer_sdp(sdp_text) -> int:
            """Nominate every candidate in ``sdp_text``; return how many.

            Same parse as the post-wait path, factored out so the first-media
            wait can use it.  ``_pre_launch_answer_sdp`` is snapshotted before
            the camera has answered, so for A001513/A001064 the parse above
            finds no credentials and nothing is ever nominated - and a camera
            that never sees USE-CANDIDATE stays in ICE "Checking" and never
            sends SRTP (see the note above).  Returns 0 while the answer is
            still absent, so the caller can keep polling.
            """
            if not sdp_text:
                return 0
            _u = _p = ""
            _cands: list = []
            for _ln in sdp_text.splitlines():
                if _ln.startswith("a=ice-ufrag:") and not _u:
                    _u = _ln[len("a=ice-ufrag:"):].strip()
                elif _ln.startswith("a=ice-pwd:") and not _p:
                    _p = _ln[len("a=ice-pwd:"):].strip()
                elif _ln.startswith("a=candidate:"):
                    _m = _re_ice.match(
                        r"a=candidate:\S+ \d+ udp \d+ ([\d.]+) (\d+) typ (\w+)",
                        _ln,
                    )
                    if _m:
                        _cands.append((_m.group(1), int(_m.group(2))))
            if not (_u and _p and _cands):
                return 0
            # Open the relay's door before probing, same invariant the setup and
            # bridge paths hold: a check sent to a peer the relay has no
            # permission for is discarded, so nominating first would probe a
            # black hole on any camera we cannot reach directly.
            _turn_install_permissions(_cands, "answer")
            for _c_ip, _c_port in _cands:
                _send_use_candidate(
                    _audio_sock, _ufrag_a, _pwd_a, _u, _p, (_c_ip, _c_port))
                _send_use_candidate(
                    _video_sock, _ufrag_v, _pwd_v, _u, _p, (_c_ip, _c_port))
            return len(_cands)

        # Open the TURN relay's door for the camera BEFORE probing it.
        #
        # A TURN server drops everything from a peer until a permission exists
        # for that peer's address (RFC 5766 s9).  We advertise the relay as a
        # candidate, but the only thing that ever created a permission here was
        # a Send indication emitted while HANDLING data that had already arrived
        # through the relay - which cannot happen until the permission exists.
        # That deadlock made the advertised relay candidate a black hole:
        # measured on a live A001513 whose host could not be reached directly,
        # ZERO packets ever arrived from the relay, so the camera had no working
        # path and sent no media at all, while the same camera streamed to a
        # host it COULD reach directly.  (The DTLS path never hit this because
        # aiortc does full TURN.)
        #
        # Defined at function scope rather than inside the branch below on
        # purpose: relay-only battery cams (LAN IP unknown) answer AFTER the
        # STUN window, so they never enter that branch at all and the bridge
        # thread is the only place their permissions can be installed.
        def _turn_create_permission(_cp_sock, _cp_peer_ip, _cp_peer_port):
            """Authenticated CreatePermission for one peer on _cp_sock's relay."""
            import hashlib as _cp_hashlib
            import hmac as _cp_hmac
            import socket as _cp_socket
            import struct as _cp_struct

            _cp = _relay_addrs.get(_cp_sock)
            if not _cp or len(_cp) < 8:
                return False
            (_, _, _cp_realm, _cp_nonce, _cp_thost, _cp_tport,
             _cp_key, _cp_user) = _cp[:8]

            def _cp_attr(_t, _v):
                return (_cp_struct.pack('!HH', _t, len(_v))
                        + _v + b'\x00' * ((-len(_v)) % 4))

            # XOR-PEER-ADDRESS: family, port ^ magic[:2], addr ^ magic
            _cp_ip_b = _cp_socket.inet_aton(_cp_peer_ip)
            _cp_xport = (_cp_peer_port ^ 0x2112) & 0xFFFF
            _cp_xaddr = bytes(a ^ b for a, b in zip(_cp_ip_b, _STUN_MAGIC, strict=True))
            _cp_xpa = b'\x00\x01' + _cp_struct.pack('!H', _cp_xport) + _cp_xaddr

            _cp_tid = os.urandom(12)
            _cp_body = (_cp_attr(0x0012, _cp_xpa)          # XOR-PEER-ADDRESS
                        + _cp_attr(0x0006, _cp_user)       # USERNAME
                        + _cp_attr(0x0014, _cp_realm)      # REALM
                        + _cp_attr(0x0015, _cp_nonce))     # NONCE
            _cp_hdr = (b'\x00\x08'                        # CreatePermission request
                       + _cp_struct.pack('!H', len(_cp_body) + 24)
                       + _STUN_MAGIC + _cp_tid)
            _cp_mi = _cp_hmac.new(_cp_key, _cp_hdr + _cp_body, _cp_hashlib.sha1).digest()
            _cp_body += _cp_attr(0x0008, _cp_mi)           # MESSAGE-INTEGRITY
            _cp_msg = (b'\x00\x08' + _cp_struct.pack('!H', len(_cp_body))
                       + _STUN_MAGIC + _cp_tid + _cp_body)
            try:
                _cp_sock.sendto(_cp_msg, (_cp_thost, _cp_tport))
                return True
            except Exception:
                _LOGGER.debug("CreatePermission send failed", exc_info=True)
                return False

        def _turn_refresh_allocation(_rf_sock, _rf_lifetime=600):
            """Refresh _rf_sock's allocation before the server expires it.

            The relay is granted for a limited LIFETIME (measured against this
            camera's TURN server: 600 s) and the server tears it down silently
            when it lapses.  Stock libwebrtc, which is what the vendor app
            runs, keeps a scheduled Refresh for exactly this reason; without
            one a held session loses its relay mid-stream and the failure looks
            like the camera stopping rather than the allocation expiring.
            """
            import hashlib as _rf_hashlib
            import hmac as _rf_hmac
            import struct as _rf_struct

            _rf = _relay_addrs.get(_rf_sock)
            if not _rf or len(_rf) < 8:
                return False
            (_, _, _rf_realm, _rf_nonce, _rf_thost, _rf_tport,
             _rf_key, _rf_user) = _rf[:8]

            def _rf_attr(_t, _v):
                return (_rf_struct.pack('!HH', _t, len(_v))
                        + _v + b'\x00' * ((-len(_v)) % 4))

            _rf_tid = os.urandom(12)
            _rf_body = (_rf_attr(0x000D, _rf_struct.pack('!I', _rf_lifetime))
                        + _rf_attr(0x0006, _rf_user)      # USERNAME
                        + _rf_attr(0x0014, _rf_realm)     # REALM
                        + _rf_attr(0x0015, _rf_nonce))    # NONCE
            _rf_hdr = (b'\x00\x04'                       # Refresh request
                       + _rf_struct.pack('!H', len(_rf_body) + 24)
                       + _STUN_MAGIC + _rf_tid)
            _rf_mi = _rf_hmac.new(
                _rf_key, _rf_hdr + _rf_body, _rf_hashlib.sha1).digest()
            _rf_body += _rf_attr(0x0008, _rf_mi)          # MESSAGE-INTEGRITY
            _rf_msg = (b'\x00\x04' + _rf_struct.pack('!H', len(_rf_body))
                       + _STUN_MAGIC + _rf_tid + _rf_body)
            try:
                _rf_sock.sendto(_rf_msg, (_rf_thost, _rf_tport))
                return True
            except Exception:
                _LOGGER.debug("TURN Refresh send failed", exc_info=True)
                return False

        def _turn_send_indication(_si_sock, _si_peer_ip, _si_peer_port, _si_data):
            """Send _si_data to a peer THROUGH _si_sock's relay allocation.

            Egress from the relay address is the whole point: ICE validates a
            candidate PAIR, so a camera can only nominate the relay candidate
            we advertised if it receives packets whose source IS that relay.
            Writing straight to the camera from the host socket leaves the
            advertised relay candidate permanently unvalidated.

            Send indications carry no MESSAGE-INTEGRITY (RFC 5766 s10.2), so
            this needs the allocation's server address and nothing else.
            """
            import struct as _si_struct

            _si = _relay_addrs.get(_si_sock)
            if not _si or len(_si) < 6:
                return False
            _si_thost, _si_tport = _si[4], _si[5]

            def _si_attr(_t, _v):
                return (_si_struct.pack('!HH', _t, len(_v))
                        + _v + b'\x00' * ((-len(_v)) % 4))

            try:
                _si_ip_b = bytes(int(_o) for _o in _si_peer_ip.split('.'))
            except Exception:
                return False
            if len(_si_ip_b) != 4:
                return False
            _si_xport = (_si_peer_port ^ 0x2112) & 0xFFFF
            _si_xaddr = bytes(
                a ^ b for a, b in zip(_si_ip_b, _STUN_MAGIC, strict=True))
            _si_xpa = b'\x00\x01' + _si_struct.pack('!H', _si_xport) + _si_xaddr

            _si_body = _si_attr(0x0012, _si_xpa) + _si_attr(0x0013, _si_data)
            _si_msg = (b'\x00\x16'                     # Send indication
                       + _si_struct.pack('!H', len(_si_body))
                       + _STUN_MAGIC + os.urandom(12) + _si_body)
            try:
                _si_sock.sendto(_si_msg, (_si_thost, _si_tport))
                return True
            except Exception:
                _LOGGER.debug("TURN Send indication failed", exc_info=True)
                return False

        def _turn_install_permissions(_ip_cands, _ip_why):
            """CreatePermission for every camera candidate on both allocations.

            Non-fatal by design: a socket with no allocation is a no-op, so the
            direct path is untouched.  Returns the number of requests sent.
            """
            _perm_ok = 0
            for _c_ip, _c_port in _ip_cands:
                for _p_sock in (_audio_sock, _video_sock):
                    if _turn_create_permission(_p_sock, _c_ip, _c_port):
                        _perm_ok += 1
            if _perm_ok:
                _status(
                    f"TURN: installed {_perm_ok} relay permission(s) for"
                    f" {len(_ip_cands)} camera candidate(s) [{_ip_why}] - relay"
                    f" path is now usable if the camera cannot be reached"
                    f" directly"
                )
            elif _ip_cands and _relay_addrs:
                _status(
                    f"TURN: NO relay permission installed for {len(_ip_cands)}"
                    f" camera candidate(s) [{_ip_why}] despite"
                    f" {len(_relay_addrs)} allocation(s) - relay path stays a"
                    f" black hole if the camera cannot be reached directly"
                )
            return _perm_ok

        if _cam_ice_ufrag and _cam_ice_pwd and _cam_ice_cands:
            _turn_install_permissions(_cam_ice_cands, "setup")

            for _c_ip, _c_port in _cam_ice_cands:
                _send_use_candidate(
                    _audio_sock, _ufrag_a, _pwd_a,
                    _cam_ice_ufrag, _cam_ice_pwd, (_c_ip, _c_port),
                )
                _send_use_candidate(
                    _video_sock, _ufrag_v, _pwd_v,
                    _cam_ice_ufrag, _cam_ice_pwd, (_c_ip, _c_port),
                )
            _status(
                f"ICE controlling: sent USE-CANDIDATE to"
                f" {len(_cam_ice_cands)} camera candidate(s)"
                f" ({_cam_ice_cands[0][0]}:{_cam_ice_cands[0][1]})"
            )
            # SCTP client: INIT will be sent from the bridge thread on BindingSucc.
        else:
            _status(
                "ICE controlling: no camera ICE credentials in answer"
                " (pre-answer or relay-only path)"
            )

        # --- Bridge thread: keep reservation sockets open for ICE + SRTP ----- #
        # The camera's ICE agent sends STUN Binding Requests to audio_port and
        # video_port AFTER this point.  If we close those sockets and let ffmpeg
        # bind them, ffmpeg cannot respond to STUN -> ICE fails -> camera never
        # sends SRTP -> 0-byte output file.
        #
        # Fix: allocate fresh loopback ports for ffmpeg, rewrite the SDP to point
        # at those ports, and keep the original sockets alive in a bridge thread
        # that:
        #   - responds to STUN Binding Requests on the original sockets
        #   - forwards all non-STUN packets (SRTP) to ffmpeg's loopback ports
        # When the session ends, SdesSession.stop() closes the original sockets,
        # which causes the bridge thread's select() to raise and it exits cleanly.
        import threading as _threading_br
        import socket as _socket_br

        def _alloc_lo_port():
            _s = _socket_br.socket(_socket_br.AF_INET, _socket_br.SOCK_DGRAM)
            try:
                _s.bind(('127.0.0.1', 0))
                return _s.getsockname()[1]
            finally:
                _s.close()

        _lo_audio_port = _alloc_lo_port()
        _lo_video_port = _alloc_lo_port()

        # Rewrite SDP to point ffmpeg at the loopback ports.
        # TUTK cameras use plain RTP/AVP (no SRTP): the bridge synthesizes
        # standard RTP packets from TUTK SFrames and forwards without crypto.
        _ts_br = int(time.time())
        # (Out-of-band SPS/PPS is injected at the write below via _inject_sprop.)
        if _use_plain_rtp:
            _br_sdp = (
                "v=0\r\n"
                f"o=- {_ts_br} {_ts_br} IN IP4 0.0.0.0\r\n"
                "s=aidot-tutk-rx\r\n"
                "t=0 0\r\n"
                f"m=audio {_lo_audio_port} RTP/AVP 0 8\r\n"
                "c=IN IP4 127.0.0.1\r\n"
                "a=rtpmap:0 PCMU/8000\r\n"
                "a=rtpmap:8 PCMA/8000\r\n"
                "a=rtcp-mux\r\n"
                f"m=video {_lo_video_port} RTP/AVP 96 97\r\n"
                "c=IN IP4 127.0.0.1\r\n"
                "a=rtpmap:96 H264/90000\r\n"
                "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
                "profile-level-id=42e01f\r\n"
                "a=rtpmap:97 H265/90000\r\n"
                "a=fmtp:97 level-id=93\r\n"
                "a=rtcp-mux\r\n"
            )
        else:
            _br_sdp = (
                "v=0\r\n"
                f"o=- {_ts_br} {_ts_br} IN IP4 0.0.0.0\r\n"
                "s=aidot-sdes-rx\r\n"
                "t=0 0\r\n"
                f"m=audio {_lo_audio_port} RTP/SAVP 0 8\r\n"
                "c=IN IP4 127.0.0.1\r\n"
                f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}\r\n"
                "a=rtpmap:0 PCMU/8000\r\n"
                "a=rtpmap:8 PCMA/8000\r\n"
                "a=rtcp-mux\r\n"
                f"m=video {_lo_video_port} RTP/SAVP 96 97\r\n"
                "c=IN IP4 127.0.0.1\r\n"
                f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_video}\r\n"
                "a=rtpmap:96 H264/90000\r\n"
                "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
                "profile-level-id=42e01f\r\n"
                "a=rtpmap:97 H265/90000\r\n"
                "a=fmtp:97 level-id=93\r\n"
                "a=rtcp-mux\r\n"
            )
        try:
            await asyncio.get_running_loop().run_in_executor(
                None, lambda: _write_text_file(sdp_path, _inject_sprop(_br_sdp, self.device_id)))
        except Exception as _br_sdp_exc:
            _LOGGER.warning("bridge: could not rewrite SDP: %s", _br_sdp_exc)

        # Shared channel: bridge thread sets [0] to a persistent send function
        # once the SCTP DataChannel is established.  SdesSession reads it via
        # _cmd_chan[0] to dispatch PTZ / IOCtrl commands from the main thread.
        _cmd_chan: list = [None]
        # Bridge installs the SCTP ABORT sender here once the command
        # channel is up; teardown calls it to close the association.
        _abort_chan: list = [None]
        # The other direction: matches the camera's AVIO replies to the commands
        # that asked for them.  Created here, not in SdesSession, because the
        # bridge thread starts dispatching into it before the session object
        # exists; the session is handed the same instance so both sides share
        # one registry.
        _avio_responses = AvioResponseRouter()
        # Proc holder: set to the ffmpeg Popen object after launch so the
        # bridge thread can poll for exit without a NameError race.
        _proc_holder: list = [None]
        # Teardown holder: [0] flips True the moment ANY locally-initiated
        # ffmpeg-kill path fires (_reap() above on a cancelled cold open, the
        # key-restart proc replace, or the DTLS-fallback abort below - plus
        # SdesSession.stop() once the session is handed off, via the
        # teardown_requested= kwarg at construction).  The bridge observe loop
        # reads it via _classify_ffmpeg_exit() to demote an expected signal
        # death (our own SIGTERM/SIGKILL) from an unexpected ffmpeg crash.
        _teardown_holder: list = [False]
        # Camera's actual video payload type (96=H.264 / 97=H.265), set by the
        # bridge on the first video RTP packet so the ffmpeg SDP can be narrowed
        # to the matching single codec before ffmpeg is launched.
        _first_video_pt: list = [None]
        # Fallback for the above, filled from the camera's negotiated answer SDP
        # when the wait below expires without a single video packet. Kept in a
        # list for the same reason as _first_video_pt: the serve-restart path
        # reads it from a nested scope.
        _answer_video_pt: list = [None]
        # Same for audio: the ffmpeg SDP advertises BOTH PCMU (0) and PCMA (8),
        # and ffmpeg binds the depacketizer to the first one listed - so the line
        # has to be narrowed to the payload type the camera actually sends, the
        # same way the video line already is.
        _first_audio_pt: list = [None]
        # Media-liveness: bridge sets [0] = time.monotonic() on every forwarded
        # media packet; the keepalive watchdog reads it via SdesSession to
        # restart a session the camera silently stopped feeding.
        _media_progress: list = [0.0]
        # Shared with the bridge thread: [packets, bytes] actually forwarded to
        # ffmpeg.  The SDES path decodes nothing in-process, so on_frame never
        # fires and these counters are the only in-process proof media flowed -
        # see SdesSession.media_stats(), which the live-validation gate reads.
        _media_counts: list = [0, 0]

        def _bridge_fn():
            nonlocal _br_first_di_logged, _br_first_srtp_logged, _br_first_req_dumped
            nonlocal _br_first_audio_logged, _br_first_video_logged, _avio_living_sent
            nonlocal _bridge_selfloop_drop_count  # incremented below; needs nonlocal
            _STUN_MAGIC_BR = b'\x21\x12\xa4\x42'
            import struct as _st_br
            import select as _sel_br
            import time as _time_br
            _br_prefer_direct_stun = {_audio_sock: False, _video_sock: False}
            _br_last_uc = 0.0
            _br_last_perm = 0.0     # last CreatePermission install (monotonic)
            _br_perm_cands = ()     # candidate set those permissions covered
            # Seeded to now, not 0: the allocation was just created, so the
            # first refresh belongs one interval out, not on the first tick.
            _br_last_alloc_refresh = _time_br.monotonic()

            def _br_send_to_cam(_s, _data, _addr, _peer):
                """Reply to the camera, relayed or direct as the peer requires.

                _addr is where the packet we are answering arrived FROM; when
                the camera reached us through our relay that is the TURN
                server, and _peer carries the camera's real address so the
                payload can be wrapped.  _peer None => plain direct send.
                """
                if _peer and _turn_send_indication(_s, _peer[0], _peer[1], _data):
                    return
                _s.sendto(_data, _addr)
            _br_stun_resp_count = 0
            _tutk_trigger_sent = False
            _last_trigger_ts    = 0.0     # time of last AVIO LIVING send
            _trigger_bs         = None    # socket used for trigger
            _trigger_bsrc       = None    # camera addr for trigger
            _trigger_peer       = None    # camera's real addr when relayed
            _sdes_probe_received = False  # True after first 0xC8 probe from camera
            _last_hb_ts         = 0.0     # time of last AVIO HEARTBEAT send
            # One-shot guard for the "ffmpeg exited" log below: while the held
            # proc keeps reporting the same stale exit code across a
            # teardown-window skip (see _bridge_should_break), only the first
            # tick logs it.  Reset to False whenever the held proc is next
            # seen alive (poll() None), so a later, genuine exit of THAT proc
            # still gets its own line.
            _br_exit_logged = False
            _lo_a = _socket_br.socket(_socket_br.AF_INET, _socket_br.SOCK_DGRAM)
            _lo_v = _socket_br.socket(_socket_br.AF_INET, _socket_br.SOCK_DGRAM)
            try:
                while True:
                    try:
                        _rl, _, _ = _sel_br.select(
                            [_audio_sock, _video_sock], [], [], 0.5
                        )
                    except Exception:
                        break
                    # Stop the bridge when ffmpeg exits (normal end or crash) -
                    # but NOT on a stale exit seen during a flagged teardown
                    # window (key-restart proc replace, stop(), _reap(), the
                    # DTLS-fallback abort): _bridge_should_break() skips the
                    # break there, and the loop resolves it either via the
                    # key-restart's _proc_holder[0] repoint (next poll() None,
                    # below) or via a genuine teardown closing the loopback
                    # sockets, which raises out of the select() above.
                    _br_proc = _proc_holder[0]
                    if _br_proc is not None:
                        _br_rc = _br_proc.poll()
                        if _br_rc is not None:
                            _br_teardown_requested = bool(_teardown_holder[0])
                            if _br_rc != 0 and not _br_exit_logged:
                                _br_exit_logged = True
                                import logging as _log_br
                                _br_level = _classify_ffmpeg_exit(
                                    _br_rc, _br_teardown_requested
                                )
                                # Name the camera.  These lines used to carry no
                                # device id at all, and this account runs several
                                # SDES cameras at once, so a burst of exits could
                                # not be attributed to one of them without extra
                                # tooling - which sent a live investigation down
                                # the wrong path once already.
                                _br_dev = getattr(self, "device_id", "?")
                                _br_msg = (
                                    "camera %s: SDES bridge: ffmpeg exited with"
                                    " code %d - stopped by teardown"
                                    if _br_level < _log_br.WARNING else
                                    "camera %s: SDES bridge: ffmpeg exited with"
                                    " code %d - stream ended"
                                )
                                _log_br.getLogger(__name__).log(
                                    _br_level, _br_msg, _br_dev, _br_rc
                                )
                                if _br_level >= _log_br.WARNING:
                                    _serr_tail = getattr(
                                        _br_proc, "_aidot_stderr_tail", None)
                                    if _serr_tail:
                                        _log_br.getLogger(__name__).warning(
                                            "camera %s: SDES serve ffmpeg stderr"
                                            " (last %d lines):\n%s", _br_dev,
                                            len(_serr_tail),
                                            "\n".join(_serr_tail),
                                        )
                            if _bridge_should_break(
                                _br_rc, _br_teardown_requested
                            ):
                                break
                        else:
                            _br_exit_logged = False

                    # AVIO HEARTBEAT (cmd=5156) every 10s, sent as an ENCRYPTED SCTP
                    # DATA chunk (PPID=53) - exactly like LIVING - NOT a raw 0xC8 AVIO
                    # frame.  The camera's control channel is SCTP-inside-0xC8: a raw
                    # 0xC8 AVIO frame is not a valid SCTP packet, so the camera's SCTP
                    # layer discards it and the app-layer keepalive never lands.  With
                    # the old raw-0xC8 heartbeat the firmware tore the session down at
                    # ~18-22 s - confirmed 2026-06-02: recorded video duration was
                    # pinned at ~18 s regardless of motion (brief tap-past 18.3 s vs
                    # sustained 40 s motion 17.6 s), while raw-0xC8 heartbeats *were*
                    # firing.  The official app sends this via DataChannel.customSend
                    # (= SCTP) on a 10 s timer (f0.java Z2() -> CMD_AVIO_CTRL_HEARTHEAT_REQ).
                    #
                    # Gating on dcep_sock confines this to the encrypted-SCTP path
                    # (battery SDES cams), which is where the watchdog bites and where
                    # _enc_c8_sctp is in scope (defined in the packet loop below, set up
                    # by the time SCTP reaches DONE - same closure pattern as the LIVING
                    # send at the DCEP_WAIT branch).  Mains plain-RTP cams never sleep
                    # and leave dcep_sock None, so they simply skip the heartbeat.
                    _hb_sock = _sctp.get('dcep_sock')
                    _hb_src  = _sctp.get('dcep_src')
                    if (_sdes_probe_received
                            and _hb_sock is not None and _hb_src is not None
                            and _time_br.time() - _last_hb_ts >= 10.0):
                        import struct as _st_hb
                        import random as _r_hb
                        _hb_seq = _r_hb.randint(0, 0x7FFFFFFF)
                        _hb_ts  = int(_time_br.time() * 1000)
                        # 28-byte AVIO header, cmd=5156 (HEARTHEAT_REQ), empty payload.
                        _hb_avio = _st_hb.pack('<IIqII4x', _hb_seq, 5156, _hb_ts, 0, 0)
                        try:
                            _hb_chunk = _sctp_data(53, _hb_avio)
                            _hb_sock.sendto(
                                _enc_c8_sctp(_sctp_pkt(_sctp['peer_tag'], _hb_chunk)),  # noqa: F821
                                _hb_src,
                            )
                            _last_hb_ts = _time_br.time()
                            _status(
                                f"SDES: sent AVIO HEARTBEAT(5156) via SCTP"
                                f" TSN={(_sctp['local_tsn']-1) & 0xFFFFFFFF}"
                                f" -> {_hb_src[0]}:{_hb_src[1]}"
                            )
                        except Exception as _hb_e:
                            _status(f"SDES: SCTP heartbeat send failed: {_hb_e}")

                    # Two-way audio speaker control - MUST run on the bridge thread.
                    # SPEAKERSTART/STOP are SCTP DATA chunks; _sctp_data() mutates the
                    # shared SCTP TSN/stream-seq, so issuing them from the talk pump
                    # thread races the heartbeat here and the camera drops the chunk
                    # (no 851 ACK).  async_start_talk/async_stop_talk only flip the
                    # want_speaker flag; the actual command is sent from here once the
                    # command channel (_cmd_chan[0], set after LIVING) is up.
                    if _talk_state is not None and _cmd_chan[0] is not None:
                        if _talk_state.get("want_speaker") and not _talk_state.get("speaker_on"):
                            # Defer SPEAKERSTART ~0.6 s after the command channel comes
                            # up.  The camera ignores it if sent immediately after LIVING
                            # (validated: the working spike sent it on first-audio,
                            # ~0.58 s post-LIVING and got the 851 ACK; sending at +22 ms
                            # gets no ACK - the camera's media/audio pipeline isn't ready).
                            _now_spk = _time_br.time()
                            if _talk_state.get("spk_eligible_ts") is None:
                                _talk_state["spk_eligible_ts"] = _now_spk
                            elif _now_spk - _talk_state["spk_eligible_ts"] >= SDES_SPEAKERSTART_DELAY:
                                try:
                                    _cmd_chan[0](848, b'\x00' * 8)  # SPEAKERSTART
                                    _talk_state["speaker_on"] = True
                                    _status("SDES talk: sent SPEAKERSTART(848) (bridge thread)")
                                except Exception:
                                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                        elif _talk_state.get("speaker_on") and not _talk_state.get("want_speaker"):
                            try:
                                _cmd_chan[0](849, b'\x00' * 8)  # SPEAKERSTOP
                                _talk_state["speaker_on"] = False
                                _status("SDES talk: sent SPEAKERSTOP(849) (bridge thread)")
                            except Exception:
                                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)

                    # RTCP PLI (Picture Loss Indication) - forces camera to
                    # resend IDR + VPS/SPS/PPS so ffmpeg gets codec params.
                    # STARTUP BURST then SLOW SAFETY: fire at 5 s for the first
                    # few PLIs to win the codec-params race (the original intent),
                    # then drop to 30 s.  A *perpetual* 5 s PLI forces an IDR every
                    # 5 s, and at a fixed camera bitrate each IDR is a bandwidth
                    # spike / P-frame quality dip - a deterministic 5 s stutter
                    # cadence (the official app sends ZERO app-level PLI and relies
                    # on the camera's own GOP).  We can't decode here to detect the
                    # first keyframe, so we time-box the burst and keep a slow
                    # safety PLI in case the camera's GOP is long.
                    # P2: front-load the burst so the first decodable IDR arrives
                    # sooner on a cold open.  Was 3 PLIs at a flat 5 s (first IDR
                    # up to ~10 s); now a denser early ramp, then the same 30 s
                    # safety PLI.  AIDOT_SDES_PLI_GAPS overrides the early gaps
                    # (comma-sep seconds; e.g. "5,5,5" restores the old cadence).
                    _pli_gaps = getattr(_bridge_fn, '_pli_gaps', None)
                    if _pli_gaps is None:
                        try:
                            _pli_gaps = tuple(
                                float(_x) for _x in os.environ.get(
                                    "AIDOT_SDES_PLI_GAPS", "0,1.5,2,3").split(",")
                                if _x.strip()
                            ) or (0.0, 1.5, 2.0, 3.0)
                        except ValueError:
                            _pli_gaps = (0.0, 1.5, 2.0, 3.0)
                        _bridge_fn._pli_gaps = _pli_gaps
                    # REMB names the VIDEO stream, which is the one to
                    # limit. An earlier version rode the audio Receiver Report
                    # and so named the audio SSRC - it asked the camera to keep
                    # PCMA under the target, which it already was, and left the
                    # video untouched. Measured no change, correctly.
                    #
                    # Own cadence: the PLI timer backs off to 30s once the
                    # stream is up, which is too slow to hold a rate.
                    if (REMB_TARGET_BPS > 0
                            and hasattr(_bridge_fn, '_cam_video_ssrc')
                            and hasattr(_bridge_fn, '_cam_srtp_sock')
                            and _time_br.time() - getattr(
                                _bridge_fn, '_last_remb_ts', 0.0) >= 1.0):
                        _bridge_fn._last_remb_ts = _time_br.time()
                        try:
                            _remb_raw = build_remb(
                                0xAB12CD34,
                                [_bridge_fn._cam_video_ssrc],
                                REMB_TARGET_BPS)
                            _remb_sess = getattr(_bridge_fn, '_pli_tx_sess', None)
                            _bridge_fn._cam_srtp_sock.sendto(
                                _remb_sess.protect_rtcp(_remb_raw)
                                if _remb_sess is not None else _remb_raw,
                                _bridge_fn._cam_srtp_src,
                            )
                            if not getattr(_bridge_fn, '_remb_logged', False):
                                _bridge_fn._remb_logged = True
                                _status(
                                    f"SDES: sent REMB {REMB_TARGET_BPS // 1000} kbps"
                                    f" for video SSRC"
                                    f" 0x{_bridge_fn._cam_video_ssrc:08x}")
                        except Exception:
                            _LOGGER.debug(
                                "camera %s: swallowed exception in %s",
                                getattr(self, "device_id", "?"), '_remb',
                                exc_info=True)

                    _pli_done       = getattr(_bridge_fn, '_pli_count', 0)
                    _pli_interval   = (_pli_gaps[_pli_done]
                                       if _pli_done < len(_pli_gaps) else 30.0)
                    if (hasattr(_bridge_fn, '_cam_video_ssrc')
                            and hasattr(_bridge_fn, '_cam_srtp_sock')
                            and _time_br.time() - getattr(
                                _bridge_fn, '_last_pli_ts', 0.0) >= _pli_interval):
                        import base64 as _b64_pli
                        _pli_sender_ssrc = 0xAB12CD34
                        _pli_media_ssrc  = _bridge_fn._cam_video_ssrc
                        _pli_raw = _st_br.pack(
                            '!BBHII',
                            0x81, 206, 2,
                            _pli_sender_ssrc,
                            _pli_media_ssrc,
                        )
                        _pli_sent = False
                        try:
                            import pylibsrtp as _plsrtp_pli
                            if not hasattr(_bridge_fn, '_pli_tx_sess'):
                                _pli_key_b64 = (
                                    _our_tx_srtp_key_audio
                                    or srtp_key_audio
                                )
                                _pli_pol = _plsrtp_pli.Policy(
                                    key=_b64_pli.b64decode(_pli_key_b64),
                                    ssrc_type=_plsrtp_pli.Policy.SSRC_SPECIFIC,
                                    ssrc_value=_pli_sender_ssrc,
                                    srtp_profile=(
                                        _plsrtp_pli.Policy
                                        .SRTP_PROFILE_AES128_CM_SHA1_80),
                                )
                                _pli_pol.allow_repeat_tx = True
                                _bridge_fn._pli_tx_sess = _plsrtp_pli.Session(
                                    policy=_pli_pol)
                            _bridge_fn._cam_srtp_sock.sendto(
                                _bridge_fn._pli_tx_sess.protect_rtcp(_pli_raw),
                                _bridge_fn._cam_srtp_src,
                            )
                            _pli_sent = True
                        except Exception:
                            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                        if not _pli_sent:
                            try:
                                _bridge_fn._cam_srtp_sock.sendto(
                                    _pli_raw, _bridge_fn._cam_srtp_src)
                            except Exception:
                                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                        _bridge_fn._last_pli_ts = _time_br.time()
                        _pli_n = getattr(_bridge_fn, '_pli_count', 0) + 1
                        _bridge_fn._pli_count = _pli_n
                        if _pli_n <= len(_pli_gaps):
                            _status(
                                f"SDES: sent RTCP PLI #{_pli_n}"
                                f" -> SSRC=0x{_pli_media_ssrc:08x}"
                                f" ({'SRTCP' if _pli_sent else 'plain'})"
                            )

                    # DCEP_WAIT -> send LIVING 300ms after DCEP_OPEN.
                    # Camera needs time to register stream 0 before LIVING arrives.
                    if (_sctp.get('state') == 'DCEP_WAIT'
                            and _time_br.time() - _sctp.get('dcep_sent_ts', 0.0) >= 0.3):
                        _dw_sock = _sctp.get('dcep_sock')
                        _dw_src  = _sctp.get('dcep_src')
                        if _dw_sock and _dw_src:
                            try:
                                _lv_dw = _sctp_data(53, _session_mode_req_msg())
                                _dw_sock.sendto(_enc_c8_sctp(_sctp_pkt(_sctp['peer_tag'], _lv_dw)), _dw_src)  # noqa: F821
                                _sctp['state'] = 'DONE'
                                _sdes_probe_received = True
                                _last_hb_ts = _time_br.time()
                                _status(
                                    f"SDES DC: DCEP_WAIT -> LIVING(5376)"
                                    f" TSN={_sctp['local_tsn']-1}"
                                    f" (300ms after DCEP_OPEN)"
                                )
                                # Build persistent command sender for PTZ/IOCtrl/talk.
                                # Computes timestamp fresh on every call so commands
                                # are not stale.  AVIO IOCtrl over SDES MUST go out as an
                                # encrypted SCTP DATA chunk (PPID=53) - identical to LIVING
                                # and the keepalive heartbeat - NOT a raw 0xC8 AVIO frame.
                                # The camera's control channel is SCTP-inside-0xC8; a raw
                                # 0xC8 AVIO frame is not a valid SCTP packet, so the camera's
                                # SCTP layer silently drops it (proven by the ~18s heartbeat
                                # watchdog cutoff that disappeared once the heartbeat was
                                # SCTP-wrapped, 2026-06-02).  _enc_c8_sctp/_sctp_data/_sctp_pkt
                                # are in scope by the time this runs (DCEP_WAIT is only reached
                                # after the encrypted-SCTP handshake defines them; same closure
                                # pattern as the LIVING send above).  dcep_sock/dcep_src are the
                                # socket+addr the SCTP handshake (and LIVING) used.
                                import struct as _st_pcmd
                                import random as _r_pcmd

                                def _persistent_sdes_cmd(_cmd, _extra=b''):
                                    _seq = _r_pcmd.randint(0, 0x7FFFFFFF)
                                    _ts  = int(_time_br.time() * 1000)
                                    _avio = _st_pcmd.pack('<IIqII4x', _seq, _cmd, _ts,
                                                          len(_extra), 0) + _extra
                                    _csock = _sctp.get('dcep_sock')
                                    _csrc  = _sctp.get('dcep_src')
                                    if _csock is None or _csrc is None:
                                        return
                                    try:
                                        _chunk = _sctp_data(53, _avio)
                                        _csock.sendto(
                                            _enc_c8_sctp(_sctp_pkt(_sctp['peer_tag'], _chunk)),
                                            _csrc,
                                        )
                                    except Exception:
                                        _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_persistent_sdes_cmd', exc_info=True)

                                def _persistent_sdes_abort():
                                    """ABORT the association from the same socket the commands use.

                                    Lives here rather than in the bridge's main
                                    loop because this is where the encrypted
                                    SCTP context actually is - the dcep socket,
                                    the peer tag and the encrypt helper. An
                                    earlier attempt to send it from the media
                                    loop referenced none of them and would not
                                    have compiled.
                                    """
                                    _csock = _sctp.get('dcep_sock')
                                    _csrc  = _sctp.get('dcep_src')
                                    if _csock is None or _csrc is None:
                                        return False
                                    _csock.sendto(
                                        _enc_c8_sctp(_sctp_pkt(_sctp['peer_tag'],
                                                               _sctp_abort_chunk())),
                                        _csrc,
                                    )
                                    return True

                                _cmd_chan[0] = _persistent_sdes_cmd
                                _abort_chan[0] = _persistent_sdes_abort
                            except Exception as _dw_e:
                                _status(f"SDES DC: DCEP_WAIT LIVING err: {_dw_e}")

                    # Periodic retrigger: resend AVIO LIVING every 2s until probe
                    # received (camera acknowledged our trigger).
                    if (_avio_living_sent
                            and not _sdes_probe_received
                            and _trigger_bs is not None
                            and _time_br.time() - _last_trigger_ts >= 2.0):
                        import struct as _st_re2
                        import random as _r_re2
                        _re_ts  = int(_time_br.time() * 1000)
                        _re_seq  = _r_re2.randint(0, 0x7FFFFFFF)
                        _re_plain = (
                            _st_re2.pack('<IIqII4x', _re_seq, 5376, _re_ts, 28, 0)
                            + _st_re2.pack('<IIIIIII', 0, 0, 1, 0, 0, 0, 0)
                        )  # 56B
                        _re_enc = None
                        try:
                            from Crypto.Cipher import AES as _AES_re2
                            from Crypto.Util.Padding import pad as _pad_re2
                            _re_key = _our_tx_srtp_key_audio[:16].encode('ascii')
                            _re_iv  = (_cam_key_audio or _our_tx_srtp_key_audio)[:16].encode('ascii')
                            _re_enc = _AES_re2.new(
                                _re_key, _AES_re2.MODE_CBC, _re_iv
                            ).encrypt(_pad_re2(_re_plain, 16))
                        except Exception:
                            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                        for _rp in [_re_enc, _re_plain]:
                            if _rp is None:
                                continue
                            _rsz = len(_rp)
                            try:
                                _br_send_to_cam(
                                    _trigger_bs,
                                    bytes([0xC8, 0x00, _rsz >> 8, _rsz & 0xFF]) + _rp,
                                    _trigger_bsrc,
                                    _trigger_peer,
                                )
                            except Exception:
                                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                        _last_trigger_ts = _time_br.time()

                    for _bs in _rl:
                        try:
                            _bpkt, _bsrc = _bs.recvfrom(4096)
                        except OSError:
                            continue
                        # --- TURN Data Indication: strip wrapper before dispatch --- #
                        # Camera SRTP and late ICE probes may arrive wrapped in
                        # TURN Data Indications (type 0x0017) on the TURN control
                        # channel (3.230.182.123:5349).  Strip to get the inner
                        # payload, record the peer address for response routing.
                        _br_turn_peer_ip = None
                        _br_turn_peer_port = None
                        if (len(_bpkt) >= 20
                                and _bpkt[:2] == b'\x00\x17'
                                and _bpkt[4:8] == _STUN_MAGIC_BR):
                            if not _br_first_di_logged:
                                _br_first_di_logged = True
                                _status(
                                    f"bridge: first Data Indication from"
                                    f" {_bsrc[0]}:{_bsrc[1]}"
                                )
                            _br_off = 20
                            _br_inner = None
                            while _br_off + 4 <= len(_bpkt):
                                _br_at, _br_al = _st_br.unpack_from(
                                    '!HH', _bpkt, _br_off)
                                _br_av = _bpkt[_br_off + 4:_br_off + 4 + _br_al]
                                _br_off += 4 + _br_al + (-_br_al % 4)
                                if _br_at == 0x0012 and _br_al >= 8:  # XOR-PEER-ADDRESS
                                    _br_xp = (_st_br.unpack_from(
                                        '!H', _br_av, 2)[0] ^ 0x2112)
                                    _br_xb = bytes(
                                        a ^ b for a, b in zip(
                                            _br_av[4:8], _STUN_MAGIC_BR, strict=False)
                                    )
                                    _br_turn_peer_ip = '.'.join(
                                        str(b) for b in _br_xb)
                                    _br_turn_peer_port = _br_xp
                                elif _br_at == 0x0013:  # DATA
                                    _br_inner = _br_av
                            if _br_inner:
                                _bpkt = _br_inner

                        # When this packet came through our relay, _bsrc is the
                        # TURN server, not the camera.  Everything we send back
                        # in response therefore has to be wrapped in a Send
                        # indication addressed to the camera's real address -
                        # writing raw SRTP or AVIO bytes to the TURN server
                        # would be parsed as a malformed STUN message and
                        # dropped.  None when the camera reached us directly,
                        # which keeps the direct path byte-for-byte unchanged.
                        _br_cam_peer = (
                            (_br_turn_peer_ip, _br_turn_peer_port)
                            if _br_turn_peer_ip and _br_turn_peer_port
                            and not _is_self_peer_ip(_br_turn_peer_ip)
                            else None
                        )

                        if (len(_bpkt) >= 20
                                and _bpkt[4:8] == _STUN_MAGIC_BR
                                and _bpkt[:2] == b'\x00\x01'):
                            # STUN Binding Request - send Binding Success Response
                            if not _br_first_req_dumped:
                                _br_first_req_dumped = True
                                _attrs = []
                                _o = 20
                                while _o + 4 <= len(_bpkt):
                                    _at, _al = _st_br.unpack_from('!HH', _bpkt, _o)
                                    _attrs.append(f"0x{_at:04x}/{_al}")
                                    _o += 4 + _al + (-_al % 4)
                                _status(
                                    f"bridge: first BindingReq from"
                                    f" {_bsrc[0]}:{_bsrc[1]}"
                                    f" attrs=[{', '.join(_attrs)}]"
                                )
                            # Learn where this probe actually came from.  The
                            # camera reached us from here, so this address is
                            # known-good in a way an advertised one is not.
                            # Relay-carried probes carry the camera's real
                            # address in XOR-PEER-ADDRESS; _bsrc is the TURN
                            # server and would be useless to nominate.
                            _br_obs = (
                                _br_cam_peer if _br_cam_peer
                                else (None if _bsrc[0] == _hp_host else _bsrc)
                            )
                            _br_prflx_was = _bridge_uc_info["prflx"]
                            _br_prflx_now = _record_peer_reflexive(
                                _bridge_uc_info["cands"], _br_prflx_was,
                                _br_obs, _is_self_peer_ip,
                            )
                            if _br_prflx_now is not _br_prflx_was:
                                # Rebind, never append: the nomination tick
                                # iterates this list.
                                _bridge_uc_info["prflx"] = _br_prflx_now
                                _status(
                                    f"ICE: learned peer-reflexive camera"
                                    f" candidate {_br_obs[0]}:{_br_obs[1]}"
                                    f" (not in the {len(_bridge_uc_info['cands'])}"
                                    f" advertised candidate(s)) - will nominate it"
                                )
                            try:
                                if _br_turn_peer_ip is None and _bsrc[0] != _hp_host:
                                    _br_prefer_direct_stun[_bs] = True
                                _btid = _bpkt[8:20]
                                # Use TURN peer address when arrived via relay
                                _bresp_ip = _br_turn_peer_ip or _bsrc[0]
                                _bresp_port = _br_turn_peer_port or _bsrc[1]
                                _bresp = _build_stun_binding_success_response(
                                    transaction_id=_btid,
                                    mapped_ip=_bresp_ip,
                                    mapped_port=_bresp_port,
                                    mi_password=(
                                        _pwd_a if _bs is _audio_sock else _pwd_v
                                    ),
                                    magic_cookie=_STUN_MAGIC_BR,
                                )
                                if _br_turn_peer_ip and _br_prefer_direct_stun.get(_bs, False):
                                    pass
                                elif (_br_turn_peer_ip and _bs in _relay_addrs
                                        and not _is_self_peer_ip(_br_turn_peer_ip)):
                                    # Arrived via TURN - respond via Send Indication
                                    _bri = _relay_addrs[_bs]
                                    _br_t_host, _br_t_port = _bri[4], _bri[5]
                                    _br_pip = bytes(
                                        int(x) for x in _br_turn_peer_ip.split('.')
                                    )
                                    _br_xip2 = bytes(
                                        a ^ b for a, b in zip(
                                            _br_pip, _STUN_MAGIC_BR, strict=False)
                                    )
                                    _br_xport2 = (
                                        _br_turn_peer_port ^ 0x2112) & 0xFFFF
                                    _br_xpa = (b'\x00\x01'
                                               + _st_br.pack('!H', _br_xport2)
                                               + _br_xip2)

                                    def _build_stun_attr(_t, _v):
                                        _p = (-len(_v)) % 4
                                        return (_st_br.pack('!HH', _t, len(_v))
                                                + _v + b'\x00' * _p)

                                    _br_si_body = (
                                        _build_stun_attr(0x0012, _br_xpa)
                                        + _build_stun_attr(0x0013, _bresp)
                                    )
                                    _br_send_ind = (
                                        b'\x00\x16'
                                        + _st_br.pack('!H', len(_br_si_body))
                                        + _STUN_MAGIC_BR + os.urandom(12)
                                        + _br_si_body
                                    )
                                    _bs.sendto(_br_send_ind, (_br_t_host, _br_t_port))
                                elif _br_turn_peer_ip and _is_self_peer_ip(_br_turn_peer_ip):
                                    _bridge_selfloop_drop_count += 1
                                    if (_bridge_selfloop_drop_count <= 5
                                            or _bridge_selfloop_drop_count % 50 == 0):
                                        _LOGGER.debug(
                                            "bridge: drop TURN self-loop STUN peer %s:%d"
                                            " (count=%d)",
                                            _br_turn_peer_ip, _br_turn_peer_port,
                                            _bridge_selfloop_drop_count,
                                        )
                                else:
                                    _bs.sendto(_bresp, _bsrc)
                                    _br_stun_resp_count += 1
                                # Late USE-CANDIDATE: send when answer arrived
                                # after bridge started (empty at setup time).
                                if not _bridge_uc_info["sent"] and _bridge_uc_info["ufrag"]:
                                    _bridge_uc_info["sent"] = True
                                    # Nominate BOTH the audio and video sockets, not
                                    # just the one that happened to receive this probe.
                                    # The early (answer-in-time) path nominates both;
                                    # the late path previously nominated only _bs (the
                                    # socket with the first BindingReq, typically audio),
                                    # so the video pair was never nominated and the
                                    # camera never started video RTP (audio/control
                                    # recovered, video stayed dark).
                                    for _br_ci, _br_cp in _bridge_uc_info["cands"]:
                                        for _uc_sock, _uc_ufrag, _uc_pwd in (
                                            (_audio_sock, _ufrag_a, _pwd_a),
                                            (_video_sock, _ufrag_v, _pwd_v),
                                        ):
                                            try:
                                                _send_use_candidate(
                                                    _uc_sock, _uc_ufrag, _uc_pwd,
                                                    _bridge_uc_info["ufrag"],
                                                    _bridge_uc_info["pwd"],
                                                    (_br_ci, _br_cp),
                                                )
                                            except Exception:
                                                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                                    _status(
                                        f"bridge: late USE-CANDIDATE sent (audio+video) to"
                                        f" {len(_bridge_uc_info['cands'])} camera candidate(s)"
                                        " (answer arrived after bridge started)"
                                    )
                            except Exception:
                                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                        elif len(_bpkt) >= 20 and _bpkt[4:8] == _STUN_MAGIC_BR:
                            # CreatePermission response from the TURN server.
                            # 0x0108 = success (the relay door is open for that
                            # peer); 0x0118 = error.  438 Stale Nonce is routine
                            # and recoverable: the server hands back a fresh
                            # NONCE, so adopt it and retry once.  401 means the
                            # credentials themselves are wrong, which is worth a
                            # warning because the relay stays shut.
                            if _bpkt[:2] in (b'\x01\x08', b'\x01\x18',
                                             b'\x01\x04', b'\x01\x14'):
                                _cp_err = 0
                                _cp_new_nonce = b''
                                _cp_i = 20
                                _cp_end = min(
                                    20 + _st_br.unpack('!H', _bpkt[2:4])[0],
                                    len(_bpkt),
                                )
                                while _cp_i + 4 <= _cp_end:
                                    _cp_at, _cp_al = _st_br.unpack_from(
                                        '!HH', _bpkt, _cp_i)
                                    _cp_v = _bpkt[_cp_i + 4:_cp_i + 4 + _cp_al]
                                    if _cp_at == 0x0009 and len(_cp_v) >= 4:
                                        _cp_err = _cp_v[2] * 100 + _cp_v[3]
                                    elif _cp_at == 0x0015:
                                        _cp_new_nonce = _cp_v
                                    _cp_i += 4 + _cp_al + ((-_cp_al) % 4)
                                _cp_what = ("CreatePermission"
                                            if _bpkt[1] == 0x08 else "Refresh")
                                if _bpkt[:2] in (b'\x01\x08', b'\x01\x04'):
                                    _cp_seen = f"_cp_ok_logged_{_cp_what}"
                                    if not getattr(_bridge_fn, _cp_seen, False):
                                        setattr(_bridge_fn, _cp_seen, True)
                                        _status(
                                            f"TURN: {_cp_what} confirmed by"
                                            f" server (success)"
                                        )
                                elif _cp_err == 438 and _cp_new_nonce:
                                    _cp_old = _relay_addrs.get(_bs)
                                    if _cp_old and len(_cp_old) >= 8:
                                        _relay_addrs[_bs] = (
                                            *_cp_old[:3], _cp_new_nonce,
                                            *_cp_old[4:],
                                        )
                                        _status(
                                            f"TURN: stale nonce on {_cp_what}"
                                            " - refreshed and retrying"
                                        )
                                        # Re-arm both gates: whichever request
                                        # hit the stale nonce, the fresh one
                                        # should be used immediately.
                                        _br_last_perm = 0.0
                                        _br_perm_cands = ()
                                        _br_last_alloc_refresh = 0.0
                                else:
                                    _status(
                                        f"TURN: {_cp_what} rejected"
                                        f" (error {_cp_err or 'unknown'}) -"
                                        f" relay path unusable"
                                    )
                                continue
                            # STUN BindingSuccess (0x0101) from camera: ICE complete.
                            # Send AES-128-CBC encrypted SESSION_MODE_REQ (AVIO LIVING).
                            #
                            # Confirmed via Ghidra static analysis of arm64
                            # libjingle_peerconnection_so.so (usrsctp_transport.cc):
                            #   - FUN_0098a9b0 (EncryptPayload): AES-128-CBC, PKCS#7 padding
                            #   - Key:  base64_decode(our_sdes_inline_key)[:16]
                            #   - IV:   base64_decode(our_sdes_inline_key)[16:30] + \x00\x00
                            #   - Packet: [0xC8][0x00][len_hi][len_lo][ciphertext]
                            #     (4-byte header, NOT the 12-byte TUTK SFrame with ts/SSRC)
                            if (_use_plain_rtp and not _tutk_trigger_sent
                                    and _bpkt[:2] == b'\x01\x01'):
                                _tutk_trigger_sent = True
                                import struct as _st_tk
                                import random as _rand_tk
                                _ts_ms = int(_time_br.time() * 1000)
                                _tk_seq = _rand_tk.randint(0, 0x7FFFFFFF)
                                _avio_plain = (
                                    _st_tk.pack('<IIqII4x', _tk_seq, 5376, _ts_ms, 28, 0)
                                    + _st_tk.pack('<IIIIIII', 0, 0, 1, 0, 0, 0, 0)
                                )  # 28B AVIO header + 28B AVStream = 56B

                                # AES-128-CBC: key=our_inline[:16] ASCII, IV=cam_inline[:16] ASCII.
                                # Confirmed by Frida v8.23 (2026-05-28): first 16 raw chars of
                                # each party's a=crypto: inline string, not base64-decoded.
                                _trigger_enc = None
                                try:
                                    from Crypto.Cipher import AES as _AES_tk
                                    from Crypto.Util.Padding import pad as _pad_tk
                                    _aes_key = _our_tx_srtp_key_audio[:16].encode('ascii')
                                    _aes_iv  = (_cam_key_audio or _our_tx_srtp_key_audio)[:16].encode('ascii')
                                    _padded  = _pad_tk(_avio_plain, 16)  # PKCS#7 -> 64B
                                    _trigger_enc = _AES_tk.new(
                                        _aes_key, _AES_tk.MODE_CBC, _aes_iv
                                    ).encrypt(_padded)
                                except Exception:
                                    pass  # no pycryptodome

                                # Packet: [0xC8][0x00][ciphertext_len_BE_2B][ciphertext]
                                for _payload, _label in [
                                    (_trigger_enc, "AES-128-CBC"),
                                    (_avio_plain,  "plaintext"),
                                ]:
                                    if _payload is None:
                                        continue
                                    _sz = len(_payload)
                                    _pkt = bytes([0xC8, 0x00, _sz >> 8, _sz & 0xFF]) + _payload
                                    try:
                                        _br_send_to_cam(_bs, _pkt, _bsrc, _br_cam_peer)
                                        _status(
                                            f"SDES: sent trigger ({_label})"
                                            f" len={_sz}"
                                            f" -> {_bsrc[0]}:{_bsrc[1]}"
                                        )
                                    except Exception:
                                        _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_persistent_sdes_cmd', exc_info=True)
                                _avio_living_sent = True
                                _last_trigger_ts = _time_br.time()
                                _trigger_bs   = _bs
                                _trigger_bsrc = _bsrc
                                _trigger_peer = _br_cam_peer

                                # Camera is SCTP initiator (a=setup:active).
                                # Be pure SCTP server: wait for camera's INIT,
                                # reply INIT-ACK, wait for COOKIE-ECHO, then send data.
                                if _dc_answer_has_app:
                                    _status(
                                        "SDES DC: m=application present"
                                        " - waiting for camera SCTP INIT"
                                        f" key={_our_tx_srtp_key_audio[:8]}"
                                        f" iv={_cam_key_audio[:8] if _cam_key_audio else '(none)'}"
                                    )
                        else:
                            # Non-STUN packet - demux by first byte.
                            if _avio_living_sent and len(_bpkt) >= 4:
                                _nsl_cnt = getattr(
                                    _bridge_fn, '_non_stun_logged', 0)
                                if _nsl_cnt < 5:
                                    _LOGGER.debug(
                                        "bridge non-STUN %dB from %s:%d"
                                        " first4=%s",
                                        len(_bpkt),
                                        _bsrc[0], _bsrc[1],
                                        _bpkt[:4].hex(),
                                    )
                                    _bridge_fn._non_stun_logged = (
                                        _nsl_cnt + 1)
                            # Plain SCTP over UDP: srcPort=dstPort=5000 (0x1388).
                            # Camera is a=setup:active (SCTP client - initiates INIT).
                            # We are SCTP server: wait for camera's INIT, reply INIT-ACK,
                            # then complete: COOKIE-ECHO -> COOKIE-ACK ->
                            # DATA_CHANNEL_OPEN + SESSION_MODE_REQ(5376).
                            # Check for any SCTP-like packet: first 4 bytes are
                            # srcPort(2B) + dstPort(2B) in SCTP common header.
                            # Standard SCTP uses port 5000 (0x1388) but log ALL
                            # candidates to catch non-standard port usage.
                            _possible_sctp = (
                                len(_bpkt) >= 12
                                and _bpkt[4:8] == b'\x00\x00\x00\x00'  # vtag=0 means INIT
                                and _bpkt[12] in (0x01, 0x02, 0x0A, 0x0B, 0x06)
                            ) or (
                                len(_bpkt) >= 12
                                and _bpkt[:2] == b'\x13\x88'
                                and _bpkt[2:4] == b'\x13\x88'
                            )
                            if _possible_sctp:
                                st = _sctp['state']
                                _chunk_type = _bpkt[12] if len(_bpkt) > 12 else 0xFF
                                _status(
                                    f"SDES DC: plain SCTP {len(_bpkt)}B"
                                    f" from {_bsrc[0]}:{_bsrc[1]}"
                                    f" state={st}"
                                    f" chunk=0x{_chunk_type:02x}"
                                    f" [{_bpkt[:16].hex()}]"
                                )
                                # Secondary C: camera may be SCTP client (a=setup:active
                                # means camera initiates). If state is CLOSED and we see
                                # an INIT chunk (type=0x01), respond with INIT-ACK.
                                if st == 'CLOSED' and _chunk_type == 0x01:
                                    peer_tag = _sctp_parse_init(_bpkt)
                                    if peer_tag:
                                        _sctp['state'] = 'COOKIE_ECHOED'
                                        try:
                                            _ack_pkt = _sctp_init_ack_pkt()
                                            _br_send_to_cam(_bs, _ack_pkt, _bsrc, _br_cam_peer)
                                            _status(
                                                f"SDES DC: camera sent SCTP INIT"
                                                f" (peer_tag=0x{peer_tag:08x})"
                                                f" - sent INIT-ACK"
                                            )
                                        except Exception as _iae:
                                            _status(f"SCTP INIT-ACK failed: {_iae}")
                                elif st in ('INIT_SENT', 'COOKIE_WAIT'):
                                    cookie = _sctp_parse_init_ack(_bpkt)
                                    if cookie:
                                        _sctp['state'] = 'COOKIE_ECHOED'
                                        try:
                                            _br_send_to_cam(_bs, _sctp_cookie_echo(cookie), _bsrc, _br_cam_peer)
                                            _status(
                                                f"SDES DC: plain INIT-ACK"
                                                f" (cookie {len(cookie)}B)"
                                                f" -> sent COOKIE-ECHO"
                                            )
                                        except Exception as _sce:
                                            _status(f"SCTP COOKIE-ECHO failed: {_sce}")
                                    else:
                                        _status("SDES DC: plain SCTP pkt in INIT_SENT/COOKIE_WAIT"
                                                " - parse_init_ack found no cookie")
                                elif st == 'COOKIE_ECHOED':
                                    # Any SCTP packet (COOKIE-ACK = type 0x0B) signals
                                    # SCTP is established.
                                    _sctp['state'] = 'ESTABLISHED'
                                    _status("SDES DC: SCTP COOKIE-ACK - DataChannel OPEN")
                                    try:
                                        _sctp_send_living(_bs, _bsrc)
                                    except Exception as _sle:
                                        _LOGGER.warning("SDES DC: send living failed: %s",
                                                        _sle)
                                elif st == 'DONE':
                                    pass  # ignore further SCTP (SACKs etc.)
                                continue
                            # Log DTLS packets (first byte 0x14-0x17) if they arrive.
                            # Camera is a=setup:active (DTLS client) - if it sends
                            # DTLS ClientHello, we'd see 0x16 here.
                            if len(_bpkt) >= 4 and 0x14 <= _bpkt[0] <= 0x17:
                                _status(
                                    f"bridge: DTLS record {len(_bpkt)}B"
                                    f" from {_bsrc[0]}:{_bsrc[1]}"
                                    f" ct=0x{_bpkt[0]:02x}"
                                    f" ver={_bpkt[1:3].hex()}"
                                )
                            #
                            # Raw hex dump of all 0xC8/0xC9 packets (first 10) to verify
                            # packet format and AES key/IV derivation.
                            if (len(_bpkt) >= 1
                                    and _bpkt[0] not in (0xC8, 0xC9)
                                    and not (len(_bpkt) >= 4 and 0x14 <= _bpkt[0] <= 0x17)
                                    and _sctp.get('state') == 'DONE'):
                                if not hasattr(_bridge_fn, '_non_c8_after_done'):
                                    _bridge_fn._non_c8_after_done = 0
                                _bridge_fn._non_c8_after_done += 1
                                if _bridge_fn._non_c8_after_done <= 5:
                                    _status(
                                        f"bridge: non-0xC8 after DONE:"
                                        f" {len(_bpkt)}B byte0=0x{_bpkt[0]:02x}"
                                        f" {_bpkt[:24].hex()}"
                                    )
                            if len(_bpkt) >= 1 and _bpkt[0] in (0xC8, 0xC9):
                                if not hasattr(_bridge_fn, '_c8_raw_count'):
                                    _bridge_fn._c8_raw_count = 0
                                _bridge_fn._c8_raw_count += 1
                                if _bridge_fn._c8_raw_count <= 10:
                                    _sdes_k = _our_tx_srtp_key_audio[:16]
                                    _sdes_v = (_cam_key_audio or _our_tx_srtp_key_audio)[:16]
                                    _status(
                                        f"bridge: RAW 0x{_bpkt[0]:02x} {len(_bpkt)}B"
                                        f" #{_bridge_fn._c8_raw_count}"
                                        f" sdes_key={_sdes_k}"
                                        f" sdes_iv={_sdes_v}"
                                        f" [{_bpkt.hex()}]"
                                    )
                            # TUTK SFrame detection (A001064 / _use_plain_rtp):
                            # The camera sends TUTK-framed data instead of SRTP.
                            # Wire-capture analysis (2026-05-02) confirmed:
                            #   byte0=0xC8 -> TUTK audio SFrame
                            #   byte0=0xC9 -> TUTK video SFrame (expected)
                            # TUTK SFrame header (12 bytes):
                            #   [0]   type   (0xC8=audio, 0xC9=video)
                            #   [1]   channel/flags
                            #   [2-3] payload_size (big-endian; = packet_len - 4)
                            #   [4-7] timestamp (big-endian, camera clock)
                            #   [8-11] SSRC (big-endian; confirmed matches actual pkt ssrc)
                            # Strip TUTK header and synthesize a standard RTP/2 packet
                            # so ffmpeg (configured with RTP/AVP, no SRTP) can decode.
                            if _use_plain_rtp and len(_bpkt) >= 12 and _bpkt[0] in (0xC8, 0xC9):
                                _tk_type  = _bpkt[0]
                                _tk_ts    = int.from_bytes(_bpkt[4:8],  'big')
                                _tk_ssrc  = int.from_bytes(_bpkt[8:12], 'big')
                                _tk_audio = (_tk_type == 0xC8)
                                _tk_pt    = 8 if _tk_audio else 96   # PCMA or H264
                                _tk_payload = _bpkt[12:]
                                # Mark probe received; start heartbeat timer
                                if not _sdes_probe_received:
                                    _sdes_probe_received = True
                                    _last_hb_ts = _time_br.time()  # HB fires in 10s
                                # Guard on a counter that is actually assigned.
                                # This used to test `_tutk_seq`, a name nothing
                                # ever set, so hasattr was False on EVERY packet
                                # and both counters were reset to 0 each time -
                                # every synthesized packet went out with
                                # sequence number 1.  ffmpeg reads a constant
                                # sequence as a stream of discontinuities and
                                # reports `RTP: missed N packets` for loss that
                                # never happened.
                                if not hasattr(_bridge_fn, '_tutk_seq_v'):
                                    _bridge_fn._tutk_seq_a = 0
                                    _bridge_fn._tutk_seq_v = 0
                                if _tk_audio:
                                    _bridge_fn._tutk_seq_a = (_bridge_fn._tutk_seq_a + 1) & 0xFFFF
                                    _tk_seq = _bridge_fn._tutk_seq_a
                                    _btgt, _lo_target, _kind = _lo_audio_port, _lo_a, "audio"
                                else:
                                    _bridge_fn._tutk_seq_v = (_bridge_fn._tutk_seq_v + 1) & 0xFFFF
                                    _tk_seq = _bridge_fn._tutk_seq_v
                                    _btgt, _lo_target, _kind = _lo_video_port, _lo_v, "video"
                                # Synthesize 12-byte RTP header (V=2, no padding/ext/csrc)
                                _rtp_hdr = _st_br.pack('!BBHII',
                                    0x80,       # V=2 P=0 X=0 CC=0
                                    _tk_pt,     # M=0 PT
                                    _tk_seq,
                                    _tk_ts,
                                    _tk_ssrc,
                                )
                                _bpkt = _rtp_hdr + _tk_payload
                                if not hasattr(_bridge_fn, '_tutk_count'):
                                    _bridge_fn._tutk_count = 0
                                    _bridge_fn._tutk_first_ts = _time_br.time()
                                _bridge_fn._tutk_count += 1
                                _elapsed = _time_br.time() - _bridge_fn._tutk_first_ts
                                # Decrypt audio probe: SRTP AES-CM (RFC 3711).
                                # AES-128-CBC: key=our_inline[:16], IV=cam_inline[:16].
                                # Frida v8.23 (2026-05-28) confirmed: both encrypt and
                                # decrypt use first 16 ASCII chars of the respective
                                # a=crypto: inline strings, NOT base64-decoded content.
                                # Ciphertext starts at byte 4 (after 0xC8 header).
                                _pd_plain = None
                                if _tk_audio and _cam_key_audio:
                                    try:
                                        from Crypto.Cipher import AES as _AES_pd
                                        from Crypto.Util.Padding import unpad as _unpad_pd
                                        _pd_key = _our_tx_srtp_key_audio[:16].encode('ascii')
                                        _pd_iv  = _cam_key_audio[:16].encode('ascii')
                                        _pd_ct  = _bpkt[4:]  # full ciphertext after 4B header
                                        if len(_pd_ct) % 16 == 0 and len(_pd_ct) >= 16:
                                            _pd_plain = _unpad_pd(
                                                _AES_pd.new(_pd_key, _AES_pd.MODE_CBC, _pd_iv
                                                            ).decrypt(_pd_ct), 16)
                                    except ImportError:
                                        pass
                                    except Exception as _pde:
                                        _status(f"bridge: SDES decrypt error: {_pde}")
                                # Log for first 5 and every 10th frame
                                if _bridge_fn._tutk_count <= 5 or _bridge_fn._tutk_count % 10 == 0:
                                    _status(
                                        f"bridge: TUTK {_kind} SFrame"
                                        f" type=0x{_tk_type:02x} ssrc={_tk_ssrc}"
                                        f" ts={_tk_ts} payload={len(_tk_payload)}B"
                                        f" #{_bridge_fn._tutk_count} (+{_elapsed:.1f}s)"
                                    )
                                    if _bridge_fn._tutk_count <= 5:
                                        _status(
                                            f"bridge: TUTK raw"
                                            f" type=0x{_tk_type:02x}"
                                            f" ts={_tk_ts} ssrc=0x{_tk_ssrc:08x}"
                                            f" payload_all={_tk_payload.hex()}"
                                        )
                                        if _pd_plain is not None:
                                            _status(
                                                f"bridge: TUTK decrypt -> "
                                                f" plain_all={_pd_plain.hex()}"
                                            )
                                # -- Encrypted SCTP state machine (SDES path) --
                                # Camera sends SCTP handshake (INIT, COOKIE-ECHO, DATA)
                                # wrapped in 0xC8 AES-128-CBC frames. Handle before
                                # forwarding to ffmpeg.
                                if (_pd_plain is not None
                                        and len(_pd_plain) >= 16
                                        and _pd_plain[:4] == b'\x13\x88\x13\x88'):
                                    _pd_ct8 = _pd_plain[12] if len(_pd_plain) > 12 else 0xFF

                                    def _enc_c8_sctp(_raw):
                                        from Crypto.Cipher import AES as _ae
                                        from Crypto.Util.Padding import pad as _pa
                                        _k = _our_tx_srtp_key_audio[:16].encode('ascii')
                                        _v = _cam_key_audio[:16].encode('ascii')
                                        _e = _ae.new(_k, _ae.MODE_CBC, _v).encrypt(_pa(_raw, 16))
                                        return bytes([0xC8, 0x00, len(_e) >> 8, len(_e) & 0xFF]) + _e

                                    _sct = _sctp['state']
                                    if _pd_ct8 == 0x01 and _sct in ('CLOSED', 'INIT_SENT', 'COOKIE_WAIT'):
                                        # Camera SCTP INIT (or retransmit) -> send encrypted INIT-ACK.
                                        # Handle retransmits in COOKIE_WAIT: re-send INIT-ACK
                                        # (our first may have been lost).
                                        _sc_p = _sctp_parse_init(_pd_plain)
                                        if _sc_p:
                                            try:
                                                _iak_plain = _sctp_init_ack_pkt()
                                                _iak_bytes = _enc_c8_sctp(_iak_plain)
                                                _br_send_to_cam(_bs, _iak_bytes, _bsrc, _br_cam_peer)
                                                _sctp['state'] = 'COOKIE_WAIT'
                                                _status(
                                                    f"SDES DC: INIT(peer=0x{_sc_p:08x})"
                                                    f" -> INIT-ACK {len(_iak_bytes)}B"
                                                    f" to {_bsrc[0]}:{_bsrc[1]}"
                                                    f" plain={_iak_plain.hex()}"
                                                )
                                            except Exception as _sce8:
                                                _status(f"SDES DC: enc INIT-ACK err: {_sce8}")
                                    elif _pd_ct8 == 0x02 and _sct in ('INIT_SENT', 'COOKIE_WAIT'):
                                        # Camera SCTP INIT-ACK -> send encrypted COOKIE-ECHO
                                        _sc_ck = _sctp_parse_init_ack(_pd_plain)
                                        if _sc_ck:
                                            try:
                                                _br_send_to_cam(_bs, _enc_c8_sctp(_sctp_cookie_echo(_sc_ck)), _bsrc, _br_cam_peer)
                                                _sctp['state'] = 'COOKIE_ECHOED'
                                                _status(
                                                    f"SDES DC: enc INIT-ACK"
                                                    f" (cookie {len(_sc_ck)}B)"
                                                    f" -> sent enc COOKIE-ECHO"
                                                )
                                            except Exception as _sce8:
                                                _status(f"SDES DC: enc COOKIE-ECHO err: {_sce8}")
                                    elif _pd_ct8 == 0x0A and _sct in ('COOKIE_WAIT', 'COOKIE_ECHOED'):
                                        # Camera COOKIE-ECHO -> send COOKIE-ACK + DCEP_OPEN,
                                        # then wait 300ms before sending LIVING (PPID=53).
                                        # Without DCEP_OPEN (PPID=50), LIVING arrives on an
                                        # unregistered stream and is silently discarded by the
                                        # camera's SCTP application layer (SACK confirms transport
                                        # delivery, but no audio/video results). The DTLS path in
                                        # client.py (the COOKIE-ECHO/COOKIE-ACK + DCEP_OPEN handler)
                                        # does exactly this sleep - required.
                                        try:
                                            _cak8 = _sctp_pkt(_sctp['peer_tag'], _sctp_chunk(0x0B, 0, b''))
                                            _br_send_to_cam(_bs, _enc_c8_sctp(_cak8), _bsrc, _br_cam_peer)
                                            _dc8 = _sctp_data(50, _dcep_open_msg())
                                            _br_send_to_cam(_bs, _enc_c8_sctp(_sctp_pkt(_sctp['peer_tag'], _dc8)), _bsrc, _br_cam_peer)
                                            _sctp['state'] = 'DCEP_WAIT'
                                            _sctp['dcep_sent_ts'] = _time_br.time()
                                            _sctp['dcep_sock'] = _bs
                                            _sctp['dcep_src'] = _bsrc
                                            _status(
                                                "SDES DC: COOKIE-ECHO"
                                                " -> COOKIE-ACK + DCEP_OPEN(50),"
                                                " waiting 300ms before LIVING"
                                            )
                                        except Exception as _sce8:
                                            _status(f"SDES DC: enc COOKIE-ACK err: {_sce8}")
                                    elif _pd_ct8 == 0x0B and _sct == 'COOKIE_ECHOED':
                                        # Camera COOKIE-ACK -> send encrypted LIVING only
                                        try:
                                            _lv8 = _sctp_data(53, _session_mode_req_msg())
                                            _br_send_to_cam(_bs, _enc_c8_sctp(_sctp_pkt(_sctp['peer_tag'], _lv8)), _bsrc, _br_cam_peer)
                                            _sctp['state'] = 'DONE'
                                            _sdes_probe_received = True
                                            _last_hb_ts = _time_br.time()
                                            _status("SDES DC: enc COOKIE-ACK -> sent enc LIVING")
                                        except Exception as _sce8:
                                            _status(f"SDES DC: enc LIVING err: {_sce8}")
                                    elif _pd_ct8 == 0x00 and _sct == 'DONE':
                                        # SCTP DATA from camera
                                        _sc_pay = _pd_plain[28:] if len(_pd_plain) > 28 else b''
                                        _sc_ppid = (int.from_bytes(_pd_plain[24:28], 'big')
                                                    if len(_pd_plain) >= 28 else 0)
                                        _sc_cmd = (int.from_bytes(_sc_pay[4:8], 'little')
                                                   if len(_sc_pay) >= 8 else 0)
                                        # This is where the camera's answers
                                        # come back on SDES.  They were parsed
                                        # and logged here long before anything
                                        # could receive them; hand them to
                                        # whoever asked.
                                        _sc_answered = _dispatch_sctp_avio(
                                            _avio_responses, _sc_pay)
                                        _status(
                                            f"SDES DC: enc DATA ppid={_sc_ppid}"
                                            f" cmd={_sc_cmd} {len(_sc_pay)}B"
                                            f"{' (answered a request)' if _sc_answered else ''}"
                                            f" {_sc_pay[:32].hex()}"
                                        )
                                    else:
                                        # Log SACK (0x03) with cumulative TSN ack for diagnostics
                                        if _pd_ct8 == 0x03 and len(_pd_plain) >= 20:
                                            _cum_tsn = int.from_bytes(_pd_plain[16:20], 'big')
                                            _status(
                                                f"SDES DC: SACK cum_tsn={_cum_tsn:#010x}"
                                                f" state={_sct}"
                                            )
                                        else:
                                            _status(
                                                f"SDES DC: enc SCTP ct=0x{_pd_ct8:02x}"
                                                f" state={_sct} {len(_pd_plain)}B"
                                            )
                                    continue  # SCTP never forwarded to ffmpeg
                                # Send RTCP RR to camera to acknowledge audio receipt.
                                if _tk_audio:
                                    _rr_our_ssrc = 0xAB12CD34
                                    _rr_pkt = _st_br.pack(
                                        '!BBHIIIIIII',
                                        0x81, 201, 7,
                                        _rr_our_ssrc,
                                        _tk_ssrc,
                                        0,
                                        _bridge_fn._tutk_count,
                                        0, 0, 0,
                                    )
                                    _rtcp_sent = False
                                    try:
                                        import pylibsrtp as _plsrtp_rr
                                        import base64 as _b64_rr
                                        _rr_key = _b64_rr.b64decode(
                                            _cam_key_audio or _our_tx_srtp_key_audio)
                                        _rr_pol = _plsrtp_rr.Policy(
                                            key=_rr_key,
                                            ssrc_type=_plsrtp_rr.Policy.SSRC_SPECIFIC,
                                            ssrc_value=_rr_our_ssrc,
                                            srtp_profile=_plsrtp_rr.Policy.SRTP_PROFILE_AES128_CM_SHA1_80,
                                        )
                                        _rr_pol.allow_repeat_tx = True
                                        _rr_sess = _plsrtp_rr.Session(policy=_rr_pol)
                                        _br_send_to_cam(_bs, _rr_sess.protect_rtcp(_rr_pkt), _bsrc, _br_cam_peer)
                                        _rtcp_sent = True
                                    except Exception:
                                        _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_persistent_sdes_cmd', exc_info=True)
                                    if not _rtcp_sent:
                                        try:
                                            _br_send_to_cam(_bs, _rr_pkt, _bsrc, _br_cam_peer)
                                        except Exception:
                                            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_enc_c8_sctp', exc_info=True)
                                    if _bridge_fn._tutk_count == 1:
                                        _status(
                                            f"SDES: sent RTCP RR to camera"
                                            f" (SSRC=0x{_tk_ssrc:08x})"
                                        )
                                # Forward decrypted PCMA audio to ffmpeg loopback.
                                # AVIO control frames (SESSION_MODE_RESP=5377, etc.) are
                                # identified by cmd field and skipped; raw PCMA bytes are sent.
                                if _tk_audio and _pd_plain is not None:
                                    _fwd_cmd = (int.from_bytes(_pd_plain[4:8], 'little')
                                                if len(_pd_plain) >= 8 else 0)
                                    # AVIO control frames identified by cmd field - never
                                    # forward these to ffmpeg's audio port.  804 =
                                    # LdsTrackSwitchNotify (device->client track-id change,
                                    # f0.java:3224): the camera can send it unsolicited, and
                                    # without it here an 804 frame is misrouted as PCMA noise.
                                    _avio_cmds = {5376, 5377, 5156, 5157, 768, 769, 511, 804}
                                    # No response dispatch here.  Measured
                                    # 2026-08-07: the camera's replies come back
                                    # as encrypted SCTP DATA (see
                                    # _dispatch_sctp_avio), never in this
                                    # framing.  Offering every audio packet to
                                    # the router would be per-packet work on the
                                    # media path for something that has never
                                    # arrived on it.
                                    if _fwd_cmd not in _avio_cmds:
                                        try:
                                            _lo_a.sendto(
                                                _rtp_hdr + _pd_plain,
                                                ('127.0.0.1', _lo_audio_port),
                                            )
                                            # Count it: media_stats() documents
                                            # itself as "media actually forwarded
                                            # to ffmpeg by the bridge", and
                                            # scripts/live_validate.py uses it as
                                            # the SDES release gate.  This is a
                                            # real forward, so omitting it made a
                                            # camera whose audio arrives on the
                                            # TUTK path under-report - far enough
                                            # to fail an otherwise healthy release.
                                            _media_progress[0] = _time_br.monotonic()
                                            _media_counts[0] += 1
                                            _media_counts[1] += len(_rtp_hdr) + len(_pd_plain)
                                            if not _br_first_audio_logged:
                                                _br_first_audio_logged = True
                                                # Record the payload type this path
                                                # SYNTHESISES (_tk_pt, PCMA) so the
                                                # SDP can be narrowed to it.  Without
                                                # this the m=audio line keeps
                                                # advertising "0 8", ffmpeg binds
                                                # PCMU and discards every packet, and
                                                # the mpegts PMT is never written - so
                                                # the consumer gets zero bytes and the
                                                # VIDEO is lost with the audio.
                                                _first_audio_pt[0] = _tk_pt
                                                _status(
                                                    f"bridge: first SDES audio -> ffmpeg"
                                                    f" loopback:{_lo_audio_port}"
                                                    f" ({len(_pd_plain)}B PCMA)"
                                                    f" pt={_tk_pt}"
                                                )
                                        except Exception:
                                            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_enc_c8_sctp', exc_info=True)
                                continue

                            # Standard SRTP/SRTCP demux by RTP payload type.
                            # Camera answers BUNDLE (a=group:BUNDLE 0 1) so all media
                            # arrives on _audio_sock.  Routing by source socket would
                            # send video RTP to ffmpeg's audio loopback.  RFC 5761:
                            #   - RTP byte[1] = M(1) | PT(7); PT in 0..127
                            #   - RTCP byte[1] = PT(8); PT in 200..204
                            # SRTP/SRTCP keeps the header in clear, so we can read PT
                            # before decryption.  Audio PTs: 0 (PCMU), 8 (PCMA).
                            # Video PTs: 96 (H264), 97 (H265).
                            if not hasattr(_bridge_fn, '_srtp_switch_logged'):
                                _bridge_fn._srtp_switch_logged = True
                                _LOGGER.info(
                                    "bridge: SRTP/non-TUTK pkt %dB from %s:%d"
                                    " first4=%s (PT=%d) - camera switched to SRTP",
                                    len(_bpkt), _bsrc[0], _bsrc[1],
                                    _bpkt[:4].hex(),
                                    (_bpkt[1] & 0x7F) if len(_bpkt) > 1 else -1,
                                )
                            _pt_byte = _bpkt[1] if len(_bpkt) > 1 else 0
                            if 200 <= _pt_byte <= 204:
                                # SRTCP from camera.  For _use_plain_rtp cameras
                                # the ffmpeg SDP uses RTP/AVP (no crypto).  If we
                                # forward encrypted SRTCP, ffmpeg reads the
                                # encrypted NTP/RTP sender-info bytes as garbage
                                # and uses them to rebase its internal clock,
                                # producing wildly wrong DTS (~ Unix epoch us).
                                # Drop encrypted SRTCP entirely; ffmpeg works fine
                                # without RTCP SR - it uses only RTP timestamps.
                                if _use_plain_rtp:
                                    continue
                                # For SDES cameras (non-plain-rtp) ffmpeg uses
                                # SAVP and handles SRTCP itself - forward as-is.
                                try:
                                    _lo_a.sendto(_bpkt, ('127.0.0.1', _lo_audio_port))
                                except Exception:
                                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                                try:
                                    _lo_v.sendto(_bpkt, ('127.0.0.1', _lo_video_port))
                                except Exception:
                                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                                continue
                            _pt = _pt_byte & 0x7F
                            if _pt in (96, 97, 98):
                                _btgt, _lo_target, _kind = (
                                    _lo_video_port, _lo_v, "video"
                                )
                            elif _pt in (0, 8):
                                _btgt, _lo_target, _kind = (
                                    _lo_audio_port, _lo_a, "audio"
                                )
                            else:
                                # Unknown PT - fall back to source-socket routing.
                                if _bs is _audio_sock:
                                    _btgt, _lo_target, _kind = (
                                        _lo_audio_port, _lo_a, "audio"
                                    )
                                else:
                                    _btgt, _lo_target, _kind = (
                                        _lo_video_port, _lo_v, "video"
                                    )
                            if not _br_first_srtp_logged:
                                _br_first_srtp_logged = True
                                # Hex-dump first 24 bytes + parsed RTP header
                                # so we can verify byte 0 is 0x80 (real RTP v2),
                                # PT, sequence, timestamp, and SSRC against the
                                # camera's announced ssrc:5075/5073.
                                _hex24 = _bpkt[:24].hex()
                                _b0 = _bpkt[0] if len(_bpkt) > 0 else 0
                                if len(_bpkt) >= 12:
                                    _seq16, _ts32, _ssrc32 = _st_br.unpack_from(
                                        '!HII', _bpkt, 2
                                    )
                                else:
                                    _seq16 = _ts32 = _ssrc32 = 0
                                _status(
                                    f"bridge: first SRTP from"
                                    f" {_bsrc[0]}:{_bsrc[1]} pt={_pt}"
                                    f" -> {_kind} loopback:{_btgt}"
                                    f"  byte0=0x{_b0:02x} byte1=0x{_pt_byte:02x}"
                                    f" seq={_seq16} ts={_ts32}"
                                    f" ssrc={_ssrc32}"
                                    f" len={len(_bpkt)}"
                                    f" hex24={_hex24}"
                                )
                            if _kind == "audio" and not _br_first_audio_logged:
                                _br_first_audio_logged = True
                                _first_audio_pt[0] = _pt
                                _status(f"bridge: first audio RTP pt={_pt}")
                                # Capture the camera's audio return address so the talk
                                # pump (SdesSession.async_start_talk) can send outbound
                                # PCMA there.  Only meaningful on a talk-capable open
                                # (offer advertised sendrecv + a=ssrc); harmless otherwise.
                                # Guarded so a video-first capture (BUNDLE) is not lost.
                                if _talk_state is not None and _talk_state.get("src") is None:
                                    _talk_state["src"]  = _bsrc
                                    _talk_state["sock"] = _bs
                                    _status(
                                        f"SDES talk: camera audio addr captured"
                                        f" {_bsrc[0]}:{_bsrc[1]} (talk pump armed)"
                                    )
                            elif _kind == "video" and not _br_first_video_logged:
                                _br_first_video_logged = True
                                _first_video_pt[0] = _pt
                                _status(f"bridge: first video RTP pt={_pt}")
                                # At INFO, not DEBUG: this is the one line that
                                # makes a bitrate figure comparable to another
                                # one, and it is emitted once per session.
                                _LOGGER.info(
                                    "camera %s: video profile %s",
                                    getattr(self, "device_id", "?"),
                                    describe_video_profile(_pt),
                                )
                                # Camera answers BUNDLE (all media on one 5-tuple),
                                # so the talk destination is the same address as
                                # video.  Capture it here too - the camera may send
                                # video first (or send no inbound audio at all), so
                                # gating talk on first-audio alone is unreliable.
                                if _talk_state is not None and _talk_state.get("src") is None:
                                    _talk_state["src"]  = _bsrc
                                    _talk_state["sock"] = _bs
                                    _status(
                                        f"SDES talk: camera media addr captured"
                                        f" {_bsrc[0]}:{_bsrc[1]} (talk pump armed)"
                                    )
                                # Capture camera video SSRC for RTCP PLI.
                                if len(_bpkt) >= 12:
                                    _bridge_fn._cam_video_ssrc = _st_br.unpack_from(
                                        '!I', _bpkt, 8)[0]
                                _bridge_fn._cam_srtp_src  = _bsrc
                                _bridge_fn._cam_srtp_sock = _bs
                                # Schedule immediate PLI so IDR+SPS arrives in
                                # the analyzeduration window.
                                _bridge_fn._last_pli_ts = 0.0
                            # For TUTK cameras (_use_plain_rtp) the ffmpeg SDP uses
                            # RTP/AVP (no crypto). After LIVING the camera switches
                            # from TUTK SFrames to standard SRTP, so we decrypt here
                            # before forwarding plain RTP to ffmpeg.
                            _fwd_pkt = _bpkt
                            if _use_plain_rtp:
                                if not hasattr(_bridge_fn, '_srtp_rx_sess'):
                                    _bridge_fn._srtp_rx_sess = None
                                    _rx_inline = _cam_key_audio or srtp_key_audio
                                    if _rx_inline:
                                        try:
                                            import pylibsrtp as _plsrtp_rx
                                            import base64 as _b64_srx
                                            _rx_pol = _plsrtp_rx.Policy(
                                                key=_b64_srx.b64decode(_rx_inline),
                                                ssrc_type=_plsrtp_rx.Policy.SSRC_ANY_INBOUND,
                                                srtp_profile=_plsrtp_rx.Policy.SRTP_PROFILE_AES128_CM_SHA1_80,
                                            )
                                            _rx_pol.allow_repeat_tx = True
                                            _bridge_fn._srtp_rx_sess = _plsrtp_rx.Session(
                                                policy=_rx_pol
                                            )
                                            self._cold_phase("first-media")
                                            _status("bridge: SRTP RX session ready (cam->us)")
                                        except Exception as _srx_e:
                                            _status(f"bridge: SRTP RX init failed: {_srx_e}")
                                if _bridge_fn._srtp_rx_sess is not None:
                                    try:
                                        _fwd_pkt = _bridge_fn._srtp_rx_sess.unprotect(_bpkt)
                                    except Exception as _srx_dec_e:
                                        _ec = getattr(
                                            _bridge_fn, '_decrypt_err_n', 0)
                                        if _ec < 8:
                                            _bridge_fn._decrypt_err_n = _ec + 1
                                            _seq_d = (int.from_bytes(
                                                _bpkt[2:4], 'big')
                                                if len(_bpkt) >= 4 else -1)
                                            _ssrc_d = (int.from_bytes(
                                                _bpkt[8:12], 'big')
                                                if len(_bpkt) >= 12 else 0)
                                            _status(
                                                f"bridge: SRTP decrypt err:"
                                                f" {_srx_dec_e}"
                                                f" seq={_seq_d}"
                                                f" ssrc=0x{_ssrc_d:08x}"
                                                f" pt={_pt}"
                                            )
                            # Capture this camera's SPS/PPS once so future streams
                            # can inject sprop-parameter-sets (out-of-band decoder
                            # init, robust to in-band SPS loss).  Parses only until
                            # both are seen; then _sprop_done short-circuits.
                            if (_kind == "video"
                                    and not getattr(_bridge_fn, "_sprop_done", False)):
                                _ps = _extract_param_sets_from_rtp(_fwd_pkt)
                                if _ps:
                                    _psc = getattr(_bridge_fn, "_ps_cache", None)
                                    if _psc is None:
                                        _psc = {}
                                        _bridge_fn._ps_cache = _psc
                                    _psc.update(_ps)
                                    if 7 in _psc and 8 in _psc:
                                        _sprop_new = _build_sprop(_psc[7], _psc[8])
                                        if _sprop_new != _load_sprop(self.device_id):
                                            _save_sprop(self.device_id, _sprop_new)
                                            _status("bridge: cached sprop-parameter"
                                                    f"-sets for {self.device_id}")
                                        _bridge_fn._sprop_done = True
                            # Rebase RTP timestamps to start near 0.  Camera picks a
                            # random starting timestamp (RFC 3550 section 5.1); the 90 kHz
                            # video clock can be near 2^32 and wraps, producing huge
                            # or negative DTS values that the MPEG-TS muxer drops.
                            # Subtracting the first-seen timestamp per-stream gives
                            # ffmpeg a monotonically increasing sequence from 0.
                            if (_use_plain_rtp
                                    and len(_fwd_pkt) >= 8
                                    and _fwd_pkt[0] == 0x80):
                                _rtp_raw_ts = _st_br.unpack_from('!I', _fwd_pkt, 4)[0]
                                _ts_base_attr = (
                                    '_rtp_ts_base_video' if _kind == 'video'
                                    else '_rtp_ts_base_audio'
                                )
                                if not hasattr(_bridge_fn, _ts_base_attr):
                                    setattr(_bridge_fn, _ts_base_attr, _rtp_raw_ts)
                                _rtp_base = getattr(_bridge_fn, _ts_base_attr)
                                _rtp_norm = (_rtp_raw_ts - _rtp_base) & 0xFFFFFFFF
                                _fwd_pkt = (_fwd_pkt[:4]
                                            + _st_br.pack('!I', _rtp_norm)
                                            + _fwd_pkt[8:])
                            try:
                                _lo_target.sendto(
                                    _fwd_pkt, ('127.0.0.1', _btgt)
                                )
                                _media_progress[0] = _time_br.monotonic()
                                _media_counts[0] += 1
                                _media_counts[1] += len(_fwd_pkt)
                            except Exception:
                                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                    # Periodic ICE controlling check: re-send USE-CANDIDATE every 2.5 s.
                    # Keeps the camera in ICE "Completed" state and satisfies consent
                    # refresh (RFC 7675).  Also handles the case where the initial
                    # USE-CANDIDATE (sent right after the STUN window) was lost.
                    _br_now = _time_br.monotonic()
                    # Relay-only battery cams (LAN IP unknown) answer AFTER the STUN
                    # window and send no probes, so the in-window proactive send saw
                    # empty _cam_ice_* and the probe-gated late-send above never runs.
                    # Fall back to the creds parsed late into _bridge_uc_info so this
                    # ungated periodic tick still nominates them.  [SDES-LATECREDS-FIX]
                    _uc_cands = _cam_ice_cands or _bridge_uc_info.get("cands")
                    # Union in anything learned from the camera's own probes.
                    # Advertised candidates stay FIRST and are still nominated,
                    # so a camera we can already reach directly is unaffected;
                    # this only adds a path where there was none.
                    _uc_prflx = _bridge_uc_info.get("prflx")
                    if _uc_prflx:
                        _uc_cands = [
                            *(_uc_cands or []),
                            *(c for c in _uc_prflx if c not in (_uc_cands or [])),
                        ]
                    _uc_cufrag = _cam_ice_ufrag or _bridge_uc_info.get("ufrag")
                    _uc_cpwd = _cam_ice_pwd or _bridge_uc_info.get("pwd")
                    if _uc_cands and _uc_cufrag and _uc_cpwd and (_br_now - _br_last_uc) >= 2.5:
                        _br_last_uc = _br_now
                        # Same RFC 5766 s9 door as the setup path, but this is
                        # the only place it can be opened for a relay-only
                        # battery cam: its candidates arrive after the STUN
                        # window, so the setup-time install above never ran and
                        # nominating without a permission probes a black hole.
                        # Re-install when the candidate set changes and every
                        # 120 s thereafter - a TURN permission expires 300 s
                        # after it is installed (RFC 5766 s8).
                        _perm_key = tuple(sorted(_uc_cands))
                        if (_perm_key != _br_perm_cands
                                or (_br_now - _br_last_perm) >= 120.0):
                            _br_perm_cands = _perm_key
                            _br_last_perm = _br_now
                            _turn_install_permissions(_uc_cands, "bridge")
                        # Keep the allocation itself alive.  The server grants
                        # it for a LIFETIME (600 s here) and drops it silently
                        # when that lapses, so refresh well inside the window.
                        if (_relay_addrs
                                and (_br_now - _br_last_alloc_refresh) >= 240.0):
                            _br_last_alloc_refresh = _br_now
                            _rf_ok = sum(
                                1 for _rf_s in (_audio_sock, _video_sock)
                                if _turn_refresh_allocation(_rf_s)
                            )
                            if _rf_ok:
                                _status(
                                    f"TURN: refreshed {_rf_ok} relay"
                                    f" allocation(s)"
                                )
                        for _c_ip, _c_port in _uc_cands:
                            _send_use_candidate(
                                _audio_sock, _ufrag_a, _pwd_a,
                                _uc_cufrag, _uc_cpwd, (_c_ip, _c_port),
                            )
                            _send_use_candidate(
                                _video_sock, _ufrag_v, _pwd_v,
                                _uc_cufrag, _uc_cpwd, (_c_ip, _c_port),
                            )
            finally:
                try:
                    _lo_a.close()
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
                try:
                    _lo_v.close()
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)

        _br_first_di_logged = False
        _br_first_srtp_logged = False
        _br_first_req_dumped = False
        _br_first_audio_logged = False
        _br_first_video_logged = False
        _avio_living_sent = False

        # Shared mutable container so the bridge can send USE-CANDIDATE even
        # when the camera's SDP answer arrives after the bridge starts (late-
        # wakeup path where _cam_ice_ufrag/pwd/cands are still empty at setup).
        # Written by the main coroutine, read by the bridge thread.  Safe in
        # CPython: dict key assignment and simple attribute writes are atomic
        # under the GIL.
        _bridge_uc_info: dict = {
            "ufrag":   _cam_ice_ufrag,
            "pwd":     _cam_ice_pwd,
            "cands":   list(_cam_ice_cands),
            "sent":    bool(_cam_ice_cands),  # True if already sent at setup
            # Addresses the camera's own probes arrived from, which may differ
            # from anything it advertised.  Written by the bridge thread only;
            # read there too, so no cross-thread ordering to reason about.
            "prflx":   [],
        }

        _bridge_thread = _threading_br.Thread(
            target=_bridge_fn, daemon=True, name="sdes-bridge"
        )
        _bridge_thread.start()
        _status(
            f"bridge thread started - camera sockets audio={audio_port}"
            f" video={video_port} -> ffmpeg loopback {_lo_audio_port}/{_lo_video_port}"
        )

        async def _consume_camera_trickle():
            """Feed the camera's trickled candidates to TURN and to nomination.

            The answer SDP carries only the camera's host candidate - a private
            address.  A TURN permission is matched against the peer's source
            address AS THE RELAY SEES IT, so a permission built from that host
            candidate authorises an address that can never arrive, and the relay
            keeps discarding the camera exactly as if no permission existed.
            Measured on a live A001513: CreatePermission for the host candidate
            drew a success response and still produced ZERO relay-carried
            inbound packets.

            The addresses that can actually reach us - the camera's srflx and
            its own relay allocation - arrive later, by iceCandidateReq trickle.
            Permission each one as it appears, and add it to the nomination set:
            when the camera is not directly reachable, USE-CANDIDATE aimed only
            at its host candidate cannot be delivered either.
            """
            _tk_deadline = time.monotonic() + min(max(timeout, 30.0), 90.0)
            _tk_next = 0
            _tk_seen: set = set()
            while time.monotonic() < _tk_deadline:
                while _tk_next < len(ice_cands_seen):
                    _tk_line = (ice_cands_seen[_tk_next].get("candidate") or "")
                    _tk_next += 1
                    _tk_m = _re_ice.match(
                        r"(?:a=)?candidate:\S+ \d+ udp \d+ ([\d.]+) (\d+) typ (\w+)",
                        _tk_line,
                    )
                    if not _tk_m:
                        continue
                    _tk_ip = _tk_m.group(1)
                    _tk_port = int(_tk_m.group(2))
                    _tk_typ = _tk_m.group(3)
                    if (_tk_ip, _tk_port) in _tk_seen:
                        continue
                    _tk_seen.add((_tk_ip, _tk_port))
                    _turn_install_permissions(
                        [(_tk_ip, _tk_port)], f"trickle {_tk_typ}")
                    _tk_cur = _bridge_uc_info["cands"]
                    if (_tk_ip, _tk_port) not in _tk_cur:
                        # Rebind to a NEW list rather than appending: the bridge
                        # thread iterates this and must never see it mutate.
                        _bridge_uc_info["cands"] = [
                            *_tk_cur, (_tk_ip, _tk_port)]
                await asyncio.sleep(0.25)

        if ice_cands_seen is not None:
            # Hold a strong reference: a task referenced only by a local can be
            # garbage-collected mid-flight once this coroutine returns.
            _trickle_task = loop.create_task(_consume_camera_trickle())
            _SDES_BACKGROUND_TASKS.add(_trickle_task)
            _trickle_task.add_done_callback(_SDES_BACKGROUND_TASKS.discard)

        # --- DTLS fallback: echo-reversal camera did not do ICE or SRTP ----- #
        # LK.IPC.A001064 echoes our webrtcReq offer and webrtcResp answer back
        # over MQTT but then never initiates STUN connectivity checks or sends
        # any SRTP packets.  The camera appears to require DTLS (peerid _2)
        # despite reporting enableSdes='1' in its device properties.
        # Detect this by checking: echo-reversal received (_cam_echo_received)
        # AND no STUN in the ICE window AND no early SRTP.
        # _cam_echo_received=True only for cameras that mirror our webrtcReq
        # (e.g. A001064); non-echo SDES cameras (e.g. A001513) always have it
        # False and are never affected by this block.
        # NOTE: isDTLS='0' (dtls_fallback_ok=False) does NOT mean the camera
        # cannot do DTLS - webrtc_internals_dump confirms LK.IPC.A001064 uses
        # DTLS (UDP/TLS/RTP/SAVPF) when given a proper DTLS offer.  The
        # property means SDES is available, not that DTLS is absent.
        if (_cam_echo_received
                and _stun_count == 0
                and _camera_side_pkt_count == 0
                and not _srtp_detected
                and dtls_fallback_ok):
            _status(
                "echo-reversal camera: no STUN or SRTP received in ICE window"
                " - camera likely requires DTLS; falling back"
            )
            # Stop bridge thread by closing its sockets before we raise.
            for _rsock in (_audio_sock, _video_sock):
                try:
                    _rsock.close()
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
            try:
                os.unlink(sdp_path)
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
            outgoing_q.put_nowait(None)   # stop MQTT thread
            raise CameraMixin._SdesNoAnswerError()
        elif (_cam_echo_received
              and _stun_count == 0
              and _camera_side_pkt_count == 0
              and not _srtp_detected
              and not dtls_fallback_ok):
            _status(
                "echo-reversal camera: no STUN or SRTP received in ICE window"
                " - DTLS fallback disabled by camera flags; continuing SDES path"
            )

        if rtsp_push_url and rtsp_push_url.startswith("http"):
            # Cold-start relay: when the keepalive loop is holding the public
            # serve port, point ffmpeg at a fresh internal port and let the relay
            # splice the public port to it - so an early go2rtc pull waits through
            # this handshake instead of being refused.  set_backend retries the
            # dial until ffmpeg's -listen socket binds (only after input frames).
            _relay = getattr(self, "_serve_relay", None)
            if _relay is not None:
                _ff_port = _grab_free_port()
                rtsp_push_url = _rewrite_serve_port(rtsp_push_url, _ff_port)
                _relay.set_backend(_ff_port)
            # PULL model: SERVE the decrypted stream over an HTTP-listen socket so
            # go2rtc / HA's stream integration pull it the standard way - the only
            # per-camera side effect here is the cold-start serve-relay rewrite
            # above; the destination + audio args (and the PMT-stall rationale for
            # audio being opt-in) live in _build_sdes_serve_cmd, built once below.
        elif output_path:
            # Ensure the output directory exists before ffmpeg opens the file
            # (ffmpeg fails with "No such file or directory" otherwise).
            out_dir = os.path.dirname(output_path)
            if out_dir:
                os.makedirs(out_dir, exist_ok=True)
        # The ffmpeg command is built once below, AFTER the first-media wait
        # resolves the real payload types (single source of truth:
        # _build_sdes_serve_cmd) - so both the SDP narrowing and the push
        # video-only decision see the observed codecs.
        _serve_audio = self._resolve_sdes_serve_audio()
        # --- H.265 fix: narrow the ffmpeg SDP to the camera's actual codec ----
        # The camera streams H.264 (pt=96) OR H.265 (pt=97), varying per session.
        # An m=video line listing both ("96 97") makes ffmpeg bind the RTP
        # depacketizer to the FIRST pt (96/H.264) and silently drop the camera's
        # H.265 packets -> 0 frames.  Wait for the bridge to observe the real
        # video pt, then rewrite the SDP to that single codec before launch.
        # ffmpeg recovers on the next periodic keyframe, so the small spawn delay
        # is harmless.  Falls back to the dual-codec SDP if no video is seen.
        # When audio is being served the same wait has to cover the AUDIO payload
        # type, and for a stricter reason: an unnarrowed video line merely risks the
        # picture, while an unnarrowed audio line guarantees the mux never writes its
        # PAT/PMT, which loses the picture AND the audio.  Wait for both, then take
        # whatever has been observed by the deadline.
        # Wait for the session's first media, then briefly for audio.
        #
        # Measured on an A001513: a cold session's media starts at ~21s, and its
        # video and audio payload types then arrive 40-70ms apart because the camera
        # answers BUNDLE.  So audio is never "late" relative to video - the old 15s
        # video deadline simply expired BEFORE any media at all, and the serve
        # launched with both payload types unknown.  Waiting for first media costs
        # no picture latency: ffmpeg cannot produce before media exists, and
        # launching earlier only binds the wrong depacketizers.
        # The camera's answer carries the ICE credentials we need to nominate a
        # pair, and it lands about a second after webrtcReq - but it is not
        # awaited until AFTER this wait.  So for a camera whose answer misses the
        # _pre_launch_answer_sdp snapshot, USE-CANDIDATE was only ever sent once
        # this window had already expired, the camera sat in ICE "Checking"
        # forever, and the wait below could not do anything but time out.
        # Measured on an A001513: answer at +1.3 s, nomination at +81 s.
        # Nominate as soon as the answer is readable instead.
        _early_nominated = bool(_cam_ice_ufrag and _cam_ice_pwd and _cam_ice_cands)
        _media_deadline = time.monotonic() + _FIRST_MEDIA_WAIT_S
        while _first_video_pt[0] is None and time.monotonic() < _media_deadline:
            if terminal_error_fut is not None and terminal_error_fut.done():
                _code, _desc = terminal_error_fut.result()
                _status(f"camera refused: ack {_code} {_desc}"
                        " - terminal, abandoning the first-media wait")
                raise AidotCameraBusy(_code, _desc)
            if (not _early_nominated and answer_fut is not None
                    and answer_fut.done() and not answer_fut.cancelled()):
                # Read-only peek: the real await below still consumes this
                # future and drives the answer/fallback paths unchanged.
                # ``cancelled()`` is checked explicitly because the answer-wait
                # cancels this future on timeout, and ``.exception()`` on a
                # cancelled future raises CancelledError - a BaseException that
                # the ``except Exception`` below would NOT catch.
                _peek_sdp = ""
                try:
                    if answer_fut.exception() is None:
                        _peek_sdp = (answer_fut.result() or {}).get("sdp", "") or ""
                except Exception:
                    _peek_sdp = ""
                _n_nom = _nominate_from_answer_sdp(_peek_sdp)
                if _n_nom:
                    _early_nominated = True
                    _status(
                        "ICE controlling: nominated %d candidate(s) from the"
                        " camera's answer during the first-media wait" % _n_nom
                    )
            await asyncio.sleep(0.1)
        if _serve_audio and _first_audio_pt[0] is None:
            _apt_deadline = time.monotonic() + _AUDIO_PT_GRACE_S
            while _first_audio_pt[0] is None and time.monotonic() < _apt_deadline:
                await asyncio.sleep(0.1)
        _vpt = _first_video_pt[0]
        _apt = _first_audio_pt[0]
        # Which single payload type (if any) each line can be narrowed to. Audio
        # is usable only when its type was actually observed; otherwise the "0 8"
        # line cannot be narrowed.
        if (_vpt not in _SDP_VIDEO_PTS and answer_fut is not None
                and answer_fut.done() and not answer_fut.cancelled()):
            # No video packet arrived inside the window, but the camera's answer
            # still names the codec it agreed to send. Narrowing on that beats
            # leaving both codecs advertised: an unnarrowed video line makes
            # ffmpeg bind the wrong depacketizer, and makes the RTSP-push
            # ANNOUNCE carry a parameterless H.265 stream that go2rtc rejects
            # outright - no publisher, and every viewer sees a 404.
            try:
                if answer_fut.exception() is None:
                    _answer_video_pt[0] = video_pt_from_answer_sdp(
                        (answer_fut.result() or {}).get("sdp", "") or "")
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s",
                              getattr(self, "device_id", "?"),
                              'video_pt_from_answer_sdp', exc_info=True)
            if _answer_video_pt[0] is not None:
                _LOGGER.info(
                    "camera %s: no video observed before the serve launched; "
                    "narrowing the SDP to payload type %d from the camera's "
                    "negotiated answer instead of advertising both codecs.",
                    getattr(self, "device_id", "?"), _answer_video_pt[0],
                )
        _keep_v = int(_vpt) if _vpt in _SDP_VIDEO_PTS else _answer_video_pt[0]
        _keep_a = int(_apt) if _apt in _SDP_AUDIO_PTS else None
        if _serve_audio and _keep_a is None:
            # No audio observed in the window, so the line cannot be narrowed and
            # mapping audio would stall the mux (pull) or break the ANNOUNCE
            # (push).  Serve video only rather than serving nothing, and say so.
            _LOGGER.warning(
                "camera %s: no audio observed before the serve launched, so the "
                "audio payload type is unknown; mapping it would stall the mpegts "
                "mux and serve no video. Continuing without audio.",
                getattr(self, "device_id", "?"),
            )
            _serve_audio = False
        # RTSP-PUSH copies EVERY input stream (-c copy). If the audio line was
        # never narrowed to a single type, its multi-PT ANNOUNCE is rejected by
        # the RTSP server (400 Bad Request) and takes the whole publish down - so
        # push must map video only whenever audio is not usable. (Pull already
        # gates audio via sdes_audio in _build_sdes_serve_cmd.)
        _is_push = bool(rtsp_push_url) and not rtsp_push_url.startswith("http")
        _push_video_only = _is_push and _keep_a is None
        # Only the -f null drain decodes; the rest are -c copy, so this is the
        # one place a decoder choice can pay.  Read the cache only - probing
        # shells out for several seconds on a cold host and would stall the
        # event loop for every camera; None just means "let ffmpeg choose", as
        # before, and an empty list is a real "software decoding" answer.
        from .hwaccel import cached_decoder  # lazy: keep import cost off setup
        _video_decoder = (
            cached_decoder("h264" if _keep_v == 96 else "hevc")
            if _keep_v is not None else None
        )
        cmd = _build_sdes_serve_cmd(
            sdp_path=sdp_path,
            rtsp_push_url=rtsp_push_url,
            output_path=output_path,
            max_seconds=max_seconds,
            sdes_audio=_serve_audio,
            audio_gain_db=self._resolve_sdes_audio_gain_db(),
            push_video_only=_push_video_only,
            video_decoder=_video_decoder,
        )
        # Audio matters even more than video here.  A multi-PT m-line makes ffmpeg
        # bind the depacketizer to the FIRST payload type and silently discard the
        # rest, and the mpegts mux withholds its PAT/PMT until EVERY mapped stream
        # has produced a packet - so an audio line advertising "0 8" (PCMU first)
        # on a camera that sends PCMA discards every audio packet and the consumer
        # receives zero bytes.  That takes the video down with it, which is why
        # enabling serve audio appeared to break streaming outright.
        if _keep_v is not None or _keep_a is not None:
            def _read_sdp_file() -> str:
                with open(sdp_path, encoding="utf-8") as _f_sdp:
                    return _f_sdp.read()
            try:
                _cur_sdp = await asyncio.get_running_loop().run_in_executor(
                    None, _read_sdp_file)
                await asyncio.get_running_loop().run_in_executor(
                    None, _write_text_file, sdp_path,
                    narrow_sdp_payload_types(_cur_sdp, _keep_v, _keep_a))
                _status(
                    "SDES: narrowed ffmpeg SDP to"
                    + (f" video pt={_keep_v}"
                       f" ({'H264' if _keep_v == 96 else 'H265'})"
                       if _keep_v is not None else "")
                    + (f" audio pt={_keep_a}"
                       f" ({'PCMA' if _keep_a == 8 else 'PCMU'})"
                       if _keep_a is not None else ""))
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_open_sdes_stream', exc_info=True)
        _LOGGER.info("SDES ffmpeg cmd: %s", " ".join(cmd))
        if _ffmpeg_path() is None:
            # ffmpeg is not installed - clean up and surface a clear error
            # before launching (avoids a cryptic FileNotFoundError).
            for _rsock in (_audio_sock, _video_sock):
                try:
                    _rsock.close()
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
            try:
                os.unlink(sdp_path)
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
            outgoing_q.put_nowait(None)   # stop MQTT thread
            raise RuntimeError(
                "ffmpeg not found - install ffmpeg to stream SDES-SRTP cameras.\n"
                "  Ubuntu/Debian:  sudo apt install ffmpeg\n"
                "  macOS (Homebrew): brew install ffmpeg\n"
                "  Windows:         https://ffmpeg.org/download.html"
            )
        try:
            proc = subprocess.Popen(
                cmd,
                stdout=subprocess.DEVNULL,
                stderr=subprocess.PIPE,
            )
            _start_serve_stderr_drain(proc)
            _proc_holder[0] = proc
            _cl(_reap, proc)   # kill ffmpeg if the open is cancelled before hand-off
        except FileNotFoundError:
            # ffmpeg is not installed - clean up and surface a clear error.
            for _rsock in (_audio_sock, _video_sock):
                try:
                    _rsock.close()
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
            try:
                os.unlink(sdp_path)
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_bridge_fn', exc_info=True)
            outgoing_q.put_nowait(None)   # stop MQTT thread
            raise RuntimeError(
                "ffmpeg not found - install ffmpeg to stream SDES-SRTP cameras.\n"
                "  Ubuntu/Debian:  sudo apt install ffmpeg\n"
                "  macOS (Homebrew): brew install ffmpeg\n"
                "  Windows:         https://ffmpeg.org/download.html"
            )

        # Wait until ffmpeg has actually bound the UDP ports before sending
        # webrtcReq.  Popen() returns as soon as the process is created;
        # ffmpeg needs additional time to parse the SDP and call bind().
        # If we send webrtcReq before ffmpeg is listening, the camera's first
        # SRTP packets hit closed ports, the OS sends ICMP port-unreachable,
        # and the camera stops streaming - producing 0 frames.
        def _udp_port_bound(port: int) -> bool:
            """True if a UDP socket is currently bound to `port` on 127.0.0.1.

            Uses try-bind: if we can bind the port it's free; if EADDRINUSE
            it's taken - i.e. ffmpeg is listening.  Works on macOS and Linux
            without /proc/net/udp.
            """
            try:
                _s_pb = _socket_br.socket(
                    _socket_br.AF_INET, _socket_br.SOCK_DGRAM)
                try:
                    _s_pb.bind(('127.0.0.1', port))
                    return False   # bound cleanly -> port was free
                except OSError:
                    return True    # EADDRINUSE -> ffmpeg is listening
                finally:
                    _s_pb.close()
            except Exception:
                return False

        _t0 = time.monotonic()
        _bind_deadline = _t0 + 3.0
        _bound = False
        while time.monotonic() < _bind_deadline:
            if _udp_port_bound(_lo_audio_port) and _udp_port_bound(_lo_video_port):
                _bound = True
                break
            await asyncio.sleep(0.05)

        _bind_ms = int((time.monotonic() - _t0) * 1000)
        self._cold_phase("serving (sdes ffmpeg bound)")
        if _bound:
            _status(
                f"SDES ffmpeg ready - loopback audio={_lo_audio_port}"
                f" video={_lo_video_port} bound in {_bind_ms} ms  (pid={proc.pid})"
            )
        else:
            # /proc/net/udp unavailable or ffmpeg very slow - use fixed delay
            _status(
                f"ffmpeg port bind not confirmed after {_bind_ms} ms"
                " - sleeping 1.5 s as fallback"
            )
            await asyncio.sleep(1.5)

        # --- Wait for SDP answer -------------------------------------------- #
        # Wait for the camera's SDP answer.  True SDES cameras (e.g.
        # LK.IPC.A001513) send webrtcResp within a few seconds.  If no
        # answer arrives the camera is not operating in SDES mode - most
        # likely it has enableSdes='1' set incorrectly and actually requires
        # DTLS.  In that case abort the ffmpeg process and raise
        # _SdesNoAnswerError so the caller retries with the DTLS path.
        # Battery SDES cameras (A001513 etc.) take up to ~15s to wake from
        # deep sleep - they answer webrtcReq well after the bridge starts.
        # Cameras with DTLS fallback keep 8s so the fallback fires quickly.
        _sdes_answer_timeout = (
            sdes_answer_timeout if sdes_answer_timeout is not None
            else min(timeout, 8.0 if dtls_fallback_ok else 20.0)
        )
        try:
            # Race the answer against a TERMINAL refusal (-50002 max-streams /
            # -50015 SD-cap).  Without this the SDES path is blind to a camera
            # that explicitly said no: it waited out its whole answer budget and
            # then handed a "no answer" to the DTLS fallback, which sent a second
            # offer and only THEN surfaced AidotCameraBusy.  Measured ~48s to
            # report a refusal that arrived in about one second - and two of the
            # three validated models (A001513, A001064) take this path.
            answer = await _sdes_await_answer_or_terminal(
                answer_fut, terminal_error_fut, _sdes_answer_timeout, _status,
            )
            _ans_sdp = answer.get("sdp", "")
            _ans_mlines = [ln for ln in _ans_sdp.splitlines() if ln.startswith("m=")]
            _status(
                "webrtcResp received (SDES) - answer m-sections (%d): %s"
                % (len(_ans_mlines), " | ".join(_ans_mlines))
            )
            # If the answer arrived late (after bridge started without ICE creds),
            # parse the camera's ICE credentials now and populate _bridge_uc_info
            # so the bridge can send USE-CANDIDATE on the next camera BindingReq.
            if not _bridge_uc_info["sent"] and not _bridge_uc_info["ufrag"] and _ans_sdp:
                import re as _re_late_ice
                _late_ufrag = _late_pwd = ""
                _late_cands: list = []
                for _al in _ans_sdp.splitlines():
                    if _al.startswith("a=ice-ufrag:") and not _late_ufrag:
                        _late_ufrag = _al[len("a=ice-ufrag:"):].strip()
                    elif _al.startswith("a=ice-pwd:") and not _late_pwd:
                        _late_pwd = _al[len("a=ice-pwd:"):].strip()
                    elif _al.startswith("a=candidate:"):
                        _cm = _re_late_ice.match(
                            r"a=candidate:\S+ \d+ udp \d+ ([\d.]+) (\d+) typ",
                            _al,
                        )
                        if _cm:
                            _late_cands.append(
                                (_cm.group(1), int(_cm.group(2))))
                if _late_ufrag and _late_pwd and _late_cands:
                    _bridge_uc_info["ufrag"]  = _late_ufrag
                    _bridge_uc_info["pwd"]    = _late_pwd
                    _bridge_uc_info["cands"]  = _late_cands
                    # Update _dc_answer_has_app and _cam_key_audio so the bridge's
                    # SCTP path activates for late-wake cameras.
                    if "m=application" in _ans_sdp:
                        _dc_answer_has_app = True
                    if not _cam_key_audio and _ans_sdp:
                        import re as _re_lk
                        _lk_in = False
                        for _lk_ln in _ans_sdp.splitlines():
                            if _lk_ln.startswith("m=audio"):
                                _lk_in = True
                            elif _lk_ln.startswith("m=") and _lk_in:
                                break
                            elif _lk_in and _lk_ln.startswith("a=crypto:"):
                                _lk_m = _re_lk.search(r"inline:([A-Za-z0-9+/=]+)", _lk_ln)
                                if _lk_m:
                                    _cam_key_audio = _lk_m.group(1)
                                    break
                    _status(
                        f"late ICE creds parsed - bridge will send USE-CANDIDATE"
                        f" to {len(_late_cands)} candidate(s)"
                        + (" [m=application present]" if _dc_answer_has_app else "")
                        + (f" [cam_key set: {_cam_key_audio[:8]}...]" if _cam_key_audio else "")
                    )
            # For echo-reversal cameras (A001064) the first answer_fut was set by
            # the broker echo of our own webrtcResp.  Wait briefly for the camera's
            # real second webrtcResp, which may carry a different SRTP key.
            if second_answer_fut is not None and _cam_echo_received and not second_answer_fut.done():
                try:
                    _second_ans = await asyncio.wait_for(
                        asyncio.shield(second_answer_fut), timeout=5.0
                    )
                except TimeoutError:
                    _second_ans = None
            elif second_answer_fut is not None and second_answer_fut.done():
                try:
                    _second_ans = second_answer_fut.result()
                except Exception:
                    _second_ans = None
            else:
                _second_ans = None

            if _second_ans:
                _second_sdp = _second_ans.get("sdp", "")
                if _second_sdp:
                    _status("camera real webrtcResp - extracting SRTP keys")
                    import re as _re2

                    def _extract_key(sdp, media):
                        _in_sec = False
                        for _ln in sdp.splitlines():
                            if _ln.startswith(f"m={media}"):
                                _in_sec = True
                            elif _ln.startswith("m=") and _in_sec:
                                break
                            elif _in_sec and _ln.startswith("a=crypto:"):
                                _km = _re2.search(r"inline:([A-Za-z0-9+/=]+)", _ln)
                                if _km:
                                    return _km.group(1)
                        return ""

                    _real_key_audio = _extract_key(_second_sdp, "audio")
                    _real_key_video = _extract_key(_second_sdp, "video")
                    _keys_changed = False
                    if _real_key_audio and _real_key_audio != srtp_key_audio:
                        _status("camera audio SRTP key differs from offer - restarting ffmpeg")
                        srtp_key_audio = _real_key_audio
                        _keys_changed = True
                    if _real_key_video and _real_key_video != srtp_key_video:
                        _status("camera video SRTP key differs from offer - restarting ffmpeg")
                        srtp_key_video = _real_key_video
                        _keys_changed = True
                    if _keys_changed:
                        # Locally-initiated kill (the offered key was wrong;
                        # restarting ffmpeg with the camera's real key) - flag it
                        # so the bridge observe loop does not warn on the old
                        # proc's signal death.  Reset once the NEW proc is live
                        # (below) so a later, genuine crash of the restarted
                        # ffmpeg is not silently swallowed as "expected teardown".
                        _teardown_holder[0] = True
                        proc.terminate()
                        try:
                            proc.wait(timeout=2)
                        except Exception:
                            proc.kill()
                        _ts2 = int(time.time())
                        # Match the PRIMARY SDP on both counts, or this restart
                        # undoes two fixes at once.
                        #
                        # Transport: for _use_plain_rtp models - which is every
                        # SDES camera this library supports - the bridge decrypts
                        # and forwards PLAIN RTP, so the primary SDP is RTP/AVP
                        # with no a=crypto.  Writing RTP/SAVP here makes ffmpeg
                        # try to authenticate already-decrypted packets: every one
                        # fails its HMAC check and a working stream drops to zero
                        # bytes mid-session.
                        #
                        # Payload types: hard-coding "0 8" and "96 97" throws away
                        # the narrowing, and ffmpeg binds the FIRST type on each
                        # line - so an H.265 camera silently loses all video (and
                        # with it the PAT/PMT, hence the whole output), and a PCMA
                        # camera loses its audio.  Both types are known by now.
                        _proto = "RTP/AVP" if _use_plain_rtp else "RTP/SAVP"

                        def _crypto(key: str) -> str:
                            if _use_plain_rtp:
                                return ""
                            return (f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 "
                                    f"inline:{key}\r\n")

                        _new_sdp = (
                            "v=0\r\n"
                            f"o=- {_ts2} {_ts2} IN IP4 0.0.0.0\r\n"
                            "s=aidot-sdes-rx\r\n"
                            "t=0 0\r\n"
                            f"m=audio {_lo_audio_port} {_proto} 0 8\r\n"
                            "c=IN IP4 127.0.0.1\r\n"
                            f"{_crypto(srtp_key_audio)}"
                            "a=rtpmap:0 PCMU/8000\r\n"
                            "a=rtpmap:8 PCMA/8000\r\n"
                            "a=rtcp-mux\r\n"
                            f"m=video {_lo_video_port} {_proto} 96 97\r\n"
                            "c=IN IP4 127.0.0.1\r\n"
                            f"{_crypto(srtp_key_video)}"
                            "a=rtpmap:96 H264/90000\r\n"
                            "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
                            "profile-level-id=42e01f\r\n"
                            "a=rtpmap:97 H265/90000\r\n"
                            "a=fmtp:97 level-id=93\r\n"
                            "a=rtcp-mux\r\n"
                        )
                        _new_sdp = narrow_sdp_payload_types(
                            _new_sdp,
                            # Same answer-SDP fallback as the pre-launch narrowing:
                            # this restart rebuilds the dual-codec template, so
                            # without it a session that never saw a video packet
                            # reproduces the unnarrowed SDP on every watchdog cycle.
                            keep_video=(_first_video_pt[0]
                                        if _first_video_pt[0] in _SDP_VIDEO_PTS
                                        else _answer_video_pt[0]),
                            keep_audio=(_first_audio_pt[0]
                                        if _first_audio_pt[0] in (0, 8) else None),
                        )
                        try:
                            # Re-apply the cached sprop-parameter-sets here too:
                            # this serve-restart rewrite is the SDP the keepalive
                            # ffmpeg actually reads on every watchdog cycle, so
                            # without this the out-of-band SPS is lost on restart
                            # (the failure persisted live even for cached cameras).
                            with open(sdp_path, "w") as _f2:
                                _f2.write(_inject_sprop(_new_sdp, self.device_id))
                        except Exception as _sdp_exc2:
                            _LOGGER.warning("could not rewrite SDP for restart: %s", _sdp_exc2)
                        proc = subprocess.Popen(
                            cmd,
                            stdout=subprocess.DEVNULL,
                            stderr=subprocess.PIPE,
                        )
                        _start_serve_stderr_drain(proc)
                        # Point the shared holder at the live proc immediately.
                        # The bridge thread polls _proc_holder[0]; if it still
                        # sees the terminated old proc it logs "stream ended",
                        # breaks, and closes the loopback sockets - starving the
                        # restarted ffmpeg (0-frame stream).
                        _proc_holder[0] = proc
                        # New proc is live and owns the holder; clear the
                        # teardown flag so this session's NEXT exit (the
                        # restarted ffmpeg's) is classified fresh.
                        _teardown_holder[0] = False
                        _status("ffmpeg restarted with camera's SRTP keys")
        except TimeoutError:
            if _cam_echo_received:
                # We already sent webrtcResp in response to the camera's echo.
                # The camera should now be streaming; keep ffmpeg running.
                _status(
                    f"no second webrtcResp in {_sdes_answer_timeout:.0f}s"
                    " - camera acknowledged; continuing with ffmpeg"
                )
            elif dtls_fallback_ok:
                # Camera likely requires DTLS despite enableSdes='1'.  Kill
                # ffmpeg and signal the caller to retry with the DTLS path.
                _status(
                    f"no webrtcResp in {_sdes_answer_timeout:.0f}s"
                    " - SDES handshake failed; aborting ffmpeg and falling back to DTLS"
                )
                # Locally-initiated abort (no answer; giving up on SDES for this
                # attempt) - flag it so the bridge observe loop does not warn on
                # the resulting signal death.  Nothing continues to use this
                # proc/holder afterward, so no reset is needed here.
                _teardown_holder[0] = True
                proc.terminate()
                try:
                    proc.wait(timeout=2)
                except Exception:
                    proc.kill()
                for _rsock in (_audio_sock, _video_sock):
                    try:
                        _rsock.close()
                    except Exception:
                        _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_udp_port_bound', exc_info=True)
                try:
                    os.unlink(sdp_path)
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_udp_port_bound', exc_info=True)
                outgoing_q.put_nowait(None)   # signal MQTT thread to exit
                raise CameraMixin._SdesNoAnswerError()
            else:
                # isDTLS='0': DTLS fallback is disabled by camera flags (NOT that
                # the camera lacks DTLS - see the NOTE above), so there is nothing
                # to fall back to.  Some SDES cameras (e.g. LK.IPC.A001064) start
                # streaming SRTP directly to our ports without sending a
                # webrtcResp SDP answer.  Keep ffmpeg running - it already
                # has the SRTP key it needs from the offered SDP, and the
                # camera's RTP should start arriving momentarily.
                _status(
                    f"no webrtcResp in {_sdes_answer_timeout:.0f}s"
                    " - isDTLS=0: DTLS fallback not available;"
                    " continuing with ffmpeg (camera may stream without SDP answer)"
                )
                # Battery cameras often answer after the 20s timeout (they take
                # 15-25s to wake).  The answer arrives as a second webrtcResp
                # (answer_fut was cancelled, so MQTT handler sets second_answer_fut).
                # Spawn a background task to pick it up and inject ICE credentials
                # into _bridge_uc_info so the bridge can send USE-CANDIDATE.
                if second_answer_fut is not None:
                    import re as _re_sa
                    async def _late_second_answer_task():
                        nonlocal _dc_answer_has_app
                        try:
                            _la = await asyncio.wait_for(
                                asyncio.shield(second_answer_fut), timeout=30.0
                            )
                            _la_sdp = (_la or {}).get("sdp", "") if _la else ""
                            if (_la_sdp
                                    and not _bridge_uc_info["sent"]
                                    and not _bridge_uc_info["ufrag"]):
                                _la_ufrag = _la_pwd = ""
                                _la_cands: list = []
                                for _ll in _la_sdp.splitlines():
                                    if _ll.startswith("a=ice-ufrag:") and not _la_ufrag:
                                        _la_ufrag = _ll[len("a=ice-ufrag:"):].strip()
                                    elif _ll.startswith("a=ice-pwd:") and not _la_pwd:
                                        _la_pwd = _ll[len("a=ice-pwd:"):].strip()
                                    elif _ll.startswith("a=candidate:"):
                                        _cm2 = _re_sa.match(
                                            r"a=candidate:\S+ \d+ udp \d+ ([\d.]+) (\d+) typ",
                                            _ll,
                                        )
                                        if _cm2:
                                            _la_cands.append(
                                                (_cm2.group(1), int(_cm2.group(2))))
                                if _la_ufrag and _la_pwd and _la_cands:
                                    _bridge_uc_info["ufrag"]  = _la_ufrag
                                    _bridge_uc_info["pwd"]    = _la_pwd
                                    _bridge_uc_info["cands"]  = _la_cands
                                    if "m=application" in _la_sdp:
                                        _dc_answer_has_app = True
                                    _status(
                                        f"late second_answer_fut: ICE creds parsed"
                                        f" ({len(_la_cands)} candidate(s))"
                                        + (" [m=app]" if _dc_answer_has_app else "")
                                    )
                        except Exception:
                            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_late_second_answer_task', exc_info=True)
                    _spawn_bg(_late_second_answer_task())

        return SdesSession(
            proc=proc,
            sdp_path=sdp_path,
            outgoing_q=outgoing_q,
            mqtt_fut=mqtt_fut,
            audio_sock=_audio_sock,   # bridge thread keeps these open; stop() closes them
            video_sock=_video_sock,
            cmd_chan=_cmd_chan,
            talk_state=_talk_state,
            media_progress=_media_progress,
            media_counts=_media_counts,
            teardown_requested=_teardown_holder,
            first_video_pt=_first_video_pt,
            first_audio_pt=_first_audio_pt,
            device_id=getattr(self, "device_id", None),
            responses=_avio_responses,
            abort_chan=_abort_chan,
        )
