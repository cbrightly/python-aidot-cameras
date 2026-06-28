# python-aidot-cameras

[![PyPI version](https://img.shields.io/pypi/v/python-aidot-cameras)](https://pypi.org/project/python-aidot-cameras/)
[![Python versions](https://img.shields.io/pypi/pyversions/python-aidot-cameras)](https://pypi.org/project/python-aidot-cameras/)
[![License: MIT](https://img.shields.io/pypi/l/python-aidot-cameras)](LICENSE)

Control AIDOT WiFi lights **and cameras** from Python.

## What this library does

This is a camera-capable fork of the upstream lights-only
[`python-aidot`](https://github.com/AiDot-Development-Team/python-AiDot). On top of
the original light controls it adds, for AiDot/Leedarson cameras:

- live WebRTC video streaming (both DTLS-SRTP and SDES-SRTP paths),
- snapshots, PTZ, and camera controls,
- cloud recordings, thumbnails, and motion polling,
- two-way (push-to-talk) audio.

This repository is the **library** (distribution name `python-aidot-cameras`); it
is what your code imports. The Home Assistant custom component
(`custom_components/aidot/`) is **not** here — it lives in the companion
integration repo
[`cbrightly/hass-aidot-cameras`](https://github.com/cbrightly/hass-aidot-cameras),
which depends on this library.

## Install

Install from PyPI — the simplest, recommended method:

```bash
# lights + camera cloud/control only:
pip install python-aidot-cameras
# add live WebRTC streaming, snapshots, and two-way audio:
pip install python-aidot-cameras[webrtc]
```

The `[webrtc]` extra pulls in the dependencies (aiortc, av, …) needed for live
streaming, snapshots, and two-way audio. Without it you still get lights plus the
camera cloud/control APIs, just not live media.

Want the latest unreleased code? Install straight from the GitHub repo instead:

```bash
# lights + camera cloud/control only:
pip install "git+https://github.com/cbrightly/python-aidot-cameras"
# add live WebRTC streaming, snapshots, and two-way audio:
pip install "python-aidot-cameras[webrtc] @ git+https://github.com/cbrightly/python-aidot-cameras"
```

## Stream a camera in a few lines

Open a live WebRTC stream from a camera device client and receive decoded frames
via a callback:

```python
session = await device_client.async_open_webrtc_stream(on_frame=cb, timeout=30.0)
# ... session.stop() when done
```

Two-way (push-to-talk) audio rides the same session:

```python
session = await device_client.async_open_webrtc_stream(..., talk=True)
await session.async_start_talk(pcm_provider)   # provider() -> 320B s16le PCM (20ms @ 8kHz), or None
# ... speak ...
await session.async_stop_talk()
```

## Stream without Home Assistant: `aidot-go2rtc`

Bridge a camera into [go2rtc](https://github.com/AlexxIT/go2rtc) (or any RTSP/HTTP
consumer) **without Home Assistant**. Installing the package provides the
`aidot-go2rtc` console script; for an isolated tool install use pipx or uv:

```bash
pipx install "python-aidot-cameras[webrtc]"
# or:
uv tool install "python-aidot-cameras[webrtc]"

aidot-go2rtc --list                  # discover cameras + their transport
aidot-go2rtc <device_id> '{output}'  # stream one camera (as a go2rtc exec: source)
```

## Using this with Home Assistant

If you came here because a Home Assistant integration depends on this library: the
custom component (`custom_components/aidot/`) is maintained in the companion
integration repo
[`cbrightly/hass-aidot-cameras`](https://github.com/cbrightly/hass-aidot-cameras),
which depends on this library. See that repo for installing the component — via
HACS, or by copying `custom_components/aidot/`.

## Going deeper: the full camera API

[`docs/CAMERAS.md`](docs/CAMERAS.md) is the complete camera reference: streaming,
snapshots, recordings, motion polling, two-way audio, LAN-direct media, and the
finer-grained tuning knobs. Start there once you have a stream working.

## Configuration: environment variables

The library reads a small set of environment variables.

### Credentials

Read by the credential helper (`aidot.credentials`); they take priority over any
stored credentials file. See [`src/aidot/credentials.py`](src/aidot/credentials.py).

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_USERNAME` | AiDot account username/email. Used with `AIDOT_PASSWORD`. | (none) |
| `AIDOT_PASSWORD` | AiDot account password. Used with `AIDOT_USERNAME`. | (none) |
| `AIDOT_COUNTRY` | Account region/country code. | `US` |

### Camera streaming / tuning

Read by the camera client (`aidot.camera.client`). Defaults work out of the box;
override only to tune. The on-by-default streaming optimizations and the deeper
experimental/advanced flags are documented in
[`docs/CAMERAS.md`](docs/CAMERAS.md#advanced-tuning-environment-variables).

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_MAX_CONCURRENT_OPENS` | Caps concurrent stream opens across all cameras. | `2` |
| `AIDOT_MAX_CONCURRENT_STREAMS` | Caps how many cameras stream at once. | `3` |
| `AIDOT_FAST_CONNECT` | LAN-direct "fast connect" (STUN-only, skips several cloud signaling waits) when truthy. On-LAN only — leave off for off-subnet / strict-NAT viewers. | unset (off) |
| `AIDOT_LIVESTREAM_PARAM` | Cloud `liveStreamParam` pre-connect that provisions battery cameras' live-stream sessions. Set `0` to skip; without it battery cameras (e.g. L2) reject streaming with `-50019`. | `1` (enabled) |
