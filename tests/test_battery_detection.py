"""Battery cameras must be recognized from their own cloud data, not only a list.

Every battery protection hangs off ``is_battery_camera``: the SDES TURN relay
pre-allocation is force-KEPT (a camera woken through the cloud has no LAN host
candidate, so the relay is its only return path), the cloud keep-alive is renewed
mid-view, the HTTP wake fires, adaptive mode is refused, and the camera is told
``powerType=2``. A battery camera that isn't recognized as one loses all of that
at once, and the failure is asymmetric by consumer: a standalone run leaves the
LAN-direct options at their relay-keeping defaults and streams fine, while Home
Assistant - where those options are actually set - gets a session that negotiates
and never delivers a frame. So detection must not depend on this library having
already seen the exact model id.

Also locked here: the model-id matches that used to be re-typed inline per call
site (powerType) or compared for exact equality (the plain-RTP framing set), both
of which broke on a revision suffix.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_battery_evidence" in v.__dict__)


class _Info:
    def __init__(self, model_id):
        self.model_id = model_id


class _Status:
    def __init__(self, battery_remaining=None):
        self.battery_remaining = battery_remaining


def _cam(model_id="LK.IPC.A000088", props=None, status=None, **attrs):
    c = _CAM.__new__(_CAM)
    c.info = _Info(model_id)
    c.device_id = "DEV1234"
    c._raw_device = {"properties": props} if props is not None else {}
    if status is not None:
        c.status = status
    for k, v in attrs.items():
        setattr(c, k, v)
    return c


# --- the known-model list ---------------------------------------------------- #

def test_listed_battery_models_are_battery():
    for _m in ("LK.IPC.A001513", "LK.IPC.A001108", "LK.IPC.A001360"):
        assert _cam(_m).is_battery_camera is True, _m


def test_a_revision_suffix_is_still_the_same_battery_model():
    # A000088-1 exists on the DTLS side, so revisions are a real naming pattern;
    # the battery match is substring-based for exactly that reason.
    assert _cam("LK.IPC.A001513-1").is_battery_camera is True


def test_mains_models_are_not_battery():
    for _m in ("LK.IPC.A000088", "LK.IPC.A000088-1", "LK.IPC.A001064"):
        assert _cam(_m).is_battery_camera is False, _m


def test_unknown_model_with_no_evidence_is_not_battery():
    # No signal either way: stay mains rather than silently disabling the
    # LAN-direct optimizations for a wired camera.
    assert _cam("LK.IPC.A009999").is_battery_camera is False


# --- evidence from the camera's own cloud data -------------------------------- #

def test_reported_battery_level_makes_an_unlisted_model_battery():
    # The rule lan_control.is_mains_powered inverts: a camera that reports a
    # battery level has a battery.
    c = _cam("LK.IPC.A009999", props={"Battery_remaining": 74})
    assert c.is_battery_camera is True


def test_zero_battery_level_still_counts():
    # A flat battery is still a battery - the field's PRESENCE is the signal.
    assert _cam("LK.IPC.A009999", props={"Battery_remaining": 0}).is_battery_camera


def test_numeric_string_battery_level_counts():
    # Cloud attrs arrive as strings routinely.
    assert _cam("LK.IPC.A009999", props={"Battery_remaining": "38"}).is_battery_camera


def test_battery_mode_two_counts():
    assert _cam("LK.IPC.A009999", props={"batteryMode": 2}).is_battery_camera is True
    assert _cam("LK.IPC.A009999", props={"batteryMode": "2"}).is_battery_camera is True


def test_battery_mode_one_is_not_evidence():
    assert _cam("LK.IPC.A009999", props={"batteryMode": 1}).is_battery_camera is False


def test_power_type_property_is_deliberately_not_evidence():
    # powerType is what we SEND in livePlayReq, has never been observed coming
    # back as a device property, and its neighbour p2pCache reads 2 on every
    # camera including mains ones. Treating it as evidence would risk classifying
    # a mains camera as battery and putting powerType=2 on ITS payload, which can
    # leave an A000088 un-armed.
    assert _cam("LK.IPC.A000088", props={"powerType": 2}).is_battery_camera is False


def test_parsed_status_battery_level_counts():
    # A camera refreshed from an attribute push rather than a device-list dict.
    c = _cam("LK.IPC.A009999", status=_Status(battery_remaining=12))
    assert c.is_battery_camera is True


def test_non_numeric_battery_value_is_not_evidence():
    # A malformed cloud value must not classify the camera either way.
    assert _cam("LK.IPC.A009999", props={"Battery_remaining": "n/a"}).is_battery_camera is False


def test_evidence_never_takes_a_listed_model_out():
    # Absence of evidence falls through to the list; it can only ever ADD.
    assert _cam("LK.IPC.A001513", props={}).is_battery_camera is True
    assert _cam("LK.IPC.A001513", props={"powerType": 1}).is_battery_camera is True


def test_detection_survives_missing_attributes():
    # Read from hot paths (every guard, every open) - must never raise.
    c = _CAM.__new__(_CAM)
    assert c.is_battery_camera is False


def test_detection_survives_a_malformed_raw_device():
    for _bad in (None, [], "nope", {"properties": "nope"}, {"properties": None}):
        c = _CAM.__new__(_CAM)
        c.info = _Info("LK.IPC.A009999")
        c._raw_device = _bad
        assert c.is_battery_camera is False, _bad


# --- powerType on the wire --------------------------------------------------- #

def test_power_type_follows_battery_detection():
    # IpcServiceImpl.java B(): 2 for battery, 1 for wired. One derivation, so the
    # value the camera is told can't disagree with the guards we apply.
    assert _cam("LK.IPC.A001513").live_power_type == 2
    assert _cam("LK.IPC.A000088").live_power_type == 1
    assert _cam("LK.IPC.A009999", props={"Battery_remaining": 50}).live_power_type == 2
    assert _cam("LK.IPC.A009999").live_power_type == 1


# --- the plain-RTP (TUTK-framed) model set ----------------------------------- #

def test_plain_rtp_models_match_by_substring():
    # Was an equality check against the bare ids, so a revision suffix read as a
    # standard-SRTP camera: ffmpeg then tried to decrypt TUTK frames with the
    # announced fake key and the bridge never stripped the TUTK header - a
    # session that negotiates and delivers nothing decodable.
    _models = _CAM._PLAIN_RTP_MODELS
    for _m in ("LK.IPC.A001064", "LK.IPC.A001513", "LK.IPC.A001513-1"):
        assert any(k in _m for k in _models), _m
    for _m in ("LK.IPC.A000088", "LK.IPC.A001108"):
        assert not any(k in _m for k in _models), _m


# --- adaptive mode is refused for battery cameras ---------------------------- #

def test_adaptive_never_on_for_a_battery_camera(monkeypatch):
    # Adaptive chases the TURN pre-allocation saving, which is force-kept for a
    # battery camera - so the "fast" attempt runs the same handshake and differs
    # only in getting 45 s to open and a 40 s media grace, inside the documented
    # 25-70 s battery cold-start window. A slow-but-healthy wake would then be
    # scored as a fast-path failure.
    monkeypatch.setenv("AIDOT_SDES_ADAPTIVE", "1")
    assert _cam("LK.IPC.A001513")._resolve_sdes_adaptive() is False
    assert _cam("LK.IPC.A001513", _sdes_adaptive_opt=True)._resolve_sdes_adaptive() is False
    # Detected-by-evidence battery cameras get the same guard.
    c = _cam("LK.IPC.A009999", props={"Battery_remaining": 60}, _sdes_adaptive_opt=True)
    assert c._resolve_sdes_adaptive() is False


def test_adaptive_still_available_for_mains(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_ADAPTIVE", raising=False)
    assert _cam("LK.IPC.A001064", _sdes_adaptive_opt=True)._resolve_sdes_adaptive() is True
    assert _cam("LK.IPC.A001064")._resolve_sdes_adaptive() is False
    monkeypatch.setenv("AIDOT_SDES_ADAPTIVE", "1")
    assert _cam("LK.IPC.A001064")._resolve_sdes_adaptive() is True


# --- the guards an evidence-detected battery camera now gets ----------------- #

def test_evidence_detected_battery_keeps_the_turn_relay(monkeypatch):
    # The headline consequence: HA's LAN-direct mode can no longer strip the only
    # return path to a battery camera it hadn't been taught to recognize.
    monkeypatch.setenv("AIDOT_SDES_SKIP_TURN_PREALLOC", "1")
    c = _cam("LK.IPC.A009999", props={"Battery_remaining": 41},
             _sdes_skip_turn_opt=True)
    assert c._resolve_sdes_skip_turn() is False


def test_evidence_detected_battery_closes_the_livestreamparam_gate(monkeypatch):
    monkeypatch.setenv("AIDOT_LIVESTREAM_PARAM", "1")
    c = _cam("LK.IPC.A009999", props={"batteryMode": 2})
    assert c._resolve_live_stream_param() is False
