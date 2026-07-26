"""Unit tests for camera-HTTP token-refresh recovery (21026 'Please login again').

Locks down the auth-error detector and the refresh-then-retry hook that lets the
smarthome calls (motion events, MQTT URL) recover from a stale access token
instead of failing silently. No network / camera needed.
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.models.auth_model import UserInformation
from aidot.models.device_model import DeviceModel

from aidot_cameras.client import CameraClient
from aidot_cameras.device_client import CameraDeviceClient


def test_is_auth_error_detects_21026_and_login_again():
    assert CameraDeviceClient._is_auth_error({"code": 21026}) is True
    assert CameraDeviceClient._is_auth_error({"code": "21026"}) is True
    assert CameraDeviceClient._is_auth_error({"code": 21027}) is True
    assert CameraDeviceClient._is_auth_error({"desc": "Please login again."}) is True
    # Not auth errors:
    assert CameraDeviceClient._is_auth_error({"code": 200}) is False
    assert CameraDeviceClient._is_auth_error({"code": 200, "desc": "Success."}) is False
    assert CameraDeviceClient._is_auth_error(None) is False
    assert CameraDeviceClient._is_auth_error([]) is False


def _make_dc():
    # Upstream's constructor takes typed models; the camera layer still needs the
    # raw cloud records, so both are handed over exactly as CameraClient does.
    dev = {"id": "devX", "modelId": "LK.IPC.A001513", "aesKey": [None]}
    user = {"id": "u1", "accessToken": "stale"}
    return CameraDeviceClient(
        DeviceModel.from_json(data=dev),
        UserInformation.from_json(data=user),
        raw_device=dev,
        login_info=dict(user),
    )


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


def test_refreshed_token_reaches_camera_clients():
    """A successful refresh must land in the account-shared login_info dict.

    Upstream's CloudApi.refresh_token writes user_info.accessToken - a dataclass
    field - and fires the token-refreshed callback.  The camera layer does NOT
    read that field: every camera HTTP header is built from the login_info dict
    the client handed each camera client at construction time, and that dict
    only picks up a rotated token when the login_info property getter runs.  So
    the getter has to run inside the refresh callback, or a camera that hit a
    21026, called async_ensure_token() and retried would retry with exactly the
    token that had just expired.

    _token_fresh_cb is deliberately left UNSET here: the shipped consumers set a
    persist callback that happens to read login_info, which masks the gap.
    """
    # Built OUTSIDE a running loop on purpose: __init__'s proactive-refresh
    # scheduling and discovery both no-op without one, so this stays offline.
    client = CameraClient(None, country_code="US")
    client.user_info.accessToken = "stale"
    client.user_info.refreshToken = "rt"

    dev = {"id": "camX", "modelId": "LK.IPC.A001513", "aesKey": [None]}
    dc = CameraDeviceClient(
        DeviceModel.from_json(data=dev),
        client.user_info,
        raw_device=dev,
        # The SAME dict object the client mutates in place - what the real
        # dispatch seam passes (CameraClient.get_device_client).
        login_info=client.login_info,
    )
    assert dc._user_info is client.login_info, "camera must share the account dict"
    assert dc._user_info["accessToken"] == "stale"

    async def _fake_refresh():
        # Exactly what upstream's CloudApi.refresh_token does on success.
        client.user_info.accessToken = "fresh"
        client._on_token_refreshed()
        return {"accessToken": "fresh"}

    client._cloud_api.refresh_token = _fake_refresh

    async def _run():
        ok = await client.async_ensure_token()
        # Cancel the proactive refresh the callback rescheduled, while the loop
        # this test owns is still running.
        if client._refresh_task is not None:
            client._refresh_task.cancel()
        return ok

    assert asyncio.run(_run()) is True
    assert client._token_fresh_cb is None, "the gap must not be masked by a cb"
    assert dc._user_info["accessToken"] == "fresh"


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
