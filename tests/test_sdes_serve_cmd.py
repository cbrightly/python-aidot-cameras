"""Unit tests for _build_sdes_serve_cmd - the SDES bridge ffmpeg command builder.

This is the pure, side-effect-free seam extracted so every serve destination
(http-listen pull / RTSP push / file / null) and the audio trade-off can be
asserted without a live camera. The real-time PMT-stall behaviour on sparse
battery PCMA is validated by live soak, not here.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import _build_sdes_serve_cmd as build


def _idx(cmd, item):
    return cmd.index(item)


def test_http_pull_video_only_default():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="http://127.0.0.1:9000")
    assert cmd[0] == "ffmpeg"
    assert "-c:v" in cmd and cmd[_idx(cmd, "-c:v") + 1] == "copy"
    assert "-an" in cmd                       # audio dropped by default
    assert "-c:a" not in cmd                  # no AAC encoder
    assert cmd[-3:] == ["-listen", "1", "http://127.0.0.1:9000"]


def test_http_pull_with_audio_silence_mix():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="http://127.0.0.1:9000",
                sdes_audio=True, audio_gain_db=-8.0)
    assert "-an" not in cmd
    assert cmd[_idx(cmd, "-c:a") + 1] == "aac"
    # Continuous silence second input keeps the encoder fed from t=0.
    assert "anullsrc=r=8000:cl=mono" in cmd
    fc = cmd[_idx(cmd, "-filter_complex") + 1]
    assert "amix=inputs=2:duration=longest:normalize=0" in fc  # silence-base mix
    assert "volume=-8.0dB" in fc
    # explicit maps: copied video + mixed audio
    assert "0:v:0" in cmd and "[aout]" in cmd
    # output muxer is mpegts (the first -f is the lavfi silence input)
    assert "mpegts" in cmd and cmd[cmd.index("mpegts") - 1] == "-f"
    assert cmd[-4:] == ["mpegts", "-listen", "1", "http://127.0.0.1:9000"]


def test_audio_gain_is_parameterised():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="http://h", sdes_audio=True, audio_gain_db=-3.5)
    assert "volume=-3.5dB" in cmd[_idx(cmd, "-filter_complex") + 1]


def test_video_copy_present_even_with_audio():
    # The whole point: video is always -c:v copy, never gated on audio, so an
    # audio-side stall can't block the video PMT being known from the SDP.
    for audio in (False, True):
        cmd = build(sdp_path="/x.sdp", rtsp_push_url="http://h", sdes_audio=audio)
        assert cmd[_idx(cmd, "-c:v") + 1] == "copy"


def test_file_recording_is_always_copy_even_with_audio():
    # File output (snapshots/diagnostics) never transcodes audio - the silence
    # mix is for the live serve only.  Keeps snapshots a fast video-only copy and
    # avoids referencing [0:a] on a video-narrowed SDP.
    for ext in ("ts", "mkv", "mp4"):
        cmd = build(sdp_path="/x.sdp", output_path=f"/tmp/rec.{ext}", sdes_audio=True, max_seconds=30)
        assert "anullsrc" not in " ".join(cmd), ext
        assert cmd[_idx(cmd, "-c") + 1] == "copy", ext
        assert cmd[-1] == f"/tmp/rec.{ext}", ext


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


# --------------------------------------------------------------------------- #
# RTSP PUSH + audio: browsers cannot play G.711
# --------------------------------------------------------------------------- #
# Measured 2026-08-21 on the live fleet: the SDES cameras publish
# `audio, recvonly, PCMA/8000` and go2rtc duly creates a `pcm_alaw` sender - but
# fMP4/MSE has no mapping for G.711, so go2rtc negotiates VIDEO ONLY and every
# browser using the MSE path gets a silent stream. Passthrough is therefore only
# correct for consumers that can take G.711 (WebRTC); for MSE the push has to
# carry AAC, exactly as the http-listen serve already does.
#
# The battery-camera hazard is the same one the pull path already solved: a
# battery camera sends PCMA sparsely, and an AAC encoder with no samples emits no
# frames. So the push path reuses the identical silence-base mix - anullsrc under
# amix(normalize=0) - which feeds the encoder from t=0 without altering real audio.


def test_push_with_audio_encodes_aac_not_g711_passthrough():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="rtsp://127.0.0.1:8554/cam",
                sdes_audio=True, audio_gain_db=-8.0)
    assert cmd[_idx(cmd, "-c:a") + 1] == "aac"
    assert cmd[_idx(cmd, "-c:v") + 1] == "copy"     # video is never re-encoded
    assert "-c" not in cmd                          # not blanket `-c copy`
    assert cmd[-4:] == ["-f", "rtsp", "-rtsp_transport", "tcp"] or \
        cmd[-1] == "rtsp://127.0.0.1:8554/cam"


def test_push_with_audio_uses_the_silence_base_for_sparse_battery_pcma():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="rtsp://h/cam",
                sdes_audio=True, audio_gain_db=-8.0)
    assert "anullsrc=r=8000:cl=mono" in cmd
    fc = cmd[_idx(cmd, "-filter_complex") + 1]
    assert "amix=inputs=2:duration=longest:normalize=0" in fc
    assert "volume=-8.0dB" in fc
    assert "0:v:0" in cmd and "[aout]" in cmd


def test_push_video_only_still_wins_over_audio():
    # No audio payload type was ever observed: announcing an audio line the RTSP
    # server cannot accept kills the publish with 400. That guard outranks the
    # audio request - a silent picture beats no picture.
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="rtsp://h/cam",
                sdes_audio=True, push_video_only=True)
    assert "-c:a" not in cmd
    assert "anullsrc=r=8000:cl=mono" not in cmd
    assert cmd[_idx(cmd, "-c:v") + 1] == "copy"
    assert "0:v:0" in cmd


def test_push_without_audio_is_unchanged_passthrough():
    # Regression guard: audio off must still be the proven `-c copy` publish.
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="rtsp://h/cam", sdes_audio=False)
    assert cmd[_idx(cmd, "-c") + 1] == "copy"
    assert "-c:a" not in cmd
    assert "anullsrc=r=8000:cl=mono" not in cmd
