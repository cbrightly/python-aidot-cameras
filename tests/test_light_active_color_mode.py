"""Tests for the carried active_color_mode fix (python-aidot PR #6).

AiDot RGBW+CCT bulbs report state as deltas: a CCT-mode push carries only
CCT (RGBW is None); an RGB-mode push carries only RGBW (CCT is None). But
the getDevAttr login-sync returns BOTH the retained RGBW register and CCT
together, which is ambiguous about which mode is actually active. This
must update the retained values but must NOT change active_color_mode.

The fix is NOT in upstream yet, so the class under test must be OUR
`aidot_cameras.device_client.DeviceStatusData`: upstream's same-named class has
no `active_color_mode` at all, and importing it here would turn every assertion
below into an AttributeError (or, worse, quietly test the wrong class if the
attribute ever appeared for another reason). `DeviceAttr` stays upstream's -
it is the input the fix consumes, not the code under test.

The second half of this file covers the delivery path: a bulb never goes
through CameraDeviceClient, so `CameraClient.get_device_client` has to attach
the carried status to plain upstream clients - and only to the RGBW+CCT ones.
"""

import asyncio

from aidot.device_client import DeviceClient as UpstreamDeviceClient
from aidot.device_client import DeviceStatusData as UpstreamDeviceStatusData
from aidot.models.device_client_model import DeviceAttr
from upstream_shapes import (
    make_upstream_device_client,
)

import aidot_cameras.client as client_mod
from aidot_cameras.client import CameraClient
from aidot_cameras.device_client import CameraDeviceClient, DeviceStatusData


def test_cct_only_delta_sets_cct_mode():
    status = DeviceStatusData()
    status.update(DeviceAttr(OnOff=1, Dimming=100, CCT=3000))
    assert status.active_color_mode == "cct"
    assert status.cct == 3000


def test_rgbw_only_delta_sets_rgbw_mode():
    status = DeviceStatusData()
    status.update(DeviceAttr(RGBW=0x00FF0000))
    assert status.active_color_mode == "rgbw"
    assert status.rgbw == (0, 255, 0, 0)


def test_ambiguous_sync_does_not_change_mode_from_unknown():
    # Starting from a fresh (unknown/None) mode, an ambiguous sync carrying
    # both RGBW and CCT must update the values but leave the mode None.
    status = DeviceStatusData()
    assert status.active_color_mode is None
    status.update(DeviceAttr(OnOff=1, Dimming=100, RGBW=-1660909312, CCT=3000))
    assert status.active_color_mode is None
    assert status.rgbw == (157, 0, 137, 0)
    assert status.cct == 3000


def test_ambiguous_sync_does_not_revert_established_mode():
    status = DeviceStatusData()
    # Unambiguous CCT-only delta establishes "cct".
    status.update(DeviceAttr(OnOff=1, Dimming=100, CCT=3000))
    assert status.active_color_mode == "cct"
    # A subsequent ambiguous sync (both RGBW and CCT present) must NOT flip
    # the mode back to "rgbw", even though RGBW is present in the payload.
    status.update(DeviceAttr(OnOff=1, Dimming=100, RGBW=-1660909312, CCT=3000))
    assert status.active_color_mode == "cct"
    assert status.rgbw == (157, 0, 137, 0)
    assert status.cct == 3000


def test_rgbw_zero_only_does_not_set_rgbw_mode():
    status = DeviceStatusData()
    status.update(DeviceAttr(RGBW=0))
    assert status.active_color_mode is None
    # Existing default-red value behavior must be unchanged.
    assert status.rgdb == 0xFF000000
    assert status.rgbw == (255, 0, 0, 0)


# --------------------------------------------------------------------------- #
# delivery: CameraClient.get_device_client attaches the carried status
# --------------------------------------------------------------------------- #

def _service(identity):
    return {"identity": identity, "properties": [{"minValue": "2700", "maxValue": "6500"}]}


def _device(dev_id, model_id, services):
    return {
        "id": dev_id,
        "name": dev_id,
        "modelId": model_id,
        "aesKey": ["k" * 16],
        "password": "pw",
        "product": {"serviceModules": services},
    }


RGBW_BULB = _device("bulb-rgbw", "lk.WIFI-RGBWLight-D0006",
                    [_service("control.light.rgbw"), _service("control.light.cct")])
CCT_BULB = _device("bulb-cct", "lk.WIFI-CCTLight-D0001", [_service("control.light.cct")])
CAMERA = _device("cam1", "LK.IPC.A000088", [])


def _dispatch(device):
    """Run get_device_client for `device` on a network-free client.

    A loop is required because upstream's get_device_client ends in
    update_ip_address, which spawns the login task; discovery stays off because
    setup_discover returns early while the account has no user id.
    """
    async def _run():
        client = CameraClient(None, country_code="US")
        return client, client.get_device_client(device)

    return asyncio.run(_run())


def test_rgbw_bulb_gets_the_carried_status_on_a_pure_upstream_client():
    _client, dc = _dispatch(RGBW_BULB)
    # The device itself must stay 100% upstream - no camera code in a bulb's path.
    assert type(dc) is UpstreamDeviceClient
    assert not isinstance(dc, CameraDeviceClient)
    # ...but its status carries the fix.
    assert isinstance(dc.status, DeviceStatusData)
    dc.status.update(DeviceAttr(OnOff=1, Dimming=100, CCT=3000))
    assert dc.status.active_color_mode == "cct"


def test_carrying_preserves_state_upstream_already_seeded():
    """The swap copies the old status object's state onto the new one.

    Upstream seeds status during login (status.online) before anything else
    holds a reference, so the carry must not start from a blank object.
    """
    client = CameraClient(None, country_code="US")
    dc = make_upstream_device_client(RGBW_BULB, {"id": "u1"})
    dc.status.online = True
    dc.status.cct = 4200
    client._carry_active_color_mode(dc)
    assert isinstance(dc.status, DeviceStatusData)
    assert dc.status.online is True and dc.status.cct == 4200


def test_carrying_is_idempotent_across_repeat_dispatch():
    async def _run():
        client = CameraClient(None, country_code="US")
        dc = client.get_device_client(RGBW_BULB)
        carried = dc.status
        # get_device_client is called repeatedly by consumers; the second call
        # must not wrap the already-carried status a second time.
        again = client.get_device_client(RGBW_BULB)
        return dc, again, carried

    dc, again, carried = asyncio.run(_run())
    assert again is dc
    assert dc.status is carried


def test_cct_only_bulb_stays_exactly_upstream():
    _client, dc = _dispatch(CCT_BULB)
    assert type(dc) is UpstreamDeviceClient
    # enable_rgbw is False here, so nothing is carried: the status object is
    # upstream's own class, not our subclass.
    assert type(dc.status) is UpstreamDeviceStatusData


def test_camera_gets_the_camera_client(monkeypatch):
    # The ICE prefetch is a background HTTP warm-up; stub it out so dispatch
    # stays offline.
    async def _no_prefetch(_dc):
        return None

    monkeypatch.setattr(client_mod, "_prefetch_ice_config", _no_prefetch)
    _client, dc = _dispatch(CAMERA)
    assert isinstance(dc, CameraDeviceClient)
