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


async def _watch_then(cam, pm, callback, *deliveries):
    """Start the watch, deliver each (method, payload) and let the loop run
    the callbacks it was handed."""
    ok = await cam.async_watch_unbind(callback)
    for method, payload in deliveries:
        pm.deliver(method, **payload)
        await asyncio.sleep(0.01)
    return ok


def test_our_notice_calls_back_and_others_do_not(make_camera_device_client):
    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)
    calls = []
    ok = asyncio.run(
        _watch_then(
            cam,
            pm,
            lambda: calls.append(1),
            ("setDevAttrNotif", {"devId": cam.device_id, "attr": {"micEnable": "1"}}),
            ("devUnbindNotif", {"devId": "someone-else"}),
        )
    )
    assert ok is True
    assert any(t.startswith("iot/v1/c/") and t.endswith("/#") for t in pm.subscribed)
    assert calls == []
    asyncio.run(
        _watch_then(
            cam,
            pm,
            lambda: calls.append(1),
            ("devUnbindNotif", {"devId": cam.device_id}),
        )
    )
    assert calls == [1]


def test_a_notice_without_a_device_id_is_passed_on(make_camera_device_client):
    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)
    calls = []
    asyncio.run(_watch_then(cam, pm, lambda: calls.append(1), ("devUnbindNotif", {})))
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

    asyncio.run(
        _watch_then(cam, pm, boom, ("devUnbindNotif", {"devId": cam.device_id}))
    )


def test_the_callback_runs_on_the_loop_not_the_mqtt_thread(make_camera_device_client):
    import threading

    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)
    seen = []

    async def go():
        loop = asyncio.get_running_loop()
        await cam.async_watch_unbind(lambda: seen.append(threading.current_thread()))
        t = threading.Thread(
            target=pm.deliver, args=("devUnbindNotif",), kwargs={"devId": cam.device_id}
        )
        t.start()
        t.join()
        await asyncio.sleep(0.05)
        return loop

    asyncio.run(go())
    assert seen and seen[0] is threading.main_thread()


def test_a_src_addr_names_the_device(make_camera_device_client):
    pm = _Pm()
    cam = _cam(make_camera_device_client, pm)
    calls = []

    async def go():
        await cam.async_watch_unbind(lambda: calls.append(1))
        for src in (f"1.{cam.device_id}", "1.someone-else"):
            msg = json.dumps(
                {"method": "devUnbindNotif", "srcAddr": src, "payload": {}}
            )
            for h in list(pm.handlers):
                h("iot/v1/c/u1/device/devUnbindNotif", msg)
            await asyncio.sleep(0.01)

    asyncio.run(go())
    assert len(calls) == 1


def test_stop_uses_the_connection_the_handler_was_added_to(make_camera_device_client):
    pm1, pm2 = _Pm(), _Pm()
    cam = _cam(make_camera_device_client, pm1)
    asyncio.run(cam.async_watch_unbind(lambda: None))

    async def _pm2():
        return pm2

    cam._get_persistent_mqtt = _pm2  # the connection was rebuilt meanwhile
    asyncio.run(cam.async_stop_watching_unbind())
    assert pm1.handlers == [] and pm2.handlers == []
