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


def test_every_exit_that_stops_streaming_also_deregisters():
    """Enumerate the exits instead of slicing to one of them.

    The tests above slice from `if _idle_release:` to the end of the source, so
    they can only ever see that one exit. A second exit was added later - the
    futile-keepalive abandon - and it returned without deregistering, leaving a
    dormant camera's stream pointed at a dead serve port. No test could see it,
    because no test looked anywhere else.

    This walks the function instead: every `return` that follows
    `self._streaming_active = False` must have a `_deregister_go2rtc` call
    between the two. It fails on the next exit added carelessly, which the
    slicing form cannot.
    """
    import ast
    import textwrap

    for name in ("_sdes_keepalive_loop_inner", "_dtls_serve_loop_inner"):
        tree = ast.parse(textwrap.dedent(_src(name)))
        fn = tree.body[0]

        stops = []          # (lineno of `_streaming_active = False`)
        for node in ast.walk(fn):
            if not isinstance(node, ast.Assign):
                continue
            for t in node.targets:
                if (isinstance(t, ast.Attribute) and t.attr == "_streaming_active"
                        and isinstance(node.value, ast.Constant)
                        and node.value.value is False):
                    stops.append(node.lineno)

        assert stops, f"{name}: no `_streaming_active = False` found at all"

        deregs = [n.lineno for n in ast.walk(fn)
                  if isinstance(n, ast.Attribute) and n.attr == "_deregister_go2rtc"]
        returns = [n.lineno for n in ast.walk(fn) if isinstance(n, ast.Return)]

        for stop in stops:
            # The return that this stop falls through to.
            after = [r for r in returns if r > stop]
            if not after:
                continue        # falls through to the loop, not an exit
            exit_line = min(after)
            between = [d for d in deregs if stop < d < exit_line]
            assert between, (
                f"{name}: the exit at line {exit_line} sets _streaming_active="
                f"False (line {stop}) and returns without calling "
                f"_deregister_go2rtc. A dormant camera's go2rtc stream is left "
                f"pointing at a serve port with nothing listening."
            )
