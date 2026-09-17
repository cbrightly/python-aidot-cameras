"""A cloud-call failure must name its exception, even a timeout.

A soak monitor paged on 2026-09-17 with an UNCLASSIFIED library error: four
lines reading `async_get_cloud_recordings failed for <id>: ` with nothing after
the colon. The cause was benign - a transient box-side DNS/network blip that
also timed out Frigate and ESPHome in the same window, caught and returned as
an empty list. But the log said nothing usable, because the handler formatted
the exception with `%s`, and `str(asyncio.TimeoutError())` is the empty string.

An error line that cannot be classified is worse than one that can: it turns
every transient cloud timeout into a same-looking page. The fix is to format
the exception so its TYPE always shows. These tests pin that for the cloud-HTTP
error/warning logs where a bare timeout is a realistic path.
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

    def _aidot_headers(self):
        return {}

    def _is_auth_error(self, body):
        return False

    async def _async_refresh_auth_token(self, data=None):
        return False

    async_get_cloud_recordings = cc.CameraMixin.async_get_cloud_recordings


def _run_timeout(caplog):
    """Drive the method with a session whose POST raises a bare TimeoutError."""
    client = _Client()

    class _Session:
        def post(self, *a, **k):
            raise TimeoutError()  # str() == "" - the whole point

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    import aiohttp

    real = aiohttp.ClientSession
    aiohttp.ClientSession = lambda *a, **k: _Session()
    try:
        with caplog.at_level(logging.ERROR, logger="aidot_cameras.camera.client"):
            out = asyncio.run(client.async_get_cloud_recordings(0, 1))
        return out, caplog.text
    finally:
        aiohttp.ClientSession = real


def test_a_timeout_is_still_caught_and_returns_empty(caplog):
    out, _ = _run_timeout(caplog)
    assert out == []


def test_the_error_line_names_the_exception_type_not_an_empty_string(caplog):
    _, text = _run_timeout(caplog)
    assert "async_get_cloud_recordings failed" in text
    # The regression: the line used to end at "...: " with nothing after.
    assert "TimeoutError" in text, (
        "the error log must name the exception type; a bare asyncio.TimeoutError "
        "has an empty str() and produced an unclassifiable page"
    )
