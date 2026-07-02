"""Battery-camera setKeepAliveTime renewal during streaming (app parity).

The official app re-issues setKeepAliveTime throughout a live view so a battery
camera's low-power timer doesn't return it to sleep mid-stream. We renew inside
the 25 s window; mains cameras never sleep and are skipped.
"""
import asyncio
from unittest.mock import AsyncMock, MagicMock, patch

from aidot.camera.client import CameraMixin


def _obj(battery=True, streaming=True):
    o = MagicMock()
    o.is_battery_camera = battery
    o._streaming_active = streaming
    o.device_id = "cam1"
    o._async_set_keep_alive = AsyncMock()
    return o


def test_renew_loop_skips_mains_cameras():
    o = _obj(battery=False)
    asyncio.run(CameraMixin._keepalive_renew_loop(o))
    o._async_set_keep_alive.assert_not_awaited()


def test_renew_loop_renews_battery_until_stream_stops():
    o = _obj(battery=True)
    n = {"i": 0}

    async def fake_sleep(_s):
        n["i"] += 1
        if n["i"] >= 3:
            o._streaming_active = False   # stop after the 3rd sleep

    with patch("aidot.camera.client.asyncio.sleep", fake_sleep):
        asyncio.run(CameraMixin._keepalive_renew_loop(o))
    # sleep(renew), sleep(renew), sleep(stop -> break): 2 renewals
    assert o._async_set_keep_alive.await_count == 2


def test_renew_loop_swallows_cancel():
    o = _obj(battery=True)

    async def _run():
        t = asyncio.ensure_future(CameraMixin._keepalive_renew_loop(o))
        await asyncio.sleep(0)   # let it reach the first (real) sleep
        t.cancel()               # cancel interrupts the sleep immediately
        await t                  # CancelledError is swallowed inside the loop

    asyncio.run(_run())
    o._async_set_keep_alive.assert_not_awaited()


def test_set_keep_alive_posts_expected_request():
    o = MagicMock()
    o.device_id = "cam1"
    o._aidot_v32_base = "https://api.example/v3.2"
    o._aidot_headers = MagicMock(return_value={"Authorization": "x"})
    cap = {}

    class _Resp:
        status = 200

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    class _Sess:
        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

        def post(self, url, json=None, headers=None, timeout=None):
            cap.update(url=url, json=json, headers=headers)
            return _Resp()

    with patch("aiohttp.ClientSession", lambda: _Sess()):
        asyncio.run(CameraMixin._async_set_keep_alive(o))

    assert cap["url"] == "https://api.example/v3.2/devices/cam1/setKeepAliveTime"
    assert cap["json"] == {"keepAliveTime": 25}
    assert cap["headers"] == {"Authorization": "x"}
