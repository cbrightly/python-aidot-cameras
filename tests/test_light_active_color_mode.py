"""Tests for DeviceStatusData.active_color_mode tracking.

AiDot RGBW+CCT bulbs report state as deltas: a CCT-mode push carries only
CCT (RGBW is None); an RGB-mode push carries only RGBW (CCT is None). But
the getDevAttr login-sync returns BOTH the retained RGBW register and CCT
together, which is ambiguous about which mode is actually active. This
must update the retained values but must NOT change active_color_mode.
"""

from aidot.device_client import DeviceStatusData
from aidot.models.device_client_model import DeviceAttr


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
