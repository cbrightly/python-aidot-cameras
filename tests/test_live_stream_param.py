"""Unit tests for the liveStreamParam provision-request builder.

Battery cameras (e.g. L2_170, A001513) must have their live-stream session
provisioned by the cloud before MQTT signaling, or they reject livePlayReq with
-50019 and never stream. The request shape is exact: a JSON ARRAY body of device
ids (an object body returns HTTP 500) plus an ``owner`` header. This locks that
format in. No network: builds the request from a bare instance.
"""
import json
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot.camera.client as cc

# The camera mixin class that defines the builder (avoid hard-coding the name).
_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_live_stream_param_request" in v.__dict__)


def _stub(**over):
    cam = _CAM.__new__(_CAM)
    cam._user_info = over.get("user_info", {"accessToken": "TKN", "id": "owner-123"})
    cam._region = "us"  # _smarthome_base is a property derived from _region
    cam.device_id = over.get("device_id", "DEV1234")
    cam.user_id = "U1"
    return cam


def test_url_is_livestreamparam_endpoint():
    url, _, _ = _stub()._live_stream_param_request()
    assert url.endswith("/api/ipc/liveStream/liveStreamParam")
    assert url.startswith("https://")


def test_body_is_json_array_of_device_id():
    # MUST be a JSON array - an object body ({"deviceId": id}) returns HTTP 500.
    _, _, body = _stub()._live_stream_param_request()
    parsed = json.loads(body)
    assert isinstance(parsed, list)
    assert parsed == ["DEV1234"]


def test_owner_header_required():
    # The endpoint needs owner (like the wake endpoint); falls back across keys.
    _, headers, _ = _stub()._live_stream_param_request()
    assert headers.get("owner") == "owner-123"


def test_owner_falls_back_to_user_id():
    cam = _stub(user_info={"accessToken": "T"})  # no id/owner/userId
    _, headers, _ = cam._live_stream_param_request()
    assert headers.get("owner") == "U1"


def test_auth_headers_carried():
    _, headers, _ = _stub()._live_stream_param_request()
    assert headers.get("token") == "TKN"
    assert headers.get("terminal") == "app"


if __name__ == "__main__":
    import traceback
    _fail = 0
    for _k, _v in sorted(globals().items()):
        if _k.startswith("test_"):
            try:
                _v()
                print(f"PASS {_k}")
            except Exception:
                _fail += 1
                print(f"FAIL {_k}")
                traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
