"""The unsupported-device summary is a fact about the account, not an event.

The line already exists to avoid a WARNING per Zigbee sensor: it counts them
and reports the total instead.  But the device list refreshes for the life of
the process, and the summary is rebuilt identically every time, so "reported
once" in the comment was only ever once *per refresh* -- 143 identical lines
in 11.7 hours on a real box.

It is worth INFO when it changes (a new accessory appeared, or one this
version cannot build stopped being skipped) and worth nothing at all when it
does not.  So: INFO on a change, DEBUG on a repeat.
"""

import asyncio
import logging
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.client import CameraClient
from upstream_shapes import stub_account_http

_HOUSE = 4242
_LOGGER_NAME = "aidot_cameras.client"


class _Cloud:
    """One buildable camera plus whatever unsupported devices a test wants."""

    def __init__(self, unsupported):
        self.unsupported = unsupported

    async def get_houses(self):
        return [{"id": _HOUSE, "name": "My Home", "isOwner": True}]

    async def get_devices(self, house_id):
        return [
            {"id": "cam1", "modelId": "LK.IPC.A000088",
             "productId": "p1", "aesKey": ["k"]},
            *self.unsupported,
        ]

    async def get_products(self, product_ids):
        return [{"id": "p1"}]


def _sensor(dev_id, model):
    # `[None]`, not `[]`: an empty aesKey is fine, and only the null-first-entry
    # shape is the one upstream's DeviceClient cannot be built for.  Taking the
    # obvious-looking `[]` here made every assertion below pass for the wrong
    # reason - nothing was skipped, so there was no summary to grade.
    return {"id": dev_id, "modelId": model, "productId": "p1", "aesKey": [None]}


def _client(cloud):
    client = object.__new__(CameraClient)
    stub_account_http(client, cloud)
    client._device_clients = {}
    return client


def _summary_records(caplog, level):
    """Only the SUMMARY line.

    The per-device line also says "no usable aesKey", and matching on that
    alone counted it too - which read as the summary having been emitted twice.
    """
    return [r for r in caplog.records
            if r.name == _LOGGER_NAME
            and r.levelno == level
            and r.getMessage().startswith("skipped ")
            and "device(s) with no usable aesKey" in r.getMessage()]


def test_first_summary_is_info(caplog):
    caplog.set_level(logging.DEBUG, logger=_LOGGER_NAME)
    client = _client(_Cloud([_sensor("z1", "lk.ZB-DoorSensor-D0003")]))

    asyncio.run(client.async_get_all_device())

    info = _summary_records(caplog, logging.INFO)
    assert len(info) == 1
    assert "lk.ZB-DoorSensor-D0003 x1" in info[0].getMessage()


def test_identical_repeat_drops_to_debug(caplog):
    caplog.set_level(logging.DEBUG, logger=_LOGGER_NAME)
    client = _client(_Cloud([_sensor("z1", "lk.ZB-DoorSensor-D0003")]))

    asyncio.run(client.async_get_all_device())
    caplog.clear()
    asyncio.run(client.async_get_all_device())

    assert _summary_records(caplog, logging.INFO) == []
    # Still said, just not at a level that fills a user's log.
    assert len(_summary_records(caplog, logging.DEBUG)) == 1


def test_a_changed_inventory_is_info_again(caplog):
    caplog.set_level(logging.DEBUG, logger=_LOGGER_NAME)
    cloud = _Cloud([_sensor("z1", "lk.ZB-DoorSensor-D0003")])
    client = _client(cloud)

    asyncio.run(client.async_get_all_device())
    cloud.unsupported.append(_sensor("z2", "lk.ZB-MotionSensor-D0003"))
    caplog.clear()
    asyncio.run(client.async_get_all_device())

    info = _summary_records(caplog, logging.INFO)
    assert len(info) == 1
    assert "x2" in info[0].getMessage() or "MotionSensor" in info[0].getMessage()


def test_going_empty_is_reported_once(caplog):
    """The skips ending is a change worth one line, not silence forever."""
    caplog.set_level(logging.DEBUG, logger=_LOGGER_NAME)
    cloud = _Cloud([_sensor("z1", "lk.ZB-DoorSensor-D0003")])
    client = _client(cloud)

    asyncio.run(client.async_get_all_device())
    cloud.unsupported.clear()
    caplog.clear()
    asyncio.run(client.async_get_all_device())

    # Nothing is skipped now, so there is no summary at all - and critically
    # the next time something IS skipped it must count as a change again.
    assert _summary_records(caplog, logging.INFO) == []
    cloud.unsupported.append(_sensor("z1", "lk.ZB-DoorSensor-D0003"))
    caplog.clear()
    asyncio.run(client.async_get_all_device())
    assert len(_summary_records(caplog, logging.INFO)) == 1
