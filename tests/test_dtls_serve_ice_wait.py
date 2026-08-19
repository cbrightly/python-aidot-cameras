"""The ICE wait must not inherit the signalling timeout.

`_async_open_webrtc_stream_impl` used one `timeout` for two SEQUENTIAL phases:
the signalling wait (`_init_deadline`, webrtc_open.py:2467) and then the ICE
wait (`ice_wait_timeout(default_timeout=timeout)`, webrtc_open.py:3734). So
raising the serve-loop open timeout to 75s to recover late answers also doubled
the worst case to ~150s on a gate whose cap is 2 - and that second half was
never what the evidence asked for. Every measured late answer (30.7s to 99.5s,
all code=200) was a SIGNALLING latency; nothing measured said ICE needed longer.

`ice_wait_timeout`'s own docstring names the reason this matters: 45s "is past
the ~30 s deadline Home Assistant's stream worker allows, which is the failure
this project already learned the hard way".

So the serve loop now asks for a long signalling wait and the ICE wait it always
had. Other callers - notably the SDES keepalive loop, which passes timeout=120 -
pass nothing and keep the previous inherit-the-timeout behaviour, because
nothing in this investigation measured battery-camera ICE.

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""
import asyncio
import types

import aidot_cameras.camera.client as camera_client
from aidot_cameras.camera.client import CameraMixin


def test_serve_ice_wait_stays_at_the_old_thirty_seconds():
    assert camera_client._DTLS_SERVE_ICE_WAIT_S == 30.0


def test_serve_ice_wait_is_shorter_than_the_open_timeout():
    # The whole point of splitting them: signalling gets the long budget, ICE
    # does not. If these are ever equal again the split has been undone.
    assert (camera_client._DTLS_SERVE_ICE_WAIT_S
            < camera_client._DTLS_SERVE_OPEN_TIMEOUT_S)


def test_serve_ice_wait_is_env_tunable(monkeypatch):
    from aidot_cameras.camera.client import _parse_env_float
    monkeypatch.setenv("AIDOT_DTLS_SERVE_ICE_WAIT_S", "12.5")
    assert _parse_env_float("AIDOT_DTLS_SERVE_ICE_WAIT_S", 30.0) == 12.5


class _Ready:
    def clear(self):
        pass

    def set(self):
        pass


def test_dtls_serve_loop_passes_both_budgets():
    calls = []

    class _Fake:
        _streaming_active = True
        _keepalive_rtsp_url = "http://127.0.0.1:8999/cam.ts"
        device_id = "TESTCAM"
        _serve_ready = _Ready()

        async def async_open_webrtc_stream(self, *a, **k):
            calls.append(k)
            raise asyncio.CancelledError

    fake = _Fake()

    async def _run():
        await types.MethodType(CameraMixin._dtls_serve_loop_inner, fake)()

    asyncio.run(_run())

    assert calls, "async_open_webrtc_stream was never called"
    assert calls[0].get("timeout") == camera_client._DTLS_SERVE_OPEN_TIMEOUT_S
    assert (calls[0].get("ice_wait_timeout_s")
            == camera_client._DTLS_SERVE_ICE_WAIT_S)


def test_other_callers_keep_inheriting_the_timeout():
    # Passing nothing must mean "behave as before": the SDES keepalive loop
    # opens with timeout=120 and its ICE budget must not silently become 30.
    import inspect
    sig = inspect.signature(CameraMixin._async_open_webrtc_stream_impl)
    assert sig.parameters["ice_wait_timeout_s"].default is None
