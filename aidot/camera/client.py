"""Camera / WebRTC / SDES extensions, layered additively over the core DeviceClient."""

import json
import logging
import os
import random
import time
import asyncio
from typing import Any, Callable, List, Optional

from ..exceptions import AidotCameraBusy, AidotCameraNotReady
from ..login_const import APP_ID as _AIDOT_APP_ID
from ..const import (
    LOGIN_INFO_PERSISTENT_MQTT_KEY,
    LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY,
)

# Wire/protocol constants live in constants.py; re-imported here so all
# in-module references (and any external importers) keep resolving.
from .constants import (
    _LEEDARSON_APP_ID,
    _SMARTHOME_URL_TEMPLATE,
    GETSTREAMCTRL_CMD,  # noqa: F401 - re-exported for back-compat (public pair; unused in-module)
    SDES_SPEAKERSTART_DELAY,  # noqa: F401 - used by the sdes_open mixin; kept re-exported
)
from .constants import (  # noqa: F401 - re-exported for device_client / back-compat
    TALK_PCM_FRAME_BYTES,
    TALK_PCM_RATE,
)
from .models import (  # re-exported: data models split into models.py
    CameraDeviceInformation,
    CameraStatusData,
    VideoFrame,
)
from .tutk import TutkStreamSession  # re-exported (split into tutk.py)
from .playback import (  # re-exported (split into playback.py)
    CloudPlaybackSession,
    LiveStreamSession,  # noqa: F401 - back-compat re-export (unused in-module)
)
from .webrtc import WebRTCSession  # re-exported (split into webrtc.py)
from .sdes import SdesSession  # noqa: F401 - re-exported (split into sdes.py); used by sdes_open mixin
from .controls import _CameraControlsMixin
from .webrtc_open import _WebRTCOpenMixin
from .sdes_open import _SdesOpenMixin
from .protocol import (  # noqa: F401 - used here and/or by the webrtc_open mixin; kept re-exported for back-compat
    _mqtt_timestamp,
    ReconnectPacer,
    _build_stun_binding_success_response,
    _terminal_webrtc_ack,
    _install_highport_nomination_patch,
    _compress_sdp_for_camera,
    _make_talk_audio_track,
    _webrtc_consume_video,
    _write_text_file,
    _make_sdp_tempfile,
    _terminate_proc,
    _extract_param_sets_from_rtp,
    _build_sprop,
    _load_sprop,
    _save_sprop,
    _inject_sprop,
    _sdes_serve_port,
    _serve_host,
    _warn_lan_serve,
    _ServeRelay,
    _rewrite_serve_port,
    _grab_free_port,
    _tcp_table_has_established_on_port,
    _idle_release_due,
    _dtls_av_mux_run,
    _h264_has_keyframe,
    _mqtt_session_sync,
    _mqtt_session,
    _mqtt_session_with_status,
    _mqtt_get_playback_server_info,
    _ip_looks_ascii_garbled,
    _normalize_bundle_ice_credentials,
    _reorder_m_section_ice_attrs,
    _filter_sdp_candidates,
    _dedup_bundle_candidates,
    _upgrade_sctp,
    _sdp_transport,
)
from .protocol import (  # noqa: F401 - re-exported for device_client / back-compat
    _CAMERA_ALARM_TYPES,
    _WEBRTC_TERMINAL_ACK_CODES,
    _highport_nomination_decision,
    _parse_alarm_event,
)

_LOGGER = logging.getLogger(__name__)

# Offline keepalive pause (see CameraMixin._backoff_or_offline_pause): while the
# cloud explicitly reports a device offline, failed-open retries re-check the
# flag every RECHECK seconds and only probe a real open every PROBE seconds,
# instead of consuming an open-gate slot on the normal reconnect cadence.
_OFFLINE_RECHECK_S = float(os.environ.get("AIDOT_OFFLINE_RECHECK_S", "30"))
_OFFLINE_PROBE_S = float(os.environ.get("AIDOT_OFFLINE_PROBE_S", "600"))

# Strong refs to fire-and-forget tasks: asyncio only keeps weak refs, so a
# discarded task can be garbage-collected mid-flight. Discarded on completion.
_BG_TASKS: set = set()


def _spawn_bg(coro):
    _t = asyncio.ensure_future(coro)
    _BG_TASKS.add(_t)

    def _done(task):
        _BG_TASKS.discard(task)
        # Retrieve the exception so a failing background task doesn't surface as
        # an unhandled "Task exception was never retrieved" at GC time; log it.
        if not task.cancelled():
            exc = task.exception()
            if exc is not None:
                _LOGGER.debug("aidot background task failed: %r", exc, exc_info=exc)

    _t.add_done_callback(_done)
    return _t


def _mqtt_publish_delivered(status) -> bool:
    """True if an MQTT command reached the broker (connection up, no error).

    Distinguishes a genuine fire-and-forget publish (broker connected, the device
    simply doesn't ACK) from a refused/failed connection - which returns an empty
    message list too, and must NOT be reported as "sent".
    """
    if not status:
        return True  # no status available: preserve prior best-effort behavior
    if status.get("error"):
        return False
    return status.get("connected", True) is not False


def _retry_policy(failure_kind: str, burst_attempt: int, *,
                  burst_delay: float = 3.0, burst_max: int = 4,
                  base_gate: float = 15.0) -> "tuple[float, bool]":
    """Return (delay_seconds, bypass_open_gate) for the DTLS serve retry.

    A clean 'not_ready' decline (camera awake but encoder still cold - a DC-only
    WebRTC answer) gets a bounded fast burst (burst_delay x burst_max) that
    bypasses the 15s inter-attempt gate, then falls back to the normal gate so a
    persistently-declining camera is not hammered.  Every other failure keeps
    the unchanged 15s behaviour."""
    if failure_kind == "not_ready" and burst_attempt <= burst_max:
        return (burst_delay, True)
    return (base_gate, False)


def _open_gate_delay(last_open_at: float, now: float, open_gate: float) -> float:
    """Seconds to wait before the next open to honour the inter-attempt gate.

    The DTLS serve loop spaces OPEN attempts >= open_gate apart, keyed to the
    PREVIOUS open's start (`last_open_at`).  Returns 0 when there is no prior
    open (`last_open_at` is the 0.0 sentinel - monotonic time is never 0) or the
    gate has already elapsed (e.g. after a long healthy session that just died,
    so it reopens immediately), else the remaining gate time.  This is the sole
    reconnect-spacing mechanism: a fast death still gets spaced a full gate from
    its open-start (anti-hammer), so no separate post-death delay is needed."""
    if not last_open_at:
        return 0.0
    remaining = open_gate - (now - last_open_at)
    return remaining if remaining > 0 else 0.0


_FFMPEG_MISSING_MSG = (
    "ffmpeg not found on PATH; install ffmpeg to enable recording/transcoding.\n"
    "  Ubuntu/Debian:    sudo apt install ffmpeg\n"
    "  macOS (Homebrew): brew install ffmpeg\n"
    "  Windows:          https://ffmpeg.org/download.html"
)


def _ffmpeg_path(binary: str = "ffmpeg") -> Optional[str]:
    """Return the resolved path to the ffmpeg binary, or None if not on PATH."""
    import shutil
    return shutil.which(binary)


# Runtime import: device_client defines these classes BEFORE importing this
# module (the package __init__ loads aidot.device_client first), so the
# partially-initialized module already exposes them here.
try:
    from ..device_client import DeviceStatusData
except ImportError as _exc:  # pragma: no cover - ordering contract violation
    raise ImportError(
        "aidot.camera.client must be imported via aidot.device_client "
        "(import aidot first); keep the camera import block in "
        "device_client.py below the DeviceStatusData/DeviceInformation "
        "definitions"
    ) from _exc






# --------------------------------------------------------------------------- #
# Camera / Leedarson smarthome API constants
# --------------------------------------------------------------------------- #



# --------------------------------------------------------------------------- #
# JPEG snapshot helper
# --------------------------------------------------------------------------- #

def _save_frame_as_jpeg(image_data: Any, output_path: str) -> bool:
    """Write a PIL Image or RGB numpy array to a JPEG file.

    image_data must already be fully decoded from any PyAV buffer before this
    is called (PIL Image and ndarray are both independent copies).
    """
    import os
    os.makedirs(os.path.dirname(os.path.abspath(output_path)) or ".", exist_ok=True)

    # PIL Image path (fastest, no subprocess)
    if hasattr(image_data, "save"):
        try:
            image_data.save(output_path, "JPEG")
            return True
        except Exception as exc:
            _LOGGER.warning("async_snapshot: PIL save failed: %s", exc)
            return False

    # numpy ndarray path - try PIL then ffmpeg
    try:
        import numpy as _np
        if isinstance(image_data, _np.ndarray):
            try:
                from PIL import Image as _PILImage
                _PILImage.fromarray(image_data).save(output_path, "JPEG")
                return True
            except ImportError:
                pass
            # Pillow not available - pipe raw RGB to ffmpeg
            import subprocess as _sp
            if _ffmpeg_path() is None:
                _LOGGER.warning("async_snapshot: %s", _FFMPEG_MISSING_MSG)
                return False
            h, w = image_data.shape[:2]
            r = _sp.run(
                [
                    "ffmpeg", "-y",
                    "-f", "rawvideo", "-pix_fmt", "rgb24",
                    "-s", f"{w}x{h}", "-i", "pipe:0",
                    "-vframes", "1", output_path,
                ],
                input=image_data.tobytes(),
                capture_output=True,
                timeout=15,  # bound the hang; a wedged ffmpeg must not block forever
            )
            return r.returncode == 0
    except Exception as exc:
        _LOGGER.warning("async_snapshot: encode failed: %s", exc)

    return False


def _build_sdes_serve_cmd(
    *,
    sdp_path: str,
    rtsp_push_url: Optional[str] = None,
    output_path: Optional[str] = None,
    max_seconds: Optional[float] = None,
    sdes_audio: bool = False,
    audio_gain_db: float = -8.0,
) -> list:
    """Build the ffmpeg argv for the SDES bridge serve.

    Pure (no I/O - the caller creates any output directory and resolves
    ``sdes_audio`` / ``audio_gain_db`` from opts/env).  Four destinations:

    - ``rtsp_push_url`` is http(s)  -> PULL model: serve mpegts over an
      ``-listen`` socket so go2rtc / HA's stream integration pull it (no go2rtc
      pre-registration).
    - ``rtsp_push_url`` is anything else -> legacy PUSH model: publish to an RTSP
      server (``-f rtsp``), for callers running their own pre-registered go2rtc.
    - ``output_path`` -> record to file.
    - neither -> ``-f null`` (keep ffmpeg alive draining SRTP, write nothing).

    AUDIO (the http-listen serve only): the mpegts mux writes its PAT/PMT once
    every mapped stream has produced a packet.  Video is ``-c:v copy`` (parameters
    known from the SDP at once); the ``-c:a aac`` encoder needs PCM samples to emit
    a frame, and battery cameras send PCMA sparsely.  So we feed the encoder a
    continuous ``anullsrc`` silence base and ``amix`` the real PCMA over it
    (``normalize=0`` -> the 0-valued silence is a no-op whenever real audio is
    present): the encoder is fed from t=0 so the PMT writes promptly and any gaps
    are filled with silence.  go2rtc/HA pulls this mpegts the same way as the
    video-only serve, so the pull model + cold-start relay are unchanged.
    ``volume`` is the stateless hot-mic trim (dynamic normalizers regressed the
    pipe in testing).  File recording (snapshots/diagnostics) is always -c copy."""
    time_args = ["-t", str(int(max_seconds))] if max_seconds else []

    if rtsp_push_url:
        _warn_lan_serve(_serve_host(rtsp_push_url), context="sdes-serve")

    if rtsp_push_url and rtsp_push_url.startswith("http"):
        if sdes_audio:
            dest_args = [
                # Silence-base mix: a continuous a-law-rate anullsrc keeps the AAC
                # encoder fed from t=0 so the mpegts PMT writes promptly on sparse
                # battery PCMA; real audio mixes over the 0-valued silence
                # (normalize=0 -> no-op when audio is present).
                "-f", "lavfi", "-i", "anullsrc=r=8000:cl=mono",
                "-filter_complex",
                ("[0:a]aresample=async=1[a0];"
                 "[a0][1:a]amix=inputs=2:duration=longest:normalize=0,"
                 f"volume={audio_gain_db}dB[aout]"),
                "-map", "0:v:0", "-map", "[aout]",
                "-c:v", "copy", "-c:a", "aac", "-ar", "48000", "-b:a", "128k",
                *time_args,
                "-f", "mpegts", "-listen", "1",
                rtsp_push_url,
            ]
        else:
            dest_args = [
                "-c:v", "copy", "-an",
                *time_args,
                "-f", "mpegts", "-listen", "1",
                rtsp_push_url,
            ]
    elif rtsp_push_url:
        dest_args = [
            "-c", "copy",
            *time_args,
            "-f", "rtsp", "-rtsp_transport", "tcp",
            rtsp_push_url,
        ]
    elif output_path:
        # File recording (snapshots, diagnostics) is always a plain copy of the
        # narrowed SDP - the audio mix is for the live serve, not file capture.
        dest_args = ["-c", "copy", *time_args, output_path]
    else:
        dest_args = [*time_args, "-f", "null", "/dev/null"]
    return [
        "ffmpeg", "-y",
        "-loglevel", "warning",
        "-protocol_whitelist", "file,rtp,udp,srtp",
        # 2 s analyzeduration: the camera sends SPS+PPS+IDR in the first burst;
        # 15 s consumed nearly all packets during analysis.  PLI re-arms an IDR
        # every 5 s so parameters always re-arrive.
        "-fflags", "+nobuffer+genpts",
        "-analyzeduration", "2000000",
        "-probesize", "500000",
        "-i", sdp_path,
        *dest_args,
    ]


# --------------------------------------------------------------------------- #
# TCP binary framing helpers
# --------------------------------------------------------------------------- #











# Terminal webrtcResp ack codes - the camera/cloud refused the stream and retrying
# is futile. Source: decompiled AckBean.java; the official app treats both as terminal
# (LiveCameraView.java:765 - shows an error, does NOT retry).
#   -50002 = WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED (max concurrent streams)
#   -50015 = LIVE_SD_MAX_CONNECT_ERROR (SD-card / connection cap)








# Real-time camera alarm types - carried as a TRANSIENT `alarmType` field inside the
# setDevAttrNotif MQTT push (iot/v1/c/{userId}/#), NOT a separate event message.
# Source: decompiled NewLiveFragment.java:6934-6938 / props.alarmType parse :3654.
#   65 = motion, 66 = person. (Historical events with picUrl/videoUrl are a separate
#   cloud-REST poll - async_get_cloud_recordings.)






# --------------------------------------------------------------------------- #
# CloudPlaybackSession
# --------------------------------------------------------------------------- #


# --------------------------------------------------------------------------- #
# TutkStreamSession
#
# TUTK IOTC P2P live-stream session for Leedarson/AiDot cameras.
# Use DeviceClient.async_open_live_stream() to obtain an instance.
#
# Protocol source: classes.jar.decompiled.zip / TutkManager.java
#   IOTC_Connect_ByUID_Parallel(uid, sid) -> nSID
#   avClientStart2(nSID, "admin", "admin123", ...) -> avIndex
#   avSendIOCtrl(avIndex, 511, ...) -> start video (IOTYPE_USER_IPCAM_START)
#   avRecvFrameData2(avIndex, ...) -> frame data loop
#
# Requires: libIOTCAPIs.so + libAVAPIs.so from the TUTK SDK.
# Obtain them from the TUTK SDK distribution or an extracted AiDot APK.
# --------------------------------------------------------------------------- #



# --------------------------------------------------------------------------- #
# LiveStreamSession
#
# Manages a single live-stream TCP session for a Leedarson/AiDot camera.
# Use DeviceClient.async_open_live_stream() to obtain an instance.
#
# Protocol source: iOS LDSXplayer startRealPlay -> LDSTCPManager
#   connectHost:port:sessionId:aesKey:heartbeat:msg:cmd:subCmd:cmdParam:tls:
#
# Wire format: same 37-byte header + payload as CloudPlaybackSession, but:
#   - TLS socket (server cert not verified -- IoT device)
#   - AES-256/ECB/PKCS7 encrypts outbound payloads; decrypts inbound payloads
#   - LOGIN payload carries sessionId from the MQTT connectipc response
#   - STREAM_REQ starts the live video feed (no taskId needed)
# --------------------------------------------------------------------------- #



# --------------------------------------------------------------------------- #
# WebRTCSession
#
# Manages a live WebRTC stream opened by DeviceClient.async_open_webrtc_stream.
# Call await session.stop() to tear down the peer connection and MQTT session.
# --------------------------------------------------------------------------- #





# Two-way-audio (talk) PCM format: signed-16-bit little-endian, 8 kHz mono, in
# 20 ms frames (PCMA/PT=8 on the wire). Exported so producers - e.g. the HA
# aidot.talk service - frame audio identically to what the talk track consumes.

# SDES two-way-audio timing.  SPEAKERSTART must be deferred ~0.6 s after the
# command channel is up: the camera ignores it if sent immediately after LIVING
# (no 851 ACK) but accepts it once its media pipeline is ready (~0.58 s observed).








# --------------------------------------------------------------------------- #
# MQTT helpers (playback provisioning + live-stream discovery)
#
# Uses paho-mqtt with WebSocket transport.  The synchronous paho loop runs in
# a thread-pool executor so it never blocks the asyncio event loop.
# Threading primitives (threading.Event, queue.Queue) replace the complex
# asyncio Future/call_soon_threadsafe bridge that had VERSION2 ReasonCode
# compatibility issues.
# --------------------------------------------------------------------------- #







_WEBRTC_OPEN_GATE: "Optional[asyncio.Semaphore]" = None


def _get_webrtc_open_gate() -> "asyncio.Semaphore":
    """Global gate that serializes WebRTC handshakes across ALL cameras.

    HA prefetches every camera's stream at once; 7 simultaneous handshakes drown
    the shared MQTT signaling channel and the cameras never answer
    ('no webrtcResp'). Limiting how many open concurrently lets them connect
    reliably. Tunable via AIDOT_MAX_CONCURRENT_OPENS (default 2).
    """
    global _WEBRTC_OPEN_GATE
    if _WEBRTC_OPEN_GATE is None:
        try:
            _n = max(1, int(os.environ.get("AIDOT_MAX_CONCURRENT_OPENS", "2")))
        except (ValueError, TypeError):
            _n = 2
        _WEBRTC_OPEN_GATE = asyncio.Semaphore(_n)
    return _WEBRTC_OPEN_GATE


_STREAM_SLOTS: "Optional[asyncio.Semaphore]" = None


def _get_stream_slots() -> "asyncio.Semaphore":
    """Global cap on concurrently-ACTIVE camera serves (vs the open-gate, which
    only limits concurrent handshakes).  Each active DTLS serve runs an aiortc
    decode + a PyAV mux thread + an AAC encoder; on a small host (Raspberry Pi)
    too many at once overwhelm it.  A camera holds a slot for the lifetime of its
    serve and waits for one if the cap is reached.  Tunable via
    AIDOT_MAX_CONCURRENT_STREAMS (default 3).

    NOT an app-parity feature - the official app/web is single-live-view and has
    no concurrency cap of any kind (its only concurrency limit is the camera-side
    -50002 SESSION_EXCEED terminal error, which we mirror separately).  This cap
    is an HA-specific resource guard: HA dashboards can request N cameras at once,
    which a phone never does.  Deliberate divergence, kept for host protection."""
    global _STREAM_SLOTS
    if _STREAM_SLOTS is None:
        try:
            _n = max(1, int(os.environ.get("AIDOT_MAX_CONCURRENT_STREAMS", "3")))
        except (ValueError, TypeError):
            _n = 3
        _STREAM_SLOTS = asyncio.Semaphore(_n)
    return _STREAM_SLOTS


