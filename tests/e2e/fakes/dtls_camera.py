"""A fake DTLS camera (A000088 / M3 Pro) with a real media plane.

``FakeDtlsCamera`` answers the client's ``webrtcReq`` with a real aiortc peer
connection (the library's vendored copy) on this host: ICE and the DTLS
handshake run for real over UDP, and the camera then sends H.264 video. The
certificate it presents is one the test holds, so the test decides when the
camera keeps its certificate and when it comes back with a new one.

What it copies from the real camera, because the client depends on it:

- the answer carries no fingerprint the client can use: its
  ``a=fingerprint:sha-256`` lines are EMPTY, as the A000088's are. The client
  fills in a placeholder, so the only thing that can refuse the camera's
  certificate is the camera's pin (learned, or AIDOT_DTLS_PINNED_FP).
  ``fingerprint_mode="echo"`` puts the client's OWN fingerprint in the answer
  instead. The client checks a fingerprint an answer carries, so it refuses
  that handshake whatever the pin says; the mode is there to show it.
- ICE candidates trickle. The client's offer carries none (only
  end-of-candidates) and its candidates follow as ``iceCandidateReq``; the
  camera's own ride in its ``webrtcResp`` answer.
- H.264 only, as the camera sends.

STUN and TURN are off on the camera side too, so a handshake never leaves
this host.

``reboot(new_certificate=...)`` ends every session, as a power cycle does,
and optionally makes a new certificate, as a factory reset does (or firmware
that makes one per boot).
"""

import asyncio
import re

from tests.e2e.fakes.signaling import FakeCameraSignaling


def _aiortc():
    """The library's vendored aiortc: the fake needs no second copy."""
    from aidot_cameras._vendor import aiortc

    return aiortc


def make_certificate():
    """A fresh self-signed DTLS certificate, as a camera makes on reset."""
    return _aiortc().RTCCertificate.generateCertificate()


def certificate_fingerprint(certificate) -> str:
    """``certificate``'s sha-256 fingerprint as the pin store keeps it:
    lowercase hex with no colons (``dtls_pins.normalize_fingerprint``)."""
    for fp in certificate.getFingerprints():
        if fp.algorithm == "sha-256":
            return fp.value.replace(":", "").lower()
    raise ValueError("certificate has no sha-256 fingerprint")


def _video_track():
    """A small synthetic picture source; aiortc encodes it as H.264."""
    import av

    class _Frames(_aiortc().VideoStreamTrack):
        def __init__(self) -> None:
            super().__init__()
            self._n = 0

        async def recv(self):
            pts, time_base = await self.next_timestamp()
            frame = av.VideoFrame(width=320, height=240, format="yuv420p")
            shade = (self._n * 8) % 256
            for plane, value in zip(frame.planes, (shade, 128, 128)):
                plane.update(bytes([value]) * plane.buffer_size)
            self._n += 1
            frame.pts = pts
            frame.time_base = time_base
            return frame

    return _Frames()


