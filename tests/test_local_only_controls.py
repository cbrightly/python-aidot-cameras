"""Three camera settings that were reachable but not controllable.

Every one was toggled on a live A000088 over pure LAN and read back, because on
this firmware an ack does NOT mean the write took. Measured across the whole
unexposed set: StreamType and spkNSLevel acked every value and kept their own;
VideoAngle accepted 7, which no rotation setting should; and
SdcardRecord_Enable - which looked like the most useful control of the lot, and
was in an earlier draft of this file - acked and refused to change. Only
attributes with a demonstrated read-back are here.

Setting goes through async_set_device_attribute, which prefers the LAN client
when one is attached and falls back to cloud MQTT otherwise, so these work
either way.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.camera.lan_control import ATTR_KEYS
from aidot_cameras.camera.models import CameraStatusData

_CASES = [
    ("osd_timestamp", "OSDEnable", "async_set_osd_timestamp"),
    ("auto_light", "autoLightEnable", "async_set_auto_light"),
    ("voice_prompts", "voiceEnable", "async_set_voice_prompts"),
    ("hdr", "HDRStatus", "async_set_hdr"),
]


@pytest.mark.parametrize("field,wire,_setter", _CASES)
def test_the_attribute_is_parsed_into_status(field, wire, _setter):
    s = CameraStatusData()
    assert getattr(s, field) is None, f"{field} should start unknown"
    s.update({wire: 1})
    assert getattr(s, field) is True
    s.update({wire: 0})
    assert getattr(s, field) is False


@pytest.mark.parametrize("field,wire,_setter", _CASES)
def test_a_partial_push_does_not_clear_it(field, wire, _setter):
    """Attribute pushes are partial - a motion notif carries no settings keys."""
    s = CameraStatusData()
    s.update({wire: 1})
    s.update({"Occupancy": 1})
    assert getattr(s, field) is True, (
        f"{field} was cleared by an unrelated push"
    )


@pytest.mark.parametrize("friendly,wire,_setter", _CASES)
def test_the_friendly_name_maps_to_the_wire_key(friendly, wire, _setter):
    """ATTR_KEYS is what CameraLanClient.async_set accepts."""
    assert ATTR_KEYS.get(friendly) == wire


@pytest.mark.parametrize("_field,wire,setter", _CASES)
async def test_the_setter_writes_the_right_wire_value(_field, wire, setter):
    from aidot_cameras.camera.controls import _CameraControlsMixin

    seen = {}

    class _C(_CameraControlsMixin):
        async def async_set_device_attribute(self, attr, value, **kw):
            seen["attr"], seen["value"] = attr, value
            return True

    c = _C()
    assert await getattr(c, setter)(True) is True
    assert seen == {"attr": wire, "value": 1}
    await getattr(c, setter)(False)
    assert seen == {"attr": wire, "value": 0}
