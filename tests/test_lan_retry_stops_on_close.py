"""Finding 1 of the 2026-10-13 review, plus a follow-up review of the first fix.

`LanRetryMixin._schedule_reconnect` arms a delayed `_retry` task (1-60 s
backoff) that sleeps and then calls `self.async_login()` with no re-check of
`_is_close`.  `CameraClient.async_close` cancels a pending reconnect only
through `_upstream.cancel_pending_reconnect`, which (before the first fix)
knew upstream's `_reconnect_handle`/`_reconnect_timer` but not the mixin's
delayed retry at all.  So after an integration unload/reload, a retry armed
before close woke up afterwards and logged a light back in on a closed
client; on success that leaked upstream's receive task and 30 s ping timer
for the life of the process.

The first fix tracked the delayed retry under upstream's own `_login_task`
attribute.  A follow-up review found that false on the discovery path:
upstream's `connect()` and `update_ip_address()` both overwrite `_login_task`
unconditionally with a fresh in-flight login task, with no cancel of
whatever was already parked there.  Discovery calls `update_ip_address` on
every broadcast, same address or not, so a delayed retry sharing that name
was silently orphaned - unreachable to `cancel_pending_reconnect` - rather
than cancelled, and still fired its own login a few seconds later: two
attempts for one broadcast.  The retry is now tracked under its own
`_lan_retry_task` attribute instead.

Tests, in order: the delayed task refuses to log in once closed (defense in
depth, independent of anyone cancelling it); the shared cancel helper finds
and cancels it; arming a second retry while one is still pending cancels the
first instead of leaving both to log in; that cancel-the-previous-task logic
must not cancel ITSELF when `async_login` fails and calls back into
`_schedule_reconnect` from inside the very task being replaced; an ordinary
retry with nothing closing it still fires (positive control); the discovery
path (`update_ip_address`) cancels a sleeping retry instead of orphaning it,
and the retry that replaces it stays reachable to `cancel_pending_reconnect`;
and the same close defect end to end through the real `CameraClient` dispatch
and teardown, not the bare mixin - this repo has already shipped a fix that
was correct code on a class the real construction path never built (see
`tests/test_lan_retry_reaches_the_storming_devices.py`).
"""

import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot.device_client as _upstream_device_client  # noqa: E402

from aidot_cameras import _upstream  # noqa: E402
from aidot_cameras import lan_retry  # noqa: E402
from aidot_cameras.client import CameraClient  # noqa: E402
from aidot_cameras.lan_retry import LanRetryMixin  # noqa: E402


class _FakeUpstream:
    """Stands in for `aidot.device_client.DeviceClient` for this mixin.

    `update_ip_address` reproduces upstream's real behaviour exactly where it
    matters here: it overwrites `_login_task` unconditionally with a fresh
    in-flight login task whenever the client is not mid-connect and not
    logged in, with no cancel of whatever was already parked under that
    name.  See `aidot.device_client.DeviceClient.update_ip_address`.
    """

    def __init__(self):
        self.device_id = "dev-a"
        self._ip_address = "192.168.1.50"
        self._connecting = False
        self._connect_and_login = False
        self._is_close = False
        self._login_task = None
        self.login_calls = 0

    async def async_login(self):
        self.login_calls += 1

    def update_ip_address(self, ip):
        if ip is None:
            return
        self._ip_address = ip
        if self._connecting is not True and self._connect_and_login is not True:
            self._login_task = asyncio.create_task(self.async_login())


class _Client(LanRetryMixin, _FakeUpstream):
    pass


def test_a_pending_retry_does_not_log_in_once_closed(monkeypatch):
    """The delayed task re-checks `_is_close` itself, after its sleep.

    Closed directly (no cancel), the way a caller unaware of `_lan_retry_task`
    would leave it: the task must still refuse to call `async_login()`.
    """
    monkeypatch.setattr(
        "aidot_cameras.lan_retry._next_login_retry_delay", lambda attempt: 0.05
    )

    async def _run():
        c = _Client()
        c._schedule_reconnect()
        task = c._lan_retry_task
        assert task is not None
        c._is_close = True  # close() lands while the retry is still asleep
        await asyncio.sleep(0.15)
        return c.login_calls, task.done()

    login_calls, done = asyncio.run(_run())
    assert login_calls == 0, "a closed client must not be logged back in"
    assert done


