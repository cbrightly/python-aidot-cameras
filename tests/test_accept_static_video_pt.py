"""Registering a static payload type against the live video receiver.

aiortc will not do this during negotiation: find_common_codecs adopts the
remote payload type only when it is dynamic (rtcpeerconnection.py), so an
answer announcing video on payload type 0 leaves the negotiated codec on OUR
payload type and the router discards every packet the camera sends.

So after the answer is applied we add the mapping ourselves, in the two places
that decide the packet's fate - the receiver's own codec table, which selects
the depacketizer per packet, and the transport's router, which decides whether
the packet reaches a receiver at all.

The vendored tree is not modified; these tests assert against its real router.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras._vendor.aiortc.rtcdtlstransport import RtpRouter
from aidot_cameras._vendor.aiortc.rtcrtpparameters import RTCRtpCodecParameters
from aidot_cameras.camera.webrtc_open import _accept_static_video_pts

H264 = RTCRtpCodecParameters(
    mimeType="video/H264", clockRate=90000, payloadType=101
)
PCMA = RTCRtpCodecParameters(
    mimeType="audio/PCMA", clockRate=8000, channels=1, payloadType=8
)


class _FakeReceiver:
    """Stands in for RTCRtpReceiver: same private codec table, by name."""

    def __init__(self, codec, transport):
        self._RTCRtpReceiver__codecs = {codec.payloadType: codec}
        self.transport = transport


class _FakeTransport:
    def __init__(self):
        self._rtp_router = RtpRouter()


class _FakeTransceiver:
    def __init__(self, kind, receiver):
        self.kind = kind
        self.receiver = receiver


class _FakePC:
    def __init__(self, transceivers):
        self._tcvrs = transceivers

    def getTransceivers(self):
        return self._tcvrs


class _Pkt:
    def __init__(self, payload_type, ssrc=0x51480688):
        self.payload_type = payload_type
        self.ssrc = ssrc


def _pc_with_video_and_audio():
    transport = _FakeTransport()
    video_rx = _FakeReceiver(H264, transport)
    audio_rx = _FakeReceiver(PCMA, transport)
    transport._rtp_router.register_receiver(
        video_rx, ssrcs=[], payload_types=[101], mid="1"
    )
    transport._rtp_router.register_receiver(
        audio_rx, ssrcs=[], payload_types=[8], mid="0"
    )
    pc = _FakePC([
        _FakeTransceiver("video", video_rx),
        _FakeTransceiver("audio", audio_rx),
    ])
    return pc, video_rx, audio_rx, transport


def test_registers_the_payload_type_in_both_places():
    pc, video_rx, _audio_rx, transport = _pc_with_video_and_audio()

    assert _accept_static_video_pts(pc, [0], "dev") == [0]

    codec = video_rx._RTCRtpReceiver__codecs[0]
    assert codec.mimeType == "video/H264"
    assert codec.payloadType == 0
    assert video_rx in transport._rtp_router.payload_type_table[0]


def test_a_packet_on_that_pt_now_routes_to_video():
    """The router is the gate: before this, route_rtp returned None."""
    pc, video_rx, _audio_rx, transport = _pc_with_video_and_audio()

    assert transport._rtp_router.route_rtp(_Pkt(0)) is None
    _accept_static_video_pts(pc, [0], "dev")
    assert transport._rtp_router.route_rtp(_Pkt(0)) is video_rx


def test_the_h264_codec_is_copied_not_aliased():
    """Mutating the new entry must not rewrite the negotiated 101 codec."""
    pc, video_rx, _a, _t = _pc_with_video_and_audio()
    _accept_static_video_pts(pc, [0], "dev")
    assert video_rx._RTCRtpReceiver__codecs[101].payloadType == 101


def test_refuses_a_pt_another_receiver_already_claims():
    """Never steal audio's payload type - that would decode video as PCMA."""
    pc, video_rx, audio_rx, transport = _pc_with_video_and_audio()

    assert _accept_static_video_pts(pc, [8], "dev") == []
    assert transport._rtp_router.payload_type_table[8] == {audio_rx}
    assert 8 not in video_rx._RTCRtpReceiver__codecs


def test_no_video_receiver_is_not_an_error():
    transport = _FakeTransport()
    audio_rx = _FakeReceiver(PCMA, transport)
    pc = _FakePC([_FakeTransceiver("audio", audio_rx)])
    assert _accept_static_video_pts(pc, [0], "dev") == []


def test_a_receiver_with_no_h264_codec_is_left_alone():
    """Nothing to copy means nothing to register - and no exception."""
    transport = _FakeTransport()
    odd = _FakeReceiver(PCMA, transport)  # audio codec on a video transceiver
    pc = _FakePC([_FakeTransceiver("video", odd)])
    assert _accept_static_video_pts(pc, [0], "dev") == []


