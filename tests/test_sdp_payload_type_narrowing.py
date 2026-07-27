"""The ffmpeg SDP must advertise ONE payload type per media line.

ffmpeg binds each RTP depacketizer to the first payload type on the m-line and
silently discards packets carrying any other one. The SDP lists both candidates
per line because the camera picks one per session, so whichever it actually sends
has to be promoted before launch.

On the video line, getting this wrong costs the picture - that was the known
H.265 bug. On the audio line it also costs the picture, and far less obviously:
the mpegts mux withholds its PAT/PMT until every mapped stream has produced a
packet, so an audio stream whose packets are all discarded leaves the consumer
with zero bytes, video included, while signaling looks perfectly healthy. That is
what made enabling serve audio look like it broke streaming outright.
"""
from aidot_cameras.camera.sdes_open import narrow_sdp_payload_types

_SDP = (
    "v=0\r\n"
    "o=- 1 1 IN IP4 0.0.0.0\r\n"
    "s=aidot-sdes-rx\r\n"
    "t=0 0\r\n"
    "m=audio 34162 RTP/AVP 0 8\r\n"
    "c=IN IP4 127.0.0.1\r\n"
    "a=rtpmap:0 PCMU/8000\r\n"
    "a=rtpmap:8 PCMA/8000\r\n"
    "m=video 38738 RTP/AVP 96 97\r\n"
    "c=IN IP4 127.0.0.1\r\n"
    "a=rtpmap:96 H264/90000\r\n"
    "a=rtpmap:97 H265/90000\r\n"
    "a=fmtp:96 packetization-mode=1\r\n"
)


def test_pcma_camera_gets_an_audio_line_advertising_only_pcma():
    # The live failure: the camera sends PCMA (8) but PCMU (0) is listed first, so
    # ffmpeg bound PCMU and discarded every audio packet.
    out = narrow_sdp_payload_types(_SDP, keep_video=96, keep_audio=8)
    assert "m=audio 34162 RTP/AVP 8\r\n" in out
    assert "a=rtpmap:8 PCMA/8000" in out
    assert "a=rtpmap:0 PCMU/8000" not in out


def test_pcmu_camera_gets_the_other_one():
    out = narrow_sdp_payload_types(_SDP, keep_video=96, keep_audio=0)
    assert "m=audio 34162 RTP/AVP 0\r\n" in out
    assert "a=rtpmap:0 PCMU/8000" in out
    assert "a=rtpmap:8 PCMA/8000" not in out


def test_h265_video_narrowing_still_works():
    # The pre-existing behaviour this generalises; it must not regress.
    out = narrow_sdp_payload_types(_SDP, keep_video=97, keep_audio=8)
    assert "m=video 38738 RTP/AVP 97\r\n" in out
    assert "a=rtpmap:97 H265/90000" in out
    assert "a=rtpmap:96 H264/90000" not in out
    assert "a=fmtp:96 " not in out          # the dropped codec's fmtp goes too


def test_h264_keeps_its_fmtp():
    out = narrow_sdp_payload_types(_SDP, keep_video=96, keep_audio=8)
    assert "a=fmtp:96 packetization-mode=1" in out


def test_none_leaves_a_line_untouched():
    # Video observed, audio not (or audio not being served): the audio line must
    # be left exactly as it was rather than half-rewritten.
    out = narrow_sdp_payload_types(_SDP, keep_video=96, keep_audio=None)
    assert "m=audio 34162 RTP/AVP 0 8\r\n" in out
    assert "a=rtpmap:0 PCMU/8000" in out
    assert "a=rtpmap:8 PCMA/8000" in out
    assert "m=video 38738 RTP/AVP 96\r\n" in out


def test_both_none_is_an_exact_passthrough():
    assert narrow_sdp_payload_types(_SDP) == _SDP


def test_line_endings_and_ordering_survive():
    # ffmpeg is strict about SDP framing; the rewrite must not reflow it.
    out = narrow_sdp_payload_types(_SDP, keep_video=96, keep_audio=8)
    assert out.endswith("\r\n")
    assert "\n\n" not in out
    assert out.index("m=audio") < out.index("m=video")   # stream order preserved
