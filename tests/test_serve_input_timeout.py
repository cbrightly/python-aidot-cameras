"""Unit tests for the serve's SDP-input silence tolerance.

The sdp demuxer's ``listen_timeout`` doubles as its packet-read timeout
(ffmpeg READ_PACKET_TIMEOUT_S, default 10 s): 10 s with no packets on any RTP
socket errors the input ("Error during demuxing: Operation timed out") and the
serve dies.  Measured on an A001064, the camera episodically stops
transmitting for >=10 s mid-session and the link recovers on a 10-20 s scale,
so a mains camera rides the gap out with a longer window while a battery
camera keeps the fast default (its stops are real sleeps that only a reopen
ends).
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import (  # noqa: E402
    _SERVE_INPUT_TIMEOUT_BATTERY_S,
    _SERVE_INPUT_TIMEOUT_MAINS_S,
    _build_sdes_serve_cmd as build,
    _resolve_serve_input_timeout_s as resolve,
)


def test_mains_gets_the_long_window():
    assert resolve(False) == _SERVE_INPUT_TIMEOUT_MAINS_S == 30


def test_battery_keeps_ffmpeg_default():
    # 10 is ffmpeg's own READ_PACKET_TIMEOUT_S: passing it changes nothing for
    # battery cameras, whose mid-session stops are real sleeps.
    assert resolve(True) == _SERVE_INPUT_TIMEOUT_BATTERY_S == 10


def test_env_override_wins_for_both(monkeypatch):
    monkeypatch.setenv("AIDOT_SERVE_INPUT_TIMEOUT_S", "45")
    assert resolve(False) == 45
    assert resolve(True) == 45


def test_env_override_garbage_falls_back(monkeypatch):
    monkeypatch.setenv("AIDOT_SERVE_INPUT_TIMEOUT_S", "soon")
    assert resolve(False) == _SERVE_INPUT_TIMEOUT_MAINS_S
    assert resolve(True) == _SERVE_INPUT_TIMEOUT_BATTERY_S


def test_env_override_is_floored_at_one_second(monkeypatch):
    monkeypatch.setenv("AIDOT_SERVE_INPUT_TIMEOUT_S", "0")
    assert resolve(False) == 1


def test_builder_places_listen_timeout_before_input():
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="rtsp://127.0.0.1:8554/cam",
                input_timeout_s=30)
    i = cmd.index("-listen_timeout")
    assert cmd[i + 1] == "30"
    # Input option: must precede -i, or ffmpeg reads it as an output option.
    assert i < cmd.index("-i")


def test_builder_default_is_unchanged_behavior():
    # None -> no flag at all, so callers that do not opt in keep ffmpeg's own
    # default (and the argv stays byte-identical to pre-change builds).
    cmd = build(sdp_path="/x.sdp", rtsp_push_url="rtsp://127.0.0.1:8554/cam")
    assert "-listen_timeout" not in cmd
