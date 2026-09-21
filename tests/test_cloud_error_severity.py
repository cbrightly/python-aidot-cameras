"""A transient cloud blip must not page; a persistent one must.

The motion poll calls ``async_get_cloud_recordings`` every ``_motion_interval``
over a ``lookback_s`` window and dedupes by event id, so a failed poll is
re-read by the next one and costs nothing. Any exception was logged at ERROR
regardless, so a slow afternoon on the vendor cloud produced a run of ERROR
lines that read as a fault.

Measured on the live box 2026-09-21: 30 failures in 134 minutes, about 2% of
polls, against a 30 s request timeout - the cloud simply took longer than that,
and a bare request to it was already taking 3-4 s. Streaming was untouched
throughout.

The same log has now been read as a fault twice (see
test_cloud_error_log_names_the_exception for 2026-09-17), which is what a
severity that cannot distinguish "slow" from "broken" buys. The threshold here
is not a taste call: escalate once the run of failures has covered the lookback
window, because that is the point at which an event can actually be missed
rather than re-read.
"""

import asyncio
import logging
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc

_ME = "0a1b2c3d4e5f60718293a4b5c6d7e8f9"


class _Client:
    device_id = _ME
    _aidot_v32_base = "https://api.invalid/v32/api/ipc"
    _motion_interval = 30.0
    _motion_lookback_s = 600

    def _aidot_headers(self):
        return {}

    def _is_auth_error(self, body):
        return False

    async def _async_refresh_auth_token(self, data=None):
        return False

    async_get_cloud_recordings = cc.CameraMixin.async_get_cloud_recordings


def _drive(client, exc, times=1):
    """Call the method ``times`` times with a POST that raises ``exc``."""
    import aiohttp

    class _Session:
        def post(self, *a, **k):
            raise exc

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    real = aiohttp.ClientSession
    aiohttp.ClientSession = lambda *a, **k: _Session()
    try:
        out = None
        for _ in range(times):
            out = asyncio.run(client.async_get_cloud_recordings(0, 1))
        return out
    finally:
        aiohttp.ClientSession = real


def test_one_transient_timeout_does_not_log_an_error(caplog):
    client = _Client()
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        out = _drive(client, TimeoutError())
    assert out == []
    errors = [r for r in caplog.records if r.levelno >= logging.ERROR]
    assert not errors, f"a single cloud timeout paged: {[r.message for r in errors]}"
    # It still has to be visible, and still has to name the type.
    assert "TimeoutError" in caplog.text


def test_a_dns_blip_does_not_log_an_error(caplog):
    """The 2026-09-21 page was a ClientConnectorDNSError."""
    import aiohttp

    try:
        exc = aiohttp.ClientConnectorDNSError(
            connection_key=None, os_error=OSError("Name or service not known")
        )
    except Exception:  # older aiohttp without the DNS subclass
        exc = aiohttp.ClientOSError("Name or service not known")

    client = _Client()
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        _drive(client, exc)
    assert not [r for r in caplog.records if r.levelno >= logging.ERROR]


def test_a_run_that_covers_the_lookback_window_does_log_an_error(caplog):
    """20 failures x 30 s = the 600 s window: an event can now be missed."""
    client = _Client()
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        _drive(client, TimeoutError(), times=20)
    errors = [r for r in caplog.records if r.levelno >= logging.ERROR]
    assert errors, "a run of failures covering the lookback must still page"
    assert "TimeoutError" in errors[-1].getMessage()


def test_an_unexpected_exception_pages_immediately(caplog):
    """Only the known transient network classes are demoted. Anything else is
    a surprise and keeps its ERROR on the first occurrence."""
    client = _Client()
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        _drive(client, ValueError("boom"))
    errors = [r for r in caplog.records if r.levelno >= logging.ERROR]
    assert errors, "an unexpected exception must page on the first occurrence"
    assert "ValueError" in errors[-1].getMessage()


def test_a_success_clears_the_streak(caplog):
    """Otherwise an hour of blips would eventually page on an unrelated one."""
    client = _Client()
    _drive(client, TimeoutError(), times=19)

    import aiohttp

    class _Resp:
        async def json(self, content_type=None):
            return {"code": 200, "data": {"list": [], "total": 0}}

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    class _OkSession:
        def post(self, *a, **k):
            return _Resp()

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    real = aiohttp.ClientSession
    aiohttp.ClientSession = lambda *a, **k: _OkSession()
    try:
        asyncio.run(client.async_get_cloud_recordings(0, 1))
    finally:
        aiohttp.ClientSession = real

    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.client"):
        _drive(client, TimeoutError())
    assert not [r for r in caplog.records if r.levelno >= logging.ERROR], (
        "the streak survived a successful poll"
    )
