"""The DTLS serve loop backs off a camera whose sessions keep failing fast.

Measured 2026-11-04 on an A000088: for half an hour its sessions died within
seconds of opening, the next open came on the bare 15 s gate each time (an
open that connects resets the pacer), then opens stopped connecting at all
("ICE connection not established"), and every retry 15 s after one of those
was refused as busy (-50002) - the camera still held the session it had
accepted for the offer that never connected. Then it went offline.

Two changes, pinned here:

* after an ICE failure the next open waits at least AIDOT_BUSY_RETRY_S,
  as after a busy refusal, rather than walking into one;
* the pacer is no longer reset by a connected open but by a session that
  lasted and delivered video, so sessions that keep dying fast escalate it and
  the loop waits its delay before reopening, once per session, after the
  session has been stopped.

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""

import ast
import asyncio
import inspect
import logging
import types

from aidot_cameras import settings
from aidot_cameras.camera import client as client_mod
from aidot_cameras.camera.client import (
    _DTLS_SHORT_SESSION_S,
    CameraMixin,
    _session_end_pacing,
)
from aidot_cameras.camera.protocol import ReconnectPacer
from aidot_cameras.exceptions import (
    AidotIceNotEstablished,
    AidotNegotiationFailed,
)


class _Ready:
    def clear(self):
        pass

    def set(self):
        pass


def _first_retry_delay(monkeypatch, error):
    waits = []

    class _Fake:
        _streaming_active = True
        _keepalive_rtsp_url = "http://127.0.0.1:8999/cam.ts"
        device_id = "TESTCAM"
        _serve_ready = _Ready()
        status = types.SimpleNamespace(online=True)

        async def async_open_webrtc_stream(self, *a, **k):
            raise error

        def _open_fail_logger(self):
            return logging.getLogger("test").warning

        async def _backoff_or_offline_pause(self, delay):
            waits.append(delay)
            raise asyncio.CancelledError

    async def _no_sleep(d):
        return None

    monkeypatch.setattr("aidot_cameras.camera.client.asyncio.sleep", _no_sleep)
    asyncio.run(types.MethodType(CameraMixin._dtls_serve_loop_inner, _Fake())())
    assert len(waits) == 1, waits
    return waits[0]


def test_an_ice_failure_waits_out_the_busy_interval(monkeypatch):
    delay = _first_retry_delay(
        monkeypatch, AidotIceNotEstablished("ICE connection not established")
    )
    assert delay >= settings.read("AIDOT_BUSY_RETRY_S")


def test_another_negotiation_failure_keeps_the_ordinary_backoff(monkeypatch):
    delay = _first_retry_delay(monkeypatch, AidotNegotiationFailed("bad answer"))
    assert delay < settings.read("AIDOT_BUSY_RETRY_S")


def test_the_ice_failure_is_still_a_negotiation_failure():
    """Callers that catch AidotNegotiationFailed keep catching it."""
    assert issubclass(AidotIceNotEstablished, AidotNegotiationFailed)


def _pacer():
    # rand=1.0 takes the top of each jitter band, so the delays are exact.
    return ReconnectPacer(15.0, 300.0, rand=lambda: 1.0)


def test_sessions_that_keep_dying_fast_wait_longer_each_time():
    pacer = _pacer()
    waits = [_session_end_pacing(pacer, 5.0, had_video=True) for _ in range(6)]
    assert waits == [30.0, 60.0, 120.0, 240.0, 300.0, 300.0]


def test_a_session_that_lasted_resets_the_pacer_and_reopens_at_once():
    pacer = _pacer()
    _session_end_pacing(pacer, 5.0, had_video=True)
    _session_end_pacing(pacer, 5.0, had_video=True)
    assert _session_end_pacing(pacer, 120.0, had_video=True) == 0.0
    assert pacer.attempt == 0
    assert _session_end_pacing(pacer, 5.0, had_video=True) == 30.0


def test_a_video_less_session_is_failed_however_long_it_ran():
    pacer = _pacer()
    assert _session_end_pacing(pacer, 600.0, had_video=False) == 30.0
    assert pacer.attempt == 1


def test_a_healthy_idle_out_is_never_a_short_session():
    """A session with no viewer runs for minutes before it idles out."""
    assert _DTLS_SHORT_SESSION_S <= 60.0


def _calls_in(fn):
    src = inspect.getsource(fn)
    return {
        n.func.id
        for n in ast.walk(ast.parse(__import__("textwrap").dedent(src)))
        if isinstance(n, ast.Call) and isinstance(n.func, ast.Name)
    }


def test_sessions_are_paced_once_at_session_end_not_per_serve_cycle():
    """Pacing inside the serve cycle would sleep per ffmpeg restart, holding a
    live session; it belongs in the loop, after the session is stopped."""
    assert "_session_end_pacing" in _calls_in(CameraMixin._dtls_serve_loop_inner)
    assert "_session_end_pacing" not in _calls_in(
        client_mod._dtls_serve_loop_serve_cycle
    )
