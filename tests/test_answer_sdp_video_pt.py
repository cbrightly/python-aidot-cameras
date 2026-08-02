"""Deriving the video payload type from the camera's answer SDP.

The ffmpeg SDP advertises both codecs (``m=video ... 96 97``) because which one
a camera sends varies per session, and it is narrowed to the observed payload
type before launch. When no video packet arrives inside the wait window there was
nothing to narrow on, so the dual-codec SDP went to ffmpeg as-is - and per
``narrow_sdp_payload_types``' own docstring that makes ffmpeg bind its
depacketizer to the FIRST payload type and discard the rest, and makes the
RTSP-push ANNOUNCE carry a parameterless H.265 stream that go2rtc rejects. No
publisher attaches and every viewer gets a 404.

Measured on the box 2026-08-02: the ``SDES: narrowed ffmpeg SDP to ...`` status
line never appeared in the logs at all, while other INFO from the same library
flowed freely - narrowing was simply not running.

The camera's answer already states the codec it agreed to send, so it is a
negotiated fact rather than a guess.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (
    narrow_sdp_payload_types,
    video_pt_from_answer_sdp,
)

# The dual-codec video line the ffmpeg SDP template writes, verbatim.
_FFMPEG_SDP = (
    "v=0\r\n"
    "m=audio 5002 RTP/SAVP 0 8\r\n"
    "a=rtpmap:0 PCMU/8000\r\n"
    "a=rtpmap:8 PCMA/8000\r\n"
    "m=video 5004 RTP/SAVP 96 97\r\n"
    "a=rtpmap:96 H264/90000\r\n"
    "a=fmtp:96 profile-level-id=42e01f\r\n"
    "a=rtpmap:97 H265/90000\r\n"
    "a=fmtp:97 level-id=93\r\n"
)


def _answer(video_line: str, *rtpmaps: str, audio: str = "") -> str:
    return "\r\n".join(
        [
            "v=0",
            "o=- 0 0 IN IP4 0.0.0.0",
            "s=-",
            "t=0 0",
            *([audio] if audio else []),
            video_line,
            "c=IN IP4 0.0.0.0",
            *rtpmaps,
            "a=sendonly",
        ]
    )


def test_h264_answer_narrows_to_96():
    sdp = _answer("m=video 5004 RTP/SAVP 96", "a=rtpmap:96 H264/90000")
    assert video_pt_from_answer_sdp(sdp) == 96


def test_h265_answer_narrows_to_97():
    sdp = _answer("m=video 5004 RTP/SAVP 97", "a=rtpmap:97 H265/90000")
    assert video_pt_from_answer_sdp(sdp) == 97


def test_first_payload_on_the_m_line_wins():
    """The m-line order is the camera's preference, and ffmpeg binds to the first."""
    sdp = _answer(
        "m=video 5004 RTP/SAVP 97 96",
        "a=rtpmap:96 H264/90000",
        "a=rtpmap:97 H265/90000",
    )
    assert video_pt_from_answer_sdp(sdp) == 97

    sdp = _answer(
        "m=video 5004 RTP/SAVP 96 97",
        "a=rtpmap:96 H264/90000",
        "a=rtpmap:97 H265/90000",
    )
    assert video_pt_from_answer_sdp(sdp) == 96


def test_camera_numbering_is_translated_to_our_template():
    """The camera may number H.265 anything; our SDP writes it as 97.

    Returning the camera's number would narrow our template to a payload type it
    does not contain, dropping the video line entirely.
    """
    sdp = _answer("m=video 5004 RTP/SAVP 100", "a=rtpmap:100 H265/90000")
    assert video_pt_from_answer_sdp(sdp) == 97


def test_codec_name_case_is_ignored():
    sdp = _answer("m=video 5004 RTP/SAVP 96", "a=rtpmap:96 h264/90000")
    assert video_pt_from_answer_sdp(sdp) == 96


def test_audio_section_payloads_do_not_leak():
    """A payload number reused in the audio section must not answer for video."""
    sdp = "\r\n".join(
        [
            "v=0",
            "m=audio 5002 RTP/SAVP 96",
            "a=rtpmap:96 H264/90000",  # pathological, but must be ignored
            "m=video 5004 RTP/SAVP 99",
            "a=rtpmap:99 VP8/90000",
        ]
    )
    assert video_pt_from_answer_sdp(sdp) is None


def test_only_the_first_video_section_is_read():
    sdp = "\r\n".join(
        [
            "v=0",
            "m=video 5004 RTP/SAVP 99",
            "a=rtpmap:99 VP8/90000",
            "m=video 5006 RTP/SAVP 96",
            "a=rtpmap:96 H264/90000",
        ]
    )
    assert video_pt_from_answer_sdp(sdp) is None


def test_unknown_codec_returns_none():
    sdp = _answer("m=video 5004 RTP/SAVP 99", "a=rtpmap:99 VP8/90000")
    assert video_pt_from_answer_sdp(sdp) is None


def test_video_line_without_rtpmap_returns_none():
    sdp = _answer("m=video 5004 RTP/SAVP 96")
    assert video_pt_from_answer_sdp(sdp) is None


def test_no_video_line_returns_none():
    sdp = "\r\n".join(["v=0", "m=audio 5002 RTP/SAVP 8", "a=rtpmap:8 PCMA/8000"])
    assert video_pt_from_answer_sdp(sdp) is None


def test_empty_and_garbage_never_raise():
    for junk in ("", "   ", "not an sdp", "m=video", "a=rtpmap:", None):
        assert video_pt_from_answer_sdp(junk) is None


def test_answer_derived_pt_actually_narrows_the_ffmpeg_sdp():
    """End to end: an H.265 answer must leave a single-codec video line.

    This is the contract that matters. A leftover second codec is what makes the
    RTSP-push ANNOUNCE carry a parameterless H.265 stream, and go2rtc rejects it.
    """
    answer = _answer("m=video 5004 RTP/SAVP 97", "a=rtpmap:97 H265/90000")
    out = narrow_sdp_payload_types(
        _FFMPEG_SDP, keep_video=video_pt_from_answer_sdp(answer), keep_audio=8
    )

    assert "m=video 5004 RTP/SAVP 97\r\n" in out
    assert "a=rtpmap:97 H265/90000" in out
    # the dropped codec must be gone entirely, rtpmap and fmtp alike
    assert "H264/90000" not in out
    assert "a=fmtp:96" not in out
    assert " 96" not in out.split("m=video")[1].splitlines()[0]


def test_unusable_answer_leaves_the_sdp_untouched():
    """No regression when the answer names nothing we write: behaviour is exactly
    today's, an unnarrowed line - not a wrong guess."""
    answer = _answer("m=video 5004 RTP/SAVP 99", "a=rtpmap:99 VP8/90000")
    out = narrow_sdp_payload_types(
        _FFMPEG_SDP, keep_video=video_pt_from_answer_sdp(answer), keep_audio=None
    )
    assert out == _FFMPEG_SDP


def test_lf_only_line_endings_are_handled():
    """Answers are not guaranteed to use CRLF."""
    sdp = "v=0\nm=video 5004 RTP/SAVP 97\na=rtpmap:97 H265/90000\n"
    assert video_pt_from_answer_sdp(sdp) == 97
