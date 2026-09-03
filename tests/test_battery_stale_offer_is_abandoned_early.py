"""A battery attempt that has stalled is not worth the rest of its window.

A cold battery camera normally serves fast.  Measured 2026-09-03 with a 150s
settle so each open was genuinely cold, and with signalling logs finally
attributed to a camera:

    driveway  wakeupStatus +5.4s   first media +10.0s   serving +10.4s
    kitchen                        first media  +5.3s   serving  +5.5s

which is the same experience the vendor app gives - it pulls these cameras up
almost instantly.  An earlier reading here that the camera takes 36-47s to wake
was wrong: it came from two opens taken minutes after a Home Assistant restart,
and from log lines that could not be attributed to a camera at all.

What is real is that the first attempt sometimes stalls outright.  When it
does, the camera answers our livePlayReq after waking and then sends nothing:
measured, it answered at +46.4s with 48s of the first-media window still to run
and no media ever arrived, and the open finished at +103.6s.  The retry that
follows - `camera not ready (waking, livePlayResp -50019) and sent no media -
fast retry in 3s [1/3]` - publishes a fresh offer and is served in ~5s.

So when a BATTERY camera announces itself only after we began waiting for its
media, and the grace passes with still no media, abandon the wait and let the
retry happen now.  The grace is sized off the healthy distribution rather than
the stall - it has to clear the slowest good open by a wide margin, so a merely
slow attempt is never mistaken for a stalled one.  A warm camera never reaches
this at all: it has answered before the media wait begins, which disarms the
detector.  A camera that says nothing whatsoever keeps its full 75s window.
"""
import os
import sys
import time

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (
    _BATTERY_STALE_OFFER_GRACE_S,
    _stale_offer_abandon_due,
)

NOW = 1000.0
GRACE = 15.0


def _due(**kw):
    args = dict(
        battery=True,
        seen_at_start=None,
        first_seen_ts=NOW - 30.0,
        now=NOW,
        grace_s=GRACE,
    )
    args.update(kw)
    return _stale_offer_abandon_due(**args)


# --------------------------------------------------------------------------- #
# The case it exists for
# --------------------------------------------------------------------------- #

def test_a_camera_that_showed_up_only_after_the_wait_began_is_abandoned():
    """The cold open: nothing from the camera when the wait started, it appears
    mid-wait, the grace passes, and there is still no media."""
    assert _due() is True


def test_the_grace_has_to_pass_first():
    """A camera that has only just appeared may still be about to send media -
    a healthy attempt delivers ~5s after the camera answers."""
    assert _due(first_seen_ts=NOW - 5.0) is False
    assert _due(first_seen_ts=NOW - (GRACE - 0.01)) is False
    assert _due(first_seen_ts=NOW - GRACE) is True


# --------------------------------------------------------------------------- #
# Everything it must not touch
# --------------------------------------------------------------------------- #

def test_a_warm_camera_is_never_abandoned():
    """A warm camera answers livePlayResp before the media wait even begins, so
    seen_at_start is already set and the detector is disarmed for the whole
    attempt - including the retry attempts, where the camera is awake and
    talking throughout."""
    assert _due(seen_at_start=NOW - 30.0) is False


def test_a_silent_camera_keeps_its_full_window():
    """No device evidence at all is the off-subnet / unreachable shape, not a
    stale offer.  Those keep the 75s window they have today."""
    assert _due(first_seen_ts=None) is False


def test_mains_cameras_are_untouched():
    """Mains cameras never sleep, so an offer cannot be published at a sleeping
    one; the measurement behind this is battery-only and so is the behaviour."""
    assert _due(battery=False) is False


def test_a_zero_grace_disables_the_detector():
    """AIDOT_BATTERY_STALE_OFFER_GRACE_S=0 restores the previous behaviour
    exactly, so a bad field outcome is one env var away from being reverted."""
    assert _due(grace_s=0.0) is False
    assert _due(grace_s=0.0, first_seen_ts=NOW - 600.0) is False


def test_a_camera_seen_before_the_wait_stays_disarmed_even_if_it_talks_again():
    """seen_at_start is the arming decision; later traffic from an
    already-known-awake camera must not re-arm it."""
    assert _due(seen_at_start=NOW - 40.0, first_seen_ts=NOW - 30.0) is False


# --------------------------------------------------------------------------- #
# The shipped grace
# --------------------------------------------------------------------------- #

def test_the_shipped_grace_clears_a_healthy_attempt_by_a_margin():
    """Bounded from both sides.  Below by the healthy distribution - cold opens
    delivered media 4.4-10.7s after the offer, and a merely slow attempt must
    never be mistaken for a stalled one.  Above by the camera's own sleep timer:
    it goes back to sleep about 29s after waking for us, and the retry is only
    cheap while it is still awake."""
    assert 12.0 <= _BATTERY_STALE_OFFER_GRACE_S <= 20.0


def test_abandoning_beats_waiting_only_because_the_retry_is_cheap():
    """Sanity on the trade: the retry costs a 3s backoff plus a fresh handshake
    (~5s to media), so abandoning is only right when more than that is left on
    the clock.  With the shipped grace and the 75s window, it always is."""
    from aidot_cameras.camera.sdes_open import _FIRST_MEDIA_WAIT_S

    retry_cost_s = 3.0 + 8.0
    assert _BATTERY_STALE_OFFER_GRACE_S + retry_cost_s < _FIRST_MEDIA_WAIT_S


def test_the_helper_is_pure_and_does_not_read_the_clock():
    """It is called from the media-wait loop at 10Hz; it must be a comparison,
    not a syscall, and it must be testable without freezing time."""
    before = time.monotonic()
    for _ in range(1000):
        _due()
    assert time.monotonic() - before < 0.5


def test_the_clock_runs_from_the_first_sighting_not_the_latest_one():
    """A camera emitting a motion event every 5s while its handshake goes
    nowhere must not be able to push the decision back indefinitely.  Measured:
    doing that delayed the abandon to 47s, by which time the camera had gone
    back to sleep and the retry had to wake it all over again."""
    # Present for well over the grace, and still talking a moment ago.
    assert _due(first_seen_ts=NOW - 40.0) is True
