"""The SRTP key-restart must rewrite the SDP the same way the primary path does.

It rebuilds the ffmpeg SDP from scratch and relaunches. Two things it got wrong,
each undoing a shipped fix:

- Transport. Every SDES model this library supports is in `_use_plain_rtp`: the
  bridge decrypts and forwards PLAIN RTP, so the primary SDP is RTP/AVP with no
  a=crypto. Writing RTP/SAVP makes ffmpeg authenticate already-decrypted packets,
  every HMAC check fails, and a working stream drops to zero bytes mid-session.
- Payload types. Hard-coding "0 8" and "96 97" discards the narrowing, and ffmpeg
  binds the FIRST type per line - so an H.265 camera loses all video, and with it
  the PAT/PMT and therefore the entire output.

These tests read the SDP `_build_restart_sdp` actually emits. An earlier version
read the module's SOURCE and checked that the substrings "narrow_sdp_payload_types(",
"keep_video=" and "keep_audio=" appeared somewhere in it, which is true of a call
that hard-codes `keep_video=96` - the exact regression described above. The payload
selection therefore lives INSIDE the builder: a caller free to pass a payload type
of its own is a caller free to reintroduce the hard-coding.
"""
import re

import aidot_cameras.camera.sdes_open as so

_AUDIO_PORT = 41000
_VIDEO_PORT = 41002
_KEY_A = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
_KEY_V = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"


def _build(**overrides) -> str:
    """The builder with a realistic plain-RTP H.264+PCMU session as the default."""
    kwargs = dict(
        ts=1234567890,
        lo_audio_port=_AUDIO_PORT,
        lo_video_port=_VIDEO_PORT,
        use_plain_rtp=True,
        srtp_key_audio=_KEY_A,
        srtp_key_video=_KEY_V,
        first_video_pt=96,
        answer_video_pt=None,
        first_audio_pt=0,
    )
    kwargs.update(overrides)
    return so._build_restart_sdp(**kwargs)


def _m_line(sdp: str, media: str) -> str:
    match = re.search(rf"^m={media} .*$", sdp, re.M)
    assert match, f"no m={media} line in:\n{sdp}"
    return match.group(0).rstrip("\r")


# --------------------------------------------------------------------- #
# Transport
# --------------------------------------------------------------------- #

def test_plain_rtp_session_gets_rtp_avp_media_lines():
    sdp = _build(use_plain_rtp=True)
    assert _m_line(sdp, "audio").startswith(f"m=audio {_AUDIO_PORT} RTP/AVP ")
    assert _m_line(sdp, "video").startswith(f"m=video {_VIDEO_PORT} RTP/AVP ")


def test_plain_rtp_session_carries_no_crypto_line():
    # The bridge hands ffmpeg packets it has already decrypted; an a=crypto line
    # makes ffmpeg authenticate them and every HMAC check fails.
    sdp = _build(use_plain_rtp=True)
    assert "a=crypto" not in sdp
    assert _KEY_A not in sdp
    assert _KEY_V not in sdp


def test_srtp_session_keeps_savp_and_both_inline_keys():
    # The other half of the same decision: when the transport IS secured, the
    # keys have to be there, one per media section, and not swapped.
    sdp = _build(use_plain_rtp=False)
    assert _m_line(sdp, "audio").startswith(f"m=audio {_AUDIO_PORT} RTP/SAVP ")
    assert _m_line(sdp, "video").startswith(f"m=video {_VIDEO_PORT} RTP/SAVP ")
    audio_sec, _, video_sec = sdp.partition("m=video")
    assert f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{_KEY_A}" in audio_sec
    assert f"a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{_KEY_V}" in video_sec


# --------------------------------------------------------------------- #
# Video payload type
# --------------------------------------------------------------------- #

def test_h265_session_narrows_the_video_line_to_97():
    # The regression this file exists for: a restart that emits "96 97" (or a
    # hard-coded 96) against an H.265 session binds ffmpeg's H.264
    # depacketizer, every video packet is discarded, and the mpegts mux never
    # emits its PAT/PMT - so the consumer receives zero bytes, audio included.
    sdp = _build(first_video_pt=97)
    assert _m_line(sdp, "video") == f"m=video {_VIDEO_PORT} RTP/AVP 97"
    assert "a=rtpmap:97 H265/90000" in sdp
    assert "a=rtpmap:96" not in sdp
    assert "a=fmtp:96" not in sdp


