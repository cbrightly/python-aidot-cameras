"""Keep RTCP feedback in the offer, so the camera has a reason to slow down.

The gap: vendor clients take 225-500 Kbps from an A001064 where we take
1900-3700 from the same camera. Nine hypotheses died there, all argued from what
some client sends. This one comes from what the camera itself answers.

Its answer SDP, captured from a web-app debug log against that exact device:

    a=rtpmap:103 H264/90000
    a=rtcp-fb:103 nack
    a=rtcp-fb:103 goog-remb
    a=rtcp-fb:103 transport-cc

The camera is willing to do NACK, receiver-estimated max bitrate and
transport-wide congestion control. `_compress_sdp_for_camera` drops every
`a=rtcp-fb` line, so we never ask for any of it, and a sender with no receiver
feedback has no reason to send at less than full rate.

The compressor still has a job. It exists because a full WebRTC offer - the web
client's is 7778 bytes - was observed making cameras drop the MQTT session on
receipt. But that bulk is `extmap`, `msid`, per-ssrc lines and the forty codec
variants a browser offers. For an offer carrying two or three payload types,
`rtcp-fb` is a handful of lines. The two concerns were conflated; only one of
them is about size.

So: keep `rtcp-fb`, but only for payload types that survived the narrowing.
Feedback for a codec we dropped is bytes describing something that will never be
sent.
"""
import pytest

from aidot_cameras.camera.protocol import _compress_sdp_for_camera

# Shaped like a browser offer: H264 kept, VP9 and its rtx dropped.
OFFER = "\r\n".join([
    "v=0",
    "o=- 5733354595989457600 2 IN IP4 127.0.0.1",
    "s=-",
    "t=0 0",
    "a=group:BUNDLE 0 1",
    "m=audio 9 UDP/TLS/RTP/SAVPF 8 111",
    "c=IN IP4 0.0.0.0",
    "a=mid:0",
    "a=sendrecv",
    "a=rtpmap:8 PCMA/8000",
    "a=rtcp-fb:8 nack",
    "a=rtcp-fb:8 goog-remb",
    "a=rtcp-fb:8 transport-cc",
    "a=rtpmap:111 opus/48000/2",
    "a=extmap:1 urn:ietf:params:rtp-hdrext:ssrc-audio-level",
    "m=video 9 UDP/TLS/RTP/SAVPF 103 104 100",
    "c=IN IP4 0.0.0.0",
    "a=mid:1",
    "a=recvonly",
    "a=rtpmap:103 H264/90000",
    "a=rtcp-fb:103 nack",
    "a=rtcp-fb:103 goog-remb",
    "a=rtcp-fb:103 transport-cc",
    "a=fmtp:103 profile-level-id=42001f",
    "a=rtpmap:104 rtx/90000",
    "a=fmtp:104 apt=103",
    "a=rtpmap:100 VP9/90000",
    "a=rtcp-fb:100 goog-remb",
    "a=rtcp-fb:100 transport-cc",
    "a=extmap:2 http://www.webrtc.org/experiments/rtp-hdrext/abs-send-time",
    "a=msid:stream track",
    "",
])


def _fb(sdp):
    return [l for l in sdp.splitlines() if l.startswith("a=rtcp-fb:")]


@pytest.mark.parametrize("want", [
    "a=rtcp-fb:103 nack",
    "a=rtcp-fb:103 goog-remb",
    "a=rtcp-fb:103 transport-cc",
])
def test_feedback_for_the_kept_video_codec_survives(want):
    assert want in _fb(_compress_sdp_for_camera(OFFER))


def test_feedback_for_the_kept_audio_codec_survives():
    assert "a=rtcp-fb:8 goog-remb" in _fb(_compress_sdp_for_camera(OFFER))


def test_feedback_for_a_dropped_codec_is_dropped_with_it():
    """VP9 does not survive the narrowing, so neither should its feedback.

    Describing congestion control for a codec that will never be sent is bytes
    spent on nothing, in an offer whose size is the reason the compressor
    exists.
    """
    kept = _fb(_compress_sdp_for_camera(OFFER))
    assert not [l for l in kept if l.startswith("a=rtcp-fb:100")]


def test_feedback_for_the_rtx_payload_is_dropped():
    """rtx carries no feedback of its own; it rides the codec it retransmits."""
    kept = _fb(_compress_sdp_for_camera(OFFER))
    assert not [l for l in kept if l.startswith("a=rtcp-fb:104")]


def test_the_bulk_is_still_removed():
    """The compressor's actual job is unchanged - this is the regression guard.

    extmap, msid and the unused codec are what make a browser offer 7778 bytes
    and were observed making cameras drop the MQTT session on receipt.
    """
    out = _compress_sdp_for_camera(OFFER)
    assert "a=extmap:" not in out
    assert "a=msid:stream" not in out
    assert "VP9/90000" not in out
    # opus is NOT removed - the audio keep-list is opus/PCMU/PCMA/AAC by
    # design, and this test is about the bulk, not the codec policy.


def test_the_size_cost_is_small():
    """Measured, not asserted from intuition.

    The whole argument for this change is that feedback is cheap and the bulk is
    not. If keeping it ever costs more than a couple of hundred bytes on a
    narrowed offer, that argument is wrong and this test should fail loudly.
    """
    with_fb = len(_compress_sdp_for_camera(OFFER))
    without = len("".join(
        l + "\r\n" for l in _compress_sdp_for_camera(OFFER).splitlines()
        if not l.startswith("a=rtcp-fb:")))
    assert with_fb - without <= 200, f"rtcp-fb cost {with_fb - without} bytes"
