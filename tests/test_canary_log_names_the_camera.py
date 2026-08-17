"""The serve canary must name its camera.

The canary counts frames at the tap, upstream of every queue, pipe and muxer -
it is the only measurement that says what the camera is actually delivering.
On a host with several cameras the line was unattributable: establishing that a
26.5 fps series belonged to one particular camera on 2026-08-17 required first
proving the other camera produced no frames at all.
"""

import logging
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import _log_serve_canary

CANARY = {"frames": 300, "keyframes": 10, "max_gap": 92, "gap": 4}


def test_canary_line_includes_the_device_id(caplog):
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        _log_serve_canary("7c89a5c1b36346e5b5c2f77f8554ad63", CANARY)
    assert "7c89a5c1b36346e5b5c2f77f8554ad63" in caplog.text


def test_canary_line_keeps_its_counters(caplog):
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        _log_serve_canary("dev", CANARY)
    assert "frames=300" in caplog.text
    assert "keyframes=10" in caplog.text
    assert "max_keyframe_gap=92" in caplog.text
    assert "cur_gap=4" in caplog.text


def test_a_missing_device_id_is_not_a_crash(caplog):
    """The tap is a hot path: a log line must never take a session down."""
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        _log_serve_canary(None, CANARY)
    assert "frames=300" in caplog.text
