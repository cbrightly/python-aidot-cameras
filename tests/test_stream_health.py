"""stream_health: what the serve loops report about a camera's stream.

The tracker's own rules are tested directly; what the loops feed it is tested
by driving the real loops (tests/keepalive_driver.py) and reading only the
public surface: ``stream_health`` and ``add_stream_health_listener``.
"""

from __future__ import annotations

import asyncio
import dataclasses

import pytest

import aidot_cameras
import aidot_cameras.camera.client as cc
from aidot_cameras.camera import stream_health as sh
from aidot_cameras.camera.stream_health import StreamHealth
from aidot_cameras.exceptions import (
    AidotCameraBusy,
    AidotCameraNotReady,
    AidotCameraWrongCodec,
    AidotNegotiationFailed,
)

from tests.keepalive_driver import (
    DtlsServeRun,
    FakeDtlsSession,
    FakeSession,
    SdesKeepaliveRun,
    dtls_view,
    no_media,
)


def _watch(client) -> list:
    """Every snapshot the client announces, in order."""
    seen: list = []
    client.add_stream_health_listener(lambda: seen.append(client.stream_health))
    return seen


def _states(seen) -> list:
    """The announced states with consecutive repeats folded."""
    out: list = []
    for h in seen:
        if not out or out[-1] != h.state:
            out.append(h.state)
    return out


def _battery(monkeypatch, cam, value: bool = True) -> None:
    monkeypatch.setattr(type(cam), "is_battery_camera", property(lambda s: value))


# --------------------------------------------------------------------------- #
# The public shape
# --------------------------------------------------------------------------- #


def test_the_snapshot_is_exported_and_immutable():
    assert aidot_cameras.StreamHealth is StreamHealth
    assert "StreamHealth" in aidot_cameras.__all__
    h = StreamHealth()
    assert h == StreamHealth("idle", None, None, None, 0)
    with pytest.raises(dataclasses.FrozenInstanceError):
        h.state = "streaming"


def test_the_states_and_reasons_are_the_documented_ones():
    assert sh.STREAM_STATES == (
        "idle",
        "connecting",
        "waking",
        "streaming",
        "retrying",
        "dormant",
        "off",
    )
    assert len(set(sh.STREAM_END_REASONS)) == len(sh.STREAM_END_REASONS)
    for name in dir(sh):
        if name.startswith("END_"):
            assert getattr(sh, name) in sh.STREAM_END_REASONS
        if name.startswith("STATE_"):
            assert getattr(sh, name) in sh.STREAM_STATES


def test_a_new_client_is_idle(make_camera_device_client):
    cam = make_camera_device_client("A001064")
    assert cam.stream_health == StreamHealth(state="idle")


# --------------------------------------------------------------------------- #
# The tracker
# --------------------------------------------------------------------------- #


def test_the_connect_is_timed_once_from_the_open_that_succeeded():
    t = sh._StreamHealthTracker()
    t.opening(100.0)
    t.ended(sh.END_OPEN_FAILED, 10.0)
    assert (t.phase, t.retry_delay_s, t.failures) == ("retrying", 10.0, 1)
    t.opening(110.0)
    assert (t.phase, t.retry_delay_s) == ("connecting", None)
    t.media_started(113.25)
    assert (t.phase, t.last_connect_s, t.failures) == ("streaming", 3.25, 0)
    t.media_started(200.0)  # a later poll of the same session
    assert t.last_connect_s == 3.25
    assert t.last_end_reason == sh.END_OPEN_FAILED  # kept while streaming


def test_a_session_that_streamed_is_not_a_failure():
    t = sh._StreamHealthTracker()
    t.opening(0.0)
    t.media_started(1.0)
    t.ended(sh.END_MEDIA_STALLED, 10.0)
    assert (t.phase, t.failures, t.last_end_reason) == (
        "retrying",
        0,
        "media_stalled",
    )
    t.ended(sh.END_NO_MEDIA, 20.0)
    assert t.failures == 1


def test_no_delay_or_a_quick_retry_reads_as_still_connecting():
    t = sh._StreamHealthTracker()
    t.opening(0.0)
    t.ended(sh.END_CONNECTION_LOST, 0.0)
    assert t.phase == "connecting"
    t.ended(sh.END_NOT_READY, 3.0, still_connecting=True)
    assert (t.phase, t.retry_delay_s) == ("connecting", None)


