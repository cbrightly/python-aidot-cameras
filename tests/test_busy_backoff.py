"""How long to wait after a camera says it has no free session.

On `-50002` both keepalive loops slept `_MAX_DELAY` - **300 seconds** - on the
reasoning that "the camera only releases slowly". Measured 2026-08-07 on a mains
A001064, that is wrong by more than an order of magnitude:

    close, reopen after 2s  -> -50002 refused   (three times, reproducibly)
    close, reopen after 8s  -> reopened fine    (every time)
    close, reopen after 20s -> reopened fine

The probe minted a fresh peerid on every attempt - the case the comment warned
about - and still recovered inside 8 seconds.

This matters beyond a slow reconnect. A camera that clears in seconds, paired
with a five-minute refusal to retry, is indistinguishable from a camera that
needs a long rest, and that appearance is most likely where the project's
"battery cameras need 30-45 minutes between sessions" rule came from. Three
back-to-back sessions on a battery L2 all streamed fine the same day.

The wait still has to be a real wait. Retrying immediately would hammer a camera
that genuinely has no free session, and on a battery model that risks the
wake-then-sleep loop the original comment was guarding against. So: comfortably
past the measured window, nowhere near five minutes.
"""
from aidot_cameras.camera.client import _BUSY_BACKOFF_S


def test_the_wait_clears_the_measured_refusal_window():
    """8s reopened cleanly every time; leave real headroom above that."""
    assert _BUSY_BACKOFF_S >= 15.0


def test_the_wait_is_not_a_five_minute_rest():
    """The whole point: a camera that clears in seconds must not cost minutes."""
    assert _BUSY_BACKOFF_S <= 60.0