class FakeDtlsCamera(FakeCameraSignaling):
    """An A000088-shaped camera that answers with a real DTLS peer."""

    def __init__(
        self,
        broker_url: str,
        *,
        device_id: str,
        user_id: str,
        client_id: str | None = None,
        certificate=None,
        fingerprint_mode: str = "empty",
    ) -> None:
        super().__init__(
            broker_url, device_id=device_id, user_id=user_id, client_id=client_id
        )
        if fingerprint_mode not in ("empty", "echo"):
            raise ValueError("fingerprint_mode is 'empty' or 'echo'")
        self.certificate = certificate or make_certificate()
        self.fingerprint_mode = fingerprint_mode
        #: the fingerprint presented on each answered offer, in order
        self.sessions: list[str] = []
        #: the camera side's DTLS state for each session, as it last changed
        self.dtls_states: list[str] = []
        self.boots = 1
        self._loop: asyncio.AbstractEventLoop | None = None
        # peerid -> the latest session's RTCPeerConnection (None while it is
        # being built); trickled candidates go to it
        self._pcs: dict = {}
        self._older_pcs: list = []
        self._pending_candidates: dict = {}  # peerid -> [candidate dict]
        self._answered_ufrags: set = set()

    @property
    def fingerprint(self) -> str:
        """The fingerprint of the certificate the camera presents now."""
        return certificate_fingerprint(self.certificate)

    # -- lifecycle ---------------------------------------------------------- #

    async def start(self, timeout: float = 10.0) -> "FakeDtlsCamera":
        # paho calls back on its own thread; the peer connections live on the
        # test's loop, and every media-plane step is handed over to it.
        self._loop = asyncio.get_running_loop()
        await super().start(timeout)
        return self

    async def close_sessions(self) -> None:
        """End every session the camera holds."""
        pcs = [pc for pc in self._pcs.values() if pc is not None] + self._older_pcs
        self._pcs, self._older_pcs, self._pending_candidates = {}, [], {}
        for pc in pcs:
            try:
                await pc.close()
            except Exception:
                pass

    async def reboot(self, new_certificate: bool) -> None:
        """Power-cycle the camera: every session ends.

        ``new_certificate`` True is a factory reset (or firmware that makes a
        certificate per boot); False keeps the certificate it had.
        """
        await self.close_sessions()
        if new_certificate:
            self.certificate = make_certificate()
        self.boots += 1

    async def aclose(self) -> None:
        await self.close_sessions()
        self.stop()

    async def __aexit__(self, *_exc) -> None:
        await self.aclose()

    # -- signaling hooks (paho thread) -------------------------------------- #

    def _on_loop(self, coro) -> None:
        assert self._loop is not None, "start() the camera first"
        asyncio.run_coroutine_threadsafe(coro, self._loop)

    def on_webrtc_req(self, inner: dict, body: dict) -> None:
        if self.busy_ack is not None:
            super().on_webrtc_req(inner, body)
            return
        peer_id = inner.get("peerid")
        wp = inner.get("wPayload") or {}
        offer_sdp = (wp.get("offer") or {}).get("sdp") or (
            inner.get("offer") or {}
        ).get("sdp", "")
        # The client re-sends an offer it has no answer to (same offer, fresh
        # seq): one answer per offer. A new open may reuse the peer id, so an
        # offer is told apart by its ICE credentials, not its peer id.
        ufrag = re.search(r"(?m)^a=ice-ufrag:(\S+)", offer_sdp)
        key = (peer_id, ufrag.group(1) if ufrag else offer_sdp)
        if key in self._answered_ufrags:
            return
        self._answered_ufrags.add(key)
        self._on_loop(self._answer(peer_id, offer_sdp))

    def on_ice_candidate(self, inner: dict, body: dict) -> None:
        cand = inner.get("candidate") or (inner.get("wPayload") or {}).get("candidate")
        if isinstance(cand, dict) and cand.get("candidate"):
            self._on_loop(self._add_candidate(inner.get("peerid"), cand))

    # -- the media plane (event loop) --------------------------------------- #

    async def _add_candidate(self, peer_id, cand: dict) -> None:
        pc = self._pcs.get(peer_id)
        if pc is None:
            # Not answered yet: held until the answer is out.
            self._pending_candidates.setdefault(peer_id, []).append(cand)
            return
        line = cand["candidate"]
        if line.startswith("candidate:"):
            line = line[len("candidate:") :]
        try:
            ice = _aiortc().sdp.candidate_from_sdp(line)
        except Exception:
            return
        ice.sdpMid = str(cand.get("sdpMid", "0"))
        ice.sdpMLineIndex = cand.get("sdpMLineIndex", 0)
        try:
            await pc.addIceCandidate(ice)
        except Exception:
            pass

    async def _answer(self, peer_id, offer_sdp: str) -> None:
        previous = self._pcs.get(peer_id)
        if previous is not None:
            self._older_pcs.append(previous)
        self._pcs[peer_id] = None
        self._pending_candidates[peer_id] = []
        try:
            answer_sdp = await self._build_answer(peer_id, offer_sdp)
        except Exception:
            import traceback

            traceback.print_exc()
            return
        self._publish(
            "webrtcResp",
            {
                "peerid": peer_id,
                "offer": {"type": "answer", "sdp": answer_sdp},
                "trackId": 0,
            },
        )
        for cand in self._pending_candidates.pop(peer_id, None) or []:
            await self._add_candidate(peer_id, cand)

    async def _build_answer(self, peer_id, offer_sdp: str) -> str:
        aiortc = _aiortc()
        pc = aiortc.RTCPeerConnection(aiortc.RTCConfiguration(iceServers=[]))
        # aiortc makes a certificate per peer connection; this camera's
        # identity is the one the test holds.
        pc._RTCPeerConnection__certificates = [self.certificate]
        index = len(self.sessions)
        self.sessions.append(self.fingerprint)
        self.dtls_states.append("new")

        # The offer's candidates trickle after it, so drop its
        # end-of-candidates: kept, it tells the ICE agent the client has none.
        offer_sdp = re.sub(r"(?m)^a=end-of-candidates\r?\n", "", offer_sdp)
        await pc.setRemoteDescription(
            aiortc.RTCSessionDescription(sdp=offer_sdp, type="offer")
        )
        h264 = [
            c
            for c in aiortc.RTCRtpSender.getCapabilities("video").codecs
            if c.mimeType in ("video/H264", "video/rtx")
        ]
        for tc in pc.getTransceivers():
            if tc.kind == "video":
                tc.setCodecPreferences(h264)
                tc.direction = "sendonly"
                tc.sender.replaceTrack(_video_track())

        await pc.setLocalDescription(await pc.createAnswer())
        self._pcs[peer_id] = pc

        dtls = pc.getTransceivers()[0].sender.transport

        @dtls.on("statechange")
        def _on_state() -> None:
            self.dtls_states[index] = dtls.state

        return self._shape_answer(pc.localDescription.sdp, offer_sdp)

    def _shape_answer(self, sdp: str, offer_sdp: str) -> str:
        """Give the answer's fingerprint the shape the A000088's has."""
        sdp = re.sub(r"(?m)^a=fingerprint:sha-(?:384|512)[^\r\n]*\r?\n", "", sdp)
        if self.fingerprint_mode == "empty":
            value = ""
        else:
            ours = re.search(r"(?m)^a=fingerprint:sha-256 ([^\r\n]+)", offer_sdp)
            value = ours.group(1) if ours else ""
        return re.sub(
            r"(?m)^a=fingerprint:sha-256[^\r\n]*", f"a=fingerprint:sha-256 {value}", sdp
        )
