"""A serve loop that dies releases the camera, and its neighbours.

The DTLS serve loop and the SDES keepalive loop used to end by exception
with ``_streaming_active`` still True. These tests pin the release path and
the smaller fixes that shipped with it.
"""

import asyncio
import inspect
import queue
import time
<<<<<<< HEAD
import types
=======
>>>>>>> origin/chore/dead-code-pass

import pytest

import aidot_cameras.camera.client as camera_client
import aidot_cameras.camera.protocol as proto
import aidot_cameras.camera.sdes_open as sdes_open
import aidot_cameras.camera.webrtc_open as webrtc_open
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


def test_both_loops_route_unexpected_errors_to_the_release():
    for fn in (CameraMixin._dtls_serve_loop, CameraMixin._sdes_keepalive_loop):
        src = inspect.getsource(fn)
        assert "except asyncio.CancelledError:" in src
        assert "_release_after_loop_error(" in src


def test_no_bare_env_float_remains_in_the_camera_client():
    src = inspect.getsource(camera_client)
    assert 'float(os.environ.get("AIDOT_' not in src


def test_the_jpeg_watchdog_starts_each_session_with_a_fresh_clock():
    src = inspect.getsource(CameraMixin._jpeg_loop if hasattr(CameraMixin, "_jpeg_loop") else camera_client)
    i = src.index("_open_time = asyncio.get_running_loop().time()")
    assert "self._last_frame_time = 0.0" in src[i - 300 : i]


def test_start_keepalive_waits_for_a_dormant_loop_instead_of_dropping_the_view():
    src = inspect.getsource(CameraMixin.start_keepalive)
    i = src.index("if self._stream_task is not None and not self._stream_task.done():")
    assert "if self._streaming_active:" in src[i : i + 200]
    assert "asyncio.shield(self._stream_task)" in src[i : i + 600]


def test_async_speak_closes_its_own_session_on_every_exit():
    src = inspect.getsource(CameraMixin.async_speak)
    i = src.index("started = bool(await session.async_start_talk(_provider))")
    tail = src[i:]
    assert tail.count("finally:") >= 2
    assert "if own_session:\n                    await session.stop()" in tail


def test_dtls_publish_thread_reports_any_exception():
    src = inspect.getsource(rtsp_publish.dtls_rtp_publish_run)
    assert "except RtspPublishError as exc:" in src
    assert "except Exception as exc:" in src.split("except RtspPublishError as exc:", 1)[1]


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
    src = inspect.getsource(sdes_open)
    assert "SDES bridge ended by an unexpected error" in src
    i = src.index("_cp_ip_b = _cp_socket.inet_aton(_cp_peer_ip)")
    assert "except OSError:" in src[i : i + 200]


def test_lan_mode_gates_the_turn_preallocation():
    src = inspect.getsource(sdes_open)
<<<<<<< HEAD
    assert "if not _relay_addrs and not _fast_connect and not _skip_turn_prealloc:" in src
=======
    assert "if not _relay_addrs and not _skip_turn_prealloc:" in src
>>>>>>> origin/chore/dead-code-pass


def test_dtls_signaling_wait_watches_the_refusal_future():
    src = inspect.getsource(webrtc_open)
    i = src.index("_init_pending: set = {")
    assert "terminal_error_fut" in src[i : i + 200]
    j = src.index("_rr_done, _rr_pending = _init_done, _init_pending")
    assert "raise AidotCameraBusy(_code, _desc)" in src[j : j + 1200]


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
        "wss://broker.example/mqtt", "u", "p", "cid", ["t/a"], [], 5.0,
        outgoing_queue=outgoing,
    )
    assert "out/1" in published