def test_a_stop_does_not_rewrite_a_release_or_a_give_up():
    t = sh._StreamHealthTracker()
    t.opening(0.0)
    t.media_started(1.0)
    t.released(sh.END_IDLE_RELEASE)
    t.stopped()
    assert (t.phase, t.last_end_reason) == ("idle", "idle_release")

    t.starting()
    t.ending(sh.END_NO_MEDIA)
    t.gave_up()
    t.stopped()
    assert (t.phase, t.last_end_reason) == ("dormant", "no_media")

    t.starting()
    assert t.phase == "connecting"
    t.stopped()
    assert (t.phase, t.last_end_reason) == ("idle", "stopped")


class TestAStoppedStreamStaysStoppedUntilItIsStartedAgain:
    """``async_stop_streaming`` marks the stream idle before its loop has been
    cancelled, so the loop can still report between two awaits."""

    def test_nothing_a_loop_reports_after_the_stop_is_taken(self):
        t = sh._StreamHealthTracker()
        calls: list = []
        t.starting()
        t.opening(0.0)
        t.stopped()
        t.add_listener(lambda: calls.append(t.phase))
        t.media_started(1.0)
        t.opening(2.0)
        t.ended(sh.END_NO_MEDIA, 30.0)
        t.ending(sh.END_NO_MEDIA)
        t.gave_up()
        t.released(sh.END_ERROR)
        assert (t.phase, t.last_end_reason) == ("idle", "stopped")
        assert (t.failures, t.last_connect_s, t.retry_delay_s) == (0, None, None)
        assert calls == []

    def test_a_stop_of_a_stream_already_let_go_is_guarded_too(self):
        t = sh._StreamHealthTracker()
        t.starting()
        t.released(sh.END_IDLE_RELEASE)
        t.stopped()
        t.media_started(1.0)
        assert (t.phase, t.last_end_reason) == ("idle", "idle_release")

    def test_the_next_start_is_reported_as_usual(self):
        t = sh._StreamHealthTracker()
        t.starting()
        t.stopped()
        t.starting()
        t.opening(10.0)
        t.media_started(12.0)
        assert (t.phase, t.last_connect_s) == ("streaming", 2.0)

    def test_a_frame_that_arrives_after_the_stop_does_not_read_as_streaming(
        self, make_camera_device_client
    ):
        cam = make_camera_device_client("A000088")

        async def _deregister():
            return None

        cam._deregister_go2rtc = _deregister
        t = cam._stream_health_tracker
        t.starting()
        t.opening(0.0)
        seen = _watch(cam)
        asyncio.run(cam.async_stop_streaming())
        t.media_started(1.0)
        assert cam.stream_health.state == "idle"
        assert cam.stream_health.last_end_reason == "stopped"
        assert [h.state for h in seen] == ["idle"]


def test_an_end_that_is_not_a_failure_is_not_counted():
    t = sh._StreamHealthTracker()
    t.opening(0.0)
    t.ended(sh.END_WRONG_CODEC, 1.0, still_connecting=True, failure=False)
    assert (t.phase, t.failures, t.last_end_reason) == (
        "connecting",
        0,
        "wrong_codec",
    )
    t.ended(sh.END_NO_MEDIA, 1.0, still_connecting=True)
    assert t.failures == 1


def test_listeners_are_not_told_what_they_already_know():
    t = sh._StreamHealthTracker()
    calls: list = []
    t.add_listener(lambda: calls.append(t.phase))
    t.released(sh.END_STOPPED)  # idle already, but the reason is new
    t.released(sh.END_STOPPED)
    assert calls == ["idle"]
    t.starting()
    t.opening(0.0)  # still connecting, nothing else changed
    t.opening(1.0)
    assert calls == ["idle", "connecting"]
    t.ended(sh.END_OPEN_FAILED, 10.0)
    t.gave_up()
    t.gave_up()
    assert calls == ["idle", "connecting", "retrying", "dormant"]
    # A change the listeners can read is always announced, whatever the phase.
    t.starting()
    t.ended(sh.END_NOT_READY, 3.0, still_connecting=True)  # failures moved
    assert calls[-2:] == ["connecting", "connecting"]


