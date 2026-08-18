"""The cloud HTTP wake is for cameras that sleep. Ours went to every camera.

Verified in the decompiled client 2026-08-18 (`~/source/AiDot_decompiled`):

    DeviceWakeUpRepos.smali:665   "%s/api/ipc/devices/%s/lowPowerActiveState"
    NewLiveFragment.smali ~21453  invoke-virtual IpcDeviceBean->isLowPowerDevice()Z
                                  if-eqz  ->  jumps PAST the wakeUpRepos call

So the official app never wakes a mains camera over the cloud. We did, on every
open - confirmed live, `lowPowerActiveState HTTP 200` for a mains A000088 that
re-opens every 40-90 s, on a channel whose stated purpose is reaching a camera
that has dropped its MQTT session. A camera that never sleeps cannot need it.

`client.py:async_wake_camera` already had this guard
(`if not self.is_battery_camera: return True`); the copy on the webrtc_open
path did not.

The MQTT wake stays ungated on purpose: it rides an already-open connection and
its cloud ack releases the pre-offer wait, so gating it would cost a mains
camera up to 12 s of cold start.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

SRC = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
                   "aidot_cameras", "camera", "webrtc_open.py")


def _source():
    with open(SRC) as fh:
        return fh.read()


def test_the_http_wake_is_guarded_by_is_battery_camera():
    s = _source()
    i = s.index("_spawn_bg(_http_wake())")
    window = s[max(0, i - 900):i]
    assert "is_battery_camera" in window, (
        "the HTTP wake must be reachable only for a battery camera")


def test_the_mqtt_wake_is_not_guarded_with_it():
    """Deliberate: its cloud ack is what releases the pre-offer wait."""
    s = _source()
    i = s.index("lowPowerActiveStateReq")
    window = s[max(0, i - 400):i]
    assert "is_battery_camera" not in window


def test_a_mains_camera_says_why_it_skipped():
    assert "skipping HTTP wake" in _source()


def test_the_endpoint_still_matches_the_app():
    """DeviceWakeUpRepos.smali:665 - "%s/api/ipc/devices/%s/lowPowerActiveState"."""
    s = _source()
    assert "/lowPowerActiveState" in s
    assert "\"status\": \"wakeup\"" in s or '"status": "wakeup"' in s
