"""Pinning the camera-facing OFFER to a single video codec.

The offer sent in webrtcReq advertises BOTH H264 (96) and H265 (97) on its
video m-line and expresses no preference, so the camera picks which to send in
its answer. Measured on an A001064 across eleven sessions in one afternoon it
picked H264 nine times and H265 twice for an otherwise identical request, and
the codec it picked determined the resolution (H264 -> 1280x720, H265 ->
2560x1440, 11 of 11). An unpinned choice is what makes the profile unpredictable.

It is the OFFER, not the answer. Traced live with every status line printed,
this path sends webrtcReq carrying our offer and then reports "Using camera's
video SRTP key from answer" - the camera answers, we do not. An earlier version
pinned the answer builder instead, which for this camera is dead code, and the
arms still came out looking like the pin had worked.

Default is unpinned, so behaviour is byte-identical to today unless the
override is set. The SDES offer path is shared by every SDES camera and this
project's CHANGELOG records fleet-wide blackouts from changes to shared paths,
so "off changes nothing" is the property that matters most here.
"""
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (  # noqa: E402
    _resolve_sdes_video_pt,
    narrow_sdp_payload_types,
)

_ENV = "AIDOT_SDES_VIDEO_PT"

#: The offer template built at sdes_open.py:1082, verbatim in shape.
_OFFER = (
    "v=0\r\n"
    "o=- 1 1 IN IP4 10.0.0.1\r\n"
    "s=-\r\n"
    "t=0 0\r\n"
    "m=audio 40000 RTP/SAVPF 0 8\r\n"
    "c=IN IP4 10.0.0.1\r\n"
    "a=sendonly\r\n"
    "a=rtpmap:0 PCMU/8000\r\n"
    "a=rtpmap:8 PCMA/8000\r\n"
    "m=video 40002 RTP/SAVPF 96 97\r\n"
    "c=IN IP4 10.0.0.1\r\n"
    "a=sendonly\r\n"
    "a=rtpmap:96 H264/90000\r\n"
    "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
    "profile-level-id=42e01f\r\n"
    "a=rtpmap:97 H265/90000\r\n"
    "a=fmtp:97 level-id=93\r\n"
    "a=rtcp-mux\r\n"
)


def test_unset_means_unpinned(monkeypatch):
    monkeypatch.delenv(_ENV, raising=False)
    assert _resolve_sdes_video_pt() is None


@pytest.mark.parametrize("raw,want", [("96", 96), ("97", 97), (" 97 ", 97)])
def test_a_payload_number_is_read(monkeypatch, raw, want):
    monkeypatch.setenv(_ENV, raw)
    assert _resolve_sdes_video_pt() == want


@pytest.mark.parametrize("raw", ["", "h265", "abc", "-1", "0", "9.5", "999"])
def test_anything_unusable_falls_back_to_unpinned(monkeypatch, raw):
    """Failing closed to a pinned-but-wrong payload type would cost the picture
    on every SDES camera; falling back to today's behaviour cannot."""
    monkeypatch.setenv(_ENV, raw)
    assert _resolve_sdes_video_pt() is None


def test_unpinned_leaves_the_offer_byte_identical():
    assert narrow_sdp_payload_types(_OFFER, keep_video=None) == _OFFER


def test_pinning_h265_narrows_only_the_video_line():
    out = narrow_sdp_payload_types(_OFFER, keep_video=97)
    assert "m=video 40002 RTP/SAVPF 97\r\n" in out
    assert "a=rtpmap:97 H265/90000\r\n" in out
    assert "a=fmtp:97 level-id=93\r\n" in out
    assert "a=rtpmap:96 H264/90000" not in out
    assert "level-asymmetry-allowed" not in out
    # The audio line must survive untouched: the mpegts mux withholds its
    # PAT/PMT until every mapped stream produces a packet, so breaking audio
    # costs the video too.
    assert "m=audio 40000 RTP/SAVPF 0 8\r\n" in out
    assert "a=rtpmap:0 PCMU/8000\r\n" in out
    assert "a=rtpmap:8 PCMA/8000\r\n" in out


def test_pinning_h264_is_symmetric():
    out = narrow_sdp_payload_types(_OFFER, keep_video=96)
    assert "m=video 40002 RTP/SAVPF 96\r\n" in out
    assert "a=rtpmap:96 H264/90000\r\n" in out
    assert "a=rtpmap:97 H265/90000" not in out
    assert "a=fmtp:97 level-id=93" not in out


def test_a_payload_type_the_offer_does_not_advertise_changes_nothing():
    """Narrowing to something absent must not empty the m-line - a video line
    with no payload type at all would leave the camera nothing to send."""
    assert narrow_sdp_payload_types(_OFFER, keep_video=98) == _OFFER
