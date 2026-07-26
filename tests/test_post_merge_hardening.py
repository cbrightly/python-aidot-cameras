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
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))


from aidot.device_client import DeviceClient as UpstreamDeviceClient
from aidot.models.auth_model import UserInformation
from aidot.models.device_client_model import DeviceAttr
from aidot.models.device_model import DeviceModel

from aidot_cameras.camera.client import CameraMixin, CameraStatusData
from aidot_cameras.client import _is_camera
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
        DeviceModel.from_json(data=dict(device)),
        UserInformation.from_json(data=dict(USER)),
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

def test_raw_camera_attrs_survive_upstreams_typed_model(monkeypatch):
    """The read_data/_notify_status_update pair recovers camera-only keys.

    Upstream's receive loop feeds status from its typed DeviceAttr model, which
    has no field for Battery_remaining (or Occupancy, SDcardStatus,
    MotionDetection_*), so those keys are dropped on the way in.  read_data
    stashes the raw frame and _notify_status_update re-applies payload.attr
    before the callback fires.  Both are called polymorphically by upstream's
    receive_data, which is what makes the pair reachable at all.
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
