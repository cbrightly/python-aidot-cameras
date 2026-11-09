"""A device removed from the account is announced, not discovered at a poll.

The cloud sends ``devUnbindNotif`` on the account's topics; the app's
device-delete strategy acts on it at once. ``async_watch_unbind`` rides the
persistent MQTT connection and calls back for this device's notice.
"""

from __future__ import annotations

import asyncio
import json


class _Pm:
    def __init__(self) -> None:
        self.handlers: list = []
        self.subscribed: list = []

    def add_handler(self, cb):
        self.handlers.append(cb)
        return cb

    def remove_handler(self, cb):
        self.handlers.remove(cb)

    async def subscribe(self, topics):
        self.subscribed.extend(topics)

    def deliver(self, method, **payload):
        msg = json.dumps({"method": method, "payload": payload})
        for h in list(self.handlers):
            h("iot/v1/c/u1/device/" + method, msg)


def _cam(make_camera_device_client, pm):
    cam = make_camera_device_client("A000088")

    async def _pm():
        return pm

    cam._get_persistent_mqtt = _pm
    return cam


def test_our_notice_calls_back_and_others_do_not(make_camera_device_client):
    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)
    calls = []
    assert asyncio.run(cam.async_watch_unbind(lambda: calls.append(1))) is True
    assert any(t.startswith("iot/v1/c/") and t.endswith("/#") for t in pm.subscribed)
    pm.deliver("setDevAttrNotif", devId=cam.device_id, attr={"micEnable": "1"})
    pm.deliver("devUnbindNotif", devId="someone-else")
    assert calls == []
    pm.deliver("devUnbindNotif", devId=cam.device_id)
    assert calls == [1]


def test_a_notice_without_a_device_id_is_passed_on(make_camera_device_client):
    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)
    calls = []
    asyncio.run(cam.async_watch_unbind(lambda: calls.append(1)))
    pm.deliver("devUnbindNotif")
    assert calls == [1]


def test_stopping_removes_the_handler_and_watching_twice_keeps_one(
    make_camera_device_client,
):
    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)
    asyncio.run(cam.async_watch_unbind(lambda: None))
    asyncio.run(cam.async_watch_unbind(lambda: None))
    assert len(pm.handlers) == 1
    asyncio.run(cam.async_stop_watching_unbind())
    assert pm.handlers == []
    asyncio.run(cam.async_stop_watching_unbind())  # idempotent


def test_no_mqtt_means_false(make_camera_device_client):
    cam = make_camera_device_client("A000088")

    async def _none():
        return None

    cam._get_persistent_mqtt = _none
    assert asyncio.run(cam.async_watch_unbind(lambda: None)) is False


def test_a_failing_callback_is_contained(make_camera_device_client):
    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)

    def boom():
        raise RuntimeError("x")

    asyncio.run(cam.async_watch_unbind(boom))
    pm.deliver("devUnbindNotif", devId=cam.device_id)  # must not raise
