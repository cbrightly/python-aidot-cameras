"""The cloud's event notices (devEventNotif / eventNotify) poke the motion poll."""

from __future__ import annotations

import asyncio
import json


class _Pm:
    def __init__(self) -> None:
        self.handlers: list = []

    def add_handler(self, cb):
        self.handlers.append(cb)
        return cb

    def remove_handler(self, cb):
        self.handlers.remove(cb)

    async def subscribe(self, topics):
        pass

    def deliver(self, method, **payload):
        for h in list(self.handlers):
            h(
                "iot/v1/c/u1/device/" + method,
                json.dumps({"method": method, "payload": payload}),
            )


def _cam(make_camera_device_client, pm):
    cam = make_camera_device_client("A000088")

    async def _pm():
        return pm

    cam._get_persistent_mqtt = _pm
    return cam


def test_our_event_notice_calls_back(make_camera_device_client):
    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)
    calls = []

    async def go():
        assert await cam.async_watch_events(lambda: calls.append(1)) is True
        pm.deliver("devEventNotif", devId=cam.device_id, eventType=1)
        pm.deliver("eventNotify", devId="someone-else")
        pm.deliver("setDevAttrNotif", devId=cam.device_id, attr={})
        await asyncio.sleep(0.01)

    asyncio.run(go())
    assert calls == [1]


def test_poke_wakes_the_motion_poll_wait(make_camera_device_client):
    cam = make_camera_device_client("A000088")

    async def go():
        cam._motion_poke = asyncio.Event()
        cam.poke_motion_poll()
        await asyncio.wait_for(cam._motion_poke.wait(), 0.5)

    asyncio.run(go())


def test_poke_without_a_running_poll_is_harmless(make_camera_device_client):
    cam = make_camera_device_client("A000088")
    cam.poke_motion_poll()
