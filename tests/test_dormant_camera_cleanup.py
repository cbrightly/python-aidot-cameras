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

import asyncio

import aidot_cameras.camera.client as cc
from aidot_cameras.camera.sdes_open import (
    _FFMPEG_EXIT_EPIPE,
    _classify_ffmpeg_exit,
)
import logging

from tests.source_parts import full_source


def _src(name):
    return full_source(name)


def test_sdes_idle_release_goes_dormant():
    src = _src("_sdes_keepalive_loop_inner")
    idle = src[src.index("if _idle_release:") :]
    assert "_go_dormant(release_url=True" in idle


def test_dtls_idle_release_goes_dormant():
    src = _src("_dtls_serve_loop_inner")
    idle = src[src.index("if idle_release:") :]
    assert "_go_dormant(release_url=True" in idle


class _Dormant:
    """The attributes _go_dormant touches, with a go2rtc that may refuse."""

    def __init__(self, fail=False):
        self.device_id = "cam"
        self._streaming_active = True
        self._keepalive_rtsp_url = "rtsp://x"
        self._serve_ready = asyncio.Event()
        self._serve_ready.set()
        self.cancelled = 0
        self.deregistered = 0
        self._fail = fail

    def _cancel_keepalive_renew(self):
        self.cancelled += 1

    async def _deregister_go2rtc(self):
        self.deregistered += 1
        if self._fail:
            raise OSError("go2rtc unreachable")


async def test_going_dormant_deregisters_and_releases_the_url():
    d = _Dormant()
    await cc.CameraMixin._go_dormant(d, release_url=True, why="idle release")
    assert d._streaming_active is False
    assert d.cancelled == 1 and d.deregistered == 1
    assert d._keepalive_rtsp_url is None and not d._serve_ready.is_set()


async def test_an_abandon_keeps_the_url_but_still_deregisters():
    d = _Dormant()
    await cc.CameraMixin._go_dormant(d, release_url=False, why="abandon")
    assert d._streaming_active is False
    assert d.deregistered == 1
    assert d._keepalive_rtsp_url == "rtsp://x" and d._serve_ready.is_set()


async def test_deregistration_failure_cannot_break_the_release():
    # Going dormant must still complete if go2rtc is unreachable.
    d = _Dormant(fail=True)
    await cc.CameraMixin._go_dormant(d, release_url=True, why="idle release")
    assert d._streaming_active is False and d.deregistered == 1


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

    Since the exits share `_go_dormant`, which sets the flag and deregisters
    in one call, a call to it counts as both; a bare `_streaming_active =
    False` in a loop is still checked the old way.
    """
    import ast
    import textwrap

    for name in ("_sdes_keepalive_loop_inner", "_dtls_serve_loop_inner"):
        # The loop and its region coroutines, parsed together as a module.
        fn = ast.parse(textwrap.dedent(_src(name)))

        stops = []  # (lineno of `_streaming_active = False`)
        dormants = [
            n.lineno
            for n in ast.walk(fn)
            if isinstance(n, ast.Attribute) and n.attr == "_go_dormant"
        ]
        for node in ast.walk(fn):
            if not isinstance(node, ast.Assign):
                continue
            for t in node.targets:
                if (
                    isinstance(t, ast.Attribute)
                    and t.attr == "_streaming_active"
                    and isinstance(node.value, ast.Constant)
                    and node.value.value is False
                ):
                    stops.append(node.lineno)

        assert stops or dormants, f"{name}: nothing in it ever stops streaming"

        deregs = dormants + [
            n.lineno
            for n in ast.walk(fn)
            if isinstance(n, ast.Attribute) and n.attr == "_deregister_go2rtc"
        ]
        returns = [n.lineno for n in ast.walk(fn) if isinstance(n, ast.Return)]

        for stop in stops:
            # The return that this stop falls through to.
            after = [r for r in returns if r > stop]
            if not after:
                continue  # falls through to the loop, not an exit
            exit_line = min(after)
            between = [d for d in deregs if stop < d < exit_line]
            assert between, (
                f"{name}: the exit at line {exit_line} sets _streaming_active="
                f"False (line {stop}) and returns without calling "
                f"_deregister_go2rtc. A dormant camera's go2rtc stream is left "
                f"pointing at a serve port with nothing listening."
            )