def test_cancel_pending_reconnect_finds_the_mixin_s_delayed_retry(monkeypatch):
    """`_upstream.cancel_pending_reconnect` is what `CameraClient.async_close`
    actually calls; it has to know about `_lan_retry_task`, not only the two
    upstream timer-handle spellings."""
    monkeypatch.setattr(
        "aidot_cameras.lan_retry._next_login_retry_delay", lambda attempt: 60.0
    )

    async def _run():
        c = _Client()
        c._schedule_reconnect()
        task = c._lan_retry_task
        assert task is not None and not task.done()
        cancelled = _upstream.cancel_pending_reconnect(c)
        c._is_close = True
        await asyncio.sleep(0)
        return cancelled, task.cancelled() or task.done(), c.login_calls

    cancelled, task_gone, login_calls = asyncio.run(_run())
    assert cancelled is True
    assert task_gone
    assert login_calls == 0


def test_scheduling_a_new_retry_cancels_the_still_pending_one(monkeypatch):
    """`reset()` can call back into `_schedule_reconnect` (another dropped
    connection, or an address change) while an earlier delayed retry is still
    asleep.  Overwriting `_lan_retry_task` without cancelling it would let
    both reach `async_login()`."""
    monkeypatch.setattr(
        "aidot_cameras.lan_retry._next_login_retry_delay", lambda attempt: 0.05
    )

    async def _run():
        c = _Client()
        c._login_attempt = 0
        c._schedule_reconnect()
        first = c._lan_retry_task
        c._login_attempt = 0  # a fresh cycle, as an address change would give it
        c._schedule_reconnect()
        second = c._lan_retry_task
        assert second is not first
        await asyncio.sleep(0.15)
        return first.cancelled(), c.login_calls

    first_cancelled, login_calls = asyncio.run(_run())
    assert first_cancelled
    assert login_calls == 1, "only the replacement retry should reach async_login"


def test_a_retry_still_logs_in_when_nothing_closed_it(monkeypatch):
    """The fix must not turn into a fix that never retries at all."""
    monkeypatch.setattr(
        "aidot_cameras.lan_retry._next_login_retry_delay", lambda attempt: 0.05
    )

    async def _run():
        c = _Client()
        c._schedule_reconnect()
        await asyncio.sleep(0.15)
        return c.login_calls

    assert asyncio.run(_run()) == 1


def test_a_retry_whose_login_fails_can_rearm_itself(monkeypatch):
    """The cancel-the-previous-task guard must not cancel the task it runs in.

    A failed login ends in upstream's `reset()`, which calls
    `_schedule_reconnect()` again - from inside THIS `_retry` task, on this
    same call stack.  At that point `self._lan_retry_task` (the "previous" one
    about to be replaced) IS the task currently running this code.  Cancelling
    it there would cut the retry attempt short instead of merely replacing a
    DIFFERENT, still-sleeping task.
    """
    monkeypatch.setattr(
        "aidot_cameras.lan_retry._next_login_retry_delay", lambda attempt: 0.05
    )

    class _ClientThatRearmsOnFailure(_Client):
        async def async_login(self):
            await super().async_login()
            if self.login_calls < 2:
                self._schedule_reconnect()  # what reset() does on failure
            else:
                self._is_close = True  # stop the chain once both ran

    async def _run():
        c = _ClientThatRearmsOnFailure()
        c._schedule_reconnect()
        first = c._lan_retry_task
        await asyncio.sleep(0.15)  # first attempt's sleep + its own re-arm
        second = c._lan_retry_task
        await asyncio.sleep(0.15)  # second attempt's sleep
        return first, second, c.login_calls

    first, second, login_calls = asyncio.run(_run())
    assert not first.cancelled(), "the retry must not cancel itself"
    assert second is not first
    assert login_calls == 2, "both the first and the re-armed retry must fire"


