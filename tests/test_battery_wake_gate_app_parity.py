"""Offer to a battery camera once it has answered, the way the app does.

The official client's live view does three things on mount: it fires
`keepAliveHandle()`, it sends the wake, and it renders `IPC.Status.Sleep` -
a spinner, no session - until the device's own `lowPowerActiveState` reads
`"wakeup"`.  Only then does it open a stream.  We did the first two and skipped
the third: `sdes_fast_liveplay` sends `webrtcReq` without waiting for the
camera to answer at all.

That is fine on a camera that is already awake, and it is the whole failure on
one that is not.  Measured 2026-09-03, the camera's own first message landed
anywhere from +0.9 s to +40.7 s after the open, and when it landed late the
offer had long since been published at a sleeping device: the camera answered
it after waking and then sent nothing, the attempt died at the first-media
window, and only the retry's fresh offer was served.

So a battery camera can be made to wait for evidence from the camera itself
before the offer goes out.  **It is shipped off**, because measuring it showed
the live-play signalling is what wakes the camera: at a 20 s budget, on a camera
settled for ten minutes, the gate ran its whole budget with the camera silent,
the offer went out at +20.9 s, the camera's own wakeupStatus arrived at +23.6 s
- after the offer - and first media at +27.0 s, against 5.4-10.0 s with no gate.
Withholding the offer withholds the wake.

The code and the knob stay for anyone re-testing this on other firmware.  When
enabled, the wait:

* releases on ANY message from this camera, not just `livePlayResp` - the
  camera's device-channel traffic says it is listening just as well, and
  binding the gate to one message type would deadlock a camera that answers
  differently;
* is bounded, and on expiry proceeds exactly as today, so the worst case is
  the behaviour we already had;
* costs a warm camera nothing - it answers in 0.2-0.75 s;
* does not exist for mains cameras, which never sleep.  That matters for the
  A001064 PTZ in particular: it is the role-reversal model, its timing is
  delicate, and it is mains, so this cannot touch it.

**No new battery cost.** The gate adds no wake, no poll and no traffic of its
own - it waits on messages that were already arriving, inside an open that had
already woken the camera because somebody asked to watch it.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (
    _BATTERY_WAKE_GATE_S,
    _battery_wake_gate_s,
)


def test_a_battery_camera_gets_the_gate():
    assert _battery_wake_gate_s(battery=True, budget=20.0) == 20.0


def test_a_mains_camera_never_waits():
    """Mains cameras never sleep, so there is nothing to wait for - and the PTZ
    is mains, which keeps this change away from the role-reversal path."""
    assert _battery_wake_gate_s(battery=False, budget=20.0) == 0.0


def test_the_gate_can_be_switched_off_entirely():
    """AIDOT_BATTERY_WAKE_GATE_S=0 restores the previous behaviour exactly."""
    assert _battery_wake_gate_s(battery=True, budget=0.0) == 0.0
    assert _battery_wake_gate_s(battery=True, budget=-1.0) == 0.0


def test_it_ships_off_because_the_offer_is_what_wakes_the_camera():
    """Measured twice and it is self-defeating on this family: the live-play
    signalling is what wakes the camera, so withholding the offer withholds the
    wake. At 20s, on a camera settled ten minutes, the gate ran its full budget
    with the camera silent, the offer went out at +20.9s, the camera's own
    wakeupStatus arrived at +23.6s - after the offer - and first media at
    +27.0s, against 5.4-10.0s on the same camera with no gate."""
    assert _BATTERY_WAKE_GATE_S == 0.0


def test_enabling_it_still_leaves_room_for_everything_after_it():
    """If anyone does re-enable it, the gate plus the backstop that follows must
    still fit inside the first-media wait - otherwise the two waits stack into
    the failure the 30s run already demonstrated."""
    from aidot_cameras.camera.sdes_open import (
        _BATTERY_STALE_OFFER_GRACE_S,
        _FIRST_MEDIA_WAIT_S,
    )

    assert 20.0 + _BATTERY_STALE_OFFER_GRACE_S < _FIRST_MEDIA_WAIT_S
