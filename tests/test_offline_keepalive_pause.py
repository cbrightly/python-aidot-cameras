"""Offline keepalive pause: failed-open retries hold while the device is
cloud-offline (CameraMixin._backoff_or_offline_pause) instead of consuming an
open-gate slot on the normal reconnect cadence."""

import asyncio
import time
from types import SimpleNamespace

import aidot.camera.client as camera_client
from aidot.camera.client import CameraMixin
from aidot.device_client import DeviceStatusData


def _stub(online: bool, explicit: bool, streaming: bool = True) -> SimpleNamespace:
    s = SimpleNamespace(
        status=DeviceStatusData(),
        device_id="test-device",
        _streaming_active=streaming,
    )
    s.status.online = online
    if explicit:
        s._cloud_online_explicit = True
    return s


def _run(coro):
    return asyncio.run(coro)


def test_online_device_sleeps_plain_delay():
    stub = _stub(online=True, explicit=True)
    t0 = time.monotonic()
    _run(CameraMixin._backoff_or_offline_pause(stub, 0.05))
    took = time.monotonic() - t0
    assert 0.04 <= took < 1.0


def test_unreported_online_state_never_pauses(monkeypatch):
    # DeviceStatusData.online defaults to False; without the explicit marker
    # that must NOT read as "offline" (a long pause here would slow every
    # camera whose device dict was never merged).
    monkeypatch.setattr(camera_client, "_OFFLINE_PROBE_S", 60.0)
    stub = _stub(online=False, explicit=False)
    t0 = time.monotonic()
    _run(CameraMixin._backoff_or_offline_pause(stub, 0.05))
    took = time.monotonic() - t0
    assert took < 1.0


def test_offline_device_holds_then_probes(monkeypatch):
    # Explicitly offline: the pause holds past the plain delay and falls
    # through after the probe interval so one probe open still runs.
    monkeypatch.setattr(camera_client, "_OFFLINE_RECHECK_S", 0.05)
    monkeypatch.setattr(camera_client, "_OFFLINE_PROBE_S", 0.3)
    stub = _stub(online=False, explicit=True)
    t0 = time.monotonic()
    _run(CameraMixin._backoff_or_offline_pause(stub, 0.01))
    took = time.monotonic() - t0
    assert took >= 0.3  # held for the whole probe interval
    assert took < 2.0


def test_offline_device_resumes_when_back_online(monkeypatch):
    monkeypatch.setattr(camera_client, "_OFFLINE_RECHECK_S", 0.05)
    monkeypatch.setattr(camera_client, "_OFFLINE_PROBE_S", 30.0)
    stub = _stub(online=False, explicit=True)

    async def scenario():
        async def flip():
            await asyncio.sleep(0.15)
            stub.status.online = True

        t0 = time.monotonic()
        await asyncio.gather(
            CameraMixin._backoff_or_offline_pause(stub, 0.01), flip()
        )
        return time.monotonic() - t0

    took = _run(scenario())
    # resumed within a couple of recheck ticks of the flip, far short of PROBE
    assert 0.15 <= took < 2.0


def test_offline_pause_exits_when_streaming_stops(monkeypatch):
    monkeypatch.setattr(camera_client, "_OFFLINE_RECHECK_S", 0.05)
    monkeypatch.setattr(camera_client, "_OFFLINE_PROBE_S", 30.0)
    stub = _stub(online=False, explicit=True)

    async def scenario():
        async def stop():
            await asyncio.sleep(0.15)
            stub._streaming_active = False

        t0 = time.monotonic()
        await asyncio.gather(
            CameraMixin._backoff_or_offline_pause(stub, 0.01), stop()
        )
        return time.monotonic() - t0

    took = _run(scenario())
    assert took < 2.0


def test_update_status_sets_explicit_marker():
    stub = SimpleNamespace(status=DeviceStatusData())
    CameraMixin.update_status_from_device(stub, {"online": False})
    assert stub.status.online is False
    assert getattr(stub, "_cloud_online_explicit", False) is True

    stub2 = SimpleNamespace(status=DeviceStatusData())
    CameraMixin.update_status_from_device(stub2, {"name": "no online key"})
    assert getattr(stub2, "_cloud_online_explicit", False) is False
