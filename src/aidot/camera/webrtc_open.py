"""WebRTC (DTLS-SRTP) stream-open state machine, split out of client.py.

``CameraMixin._async_open_webrtc_stream_impl`` is the ~3.3k-line DTLS open path.
It lives here as a mixin to keep client.py readable; behaviour-preserving (the
method body is unchanged except for a lazy import that breaks the
client<->webrtc_open cycle).  CameraMixin inherits this mixin, so ``self`` is a
full CameraMixin at runtime.
"""

import asyncio
import json
import os
import random
import struct
import time
import logging
from typing import Callable, Optional

from ..exceptions import AidotCameraBusy
from .models import VideoFrame
from .webrtc import WebRTCSession
from .protocol import (
    _compress_sdp_for_camera,
    _dedup_bundle_candidates,
    _filter_sdp_candidates,
    _install_highport_nomination_patch,
    _ip_looks_ascii_garbled,
    _make_talk_audio_track,
    _mqtt_session_sync,
    _mqtt_timestamp,
    _normalize_bundle_ice_credentials,
    _reorder_m_section_ice_attrs,
    _sdp_transport,
    _terminal_webrtc_ack,
    _upgrade_sctp,
    _webrtc_consume_video,
)

_LOGGER = logging.getLogger(__name__)