def test_a_rebroadcast_cancels_the_sleeping_retry_instead_of_orphaning_it(
    monkeypatch,
):
    """The discovery path, not a direct `_schedule_reconnect` call.

    Discovery calls `update_ip_address` on every broadcast, same address or
    not.  Upstream's own version (reached via `super()`) overwrites
    `_login_task` unconditionally with a fresh in-flight login task.  Before
    this test's fix, the mixin's delayed retry shared that same attribute
    name, so the rebroadcast's fresh task silently replaced it in the
    attribute - the sleeping retry coroutine kept running, unreachable to
    `cancel_pending_reconnect`, and fired its own `async_login()` a few
    seconds later: two login attempts for one broadcast.  Tracking the retry
    under its own `_lan_retry_task` means `update_ip_address` has to cancel
    it explicitly, which it now does.

    The rebroadcast's own login then fails (as it would for a device that is
    not actually there yet) and arms a new retry; that one must stay
    reachable to `cancel_pending_reconnect`, proving the fix did not just move
    the orphaning one step later.
    """
    monkeypatch.setattr(
        "aidot_cameras.lan_retry._next_login_retry_delay", lambda attempt: 60.0
    )

    class _ClientThatFailsItsFreshLogin(_Client):
        async def async_login(self):
            await super().async_login()
            if self.login_calls == 1:
                self._schedule_reconnect()  # the rebroadcast's login fails

    async def _run():
        c = _ClientThatFailsItsFreshLogin()
        c._schedule_reconnect()  # an earlier failure's retry, still asleep
        sleeping_retry = c._lan_retry_task
        assert sleeping_retry is not None and not sleeping_retry.done()

        c.update_ip_address(c._ip_address)  # a same-address rebroadcast
        await asyncio.sleep(0)  # the fresh immediate login runs and fails

        new_retry = c._lan_retry_task
        cancelled = _upstream.cancel_pending_reconnect(c)
        await asyncio.sleep(0)
        return sleeping_retry, new_retry, cancelled, c.login_calls

    sleeping_retry, new_retry, cancelled, login_calls = asyncio.run(_run())
    assert sleeping_retry.cancelled(), (
        "the pre-existing retry must be cancelled by the rebroadcast, not left "
        "to fire on its own later"
    )
    assert new_retry is not None and new_retry is not sleeping_retry
    assert cancelled is True, (
        "cancel_pending_reconnect must still find the retry the rebroadcast armed"
    )
    assert login_calls == 1, (
        "exactly one login from the rebroadcast; the orphaned retry firing too "
        "would make this two"
    )


def test_a_closed_light_does_not_log_back_in_through_real_account_close(
    monkeypatch,
):
    """The same defect, through `CameraClient.async_close`, not the bare mixin.

    `get_device_client` is the real dispatch seam and `async_close` is the
    real integration-unload path; a fix proven only against a hand-built
    `_Client` has, in this repo, once been correct code that the real
    construction path never reached.
    """
    monkeypatch.setattr(lan_retry, "_next_login_retry_delay", lambda attempt: 0.05)

    calls = []

    async def fake_connect(self, ip):
        calls.append((self.device_id, ip, self._is_close))

    monkeypatch.setattr(_upstream_device_client.DeviceClient, "connect", fake_connect)

    async def _run():
        token = {
            "id": "u1",
            "username": "a@b",
            "password": "x",
            "region": "us",
            "country": "United States",
            "accessToken": "t",
            "refreshToken": "r",
        }
        client = CameraClient(None, token=token)
        device_client = client.get_device_client(
            {"id": "light1", "modelId": "lk.light", "name": "L", "properties": {}}
        )
        device_client._ip_address = "127.0.0.1"
        await device_client.reset()  # a dropped connection arms the retry
        task = device_client._lan_retry_task
        assert task is not None and not task.done()
        await client.async_close()  # the real integration-unload path
        calls.clear()
        await asyncio.sleep(0.15)
        return calls, task

    calls_after_close, task = asyncio.run(_run())
    lan_retry._OVER_CEILING.clear()
    assert calls_after_close == [], (
        "a closed account's light must not open a new LAN connection"
    )
    assert task.cancelled() or task.done()
