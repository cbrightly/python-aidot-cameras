"""The battery unknown-viewer cap must survive a reopened SDES session.

Found by the 2026-10-11 review: the cap, the idle window and the start-up grace
were all measured from each session's open. A publish the target refuses ends
the serve before the first viewer check, a fresh session opens 10 s later, and
the anchors reset, so a battery camera was re-woken every 35-60 s for as long as
the cause lasted (reproduced against integration 2.37.2 on a bundled-only
install, and an add-on outage does the same).
"""

from aidot_cameras.camera.protocol import _battery_cap_spans_sessions_due as due


def test_a_battery_keepalive_with_no_known_viewer_past_the_cap_stops():
    assert due(True, 300.0, last_known_viewer=0.0, now=301.0) is True


def test_inside_the_cap_it_reopens():
    assert due(True, 300.0, last_known_viewer=0.0, now=299.0) is False


def test_a_mains_camera_is_never_stopped_this_way():
    assert due(False, 300.0, last_known_viewer=0.0, now=10_000.0) is False


def test_a_cap_of_zero_or_none_disables_it():
    assert due(True, 0.0, last_known_viewer=0.0, now=10_000.0) is False
    assert due(True, None, last_known_viewer=0.0, now=10_000.0) is False


def _short_sessions(
    monkeypatch, make_camera_device_client, profile, viewer, life, n=40
):
    from tests.keepalive_driver import FakeSession, SdesKeepaliveRun

    cam = make_camera_device_client(profile)
    return SdesKeepaliveRun(
        monkeypatch,
        cam,
        [FakeSession(media=True, life=life) for _ in range(n)],
        viewer=viewer,
    ).run()


def test_sessions_that_end_on_their_own_cannot_outrun_the_cap(
    monkeypatch, make_camera_device_client
):
    """The anchor is set once per keepalive, and checked after every session.

    Each session here ends on its own after 40 s with the viewers unknown, so no
    single session ever reaches the 300 s cap. Anchored per session, the cap
    would never fire and a battery camera would be re-woken for as long as the
    cause lasted; anchored on the keepalive, the session that ends past the cap
    is the last one.
    """
    run = _short_sessions(
        monkeypatch, make_camera_device_client, "A001513", lambda now: None, 40.0
    )
    cap = 300.0
    assert run.dormant == [{"release_url": True, "why": "idle release"}]
    start = run.open_times[0]
    assert cap <= run.dormant_at[0] - start < cap + 60.0, run.dormant_at
    # released where the ended session is checked: no further open after it
    assert run.open_times[-1] < run.dormant_at[0]


def test_a_known_viewer_refreshes_the_keepalive_anchor(
    monkeypatch, make_camera_device_client
):
    """The cap counts from the last viewer KNOWN present, in any session."""
    seen_until = 1000.0 + 200.0
    run = _short_sessions(
        monkeypatch,
        make_camera_device_client,
        "A001513",
        lambda now: True if now < seen_until else None,
        40.0,
    )
    assert run.dormant, "still released in the end"
    # the last poll that saw the viewer is at most one 5 s poll before it left
    held = run.dormant_at[0] - seen_until
    assert 300.0 - 15.0 <= held < 300.0 + 60.0, run.dormant_at


def test_a_known_absence_keeps_its_per_session_window(
    monkeypatch, make_camera_device_client
):
    """Unknown counts across reopens; a known absence counts from the session's
    own open, so a viewer re-attaching after a camera-side drop is not cut.

    Mains camera, viewers known absent, 120 s idle window: sessions that end on
    their own after 100 s never reach it; one that lasts 200 s does.
    """
    monkeypatch.setenv("AIDOT_STREAM_IDLE_S", "120")
    short = _short_sessions(
        monkeypatch, make_camera_device_client, "A001064", lambda now: False, 100.0, n=6
    )
    assert short.dormant == []
    long = _short_sessions(
        monkeypatch, make_camera_device_client, "A001064", lambda now: False, 200.0, n=6
    )
    assert long.dormant == [{"release_url": True, "why": "idle release"}]
    assert long.dormant_at[0] - long.open_times[0] < 200.0
