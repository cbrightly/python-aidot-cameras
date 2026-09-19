# Plan: standalone AiDot bridge container ("Option B")

Status: **later phase - not started.** Depends on the direct publisher from
[`DESIGN-direct-publish.md`](DESIGN-direct-publish.md) reaching phase A4, and on
1.0.0 shipping. Nothing here is scheduled until there is demand from users
outside Home Assistant.

## Goal

One container that makes AiDot cameras look like ordinary local cameras to
anything that speaks RTSP/WebRTC/HLS - Frigate, Scrypted (and through it
HomeKit/HKSV, Google Home, Alexa), Blue Iris, other NVRs - with the camera
controls available over MQTT (including HA MQTT discovery for users who run HA
without the custom integration). Modelled on docker-wyze-bridge.

This replaces the "write a Scrypted plugin" idea as the first non-HA step: one
artifact reaches every consumer, and it does not depend on a single-maintainer
SDK. A native Scrypted plugin stays possible later, reusing the same publisher
(RFC 4571 output) if HomeKit-specific needs appear (battery-aware prebuffer,
HKSV motion).

## Shape

```
 +-------------------- aidot-bridge container ---------------------+
 |  aidot-bridge (Python, python-aidot-cameras[webrtc])             |
 |    account login + persistent MQTT (one per account)             |
 |    per camera: on-demand session -> RtspPublisher --------+      |
 |    controls: MQTT command/state topics, HA discovery      |      |
 |    health: /healthz, per-camera status JSON               |      |
 |                                                           v      |
 |  go2rtc (upstream binary, pinned)  <- RTSP publish, 127.0.0.1    |
 |    exposes :8554 RTSP, :1984 WebRTC/API, :8555 WebRTC            |
 +------------------------------------------------------------------+
        |RTSP / WebRTC / HLS                 |MQTT
   Frigate, Scrypted, NVRs, browsers     broker (HA, Node-RED, ...)
```

go2rtc over MediaMTX: it is what HA and Frigate already embed, its RTSP publish
semantics are the ones A1 is built and tested against, and its consumer
counting drives on-demand start/stop.

## Work breakdown

| # | Item | Notes |
| --- | --- | --- |
| B1 | `aidot-bridge` entry point | Grows out of `aidot-go2rtc` (`__main__.py`). One process, all cameras, instead of one exec per camera. Config via env/YAML: credentials (reuse `aidot_cameras.credentials`, Fernet key outside the config dir), camera allow-list, go2rtc address. |
| B2 | On-demand sessions | Poll go2rtc `GET /api/streams` consumer counts (the library already has `Go2rtcClient.viewer_count`); start a session when a consumer appears, release after the existing idle policy; honour `AIDOT_MAX_CONCURRENT_STREAMS`. Battery cameras never prewarm. |
| B3 | Stream definitions | Bridge owns go2rtc config: creates `aidot_<id12>` streams with a fail-fast placeholder src (go2rtc cannot create an empty stream), publishes into them. |
| B4 | MQTT control plane | Topics per camera: `aidot/<id>/<control>/set` + state. Map the integration's controls (motion detection, night vision, floodlight, siren, PTZ, sensitivity, volume, battery/RSSI/SD sensors, motion events from cloud polling). HA MQTT discovery payloads. Reuse library control APIs; no HA code. |
| B5 | Snapshots | go2rtc `/api/frame.jpeg` for live; cloud thumbnail endpoint for battery cameras (avoid waking them). |
| B6 | Packaging | Multi-arch image (amd64, arm64) with ffmpeg only for snapshots/clips; non-root; healthcheck; compose example; optional HA add-on wrapper (the private go2rtc add-on experiment in ROAD-TO-1.0 is the starting point). |
| B7 | Docs | Frigate, Scrypted (RTSP camera plugin), HA-without-integration recipes. |

## Risks

- **Account-level MQTT exclusivity.** The broker binds auth to one `client_id`
  per account: the bridge and the HA integration cannot both hold the persistent
  connection for the same account. Users must pick one, or use a second
  (shared-device) account. Document loudly; detect and warn.
- Camera viewer cap (-50002) and the ~120 s slot hold after a viewer leaves:
  many consumers must share one session through go2rtc, never open their own.
- Security: RTSP/WebRTC on the LAN serve decrypted media; bind to loopback by
  default, require explicit opt-in (`AIDOT_ALLOW_LAN_SERVE` semantics) and
  support go2rtc auth.
- Support load outside HA (NVR-specific issues).

## Exit criteria for starting

- Direct publish is default-on (A4) with a clean release behind it.
- 1.0.0 shipped.
- At least a handful of concrete non-HA user requests (Frigate/Scrypted/HomeKit).
