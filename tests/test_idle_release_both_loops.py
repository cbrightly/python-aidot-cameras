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
    assert "progress[0] > _window_dtls" in src


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


def test_sdes_loop_releases_a_battery_camera_on_unknown_after_the_cap():
    # The SDES site must hand the rule the camera's power type and the cap;
    # without them a battery camera whose viewers cannot be counted streams
    # until restart.
    src = _src("_sdes_keepalive_loop_inner")
    call = src[src.index("_idle_release_due(") :]
    call = call[: call.index("_idle_release = True")]
    assert "battery=_battery" in call
    assert '_battery = bool(getattr(self, "is_battery_camera", False))' in src
    assert "unknown_cap_s=" in call
    assert "_battery_unknown_release_s(" in src


def test_dtls_loop_releases_a_battery_camera_on_unknown_after_the_cap():
    src = _src("_dtls_serve_loop_inner")
    assert "_battery_unknown_release_s(" in src
    assert "_last_viewer_dtls" in src[src.index("unknown_cap_s=") - 400 :]
    # The staleness fallback remains for every camera (before the first
    # viewer it waits the start-up grace, mains included).
    assert "progress[0] > _window_dtls" in src


def test_sdes_loop_waits_the_startup_grace_for_its_first_viewer():
    # A short idle window counted from the open would release a session whose
    # first viewer connects late (a slow battery wake). The SDES site must
    # remember whether a viewer was seen and hand the rule the grace.
    src = _src("_sdes_keepalive_loop_inner")
    assert "_stream_startup_grace_s(" in src
    call = src[src.index("_idle_release_due(") :]
    call = call[: call.index("_idle_release = True")]
    assert "viewer_seen=_viewer_seen" in call
    assert "startup_grace_s=" in call
    seen_at = src.index("_viewer_seen = True")
    assert src.rindex("if _present:", 0, seen_at) > src.index("_viewer_present(")


def test_dtls_loop_waits_the_startup_grace_for_its_first_viewer():
    src = _src("_dtls_serve_loop_inner")
    assert "_stream_startup_grace_s(" in src
    assert "_viewer_seen_dtls = True" in src
    # The no-viewer and staleness comparisons use the window that honours the
    # grace, not the raw idle window.
    assert "_now - _last_viewer_dtls > _window_dtls" in src
    assert "progress[0] > _window_dtls" in src
    call = src[src.index("_idle_release_due(") :]
    call = call[: call.index("idle_release = True")]
    assert "viewer_seen=_viewer_seen_dtls" in call
    assert "startup_grace_s=" in call


def test_dtls_start_up_grace_is_once_per_session_not_per_serve_cycle():
    # The serve cycle restarts inside the warm-PC loop whenever go2rtc drops
    # and re-attaches the producer. Resetting "viewer seen" there would hand
    # every restarted cycle the full grace after a viewer has already left.
    src = _src("_dtls_serve_loop_inner")
    cycle = src.index("while self._streaming_active and not _pc_dead():")
    reset = src.index("_viewer_seen_dtls = False")
    assert reset < cycle, "the reset must sit above the serve-cycle loop"
    assert src.count("_viewer_seen_dtls = False") == 1
    # The last-viewer clock still restarts per cycle, as before.
    assert src.index("_last_viewer_dtls = loop.time()") > cycle
