"""``async_sleep_camera``: the app's wake-or-sleep request, sleep direction."""

from __future__ import annotations

import asyncio
import json


def _posts(cam, answers):
    seen = []

    async def _post(url, headers, body, *, label="", **kw):
        seen.append((url.rsplit("/", 1)[-1], json.loads(body)["status"], label))
        return answers.pop(0) if answers else True

    cam._async_post_ok = _post
    cam._leedarson_headers = lambda: {}
    cam._owner_id = lambda: "o"
    return seen


def test_sleep_posts_the_low_power_endpoint_with_status_sleep(
    make_camera_device_client,
):
    cam = make_camera_device_client("A001513")
    seen = _posts(cam, [True])
    assert asyncio.run(cam.async_sleep_camera()) is True
    assert seen == [("lowPowerActiveState", "sleep", "http sleep")]


def test_wake_still_posts_wakeup(make_camera_device_client):
    cam = make_camera_device_client("A001513")
    seen = _posts(cam, [True])
    assert asyncio.run(cam.async_wake_camera()) is True
    assert seen == [("lowPowerActiveState", "wakeup", "http wakeup")]


def test_sleep_retries_once_then_gives_up(make_camera_device_client, monkeypatch):
    import aidot_cameras.camera.client as cc

    async def _nosleep(d):
        return None

    monkeypatch.setattr(cc.asyncio, "sleep", _nosleep)
    cam = make_camera_device_client("A001513")
    seen = _posts(cam, [False, False])
    assert asyncio.run(cam.async_sleep_camera()) is False
    assert len(seen) == 2


def test_a_mains_camera_is_not_put_to_sleep(make_camera_device_client):
    cam = make_camera_device_client("A000088")
    seen = _posts(cam, [])
    assert asyncio.run(cam.async_sleep_camera()) is True
    assert seen == []


def test_no_pause_after_the_last_attempt(make_camera_device_client, monkeypatch):
    import aidot_cameras.camera.client as cc

    pauses = []

    async def _sleep(d):
        pauses.append(d)

    monkeypatch.setattr(cc.asyncio, "sleep", _sleep)
    cam = make_camera_device_client("A001513")
    _posts(cam, [False, False])
    asyncio.run(cam.async_wake_camera())
    assert pauses == [1.0]


def test_keep_awake_renews_every_ten_seconds_until_a_stream_takes_over(
    make_camera_device_client, monkeypatch
):
    import aidot_cameras.camera.client as cc

    cam = make_camera_device_client("A001513")
    renewals = []
    now = [0.0]

    async def _renew(keep_alive_s=25):
        renewals.append(now[0])

    async def _sleep(d):
        now[0] += d
        if now[0] >= 35:
            cam._streaming_active = True  # a view opened: its loop renews now

    cam._async_set_keep_alive = _renew
    cam._streaming_active = False
    monkeypatch.setattr(cc.asyncio, "sleep", _sleep)

    class _Loop:
        def time(self):
            return now[0]

    monkeypatch.setattr(cc.asyncio, "get_running_loop", lambda: _Loop())
    asyncio.run(cam.async_keep_awake(90))
    assert renewals == [0.0, 10.0, 20.0, 30.0]


def test_keep_awake_does_nothing_on_a_mains_camera(make_camera_device_client):
    cam = make_camera_device_client("A000088")
    called = []

    async def _renew(keep_alive_s=25):
        called.append(1)

    cam._async_set_keep_alive = _renew
    asyncio.run(cam.async_keep_awake(60))
    assert called == []