class _WebRTCOpenMixin:
    async def _async_open_webrtc_stream_impl(
        self,
        on_frame: Optional[Callable[["VideoFrame"], None]] = None,
        *,
        stream_id: int = 0,
        timeout: float = 30.0,
        output_path: Optional[str] = None,
        max_seconds: Optional[float] = None,
        status_callback: Optional[Callable[[str], None]] = None,
        force_sdes: Optional[bool] = None,
        _skip_ice_config: bool = False,
        _ice_config: "Optional[dict]" = None,
        _sdes_answer_timeout: Optional[float] = None,
        rtsp_push_url: Optional[str] = None,
        talk_pcm_provider: "Optional[Callable[[], Optional[bytes]]]" = None,
        talk: bool = False,
    ) -> "WebRTCSession":
        """Open a liveType=2 WebRTC stream via MQTT signaling.

        Performs the full WebRTC handshake over MQTT, then delivers decoded
        video frames to ``on_frame`` (and/or records to ``output_path``).

        Supports both DTLS-SRTP cameras (aiortc path, peerid suffix ``_2``)
        and SDES-SRTP cameras (ffmpeg path, peerid suffix ``_1``).  The
        transport is auto-detected from ``self.is_sdes_camera`` unless
        overridden by ``force_sdes``.

        Protocol (confirmed from live MQTT capture, 2025-03 / 2026-03):
          1. Subscribe ``iot/v1/c/{userId}/#`` on the authorised MQTT clientId
          2. Publish to ``iot/v1/s/{userId}/IPC/getIceConfigReq`` (server-side wake)
             → wait 2 s for broker session init
          3. Publish to ``iot/v1/s/{userId}/IPC/livePlayReq`` (camera-side arm)
             → wait 0.5 s for camera WebRTC subsystem to arm
          4. Create peer connection (aiortc or SDES SDP), add recvonly tracks
          5. Generate SDP offer → publish to ``iot/v1/s/{userId}/IPC/webrtcReq``
          6. Receive ``IPC/webrtcResp`` on ``iot/v1/c/{userId}/#`` → set remote description
          7. Exchange ICE candidates on ``iot/v1/s/{userId}/IPC/iceCandidateReq``
          8. Receive media tracks → call ``on_frame`` for each VideoFrame

        Topic routing: ALL IPC publish messages (getIceConfigReq, livePlayReq,
        webrtcReq, iceCandidateReq) go to ``iot/v1/s/{userId}/IPC/...``.
        The broker routes to the specific camera using the ``devId`` field in
        the JSON payload, NOT based on the MQTT topic path.  Confirmed from
        iOS app telemetry (2025-03-23): explicit publish logs show userId in
        the topic for iceCandidateReq and livePlayReq.

        Parameters
        ----------
        on_frame : callable or None
            Called with each ``av.VideoFrame`` from the camera (DTLS path only).
        stream_id : int
            Stream index: 0 = main stream, 1 = sub-stream.
        timeout : float
            Seconds to wait for ICE connection before raising RuntimeError.
        output_path : str or None
            Record the stream to this file (e.g. ``/tmp/live.ts``) via
            aiortc MediaRecorder (DTLS) or ffmpeg (SDES).  Supports any
            container ffmpeg can write; ``.ts`` is streamable via vlc/ffplay.
        max_seconds : float or None
            Stop recording after this many seconds (SDES path: passed as
            ``-t`` to ffmpeg so it exits cleanly).  For DTLS, the caller is
            responsible for calling ``session.stop()`` after the desired
            duration; max_seconds is ignored on the DTLS path.
        force_sdes : bool or None
            Override transport auto-detection.  ``True`` → SDES path,
            ``False`` → DTLS path, ``None`` → auto (uses ``is_sdes_camera``).

        Returns
        -------
        WebRTCSession or SdesSession
            Call ``await session.stop()`` to close the stream.

        Raises
        ------
        ImportError
            If ``aiortc`` is not installed (DTLS path only).
            (``pip install python-aidot[webrtc]``).
        RuntimeError
            If the MQTT connection fails or ICE does not complete within
            ``timeout`` seconds.
        """
        from .client import CameraMixin, _spawn_bg  # lazy: break client<->webrtc_open import cycle
        import queue as _q_mod

        # Cold-start instrumentation: stamp t0 so phase markers below (and in the
        # bridge/serve threads) log elapsed-ms.  Grep ``cold-start[``.
        self._cold_open_t0 = time.monotonic()

        use_sdes = force_sdes if force_sdes is not None else self.is_sdes_camera

        # Battery cameras must have their live-stream session provisioned by the
        # cloud BEFORE MQTT signaling, or the camera rejects livePlayReq with
        # -50019 ("not ready") and never runs ICE -> no media.  App parity:
        # KVSPreConnectStrategy.fetchKvsParams calls liveStreamParam first.  We
        # only need the provisioning side effect (we stream over MQTT/SDES, not
        # AWS KVS).  Mains cameras are always stream-ready, so skip them.
        # Validated live on L2_170 (A001513): with this call livePlay succeeds and
        # decrypted RTP flows; without it, persistent -50019.  Controllable like
        # fast_connect/sdes_audio: start_keepalive(live_stream_param=...) wins,
        # else the AIDOT_LIVESTREAM_PARAM env var (default on).
        _lsp = getattr(self, "_live_stream_param_opt", None)
        if _lsp is None:
            _lsp = os.environ.get("AIDOT_LIVESTREAM_PARAM", "1") != "0"
        if self.is_battery_camera and _lsp:
            _lsp_ok = await self._async_fetch_live_stream_param()
            _LOGGER.debug("camera %s: liveStreamParam provisioned ok=%s",
                          self.device_id, _lsp_ok)

        # AIDOT_FAST_CONNECT (default off): LAN-direct mode.  Both transports stall
        # the offer on a TURN relay allocation to the cloud TURN server before ICE
        # can even start - for DTLS, aiortc's setLocalDescription blocks until ICE
        # gathering (incl. TURN Allocate) completes; for SDES we synchronously
        # pre-allocate relay before building the offer.  On a LAN the camera's host
        # candidate wins anyway, so this is pure cold-start latency (~2-3 s).  When
        # set, we skip relay allocation so the offer goes out immediately and the
        # LAN host candidate connects in ~1 s.  TRADE-OFF: no relay means cameras
        # on a different network segment / behind strict NAT cannot connect, so
        # this is opt-in and off by default.
        # Prefer an explicit per-camera setting (e.g. HA config-entry option via
        # start_keepalive(fast_connect=...)); fall back to the env var otherwise.
        _fast_connect = getattr(self, "_fast_connect_opt", None)
        if _fast_connect is None:
            _fast_connect = os.environ.get("AIDOT_FAST_CONNECT", "").strip().lower() in (
                "1", "true", "yes", "on",
            )
        # fast_connect (skip livePlay/ICE waits + strip TURN) is validated for the
        # DTLS path only.  On SDES cameras those skips leave the SCTP handshake
        # under-armed and the session churns/re-establishes (~every 60-90s),
        # dropping the live view to a snapshot.  Force the full, stable handshake
        # for SDES regardless of the option/env.
        if self.is_sdes_camera:
            _fast_connect = False

        # P5 EXPERIMENT (opt-in): skip ONLY the livePlayResp blocking wait (~2 s)
        # for SDES, while keeping the full ICE/TURN/SCTP handshake.  Rationale: the
        # SDES instability that forces _fast_connect off comes from skipping the
        # ICE/TURN waits + stripping TURN (under-arming SCTP) - NOT from skipping
        # the livePlayResp ack, which is purely a camera accept/reject we don't
        # need to block on (the official app never waits for it).  So this targeted
        # skip is expected to be SCTP-safe and shave ~2 s off the SDES cold start.
        # Off by default, pending live validation.  Applied in _open_sdes_stream
        # (the SDES path has its own livePlayResp wait); start_keepalive(
        # sdes_fast_liveplay=...) wins, else AIDOT_SDES_FAST_LIVEPLAY env.

        # Wake battery cameras via the cloud HTTP low-power endpoint before the
        # handshake (matches the app, which fires the HTTP wake so a sleeping camera
        # gets the signal even with no live MQTT session; the MQTT
        # lowPowerActiveStateReq is also published during signaling).  No-op for
        # non-battery / already-awake cameras; failures are non-fatal.
        if self.is_battery_camera:
            try:
                await self.async_wake_camera()
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception", '_async_open_webrtc_stream_impl', exc_info=True)

        if not use_sdes:
            try:
                from aiortc import RTCPeerConnection, RTCSessionDescription
                from aiortc.sdp import candidate_from_sdp
            except ImportError:
                raise ImportError(
                    "aiortc is required for WebRTC streaming. "
                    "Install with: pip install python-aidot[webrtc]"
                )

            # A000088 two-port DTLS nomination fix (validated 2026-06-01): nominate
            # only the higher of the camera's consecutive [P, P+1] ICE ports (its
            # live DTLS socket). aioice's aggressive nomination of BOTH ports made
            # the camera latch the lower one and withhold DTLS - the dominant ~25%
            # failure. A/B on M3 Pro v2: high-port-only ~87% vs ~25% baseline vs
            # ~12% low-port. Self-gating no-op for non-A000088 peers; disable via
            # AIDOT_DISABLE_HIGHPORT_FIX=1. See _install_highport_nomination_patch.
            _install_highport_nomination_patch()

            # Allow DTLS 1.0 client hellos.  L2_162 (A001513) and likely other
            # KVS-derived camera firmware sends DTLS 1.0 ClientHello (0xfeff).
            # OpenSSL 3.x disables DTLS 1.0 by default, so aiortc's SSL context
            # aborts the handshake immediately ("Unexpected EOF") without
            # responding.  Monkey-patch RTCCertificate._create_ssl_context to
            # set the min protocol version to DTLS 1.0.  Safe: A000088 cameras
            # that negotiate DTLS 1.2 still get DTLS 1.2; this only widens
            # the floor.
            try:
                from aiortc.rtcdtlstransport import RTCCertificate as _AidotRTCCert
                if not getattr(_AidotRTCCert, "_aidot_dtls10_patched", False):
                    _orig_create_ctx = _AidotRTCCert._create_ssl_context
                    _DTLS1_VERSION = 0xFEFF  # not exposed in pyOpenSSL; raw OpenSSL constant
                    def _aidot_create_ssl_context(self, srtp_profiles):
                        try:
                            _ctx = _orig_create_ctx(self, srtp_profiles)
                        except TypeError:
                            # Older pyOpenSSL (<24) requires OpenSSL.crypto.X509
                            # / PKey for use_certificate / use_privatekey, but
                            # aiortc 1.14 passes cryptography objects directly,
                            # raising "cert must be an X509 instance".  This hit
                            # on a Pi deployment and crashed every DTLS attempt
                            # in RTCPeerConnection.__connect().  Convert the
                            # cert/key to legacy pyOpenSSL types in place
                            # (idempotent via isinstance) and retry the original
                            # builder so its cipher list, SRTP profiles and
                            # verify settings are preserved.  Keeps DTLS working
                            # across pyOpenSSL versions (HA addons may ship old).
                            from OpenSSL import crypto as _ossl_crypto
                            if not isinstance(self._cert, _ossl_crypto.X509):
                                self._cert = _ossl_crypto.X509.from_cryptography(
                                    self._cert
                                )
                            if not isinstance(self._key, _ossl_crypto.PKey):
                                self._key = _ossl_crypto.PKey.from_cryptography_key(
                                    self._key
                                )
                            _ctx = _orig_create_ctx(self, srtp_profiles)
                        try:
                            _ctx.set_min_proto_version(_DTLS1_VERSION)
                        except Exception as _e:
                            _LOGGER.warning(
                                "DTLS 1.0 enable failed: %s", _e
                            )
                        return _ctx
                    _AidotRTCCert._create_ssl_context = _aidot_create_ssl_context
                    _AidotRTCCert._aidot_dtls10_patched = True
                    _LOGGER.debug("aiortc DTLS min version lowered to 1.0")
            except Exception as _patch_exc:
                _LOGGER.warning(
                    "DTLS 1.0 patch could not be applied: %s", _patch_exc
                )

        # ------------------------------------------------------------------ #
        # Credentials + MQTT setup
        # ------------------------------------------------------------------ #
        smarthome_auth = await self._async_get_smarthome_auth()
        mqtt_user = (smarthome_auth or {}).get("mqttUser") or str(self.user_id)
        mqtt_pwd  = (smarthome_auth or {}).get("mqttPassword") or ""
        user_id   = str(self.user_id)
        mqtt_cid  = (
            self._user_info.get("mqttClientId") or
            (self._user_info.get("_userConfigRaw") or {}).get("mqtt", {}).get("clientId") or
            f"app-{mqtt_user}"
        )
        # terminalIndex is the session prefix of mqtt_cid (e.g. "1i1h3m" from "1i1h3m-{userId}").
        # The camera validates srcAddr against active MQTT sessions; "0.{userId}" matches nothing.
        mqtt_url = await self._async_get_mqtt_url()
        if not mqtt_url:
            raise RuntimeError("async_open_webrtc_stream: no MQTT URL available")

        # Fetch camera's registered numeric userId.  Used for:
        # 1. Additional MQTT subscriptions (cameras may route webrtcResp by numeric ID)
        # 2. Injecting "userId" into the IPC message envelope so camera firmware can
        #    validate the caller (LK.IPC.A001064 silently ignores offers without it).
        # Run batchGetDeviceUserInfo and HTTP ICE config fetch concurrently -
        # both are independent HTTP calls; sequencing them wastes up to ~10 s.
        _fetch_http_ice = not _skip_ice_config and _ice_config is None
        if _fetch_http_ice:
            _cam_user_info, _http_ice_config = await asyncio.gather(
                self.async_get_device_user_info(all_device_ids=self._all_device_ids or None),
                self.async_get_ice_config_http(),
            )
        else:
            _cam_user_info = await self.async_get_device_user_info(
                all_device_ids=self._all_device_ids or None
            )
            _http_ice_config = None
        _numeric_uid_raw = (_cam_user_info or {}).get("userId")
        if _numeric_uid_raw is not None:
            try:
                _numeric_uid_raw = int(_numeric_uid_raw)
            except (TypeError, ValueError):
                _LOGGER.warning(
                    "async_open_webrtc_stream: unexpected userId type %r - skipping injection",
                    _numeric_uid_raw,
                )
                _numeric_uid_raw = None
        numeric_user_id = str(_numeric_uid_raw) if _numeric_uid_raw is not None else None
        if numeric_user_id is None:
            _LOGGER.warning(
                "async_open_webrtc_stream: no numeric userId from batchGetDeviceUserInfo"
                " for %s - user_info_keys=%s.  Camera firmware may reject WebRTC offers"
                " without a matching userId, and getIceConfigResp may not be routed to"
                " us (TURN credentials unavailable, ICE will likely fail over segmented"
                " networks).",
                self.device_id,
                sorted((_cam_user_info or {}).keys()),
            )

        # Also extract the camera's local IP address from batchGetDeviceUserInfo if present.
        # Used to pre-seed cam_ip_q in the role-reversal ICE wait loop as a fallback when
        # setDevAttrNotif doesn't arrive or uses an unexpected IP field name.
        # Final fallback: self._ip_address populated by LAN broadcast discovery
        # (update_ip_address / Discover.send_broadcast) - covers ICE-lite cameras such as
        # LK.IPC.A001064 whose batchGetDeviceUserInfo response contains no IP field.
        # NB: the raw-device "properties" fields below are the SAME cloud values
        # that can be ASCII-garbled (e.g. "49.57.50.46"); self._ip_address is
        # already guarded but those fallbacks are not, so filter every source
        # through _ip_looks_ascii_garbled.  A garbled value here would otherwise
        # become a synthetic host candidate pointing at a nonexistent address.
        _cam_ip_sources = [
            (_cam_user_info or {}).get("localIp"),
            (_cam_user_info or {}).get("ipAddress"),
            (_cam_user_info or {}).get("ip"),
            (_cam_user_info or {}).get("localIPAddress"),
            self._ip_address,
            ((self._raw_device or {}).get("properties") or {}).get("ipAddress"),
            ((self._raw_device or {}).get("properties") or {}).get("ip"),
        ]
        _cam_local_ip: str | None = next(
            (str(_s) for _s in _cam_ip_sources
             if _s and not _ip_looks_ascii_garbled(str(_s))),
            None,
        )
        if not _cam_local_ip:
            _LOGGER.debug(
                "async_open_webrtc_stream: camera LAN IP unknown for %s"
                " (no IP field in batchGetDeviceUserInfo, device properties, or"
                " LAN-discovered IP).  Relying on the camera's real ICE candidates"
                " (iceCandidateReq trickle + TURN relay), same as the official app.",
                self.device_id,
            )

        # Extract the camera's userUuid from batchGetDeviceUserInfo.
        # Some camera firmware (e.g. LK.IPC.A001064) publishes its real
        # webrtcResp/webrtcReq to MQTT topics keyed by the camera's own
        # userUuid rather than the app user's UUID.  If we don't subscribe
        # to those topics, the camera's answer (with its actual ICE candidates)
        # is silently dropped and we fall into the broken echo-only path.
        _cam_user_uuid: str | None = ((_cam_user_info or {}).get("userUuid") or None)
        _LOGGER.debug(
            "batchGetDeviceUserInfo: device=%s  userId=%s  userUuid=%s",
            self.device_id, _numeric_uid_raw, _cam_user_uuid,
        )

        # Respect isDTLS='0': those cameras cannot do DTLS, so falling back
        # after an SDES timeout would only hang the stream (~30 s with zero
        # frames).  For cameras where isDTLS is absent or non-zero, allow DTLS
        # fallback in case enableSdes='1' was incorrectly provisioned.
        # LK.IPC.A001064 (enableSdes='1') echoes our MQTT messages but does
        # not send STUN or SRTP - the echo-reversal DTLS fallback path at the
        # end of _open_sdes_stream handles this without launching ffmpeg.
        _cam_props = (self._raw_device or {}).get("properties") or {}
        _dtls_fallback_ok = str(_cam_props.get("isDTLS", "1")) != "0"

        device_id = self.device_id
        peer_id   = self.generate_webrtc_peer_id(
            live_type=2, stream_id=stream_id, sdes=use_sdes
        )
        loop      = asyncio.get_running_loop()

        # Broad wildcard subscriptions - the camera's full namespace is not
        # documented, and narrowing to specific service paths (IPC/IPCAM/device)
        # missed camera wake signals on other paths.  The # wildcard ensures we
        # receive all messages regardless of what service prefix the camera uses.
        sub_topics = [
            f"iot/v1/c/{user_id}/#",
            f"iot/v1/cb/{user_id}/#",
            f"iot/v1/cb/{device_id}/#",
            f"iot/v1/c/{device_id}/#",
            f"lds/v1/c/{user_id}/#",
            f"lds/v1/cb/{device_id}/#",
        ]
        # Some firmware routes responses on topics keyed by the camera's numeric
        # userId rather than the account UUID.
        if numeric_user_id and numeric_user_id != user_id:
            sub_topics += [
                f"iot/v1/c/{numeric_user_id}/#",
                f"iot/v1/cb/{numeric_user_id}/#",
                f"lds/v1/c/{numeric_user_id}/#",
            ]
        # Some firmware routes webrtcResp on topics keyed by the camera's own
        # userUuid from batchGetDeviceUserInfo instead of the app user UUID.
        _known_ids = {user_id, device_id, numeric_user_id or ""}
        if _cam_user_uuid and _cam_user_uuid not in _known_ids:
            sub_topics += [
                f"iot/v1/c/{_cam_user_uuid}/#",
                f"iot/v1/cb/{_cam_user_uuid}/#",
                f"lds/v1/c/{_cam_user_uuid}/#",
            ]
            _LOGGER.debug(
                "webrtc: adding camera userUuid subscriptions for %s (uuid=%s)",
                self.device_id, _cam_user_uuid,
            )
        # iOS app telemetry (2025-03-23) confirms ALL IPC publish topics use
        # the userId path.  The broker routes to the specific camera using the
        # ``devId`` field inside the JSON payload, NOT the MQTT topic path.
        webrtc_req_topic = f"iot/v1/s/{user_id}/IPC/webrtcReq"
        ice_cand_topic   = f"iot/v1/s/{user_id}/IPC/iceCandidateReq"
        live_play_topic  = f"iot/v1/s/{user_id}/IPC/livePlayReq"

        # ------------------------------------------------------------------ #
        # MQTT ↔ asyncio bridge
        # ------------------------------------------------------------------ #
        outgoing_q:       _q_mod.Queue    = _q_mod.Queue()
        answer_fut:        asyncio.Future = loop.create_future()
        second_answer_fut: asyncio.Future = loop.create_future()   # captures discarded broker-echo camera's real webrtcResp
        terminal_error_fut: asyncio.Future = loop.create_future()  # set to (code, desc) on a terminal webrtcResp ack (-50002/-50015)
        camera_offer_fut:     asyncio.Future = loop.create_future()  # set when camera sends webrtcReq (role-reversal)
        webrtc_req_echo_fut:  asyncio.Future = loop.create_future()  # set when broker echoes our own webrtcReq back (is_echo=True)
        ice_config_fut:    asyncio.Future = loop.create_future()  # TURN credentials from getIceConfigResp
        ice_q:            asyncio.Queue   = asyncio.Queue()
        cam_ip_q:         asyncio.Queue   = asyncio.Queue()  # camera IP from setDevAttrNotif
        camera_ready_ev:  asyncio.Event   = asyncio.Event()  # set when camera is on MQTT
        liveplay_echo_ev: asyncio.Event   = asyncio.Event()  # set when livePlayReq echo arrives
        liveplay_resp_fut: asyncio.Future = loop.create_future()  # set on livePlayResp
        camera_reconnect_ev: asyncio.Event = asyncio.Event() # set when camera sends device/connect
        # Mutable flag: set True when setDevAttrNotif delivers sptPreconn:1.
        # Confirmed 2026-05-02: both A000088 and A001064 PTZ report
        # sptPreconn:1.  AVIO LIVING (SESSION_MODE_REQ=5376) must be sent
        # via the data channel to trigger streaming in PreCon cameras.
        _spt_preconn: list = [False]

        # Gate: block asyncio until MQTT is connected + subscribed
        import threading as _threading
        _mqtt_ready_ev     = _threading.Event()
        _mqtt_conn_status: dict = {}

        def _status(msg: str) -> None:
            """Fire status_callback and log.  Callback is the primary output
            channel; logging is DEBUG so log files capture detail without INFO
            flood when the callback is not routing to a logger."""
            if status_callback:
                status_callback(msg)
                _LOGGER.debug("webrtc: %s", msg)
            else:
                _LOGGER.info("webrtc: %s", msg)

        if _numeric_uid_raw is not None and numeric_user_id != user_id:
            _LOGGER.debug("webrtc: numeric userId for payload injection: %s", _numeric_uid_raw)

        # ------------------------------------------------------------------ #
        # HTTP-first ICE config pre-fetch (matches official app behaviour)
        # ------------------------------------------------------------------ #
        # HTTP ICE config was fetched concurrently with batchGetDeviceUserInfo
        # above (parallel gather).  Just log the outcome here.
        if _fetch_http_ice:
            if _http_ice_config:
                _status("ICE config fetched via HTTP (primary path, parallel fetch)")
            else:
                _status("HTTP ICE config unavailable - will use MQTT path")

        def _on_mqtt_ready(st: dict) -> None:
            _mqtt_conn_status.update(st)
            _mqtt_ready_ev.set()

        def _extract_cam_ip(method_name: str, inner: dict, msg: dict) -> None:
            _cam_ip = (inner.get("ipAddress") or inner.get("ip")
                       or inner.get("localIp") or inner.get("localIPAddress")
                       or inner.get("wlanIp") or inner.get("wlanIPAddress")
                       or inner.get("deviceIp") or inner.get("localAddr")
                       or msg.get("ipAddress") or msg.get("ip")
                       or msg.get("localIp") or msg.get("localIPAddress"))
            if _cam_ip:
                loop.call_soon_threadsafe(cam_ip_q.put_nowait, _cam_ip)
                loop.call_soon_threadsafe(
                    lambda ip=_cam_ip: _status(f"{method_name}: camera IP = {ip}")
                )
            else:
                _LOGGER.warning(
                    "%s: no IP address field found; inner_keys=%s  msg_keys=%s",
                    method_name, list(inner.keys()), [k for k in msg if k != "payload"],
                )

        def _on_mqtt_message(topic: str, payload_str: str) -> None:
            _LOGGER.debug("webrtc rx  topic=%s  %.400s", topic, payload_str)
            try:
                msg = json.loads(payload_str)
            except Exception:
                loop.call_soon_threadsafe(
                    lambda t=topic, p=payload_str: _status(
                        f"camera raw (non-JSON)  topic={t}  data={p[:200]!r}"
                    )
                )
                return
            method = msg.get("method") or ""
            inner  = msg.get("payload") or {}
            # Fire camera_ready_ev the moment the camera appears on MQTT - either via its
            # explicit wake-ACK (lowPowerActiveStateResp), any message on the device channel,
            # OR any message on the user channel whose payload identifies our device.
            # LK.IPC.A001064 (and similar) responds on the user channel (iot/v1/c/{userId}/…)
            # rather than the device channel, so the old topic-prefix check never fired -
            # causing the 17-second getIceConfigReq timeout overhead every session.
            if (method == "lowPowerActiveStateResp"
                    or topic.startswith(f"iot/v1/c/{device_id}/")
                    or topic.startswith(f"lds/v1/cb/{device_id}/")
                    or inner.get("devId") == device_id
                    or msg.get("devId") == device_id):
                loop.call_soon_threadsafe(camera_ready_ev.set)
            # livePlayResp: explicit camera ack/nack for start-play command.
            # The camera echoes our peer_id (verified live); its payload has NO
            # devId, so the old devId-only match never fired and this future never
            # resolved (the wait always timed out).  Match on the echoed peer_id
            # (per-open, precise); keep devId as a fallback for any camera that
            # does send it.  dstAddr is the account-wide userId - too loose to use.
            if method == "livePlayResp" and (
                    inner.get("peerid") == peer_id
                    or inner.get("devId") == device_id):
                if not liveplay_resp_fut.done():
                    loop.call_soon_threadsafe(liveplay_resp_fut.set_result, inner)
            # livePlayReq echo: broker/camera confirmed delivery of our livePlayReq.
            # Signal _open_sdes_stream to proceed with webrtcReq.
            if method == "livePlayReq" and inner.get("devId") == device_id:
                loop.call_soon_threadsafe(liveplay_echo_ev.set)
            if method == "webrtcResp":
                # GAP D: a terminal ack (-50002 max-streams / -50015 SD-cap) means the
                # camera refused - retrying is futile (mirrors the official app, which
                # shows an error and does NOT retry). Signal it so the connect raises
                # AidotCameraBusy instead of timing out into a generic retry.
                _term = _terminal_webrtc_ack(msg)
                if _term is not None:
                    if not terminal_error_fut.done():
                        loop.call_soon_threadsafe(terminal_error_fut.set_result, _term)
                    return
                resp_pid = inner.get("peerid")
                answer   = inner.get("offer") or inner.get("answer") or {}
                if not answer.get("sdp"):
                    return  # empty / incomplete response - ignore
                if resp_pid == peer_id:
                    pass  # exact peerid match - accept (fast path)
                elif (inner.get("devId") == device_id
                        and inner.get("dstAddr") == user_id):
                    # Camera replied with its own stable session peerid rather than
                    # echoing back our peerid.  Accept only if BOTH devId and dstAddr
                    # match - dstAddr alone matches every message to our account.
                    loop.call_soon_threadsafe(
                        lambda rp=resp_pid: _status(
                            f"webrtcResp accepted (camera peerid) -"
                            f" got {rp!r}"
                            f" expected ...{peer_id[-12:]}"
                        )
                    )
                else:
                    loop.call_soon_threadsafe(
                        lambda rp=resp_pid: _status(
                            f"webrtcResp IGNORED - peerid/devId/dstAddr mismatch:"
                            f" got {rp!r}"
                        )
                    )
                    return
                if not answer_fut.done():
                    loop.call_soon_threadsafe(answer_fut.set_result, answer)
                else:
                    # Second webrtcResp - likely the camera's real answer (the first
                    # was the broker echo of our own webrtcResp).  Capture and log it.
                    _LOGGER.debug(
                        "SDES: second webrtcResp received (answer_fut already set)"
                        " - camera real answer SDP (len=%d)",
                        len(answer.get("sdp", "")),
                    )
                    if not second_answer_fut.done():
                        loop.call_soon_threadsafe(second_answer_fut.set_result, answer)
            elif method == "iceCandidateReq":
                resp_pid = inner.get("peerid")
                # Isolate per camera/stream.  NOTE: dstAddr is the shared account
                # user_id (identical for every camera), so it must NOT gate
                # isolation - including it made the filter never reject, letting a
                # FOREIGN camera's ICE candidates leak into this connection (the
                # cross-contamination / wrong-feed bug).  peer_id is per-stream and
                # devId is per-camera; either is a valid isolation key.
                if resp_pid != peer_id and inner.get("devId") != device_id:
                    return   # candidate for a different camera/session
                cand = inner.get("candidate") or {}
                if cand.get("candidate"):
                    loop.call_soon_threadsafe(ice_q.put_nowait, cand)
            elif method == "webrtcReq":
                # Camera acting as WebRTC offerer (role reversal observed on
                # LK.IPC.A001064).  Set camera_offer_fut so the DTLS path can
                # respond with a proper webrtcResp answer.
                resp_pid   = inner.get("peerid")
                cam_offer  = inner.get("offer") or {}
                src_addr   = inner.get("srcAddr") or msg.get("srcAddr") or ""
                own_prefix = f"0.{user_id}"
                is_echo    = src_addr.startswith(own_prefix) or src_addr == own_prefix
                if is_echo:
                    # Broker echoes our own webrtcReq back with our srcAddr.
                    # Signal the SDES path so it can send webrtcResp to the camera.
                    if not webrtc_req_echo_fut.done():
                        loop.call_soon_threadsafe(
                            webrtc_req_echo_fut.set_result, cam_offer
                        )
                elif (cam_offer.get("sdp")
                        # Same isolation as iceCandidateReq: accept the offer only
                        # if it's for THIS camera/stream.  dstAddr is the shared
                        # account id, so it must not be an accept key (it would
                        # accept every camera's offer).
                        and (resp_pid == peer_id
                             or inner.get("devId") == device_id)):
                    if not camera_offer_fut.done():
                        loop.call_soon_threadsafe(
                            camera_offer_fut.set_result, cam_offer
                        )
                # Extract IceServerList from the camera's webrtcReq and seed
                # ice_config_fut so RTCPeerConnection can use TURN servers.
                # Only process when not an echo - we sent those servers ourselves.
                if not is_echo:
                    _req_ice_list = inner.get("IceServerList") or []
                    if _req_ice_list and not ice_config_fut.done():
                        loop.call_soon_threadsafe(
                            ice_config_fut.set_result,
                            {"IceServerList": _req_ice_list},
                        )
                loop.call_soon_threadsafe(
                    lambda m=method, t=topic: _status(
                        f"camera replied  method={m!r}  endpoint={t.rsplit('/', 1)[-1]}"
                    )
                )
            elif method == "setDevAttrNotif":
                # Camera broadcasts its real LAN IP shortly after ICE starts.
                # Capture it so the role-reversal path can inject synthetic
                # remote candidates (camera-IP + echoed ports) to give aiortc
                # a concrete target for STUN probes when the camera is ICE-lite
                # and won't send its own binding requests.
                _extract_cam_ip("setDevAttrNotif", inner, msg)
                # Also capture sptPreconn (PreCon / PreConnect support flag).
                # Confirmed 2026-05-02: both A001064 PTZ and A000088 have
                # sptPreconn:1.  Per BaseKVSCameraView.k():805-815, the official
                # client sends AVIO LIVING (SESSION_MODE_REQ=5376) via the data
                # channel only when isSupportPreCon() is true.  Set the flag
                # here so the DC on("open") callback can send LIVING.
                _spt = inner.get("attr", {}).get("sptPreconn", 0)
                if _spt:
                    loop.call_soon_threadsafe(
                        lambda: _spt_preconn.__setitem__(0, True)
                    )
            elif method == "getDevAttrResp":
                # Defensive: some camera firmware pushes getDevAttrResp rather
                # than setDevAttrNotif.  Extract the LAN IP if present.
                _extract_cam_ip("getDevAttrResp", inner, msg)
            elif method == "getIceConfigResp":
                # Capture TURN server credentials so aiortc can use them.
                # getIceConfigResp arrives before RTCPeerConnection is created,
                # so we store it in ice_config_fut and consumed later when
                # building the RTCPeerConnection configuration.
                #
                # Accept any non-empty response regardless of key names -
                # the actual structure is normalised in the extraction code
                # below.  Previously this block silently dropped responses
                # whose payload didn't have top-level "app"/"dev" keys,
                # which prevented TURN credentials from reaching aiortc.
                _ice_inner = inner if isinstance(inner, dict) else {}
                if not _ice_inner and isinstance(msg.get("data"), dict):
                    _ice_inner = msg["data"]
                # Accept anything that looks like ICE config data.
                _has_known_keys = ("app" in _ice_inner or "dev" in _ice_inner
                                   or "iceServers" in _ice_inner
                                   or "turnServers" in _ice_inner)
                if not _has_known_keys and _ice_inner:
                    # Log the actual structure so we can learn the format.
                    _LOGGER.warning(
                        "getIceConfigResp: unexpected structure (no app/dev/iceServers)"
                        " - storing raw for extraction attempt.  keys=%s",
                        list(_ice_inner.keys())[:20],
                    )
                if _ice_inner and not ice_config_fut.done():
                    loop.call_soon_threadsafe(ice_config_fut.set_result, _ice_inner)
                    # getIceConfigResp arriving means the broker session is
                    # active and TURN credentials are available.  Wake the
                    # camera_ready_ev so we don't burn the full 12-second
                    # timeout when the server responds promptly.
                    loop.call_soon_threadsafe(camera_ready_ev.set)
                elif not _ice_inner and not ice_config_fut.done():
                    # No usable payload - store the whole message as fallback.
                    _LOGGER.warning(
                        "getIceConfigResp: empty inner payload; storing full msg."
                        " msg_keys=%s", list(msg.keys())
                    )
                    if msg and not ice_config_fut.done():
                        loop.call_soon_threadsafe(ice_config_fut.set_result, msg)
            elif method == "connect":
                # Camera re-connected (quickConn cycle after WebRTC signaling).
                # Signal the ICE wait loop so it can re-send webrtcResp + ICE
                # candidates - the camera may have reset its WebRTC session state
                # and needs fresh signaling to continue ICE connectivity checks.
                if inner.get("devId") == device_id:
                    loop.call_soon_threadsafe(camera_reconnect_ev.set)
                loop.call_soon_threadsafe(
                    lambda m=method, t=topic: _status(
                        f"camera replied  method={m!r}  endpoint={t.rsplit('/', 1)[-1]}"
                    )
                )
            else:
                loop.call_soon_threadsafe(
                    lambda m=method, t=topic: _status(
                        f"camera replied  method={m!r}  endpoint={t.rsplit('/', 1)[-1]}"
                    )
                )

        # MQTT transport for the stream signaling.  Default: a dedicated
        # connect-per-stream session in an executor (stopped via outgoing_q
        # sentinel on WebRTCSession.stop()).  AIDOT_PERSISTENT_MQTT (Phase 2):
        # ride the SAME account-level persistent connection commands/attrs use
        # (the stream's mqtt_cid IS the authorized mqttClientId, so it's the same
        # connection) - subscribe + register a handler + drain outgoing_q through
        # it, and DON'T tear the connection down on stop (matching the app).
        # A prior open may have left a drain running (e.g. a reopen with no
        # async_stop_streaming between them); reap it before starting a new one
        # so we don't orphan its executor thread + handler on the shared conn.
        await self._reap_stream_drain()
        _pm_stream = (await self._get_persistent_mqtt()
                      if self._resolve_persistent_mqtt() else None)
        if _pm_stream is not None:
            await _pm_stream.subscribe(sub_topics)
            _pm_stream.add_handler(_on_mqtt_message)

            async def _pm_stream_drain():
                try:
                    while True:
                        out = await loop.run_in_executor(None, outgoing_q.get)
                        if out is None:   # stop sentinel from WebRTCSession.stop()
                            return
                        await _pm_stream.publish(out[0], out[1])
                finally:
                    _pm_stream.remove_handler(_on_mqtt_message)

            mqtt_fut = asyncio.ensure_future(_pm_stream_drain())
            # Track the drain AND its queue so teardown (or a replacing open) can
            # reap it even if this open is cancelled before a WebRTCSession takes
            # ownership.  _reap_stream_drain pushes the outgoing_q sentinel to
            # release the thread blocked on outgoing_q.get and removes the handler
            # from the shared persistent connection.
            self._stream_mqtt_drain = mqtt_fut
            self._stream_mqtt_outq = outgoing_q
            _on_mqtt_ready({"connected": True, "rc": 0, "rc_str": "persistent"})
        else:
            mqtt_fut = loop.run_in_executor(
                None,
                lambda: _mqtt_session_sync(
                    mqtt_url, mqtt_user, mqtt_pwd, mqtt_cid,
                    sub_topics, [], 3600.0, _on_mqtt_message,
                    "/mqtt", _on_mqtt_ready, outgoing_q,
                ),
            )

        # Wait for MQTT to be connected and subscribed before proceeding.
        # threading.Event.wait(timeout) returns True if set, False on timeout.
        mqtt_ok = await loop.run_in_executor(
            None, lambda: _mqtt_ready_ev.wait(timeout=15.0)
        )
        if not mqtt_ok or not _mqtt_conn_status.get("connected"):
            outgoing_q.put_nowait(None)   # stop MQTT thread
            _err = (
                _mqtt_conn_status.get("error")
                or _mqtt_conn_status.get("rc_str")
                or f"rc={_mqtt_conn_status.get('rc')}"
            )
            raise RuntimeError(
                f"async_open_webrtc_stream: MQTT connection failed: {_err}"
            )
        _status(f"MQTT connected (clientId={mqtt_cid})")

        # ------------------------------------------------------------------ #
        # Announce user presence (l.java P()) so the camera pushes its current
        # state via setDevAttrNotif.  The setDevAttrNotif handler enqueues
        # the camera's LAN IP into cam_ip_q so the ICE wait loop can
        # synthesise a host candidate for direct LAN probing.
        # Replaces the invented getDevAttrReq which does not exist in the APK.
        # ------------------------------------------------------------------ #
        outgoing_q.put_nowait((
            f"iot/v1/cb/{user_id}/user/connect",
            json.dumps({
                "service": "user",
                "method":  "connect",
                "srcAddr": f"0.{user_id}",
                "payload": {"timestamp": _mqtt_timestamp()},
            }),
        ))

        # ------------------------------------------------------------------ #
        # Send getIceConfigReq first - this warms up the broker-side WebRTC
        # session and registers the camera routing so the subsequent webrtcReq
        # is forwarded to the device.  iOS app telemetry confirms getIceConfigReq
        # is always sent before webrtcReq.  Without this step the broker echoes
        # webrtcReq back to us but never routes it to the camera.
        #
        # Skipped on SDES→DTLS fallback retries (_skip_ice_config=True): the
        # camera is already awake from the SDES attempt so the wake phase is
        # unnecessary; skipping saves up to 17 s of timeout overhead.
        # ------------------------------------------------------------------ #
        # Build getIceConfigReq payload unconditionally - used in both the
        # normal wake-wait path and the DTLS-fallback no-wait path below.
        _ice_req_payload = json.dumps({
            "method":  "getIceConfigReq",
            "service": "IPC",
            "devId":   device_id,
            "srcAddr": f"0.{user_id}",
            "seq":     f"ap{random.randint(1000000, 9999999)}",
            "tst":     int(time.time() * 1000),
            **( {"userId": _numeric_uid_raw} if _numeric_uid_raw is not None else {} ),
            "payload": {"devId": device_id, "userId": user_id},
        })

        if _skip_ice_config:
            camera_ready_ev.set()  # camera already awake; skip wake handshake
            _status("Skipping getIceConfigReq wake wait (DTLS fallback - camera already awake)")
            # Still send getIceConfigReq so TURN credentials can be gathered
            # asynchronously.  The server may respond now that the camera already
            # has an active MQTT session from the preceding SDES attempt.
            outgoing_q.put_nowait(
                (f"iot/v1/s/{user_id}/IPC/getIceConfigReq", _ice_req_payload)
            )
            # Seed ice_config_fut from pre-loaded config (caller-supplied or
            # HTTP pre-fetch) so RTCPeerConnection picks up TURN servers.
            _effective_ice = _ice_config or _http_ice_config
            if _effective_ice is not None and not ice_config_fut.done():
                loop.call_soon_threadsafe(ice_config_fut.set_result, _effective_ice)
        else:
            # Seed ice_config_fut from HTTP pre-fetch if available, so TURN
            # credentials are ready before the camera even wakes.
            _effective_ice = _ice_config or _http_ice_config
            if _effective_ice is not None and not ice_config_fut.done():
                loop.call_soon_threadsafe(ice_config_fut.set_result, _effective_ice)

            # Wake the camera via two parallel channels (n.java DeviceWakeUpRepos k()+l()):
            #   1. MQTT lowPowerActiveStateReq - reaches cameras already on MQTT
            #   2. HTTP lowPowerActiveState    - cloud push for deep-sleep cameras
            # Both are fire-and-forget; getIceConfigReq below confirms wakeup.
            # Payload from n.java l() - publishes extendsObj directly (no outer wrapper).
            _wake_mqtt_payload = json.dumps({
                "method":  "lowPowerActiveStateReq",
                "devId":   device_id,
                "userId":  user_id,
                "service": "IPC",
                "payload": {
                    "devId":  device_id,
                    "status": "wakeup",
                },
            })
            outgoing_q.put_nowait((
                f"iot/v1/s/{user_id}/IPCAM/lowPowerActiveStateReq",
                _wake_mqtt_payload,
            ))

            async def _http_wake() -> None:
                try:
                    import aiohttp as _aiohttp_w
                    async with _aiohttp_w.ClientSession() as _ws:
                        async with _ws.post(
                            f"{self._aidot_v32_base}/devices/{device_id}"
                            "/lowPowerActiveState",
                            json={"deviceId": device_id, "status": "wakeup"},
                            headers=self._aidot_headers(),
                            timeout=_aiohttp_w.ClientTimeout(total=8),
                        ) as _wr:
                            _LOGGER.debug(
                                "lowPowerActiveState HTTP %d for %s",
                                _wr.status, device_id,
                            )
                except Exception as _we:
                    _LOGGER.debug(
                        "lowPowerActiveState HTTP failed for %s: %s", device_id, _we
                    )

            async def _http_keepalive() -> None:
                # setKeepAliveTime keeps the camera in active state for 25 s
                # after wake (n.java:224 - keepAliveTime=25, not 20).
                # Without this call the camera's built-in timer may return it
                # to sleep before SCTP + LIVING completes (~10-15 s).
                try:
                    import aiohttp as _aiohttp_k
                    async with _aiohttp_k.ClientSession() as _ks:
                        async with _ks.post(
                            f"{self._aidot_v32_base}/devices/{device_id}"
                            "/setKeepAliveTime",
                            json={"keepAliveTime": 25},
                            headers=self._aidot_headers(),
                            timeout=_aiohttp_k.ClientTimeout(total=8),
                        ) as _kr:
                            _LOGGER.debug(
                                "setKeepAliveTime HTTP %d for %s",
                                _kr.status, device_id,
                            )
                except Exception as _ke:
                    _LOGGER.debug(
                        "setKeepAliveTime HTTP failed for %s: %s", device_id, _ke
                    )

            _spawn_bg(_http_wake())
            _spawn_bg(_http_keepalive())

            outgoing_q.put_nowait(
                (f"iot/v1/s/{user_id}/IPC/getIceConfigReq", _ice_req_payload)
            )
            _status("getIceConfigReq sent - waiting for camera to wake (up to 12s)")
            try:
                await asyncio.wait_for(camera_ready_ev.wait(), timeout=12.0)
                _status("Camera awake - got MQTT signal")
            except TimeoutError:
                # First 12s window elapsed without a camera MQTT signal.
                # Resend both the wake signal and ICE config req, then give
                # a shorter 5s window before proceeding regardless - cameras
                # either wake quickly or take >24s, so the extra wait rarely
                # catches the slow case but always costs time for the fast one.
                _status("Camera wake timeout - retrying ...")
                outgoing_q.put_nowait((
                    f"iot/v1/s/{user_id}/IPCAM/lowPowerActiveStateReq",
                    _wake_mqtt_payload,
                ))
                outgoing_q.put_nowait(
                    (f"iot/v1/s/{user_id}/IPC/getIceConfigReq", _ice_req_payload)
                )
                _spawn_bg(_http_wake())
                try:
                    await asyncio.wait_for(camera_ready_ev.wait(), timeout=5.0)
                    _status("Camera awake - got MQTT signal (after retry)")
                except TimeoutError:
                    _status("Camera not responding - proceeding anyway")
                    if use_sdes:
                        _status(
                            "Note: getIceConfigReq timed out for claimed-SDES camera"
                            " - will fall back to DTLS if SDES handshake fails"
                        )

        # ------------------------------------------------------------------ #
        # powerType / p2pCache - source: IpcServiceImpl.java:B()
        # B() returns 2 for battery/low-power models (A001513, A001108, A001360)
        # and 1 for everything else.  p2pCache is the camera's prop value
        # (all tested cameras report p2pCache=2).  Both fields appear in
        # livePlayReq, webrtcReq, and the SDES webrtcReq.
        _live_model_id = getattr(getattr(self, "info", None), "model_id", None) or ""
        # App parity: LivePlayPaylodBean declares powerType/p2pCache as INT (not
        # String) - send ints so a strict camera JSON parser accepts livePlayReq.
        _live_power_type = 2 if any(
            m in _live_model_id for m in ("A001513", "A001108", "A001360")
        ) else 1
        _live_p2p_cache = 2  # All cameras report p2pCache:2 in device attrs

        # ------------------------------------------------------------------ #
        # Send livePlayReq BEFORE the SDP offer.  iOS app telemetry confirms
        # this message is always sent first to arm the camera's WebRTC
        # subsystem; the camera silently ignores webrtcReq without it.
        # ------------------------------------------------------------------ #
        _live_req_payload = json.dumps({
            "method":  "livePlayReq",
            "service": "IPC",
            "devId":   device_id,
            "srcAddr": f"0.{user_id}",
            "seq":     f"ap{random.randint(1000000, 9999999)}",
            "tst":     int(time.time() * 1000),
            **( {"userId": _numeric_uid_raw} if _numeric_uid_raw is not None else {} ),
            "payload": {
                "peerid":  peer_id,
                "devId":   device_id,
                # Decompiled reference app (tyrus/o.java) sets payload.dstAddr
                # to the target deviceId for livePlayReq.
                "dstAddr": device_id,
                # App payload compatibility fields (see LiveRequestParamsBean /
                # LivePlayPaylodBean in decompiled app).
                "livePlay": 1,
                "powerType": _live_power_type,
                "p2pCache": _live_p2p_cache,
                "dseq": self._next_dseq(),
            },
        })
        if not use_sdes:
            # SDES path sends its own livePlayReq inside _open_sdes_stream;
            # only send here for the DTLS path to avoid a duplicate.
            outgoing_q.put_nowait((live_play_topic, _live_req_payload))
            _status(f"livePlayReq sent  peerid={peer_id}")
            # Wait for the broker to echo livePlayReq back (confirms delivery).
            # Proceed as soon as the echo arrives; fall through after 0.5 s.
            # AIDOT_FAST_CONNECT skips this too (the echo often just times out,
            # costing a flat 0.5 s); we proceed straight to SDP/ICE.
            if not _fast_connect:
                try:
                    await asyncio.wait_for(liveplay_echo_ev.wait(), timeout=0.5)
                except TimeoutError:
                    pass
            # livePlayResp carries camera-side accept/reject.  If the camera
            # rejects start-play, continuing to SDP/ICE causes large STUN churn
            # but no media.  Fail fast with the concrete code.
            # MEASURED (2026-06-07, live A/B): this up-to-2 s wait is the DOMINANT
            # cold-start cost (camera-awake → ICE-servers ≈ 2.5 s = 0.5 s echo +
            # 2.0 s here).  The official app does NOT wait for/parse livePlayResp
            # (parity-confirmed).  AIDOT_FAST_CONNECT skips it and proceeds to
            # SDP/ICE immediately - losing fast-fail on rejection (rare; ICE then
            # fails instead) in exchange for ~2 s off every LAN connect.
            if _fast_connect:
                _LOGGER.info(
                    "signaling-wait[%s] livePlayResp skipped (fast_connect)",
                    self.device_id)
                _status(
                    "AIDOT_FAST_CONNECT: skipping livePlayResp wait (~2s) -"
                    " proceeding to SDP/ICE (app-parity, no fast-fail on reject)"
                )
            else:
                # Instrumented: how long this wait actually costs (it returns as
                # soon as livePlayResp arrives; the 2s is only paid on timeout).
                # This is exactly the cost the sdes_fast_liveplay experiment skips.
                _lp_t0 = time.monotonic()
                _lp_arrived = False
                try:
                    _lp_resp = await asyncio.wait_for(
                        asyncio.shield(liveplay_resp_fut), timeout=2.0
                    )
                    _lp_arrived = True
                    _lp_code = int(_lp_resp.get("code", 200))
                    _lp_on = int(_lp_resp.get("livePlay", 1))
                    # Fast-fail only on an unambiguous refusal (livePlay=0).
                    # Other non-OK codes (incl. -50019 "not ready") are
                    # transient on battery cameras and recover via ICE; abort
                    # there would spuriously kill otherwise-good streams.
                    if _lp_on == 0:
                        raise RuntimeError(
                            f"livePlay refused by camera (livePlay=0, code={_lp_code})"
                        )
                    elif _lp_code not in (0, 200):
                        _status(
                            f"livePlayResp: non-OK code {_lp_code}"
                            f"{' (not ready, transient)' if _lp_code == -50019 else ''}"
                            " - proceeding"
                        )
                except TimeoutError:
                    pass
                _LOGGER.info(
                    "signaling-wait[%s] livePlayResp elapsed=%dms arrived=%s",
                    self.device_id,
                    int((time.monotonic() - _lp_t0) * 1000), _lp_arrived)
            # Short extra wait for getIceConfigResp - the server may only respond
            # once a live camera session is active (i.e. after livePlayReq).
            # Waiting here ensures TURN credentials arrive before RTCPeerConnection
            # is created.  MEASURED (2026-06-07, live): this wait is the DOMINANT
            # cold-start cost - ~2.5 s for getIceConfigResp to arrive after camera
            # wake (NOT the TURN gather, which is ~70 ms).  AIDOT_FAST_CONNECT
            # skips it: we proceed immediately on STUN/host candidates (no relay),
            # so a LAN camera connects in ~1 s.  getIceConfigResp only supplies the
            # per-session TURN relay credentials, which LAN-direct doesn't need;
            # remote/strict-NAT cameras do, hence this is opt-in (see flag above).
            if not ice_config_fut.done() and not _fast_connect:
                _ic_t0 = time.monotonic()
                _ic_arrived = False
                try:
                    await asyncio.wait_for(asyncio.shield(ice_config_fut), timeout=3.0)
                    _ic_arrived = True
                    _status("getIceConfigResp received (post-livePlayReq)")
                except TimeoutError:
                    pass  # proceed without TURN; synthetic candidates are fallback
                _LOGGER.info(
                    "signaling-wait[%s] getIceConfigResp elapsed=%dms arrived=%s",
                    self.device_id,
                    int((time.monotonic() - _ic_t0) * 1000), _ic_arrived)
            elif _fast_connect:
                _status(
                    "AIDOT_FAST_CONNECT: skipping getIceConfigResp wait"
                    " (~2.5s) - STUN/host candidates only, LAN-direct"
                )
            # Second livePlayResp check: catches late rejections that arrived
            # while we were waiting for ice_config (fast-ICE + slow-reject path).
            if not _fast_connect and liveplay_resp_fut.done():
                try:
                    _lp_resp2 = liveplay_resp_fut.result()
                    _lp_code2 = int(_lp_resp2.get("code", 200))
                    _lp_on2   = int(_lp_resp2.get("livePlay", 1))
                    if _lp_on2 == 0:
                        raise RuntimeError(
                            f"livePlay refused by camera (livePlay=0, code={_lp_code2})"
                        )
                    elif _lp_code2 not in (0, 200):
                        _status(
                            f"livePlayResp: non-OK code {_lp_code2}"
                            f"{' (not ready, transient)' if _lp_code2 == -50019 else ''}"
                            " - proceeding"
                        )
                except RuntimeError:
                    raise
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception", '_http_keepalive', exc_info=True)

        # ------------------------------------------------------------------ #
        # Branch: SDES-SRTP cameras use ffmpeg; DTLS cameras use aiortc
        # ------------------------------------------------------------------ #
        if use_sdes:
            try:
                return await self._open_sdes_stream(
                    peer_id=peer_id,
                    user_id=user_id,
                    device_id=device_id,
                    outgoing_q=outgoing_q,
                    answer_fut=answer_fut,
                    camera_offer_fut=camera_offer_fut,
                    webrtc_req_echo_fut=webrtc_req_echo_fut,
                    loop=loop,
                    timeout=timeout,
                    output_path=output_path,
                    max_seconds=max_seconds,
                    _status=_status,
                    mqtt_fut=mqtt_fut,
                    liveplay_echo_ev=liveplay_echo_ev,
                    liveplay_resp_fut=liveplay_resp_fut,
                    numeric_uid_raw=_numeric_uid_raw,
                    dtls_fallback_ok=_dtls_fallback_ok,
                    second_answer_fut=second_answer_fut,
                    ice_config=(
                        ice_config_fut.result() if ice_config_fut.done()
                        else _http_ice_config
                    ),
                    camera_reconnect_ev=camera_reconnect_ev,
                    sdes_answer_timeout=_sdes_answer_timeout,
                    rtsp_push_url=rtsp_push_url,
                    talk=talk,
                )
            except CameraMixin._SdesNoAnswerError:
                # Camera reported enableSdes='1' but did not respond to our SDES
                # offer.  iOS telemetry shows models such as LK.IPC.A001064 can
                # have an incorrectly set enableSdes property while actually
                # requiring DTLS (peerid suffix _2).  The MQTT session was already
                # told to close (None sentinel sent to outgoing_q inside
                # _open_sdes_stream); wait for the thread to finish before opening
                # a new MQTT connection with the same clientId.
                _status(
                    "SDES handshake failed - camera may be DTLS despite"
                    " enableSdes='1'.  Retrying with DTLS (aiortc) ..."
                )
                try:
                    await asyncio.wait_for(mqtt_fut, timeout=5.0)
                except Exception:
                    pass   # MQTT thread exit errors don't affect the retry
                return await self.async_open_webrtc_stream(
                    on_frame,
                    stream_id=stream_id,
                    timeout=timeout,
                    output_path=output_path,
                    status_callback=status_callback,
                    force_sdes=False,
                    _skip_ice_config=True,
                    _ice_config=(
                        ice_config_fut.result() if ice_config_fut.done() else None
                    ),
                )

        # ------------------------------------------------------------------ #
        # aiortc peer connection (DTLS-SRTP path)
        # ------------------------------------------------------------------ #
        import logging as _logging_dtls
        _logging_dtls.getLogger("aioice").setLevel(_logging_dtls.DEBUG)
        from aiortc import RTCConfiguration, RTCIceServer

        def _sanitize_ice_uris(uris):
            """Remove ?transport= from stun: URIs - invalid per RFC 7064."""
            result = []
            for u in uris:
                if isinstance(u, str) and u.startswith("stun:") and "?" in u:
                    u = u.split("?")[0]
                result.append(u)
            return result

        _ice_servers = [RTCIceServer(urls=["stun:stun.l.google.com:19302"])]
        if ice_config_fut.done():
            try:
                _ice_data = ice_config_fut.result()
                # Normalise multiple possible response shapes:
                #   Arnoo format:   {app: [{uris, id, token}, ...], dev: [...]}
                #   Standard W3C:  {iceServers: [{urls, username, credential}, ...]}
                #   Nested:        {data: {app: [...], ...}} or {data: {iceServers: [...]}}
                #   Wrapped:       {payload: {app: [...], ...}} or {code, data: {...}}
                def _unwrap(d):
                    """Recursively unwrap common envelope keys to reach the list."""
                    if not isinstance(d, dict):
                        return d
                    for _k in ("data", "payload", "result"):
                        if _k in d and isinstance(d[_k], dict):
                            inner = d[_k]
                            if any(k in inner for k in
                                   ("app", "dev", "iceServers", "turnServers")):
                                return inner
                            # one more level
                            unwrapped = _unwrap(inner)
                            if unwrapped is not inner:
                                return unwrapped
                    return d
                _ice_data = _unwrap(_ice_data)

                # Arnoo app/dev lists: [{uris/Uris/dnsUris, id/Username, token/Password}, ...]
                # app = user-level TURN entries; dev = per-device TURN entries.
                # The camera needs its device-specific credential (dev entry, Username=deviceId)
                # to allocate a TURN relay when direct / srflx connectivity is unavailable.
                # Both sections must be extracted and included in the webrtcReq IceServerList
                # so the camera receives those credentials and can initiate ICE via TURN.
                for _sect in ("app", "dev"):
                    for _entry in (_ice_data.get(_sect) or []):
                        _uris = _sanitize_ice_uris(
                            _entry.get("uris") or _entry.get("Uris")
                            or _entry.get("dnsUris") or []
                        )
                        _uid  = str(_entry.get("id") or _entry.get("Username")
                                    or _entry.get("username") or "")
                        _cred = str(_entry.get("token") or _entry.get("Password")
                                    or _entry.get("credential") or "")
                        if _uris:
                            _ice_servers.append(
                                RTCIceServer(urls=_uris, username=_uid, credential=_cred)
                            )
                # Standard W3C / plain iceServers list
                for _entry in (_ice_data.get("iceServers")
                               or _ice_data.get("turnServers") or []):
                    _uris = (_entry.get("urls") or _entry.get("uris")
                             or _entry.get("dnsUris") or [])
                    if isinstance(_uris, str):
                        _uris = [_uris]
                    _uris = _sanitize_ice_uris(_uris)
                    _uid  = str(_entry.get("username") or _entry.get("id") or "")
                    _cred = str(_entry.get("credential") or _entry.get("token") or "")
                    if _uris:
                        _ice_servers.append(
                            RTCIceServer(urls=_uris, username=_uid, credential=_cred)
                        )
                # Camera IceServerList format (from webrtcReq echo):
                # [{Uris: ['stun:...', 'turn:...'], id: '...', token: '...'}]
                # Uses capital 'Uris' key and may carry TURN credentials.
                for _entry in (_ice_data.get("IceServerList") or []):
                    _uris = (_entry.get("Uris") or _entry.get("uris")
                             or _entry.get("dnsUris") or [])
                    if isinstance(_uris, str):
                        _uris = [_uris]
                    _uris = _sanitize_ice_uris(_uris)
                    _uid  = str(_entry.get("id") or _entry.get("username")
                               or _entry.get("Username") or "")
                    _cred = str(_entry.get("token") or _entry.get("credential")
                                or _entry.get("Password") or "")
                    if _uris:
                        _ice_servers.append(
                            RTCIceServer(urls=_uris, username=_uid, credential=_cred)
                        )
                _has_turn_in_resp = any(
                    any(u.startswith(("turn:", "turns:"))
                        for u in (srv.urls if isinstance(srv.urls, list) else [srv.urls]))
                    for srv in _ice_servers[1:]
                )
                if len(_ice_servers) > 1:
                    _status(
                        f"ICE config from getIceConfigResp"
                        f" ({'TURN' if _has_turn_in_resp else 'STUN-only'}):"
                        f" {[s.urls for s in _ice_servers[1:]]}"
                    )
                else:
                    _LOGGER.warning(
                        "getIceConfigResp received but no ICE servers extracted."
                        " ice_data keys=%s", list(_ice_data.keys())[:20]
                    )
            except Exception as _ice_exc:
                _LOGGER.warning(
                    "Failed to parse getIceConfigResp ICE config: %s", _ice_exc
                )
                _has_turn_in_resp = False
        else:
            _has_turn_in_resp = False
        # Always ensure a TURN relay is available.  getIceConfigResp often returns
        # only STUN (e.g. when ice_config_fut is seeded from the camera's webrtcReq
        # echo which carries only Google STUN).  Without relay, ICE fails for cameras
        # that are on a different network segment or behind strict NAT.  The Arnoo
        # TURN server (confirmed from browser webrtc_internals - ALL candidates are
        # relay type) accepts connections without explicit per-session credentials.
        _arnoo_stun = "stun:3.230.182.123:3478"
        _arnoo_turn = "turn:3.230.182.123:5349"
        if not _has_turn_in_resp:
            _ice_servers.append(RTCIceServer(urls=[_arnoo_stun, _arnoo_turn]))
        # Log the final ICE server configuration.
        _stun_url = (_ice_servers[0].urls[0]
                     if _ice_servers and _ice_servers[0].urls
                     else "none")
        _turn_entries = [s.urls for s in _ice_servers[1:]]
        _status(
            f"ICE servers: STUN={_stun_url}"
            f"  relay×{len(_turn_entries)}: {_turn_entries}"
        )
        if _fast_connect:
            # Strip TURN URIs so aiortc's setLocalDescription doesn't block on a
            # TURN Allocate round-trip during ICE gathering - the LAN host
            # candidate then connects in ~1 s.  Keep STUN (cheap, no allocate).
            _stun_only = []
            for _srv in _ice_servers:
                _su = [
                    u for u in (_srv.urls if isinstance(_srv.urls, list) else [_srv.urls])
                    if not str(u).startswith(("turn:", "turns:"))
                ]
                if _su:
                    _stun_only.append(
                        RTCIceServer(urls=_su, username=_srv.username,
                                     credential=_srv.credential)
                    )
            _ice_servers = _stun_only or [
                RTCIceServer(urls=["stun:stun.l.google.com:19302"])
            ]
            _status(
                "AIDOT_FAST_CONNECT: stripped TURN (STUN-only, LAN-direct) -"
                f" {len(_ice_servers)} ICE server(s), no relay gather stall"
            )
        pc = RTCPeerConnection(
            configuration=RTCConfiguration(iceServers=_ice_servers)
        )
        # Audio: sendrecv WITHOUT a real audio sender.  Empirical findings
        # from 2026-04-26 testing (commits aa341a1b, aeaea893):
        #
        #   recvonly             → camera tears down at ~22s after SCTP up
        #                          (REMOTE-initiated DTLS Alert)
        #   sendrecv, no track   → camera patient-waits indefinitely
        #                          (no remote tear-down within test window)
        #   sendrecv + silent
        #     PCMA RTP track     → camera tears down at ~24s after SCTP up
        #                          (REMOTE-initiated DTLS Alert)
        #
        # Sendrecv flips a "viewer is a real client" gate in the camera's
        # KVS-WebRTC firmware (verified against reference offer in
        # research/webrtc_internals_dump.json:5433 which also uses sendrecv).
        # But actually emitting audio RTP from our side regresses the
        # camera - the firmware appears to dislike receiving viewer audio
        # in some way (possibly: codec mismatch, SSRC routing bug, or
        # firmware bug processing inbound audio).  Camera answer keeps
        # sendrecv on its mid:0 audio (line 327 of test log) so it's not
        # a direction-mismatch issue at the SDP layer.
        #
        # No sender tracks - camera is sendonly (a=sendonly in its answer SDP).
        # sendrecv on audio prevents direction-mismatch errors in the answer.
        #
        # Two-way audio (talk): when a talk_pcm_provider is supplied we attach a
        # real PCMA sender track from the start, mirroring the official app
        # (f0.java:695 createAudioTrack + addTrack, enabled only during talk).
        # The track emits silence until the provider returns PCM, so the camera
        # sees a genuine, continuously-present sender - the APK keeps the track
        # present the whole session, so track presence is NOT the ~24s-teardown
        # cause.  Audio is enabled on the wire by also sending AVIO SPEAKERSTART
        # (848) once the DataChannel opens (see _send_avio_speaker below).
        # Audio mid:0 - ALWAYS a sendrecv transceiver (not addTrack), so we keep the
        # RTCRtpSender and can toggle the talk track on/off at runtime via replaceTrack.
        # That is the app's setEnabled(false) equivalent: present-but-idle = no RTP, so
        # no ~24s silence-RTP teardown and no renegotiation. The talk track reads from a
        # mutable holder so async_start_talk()/async_stop_talk() control it on a LIVE
        # session. If talk_pcm_provider is supplied at open we attach immediately
        # (backward compatible; SPEAKERSTART then fires on DC open).
        _audio_tcvr   = pc.addTransceiver("audio", direction="sendrecv")  # mid:0
        _audio_sender = getattr(_audio_tcvr, "sender", None)
        _talk_holder  = {"provider": talk_pcm_provider}
        _talk_track   = _make_talk_audio_track(
            lambda: (_talk_holder["provider"]() if _talk_holder["provider"] else None)
        )
        if talk_pcm_provider is not None and _talk_track is not None and _audio_sender is not None:
            _audio_sender.replaceTrack(_talk_track)
            _status("talk: attached PCMA sender track (two-way audio enabled)")
        pc.addTransceiver("video", direction="recvonly")   # mid:1  H264 video
        # Wire capture (2026-05-02) of an official iOS Aidot session against
        # A000088 showed the camera answers with FOUR BUNDLE'd m-sections:
        #   mid:0  audio      sendrecv  PCMA/8000
        #   mid:1  H264 video sendonly  PT 127 + RTX 124
        #   mid:2  H265 video sendonly  PT 98  + RTX 99   ← separate section!
        #   mid:3  datachannel sctp-port:5000
        # Our old 3-section offer (audio/video/DC at mids 0/1/2) caused the
        # camera's H265 answer on mid:2 to collide with our DC declaration
        # (application ≠ video), so the answer-rebuild stubbed it as DC and
        # discarded H265 entirely.  The camera never got confirmation that
        # video negotiation succeeded and sent nothing on mid:1 either.
        # H265 mid:2 is INJECTED as a synthetic SDP text section via
        # _inject_h265_section() in the offer pipeline - NO aiortc transceiver
        # is created for it.  This is intentional: aiortc does not have H265
        # in its codec registry and raises OperationError on any stub we give
        # it for a H265 transceiver.  The camera sees a proper 4-section offer
        # (audio/H264/H265/DC) from the sent SDP; aiortc only manages the
        # 3 sections it knows about (audio mid:0 / H264 mid:1 / DC mid:2).
        # Before calling setRemoteDescription, the answer is stripped of the
        # H265 stub and DC is renumbered from mid:3 → mid:2.
        # SCTP data channel - KVS firmware ALWAYS opens SCTP regardless of
        # whether m=application is in the negotiated answer.  Decoded the
        # post-handshake 0x17 records as SCTP INIT chunks (srcPort=5000
        # dstPort=5000 verifTag=0 chunkType=0x01).  Camera retransmits INIT
        # at ~6s intervals and tears DTLS down with close_notify after ~22s
        # if it never receives INIT-ACK.  Adding the data channel here
        # creates an SCTP endpoint on our side that answers the INIT.
        # Without the H265 transceiver, DC stays at mid:2 in aiortc's eyes.
        # Skipped only for A001064 (see _NO_DATACHANNEL_MODELS).
        # Helper: build the 36-byte AVIO LIVING TUTK frame and send via dc.
        # Extracted so it can be invoked from either dc.on("open") (we created
        # the channel) or pc.on("datachannel") (camera initiated DCEP).
        def _send_avio_living(_dc_ref, _label_for_log: str) -> None:
            try:
                _seq = random.randint(0, 0x7fffffff)
                _ts_ms = int(time.time() * 1000)
                _payload = struct.pack("<IB3x", 0, 1)  # channel=0, mode=LIVING
                _hdr = struct.pack(
                    "<IIqII4x",
                    _seq, 5376, _ts_ms, len(_payload), 0,
                )
                _frame = _hdr + _payload
                _dc_ref.send(_frame)
                _status(
                    f"DC[{_label_for_log}] OPEN → sent AVIO LIVING (5376)"
                    f" {len(_frame)}B seq=0x{_seq:08x}"
                )
            except Exception as _dc_send_exc:
                _status(
                    f"DC[{_label_for_log}] LIVING send failed: {_dc_send_exc}"
                )

        def _send_avio_heartbeat(_dc_ref) -> None:
            # CMD_AVIO_CTRL_HEARTHEAT_REQ = 5156 (AVIOCTRLDEFs.java:119).
            # Official app sends this every 10 s (f0.java:2991 timer period
            # 10000 ms) to prevent the camera's 22-second watchdog from
            # tearing down the DTLS session.  Empty payload, header only.
            try:
                _seq = random.randint(0, 0x7fffffff)
                _ts_ms = int(time.time() * 1000)
                _hdr = struct.pack("<IIqII4x", _seq, 5156, _ts_ms, 0, 0)
                _dc_ref.send(_hdr)
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception", '_send_avio_heartbeat', exc_info=True)

        def _send_avio_audiostart(_dc_ref) -> None:
            # IOTYPE_USER_IPCAM_AUDIOSTART = 768 (AVIOCTRLDEFs.java:154).
            # The camera negotiates audio as sendrecv.  aiortc's RTCRtpSender
            # sends RTCP SR with packet_count=0 (no track), which tells the
            # camera we said sendrecv but are sending nothing.  After ~25 s
            # the camera interprets this as "viewer not receiving audio" and
            # stops its own audio RTP stream, even though DTLS/video continue.
            # Sending AUDIOSTART explicitly tells the camera to keep audio
            # streaming regardless of our send state.  8-byte payload of zeros
            # matches the TUTK IOTC path usage in a.java:1126.
            try:
                _seq = random.randint(0, 0x7fffffff)
                _ts_ms = int(time.time() * 1000)
                _payload = b'\x00' * 8
                _hdr = struct.pack("<IIqII4x", _seq, 768, _ts_ms, len(_payload), 0)
                _dc_ref.send(_hdr + _payload)
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception", '_send_avio_audiostart', exc_info=True)

        def _send_avio_speaker(_dc_ref, start: bool) -> None:
            # IOTYPE_USER_IPCAM_SPEAKERSTART = 848 / SPEAKERSTOP = 849
            # (AVIOCTRLDEFs.java:268-271).  The official app sends SPEAKERSTART
            # when talk begins (f0.java:1500, startTalk m2()) to open the
            # camera's speaker, and SPEAKERSTOP to close it.  Payload is
            # SMsgAVIoctrlAVStream.parseContent(0) = 8 bytes, channel=0 LE.
            try:
                _cmd = 848 if start else 849
                _seq = random.randint(0, 0x7fffffff)
                _ts_ms = int(time.time() * 1000)
                _payload = b'\x00' * 8  # channel=0
                _hdr = struct.pack("<IIqII4x", _seq, _cmd, _ts_ms, len(_payload), 0)
                _dc_ref.send(_hdr + _payload)
                _status(f"talk: sent AVIO SPEAKER{'START' if start else 'STOP'}"
                        f" ({_cmd})")
            except Exception as _spk_exc:
                _status(f"talk: SPEAKER{'START' if start else 'STOP'} failed:"
                        f" {_spk_exc}")

        # Camera-initiated DCEP path: KVS firmware on A000088 doesn't include
        # m=application in its answer SDP, suggesting the data channel may
        # actually be opened by the camera (master), not the viewer.  Hook
        # pc.on("datachannel") so we receive whatever channel arrives.
        @pc.on("datachannel")
        def _on_remote_datachannel(channel) -> None:
            _status(
                f"pc.on(datachannel) FIRED - label={channel.label!r}"
                f" id={channel.id} state={channel.readyState}"
            )

            @channel.on("open")
            def _on_remote_dc_open() -> None:
                _status(f"DC[remote:{channel.label}] OPEN (camera-initiated)")

            @channel.on("message")
            def _on_remote_dc_message(message) -> None:
                try:
                    if isinstance(message, (bytes, bytearray)):
                        _status(
                            f"DC[remote:{channel.label}] RX {len(message)}B"
                            f" hex={bytes(message)[:32].hex()}"
                        )
                    else:
                        _status(f"DC[remote:{channel.label}] RX text {message!r}")
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception", '_on_remote_dc_message', exc_info=True)

        track_tasks: list = []
        _kvs_dc = None
        if self._offer_should_include_datachannel:
            # Match the official client's label exactly: f0.java:2923 uses
            # "data-channel-of-" + this.h, where this.h is the peer_id we
            # already generate (deviceId_random_count_0_streamID).  Camera
            # firmware likely pattern-matches this label.
            _dc_label = f"data-channel-of-{peer_id}"
            # Pre-negotiated channel (negotiated=True, id=1): skip DCEP
            # entirely.  Tested default (DCEP) createDataChannel on
            # 2026-04-25 - DTLS regression: aiortc allocates a second
            # ICE/DTLS transport for the DCEP-mode datachannel that doesn't
            # survive the answer-rebuild BUNDLE rewrite, and `__connect()`
            # raises InvalidStateError("RTCIceTransport is closed") before
            # DTLS handshake can complete.  Pre-negotiated mode rides on the
            # bundled transport, so DTLS comes up.  AVIO LIVING send below is
            # disabled separately to test whether the camera tolerates its
            # absence (per BaseKVSCameraView.k():805-815, the official client
            # only sends LIVING for PreCon cameras).
            _kvs_dc = pc.createDataChannel(_dc_label, negotiated=True, id=0)
            _status(
                f"offer: including SCTP datachannel label={_dc_label!r}"
                f" (pre-negotiated; KVS opens SCTP regardless)"
            )

            # AVIO LIVING (E_CMD_AVIO_CTRL_SESSION_MODE_REQ=5376) is sent
            # unconditionally when DC opens.  2026-05-03 testing confirmed:
            # A000088 cameras connect via DTLS,
            # SCTP comes up, DC opens at t+1-2s - but camera tears down at
            # t+22s if LIVING is not sent.  This is the PreCon watchdog:
            # without LIVING the camera gives up waiting for a viewer.
            # Previously LIVING was gated on setDevAttrNotif(sptPreconn:1),
            # but setDevAttrNotif only arrives during quickConn reset cycles
            # (now eliminated) - so the flag never gets set.
            # Per BaseKVSCameraView.k():805-815, non-PreCon cameras ignore
            # LIVING; sending it unconditionally is safe.

            @_kvs_dc.on("open")
            def _on_kvs_dc_open() -> None:
                async def _dcep_open_then_living() -> None:
                    # The official KVS Android client uses DCEP
                    # (createDataChannel with default Init), so the camera
                    # registers stream 0 only after receiving DATA_CHANNEL_OPEN
                    # (PPID=50, RFC 8832 §5.1).  Our pre-negotiated DC skips
                    # DCEP, so AVIO LIVING (PPID=51) arrives on an unregistered
                    # stream and is silently discarded at the application layer
                    # (SCTP SACK confirms transport delivery, but no response).
                    # Send DATA_CHANNEL_OPEN first so the camera registers
                    # stream 0, then send AVIO LIVING.
                    try:
                        _label_bytes = _dc_label.encode("utf-8")
                        _dc_open_msg = struct.pack(
                            "!BBHLHH",
                            0x03, 0x00,          # OPEN, DATA_CHANNEL_RELIABLE
                            0, 0,                # priority, reliability_param
                            len(_label_bytes), 0,  # label_len, proto_len
                        ) + _label_bytes
                        await _kvs_dc.transport._send(0, 50, _dc_open_msg)
                        _status(
                            f"DC[{_dc_label}] sent DATA_CHANNEL_OPEN"
                            f" (PPID=50) {len(_dc_open_msg)}B"
                        )
                        # Yield to event loop so DATA_CHANNEL_OPEN is
                        # transmitted and camera can register stream 0.
                        await asyncio.sleep(0.3)
                    except Exception as _dc_open_exc:
                        _status(
                            f"DC[{_dc_label}] DATA_CHANNEL_OPEN failed:"
                            f" {_dc_open_exc}"
                        )
                    _send_avio_living(_kvs_dc, _dc_label + " (PreCon)")
                    _send_avio_audiostart(_kvs_dc)
                    # Talk mode: open the camera speaker so it plays our audio.
                    if talk_pcm_provider is not None:
                        _send_avio_speaker(_kvs_dc, True)

                    # Periodic keepalive loop:
                    # - HEARTBEAT (5156) every 10 s: prevents camera's ~22 s
                    #   DTLS watchdog (f0.java:2991).
                    # - AUDIOSTART (768) every 10 s: prevents camera's ~25 s
                    #   audio watchdog.  Camera negotiates audio as sendrecv;
                    #   aiortc's sender emits RTCP SR with packet_count=0, which
                    #   the camera reads as "viewer not sending audio → stop
                    #   sending audio".  Periodic AUDIOSTART keeps audio alive.
                    async def _heartbeat_loop() -> None:
                        try:
                            while True:
                                await asyncio.sleep(10.0)
                                _send_avio_heartbeat(_kvs_dc)
                                _send_avio_audiostart(_kvs_dc)
                        except asyncio.CancelledError:
                            pass
                    _hb_task = asyncio.ensure_future(_heartbeat_loop())
                    track_tasks.append(_hb_task)

                _spawn_bg(_dcep_open_then_living())

            @_kvs_dc.on("message")
            def _on_kvs_dc_message(message) -> None:
                try:
                    if isinstance(message, (bytes, bytearray)):
                        _status(
                            f"DC[{_dc_label}] RX {len(message)}B"
                            f" hex={bytes(message)[:32].hex()}"
                        )
                    else:
                        _status(f"DC[{_dc_label}] RX text {message!r}")
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception", '_on_kvs_dc_message', exc_info=True)

            # Periodic readyState diagnostic - last run had no "DC OPEN"
            # log line, so we want to see whether readyState ever transitions
            # away from "connecting" or stays stuck.  Polls for 30s then stops.
            async def _poll_dc_state():
                _last = None
                for _ in range(30):
                    try:
                        _cur = _kvs_dc.readyState
                    except Exception:
                        _cur = "<error>"
                    if _cur != _last:
                        _status(f"DC[{_dc_label}] readyState: {_last} → {_cur}")
                        _last = _cur
                    if _cur in ("open", "closed"):
                        break
                    await asyncio.sleep(1.0)

            track_tasks.append(asyncio.ensure_future(_poll_dc_state()))
        else:
            _status("offer: SCTP datachannel skipped (model in _NO_DATACHANNEL_MODELS)")

        @pc.on("track")
        def _on_track(track) -> None:
            if track.kind == "audio":
                _status(f"audio track: id={track.id} kind={track.kind}")
                # The audio transceiver is sendrecv (no actual sender track).
                # aiortc's RTCRtpSender fires RTCP SR every ~1 s with
                # packet_count=0.  Camera firmware interprets 25 s of
                # zero-packet SR as "viewer not transmitting audio → stop
                # sending audio to them."  We patch the sender's _send_rtcp
                # to a no-op so no SR reaches the camera.
                #
                # In talk mode we attach a REAL PCMA sender, so its RTCP SR
                # carries genuine packet counts - skip the suppression so the
                # camera sees a properly-transmitting viewer.
                async def _suppress_audio_sender_rtcp() -> None:
                    # Patch _send_rtcp on the sender instance to a no-op so
                    # the RTCP SR loop keeps running (no BYE sent) but every
                    # SR with packet_count=0 is silently discarded.
                    # Cancelling the task instead would trigger a RTCP BYE,
                    # signalling "sender done" to the camera - worse outcome.
                    await asyncio.sleep(1.5)  # wait for sender to start
                    for _t in pc.getTransceivers():
                        if _t.kind == "audio":
                            async def _noop_rtcp(_pkts, **_kw):
                                pass
                            _t.sender._send_rtcp = _noop_rtcp
                            _status(
                                "audio sender: _send_rtcp patched → no-op"
                                " (suppresses 0-packet SR audio watchdog)"
                            )
                            break
                if talk_pcm_provider is None:
                    _spawn_bg(_suppress_audio_sender_rtcp())
                # Drain decoded audio frames so the queue doesn't grow unbounded.
                async def _drain_audio() -> None:
                    try:
                        while True:
                            await track.recv()
                    except Exception:
                        _LOGGER.debug("camera %s: swallowed exception", '_drain_audio', exc_info=True)
                t = asyncio.ensure_future(_drain_audio())
                track_tasks.append(t)
            elif track.kind == "video":
                # Request an IDR keyframe immediately via RTCP PLI so we start
                # decoding from a complete frame.  Without this, if the camera's
                # first IDR arrived before our SRTP path was ready (seq gap at
                # the start), H264 decoding silently drops every subsequent
                # delta frame and we receive zero decoded frames.
                async def _request_keyframe() -> None:
                    # aiortc 1.14 changed _send_rtcp_pli(media_ssrc) to require
                    # the remote SSRC.  The SSRC is only known once RTP starts
                    # arriving, so poll getSynchronizationSources() briefly and
                    # send PLI for each discovered source.
                    for _attempt in range(10):  # up to ~5s
                        await asyncio.sleep(0.5)
                        try:
                            _recv = next(
                                (r for r in pc.getReceivers() if r.track is track),
                                None,
                            )
                            if _recv is None:
                                continue
                            _ssrcs = [
                                s.source
                                for s in _recv.getSynchronizationSources()
                            ]
                            if not _ssrcs:
                                continue
                            for _ssrc in _ssrcs:
                                await _recv._send_rtcp_pli(_ssrc)
                            _status(
                                f"video track: sent RTCP PLI (keyframe request)"
                                f" ssrc={_ssrcs}"
                            )
                            return
                        except Exception as _pli_exc:
                            _LOGGER.debug("RTCP PLI attempt failed: %s", _pli_exc)
                    _LOGGER.debug("RTCP PLI: no SSRC discovered within 5s")
                _spawn_bg(_request_keyframe())
                if on_frame is not None:
                    t = asyncio.ensure_future(_webrtc_consume_video(track, on_frame))
                    track_tasks.append(t)

        recorder = None
        if output_path:
            try:
                from aiortc.contrib.media import MediaRecorder
                recorder = MediaRecorder(output_path)

                _video_recorded = [False]

                @pc.on("track")
                def _on_track_rec(track) -> None:
                    if track.kind == "video":
                        if not _video_recorded[0]:
                            recorder.addTrack(track)
                            _video_recorded[0] = True
                    else:
                        recorder.addTrack(track)
            except Exception as exc:
                _LOGGER.warning(
                    "async_open_webrtc_stream: MediaRecorder not available: %s", exc
                )

        # ------------------------------------------------------------------ #
        # Create SDP offer and publish webrtcReq
        # ------------------------------------------------------------------ #
        offer = await pc.createOffer()
        await pc.setLocalDescription(offer)
        _LOGGER.debug(
            "webrtc: SDP offer (first 500 chars):\n%s",
            pc.localDescription.sdp[:500],
        )

        # Scope the A000088 high-port nomination fix to THIS DTLS camera's aioice
        # connection only. The build_request monkeypatch is process-global, but it
        # acts solely on connections carrying this tag - so SDES cameras and every
        # non-camera device are provably unaffected (they are never tagged).
        if not use_sdes:
            try:
                _hp_xport = pc.getTransceivers()[0].receiver.transport.transport
                _hp_conn = getattr(_hp_xport, "_connection", None)
                if _hp_conn is not None:
                    _hp_conn._aidot_highport = True
                    _LOGGER.debug(
                        "highport-fix: scoped to this DTLS camera connection")
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception", '_on_track', exc_info=True)


        _sdp = pc.localDescription.sdp
        _status(
            f"SDP offer  m=video={_sdp_transport(_sdp, 'video')}"
            f"  m=audio={_sdp_transport(_sdp, 'audio')}"
        )
        _mlines = [ln for ln in _sdp.splitlines() if ln.startswith("m=")]
        _status("SDP m-sections (%d): %s" % (len(_mlines), " | ".join(_mlines)))

        def _seq() -> str:
            return f"ap{random.randint(1000000, 9999999)}"








        _offer_sdp = _dedup_bundle_candidates(
            _filter_sdp_candidates(
                _reorder_m_section_ice_attrs(
                    _normalize_bundle_ice_credentials(
                        _upgrade_sctp(pc.localDescription.sdp)
                    )
                )
            )
        )
        # Strip a=fingerprint:sha-384 and sha-512 lines.  aiortc emits all three
        # algorithm fingerprints; the camera's KVS-derived DTLS validator picks
        # one and fails when it doesn't match (closes the handshake with
        # "Unexpected EOF" after we send our certificate).  Browser WebRTC sends
        # sha-256 only - verified from a recorded successful session on the
        # same A001513 camera.  Keep only sha-256 to mirror that behaviour.
        import re as _fp_strip_re
        _offer_sdp = _fp_strip_re.sub(
            r'(?m)^a=fingerprint:sha-(?:384|512)[^\r\n]*\r?\n', '', _offer_sdp
        )
        _patched_mlines = [ln for ln in _offer_sdp.splitlines() if ln.startswith("m=")]
        _status("Offer m-sections (patched): %s" % " | ".join(_patched_mlines))
        # Build IceServerList from available _ice_servers for inclusion in
        # webrtcReq.  The browser always sends IceServerList; without it some
        # camera firmware (e.g. LK.IPC.A001064) does not activate its ICE
        # agent and never sends STUN probes, causing ICE to time out.
        # Deduplicate by URI tuple - getIceConfigResp commonly returns the
        # same TURN server 8x (one entry per relay slot).  Sending all 8
        # bloats the webrtcReq payload past A001064's MQTT receive buffer.
        _ice_server_list: list = []
        _seen_uris: set = set()
        for _srv in _ice_servers:
            _uris = _srv.urls if isinstance(_srv.urls, list) else [_srv.urls]
            _key = tuple(_uris)
            if _key in _seen_uris:
                continue
            _seen_uris.add(_key)
            _entry: dict = {"Uris": _uris}
            if getattr(_srv, "username", None):
                _entry["Username"] = _srv.username
            if getattr(_srv, "credential", None):
                _entry["Password"] = _srv.credential
            _ice_server_list.append(_entry)

        import random as _rnd_psk_dtls
        _psk_charset_dtls = "123456789abcdef"
        _psk_dtls = "".join(
            _psk_charset_dtls[_rnd_psk_dtls.randint(0, len(_psk_charset_dtls) - 1)]
            for _ in range(64)
        )

        # Compress SDP for wPayload.offer.sdp.  encOffer=1 in the official
        # client signals "compact SDP" - the camera's parser only consumes
        # the lines kept by _compress_sdp_for_camera.  Sending the full
        # aiortc-generated SDP (~5 KB+ of extmap/rtcp-fb/ssrc/msid) overflows
        # A001064's MQTT receive buffer and the camera quickConn-resets the
        # broker session before processing the request.
        _compressed_offer_sdp = _compress_sdp_for_camera(_offer_sdp)

        webrtc_req_payload = json.dumps({
            "method":  "webrtcReq",
            "service": "IPC",
            "devId":   device_id,
            "srcAddr": f"0.{user_id}",
            "seq":     _seq(),
            "tst":     int(time.time() * 1000),
            **( {"userId": _numeric_uid_raw} if _numeric_uid_raw is not None else {} ),
            "payload": {
                # Browser-style nested fields - newer firmware (e.g. A001064)
                # parses payload.wPayload.peerid / payload.wPayload.offer and
                # requires IceServerList to activate its ICE agent.
                # HAR captures from the official AiDot web app confirm this is
                # the canonical format; flat fields are kept for older firmware.
                # wPayload.offer.sdp must carry the FULL SDP (not the
                # compressed form): the camera's compressed-answer codepath
                # appears to skip its fingerprint-fill step when fed a
                # compressed offer, returning a malformed
                # `a=fingerprint:sha-256 ` line with empty value that
                # aiortc then rejects with "not enough values to unpack".
                # The legacy flat payload.offer.sdp gets the COMPRESSED SDP
                # so the total request still fits under the camera's MQTT
                # receive buffer (~10 KB threshold; we want < ~7-8 KB).
                "wPayload": {
                    "peerid": peer_id,
                    "sts":    int(time.time() * 1000),
                    "psk":    _psk_dtls,
                    "offer":  {"type": pc.localDescription.type,
                                "sdp":  _offer_sdp},
                },
                "IceServerList": _ice_server_list,
                # Legacy flat fields - older firmware parses payload.peerid directly.
                "peerid":  peer_id,
                "devId":   device_id,
                "offer":   {"type": pc.localDescription.type,
                             "sdp":  _compressed_offer_sdp},
                "trackId": 0,
                # Decompiled reference app (tyrus/o.java) sets dstAddr=deviceId
                # for webrtcReq.
                "dstAddr": device_id,
                "liveMqtt": 1,
                "encOffer": 1,
                # powerType/p2pCache: per docs/official_camera_network_calls.md
                # §5.2, both fields are present on every webrtcReq.  Defaults
                # used here match the fallback behaviour in the decompiled
                # reference app when IPC device info is unavailable.
                "powerType": _live_power_type,
                "p2pCache": _live_p2p_cache,
            },
        })
        outgoing_q.put_nowait((webrtc_req_topic, webrtc_req_payload))
        self._cold_phase("webrtcReq")
        _status(f"webrtcReq sent  peerid={peer_id}"
                f"  IceServerList×{len(_ice_server_list)}"
                f"  payload={len(webrtc_req_payload)}B")

        # GAP B (APK parity): the official app re-publishes webrtcReq up to ~3x,
        # ~15 s apart, when no webrtcResp arrives (f0.java reconnect / offer-resend),
        # targeting the "camera never answered" loss.  Re-send the SAME offer with a
        # fresh seq/tst (so the camera does not dedup it), gated on answer_fut /
        # camera_offer_fut so it is a no-op the instant the camera answers - it
        # therefore cannot disturb the connect path.  NB: the no-webrtcResp loss is
        # largely downstream (broker->camera), so this is a marginal parity addition,
        # not a guaranteed fix; the connect still relies on the per-attempt retry.
        async def _resend_webrtcreq() -> None:
            for _ in range(2):                          # up to 2 resends (3 total)
                await asyncio.sleep(15.0)
                if answer_fut.done() or camera_offer_fut.done():
                    return
                try:
                    _rs = json.loads(webrtc_req_payload)
                    _rs["seq"] = _seq()
                    _rs["tst"] = int(time.time() * 1000)
                    outgoing_q.put_nowait((webrtc_req_topic, json.dumps(_rs)))
                    _status("webrtcReq resent (no webrtcResp in 15 s - GAP B parity)")
                except Exception:
                    return
        track_tasks.append(asyncio.ensure_future(_resend_webrtcreq()))

        # Re-announce user presence now that the camera is confirmed awake
        # (it responded to livePlayReq).  The first user/connect was sent at
        # MQTT-setup time; this second send gives ICE-lite cameras a better
        # chance of pushing setDevAttrNotif with their LAN IP before the ICE
        # wait loop starts.  Replaces the invented getDevAttrReq.
        if not _cam_local_ip:
            outgoing_q.put_nowait((
                f"iot/v1/cb/{user_id}/user/connect",
                json.dumps({
                    "service": "user",
                    "method":  "connect",
                    "srcAddr": f"0.{user_id}",
                    "payload": {"timestamp": _mqtt_timestamp()},
                }),
            ))

        # Store ICE candidates for reconnect re-send (camera may quickConn-reset
        # during the signaling wait and need a fresh offer + candidates).
        _dtls_ice_payloads: list = []  # list of (topic, json_str)

        # Forward our own ICE candidates to the camera via MQTT
        @pc.on("icecandidate")
        def _on_local_ice(candidate) -> None:
            # Diag: aiortc gathers candidates during setLocalDescription and
            # may never fire this event (vanilla-ICE behavior).  Log unconditionally
            # so the next run tells us whether we can rely on this hook or must
            # do the manual local-SDP candidate extraction (see post-SRD block).
            _status(f"icecandidate event fired: {candidate!r}")
            if candidate is None:
                return
            # Skip candidates that remote cameras cannot use.
            # Docker-bridge (172.17.x), CGNAT/Tailscale (100.x.x.x), and
            # IPv6 addresses are unreachable from cameras on the internet or
            # a different LAN segment.  Sending them wastes ICE checking time
            # and can cause ICE-lite cameras to echo them back as their own
            # candidates, polluting the remote candidate list.
            import re as _re
            _cand_ip = getattr(candidate, "ip", "") or ""
            if _re.match(r'^172\.17\.', _cand_ip):
                return  # Docker bridge - skip
            if _re.match(r'^100\.', _cand_ip):
                return  # CGNAT / Tailscale - skip
            if ':' in _cand_ip:
                return  # IPv6 - skip
            cand_str = (
                f"candidate:{candidate.foundation} {candidate.component} "
                f"{candidate.protocol} {candidate.priority} {candidate.ip} "
                f"{candidate.port} typ {candidate.type}"
            )
            if getattr(candidate, "relatedAddress", None):
                cand_str += (
                    f" raddr {candidate.relatedAddress}"
                    f" rport {candidate.relatedPort}"
                )
            # Include sdpMid/sdpMLineIndex so the camera can map the candidate
            # to the correct m-section.  A001064 and similar firmware needs these
            # fields; without them the camera may silently ignore the candidates.
            _cand_obj: dict = {"candidate": cand_str}
            _c_mid     = getattr(candidate, "sdpMid",        None)
            _c_mid_idx = getattr(candidate, "sdpMLineIndex",  None)
            if _c_mid is not None:
                _cand_obj["sdpMid"] = _c_mid
            if _c_mid_idx is not None:
                _cand_obj["sdpMLineIndex"] = _c_mid_idx
            payload = json.dumps({
                "method":  "iceCandidateReq",
                "service": "IPC",
                "devId":   device_id,
                "srcAddr": f"0.{user_id}",
                "seq":     _seq(),
                "tst":     int(time.time() * 1000),
                **( {"userId": _numeric_uid_raw} if _numeric_uid_raw is not None else {} ),
                "payload": {
                    # dstAddr routes the candidate to the target device (device ID,
                    # not user ID).  HAR captures from the official AiDot web app
                    # confirm payload.dstAddr = deviceId on every iceCandidateReq.
                    "dstAddr": device_id,
                    # wPayload is the browser-style nested format required by
                    # newer firmware (e.g. LK.IPC.A001064) that parses
                    # payload.wPayload.candidate rather than payload.candidate.
                    "wPayload": {
                        "peerid":    peer_id,
                        "candidate": _cand_obj,
                    },
                    # Keep flat fields for older firmware that reads payload.peerid /
                    # payload.candidate directly.
                    "peerid":    peer_id,
                    "devId":     device_id,
                    "candidate": _cand_obj,
                },
            })
            outgoing_q.put_nowait((ice_cand_topic, payload))
            _dtls_ice_payloads.append((ice_cand_topic, payload))

        # Wait for EITHER webrtcResp (normal) OR webrtcReq from camera (role reversal).
        # Some firmware (e.g. LK.IPC.A001064) responds to our offer by sending back
        # its own webrtcReq (counter-offer) instead of a webrtcResp answer.
        #
        # Also include webrtc_req_echo_fut: for "echo-only" cameras (e.g.
        # LK.IPC.A001064) the broker echo of our webrtcReq is the camera's ONLY
        # signal - no separate webrtcResp or non-echo webrtcReq ever arrives.
        # If only the echo fires we do a brief secondary wait; if still no real
        # response we synthesise camera_offer_fut from the echo SDP so the
        # existing role-reversal path can proceed (it already handles mirrored
        # ICE credentials - see comment below at "Observed behaviour").
        #
        # Note: _patch_answer_mid2_for_aiortc is no longer needed.  With 3-section
        # SDP the camera's answer mid:1=H264 video and mid:2=datachannel - aiortc
        # accepts both without patching.
        # Initial signaling wait: poll so we can also notice a quickConn reset
        # that arrives BEFORE any of the three signaling futures fire.  Some
        # firmware (LK.IPC.A001064 with isDTLS=0/enableSdes=1) drops the MQTT
        # session immediately on receiving our DTLS webrtcReq - neither echoes
        # it back nor sends a webrtcResp - and only re-subscribes ~3 s later.
        # Without re-publishing webrtcReq + ICE candidates after the reconnect,
        # the camera silently ignores the original request and we time out.
        _init_done: set = set()
        _init_pending: set = {answer_fut, camera_offer_fut, webrtc_req_echo_fut}
        _init_deadline = asyncio.get_running_loop().time() + timeout
        _init_reconnect_resends = 0
        while asyncio.get_running_loop().time() < _init_deadline:
            _init_remaining = _init_deadline - asyncio.get_running_loop().time()
            _init_done, _init_pending = await asyncio.wait(
                _init_pending,
                timeout=min(1.0, max(0.01, _init_remaining)),
                return_when=asyncio.FIRST_COMPLETED,
            )
            if _init_done:
                break
            if (camera_reconnect_ev.is_set()
                    and _init_reconnect_resends < 2):
                camera_reconnect_ev.clear()
                _init_reconnect_resends += 1
                _status(
                    f"camera quickConn-reset before signaling reply"
                    f" (resend {_init_reconnect_resends})"
                    " - re-sending DTLS webrtcReq + ICE candidates"
                )
                await asyncio.sleep(1.5)   # let camera re-subscribe to MQTT
                outgoing_q.put_nowait((webrtc_req_topic, webrtc_req_payload))
                for _di_p in _dtls_ice_payloads:
                    outgoing_q.put_nowait(_di_p)
        _rr_done, _rr_pending = _init_done, _init_pending

        # Echo-reversal path: echo arrived but no proper response yet.
        # Poll up to 20 s for a real webrtcResp; handle camera quickConn resets
        # by re-sending webrtcReq + ICE candidates so the camera gets a fresh offer.
        # LK.IPC.A001064: SDES path (run before us) poisons its session; camera
        # resets (quickConn=1) on receiving our DTLS webrtcReq, then comes back
        # clean after ~3-5 s.  Re-sending the offer after reconnect gets the
        # camera to respond with its real DTLS answer in ~242 ms.
        _rr_echo_only = False  # True only for cameras that echo but never send a real webrtcResp
        if (webrtc_req_echo_fut in _rr_done
                and answer_fut not in _rr_done
                and camera_offer_fut not in _rr_done):
            _status("webrtcReq echo received - waiting for camera webrtcResp...")
            _rr_secondary_limit = 20.0
            _rr_secondary_deadline = asyncio.get_running_loop().time() + _rr_secondary_limit
            _rr_done2: set = set()
            _rr_pending2 = _rr_pending   # {answer_fut, camera_offer_fut}
            _rr_reconnect_resends = 0
            while asyncio.get_running_loop().time() < _rr_secondary_deadline:
                _remaining = _rr_secondary_deadline - asyncio.get_running_loop().time()
                _rr_done2, _rr_pending2 = await asyncio.wait(
                    _rr_pending2,
                    timeout=min(1.0, max(0.01, _remaining)),
                    return_when=asyncio.FIRST_COMPLETED,
                )
                if _rr_done2:
                    break   # Real response arrived
                # Camera quickConn reset: came back clean, needs a fresh offer.
                if (camera_reconnect_ev.is_set()
                        and _rr_reconnect_resends < 2):
                    camera_reconnect_ev.clear()
                    _rr_reconnect_resends += 1
                    _status(
                        f"camera reconnected during webrtcResp wait"
                        f" (resend {_rr_reconnect_resends})"
                        " - re-sending webrtcReq + ICE candidates"
                    )
                    await asyncio.sleep(1.5)   # let camera re-subscribe
                    outgoing_q.put_nowait((webrtc_req_topic, webrtc_req_payload))
                    for _di_p in _dtls_ice_payloads:
                        outgoing_q.put_nowait(_di_p)
            if _rr_done2:
                # Real response arrived - proceed normally
                _rr_done, _rr_pending = _rr_done2, _rr_pending2
            else:
                # No real webrtcResp in 20 s.  Camera (e.g. LK.IPC.A001064) is
                # likely still in its prior SDES session (SDES webrtcReq was sent
                # before us; camera waits ~25-30 s for a webrtcResp that never
                # comes).  Re-send DTLS webrtcReq now and wait another 20 s;
                # once the SDES session clears the camera answers in ~242 ms.
                _status(
                    "echo-reversal: no real webrtcResp after 20 s"
                    " - re-sending DTLS webrtcReq (camera clearing SDES session)"
                )
                outgoing_q.put_nowait((webrtc_req_topic, webrtc_req_payload))
                for _di_p in _dtls_ice_payloads:
                    outgoing_q.put_nowait(_di_p)

                _rr_ext_limit    = 20.0
                _rr_ext_deadline = asyncio.get_running_loop().time() + _rr_ext_limit
                _rr_done3: set   = set()
                _rr_reconnect_ext = 0
                while asyncio.get_running_loop().time() < _rr_ext_deadline:
                    _ext_rem = _rr_ext_deadline - asyncio.get_running_loop().time()
                    _rr_done3, _rr_pending2 = await asyncio.wait(
                        _rr_pending2,
                        timeout=min(1.0, max(0.01, _ext_rem)),
                        return_when=asyncio.FIRST_COMPLETED,
                    )
                    if _rr_done3:
                        break
                    if (camera_reconnect_ev.is_set()
                            and _rr_reconnect_ext < 2):
                        camera_reconnect_ev.clear()
                        _rr_reconnect_ext += 1
                        _status(
                            f"camera reconnected (extended wait, resend {_rr_reconnect_ext})"
                            " - re-sending DTLS webrtcReq"
                        )
                        await asyncio.sleep(1.5)
                        outgoing_q.put_nowait((webrtc_req_topic, webrtc_req_payload))
                        for _di_p in _dtls_ice_payloads:
                            outgoing_q.put_nowait(_di_p)

                if _rr_done3:
                    # Camera finally answered - proceed via normal path
                    _rr_done, _rr_pending = _rr_done3, _rr_pending2
                else:
                    # Still no response after ~40 s total.  Last-resort role-reversal.
                    _rr_echo_only = True
                    _status(
                        "echo-reversal: no real webrtcResp after extended wait"
                        " - role-reversal as last resort"
                    )
                    camera_offer_fut.set_result(webrtc_req_echo_fut.result())
                    _rr_done    = {camera_offer_fut}
                    _rr_pending = _rr_pending2

        for _f in _rr_pending:
            _f.cancel()

        if not _rr_done:
            # GAP D: distinguish a TERMINAL refusal (camera said -50002/-50015) from a
            # generic no-answer. A terminal refusal must NOT be retried.
            if terminal_error_fut.done():
                _code, _desc = terminal_error_fut.result()
                _status(f"camera refused: ack {_code} {_desc} - terminal, not retrying")
                outgoing_q.put_nowait(None)
                await pc.close()
                raise AidotCameraBusy(_code, _desc)
            _status(f"no webrtcResp in {timeout}s")
            outgoing_q.put_nowait(None)
            await pc.close()
            raise RuntimeError(
                f"async_open_webrtc_stream: no webrtcResp received within {timeout}s"
            )

        # Ports from camera counter-offer for synthetic candidate injection.
        # Populated in the role-reversal path below; consumed in the ICE wait loop.
        _rr_cam_ports: list = []  # list of (sdpMid, sdpMLineIndex, port)

        # Stored webrtcResp + ICE candidate payloads for reconnect re-send.
        # Set in the role-reversal block; consumed in the ICE wait loop when the
        # camera sends device/connect (quickConn reconnect) during ICE checking.
        _rr_webrtc_resp_topic:   str  = ""
        _rr_webrtc_resp_payload: str  = ""
        _rr_ice_payloads:        list = []  # list of (topic, json_str) tuples

        if camera_offer_fut in _rr_done and answer_fut not in _rr_done:
            # ---- ROLE REVERSAL path (e.g. LK.IPC.A001064) ---------------------- #
            # Camera sent webrtcReq (its offer) instead of webrtcResp.
            # Protocol: we send webrtcResp (our answer to the camera's counter-offer),
            # then the camera sends its real second webrtcResp (captured in
            # second_answer_fut, ignored here - ICE timing).
            # We call setRemoteDescription NOW using a synthetic answer built from
            # pc.localDescription.sdp (for correct codec PTs) but patched with the
            # camera's own ICE credentials from the counter-offer (so aioice can
            # authenticate the camera's incoming STUN binding requests).
            _status(
                "camera sent webrtcReq (role reversal) - answering and sending webrtcResp"
            )
            # ---- Fingerprint bypass for echo-reversal --------------------------- #
            # The camera echoes our fingerprint in its SDP.  When the camera
            # (DTLS active/client) presents its own self-signed certificate during
            # the handshake, aiortc's _validate_peer_identity would compare the
            # camera's cert digest against our echoed fingerprint → always fails,
            # leaving the DTLS transport in State.FAILED and producing 0 frames.
            #
            # Patch each DTLS transport to overwrite the stored fingerprint with
            # the camera's actual certificate digest so verification always succeeds.
            # This must be applied before the DTLS handshake starts (i.e. before
            # ICE connects), but does NOT require setRemoteDescription first.
            import types as _types
            try:
                from aiortc.rtcdtlstransport import (
                    RTCDtlsFingerprint as _RRFp,
                    certificate_digest as _rr_cert_fp,
                )

                def _accept_camera_cert(self, remote_params):
                    """Accept camera's actual DTLS cert (echo-reversal fingerprint fix)."""
                    try:
                        _cam_cert = self._ssl.get_peer_certificate(
                            as_cryptography=True
                        )
                        if _cam_cert is not None:
                            _real_fp = _rr_cert_fp(_cam_cert, "sha-256")
                            remote_params.fingerprints[:] = [
                                _RRFp(algorithm="sha-256", value=_real_fp)
                            ]
                    except Exception:
                        pass  # cert retrieval failed; skip verification

                for _rr_tc in pc.getTransceivers():
                    _rr_tc.receiver.transport._validate_peer_identity = (
                        _types.MethodType(
                            _accept_camera_cert, _rr_tc.receiver.transport
                        )
                    )
                _status(
                    "role-reversal fingerprint bypass applied"
                    f" ({len(pc.getTransceivers())} transceivers)"
                )
            except Exception as _rr_fp_exc:
                _status(
                    f"role-reversal fingerprint bypass skipped: {_rr_fp_exc}"
                )
            # ---- Early setRemoteDescription from camera's counter-offer ---------- #
            # The camera starts STUN probing as soon as it receives our webrtcResp +
            # iceCandidateReq.  If we wait for second_answer_fut (up to 8 s) before
            # calling setRemoteDescription, the camera's initial binding checks may
            # expire before aiortc's ICE agent starts - leaving ICE stuck in
            # "checking" indefinitely.  Instead, call setRemoteDescription NOW with
            # a synthetic answer (built from our local offer but with the camera's
            # ICE credentials injected).  aiortc enters ICE "checking" immediately;
            # when the camera's STUN probes arrive after iceCandidateReq, aioice
            # creates peer-reflexive candidates and ICE connects.
            import re as _rr_re
            # Extract camera's ICE credentials from its counter-offer.
            # Observed behaviour for LK.IPC.A001064: the camera echoes our ICE
            # credentials exactly (ufrag/pwd are identical in both the counter-offer
            # and the real second answer).  The substitution below is therefore a
            # no-op in practice for this model, but is kept for correctness in case
            # a future firmware version uses its own credentials.  Without the
            # substitution, any camera that does generate different credentials would
            # cause every STUN probe to fail auth → ICE stuck checking.
            _cam_counter_sdp = (camera_offer_fut.result() or {}).get("sdp", "")
            _cam_ice_ufrag: str | None = None
            _cam_ice_pwd:   str | None = None
            _rr_cm_idx = -1
            for _rr_cln in _cam_counter_sdp.splitlines():
                if _rr_cln.startswith("a=ice-ufrag:") and _cam_ice_ufrag is None:
                    _cam_ice_ufrag = _rr_cln.strip()
                if _rr_cln.startswith("a=ice-pwd:") and _cam_ice_pwd is None:
                    _cam_ice_pwd = _rr_cln.strip()
                # Collect ports from the camera counter-offer m-lines for later
                # synthetic candidate injection (cam-IP + echoed port).
                if _rr_cln.startswith("m="):
                    _rr_cm_idx += 1
                    _rr_pm = _rr_re.match(r'm=\S+ (\d+)', _rr_cln)
                    if _rr_pm:
                        _rr_cp = int(_rr_pm.group(1))
                        if _rr_cp != 0 and _rr_cm_idx <= 1:
                            _rr_cam_ports.append((str(_rr_cm_idx), _rr_cm_idx, _rr_cp))
            # Use pc.localDescription.sdp (the unpatched aiortc offer) as the
            # synthetic answer base so the codec list matches aiortc's internal state
            # (VP8/H264 PT=97-102 for all m-sections, no PT=103=H265).
            _rr_synth_sdp = pc.localDescription.sdp
            _rr_synth_sdp = _normalize_bundle_ice_credentials(_rr_synth_sdp)
            # Strip our own local ICE candidates - they are useless as "remote"
            # candidates for the answer.  aioice will discover peer-reflexive
            # candidates when the camera probes after iceCandidateReq.
            _rr_synth_sdp = _rr_re.sub(
                r'a=candidate:[^\r\n]*\r?\n', '', _rr_synth_sdp
            )
            _rr_synth_sdp = (
                _rr_synth_sdp
                .replace('a=end-of-candidates\r\n', '')
                .replace('a=end-of-candidates\n', '')
            )
            # Strip local SSRC lines - they belong to our own transceivers and must
            # not appear as remote sender SSRCs.  Removing them lets aiortc accept
            # incoming RTP from the camera regardless of its actual SSRC.
            _rr_synth_sdp = _rr_re.sub(r'a=ssrc(?:-group)?:[^\r\n]*\r?\n', '', _rr_synth_sdp)
            # Replace our local ICE credentials with the camera's so aioice
            # authenticates the camera's STUN probes correctly.
            if _cam_ice_ufrag:
                _rr_synth_sdp = _rr_re.sub(
                    r'a=ice-ufrag:[^\r\n]*', _cam_ice_ufrag, _rr_synth_sdp
                )
            if _cam_ice_pwd:
                _rr_synth_sdp = _rr_re.sub(
                    r'a=ice-pwd:[^\r\n]*', _cam_ice_pwd, _rr_synth_sdp
                )
            # Camera sends media to us → its answer direction is sendonly.
            _rr_synth_sdp = _rr_synth_sdp.replace('a=recvonly\r\n', 'a=sendonly\r\n')
            # DTLS setup role depends on whether the camera is echo-only or real-reversal.
            # Echo-only (e.g. LK.IPC.A001064): camera never sends its own webrtcResp, so
            # we cannot know its DTLS preference.  We declare setup:passive in our
            # webrtcResp, making the camera DTLS active/ICE-controlling.  The camera then
            # allocates a TURN relay and sends iceCandidateReq → ICE connects via relay.
            # Tell aiortc the remote is DTLS active so aiortc becomes passive/server.
            # RFC 5763: remote=active → local=passive (server).
            #
            # Real role-reversal (camera sent setup:passive in its second webrtcResp):
            # we send setup:active so we are DTLS client and initiate the ClientHello.
            # Tell aiortc the remote is passive so aiortc becomes active/client.
            # RFC 5763: remote=passive → local=active (client).
            _rr_synth_sdp = _rr_synth_sdp.replace(
                'a=setup:actpass\r\n',
                'a=setup:active\r\n' if _rr_echo_only else 'a=setup:passive\r\n'
            )
            try:
                await pc.setRemoteDescription(
                    RTCSessionDescription(sdp=_rr_synth_sdp, type="answer")
                )
                _status(
                    "role-reversal: setRemoteDescription from counter-offer"
                    " - ICE now checking"
                )
            except Exception as _rr_srd_exc:
                _status(
                    f"role-reversal: early setRemoteDescription failed:"
                    f" {_rr_srd_exc}"
                )
                outgoing_q.put_nowait(None)
                await pc.close()
                raise RuntimeError(
                    f"async_open_webrtc_stream: setRemoteDescription failed:"
                    f" {_rr_srd_exc}"
                ) from _rr_srd_exc
            # APK parity: do NOT pre-seed cam_ip_q for synthetic candidate injection
            # (that injection is removed below).  We rely only on the camera's REAL
            # candidates - TURN relay + the camera's second webrtcResp SDP
            # (second_answer_fut) + the iceCandidateReq trickle - exactly as the
            # official app does (verbatim addIceCandidate, no fabricated candidates).

            # Send webrtcResp so the camera knows our DTLS fingerprint and ICE params.
            # setup value is role-dependent (see comment above the synth-SDP replace):
            #   echo-only  → setup:passive (we are DTLS server; camera is DTLS client/
            #                               ICE-controlling; camera allocates relay and
            #                               sends iceCandidateReq → ICE via TURN relay)
            #   real-reversal → setup:active (we initiate DTLS ClientHello after ICE)
            # aiortc generates SDPs with \r\n so a simple replace is safe here.
            _rr_setup_val = "passive" if _rr_echo_only else "active"
            _rr_answer_sdp = _normalize_bundle_ice_credentials(
                pc.localDescription.sdp.replace(
                    "a=setup:actpass\r\n", f"a=setup:{_rr_setup_val}\r\n"
                )
            )
            _webrtc_resp_topic   = f"iot/v1/s/{user_id}/IPC/webrtcResp"
            _webrtc_resp_payload = json.dumps({
                "method":  "webrtcResp",
                "service": "IPC",
                "devId":   device_id,
                "srcAddr": f"0.{user_id}",
                "seq":     _seq(),
                "tst":     int(time.time() * 1000),
                **( {"userId": _numeric_uid_raw} if _numeric_uid_raw is not None else {} ),
                "payload": {
                    "peerid":  peer_id,
                    "devId":   device_id,
                    "answer":  {"type": "answer", "sdp": _rr_answer_sdp},
                    "trackId": 0,
                    "dstAddr": device_id,
                    # wPayload mirrors the webrtcReq format.  Newer firmware
                    # (e.g. LK.IPC.A001064) parses wPayload.answer.sdp to
                    # extract our ICE credentials and candidates; without this
                    # the camera cannot form valid STUN binding requests and
                    # never initiates ICE connectivity checks → ICE closed.
                    "wPayload": {
                        "peerid": peer_id,
                        "answer": {"type": "answer", "sdp": _rr_answer_sdp},
                    },
                },
            })
            outgoing_q.put_nowait((_webrtc_resp_topic, _webrtc_resp_payload))
            # Save for potential reconnect re-send in the ICE wait loop.
            _rr_webrtc_resp_topic   = _webrtc_resp_topic
            _rr_webrtc_resp_payload = _webrtc_resp_payload
            _status(f"webrtcResp sent (role-reversal answer, setup={_rr_setup_val})")

            # Re-announce ALL our gathered ICE candidates (host + srflx/prflx) after
            # sending webrtcResp.  The @pc.on("icecandidate") callbacks fired during
            # ICE gathering (possibly before the camera's MQTT session was ready to
            # process them).  We resend here to ensure the camera sees every candidate
            # at the right time so it can initiate STUN checks against us.
            #
            # Key fix: the previous version only sent the first private-LAN host
            # candidate (e.g. 192.168.1.175), which is unreachable from a remote
            # camera.  We now send ALL gathered candidates including server-reflexive
            # ones (e.g. 72.84.199.230 public IP) so a remote camera can reach us
            # even when NAT traversal without TURN is possible.
            _rr_local_sdp  = pc.localDescription.sdp
            _rr_ice_topic  = f"iot/v1/s/{user_id}/IPC/iceCandidateReq"
            _rr_cand_count = 0
            _rr_cur_midx   = -1
            for _rr_ln in _rr_re.split(r'\r?\n', _rr_local_sdp):
                if _rr_ln.startswith('m='):
                    _rr_cur_midx += 1
                    if _rr_cur_midx > 1:   # only mid:0 audio, mid:1 video (skip mid:2 datachannel)
                        break
                elif _rr_ln.startswith('a=candidate:') and _rr_cur_midx >= 0:
                    # Skip loopback candidates (unreachable from any remote peer).
                    _rr_cip_m = _rr_re.search(
                        r'a=candidate:\S+ \d+ \w+ \d+ (\S+)', _rr_ln
                    )
                    if _rr_cip_m:
                        _rr_cip = _rr_cip_m.group(1)
                        if _rr_cip.startswith('127.') or _rr_cip == '::1':
                            continue
                    # Convert SDP attribute form (a=candidate:...) to MQTT form
                    # (candidate:...) - the leading "a=" is an SDP-layer decoration.
                    _rr_cand_str = 'candidate:' + _rr_ln.split('a=candidate:', 1)[-1]
                    _rr_cand_obj = {
                        "candidate":     _rr_cand_str,
                        "sdpMid":        str(_rr_cur_midx),
                        "sdpMLineIndex": _rr_cur_midx,
                    }
                    _rr_ice_json = json.dumps({
                        "method":  "iceCandidateReq",
                        "service": "IPC",
                        "devId":   device_id,
                        "srcAddr": f"0.{user_id}",
                        "seq":     _seq(),
                        "tst":     int(time.time() * 1000),
                        **( {"userId": _numeric_uid_raw} if _numeric_uid_raw is not None else {} ),
                        "payload": {
                            "dstAddr": device_id,
                            "wPayload": {
                                "peerid":    peer_id,
                                "candidate": _rr_cand_obj,
                            },
                            "peerid":    peer_id,
                            "devId":     device_id,
                            "candidate": _rr_cand_obj,
                        },
                    })
                    outgoing_q.put_nowait((_rr_ice_topic, _rr_ice_json))
                    _rr_ice_payloads.append((_rr_ice_topic, _rr_ice_json))
                    _rr_cand_count += 1
            _status(
                f"iceCandidateReq sent (role-reversal, post-webrtcResp,"
                f" {_rr_cand_count} candidates)"
            )

        else:
            # ---- NORMAL path: camera sent webrtcResp ---------------------------- #
            answer   = answer_fut.result()
            _ans_sdp = answer["sdp"]
            _ans_mlines = [ln for ln in _ans_sdp.splitlines() if ln.startswith("m=")]
            _status(
                f"webrtcResp received - m=video={_sdp_transport(_ans_sdp, 'video')}"
                f"  m=audio={_sdp_transport(_ans_sdp, 'audio')}"
            )
            _status("Answer m-sections (%d): %s" % (len(_ans_mlines), " | ".join(_ans_mlines)))
            # Log negotiated direction per m-section so we can see whether
            # camera renegotiated audio sendrecv→sendonly (or kept sendrecv).
            # Also expose ssrc/msid lines - useful when diagnosing why media
            # doesn't flow even though signaling completes.
            try:
                _ans_dirs = []
                _cur_mid = None
                for _ln in _ans_sdp.splitlines():
                    if _ln.startswith("m="):
                        _cur_mid = _ln.split()[0][2:]  # "audio", "video", "application"
                    elif _ln.startswith("a=mid:"):
                        _cur_mid = f"{_cur_mid}/mid={_ln[6:]}"
                    elif _ln in ("a=sendrecv", "a=sendonly", "a=recvonly", "a=inactive"):
                        _ans_dirs.append(f"{_cur_mid}={_ln[2:]}")
                _status(f"Answer m-section directions: {_ans_dirs}")
            except Exception as _ans_dir_exc:
                _status(f"Answer m-section direction log failed: {_ans_dir_exc}")

            # ---- Fingerprint placeholder + bypass -------------------------------- #
            # A001064 (and family) returns answer SDP with empty
            # ``a=fingerprint:sha-256 `` lines (template not filled in by the
            # camera's compressed-answer codepath; behaviour is identical
            # whether we send a compressed or uncompressed offer in
            # wPayload).  aiortc's SDP parser then fails at the empty value
            # with "not enough values to unpack".  Substitute a placeholder
            # 32-byte zero fingerprint so the parse succeeds, then patch
            # _validate_peer_identity on each DTLS transport so the actual
            # camera certificate is accepted at handshake time (same pattern
            # used in the role-reversal branch above).  This is safe because
            # the MQTT signaling channel is already authenticated; cert
            # pinning here would add no extra security.
            import re as _fp_re
            _ZERO_FP = ":".join(["00"] * 32)
            _ans_sdp_patched, _fp_subs = _fp_re.subn(
                r'(?m)^(a=fingerprint:sha-256)\s*$',
                rf'\1 {_ZERO_FP}',
                _ans_sdp,
            )
            if _fp_subs:
                _status(
                    f"answer SDP: filled in {_fp_subs} empty"
                    " a=fingerprint:sha-256 line(s) with placeholder"
                )
                _ans_sdp = _ans_sdp_patched

            # ---- Rebuild answer m-sections to match offer's mid+kind ------------ #
            # A001064 firmware violates RFC 3264 §6 in three observed ways:
            #   1. Drops rejected m-sections entirely instead of port=0.
            #   2. Replaces a rejected section with a different KIND (e.g.
            #      datachannel → second video for H265).
            #   3. Adds m-sections for kinds we never offered (e.g. extra
            #      H265 video at mid:2 when offer was audio+video only).
            # aiortc enforces strict count + kind match.  Rebuild the answer
            # by walking the offer's mid order: for each offer mid take the
            # answer's matching same-kind section if present, otherwise
            # synthesise a rejected (port=0) stub.  Answer mids not in the
            # offer are dropped.  Result has exactly the same mid order +
            # kind sequence as the offer.
            # Use _offer_sdp (the sent offer with injected H265 mid:2 + DC mid:3)
            # to drive the rebuild, NOT pc.localDescription.sdp which only has
            # 3 sections (audio/H264/DC at mids 0/1/2 without H265).  This gives
            # _offer_mids=['0','1','2','3'] matching the camera's 4-section answer.
            _offer_mids: list = []
            _offer_kinds: dict = {}
            _cur_kind: str = ""
            for _ln in _offer_sdp.splitlines():
                if _ln.startswith("m="):
                    _cur_kind = _ln.split(" ", 1)[0][2:]   # audio/video/application
                elif _ln.startswith("a=mid:"):
                    _mid = _ln[len("a=mid:"):].strip()
                    _offer_mids.append(_mid)
                    _offer_kinds[_mid] = _cur_kind

            # Split answer into header (pre-first-m=) + per-section blocks.
            # Each section block runs from its m= line through the line
            # before the next m= (or end-of-SDP).
            def _first_attr(_sdp: str, _attr: str) -> str:
                for _ln2 in _sdp.splitlines():
                    if _ln2.startswith(_attr):
                        return _ln2
                return ""
            _ice_ufrag_ln = _first_attr(_ans_sdp, "a=ice-ufrag:")
            _ice_pwd_ln   = _first_attr(_ans_sdp, "a=ice-pwd:")
            _fp_ln        = _first_attr(_ans_sdp, "a=fingerprint:")
            _setup_ln     = _first_attr(_ans_sdp, "a=setup:")

            _ans_lines = _ans_sdp.splitlines()
            _ans_sections: dict = {}      # mid → list[str] of section's lines
            _ans_header: list = []
            _cur_block: list = []
            _cur_mid: str = ""
            _cur_block_kind: str = ""
            _seen_first_m = False
            def _flush():
                if _cur_block and _cur_mid:
                    _ans_sections[_cur_mid] = (_cur_block_kind, list(_cur_block))
            for _ln in _ans_lines:
                if _ln.startswith("m="):
                    _flush()
                    _seen_first_m = True
                    _cur_block = [_ln]
                    _cur_mid = ""
                    _cur_block_kind = _ln.split(" ", 1)[0][2:]
                elif not _seen_first_m:
                    _ans_header.append(_ln)
                else:
                    _cur_block.append(_ln)
                    if _ln.startswith("a=mid:"):
                        _cur_mid = _ln[len("a=mid:"):].strip()
            _flush()

            def _stub(_m: str, _kind: str) -> list:
                if _kind == "application":
                    # Synthesize a VALID m=application section so aiortc's SCTP
                    # transport can start.  Camera (KVS firmware) answers mid:2
                    # with m=video H265 instead of m=application but always
                    # opens SCTP on port 5000 regardless.  Without these attrs
                    # aiortc crashes in serialize_packet (port=None).
                    # port=9 per RFC 8842 = "use sctp-port".
                    _hdr = "m=application 9 UDP/DTLS/SCTP webrtc-datachannel"
                    _block = [_hdr, "c=IN IP4 0.0.0.0", f"a=mid:{_m}"]
                    for _x in (_ice_ufrag_ln, _ice_pwd_ln, _fp_ln, _setup_ln):
                        if _x:
                            _block.append(_x)
                    _block.append("a=sctp-port:5000")
                    _block.append("a=max-message-size:262144")
                    return _block
                if _kind == "audio":
                    _hdr = "m=audio 0 UDP/TLS/RTP/SAVPF 0"
                else:
                    # PT=0 (PCMU/audio) in a video stub causes aiortc to fail
                    # "Failed to set remote video description send parameters"
                    # because PT=0 is not in aiortc's video codec registry.
                    # PT=97 is always in aiortc's video codec list (VP8 or H264
                    # variant) and satisfies codec lookup for a rejected section.
                    _hdr = "m=video 0 UDP/TLS/RTP/SAVPF 97"
                _block = [_hdr, "c=IN IP4 0.0.0.0", f"a=mid:{_m}"]
                for _x in (_ice_ufrag_ln, _ice_pwd_ln, _fp_ln, _setup_ln):
                    if _x:
                        _block.append(_x)
                # aiortc's __validate_description requires a=rtcp-mux in every
                # m-section that appears in the answer when the offer had it.
                # Our offer always includes a=rtcp-mux; stub sections must echo
                # it or setRemoteDescription raises "RTCP mux is not enabled".
                _block.append("a=rtcp-mux")
                _block.append("a=inactive")
                return _block

            # PTs we assigned to H265 in _replace_mid2_codecs_with_h265.
            # If the camera answers a video section using only these PTs,
            # aiortc fails with "Failed to set remote video description send
            # parameters" because H265 is not in its codec registry.  Stub
            # those sections so aiortc sees port=0/inactive for H265 - we
            # still benefit from the camera negotiating H265 (it proves the
            # 4-section BUNDLE is aligned) but we only decode H264 and audio.
            _h265_offer_pts: set = set()  # H265 no longer offered; camera won't answer H265 PTs

            _rebuilt: list = list(_ans_header)
            _kept_count = 0
            _stub_count = 0
            _dropped_mids: list = []
            _rejected_mids: set = set()  # mids whose stub has port=0 (excluded from BUNDLE)
            for _m in _offer_mids:
                _expected_kind = _offer_kinds[_m]
                _ans_kind, _ans_block = _ans_sections.get(_m, ("", []))
                # Force-stub H265 sections: detect by camera's answer m-line
                # using only our H265 PTs (103/104).
                _ans_h265 = False
                if _ans_block and _ans_kind == "video":
                    for _al in _ans_block:
                        if _al.startswith("m="):
                            _pts = set(_al.split()[3:])
                            if _pts and _pts.issubset(_h265_offer_pts):
                                _ans_h265 = True
                            break
                if _ans_h265:
                    _s = _stub(_m, _expected_kind)
                    _rebuilt.extend(_s)
                    _stub_count += 1
                    if _s and _s[0].split()[1] == '0':
                        _rejected_mids.add(_m)
                elif _ans_block and _ans_kind == _expected_kind:
                    _rebuilt.extend(_ans_block)
                    _kept_count += 1
                else:
                    _s = _stub(_m, _expected_kind)
                    _rebuilt.extend(_s)
                    _stub_count += 1
                    if _s and _s[0].split()[1] == '0':
                        _rejected_mids.add(_m)
            for _ans_mid in _ans_sections:
                if _ans_mid not in _offer_kinds:
                    _dropped_mids.append(_ans_mid)

            if _stub_count or _dropped_mids:
                _status(
                    f"answer SDP rebuilt: kept={_kept_count}"
                    f" stubbed={_stub_count} dropped={_dropped_mids}"
                    f" (offer mids={_offer_mids})"
                    + (f" rejected_from_bundle={sorted(_rejected_mids)}" if _rejected_mids else "")
                )

            # Fix a=group:BUNDLE: use offer mid order but exclude port=0 (rejected)
            # stubs.  RFC 8843 prohibits rejected m-sections in BUNDLE; aiortc
            # raises "Failed to set remote video description send parameters"
            # when a port=0 section is in the BUNDLE group.
            _bundle_mids = [m for m in _offer_mids if m not in _rejected_mids]
            _rebuilt2: list = []
            _bundle_replaced = False
            for _ln in _rebuilt:
                if _ln.startswith("a=group:BUNDLE") and not _bundle_replaced:
                    _rebuilt2.append("a=group:BUNDLE " + " ".join(_bundle_mids))
                    _bundle_replaced = True
                else:
                    _rebuilt2.append(_ln)
            _ans_sdp = "\r\n".join(_rebuilt2) + "\r\n"

            # Apply DTLS cert bypass - always, whenever we patched
            # fingerprints (camera's empty-fingerprint quirk applies on
            # every webrtcResp from this firmware family).
            if _fp_subs:
                import types as _np_types
                try:
                    from aiortc.rtcdtlstransport import (
                        RTCDtlsFingerprint as _NPFp,
                        certificate_digest as _np_cert_fp,
                    )

                    def _np_accept_cam_cert(self, remote_params):
                        try:
                            _cam_cert = self._ssl.get_peer_certificate(
                                as_cryptography=True
                            )
                            if _cam_cert is not None:
                                _real_fp = _np_cert_fp(_cam_cert, "sha-256")
                                remote_params.fingerprints[:] = [
                                    _NPFp(algorithm="sha-256", value=_real_fp)
                                ]
                        except Exception:
                            _LOGGER.debug("camera %s: swallowed exception", '_np_accept_cam_cert', exc_info=True)

                    # Diag: log PC/ICE state at patch-application time so we
                    # can see whether DTLS handshake has *already* started by
                    # the time we install the bypass (some aiortc versions
                    # kick off DTLS during setRemoteDescription itself).
                    _status(
                        "fingerprint bypass: pre-patch"
                        f" pc.connectionState={pc.connectionState}"
                        f" pc.iceConnectionState={pc.iceConnectionState}"
                    )
                    for _np_idx, _np_tc in enumerate(pc.getTransceivers()):
                        _np_dtls = _np_tc.receiver.transport
                        _np_ice = _np_dtls.transport
                        _np_pre_vpi = getattr(
                            getattr(
                                _np_dtls, "_validate_peer_identity", None
                            ),
                            "__qualname__",
                            "missing",
                        )
                        _status(
                            f"  patch[{_np_idx}] id(dtls)={id(_np_dtls)}"
                            f" id(ice)={id(_np_ice)}"
                            f" dtls.state={getattr(_np_dtls, 'state', '?')}"
                            f" ice.state={getattr(_np_ice, 'state', '?')}"
                            f" pre.vpi={_np_pre_vpi}"
                        )
                        _np_dtls._validate_peer_identity = (
                            _np_types.MethodType(_np_accept_cam_cert, _np_dtls)
                        )
                        _np_post_vpi = getattr(
                            getattr(
                                _np_dtls, "_validate_peer_identity", None
                            ),
                            "__qualname__",
                            "missing",
                        )
                        _status(f"  patch[{_np_idx}] post.vpi={_np_post_vpi}")
                    _status(
                        "fingerprint bypass applied"
                        f" ({len(pc.getTransceivers())} transceivers)"
                    )
                except Exception as _np_fp_exc:
                    _status(f"fingerprint bypass skipped: {_np_fp_exc}")

            # Rebuild the camera's answer into the 3 sections aiortc manages
            # (audio/H264/DC at mids 0/1/2), in aiortc's offer order.  H265-capable
            # firmware (A000088) may answer with 4 sections, reorder them, or pad a
            # bogus single-PT video stub - all of which break aiortc's positional
            # matching.  _aiortc_answer() below selects by content and re-emits.
            import re as _re_ans  # module has no top-level `import re`
            # Our offer's video payload types (H264 + RTX), used to pick the
            # camera's REAL video answer section apart from H265/bogus stubs.
            _offer_video_pts: set = set()
            for _ol in _offer_sdp.splitlines():
                if _ol.startswith('m=video'):
                    _offer_video_pts = set(_ol.split()[3:])
                    break

            def _aiortc_answer(sdp: str) -> str:
                """Produce a 3-section answer SDP for aiortc (audio/H264/DC),
                in aiortc's fixed transceiver order regardless of how the camera
                ordered or padded its reply.

                aiortc matches answer m-sections to its transceivers BY POSITION:
                audio(mid:0), H264(mid:1), DC(mid:2).  H265-capable firmware
                (A000088) sometimes answers with 4 sections AND reorders them
                (e.g. video / audio / video / application) and/or includes a bogus
                single-PT video stub.  Positional matching then fails with
                "Media sections in answer do not match offer".

                This selects by CONTENT and re-emits in offer order:
                  1. audio  = first m=audio section.
                  2. video  = the m=video section whose payload types intersect
                     our offer's video PTs (the real H264 answer), preferring the
                     one with the most overlap; falls back to the m=video with the
                     most PTs.  Bogus/H265 stubs are dropped.
                  3. DC     = first m=application section, or a synthesised stub
                     using the camera's ICE credentials.
                Each selected section's a=mid is rewritten to 0/1/2 and BUNDLE is
                normalised to "0 1 2".
                """
                _lines2 = _re_ans.split(r'\r?\n', sdp)
                _secs2, _cur2 = [], []
                for _l2 in _lines2:
                    if _l2.startswith('m=') and _cur2:
                        _secs2.append(_cur2); _cur2 = [_l2]
                    else:
                        _cur2.append(_l2)
                if _cur2:
                    _secs2.append(_cur2)

                _audio_secs = [s for s in _secs2 if s and s[0].startswith('m=audio')]
                _video_secs = [s for s in _secs2 if s and s[0].startswith('m=video')]
                _app_secs   = [s for s in _secs2 if s and s[0].startswith('m=application')]

                def _set_mid(_sec: list, _mid: str) -> list:
                    _seen_mid = False
                    _res = []
                    for _l in _sec:
                        if _l.startswith('a=mid:'):
                            _res.append(f'a=mid:{_mid}')
                            _seen_mid = True
                        else:
                            _res.append(_l)
                    if not _seen_mid:
                        # Insert mid right after the m= line.
                        _res.insert(1, f'a=mid:{_mid}')
                    return _res

                _out_secs: list[list[str]] = []
                _dc_only_video = False
                _dc_only_audio = False

                # 1. audio → mid:0
                if _audio_secs:
                    if _audio_secs[0][0].split()[1] == '0':
                        # Full datachannel-only answer: the camera also rejected
                        # audio (port 0, a=inactive).  Re-emit a port-0 stub that
                        # still carries an a=rtpmap, otherwise aiortc's
                        # find_common_codecs() is empty and setRemoteDescription
                        # raises "Failed to set remote audio description send
                        # parameters" (rtcpeerconnection.py:909).  PT 0 (PCMU) is
                        # always present in our offer.  Flag it so mid:0 is also
                        # EXCLUDED from BUNDLE below (aiortc rejects a port-0
                        # section inside the BUNDLE group).  Verified offline:
                        # audio+video stubs WITH rtpmap → SRD accepted.
                        _astub = ['m=audio 0 UDP/TLS/RTP/SAVPF 0',
                                  'c=IN IP4 0.0.0.0', 'a=mid:0']
                        for _xa in (_ice_ufrag_ln, _ice_pwd_ln, _fp_ln, _setup_ln):
                            if _xa:
                                _astub.append(_xa)
                        _astub += ['a=rtpmap:0 PCMU/8000', 'a=rtcp-mux',
                                   'a=inactive']
                        _out_secs.append(_astub)
                        _dc_only_audio = True
                    else:
                        _out_secs.append(_set_mid(_audio_secs[0], '0'))

                # 2. real video → mid:1 (pick best PT overlap with our offer)
                _best_video = None
                _best_overlap = -1
                for _vs in _video_secs:
                    _pts = set(_vs[0].split()[3:])
                    if _vs[0].split()[1] == '0':
                        continue  # port-0 stub
                    _overlap = len(_pts & _offer_video_pts)
                    # Prefer real PT overlap; tie-break on PT count (richer section).
                    _score = (_overlap, len(_pts))
                    if _score > (_best_overlap, -1) and (_overlap > 0 or _best_video is None):
                        _best_video = _vs
                        _best_overlap = _overlap
                if _best_video is None and _video_secs:
                    # No overlap at all - fall back to the richest non-stub video.
                    _best_video = max(
                        (s for s in _video_secs if s[0].split()[1] != '0'),
                        key=lambda s: len(s[0].split()[3:]),
                        default=None,
                    )
                if _best_video is not None:
                    _out_secs.append(_set_mid(_best_video, '1'))
                else:
                    # DC-only answer: the camera rejected video (port 0, no
                    # usable H264).  Keep a rejected port-0 video placeholder at
                    # mid:1 so the section count/order still matches our
                    # 3-section offer and aiortc accepts the answer instead of
                    # throwing - we then fail cleanly into retry rather than
                    # crashing setRemoteDescription.  (Driving media over the
                    # datachannel was tested and ruled out: a DC-only answer is
                    # the camera declining media and always co-fails DTLS, so the
                    # channel never opens - see docs/EXPERIMENT_DC_ONLY_HANDOFF.md.)
                    _vstub = ['m=video 0 UDP/TLS/RTP/SAVPF 97',
                              'c=IN IP4 0.0.0.0', 'a=mid:1']
                    for _xv in (_ice_ufrag_ln, _ice_pwd_ln, _fp_ln, _setup_ln):
                        if _xv:
                            _vstub.append(_xv)
                    # rtpmap required or aiortc rejects with "Failed to set remote
                    # video description send parameters" (find_common_codecs empty).
                    # PT 97 H264 is always in our offer.  Verified offline.
                    _vstub += ['a=rtpmap:97 H264/90000',
                               'a=fmtp:97 level-asymmetry-allowed=1;'
                               'packetization-mode=1;profile-level-id=42e01f',
                               'a=rtcp-mux', 'a=inactive']
                    _out_secs.append(_vstub)
                    _dc_only_video = True

                # 3. datachannel → mid:2
                if _app_secs:
                    _out_secs.append(_set_mid(_app_secs[0], '2'))
                else:
                    _dc_stub = [
                        'm=application 9 UDP/DTLS/SCTP webrtc-datachannel',
                        'c=IN IP4 0.0.0.0',
                        'a=mid:2',
                    ]
                    for _x2 in (_ice_ufrag_ln, _ice_pwd_ln, _fp_ln, _setup_ln):
                        if _x2:
                            _dc_stub.append(_x2)
                    _dc_stub.extend(['a=sctp-port:5000', 'a=max-message-size:262144'])
                    _out_secs.append(_dc_stub)

                # Session header = everything before the first m= section.
                _header = _secs2[0] if (_secs2 and not _secs2[0][0].startswith('m=')) else []
                _out2: list[str] = list(_header)
                for _sec in _out_secs:
                    _out2.extend(_sec)
                sdp2 = '\r\n'.join(_out2)
                # Ensure BUNDLE lists only the ACTIVE mids in order.  Any port-0
                # (rejected) section must be EXCLUDED - aiortc rejects a port-0
                # section inside the BUNDLE group.  A full datachannel-only answer
                # rejects both audio and video, leaving BUNDLE just "2".
                _bundle_mids = []
                if not _dc_only_audio:
                    _bundle_mids.append('0')
                if not _dc_only_video:
                    _bundle_mids.append('1')
                if _app_secs:
                    _bundle_mids.append('2')
                _bundle_str = 'a=group:BUNDLE ' + ' '.join(_bundle_mids)
                if _dc_only_audio or _dc_only_video:
                    _status(
                        f"DC-only answer: audio_rejected={_dc_only_audio}"
                        f" video_rejected={_dc_only_video} → {_bundle_str}"
                        " (camera declined media; accepted to fail cleanly into"
                        " retry - DC-only co-fails DTLS, no stream)"
                    )
                if 'a=group:BUNDLE' in sdp2:
                    sdp2 = _re_ans.sub(
                        r'a=group:BUNDLE [0-9 ]+',
                        _bundle_str,
                        sdp2, count=1,
                    )
                return sdp2

            _aiortc_sdp = _aiortc_answer(_ans_sdp)

            try:
                await pc.setRemoteDescription(
                    RTCSessionDescription(
                        sdp=_aiortc_sdp,
                        type=answer.get("type", "answer"),
                    )
                )
                # Diag: per-transceiver DTLS/ICE state immediately after
                # setRemoteDescription returns.  If dtls.state already !=
                # "new" here, aiortc has kicked off DTLS handshake on the
                # setRemoteDescription call itself - meaning our patch
                # (applied above) ran before, but any handshake that
                # already failed earlier wouldn't show that here either.
                try:
                    for _srd_idx, _srd_tc in enumerate(
                        pc.getTransceivers()
                    ):
                        _srd_dtls = _srd_tc.receiver.transport
                        _srd_ice = _srd_dtls.transport
                        _srd_vpi = getattr(
                            getattr(
                                _srd_dtls, "_validate_peer_identity", None
                            ),
                            "__qualname__",
                            "missing",
                        )
                        # Log id() of dtls/ice transports per transceiver:
                        # if BUNDLE merged correctly, all transceivers share
                        # the SAME RTCDtlsTransport + RTCIceTransport
                        # objects.  Different ids ⇒ BUNDLE failed and aiortc
                        # is running parallel transports - RTP would arrive
                        # on whichever transport's DTLS is up but the
                        # decoder receivers may be bound to the other one.
                        _srd_kind = getattr(
                            getattr(_srd_tc, "receiver", None),
                            "_kind",
                            getattr(_srd_tc, "kind", "?"),
                        )
                        _srd_mid = getattr(_srd_tc, "mid", "?")
                        _status(
                            f"  post-SRD[{_srd_idx}]"
                            f" mid={_srd_mid} kind={_srd_kind}"
                            f" dtls.id=0x{id(_srd_dtls):x}"
                            f" ice.id=0x{id(_srd_ice):x}"
                            f" dtls.state={getattr(_srd_dtls, 'state', '?')}"
                            f" ice.state={getattr(_srd_ice, 'state', '?')}"
                            f" vpi={_srd_vpi}"
                        )
                except Exception as _srd_diag_exc:
                    _status(f"  post-SRD diag failed: {_srd_diag_exc}")

                # --- Manual iceCandidateReq trickle (DTLS path) ----------- #
                # The official Android app sends iceCandidateReq for every local
                # ICE candidate AFTER receiving webrtcResp.  The camera waits
                # for this trickle, and tears down the DTLS session if it never
                # arrives - even when the same candidates are present in the
                # SDP offer's a=candidate lines.  aiortc gathers candidates
                # synchronously during setLocalDescription so the @pc.on(
                # "icecandidate") hook above never fires (vanilla-ICE);
                # parse pc.localDescription.sdp directly and publish each.
                try:
                    import re as _re_lc
                    _lsdp = (
                        pc.localDescription.sdp if pc.localDescription
                        else ""
                    )
                    _cur_mid: str | None = None
                    _cur_idx: int = -1
                    _sent_n = 0
                    for _ln in _lsdp.splitlines():
                        if _ln.startswith("m="):
                            _cur_idx += 1
                            _cur_mid = None
                            continue
                        _mid_m = _re_lc.match(r'^a=mid:(\S+)', _ln)
                        if _mid_m:
                            _cur_mid = _mid_m.group(1)
                            continue
                        _cand_m = _re_lc.match(r'^a=candidate:(.+)$', _ln)
                        if not _cand_m or _cur_mid is None:
                            continue
                        _cand_str = "candidate:" + _cand_m.group(1).strip()
                        # Skip Docker bridge / CGNAT / IPv6 - same filters as
                        # the dead @pc.on("icecandidate") handler above.
                        _cand_ip_m = _re_lc.search(
                            r'\s(\d+\.\d+\.\d+\.\d+|[0-9a-fA-F:]+)\s\d+\s+typ\s',
                            _cand_str,
                        )
                        _cand_ip = _cand_ip_m.group(1) if _cand_ip_m else ""
                        if (_cand_ip.startswith("172.17.")
                                or _cand_ip.startswith("100.")
                                or ':' in _cand_ip):
                            continue
                        _cand_obj = {
                            "candidate":     _cand_str,
                            "sdpMid":        _cur_mid,
                            "sdpMLineIndex": _cur_idx,
                        }
                        _ic_payload = json.dumps({
                            "method":  "iceCandidateReq",
                            "service": "IPC",
                            "devId":   device_id,
                            "srcAddr": f"0.{user_id}",
                            "seq":     _seq(),
                            "tst":     int(time.time() * 1000),
                            **( {"userId": _numeric_uid_raw}
                                if _numeric_uid_raw is not None else {} ),
                            "payload": {
                                "dstAddr": device_id,
                                "wPayload": {
                                    "peerid":    peer_id,
                                    "candidate": _cand_obj,
                                },
                                "peerid":    peer_id,
                                "devId":     device_id,
                                "candidate": _cand_obj,
                            },
                        })
                        outgoing_q.put_nowait((ice_cand_topic, _ic_payload))
                        _sent_n += 1
                    _status(
                        f"iceCandidateReq trickle (post-SRD): {_sent_n} candidate(s)"
                        f" sent from local SDP"
                    )
                except Exception as _trickle_exc:
                    _status(f"  manual iceCandidateReq trickle failed: {_trickle_exc}")
            except Exception as exc:
                # Dump full SDP and traceback so we can pinpoint where aiortc
                # is choking (the {exc} message alone - e.g. "not enough
                # values to unpack" - does not identify the offending line).
                import traceback as _tb_dtls
                _LOGGER.warning(
                    "setRemoteDescription failed: %s\n--- camera answer SDP ---\n%s\n--- traceback ---\n%s",
                    exc, _ans_sdp, _tb_dtls.format_exc(),
                )
                _status(f"setRemoteDescription failed: {exc}")
                outgoing_q.put_nowait(None)
                await pc.close()
                raise RuntimeError(
                    f"async_open_webrtc_stream: setRemoteDescription failed: {exc}"
                ) from exc

            # Extract ICE candidates embedded in the SDP answer body.
            # The AiDot camera includes its host candidate as a=candidate: lines
            # inside the answer SDP.  The browser WebRTC API processes these
            # automatically; aiortc requires explicit addIceCandidate() calls.
            # Track m-section index / mid so we can set sdpMid+sdpMLineIndex.
            import re as _re
            _sdp_cand_midx = -1
            _sdp_cand_smid = "0"
            for _sdp_ans_ln in _re.split(r'\r?\n', _ans_sdp):
                if _sdp_ans_ln.startswith('m='):
                    _sdp_cand_midx += 1
                    _sdp_cand_smid = str(_sdp_cand_midx)
                elif _sdp_ans_ln.startswith('a=mid:'):
                    _sdp_cand_smid = _sdp_ans_ln[len('a=mid:'):].strip()
                elif _sdp_ans_ln.startswith('a=candidate:'):
                    _sdp_cand_line = _re.sub(
                        r'\s+generation\s+\d+.*$',
                        '',
                        _sdp_ans_ln[len('a=candidate:'):].strip(),
                    )
                    try:
                        _sdp_ice = candidate_from_sdp(_sdp_cand_line)
                        _sdp_ice.sdpMid = _sdp_cand_smid
                        _sdp_ice.sdpMLineIndex = max(_sdp_cand_midx, 0)
                        await pc.addIceCandidate(_sdp_ice)
                        _status(f"addIceCandidate (answer SDP): {_sdp_cand_line[:80]}")
                    except Exception as _sdp_exc:
                        _LOGGER.debug(
                            "addIceCandidate (answer SDP) error: %s", _sdp_exc
                        )

        # ------------------------------------------------------------------ #
        # Apply remote ICE candidates + wait for ICE connection
        # ------------------------------------------------------------------ #
        connected_ev = asyncio.Event()

        @pc.on("connectionstatechange")
        async def _on_conn_state() -> None:
            _status(f"WebRTC connectionState → {pc.connectionState}")
            if pc.connectionState == "failed":
                # Dump per-transceiver DTLS + ICE state so we can see which
                # transport actually failed and why.  aiortc transitions
                # connectionState to "failed" on the first transport that
                # enters either dtlsTransport.state = "failed" or
                # iceTransport.state = "failed".
                try:
                    for _i, _tc in enumerate(pc.getTransceivers()):
                        _dtls = _tc.receiver.transport
                        _ice  = _dtls.transport
                        _status(
                            f"  transceiver[{_i}] kind={_tc.receiver.track.kind if _tc.receiver.track else '?'}"
                            f"  dtls.state={getattr(_dtls, 'state', '?')}"
                            f"  ice.state={getattr(_ice, 'state', '?')}"
                            f"  ice.role={getattr(getattr(_ice, '_connection', None), 'role', '?')}"
                        )
                except Exception as _diag_exc:
                    _status(f"  transceiver state dump failed: {_diag_exc}")
            if pc.connectionState in ("connected", "completed"):
                connected_ev.set()
            elif pc.connectionState in ("failed", "closed"):
                connected_ev.set()   # unblock the wait; session will detect failure

        @pc.on("iceconnectionstatechange")
        async def _on_ice_state() -> None:
            _status(f"ICE connectionState → {pc.iceConnectionState}")

        @pc.on("icegatheringstatechange")
        async def _on_ice_gather() -> None:
            _LOGGER.info("webrtc: ICE gatheringState → %s", pc.iceGatheringState)

        deadline = time.monotonic() + timeout
        _last_ice_log = time.monotonic()
        _userconnect_midloop_sent = False  # guard: re-send user/connect once at half-timeout
        _second_ans_processed = False  # guard: process second_answer_fut candidates once
        _reconnect_resent_count = 0   # counter: re-send webrtcResp+ICE on each reconnect (max 3)
        while not connected_ev.is_set() and time.monotonic() < deadline:
            # Drain incoming ICE candidates from the camera
            while True:
                try:
                    cand_dict = ice_q.get_nowait()
                except asyncio.QueueEmpty:
                    break
                cand_line = cand_dict.get("candidate", "")
                if cand_line.startswith("candidate:"):
                    cand_line = cand_line[len("candidate:"):]
                # Strip non-standard trailing extensions (generation, network-cost)
                # that aioice's candidate_from_sdp cannot parse.
                import re as _re
                cand_line = _re.sub(r'\s+generation\s+\d+.*$', '', cand_line).strip()
                try:
                    ice_cand = candidate_from_sdp(cand_line)
                    _smid_val = cand_dict.get("sdpMid")
                    _smidx_val = cand_dict.get("sdpMLineIndex")
                    ice_cand.sdpMid = (
                        str(_smid_val) if _smid_val is not None else
                        (str(_smidx_val) if _smidx_val is not None else "0")
                    )
                    ice_cand.sdpMLineIndex = _smidx_val
                    await pc.addIceCandidate(ice_cand)
                    _status(f"addIceCandidate: {cand_line[:80]}")
                except Exception as exc:
                    _LOGGER.debug(
                        "async_open_webrtc_stream: addIceCandidate error: %s", exc
                    )
            # APK parity: the official app only relays the camera's REAL ICE
            # candidates verbatim (addIceCandidate of the signaled candidate string)
            # - it never fabricates host candidates from a LAN IP.  We used to
            # synthesize `typ host` candidates from cam_ip_q (setDevAttrNotif /
            # user-info IP) as an ICE-lite A001064 role-reversal fallback; that was
            # non-parity, so it is removed.  The camera's real candidates are still
            # processed below from its second webrtcResp SDP (second_answer_fut) and
            # from the iceCandidateReq trickle.  Drain the queue so it cannot grow.
            while True:
                try:
                    cam_ip_q.get_nowait()
                except asyncio.QueueEmpty:
                    break
            # Process camera's real second webrtcResp SDP for ICE candidates.
            # For LK.IPC.A001064 (role-reversal), the camera's counter-offer echoes
            # our SDP (so _rr_cam_ports has OUR ports, not camera's).  The camera's
            # real second webrtcResp (captured in second_answer_fut) typically
            # contains the camera's actual a=candidate: host lines with its real
            # ICE port.  Without this, aiortc probes wrong ports forever and ICE
            # times out.
            if not _second_ans_processed and second_answer_fut.done():
                _second_ans_processed = True
                try:
                    _sa_result = second_answer_fut.result()
                    _sa_sdp = (_sa_result or {}).get("sdp", "")
                    if _sa_sdp:
                        import re as _re3
                        _sa_midx = -1
                        _sa_smid = "0"
                        for _sa_ln in _re3.split(r'\r?\n', _sa_sdp):
                            if _sa_ln.startswith('m='):
                                _sa_midx += 1
                                _sa_smid = str(_sa_midx)
                            elif _sa_ln.startswith('a=candidate:'):
                                _sa_cand = _sa_ln[len('a=candidate:'):]
                                _sa_cand = _re3.sub(
                                    r'\s+generation\s+\d+.*$', '', _sa_cand
                                ).strip()
                                try:
                                    _sa_ice = candidate_from_sdp(_sa_cand)
                                    _sa_ice.sdpMid = _sa_smid
                                    _sa_ice.sdpMLineIndex = max(_sa_midx, 0)
                                    await pc.addIceCandidate(_sa_ice)
                                    _status(
                                        f"addIceCandidate (2nd-answer SDP):"
                                        f" {_sa_cand[:80]}"
                                    )
                                except Exception as _sa_ce:
                                    _LOGGER.debug(
                                        "addIceCandidate (2nd-answer SDP) error: %s",
                                        _sa_ce,
                                    )
                    else:
                        _status("second_answer_fut resolved but SDP is empty")
                except Exception as _sa_exc:
                    _LOGGER.debug("second_answer_fut result() error: %s", _sa_exc)
            # Periodic ICE state heartbeat so we know the loop is alive.
            if time.monotonic() - _last_ice_log >= 5.0:
                _status(
                    f"ICE wait  connState={pc.connectionState}"
                    f"  iceState={pc.iceConnectionState}"
                    f"  remaining={deadline - time.monotonic():.0f}s"
                )
                _last_ice_log = time.monotonic()
            # Mid-loop user/connect retry: if no camera IP yet and ~half of the
            # ICE timeout has elapsed, re-announce user presence so the camera
            # pushes setDevAttrNotif again.  Replaces the invented getDevAttrReq.
            _remaining = deadline - time.monotonic()
            if (not _userconnect_midloop_sent
                    and cam_ip_q.empty()
                    and _remaining > 0
                    and _remaining <= timeout / 2.0):
                outgoing_q.put_nowait((
                    f"iot/v1/cb/{user_id}/user/connect",
                    json.dumps({
                        "service": "user",
                        "method":  "connect",
                        "srcAddr": f"0.{user_id}",
                        "payload": {"timestamp": _mqtt_timestamp()},
                    }),
                ))
                _userconnect_midloop_sent = True
                _status("user/connect re-sent (mid-loop, waiting for camera IP)")
            # Re-send webrtcResp + ICE candidates if camera reconnected during ICE.
            # LK.IPC.A001064 performs a quickConn MQTT reconnect after receiving
            # our webrtcResp; the reconnect resets its ICE agent so it no longer
            # has our ICE credentials or candidates.  We must re-send webrtcResp
            # first (to restore credentials/fingerprint) then ICE candidates.
            # ICE candidates alone are not sufficient - without webrtcResp the
            # camera has no credentials to validate STUN, so it never probes us.
            # Guard: only for echo-only role-reversal cameras (_rr_ice_payloads
            # is empty for all other paths) and at most 3 times per session.
            if (camera_reconnect_ev.is_set()
                    and _reconnect_resent_count < 3
                    and _rr_ice_payloads):
                _reconnect_resent_count += 1
                camera_reconnect_ev.clear()
                _status(
                    f"camera reconnected during ICE (retry {_reconnect_resent_count})"
                    " - re-sending webrtcResp + ICE candidates"
                )
                await asyncio.sleep(1.5)  # let camera re-subscribe before re-send
                if _rr_webrtc_resp_topic and _rr_webrtc_resp_payload:
                    outgoing_q.put_nowait((_rr_webrtc_resp_topic, _rr_webrtc_resp_payload))
                for _rr_ice_p in _rr_ice_payloads:
                    outgoing_q.put_nowait(_rr_ice_p)
            await asyncio.sleep(0.1)

        if pc.connectionState not in ("connected", "completed"):
            outgoing_q.put_nowait(None)
            await pc.close()
            raise RuntimeError(
                f"async_open_webrtc_stream: ICE connection not established "
                f"(connState={pc.connectionState}"
                f"  iceState={pc.iceConnectionState}) within {timeout}s"
            )

        if recorder:
            await recorder.start()

        _LOGGER.info(
            "WebRTC stream open for %s (peerid=%s)", device_id, peer_id
        )
        return WebRTCSession(
            pc=pc,
            outgoing_q=outgoing_q,
            mqtt_fut=mqtt_fut,
            recorder=recorder,
            track_tasks=track_tasks,
            dc=_kvs_dc,
            audio_sender=_audio_sender,
            talk_track=_talk_track,
            talk_holder=_talk_holder,
        )
