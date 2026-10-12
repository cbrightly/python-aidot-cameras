"""Findings 4, 9, 12 and 13 of the 2026-10-11 review.

4: past the ceiling, a refused or accept-then-close login still logged
   upstream's login lines and the library's own "did not answer" / "giving up"
   warnings on every attempt (576 WARNING + 288 ERROR a day on a 5-min poll).
9: an address change kept the old address's backoff count, so the new address
   got no retries and the "giving up" line reported the stale count.
12: AIDOT_LOGIN_RETRY_LIMIT <= 0 made `0 >= limit` true for working lights.
13: the two rc45 SDES graces were parsed with a bare float() at import.
"""

import asyncio
import logging
import os
import subprocess
import sys

import pytest

from aidot_cameras import lan_retry
from aidot_cameras.lan_retry import LanRetryMixin, SilenceOverCeilingFilter

_UPSTREAM = logging.getLogger("aidot.device_client")


@pytest.fixture(autouse=True)
def _clean():
    lan_retry._OVER_CEILING.clear()
    yield
    lan_retry._OVER_CEILING.clear()


def _record(msg, level=logging.WARNING):
    return logging.LogRecord("aidot.device_client", level, __file__, 1, msg, None, None)


@pytest.mark.parametrize(
    "msg,level",
    [
        ("dev-a:connect device: 192.168.1.254", logging.WARNING),
        ("dev-a:login result: {'ack': {'code': 403}}", logging.WARNING),
        ("dev-a:login error, code: 403", logging.ERROR),
        ("dev-a login read status error Connection reset", logging.ERROR),
    ],
)
def test_every_per_attempt_line_is_dropped_over_the_ceiling(msg, level):
    f = SilenceOverCeilingFilter()
    lan_retry._OVER_CEILING.add("dev-a")
    assert f.filter(_record(msg, level)) is False
    lan_retry._OVER_CEILING.clear()
    assert f.filter(_record(msg, level)) is True


def test_other_devices_other_lines_and_critical_pass():
    f = SilenceOverCeilingFilter()
    lan_retry._OVER_CEILING.add("dev-a")
    assert f.filter(_record("dev-b:login error, code: 403", logging.ERROR)) is True
    assert f.filter(_record("dev-a:send action error boom", logging.ERROR)) is True
    assert f.filter(_record("dev-a:login error, code: 403", logging.CRITICAL)) is True
    assert f.filter(_record("dev-ab:login error, code: 403", logging.ERROR)) is True


def test_the_timeout_warning_is_debug_past_the_ceiling(caplog):
    caplog.set_level(logging.DEBUG, logger="aidot_cameras.lan_retry")

    async def never():
        await asyncio.sleep(10)

    async def noop():
        return None

    lan_retry._OVER_CEILING.add("dev-a")
    asyncio.run(lan_retry._await_connect_with_deadline(never(), 0.01, "dev-a", noop))
    lan_retry._OVER_CEILING.clear()
    asyncio.run(lan_retry._await_connect_with_deadline(never(), 0.01, "dev-b", noop))
    lines = [r for r in caplog.records if "did not answer" in r.getMessage()]
    assert [r.levelno for r in lines] == [logging.DEBUG, logging.WARNING]


class _Fake:
    def __init__(self):
        self.device_id = "dev-a"
        self._ip_address = "192.168.1.254"
        self._connecting = False
        self._connect_and_login = False
        self._is_close = False

    def update_ip_address(self, ip):
        self._ip_address = ip


class _Client(LanRetryMixin, _Fake):
    pass


def test_giving_up_warns_once_then_debug(caplog, monkeypatch):
    caplog.set_level(logging.DEBUG, logger="aidot_cameras.lan_retry")
    monkeypatch.setattr(lan_retry, "_next_login_retry_delay", lambda attempt: None)
    c = _Client()
    c._login_attempt = 6
    c._schedule_reconnect()
    c._schedule_reconnect()
    c._schedule_reconnect()
    levels = [r.levelno for r in caplog.records if "giving up" in r.getMessage()]
    assert levels == [logging.WARNING, logging.DEBUG, logging.DEBUG]


def test_an_address_change_resets_the_backoff_and_the_giving_up_line():
    c = _Client()
    c._login_attempt = 6
    c._connect_failures = 6
    c._gave_up_logged = True
    lan_retry._OVER_CEILING.add("dev-a")
    c.update_ip_address("192.168.0.40")
    assert (c._login_attempt, c._connect_failures, c._gave_up_logged) == (0, 0, False)
    assert "dev-a" not in lan_retry._OVER_CEILING


@pytest.mark.parametrize("limit", [0, -1])
def test_a_limit_of_zero_or_less_never_marks_a_working_light(monkeypatch, limit):
    monkeypatch.setattr(lan_retry, "_LOGIN_RETRY_LIMIT", limit)
    c = _Client()
    c._connect_failures = 0
    assert c.lan_login_over_ceiling is False
    c._connect_and_login = True
    c._connect_failures = 3
    assert c.lan_login_over_ceiling is False


def test_the_default_limit_still_marks_a_failing_light():
    c = _Client()
    c._connect_failures = lan_retry._LOGIN_RETRY_LIMIT
    assert c.lan_login_over_ceiling is True


@pytest.mark.parametrize("value", ["", "20s", "nan", "inf"])
def test_a_bad_grace_value_neither_breaks_the_import_nor_fires_at_once(value):
    env = dict(os.environ)
    env["AIDOT_SDES_TRIGGER_GRACE_S"] = value
    env["AIDOT_SDES_ANSWER_PROBE_GRACE_S"] = value
    out = subprocess.run(
        [
            sys.executable,
            "-c",
            "import aidot_cameras.camera.sdes_open as s;"
            "print(s._TRIGGER_GRACE_S, s._ANSWER_PROBE_GRACE_S)",
        ],
        env=env,
        capture_output=True,
        text=True,
        timeout=60,
    )
    assert out.returncode == 0, out.stderr[-500:]
    assert out.stdout.split() == ["20.0", "20.0"]
