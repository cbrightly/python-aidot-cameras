"""Unit tests for _build_sdes_serve_cmd - the SDES bridge ffmpeg command builder.

This is the pure, side-effect-free seam extracted so every serve destination
(http-listen pull / RTSP push / file / null) and the audio trade-off can be
asserted without a live camera. The real-time PMT-stall behaviour on sparse
battery PCMA is validated by live soak, not here.
"""
import os
import sys

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

from aidot.camera.client import _build_sdes_serve_cmd as build


def _idx(cmd, item):
    return cmd.index(item)


def test_http_pull_video_only_default():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="http://127.0.0.1:9000")
    assert cmd[0] == "ffmpeg"
    assert "-c:v" in cmd and cmd[_idx(cmd, "-c:v") + 1] == "copy"
    assert "-an" in cmd                       # audio dropped by default
    assert "-c:a" not in cmd                  # no AAC encoder
    assert cmd[-3:] == ["-listen", "1", "http://127.0.0.1:9000"]


def test_http_pull_with_audio_transcodes_aac():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="http://127.0.0.1:9000",
                sdes_audio=True, audio_gain_db=-8.0)
    assert "-an" not in cmd
    assert cmd[_idx(cmd, "-c:a") + 1] == "aac"
    af = cmd[_idx(cmd, "-af") + 1]
    assert "aresample=async=1:first_pts=0" in af   # silence-pad to feed encoder
    assert "volume=-8.0dB" in af
    assert cmd[_idx(cmd, "-f") + 1] == "mpegts"


def test_audio_gain_is_parameterised():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="http://h", sdes_audio=True, audio_gain_db=-3.5)
    assert "volume=-3.5dB" in cmd[_idx(cmd, "-af") + 1]


def test_video_copy_present_even_with_audio():
    # The whole point: video is always -c:v copy, never gated on audio, so an
    # audio-side stall can't block the video PMT being known from the SDP.
    for audio in (False, True):
        cmd = build(sdp_path="/x.sdp", rtsp_push_url="http://h", sdes_audio=audio)
        assert cmd[_idx(cmd, "-c:v") + 1] == "copy"


def test_rtsp_push_copies_both():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="rtsp://127.0.0.1:8554/cam")
    assert cmd[_idx(cmd, "-c") + 1] == "copy"
    assert cmd[_idx(cmd, "-f") + 1] == "rtsp"
    assert cmd[-1] == "rtsp://127.0.0.1:8554/cam"


def test_file_output():
    cmd = build(sdp_path="/x.sdp", output_path="/tmp/o.ts")
    assert cmd[-2:] == ["-c", "copy"][:1] + ["/tmp/o.ts"] or cmd[-1] == "/tmp/o.ts"
    assert cmd[-1] == "/tmp/o.ts"


def test_null_when_no_destination():
    cmd = build(sdp_path="/x.sdp")
    assert cmd[-2:] == ["-f", "null"][:1] + ["/dev/null"] or cmd[-1] == "/dev/null"
    assert "null" in cmd


def test_max_seconds_adds_t_flag():
    cmd = build(sdp_path="/x.sdp", output_path="/tmp/o.ts", max_seconds=30)
    assert cmd[_idx(cmd, "-t") + 1] == "30"
    # no -t when unset
    assert "-t" not in build(sdp_path="/x.sdp", output_path="/tmp/o.ts")


def test_sdp_input_always_present():
    cmd = build(sdp_path="/path/to/session.sdp")
    assert cmd[_idx(cmd, "-i") + 1] == "/path/to/session.sdp"
    assert "file,rtp,udp,srtp" in cmd
