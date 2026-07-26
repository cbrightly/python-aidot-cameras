"""Unit 3 integration: the DTLS serve loop fast-retries a DC-only decline.

Drives the REAL `CameraMixin._dtls_serve_loop_inner` with a synthetic
`AidotCameraNotReady` (what a cold camera's DC-only answer raises) and a patched
`asyncio.sleep` that records delays without waiting.  Confirms the loop's
exception handler fast-retries at 3s (bypassing the 15s inter-attempt gate) for
the bounded burst, then falls back to the 15s gate - the part of Unit 3 the
pure `_retry_policy` test can't cover (the loop's counter management).

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""
import asyncio
import types

from aidot_cameras.camera.client import CameraMixin
from aidot_cameras.exceptions import AidotCameraNotReady


class _Ready:
    def clear(self):
        pass

    def set(self):
        pass


def test_dtls_serve_loop_bursts_then_falls_back(caplog, monkeypatch):
    delays = []

    class _Fake:
        _streaming_active = True
        _keepalive_rtsp_url = "http://127.0.0.1:8999/cam.ts"
        device_id = "TESTCAM"
        _serve_ready = _Ready()
        _open_calls = 0

        async def async_open_webrtc_stream(self, *a, **k):
            self._open_calls += 1
            raise AidotCameraNotReady(self.device_id)

    fake = _Fake()

    async def _fake_sleep(d):
        delays.append(d)
        # Stop the loop once the burst has fallen back to the 15s gate.
        if d >= 15.0:
            raise asyncio.CancelledError

    monkeypatch.setattr("aidot_cameras.camera.client.asyncio.sleep", _fake_sleep)

    async def _run():
        await types.MethodType(CameraMixin._dtls_serve_loop_inner, fake)()

    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        asyncio.run(_run())

    msgs = [r.getMessage() for r in caplog.records]
    burst = [m for m in msgs if "retry 3s [burst]" in m]
    gate = [m for m in msgs if "retry 15s [gate]" in m]

    # 4 fast-retries that bypass the gate, then fall back to the 15s gate.
    assert len(burst) == 4, msgs
    assert len(gate) >= 1, msgs
    assert delays[:5] == [3.0, 3.0, 3.0, 3.0, 15.0], delays
    assert fake._open_calls == 5  # 4 burst attempts + the 1 gate attempt
