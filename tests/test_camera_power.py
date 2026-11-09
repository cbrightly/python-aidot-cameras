"""The camera's own power switch (``TurnOnOff``).

The property the official app writes most, from its live screen. Off, the
camera keeps answering MQTT but refuses to stream (no webrtcResp, measured
2026-11-09 on an A000088), so the serve loops treat it like a cloud-offline
camera rather than hammering it with opens.
"""

from __future__ import annotations

import asyncio
from types import SimpleNamespace

import pytest

import aidot_cameras.camera.client as camera_client
from aidot_cameras.camera.client import CameraMixin
from aidot_cameras.camera.models import (
    CameraStatusData,
    _camera_powered_off,
    _cloud_says_unavailable,
)


@pytest.mark.parametrize(
    "raw, expected", [("1", True), ("0", False), (1, True), (0, False)]
)
def test_turnonoff_is_read_into_powered(raw, expected):
    s = CameraStatusData()
    s.update({"TurnOnOff": raw})
    assert s.powered is expected


def test_a_notif_without_the_key_keeps_the_last_value():
    s = CameraStatusData()
    s.update({"TurnOnOff": "0"})
    s.update({"micEnable": "1"})
    assert s.powered is False


def test_unreported_is_not_off():
    dev = SimpleNamespace(status=CameraStatusData())
    assert not _camera_powered_off(dev)
    assert not _cloud_says_unavailable(dev)


def test_off_counts_as_unavailable_even_while_cloud_online():
    dev = SimpleNamespace(status=CameraStatusData(), _cloud_online_explicit=True)
    dev.status.online = True
    dev.status.powered = False
    assert _camera_powered_off(dev)
    assert _cloud_says_unavailable(dev)


def test_set_power_writes_the_property_and_records_the_state(make_camera_device_client):
    cam = make_camera_device_client("A000088")
    writes = []

    async def _set(name, value, **kw):
        writes.append((name, value))
        return True

    cam.async_set_device_attribute = _set
    assert asyncio.run(cam.async_set_power(False)) is True
    assert writes == [("TurnOnOff", 0)]
    assert cam.status.powered is False
    assert asyncio.run(cam.async_set_power(True)) is True
    assert cam.status.powered is True


def test_a_refused_write_leaves_the_state_alone(make_camera_device_client):
    cam = make_camera_device_client("A000088")

    async def _set(name, value, **kw):
        return False

    cam.async_set_device_attribute = _set
    cam.status.powered = True
    assert asyncio.run(cam.async_set_power(False)) is False
    assert cam.status.powered is True


def _stub(*, powered, online=True):
    s = SimpleNamespace(
        status=CameraStatusData(), device_id="test-device", _streaming_active=True
    )
    s.status.online = online
    s._cloud_online_explicit = True
    s.status.powered = powered
    s._chunked_wait = CameraMixin._chunked_wait.__get__(s)
    return s


def test_the_keepalive_pause_holds_while_switched_off(monkeypatch):
    """Off, the loop pauses as for a cloud-offline camera and ends the pause
    the moment the camera is switched back on, not on its own cadence."""
    monkeypatch.setattr(camera_client, "_OFFLINE_RECHECK_S", 0.01)
    monkeypatch.setattr(camera_client, "_OFFLINE_PROBE_S", 5.0)
    stub = _stub(powered=False)
    slept = []

    async def _sleep(d):
        slept.append(d)

    monkeypatch.setattr(camera_client.asyncio, "sleep", _sleep)

    async def go():
        async def _switch_on():
            await asyncio.sleep(0)  # the patched sleep: yields at once
            stub.status.powered = True

        asyncio.get_running_loop().create_task(_switch_on())
        await CameraMixin._backoff_or_offline_pause(stub, 30.0)

    asyncio.run(go())
    assert 30.0 not in slept, "no plain backoff sleep while off"


def test_the_keepalive_pause_is_plain_when_on(monkeypatch):
    stub = _stub(powered=True)
    slept = []

    async def _sleep(d):
        slept.append(d)

    monkeypatch.setattr(camera_client.asyncio, "sleep", _sleep)
    asyncio.run(CameraMixin._backoff_or_offline_pause(stub, 30.0))
    assert slept == [30.0]
