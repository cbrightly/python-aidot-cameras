"""SDES-SRTP media session, split out of client.py.

SdesSession drives the SDES-SRTP camera path (battery/non-A000088 models): the
AVIO control channel, speaker/talk pump, and media bridge handoff. Behavior-
preserving extraction; re-exported from client.py.
"""

import asyncio
import logging
from typing import Callable, Optional

from .constants import SDES_TALK_PUMP_IDLE_TICK

_LOGGER = logging.getLogger(__name__)


class SdesSession:
    """Active SDES-SRTP stream session managed by an ffmpeg subprocess.

    Obtain via ``await DeviceClient.async_open_webrtc_stream(...)`` when the
    camera uses SDES-SRTP (``isDTLS == '0'``).
    Call ``await session.stop()`` when done.
    """

    def __init__(
        self,
        *,
        proc,
        sdp_path: str,
        outgoing_q,
        mqtt_fut,
        audio_sock=None,
        video_sock=None,
        cmd_chan=None,
        talk_state=None,
        media_progress=None,
    ) -> None:
        self._proc       = proc
        self._sdp_path   = sdp_path
        self._outgoing_q = outgoing_q
        self._mqtt_fut   = mqtt_fut
        self._audio_sock = audio_sock
        self._video_sock = video_sock
        # Mutable one-element list shared with the bridge thread: [0] holds the
        # monotonic timestamp of the last media packet the bridge forwarded (0.0
        # until first media).  The keepalive watchdog reads it to detect a camera
        # that stopped sending (battery teardown ~49-72s) and reconnect - ffmpeg
        # itself never exits on a dead UDP input, so wait_done() alone would hang.
        self._media_progress = media_progress if media_progress is not None else [0.0]
        # Mutable one-element list shared with the bridge thread.  Bridge sets
        # [0] to a callable(cmd, payload) once the SCTP channel is up.
        self._cmd_chan   = cmd_chan if cmd_chan is not None else [None]
        # Shared talk state (dict) for outbound two-way audio, or None when the
        # session was not opened talk-capable (offer stayed recvonly).  Populated
        # by the bridge (camera audio addr) and by async_start_talk (provider).
        self._talk_state  = talk_state
        self._talk_thread = None

    def _avio_cmd(self, cmd: int, payload: bytes = b"") -> bool:
        """Send an AVIO IOCtrl command via the encrypted SDES SCTP channel.

        Returns True if the command was dispatched, False if the channel is not
        yet established.
        """
        fn = self._cmd_chan[0]
        if fn is None:
            return False
        try:
            fn(cmd, payload)
            return True
        except Exception:
            return False

    @property
    def talk_supported(self) -> bool:
        """True if two-way audio can be used on this SDES session.

        Requires a talk-capable open (offer advertised sendrecv + a=ssrc).  The
        camera audio address is filled in by the bridge on first inbound audio;
        async_start_talk tolerates being called before that - the pump waits for
        the address before emitting.
        """
        return bool(self._talk_state)

    async def async_start_talk(
        self, pcm_provider: "Callable[[], Optional[bytes]]"
    ) -> bool:
        """Begin two-way audio: open the camera speaker and stream viewer->camera.

        ``pcm_provider()`` is polled once per 20 ms and returns 320 bytes of
        s16le PCM @ 8 kHz mono (short/empty -> silence) or ``None`` once the clip
        is finished.  Mirrors the DTLS WebRTCSession API.  Sends AVIO
        SPEAKERSTART (848) over the SCTP command channel (camera ACKs 851) and
        starts the SRTP PCMA pump.  False if talk isn't available on this session.
        """
        if not self.talk_supported:
            return False
        self._talk_state["provider"] = pcm_provider
        self._talk_state["stop"] = False
        self._talk_state["want_speaker"] = True
        # SPEAKERSTART(848) is sent by the BRIDGE thread (not here) once the SCTP
        # command channel is up - all SCTP DATA must stay on one thread or the
        # command races the heartbeat's TSN and the camera drops it.
        if self._talk_thread is None or not self._talk_thread.is_alive():
            import threading
            self._talk_thread = threading.Thread(
                target=_run_sdes_talk_pump, args=(self._talk_state,), daemon=True
            )
            self._talk_thread.start()
        return True

    async def async_stop_talk(self) -> bool:
        """End two-way audio: close the camera speaker and stop sending audio."""
        if self._talk_state is not None:
            # The bridge thread sends SPEAKERSTOP(849) when want_speaker flips off;
            # clearing the provider stops the pump from emitting audio.  Reset
            # spk_eligible_ts so a later clip on this SAME session waits the full
            # SPEAKERSTART delay again - a stale timestamp would make the next
            # SPEAKERSTART fire immediately and the camera ignore it (no 851 ACK).
            self._talk_state["want_speaker"] = False
            self._talk_state["provider"] = None
            self._talk_state["spk_eligible_ts"] = None
        return True

    @property
    def is_alive(self) -> bool:
        """True while ffmpeg is still running."""
        return self._proc.poll() is None

    @property
    def last_media_monotonic(self) -> float:
        """time.monotonic() of the last media packet forwarded, 0.0 if none yet."""
        return self._media_progress[0]

    @staticmethod
    def is_stalled(
        last_media: float,
        started_at: float,
        now: float,
        watchdog: float = 30.0,
        grace: float = 60.0,
    ) -> bool:
        """Decide if an SDES session should be torn down and reconnected.

        Once media has started (``last_media > 0``) a gap longer than ``watchdog``
        means the camera stopped sending (battery teardown); if media never
        started within ``grace`` of the open the session is dead too.  Pure
        function so the watchdog policy is unit-testable without a live session.
        """
        if last_media > 0.0:
            return (now - last_media) > watchdog
        return (now - started_at) > grace

    async def wait_done(self) -> int:
        """Wait (non-blocking poll) until ffmpeg exits; return its exit code."""
        while self._proc.poll() is None:
            await asyncio.sleep(0.5)
        return self._proc.returncode

    async def stop(self) -> None:
        """Tear down the stream: terminate ffmpeg and stop MQTT."""
        if self._talk_state is not None:
            # Ask the bridge to close the camera speaker, then give it a brief
            # window to emit SPEAKERSTOP(849) on the still-live SCTP channel BEFORE
            # we terminate ffmpeg/tear down - otherwise (e.g. stopping mid-clip) the
            # camera mic can stay occupied and the next talk session gets 851
            # "mic occupied".  No wait if the speaker was never opened, or if the
            # clip already ended (the pump clears want_speaker -> bridge sends 849).
            self._talk_state["want_speaker"] = False
            self._talk_state["spk_eligible_ts"] = None
            if self._talk_state.get("speaker_on"):
                for _ in range(40):                  # up to ~0.8s (> bridge select tick)
                    if not self._talk_state.get("speaker_on"):
                        break
                    await asyncio.sleep(0.02)
                # speaker_on flips once the bridge SENDS SPEAKERSTOP(849); give the
                # camera a brief window to PROCESS it over the still-live SCTP before
                # we tear down, so its talk channel is actually freed (avoids the
                # next session getting 851 "mic occupied").
                await asyncio.sleep(0.3)
            self._talk_state["stop"] = True
            self._talk_state["provider"] = None
        self._proc.terminate()
        try:
            _stop_loop = asyncio.get_running_loop()
            await _stop_loop.run_in_executor(None, lambda: self._proc.wait(5))
        except Exception:
            self._proc.kill()
        stderr_bytes = b""
        try:
            stderr_bytes = self._proc.stderr.read()
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception", 'stop', exc_info=True)
        if stderr_bytes:
            _LOGGER.warning("ffmpeg SDES stderr:\n%s", stderr_bytes.decode(errors="replace"))
        import os
        try:
            os.unlink(self._sdp_path)
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception", 'stop', exc_info=True)
        for _sock in (self._audio_sock, self._video_sock):
            if _sock is not None:
                try:
                    _sock.close()
                except Exception:
                    _LOGGER.debug("camera %s: swallowed exception", 'stop', exc_info=True)
        self._outgoing_q.put_nowait(None)
        try:
            await asyncio.wait_for(self._mqtt_fut, timeout=5.0)
        except Exception:
            _LOGGER.debug("camera %s: swallowed exception", 'stop', exc_info=True)


