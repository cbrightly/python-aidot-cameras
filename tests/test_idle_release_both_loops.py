"""Both serve loops must decide idleness by asking who is WATCHING.

0.12.9 fixed this for the SDES loop and left the DTLS loop on its old signal -
pipe-progress staleness - which is the same unanswerable question in disguise: the
pipe only backs up when nothing drains the serve socket, and go2rtc drains it
forever as the stream's producer. On a fleet that is mostly DTLS cameras (this one
is 4 of 5) the fix therefore did nothing.
"""

import asyncio

import pytest

import aidot_cameras.camera.client as cc
from tests.keepalive_driver import dtls_view as _dtls_view


def test_the_stream_slot_is_released_even_if_the_relay_fails_to_start(
    make_camera_device_client,
):
    """Thread.start() raises RuntimeError under thread exhaustion. Starting the
    relay outside the try that releases lost the permit for the life of the
    process and the cap silently shrank."""
    slots = cc._get_stream_slots()
    cam = make_camera_device_client("A000088")

    def _no_threads(url):
        raise RuntimeError("can't start new thread")

    cam._maybe_start_serve_relay = _no_threads
    before = slots._value
    for _ in range(before + 1):
        with pytest.raises(RuntimeError):
            asyncio.run(cam._dtls_serve_loop())
    assert slots._value == before


def test_teardown_does_not_join_a_thread_that_never_started(
    monkeypatch, make_camera_device_client
):
    """join() on an unstarted thread raises and would skip the ffmpeg terminate
    and the session stop that follow it."""
    import threading

    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    real_start = threading.Thread.start

    def _start(self):
        if (
            self.name != "aidot-dtls-publish"
            and getattr(self, "_target", None) is cc._dtls_av_mux_run
        ):
            raise RuntimeError("can't start new thread")
        return real_start(self)

    cam = make_camera_device_client("A000088")
    session = FakeDtlsSession()
    run = DtlsServeRun(monkeypatch, cam, [session], viewer=lambda e: True)
    monkeypatch.setattr(threading.Thread, "start", _start)
    with pytest.raises(RuntimeError, match="can't start new thread"):
        asyncio.run(cam._dtls_serve_loop_inner())
    assert session.stopped == 1, "the session must still be stopped"
    assert run.direct, "the cycle got as far as its serve"


def test_dtls_loop_releases_a_battery_camera_on_unknown_after_the_cap(
    monkeypatch, make_camera_device_client
):
    """go2rtc keeps draining the pipe, so staleness never fires: a battery
    camera with its viewers unknown is released after the cap since the last
    known viewer, a mains camera is not."""
    mains = _dtls_view(
        monkeypatch,
        make_camera_device_client,
        lambda e: None,
        idle=5.0,
        grace=0.0,
        cap=0.4,
        timeout=1.0,
    )
    assert mains.dormant == []
    # last: the battery override stays on the class for the rest of the test
    battery = _dtls_view(
        monkeypatch,
        make_camera_device_client,
        lambda e: None,
        idle=5.0,
        grace=0.0,
        cap=0.4,
        battery=True,
    )
    assert battery.dormant == [{"release_url": True, "why": "idle release"}]
    assert 0.4 <= battery.dormant_after[0] < 1.5


def test_dtls_loop_waits_the_startup_grace_for_its_first_viewer(
    monkeypatch, make_camera_device_client
):
    """Before the first viewer the window is at least the start-up grace; once
    one has been seen it is the idle window again."""
    never = _dtls_view(
        monkeypatch, make_camera_device_client, lambda e: False, idle=0.1, grace=0.6
    )
    assert never.dormant and never.dormant_after[0] >= 0.6

    seen = _dtls_view(
        monkeypatch,
        make_camera_device_client,
        lambda e: e < 0.3,
        idle=0.1,
        grace=1.5,
    )
    assert seen.dormant and seen.dormant_after[0] < 1.0, seen.dormant_after


