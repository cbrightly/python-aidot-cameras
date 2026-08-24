"""The recording list the vendor app actually asks for.

Every validation run has reported `recordings=PASS ... listing returned 0
events` for every camera on the fleet. A capture of the app from 2026-04-05
shows why that is not the whole story: the app's event page does not use the
range query this library implements, it posts

    /api/ipc/playback/getRecentEventRecordingList  {"deviceIds": [...], "total": 3}

and gets real events back - including three for an A000088, one of the models
whose recordings this project had written down as unreachable.

Two things about that endpoint break the handling every other call in the client
uses, and both are what these tests exist to hold:

  * the success response is a BARE JSON ARRAY, not the `{"code": ..., "data":
    ...}` envelope. Code that reaches for `.get("code")` sees a list and either
    raises or silently reports failure;
  * the reply is not per-device. The app asks for a whole house at once, so a
    caller that returns it unfiltered attributes other cameras' events to this
    one.
"""
import asyncio
import os
import sys
from typing import Any

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc

_ME = "0a1b2c3d4e5f60718293a4b5c6d7e8f9"
_OTHER = "a9b8c7d6e5f40312213243546576879a"


def _event(device_id: str, uuid: str = "v1:614a4d90") -> dict:
    """One item in the shape the capture shows."""
    return {
        "eventUuid": uuid,
        "eventTime": 1775415056648,
        "deviceId": device_id,
        "deviceName": "M3 Pro v2",
        "eventCodeList": ["4"],
        "eventDescList": ["Person"],
        "picUrl": "https://example.invalid/x.jpg",
        "safetyItemList": None,
    }


class _Client:
    """Just enough of the camera client to drive the method under test."""

    def __init__(self, reply: Any):
        self.device_id = _ME
        self._reply = reply
        self.refreshed = False

    _aidot_v32_base = "https://api.invalid/v32/api/ipc"

    def _aidot_headers(self):
        return {}

    def _is_auth_error(self, body):
        return isinstance(body, dict) and body.get("code") == 401

    async def _async_refresh_auth_token(self):
        self.refreshed = True
        return True

    async_get_recent_recordings = cc.CameraMixin.async_get_recent_recordings


def _run(reply, **kw):
    client = _Client(reply)

    class _Resp:
        async def json(self, content_type=None):
            return client._reply

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    class _Session:
        def post(self, *a, **k):
            client.last_body = k.get("json")
            return _Resp()

        async def __aenter__(self):
            return self

        async def __aexit__(self, *a):
            return False

    import aiohttp
    real = aiohttp.ClientSession
    aiohttp.ClientSession = lambda *a, **k: _Session()
    try:
        return client, asyncio.run(client.async_get_recent_recordings(**kw))
    finally:
        aiohttp.ClientSession = real


def test_a_bare_array_is_a_success_not_a_failure():
    # The shape that would break `.get("code")` handling.
    _, out = _run([_event(_ME)])
    assert len(out) == 1
    assert out[0]["eventUuid"] == "v1:614a4d90"


def test_other_cameras_events_are_not_attributed_to_this_one():
    # The app asks for a whole house; the reply is not per-device.
    _, out = _run([_event(_ME), _event(_OTHER, "v1:other"), _event(_ME, "v1:mine2")])
    assert [e["eventUuid"] for e in out] == ["v1:614a4d90", "v1:mine2"]


def test_the_request_asks_for_a_count_not_a_page():
    client, _ = _run([], total=25)
    assert client.last_body == {"deviceIds": [_ME], "total": 25}


def test_a_total_below_one_is_clamped_rather_than_sent():
    client, _ = _run([], total=0)
    assert client.last_body["total"] == 1


def test_an_envelope_response_means_an_error_and_yields_nothing():
    # Success is a bare array, so a dict is the server complaining.
    _, out = _run({"code": 500, "msg": "nope"})
    assert out == []


def test_an_empty_array_is_an_empty_list_not_an_error():
    _, out = _run([])
    assert out == []


def test_a_malformed_reply_does_not_raise():
    # This runs behind a service call in Home Assistant; it must never throw.
    _, out = _run("not json at all")
    assert out == []


if __name__ == "__main__":
    import traceback
    _fns = [v for k, v in sorted(globals().items()) if k.startswith("test_")]
    _fail = 0
    for _fn in _fns:
        try:
            _fn()
            print(f"PASS {_fn.__name__}")
        except Exception:
            _fail += 1
            print(f"FAIL {_fn.__name__}")
            traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