def _run_sdes_talk_pump(state: dict) -> None:
    """Outbound talk pump for SDES sessions (runs in a daemon thread).

    Reads the shared ``state`` dict (filled by the bridge + SdesSession): emits
    one SRTP PCMA (PT=8) packet per 20 ms from ``state['provider']`` to the
    camera's audio address (``state['src']`` via ``state['sock']``), encrypted
    with our offer key at our offer SSRC.  Waits until the camera audio address
    is known before emitting, idles cheaply (20 ms tick) when no provider is set,
    and exits when ``state['stop']`` is set.  This is the validated spike pump
    with a live provider in place of the test tone.
    """
    import time as _t
    import struct as _st
    import base64 as _b64
    try:
        import pylibsrtp as _pls
    except Exception:
        return
    from ..g711 import pcm_to_alaw

    _tx = None
    _seq = 1
    _ts = 0
    _next = _t.time()
    while not state.get("stop"):
        _provider = state.get("provider")
        _src = state.get("src")
        _sock = state.get("sock")
        # Emit only after the bridge thread has opened the camera speaker
        # (speaker_on).  SPEAKERSTART/STOP are sent on the bridge thread to keep
        # all SCTP DATA on one thread; audio is plain SRTP on the media socket
        # (no shared SCTP state) so it is safe to send from here.
        if (_provider is not None and _src is not None and _sock is not None
                and state.get("speaker_on")):
            if _tx is None:
                try:
                    _pol = _pls.Policy(
                        key=_b64.b64decode(state["key"]),
                        ssrc_type=_pls.Policy.SSRC_SPECIFIC,
                        ssrc_value=int(state["ssrc"]),
                        srtp_profile=_pls.Policy.SRTP_PROFILE_AES128_CM_SHA1_80,
                    )
                    _pol.allow_repeat_tx = True
                    _tx = _pls.Session(policy=_pol)
                except Exception:
                    return
            try:
                _pcm = _provider()
            except Exception:
                _pcm = None
            if _pcm is None:
                # Clip finished - ask the bridge to SPEAKERSTOP and stop emitting;
                # re-arm the SPEAKERSTART delay for any later clip on this session.
                state["provider"] = None
                state["want_speaker"] = False
                state["spk_eligible_ts"] = None
            else:
                _alaw = pcm_to_alaw(_pcm)
                if _alaw:
                    _hdr = _st.pack('!BBHII', 0x80, 8,
                                    _seq & 0xFFFF, _ts & 0xFFFFFFFF, int(state["ssrc"]))
                    try:
                        _sock.sendto(_tx.protect(_hdr + _alaw), _src)
                    except Exception:
                        _LOGGER.debug("camera %s: swallowed exception", '_run_sdes_talk_pump', exc_info=True)
                    _seq = (_seq + 1) & 0xFFFF
                    _ts = (_ts + len(_alaw)) & 0xFFFFFFFF
            # Active talk: hold 20 ms pacing for the audio cadence.
            _next += 0.02
            _d = _next - _t.time()
            if _d > 0:
                _t.sleep(_d)
            else:
                _next = _t.time()
        else:
            # Idle (talk not active / speaker not open): tick slowly so the
            # thread does not busy-spin for the whole session lifetime.
            _t.sleep(SDES_TALK_PUMP_IDLE_TICK)
            _next = _t.time()
