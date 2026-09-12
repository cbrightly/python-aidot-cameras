"""Stamp the serve input by ARRIVAL, because the camera's own stamps go backwards.

Instrumented on an A001513 on 2026-09-12: the camera emits a packet that is the
NEXT IN SEQUENCE (`seq_delta=1`), that nobody asked to be resent
(`repeat_age=None`, `pending=0`), and whose RTP timestamp is ~1.7 s in the PAST,
on a 30-second period. Nothing is late, reordered or lost, so there is no packet
worth dropping - the only honest lever is to stop believing the camera's clock.

**It is an INPUT option.** After `-i` ffmpeg would not apply it to the input at
all, so position is what these tests pin.

Efficacy is NOT asserted here and cannot be: "Non-monotonic DTS" is a muxer
complaint, four local harnesses failed to reproduce it, and the measurement
lives on the box against a 6.1-6.8 warnings-per-streaming-minute baseline.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import _build_sdes_serve_cmd as build

FLAG = "-use_wallclock_as_timestamps"


def _cmd(**kw):
    return build(sdp_path="/tmp/x.sdp", **kw)


def test_arrival_timestamps_are_requested():
    cmd = _cmd()
    assert FLAG in cmd
    assert cmd[cmd.index(FLAG) + 1] == "1"


def test_it_is_an_input_option():
    assert _cmd().index(FLAG) < _cmd().index("-i")


def test_it_applies_to_every_destination():
    for kw in ({}, {"rtsp_push_url": "rtsp://127.0.0.1:8554/x"},
               {"output_path": "/tmp/out.mp4"}):
        cmd = _cmd(**kw)
        assert FLAG in cmd, kw
        assert cmd.index(FLAG) < cmd.index("-i"), kw


def test_genpts_is_kept():
    assert "genpts" in _cmd()[_cmd().index("-fflags") + 1]
