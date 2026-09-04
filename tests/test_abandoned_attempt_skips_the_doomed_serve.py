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


def test_the_plain_timeout_path_still_serves():
    """A camera that never spoke keeps today's behaviour exactly: the serve is
    launched, and HA's stream worker retries into it. Only the backstop path,
    which fires when the camera IS present, is redirected to the retry."""
    assert _s(abandoned=False) is False
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


def test_the_no_media_branch_does_not_use_the_open_failure_backoff():
    """`fail_delay()` escalates on every open failure. This case is not an open
    failure - the camera answered, it just sent nothing - and the path it stands
    in for (serve launched, session died) gets the fast not-ready retry. Pacing
    it as an open failure would be slower than the doomed serve it replaces."""
    src = _loop_source()
    branch = src[src.index("except AidotCameraNoMedia"):src.index("except Exception")]
    assert "_not_ready_retry_delay" in branch, "must reuse the not-ready pacing"
    assert "fail_delay" not in branch, "must NOT use the escalating open-failure backoff"


def test_the_no_media_branch_retries_rather_than_giving_up():
    """It must loop round to another attempt, not return or re-raise - the retry
    is the entire point."""
    src = _loop_source()
    branch = src[src.index("except AidotCameraNoMedia"):src.index("except Exception")]
    assert "continue" in branch
    assert "raise" not in branch
