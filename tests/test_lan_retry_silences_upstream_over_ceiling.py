"""Over the LAN-login ceiling, upstream's per-attempt lines stop; the attempts do not.

Measured 2026-10-09 on one installation: a light whose discovered address was
on another subnet logged upstream's ``connect device error`` WARNING twelve
times an hour, 159 a day, AFTER ``LanRetryMixin`` had announced that it was
"not trying again".  The ceiling gated discovery kicks and upstream's own
reconnect loop, but the integration's light coordinator asks for a login every
five minutes through ``async_login``, which the ceiling never looked at.

The attempts are kept on purpose - they are how a light that comes back is
noticed without a restart - so the fix is to the noise: once a device is over
the ceiling at an unchanged address, upstream's lines for THAT device are
dropped until its address changes or a login gets through.
"""

import asyncio
import logging
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras import lan_retry  # noqa: E402
from aidot_cameras.lan_retry import _LOGIN_RETRY_LIMIT, LanRetryMixin  # noqa: E402

_UPSTREAM = logging.getLogger("aidot.device_client")


class _FakeUpstream:
    """The shape of upstream's DeviceClient login path, with its two log lines."""

    def __init__(self, device_id, ip):
        self.device_id = device_id
        self._ip_address = ip
        self._connecting = False
        self._connect_and_login = False
        self.attempts = 0
        self.succeed = False

    async def async_login(self):
        if self._ip_address is None:
            return
        if self._connecting is not True and self._connect_and_login is not True:
            await self.connect(self._ip_address)

    async def connect(self, ip_address):
        _UPSTREAM.warning(f"{self.device_id}:connect device: {ip_address}")
        self._connecting = True
        self.attempts += 1
        try:
            if self.succeed:
                self._connect_and_login = True
                return
            raise OSError(113, "Connect call failed")
        except Exception as e:
            self._connect_and_login = False
            _UPSTREAM.warning(f"{self.device_id}:connect device error: {e}")
        finally:
            self._connecting = False

    def update_ip_address(self, ip):
        self._ip_address = ip

    def reset(self):
        self._connect_and_login = False


class _Client(LanRetryMixin, _FakeUpstream):
    pass


def _upstream_errors(caplog, device_id):
    return [
        r
        for r in caplog.records
        if r.name == "aidot.device_client"
        and r.getMessage().startswith(f"{device_id}:connect device error")
    ]


@pytest.fixture(autouse=True)
def _levels(caplog):
    caplog.set_level(logging.DEBUG, logger="aidot.device_client")
    caplog.set_level(logging.DEBUG, logger="aidot_cameras.lan_retry")
    lan_retry._OVER_CEILING.clear()
    yield
    lan_retry._OVER_CEILING.clear()


def test_the_filter_is_on_the_upstream_logger_once():
    filters = [
        f
        for f in _UPSTREAM.filters
        if isinstance(f, lan_retry.SilenceOverCeilingFilter)
    ]
    assert len(filters) == 1
    lan_retry.install_over_ceiling_filter()
    assert (
        len(
            [
                f
                for f in _UPSTREAM.filters
                if isinstance(f, lan_retry.SilenceOverCeilingFilter)
            ]
        )
        == 1
    )


def test_attempts_continue_but_upstream_stops_logging_over_the_ceiling(caplog):
    extra = 4

    async def _run():
        c = _Client("dev-a", "192.168.1.254")
        for _ in range(_LOGIN_RETRY_LIMIT + extra):
            await c.async_login()
        return c

    c = asyncio.run(_run())
    # Every poll still tried: a light that comes back is noticed this way.
    assert c.attempts == _LOGIN_RETRY_LIMIT + extra
    # Upstream's line reached the log for the attempts up to the ceiling only.
    assert len(_upstream_errors(caplog, "dev-a")) == _LOGIN_RETRY_LIMIT
    ours = [r for r in caplog.records if r.name == "aidot_cameras.lan_retry"]
    assert sum(1 for r in ours if r.levelno == logging.WARNING) == 1
    assert (
        "no longer logged"
        in [r for r in ours if r.levelno == logging.WARNING][0].getMessage()
    )
    # The attempts after the ceiling are still visible at DEBUG.
    assert sum(1 for r in ours if r.levelno == logging.DEBUG) >= extra


def test_another_device_is_not_silenced(caplog):
    async def _run():
        a = _Client("dev-a", "192.168.1.254")
        b = _Client("dev-b", "192.168.1.17")
        for _ in range(_LOGIN_RETRY_LIMIT + 2):
            await a.async_login()
        for _ in range(2):
            await b.async_login()

    asyncio.run(_run())
    assert len(_upstream_errors(caplog, "dev-a")) == _LOGIN_RETRY_LIMIT
    assert len(_upstream_errors(caplog, "dev-b")) == 2


def test_a_new_address_lifts_the_silence(caplog):
    async def _run():
        c = _Client("dev-a", "192.168.1.254")
        for _ in range(_LOGIN_RETRY_LIMIT + 2):
            await c.async_login()
        before = len(_upstream_errors(caplog, "dev-a"))
        c.update_ip_address("192.168.0.50")
        await c.async_login()
        return before

    before = asyncio.run(_run())
    assert before == _LOGIN_RETRY_LIMIT
    after = _upstream_errors(caplog, "dev-a")
    assert len(after) == _LOGIN_RETRY_LIMIT + 1
    # The attempt at the new address is logged in full, its connect line too.
    assert any(
        r.getMessage() == "dev-a:connect device: 192.168.0.50"
        for r in caplog.records
        if r.name == "aidot.device_client"
    )


def test_a_login_that_gets_through_lifts_the_silence(caplog):
    async def _run():
        c = _Client("dev-a", "192.168.1.254")
        for _ in range(_LOGIN_RETRY_LIMIT + 2):
            await c.async_login()
        c.succeed = True
        await c.async_login()
        assert c._connect_and_login is True
        # The device drops again: its failures count from zero and are logged.
        c.succeed = False
        c._connect_and_login = False
        await c.async_login()

    asyncio.run(_run())
    assert len(_upstream_errors(caplog, "dev-a")) == _LOGIN_RETRY_LIMIT + 1
