"""A serve loop that dies releases the camera, and its neighbours.

The DTLS serve loop and the SDES keepalive loop used to end by exception
with ``_streaming_active`` still True. These tests pin the release path and
the smaller fixes that shipped with it.
"""

import asyncio
import queue
import threading
import time

import pytest

import aidot_cameras.camera.client as camera_client
import aidot_cameras.camera.protocol as proto
import aidot_cameras.camera.sdes_open as sdes_open
from aidot_cameras.camera import rtsp_publish
from aidot_cameras.camera.client import CameraMixin, _FfmpegMissing
from aidot_cameras.camera.controls import (
    LIGHT_BRIGHTNESS_MIN,
    _CameraControlsMixin,
)

from test_mqtt_session_reconnect import _FakeMsg, _install_fake_client


def _dormant_candidate():
    """A CameraMixin with only the fields the release path touches."""
    cam = object.__new__(CameraMixin)
    cam.device_id = "cam"
    cam._streaming_active = True
    cam._keepalive_rtsp_url = "rtsp://127.0.0.1:8554/x"
    cam._serve_ready = asyncio.Event()
    cam._serve_ready.set()
    calls = []
    cam._cancel_keepalive_renew = lambda: calls.append("renew-cancelled")

    async def _dereg():
        calls.append("deregistered")

    cam._deregister_go2rtc = _dereg

    class _Session:
        async def stop(self):
            calls.append("session-stopped")

    cam._stream_session = _Session()
    return cam, calls


@pytest.mark.asyncio
async def test_release_after_loop_error_puts_the_camera_to_sleep(caplog):
    cam, calls = _dormant_candidate()
    await cam._release_after_loop_error("DTLS serve", RuntimeError("boom"))
    assert cam._streaming_active is False
    assert cam._keepalive_rtsp_url is None
    assert not cam._serve_ready.is_set()
    assert cam._stream_session is None
    assert calls == ["renew-cancelled", "session-stopped", "deregistered"]
    assert any("unexpected error" in r.getMessage() for r in caplog.records)


@pytest.mark.asyncio
async def test_a_missing_ffmpeg_is_named_not_retried(caplog):
    cam, _calls = _dormant_candidate()
    await cam._release_after_loop_error("DTLS serve", _FfmpegMissing("no ffmpeg"))
    assert cam._streaming_active is False
    assert any("install ffmpeg" in r.getMessage() for r in caplog.records)


@pytest.mark.parametrize("which", ["_sdes_keepalive_loop", "_dtls_serve_loop"])
def test_both_loops_route_unexpected_errors_to_the_release(
    which, make_camera_device_client
):
    """An exception out of a loop body releases the camera like the idle
    release does; a cancellation still propagates as one."""
    cam = make_camera_device_client("A001064" if "sdes" in which else "A000088")
    inner = which + "_inner"
    released = []

    async def _boom():
        raise RuntimeError("malformed environment")

    async def _release(what, exc):
        released.append((what, exc))
        cam._streaming_active = False

    cam._streaming_active = True
    cam._keepalive_rtsp_url = None  # no relay
    setattr(cam, inner, _boom)
    cam._release_after_loop_error = _release
    asyncio.run(getattr(cam, which)())
    assert len(released) == 1 and isinstance(released[0][1], RuntimeError)

    async def _cancelled():
        raise asyncio.CancelledError

    setattr(cam, inner, _cancelled)
    with pytest.raises(asyncio.CancelledError):
        asyncio.run(getattr(cam, which)())
    assert len(released) == 1, "a cancellation is not an error to release on"


