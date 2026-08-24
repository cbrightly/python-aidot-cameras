"""Counting a window's cloud events in one request instead of one per ten.

The listing endpoint reports the window's true ``total`` alongside whatever
page it serves, and that total is correct even on a one-item page - measured
2026-08-11 across seven cameras. `async_count_cloud_recordings` asks for
`pageSize: 1` and reads only `data.total`, so a caller titling a folder with
an event count does not have to page the whole window to get it.
"""
import asyncio
import os
import sys
from typing import Any

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc

_ME = "0a1b2c3d4e5f60718293a4b5c6d7e8f9"


class _Client:
    """Just enough of the camera client to drive the method under test."""

    def __init__(self, reply: Any):
        self.device_id = _ME
        self._reply = reply
        self.refreshed = False
        self.last_json = None
        self.last_url = None

    _aidot_v32_base = "https://api.invalid/v32/api/ipc"

    def _aidot_headers(self):
        return {}

    def _is_auth_error(self, body):
        return isinstance(body, dict) and body.get("code") == 401

    async def _async_refresh_auth_token(self):
        self.refreshed = True
        return True

    async_count_cloud_recordings = cc.CameraMixin.async_count_cloud_recordings


def _run(reply, raises=False):
    """Drive the method under test.

    ``reply`` may be a single reply or a LIST of replies served in order, which
    is what a 401-then-200 refresh sequence needs - the single-reply harness
    could not express it, so the retry went untested.
    """
    replies = list(reply) if isinstance(reply, list) else [reply]
    client = _Client(replies[0])
    client._queue = replies

    class _Resp:
        async def json(self, content_type=None):
            return (client._queue.pop(0) if len(client._queue) > 1
                    else client._queue[0])

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    class _Session:
        def post(self, *a, **k):
            client.last_json = k.get("json")
            client.last_url = a[0] if a else k.get("url")
            if raises:
                raise RuntimeError("connection reset")
            return _Resp()

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    import aiohttp
    real = aiohttp.ClientSession
    aiohttp.ClientSession = lambda *a, **k: _Session()
    try:
        return client, asyncio.run(client.async_count_cloud_recordings(0, 1))
    finally:
        aiohttp.ClientSession = real


def test_a_populated_window_returns_its_true_total():
    _, out = _run(
        {"code": 200, "data": {"total": 1517, "list": [{"eventUuid": "x"}]}}
    )
    assert out == 1517


def test_an_empty_window_returns_zero_not_none():
    # An empty window is a real answer and must not come back as None.
    _, out = _run({"code": 200, "data": {"total": 0, "list": []}})
    assert out == 0


def test_a_failed_envelope_is_none():
    _, out = _run({"code": 500, "desc": "boom", "data": None})
    assert out is None


def test_a_missing_total_is_none():
    _, out = _run({"code": 200, "data": {"list": []}})
    assert out is None


def test_the_request_asks_for_page_size_one():
    # The entire point of this method is that it does not fetch a page of
    # data to count one.
    client, _ = _run({"code": 200, "data": {"total": 3, "list": []}})
    assert client.last_json["pageSize"] == 1


def test_an_unexpected_shape_does_not_raise():
    # The bare-list case is wrapped as a single-item sequence ([[]]) because
    # _run's ``reply`` list means "replies served in order" - an unwrapped []
    # would mean "zero replies queued", not "the server's body was an empty
    # list".
    for reply in ([[]], None):
        _, out = _run(reply)
        assert out is None


def test_a_401_is_retried_once_after_a_token_refresh():
    # Delete the retry line from the method and this test must fail. Before
    # this existed, deleting it changed nothing.
    client, out = _run([{"code": 401, "desc": "token expired"},
                        {"code": 200, "data": {"total": 42, "list": []}}])
    assert client.refreshed is True
    assert out == 42


def test_a_request_that_raises_is_none_rather_than_an_exception():
    # The isinstance() guard catches malformed BODIES; this is the transport
    # failing, which only the except block can catch.
    _, out = _run({"code": 200, "data": {"total": 3, "list": []}}, raises=True)
    assert out is None


def test_the_request_goes_to_the_listing_endpoint():
    # The path is built by concatenation; a dropped or doubled slash would
    # otherwise pass silently.
    client, _ = _run({"code": 200, "data": {"total": 3, "list": []}})
    assert client.last_url.endswith("/playback/eventRecordingList")
    assert "//playback" not in client.last_url
