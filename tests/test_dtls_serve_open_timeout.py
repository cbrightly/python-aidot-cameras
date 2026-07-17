"""AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S: env-tunable timeout for the DTLS serve
loop's async_open_webrtc_stream call (aidot/camera/client.py, ~line 3502).

Without a ``timeout`` kwarg that call is hard-pinned to the 30.0s default in
_async_open_webrtc_stream_impl (webrtc_open.py ~146-151); a dead/wedged DTLS
camera then burns a full 30s per open attempt.  This makes it tunable while
keeping the default unchanged (battery cameras can legitimately need the full
30s to wake).

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""
import asyncio
import types

import aidot.camera.client as camera_client
from aidot.camera.client import CameraMixin, _parse_env_float


# --- _parse_env_float: the try/except-fallback idiom used elsewhere in this
# module for numeric AIDOT_* env vars (see _get_webrtc_open_gate /
# _get_stream_slots' AIDOT_MAX_CONCURRENT_OPENS / _STREAMS handling). ------- #

def test_parse_env_float_defaults_when_unset(monkeypatch):
    monkeypatch.delenv("AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S", raising=False)
    assert _parse_env_float("AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S", 30.0) == 30.0


def test_parse_env_float_override(monkeypatch):
    monkeypatch.setenv("AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S", "12.5")
    assert _parse_env_float("AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S", 30.0) == 12.5


def test_parse_env_float_malformed_falls_back_to_default(monkeypatch):
    monkeypatch.setenv("AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S", "not-a-number")
    assert _parse_env_float("AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S", 30.0) == 30.0


def test_module_constant_defaults_to_30():
    # Guards the preserved-behavior requirement: unset env -> 30.0, matching
    # _async_open_webrtc_stream_impl's prior hard-pinned default exactly.
    assert camera_client._DTLS_SERVE_OPEN_TIMEOUT_S == 30.0


# --- Nice-to-have: the serve loop passes the configured timeout through ---- #

class _Ready:
    def clear(self):
        pass

    def set(self):
        pass


def test_dtls_serve_loop_passes_configured_timeout(monkeypatch):
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