def test_listeners_are_called_removed_and_cannot_break_the_loop():
    t = sh._StreamHealthTracker()
    calls: list = []

    def _bad():
        raise RuntimeError("consumer bug")

    t.add_listener(_bad)
    remove = t.add_listener(lambda: calls.append(t.phase))
    t.opening(0.0)
    t.media_started(1.0)
    assert calls == ["connecting", "streaming"]
    t.media_started(2.0)  # nothing changed: nothing announced
    assert calls == ["connecting", "streaming"]
    remove()
    remove()  # twice is harmless
    t.released(sh.END_STOPPED)
    assert calls == ["connecting", "streaming"]


def test_a_client_built_without_its_initialiser_still_has_a_tracker():
    bare = object.__new__(cc.CameraMixin)
    tracker = sh._tracker_of(bare)
    assert sh._tracker_of(bare) is tracker


# --------------------------------------------------------------------------- #
# What only the camera knows: the power switch and the battery
# --------------------------------------------------------------------------- #


def test_a_battery_camera_connecting_reads_as_waking(
    monkeypatch, make_camera_device_client
):
    cam = make_camera_device_client("A001064")
    cam._stream_health_tracker.opening(0.0)
    _battery(monkeypatch, cam, False)
    assert cam.stream_health.state == "connecting"
    _battery(monkeypatch, cam, True)
    assert cam.stream_health.state == "waking"
    cam._stream_health_tracker.media_started(2.0)
    assert cam.stream_health.state == "streaming"


@pytest.mark.parametrize("phase", ["idle", "connecting", "retrying", "dormant"])
def test_a_camera_switched_off_reads_as_off_unless_it_streams(
    make_camera_device_client, phase
):
    cam = make_camera_device_client("A001064")
    t = cam._stream_health_tracker
    t.opening(0.0)
    if phase == "retrying":
        t.ended(sh.END_OPEN_FAILED, 30.0)
    elif phase == "dormant":
        t.gave_up()
    elif phase == "idle":
        t.released(sh.END_STOPPED)
    cam.status.powered = False
    health = cam.stream_health
    assert health.state == "off"
    assert health.retry_delay_s is None
    cam.status.powered = True
    assert cam.stream_health.state != "off"

    cam.status.powered = False
    t.opening(0.0)
    t.media_started(1.0)
    assert cam.stream_health.state == "streaming"


def test_switching_the_camera_off_is_announced(make_camera_device_client):
    cam = make_camera_device_client("A001064")
    seen = _watch(cam)

    async def _attr(name, value):
        return True

    cam.async_set_device_attribute = _attr
    assert asyncio.run(cam.async_set_power(False)) is True
    assert [h.state for h in seen] == ["off"]
    assert asyncio.run(cam.async_set_power(True)) is True
    assert [h.state for h in seen] == ["off", "idle"]


# --------------------------------------------------------------------------- #
# The SDES keepalive loop
# --------------------------------------------------------------------------- #


def test_sdes_a_refusal_then_a_session_then_its_end(
    monkeypatch, make_camera_device_client
):
    cam = make_camera_device_client("A001064")
    _battery(monkeypatch, cam, False)
    seen = _watch(cam)
    SdesKeepaliveRun(
        monkeypatch,
        cam,
        [AidotCameraBusy(-50002), FakeSession(media=True, life=30.0)],
        viewer=lambda now: True,
    ).run()

    assert _states(seen) == [
        "connecting",
        "retrying",
        "connecting",
        "streaming",
        "retrying",
        "connecting",
    ]
    busy = next(h for h in seen if h.state == "retrying")
    assert busy.last_end_reason == "busy"
    assert busy.retry_delay_s == cc._BUSY_BACKOFF_S
    assert busy.failures == 1
    streaming = next(h for h in seen if h.state == "streaming")
    assert streaming.last_connect_s == 0.0  # the fake open takes no time
    assert streaming.failures == 0
    ended = [h for h in seen if h.state == "retrying"][-1]
    assert ended.last_end_reason == "serve_ended"
    assert ended.failures == 0
    assert ended.retry_delay_s > 0


