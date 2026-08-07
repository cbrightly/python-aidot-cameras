"""Stop waking a battery camera that never delivers media.

Observed on an A001513 2026-08-07: 22 session opens across roughly eight hours,
every one logging "no audio observed before the serve launched" and 21 of them
ending with the serve's ffmpeg exiting because it never got a video frame to
take codec parameters from. No media ever arrived, and the keepalive loop kept
reopening - `while self._streaming_active` with an escalating backoff but no
condition under which it ever stops.

On a mains camera that is merely wasteful. On a battery camera it spends charge,
and this project has already drained a unit to 5% that way.

**This does not fix why the media never arrives.** That is a separate and
older question - the same camera streams fine from other hosts on the same LAN,
including the validation runner, so it is not the camera and not the library's
protocol. What this fixes is the loop: a background task that has failed the
same way many times over should stop and say so, rather than continuing
indefinitely at the camera's expense.

Giving up is scoped to the background keepalive. A view still starts a session
on demand - the user asking to see the camera is new information, and the
retry ceiling should not decide on their behalf that it cannot work.
"""
import pytest

from aidot_cameras.camera.client import _should_abandon_keepalive as abandon

LIMIT = 5


@pytest.mark.parametrize("streak", [0, 1, 3, 4])
def test_a_battery_camera_gets_several_chances(streak):
    """A cold battery wake legitimately takes a few attempts."""
    assert abandon(streak, is_battery=True, limit=LIMIT) is False


def test_a_battery_camera_is_left_alone_after_the_limit():
    assert abandon(LIMIT, is_battery=True, limit=LIMIT) is True
    assert abandon(LIMIT + 9, is_battery=True, limit=LIMIT) is True


def test_a_mains_camera_keeps_trying():
    """Nothing to protect, and a mains camera that recovers should recover.

    The observed loop cost battery; on a mains camera the same persistence is
    what gets a stream back after a router reboot or a camera power-cycle.
    """
    assert abandon(999, is_battery=False, limit=LIMIT) is False


def test_the_streak_is_consecutive_failures_only():
    """A session that delivered media resets it - that is the caller's job, and
    this exists so the intent is written down where the limit is."""
    assert abandon(0, is_battery=True, limit=LIMIT) is False


def test_a_limit_of_zero_never_abandons():
    """The escape hatch has to actually disable the behaviour."""
    assert abandon(50, is_battery=True, limit=0) is False
