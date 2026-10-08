"""The LAN discovery sweep runs only for an account that has a light.

Cameras ignore the broadcast sweep entirely (their LAN address comes from
WebRTC signalling), and the sweep's only consumers are the light clients it
hands addresses to. It still started for every account on the first
``get_device_client`` - camera or not - and then broadcast on every IPv4
interface every two minutes for the life of the process. For this
integration's usual account, cameras only, that was traffic for nothing.
"""

import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.client as client_mod
from aidot_cameras.client import CameraClient

LIGHT = {
    "id": "light-1",
    "name": "Lamp",
    "modelId": "LK.WIFI.A000001",  # not a camera model
    "aesKey": ["0123456789abcdef"],
    "properties": {},
}


class _RecordingDiscover:
    started: list = []

    def __init__(self, login_info, callback):
        self.discovered_device = {}
        _RecordingDiscover.started.append(self)

    def start_repeat_broadcast(self):
        pass

    def close(self):
        pass


def _client(monkeypatch):
    _RecordingDiscover.started = []
    monkeypatch.setattr(client_mod, "CameraDiscover", _RecordingDiscover)
    client = CameraClient(None, country_code="US")
    client.login_info = {"id": "u1"}
    return client


def test_cameras_alone_never_start_the_sweep(monkeypatch, raw_device):
    async def run():
        client = _client(monkeypatch)
        client.get_device_client(raw_device("A000088"))
        client.get_device_client(raw_device("A001064"))
        return client._discover, list(_RecordingDiscover.started)

    discover, started = asyncio.run(run())
    assert discover is None and started == []


def test_the_first_light_starts_the_sweep_once(monkeypatch, raw_device):
    async def run():
        client = _client(monkeypatch)
        client.get_device_client(raw_device("A000088"))
        client.get_device_client(LIGHT)
        client.get_device_client(dict(LIGHT, id="light-2"))
        return client._discover, list(_RecordingDiscover.started)

    discover, started = asyncio.run(run())
    assert discover is not None and started == [discover]
