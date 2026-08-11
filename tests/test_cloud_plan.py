"""The cloud recording subscription, which decides whether an empty event list
means "nothing happened" or "you are not paying for this any more".

Measured 2026-08-11 on all seven cameras of the reference fleet: an active
plan answers `{"code": 200, "data": {"packageName": "AI Protection ",
"subscribeStatus": 1, "endTime": 1787781600000, ...}}` from
`GET /recordPlanController/getPackageInfoByDevId?deviceId=`.

The method returns None rather than {} on failure because the caller's question
is "is there a plan", and an empty dict answers it wrong in a truthiness test.
"""
import asyncio
import os
import sys
from typing import Any

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc

_ME = "7c89a5c1b36346e5b5c2f77f8554ad63"

_PLAN = {
    "packageName": "AI Protection ",
    "packageType": 1,
    "subscribeStatus": 1,
    "changeStatus": 0,
    "startTime": 1785099600000,
    "endTime": 1787781600000,
    "expiredDays": 15,
    "videoLength": 60,
}


class _Client:
    """Just enough of the camera client to drive the method under test."""

    def __init__(self, reply: Any):
        self.device_id = _ME
        self._reply = reply
        self.refreshed = False
        self.last_params = None
        self.last_url = None

    _aidot_v32_base = "https://api.invalid/v32/api/ipc"

    def _aidot_headers(self):
        return {}

    def _is_auth_error(self, body):
        return isinstance(body, dict) and body.get("code") == 401

    async def _async_refresh_auth_token(self):
        self.refreshed = True
        return True

    async_get_cloud_plan = cc.CameraMixin.async_get_cloud_plan


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
        def get(self, *a, **k):
            client.last_params = k.get("params")
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
        return client, asyncio.run(client.async_get_cloud_plan())
    finally:
        aiohttp.ClientSession = real


def test_an_active_plan_comes_back_as_its_data_object():
    client, out = _run({"code": 200, "desc": None, "data": _PLAN})
    assert out["subscribeStatus"] == 1
    assert out["endTime"] == 1787781600000
    assert client.last_params == {"deviceId": _ME}


def test_a_failed_envelope_is_none_not_an_empty_dict():
    # The caller asks "is there a plan" with a truthiness test, and {} answers
    # that wrong.
    _, out = _run({"code": 500, "desc": "boom", "data": None})
    assert out is None


def test_a_missing_data_object_is_none():
    _, out = _run({"code": 200, "data": None})
    assert out is None


def test_an_unexpected_shape_does_not_raise():
    # A bare list, a string, anything - a listing helper must never take down
    # the browser it feeds. The bare-list case is wrapped as a single-item
    # sequence ([[]]) because _run's ``reply`` list means "replies served in
    # order" - an unwrapped [] would mean "zero replies queued", not "the
    # server's body was an empty list".
    for reply in ([[]], "nope", None):
        _, out = _run(reply)
        assert out is None


def test_a_401_is_retried_once_after_a_token_refresh():
    # Delete the retry line from the method and this test must fail. Before
    # this existed, deleting it changed nothing.
    client, out = _run([{"code": 401, "desc": "token expired"},
                        {"code": 200, "data": _PLAN}])
    assert client.refreshed is True
    assert out is not None and out["subscribeStatus"] == 1


def test_a_request_that_raises_is_none_rather_than_an_exception():
    # The isinstance() guard catches malformed BODIES; this is the transport
    # failing, which only the except block can catch.
    _, out = _run({"code": 200, "data": _PLAN}, raises=True)
    assert out is None


def test_an_empty_data_dict_is_none():
    # "code 200 and nothing in it" is not a plan. Dropping the `and data`
    # clause would otherwise ship unnoticed.
    _, out = _run({"code": 200, "data": {}})
    assert out is None


def test_the_request_goes_to_the_plan_endpoint():
    # The path is built by concatenation; a dropped or doubled slash would
    # otherwise pass silently.
    client, _ = _run({"code": 200, "data": _PLAN})
    assert client.last_url.endswith(
        "/recordPlanController/getPackageInfoByDevId")
    assert "//recordPlanController" not in client.last_url
