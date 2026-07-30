"""Unit tests for the liveStreamParam provision-request builder and its gate.

The builder's request shape is exact: a JSON ARRAY body of device ids (an object
body returns HTTP 500) plus an ``owner`` header. This locks that format in, for
the day a camera is found that genuinely needs the call.

The gate itself is now always closed (_resolve_live_stream_param). The
pre-connect provisions a battery camera's session toward AWS KVS and the camera
sends its media there instead of to this library, so the live view negotiates and
then serves no video at all - and battery cameras are the only ones it was ever
made for. It was added to cure a -50019 ("not ready") livePlayResp, which is
benign: mains cameras emit it too and recover via ICE.

No network: everything below builds from a bare instance.
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


# --- the gate ----------------------------------------------------------------
# These call the REAL resolver the open path calls. The previous version of this
# file re-implemented the gate as a local `_resolve_lsp` helper and asserted
# against that copy, so the shipped gate was never covered and the copy was free
# to drift from it - which is how a flag that must stay off stayed "locked" by a
# test that could not see it.

_GATE = next(v for v in vars(cc).values()
             if isinstance(v, type) and "_resolve_live_stream_param" in v.__dict__)


class _Info:
    def __init__(self, model_id):
        self.model_id = model_id


def _cam(model_id="LK.IPC.A001513", **attrs):
    c = _GATE.__new__(_GATE)
    c.info = _Info(model_id)
    c.device_id = "DEV1234"
    for k, v in attrs.items():
        setattr(c, k, v)
    return c


def test_gate_closed_by_default(monkeypatch):
    monkeypatch.delenv("AIDOT_LIVESTREAM_PARAM", raising=False)
    assert _cam()._resolve_live_stream_param() is False


def test_gate_stays_closed_for_the_env(monkeypatch):
    monkeypatch.setenv("AIDOT_LIVESTREAM_PARAM", "1")
    assert _cam()._resolve_live_stream_param() is False


def test_gate_stays_closed_for_an_explicit_opt(monkeypatch):
    # start_keepalive(live_stream_param=True) - what a consumer that surfaces the
    # setting passes. It must not be able to re-break a battery camera's video.
    monkeypatch.delenv("AIDOT_LIVESTREAM_PARAM", raising=False)
    c = _cam(_live_stream_param_opt=True)
    assert c._resolve_live_stream_param() is False


def test_gate_closed_for_every_battery_model(monkeypatch):
    monkeypatch.setenv("AIDOT_LIVESTREAM_PARAM", "1")
    for _m in ("LK.IPC.A001513", "LK.IPC.A001513-1", "LK.IPC.A001108",
               "LK.IPC.A001360"):
        c = _cam(_m, _live_stream_param_opt=True)
        assert c._resolve_live_stream_param() is False, _m


def test_gate_closed_for_a_mains_camera(monkeypatch):
    # The call was never made for a mains camera; the gate agrees.
    monkeypatch.setenv("AIDOT_LIVESTREAM_PARAM", "1")
    c = _cam("LK.IPC.A000088", _live_stream_param_opt=True)
    assert c._resolve_live_stream_param() is False


def test_ignored_request_is_warned_once(caplog):
    # A consumer that set the option gets told why it did nothing, so a live view
    # that serves no video isn't debugged blind - but only once per camera, not
    # on every reconnect.
    c = _cam(_live_stream_param_opt=True)
    with caplog.at_level("WARNING"):
        for _ in range(3):
            c._resolve_live_stream_param()
    _warnings = [r for r in caplog.records if "liveStreamParam" in r.getMessage()]
    assert len(_warnings) == 1
    assert "DEV1234" in _warnings[0].getMessage()


def test_no_warning_when_nobody_asked(monkeypatch, caplog):
    monkeypatch.delenv("AIDOT_LIVESTREAM_PARAM", raising=False)
    with caplog.at_level("WARNING"):
        _cam()._resolve_live_stream_param()
    assert not [r for r in caplog.records if "liveStreamParam" in r.getMessage()]
