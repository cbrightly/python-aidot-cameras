"""Both serve loops must decide idleness by asking who is WATCHING.

0.12.9 fixed this for the SDES loop and left the DTLS loop on its old signal -
pipe-progress staleness - which is the same unanswerable question in disguise: the
pipe only backs up when nothing drains the serve socket, and go2rtc drains it
forever as the stream's producer. On a fleet that is mostly DTLS cameras (this one
is 4 of 5) the fix therefore did nothing.
"""
import inspect

import aidot_cameras.camera.client as cc


def _src(name):
    return inspect.getsource(getattr(cc.CameraMixin, name))


def test_sdes_loop_asks_who_is_watching():
    assert "_viewer_present" in _src("_sdes_keepalive_loop_inner")


def test_dtls_loop_asks_who_is_watching():
    # The regression: this loop used only `_now - progress[0] > idle_secs`.
    assert "_viewer_present" in _src("_dtls_serve_loop_inner")


def test_dtls_loop_still_has_a_fallback_when_nobody_can_answer():
    # If go2rtc cannot be reached the old staleness heuristic must remain, so an
    # unreachable go2rtc does not mean "hold every stream open forever".
    src = _src("_dtls_serve_loop_inner")
    assert "progress[0] > idle_secs" in src


def test_the_stream_slot_is_released_even_if_the_relay_fails_to_start():
    # _maybe_start_serve_relay catches OSError, but Thread.start() raises
    # RuntimeError under thread exhaustion. Starting it outside the try meant the
    # permit was lost for the life of the process and the cap silently shrank.
    src = _src("_dtls_serve_loop")
    acquire = src.index("slots.acquire()")
    try_at = src.index("try:", acquire)
    relay_at = src.index("_maybe_start_serve_relay", acquire)
    assert try_at < relay_at, "relay start must be inside the try that releases"


def test_teardown_does_not_join_a_thread_that_never_started():
    # join() on an unstarted thread raises and would skip the ffmpeg terminate
    # and the session stop that follow it.
    assert "mux_thread.is_alive()" in _src("_dtls_serve_loop_inner")
