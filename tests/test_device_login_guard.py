"""Unit tests for CameraDeviceClient's local-login guard.

The TCP:10000 control channel is the LIGHT protocol; cameras must never use it
(they use the separate CameraLanClient + WebRTC signaling for their LAN IP). A
camera reaching async_login would hammer a refusing port and spam
"login read status error". These tests lock the camera exclusion - and the fact
that a non-camera still reaches upstream's login - without a real socket.
"""
import asyncio
import os
import sys
from types import SimpleNamespace

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest
from aidot.device_client import DeviceClient as UpstreamDeviceClient

from aidot_cameras.device_client import CameraDeviceClient


@pytest.fixture(autouse=True)
def _record_base_login(monkeypatch):
    """Replace upstream's async_login with a recorder.

    The TCP:10000 connect itself is upstream's code; what belongs to us is
    purely whether the override delegates to it.  Recording the base call keeps
    the assertion on our contract and off upstream's socket handling.
    """
    calls = []

    async def _fake_base_login(self):
        calls.append(self._ip_address)

    monkeypatch.setattr(UpstreamDeviceClient, "async_login", _fake_base_login)
    return calls


def _client(model_id):
    c = CameraDeviceClient.__new__(CameraDeviceClient)
    c.info = SimpleNamespace(model_id=model_id)
    c._ip_address = "192.168.1.50"
    return c


def test_camera_never_does_base_login(_record_base_login):
    cam = _client("LK.IPC.A001513")          # battery SDES camera
    asyncio.run(cam.async_login())
    assert _record_base_login == []           # camera excluded - no TCP:10000 login


def test_ptz_camera_excluded(_record_base_login):
    cam = _client("LK.IPC.A001064")
    asyncio.run(cam.async_login())
    assert _record_base_login == []


def test_light_still_logs_in(_record_base_login):
    light = _client("lk.WIFI-RGBWLight-D0006")
    asyncio.run(light.async_login())
    assert _record_base_login == ["192.168.1.50"]   # lights keep the base login


def test_unknown_model_logs_in(_record_base_login):
    # No "IPC" in model -> treated as a (light) device that uses the base channel.
    dev = _client("")
    asyncio.run(dev.async_login())
    assert _record_base_login == ["192.168.1.50"]


# NOTE: the pre-inversion fork also carried a 30-second re-login throttle inside
# update_ip_address, and a test for it lived here.  update_ip_address is now
# upstream's (aidot.device_client) and we do not override it: upstream suppresses
# the repeat differently, by only spawning a login while its state machine is
# DeviceState.IDLE.  Asserting that is asserting upstream's internals, so the
# test was dropped; test_upstream_compat.py locks the method's existence.


if __name__ == "__main__":
    # The tests take a pytest fixture now, so run them through pytest.
    raise SystemExit(pytest.main([__file__]))
