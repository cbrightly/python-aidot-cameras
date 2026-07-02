"""Unit tests for the base-DeviceClient local-login guard + throttle.

The TCP:10000 control channel is the LIGHT protocol; cameras must never use it
(they use the separate CameraLanClient + WebRTC signaling for their LAN IP). A
camera reaching async_login would hammer a refusing port and spam
"login read status error". These tests lock the camera-exclusion and the
re-login throttle without a real socket.
"""
import asyncio
import os
import sys
from types import SimpleNamespace

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot.device_client as dc_mod

DeviceClient = dc_mod.DeviceClient


def _client(model_id):
    c = DeviceClient.__new__(DeviceClient)
    c.info = SimpleNamespace(model_id=model_id)
    c._ip_address = "192.168.1.50"
    c._connecting = False
    c._connect_and_login = False
    c._last_login_attempt = 0.0
    c._connected = []  # record connect() calls
    async def _fake_connect(ip):
        c._connected.append(ip)
    c.connect = _fake_connect
    return c


def test_camera_never_does_base_login():
    cam = _client("LK.IPC.A001513")          # battery SDES camera
    asyncio.run(cam.async_login())
    assert cam._connected == []               # camera excluded - no TCP:10000 login


def test_ptz_camera_excluded():
    cam = _client("LK.IPC.A001064")
    asyncio.run(cam.async_login())
    assert cam._connected == []


def test_light_still_logs_in():
    light = _client("lk.WIFI-RGBWLight-D0006")
    asyncio.run(light.async_login())
    assert light._connected == ["192.168.1.50"]   # lights keep the base login


def test_unknown_model_logs_in():
    # No "IPC" in model -> treated as a (light) device that uses the base channel.
    dev = _client("")
    asyncio.run(dev.async_login())
    assert dev._connected == ["192.168.1.50"]


def test_update_ip_address_throttles_relogin():
    light = _client("lk.WIFI-RGBWLight-D0006")
    spawned = []
    # update_ip_address schedules async_login via create_task; capture the calls
    # by counting how many times the throttle lets a login attempt through.
    light._login_task = None
    orig = dc_mod.asyncio.create_task

    def _count_task(coro):
        spawned.append(1)
        coro.close()            # don't actually run the login
        return SimpleNamespace(done=lambda: True, cancel=lambda: None)

    dc_mod.asyncio.create_task = _count_task
    try:
        light.update_ip_address("192.168.1.50")   # first: allowed
        light.update_ip_address("192.168.1.50")   # immediate repeat: throttled
        light.update_ip_address("192.168.1.50")   # still throttled
    finally:
        dc_mod.asyncio.create_task = orig
    assert len(spawned) == 1, f"expected 1 login within the 30s window, got {len(spawned)}"


if __name__ == "__main__":
    import traceback
    _fail = 0
    for _k, _v in sorted(globals().items()):
        if _k.startswith("test_"):
            try:
                _v()
                print(f"PASS {_k}")
            except Exception:
                _fail += 1
                print(f"FAIL {_k}")
                traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
