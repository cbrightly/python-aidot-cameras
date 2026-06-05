"""Unit tests for real-time camera alarm parsing (GAP/Phase B) - no camera required.

Validates `_parse_alarm_event` against the wire format reverse-engineered from the APK:
motion/person arrive as a transient `alarmType` field (65=motion, 66=person) inside a
`setDevAttrNotif` MQTT push - NOT a separate event message.

Runs under pytest, or standalone:  python tests/test_alarm_event.py
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.device_client import _parse_alarm_event, _CAMERA_ALARM_TYPES


def test_motion_event_from_attr():
    msg = {"method": "setDevAttrNotif", "srcAddr": "0.dev123", "id": "dev123",
           "payload": {"attr": {"alarmType": 65, "Battery_remaining": 80}}}
    assert _parse_alarm_event(msg) == {"device_id": "dev123", "type": "motion", "alarm_type": 65}


def test_person_event_from_props():
    # Some pushes surface attrs under "props" (a.java copies attr->props).
    msg = {"method": "setDevAttrNotif", "id": "cam9",
           "payload": {"props": {"alarmType": "66"}}}  # string code too
    assert _parse_alarm_event(msg) == {"device_id": "cam9", "type": "person", "alarm_type": 66}


def test_device_id_from_srcaddr_when_id_missing():
    msg = {"method": "setDevAttrNotif", "srcAddr": "0.abc",
           "payload": {"attr": {"alarmType": 65}}}
    assert _parse_alarm_event(msg)["device_id"] == "abc"


def test_non_alarm_setdevattrnotif_is_ignored():
    # Ordinary attribute push (no alarmType) must not produce an event.
    msg = {"method": "setDevAttrNotif", "id": "d",
           "payload": {"attr": {"micEnable": 1, "MotionDetection_Enable": "1"}}}
    assert _parse_alarm_event(msg) is None


def test_unknown_alarm_code_ignored():
    msg = {"method": "setDevAttrNotif", "id": "d", "payload": {"attr": {"alarmType": 99}}}
    assert _parse_alarm_event(msg) is None


def test_wrong_method_ignored():
    msg = {"method": "setDevAttrResp", "id": "d", "payload": {"attr": {"alarmType": 65}}}
    assert _parse_alarm_event(msg) is None


def test_garbage_inputs_safe():
    for bad in (None, "x", 42, [], {}, {"method": "setDevAttrNotif"},
                {"method": "setDevAttrNotif", "payload": "nope"},
                {"method": "setDevAttrNotif", "payload": {"attr": {"alarmType": "n/a"}}}):
        assert _parse_alarm_event(bad) is None


def test_alarm_type_map():
    assert _CAMERA_ALARM_TYPES == {65: "motion", 66: "person"}


if __name__ == "__main__":
    fns = [v for k, v in sorted(globals().items()) if k.startswith("test_") and callable(v)]
    for fn in fns:
        fn()
        print(f"PASS {fn.__name__}")
    print(f"\nALL {len(fns)} TESTS PASSED")
