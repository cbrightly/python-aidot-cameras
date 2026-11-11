# Protocol inventory: what the app and the cameras say to each other

An inventory of every message between the official app and the cameras that
the decompiled app (the one `APP-PARITY-STATUS.md` cites) can send or handle,
against what this library and the Home Assistant integration implement. Its
purpose is to make "did we miss anything" a list to read rather than a
feeling. Each row says: implemented, not implemented (with why, or "not yet"),
or deliberately skipped.

Status words: **yes** (implemented and live-tested), **partial**, **no**
(not implemented, worth considering), **skip** (not worth implementing, reason
given), **n/a** (not camera-related).

The app has two transports to a camera: the WebRTC data channel (what this
library speaks) and TUTK P2P (`LdsTutkChannel`), which carries the same
control commands with a different framing. Everything below is the WebRTC
side unless marked.

## 1. Cloud signalling over MQTT (`iot/v1/...` topics)

Messages the app's IPC service sends or handles, by method name.

| Method | Direction | Purpose | Status |
|---|---|---|---|
| `livePlayReq` / `livePlayResp` | app -> camera | arm the camera's WebRTC subsystem before the offer; `-50019` = not ready (waking) | yes |
| `webrtcReq` / `webrtcResp` | app <-> camera | SDP offer and answer | yes |
| `iceCandidateReq` | both | trickled ICE candidates | yes |
| `getIceConfigReq` / `Resp` | app -> cloud | STUN/TURN servers | yes |
| `lowPowerActiveStateReq` / `Resp` | app -> camera | wake a battery camera (`DeviceWakeUpRepos`) | yes |
| `setDevAttrReq` / `Resp` | app -> camera | write a property (see section 3) | yes |
| `setDevAttrNotif` | camera -> app | property changed (the app's `onPartialUpdateEvent`) | yes (the library listens; the integration takes the result through the library's device events) |
| `getDevAttrReq` / `Resp` | app -> camera | read properties | yes |
| `devActionReq` / `Resp` | app -> camera | actions: `wakeup`, `reboot`, `alarm` (siren), `ota` | partial: reboot and alarm yes; `wakeup` is done through `lowPowerActiveStateReq` instead; `ota` no (see 5) |
| `deviceControlByDevActionReq` | app -> camera | SD card status / format from the card page (`SDEventsPresenter`) | no: card status is read through the AVIO listing; format is not offered |
| `devEventNotif` | camera -> app | device event push over the app's Netty socket (`NettyManager`) | no: the integration reads the cloud event list instead (polling); a push path would make motion events faster. Worth a look |
| `eventNotify` | cloud -> app | event list changed (`IpcServiceImpl$5`, `$7`) | no: same as above |
| `thumbnailPhotoNotify` | in-app only | the app's own event bus notice that a thumbnail it received over the stream (`0x4ba`, section 2) was saved; not a cloud or camera message | n/a |
| `updateOtaStatusNotify` / `updateOtaStautsNotif` | camera -> app | firmware update progress | no (see 5) |
| `devUnbindNotif` | cloud -> app | device removed from the account | yes since library 1.1.1 (`async_watch_unbind`) and integration 2.40.0 (re-syncs the device list on the notice) |
| `devDiscoveryReq` | app -> LAN | LAN discovery broadcast (lights and cameras) | yes |
| `loginReq` / `loginResp` | app -> device (LAN) | LAN login (lights) | yes (upstream python-aidot) |
| `getVerListReq`, `getDevInfoReq` | app -> cloud/camera | firmware version list; device info for an abnormal version | no (see 5) |
| `getPlaybackServerInfoReq`, `getVideoListResp`, `playVideoResp`, `hasVideoResp` | app <-> cloud | cloud (paid plan) recordings over the Netty stream service | partial: cloud recordings are read over HTTP by the integration (`recordings.py`); the app's streaming server path is not used |
| `commonNotify`, `bleNotify` | BLE | Bluetooth provisioning of lights | n/a |

## 2. Control commands on the stream (AVIO / TUTK IOCTRL over the data channel)

Every command the app's WebRTC channel can send, from its one sender.
Hex is the TUTK IOTYPE; the struct is TUTK's unless noted.

