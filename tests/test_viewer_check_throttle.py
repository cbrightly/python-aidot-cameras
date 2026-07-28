"""The viewer check must not be asked of go2rtc on every watchdog tick.

The DTLS watchdog loops twice a second, and the go2rtc query opens a fresh HTTP
session per call - so an unthrottled check is two requests per second per camera,
forever, aimed at the very service that also has to serve the video. On a small
fleet that was enough to stop go2rtc answering at all, which presents as no video
anywhere. Idle release is measured in minutes, so a few seconds of staleness in
the answer costs nothing.
"""
import asyncio

import aidot_cameras.camera.client as cc
from aidot_cameras.device_client import CameraDeviceClient


def _client(answer):
    c = CameraDeviceClient.__new__(CameraDeviceClient)
    c._go2rtc_url = None
    c._keepalive_rtsp_url = "http://127.0.0.1:18931/x.ts"
    c.device_id = "abc123abc123def"
    c._viewer_cache = (0.0, None)
    calls = []

    def _probe(port):
        calls.append(port)
        return answer

    c._sdes_serve_consumer_present = _probe
    return c, calls


def test_repeated_calls_hit_the_backend_once():
    c, calls = _client(True)

    async def go():
        for _ in range(40):          # 40 watchdog ticks
            await c._viewer_present(18931)
    asyncio.run(go())
    assert len(calls) == 1, f"backend hit {len(calls)} times, expected 1"


def test_the_cached_answer_is_returned_verbatim():
    for answer in (True, False, None):
        c, _ = _client(answer)

        async def go():
            first = await c._viewer_present(18931)
            second = await c._viewer_present(18931)
            return first, second
        first, second = asyncio.run(go())
        assert first == answer and second == answer


def test_the_cache_expires_so_the_answer_can_change():
    c, calls = _client(True)

    async def go():
        await c._viewer_present(18931)
        # Pretend the interval has elapsed.
        stamp, cached = c._viewer_cache
        c._viewer_cache = (stamp - cc._VIEWER_CHECK_INTERVAL_S - 1, cached)
        await c._viewer_present(18931)
    asyncio.run(go())
    assert len(calls) == 2


def test_the_interval_is_long_enough_to_matter():
    # The watchdog ticks every 0.5s; anything under a couple of seconds would
    # still be a request storm.
    assert cc._VIEWER_CHECK_INTERVAL_S >= 5.0
