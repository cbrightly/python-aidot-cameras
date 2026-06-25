# python-aidot-cameras

Control AIDOT WiFi lights **and cameras** from Python.

This is a camera-capable fork of the upstream lights-only
[`python-aidot`](https://github.com/AiDot-Development-Team/python-AiDot). It adds
live WebRTC video streaming (DTLS and SDES-SRTP paths), snapshots, PTZ, camera
controls, cloud recordings/thumbnails, and two-way (push-to-talk) audio.

This repository is the **library** (distribution name `python-aidot-cameras`).
The Home Assistant custom component (`custom_components/aidot/`) lives in the
companion integration repo
[`cbrightly/hass-aidot-cameras`](https://github.com/cbrightly/hass-aidot-cameras), which depends
on this library.

## Library install

Install from PyPI (the simplest, recommended method):

```bash
# lights + camera cloud/control only:
pip install python-aidot-cameras
# add live WebRTC streaming, snapshots, and two-way audio:
pip install python-aidot-cameras[webrtc]
```

`[webrtc]` pulls in the extra dependencies (aiortc, av, …) needed for live
streaming, snapshots, and two-way audio. Without it you still get lights plus
the camera cloud/control APIs, but not live media.

For the latest unreleased code, install straight from the GitHub repo instead:

```bash
# lights + camera cloud/control only:
pip install "git+https://github.com/cbrightly/python-aidot-cameras"
# add live WebRTC streaming, snapshots, and two-way audio:
pip install "python-aidot-cameras[webrtc] @ git+https://github.com/cbrightly/python-aidot-cameras"
```

## Usage

Open a live WebRTC stream from a camera device client:

```python
session = await device_client.async_open_webrtc_stream(on_frame=cb, timeout=30.0)
# ... session.stop() when done
```

Two-way (push-to-talk) audio:

```python
session = await device_client.async_open_webrtc_stream(..., talk=True)
await session.async_start_talk(pcm_provider)   # provider() -> 320B s16le PCM (20ms @ 8kHz), or None
# ... speak ...
await session.async_stop_talk()
```

See [`docs/CAMERAS.md`](docs/CAMERAS.md) for the full camera API (streaming,
snapshots, recordings, motion polling, two-way audio, and LAN-direct media).

## Home Assistant component and CLI

The Home Assistant custom component (`custom_components/aidot/`) is **not** part
of this library repo - it lives in the companion integration repo
[`cbrightly/hass-aidot-cameras`](https://github.com/cbrightly/hass-aidot-cameras), which depends
on this library. See that repo for installing the component (via HACS or by
copying `custom_components/aidot/`).

The `test_camera.py` CLI harness and the scripts under `tools/` are local
developer/diagnostic tools (they carry hardcoded LAN IPs and are gitignored), so
they are not shipped with the published library.

## Environment variables

The library reads the following environment variables.

### Credentials

Used by the credential helper (`aidot.credentials`); they take priority over any
stored credentials file. See [`src/aidot/credentials.py`](src/aidot/credentials.py).

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_USERNAME` | AiDot account username/email. Used with `AIDOT_PASSWORD`. | (none) |
| `AIDOT_PASSWORD` | AiDot account password. Used with `AIDOT_USERNAME`. | (none) |
| `AIDOT_COUNTRY` | Account region/country code. | `US` |

### Camera streaming / tuning

Optional knobs read by the camera client (`aidot.camera.client`). Defaults are
chosen to work out of the box; override only when tuning.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_SPROP_DIR` | Directory where captured SPS/PPS (sprop) parameter sets are cached. Set this to a writable path (e.g. for Home Assistant) if the default location is read-only. | `<package dir>` |
| `AIDOT_DISABLE_HIGHPORT_FIX` | If set (any value), disables the DTLS high-port `USE-CANDIDATE` nomination fix and falls back to upstream aioice behavior (used to measure the baseline connect rate). | unset (fix enabled) |
| `AIDOT_FAST_CONNECT` | Enables LAN-direct "fast connect" mode (STUN-only, skips several cloud signaling waits) when set to a truthy value. | unset (off) |
| `AIDOT_PERSISTENT_MQTT` | Reuse ONE account-level persistent MQTT connection for device commands, attribute fetches, AND stream-open signaling (matching the official app) instead of connecting per operation, cutting cloud connect churn. **On by default** (the app's behaviour; live soak cut SDES NO_MEDIA ~57%→~19%); set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_SDES_ADAPTIVE` | Adaptive fast-with-fallback for the SDES keepalive loop: try the fast path first (skip livePlay waits + TURN relay pre-alloc) and fall back to the full relay path if a fast attempt delivers no media. A per-device cache skips the fast attempt on later views once it has failed for a camera. Truthy value enables. | unset (off) |
| `AIDOT_SDES_SKIP_TURN_PREALLOC` | Skip the SDES TURN relay pre-allocation (~2–3 s of cold-start latency) so signaling goes straight out with the host candidate instead of waiting on a relay address. Saves time on a LAN at the cost of no relay fallback for a camera on a different segment / behind strict NAT; the rest of the SDES handshake is left intact. Experimental, opt-in (truthy = `1`/`true`/`yes`/`on`). | unset (off) |
| `AIDOT_SDES_FAST_LIVEPLAY` | Don't block on the `livePlayResp` wait for eligible SDES cameras (~4.5 s faster cold start), going straight to webrtcReq/ICE — what the official app does (it never waits for livePlayResp). Role-reversal models (A001064 PTZ) always excluded for correctness. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_DTLS_FAST_LIVEPLAY` | The DTLS (A000088) analogue of `AIDOT_SDES_FAST_LIVEPLAY`: skip only the `livePlayResp` wait (the dominant LAN cold-start cost, ~2 s) while keeping the full ICE/TURN/DTLS handshake, so remote/relay viewing is unaffected. The only thing lost is fast-fail on an outright camera refusal (rare), which then surfaces as an ICE failure instead. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_SERVE_RELAY` | Holds the public stream port via an internal relay that proxies to ffmpeg, so the first (cold) view connects instead of failing while ffmpeg can't pre-bind the port. Set to `0` to serve ffmpeg directly. | `1` (enabled) |
| `AIDOT_MAX_CONCURRENT_OPENS` | Caps how many stream opens run concurrently. | `2` |
| `AIDOT_MAX_CONCURRENT_STREAMS` | Caps how many cameras stream at once. | `3` |
| `AIDOT_STREAM_IDLE_S` | Seconds of stream idle before an idle release. | `120` |
| `AIDOT_SDES_IDLE_RELEASE` | Set to `0` to disable idle release for SDES streams. | `1` (enabled) |
| `AIDOT_ICE_DISCONNECT_S` | ICE-disconnect debounce, in seconds, before tearing down. | `8` |
| `AIDOT_DTLS_RETRY_GATE_S` | Minimum spacing, in seconds, between DTLS open retries. | `15` |
| `AIDOT_BUSY_RETRY_S` | Delay, in seconds, before retrying when a camera reports busy. | `45` |
| `AIDOT_LIVESTREAM_PARAM` | Set to `0` to skip the cloud `liveStreamParam` pre-connect that provisions battery cameras' live-stream sessions before signaling (without it, battery cameras like the L2 models reject streaming with `-50019`). | `1` (enabled) |
| `AIDOT_GOP_PLI_S` | Interval, in seconds, between PLI (keyframe) requests. | `2.0` |
| `AIDOT_STALL_PLI_S` | If muxed frames stall for this many seconds (a dropped GOP on a jittery link, e.g. a weak-RSSI camera), request an IDR keyframe immediately instead of waiting out the full `AIDOT_GOP_PLI_S` cadence, so playback recovers sooner. Fires at most once per stall episode. Mains DTLS cameras only; `0` disables. | `1.0` |
| `AIDOT_SDES_PLI_GAPS` | Comma-separated second offsets for the early PLI (keyframe-request) burst on SDES cameras, to pull the first keyframe in faster on cold start. | `0,1.5,2,3` |
| `AIDOT_AUDIO_TARGET_DBFS` | Target loudness (dBFS) for two-way audio normalization. | `-15` |
| `AIDOT_AUDIO_MAXGAIN_DB` | Maximum gain (dB) applied by the audio normalizer. | `30` |
| `AIDOT_AUDIO_MINGAIN_DB` | Minimum gain (dB) applied by the audio normalizer. | `-12` |
| `AIDOT_AUDIO_GATE_DBFS` | Noise-gate threshold (dBFS) for two-way audio. | `-45` |
| `AIDOT_SDES_SERVE_AUDIO` | Serve audio on SDES cameras (**on by default** — a silence-base mix keeps the audio encoder fed so battery-camera audio streams smoothly). Set to `0`/`false`/`no`/`off` to disable. | on |
| `AIDOT_SDES_AUDIO_GAIN_DB` | Gain (dB) applied when SDES audio is served. | `-8` |