def test_the_jpeg_watchdog_starts_each_session_with_a_fresh_clock(
    monkeypatch, make_camera_device_client
):
    """The last frame of a previous session must not be held against the next:
    a stale stamp made the watchdog restart a fresh session on its first tick.
    """
    cam = make_camera_device_client("A000088")
    opens = []

    class _Session:
        async def stop(self):
            pass

    async def _open(**kw):
        opens.append(kw)
        return _Session()

    real_sleep = asyncio.sleep
    ticks = []

    async def _sleep(d, *a, **k):
        ticks.append(d)
        if len(ticks) > 8:
            cam._streaming_active = False
        await real_sleep(0)

    cam.async_open_webrtc_stream = _open
    cam._streaming_active = True
    cam._last_frame_time = 1.0  # a frame from long ago, in a previous session
    monkeypatch.setattr(camera_client.asyncio, "sleep", _sleep)
    asyncio.run(cam._streaming_loop())
    assert len(opens) == 1, "the new session was torn down by an old frame"


def test_start_keepalive_waits_for_a_dormant_loop_instead_of_dropping_the_view(
    make_camera_device_client,
):
    """A previous loop that went dormant may still be finishing (its go2rtc
    deregister). Returning there dropped the new view until the next one."""
    cam = make_camera_device_client("A001064")
    started = []
    finished = []

    async def _loop():
        started.append(True)

    cam._sdes_keepalive_loop = _loop
    cam._start_keepalive_renew = lambda: None

    async def _go():
        async def _finishing():
            await asyncio.sleep(0.05)
            finished.append(True)

        cam._stream_task = asyncio.ensure_future(_finishing())
        cam._streaming_active = False  # dormant, still deregistering
        await cam.start_keepalive("rtsp://127.0.0.1:8554/cam")
        assert finished == [True], "the dormant loop was waited for, not cancelled"
        await cam._stream_task
        assert started == [True], "and the new view started"

        started.clear()
        await cam.start_keepalive("rtsp://127.0.0.1:8554/cam")  # active now
        assert started == [], "a live loop is left alone"

    asyncio.run(_go())


@pytest.mark.parametrize("ending", ["refused", "raises", "plays", "stop_talk_raises"])
def test_async_speak_closes_its_own_session_on_every_exit(
    make_camera_device_client, ending
):
    cam = make_camera_device_client("A001064")
    cam._stream_session = None

    class _Talk:
        talk_supported = True
        stopped = 0
        talk_stopped = 0

        async def async_start_talk(self, provider):
            if ending == "refused":
                return False
            if ending == "raises":
                raise RuntimeError("talk failed")
            while provider() is not None:
                pass
            return True

        async def async_stop_talk(self):
            self.talk_stopped += 1
            if ending == "stop_talk_raises":
                raise RuntimeError("stop failed")

        async def stop(self):
            self.stopped += 1

    session = _Talk()

    async def _open(**kw):
        return session

    cam.async_open_webrtc_stream = _open
    frames = iter([b"\x00" * 320])
    try:
        asyncio.run(cam.async_speak(lambda: next(frames, None), max_seconds=1))
    except RuntimeError:
        assert ending in ("raises", "stop_talk_raises")
    assert session.stopped == 1, "its own session is closed whatever happened"
    assert session.talk_stopped == (1 if ending in ("plays", "stop_talk_raises") else 0)


@pytest.mark.parametrize(
    "error", [rtsp_publish.RtspPublishError("refused"), RuntimeError("boom")]
)
def test_dtls_publish_thread_reports_any_exception(error):
    """The publish runs on a thread: an exception it does not report is a
    publish that silently stopped. Both kinds land in the result, and neither
    escapes the thread."""

    class _Pub:
        packets_sent = 0

        def __init__(self, *a, **k):
            pass

        def connect(self):
            raise error

        def close(self, teardown=False):
            pass

    res = {}
    rtsp_publish.dtls_rtp_publish_run(
        queue.Queue(),
        queue.Queue(),
        "rtsp://127.0.0.1:1/x",
        [0.0],
        threading.Event(),
        result=res,
        publisher_factory=_Pub,
    )
    assert str(error) in res["error"]


