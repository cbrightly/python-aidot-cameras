"""Regression tests for the post-upstream-sync hardening fixes.

Covers: the connect() login gate, reconnect chain dedup/backoff, discovery
quiescence after close(), the camera filter on discovered IPs, status
None-defaults, the dict/model update dispatch, and the DeviceClient/
CameraMixin name-collision tripwire for future upstream merges.
"""

import asyncio
import os
import sys
import time

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

import pytest

from aidot.camera.client import CameraMixin, CameraStatusData, CameraDeviceInformation
from aidot.client import _is_camera
from aidot.device_client import DeviceClient, DeviceStatusData
from aidot.discover import Discover
from aidot.models.device_client_model import DeviceAttr


DEVICE = {
    "id": "dev1",
    "name": "Test Light",
    "modelId": "LK.light.A001497",
    "aesKey": ["k" * 16],
    "password": "pw",
    "online": True,
}
CAMERA = {**DEVICE, "id": "cam1", "name": "Test Cam", "modelId": "LK.IPC.A000088"}
USER = {"id": "user1", "region": "us"}


def make_dc(device=DEVICE):
    return DeviceClient(dict(device), dict(USER))


# --------------------------------------------------------------------------- #
# connect() gate
# --------------------------------------------------------------------------- #

def test_connect_gate_ignores_cloud_seeded_online(monkeypatch):
    """A swallowed login failure must not report as connected just because
    the cloud device dict said online=true."""
    dc = make_dc()
    dc.status.online = True  # cloud-seeded (update_status_from_device)

    class _W:
        def get_extra_info(self, _):
            import socket as _s
            s = _s.socket()
            return s

    async def fake_open(*a, **k):
        return object(), _W()

    async def fake_login(self):
        return  # login fails silently (exception swallowed inside login())

    monkeypatch.setattr(asyncio, "open_connection", fake_open)
    monkeypatch.setattr(DeviceClient, "login", fake_login)
    asyncio.run(dc.connect("127.0.0.1"))
    assert dc.connect_and_login is False


# --------------------------------------------------------------------------- #
# reconnect chain
# --------------------------------------------------------------------------- #

def test_schedule_reconnect_single_chain_and_backoff():
    async def run():
        dc = make_dc()
        dc._ip_address = "127.0.0.1"
        dc._last_login_attempt = time.monotonic()  # recent attempt -> backoff

        dc._schedule_reconnect()
        first_handle = dc._reconnect_handle
        assert first_handle is not None
        assert dc._login_task is None  # backoff floor suppressed immediate login

        dc._schedule_reconnect()  # concurrent reset path
        assert dc._reconnect_handle is first_handle  # no second chain

        first_handle.cancel()

    asyncio.run(run())


def test_schedule_reconnect_noop_after_close():
    async def run():
        dc = make_dc()
        dc._ip_address = "127.0.0.1"
        dc._is_close = True
        dc._schedule_reconnect()
        assert dc._reconnect_handle is None

    asyncio.run(run())


# --------------------------------------------------------------------------- #
# discovery
# --------------------------------------------------------------------------- #

def test_discover_quiescent_after_close():
    async def run():
        d = Discover({"id": "user1"}, None)
        d.close()
        await d._do_broadcast()  # in-flight task firing after close
        await d._ensure_sockets()
        assert d._protocols == []  # no sockets recreated

    asyncio.run(run())


def test_camera_filter():
    assert _is_camera(make_dc(CAMERA)) is True
    assert _is_camera(make_dc(DEVICE)) is False


# --------------------------------------------------------------------------- #
# status semantics
# --------------------------------------------------------------------------- #

def test_status_defaults_are_unknown():
    s = CameraStatusData()
    assert s.dimming is None and s.cct is None and s.rgbw is None and s.rgdb is None


def test_update_dispatch_model_vs_dict():
    s = CameraStatusData()
    s.update(DeviceAttr(OnOff=1, CCT=4000))
    assert s.on == 1 and s.cct == 4000
    # dict path: camera keys applied, light keys ignored (model path owns them)
    s.update({"Battery_remaining": "77", "CCT": 9999})
    assert s.battery_remaining == 77
    assert s.cct == 4000
    # malformed model-path payload must not raise
    s.update("garbage")
    s.update(["garbage"])


def test_camera_state_carry_forward():
    """State seeded into the core status object before the camera swap must
    survive on the replacement object."""
    dc = make_dc()
    # simulate: as if the core constructor had seeded state pre-swap
    core_status = DeviceStatusData()
    core_status.online = True
    dc.status = core_status
    dc._init_camera_state(dict(DEVICE), dict(USER))
    assert isinstance(dc.status, CameraStatusData)
    assert dc.status.online is True


# --------------------------------------------------------------------------- #
# upstream-merge tripwire
# --------------------------------------------------------------------------- #

def test_no_silent_mro_shadowing():
    """If upstream adds a DeviceClient method whose name CameraMixin already
    defines, the upstream version silently shadows the camera one after a
    conflict-free merge.  Keep this intersection explicitly reviewed."""
    intentional = {
        # device_client.py deliberately overrides/joins these:
        "__init__", "__doc__", "__module__", "__qualname__", "__dict__",
        "__weakref__", "__annotations__",
    }
    core = set(DeviceClient.__dict__)
    camera = set(CameraMixin.__dict__)
    collisions = (core & camera) - intentional
    assert collisions == set(), (
        f"DeviceClient now shadows CameraMixin names {sorted(collisions)}; "
        "verify intent and update this test's allowlist"
    )
