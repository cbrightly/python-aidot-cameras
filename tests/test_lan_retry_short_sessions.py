"""A light that accepts the login and then closes the session is not working.

Measured 2026-10-24 on the reference installation: four lights answered the
LAN login with a 200 ack, then closed the socket about a second later.
Upstream's receive loop logged ``<id>:read status error 0 bytes read on a total
of 8 expected bytes`` at ERROR, reset() handed the device to the retry policy,
the policy waited its first one-second step and logged in again - and because
that login got through, it reset the backoff to the first step.  The backoff
never grew and the ceiling was never reached: 54,000 ERROR lines in one day.

These tests run upstream's real ``DeviceClient`` (through the library's light
client) against a local TCP server that plays such a light: it reads the login,
answers it with a valid encrypted 200 ack, and closes.
"""

import asyncio
import json
import logging
import os
import struct
import sys
import time

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.aes_utils import aes_encrypt  # noqa: E402

from aidot_cameras import lan_retry  # noqa: E402
from aidot_cameras.client import CameraClient  # noqa: E402

KEY = "k" * 16
DEV_ID = "0a0b0c0d0e0f"
UPSTREAM = lan_retry.UPSTREAM_DEVICE_LOGGER
LIMIT = lan_retry._LOGIN_RETRY_LIMIT


def _light():
    return {
        "id": DEV_ID,
        "name": DEV_ID,
        "modelId": "lk.WIFI-CCTLight-D0001",
        "aesKey": [KEY],
        "password": "pw",
        "simpleVersion": "1",
        "product": {
            "serviceModules": [
                {
                    "identity": "control.light.cct",
                    "properties": [{"minValue": "2700", "maxValue": "6500"}],
                },
            ]
        },
    }


def _packet(message: dict) -> bytes:
    body = aes_encrypt(json.dumps(message).encode(), KEY.encode())
    return struct.pack(">HhI", 0x1EED, 1, len(body)) + body


class FakeLight:
    """A TCP:10000 light. ``hold(n)`` says how long session ``n`` stays up."""

    def __init__(self, hold, code=200):
        self.hold = hold
        self.code = code
        self.connects: list[float] = []
        self.server = None

    async def _serve(self, reader, writer):
        n = len(self.connects)
        self.connects.append(time.monotonic())
        try:
            header = await reader.readexactly(8)
            (size,) = struct.unpack(">I", header[4:])
            await reader.readexactly(size)
            writer.write(
                _packet(
                    {
                        "service": "device",
                        "method": "loginResp",
                        "ack": {"code": self.code},
                        "payload": {"ascNumber": 1},
                    }
                )
            )
            await writer.drain()
            await asyncio.sleep(self.hold(n))
        except (asyncio.IncompleteReadError, ConnectionError):
            pass
        finally:
            writer.close()

    async def start(self, mp):
        self.server = await asyncio.start_server(self._serve, "127.0.0.1", 0)
        port = self.server.sockets[0].getsockname()[1]
        real = asyncio.open_connection

        def _to_fake(host, _port, **kw):  # upstream hardcodes port 10000
            return real(host, port, **kw)

        mp.setattr(asyncio, "open_connection", _to_fake)

    async def stop(self):
        self.server.close()


@pytest.fixture(autouse=True)
def _clean_sets(monkeypatch):
    lan_retry._OVER_CEILING.clear()
    lan_retry._FLAPPING.clear()
    # Fast backoff: 10, 20, 40 ... ms, capped well below the test's patience.
    monkeypatch.setattr(lan_retry, "_LOGIN_RETRY_BASE_S", 0.01)
    monkeypatch.setattr(lan_retry, "_LOGIN_RETRY_CAP_S", 0.2)
    yield
    lan_retry._OVER_CEILING.clear()
    lan_retry._FLAPPING.clear()


async def _light_client(mp, stable_s):
    dc = CameraClient(None, country_code="US").get_device_client(_light())
    mp.setattr(type(dc), "_lan_stable_session_s", stable_s, raising=False)
    dc._ip_address = "127.0.0.1"
    dc.user_id = "u1"  # no account behind this client; upstream needs one
    return dc


def _lines(caplog, text, logger=None):
    return [
        r
        for r in caplog.records
        if text in r.getMessage() and (logger is None or r.name == logger)
    ]


def test_closed_right_after_login_backs_off_and_reaches_the_ceiling(caplog):
    caplog.set_level(logging.DEBUG, logger="aidot_cameras.lan_retry")
    caplog.set_level(logging.WARNING, logger=UPSTREAM)

    async def _run():
        with pytest.MonkeyPatch.context() as mp:
            light = FakeLight(hold=lambda n: 0.0)
            await light.start(mp)
            dc = await _light_client(mp, stable_s=5.0)
            await dc.async_login()
            await asyncio.sleep(2.0)
            stats = (
                list(light.connects),
                dc._login_attempt,
                dc._short_sessions,
                dc.lan_session_unstable,
            )
            await dc.close()
            await light.stop()
            return stats

    connects, attempt, short, unstable = asyncio.run(_run())

    # One login, then one retry per backoff step up to the ceiling, then none.
    assert len(connects) == LIMIT + 1, connects
    assert attempt == LIMIT
    assert short == LIMIT + 1
    assert unstable is True
    gaps = [b - a for a, b in zip(connects, connects[1:])]
    assert gaps[-1] > gaps[0] * 4, f"the backoff did not grow: {gaps}"

    # Upstream's per-close ERRORs: only the first close's get through (the
    # receive loop's line and, racing it, a ping on the dead socket), and as
    # WARNINGs.  Unfiltered this run logs one or more per close.
    ends = [
        r
        for r in caplog.records
        if r.name == UPSTREAM
        and r.getMessage()[len(DEV_ID) :]
        .lstrip(": ")
        .startswith(lan_retry._SESSION_END_PREFIXES)
    ]
    assert 1 <= len(ends) <= 3, [r.getMessage() for r in ends]
    assert {r.levelno for r in ends} == {logging.WARNING}
    assert not [
        r for r in caplog.records if r.name == UPSTREAM and r.levelno >= logging.ERROR
    ], [r.getMessage() for r in caplog.records if r.levelno >= logging.ERROR]

    # Ours: one line for the outage, one at the ceiling.
    early = _lines(caplog, "closed its LAN session")
    assert [r.levelno for r in early] == [logging.WARNING]
    giving_up = [
        r for r in _lines(caplog, "giving up LAN login") if r.levelno >= logging.WARNING
    ]
    assert len(giving_up) == 1


