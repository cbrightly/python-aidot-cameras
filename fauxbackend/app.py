#!/usr/bin/env python3
"""Faux AiDot cloud HTTPS API (FastAPI).

Implements the arnoo endpoints the bridge and cameras call, returning responses
that point MQTT at HA's broker and ICE config at our own coturn. TLS-terminate
this with the *.arnoo.com cert (certs/gen_certs.sh) on :443 for every arnoo API
host (prod-{r}-api / {r}-smarthome / us-storage / prod-us-app-log).

SCAFFOLD: response shapes are modeled from the captured inventory in the
blackhole-feasibility memory + decrypted intercept traffic. Fields still needing
validation against a real capture are marked `TODO-SHAPE`. Nothing here is
authoritative until checked against a live camera/bridge.

Run (dev):  uvicorn app:app --host 0.0.0.0 --port 8080
Behind a TLS terminator (nginx/caddy/uvicorn --ssl-*) presenting the arnoo cert.

Config via env (see config.example.env):
  FAUX_MQTT_WSS   wss URL the bridge/camera should use   (default wss://<HA>:8443/mqtt)
  FAUX_MQTT_USER / FAUX_MQTT_PASS  creds our broker accepts
  FAUX_TURN_HOST  our coturn host/ip                     (default 192.168.1.162)
  FAUX_TURN_USER / FAUX_TURN_PASS  long-term coturn creds
  FAUX_DEVICES    path to device-list JSON captured once from the real cloud
"""
import json
import os
import time

from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse

app = FastAPI(title="faux-aidot", docs_url=None, redoc_url=None)

HA = os.environ.get("FAUX_HA_HOST", "192.168.1.162")
MQTT_WSS = os.environ.get("FAUX_MQTT_WSS", f"wss://{HA}:8443/mqtt")
MQTT_USER = os.environ.get("FAUX_MQTT_USER", "aidot")
MQTT_PASS = os.environ.get("FAUX_MQTT_PASS", "aidot")
TURN_HOST = os.environ.get("FAUX_TURN_HOST", HA)
TURN_USER = os.environ.get("FAUX_TURN_USER", "aidot")
TURN_PASS = os.environ.get("FAUX_TURN_PASS", "aidot")
DEVICES_PATH = os.environ.get("FAUX_DEVICES", os.path.join(os.path.dirname(__file__), "devices.json"))

# A constant token; nothing validates it offline (camera/bridge don't verify).
_TOKEN = "faux-access-token"
_USERID = os.environ.get("FAUX_USERID", "1000000000000000001")


def _now_ms() -> int:
    return int(time.time() * 1000)


def _devices() -> dict:
    """Device list captured once from the real cloud (freeze it). TODO-SHAPE: keep
    the exact fields the bridge reads (id, model_id/productId, etc.)."""
    try:
        with open(DEVICES_PATH) as f:
            return json.load(f)
    except FileNotFoundError:
        return {"houses": [], "devices": [], "products": []}


# ── auth ──────────────────────────────────────────────────────────────────
@app.post("/v17/users/loginWithFreeVerification")
async def login(req: Request):
    return {"accessToken": _TOKEN, "refreshToken": "faux-refresh",
            "expiresIn": 31536000, "id": _USERID}


@app.post("/v17/users/refreshToken")
async def refresh(req: Request):
    return {"accessToken": _TOKEN, "refreshToken": "faux-refresh", "expiresIn": 31536000}


@app.get("/commons/userConfig")
async def user_config():
    # the bridge reads MQTT creds here
    return {"mqttClientId": f"app-{_USERID}", "mqttPassword": MQTT_PASS,
            "mqtt": {"clientId": f"app-{_USERID}", "password": MQTT_PASS}}


# ── leedarson smarthome: broker discovery + p2p + stream provisioning ──────
@app.get("/commonController/getServerUrlConfig")
async def server_url_config():
    # THE redirect: tell the client to use OUR broker
    return {"data": {"mqttServerUrl": MQTT_WSS, "mqttUser": MQTT_USER,
                     "mqttPassword": MQTT_PASS, "userId": _USERID, "ip": HA}}


@app.get("/user/getUser")
async def get_user():
    return {"data": {"mqttUser": MQTT_USER, "mqttPassword": MQTT_PASS, "userId": _USERID}}


# ── device / home listing ──────────────────────────────────────────────────
# NOTE: these return BARE LISTS, not {"data": [...]} — the bridge does
# `for house in await async_get_houses()` directly on the JSON (client.py:442).
@app.get("/v17/houses")
async def houses():
    return _devices().get("houses", [])


@app.get("/v17/devices")
async def devices(houseId: str = ""):
    return _devices().get("devices", [])


@app.get("/v17/products/{product_ids}")
async def products(product_ids: str):
    return _devices().get("products", [])


@app.post("/v21/devices/batchGetDeviceUserInfo")
async def batch_device_user_info(req: Request):
    # WebRTC (liveType=2) fleet doesn't need TUTK p2pId; return empty-ish. TODO-SHAPE.
    return {"data": []}


# ── ICE / TURN config — point at OUR coturn (Case A redirect) ──────────────
def _ice_servers():
    return [
        {"urls": [f"stun:{TURN_HOST}:3478"]},
        {"urls": [f"turn:{TURN_HOST}:3478?transport=udp"],
         "username": TURN_USER, "credential": TURN_PASS},
    ]


@app.get("/v29/api/webrtc/iceConfig")
async def ice_config():
    return {"data": {"iceServers": _ice_servers()}}


# ── live-stream provisioning / battery wake ────────────────────────────────
@app.get("/api/ipc/liveStream/liveStreamParam")
async def live_stream_param(req: Request):
    return {"code": 0, "data": {}}  # TODO-SHAPE: session tokens if the bridge needs them


@app.post("/api/ipc/devices/{device_id}/lowPowerActiveState")
async def low_power_active(device_id: str, req: Request):
    return {"code": 0}


# ── camera-originated calls observed via the intercept (just accept) ───────
@app.post("/fileController/uploadImage")
async def upload_image(req: Request):
    # us-storage.arnoo.com — camera uploads snapshots. Accept + drop (or save).
    body = await req.body()
    return JSONResponse({"code": 0, "data": {"url": ""}})


@app.post("/logs/statistics")
async def app_log_statistics(req: Request):
    # prod-us-app-log.arnoo.com — telemetry. Swallow it.
    return {"code": 0}


# ── playback (stub) ────────────────────────────────────────────────────────
@app.post("/v32/api/ipc/playback/eventRecordingList")
async def playback_list(req: Request):
    return {"data": []}


@app.post("/v32/api/ipc/playback/getEventVideoUrl")
async def playback_url(req: Request):
    return {"data": {"type": "event", "url": "", "mimeType": "video/mp4"}}


# ── catch-all: log anything we haven't modeled yet (recon aid) ─────────────
@app.api_route("/{path:path}", methods=["GET", "POST", "PUT", "DELETE"])
async def catch_all(path: str, req: Request):
    print(f"[faux-unhandled] {req.method} {req.url.path} host={req.headers.get('host')}")
    return JSONResponse({"code": 0, "data": {}})
