"""Houses this account does not own are skipped unless the seam is set.

The live-validation account is deliberately a secondary one with the cameras
shared to it, so the non-owned house is the *only* one holding devices.  The
same client feeds the Home Assistant integration's device list, though, so the
filter stays on by default and both directions are locked here: unset must be
byte-identical to today (the shared house is never even queried), and set must
surface its devices.
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.client import CameraClient, _include_shared_houses
from aidot_cameras.const import CONF_DEVICE_LIST
from upstream_shapes import stub_account_http

_OWNED_EMPTY = 2082907      # the CI account's own house: real, and empty
_SHARED_WITH_CAMERAS = 51744


class _FakeCloudApi:
    """The shape a shared CI account actually sees (verified against the cloud).

    Method names follow the typed shape's ``CloudApi``; ``stub_account_http``
    rebinds them onto the client's own ``async_get_*`` methods when the dict
    shape is installed, where upstream folded ``CloudApi`` into the client.
    """

    def __init__(self):
        self.queried: list[int] = []

    async def get_houses(self):
        return [
            {"id": _OWNED_EMPTY, "name": "My Home", "isOwner": True},
            {"id": _SHARED_WITH_CAMERAS, "name": "My Home", "isOwner": False},
        ]

    async def get_devices(self, house_id):
        self.queried.append(house_id)
        if house_id != _SHARED_WITH_CAMERAS:
            return []
        return [
            {"id": "cam1", "modelId": "LK.IPC.A000088",
             "productId": "p1", "aesKey": ["k"]},
        ]

    async def get_products(self, product_ids):
        return [{"id": "p1"}]


def _client_with(cloud):
    client = object.__new__(CameraClient)
    # Which surface async_get_all_device drives depends on the installed
    # upstream shape; stubbing _cloud_api alone leaves the dict shape reaching
    # into a half-built client for _base_url.
    stub_account_http(client, cloud)
    client._device_clients = {}
    return client


def test_include_shared_houses_default_off(monkeypatch):
    monkeypatch.delenv("AIDOT_INCLUDE_SHARED_HOUSES", raising=False)
    assert _include_shared_houses() is False


def test_include_shared_houses_override(monkeypatch):
    monkeypatch.setenv("AIDOT_INCLUDE_SHARED_HOUSES", "1")
    assert _include_shared_houses() is True
    monkeypatch.setenv("AIDOT_INCLUDE_SHARED_HOUSES", "0")
    assert _include_shared_houses() is False


def test_shared_house_skipped_when_unset(monkeypatch):
    """Unset is a no-op: the shared house is not merely filtered, never fetched."""
    monkeypatch.delenv("AIDOT_INCLUDE_SHARED_HOUSES", raising=False)
    cloud = _FakeCloudApi()

    # asyncio.run rather than an async test: this branch has no pytest-asyncio
    # configuration, and a seam test should not be the thing that introduces a
    # plugin dependency for the whole suite.
    result = asyncio.run(_client_with(cloud).async_get_all_device())

    assert result[CONF_DEVICE_LIST] == []
    assert cloud.queried == [_OWNED_EMPTY]


def test_shared_house_devices_surface_when_set(monkeypatch):
    """Set: the shared house is queried and its cameras come back."""
    monkeypatch.setenv("AIDOT_INCLUDE_SHARED_HOUSES", "1")
    cloud = _FakeCloudApi()

    result = asyncio.run(_client_with(cloud).async_get_all_device())

    assert [d["id"] for d in result[CONF_DEVICE_LIST]] == ["cam1"]
    assert cloud.queried == [_OWNED_EMPTY, _SHARED_WITH_CAMERAS]
