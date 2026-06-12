# LAN control client - implementation plan

A local (offline) **device-control** path for AiDot cameras: discover via unicast
UDP:6666, connect to TCP:10000, AES-login with cached `aesKey`+`password`, then
`getDevAttr`/`setDevAttr`/`setDevAttrNotif`. **Control only - not video.** Video is
WebRTC (`liveType=2`) and stays cloud-MQTT-signaled; there is no clean local video
path on this hardware.

## Ports recap
- **6666/UDP** - discovery: stateless, no auth. Encrypted `devDiscoveryReq` →
  one `devDiscoveryResp` (`devId`, `mac`, `productModel`, `lanMode`, `localCtrFlag`).
  Cameras answer **unicast only**, not broadcast (only A000088 firmware answers at all).
- **10000/TCP** - control: stateful, AES-ECB, session-based. `loginReq` (auth) →
  `getDevAttr`/`setDevAttr`/`ping`/`setDevAttrNotif`. Same protocol as the lights.

## Phase 0 - RESULT: PASS with mandated constraints (validated 2026-06-12, .225 A000088)
1. **Concurrency = SINGLE SESSION.** A 2nd loginReq instantly evicts the 1st (broken
   pipe). => persistent held socket impossible; design MUST be short-lived
   connect -> login -> cmd(s) -> close, one in-flight session per camera.
2. **setDevAttr works + applies.** Toggled LedOnOff 0->1 (readback confirmed), restored
   0 (confirmed). Camera returns setDevAttrResp.
3. **No push.** Camera does NOT emit setDevAttrNotif on change => status stays poll-based
   (short-lived getDevAttr on interval) or cloud. No local motion/occupancy push.
4. **Latency.** connect+login 34-400ms (warm ~50ms / cold ~300ms), set+ack ~250ms.
   One-off op ~300-650ms; burst (hold for the interaction, idle-close) ~250ms/cmd.
   Win is real for PTZ bursts, modest for single toggles; offline-capable regardless.
5. **Battery gating confirmed**: A000088 wired (battery=None) answers unicast+login ->
   eligible. Battery A001513 don't answer unicast -> naturally excluded.

STILL UNVERIFIED (manual - need official app / physical access):
- Does a local setDevAttr propagate to the cloud/app view, or diverge?
- Does the official app ever open a local :10000 session? (If yes, app+HA evict each
  other; if it only uses cloud, they coexist.) Verify before wide rollout.

## Phase 1 - Library: local control transport
- `CameraLanClient`: **short-lived sessions only** (Phase 0: single-session channel,
  no push). Per control op (or burst): unicast-discover → connect `:10000` → AES login
  → get/setDevAttr → idle-close after ~2-5s. Serialize: one in-flight session/camera,
  never hold. Status = poll via a short-lived `getDevAttr` on an interval (no notif push).
- Add **unicast probing** to `Discover` (send to known device IPs, not just the
  broadcast) - fixes camera discoverability generally.
- **Credential bootstrap**: fetch + cache `aesKey`/`password` once from cloud (reuse
  the existing `credentials` cache).
- **Eligibility gate**: `localCtrFlag==1` AND answers unicast AND
  `battery_remaining is None` → eligible; everything else stays cloud.
- **Local-first dispatch with cloud fallback** per operation (timeout/error → cloud).

## Phase 2 - hass-AiDot integration
- Route control (PTZ via `trackingMode`, switches, selects, numbers) through the
  local client for eligible cameras only; cloud for the rest.
- **Opt-in config toggle**: "Use local camera control (experimental)".
- Prefer local push for eligible cameras' control-plane sensors.
- **Label precisely: "local control", never "local camera"** (avoid the video trap).

## Phase 3 - Hardening & tests
- Reuse the 0.6.1 reconnect/backoff/teardown patterns (don't reintroduce flapping).
- **Never hold a socket to battery/sleeping cams**; short-lived connect-cmd-close for
  control, keepalive only for confirmed-wired models that benefit from push status.
- VLAN/unreachable → silent graceful fallback, no log spam.
- Unit tests + a live smoke test mirroring the existing one.

## Phase 4 - Rollout
- Ship behind the opt-in flag, **wired-only, local-first + cloud fallback**. Library
  minor bump, integration version bump + manifest pin.
- Measure latency improvement and fallback rate on the A000088s before widening.

## Reference (verified)
- Local login: `magic 0x1EED + msgtype(1) + bodylen + aes_encrypt(json, aes_key)`,
  `aes_key` = 16-byte bytearray of the cloud dict's `aesKey[0]`; loginReq shape =
  `DeviceClient.login()`. Got `loginResp ack.code:200` + `ascNumber`.
- `getDevAttrReq` over the same socket returns the full attr set incl. `trackingMode`
  (PTZ), `MotionDetection_*`, `nightVisionMode`, `sirenRing`, `LedOnOff`, `Occupancy`,
  `Battery_remaining`, plus stream-negotiation fields (`liveType`, `enableSdes`,
  `isDTLS`) used only for the cloud video path.
- Fleet probed: Bedroom M3 Pro / Deck / M3 Pro v2 (`LK.IPC.A000088`) all
  `liveType=2` WebRTC, wired (`battery=None`), answer unicast + local login.