| Command | Hex | Purpose | Status |
|---|---|---|---|
| `SESSION_MODE` (LIVING / SD / IDLE) | `0x1500` | select live vs card playback vs idle on the session | yes (live and SD playback) |
| `START` / `AUDIOSTART` / `AUDIOSTOP` | `0x1ff`, `0x300`, `0x301` | start video / audio on TUTK; the WebRTC path negotiates these in SDP | yes where needed (SDES path) |
| `SETSTREAMCTRL` | `0x320` | quality: 1 = HD, 5 = SD, 16 = Auto | yes (`hd`, `sd`, `auto`); the camera records it as `StreamType`, the encode does not change (APP-PARITY-STATUS.md) |
| `GETSTREAMCTRL` | `0x322` | read the quality back | yes (instrument only) |
| `SPEAKERSTART` / `SPEAKERSTOP` | `0x350`, `0x351` | two-way audio | yes |
| `PTZ_COMMAND` | `0x1001` | pan / tilt / zoom / presets | yes (pan, tilt, zoom; presets not exposed) |
| `LISTEVENT` / `HASLISTEVENT` | `0x318`, `0x4b5` | SD card recording list and day map | yes |
| `RECORD_PLAYCONTROL` | `0x31a` | play / pause / seek a card recording | yes |
| `DELLISTEVENT` | `0x4b7` | delete card recordings: u32 channel, u32 count, u8 0, u8 1, u8 count, u8 delete type (0), i16 the list page's event type, then per recording its STimeDay (Java weekday, Sunday 1) and index; `0x4b8` answers one byte, 0 for done | yes since library 1.3.0 (`async_delete_sd_recordings`) and integration 2.41.0 (`aidot.delete_sd_recording`) |
| `CUSTOM_COMMAND` (thumbnails) | `0x4b9` | ask for thumbnails of card recordings: a request channel id (int32 LE) followed by the recordings' start times (epoch ms, int64 LE each); each `0x4ba` reply carries the channel, one start time and that recording's JPEG (the app's handler reads 4 + 8 bytes then the picture, saves it and raises its in-app `thumbnailPhotoNotify`, which is not an MQTT message - an earlier version of this row said it was) | yes since library 1.3.0 (`async_get_sd_thumbnails`, DTLS sessions only: an SDES session reads one datagram at a time and cannot carry a picture) and integration 2.41.0 (fetched on a listing taken over a live session, shown in the media browser) |
| `RT_RADAR_START` / `STOP` / `SDCARD_START` / `REPORT` | `0x528`-`0x52f` | the radar / tracking overlay the app draws for models with a radar | skip: no supported model reports one |

Commands the library sends that the app does not: none on the live path. The
library's AVIO header is byte-identical to the app's (measured, 1.0.0b27).

## 3. Device properties (`props`)

The app's property bean lists these. "App writes" is from the app's
`setDevAttrReq` builders; "we" is the library or the integration.

| Property | App reads | App writes | Status |
|---|---|---|---|
| `TurnOnOff` | yes | yes (the most-written property; `NewLivePresenter.DeviceTurnOn`, the live screen's power switch) | yes since library 1.1.1 (`status.powered`, `async_set_power`) and integration 2.40.0 (the camera entity's own on/off) |
| `Dimming`, `LightOnOff` | yes | yes | yes (floodlight) |
| `MotionDetection_Enable` | yes | yes | yes |
| `PirDetection_Enable`, `PirDetection_Sen` | yes | yes (`setPIRConfig`) | yes (motion detection and sensitivity on battery models) |
| `micEnable` | yes | yes | yes |
| `sirenRing` | yes | yes | yes (siren) |
| `alarmType` | yes | yes | yes (alarm / event kind) |
| `trackingMode` | yes | yes | yes (PTZ tracking) |
| `SdcardRecord_Type` | yes | yes | partial: read for the card browser; the recording mode (continuous / event) is not exposed as a control |
| `StreamType` | yes | no (camera-written) | yes: reported back from the quality command; not yet shown by the integration |
| `talkMode` | yes | yes (once) | no: half- vs full-duplex talk; our talk path is push-to-talk. Low value |
| `spkNSLevel` | yes | no | no: speaker noise suppression level; read-only in the app |
| `digitZoom` | yes | no | no: digital zoom state; the PTZ zoom command covers the action |
| `PrivacyNo` | yes | no | no: unknown meaning (a privacy-zone count?), never written by the app |
| `Battery_remaining`, `charging`, `lowPowerStatus`, `networkRssi`, `WifiStrength` | yes | no | yes (sensors), except `WifiStrength` (RSSI is used) |
| `enableSdes`, `isDTLS`, `liveType`, `p2pCache`, `sptPreconn`, `audioCodec`, `videoCodec` | yes | no | yes: transport selection reads these |
| `dynamicStream`, `isAuto`, `isSupportAuto` | yes | no | read-only capability flags behind the three-arm quality picker; not needed |
| `splicingDistance`, `supportIpv6` | yes | no | skip: panoramic stitching and IPv6 flags, no supported model uses them |

The model profile (`product.serviceModules` declarations such as
`LedOnOffConfig`, `VoiceEnableConfig`, `WDREnableConfig`, `OSDEnable`,
`HDRStatus`, `autoLightEnable`, `lightBehavior`, `LingerDuration`) gates the
integration's switches and selects the way the app gates its settings pages;
see `model_profile.py` in the integration.

## 4. Cloud HTTP

The app's REST calls go through the upstream `python-aidot` client where the
library shares them (login, houses, devices, products, token refresh) plus the
camera endpoints the library adds: `/v21/devices/batchGetDeviceUserInfo`,
`/v25/commons/mqttConfig2` (opt-in), `/v29/api/webrtc/iceConfig`, `/v32/api/ipc`
(recordings, playback URLs). The app also calls `/v15/newSecurity/operateSecurityAlarm`
(the account-level security alarm, not a camera control) and `/v16/devices/`.
No camera control reaches a camera over HTTP; everything else is MQTT or the
stream.

## 5. Not implemented, by choice or not yet

- **Firmware update (OTA):** `getVerListReq`, `devActionReq ota`,
  `updateOtaStatusNotify`. Not implemented. Exposing a firmware update in Home
  Assistant is possible (an `update` entity) but a failed update bricks a
  camera the user cannot reach; left to the app on purpose. The firmware
  version is shown.
- **Event push (`devEventNotif` / `eventNotify`):** the integration polls the
  cloud event list; the app gets a push over its own socket service. Whether
  the cameras also announce events on the MQTT topics is unmeasured: a
  12-minute idle capture and a provoked PTZ pan showed none, and a real
  motion event has not yet been captured. Open.
- **Cloud recordings via the app's streaming server**: the HTTP path works;
  no reason to change.
- **Radar overlay, BLE, panorama stitching, IPv6:** out of scope for the
  supported models.

## How this was made

The method names, command constants and property names were listed from the
decompiled app with text search over its classes (the KVS WebRTC channel, the
IPC service, the live presenter and the property bean) and matched by name
against `aidot_cameras/camera/constants.py`, the MQTT method strings in the
library and the integration, and the integration's entity descriptions. Dated
2026-11-09; re-run the search when the app is updated.
