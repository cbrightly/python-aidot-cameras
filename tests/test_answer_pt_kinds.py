"""Demuxing the camera's media by the ANSWER's payload numbering, not a table.

The bridge splits a BUNDLEd stream into ffmpeg's audio and video loopbacks by
RTP payload type. It used to do that from a fixed table -- 96/97/98 video,
0/8 audio -- and that table is wrong for this fleet.

Measured 2026-08-26 across 107 A001064 opens on the live camera: 15 of them
(14%) negotiated H265 on payload type 0, announced it correctly in the answer
as ``m=video ... 0`` with ``a=rtpmap:0 H265/90000``, and then sent 2668
full-size (1222 B) video packets on pt=0 while audio ran normally on pt=8
(verified on the wire, tshark by SSRC: two streams, 0x29d9/pt=0 video and
0x29d7/pt=8 audio). The fixed table posted every one of those video packets to
the AUDIO loopback, so no video was ever observed, the 75 s first-media wait
expired, and the serve launched into an empty stream and exited -- 82 s, then a
full reopen. The other 92 opens answered ``m=video ... 96`` and worked. Perfect
separation in both directions.

The same renumbering is already on record for the A000088 in
``test_answer_section_selection.py`` (PT 0 carrying H265), where the DTLS path
handles it by selecting sections by content. The SDES bridge was the one place
left trusting a static tuple.

The m-lines below are transcribed from the live log; the ``a=rtpmap:0
H265/90000`` line is inferred from ``video_pt_from_answer_sdp`` returning 97 on
every one of those 15 sessions (logged as "the camera's answer (97)"), which is
reachable only through the H265 branch of ``_SDP_VIDEO_PT_BY_CODEC``.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (
    answer_pt_kinds,
    rewrite_rtp_payload_type,
    video_pt_from_answer_sdp,
)

# The 92-of-107 shape: ordinary 3-section answer, video on 96.
_HEALTHY = (
    "v=0\r\n"
    "m=audio 9 RTP/SAVPF 8\r\n"
    "a=rtpmap:8 PCMA/8000\r\n"
    "m=video 9 RTP/SAVPF 96\r\n"
    "a=rtpmap:96 H264/90000\r\n"
    "m=application 9 SCTP webrtc-datachannel\r\n"
)

# The 15-of-107 shape: a leading video section numbering H265 as payload type 0.
_PT0_VIDEO = (
    "v=0\r\n"
    "m=video 9 RTP/SAVPF 0\r\n"
    "a=rtpmap:0 H265/90000\r\n"
    "m=audio 9 RTP/SAVPF 8\r\n"
    "a=rtpmap:8 PCMA/8000\r\n"
    "m=video 9 RTP/SAVPF 96\r\n"
    "a=rtpmap:96 H264/90000\r\n"
    "m=application 9 SCTP webrtc-datachannel\r\n"
)

# A000088 H265-capable firmware: legitimately four sections, reordered. A
# predicate keyed on the section COUNT would break this model, which works.
_A000088_REORDERED = (
    "v=0\r\n"
    "m=video 9 UDP/TLS/RTP/SAVPF 0\r\n"
    "a=rtpmap:0 H265/90000\r\n"
    "m=audio 9 UDP/TLS/RTP/SAVPF 8\r\n"
    "a=rtpmap:8 PCMA/8000\r\n"
    "m=video 9 UDP/TLS/RTP/SAVPF 101 102\r\n"
    "a=rtpmap:101 H264/90000\r\n"
    "m=application 9 DTLS/SCTP 5000\r\n"
)


def test_healthy_answer_maps_kinds():
    assert answer_pt_kinds(_HEALTHY) == {8: "audio", 96: "video"}


def test_pt0_video_is_reported_as_video_not_audio():
    # The whole defect in one assertion: without the answer, pt=0 reads as PCMU.
    kinds = answer_pt_kinds(_PT0_VIDEO)
    assert kinds[0] == "video"
    assert kinds[8] == "audio"
    assert kinds[96] == "video"


def test_pt0_video_answer_resolves_to_our_h265_payload_type():
    # What the bridge translates the wire PT into before ffmpeg sees it.
    assert video_pt_from_answer_sdp(_PT0_VIDEO) == 97


def test_a000088_reordered_four_sections_still_map_correctly():
    kinds = answer_pt_kinds(_A000088_REORDERED)
    assert kinds[0] == "video"
    assert kinds[8] == "audio"
    assert kinds[101] == "video"
    assert kinds[102] == "video"


def test_payload_type_claimed_by_both_kinds_is_dropped():
    # Ambiguity is not better evidence than the fallback table, so the map
    # declines to answer for that payload type rather than guessing.
    ambiguous = (
        "m=video 9 RTP/SAVPF 0\r\n"
        "m=audio 9 RTP/SAVPF 0 8\r\n"
    )
    kinds = answer_pt_kinds(ambiguous)
    assert 0 not in kinds
    assert kinds[8] == "audio"


def test_malformed_and_empty_answers_do_not_raise():
    assert answer_pt_kinds("") == {}
    # Out of contract, but the media path must not raise on it either.
    assert answer_pt_kinds(None) == {}  # type: ignore[arg-type]
    assert answer_pt_kinds("m=video\r\nm=audio 9\r\n") == {}
    # Non-numeric and out-of-range payload tokens are skipped, not raised on.
    assert answer_pt_kinds("m=video 9 RTP/SAVPF x 300 96\r\n") == {96: "video"}


def test_rewrite_payload_type_preserves_the_marker_bit():
    # byte1 = M(1) | PT(7). 0x80 is marker set + pt 0; 0x00 is marker clear.
    marked = bytes([0x80, 0x80]) + b"rest"
    assert rewrite_rtp_payload_type(marked, 97) == bytes([0x80, 0xE1]) + b"rest"
    unmarked = bytes([0x80, 0x00]) + b"rest"
    assert rewrite_rtp_payload_type(unmarked, 97) == bytes([0x80, 0x61]) + b"rest"


def test_rewrite_payload_type_leaves_the_rest_of_the_packet_alone():
    pkt = bytes([0x80, 0x00]) + bytes(range(2, 40))
    out = rewrite_rtp_payload_type(pkt, 96)
    assert out[0] == pkt[0]
    assert out[2:] == pkt[2:]
    assert out[1] & 0x7F == 96


def test_rewrite_payload_type_ignores_a_runt_packet():
    # Media path: a malformed packet is ffmpeg's to discard, not ours to raise.
    assert rewrite_rtp_payload_type(b"", 97) == b""
    assert rewrite_rtp_payload_type(b"\x80", 97) == b"\x80"