def test_sdes_the_connect_time_is_the_open_that_delivered(
    monkeypatch, make_camera_device_client
):
    cam = make_camera_device_client("A001064")
    _battery(monkeypatch, cam, False)
    run = SdesKeepaliveRun(
        monkeypatch,
        cam,
        [AidotCameraBusy(-50002), FakeSession(media=True, life=30.0)],
        viewer=lambda now: True,
    )
    fake_open = cam.async_open_webrtc_stream

    async def _slow_open(**kwargs):
        run.clock.now += 4.5  # every open takes this long on the fake clock
        return await fake_open(**kwargs)

    cam.async_open_webrtc_stream = _slow_open
    seen = _watch(cam)
    run.run()
    streaming = next(h for h in seen if h.state == "streaming")
    # The successful attempt alone: not the refused one, not the wait.
    assert streaming.last_connect_s == 4.5


def test_sdes_a_battery_camera_that_never_delivers_goes_dormant(
    monkeypatch, make_camera_device_client
):
    cam = make_camera_device_client("A001064")
    _battery(monkeypatch, cam, True)
    seen = _watch(cam)
    run = SdesKeepaliveRun(
        monkeypatch, cam, no_media(cc._FUTILE_KEEPALIVE_LIMIT + 2)
    ).run()

    assert run.dormant == [{"release_url": False, "why": "abandon"}]
    health = cam.stream_health
    assert health.state == "dormant"
    assert health.last_end_reason == "no_media"
    assert health.failures == cc._FUTILE_KEEPALIVE_LIMIT
    assert health.last_connect_s is None
    states = _states(seen)
    assert states[0] == "waking"
    assert states[-1] == "dormant"
    assert "streaming" not in states

    # A stop after the give-up (the consumer's cleanup) leaves the account.
    cam._deregister_go2rtc = run.client._deregister_go2rtc
    asyncio.run(cam.async_stop_streaming())
    assert cam.stream_health.state == "dormant"


def test_sdes_a_wrong_codec_reopen_is_the_same_connect_and_no_failure(
    monkeypatch, make_camera_device_client
):
    cam = make_camera_device_client("A001064")
    _battery(monkeypatch, cam, False)
    seen = _watch(cam)
    SdesKeepaliveRun(
        monkeypatch,
        cam,
        [AidotCameraWrongCodec(98, 96), AidotCameraWrongCodec(98, 96)],
    ).run()
    assert _states(seen) == ["connecting"]
    assert cam.stream_health.last_end_reason == "wrong_codec"
    assert cam.stream_health.failures == 0


class TestTheRetryDelayIsTheWaitThatWillHappen:
    """While the cloud reports the camera offline the SDES and JPEG loops hold
    for the offline probe interval, not for the pacer's delay."""

    def test_the_wait_is_the_pacers_for_a_camera_that_is_online(
        self, make_camera_device_client
    ):
        cam = make_camera_device_client("A001064")
        assert cc._backoff_wait_s(cam, 12.0) == 12.0

    def test_the_wait_is_the_probe_interval_for_one_reported_offline(
        self, make_camera_device_client
    ):
        cam = make_camera_device_client("A001064")
        cam.status.online = False
        cam._cloud_online_explicit = True
        assert cc._backoff_wait_s(cam, 12.0) == cc._OFFLINE_PROBE_S
        assert cc._OFFLINE_PROBE_S > 12.0

    def test_sdes_a_failed_open_of_an_offline_camera_says_so(
        self, monkeypatch, make_camera_device_client
    ):
        cam = make_camera_device_client("A001064")
        _battery(monkeypatch, cam, False)
        cam.status.online = False
        cam._cloud_online_explicit = True
        seen = _watch(cam)
        run = SdesKeepaliveRun(
            monkeypatch, cam, [AidotNegotiationFailed("no answer")]
        ).run()
        retry = next(h for h in seen if h.state == "retrying")
        assert retry.last_end_reason == "open_failed"
        assert retry.retry_delay_s == cc._OFFLINE_PROBE_S
        # And that is what the loop then waited before it opened again.
        assert run.open_times[1] - run.open_times[0] == pytest.approx(
            cc._OFFLINE_PROBE_S, abs=cc._OFFLINE_RECHECK_S
        )

    def test_sdes_a_failed_open_of_an_online_camera_gives_the_backoff(
        self, monkeypatch, make_camera_device_client
    ):
        cam = make_camera_device_client("A001064")
        _battery(monkeypatch, cam, False)
        seen = _watch(cam)
        run = SdesKeepaliveRun(
            monkeypatch, cam, [AidotNegotiationFailed("no answer")]
        ).run()
        retry = next(h for h in seen if h.state == "retrying")
        assert retry.retry_delay_s == round(run.sleeps[0], 1)
        assert retry.retry_delay_s < cc._OFFLINE_PROBE_S