def test_dtls_start_up_grace_is_once_per_session_not_per_serve_cycle(
    monkeypatch, make_camera_device_client
):
    """The serve cycle restarts inside the warm session whenever the publish
    ends. Resetting "viewer seen" there would hand every restarted cycle the
    full grace after a viewer had already left."""
    run = _dtls_view(
        monkeypatch,
        make_camera_device_client,
        lambda e: e < 0.3,
        idle=0.1,
        grace=1.5,
        url="rtsp://127.0.0.1:8554/cam",
        publish_for=0.15,
    )
    assert run.mux_targets.count("publish") >= 2, "the cycle restarted"
    assert run.dormant and run.dormant_after[0] < 1.0, run.dormant_after


def _sdes_view(monkeypatch, make_camera_device_client, profile, viewer, **env):
    from tests.keepalive_driver import FakeSession, SdesKeepaliveRun

    for k, v in env.items():
        monkeypatch.setenv(k, v)
    cam = make_camera_device_client(profile)
    return SdesKeepaliveRun(
        monkeypatch, cam, [FakeSession(life=None)], viewer=viewer
    ).run()


def test_sdes_loop_asks_who_is_watching(monkeypatch, make_camera_device_client):
    run = _sdes_view(monkeypatch, make_camera_device_client, "A001064", lambda n: True)
    assert run.viewer_asks and set(run.viewer_asks) == {8554}
    assert run.dormant == [], "a present viewer keeps the view"


def test_dtls_loop_asks_who_is_watching(monkeypatch, make_camera_device_client):
    """The regression: this loop used only pipe staleness, which go2rtc - always
    draining the serve as its producer - never lets go stale."""
    run = _dtls_view(
        monkeypatch, make_camera_device_client, lambda e: True, timeout=1.0
    )
    assert run.viewer_asks and set(run.viewer_asks) == {18555}
    assert run.dormant == []


def test_dtls_loop_still_has_a_fallback_when_nobody_can_answer(
    monkeypatch, make_camera_device_client
):
    """go2rtc unreachable (unknown): the staleness heuristic remains, so an
    unreachable go2rtc does not mean "hold every stream open forever"."""
    stale = _dtls_view(
        monkeypatch, make_camera_device_client, lambda e: None, frames=False
    )
    assert stale.dormant == [{"release_url": True, "why": "idle release"}]
    flowing = _dtls_view(
        monkeypatch, make_camera_device_client, lambda e: None, timeout=1.0
    )
    assert flowing.dormant == [], "a mains camera with frames flowing is kept"


def test_sdes_loop_releases_a_battery_camera_on_unknown_after_the_cap(
    monkeypatch, make_camera_device_client
):
    """Unknown never releases a mains camera; a battery camera is released after
    the cap, or it would stream until restart."""
    battery = _sdes_view(
        monkeypatch, make_camera_device_client, "A001513", lambda n: None
    )
    assert battery.dormant == [{"release_url": True, "why": "idle release"}]
    held = battery.dormant_at[0] - battery.open_times[0]
    assert 300.0 <= held < 320.0, held

    mains = _sdes_view(
        monkeypatch, make_camera_device_client, "A001064", lambda n: None
    )
    assert mains.dormant == []


def test_sdes_loop_waits_the_startup_grace_for_its_first_viewer(
    monkeypatch, make_camera_device_client
):
    """A short idle window counted from the open would release a session whose
    first viewer connects late. Until a viewer is seen the window is at least
    the start-up grace; after one is seen it is the idle window again."""
    env = {"AIDOT_STREAM_IDLE_S": "10", "AIDOT_STREAM_STARTUP_GRACE_S": "60"}
    never = _sdes_view(
        monkeypatch, make_camera_device_client, "A001064", lambda n: False, **env
    )
    assert never.dormant, never.dormant
    assert never.dormant_at[0] - never.open_times[0] >= 60.0

    left_at = 1000.0 + 100.0
    seen = _sdes_view(
        monkeypatch,
        make_camera_device_client,
        "A001064",
        lambda n: n < left_at,
        **env,
    )
    assert seen.dormant
    assert left_at <= seen.dormant_at[0] <= left_at + 10.0 + 5.0 + 1.0