def test_empty_pt_list_is_a_no_op():
    pc, video_rx, _a, _t = _pc_with_video_and_audio()
    before = dict(video_rx._RTCRtpReceiver__codecs)
    assert _accept_static_video_pts(pc, [], "dev") == []
    assert video_rx._RTCRtpReceiver__codecs == before


def test_a_broken_peer_connection_does_not_raise():
    """Best-effort by design: this must never fail an otherwise working open."""

    class _Exploding:
        def getTransceivers(self):
            raise RuntimeError("boom")

    assert _accept_static_video_pts(_Exploding(), [0], "dev") == []


def test_env_seam_defaults_to_off():
    """Off until it is proven on hardware - the payload could be H.265."""
    from aidot_cameras.camera.webrtc_open import _static_video_pt_enabled

    assert _static_video_pt_enabled({}) is False


def test_env_seam_turns_it_on():
    from aidot_cameras.camera.webrtc_open import _static_video_pt_enabled

    assert _static_video_pt_enabled({"AIDOT_ACCEPT_STATIC_VIDEO_PT": "1"}) is True


def test_env_seam_ignores_junk():
    from aidot_cameras.camera.webrtc_open import _static_video_pt_enabled

    assert _static_video_pt_enabled(
        {"AIDOT_ACCEPT_STATIC_VIDEO_PT": "yes"}
    ) is False


# --- the rescue must not break a camera that did not need it ----------------

import asyncio  # noqa: E402

from aidot_cameras.camera.webrtc_open import (  # noqa: E402
    _rescue_static_video_pts,
    _video_receiver_is_receiving,
)


def test_a_receiver_that_has_taken_no_rtp_is_not_receiving():
    pc, _v, _a, _t = _pc_with_video_and_audio()
    assert _video_receiver_is_receiving(pc) is False


def test_one_routed_packet_makes_it_receiving():
    """route_rtp learns the SSRC, which is the evidence this reads."""
    pc, video_rx, _a, transport = _pc_with_video_and_audio()
    assert transport._rtp_router.route_rtp(_Pkt(101)) is video_rx
    assert _video_receiver_is_receiving(pc) is True


def test_audio_arriving_does_not_count_as_video():
    pc, _v, audio_rx, transport = _pc_with_video_and_audio()
    assert transport._rtp_router.route_rtp(_Pkt(8)) is audio_rx
    assert _video_receiver_is_receiving(pc) is False


def test_the_rescue_leaves_a_working_camera_alone():
    """The regression, 2026-08-17.

    The camera announces the bare `m=video ... 0` stub even when it is about
    to transmit perfectly good video on 101. Claiming payload type 0 as well
    let a second SSRC route into the same receiver - route_rtp learns it and
    keeps it - so two streams shared one jitter buffer, reassembly was
    corrupted, and the session produced 600 frames and not one keyframe while
    every other session that hour was healthy.
    """
    pc, video_rx, _a, transport = _pc_with_video_and_audio()
    transport._rtp_router.route_rtp(_Pkt(101))          # video is flowing

    assert asyncio.run(
        _rescue_static_video_pts(pc, [0], "dev", delay=0)) == []
    assert transport._rtp_router.route_rtp(_Pkt(0, ssrc=0x999)) is None
    assert 0 not in video_rx._RTCRtpReceiver__codecs


def test_the_rescue_still_saves_a_camera_that_sent_nothing():
    """The case it was written for: video only ever arrives on PT 0."""
    pc, video_rx, _a, transport = _pc_with_video_and_audio()

    assert asyncio.run(
        _rescue_static_video_pts(pc, [0], "dev", delay=0)) == [0]
    assert transport._rtp_router.route_rtp(_Pkt(0)) is video_rx


def test_a_broken_peer_connection_reads_as_not_receiving():
    class _Exploding:
        def getTransceivers(self):
            raise RuntimeError("boom")

    assert _video_receiver_is_receiving(_Exploding()) is False


def test_the_rescue_wait_has_an_env_seam():
    """8s was chosen, not measured - it must be retunable on hardware."""
    from aidot_cameras.camera.webrtc_open import (
        _STATIC_PT_RESCUE_S,
        _static_pt_rescue_delay,
    )

    assert _static_pt_rescue_delay({}) == _STATIC_PT_RESCUE_S
    assert _static_pt_rescue_delay({"AIDOT_STATIC_PT_RESCUE_S": "3"}) == 3.0
    assert _static_pt_rescue_delay(
        {"AIDOT_STATIC_PT_RESCUE_S": "junk"}) == _STATIC_PT_RESCUE_S
    assert _static_pt_rescue_delay(
        {"AIDOT_STATIC_PT_RESCUE_S": "-5"}) == _STATIC_PT_RESCUE_S
