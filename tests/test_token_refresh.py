"""Unit tests for camera-HTTP token-refresh recovery (21026 'Please login again').

Locks down the auth-error detector and the refresh-then-retry hook that lets the
smarthome calls (motion events, MQTT URL) recover from a stale access token
instead of failing silently. No network / camera needed.
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.device_client import DeviceClient


def test_is_auth_error_detects_21026_and_login_again():
    assert DeviceClient._is_auth_error({"code": 21026}) is True
    assert DeviceClient._is_auth_error({"code": "21026"}) is True
    assert DeviceClient._is_auth_error({"code": 21027}) is True
    assert DeviceClient._is_auth_error({"desc": "Please login again."}) is True
    # Not auth errors:
    assert DeviceClient._is_auth_error({"code": 200}) is False
    assert DeviceClient._is_auth_error({"code": 200, "desc": "Success."}) is False
    assert DeviceClient._is_auth_error(None) is False
    assert DeviceClient._is_auth_error([]) is False


def _make_dc():
    dev = {"id": "devX", "modelId": "LK.IPC.A001513", "aesKey": [None]}
    return DeviceClient(dev, {"id": "u1", "accessToken": "stale"})


def test_refresh_auth_token_invokes_cb_and_clears_caches():
    dc = _make_dc()
    dc._smarthome_auth = {"mqttPassword": "old"}
    dc._mqtt_url = "wss://old"
    calls = []

    async def _cb():
        calls.append(1)
        return True

    dc.set_token_refresh_cb(_cb)
    ok = asyncio.run(dc._async_refresh_auth_token())
    assert ok is True
    assert calls == [1]
    # stale caches must be dropped so the retry re-fetches with the new token
    assert dc._smarthome_auth is None
    assert dc._mqtt_url is None


def test_refresh_auth_token_no_cb_returns_false():
    dc = _make_dc()  # no cb set
    assert asyncio.run(dc._async_refresh_auth_token()) is False


def test_refresh_auth_token_swallows_cb_failure():
    dc = _make_dc()

    async def _cb():
        raise RuntimeError("network down")

    dc.set_token_refresh_cb(_cb)
    assert asyncio.run(dc._async_refresh_auth_token()) is False


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