def test_sdes_an_idle_release_reads_as_idle(monkeypatch, make_camera_device_client):
    cam = make_camera_device_client("A001064")
    _battery(monkeypatch, cam, False)
    cam._stream_idle_opt = 30.0
    monkeypatch.setenv("AIDOT_STREAM_STARTUP_GRACE_S", "0")
    seen = _watch(cam)
    run = SdesKeepaliveRun(
        monkeypatch,
        cam,
        [FakeSession(media=True, life=None)],
        viewer=lambda now: False,
    ).run()

    assert run.dormant == [{"release_url": True, "why": "idle release"}]
    assert _states(seen) == ["connecting", "streaming", "idle"]
    assert cam.stream_health.last_end_reason == "idle_release"


# --------------------------------------------------------------------------- #
# The DTLS serve loop
# --------------------------------------------------------------------------- #


def test_dtls_a_view_streams_and_is_released(monkeypatch, make_camera_device_client):
    states: list = []
    real_new = cc.CameraMixin.add_stream_health_listener

    def _make(profile):
        cam = make_camera_device_client(profile)
        real_new(cam, lambda: states.append(cam.stream_health))
        return cam

    run = dtls_view(monkeypatch, _make, lambda e: False)
    assert run.dormant == [{"release_url": True, "why": "idle release"}]
    assert _states(states) == ["connecting", "streaming", "idle"]
    streaming = next(h for h in states if h.state == "streaming")
    assert streaming.last_connect_s is not None
    assert 0.0 <= streaming.last_connect_s < 2.0
    assert states[-1].last_end_reason == "idle_release"
    assert run.client.stream_health.state == "idle"


def test_dtls_a_failed_open_reads_as_retrying(monkeypatch, make_camera_device_client):
    cam = make_camera_device_client("A000088")
    seen = _watch(cam)
    run = DtlsServeRun(monkeypatch, cam, [], timeout=1.0)

    async def _refused(**kwargs):
        run.opens += 1
        if run.opens > 1:
            raise asyncio.CancelledError
        raise AidotCameraBusy(-50002)

    cam.async_open_webrtc_stream = _refused
    run.run()
    assert _states(seen)[:2] == ["connecting", "retrying"]
    retry = next(h for h in seen if h.state == "retrying")
    assert retry.last_end_reason == "busy"
    assert retry.retry_delay_s > 0
    assert retry.failures == 1


def test_dtls_a_session_with_no_video_is_named(monkeypatch, make_camera_device_client):
    monkeypatch.setenv("AIDOT_DTLS_VIDEO_GRACE_S", "0.2")
    cam = make_camera_device_client("A000088")
    cam._stream_idle_opt = 0.0
    seen = _watch(cam)
    DtlsServeRun(
        monkeypatch,
        cam,
        [FakeDtlsSession()],
        timeout=2.0,
        frames=False,
        video=False,
    ).run()
    reasons = [h.last_end_reason for h in seen if h.state == "retrying"]
    assert reasons[:1] == ["no_video"]
    assert "streaming" not in _states(seen)
    assert next(h for h in seen if h.state == "retrying").failures == 1


