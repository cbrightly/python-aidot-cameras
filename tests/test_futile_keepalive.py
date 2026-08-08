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

from aidot_cameras.camera.client import _next_no_media_streak as next_streak
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


@pytest.mark.parametrize("streak", [0, 1, 4, LIMIT, LIMIT + 9, 999])
def test_a_session_that_delivered_media_resets_the_streak(streak):
    """Consecutive, not cumulative.

    Without the reset the count only ever rises, so a camera that works most of
    the time but fails now and then - five scattered no-media sessions over a
    day, each separated by sessions that delivered - trips a ceiling meant for
    a camera that never delivers anything, and loses its keepalive for good.
    """
    assert next_streak(streak, True) == 0


@pytest.mark.parametrize("streak", [0, 1, 4, 50])
def test_a_session_without_media_advances_the_streak_by_one(streak):
    assert next_streak(streak, False) == streak + 1


def test_the_streak_reaches_the_limit_only_on_consecutive_failures():
    """Walk the counter the way the keepalive loop does.

    Four failures then a success then four more must NOT abandon, while five
    unbroken failures must - which is the whole distinction the reset carries.
    """
    streak = 0
    for healthy in (False, False, False, False, True, False, False, False, False):
        streak = next_streak(streak, healthy)
        assert abandon(streak, is_battery=True, limit=LIMIT) is False

    streak = 0
    outcomes = []
    for _ in range(LIMIT):
        streak = next_streak(streak, False)
        outcomes.append(abandon(streak, is_battery=True, limit=LIMIT))
    assert outcomes == [False, False, False, False, True]


def test_a_limit_of_zero_never_abandons():
    """The escape hatch has to actually disable the behaviour."""
    assert abandon(50, is_battery=True, limit=0) is False
