# python-aidot-cameras

[![PyPI version](https://img.shields.io/pypi/v/python-aidot-cameras)](https://pypi.org/project/python-aidot-cameras/)
[![Python versions](https://img.shields.io/pypi/pyversions/python-aidot-cameras)](https://pypi.org/project/python-aidot-cameras/)
[![License: MIT](https://img.shields.io/pypi/l/python-aidot-cameras)](LICENSE)

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

## Standalone CLI: `aidot-go2rtc`

Bridge a camera into [go2rtc](https://github.com/AlexxIT/go2rtc) (or any
RTSP/HTTP consumer) **without Home Assistant**. Installing the package provides
the `aidot-go2rtc` console script; for an isolated tool install use pipx or uv:

```bash
pipx install "python-aidot-cameras[webrtc]"
# or:
uv tool install "python-aidot-cameras[webrtc]"

aidot-go2rtc --list                  # discover cameras + their transport
aidot-go2rtc <device_id> '{output}'  # stream one camera (as a go2rtc exec: source)
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

The most useful knobs read by the camera client (`aidot.camera.client`). Defaults
are chosen to work out of the box; override only when tuning. Finer-grained
internal knobs (audio normalization, keyframe/PLI cadence, retry timing, SDES
audio, idle release, the sprop cache path) are documented in
[`docs/CAMERAS.md`](docs/CAMERAS.md#advanced-tuning-environment-variables).

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_MAX_CONCURRENT_OPENS` | Caps how many stream opens run concurrently across all cameras. | `2` |
| `AIDOT_MAX_CONCURRENT_STREAMS` | Caps how many cameras stream at once. | `3` |
| `AIDOT_FAST_CONNECT` | Enable LAN-direct "fast connect" (STUN-only, skips several cloud signaling waits) when truthy. On-LAN only — off-subnet/strict-NAT viewers must leave it off. | unset (off) |
| `AIDOT_SDES_SKIP_TURN_PREALLOC` | Skip the SDES TURN relay pre-allocation (~2–3 s of cold-start latency) so signaling goes straight out with the host candidate. Faster on a LAN, at the cost of no relay fallback for a camera on a different segment / behind strict NAT. Experimental, opt-in (truthy = `1`/`true`/`yes`/`on`). | unset (off) |
| `AIDOT_SDES_ADAPTIVE` | Adaptive fast-with-fallback for the SDES keepalive loop: try the fast path first and fall back to the full relay path if a fast attempt delivers no media. A per-device cache skips the fast attempt on later views once it has failed. Truthy value enables. | unset (off) |
| `AIDOT_SDES_FAST_LIVEPLAY` | Don't block on the `livePlayResp` wait for eligible SDES cameras (~4.5 s faster cold start). Role-reversal models (A001064 PTZ) always excluded for correctness. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_DTLS_FAST_LIVEPLAY` | The DTLS (A000088) analogue: skip the `livePlayReq`-echo and `livePlayResp` waits (the dominant LAN cold-start cost) while keeping the full ICE/TURN/DTLS handshake, so remote/relay viewing is unaffected. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_PERSISTENT_MQTT` | Reuse ONE account-level persistent MQTT connection for commands, attribute fetches, and stream-open signaling (matching the official app) instead of connecting per operation. **On by default** (live soak cut SDES NO_MEDIA ~57%→~19%); set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_SERVE_RELAY` | Hold the public stream port via an internal relay that proxies to ffmpeg, so the first (cold) view connects instead of failing while ffmpeg can't pre-bind the port. Set to `0` to serve ffmpeg directly. | `1` (enabled) |
| `AIDOT_LIVESTREAM_PARAM` | Set to `0` to skip the cloud `liveStreamParam` pre-connect that provisions battery cameras' live-stream sessions before signaling (without it, battery cameras like the L2 models reject streaming with `-50019`). | `1` (enabled) |
