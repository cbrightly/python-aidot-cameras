"""``batteryMode=2`` alone must not make a mains camera look like a battery one.

Every property payload below is the REAL one from the live cloud account
(2026-08-02), not a construction. That matters: the bug was believing a field
meant what its name suggests, and only the actual payload disproves it.

    A001513 (real battery): batteryMode=2, Battery_remaining, lowPowerStatus, charging
    A000088 (MAINS):        batteryMode=2   <- and nothing else
    A001064 (mains PTZ):    {}

Misclassification is costly in BOTH directions, which is why the corroboration
rule is "flag AND telemetry" rather than dropping the flag or trusting it:

* battery read as mains loses the SDES TURN pre-allocation (a cloud-woken
  battery camera has no LAN host candidate, so the relay is its ONLY return
  path), the keep-alive renew and the HTTP wake;
* mains read as battery puts powerType=2 on its wire payload and hands it the
  battery idle window instead of the mains warm-hold - which is what shipped.
"""

import types

import pytest

from aidot_cameras.camera.client import CameraMixin

# --- real payloads, verbatim from the cloud device list --------------------- #
L2_BATTERY = {"batteryMode": "2", "lowPowerStatus": "0",
              "Battery_remaining": "100", "charging": "0"}
M3_PRO_MAINS = {"batteryMode": "2"}
PTZ_MAINS: dict = {}


class _Cam:
    """The real classification code on a minimal object.

    The methods and properties are taken straight off CameraMixin rather than
    reimplemented, so these tests exercise shipping logic; only the three
    attributes it reads are stubbed.
    """

    _BATTERY_MODELS = CameraMixin._BATTERY_MODELS
    _battery_evidence = CameraMixin._battery_evidence
    is_battery_camera = CameraMixin.is_battery_camera
    live_power_type = CameraMixin.live_power_type

    def __init__(self, props, model="LK.IPC.A000088"):
        self._raw_device = {"properties": props}
        self.info = types.SimpleNamespace(model_id=model)
        self.status = None


def _cam(props, model="LK.IPC.A000088"):
    return _Cam(props, model)


# --------------------------------------------------------------------------- #
# the regression
# --------------------------------------------------------------------------- #

@pytest.mark.parametrize("name", ["M3 Pro_180", "Family Room Cam",
                                  "Bedroom M3 Pro", "Deck"])
def test_mains_a000088_reporting_batterymode_is_not_a_battery_camera(name):
    """The shipped bug: four mains cameras classified as battery.

    They report `batteryMode: '2'` and no other battery field. Trusting the flag
    alone gave them powerType=2 and the battery idle window.
    """
    cam = _cam(M3_PRO_MAINS, model="LK.IPC.A000088")
    assert cam._battery_evidence() is None, name
    assert cam.is_battery_camera is False, name


def test_mains_ptz_with_no_battery_fields_stays_mains():
    cam = _cam(PTZ_MAINS, model="LK.IPC.A001064")
    assert cam.is_battery_camera is False


# --------------------------------------------------------------------------- #
# the thing that must NOT regress
# --------------------------------------------------------------------------- #

def test_a_real_battery_camera_is_still_detected():
    """A001513 must stay battery - losing this costs it the TURN pre-alloc."""
    cam = _cam(L2_BATTERY, model="LK.IPC.A001513")
    assert cam._battery_evidence() is True
    assert cam.is_battery_camera is True


def test_battery_detection_does_not_lean_on_the_model_list_alone():
    """Evidence must carry it, so an unlisted battery revision still works."""
    cam = _cam(L2_BATTERY, model="LK.IPC.A999999-2")
    assert cam.is_battery_camera is True


def test_a_listed_model_with_no_properties_is_still_battery():
    """The model list is the backstop when the payload carries nothing."""
    cam = _cam({}, model="LK.IPC.A001513")
    assert cam._battery_evidence() is None
    assert cam.is_battery_camera is True


# --------------------------------------------------------------------------- #
# the corroboration rule itself
# --------------------------------------------------------------------------- #

@pytest.mark.parametrize("corroborating", [
    {"Battery_remaining": "55"},
    {"batteryRemaining": "55"},
    {"batteryLevel": "55"},
    {"lowPowerStatus": "0"},
    {"charging": "1"},
])
def test_batterymode_counts_when_any_battery_field_corroborates(corroborating):
    """The flag is kept as a signal, not discarded - it just needs backing.

    This is what catches a future battery model whose level field we have not
    seen, provided it reports any battery-only field at all.
    """
    cam = _cam({"batteryMode": "2", **corroborating}, model="LK.IPC.A000088")
    assert cam._battery_evidence() is True


def test_a_zero_valued_battery_field_still_corroborates():
    """`charging: '0'` is a report, not an absence - presence is the signal."""
    cam = _cam({"batteryMode": "2", "charging": "0"}, model="LK.IPC.A000088")
    assert cam._battery_evidence() is True


def test_evidence_never_returns_false_so_it_cannot_demote_a_known_model():
    """It may only ADD to the battery set, never remove - preserved property."""
    for props in (M3_PRO_MAINS, PTZ_MAINS, {"batteryMode": "1"}):
        assert _cam(props)._battery_evidence() in (True, None)


def test_power_type_follows_the_classification():
    """powerType on the wire must never disagree with the battery guards."""
    assert _cam(M3_PRO_MAINS, model="LK.IPC.A000088").live_power_type == 1
    assert _cam(L2_BATTERY, model="LK.IPC.A001513").live_power_type == 2
