"""A battery SDES camera must NEVER skip the TURN relay pre-allocation.

A battery camera sleeps and is woken through the cloud, so its media returns
over the TURN relay, not a host-direct LAN path. Skipping the relay (the
LAN-direct latency optimization) leaves the camera with no reachable path back
and it sends no media at all. Validated live on an A001513: with the relay it
streams h264 1280x960; with skip_turn it serves nothing. The opt/env must not be
able to turn the relay off for a battery camera; the optimization applies only to
mains SDES cameras that actually have a host candidate on the HA segment.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_resolve_sdes_skip_turn" in v.__dict__)


class _Info:
    def __init__(self, model_id):
        self.model_id = model_id


def _cam(battery, **attrs):
    c = _CAM.__new__(_CAM)
    # is_battery_camera is a property derived from info.model_id.
    c.info = _Info("LK.IPC.A001513" if battery else "LK.IPC.A001064")
    for k, v in attrs.items():
        setattr(c, k, v)
    return c


def test_battery_never_skips_turn_even_with_opt():
    c = _cam(True, _sdes_skip_turn_opt=True)
    assert c._resolve_sdes_skip_turn() is False


def test_battery_never_skips_turn_even_with_env(monkeypatch):
    monkeypatch.setenv("AIDOT_SDES_SKIP_TURN_PREALLOC", "1")
    c = _cam(True)
    assert c._resolve_sdes_skip_turn() is False


def test_battery_never_skips_turn_with_fast_attempt_override():
    c = _cam(True, _fast_attempt_override=True)
    assert c._resolve_sdes_skip_turn() is False


def test_mains_still_honors_the_opt():
    assert _cam(False, _sdes_skip_turn_opt=True)._resolve_sdes_skip_turn() is True
    assert _cam(False, _sdes_skip_turn_opt=False)._resolve_sdes_skip_turn() is False


def test_mains_default_off(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_SKIP_TURN_PREALLOC", raising=False)
    assert _cam(False)._resolve_sdes_skip_turn() is False