def test_dtls_audio_without_video_is_not_streaming(
    monkeypatch, make_camera_device_client
):
    """The mux writes audio too, so its progress moves on a session that
    carries no video. Such a session is still connecting until the loop ends
    it, and it is a failure."""
    monkeypatch.setenv("AIDOT_DTLS_VIDEO_GRACE_S", "0.2")
    cam = make_camera_device_client("A000088")
    cam._stream_idle_opt = 0.0
    seen = _watch(cam)
    DtlsServeRun(
        monkeypatch,
        cam,
        [FakeDtlsSession()],
        timeout=2.0,
        frames=True,
        video=False,
    ).run()
    assert "streaming" not in _states(seen)
    retry = next(h for h in seen if h.state == "retrying")
    assert retry.last_end_reason == "no_video"
    assert retry.failures == 1
    assert retry.last_connect_s is None


def test_dtls_a_not_ready_quick_retry_is_the_same_connect(
    monkeypatch, make_camera_device_client
):
    """As on an SDES camera: the quick retries of an encoder still warming up
    read as connecting; once the burst is spent the wait is a real one."""
    cam = make_camera_device_client("A000088")
    seen = _watch(cam)
    run = DtlsServeRun(monkeypatch, cam, [], timeout=5.0, scale=0.0001)
    burst = cc._NOT_READY_BURST_MAX

    async def _not_ready(**kwargs):
        run.opens += 1
        if run.opens > burst + 1:
            raise asyncio.CancelledError
        raise AidotCameraNotReady("cold")

    async def _no_wait(interval, **kwargs):
        return None

    cam.async_open_webrtc_stream = _not_ready
    cam._chunked_wait = _no_wait  # it waits on the real clock
    run.run()
    seen = [h for h in seen if h.last_end_reason is not None]
    quick = seen[:burst]
    assert [h.state for h in quick] == ["connecting"] * burst
    assert [h.last_end_reason for h in quick] == ["not_ready"] * burst
    assert [h.failures for h in quick] == list(range(1, burst + 1))
    assert all(h.retry_delay_s is None for h in quick)
    after = seen[burst]
    assert (after.state, after.last_end_reason) == ("retrying", "not_ready")
    assert after.retry_delay_s > 0


class TestALoopThatCannotStartIsNotLeftConnecting:
    def test_a_dtls_serve_that_fails_to_start_reads_as_idle_with_the_reason(
        self, monkeypatch, make_camera_device_client
    ):
        # Its own slots: the process-wide ones belong to whichever event loop
        # first waited on them.
        slots = asyncio.Semaphore(1)
        monkeypatch.setattr(cc, "_get_stream_slots", lambda: slots)
        cam = make_camera_device_client("A000088")
        cam._keepalive_rtsp_url = "http://127.0.0.1:18559/cam.ts"
        cam._streaming_active = True

        async def _deregister():
            return None

        def _no_threads(url):
            raise RuntimeError("can't start new thread")

        cam._deregister_go2rtc = _deregister
        cam._start_ts_serve = _no_threads
        cam._stream_health_tracker.starting()
        seen = _watch(cam)
        asyncio.run(cam._dtls_serve_loop())
        assert [h.state for h in seen] == ["idle"]
        assert cam.stream_health.last_end_reason == "error"
        assert cam._streaming_active is False

    def test_an_sdes_relay_that_fails_to_start_reads_as_idle_with_the_reason(
        self, make_camera_device_client
    ):
        cam = make_camera_device_client("A001064")
        cam._keepalive_rtsp_url = "http://127.0.0.1:18559/cam.ts"
        cam._streaming_active = True

        async def _deregister():
            return None

        def _no_threads(url):
            raise RuntimeError("can't start new thread")

        cam._deregister_go2rtc = _deregister
        cam._maybe_start_serve_relay = _no_threads
        cam._stream_health_tracker.starting()
        asyncio.run(cam._sdes_keepalive_loop())
        assert cam.stream_health.state == "idle"
        assert cam.stream_health.last_end_reason == "error"
        assert cam._serve_relay is None


def test_a_loop_error_reads_as_idle_with_the_reason(make_camera_device_client):
    cam = make_camera_device_client("A000088")

    async def _deregister():
        return None

    cam._deregister_go2rtc = _deregister
    cam._stream_health_tracker.opening(0.0)
    asyncio.run(cam._release_after_loop_error("DTLS serve", RuntimeError("x")))
    assert cam.stream_health.state == "idle"
    assert cam.stream_health.last_end_reason == "error"
