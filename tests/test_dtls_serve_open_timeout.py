"""AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S: env-tunable timeout for the DTLS serve
loop's async_open_webrtc_stream call (aidot_cameras/camera/client.py, ~line 3502).

Without a ``timeout`` kwarg that call is hard-pinned to the 30.0s default in
_async_open_webrtc_stream_impl (webrtc_open.py ~146-151); a dead/wedged DTLS
camera then burns a full attempt per open.  This makes it tunable, and the
default is now 75s: at 30s the offer-resend was cut short and answers that
arrived at 30.7-99.5s were thrown away (see the constant's own comment).

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""
import asyncio
import types

import aidot_cameras.camera.client as camera_client
from aidot_cameras.camera.client import CameraMixin, _parse_env_float


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


def test_module_constant_outlasts_the_second_offer_resend():
    # _resend_webrtcreq re-publishes the same offer at 15s and again at 30s.
    # The default MUST outlast the second resend by a wide margin, or the
    # attempt dies at the moment its last resend goes out and the camera's
    # answer (measured at 30.7-99.5s, all code=200) lands with nobody waiting.
    assert camera_client._DTLS_SERVE_OPEN_TIMEOUT_S == 75.0
    assert camera_client._DTLS_SERVE_OPEN_TIMEOUT_S > 30.0 + 15.0


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
