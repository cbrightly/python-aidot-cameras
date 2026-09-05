"""Ending the media wait early must not cost the session its audio.

The stale-offer backstop ends the first-media wait once the camera has turned up
and stayed silent. What happens next is that the serve SDP is built from the
payload types actually *observed*, and the serve ffmpeg is launched with it - so
if the wait ends while the first packets are still in flight, neither type has
been seen and the session serves video only, for its whole life.

Measured 2026-09-03, trial 1 of the validation series:

    19:35:42.2  first media never arrived (39 s)   <- backstop ends the wait
    19:35:43.2  no video observed ... narrowing to the PINNED payload type 96
    19:35:43.2  no audio observed ... Continuing without audio
    19:35:43.2  serve ffmpeg launched, video only
    19:35:47.4  video profile pt=96                <- media arrives 4.2 s later

The card played, at 55.5 s, and had no sound. The 1 s `_AUDIO_PT_GRACE_S` that
follows the wait is for a session whose video is already flowing; it is far too
short for one whose media has not started at all.

So when the backstop is what ended the wait, and nothing has been observed yet,
give the first packets a bounded extra grace before building the SDP. That case
is exactly the one where media is plausibly imminent: the backstop only fires
after the camera itself has been heard from. On the plain timeout path the
camera never spoke at all, media is not imminent, and no grace is given.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (
    _abandoned_media_grace_s,
    _post_abandon_media_grace_s,
)

GRACE = 8.0


def _g(**kw):
    args = dict(abandoned=True, have_video=False, grace_s=GRACE)
    args.update(kw)
    return _post_abandon_media_grace_s(**args)


def test_the_abandoned_wait_gets_a_grace_for_the_first_packets():
    """The measured case: backstop fired, nothing observed, media 4.2 s out."""
    assert _g() == GRACE


def test_a_session_already_carrying_video_needs_no_grace():
    """Its payload types are known; the existing audio grace covers the rest."""
    assert _g(have_video=True) == 0.0


def test_the_plain_timeout_path_gets_nothing():
    """There the camera never spoke at all, so media is not imminent and waiting
    longer would only add to a failure that has already taken 75 s."""
    assert _g(abandoned=False) == 0.0


def test_it_can_be_switched_off():
    assert _g(grace_s=0.0) == 0.0
    assert _g(grace_s=-1.0) == 0.0


def test_the_shipped_grace_covers_the_measured_gap_with_room(monkeypatch):
    """Media arrived 4.2 s after the wait ended. The grace has to clear that
    comfortably, and stay small enough that a session which never delivers pays
    only a little more than it already does."""
    # Read through the accessor with the env cleared: the value comes from
    # AIDOT_ABANDONED_MEDIA_GRACE_S, so reading it raw makes the suite fail on
    # any machine where an operator set it - including the documented =0 escape
    # hatch. The test is about the shipped default, so pin the shipped default.
    monkeypatch.delenv("AIDOT_ABANDONED_MEDIA_GRACE_S", raising=False)
    assert 6.0 <= _abandoned_media_grace_s() <= 15.0


def test_the_grace_still_leaves_the_backstop_worth_having(monkeypatch):
    """The whole point of ending the wait early is to reach the serve sooner
    than the full window. Grace plus the stale-offer grace must stay well inside
    it, or the backstop has given back what it saved."""
    from aidot_cameras.camera.sdes_open import (
        _BATTERY_STALE_OFFER_GRACE_S,
        _FIRST_MEDIA_WAIT_S,
    )

    monkeypatch.delenv("AIDOT_ABANDONED_MEDIA_GRACE_S", raising=False)
    monkeypatch.delenv("AIDOT_BATTERY_STALE_OFFER_GRACE_S", raising=False)
    assert (_BATTERY_STALE_OFFER_GRACE_S + _abandoned_media_grace_s()
            < _FIRST_MEDIA_WAIT_S / 2)
