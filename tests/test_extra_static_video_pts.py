"""The camera can announce a video payload type our offer never carried.

Measured 2026-08-17 on an A000088: the answer prepends a bare
``m=video 9 UDP/TLS/RTP/SAVPF 0`` - no a=mid, no a=rtpmap, no direction -
ahead of an otherwise correct 3-section answer, and then transmits its video
on payload type 0 (557 kbps in 1114-byte packets, read off the wire; SRTP
leaves the RTP header in the clear). A second A000088 on the same host answers
with no such section and transmits on PT 101, so this is per-unit behaviour,
not the model.

aiortc adopts a remote payload type only when it is dynamic (96-127), so PT 0
is never negotiated and every packet on it is discarded by the router. Naming
those payload types is the first half of doing something about it.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.protocol import extra_static_video_pts

OFFER_VIDEO_PTS = {"97", "98", "99", "100", "101", "102"}

# The exact answer measured from the failing camera (192.168.0.123).
ANSWER_WITH_STATIC_PT = "\r\n".join([
    "v=0",
    "o=- 0 0 IN IP4 0.0.0.0",
    "s=-",
    "t=0 0",
    "m=video 9 UDP/TLS/RTP/SAVPF 0",
    "c=IN IP4 0.0.0.0",
    "m=audio 9 UDP/TLS/RTP/SAVPF 8",
    "a=mid:0",
    "a=sendonly",
    "a=rtpmap:8 PCMA/8000",
    "m=video 9 UDP/TLS/RTP/SAVPF 101 102",
    "a=mid:1",
    "a=sendonly",
    "a=rtpmap:101 H264/90000",
    "a=rtpmap:102 rtx/90000",
    "m=application 9 UDP/DTLS/SCTP webrtc-datachannel",
    "a=mid:2",
])

# The healthy camera on the same host (192.168.0.129), same minute.
ANSWER_HEALTHY = "\r\n".join([
    "v=0",
    "o=- 0 0 IN IP4 0.0.0.0",
    "s=-",
    "t=0 0",
    "m=audio 9 UDP/TLS/RTP/SAVPF 8",
    "a=mid:0",
    "a=rtpmap:8 PCMA/8000",
    "m=video 9 UDP/TLS/RTP/SAVPF 101 102",
    "a=mid:1",
    "a=rtpmap:101 H264/90000",
    "m=application 9 UDP/DTLS/SCTP webrtc-datachannel",
    "a=mid:2",
])


def test_names_the_static_pt_the_camera_announced():
    assert extra_static_video_pts(ANSWER_WITH_STATIC_PT, OFFER_VIDEO_PTS) == [0]


def test_healthy_answer_has_nothing_to_report():
    assert extra_static_video_pts(ANSWER_HEALTHY, OFFER_VIDEO_PTS) == []


def test_dynamic_pts_are_not_reported():
    """A dynamic PT we did not offer is aiortc's to adopt, not ours."""
    sdp = ANSWER_HEALTHY.replace(
        "m=video 9 UDP/TLS/RTP/SAVPF 101 102",
        "m=video 9 UDP/TLS/RTP/SAVPF 103",
    )
    assert extra_static_video_pts(sdp, OFFER_VIDEO_PTS) == []


def test_rejected_section_is_ignored():
    """Port 0 means the camera declined that section; it sends nothing on it."""
    sdp = ANSWER_WITH_STATIC_PT.replace(
        "m=video 9 UDP/TLS/RTP/SAVPF 0",
        "m=video 0 UDP/TLS/RTP/SAVPF 0",
    )
    assert extra_static_video_pts(sdp, OFFER_VIDEO_PTS) == []


def test_audio_static_pts_are_not_video():
    assert 8 not in extra_static_video_pts(ANSWER_WITH_STATIC_PT, OFFER_VIDEO_PTS)


def test_malformed_m_line_does_not_raise():
    assert extra_static_video_pts("m=video\r\nm=video 9\r\n", OFFER_VIDEO_PTS) == []


def test_duplicates_are_reported_once():
    sdp = ANSWER_WITH_STATIC_PT + "\r\nm=video 9 UDP/TLS/RTP/SAVPF 0"
    assert extra_static_video_pts(sdp, OFFER_VIDEO_PTS) == [0]


def test_lf_only_line_endings_are_handled():
    """Nothing guarantees CRLF once an SDP has been through a JSON round trip."""
    sdp = ANSWER_WITH_STATIC_PT.replace("\r\n", "\n")
    assert extra_static_video_pts(sdp, OFFER_VIDEO_PTS) == [0]
