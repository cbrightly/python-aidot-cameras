"""Unit tests for GAP D: terminal webrtcResp ack detection (no camera required).

Validates `_terminal_webrtc_ack` - the pure function that decides whether a camera
`webrtcResp` is a TERMINAL refusal (-50002 max-streams / -50015 SD-cap) that must NOT
be retried - and the `AidotCameraBusy` exception it drives.

Runs under pytest, or standalone:  python tests/test_terminal_ack.py
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.device_client import _terminal_webrtc_ack, _WEBRTC_TERMINAL_ACK_CODES
from aidot_cameras.exceptions import AidotCameraBusy, AidotError


def test_detects_minus_50002_max_streams():
    msg = {"method": "webrtcResp", "ack": {"code": -50002, "desc": "session exceed"},
           "payload": {"peerid": "p"}}
    assert _terminal_webrtc_ack(msg) == (-50002, "session exceed")


def test_detects_minus_50015_sd_cap():
    msg = {"method": "webrtcResp", "ack": {"code": -50015, "desc": "sd max"}}
    assert _terminal_webrtc_ack(msg) == (-50015, "sd max")


def test_success_ack_is_not_terminal():
    msg = {"method": "webrtcResp", "ack": {"code": 200, "desc": "success"},
           "payload": {"offer": {"type": "answer", "sdp": "v=0..."}}}
    assert _terminal_webrtc_ack(msg) is None


def test_missing_ack_is_not_terminal():
    assert _terminal_webrtc_ack({"method": "webrtcResp", "payload": {}}) is None


def test_terminal_code_on_other_method_is_ignored():
    # Scope is webrtcResp only - a -50002 on a different method must not trip it.
    assert _terminal_webrtc_ack({"method": "livePlayResp", "ack": {"code": -50002}}) is None


def test_non_dict_inputs_are_safe():
    for bad in (None, "x", 42, [], {"ack": "notdict", "method": "webrtcResp"}):
        assert _terminal_webrtc_ack(bad) is None


def test_desc_defaults_to_empty_string():
    assert _terminal_webrtc_ack({"method": "webrtcResp", "ack": {"code": -50002}}) == (-50002, "")


def test_terminal_set_is_exactly_the_two_known_codes():
    assert set(_WEBRTC_TERMINAL_ACK_CODES) == {-50002, -50015}


def test_exception_carries_code_and_is_aidot_error():
    exc = AidotCameraBusy(-50002, "session exceed")
    assert exc.code == -50002
    assert exc.desc == "session exceed"
    assert isinstance(exc, AidotError)
    assert "-50002" in str(exc) and "session exceed" in str(exc)


if __name__ == "__main__":
    fns = [v for k, v in sorted(globals().items()) if k.startswith("test_") and callable(v)]
    passed = 0
    for fn in fns:
        fn()
        print(f"PASS {fn.__name__}")
        passed += 1
    print(f"\nALL {passed} TESTS PASSED")
