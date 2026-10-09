"""The LAN login state a consumer may read without touching private names."""

import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.lan_retry import _LOGIN_RETRY_LIMIT, LanRetryMixin  # noqa: E402


class _FakeUpstream:
    def __init__(self, device_id, ip):
        self.device_id = device_id
        self._ip_address = ip
        self._connecting = False
        self._connect_and_login = False
        self.succeed = False

    async def async_login(self):
        if self._ip_address is None:
            return
        if self._connecting is not True and self._connect_and_login is not True:
            await self.connect(self._ip_address)

    async def connect(self, ip_address):
        self._connecting = True
        try:
            if self.succeed:
                self._connect_and_login = True
                return
            self._connect_and_login = False
        finally:
            self._connecting = False

    def update_ip_address(self, ip):
        self._ip_address = ip

    def reset(self):
        self._connect_and_login = False


class _Client(LanRetryMixin, _FakeUpstream):
    pass


def test_a_fresh_client_reports_its_address_and_no_failures():
    c = _Client("dev-a", "192.168.1.254")
    assert c.lan_address == "192.168.1.254"
    assert c.lan_login_failures == 0
    assert c.lan_login_over_ceiling is False


def test_failures_count_up_to_and_past_the_ceiling():
    async def _run():
        c = _Client("dev-a", "192.168.1.254")
        for _ in range(_LOGIN_RETRY_LIMIT - 1):
            await c.async_login()
        assert c.lan_login_failures == _LOGIN_RETRY_LIMIT - 1
        assert c.lan_login_over_ceiling is False
        await c.async_login()
        assert c.lan_login_over_ceiling is True
        await c.async_login()
        assert c.lan_login_failures == _LOGIN_RETRY_LIMIT + 1
        return c

    asyncio.run(_run())


def test_a_login_that_gets_through_clears_the_count():
    async def _run():
        c = _Client("dev-a", "192.168.1.254")
        for _ in range(_LOGIN_RETRY_LIMIT + 1):
            await c.async_login()
        c.succeed = True
        await c.async_login()
        assert c.lan_login_failures == 0
        assert c.lan_login_over_ceiling is False

    asyncio.run(_run())


def test_no_address_reads_as_none():
    c = _Client("dev-a", None)
    assert c.lan_address is None
