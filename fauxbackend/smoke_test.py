#!/usr/bin/env python3
"""Smoke-test the faux backend: hit every route and print method/url/status/body.

Verifies the redirect-critical responses point at OUR infra (HA broker, our coturn)
and that camera-originated endpoints accept. No network/MQTT needed (TestClient).

  pip install fastapi httpx
  python smoke_test.py
"""
import json
import os
import sys

os.environ.setdefault("FAUX_HA_HOST", "192.168.1.162")
os.environ.setdefault("FAUX_TURN_HOST", "192.168.1.162")

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from fastapi.testclient import TestClient
import app as fauxapp

client = TestClient(fauxapp.app)

CASES = [
    ("POST", "/v17/users/loginWithFreeVerification", {}),
    ("POST", "/v17/users/refreshToken", {"refreshToken": "x"}),
    ("GET", "/commons/userConfig", None),
    ("GET", "/commonController/getServerUrlConfig", None),
    ("GET", "/user/getUser", None),
    ("GET", "/v17/houses", None),
    ("GET", "/v17/devices?houseId=h1", None),
    ("GET", "/v17/products/p1,p2", None),
    ("POST", "/v21/devices/batchGetDeviceUserInfo", {"deviceIds": ["d1"]}),
    ("GET", "/v29/api/webrtc/iceConfig?forceRefresh=0", None),
    ("GET", "/api/ipc/liveStream/liveStreamParam", None),
    ("POST", "/api/ipc/devices/d1/lowPowerActiveState", {}),
    ("POST", "/fileController/uploadImage", b"\xff\xd8jpegbytes"),
    ("POST", "/logs/statistics", {"foo": 1}),
    ("POST", "/v32/api/ipc/playback/eventRecordingList", {"deviceId": "d1"}),
    ("POST", "/v32/api/ipc/playback/getEventVideoUrl", {"deviceId": "d1", "eventId": "e1"}),
    ("GET", "/some/unmodeled/path", None),  # catch-all
]


def run():
    fails = 0
    for method, url, body in CASES:
        kw = {}
        if body is not None:
            kw = {"data": body} if isinstance(body, bytes) else {"json": body}
        r = client.request(method, url, **kw)
        try:
            j = r.json()
        except Exception:
            j = r.text
        ok = r.status_code == 200
        fails += 0 if ok else 1
        print(f"{'OK ' if ok else 'ERR'} {method:4} {url:55} {r.status_code}  {json.dumps(j)[:90]}")

    # redirect-critical assertions
    su = client.get("/commonController/getServerUrlConfig").json()
    ice = client.get("/v29/api/webrtc/iceConfig").json()
    uc = client.get("/commons/userConfig").json()
    print("\n--- redirect assertions ---")
    checks = [
        ("getServerUrlConfig.mqttServerUrl points at HA broker",
         su["data"]["mqttServerUrl"].startswith("wss://192.168.1.162:8443")),
        ("iceConfig advertises OUR coturn (not 3.230.182.123)",
         any("192.168.1.162" in u for s in ice["data"]["iceServers"] for u in s["urls"])
         and not any("3.230.182.123" in u for s in ice["data"]["iceServers"] for u in s["urls"])),
        ("userConfig returns an mqttPassword", bool(uc.get("mqttPassword"))),
        ("login returns accessToken", bool(client.post("/v17/users/loginWithFreeVerification", json={}).json().get("accessToken"))),
    ]
    for name, passed in checks:
        print(f"  {'PASS' if passed else 'FAIL'}  {name}")
        fails += 0 if passed else 1

    print(f"\n{'ALL GOOD' if fails == 0 else str(fails)+' FAILURES'}")
    return 1 if fails else 0


if __name__ == "__main__":
    raise SystemExit(run())