def test_a_stable_session_that_drops_gets_the_prompt_first_retry(caplog):
    caplog.set_level(logging.DEBUG, logger="aidot_cameras.lan_retry")

    async def _run():
        with pytest.MonkeyPatch.context() as mp:
            # First session outlives the stable window, then the light drops
            # it; the next one stays up.
            light = FakeLight(hold=lambda n: 0.3 if n == 0 else 30.0)
            await light.start(mp)
            dc = await _light_client(mp, stable_s=0.1)
            dc._login_attempt = 5  # earlier failures: next step would be the cap
            await dc.async_login()
            await asyncio.sleep(0.8)
            stats = (list(light.connects), dc._login_attempt, dc._short_sessions)
            await dc.close()
            await light.stop()
            return stats

    connects, attempt, short = asyncio.run(_run())
    assert len(connects) == 2
    # Stable: the backoff was cleared, so the drop got the first step (10 ms
    # here), not the capped one (200 ms) the earlier failures had reached.
    assert connects[1] - connects[0] < 0.3 + 0.1
    assert short == 0
    assert attempt == 0  # and the second session is stable too
    assert not _lines(caplog, "closed its LAN session")


def test_a_refused_login_is_counted_once_and_not_as_an_early_close():
    async def _run():
        with pytest.MonkeyPatch.context() as mp:
            light = FakeLight(hold=lambda n: 0.0, code=401)
            await light.start(mp)
            dc = await _light_client(mp, stable_s=5.0)
            # Keep upstream's reset() from scheduling anything: one attempt.
            mp.setattr(type(dc), "_schedule_reconnect", lambda self: None)
            await dc.async_login()
            await asyncio.sleep(0.1)
            stats = (len(light.connects), dc._connect_failures, dc._short_sessions)
            await dc.close()
            await light.stop()
            return stats

    assert asyncio.run(_run()) == (1, 1, 0)


def test_closing_the_client_mid_session_is_not_an_early_close():
    async def _run():
        with pytest.MonkeyPatch.context() as mp:
            light = FakeLight(hold=lambda n: 30.0)
            await light.start(mp)
            dc = await _light_client(mp, stable_s=5.0)
            await dc.async_login()
            await asyncio.sleep(0.05)
            await dc.close()
            await light.stop()
            return dc._short_sessions, DEV_ID in lan_retry._FLAPPING

    assert asyncio.run(_run()) == (0, False)


class _Fake:
    def __init__(self):
        self.device_id = DEV_ID
        self._ip_address = "10.0.1.50"
        self._connecting = False
        self._connect_and_login = False
        self._is_close = False
        self.kicks = 0

    def update_ip_address(self, ip):
        self._ip_address = ip
        self.kicks += 1


class _Client(lan_retry.LanRetryMixin, _Fake):
    pass


def test_discovery_stops_kicking_at_the_ceiling_until_the_address_changes():
    c = _Client()
    c._short_sessions = LIMIT
    c.update_ip_address("10.0.1.50")
    assert c.kicks == 0
    c.update_ip_address("10.0.1.51")
    assert c.kicks == 1
    assert c._short_sessions == 0


def _record(message, level=logging.ERROR):
    return logging.LogRecord(UPSTREAM, level, __file__, 1, message, None, None)


def test_the_filter_demotes_the_first_close_and_drops_the_rest_while_flapping():
    f = lan_retry.SilenceOverCeilingFilter()
    for line in (
        f"{DEV_ID}:read status error 0 bytes read",
        f"{DEV_ID} ping error Connection lost",
    ):
        first = _record(line)
        assert f.filter(first) is True
        assert first.levelno == logging.WARNING and first.levelname == "WARNING"

    lan_retry._FLAPPING.add(DEV_ID)
    for line in (
        f"{DEV_ID}:read status error 0 bytes read",
        f"{DEV_ID} writer/reader close error [Errno 104] Connection reset by peer",
        f"{DEV_ID} login read status error [Errno 104] Connection reset by peer",
        f"{DEV_ID} ping error Connection lost",
    ):
        assert f.filter(_record(line)) is False, line

    # Another device, and an unrelated line for this one, pass untouched.
    other = _record("ffff0000:read status error 0 bytes read")
    assert f.filter(other) is True
    unrelated = _record(f"{DEV_ID}:recv error: bad frame")
    assert f.filter(unrelated) is True and unrelated.levelno == logging.ERROR
    critical = _record(f"{DEV_ID}:read status error x", level=logging.CRITICAL)
    assert f.filter(critical) is True
