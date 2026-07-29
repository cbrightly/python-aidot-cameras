"""A dormant camera must not leave a dead stream registered in go2rtc.

Idle-release stops the serve but used to leave the go2rtc stream in place,
pointing at a source that no longer exists. A viewer attaching then gets a hard
"connection refused" on the serve port rather than a clean miss - and in PUSH mode
that source was never dialable at all, because the keepalive publishes INTO go2rtc
rather than serving over HTTP.

Observed live: four go2rtc streams registered against serve ports with nothing
listening, and go2rtc reporting
`dial tcp 127.0.0.1:18981: connect: connection refused`.
"""
import inspect

import aidot_cameras.camera.client as cc
from aidot_cameras.camera.sdes_open import (
    _FFMPEG_EXIT_EPIPE,
    _classify_ffmpeg_exit,
)
import logging


def _src(name):
    return inspect.getsource(getattr(cc.CameraMixin, name))


def test_sdes_idle_release_deregisters_from_go2rtc():
    src = _src("_sdes_keepalive_loop_inner")
    idle = src[src.index("if _idle_release:"):]
    assert "_deregister_go2rtc" in idle


def test_dtls_idle_release_deregisters_from_go2rtc():
    src = _src("_dtls_serve_loop_inner")
    idle = src[src.index("if idle_release:"):]
    assert "_deregister_go2rtc" in idle


def test_deregistration_failure_cannot_break_the_release():
    # Going dormant must still complete if go2rtc is unreachable.
    for name, marker in (("_sdes_keepalive_loop_inner", "if _idle_release:"),
                         ("_dtls_serve_loop_inner", "if idle_release:")):
        idle = _src(name)
        idle = idle[idle.index(marker):]
        block = idle[idle.index("_deregister_go2rtc") - 200:
                     idle.index("_deregister_go2rtc") + 300]
        assert "try:" in block and "except Exception" in block


def test_consumer_disconnect_is_not_logged_as_a_failure():
    # ffmpeg returns AVERROR(EPIPE) = -32 when its consumer goes away, and an exit
    # status is an unsigned byte, so it surfaces as 224. That is the normal end of
    # a `-listen 1` serve, not a fault.
    assert _FFMPEG_EXIT_EPIPE == 224
    assert _classify_ffmpeg_exit(224, teardown_requested=False) == logging.DEBUG
    assert _classify_ffmpeg_exit(224, teardown_requested=True) == logging.DEBUG


def test_real_failures_still_warn():
    assert _classify_ffmpeg_exit(1, teardown_requested=False) == logging.WARNING
    assert _classify_ffmpeg_exit(255, teardown_requested=False) == logging.WARNING
    # A signal death with no teardown in flight is still unexpected.
    assert _classify_ffmpeg_exit(-9, teardown_requested=False) == logging.WARNING
    # ...but an expected teardown is quiet.
    assert _classify_ffmpeg_exit(-9, teardown_requested=True) == logging.DEBUG
