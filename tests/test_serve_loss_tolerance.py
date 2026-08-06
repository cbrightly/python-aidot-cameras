"""The serve must ride out real packet loss instead of dying on it.

Both ends of this path are usually wireless - the camera, and the Home Assistant
host - so RTP genuinely arrives with gaps. Measured on the reference install:
ffmpeg logged `RTP: missed N packets` while every socket in the path showed ZERO
kernel drops, which means those packets were lost before they ever reached the
box. Nothing in the code can recover them.

What the code controls is what happens next. Without reordering headroom the
demuxer treats a burst that arrives out of order as loss; without a bounded
max_delay it stalls waiting ("max delay reached. need to consume packet") and
backs its input queue up until the serve dies, dropping every attached viewer.
A brief artefact is a far better outcome than a dropped stream.
"""
import pytest

from aidot_cameras.camera.client import (
    _SERVE_MAX_DELAY_US,
    _SERVE_REORDER_QUEUE,
    _build_sdes_serve_cmd,
)

_DESTINATIONS = [
    pytest.param({"rtsp_push_url": "rtsp://127.0.0.1:8554/cam"}, id="push"),
    pytest.param({"rtsp_push_url": "http://127.0.0.1:18981/cam.ts"}, id="http-listen"),
    pytest.param({"output_path": "/tmp/cam.ts"}, id="file"),
    pytest.param({}, id="null-drain"),
]


def _cmd(**kwargs):
    return _build_sdes_serve_cmd(sdp_path="/tmp/x.sdp", **kwargs)


@pytest.mark.parametrize("dest", _DESTINATIONS)
def test_every_destination_gets_the_loss_tolerance_args(dest):
    """The input side is shared, so loss tolerance must not depend on the sink."""
    cmd = _cmd(**dest)
    assert "-reorder_queue_size" in cmd
    assert cmd[cmd.index("-reorder_queue_size") + 1] == str(_SERVE_REORDER_QUEUE)
    assert "-max_delay" in cmd
    assert cmd[cmd.index("-max_delay") + 1] == str(_SERVE_MAX_DELAY_US)
    fflags = cmd[cmd.index("-fflags") + 1]
    assert "discardcorrupt" in fflags, fflags


def test_the_reorder_queue_clears_a_keyframe():
    # 146-190 KB of keyframe is ~130 packets at MTU. A queue that cannot hold
    # more than one keyframe's worth reads an out-of-order burst as loss.
    assert _SERVE_REORDER_QUEUE >= 260


def test_max_delay_is_bounded():
    """Unbounded waiting turns loss into the stall this is meant to prevent."""
    assert 0 < _SERVE_MAX_DELAY_US <= 2_000_000


@pytest.mark.parametrize("dest", _DESTINATIONS)
def test_the_low_latency_flags_survive(dest):
    """Tolerance must not be bought by silently reintroducing buffering."""
    fflags = _cmd(**dest)[_cmd(**dest).index("-fflags") + 1]
    assert "nobuffer" in fflags and "genpts" in fflags, fflags


def test_the_args_sit_before_the_input():
    """Demuxer options after -i apply to the output and are silently ignored."""
    cmd = _cmd(rtsp_push_url="rtsp://127.0.0.1:8554/cam")
    i = cmd.index("-i")
    for opt in ("-reorder_queue_size", "-max_delay", "-fflags"):
        assert cmd.index(opt) < i, f"{opt} must precede -i to affect the input"