# Where captured SPS/PPS are cached.  Override with AIDOT_SPROP_DIR so HA can
# point it at persistent storage (the core container's ~ is ephemeral; without a
# persistent dir the cache simply re-bootstraps from the first stream each boot).
# When AIDOT_SPROP_DIR is unset, honour the XDG base-directory spec: base the
# path on $XDG_CONFIG_HOME (falling back to ~/.config) so the cache lands in the
# user's configured config home rather than a hard-coded ~/.config.


































# --------------------------------------------------------------------------- #
# DeviceClient
# --------------------------------------------------------------------------- #





class CameraMixin(_CameraControlsMixin, _WebRTCOpenMixin, _SdesOpenMixin):
    """All camera/streaming methods, mixed into DeviceClient via inheritance."""

    # Devices without an aesKey never get this set by the core constructor;
    # default it so get_send_packet and friends see None instead of raising.
    aes_key = None

    # Optional LAN control client; when attached, attribute writes go local-first.
    _lan_client = None

    def attach_lan_client(self, lan_client) -> None:
        """Route camera attribute writes through ``lan_client`` (local-first,
        cloud-fallback).  Pass an ``aidot.camera.lan_control.CameraLanClient``."""
        self._lan_client = lan_client

    def detach_lan_client(self) -> None:
        self._lan_client = None

    def _init_camera_state(self, device: dict, user_info: dict) -> None:
        """Camera-side state init, called once at the end of DeviceClient.__init__.

        Everything camera-specific that the upstream constructor does not set
        lives here, so the core __init__ body stays aligned with upstream.
        """
        # Swap in the camera-aware status/info supersets created by the core,
        # carrying forward anything the core constructor seeded into the
        # originals so upstream-added seeding cannot be lost silently.
        _core_status, _core_info = self.status, self.info
        self.status = CameraStatusData()
        self.status.__dict__.update(_core_status.__dict__)
        self.info = CameraDeviceInformation(device)
        for _k, _v in _core_info.__dict__.items():
            self.info.__dict__.setdefault(_k, _v)
        # livePlayReq dseq - app parity (Q0(): starts at 100, increments per
        # request).  Camera tracks the live-play sequence; 0 is not a valid seq.
        self._live_dseq = 100
        # Store full user_info for camera API calls
        self._user_info: dict[str, Any] = user_info
        # Region written to login_info by AidotClient.async_post_login()
        self._region: str = user_info.get("region", "us")
        # Cache slot for MQTT broker URL, fetched lazily on first playback call
        self._mqtt_url: Optional[str] = None
        # Cache slot for Leedarson smarthome auth (mqttUser, mqttPassword, userId)
        # Fetched lazily via _async_get_smarthome_auth()
        self._smarthome_auth: Optional[dict] = None
        # Cache for the HTTP ICE/TURN config.  Each TURN entry carries a server
        # ``ttl`` (Unix epoch), so caching until just before it is safe and saves
        # the ~2s fetch on repeat cold opens.  Reset on token refresh below
        # (credentials are bound to the access token).
        self._cached_ice_config: Optional[dict] = None
        self._ice_config_expiry: float = 0.0
        # Cache for batchGetDeviceUserInfo.  Unlike ICE config there is no server
        # ttl, so cap with a fixed TTL (see _DEVICE_USER_INFO_TTL): the extracted
        # numeric userId/uuid are account-static, and the LAN-IP field is vestigial
        # (a stale IP at worst skips one redundant user/connect).  On a warm reopen
        # this is the only otherwise-uncached HTTP round-trip.  Reset on token
        # refresh below (the request is access-token authenticated).
        self._cached_device_user_info: Optional[dict] = None
        self._device_user_info_expiry: float = 0.0
        # Async callback (set by AidotClient) that refreshes the access token and
        # updates the shared login_info in place, so camera HTTP calls can recover
        # from a 21026 "Please login again" instead of failing silently.
        self._token_refresh_cb: "Optional[Callable]" = None
        # Raw device dict retained for transport-type detection (isDTLS field)
        self._raw_device: dict = device

        # Seed camera/diagnostic status from the cloud device "properties" payload
        # (Battery_remaining, Occupancy, SDcardStatus, MotionDetection_*, ...).  This
        # is the authoritative, always-current source the official app itself reads
        # - cameras do not push these reliably over MQTT - so sensors populate
        # immediately on load, before any poll.  No-op for devices without it.
        self.update_status_from_device(device)
        # Full list of device IDs for this account.  Used when calling
        # batchGetDeviceUserInfo so the server returns data for this device
        # (sending only one ID may yield an empty response).  Populated by
        # AidotClient after the full device list is fetched; falls back to
        # just this device's ID so standalone DeviceClient usage still works.
        self._all_device_ids: list = [self.device_id]
        # Pre-populate _ip_address from the device-list entry if it contains
        # an IP field.  Covers cameras (e.g. LK.IPC.A001064) whose
        # batchGetDeviceUserInfo response returns no IP and whose firmware
        # does not push setDevAttrNotif promptly after user/connect.
        _dev_ip_init = (
            device.get("localIp") or device.get("ipAddress")
            or device.get("ip") or device.get("localIPAddress")
            or device.get("wlanIp") or device.get("wifiIp")
            or device.get("lanIp") or device.get("addr")
            or (device.get("properties") or {}).get("ipAddress")
            or (device.get("properties") or {}).get("ip")
        )
        if _dev_ip_init:
            # Guard against ASCII-encoded IPs (cloud stores a LAN IP's raw bytes
            # as the octets, e.g. "49.57.50.46" == ASCII of "192.").
            _ip_str = str(_dev_ip_init)
            if _ip_looks_ascii_garbled(_ip_str):
                _LOGGER.warning(
                    "DeviceClient %s: ignoring ASCII-encoded IP %r from device dict "
                    "(cloud stored IP bytes as ASCII chars; real IP unknown)",
                    device.get("id") or device.get("devId"), _ip_str,
                )
            else:
                self._ip_address = _ip_str
        # Persistent background streaming state
        self.latest_jpeg: Optional[bytes] = None
        self._streaming_active: bool = False
        self._stream_session: Optional[Any] = None
        self._stream_task: Optional["asyncio.Task[None]"] = None
        self._stream_mqtt_drain: Optional["asyncio.Future"] = None
        self._last_frame_time: float = 0.0
        self._keepalive_rtsp_url: Optional[str] = None  # local serve URL (go2rtc pulls)
        self._go2rtc_url: Optional[str] = None           # go2rtc API base (prefer-go2rtc)
        self._go2rtc_pull_url: Optional[str] = None      # go2rtc pull URL once registered
        self._go2rtc_task: "Optional[asyncio.Task[None]]" = None
        # Set by the DTLS serve loop once ffmpeg is bound + serving, so
        # camera.stream_source() can wait and hand HA a ready URL (avoids HA's
        # ~40s connection-refused retry gap on cold start).
        self._serve_ready: "asyncio.Event" = asyncio.Event()
        # Cold-start serve-port relay (holds the public port connectable through
        # the handshake so an eager go2rtc pull waits instead of being refused).
        self._serve_relay: "Optional[_ServeRelay]" = None
        # Cold-start instrumentation: monotonic at the start of an open, so any
        # thread (signaling, bridge, serve) can log elapsed-ms phase markers.
        self._cold_open_t0: "Optional[float]" = None
        # Motion/event polling (cloud event list). NOTE: the camera does NOT push motion
        # to a passive MQTT subscriber - alarmType is only emitted during an active live
        # view (decompiled NewLiveFragment) - so real-time-ish motion for HA comes from
        # polling the cloud event list, mirroring how the app surfaces background events.
        self._motion_active: bool = False
        self._motion_cb: Optional[Callable] = None
        self._motion_task: Optional["asyncio.Task[None]"] = None
        # Insertion-ordered set (dict keys) so the memory-bound trim can evict
        # the OLDEST uids deterministically rather than arbitrary ones.
        self._motion_seen: dict = {}
        self._motion_interval: float = 30.0

    # -- Pre-0.6 core API, kept for existing consumers ----------------------- #

    def set_status_fresh_cb(self, callback) -> None:
        self.on_status_update = callback

    async def read_status(self):
        return self.status

    # Camera models that report ``enableSdes: '1'`` but whose SDES-SRTP
    # implementation is non-functional on the wire.  Forced to the DTLS path.
    #
    # Models for which we should NOT include an SCTP data channel in the
    # WebRTC offer.  Most KVS-WebRTC firmware (LK.IPC.A000088, .A001513) sends
    # SCTP control messages over DTLS application_data after handshake; if the
    # client never set up an SCTP transport, the camera retransmits, then
    # tears DTLS down with close_notify after ~22 s - observed via byte-tap.
    # A001064 is the exception: across runs it answers our datachannel offer
    # with (a) no m=application section, (b) a different kind, or (c) extra
    # video sections - all of which break setRemoteDescription.  Keep it
    # data-channel-less; it's parked on the SDES/TUTK side anyway.
    _NO_DATACHANNEL_MODELS: frozenset = frozenset({"LK.IPC.A001064"})

    # A001064's role-reversal handshake (the camera echoes our offer back as its
    # own webrtcReq before doing ICE, and won't stream until it gets our
    # webrtcResp) needs the camera armed BEFORE our webrtcReq.  sdes_fast_liveplay
    # sends webrtcReq ~4.5 s earlier, which degrades A001064 media reliability
    # (2/2 -> 1/2 media in a live A/B; the flag was soak-validated only on the
    # A001513 battery cameras).  Exclude role-reversal models from the flag.
    _NO_FAST_LIVEPLAY_MODELS: frozenset = frozenset({"LK.IPC.A001064"})

    @property
    def _offer_should_include_datachannel(self) -> bool:
        model = getattr(getattr(self, "info", None), "model_id", None)
        return model not in self._NO_DATACHANNEL_MODELS

    @property
    def is_sdes_camera(self) -> bool:
        """True when ``properties.enableSdes == '1'`` in the device API response."""
        props = getattr(self, "_raw_device", {}).get("properties") or {}
        return str(props.get("enableSdes", "0")) == "1"

    @property
    def is_battery_camera(self) -> bool:
        """True for battery/low-power models (A001513, A001108, A001360).
        Source: IpcServiceImpl.java B() - powerType=2 for these models.
        """
        model = getattr(getattr(self, "info", None), "model_id", None) or ""
        return any(m in model for m in ("A001513", "A001108", "A001360"))

    def _next_dseq(self) -> int:
        """Next livePlayReq dseq (app parity: Q0() starts at 100, increments)."""
        d = self._live_dseq
        self._live_dseq += 1
        return d


    def update_status_from_device(self, device: dict) -> "DeviceStatusData":
        """Refresh camera/diagnostic status from a cloud device dict.

        The device-list payload carries every camera attribute under
        ``properties`` (battery, SD-card, occupancy, motion/night-vision
        settings, ...) plus a top-level ``online`` flag.  This is the reliable,
        always-current source the official app reads, so HA populates camera
        sensors and control-entity states from it instead of an MQTT push the
        camera never sends.  Returns the updated status.
        """
        if not isinstance(device, dict):
            return self.status
        self._raw_device = device
        props = device.get("properties")
        if isinstance(props, dict):
            self.status.update_from_camera_attributes(props)
        if device.get("online") is not None:
            self.status.online = bool(device.get("online"))
            # The cloud explicitly reported reachability - only then may the
            # keepalive loops trust an offline flag (see
            # _backoff_or_offline_pause; DeviceStatusData.online defaults to
            # False, which must not read as "offline").
            self._cloud_online_explicit = True
        return self.status

    async def _backoff_or_offline_pause(self, delay: float) -> None:
        """Sleep a reconnect backoff, extended while the device is cloud-offline.

        Normal case (device online, or the cloud never reported an online
        flag): plain ``asyncio.sleep(delay)`` - behavior unchanged.

        When an open just failed AND the cloud has explicitly reported the
        device offline (``update_status_from_device`` saw ``online: false``),
        hold here instead of retrying on the pacer cadence: re-check the flag
        every ``AIDOT_OFFLINE_RECHECK_S`` (default 30 s) and resume the moment
        it flips back on, or fall through after ``AIDOT_OFFLINE_PROBE_S``
        (default 600 s) so one probe open still runs in case the cloud flag is
        stale.  This keeps a dead/unpowered camera from consuming an open-gate
        slot every backoff cycle and starving live cameras' cold opens
        (observed live: two unpowered A000088s cycling 30 s open attempts
        pushed a healthy camera's cold open past two minutes).
        """
        if self.status.online or not getattr(self, "_cloud_online_explicit", False):
            await asyncio.sleep(delay)
            return
        _LOGGER.info(
            "keepalive[%s]: device is cloud-offline - pausing open retries "
            "(recheck %.0fs, probe %.0fs)",
            self.device_id, _OFFLINE_RECHECK_S, _OFFLINE_PROBE_S,
        )
        _t0 = time.monotonic()
        while (_elapsed := time.monotonic() - _t0) < _OFFLINE_PROBE_S:
            await asyncio.sleep(
                min(_OFFLINE_RECHECK_S, max(0.1, _OFFLINE_PROBE_S - _elapsed))
            )
            if not getattr(self, "_streaming_active", False):
                return
            if self.status.online:
                _LOGGER.info(
                    "keepalive[%s]: device back online - resuming opens",
                    self.device_id,
                )
                return
        _LOGGER.debug(
            "keepalive[%s]: offline probe interval elapsed - probing one open",
            self.device_id,
        )

    # -- Camera helpers ------------------------------------------------------ #

    @property
    def _smarthome_base(self) -> str:
        return _SMARTHOME_URL_TEMPLATE.format(region=self._region)

    def set_token_refresh_cb(self, cb: "Callable") -> None:
        """Register the AidotClient coroutine that refreshes the access token."""
        self._token_refresh_cb = cb

    @staticmethod
    def _is_auth_error(data: Any) -> bool:
        """True if a smarthome response means the token is stale (re-login)."""
        if not isinstance(data, dict):
            return False
        code = data.get("code")
        if code in (21026, 21027, 21041, "21026", "21027", "21041"):
            return True
        return "login again" in str(data.get("desc", "")).lower()

    async def _async_refresh_auth_token(self) -> bool:
        """Refresh the access token via the AidotClient callback (in place)."""
        cb = self._token_refresh_cb
        if cb is None:
            return False
        # A fresh token invalidates the cached smarthome auth / MQTT URL and
        # any cached ICE/TURN config (credentials are bound to the access token).
        self._smarthome_auth = None
        self._mqtt_url = None
        self._cached_ice_config = None
        self._ice_config_expiry = 0.0
        self._cached_device_user_info = None
        self._device_user_info_expiry = 0.0
        try:
            return bool(await cb())
        except Exception as exc:
            _LOGGER.debug("token refresh failed for %s: %s", self.device_id, exc)
            return False

    def _leedarson_headers(self) -> dict:
        # Headers from OkHttp interceptor (b.java): token, appId, terminal auto-injected.
        # NOTE: "owner" is intentionally omitted - the recording/playback endpoints
        # return 560073 "Share head error" when owner is present; token alone identifies
        # the user on us-smarthome.arnoo.com.  Add owner only if a specific endpoint
        # requires it (none currently do).
        token = (
            self._user_info.get("accessToken")
            or self._user_info.get("access_token")
            or ""
        )
        return {
            "terminal":        "app",
            "token":           token,
            "appId":           _LEEDARSON_APP_ID,
            "active-language": "en_US",
            "Content-Type":    "application/json",
        }

    def _owner_id(self) -> str:
        """The Leedarson ``owner`` (userId) header value some IPC endpoints require.

        ``_leedarson_headers`` deliberately omits ``owner`` (it breaks
        recording/playback); the wake and liveStreamParam endpoints need it.
        """
        return (
            self._user_info.get("owner")
            or self._user_info.get("id")
            or self._user_info.get("userId")
            or str(self.user_id)
        )

    async def _async_post_ok(
        self, url: str, headers: dict, body: str, *, timeout: float = 10.0,
        label: str = "ipc post",
    ) -> bool:
        """POST a JSON ``body`` string to a Leedarson IPC endpoint; True on success.

        Success = HTTP 200 with a ``code`` of 0/200/absent. Best-effort: any
        network/parse error returns False, never raises. Shared by the wake and
        liveStreamParam provisioning calls.
        """
        import aiohttp

        try:
            async with aiohttp.ClientSession() as session:
                async with session.post(
                    url, headers=headers, data=body,
                    timeout=aiohttp.ClientTimeout(total=timeout),
                ) as resp:
                    status = resp.status
                    data = await resp.json(content_type=None)
            code = data.get("code") if isinstance(data, dict) else None
            ok = status == 200 and (code in (None, 0, 200, "0", "200"))
            _LOGGER.debug("%s %s: status=%s code=%s ok=%s",
                          label, self.device_id, status, code, ok)
            return bool(ok)
        except Exception as exc:
            _LOGGER.debug("%s failed for %s: %s", label, self.device_id, exc)
            return False

    async def _async_http_wake(self) -> bool:
        """Wake a battery camera via the cloud HTTP low-power endpoint.

        Mirrors DeviceWakeUpRepos.j() in the official app (n.java): POST
        ``{smarthome}/api/ipc/devices/{id}/lowPowerActiveState`` with body
        ``{"deviceId": id, "status": "wakeup"}`` and owner+token headers.  The
        cloud forwards the wake to the camera's always-on low-power channel, so it
        reaches a deeply-sleeping camera that has dropped its MQTT session - unlike
        the MQTT ``lowPowerActiveStateReq``, which only lands if the camera is
        already connected.  Returns True if the request was accepted (HTTP 200).
        """
        # The wake endpoint (unlike recording/playback) DOES require owner (n.java:71).
        headers = self._leedarson_headers()
        headers["owner"] = self._owner_id()
        url = (f"{self._smarthome_base}/api/ipc/devices/"
               f"{self.device_id}/lowPowerActiveState")
        body = json.dumps({"deviceId": self.device_id, "status": "wakeup"})
        return await self._async_post_ok(url, headers, body, label="http wake")

    def _live_stream_param_request(self):
        """Build ``(url, headers, body)`` for the liveStreamParam provision call.

        Pure/deterministic - unit-tested in tests/test_live_stream_param.py.  The
        body MUST be a JSON array of device ids (object body -> HTTP 500) and the
        ``owner`` header is required (as on the wake endpoint).
        """
        headers = self._leedarson_headers()
        headers["owner"] = self._owner_id()
        url = f"{self._smarthome_base}/api/ipc/liveStream/liveStreamParam"
        return url, headers, json.dumps([self.device_id])

    async def _async_fetch_live_stream_param(self) -> bool:
        """Provision the camera's live-stream session via the cloud (app parity).

        Battery cameras waking from deep sleep reject the MQTT ``livePlayReq``
        with code ``-50019`` ("not ready") and then never run ICE - so no media
        ever flows - unless the app's pre-connect HTTP call has armed the session
        first.  The official app (``KVSPreConnectStrategy.fetchKvsParams``) POSTs
        the device id to ``/api/ipc/liveStream/liveStreamParam``; the cloud
        provisions the session (and brings the camera online) and returns AWS KVS
        credentials.  We only need the provisioning side effect - the library
        streams over the proprietary MQTT/SDES path, not AWS KVS - so the returned
        credentials are ignored.  Best-effort: returns True on HTTP 200.
        """
        url, headers, body = self._live_stream_param_request()
        return await self._async_post_ok(url, headers, body, label="liveStreamParam")

    async def async_wake_camera(self, retries: int = 2) -> bool:
        """Wake a battery camera on demand via the cloud HTTP low-power endpoint.

        Mirrors the official app's wake (DeviceWakeUpRepos.k()), which fires the
        HTTP wake so a sleeping camera (no live MQTT session) still gets the signal
        through the cloud, and retries on failure (n.java onError "正在重试").  The
        MQTT ``lowPowerActiveStateReq`` is also published by the streaming /
        attribute paths; this adds the reliable cloud-side channel.  Non-battery
        cameras are always reachable, so this is a no-op for them.
        """
        if not self.is_battery_camera:
            return True
        for _attempt in range(max(1, retries)):
            if await self._async_http_wake():
                return True
            await asyncio.sleep(1.0)
        return False

    async def _async_get_mqtt_url(self) -> Optional[str]:
        # Fetch and cache the WSS MQTT broker URL (and MQTT credentials) from
        # getServerUrlConfig.  The full response is stored in self._smarthome_auth
        # so mqttUser / mqttPassword are captured in the same call.
        if self._mqtt_url:
            return self._mqtt_url

        import aiohttp

        async def _fetch():
            _hdrs = {k: v for k, v in self._leedarson_headers().items()
                     if k != "Content-Type"}
            async with aiohttp.ClientSession() as session:
                async with session.get(
                    f"{self._smarthome_base}/commonController/getServerUrlConfig",
                    headers=_hdrs,
                    params={"version": "1.0.1", "clientId": f"app-{self.user_id}"},
                    timeout=aiohttp.ClientTimeout(total=10),
                ) as resp:
                    return await resp.json(content_type=None)

        try:
            body = await _fetch()
            if self._is_auth_error(body) and await self._async_refresh_auth_token():
                body = await _fetch()  # retry once with the refreshed token

            data = body.get("data") or {}
            _LOGGER.debug("getServerUrlConfig: mqtt=%s ip=%s", data.get("mqttServerUrl"), data.get("ip"))

            mqtt_host = data.get("mqttServerUrl") or ""
            if not mqtt_host:
                # Fall back to the regional MQTT broker URL known to work from
                # the AiDot web client: wss://{region}-mqtt.arnoo.com:8443/mqtt
                # Redacted: `data`/`body` carries mqttPassword; log only the
                # non-sensitive key names present, mirroring the hasPwd-style
                # redaction of the debug log below.
                _LOGGER.warning(
                    "getServerUrlConfig returned no mqttServerUrl; "
                    "using regional fallback. keys=%s", sorted(data)
                )
                mqtt_host = f"wss://{self._region}-mqtt.arnoo.com:8443/mqtt"

            self._mqtt_url = (
                mqtt_host
                if mqtt_host.startswith(("wss://", "ws://"))
                else f"wss://{mqtt_host}"
            )

            # Capture mqttUser + mqttPassword from this same response if present.
            # iOS SDK binary strings confirm these fields cluster with mqttServerUrl.
            if not self._smarthome_auth:
                self._smarthome_auth = {
                    "mqttUrl":      self._mqtt_url,
                    "mqttUser":     (data.get("mqttUser") or data.get("userId")
                                     or str(self.user_id)),
                    "mqttPassword": (data.get("mqttPassword") or data.get("mqqtPwd")
                                     or ""),
                    "raw":          data,
                }
                _LOGGER.debug(
                    "getServerUrlConfig cached: url=%s user=%s hasPwd=%s",
                    self._mqtt_url,
                    self._smarthome_auth["mqttUser"],
                    bool(self._smarthome_auth["mqttPassword"]),
                )

            # Always forward the broker-observed public IP into _smarthome_auth.raw
            # so _open_sdes_stream can build srflx candidates for WAN cameras.
            # When Strategy 1 auth (mqttPassword in login_info) fires first it sets
            # raw={"source": ...} with no "ip" key; without this patch _public_ip
            # stays None, no srflx candidate is advertised, and cameras on WAN
            # (or a different subnet) can never reach our reservation sockets.
            _raw_ref = (self._smarthome_auth or {}).get("raw")
            if data.get("ip") and isinstance(_raw_ref, dict) and "ip" not in _raw_ref:
                _raw_ref["ip"] = data["ip"]

            return self._mqtt_url

        except Exception as exc:
            _LOGGER.error("_async_get_mqtt_url failed: %s", exc)
            return None

    async def _async_get_smarthome_auth(self) -> Optional[dict]:
        """Fetch MQTT credentials (mqttUser + mqttPassword) for the Arnoo broker.

        Strategy order - stops at first success:
          0. Already cached
          1. mqttPassword already in AiDot login_info (from _async_fetch_user_config)
          2. GET /user/getUser  <- documented SDK step (reqUserAuthInfoWithCallback)
             Returns LDSAuthInfo {userId, mqttUser, mqttPassword, ...}
          3. getServerUrlConfig response side-effect (if MQTT URL not yet fetched)
          4. accessToken as MQTT password (Arnoo broker fallback)
        """
        if self._smarthome_auth and self._smarthome_auth.get("mqttPassword"):
            return self._smarthome_auth

        import aiohttp

        # Smarthome userId - 'id' in AiDot login_info is the Leedarson userId
        _mqtt_id = (
            self._user_info.get("id")
            or self._user_info.get("userId")
            or str(self.user_id)
        )

        # --- Strategy 1: mqttPassword already in AiDot login_info ---
        # _async_fetch_user_config() stores it under "mqttPassword" or "mqttPwd".
        for key in ("mqttPassword", "mqttPwd"):
            val = self._user_info.get(key)
            if val:
                self._smarthome_auth = {
                    "mqttUser":     _mqtt_id,
                    "mqttPassword": val,
                    "userId":       _mqtt_id,
                    "raw":          {"source": f"login_info.{key}"},
                }
                return self._smarthome_auth

        # --- Strategy 2: GET /user/getUser  (reqUserAuthInfoWithCallback in SDK) ---
        # CocoaPods README documents the 3-step auth flow:
        #   1. loginWithUserName -> accessToken
        #   2. setHeader (set token on SDK)
        #   3. reqUserAuthInfoWithCallback -> LDSAuthInfo {mqttUser, mqttPassword}
        token = (
            self._user_info.get("accessToken")
            or self._user_info.get("access_token")
            or ""
        )
        if token:
            headers = self._leedarson_headers()
            try:
                async with aiohttp.ClientSession() as _s2:
                    async with _s2.post(
                        f"{self._smarthome_base}/user/getUser",
                        headers=headers,
                        json={"desc": _mqtt_id},
                        timeout=aiohttp.ClientTimeout(total=10),
                    ) as resp:
                        body = await resp.json(content_type=None)

                code = body.get("code")
                data = body.get("data") or {}
                _LOGGER.debug(
                    "_async_get_smarthome_auth /user/getUser -> code=%s  data_keys=%s",
                    code, list(data.keys()) if isinstance(data, dict) else data,
                )
                if isinstance(data, dict):
                    auth = data.get("authInfo") or data
                    mqtt_user = (
                        auth.get("mqttUser")
                        or auth.get("userId")
                        or auth.get("associatedAccount")
                        or _mqtt_id
                    )
                    mqtt_pwd = (
                        auth.get("mqttPassword")
                        or auth.get("mqqtPwd")
                        or auth.get("mqttPwd")
                        or ""
                    )
                    if mqtt_pwd:
                        self._smarthome_auth = {
                            "mqttUser":     mqtt_user,
                            "mqttPassword": mqtt_pwd,
                            "userId":       auth.get("userId") or mqtt_user,
                            "raw":          auth,
                        }
                        _LOGGER.debug(
                            "_async_get_smarthome_auth OK via /user/getUser: mqttUser=%s",
                            mqtt_user,
                        )
                        return self._smarthome_auth
            except Exception as exc:
                _LOGGER.debug("_async_get_smarthome_auth /user/getUser exc: %s", exc)

        # --- Strategy 3: getServerUrlConfig ---
        if not self._mqtt_url:
            await self._async_get_mqtt_url()
        if self._smarthome_auth and self._smarthome_auth.get("mqttPassword"):
            _LOGGER.warning("_async_get_smarthome_auth: mqttPassword from getServerUrlConfig")
            return self._smarthome_auth

        # --- Strategy 4: accessToken as MQTT password (common Arnoo pattern) ---
        # The Arnoo broker accepts (userId, accessToken) as credentials when the
        # /user/getUser call fails.  Always available after AiDot login.
        access_token = (
            self._user_info.get("accessToken")
            or self._user_info.get("access_token")
            or ""
        )
        if access_token:
            _LOGGER.warning(
                "_async_get_smarthome_auth: /user/getUser gave no mqttPassword; "
                "falling back to userId+accessToken for MQTT."
            )
            self._smarthome_auth = {
                "mqttUser":     _mqtt_id,
                "mqttPassword": access_token,
                "userId":       _mqtt_id,
                "raw":          {"source": "accessToken_fallback"},
            }
            return self._smarthome_auth

        _LOGGER.error(
            "_async_get_smarthome_auth: all strategies failed. login_info_keys=%s",
            list(self._user_info.keys()),
        )
        return None

    # -- Camera public methods ----------------------------------------------- #

    @property
    def _aidot_v21_base(self) -> str:
        return f"https://prod-{self._region}-api.arnoo.com/v21"

    @property
    def _aidot_v32_base(self) -> str:
        return f"https://prod-{self._region}-api.arnoo.com/v32/api/ipc"

    def _aidot_headers(self) -> dict:
        # Auth headers for the AiDot platform API (prod-{region}-api.arnoo.com).
        # Matches AidotClient.async_session_get(): CONF_APP_ID="Appid", APP_ID,
        # CONF_TOKEN="Token", CONF_TERMINAL="Terminal" (see login_const.py/const.py).
        token = (self._user_info.get("accessToken")
                 or self._user_info.get("access_token") or "")
        return {
            "Appid":        _AIDOT_APP_ID,
            "Token":        token,
            "Terminal":     "app",
            "Content-Type": "application/json",
        }

    _DEVICE_USER_INFO_TTL = 300.0  # seconds; no server ttl, so a fixed cap

    def _store_device_user_info(self, item: "Optional[dict]") -> "Optional[dict]":
        """Cache the extracted per-device user info and return it (pass-through).

        Used at every success return of [[async_get_device_user_info]] so caching
        happens at one site.  A falsy/None item is NOT cached (fail-safe to a
        re-fetch next open).  TTL-bounded by `_DEVICE_USER_INFO_TTL` since there
        is no server-provided ttl."""
        if item:
            self._cached_device_user_info = item
            self._device_user_info_expiry = time.time() + self._DEVICE_USER_INFO_TTL
        return item

    async def async_get_device_user_info(
        self,
        all_device_ids: Optional[List[str]] = None,
    ) -> Optional[dict]:
        """Fetch per-device user info from the AiDot v21 API.

        POST /v21/devices/batchGetDeviceUserInfo
        Returns the raw data dict for this device, or None on failure.
        This is the same call the AiDot widget/app makes; the response includes
        the TUTK p2pId and any per-device streaming credentials.

        Args:
            all_device_ids: All device IDs to include in the batch request.
                The app sends all device IDs in a single call (~260 bytes for
                7 devices). Sending only one ID may cause the server to return
                an empty result. Pass the full list from the account's device
                listing if available.
        """
        if (self._cached_device_user_info is not None
                and time.time() < self._device_user_info_expiry):
            _LOGGER.debug("device_user_info: cache hit (%.0fs left)",
                          self._device_user_info_expiry - time.time())
            return self._cached_device_user_info
        import aiohttp
        ids = all_device_ids or [self.device_id]
        try:
            async with aiohttp.ClientSession() as session:
                async with session.post(
                    f"{self._aidot_v21_base}/devices/batchGetDeviceUserInfo",
                    json={"deviceIds": ids},
                    headers=self._aidot_headers(),
                    timeout=aiohttp.ClientTimeout(total=10),
                ) as resp:
                    body = await resp.json(content_type=None)
                    status = resp.status

            # Server may return a bare JSON array OR {"data": [...]} / {"data": {}}
            if isinstance(body, list):
                data = body
                _LOGGER.debug("batchGetDeviceUserInfo bare-list response for %s: %d items",
                              self.device_id, len(data))
            elif isinstance(body, dict):
                data = body.get("data") or {}
                if data:
                    _LOGGER.debug("batchGetDeviceUserInfo response for %s (status=%d): %s",
                                  self.device_id, status, body)
                else:
                    _LOGGER.warning(
                        "batchGetDeviceUserInfo no data for %s (status=%d): %s",
                        self.device_id, status, body,
                    )
            else:
                data = {}

            # Find the entry for this device
            if isinstance(data, dict):
                item = data.get(self.device_id) or next(iter(data.values()), None)
                return self._store_device_user_info(item)
            if isinstance(data, list):
                for item in data:
                    # Server may use "deviceId", "devId", or "id" as the device
                    # identifier field.  Try all variants so that the correct
                    # camera's entry is returned even when the field name differs
                    # from the most-common "deviceId".  Without this, the code
                    # falls back to data[0] (the first camera in the batch),
                    # producing the wrong numeric userId and broken MQTT topics.
                    if isinstance(item, dict) and (
                        item.get("deviceId") == self.device_id
                        or item.get("devId") == self.device_id
                        or item.get("id") == self.device_id
                    ):
                        _LOGGER.debug(
                            "batchGetDeviceUserInfo matched device %s: keys=%s",
                            self.device_id, sorted(item.keys()),
                        )
                        return self._store_device_user_info(item)
                # No exact match found - log which device IDs were present so
                # the caller can diagnose why the lookup failed.  Falling back
                # to data[0] will return the wrong camera's userId, which
                # produces incorrect MQTT subscription topics and prevents
                # getIceConfigResp (TURN credentials) from being received.
                _found_ids = [
                    item.get("deviceId") or item.get("devId") or item.get("id")
                    for item in data[:10] if isinstance(item, dict)
                ]
                _LOGGER.warning(
                    "batchGetDeviceUserInfo: no item matched device_id=%r"
                    " - falling back to data[0].  Device IDs in response: %s"
                    "  (wrong userId will cause broken MQTT topics and missing"
                    " TURN credentials)",
                    self.device_id, _found_ids,
                )
                return self._store_device_user_info(data[0] if data else None)
        except Exception as exc:
            _LOGGER.error("async_get_device_user_info failed for %s: %s",
                          self.device_id, exc)
        return None

    async def async_get_p2p_uid(self) -> Optional[str]:
        """Fetch the TUTK P2P UID for this camera.

        Tries several sources in order:
          1. POST /v21/devices/batchGetDeviceUserInfo       (AiDot platform API)
          2. POST /v5/deviceController/getP2pId?deviceId=...  (Leedarson smarthome)
          3. AiDot v32 IPC device detail                    (fallback)
        """
        import aiohttp

        # --- Source 1: AiDot v21 batchGetDeviceUserInfo ---
        try:
            dev_info = await self.async_get_device_user_info()
            if isinstance(dev_info, dict):
                uid = (dev_info.get("p2pId")
                       or dev_info.get("uid")
                       or dev_info.get("tutk_uid")
                       or dev_info.get("tutkUid"))
                if uid:
                    _LOGGER.debug("async_get_p2p_uid: got UID from batchGetDeviceUserInfo: %s", uid)
                    return str(uid)
        except Exception as exc:
            _LOGGER.debug("async_get_p2p_uid: batchGetDeviceUserInfo failed: %s", exc)

        # --- Source 2: Leedarson smarthome /v5/deviceController/getP2pId ---
        # The Android app (MutiCameraActivity) calls
        #   POST {base}/v5/deviceController/getP2pId?deviceId={deviceId}
        # with the deviceId as a URL QUERY param and an EMPTY body (GET is
        # rejected "method not supported"; a JSON body is rejected "deviceId not
        # present"). The UID comes back as data.p2pId / data. NOTE: observed to
        # return code=200 data=null when the camera is idle - the cloud appears
        # to only hand out the p2pId in some session/provisioning state we have
        # not yet reproduced; left as a best-effort source.
        headers = {k: v for k, v in self._leedarson_headers().items()
                   if k != "Content-Type"}
        try:
            async with aiohttp.ClientSession() as session:
                async with session.post(
                    f"{self._smarthome_base}/v5/deviceController/getP2pId"
                    f"?deviceId={self.device_id}",
                    headers=headers,
                    timeout=aiohttp.ClientTimeout(total=10),
                ) as resp:
                    body = await resp.json(content_type=None)

            data = body.get("data") if isinstance(body, dict) else None
            uid = None
            if isinstance(data, dict):
                uid = data.get("p2pId") or data.get("uid")
            elif isinstance(data, str):
                uid = data
            if uid:
                _LOGGER.debug("async_get_p2p_uid: got UID from getP2pId: %s", uid)
                return str(uid)
            _LOGGER.debug("async_get_p2p_uid: getP2pId returned no UID for %s. body=%s",
                          self.device_id, body)
        except Exception as exc:
            _LOGGER.debug("async_get_p2p_uid: smarthome call failed for %s: %s",
                          self.device_id, exc)

        # --- Source 3: AiDot v32 IPC device detail ---
        # Android app's NewLiveFragment.w5() parses a JSON string from the device
        # object to obtain the TUTK UID. The v32 IPC endpoint may return it directly.
        # Known paths from iOS HTTP traffic: /v32/api/ipc/devices/{id}
        for path in (
            f"/devices/{self.device_id}",
            f"/devices/{self.device_id}/info",
            f"/devices/{self.device_id}/detail",
        ):
            try:
                async with aiohttp.ClientSession() as session:
                    async with session.get(
                        f"{self._aidot_v32_base}{path}",
                        headers=self._aidot_headers(),
                        timeout=aiohttp.ClientTimeout(total=10),
                    ) as resp:
                        body = await resp.json(content_type=None)

                data = body.get("data") or body if isinstance(body, dict) else {}
                uid = (data.get("p2pId") or data.get("tutkUid")
                       or data.get("tutk_uid") or data.get("uid")
                       or data.get("iotcUid") or data.get("p2pUID"))
                if uid:
                    _LOGGER.debug("async_get_p2p_uid: got UID from v32%s: %s", path, uid)
                    return str(uid)
                _LOGGER.debug("async_get_p2p_uid: v32%s returned no UID for %s. body=%s",
                              path, self.device_id, body)
                # If we got a 200-level response (not 404/405), don't try other paths
                break
            except Exception as exc:
                _LOGGER.debug("async_get_p2p_uid: v32%s failed for %s: %s",
                              path, self.device_id, exc)

        _LOGGER.warning(
            "async_get_p2p_uid: all three sources returned empty UID for %s",
            self.device_id,
        )
        return None

    # ------------------------------------------------------------------ #
    # Phase 2: device controls via MQTT setDevAttrReq                     #
    # ------------------------------------------------------------------ #

    async def _mqtt_device_cmd(
        self,
        pub_topic: str,
        payload_str: str,
        *,
        timeout: float = 8.0,
        ack_keyword: str = "setDevAttr",
    ) -> bool:
        """Connect MQTT, optionally wake battery camera, publish command, wait for ack.

        All setDevAttrReq callers in the APK (b6.java, LiveCameraView, etc.)
        are in live-view screens where the camera is already awake and MQTT-
        connected.  For battery cameras we send lowPowerActiveStateReq first so
        the camera wakes and processes our command.
        """
        import json as _json

        smarthome_auth = await self._async_get_smarthome_auth()
        mqtt_user = (smarthome_auth or {}).get("mqttUser") or str(self.user_id)
        mqtt_pwd  = (smarthome_auth or {}).get("mqttPassword") or ""
        client_id = (self._user_info.get("mqttClientId") or f"app-{mqtt_user}")
        user_id   = self.user_id

        mqtt_url = await self._async_get_mqtt_url()
        if not mqtt_url:
            _LOGGER.error("_mqtt_device_cmd: no MQTT URL for %s", self.device_id)
            return False

        device_id = self.device_id
        sub_topics = [
            f"iot/v1/c/{user_id}/#",
            f"iot/v1/cb/{user_id}/#",
            f"iot/v1/cb/{device_id}/#",
            f"iot/v1/c/{device_id}/#",
        ]

        # Wake battery cameras before sending the control command.
        # Battery cameras (batteryMode=2) sleep between sessions and disconnect
        # from MQTT.  Without the wake call they miss our setDevAttrReq.
        # Payload from DeviceWakeUpRepos.java - no srcAddr/seq/tst.
        publish_items: list = []
        if self.is_battery_camera:
            _wake_payload = _json.dumps({
                "method":  "lowPowerActiveStateReq",
                "service": "IPC",
                "devId":   device_id,
                "userId":  str(user_id),
                "payload": {"devId": device_id, "status": "wakeup"},
            })
            _wake_topic = f"iot/v1/s/{user_id}/IPCAM/lowPowerActiveStateReq"
            publish_items.append((_wake_topic, _wake_payload))
        publish_items.append((pub_topic, payload_str))

        if self._resolve_persistent_mqtt():
            pm = await self._get_persistent_mqtt()
        else:
            pm = None
        if pm is not None:
            messages, _st = await pm.request(
                publish_items=publish_items,
                subscribe_topics=sub_topics,
                timeout=timeout,
            )
            if _st and _st.get("error"):
                # Persistent connection unavailable - fall back to a fresh per-op
                # connect so the command isn't silently dropped (and isn't falsely
                # reported as sent on the empty result the persistent path returns).
                _LOGGER.debug(
                    "_mqtt_device_cmd: persistent MQTT failed (%s); falling back "
                    "to a per-op session for %s", _st.get("error"), device_id,
                )
                messages, _st = await _mqtt_session_with_status(
                    mqtt_url, mqtt_user, mqtt_pwd, client_id,
                    subscribe_topics=sub_topics,
                    publish_items=publish_items,
                    duration=timeout,
                )
        else:
            messages, _st = await _mqtt_session_with_status(
                mqtt_url, mqtt_user, mqtt_pwd, client_id,
                subscribe_topics=sub_topics,
                publish_items=publish_items,
                duration=timeout,
            )

        for topic, raw in messages:
            if ack_keyword and ack_keyword not in topic:
                continue
            try:
                msg = _json.loads(raw)
            except Exception:
                continue
            code = (msg.get("ack") or {}).get("code") or msg.get("code")
            if code == 200:
                _LOGGER.debug("device cmd ack 200: topic=%s", topic)
                return True
            # Some firmware ACKs with code in payload.payload
            inner = msg.get("payload") or {}
            if isinstance(inner, dict) and inner.get("code") == 200:
                _LOGGER.debug("device cmd ack 200 (inner): topic=%s", topic)
                return True

        # Fire-and-forget fallback: the official app uses a delivery callback,
        # not a device response, so a published command with no 200-ack is
        # normal. But only treat it as success if the broker connection actually
        # succeeded - a refused/failed connection returns an empty message list
        # too, and reporting that as "sent" makes HA show a state change that
        # never reached the camera.
        if messages is not None and _mqtt_publish_delivered(_st):
            _LOGGER.debug(
                "device cmd: sent (no explicit 200-ack on %s, %d msgs total) "
                "- treating as sent-ok (official app is fire-and-forget)",
                device_id, len(messages),
            )
            return True

        return False

    async def async_set_device_attribute(
        self,
        attr: str,
        value,
        *,
        timeout: float = 8.0,
    ) -> bool:
        """Set one camera attribute via MQTT setDevAttrReq.

        Exact format from b6.java (NewLivePresenter.j0()):
          Topic:   iot/v1/c/{deviceId}/device/setDevAttrReq
          Payload: {"id": deviceId, "method": "setDevAttrReq",
                    "service": "device", "seq": "<generated>",
                    "payload": {"attr": {key: value}}}

        LDSBaseMqttServiceImpl auto-injects seq in the official app;
        we add it ourselves.  onlyPubAck=false means camera sends
        setDevAttrResp matched by seq.  _mqtt_device_cmd sends a
        lowPowerActiveStateReq wake signal before this command.
        Local-first: if a LAN control client is attached (see
        :meth:`attach_lan_client`) and reachable, the attribute is set over the
        LAN and the cloud round-trip is skipped; any LAN failure falls through to
        the MQTT path below.
        """
        import json as _json
        import random as _random

        lan = getattr(self, "_lan_client", None)
        if lan is not None:
            try:
                if await lan.async_set_attributes({attr: value}):
                    return True
            except Exception as _exc:
                _LOGGER.debug("LAN set %s failed (%s); falling back to cloud", attr, _exc)

        device_id = self.device_id
        user_id   = self.user_id
        # camera's local password (from device API dict 'password' field)
        cam_pwd   = getattr(getattr(self, "info", None), "device_password", "") or ""
        seq = f"ap{_random.randint(1000000, 9999999)}"
        # Web app JS (app-beautified.js:26551) includes userId + password in payload;
        # b6.java does not - camera accepts both, but web format is more complete.
        inner: dict = {"devId": device_id, "attr": {attr: value}}
        if user_id:
            inner["userId"] = str(user_id)
        if cam_pwd:
            inner["password"] = cam_pwd
        payload = _json.dumps({
            "id":      device_id,
            "method":  "setDevAttrReq",
            "service": "device",
            "seq":     seq,
            "payload": inner,
        })
        pub_topic = f"iot/v1/c/{device_id}/device/setDevAttrReq"
        _LOGGER.info("setDevAttrReq: %s=%s -> %s  seq=%s", attr, value, device_id, seq)
        ok = await self._mqtt_device_cmd(
            pub_topic, payload, timeout=timeout, ack_keyword="setDevAttr")
        if ok:
            # Reflect the change in local status right away (optimistic), so HA
            # control entities show the new value immediately instead of waiting
            # for the next 5-min cloud-properties poll.  Cameras don't push
            # setDevAttrNotif, so the poll is the only confirmation; it
            # re-validates this on its next run.  Uses the same parser as the
            # poll, so only known attributes update (others are no-ops).
            try:
                self.status.update_from_camera_attributes({attr: value})
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), 'async_set_device_attribute', exc_info=True)
        return ok

    async def async_trigger_device_action(
        self,
        action: str,
        params,
        *,
        timeout: float = 4.0,
    ) -> bool:
        """Send a devActionReq (e.g. siren/alarm trigger).

        Correct format from decompiled APK (b6.java h0()):
          Topic:   iot/v1/s/{userId}/device/devActionReq
          Payload: {"method": "devActionReq", "service": "device",
                    "payload": {"devId": deviceId, "action": action, "in": params}}
        """
        import json as _json

        user_id   = self.user_id
        device_id = self.device_id

        payload = _json.dumps({
            "method":  "devActionReq",
            "service": "device",
            "payload": {
                "devId":  device_id,
                "action": action,
                "in":     params,
            },
        })
        pub_topic = f"iot/v1/s/{user_id}/device/devActionReq"
        _LOGGER.info("devActionReq: %s %s -> %s", action, params, device_id)
        return await self._mqtt_device_cmd(
            pub_topic, payload, timeout=timeout, ack_keyword="devAction")

    # Convenience wrappers - confirmed attribute names/types from APK source











    async def async_get_camera_attributes(
        self,
        *,
        timeout: float = 8.0,
    ) -> Optional[dict]:
        """Read camera attributes by announcing user presence and waiting for setDevAttrNotif.

        Mirrors the official app flow (l.java + IpcServiceImpl.java):
          1. Subscribe iot/v1/c/{userId}/# and iot/v1/cb/{deviceId}/#
          2. Publish iot/v1/cb/{userId}/user/connect  (triggers camera to push state)
          3. For battery cameras: publish lowPowerActiveStateReq to wake from deep sleep
          4. Wait for camera-pushed setDevAttrNotif

        Returns the ``attr`` dict on success, else None.
        """
        import json as _json
        import time as _time

        # Wake battery cameras via the cloud HTTP low-power endpoint first, so a
        # deeply-sleeping camera (no live MQTT session) actually pushes its
        # setDevAttrNotif (battery / SD-card / occupancy) - otherwise those sensors
        # stay "unknown".  The MQTT lowPowerActiveStateReq below is the second
        # channel.  No-op for non-battery / already-awake cameras.
        if self.is_battery_camera:
            try:
                await self.async_wake_camera()
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), 'async_get_camera_attributes', exc_info=True)

        smarthome_auth = await self._async_get_smarthome_auth()
        mqtt_user = (smarthome_auth or {}).get("mqttUser") or str(self.user_id)
        mqtt_pwd  = (smarthome_auth or {}).get("mqttPassword") or ""
        _base_cid = (self._user_info.get("mqttClientId") or f"app-{mqtt_user}")
        client_id = f"{_base_cid}-cmd"
        user_id   = self.user_id
        device_id = self.device_id

        mqtt_url = await self._async_get_mqtt_url()
        if not mqtt_url:
            _LOGGER.error("async_get_camera_attributes: no MQTT URL for %s", device_id)
            return None

        sub_topics = [
            f"iot/v1/c/{user_id}/#",
            f"iot/v1/cb/{user_id}/#",
            f"iot/v1/cb/{device_id}/#",
            f"iot/v1/c/{device_id}/#",
        ]

        # Announce user presence - triggers online cameras to push setDevAttrNotif.
        # Exact format from l.java P():
        #   topic:   iot/v1/cb/{userId}/user/connect
        #   payload: {service:"user", method:"connect", srcAddr:"0.{userId}",
        #             payload:{timestamp:"yyyy-MM-dd HH:mm:ss.SSS"}}
        _ts = _time.strftime("%Y-%m-%d %H:%M:%S.") + f"{int(_time.time() * 1000) % 1000:03d}"
        _connect_topic   = f"iot/v1/cb/{user_id}/user/connect"
        _connect_payload = _json.dumps({
            "service": "user",
            "method":  "connect",
            "srcAddr": f"0.{user_id}",
            "payload": {"timestamp": _ts},
        })

        publish_items = [(_connect_topic, _connect_payload)]

        # For battery cameras (lowPowerActiveState): send wake signal so the camera
        # exits deep sleep and responds.  Payload from DeviceWakeUpRepos.java -
        # keys are method/devId/userId/service/payload; no srcAddr/seq/tst.
        if self.is_battery_camera:
            _wake_topic   = f"iot/v1/s/{user_id}/IPCAM/lowPowerActiveStateReq"
            _wake_payload = _json.dumps({
                "method":  "lowPowerActiveStateReq",
                "service": "IPC",
                "devId":   device_id,
                "userId":  str(user_id),
                "payload": {"devId": device_id, "status": "wakeup"},
            })
            publish_items.append((_wake_topic, _wake_payload))

        if self._resolve_persistent_mqtt():
            pm = await self._get_persistent_mqtt()
        else:
            pm = None
        if pm is not None:
            messages, _st = await pm.request(
                publish_items=publish_items,
                subscribe_topics=sub_topics,
                timeout=timeout,
            )
            if _st and _st.get("error"):
                # Persistent connection unavailable - fall back to a fresh per-op
                # connect so a transient outage doesn't masquerade as "no attrs".
                _LOGGER.debug(
                    "async_get_camera_attributes: persistent MQTT failed (%s); "
                    "falling back to a per-op session for %s",
                    _st.get("error"), device_id,
                )
                messages = await _mqtt_session(
                    mqtt_url, mqtt_user, mqtt_pwd, client_id,
                    subscribe_topics=sub_topics,
                    publish_items=publish_items,
                    duration=timeout,
                )
        else:
            messages = await _mqtt_session(
                mqtt_url, mqtt_user, mqtt_pwd, client_id,
                subscribe_topics=sub_topics,
                publish_items=publish_items,
                duration=timeout,
            )

        for topic, raw in messages:
            if "setDevAttrNotif" not in topic:
                continue
            try:
                msg = _json.loads(raw)
            except Exception:
                continue
            inner = msg.get("payload") or msg
            attr = inner.get("attr") if isinstance(inner, dict) else None
            if attr and isinstance(attr, dict):
                _LOGGER.debug(
                    "async_get_camera_attributes %s: %s", device_id, attr
                )
                return attr

        _LOGGER.debug(
            "async_get_camera_attributes: no attr response from %s (got %d msgs)",
            device_id, len(messages),
        )
        return None

    async def async_get_cloud_recordings(
        self,
        start_ts: int,
        end_ts: int,
        *,
        page: int = 1,
        page_size: int = 10,
    ) -> List[dict]:
        """List cloud-recorded event clips for this camera.

        start_ts / end_ts: Unix timestamps in milliseconds.
        page: 1-indexed page number (the server uses 1-based pagination).
        Returns list of dicts from the server (each has eventUuid, eventDesc, picUrl, etc.).

        Uses /api/ipc/playback/eventRecordingList (confirmed from EventListRepos.java).
        Body format from EventListRequestParamsBean: deviceIds (list), recordSta, recordEnd,
        eventCodes (list), areaIds (list), pageNum, pageSize.
        """
        import aiohttp

        _body = {
            "deviceIds": [self.device_id],
            "pageNum":   page,
            "pageSize":  page_size,
            "recordSta": start_ts,
            "recordEnd": end_ts,
        }
        _LOGGER.debug("eventRecordingList request for %s: %s", self.device_id, _body)
        async def _fetch():
            async with aiohttp.ClientSession() as session:
                async with session.post(
                    f"{self._aidot_v32_base}/playback/eventRecordingList",
                    json=_body,
                    headers=self._aidot_headers(),
                    timeout=aiohttp.ClientTimeout(total=30),
                ) as resp:
                    return await resp.json(content_type=None)

        try:
            body = await _fetch()
            if self._is_auth_error(body) and await self._async_refresh_auth_token():
                body = await _fetch()  # retry once with the refreshed token

            _LOGGER.debug("eventRecordingList raw response for %s: %s", self.device_id, body)
            if body.get("code") != 200:
                _LOGGER.warning(
                    "eventRecordingList code=%s msg=%s for %s",
                    body.get("code"), body.get("desc") or body.get("msg"), self.device_id,
                )
                return []

            data = body.get("data") or {}
            items = data.get("list") or []
            _LOGGER.info(
                "eventRecordingList for %s: total=%s returned=%d",
                self.device_id, data.get("total"), len(items),
            )
            return items

        except Exception as exc:
            _LOGGER.error(
                "async_get_cloud_recordings failed for %s: %s", self.device_id, exc
            )
            return []

    async def async_get_event_video_media(
        self, event_uuid: str
    ) -> Optional[tuple[str, str]]:
        """Return (url, mime_type) for a specific cloud recording.

        Prefers MP4 (type=1) over M3U8 (type=2) because HA's media player and
        most browsers handle direct MP4 more reliably than HLS with signed CDN
        segments.  Falls back to whatever is available.

        From CloudPlaybackFragment.java / EventListRepos.java:
          POST /api/ipc/playback/getEventVideoUrl
          body: {deviceId, eventList: [{eventUuid}]}
          response: data.eventUrlList[0].videoUrlList[]
          each item: {url, type}  (type 1=MP4, type 2=M3U8)
        """
        import aiohttp

        _MIME = {1: "video/mp4", 2: "application/x-mpegURL"}

        try:
            async with aiohttp.ClientSession() as session:
                async with session.post(
                    f"{self._aidot_v32_base}/playback/getEventVideoUrl",
                    json={
                        "deviceId":  self.device_id,
                        "eventList": [{"eventUuid": event_uuid}],
                    },
                    headers=self._aidot_headers(),
                    timeout=aiohttp.ClientTimeout(total=15),
                ) as resp:
                    body = await resp.json(content_type=None)

            _LOGGER.debug("getEventVideoUrl raw for %s uuid=%s: %s", self.device_id, event_uuid, body)

            if body.get("code") != 200:
                _LOGGER.warning(
                    "getEventVideoUrl code=%s for %s uuid=%s: %s",
                    body.get("code"), self.device_id, event_uuid, body,
                )
                return None

            event_url_list = ((body.get("data") or {}).get("eventUrlList") or [])
            if not event_url_list:
                return None
            video_url_list = (event_url_list[0] or {}).get("videoUrlList") or []
            if not video_url_list:
                return None

            # Prefer MP4 (type=1), fall back to first available entry
            mp4 = next((v for v in video_url_list if v.get("type") == 1), None)
            entry = mp4 or video_url_list[0]
            url = entry.get("url")
            if not url:
                return None
            mime = _MIME.get(entry.get("type"), "video/mp4")
            _LOGGER.debug(
                "getEventVideoUrl resolved for %s: type=%s mime=%s url=%.100s",
                self.device_id, entry.get("type"), mime, url,
            )
            return url, mime

        except Exception as exc:
            _LOGGER.error(
                "async_get_event_video_media failed for %s uuid=%s: %s",
                self.device_id, event_uuid, exc,
            )
            return None

    async def async_get_event_video_url(self, event_uuid: str) -> Optional[str]:
        """Return the video URL for a cloud recording (URL only, no MIME type).

        Prefer async_get_event_video_media() when you also need the MIME type.
        """
        result = await self.async_get_event_video_media(event_uuid)
        return result[0] if result else None

    async def async_get_latest_thumbnail(self) -> Optional[str]:
        """Return the URL of the most recent event/motion thumbnail for this camera.

        Uses /api/ipc/playback/eventRecordingList (pageNum=1, pageSize=1) and extracts
        the picUrl CDN field from the first result.  This endpoint works without an
        "owner" header (unlike the videoController paths which return 560073 with owner).

        Returns the CDN URL string, or None if no events exist.
        """
        import aiohttp
        import time

        end_ts = int(time.time() * 1000)
        start_ts = end_ts - 30 * 86_400_000  # look back 30 days

        try:
            async with aiohttp.ClientSession() as session:
                async with session.post(
                    f"{self._aidot_v32_base}/playback/eventRecordingList",
                    json={
                        "deviceIds": [self.device_id],
                        "pageNum":   1,
                        "pageSize":  1,
                        "recordSta": start_ts,
                        "recordEnd": end_ts,
                    },
                    headers=self._aidot_headers(),
                    timeout=aiohttp.ClientTimeout(total=15),
                ) as resp:
                    body = await resp.json(content_type=None)

            _LOGGER.debug("thumbnail eventRecordingList for %s: %s", self.device_id, body)
            if body.get("code") != 200:
                _LOGGER.debug(
                    "async_get_latest_thumbnail: code=%s for %s (no cloud plan or no events)",
                    body.get("code"), self.device_id,
                )
                return None

            items = ((body.get("data") or {}).get("list") or [])
            if not items:
                _LOGGER.debug("No event photos available for %s", self.device_id)
                return None

            url = items[0].get("picUrl")
            _LOGGER.debug("Latest thumbnail for %s: %.100s", self.device_id, url)
            return url or None

        except Exception as exc:
            _LOGGER.error(
                "async_get_latest_thumbnail failed for %s: %s", self.device_id, exc
            )
            return None

    async def async_snapshot(
        self,
        output_path: str,
        timeout: float = 30.0,
        status_callback: Optional[Callable[[str], None]] = None,
    ) -> bool:
        """Capture a JPEG still from the camera's live WebRTC stream.

        DTLS cameras (A000088): waits for the first keyframe via on_frame callback.
        SDES cameras (A001064/A001513): streams ~5s to a temp TS file, then extracts
        one JPEG with ffmpeg (on_frame is never called for SDES).

        Args:
            output_path: Destination file path (e.g. ``/tmp/snap.jpg``).
            timeout:     Seconds to wait for the first frame / stream start.
            status_callback: Optional callback for connection status messages.

        Returns:
            True if the snapshot was saved successfully, False otherwise.
        """
        import asyncio as _asyncio

        # -- SDES path: stream briefly to a temp TS file, extract one JPEG ---- #
        if self.is_sdes_camera:
            import os as _os
            import tempfile as _tf

            with _tf.NamedTemporaryFile(suffix=".ts", delete=False) as _tmpf:
                _tmp_ts = _tmpf.name
            try:
                # SDES cameras require a full SCTP/DCEP handshake before streaming.
                # The bridge thread (STUN+SCTP+DCEP) takes 25-35s; LIVING is sent
                # only after DCEP_OPEN + 300ms.  PLI forces an IDR every 5s once
                # streaming starts, so 10s of recording is always enough for a still.
                # Battery cameras (A001513) can take up to 17s to wake and send their
                # SDP answer - use a 45s cap so answer_fut does not expire first.
                _sdes_snap_seconds = 10
                _session = await self.async_open_webrtc_stream(
                    on_frame=lambda _f: None,
                    output_path=_tmp_ts,
                    max_seconds=_sdes_snap_seconds,
                    timeout=timeout,
                    status_callback=status_callback,
                    _sdes_answer_timeout=45.0,
                )
                try:
                    await _asyncio.wait_for(
                        _session.wait_done(),
                        timeout=_sdes_snap_seconds + 80,
                    )
                except (TimeoutError, _asyncio.CancelledError):
                    pass
                finally:
                    await _session.stop()

                if _os.path.getsize(_tmp_ts) == 0:
                    _LOGGER.warning(
                        "async_snapshot SDES: stream produced no data for %s",
                        self.device_id,
                    )
                    return False

                if _ffmpeg_path() is None:
                    _LOGGER.error("async_snapshot SDES: %s", _FFMPEG_MISSING_MSG)
                    return False
                # Async subprocess (not subprocess.run): a synchronous ffmpeg
                # here blocks the whole event loop - every camera, keepalive and
                # MQTT drain - for up to the timeout.
                _snap_proc = await _asyncio.create_subprocess_exec(
                    "ffmpeg", "-y", "-i", _tmp_ts,
                    "-frames:v", "1", "-f", "image2", output_path,
                    stdout=_asyncio.subprocess.DEVNULL,
                    stderr=_asyncio.subprocess.PIPE,
                )
                try:
                    _, _snap_err = await _asyncio.wait_for(
                        _snap_proc.communicate(), timeout=15
                    )
                except TimeoutError:
                    _snap_proc.kill()
                    await _snap_proc.communicate()
                    _LOGGER.warning(
                        "async_snapshot SDES: ffmpeg frame extract timed out for %s",
                        self.device_id,
                    )
                    return False
                if _snap_proc.returncode == 0 and _os.path.exists(output_path):
                    return True
                _LOGGER.warning(
                    "async_snapshot SDES: ffmpeg frame extract failed for %s: %s",
                    self.device_id,
                    (_snap_err or b"").decode(errors="replace")[-200:],
                )
                return False
            except Exception as _snap_exc:
                _LOGGER.error(
                    "async_snapshot SDES failed for %s: %s", self.device_id, _snap_exc)
                return False
            finally:
                try:
                    _os.unlink(_tmp_ts)
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), 'async_snapshot', exc_info=True)

        # -- DTLS path: on_frame callback delivers frames from aiortc ------- #
        frame_event = _asyncio.Event()
        captured: list = [None]  # stores PIL Image or ndarray once decoded

        def _on_frame(frame) -> None:
            if frame_event.is_set():
                return
            # Skip non-keyframes to avoid reference-frame artifacts on first delivery.
            if not getattr(frame, "key_frame", True):
                return
            # Decode the frame NOW while PyAV's decoder buffer is still valid.
            try:
                captured[0] = frame.to_image()          # PIL Image
            except Exception:
                try:
                    captured[0] = frame.to_ndarray(format="rgb24")  # numpy
                except Exception:
                    return
            frame_event.set()

        try:
            session = await self.async_open_webrtc_stream(
                on_frame=_on_frame,
                timeout=timeout,
                status_callback=status_callback,
            )
        except Exception as _snap_exc:
            _LOGGER.error(
                "async_snapshot DTLS failed for %s: %s", self.device_id, _snap_exc)
            return False
        try:
            try:
                await _asyncio.wait_for(frame_event.wait(), timeout=timeout)
            except TimeoutError:
                _LOGGER.warning(
                    "async_snapshot: no keyframe received within %.0fs for %s",
                    timeout, self.device_id,
                )
                return False
        finally:
            await session.stop()

        if captured[0] is None:
            return False
        # _save_frame_as_jpeg does a blocking PIL encode (or a blocking ffmpeg
        # fallback); run it off the event loop so it can't stall other cameras.
        return await _asyncio.get_running_loop().run_in_executor(
            None, _save_frame_as_jpeg, captured[0], output_path
        )

    async def async_start_streaming(self) -> None:
        """Start a persistent background WebRTC stream that updates latest_jpeg.

        DTLS cameras (A000088): frames delivered via on_frame callback, latest_jpeg updated.
        SDES cameras (A001064/A001513): on_frame not available (ffmpeg writes TS directly),
        so latest_jpeg stays None. HA camera entity falls back to cloud thumbnail in that case.
        Safe to call multiple times - does nothing if already running.
        """
        if self.is_sdes_camera:
            _LOGGER.debug(
                "SDES camera %s: latest_jpeg not updated by streaming"
                " (HA will use cloud thumbnail fallback)",
                self.device_id,
            )
            return
        if self._stream_task is not None and not self._stream_task.done():
            return
        self._streaming_active = True
        self._stream_task = asyncio.ensure_future(self._streaming_loop())

    async def async_stop_streaming(self) -> None:
        """Stop the persistent background WebRTC stream."""
        self._streaming_active = False
        self._serve_ready.clear()
        g_task, self._go2rtc_task = self._go2rtc_task, None
        if g_task is not None and not g_task.done():
            g_task.cancel()
            try:
                await g_task
            except (asyncio.CancelledError, Exception):
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), 'async_stop_streaming', exc_info=True)
        await self._deregister_go2rtc()
        session, self._stream_session = self._stream_session, None
        if session is not None:
            try:
                await session.stop()
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), 'async_stop_streaming', exc_info=True)
        task, self._stream_task = self._stream_task, None
        if task is not None and not task.done():
            task.cancel()
            try:
                await task
            except (asyncio.CancelledError, Exception):
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), 'async_stop_streaming', exc_info=True)
        # Reap a persistent-MQTT stream drain that no session stopped (e.g. an
        # open cancelled mid-handshake) so its handler is removed from the shared
        # connection and its blocked executor thread is released.
        await self._reap_stream_drain()

    async def _reap_stream_drain(self):
        """Stop and reap the persistent-MQTT stream drain task, if any.

        The drain blocks an executor thread on ``outgoing_q.get`` until a ``None``
        sentinel arrives. A normal stop pushes that sentinel from the
        WebRTCSession/SdesSession; but if no session took ownership (open
        cancelled mid-handshake) or a new open replaced this one, we must push it
        ourselves - cancelling the future alone cannot interrupt the blocked
        thread, leaking it (and its handler on the shared connection) forever."""
        drain = getattr(self, "_stream_mqtt_drain", None)
        outq = getattr(self, "_stream_mqtt_outq", None)
        self._stream_mqtt_drain = None
        self._stream_mqtt_outq = None
        if drain is None:
            return
        if outq is not None:
            try:
                outq.put_nowait(None)   # release the executor thread in outgoing_q.get
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_reap_stream_drain', exc_info=True)
        if not drain.done():
            drain.cancel()
        try:
            await drain
        except (asyncio.CancelledError, Exception):
            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_reap_stream_drain', exc_info=True)

    async def async_start_motion_polling(
        self, callback: Callable, interval: float = 30.0, lookback_s: int = 600,
    ) -> None:
        """Start polling the cloud event list and invoke ``callback(event)`` for each NEW
        motion/event clip the camera records.

        This is the working motion path for HA. The official app does NOT poll the event
        list on a timer (it's pull-to-refresh) - it gets background motion via FCM push and
        live ``alarmType`` only during an active view (both unavailable to a server-side
        integration). So polling is HA-necessary, and ``interval`` is OUR latency/load
        tradeoff, NOT an app value (there is none to match). 30 s is a sensible default
        (typical for HA cloud cameras). NOTE: depends on the camera recording events to the
        cloud - if there's no cloud event history, the list stays empty.
        ``event`` is the raw server item (``eventUuid``, ``eventCode``, ``eventTime``,
        ``picUrl``, ``eventDesc``, ...). The callback may be sync or a coroutine.

        ``interval`` = seconds between polls; ``lookback_s`` = how far back each poll
        queries. The first poll PRIMES (marks the existing backlog as seen without firing)
        so the callback only fires on events recorded after start. Idempotent.
        """
        self._motion_cb = callback
        self._motion_interval = max(5.0, float(interval))
        if self._motion_task is not None and not self._motion_task.done():
            return
        self._motion_active = True
        self._motion_seen = {}
        self._motion_task = asyncio.ensure_future(self._motion_poll_loop(lookback_s))

    async def async_stop_motion_polling(self) -> None:
        """Stop the motion-event polling started by ``async_start_motion_polling``."""
        self._motion_active = False
        self._motion_cb = None
        task, self._motion_task = self._motion_task, None
        if task is not None:
            task.cancel()
            try:
                await task
            except (asyncio.CancelledError, Exception):
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), 'async_stop_motion_polling', exc_info=True)

    async def _motion_poll_loop(self, lookback_s: int) -> None:
        """Background: poll the cloud event list; fire callback on newly-recorded events."""
        import time as _time
        primed = False
        while self._motion_active:
            try:
                now = int(_time.time() * 1000)
                items = await self.async_get_cloud_recordings(
                    now - lookback_s * 1000, now, page=1, page_size=20
                )
                fresh = []
                for it in items:
                    uid = it.get("eventUuid") or it.get("eventId") or it.get("recordId")
                    if not uid or uid in self._motion_seen:
                        continue
                    self._motion_seen[uid] = None
                    fresh.append(it)
                if primed and fresh and self._motion_cb is not None:
                    for it in sorted(fresh, key=lambda x: x.get("eventTime") or 0):
                        try:
                            res = self._motion_cb(it)
                            if asyncio.iscoroutine(res):
                                _spawn_bg(res)
                        except Exception:
                            _LOGGER.debug("motion callback raised", exc_info=True)
                primed = True
                if len(self._motion_seen) > 1000:  # bound memory
                    # Keep the 400 most-recent uids (insertion order), evicting
                    # the oldest so still-in-window events stay deduplicated.
                    self._motion_seen = dict.fromkeys(list(self._motion_seen)[-400:])
            except asyncio.CancelledError:
                return
            except Exception as exc:
                _LOGGER.debug("motion poll error for %s: %s", self.device_id, exc)
            try:
                await asyncio.sleep(self._motion_interval)
            except asyncio.CancelledError:
                return

    async def start_keepalive(
        self,
        rtsp_push_url: Optional[str] = None,
        fast_connect: Optional[bool] = None,
        sdes_audio: Optional[bool] = None,
        go2rtc_url: Optional[str] = None,
        live_stream_param: Optional[bool] = None,
        serve_relay: Optional[bool] = None,
        stream_idle_s: Optional[float] = None,
        sdes_fast_liveplay: Optional[bool] = None,
        sdes_skip_turn: Optional[bool] = None,
        sdes_adaptive: Optional[bool] = None,
        sdes_audio_gain_db: Optional[float] = None,
    ) -> None:
        """Start a persistent stream that keeps the camera session alive.

        For SDES cameras (A001064/A001513): opens an indefinite ffmpeg stream and
        optionally pushes it to an RTSP server (e.g. go2rtc at rtsp://127.0.0.1:8554).
        This avoids the 25-70s cold-start SCTP handshake on every viewer connection.

        For DTLS cameras (A000088) with an http(s) serve URL: taps aiortc's
        encoded H.264 and -c copy's it to an mpegts HTTP-listen socket go2rtc
        pulls (real play/pause video, no decode/re-encode). Without a serve URL
        it falls back to the on_frame keepalive loop (latest_jpeg / MJPEG).

        ``fast_connect`` enables LAN-direct mode (skip the ~2.5s livePlay/ICE-config
        waits + TURN relay; see _async_open_webrtc_stream_impl).  ``None`` leaves it
        to the ``AIDOT_FAST_CONNECT`` env var; ``True``/``False`` override it (e.g.
        from a Home Assistant config-entry option, since HA OS can't set env vars).

        ``live_stream_param`` toggles the battery-camera cloud pre-connect
        (liveStreamParam); like ``fast_connect`` it overrides the
        ``AIDOT_LIVESTREAM_PARAM`` env var, so an integration on HA OS (no env vars)
        can still disable it per camera.

        ``serve_relay`` toggles the cold-start serve-port relay (holds the public
        serve port connectable through the WebRTC handshake so an eager go2rtc
        pull waits instead of getting connection-refused).  Overrides the
        ``AIDOT_SERVE_RELAY`` env var (default on); set False to serve ffmpeg
        directly on the public port (the pre-0.7.18 behaviour).

        ``stream_idle_s`` sets the no-viewer idle-release window in seconds,
        overriding the ``AIDOT_STREAM_IDLE_S`` env default (120).  ``0`` (or
        negative) keeps the warm WebRTC session forever so re-views are instant
        (app-like) - sensible for mains cameras (no battery cost), but it holds a
        concurrent-stream slot for the camera's lifetime, so don't pin more
        cameras than ``AIDOT_MAX_CONCURRENT_STREAMS`` allows.  Leave None for
        battery cameras (motion-prewarm + idle-release preserves battery).

        Safe to call multiple times - does nothing if already running.
        """
        if fast_connect is not None:
            self._fast_connect_opt = fast_connect
        if sdes_audio is not None:
            self._sdes_audio_opt = sdes_audio
        if sdes_audio_gain_db is not None:
            self._sdes_audio_gain_opt = sdes_audio_gain_db
        if live_stream_param is not None:
            self._live_stream_param_opt = live_stream_param
        if serve_relay is not None:
            self._serve_relay_opt = serve_relay
        if stream_idle_s is not None:
            self._stream_idle_opt = stream_idle_s
        if sdes_fast_liveplay is not None:
            self._sdes_fast_liveplay_opt = sdes_fast_liveplay
        if sdes_skip_turn is not None:
            self._sdes_skip_turn_opt = sdes_skip_turn
        if sdes_adaptive is not None:
            self._sdes_adaptive_opt = sdes_adaptive
        if self._stream_task is not None and not self._stream_task.done():
            return
        self._keepalive_rtsp_url = rtsp_push_url
        self._go2rtc_url = go2rtc_url
        self._streaming_active = True
        if self.is_sdes_camera:
            self._stream_task = asyncio.ensure_future(self._sdes_keepalive_loop())
        elif rtsp_push_url and rtsp_push_url.startswith("http"):
            self._stream_task = asyncio.ensure_future(self._dtls_serve_loop())
        else:
            self._stream_task = asyncio.ensure_future(self._streaming_loop())
        # Prefer-go2rtc: if a go2rtc server is configured, register the local
        # serve with it once ready and expose its pull URL (ffmpeg fallback
        # otherwise). Opt-in: with no go2rtc_url, behaviour is unchanged.
        if go2rtc_url:
            self._go2rtc_task = asyncio.ensure_future(self._register_with_go2rtc())

    @property
    def stream_rtsp_url(self) -> Optional[str]:
        """RTSP pull URL for the live keepalive stream, or None if not running.

        When keepalive is active with an RTSP push URL of the form
        ``rtsp://HOST:PORT/NAME``, go2rtc makes the stream available at the same
        address for HA's stream integration to pull.
        """
        if self._streaming_active:
            # Prefer the go2rtc pull URL (low-latency WebRTC / native codec)
            # when registered; else the direct local serve URL (ffmpeg fallback,
            # e.g. HA built-in HLS).
            if self._go2rtc_pull_url:
                return self._go2rtc_pull_url
            if self._keepalive_rtsp_url:
                return self._keepalive_rtsp_url
        return None

    async def _register_with_go2rtc(self) -> None:
        """Prefer go2rtc: once the local serve is ready, register it with the
        configured go2rtc server and expose its low-latency pull URL via
        ``stream_rtsp_url``.  Best-effort - on any failure the direct serve URL
        remains the fallback (ffmpeg / HA HLS).
        """
        import aiohttp
        from .go2rtc import prefer_go2rtc
        try:
            await self.async_wait_serve_ready(timeout=40.0)
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_register_with_go2rtc', exc_info=True)
        if not (self._streaming_active and self._go2rtc_url and self._keepalive_rtsp_url):
            return
        name = f"aidot_{self.device_id[:12]}"
        try:
            async with aiohttp.ClientSession() as _s2:
                url = await prefer_go2rtc(
                    _s2, name, self._keepalive_rtsp_url, base_url=self._go2rtc_url)
            if url:
                self._go2rtc_pull_url = url
                _LOGGER.info(
                    "camera %s: preferring go2rtc stream -> %s", self.device_id, url)
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_register_with_go2rtc', exc_info=True)

    async def _deregister_go2rtc(self) -> None:
        """Remove this camera's stream from go2rtc (best-effort)."""
        base, pull = self._go2rtc_url, self._go2rtc_pull_url
        self._go2rtc_pull_url = None
        if not (base and pull):
            return
        import aiohttp
        from .go2rtc import Go2rtcClient
        name = f"aidot_{self.device_id[:12]}"
        try:
            async with aiohttp.ClientSession() as _s2:
                await Go2rtcClient(_s2, base).remove_stream(name)
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_deregister_go2rtc', exc_info=True)

    async def async_wait_serve_ready(self, timeout: float = 20.0) -> bool:
        """Wait until the DTLS serve is bound + serving (or ``timeout``).

        Lets camera.stream_source() hand HA a URL only once the serve actually
        exists, so HA connects on its first try instead of getting
        "Connection refused" and waiting out its ~40s reconnect interval.
        Returns immediately if a warm session is already serving.  No-op (False)
        for paths that don't signal readiness (e.g. SDES - its ffmpeg serve has
        its own cold-start and never sets this event, so don't block on it).
        """
        if self.is_sdes_camera:
            return False
        ev = self._serve_ready
        if ev is None:
            return False
        try:
            await asyncio.wait_for(ev.wait(), timeout=timeout)
            return True
        except TimeoutError:
            return False

    async def async_speak(
        self,
        pcm_provider: "Callable[[], Optional[bytes]]",
        *,
        max_seconds: float = 30.0,
        open_timeout: float = 30.0,
        retries: int = 3,
    ) -> bool:
        """Play viewer->camera (push-to-talk / announce) audio through the speaker.

        ``pcm_provider()`` is polled every 20 ms and must return 320 bytes of s16le
        PCM @ 8 kHz mono (a short/empty frame is padded to silence) and ``None``
        once the clip is finished. Reuses the warm keepalive session if one is open
        and talk-capable, otherwise opens a temporary session (with retries) and
        closes it afterwards. Returns True if talk ran end-to-end, False if no
        session could carry talk. Works for both DTLS and SDES cameras (same
        WebRTC audio path).
        """
        session = self._stream_session
        own_session = False
        if session is None or not getattr(session, "talk_supported", False):
            session = None
            for attempt in range(max(1, retries)):
                try:
                    # talk=True so the SDES offer advertises sendrecv + a=ssrc and
                    # the camera builds a receive path for our audio (no-op for the
                    # DTLS path, which already negotiates a sendrecv audio sender).
                    session = await self.async_open_webrtc_stream(
                        timeout=open_timeout, talk=True)
                    own_session = True
                    break
                except Exception as exc:
                    _LOGGER.debug(
                        "async_speak: connect attempt %d/%d failed: %s",
                        attempt + 1, retries, exc,
                    )
                    session = None
                    await asyncio.sleep(3)
            if session is None or not getattr(session, "talk_supported", False):
                if own_session and session is not None:
                    await session.stop()
                return False

        # Signal completion when the provider is exhausted (returns None).
        done = asyncio.Event()
        loop = asyncio.get_running_loop()

        def _provider():
            frame = pcm_provider()
            if frame is None:
                loop.call_soon_threadsafe(done.set)
                return None
            return frame

        if not await session.async_start_talk(_provider):
            if own_session:
                await session.stop()
            return False
        try:
            await asyncio.wait_for(done.wait(), timeout=max_seconds)
        except TimeoutError:
            pass
        try:
            await session.async_stop_talk()
        finally:
            if own_session:
                await session.stop()
        return True

    def _sdes_serve_consumer_present(self, port: int) -> "Optional[bool]":
        """True if a TCP client is connected to the SDES serve ``port`` (a viewer
        is pulling), False if none, None if the TCP table can't be read (non-Linux
        / sandboxed host).  The caller MUST treat None as 'unknown' and NOT release
        - failing safe to the current always-reconnect behaviour."""
        read_any = False
        found = False
        for _path in ("/proc/net/tcp", "/proc/net/tcp6"):
            try:
                with open(_path) as _fh:
                    _txt = _fh.read()
            except OSError:
                continue
            read_any = True
            if _tcp_table_has_established_on_port(_txt, port):
                found = True
        return found if read_any else None

    async def _sdes_keepalive_loop(self) -> None:
        """Keep the cold-start serve relay alive around the SDES keepalive loop.

        The relay holds the public serve port connectable through every (re)open
        so an eager go2rtc pull waits instead of getting connection-refused while
        the SDES handshake runs (the same race fixed for the DTLS serve)."""
        self._serve_relay = self._maybe_start_serve_relay(self._keepalive_rtsp_url)
        try:
            await self._sdes_keepalive_loop_inner()
        finally:
            _relay = self._serve_relay
            self._serve_relay = None
            if _relay is not None:
                _relay.close()

    async def _sdes_keepalive_loop_inner(self) -> None:
        """Background task: keep SDES stream alive; push to go2rtc via RTSP."""
        _MIN_DELAY = 10.0
        _MAX_DELAY = 300.0
        # Jittered-backoff pacer: escalates on failed/no-media opens so a degraded
        # camera (or a fleet reconnecting at once) isn't hammered into further
        # degradation / cloud rate-limiting; resets after a session that delivered
        # media (see end of loop).
        _pacer = ReconnectPacer(_MIN_DELAY, _MAX_DELAY)

        # Adaptive fast-with-fallback (default on): the first open tries the fast
        # path (skip livePlay waits + TURN relay pre-allocation) with a SHORT
        # timeout/grace so a non-LAN camera fails quickly; on no media we latch
        # _fast_failed and the remaining opens this loop use the full, patient
        # relay path.  Makes fast-by-default safe regardless of camera reachability.
        _adaptive = self._resolve_sdes_adaptive()
        _FAST_OPEN_TIMEOUT = 45.0
        _FAST_GRACE = 40.0
        # Per-device cache: once a fast attempt has failed for this camera (e.g. a
        # strict-NAT / non-LAN camera that genuinely needs the relay), remember it
        # so later views skip the fast attempt entirely instead of re-paying the
        # ~40s fast timeout on every fresh keepalive loop. Latches for the client's
        # lifetime; an integration reload / restart re-probes the fast path.
        _fast_failed = bool(getattr(self, "_fast_path_unavailable", False))

        while self._streaming_active:
            if self._serve_relay is not None:
                # Clear any stale backend from a prior session; the open below
                # points the relay at this session's fresh internal ffmpeg port.
                self._serve_relay.set_backend(None)
            _use_fast = self._adaptive_next_fast(_adaptive, _fast_failed)
            if _adaptive:
                self._fast_attempt_override = _use_fast
            try:
                session = await self.async_open_webrtc_stream(
                    rtsp_push_url=self._keepalive_rtsp_url,
                    timeout=(_FAST_OPEN_TIMEOUT if _use_fast else 120.0),
                )
            except asyncio.CancelledError:
                self._fast_attempt_override = None
                return
            except Exception as exc:
                self._fast_attempt_override = None
                if _use_fast:
                    _fast_failed = self._adaptive_after_attempt(True, False, _fast_failed)
                    self._fast_path_unavailable = True  # cache across views
                    _LOGGER.info(
                        "SDES adaptive[%s]: fast open failed (%.0fs) - "
                        "falling back to full relay path", self.device_id,
                        _FAST_OPEN_TIMEOUT)
                _delay = _pacer.fail_delay()
                _LOGGER.warning(
                    "SDES keepalive: stream open failed for %s (retry in %.0fs): %s",
                    self.device_id, _delay, exc,
                )
                try:
                    await self._backoff_or_offline_pause(_delay)
                except asyncio.CancelledError:
                    return
                continue

            self._fast_attempt_override = None
            self._stream_session = session
            # Don't block solely on wait_done(): the SDES ffmpeg reads RTP over a
            # UDP socket with no input timeout, so when a battery camera tears the
            # session down (~49-72s) ffmpeg keeps waiting on dead sockets forever
            # and never exits.  Race wait_done() against a media-liveness watchdog
            # (mirrors the JPEG _streaming_loop) so a stalled session reconnects.
            _started_at = time.monotonic()
            _done = asyncio.ensure_future(session.wait_done())
            _stalled = False
            _idle_release = False
            # No-viewer release.  Unlike the DTLS serve (which idle-releases when
            # its mux pipe goes stale), an SDES keepalive otherwise reconnects
            # FOREVER even with zero HA consumers - a battery-draining orphan that
            # also holds a stream slot and TURN-relay bandwidth.  SDES has no mux
            # pipe to watch, so detect "nobody is pulling" via an ESTABLISHED TCP
            # connection on the -listen serve port and release after the same idle
            # window as DTLS; the next view re-runs camera.stream_source().  Fail
            # safe: unknown (non-Linux /proc) never releases.  Escape hatch:
            # AIDOT_SDES_IDLE_RELEASE=0.
            # stream_idle_s / AIDOT_STREAM_IDLE_S override; <= 0 = never release.
            _idle_secs = self._resolve_idle_secs()
            _idle_on = (os.environ.get("AIDOT_SDES_IDLE_RELEASE", "1") != "0"
                        and _idle_secs > 0)
            _serve_port = _sdes_serve_port(self._keepalive_rtsp_url)
            _last_consumer = _started_at  # grace: count idle from session open
            try:
                while True:
                    _fin, _ = await asyncio.wait({_done}, timeout=5.0)
                    if _done in _fin:
                        break  # ffmpeg exited on its own (consumer gone, error)
                    if session.is_stalled(
                        session.last_media_monotonic,
                        _started_at,
                        time.monotonic(),
                        grace=(_FAST_GRACE if _use_fast else 60.0),
                    ):
                        _stalled = True
                        break
                    if _idle_on and _serve_port is not None:
                        _present = self._sdes_serve_consumer_present(_serve_port)
                        if _present:  # True -> a viewer is pulling; stay alive
                            _last_consumer = time.monotonic()
                        elif _idle_release_due(_present, _last_consumer,
                                               time.monotonic(), _idle_secs):
                            _idle_release = True
                            break
                        # _present is None (unreadable table) -> don't release
            except asyncio.CancelledError:
                _done.cancel()
                self._stream_session = None
                await session.stop()
                return
            finally:
                self._stream_session = None

            if _idle_release:
                # No HA consumer for the idle window: go dormant instead of
                # reconnecting forever.  camera.stream_source() restarts us (and
                # the camera.py stale-stream watchdog evicts HA's cached stream
                # once stream_rtsp_url is None) when someone opens the view again.
                _done.cancel()
                try:
                    await session.stop()
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_sdes_keepalive_loop', exc_info=True)
                self._streaming_active = False
                self._keepalive_rtsp_url = None
                self._serve_ready.clear()
                _LOGGER.debug(
                    "SDES serve: %s idle (no viewer) - released until next view",
                    self.device_id,
                )
                return

            if _stalled:
                _LOGGER.info(
                    "SDES %s: no media in watchdog window - restarting stream",
                    self.device_id,
                )
                _done.cancel()

            try:
                await session.stop()
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_sdes_keepalive_loop', exc_info=True)

            # Adaptive bookkeeping: a fast attempt that never delivered media
            # latches the loop onto the full relay path for its remaining opens.
            _healthy = session.last_media_monotonic > 0.0
            if _use_fast and not _healthy and not _fast_failed:
                _LOGGER.info(
                    "SDES adaptive[%s]: fast attempt delivered no media - "
                    "falling back to full relay path", self.device_id)
            _fast_failed = self._adaptive_after_attempt(_use_fast, _healthy, _fast_failed)
            if _use_fast and not _healthy:
                self._fast_path_unavailable = True  # cache across views

            if self._streaming_active:
                # Escalate backoff only when the session never delivered media
                # (camera refused / degraded on a rapid reconnect); a session
                # that streamed fine and then ended (battery teardown, consumer
                # gone) is a normal lifecycle event and resets to the base interval.
                try:
                    await asyncio.sleep(_pacer.session_end_delay(healthy=_healthy))
                except asyncio.CancelledError:
                    return

    async def _streaming_loop(self) -> None:
        """Background task: open WebRTC stream and update latest_jpeg; reconnect on drop."""
        import io as _io

        _WATCHDOG = 30.0
        # Reconnect backoff starts at 5 s (exponential ->300 s).  This is an
        # INTENTIONAL divergence from the app, which uses a fixed 15 s reconnect
        # + a 15 s gate (f0.java I1=15000).  We favour faster HA recovery; an A/B
        # of 15 s showed no measurable benefit and only slowed reconnects.  Raise
        # to 15 s here (+ the SDES/serve loops) if strict app parity is wanted.
        _MIN_DELAY = 5.0
        _MAX_DELAY = 300.0
        # Jittered-backoff pacer: escalates on failed/frameless opens, resets after
        # a session that produced frames.
        _pacer = ReconnectPacer(_MIN_DELAY, _MAX_DELAY)

        def _on_frame(frame) -> None:
            # Accept keyframes always; accept P-frames only after first keyframe.
            if not getattr(frame, "key_frame", True) and self.latest_jpeg is not None:
                return
            try:
                pil_img = frame.to_image()
            except Exception:
                try:
                    from PIL import Image as _PILImage
                    pil_img = _PILImage.fromarray(frame.to_ndarray(format="rgb24"))
                except Exception:
                    return
            try:
                buf = _io.BytesIO()
                pil_img.save(buf, "JPEG")
                self.latest_jpeg = buf.getvalue()
                self._last_frame_time = asyncio.get_running_loop().time()
            except Exception as enc_exc:
                _LOGGER.debug("Streaming encode failed for %s: %s", self.device_id, enc_exc)

        while self._streaming_active:
            _open_time = asyncio.get_running_loop().time()
            try:
                session = await self.async_open_webrtc_stream(on_frame=_on_frame)
            except asyncio.CancelledError:
                return
            except AidotCameraBusy as busy:
                # GAP D: the camera refused (e.g. another viewer holds the single
                # stream slot). Retrying fast cannot help; wait out a long backoff
                # before trying again rather than hammering it.
                _LOGGER.warning(
                    "Stream refused for %s (%s) - backing off %.0fs before retry",
                    self.device_id, busy, _MAX_DELAY,
                )
                try:
                    await asyncio.sleep(_MAX_DELAY)
                except asyncio.CancelledError:
                    return
                continue
            except Exception as exc:
                _delay = _pacer.fail_delay()
                _LOGGER.warning(
                    "Stream open failed for %s (retry in %.0fs): %s",
                    self.device_id, _delay, exc,
                )
                try:
                    await self._backoff_or_offline_pause(_delay)
                except asyncio.CancelledError:
                    return
                continue

            self._stream_session = session
            try:
                while self._streaming_active:
                    await asyncio.sleep(5.0)
                    elapsed = asyncio.get_running_loop().time() - self._last_frame_time
                    if self._last_frame_time > 0 and elapsed > _WATCHDOG:
                        _LOGGER.warning(
                            "No frames from %s in %.0fs - restarting stream",
                            self.device_id, elapsed,
                        )
                        break
            except asyncio.CancelledError:
                self._stream_session = None
                await session.stop()
                return
            finally:
                self._stream_session = None

            try:
                await session.stop()
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_on_frame', exc_info=True)

            if self._streaming_active:
                # Reset backoff if this session produced frames (a normal drop
                # after good streaming); escalate with jitter if it never did
                # (the camera-degradation case).
                _produced = self._last_frame_time > _open_time
                try:
                    await asyncio.sleep(_pacer.session_end_delay(healthy=_produced))
                except asyncio.CancelledError:
                    return

    @staticmethod
    async def _send_video_pli(pc) -> None:
        """Ask the camera for an IDR keyframe (RTCP PLI) on the video receiver."""
        try:
            for _r in pc.getReceivers():
                _tr = getattr(_r, "track", None)
                if _tr is not None and _tr.kind == "video":
                    _ssrcs = [s.source for s in _r.getSynchronizationSources()]
                    for _ssrc in _ssrcs:
                        await _r._send_rtcp_pli(_ssrc)
                    return
        except Exception as _pli_exc:
            _LOGGER.debug("PLI request failed: %s", _pli_exc)

    @staticmethod
    def _install_encoded_tap(receiver, out_q, is_video: bool) -> bool:
        """Tee aiortc's depacketized encoded frames (+ RTP timestamp) into a
        thread-safe queue before decode.  Video frames go as
        ``(bytes, timestamp, is_keyframe)``, audio as ``(bytes, timestamp)``.
        ``out_q`` is a ``queue.Queue`` (the mux runs in a worker thread)."""
        _qd = getattr(receiver, "_RTCRtpReceiver__decoder_queue", None)
        if _qd is None:
            return False
        # Idempotent: _install_av_taps is retried until the video tap lands, so a
        # receiver that's already tapped must not be wrapped again (that would
        # layer the tap N times and flood the queue).
        if getattr(_qd, "_aidot_tapped", False):
            return True
        _orig_put = _qd.put

        def _tap_put(task, *a, **k):
            try:
                if task is not None:
                    _enc = task[1]
                    _d = getattr(_enc, "data", None)
                    _ts = getattr(_enc, "timestamp", None)
                    if _d and _ts is not None:
                        _item = ((bytes(_d), int(_ts), _h264_has_keyframe(bytes(_d)))
                                 if is_video else (bytes(_d), int(_ts)))
                        try:
                            out_q.put_nowait(_item)
                        except Exception:
                            pass  # full -> drop (PLI re-arms a GOP)
            except Exception:
                _LOGGER.debug("swallowed exception in %s", '_tap_put', exc_info=True)
            return _orig_put(task, *a, **k)

        _qd.put = _tap_put
        _qd._aidot_tapped = True
        return True

    def _install_av_taps(self, pc, vq, aq) -> bool:
        """Install the video tap (required) and audio tap (best-effort).

        Called repeatedly until the video tap lands; _install_encoded_tap is
        idempotent per receiver, so audio isn't re-wrapped across retries.
        """
        got_v = False
        got_a = False
        for _r in pc.getReceivers():
            _tr = getattr(_r, "track", None)
            if _tr is None:
                continue
            if _tr.kind == "video" and not got_v:
                got_v = self._install_encoded_tap(_r, vq, True)
            elif _tr.kind == "audio" and not got_a:
                got_a = self._install_encoded_tap(_r, aq, False)
        return got_v

    def _cold_phase(self, label: str) -> None:
        """Log elapsed-ms since the open began (cold-start instrumentation).

        Defensive + best-effort: never raises, no-op if no open is in flight.
        Emits a single greppable INFO line per phase so a cold connect's timeline
        (open -> webrtcReq -> first-media -> serving) can be measured without a
        debugger or ad-hoc log scraping.  Grep ``cold-start[``."""
        try:
            t0 = self._cold_open_t0
            if t0 is None:
                return
            _LOGGER.info(
                "cold-start[%s] %s +%dms",
                self.device_id, label, int((time.monotonic() - t0) * 1000),
            )
        except Exception:
            pass

    def _resolve_idle_secs(self) -> float:
        """Serve idle-release window (seconds). A per-camera ``stream_idle_s``
        (set via start_keepalive) overrides the ``AIDOT_STREAM_IDLE_S`` env
        default of 120 s.  <= 0 means *never* idle-release (keep the warm session
        for instant re-views - sensible for mains cameras, which have no battery
        cost; note it holds a concurrent-stream slot for the camera's lifetime)."""
        opt = getattr(self, "_stream_idle_opt", None)
        if opt is not None:
            return float(opt)
        try:
            return float(os.environ.get("AIDOT_STREAM_IDLE_S", "120"))
        except (TypeError, ValueError):
            return 120.0

    def _resolve_sdes_fast_liveplay(self) -> bool:
        """Whether to use SDES fast-liveplay: don't block on the always-timing-out
        echo/livePlayResp signaling, go straight to webrtcReq/ICE.

        **Default ON** - this is exactly what the official app does (it never waits
        for/parses livePlayResp; fire-and-forget). Disable via
        ``AIDOT_SDES_FAST_LIVEPLAY`` in {0,false,no,off} or per-camera
        ``_sdes_fast_liveplay_opt=False`` (the explicit opt always wins).

        Role-reversal models (``_NO_FAST_LIVEPLAY_MODELS``, e.g. A001064) are ALWAYS
        excluded regardless of the flag - their handshake needs the camera armed
        before our webrtcReq, so the early webrtcReq degrades their media (a
        correctness exclusion, not a default choice). Only consulted for SDES
        cameras (the caller gates on is_sdes_camera)."""
        model = getattr(getattr(self, "info", None), "model_id", None)
        if model in self._NO_FAST_LIVEPLAY_MODELS:
            return False
        opt = getattr(self, "_sdes_fast_liveplay_opt", None)
        if opt is not None:
            return bool(opt)
        ov = getattr(self, "_fast_attempt_override", None)
        if ov is not None:
            return bool(ov)
        return os.environ.get("AIDOT_SDES_FAST_LIVEPLAY", "").strip().lower() not in (
            "0", "false", "no", "off")

    def _resolve_dtls_fast_liveplay(self) -> bool:
        """Whether to skip the DTLS path's livePlayReq-echo + livePlayResp waits.

        The targeted analogue of [[_resolve_sdes_fast_liveplay]] for DTLS
        (A000088) cameras.  Consumed by [[_skip_dtls_signaling_wait]], which gates
        BOTH the ~0.5 s livePlayReq-echo wait and the ~2 s livePlayResp
        accept/reject wait, while keeping the full ICE/TURN/DTLS handshake (so
        remote/relay viewing is unaffected, unlike the broader ``_fast_connect``
        which also strips TURN).

        **Default ON** - the official app never waits for/parses livePlayResp,
        and the wait was measured as the dominant LAN cold-start cost (~2 s, paid
        in full on the common timeout). The only thing lost is fast-fail on an
        outright camera refusal (livePlay=0), which is rare and then surfaces as
        an ICE failure instead. Disable via ``AIDOT_DTLS_FAST_LIVEPLAY`` in
        {0,false,no,off} or per-camera ``_dtls_fast_liveplay_opt`` (opt wins)."""
        opt = getattr(self, "_dtls_fast_liveplay_opt", None)
        if opt is not None:
            return bool(opt)
        return os.environ.get("AIDOT_DTLS_FAST_LIVEPLAY", "").strip().lower() not in (
            "0", "false", "no", "off")

    def _skip_dtls_signaling_wait(self, fast_connect: bool) -> bool:
        """Whether the DTLS open skips its livePlayReq-echo AND livePlayResp waits.

        Both waits proceed to SDP/ICE on timeout anyway, so they are skipped
        together under one predicate (keeping them in lock-step): the broad
        ``fast_connect`` mode, or the targeted default-on
        [[_resolve_dtls_fast_liveplay]] (app-parity).  Unifying the condition
        fixes a drift where the 0.5s echo wait skipped only on ``fast_connect``
        while the 2s livePlayResp wait below it also honoured dtls_fast_liveplay,
        so the default path still paid the 0.5s echo wait that usually times out."""
        return bool(fast_connect) or self._resolve_dtls_fast_liveplay()

    def _resolve_sdes_serve_audio(self) -> bool:
        """Whether to include audio in the SDES camera serve.

        **Default ON** for app-parity (the official app plays camera audio). The
        serve feeds the AAC encoder a continuous silence base (anullsrc + amix) so
        sparse battery PCMA streams cleanly through the mpegts mux. Per-camera
        ``sdes_audio`` (via ``start_keepalive``) wins; else the
        ``AIDOT_SDES_SERVE_AUDIO`` env (falsy = 0/false/no/off disables)."""
        opt = getattr(self, "_sdes_audio_opt", None)
        if opt is not None:
            return bool(opt)
        return os.environ.get("AIDOT_SDES_SERVE_AUDIO", "").strip().lower() not in (
            "0", "false", "no", "off")

    def _resolve_sdes_audio_gain_db(self) -> float:
        """Gain (dB) applied to the served SDES audio (the camera mic runs hot).

        Per-camera ``sdes_audio_gain_db`` (via ``start_keepalive``) wins; else the
        ``AIDOT_SDES_AUDIO_GAIN_DB`` env; else ``-8``.  A bad value falls back to
        the default rather than raising."""
        opt = getattr(self, "_sdes_audio_gain_opt", None)
        src = opt if opt is not None else os.environ.get("AIDOT_SDES_AUDIO_GAIN_DB", "-8")
        try:
            return float(src)
        except (ValueError, TypeError):
            return -8.0

    def _resolve_sdes_skip_turn(self) -> bool:
        """EXPERIMENTAL (opt-in, default off): skip the blocking SDES TURN relay
        pre-allocation, for cameras reachable LAN-direct.

        Before building the offer the SDES path does two synchronous RFC-5766
        Allocate round-trips (audio + video) to the cloud TURN server so the
        offer's c=/m= can carry a relay address - ~2-3 s of pure cold-start
        latency.  On a LAN the camera's host candidate wins and that relay is
        never used, so skipping it shaves the latency - at the cost of no relay
        fallback for a camera on a different segment / behind strict NAT (the
        same trade-off ``AIDOT_FAST_CONNECT`` already makes for DTLS, and which
        is force-disabled for SDES because it *also* skips the SCTP-arming
        waits; this flag skips ONLY the relay pre-allocation, leaving the rest
        of the SDES handshake intact).

        Per-camera ``sdes_skip_turn`` (set via start_keepalive) wins; else the
        ``AIDOT_SDES_SKIP_TURN_PREALLOC`` env (truthy = 1/true/yes/on), default
        off.  Only consulted for SDES cameras."""
        opt = getattr(self, "_sdes_skip_turn_opt", None)
        if opt is not None:
            return bool(opt)
        ov = getattr(self, "_fast_attempt_override", None)
        if ov is not None:
            return bool(ov)
        return os.environ.get("AIDOT_SDES_SKIP_TURN_PREALLOC", "").strip().lower() in (
            "1", "true", "yes", "on")

    def _resolve_sdes_adaptive(self) -> bool:
        """Whether the SDES keepalive loop drives the fast path adaptively
        (opt-in, default OFF): try fast-first (skip the livePlay waits + TURN
        relay pre-allocation, with a short timeout), and fall back to the full,
        patient relay path if the fast attempt delivers no media.

        This makes "fast by default" safe for cameras of unknown reachability:
        a LAN-direct camera gets the fast connect; a remote / strict-NAT camera
        loses one short fast attempt, then connects via the full relay path.

        Default OFF pending real-world fast-failure-rate data: a fast *failure*
        costs ~40 s (the grace) before fallback while success saves only ~7 s, so
        until the failure rate is known on real fleets this stays opt-in.

        Per-camera ``sdes_adaptive`` (via start_keepalive) wins; else the
        ``AIDOT_SDES_ADAPTIVE`` env (truthy = 1/true/yes/on), default off. When
        off, the per-attempt override is never set, so the explicit
        ``sdes_fast_liveplay`` / ``sdes_skip_turn`` opts (or their envs) decide -
        exactly the pre-adaptive behaviour."""
        opt = getattr(self, "_sdes_adaptive_opt", None)
        if opt is not None:
            return bool(opt)
        return os.environ.get("AIDOT_SDES_ADAPTIVE", "").strip().lower() in (
            "1", "true", "yes", "on")

    def _resolve_persistent_mqtt(self) -> bool:
        """Whether commands, attribute fetches, AND stream-open signaling reuse ONE
        account-level persistent MQTT connection (matching the app's
        LDSBaseMqttServiceImpl) instead of connecting per op.

        **Default ON** (2026-06-17): this is exactly how the official app behaves -
        one persistent connection per login session - and a live soak showed it
        cuts SDES NO_MEDIA from ~57% to ~19% (no regression). It is also safer
        than connect-per-op, which can collide on the single authorized client_id.
        Disable via ``AIDOT_PERSISTENT_MQTT`` in {0,false,no,off} or per-camera
        ``_persistent_mqtt_opt=False`` (the explicit opt always wins)."""
        opt = getattr(self, "_persistent_mqtt_opt", None)
        if opt is not None:
            return bool(opt)
        return os.environ.get("AIDOT_PERSISTENT_MQTT", "").strip().lower() not in (
            "0", "false", "no", "off")

    async def _get_persistent_mqtt(self):
        """Get-or-create the account-shared ``_PersistentMqtt`` (one per account,
        keyed on the shared ``login_info``).  The broker binds auth to the single
        authorized client_id, so there must be exactly one.  Returns None if it
        can't be built (caller falls back to per-op ``_mqtt_session``)."""
        # _user_info is the shared account dict (same object across the account's
        # DeviceClients, and the same object as AidotClient.login_info) - the right
        # place to cache one connection per account.
        li = self._user_info if isinstance(getattr(self, "_user_info", None), dict) else None
        if li is None:
            return None
        # These two keys are deliberately live runtime objects (a connection,
        # its guarding lock), NOT persistable state - see
        # RUNTIME_ONLY_LOGIN_INFO_KEYS and AidotClient.serializable_login_info(),
        # which strips them before this shared dict is ever written to
        # disk/config storage.
        pm = li.get(LOGIN_INFO_PERSISTENT_MQTT_KEY)
        if pm is not None:
            return pm
        # Serialize get-or-create. Without this, two concurrent first-callers
        # (e.g. a command publish racing a stream open) both pass the None-check
        # above, both await below, and both build a _PersistentMqtt - the second
        # clobbering the first and orphaning a connection on the single-client_id
        # broker. dict.setdefault is atomic (no await between create and insert),
        # so every caller for this account shares the one lock.
        lock = li.setdefault(LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY, asyncio.Lock())
        async with lock:
            pm = li.get(LOGIN_INFO_PERSISTENT_MQTT_KEY)  # re-check under the lock
            if pm is not None:
                return pm
            smarthome_auth = await self._async_get_smarthome_auth()
            mqtt_user = (smarthome_auth or {}).get("mqttUser") or str(self.user_id)
            mqtt_pwd  = (smarthome_auth or {}).get("mqttPassword") or ""
            client_id = (self._user_info.get("mqttClientId") or f"app-{mqtt_user}")
            mqtt_url = await self._async_get_mqtt_url()
            if not mqtt_url:
                return None
            from .protocol import _PersistentMqtt
            pm = _PersistentMqtt(mqtt_url, mqtt_user, mqtt_pwd, client_id)
            li[LOGIN_INFO_PERSISTENT_MQTT_KEY] = pm
            return pm

    @staticmethod
    def _adaptive_next_fast(adaptive: bool, fast_failed: bool) -> bool:
        """Whether the next SDES open attempt should use the fast path: only when
        adaptive mode is on and the fast path has not already failed this loop."""
        return bool(adaptive) and not bool(fast_failed)

    @staticmethod
    def _adaptive_after_attempt(use_fast: bool, healthy: bool, fast_failed: bool) -> bool:
        """Updated ``fast_failed`` after an attempt: latch it once a fast attempt
        delivers no media, so the loop stays on the full relay path (no
        oscillation) until it restarts fresh on the next view."""
        return bool(fast_failed) or (bool(use_fast) and not bool(healthy))

    def _maybe_start_serve_relay(self, serve_url: Optional[str]) -> "Optional[_ServeRelay]":
        """Hold the public serve port via a _ServeRelay so an eager go2rtc pull
        connects-and-waits instead of hitting ECONNREFUSED during the ~16-25s
        cold handshake (ffmpeg only binds its -listen socket after input frames).

        Returns the started relay, or None when disabled / not an http serve /
        the bind fails - in which case the caller serves ffmpeg directly on the
        public port (the pre-relay behaviour, so a bind clash never breaks
        streaming)."""
        if not (serve_url and serve_url.startswith("http")):
            return None
        opt = getattr(self, "_serve_relay_opt", None)
        if opt is None:
            opt = os.environ.get("AIDOT_SERVE_RELAY", "1") != "0"
        if not opt:
            return None
        port = _sdes_serve_port(serve_url)
        if port is None:
            return None
        relay = _ServeRelay(port)
        try:
            relay.start()
        except OSError as exc:
            _LOGGER.warning(
                "serve relay: bind :%s failed (%s) - serving ffmpeg directly",
                port, exc,
            )
            return None
        _LOGGER.debug(
            "serve relay: holding public port :%s for %s", port, self.device_id)
        return relay

    async def _dtls_serve_loop(self) -> None:
        """Acquire a concurrent-stream slot, then run the serve loop.

        Caps how many cameras stream at once (AIDOT_MAX_CONCURRENT_STREAMS) so a
        small host isn't overwhelmed by N decodes + mux threads at once.  Holds
        the slot for the serve's lifetime; waits for one if the cap is reached.
        """
        slots = _get_stream_slots()
        if slots.locked():
            _LOGGER.info(
                "DTLS serve: %s waiting for a stream slot (cap reached)",
                self.device_id,
            )
        await slots.acquire()
        self._serve_relay = self._maybe_start_serve_relay(self._keepalive_rtsp_url)
        try:
            await self._dtls_serve_loop_inner()
        finally:
            _relay = self._serve_relay
            self._serve_relay = None
            if _relay is not None:
                _relay.close()
            slots.release()

    async def _dtls_serve_loop_inner(self) -> None:
        """Serve a DTLS camera's live H.264 to go2rtc over an HTTP-listen socket.

        Powered (A000088) cameras have no local API and aiortc owns the
        DTLS/SRTP keys, so we can't point ffmpeg at the socket like the SDES
        path.  Instead aiortc does ICE/DTLS/SRTP-decrypt/depacketize and we tap
        the *encoded* H.264 frames and ``-c copy`` them to ``ffmpeg -f mpegts
        -listen 1`` - no decode, no re-encode (viable on a Raspberry Pi).  The
        WebRTC session is held warm; only the local ffmpeg serve is restarted
        when go2rtc disconnects/reconnects, so reconnects need no camera
        re-handshake.
        """
        import threading as _threading
        import queue as _queue
        serve_url = self._keepalive_rtsp_url
        # APK parity: the official app gates re-connects to ~15 s (f0.java I1=15000)
        # and never hammers.  We previously floored at 5 s and a partial-success ->
        # reset path could drop the effective spacing even lower, pounding a flaky
        # A000088 camera fast enough to wedge its DTLS stack (ICE completes but DTLS
        # never fires until a power-cycle).  Floor the backoff at the 15 s gate and,
        # independently, enforce a hard minimum wall-clock spacing between OPEN
        # attempts so no code path (reset bug, fast PC-death) can pound the camera.
        _MIN_DELAY, _MAX_DELAY = 15.0, 300.0
        _open_gate = float(os.environ.get("AIDOT_DTLS_RETRY_GATE_S", "15"))
        # Jittered-backoff pacer for failed opens. Unlike the SDES/JPEG loops this
        # serve loop resets on any successful open (it has the _open_gate spacing
        # below) rather than escalating on no-media - hence reset() not
        # session_end_delay().
        _pacer = ReconnectPacer(_MIN_DELAY, _MAX_DELAY)
        _last_open_at = 0.0  # monotonic of the previous open attempt (0 = none yet)
        _not_ready_burst = 0  # consecutive DC-only (encoder-cold) declines
        loop = asyncio.get_running_loop()
        while self._streaming_active:
            # Thread-safe queues: taps run on the loop, the A/V mux in a thread.
            vq: "_queue.Queue" = _queue.Queue(maxsize=600)
            aq: "_queue.Queue" = _queue.Queue(maxsize=600)
            self._serve_ready.clear()  # fresh (cold) session: not ready until bound
            # Hard inter-attempt gate (APK parity): never start an open within
            # _open_gate seconds of the previous one, regardless of the backoff.
            # This is the SOLE reconnect-spacing mechanism (no separate post-death
            # sleep) - after a healthy session it has long elapsed, so a clean
            # death reopens immediately; a fast death still owes the full gate.
            _gate_wait = _open_gate_delay(_last_open_at, loop.time(), _open_gate)
            if _gate_wait:
                try:
                    await asyncio.sleep(_gate_wait)
                except asyncio.CancelledError:
                    return
            _last_open_at = loop.time()
            try:
                session = await self.async_open_webrtc_stream(on_frame=lambda _f: None)
            except asyncio.CancelledError:
                return
            except AidotCameraBusy as busy:
                # Another viewer holds the camera's single slot. Retry on a short
                # interval (not the 5-min backoff) so it reconnects promptly once
                # the slot frees (e.g. the user closes the app / reboots the cam).
                busy_retry = float(os.environ.get("AIDOT_BUSY_RETRY_S", "45"))
                _LOGGER.warning(
                    "DTLS serve: camera %s busy (%s) - retrying in %.0fs",
                    self.device_id, busy, busy_retry,
                )
                try:
                    await asyncio.sleep(busy_retry)
                except asyncio.CancelledError:
                    return
                continue
            except AidotCameraNotReady:
                # Camera answered cleanly but declined media (DC-only answer):
                # its encoder is still cold and warming up.  Fast-retry in a
                # bounded burst (3s x4) that bypasses the 15s inter-attempt gate
                # so we catch the encoder the moment it comes up, then fall back
                # to the gate so a persistently-cold camera isn't hammered.  The
                # burst counter resets only on a successful open (below).
                _not_ready_burst += 1
                _delay, _bypass = _retry_policy("not_ready", _not_ready_burst)
                if _bypass:
                    _last_open_at = 0.0  # clear the gate for the fast burst
                _LOGGER.info(
                    "DTLS serve: camera %s not ready (encoder cold) -"
                    " retry %.0fs [%s]",
                    self.device_id, _delay, "burst" if _bypass else "gate",
                )
                try:
                    await asyncio.sleep(_delay)
                except asyncio.CancelledError:
                    return
                continue
            except Exception as exc:
                _delay = _pacer.fail_delay()
                _LOGGER.warning(
                    "DTLS serve: open failed for %s (retry %.0fs): %s",
                    self.device_id, _delay, exc,
                )
                try:
                    await self._backoff_or_offline_pause(_delay)
                except asyncio.CancelledError:
                    return
                continue

            _pacer.reset()
            _not_ready_burst = 0  # clean open: reset the encoder-cold burst
            self._stream_session = session
            pc = session._pc
            # PC-dead test for the serve loop.  closed/failed are terminal at
            # once; a transient ICE "disconnected" is debounced - aiortc reports
            # it on a brief consent/path blip that often self-recovers, so we only
            # treat a SUSTAINED disconnect as dead and force a fresh session
            # (matching the app, which reconnects on ICE FAILED/CLOSED/DISCONNECTED
            # - f0.java:2495).  Previously a stuck "disconnected" was only caught
            # ~120 s later by the idle-progress timer.
            _disc_since = [None]
            _DISC_DEBOUNCE = float(os.environ.get("AIDOT_ICE_DISCONNECT_S", "8"))

            def _pc_dead(pc=pc, _disc_since=_disc_since, _DISC_DEBOUNCE=_DISC_DEBOUNCE) -> bool:
                _st = getattr(pc, "connectionState", "closed")
                if _st in ("closed", "failed"):
                    return True
                if _st == "disconnected":
                    _t = loop.time()
                    if _disc_since[0] is None:
                        _disc_since[0] = _t
                    elif _t - _disc_since[0] >= _DISC_DEBOUNCE:
                        return True
                else:
                    _disc_since[0] = None  # connected/connecting -> reset debounce
                return False

            for _ in range(40):  # ~12s for the receiver/track(s) to exist
                if self._install_av_taps(pc, vq, aq):
                    break
                await asyncio.sleep(0.3)

            # Must exceed HA's stream-worker reconnect interval (~40s) or the serve
            # is torn down between a viewer's retries -> "Connection refused". 120s
            # survives several retries while still releasing an unviewed camera.
            # stream_idle_s / AIDOT_STREAM_IDLE_S override; <= 0 = never release
            # (keep warm for instant re-views; mains cameras only - see P1).
            idle_secs = self._resolve_idle_secs()
            proc = wfile = stop_flag = mux_thread = None
            cancelled = idle_release = False
            try:
                # (Re)start the ffmpeg serve whenever go2rtc (re)connects, while
                # the warm WebRTC session keeps delivering encoded frames.
                while self._streaming_active and not _pc_dead():
                    # Drain any backlog so a (re)connect starts on a fresh GOP.
                    for _q2 in (vq, aq):
                        try:
                            while True:
                                _q2.get_nowait()
                        except _queue.Empty:
                            pass
                    await self._send_video_pli(pc)  # request a clean keyframe
                    # With the relay holding the public port, ffmpeg listens on a
                    # fresh internal port each cycle and the relay splices to it;
                    # without it, ffmpeg owns the public port directly (old path).
                    _relay = self._serve_relay
                    if _relay is not None:
                        _ff_port = _grab_free_port()
                        _ff_url = _rewrite_serve_port(serve_url, _ff_port)
                    else:
                        _ff_port = None
                        _ff_url = serve_url
                    rfd, wfd = os.pipe()
                    proc = await self._spawn_dtls_serve_ffmpeg(_ff_url, rfd)
                    os.close(rfd)
                    if proc is None:
                        os.close(wfd)
                        break
                    if _relay is not None:
                        _relay.set_backend(_ff_port)
                    wfile = os.fdopen(wfd, "wb", buffering=0)
                    progress = [loop.time()]
                    stop_flag = _threading.Event()
                    mux_thread = _threading.Thread(
                        target=_dtls_av_mux_run,
                        args=(vq, aq, wfile, progress, stop_flag),
                        daemon=True,
                    )
                    mux_thread.start()
                    # Once the mux is feeding and ffmpeg has had a moment to bind
                    # its -listen socket, signal "serve ready" so stream_source can
                    # hand HA a working URL (no 40s connection-refused retry gap).
                    if not self._serve_ready.is_set():
                        _p0 = progress[0]
                        for _ in range(40):  # up to ~8s for the first muxed frames
                            await asyncio.sleep(0.2)
                            if progress[0] > _p0:
                                break
                        # ffmpeg binds its -listen socket at startup, so by the time
                        # the mux has written a frame (progress moved above) the
                        # socket is long bound.  A short margin is plenty; the old
                        # 2.0s blind wait was pure cold-start latency.  (Don't probe
                        # the port - `-listen 1` would consume our probe as its one
                        # client and tear down before go2rtc connects.)
                        await asyncio.sleep(0.3)
                        self._serve_ready.set()
                        self._cold_phase("serving (dtls)")
                    # Periodic keyframe request shortens the camera's GOP so HA's
                    # HLS segmenter (which cuts on keyframes) produces short
                    # segments -> far less player buffering (the ~20s HLS lag is
                    # dominated by long native GOPs).  0 disables; raising it
                    # lowers bitrate at the cost of latency.  Mains DTLS cameras
                    # only; the SDES path has its own PLI cadence.
                    _gop_pli_s = float(os.environ.get("AIDOT_GOP_PLI_S", "2.0"))
                    # Stall-triggered keyframe: if muxed frames stop for
                    # _stall_pli_s (a dropped GOP on a jittery link, e.g. a weak-
                    # RSSI camera), ask for an IDR immediately instead of waiting
                    # out the full cadence, so playback recovers sooner.  Fires
                    # at most ONCE per stall episode (re-armed only when frames
                    # resume) so a no-consumer stall - which also freezes progress
                    # - can't spam the camera with PLIs.  0 disables.
                    _stall_pli_s = float(os.environ.get("AIDOT_STALL_PLI_S", "1.0"))
                    _last_gop_pli = loop.time()
                    _stall_pli_armed = True
                    # Wait for ffmpeg to exit (go2rtc disconnect) or idle release.
                    while self._streaming_active and proc.returncode is None:
                        await asyncio.sleep(0.5)
                        if _pc_dead():
                            break
                        _now = loop.time()
                        _stall = _now - progress[0]
                        if _stall < 0.5:
                            _stall_pli_armed = True       # frames flowing; re-arm
                        if _gop_pli_s > 0 and _now - _last_gop_pli >= _gop_pli_s:
                            await self._send_video_pli(pc)
                            _last_gop_pli = _now
                        elif (_stall_pli_s > 0 and _stall_pli_armed
                              and _stall >= _stall_pli_s):
                            await self._send_video_pli(pc)
                            _last_gop_pli = _now           # also satisfies cadence
                            _stall_pli_armed = False       # one shot per stall
                        # No consumer -> the pipe fills, the mux blocks, progress
                        # goes stale.  A real viewer keeps it fresh.  idle_secs<=0
                        # disables release entirely (keep warm for instant views).
                        if idle_secs > 0 and _now - progress[0] > idle_secs:
                            idle_release = True
                            break
                    # Tear down this ffmpeg+mux cycle before the next.
                    stop_flag.set()
                    try:
                        wfile.close()
                    except Exception:
                        _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_pc_dead', exc_info=True)
                    wfile = None
                    mux_thread.join(timeout=2.0)
                    mux_thread = stop_flag = None
                    if _relay is not None:
                        _relay.set_backend(None)  # no backend until next ffmpeg
                    _terminate_proc(proc)
                    proc = None
                    if idle_release:
                        break
            except asyncio.CancelledError:
                cancelled = True
            finally:
                if stop_flag is not None:
                    stop_flag.set()
                if wfile is not None:
                    try:
                        wfile.close()
                    except Exception:
                        _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_pc_dead', exc_info=True)
                if mux_thread is not None:
                    mux_thread.join(timeout=2.0)
                _terminate_proc(proc)  # never orphan ffmpeg on teardown
                self._stream_session = None
                try:
                    await session.stop()
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception in %s", getattr(self, "device_id", "?"), '_pc_dead', exc_info=True)

            if cancelled:
                return
            if idle_release:
                # No viewer - go dormant; camera.stream_source() restarts us when
                # someone opens the live view again.
                self._streaming_active = False
                self._keepalive_rtsp_url = None
                self._serve_ready.clear()
                _LOGGER.debug(
                    "DTLS serve: %s idle (no viewer) - released until next view",
                    self.device_id,
                )
                return
            # No separate post-death delay: loop back to the top open-gate, which
            # spaces attempts from the previous open-start.  After a healthy
            # session the gate has elapsed (immediate reopen); a fast death still
            # owes the full gate there.  (Was a hardcoded 15s sleep that ignored
            # AIDOT_DTLS_RETRY_GATE_S and added dead latency after healthy drops.)

    async def _spawn_dtls_serve_ffmpeg(self, serve_url: Optional[str], stdin_fd: int):
        """Launch ffmpeg to serve the mux thread's MPEG-TS (read from ``stdin_fd``)
        on an HTTP-listen socket go2rtc pulls.  The stream is already cleanly
        timestamped + AAC-muxed (with the gain/limiter applied) by PyAV, so
        ffmpeg just copies and serves - re-encoding here dropped the live audio
        (mpegts PMT-timing on the real-time pipe).  Returns the process, or None."""
        if not serve_url:
            return None
        if _ffmpeg_path() is None:
            _LOGGER.error("DTLS serve: %s", _FFMPEG_MISSING_MSG)
            return None
        cmd = [
            "ffmpeg", "-y", "-loglevel", "warning",
            # Suppress input-side buffering: the PyAV mux already writes
            # correctly-interleaved, timestamped MPEG-TS to the pipe every
            # ~20ms.  Without +nobuffer ffmpeg's mpegts demuxer accumulates a
            # read-ahead window (and the output mpegts muxer defaults to 700ms
            # of A/V interleave delay) before flushing to go2rtc - exactly the
            # bursty/choppy audio pattern.  Matches the SDES serve's approach.
            "-fflags", "+nobuffer",
            "-i", "pipe:0",
            "-c", "copy", "-f", "mpegts", "-listen", "1", serve_url,
        ]
        try:
            return await asyncio.create_subprocess_exec(
                *cmd,
                stdin=stdin_fd,
                stdout=asyncio.subprocess.DEVNULL,
                stderr=asyncio.subprocess.DEVNULL,
            )
        except FileNotFoundError:
            _LOGGER.error("DTLS serve: ffmpeg not found")
            return None
        except Exception as exc:
            _LOGGER.warning("DTLS serve: ffmpeg launch failed: %s", exc)
            return None

    # -- PTZ physical pan/tilt (A001064) ----------------------------------- #
    # Uses IOCtrl cmd=4097 (IOTYPE_USER_IPCAM_PTZ_COMMAND) - NOT MQTT.
    # DTLS path: sent via WebRTC DataChannel. SDES path: sent via encrypted
    # SCTP cmd_chan. Requires an active stream session (_stream_session).




    async def async_open_cloud_playback(
        self,
        start_ts: int,
        end_ts: int,
        on_frame: Callable[[VideoFrame], None],
    ) -> Optional[CloudPlaybackSession]:
        """Open a cloud-playback session streaming frames for the given time range."""
        # Open a cloud-playback session and begin streaming VideoFrame objects.
        # start_ts / end_ts: Unix timestamps in milliseconds.
        # on_frame: called in the asyncio event loop for each decoded frame.
        # Returns a running CloudPlaybackSession, or None if handshake fails.
        #
        # Three-step handshake from LDSOpenSDK.playCloudRecord():
        #   1. MQTT getPlaybackServerInfoReq -> serverIP, serverPort, heartbeat
        #   2. HTTP POST playRecord          -> taskId
        #   3. TCP binary login + stream
        import aiohttp

        # Fetch MQTT credentials from the Leedarson smarthome /user/login endpoint.
        # The AiDot platform login does NOT return mqttUser/mqttPassword.
        smarthome_auth = await self._async_get_smarthome_auth()
        mqtt_user = (smarthome_auth or {}).get("mqttUser") or str(self.user_id)
        mqtt_pwd  = (smarthome_auth or {}).get("mqttPassword") or ""
        client_id = (self._user_info.get("mqttClientId") or f"app-{mqtt_user}")

        # Step 1 - MQTT
        mqtt_url = await self._async_get_mqtt_url()
        if not mqtt_url:
            _LOGGER.error(
                "async_open_cloud_playback: cannot determine MQTT URL for %s",
                self.device_id,
            )
            return None

        _LOGGER.debug("Cloud playback step 1: MQTT for %s", self.device_id)
        srv_info = await _mqtt_get_playback_server_info(
            mqtt_url, mqtt_user, mqtt_pwd, self.device_id, client_id,
        )
        if not srv_info:
            _LOGGER.error(
                "async_open_cloud_playback: MQTT response empty for %s",
                self.device_id,
            )
            return None

        server_ip   = srv_info.get("serverIP")
        server_port = srv_info.get("serverPort")
        heartbeat   = int(srv_info.get("heartbeat") or 15)

        if not server_ip or not server_port:
            _LOGGER.error(
                "async_open_cloud_playback: incomplete server info for %s: %s",
                self.device_id, srv_info,
            )
            return None

        # Step 2 - HTTP playRecord
        _LOGGER.debug("Cloud playback step 2: HTTP playRecord for %s", self.device_id)
        try:
            async with aiohttp.ClientSession() as session:
                async with session.post(
                    f"{self._smarthome_base}"
                    "/api/ipc/playbackController/playRecord",
                    json={
                        "deviceId":      self.device_id,
                        "recordStaTime": start_ts,
                        "recordEndTime": end_ts,
                    },
                    headers=self._leedarson_headers(),
                    timeout=aiohttp.ClientTimeout(total=15),
                ) as resp:
                    play_body = await resp.json(content_type=None)

            if play_body.get("code") != 200:
                _LOGGER.error(
                    "playRecord returned code=%s for %s: %s",
                    play_body.get("code"), self.device_id, play_body,
                )
                return None

            task_id = (play_body.get("data") or {}).get("taskId")
            if task_id is None:
                _LOGGER.error(
                    "playRecord: no taskId in response for %s: %s",
                    self.device_id, play_body,
                )
                return None

        except Exception as exc:
            _LOGGER.error(
                "async_open_cloud_playback: playRecord failed for %s: %s",
                self.device_id, exc,
            )
            return None

        # Step 3 - TCP
        _LOGGER.debug(
            "Cloud playback step 3: TCP to %s:%d task=%d heartbeat=%ds",
            server_ip, server_port, task_id, heartbeat,
        )
        pb_session = CloudPlaybackSession(
            server_ip=server_ip,
            server_port=int(server_port),
            heartbeat_interval=heartbeat,
            task_id=int(task_id),
            client_id=str(self.user_id),
            start_ts_s=start_ts // 1000,
            on_frame=on_frame,
        )
        if not await pb_session.start():
            return None

        _LOGGER.info(
            "Cloud playback session open for %s task=%d start=%d",
            self.device_id, task_id, start_ts // 1000,
        )
        return pb_session

    async def async_open_live_stream(
        self,
        on_frame: Callable[[VideoFrame], None],
        timeout: float = 60.0,
    ) -> Optional[TutkStreamSession]:
        """Open a TUTK IOTC P2P live-stream session, delivering frames to on_frame."""
        # Open a TUTK IOTC P2P live-stream session.
        # on_frame: called from the receive thread for each decoded VideoFrame.
        # Returns a running TutkStreamSession, or None on failure.
        #
        # Protocol: TUTK IOTC P2P (confirmed from classes.jar.decompiled.zip).
        #   p2pId (TUTK UID) <- POST /v21/devices/batchGetDeviceUserInfo
        #   IOTC_Connect_ByUID_Parallel(uid) -> nSID
        #   avClientStart2(nSID, "admin", "admin123") -> avIndex
        #   avSendIOCtrl(avIndex, 511, ...) -> start video stream
        #   avRecvFrameData2(avIndex, ...) -> frame loop
        #
        # Requires libIOTCAPIs.so + libAVAPIs.so from the TUTK SDK.

        uid = await self.async_get_p2p_uid()
        if not uid:
            props = getattr(self, "_raw_device", {}).get("properties") or {}
            is_webrtc = (str(props.get("enableSdes", "0")) == "1"
                         or str(props.get("liveType", "0")) == "2")
            if is_webrtc:
                _LOGGER.error(
                    "async_open_live_stream: p2pId not available for %s - "
                    "this camera uses WebRTC streaming (enableSdes=%s, liveType=%s); "
                    "use async_open_webrtc_stream() for live video.",
                    self.device_id,
                    props.get("enableSdes", "0"),
                    props.get("liveType", "0"),
                )
            else:
                _LOGGER.error(
                    "async_open_live_stream: p2pId not available for %s. "
                    "Ensure batchGetDeviceUserInfo returns data (check auth/request "
                    "format) or that the smarthome getP2pId endpoint is reachable.",
                    self.device_id,
                )
            return None

        _LOGGER.debug(
            "async_open_live_stream: TUTK P2P uid=%s for %s", uid, self.device_id)
        session = TutkStreamSession(uid=uid, on_frame=on_frame)
        try:
            ok = await asyncio.wait_for(session.start(), timeout=timeout)
        except TimeoutError:
            _LOGGER.error(
                "async_open_live_stream: TUTK connect timed out after %.0fs for %s",
                timeout, self.device_id,
            )
            return None
        if not ok:
            return None

        _LOGGER.info(
            "TUTK live stream session open for %s (uid=%s)",
            self.device_id, uid,
        )
        return session

    async def async_get_ice_config(self, device_id: str) -> Optional[dict]:
        """Fetch STUN/TURN ICE server credentials for a liveType=2 camera.

        Publishes ``IPC/getIceConfigReq`` via MQTT and waits up to 5 s for the
        ``IPC/getIceConfigResp`` reply.  The response contains per-device and
        per-user TURN credentials for the Arnoo TURN cluster.

        Returns a dict with keys ``app`` (list of user-side ICE server entries)
        and ``dev`` (list of device-side ICE server entries), each entry having
        the shape::

            {"id": str, "token": int, "ttl": int,
             "uris": [...], "dnsUris": [...]}

        Returns ``None`` if the MQTT session fails or no response arrives.
        A fallback ICE config (public STUN only, no TURN credentials) can be
        constructed by the caller if ``None`` is returned.
        """
        smarthome_auth = await self._async_get_smarthome_auth()
        mqtt_user = (smarthome_auth or {}).get("mqttUser") or str(self.user_id)
        mqtt_pwd  = (smarthome_auth or {}).get("mqttPassword") or ""
        user_id   = str(self.user_id)
        # Use the server-assigned authorised clientId - the broker rejects
        # random or made-up prefixes with rc=4.
        diag_cid  = (
            self._user_info.get("mqttClientId") or
            f"app-{mqtt_user}"
        )
        mqtt_url  = await self._async_get_mqtt_url()
        if not mqtt_url:
            _LOGGER.warning("async_get_ice_config: no MQTT URL available")
            return None

        seq     = f"ap{random.randint(1000000, 9999999)}"
        result: dict = {}

        payload = json.dumps({
            "method":  "getIceConfigReq",
            "service": "IPC",
            "srcAddr": f"0.{user_id}",
            "seq":     seq,
            "tst":     int(time.time() * 1000),
            "payload": {"deviceId": device_id, "userId": user_id},
        })

        def _capture(topic: str, raw: str) -> None:
            # Accept any message on the user callback topic that looks like
            # an ICE config response.
            if "iceconfig" in topic.lower() or "getice" in topic.lower():
                try:
                    msg = json.loads(raw)
                    inner = (msg.get("payload") or msg.get("data") or msg)
                    if (isinstance(inner, dict) and ("app" in inner or "dev" in inner)) or (isinstance(inner, dict) and "data" not in result):
                        result["data"] = inner
                except Exception:
                    result["data"] = raw

        await _mqtt_session(
            mqtt_url, mqtt_user, mqtt_pwd, diag_cid,
            subscribe_topics=[f"iot/v1/c/{user_id}/#"],
            publish_items=[(f"iot/v1/s/{user_id}/IPC/getIceConfigReq", payload)],
            duration=5.0,
            on_message=_capture,
        )

        if "data" not in result:
            _LOGGER.warning(
                "async_get_ice_config: no getIceConfigResp received for device %s; "
                "the device may not support MQTT ICE config (try after opening app live view)",
                device_id,
            )
        return result.get("data")

    async def async_get_ice_config_http(self) -> Optional[dict]:
        """Fetch ICE config via HTTP (primary path used by official app).

        Calls ``/v29/api/webrtc/iceConfig?forceRefresh=0`` using the same
        Appid/Token headers used by all other AiDot platform API calls.
        Returns a dict with ``app`` / ``dev`` keys on success, or ``None``.

        Cached between cold opens until just before the server-provided ttl, so
        a re-open (after the warm-hold lapses) skips this ~2s fetch.
        """
        import aiohttp

        if self._cached_ice_config is not None and time.time() < self._ice_config_expiry:
            _LOGGER.debug(
                "async_get_ice_config_http: cached config (%.0fs left)",
                self._ice_config_expiry - time.time())
            return self._cached_ice_config

        token = (
            self._user_info.get("accessToken")
            or self._user_info.get("access_token")
        )

        if not self._region or not token:
            _LOGGER.warning(
                "async_get_ice_config_http: missing region or access token"
            )
            return None

        url = (
            f"https://prod-{self._region}-api.arnoo.com"
            f"/v29/api/webrtc/iceConfig?forceRefresh=0"
        )

        try:
            async with aiohttp.ClientSession() as session:
                async with session.get(
                    url,
                    headers=self._aidot_headers(),
                    timeout=aiohttp.ClientTimeout(total=10),
                ) as resp:
                    if resp.status != 200:
                        _LOGGER.warning(
                            "async_get_ice_config_http: HTTP %s from %s",
                            resp.status, url,
                        )
                        return None
                    cfg = await resp.json(content_type=None)
                    self._cache_ice_config(cfg)
                    return cfg
        except Exception as exc:
            _LOGGER.warning("async_get_ice_config_http failed: %s", exc)
            return None

    def _cache_ice_config(self, cfg: "Any") -> None:
        """Cache the ICE config until just before the soonest TURN ``ttl``.

        Each entry carries a ``ttl`` Unix epoch; cache until ``min(ttl) - 60s``,
        capped at +1h so any mid-life server-side credential rotation is still
        picked up promptly.  No usable ttl -> don't cache (fail safe to the prior
        always-fetch behaviour).  Never caches an already-expired config."""
        ttls: "list[float]" = []

        def _walk(o: "Any") -> None:
            if isinstance(o, dict):
                v = o.get("ttl")
                if isinstance(v, (int, float)) and not isinstance(v, bool) and v > 1_000_000_000:
                    ttls.append(float(v))
                for x in o.values():
                    _walk(x)
            elif isinstance(o, list):
                for x in o:
                    _walk(x)

        _walk(cfg)
        if not ttls:
            return
        now = time.time()
        expiry = min(min(ttls) - 60.0, now + 3600.0)
        if expiry <= now:
            return
        self._cached_ice_config = cfg
        self._ice_config_expiry = expiry
        _LOGGER.debug("async_get_ice_config_http: cached for %.0fs", expiry - now)

    @staticmethod
    def generate_webrtc_peer_id(
        live_type: int = 2, stream_id: int = 0, *, sdes: bool = False
    ) -> str:
        """Generate a peerId for a WebRTC connection.

        Format observed in iOS app telemetry:
        ``{32-hex-session}_{6-hex-random}_{liveType}_{streamId}_{version}``

        The trailing version digit encodes the signalling transport:
        ``1`` for SDES-SRTP cameras, ``2`` for DTLS-SRTP cameras.
        iOS app telemetry (2025-03-23) confirms: LK.IPC.A001513 (SDES,
        ``enableSdes: "1"``) uses ``_1`` and responds; LK.IPC.A000088 /
        LK.IPC.A001064 (DTLS, ``enableSdes: "0"``) use ``_2`` and respond.
        SDES cameras appear to silently discard webrtcReq with ``_2``.
        """
        import os
        session = os.urandom(16).hex()          # 32 hex chars
        rand6   = os.urandom(3).hex()           # 6 hex chars
        version = 1 if sdes else 2
        return f"{session}_{rand6}_{live_type}_{stream_id}_{version}"

    async def async_open_webrtc_stream(self, *args, **kwargs) -> "WebRTCSession":
        """Serialized entry point for opening a WebRTC stream.

        Holds the global open-gate during the handshake so concurrent camera
        opens don't drown the shared MQTT signaling channel (see
        _get_webrtc_open_gate); the gate is released as soon as the session is
        established (or fails), so established streams run concurrently.
        """
        async with _get_webrtc_open_gate():
            return await self._async_open_webrtc_stream_impl(*args, **kwargs)


    # Keep old name as alias
    async_open_kvs_stream = async_open_webrtc_stream

    # ------------------------------------------------------------------ #
    # SDES-SRTP streaming path (cameras with isDTLS == '0')
    # ------------------------------------------------------------------ #

    class _SdesNoAnswerError(Exception):
        """Raised by _open_sdes_stream when no webrtcResp arrives and
        DTLS fallback is permitted (isDTLS != '0').

        Signals async_open_webrtc_stream to retry with the DTLS path for
        cameras that report ``enableSdes: '1'`` but actually require DTLS.
        Cameras with ``isDTLS: '0'`` are excluded from this path; they
        instead continue with ffmpeg and collect the SRTP stream directly.
        """


