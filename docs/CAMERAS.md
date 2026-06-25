# Camera support

This fork adds live streaming, snapshots, cloud recordings, real-time-ish motion
events, and two-way (push-to-talk) audio for AiDot/Leedarson cameras, on top of
the upstream lights-only library.

## Streaming

Cameras use WebRTC over the AiDot/Leedarson MQTT signaling channel (an
AWS-KVS-derived flow), not a local API. Two media-key paths exist and are
auto-selected per camera:

- **DTLS-SRTP** - A000088 models (e.g. M3 Pro, A000088-1).
- **SDES-SRTP** - other models (keys carried in the SDP).

```python
session = await device_client.async_open_webrtc_stream(on_frame=cb, timeout=30.0)
# ... session.stop() when done
```

`test_camera.py` is a local developer CLI harness (not shipped with the library)
that exercises the full path (`python test_camera.py --webrtc -d "<name>"`); see
its `--help`.

### Signaling handshake (MQTT)

Before any media flows the library exchanges JSON messages with the camera over
MQTT. The non-obvious wire facts (learned the hard way - keep them here so they
don't have to be re-discovered):

- **`peer_id` format:** `{32-hex session}_{6-hex rand}_{liveType}_{streamId}_{version}`,
  where the trailing `version` is `1` for SDES and `2` for DTLS. The camera
  **echoes our exact `peer_id` back** in its responses.
- **`livePlayResp` is matched on `peerid`, not `devId`.** The response payload
  carries **no `devId`** - it echoes back our `peerid`. Matching on `devId` (an
  earlier bug) means the match never fires and the wait runs to its full timeout
  every time. Match on the echoed `peerid` (with a `devId` fallback).
- **Reject codes:** `livePlay=0` is an unambiguous refusal - fast-fail on it.
  Other non-OK `code` values are **transient and recoverable**, most importantly
  `-50019` ("not ready"), which battery cameras emit routinely and recover from
  via ICE; treat these as transient and proceed rather than aborting. A camera
  already at its viewer limit returns a terminal ack (`-50002` / `-50015`) -
  see the busy handling below.
- **SDES role-reversal (A001064 PTZ):** this model echoes our offer back as its
  own `webrtcReq` *before* doing ICE, so it must be armed before our `webrtcReq`
  is sent. The `sdes_fast_liveplay` optimisation sends `webrtcReq` ~4.5 s earlier
  and breaks this model specifically, so A001064 is excluded from that flag
  (`_NO_FAST_LIVEPLAY_MODELS`).

The `livePlayResp` wait returns as soon as the response arrives; its timeout is
only paid when the camera never answers. `sdes_fast_liveplay`
(`AIDOT_SDES_FAST_LIVEPLAY` env / `sdes_fast_liveplay=` kwarg) skips this wait and
goes straight to webrtcReq/ICE — **on by default**, matching the official app
(which never waits for `livePlayResp`). Role-reversal models (A001064) are always
excluded. Disable with `AIDOT_SDES_FAST_LIVEPLAY` in `{0,false,no,off}`.

The **DTLS** path has the targeted equivalent (0.9.0): `dtls_fast_liveplay`
(`AIDOT_DTLS_FAST_LIVEPLAY` env / `_dtls_fast_liveplay_opt`) skips only the
up-to-2 s `livePlayResp` wait while keeping the full ICE/TURN/DTLS handshake — so
remote/relay viewing is unaffected (unlike `fast_connect`, which also strips
TURN). **On by default**; ~2 s off a cold LAN open. Separately, the HTTP ICE
config is cached until just before its server-provided `ttl` (capped 1 h), saving
the ~2 s `iceConfig` fetch on a re-open after the warm session lapses.

### Diagnostics

- `WebRTCSession.get_stats()` (0.9.0) — best-effort connection-health snapshot:
  the nominated ICE candidate pair (host/srflx/relay/prflx — relay-vs-direct) plus
  inbound RTP packets received/lost and jitter. Audio counters are reliable; the
  *video* count is undercounted (this path bridges video outside aiortc's RTP
  receiver — trust decoded-frame rate for video health).
- `CameraStatus.wifi_rssi` (0.9.0) — the camera's cloud-reported Wi-Fi RSSI (dBm),
  surfaced by HA as a diagnostic sensor; the fastest way to spot a marginal link.
- `scripts/camera_diag.py` — maintained on-hardware probe: handshake time,
  time-to-first-frame, per-second fps timeline + gaps, nominated ICE path, RTP
  health, RSSI. `python scripts/camera_diag.py --name <substr>`.

### Adaptive fast-with-fallback (SDES keepalive, opt-in)

For SDES cameras the keepalive loop can run an **adaptive** strategy
(`AIDOT_SDES_ADAPTIVE`, opt-in, default off; set `=1` to enable): the first open
tries the fast path (skip the livePlay waits + the TURN relay pre-allocation) with
a short 45 s open timeout / 40 s media grace. If that attempt delivers no media it
**falls back** to the full, patient relay path for the rest of the loop. It is
off by default because a fast *failure* costs ~40 s (the grace) before fallback
while a fast *success* saves only ~7 s — the real-world failure rate should be
characterised before making it a default. This makes fast-by-default safe regardless of
reachability — a LAN-direct camera gets the fast connect; a strict-NAT / non-LAN
camera loses one fast attempt then connects over the relay. A per-device cache
(`_fast_path_unavailable`) remembers a camera whose fast attempt failed so later
views skip straight to the full path, bounding the fast-timeout cost to once per
camera per session. The relay pre-allocation itself is also separately skippable on
the fast path via `AIDOT_SDES_SKIP_TURN_PREALLOC` (it does two synchronous TURN
Allocate round-trips, ~2-3 s, unused on a LAN).

### Connection reuse (`AIDOT_PERSISTENT_MQTT`, on by default)

Historically every device command, attribute fetch, and **stream open** opened
and tore down its own cloud MQTT WebSocket. The official app instead keeps ONE
persistent connection per login session and reuses it for everything. By default
(matching the app), `_PersistentMqtt` holds a single account-level
connection (the broker binds auth to the one authorized `client_id`, so there can
only be one), subscribes once, replays subscriptions on reconnect, and carries
commands, attributes, AND the stream-open signaling — without tearing down on
stream stop. This removes the per-open connect churn that otherwise rate-limits
the cloud account; a live 7-camera soak showed SDES `NO_MEDIA` dropping from
~57 % to ~11 % with the flag on. On by default; disable with `AIDOT_PERSISTENT_MQTT`
in `{0,false,no,off}` or per-camera `_persistent_mqtt_opt=False` (the explicit opt
always wins).

### Connection reliability (DTLS / A000088)

A000088 cameras advertise **two consecutive ICE ports** `[P, P+1]` and only
proceed to DTLS when the client nominates the **higher** one (their live DTLS
socket). The library forces `USE-CANDIDATE` onto the highest remote port, which
lifts the per-attempt connect rate from ~25% to ~75-87%. The fix is **scoped to
DTLS-camera connections only** - SDES cameras and non-camera devices are a strict
no-op. Combined with retries (`--webrtc-retries`, default 5, jittered backoff)
the effective connect rate is high.

- **Escape hatch:** set `AIDOT_DISABLE_HIGHPORT_FIX=1` to disable the nomination
  override and use upstream aioice behavior (used to measure the baseline).

If a camera is already serving its maximum number of viewers it returns a
terminal ack (`-50002` / `-50015`); the library raises `AidotCameraBusy` and
stops retrying rather than burning the retry budget.

### Connection reliability (SDES / battery)

SDES cameras - including battery models (A001513) - stream end-to-end once the
ICE/SCTP handshake completes. Battery cameras require a periodic AVIO keepalive
(`HEARTBEAT`, every 10 s) sent as an **encrypted SCTP DATA chunk** over the
control channel; without it the firmware tears the session down at ~18-22 s. The
library sends this automatically (matching the official app's `DataChannel`
keepalive timer), so battery streams hold while the camera is awake - validated
at 72 s / 49 s holds on two A001513 cameras.

Battery cameras sleep between events. The library wakes them on demand via the
cloud **HTTP low-power endpoint** (`async_wake_camera()`, also fired automatically
at the start of a stream open) in addition to the MQTT `lowPowerActiveStateReq` -
the HTTP wake is forwarded by the cloud to the camera's always-on channel, so it
reaches a deeply-sleeping camera that has dropped its MQTT session (matching the
app, which sends both). Validated on three A001513 cameras (woken with no motion).

## Two-way audio (push-to-talk)

Two-way audio works on **both** camera paths through the same API. Open with
`talk=True`, then push frames:

```python
session = await device_client.async_open_webrtc_stream(..., talk=True)
await session.async_start_talk(pcm_provider)   # provider() -> 320B s16le PCM (20ms @ 8kHz), or None
# ... speak ...
await session.async_stop_talk()
```

…or the higher-level helper, which opens a session, plays the clip, and tears
down (used by the Home Assistant `aidot.talk` service):

```python
await device_client.async_speak(pcm_provider, max_seconds=30)
```

`pcm_provider()` returns 320-byte signed-16-bit-LE PCM frames at 8 kHz (one per
20 ms), or `None` when the clip is finished. On the wire the audio is G.711
A-law (PCMA, PT=8) - the codec the camera negotiates.

- **DTLS cameras (A000088):** talk rides the same PeerConnection via an
  always-present `sendrecv` audio transceiver toggled at runtime (present-but-idle
  sends no RTP, so it never triggers a silence teardown).
- **SDES cameras (A001513 battery, etc.):** a `talk=True` open advertises the
  audio m-section `sendrecv` + `a=ssrc` so the camera builds a receive path; the
  library opens the speaker with AVIO `SPEAKERSTART`/`SPEAKERSTOP` over the SCTP
  control channel and pumps PCMA as SRTP to the camera's media address. Validated
  audibly on an A001513 (the camera ACKs `SPEAKERSTART` with `851`). Pure-streaming
  opens stay `recvonly` and are unaffected.

`tools/talk_test.py` (a local developer script) plays a 440 Hz tone for a few
seconds (validated audibly).

**Releasing the speaker on teardown:** when a session that used talk is stopped,
the library sends `SPEAKERSTOP(849)` and gives the transport a brief flush window
before closing it, so the camera actually frees its speaker/talk channel. Without
this the channel stays bound to the dead session and the next push-to-talk (ours
or the official app's) gets `851` "mic occupied". This runs on every teardown
path where talk was active — not just a clean `async_stop_talk`.

## Motion events

Cameras do **not** push motion to a passive MQTT subscriber (alarm attributes are
only sent during an active live view; background motion goes via FCM). The
working path is **cloud-event polling**:

```python
await device_client.async_start_motion_polling(callback, interval=30.0)
# callback(event) fires per new motion/person event (deduped, in time order)
await device_client.async_stop_motion_polling()
```

## Local (LAN-direct) streaming

The camera **media is peer-to-peer**: when the client and the camera are on the
same network, WebRTC ICE nominates the camera's LAN host candidate and the
DTLS-SRTP / SDES-SRTP video flows **directly to the camera's LAN IP** - it does
not traverse the internet or a cloud media relay. This was verified by
packet-capturing the official AiDot app, which streams these cameras the same way
(STUN/ICE + media straight to `192.168.x.y` camera addresses).

The cloud is used **only for signaling**: MQTT to exchange the SDP offer/answer
and ICE candidates, plus STUN/TURN for rendezvous. In normal same-LAN operation
the bandwidth-heavy media therefore stays on the LAN.

> These cameras stream over **WebRTC**, not TUTK IOTC P2P. The TUTK scaffolding in
> the library is unused for streaming - which is also why `getP2pId` returns null
> (there is no TUTK UID to fetch for a WebRTC-streamed camera).

## Known limitations

- **No fully-offline mode.** The cameras expose no local signaling API (and no
  RTSP/ONVIF/HTTP), so establishing a stream still needs the cloud signaling path
  (MQTT + STUN/TURN). Only the *media* is LAN-direct; the *handshake* is not.
- **Connect is per-attempt probabilistic** for DTLS cameras; rely on retries (the
  high-port nomination fix substantially raises the per-attempt rate).
