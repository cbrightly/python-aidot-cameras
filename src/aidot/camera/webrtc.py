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
        """Begin two-way audio (push-to-talk): stream viewer→camera audio + open speaker.

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
            _LOGGER.debug("camera %s: swallowed exception", 'async_stop_talk', exc_info=True)
        self._talk_holder["provider"] = None
        return True

    async def stop(self) -> None:
        """Tear down the stream: close peer connection and MQTT session."""
        for task in self._track_tasks:
            task.cancel()
        if self._recorder is not None:
            try:
                await self._recorder.stop()
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception", 'stop', exc_info=True)
        # Send None sentinel to stop the MQTT session in its thread
        self._outgoing_q.put_nowait(None)
        await self._pc.close()
        try:
            await asyncio.wait_for(self._mqtt_fut, timeout=5.0)
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception", 'stop', exc_info=True)
