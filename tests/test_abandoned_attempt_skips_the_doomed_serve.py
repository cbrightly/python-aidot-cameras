"""An abandoned attempt should go to the retry, not serve nothing first.

When the stale-offer backstop ends the media wait and no media has arrived even
after the grace, the code used to build a serve SDP from nothing observed,
launch ffmpeg, and wait for that serve to fail before retrying. Measured
2026-09-03 across two slow-path trials, that costs twice:

    21:33:54  backstop ends the wait
    21:34:03  grace expires: "no video observed ... no audio observed"
    21:34:03  serve launched anyway, video only          (+37.4 s)
    21:34:13  session ends: "camera not ready ... fast retry in 3s"
    21:34:17  retry
    21:34:21  retry's first media                         (+4.7 s on its own clock)
              card served at +57.0 s, audio from the RETRY, not from the serve

The doomed serve delivered nothing, delayed the retry by ~14 s, and its
video-only SDP is what dropped the audio in the first place. Skipping it goes
straight to the attempt that works.

**The delay it retries on is the whole risk.** An exception out of the open
lands in the generic handler, which uses the pacer's `fail_delay()` - the
ESCALATING open-failure backoff. Taking that path would be worse than the
behaviour being replaced, because the session-ended path this replaces gets the
fast not-ready retry instead. So the abandoned attempt raises a distinct
exception and is paced like the session-ended case it stands in for: the fast
retry when the camera said -50019, and the ordinary unhealthy-session delay
otherwise. Never the open-failure escalation.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.exceptions import AidotCameraBusy, AidotCameraNoMedia, AidotError
from aidot_cameras.camera.sdes_open import _should_skip_doomed_serve


def _s(**kw):
    args = dict(abandoned=True, have_video=False)
    args.update(kw)
    return _should_skip_doomed_serve(**args)


# --------------------------------------------------------------------------- #
# When to skip
# --------------------------------------------------------------------------- #

def test_an_abandoned_attempt_with_no_media_is_skipped():
    assert _s() is True


def test_an_attempt_that_got_media_is_served():
    """The grace caught it - this is the good outcome and must be untouched."""
    assert _s(have_video=True) is False


def test_the_plain_timeout_path_is_now_skipped_too():
    """SUPERSEDED 2026-09-05, and kept rather than deleted because the reason
    matters.

    This used to assert that a camera which never spoke keeps the old
    behaviour - serve launched, HA's stream worker retries into it - on the
    grounds that it was a transient worth tolerating. A camera that dropped off
    the WiFi showed it is not: 11 attempts, 6 stalls, every one launching an
    ffmpeg that died on "dimensions not set" because no media ever arrived.
    A permanent failure was being looped on, not a transient tolerated.

    A serve with observed video is still served, whichever wait ended."""
    assert _s(abandoned=False) is True
    assert _s(abandoned=False, have_video=True) is False


# --------------------------------------------------------------------------- #
# The exception it raises
# --------------------------------------------------------------------------- #

def test_the_exception_is_distinct_from_a_terminal_refusal():
    """AidotCameraBusy means 'stop retrying'. This one means the opposite -
    retry now - so the loop must be able to tell them apart."""
    assert not issubclass(AidotCameraNoMedia, AidotCameraBusy)
    assert issubclass(AidotCameraNoMedia, AidotError)


def test_the_exception_carries_how_long_was_waited():
    e = AidotCameraNoMedia(waited_s=37.4)
    assert e.waited_s == 37.4
    assert "37" in str(e)


def test_it_is_catchable_as_a_plain_exception_too():
    """It must not escape any existing `except Exception` that guards a caller
    which has not been taught about it."""
    with pytest.raises(Exception):
        raise AidotCameraNoMedia(waited_s=1.0)


# --------------------------------------------------------------------------- #
# How the loop paces the retry - the part that could make this worse
# --------------------------------------------------------------------------- #

def _loop_source() -> str:
    import inspect

    from aidot_cameras.camera.client import CameraMixin

    return inspect.getsource(CameraMixin._sdes_keepalive_loop_inner)


def test_the_no_media_branch_is_handled_before_the_generic_one():
    """Order matters: `except Exception` would swallow AidotCameraNoMedia and
    pace it with the open-failure backoff, which is the regression this whole
    change exists to avoid."""
    src = _loop_source()
    assert "except AidotCameraNoMedia" in src
    assert src.index("except AidotCameraNoMedia") < src.index("except Exception")


def _no_media_branch() -> str:
    """The handler body, sliced at the NEXT `except ` at the same indent.

    The old version sliced to `except Exception`, which silently mis-slices the
    moment the branch is reordered or another handler is added between them."""
    import re

    src = _loop_source()
    i = src.index("            except AidotCameraNoMedia")
    m = re.search(r"\n            except ", src[i + 1:])
    return src[i:i + 1 + m.start()] if m else src[i:]


def test_the_no_media_branch_feeds_the_futile_keepalive_guard():
    """The guard at the bottom of the loop is what stops a battery camera being
    woken forever, and its own comment says why: "a unit has already been
    drained to 5% that way". Continuing straight to the next attempt bypasses
    it, because the accounting only runs after a session object exists. This
    branch has to do that accounting itself."""
    branch = _no_media_branch()
    assert "_next_no_media_streak" in branch, (
        "the streak must advance, or the circuit breaker never trips")
    assert "_should_abandon_keepalive" in branch, (
        "the branch must be able to stop the keepalive, as the loop bottom can")


def test_the_guard_actually_trips_for_a_run_of_no_media_attempts():
    """Behaviour, not just wiring: the helpers this branch calls must reach the
    abandon decision for a battery camera that never delivers."""
    from aidot_cameras.camera.client import (
        _FUTILE_KEEPALIVE_LIMIT,
        _next_no_media_streak,
        _should_abandon_keepalive,
    )

    streak = 0
    for _ in range(_FUTILE_KEEPALIVE_LIMIT):
        streak = _next_no_media_streak(streak, False)
    assert _should_abandon_keepalive(streak, is_battery=True) is True
    # A delivered session clears it, so a camera that recovers is not punished.
    assert _should_abandon_keepalive(
        _next_no_media_streak(streak, True), is_battery=True) is False


def test_the_no_media_branch_paces_as_a_session_that_ended_without_media():
    """Which is what it is - it just ended before the serve rather than after.

    The earlier version asserted `fail_delay` was absent and called that
    "not the escalating backoff". That was wrong: session_end_delay(healthy=False)
    escalates the same shared attempt counter, marginally sooner than
    fail_delay() does. The escalation is deliberate - a camera that never
    delivers should back off - so the test now says what is true."""
    branch = _no_media_branch()
    assert "_not_ready_retry_delay" in branch, (
        "a merely-slow camera keeps the fast not-ready burst")
    assert "session_end_delay" in branch, (
        "everything else paces as an ended session, escalation included")


def test_the_no_media_branch_retries_rather_than_giving_up():
    """It must loop round to another attempt - except when the futile-keepalive
    guard deliberately stops, which is a `return`, not a failure to retry."""
    branch = _no_media_branch()
    assert "continue" in branch


# --------------------------------------------------------------------------- #
# The plain timeout path, which the first version deliberately left alone
# --------------------------------------------------------------------------- #
#
# It was left alone with a stated reason: "HA's stream worker tolerates the
# transient failure and retries into the serve, and taking that away would
# change a case this has no evidence about."
#
# There is evidence now, from a camera that dropped off the WiFi on 2026-09-05.
# Every attempt ran the full first-media wait, timed out with NOTHING observed,
# launched the serve anyway, and ffmpeg died on the spot:
#
#   Could not find codec parameters for stream 1 (Video: h264, none):
#                                                       unspecified size
#   [rtsp] dimensions not set
#   [out#0/rtsp] Could not write header (incorrect codec parameters ?)
#
# 11 attempts and 6 stalls in 25 minutes, each spawning an ffmpeg that could not
# start. The stream worker was not tolerating a transient failure; it was
# looping on a permanent one. So the skip now applies to the timeout path too:
# what makes a serve doomed is that no media was observed, not which wait ended.

def test_a_timed_out_attempt_with_nothing_observed_also_skips(monkeypatch):
    """The 2026-09-05 case: not abandoned by the backstop, just silent.

    Env cleared, because this pins the SHIPPED default and the knob is read
    from the ambient environment - an operator who set the documented escape
    hatch would otherwise get a red suite that has nothing to do with them."""
    monkeypatch.delenv("AIDOT_SKIP_DOOMED_SERVE", raising=False)
    assert _should_skip_doomed_serve(abandoned=False, have_video=False) is True


def test_a_talk_or_snapshot_open_is_never_abandoned(monkeypatch):
    """The open path is shared. async_speak (siren, announce) and
    async_snapshot run the same first-media wait with no serve to build, and
    outbound talk does not need inbound media at all - the SRTP session and the
    ICE nomination are already up. Aborting those because no video arrived would
    break the siren on exactly the cameras this was written to help."""
    monkeypatch.delenv("AIDOT_SKIP_DOOMED_SERVE", raising=False)
    assert _should_skip_doomed_serve(
        abandoned=False, have_video=False, serving=False) is False
    assert _should_skip_doomed_serve(
        abandoned=True, have_video=False, serving=False) is False


def test_the_gate_only_fires_when_a_serve_is_actually_being_built():
    import inspect

    from aidot_cameras.camera.client import CameraMixin

    src = inspect.getsource(CameraMixin._open_sdes_stream_impl)
    assert "serving=bool(rtsp_push_url or output_path)" in src, (
        "the gate must be told whether this open builds a serve at all")


def test_the_backstop_case_still_skips():
    assert _should_skip_doomed_serve(abandoned=True, have_video=False) is True


def test_an_attempt_that_saw_video_still_serves():
    """Observed video is the whole point - that serve has something to carry,
    whichever way the wait ended."""
    assert _should_skip_doomed_serve(abandoned=True, have_video=True) is False
    assert _should_skip_doomed_serve(abandoned=False, have_video=True) is False


def test_the_skip_can_be_turned_off_without_a_release():
    """This changes behaviour on the commonest failure path, so it needs an
    escape hatch that does not require shipping a build."""
    import inspect

    from aidot_cameras.camera import sdes_open as so

    assert "AIDOT_SKIP_DOOMED_SERVE" in inspect.getsource(so)


def test_the_escape_hatch_restores_the_old_behaviour(monkeypatch):
    monkeypatch.setenv("AIDOT_SKIP_DOOMED_SERVE", "0")
    assert _should_skip_doomed_serve(abandoned=False, have_video=False) is False
    # The backstop case is what the old behaviour already skipped, so it stays.
    assert _should_skip_doomed_serve(abandoned=True, have_video=False) is True


def test_a_malformed_escape_hatch_does_not_change_anything(monkeypatch):
    """An unparseable knob must not be able to alter a media path."""
    monkeypatch.setenv("AIDOT_SKIP_DOOMED_SERVE", "banana")
    assert _should_skip_doomed_serve(abandoned=False, have_video=False) is True
