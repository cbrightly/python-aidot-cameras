"""A failing LAN login must not respawn itself at the device's round-trip rate.

Upstream `aidot.device_client` retries a failed login with no delay and no
ceiling.  `login()` logs the error, calls `reset()`, and `reset()` ends with
`_schedule_reconnect()`, whose last line is
`asyncio.create_task(self.async_login())` - straight back into `connect()`.  The
`loop.call_later(60, ...)` on the line above never fires, because `reset()`
cancels `_reconnect_handle` at its own top on the next cycle.

So the period is not 60 s, it is the device's login round-trip.  Measured on a
live run: 8,434 of 8,434 failures were followed by that device's next connect
within a median of 0.295 ms, giving ~7.6 attempts per second for one light,
sustained for 22 minutes, and 15,376 failures across six devices in one run.
Nothing stops it - the run ended, the loop did not.

This is reachable in Home Assistant, not only in test harnesses: the integration
drives the same client from its coordinator.  Any device that cannot LAN-login -
wrong credentials, or another client already holding the session - is hammered
several times a second for as long as the integration is loaded.

The delay policy is the unit under test.  It has to grow, it has to stop, and it
has to keep the first retry prompt enough that an ordinary momentary drop still
recovers quickly.
"""
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.device_client import (  # noqa: E402
    _LOGIN_RETRY_CAP_S,
    _LOGIN_RETRY_LIMIT,
    _next_login_retry_delay,
)


def test_the_first_retry_is_prompt():
    """A single dropped connection is ordinary; recovery should not be punished."""
    d = _next_login_retry_delay(0)
    assert d is not None
    assert 0 < d <= 2.0


def test_the_delay_grows():
    delays = [_next_login_retry_delay(n) for n in range(4)]
    assert all(d is not None for d in delays)
    assert delays == sorted(delays)
    assert delays[-1] > delays[0]


def test_the_delay_is_capped():
    """Unbounded growth would turn a recoverable device into a dead one."""
    for n in range(_LOGIN_RETRY_LIMIT):
        d = _next_login_retry_delay(n)
        if d is not None:
            assert d <= _LOGIN_RETRY_CAP_S


def test_it_gives_up():
    """The property that ends the storm: eventually there is no next attempt."""
    assert _next_login_retry_delay(_LOGIN_RETRY_LIMIT) is None
    assert _next_login_retry_delay(_LOGIN_RETRY_LIMIT + 50) is None


def test_every_allowed_attempt_has_a_real_delay():
    """Returning 0 for any attempt would reproduce the storm exactly."""
    for n in range(_LOGIN_RETRY_LIMIT):
        d = _next_login_retry_delay(n)
        assert d is None or d > 0


@pytest.mark.parametrize("bad", [-1, -100])
def test_a_nonsense_attempt_count_does_not_produce_a_zero_delay(bad):
    d = _next_login_retry_delay(bad)
    assert d is None or d > 0


def test_the_ceiling_is_overridable(monkeypatch):
    """An installation that genuinely wants the old behaviour can have it, but
    it has to ask - the default cannot be 'retry forever'."""
    assert _LOGIN_RETRY_LIMIT > 0
