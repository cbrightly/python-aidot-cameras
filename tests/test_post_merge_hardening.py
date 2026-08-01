"""Regression tests for the post-upstream-sync hardening fixes.

Covers: discovery quiescence after close(), the camera filter on discovered IPs,
status None-defaults, the dict/model update dispatch, and the upstream
DeviceClient / CameraMixin name-collision tripwire for future upstream bumps.

The connect() login gate and the reconnect chain dedup/backoff that this file
also used to cover were fork-only hardening of a fork-only connect()/login()
pair.  Upstream owns the connection lifecycle now (a DeviceState machine plus
its own _reconnect_timer) and we do not override any of it, so those two tests
were asserting code this package no longer contains; they were dropped rather
than rewritten against upstream internals.
"""

import asyncio

import pytest
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))


from aidot.device_client import DeviceClient as UpstreamDeviceClient
from aidot.models.device_client_model import DeviceAttr
from aidot_cameras._upstream import HAS_READ_DATA_SEAM
from upstream_shapes import (
    account_record,
    arm_reconnect,
    device_record,
    make_upstream_device_client,
)

from aidot_cameras.camera.client import CameraMixin, CameraStatusData
from aidot_cameras.client import CameraClient, _is_camera
from aidot_cameras.device_client import CameraDeviceClient, DeviceStatusData
from aidot_cameras.discover import Discover


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
    # Upstream's constructor takes typed models; the raw dicts still go through
    # for the camera layer, exactly as CameraClient.get_device_client does.
    return CameraDeviceClient(
        device_record(dict(device)),
        account_record(dict(USER)),
        raw_device=dict(device),
        login_info=dict(USER),
    )


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
# teardown
# --------------------------------------------------------------------------- #

def test_no_reconnect_after_client_close():
    """Closing the account must silence an already-armed reconnect timer.

    Upstream's DeviceClient.reset() arms a ~45s delayed re-login every time a
    connection drops, and its close() only sets its closed flag - which stops
    reset() from arming a NEW one but never cancels the one already ticking.
    Left alone, a light re-opens its TCP connection about 45s after the
    integration was unloaded, leaking a socket, a receive task and a ping timer.
    Plain upstream device clients are the exposed case (cameras have their own
    login gate), so the subject here is an upstream DeviceClient.

    The handle is spelled `_reconnect_timer` on the typed shape and
    `_reconnect_handle` on the dict shape, so it is armed via `arm_reconnect`
    rather than by name - hardcoding one would arm nothing on the other shape
    and this assertion would pass without testing anything.
    """
    async def run():
        client = CameraClient(None, country_code="US")
        dc = make_upstream_device_client(dict(DEVICE), dict(USER))
        fired = []
        arm_reconnect(dc, lambda: fired.append(1), delay=0.05)
        client._device_clients[dc.info.dev_id] = dc

        await client.async_close()
        await asyncio.sleep(0.2)  # well past the armed interval

        assert fired == [], "a reconnect fired after the client was closed"

    asyncio.run(run())


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


def test_wifi_rssi_parsed_from_cloud_properties():
    s = CameraStatusData()
    assert s.wifi_rssi is None
    # cloud "properties" carry networkRssi as a (negative) dBm string
    s.update_from_camera_attributes({"networkRssi": "-56", "Battery_remaining": "80"})
    assert s.wifi_rssi == -56
    assert s.battery_remaining == 80
    # malformed value must not raise and must not clobber the prior reading
    s.update({"networkRssi": "n/a"})
    assert s.wifi_rssi == -56


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
# camera-only attributes across the upstream receive loop
# --------------------------------------------------------------------------- #

@pytest.mark.skipif(
    not HAS_READ_DATA_SEAM,
    reason=(
        "upstream's dict shape inlines the decrypt into receive_data, so there "
        "is no read_data seam to hook - see docs/UPSTREAM.md, "
        "'Known dual-support gaps'"
    ),
)
def test_raw_camera_attrs_survive_upstreams_typed_model(monkeypatch):
    """The read_data/_notify_status_update pair recovers camera-only keys.

    Upstream's receive loop feeds status from its typed DeviceAttr model, which
    has no field for Battery_remaining (or Occupancy, SDcardStatus,
    MotionDetection_*), so those keys are dropped on the way in.  read_data
    stashes the raw frame and _notify_status_update re-applies payload.attr
    before the callback fires.  Both are called polymorphically by upstream's
    receive_data, which is what makes the pair reachable at all.

    Typed shape only: the dict shape has no read_data, so this pair does not
    exist there.  Cameras never reach that loop anyway (async_login returns
    early for IPC models), which is why the gap is acceptable.
    """
    frame = {"payload": {"attr": {"Battery_remaining": "77", "OnOff": 1}}}

    async def _fake_base_read(self):
        return frame

    monkeypatch.setattr(UpstreamDeviceClient, "read_data", _fake_base_read)
    dc = make_dc(CAMERA)
    assert asyncio.run(dc.read_data()) is frame

    dc._notify_status_update()
    assert dc.status.battery_remaining == 77

    # The stash is consumed, not just read: a notify that is NOT driven by a
    # frame (reset(), login) must not re-apply a stale attribute set.
    dc.status.battery_remaining = None
    dc._notify_status_update()
    assert dc.status.battery_remaining is None


# --------------------------------------------------------------------------- #
# upstream-merge tripwire
# --------------------------------------------------------------------------- #

def test_no_silent_mro_shadowing():
    """If upstream adds a DeviceClient method whose name CameraMixin already
    defines, the upstream version wins for every name the mixin does not itself
    define first - a silent behavior change on an upstream version bump.  Keep
    this intersection explicitly reviewed."""
    intentional = {
        # CameraDeviceClient (not the mixin) deliberately overrides these, so a
        # name shared with upstream there is intended, not accidental:
        "__init__",
    }
    # Compare only non-dunder names: interpreter-added dunders vary by
    # Python version (3.13 adds __firstlineno__/__static_attributes__).
    core = {n for n in UpstreamDeviceClient.__dict__ if not n.startswith("__")}
    camera = {n for n in CameraMixin.__dict__ if not n.startswith("__")}
    collisions = (core & camera) - intentional
    assert collisions == set(), (
        f"upstream DeviceClient now shadows CameraMixin names "
        f"{sorted(collisions)}; "
        "verify intent and update this test's allowlist"
    )
