# Camera support

This fork adds live streaming, snapshots, cloud recordings, real-time-ish motion
events, and two-way (push-to-talk) audio for AiDot/Leedarson cameras, on top of
the upstream lights-only library.

## Streaming

Cameras use WebRTC over the AiDot/Leedarson MQTT signaling channel (an
AWS-KVS–derived flow), not a local API. Two media-key paths exist and are
auto-selected per camera:

- **DTLS-SRTP** - A000088 models (e.g. M3 Pro, Deck).
- **SDES-SRTP** - other models (keys carried in the SDP).

```python
session = await device_client.async_open_webrtc_stream(on_frame=cb, timeout=30.0)
# ... session.stop() when done
```

`test_camera.py` is a CLI harness that exercises the full path
(`python test_camera.py --webrtc -d "<name>"`); see `--help`.

### Connection reliability (DTLS / A000088)

A000088 cameras advertise **two consecutive ICE ports** `[P, P+1]` and only
proceed to DTLS when the client nominates the **higher** one (their live DTLS
socket). The library forces `USE-CANDIDATE` onto the highest remote port, which
lifts the per-attempt connect rate from ~25% to ~75–87%. The fix is **scoped to
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
control channel; without it the firmware tears the session down at ~18–22 s. The
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

`tools/talk_test.py` plays a 440 Hz tone for a few seconds (validated audibly).

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
