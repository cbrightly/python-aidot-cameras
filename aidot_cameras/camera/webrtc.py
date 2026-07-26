"""WebRTC (DTLS-SRTP) media session, split out of client.py.

Thin wrapper around the aiortc PeerConnection for the A000088/DTLS camera path
(frame delivery, recording, runtime talk-transceiver toggling). Behavior-
preserving; re-exported from client.py.
"""

import asyncio
import logging
import random
import struct
import time
from typing import Any, Callable, Optional

_LOGGER = logging.getLogger(__name__)


class WebRTCSession:
    """Active WebRTC live-stream session for a liveType=2 AiDot camera.

    Obtain via ``await DeviceClient.async_open_webrtc_stream(...)``.
    Call ``await session.stop()`` when done.
    """

    def __init__(
        self,
        *,
        pc: Any,
        outgoing_q: Any,
        mqtt_fut: Any,
        recorder: Any,
        track_tasks: list,
        dc: Any = None,
        audio_sender: Any = None,
        talk_track: Any = None,
        talk_holder: Any = None,
    ) -> None:
        self._pc          = pc
        self._outgoing_q  = outgoing_q
        self._mqtt_fut    = mqtt_fut
        self._recorder    = recorder
        self._track_tasks = track_tasks
        self._dc          = dc  # RTCDataChannel for AVIO IOCtrl commands
        # Two-way audio (talk): the sendrecv audio RTCRtpSender, an idle PCMA talk
        # track, and a mutable {"provider": callable|None} holder the track reads from.
        self._audio_sender = audio_sender
        self._talk_track   = talk_track
        self._talk_holder  = talk_holder if talk_holder is not None else {"provider": None}

    def _avio_cmd(self, cmd: int, payload: bytes = b"") -> bool:
        """Send an AVIO IOCtrl command via the DTLS SCTP DataChannel.

        Returns True if the command was dispatched, False if DC not open.
        """
        if self._dc is None:
            return False
        try:
            _seq = random.randint(0, 0x7FFFFFFF)
            _ts_ms = int(time.time() * 1000)
            _hdr = struct.pack("<IIqII4x", _seq, cmd, _ts_ms, len(payload), 0)
            self._dc.send(_hdr + payload)
            return True
        except Exception:
            return False

    @property
    def talk_supported(self) -> bool:
        """True if two-way audio can be used on this session (sender + track present)."""
        return self._talk_track is not None and self._audio_sender is not None

    async def async_start_talk(
        self, pcm_provider: "Callable[[], Optional[bytes]]"
    ) -> bool:
        """Begin two-way audio (push-to-talk): stream viewer->camera audio + open speaker.

        ``pcm_provider()`` is polled once per 20 ms and must return 320 bytes of
        signed-16-bit little-endian PCM @ 8 kHz mono (or ``None``/short for silence);
        aiortc encodes it to PCMA (PT=8) on the wire.

        Mirrors the official app: ``replaceTrack`` enables the otherwise-idle audio
        sender (the app's ``setEnabled(true)``) and we send AVIO SPEAKERSTART (848) to
        open the camera speaker. Returns False if talk isn't available on this session.
        """
        if not self.talk_supported:
            return False
        self._talk_holder["provider"] = pcm_provider
        try:
            self._audio_sender.replaceTrack(self._talk_track)
        except Exception:
            self._talk_holder["provider"] = None
            return False
        # IOTYPE_USER_IPCAM_SPEAKERSTART = 848 (AVIOCTRLDEFs.java), 8-byte channel=0 payload.
        self._avio_cmd(848, b"\x00" * 8)
        self._talk_holder["was_active"] = True  # ensure stop() releases the speaker
        return True

    async def async_stop_talk(self) -> bool:
        """End two-way audio: close the camera speaker and stop sending audio.

        Sends AVIO SPEAKERSTOP (849) and detaches the talk track (``replaceTrack(None)``
        - the app's ``setEnabled(false)``: present-but-idle, no RTP, no renegotiation).
        """
        # IOTYPE_USER_IPCAM_SPEAKERSTOP = 849.
        self._avio_cmd(849, b"\x00" * 8)
        try:
            if self._audio_sender is not None:
                self._audio_sender.replaceTrack(None)
        except Exception:
            _LOGGER.debug("swallowed exception in %s", 'async_stop_talk', exc_info=True)
        self._talk_holder["provider"] = None
        return True

    def _ice_path(self) -> "Optional[list]":
        """The nominated ICE candidate pair(s), read from aiortc/aioice internals.

        Returns None if nothing is nominated yet.  No public aiortc API exposes
        the selected pair, so this reaches into private attributes; it is fully
        guarded so a library upgrade that renames them degrades to None instead
        of raising.  Each entry gives the local/remote candidate *type*
        (host/srflx/relay/prflx) - the key signal for relay-vs-direct routing.
        """
        pc = self._pc
        transports = (getattr(pc, "_RTCPeerConnection__iceTransports", None)
                      or getattr(pc, "_iceTransports", None) or [])
        pairs = []
        for it in transports:
            conn = getattr(it, "_connection", None) or getattr(it, "connection", None)
            nominated = getattr(conn, "_nominated", None) or {}
            for comp, pair in sorted(nominated.items()):
                lc, rc = pair.local_candidate, pair.remote_candidate
                pairs.append({
                    "component": comp,
                    "local_type": lc.type, "local": f"{lc.host}:{lc.port}",
                    "remote_type": rc.type, "remote": f"{rc.host}:{rc.port}",
                    "transport": lc.transport,
                })
        return pairs or None

    async def get_stats(self) -> "dict[str, Any]":
        """Best-effort connection-health snapshot for diagnostics.

        Returns the nominated ICE path plus per-direction inbound RTP counters
        (packets received/lost, loss %, jitter).  Never raises: on any failure
        it returns whatever it gathered alongside an ``error`` key, so callers
        (HA diagnostics, the camera_diag CLI) can log it without guarding.
        """
        out: "dict[str, Any]" = {"ice": None, "inbound": []}
        try:
            out["ice"] = self._ice_path()
        except Exception as exc:  # pragma: no cover - private-attr drift
            out["ice_error"] = str(exc)
        try:
            report = await self._pc.getStats()
            for stat in report.values():
                if getattr(stat, "type", None) != "inbound-rtp":
                    continue
                recv = getattr(stat, "packetsReceived", 0) or 0
                lost = getattr(stat, "packetsLost", 0) or 0
                total = recv + max(lost, 0)
                out["inbound"].append({
                    "kind": getattr(stat, "kind", "?"),
                    "packets_received": recv,
                    "packets_lost": lost,
                    "loss_pct": round(100.0 * lost / total, 2) if total else 0.0,
                    "jitter": getattr(stat, "jitter", None),
                })
        except Exception as exc:
            out["error"] = str(exc)
        return out

    async def stop(self) -> None:
        """Tear down the stream: close peer connection and MQTT session."""
        # If two-way talk was active this session, RELEASE the camera speaker
        # before closing the PeerConnection: send SPEAKERSTOP(849), idle the track,
        # and give the SCTP DataChannel a brief flush window so the camera actually
        # processes the release.  Otherwise the camera keeps its talk channel bound
        # to this (now-dead) connection and the next app/HA push-to-talk gets 851
        # "mic occupied".  Idempotent (harmless if the speaker was already closed).
        if self.talk_supported and self._talk_holder.get("was_active"):
            try:
                self._avio_cmd(849, b"\x00" * 8)
                if self._audio_sender is not None:
                    self._audio_sender.replaceTrack(None)
                self._talk_holder["provider"] = None
                self._talk_holder["was_active"] = False
                await asyncio.sleep(0.4)   # let the DataChannel deliver SPEAKERSTOP
            except Exception:
                _LOGGER.debug("swallowed exception in %s", 'stop', exc_info=True)
        for task in self._track_tasks:
            task.cancel()
        if self._recorder is not None:
            try:
                await self._recorder.stop()
            except Exception:
                _LOGGER.debug("swallowed exception in %s", 'stop', exc_info=True)
        # Send None sentinel to stop the MQTT session in its thread
        self._outgoing_q.put_nowait(None)
        await self._pc.close()
        try:
            await asyncio.wait_for(self._mqtt_fut, timeout=5.0)
        except Exception:
            _LOGGER.debug("swallowed exception in %s", 'stop', exc_info=True)