def test_sdes_module_imports_with_an_empty_grace_value(monkeypatch):
    """A bare float() at import used to take every camera down on ''."""
    import importlib

    monkeypatch.setenv("AIDOT_BATTERY_STALE_OFFER_GRACE_S", "")
    monkeypatch.setenv("AIDOT_SDES_STALL_NUDGE_AFTER_S", "2.5s")
    try:
        mod = importlib.reload(sdes_open)
        assert mod._BATTERY_STALE_OFFER_GRACE_S == 15.0
        assert mod._STALL_NUDGE_AFTER_S == 2.5
    finally:
        monkeypatch.delenv("AIDOT_BATTERY_STALE_OFFER_GRACE_S")
        monkeypatch.delenv("AIDOT_SDES_STALL_NUDGE_AFTER_S")
        importlib.reload(sdes_open)


def test_sdes_bridge_thread_logs_an_unexpected_error():
    """A stray exception used to end the bridge thread with nothing at camera
    level in the log, and the session died minutes later as "stalled". The
    thread body is wrapped: an unexpected error is logged with its traceback."""
    import ast

    from tests.ast_guards import log_calls, sdes_bridge

    handlers = [
        h
        for stmt in sdes_bridge().body
        if isinstance(stmt, ast.Try)
        for h in stmt.handlers
        if h.type is not None and ast.unparse(h.type) == "Exception"
    ]
    assert handlers, "the bridge thread body is not guarded"
    assert any(
        c.func.attr in ("error", "exception")
        and any(k.arg == "exc_info" for k in c.keywords)
        for h in handlers
        for c in log_calls(h)
    )


def test_a_malformed_candidate_address_is_not_a_permission():
    """The candidate pattern admits "1.2.3.4.5"; CreatePermission for it is
    declined, not raised into the bridge thread."""
    relay = {"sock": ("1.1.1.1", 3478, "realm", "nonce", "1.1.1.1", 3478, b"k", "user")}
    assert sdes_open.turn_create_permission(relay, "sock", "1.2.3.4.5", 4000) is False


@pytest.mark.asyncio
async def test_floodlight_brightness_clamps_to_the_documented_floor():
    sent = []

    class _Cam(_CameraControlsMixin):
        async def async_set_device_attribute(self, key, value, timeout=None):
            sent.append((key, value))
            return True

    assert await _Cam().async_set_floodlight(on=True, brightness=5)
    assert ("Dimming", LIGHT_BRIGHTNESS_MIN) in sent


def test_mqtt_session_does_not_hoard_messages_it_hands_to_a_handler(monkeypatch):
    outgoing = queue.Queue()
    seen = []

    def driver(c):
        c.on_connect(c, None, {}, 0)
        for i in range(5):
            c.on_message(c, None, _FakeMsg("t/a", f"m{i}".encode()))
        time.sleep(0.05)
        outgoing.put_nowait(None)

    _install_fake_client(monkeypatch, driver)
    messages, _status = proto._mqtt_session_sync(
        "wss://broker.example/mqtt",
        "u",
        "p",
        "cid",
        ["t/a"],
        [],
        5.0,
        outgoing_queue=outgoing,
        on_message=lambda t, p: seen.append((t, p)),
    )
    assert len(seen) == 5
    assert messages == []


def test_mqtt_session_publishes_while_the_inbox_is_busy(monkeypatch):
    """Outgoing publishes used to be drained only when no message was waiting."""
    outgoing = queue.Queue()
    published = []

    def driver(c):
        c.on_connect(c, None, {}, 0)
        orig_publish = c.publish

        def _pub(topic, payload, *a, **k):
            published.append(topic)
            return orig_publish(topic, payload, *a, **k)

        c.publish = _pub
        outgoing.put_nowait(("out/1", "x"))
        for i in range(50):
            c.on_message(c, None, _FakeMsg("t/a", b"m"))
        time.sleep(0.05)
        outgoing.put_nowait(None)

    _install_fake_client(monkeypatch, driver)
    proto._mqtt_session_sync(
        "wss://broker.example/mqtt",
        "u",
        "p",
        "cid",
        ["t/a"],
        [],
        5.0,
        outgoing_queue=outgoing,
    )
    assert "out/1" in published
