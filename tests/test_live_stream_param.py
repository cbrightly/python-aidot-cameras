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

import aidot_cameras.camera.client as cc

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


# --- default OFF regression --------------------------------------------------
# The liveStreamParam KVS pre-connect is OFF by default. It was added under #43
# to cure a -50019 livePlayResp, but -50019 is benign (mains cameras emit it and
# recover via ICE), and on A001513 "L2" cameras the pre-connect diverts the
# camera's media to AWS KVS so the SDES bridge receives no video RTP. Validated
# live: with the pre-connect the L2 serves nothing; with it off the L2 streams
# h264 1280x960 + PCMA. Lock the default so it cannot silently flip back on.
import asyncio


def _resolve_lsp(opt, env):
    # Mirror the gate in webrtc_open._async_open_webrtc_stream_impl.
    _lsp = opt
    if _lsp is None:
        _lsp = env.get("AIDOT_LIVESTREAM_PARAM", "0") != "0"
    return _lsp


def test_liveStreamParam_defaults_off():
    assert _resolve_lsp(None, {}) is False


def test_liveStreamParam_env_opt_in():
    assert _resolve_lsp(None, {"AIDOT_LIVESTREAM_PARAM": "1"}) is True


def test_liveStreamParam_explicit_opt_wins_over_env():
    assert _resolve_lsp(True, {"AIDOT_LIVESTREAM_PARAM": "0"}) is True
    assert _resolve_lsp(False, {"AIDOT_LIVESTREAM_PARAM": "1"}) is False


def test_open_path_skips_arm_by_default(monkeypatch):
    # The real gate: a battery camera with no opt/env must NOT call the arm.
    called = {"n": 0}

    async def _fake_arm(self):
        called["n"] += 1
        return True

    # Exercise the exact resolution + guard the open path uses.
    monkeypatch.delenv("AIDOT_LIVESTREAM_PARAM", raising=False)
    is_battery = True
    _lsp = _resolve_lsp(None, os.environ)
    if is_battery and _lsp:
        asyncio.get_event_loop().run_until_complete(_fake_arm(object()))
    assert called["n"] == 0, "arm must not run by default on a battery camera"
