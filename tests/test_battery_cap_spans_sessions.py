"""The battery unknown-viewer cap must survive a reopened SDES session.

Found by the 2026-10-11 review: the cap, the idle window and the start-up grace
were all measured from each session's open. A publish the target refuses ends
the serve before the first viewer check, a fresh session opens 10 s later, and
the anchors reset, so a battery camera was re-woken every 35-60 s for as long as
the cause lasted (reproduced against integration 2.37.2 on a bundled-only
install, and an add-on outage does the same).
"""

from aidot_cameras.camera.protocol import _battery_cap_spans_sessions_due as due
from tests.source_parts import full_source


def test_a_battery_keepalive_with_no_known_viewer_past_the_cap_stops():
    assert due(True, 300.0, last_known_viewer=0.0, now=301.0) is True


def test_inside_the_cap_it_reopens():
    assert due(True, 300.0, last_known_viewer=0.0, now=299.0) is False


def test_a_mains_camera_is_never_stopped_this_way():
    assert due(False, 300.0, last_known_viewer=0.0, now=10_000.0) is False


def test_a_cap_of_zero_or_none_disables_it():
    assert due(True, 0.0, last_known_viewer=0.0, now=10_000.0) is False
    assert due(True, None, last_known_viewer=0.0, now=10_000.0) is False


def _src():
    return full_source("_sdes_keepalive_loop_inner")


def test_the_anchor_is_set_once_per_keepalive_not_per_session():
    src = _src()
    loop = src.index("while self._streaming_active:")
    first = src.index("_last_known_viewer = time.monotonic()")
    assert first < loop, "the keepalive-wide anchor must sit above the session loop"
    assert src.count("_last_known_viewer = time.monotonic()") == 1


def test_a_known_viewer_refreshes_the_keepalive_anchor():
    src = _src()
    seen = src.index("_viewer_seen = True")
    assert (
        "_last_known_viewer = _last_consumer"
        in src[src.rindex("if _present:", 0, seen) : seen]
    )


def test_unknown_uses_the_keepalive_anchor_and_absence_the_session_one():
    src = _src()
    call = src[src.index("_idle_release_due(") :]
    call = call[: call.index("_idle_release = True")]
    assert "_last_known_viewer if _present is None else _last_consumer" in call


def test_a_session_that_ended_on_its_own_is_checked_before_reopening():
    src = _src()
    check = src.index("_battery_cap_spans_sessions_due(")
    release = src.index("if _idle_release:", check)
    assert check < release
    assert "_battery, _unknown_cap, _last_known_viewer" in src[check:release]
