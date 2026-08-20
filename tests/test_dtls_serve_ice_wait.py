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

The budget is clamped to the caller's own timeout, because `ice_wait_timeout`
promises exactly that (protocol.py) and the two values are now independent env
reads that can disagree.

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""
import asyncio
import types

import aidot_cameras.camera.client as camera_client
from aidot_cameras.camera.client import CameraMixin


def test_serve_ice_wait_defaults_to_the_old_thirty_seconds(monkeypatch):
    # delenv first: the constant is bound at import, so without this the
    # assertion is a statement about the developer's shell, not the code.
    monkeypatch.delenv("AIDOT_DTLS_SERVE_ICE_WAIT_S", raising=False)
    from aidot_cameras.camera.client import _parse_env_float
    assert _parse_env_float("AIDOT_DTLS_SERVE_ICE_WAIT_S", 30.0) == 30.0


def test_the_constant_is_wired_to_the_env_var_it_documents():
    # The parse helper is generic, so exercising it proves nothing about the
    # wiring. Pin the wiring itself: a constant reading the wrong env name is
    # exactly the bug a _parse_env_float test cannot see.
    import inspect
    src = inspect.getsource(camera_client)
    assert ('_DTLS_SERVE_ICE_WAIT_S = _parse_env_float('
            '"AIDOT_DTLS_SERVE_ICE_WAIT_S", 30.0)') in src


def test_ice_budget_never_extends_the_callers_own_timeout():
    """ice_wait_timeout promises it (protocol.py): "a caller asking for 5 s
    means 5 s". The serve loop's ICE budget is read from its OWN env var, so
    without a clamp an operator setting AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S=20 for
    faster failure would get 20s signalling + 30s ICE = 50s per failed attempt,
    worse than the 40s the inherit-the-timeout code gave - on the knob whose
    entire purpose is to fail faster.
    """
    from aidot_cameras.camera.webrtc_open import _resolve_ice_budget
    assert _resolve_ice_budget(20.0, 30.0) == 20.0   # clamped to the caller
    assert _resolve_ice_budget(75.0, 30.0) == 30.0   # the split still applies
    assert _resolve_ice_budget(75.0, None) == 75.0   # unset inherits, as before


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
    assert (calls[0].get("_ice_wait_timeout_s")
            == camera_client._DTLS_SERVE_ICE_WAIT_S)


def test_other_callers_keep_inheriting_the_timeout():
    # Passing nothing must mean "behave as before": the SDES keepalive loop
    # opens with timeout=120 and its ICE budget must not silently become 30.
    import inspect
    sig = inspect.signature(CameraMixin._async_open_webrtc_stream_impl)
    assert sig.parameters["_ice_wait_timeout_s"].default is None
