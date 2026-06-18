# AiDot cloud endpoint surface (to fake)

Compiled from the codebase inventory (what the **bridge** calls) + the L3-intercept
capture of decrypted **camera**-originated traffic. `app.py` implements these.
Region `{r}` ∈ us/eu/jp.

## HTTPS — bridge-originated (`prod-{r}-api.arnoo.com`, `{r}-smarthome.arnoo.com`)

| Method | Path | Purpose | Faked in app.py |
|---|---|---|---|
| POST | `/v17/users/loginWithFreeVerification` | login → token | ✓ |
| POST | `/v17/users/refreshToken` | token refresh | ✓ |
| GET  | `/commons/userConfig` | MQTT creds | ✓ |
| GET  | `/commonController/getServerUrlConfig` | **MQTT broker URL** (the redirect) | ✓ |
| GET  | `/user/getUser` | mqtt user/pass | ✓ |
| GET  | `/v17/houses` | home list | ✓ (from devices.json) |
| GET  | `/v17/devices?houseId=` | device list | ✓ |
| GET  | `/v17/products/{ids}` | product meta | ✓ |
| POST | `/v21/devices/batchGetDeviceUserInfo` | TUTK p2pId (unused for WebRTC fleet) | ✓ stub |
| GET  | `/v29/api/webrtc/iceConfig` | **TURN creds** → our coturn | ✓ |
| GET  | `/api/ipc/liveStream/liveStreamParam` | stream provisioning | ✓ stub TODO-SHAPE |
| POST | `/api/ipc/devices/{id}/lowPowerActiveState` | battery wake | ✓ |
| POST | `/v32/api/ipc/playback/eventRecordingList` | recordings | ✓ stub |
| POST | `/v32/api/ipc/playback/getEventVideoUrl` | playback url | ✓ stub |

## HTTPS — camera-originated (decrypted via intercept 2026-06-18)

| Method | Host | Path | Purpose | Faked |
|---|---|---|---|---|
| POST | `us-storage.arnoo.com` | `/fileController/uploadImage` | snapshot/image upload | ✓ accept |
| POST | `prod-us-app-log.arnoo.com` | `/logs/statistics` | telemetry | ✓ swallow |

> The `catch_all` route in app.py logs any unmodeled `METHOD host path` so further
> captures auto-extend this table. Re-run the intercept during camera activity
> (motion/snapshot/reboot) to flush out OTA, time-sync, push, etc.

## MQTT (HA Mosquitto) — `{r}-mqtt.arnoo.com:8443` wss `/mqtt`

Transport for commands, attrs, and signaling. Cloud-mediated bits handled by
`mqtt_responder.py`:

| Topic | Dir | Handled |
|---|---|---|
| `iot/v1/s/{uid}/IPC/getIceConfigReq` | in | ✓ → resp with our coturn |
| `iot/v1/c/{uid}/IPC/getIceConfigResp` | out | ✓ |
| `iot/v1/s/{uid}/IPCAM/lowPowerActiveStateReq` | in | ✓ ACK |
| `iot/v1/s|c/{uid}/IPC/webrtcReq/Resp`, `iceCandidateReq` | p2p | broker relay (no responder) |

## TURN/STUN

Real: `3.230.182.123:3478/5349`. Ours: coturn (`coturn/turnserver.conf`).

## TLS note

Cameras accept self-signed certs (do not validate CA) → `certs/gen_certs.sh` cert
works for every host above. Terminate all arnoo `:443` + the wss `:8443` with it.