def test_h264_session_narrows_the_video_line_to_96():
    sdp = _build(first_video_pt=96)
    assert _m_line(sdp, "video") == f"m=video {_VIDEO_PORT} RTP/AVP 96"
    assert "a=rtpmap:96 H264/90000" in sdp
    assert "a=rtpmap:97" not in sdp
    assert "a=fmtp:97" not in sdp


def test_a_session_that_saw_no_video_narrows_from_the_answer_instead():
    # No video packet observed, but the camera's answer named the codec it
    # agreed to send. Without this fallback the restart reproduces the
    # unnarrowed template on every watchdog cycle.
    sdp = _build(first_video_pt=None, answer_video_pt=97)
    assert _m_line(sdp, "video") == f"m=video {_VIDEO_PORT} RTP/AVP 97"


def test_an_unknown_video_type_falls_back_rather_than_narrowing_to_it():
    # 99 is not one of the types the template advertises; narrowing to it would
    # leave the camera nothing to send.
    sdp = _build(first_video_pt=99, answer_video_pt=96)
    assert _m_line(sdp, "video") == f"m=video {_VIDEO_PORT} RTP/AVP 96"


def test_no_video_information_at_all_leaves_both_codecs_advertised():
    sdp = _build(first_video_pt=None, answer_video_pt=None)
    assert _m_line(sdp, "video") == f"m=video {_VIDEO_PORT} RTP/AVP 96 97"
    assert "a=rtpmap:96 H264/90000" in sdp
    assert "a=rtpmap:97 H265/90000" in sdp


# --------------------------------------------------------------------- #
# Audio payload type
# --------------------------------------------------------------------- #

def test_pcma_session_narrows_the_audio_line_to_8():
    # An audio stream whose packets are all discarded withholds the PAT/PMT
    # just as effectively as a broken video line.
    sdp = _build(first_audio_pt=8)
    assert _m_line(sdp, "audio") == f"m=audio {_AUDIO_PORT} RTP/AVP 8"
    assert "a=rtpmap:8 PCMA/8000" in sdp
    assert "a=rtpmap:0" not in sdp


def test_pcmu_session_narrows_the_audio_line_to_0():
    sdp = _build(first_audio_pt=0)
    assert _m_line(sdp, "audio") == f"m=audio {_AUDIO_PORT} RTP/AVP 0"
    assert "a=rtpmap:0 PCMU/8000" in sdp
    assert "a=rtpmap:8" not in sdp


def test_an_unobserved_audio_type_leaves_the_line_alone():
    sdp = _build(first_audio_pt=None)
    assert _m_line(sdp, "audio") == f"m=audio {_AUDIO_PORT} RTP/AVP 0 8"


# --------------------------------------------------------------------- #
# Shape and timing
# --------------------------------------------------------------------- #

def test_the_restart_sdp_is_a_well_formed_two_section_sdp():
    sdp = _build()
    assert sdp.startswith("v=0\r\n")
    assert "o=- 1234567890 1234567890 IN IP4 0.0.0.0\r\n" in sdp
    assert sdp.count("m=audio ") == 1
    assert sdp.count("m=video ") == 1
    assert sdp.count("c=IN IP4 127.0.0.1\r\n") == 2
    assert sdp.count("a=rtcp-mux\r\n") == 2
    assert sdp.endswith("a=rtcp-mux\r\n")
    assert all(ln.endswith("\r") for ln in sdp.split("\n")[:-1])


def test_the_builder_is_pure_and_repeatable():
    # No clock, no I/O, no self: the same inputs give byte-identical output, so
    # the timestamp comes from the caller.
    assert _build() == _build()
    assert _build(ts=1) != _build(ts=2)


def test_first_media_wait_covers_the_documented_cold_start():
    # The repo documents a 25-70s cold start for battery cameras in several
    # places. A 45s deadline sat inside that range, so a camera at the slow end
    # launched with no payload types known - the exact failure the wait exists to
    # prevent.
    assert so._FIRST_MEDIA_WAIT_S >= 70.0


def test_narrowing_still_produces_a_single_payload_type_per_line():
    sdp = (
        "m=audio 1000 RTP/AVP 0 8\r\n"
        "a=rtpmap:0 PCMU/8000\r\na=rtpmap:8 PCMA/8000\r\n"
        "m=video 1002 RTP/AVP 96 97\r\n"
        "a=rtpmap:96 H264/90000\r\na=rtpmap:97 H265/90000\r\n"
    )
    out = so.narrow_sdp_payload_types(sdp, keep_video=97, keep_audio=8)
    assert re.search(r"m=audio 1000 RTP/AVP 8\r\n", out)
    assert re.search(r"m=video 1002 RTP/AVP 97\r\n", out)
