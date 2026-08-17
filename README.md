# python-aidot-cameras

[![PyPI version](https://img.shields.io/pypi/v/python-aidot-cameras)](https://pypi.org/project/python-aidot-cameras/)
[![Python versions](https://img.shields.io/pypi/pyversions/python-aidot-cameras)](https://pypi.org/project/python-aidot-cameras/)
[![License: MIT](https://img.shields.io/pypi/l/python-aidot-cameras)](https://github.com/cbrightly/python-aidot-cameras/blob/main/LICENSE)

Control AIDOT WiFi lights **and cameras** from Python.

This **extends** the upstream lights-only
[`python-aidot`](https://github.com/AiDot-Development-Team/python-AiDot), which it
installs as a dependency rather than forking. It adds live WebRTC video streaming
(DTLS and SDES-SRTP paths), snapshots, PTZ, camera controls, cloud
recordings/thumbnails, and two-way (push-to-talk) audio.

Upstream owns the `aidot` import name and handles non-camera devices (lights,
plugs, switches) with none of this package's code in the path; everything here
lives under `aidot_cameras`. Taking a new upstream release is a dependency bump
plus a test run - see [`docs/UPSTREAM.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/UPSTREAM.md).

This repository is the **library** (distribution name `python-aidot-cameras`).
The Home Assistant custom component (`custom_components/aidot/`) lives in the
companion integration repo
[`cbrightly/hass-aidot-cameras`](https://github.com/cbrightly/hass-aidot-cameras), which depends
on this library.

## Supported cameras

The streaming transport is auto-selected per camera from its model id:

- **A000088** (M3 Pro) - DTLS-SRTP, wired/mains.
- **A001513** ("L2") - SDES-SRTP, **battery** (woken on demand; validated end-to-end).
- **A001064** (PTZ) - SDES-SRTP, wired/mains (role-reversal handshake).

Other battery models (A001108, A001360) are recognized in code with the same
battery handling. See [`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md#supported-cameras) for
the authoritative table and per-model notes.

## Known characteristics

Two behaviours that are measured, understood as far as the evidence allows, and
not going to change in this release. Neither is a fault to report.

**Recorded video is retrievable only from cloud storage.** Whether a camera
records to the cloud or to its own SD card is per-camera, not per-model, and the
device reports it as `IsSupportPlayback`. This library lists cloud recordings
(`async_get_cloud_recordings`) and resolves a playable HLS URL for one
(`async_get_event_video_media`); it has no SD-card equivalent, so a camera
storing to a card records normally and
nothing here can play it back. On the reference fleet that is four of seven
cameras. The vendor's own commands for it are identified but their responses
have never been decoded; see `docs/ROAD-TO-1.0.md` item 6.

**The A001064 (PTZ) streams at roughly 2.2 Mbps, about 24% above the vendor
app.** Measured from a capture of the app on the same camera: it takes about
1796 Kbps on HD and about 866 Kbps on SD. An earlier version of this note
repeated a figure of 225-500 Kbps for the app - that was never measured here,
and the capture contradicts it at both quality settings.

So the practical difference is a quarter more bandwidth at equivalent quality,
plus a roughly 2:1 saving that the app's SD control achieves and this library's
does not. The command sent here is byte-for-byte the app's, in-band and
mid-session on the same transport, and it does not change the rate; the app's
does. Streaming is correct either way and the cost is bandwidth, which matters
on a metered or congested link.

## Local (LAN) control and account ownership

Local control over the devices' TCP:10000 channel - both the light/plug protocol
and this package's `CameraLanClient` - is accepted **only for the account that
owns the devices**. A member of a shared home is rejected at the device, even
though the cloud happily hands that member a full device list, complete with the
per-device `password` and `aesKey`.

The rejection is easy to misread, because the device answers with a code that
varies by firmware family and a message that blames the wrong thing:

| Model | Ack |
| --- | --- |
| `LK.light.A001493` | 400 `not equal abort user id or password` |
| `LK.light.A001497`, `LK.plug.A001535` | 4354 `fail` |
| `LK.IPC.A000088` | 4352 `fail` |

The A001493's message is the honest one: it is the **user id** that does not
match, not the password. Every credential in the request can be correct and the
login will still be refused if the `userId` is not the owner's.

Verified 2026-08-08 against ten devices across four model families: every one
returns 200 for the owning account and one of the codes above for a shared-home
member.

**If local control never engages, check which account the integration is signed
in as before looking anywhere else.** A dedicated or secondary login - the sort
of thing you might use to avoid contending with a phone app over a rotating
refresh token - will control everything fine through the cloud and never once
log in locally.

Cameras' live video is unaffected either way: it is WebRTC signaled over cloud
MQTT and does not use this path.

## Library install

Install from PyPI (the simplest, recommended method):

```bash
# lights + camera cloud/control only:
pip install python-aidot-cameras
# add live WebRTC streaming, snapshots, and two-way audio:
pip install python-aidot-cameras[webrtc]
```

`[webrtc]` pulls in the extra dependencies (aiortc, av, ...) needed for live
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

export AIDOT_USERNAME=... AIDOT_PASSWORD=...   # or AIDOT_TOKEN_FILE, see below
aidot-go2rtc --list                  # discover cameras + their transport

# Stream one camera. Pick the form that matches how you are running it:
aidot-go2rtc <device_id> -                              # DTLS: MPEG-TS to stdout
aidot-go2rtc <device_id> '{output}'                     # SDES: go2rtc exec: only
aidot-go2rtc <device_id> http://127.0.0.1:8555/cam.ts   # either: serve, then pull
```

`'{output}'` is a placeholder go2rtc substitutes; run by hand it is passed
through as an RTSP push URL. Both transports can push, so `{output}` works on
any camera - but on a DTLS one prefer `-`, which carries the mux's 48 kHz AAC
untouched where the push has to transcode audio down to 8 kHz G.711.

**ffmpeg must be on PATH.** SDES cameras - two of the three validated models -
stream entirely through an ffmpeg subprocess, and pip cannot install a system
binary:

```bash
sudo apt install ffmpeg      # Debian/Ubuntu
brew install ffmpeg          # macOS
```

Authenticates via `AIDOT_USERNAME`/`AIDOT_PASSWORD` (`AIDOT_COUNTRY`, default
`US`) - a dedicated login is recommended for long-running standalone use, so
it doesn't fight Home Assistant over a shared rotating refresh token. Set
`AIDOT_TOKEN_FILE=/path/to/token.json` to cache the login across restarts;
token rotations are written back automatically. Run `aidot-go2rtc --help` for
the full list of authentication and stream env vars.

## Usage

Import from `aidot_cameras`, never from `aidot`. The upstream `aidot` package is
a dependency of this one and its `AidotClient` is lights-only: importing from it
gives you a client with no camera surface and **no ImportError** to tell you
why.

Log in, find a camera, and open a stream:

```python
import aiohttp
from aidot_cameras.client import AidotClient
from aidot_cameras.const import CONF_DEVICE_LIST, CONF_NAME

async with aiohttp.ClientSession() as http:
    client = AidotClient(http, country_code="US", username=..., password=...)
    await client.async_post_login()
    devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
    cam = next(d for d in devices if d.get(CONF_NAME) == "Front Door")
    device_client = client.get_device_client(cam)
```

`get_device_client` returns a `CameraDeviceClient` for a camera. Plain
`DeviceClient` and `LightDeviceClient` are the lights clients and carry none of
the camera surface, so a `hasattr` probe against the wrong one fails silently.

Open a live WebRTC stream:

```python
session = await device_client.async_open_webrtc_stream(on_frame=cb, timeout=30.0)
# ... session.stop() when done
```

`on_frame` receives a PyAV `av.VideoFrame` - use `pts` / `time_base` /
`to_ndarray()`. It is **DTLS-only** (A000088): SDES cameras (A001513, A001064)
decode out of process, so `on_frame` never fires for them and no error is
raised. For those, take the media as a file or a stream instead:

```python
session = await device_client.async_open_webrtc_stream(
    output_path="/tmp/live.ts",                      # record, or
    rtsp_push_url="rtsp://127.0.0.1:8554/cam",       # push to go2rtc
)
```

Two-way (push-to-talk) audio:

```python
session = await device_client.async_open_webrtc_stream(..., talk=True)
await session.async_start_talk(pcm_provider)   # provider() -> 320B s16le PCM (20ms @ 8kHz), or None
# ... speak ...
await session.async_stop_talk()
```

See [`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md) for the full camera API (streaming,
snapshots, recordings, motion polling, two-way audio, and LAN-direct media).

## Getting an RTSP URL

Want a plain `rtsp://` URL for Frigate, an NVR, or VLC? You can have one, but
not from this library alone: **it does not run an RTSP server**, and neither do
the cameras - they have no RTSP and no ONVIF endpoint. The only way video
leaves them is the cloud-signalled WebRTC handshake and SRTP decrypt this
library does. Pair it with [go2rtc](https://github.com/AlexxIT/go2rtc) and
go2rtc publishes the RTSP URL for you.

Three steps, about five minutes.

**1. List your cameras.** The transport column decides one word in step 3, so
keep the output around:

```console
$ pip install "python-aidot-cameras[webrtc]"
$ export AIDOT_USERNAME='you@example.com' AIDOT_PASSWORD='...' AIDOT_COUNTRY=US
$ aidot-go2rtc --list
1a2b3c4d...  LK.IPC.A000088  DTLS (http-pull)
5e6f7a8b...  LK.IPC.A001064  SDES (rtsp-push)
```

**2. Add one line per camera to `go2rtc.yaml`.** Both forms make go2rtc the
parent process, so it starts the stream on the first viewer and kills it when
the last one leaves - nothing talks to your cameras while nobody is watching:

```yaml
streams:
  driveway:  exec:aidot-go2rtc 1a2b3c4d... -          # DTLS camera
  frontdoor: exec:aidot-go2rtc 5e6f7a8b... {output}   # SDES camera
```

Use `-` for a DTLS camera and `{output}` for an SDES one - that is the whole
difference, and `--list` told you which is which. (`{output}` is a placeholder
go2rtc fills in with its own publish URL. It is not something you type at a
shell; `-` is the form for that.) go2rtc needs the same `AIDOT_*` environment
variables the CLI does, so set them wherever go2rtc starts.

**3. Use the URL.** go2rtc serves every stream you named at:

```
rtsp://<go2rtc-host>:8554/driveway
```

Check it before wiring anything up to it:

```console
$ ffprobe rtsp://127.0.0.1:8554/driveway
Stream #0:0: Video: h264 (High), yuv420p, 1280x720, 15 fps
Stream #0:1: Audio: aac (LC), 48000 Hz, mono
```

### Details worth knowing

- **Which camera is which.** `--list` reports the transport per device. DTLS is
  the mains A000088; SDES is the A001064 / A001513 family. Passing the wrong
  form is not silent - the CLI tells you which one to use and exits.
- **Audio.** The SDES `{output}` push carries G.711 audio alongside video; the
  DTLS `-` producer carries AAC (48 kHz mono, resampled from the camera's 8 kHz
  A-law, because 8 kHz AAC plays silent in a lot of browsers).
- **A stream that starts with no video** and picks it up a few seconds later is
  normal: the mux waits for a keyframe so the first GOP is decodable. A stream
  that stays audio-only is a camera that never sent one - retry the view.
- **Skipping go2rtc.** `aidot-go2rtc <id> http://127.0.0.1:8555/cam.ts` serves
  MPEG-TS on a listening socket instead, for a consumer that pulls rather than
  spawns. That process has to stay running to keep the port bound, so it holds
  a camera session whether or not anyone is watching. Prefer the `exec:` forms.
- **Pushing to your own RTSP server** rather than go2rtc: `rtsp_push_url=`
  from Python, or an `rtsp://` output on the CLI. Both transports support it.
  A DTLS push transcodes audio to G.711 A-law, matching what SDES has always
  sent - ffmpeg's RTSP muxer will not accept the AAC that MPEG-TS carries.
  `output_path=` records to a file. `Go2RtcClient.rtsp_url(name)` builds the
  address above, and `ensure_stream(name, source)` registers a stream with a
  running go2rtc if you would rather not edit YAML.
- **Home Assistant users need none of this.** The integration manages go2rtc
  itself; this section is for standalone use.

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
stored credentials file. See [`aidot_cameras/credentials.py`](https://github.com/cbrightly/python-aidot-cameras/blob/main/aidot_cameras/credentials.py).

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_USERNAME` | AiDot account username/email. Used with `AIDOT_PASSWORD`. | (none) |
| `AIDOT_PASSWORD` | AiDot account password. Used with `AIDOT_USERNAME`. | (none) |
| `AIDOT_COUNTRY` | Account region/country code. | `US` |

### Camera streaming / tuning

The most useful knobs read by the camera client (`aidot_cameras.camera.client`). Defaults
are chosen to work out of the box; override only when tuning. Finer-grained
internal knobs (audio normalization, keyframe/PLI cadence, retry timing, SDES
audio, idle release, the sprop cache path) are documented in
[`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md#advanced-tuning-environment-variables).

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_VIDEO_DECODER` | Force a video decoder instead of measuring one: a decoder name (`h264_v4l2m2m`), or an acceleration method prefixed with `hwaccel:` (`hwaccel:videotoolbox`). The prefix is required because the two are not interchangeable - VideoToolbox and VAAPI have no decoder to name. | (measured) |
| `AIDOT_DISABLE_HWACCEL` | Keep to software decoding and skip the measurement entirely. | (unset) |
| `AIDOT_MAX_CONCURRENT_OPENS` | Caps how many stream opens run concurrently across all cameras. | `2` |
| `AIDOT_MAX_CONCURRENT_STREAMS` | Caps how many cameras stream at once. | `3` |
| `AIDOT_FAST_CONNECT` | Enable LAN-direct "fast connect" (STUN-only, skips several cloud signaling waits) when truthy. On-LAN only - off-subnet/strict-NAT viewers must leave it off. | unset (off) |
| `AIDOT_SDES_SKIP_TURN_PREALLOC` | Skip the SDES TURN relay pre-allocation (~2-3 s of cold-start latency) so signaling goes straight out with the host candidate. Faster on a LAN, at the cost of no relay fallback for a camera on a different segment / behind strict NAT. Experimental, opt-in (truthy = `1`/`true`/`yes`/`on`). | unset (off) |
| `AIDOT_SDES_ADAPTIVE` | Adaptive fast-with-fallback for the SDES keepalive loop: try the fast path first and fall back to the full relay path if a fast attempt delivers no media. A per-device cache skips the fast attempt on later views once it has failed. Truthy value enables. Ignored for battery cameras, where the fast path cannot win and its short grace truncates the cold start. | unset (off) |
| `AIDOT_SDES_FAST_LIVEPLAY` | Don't block on the `livePlayResp` wait for eligible SDES cameras (~4.5 s faster cold start). Role-reversal models (A001064 PTZ) always excluded for correctness. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_SDES_VIDEO_PT` | Pin the SDES offer to ONE video codec by payload type, so the camera cannot choose. The offer advertises 96 (H264) and 97 (H265) and the camera decides which to send; on an A001064 that means the same request comes back h264 1280x720 most sessions and hevc 2560x1440 occasionally, at a third of the bitrate. Set to `96` for H264 only (measured h264 720p in 4 of 4 sessions). **Do not set it to `97`** - an H265-only offer returned no video at all in 3 of 3 rounds; narrowing to H265 removes the option rather than selecting it. | unset (both offered) |
| `AIDOT_SDES_VIDEO_PT_ORDER` | Reorder the SDES offer's video codec list without narrowing it, as a comma-separated preference list (`97,96`). RFC 3264 makes the `m=video` payload-type list a preference list, most-preferred first, and ours has always read `96 97` - not by decision, it has simply never been varied. Whatever is named leads and the rest is appended, so this can express a preference and can never produce a narrowed or empty video m-line. **Experimental and untested on hardware:** whether the camera acts on the order is the open question, so the default is deliberately unchanged. | unset (`96 97`) |
| `AIDOT_DTLS_FAST_LIVEPLAY` | The DTLS (A000088) analogue: skip the `livePlayReq`-echo and `livePlayResp` waits (the dominant LAN cold-start cost) while keeping the full ICE/TURN/DTLS handshake, so remote/relay viewing is unaffected. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_PERSISTENT_MQTT` | Reuse ONE account-level persistent MQTT connection for commands, attribute fetches, and stream-open signaling (matching the official app) instead of connecting per operation. **On by default** (live soaks cut SDES NO_MEDIA from ~57% to ~11-19%); set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_SERVE_RELAY` | Hold the public stream port via an internal relay that proxies to ffmpeg, so the first (cold) view connects instead of failing while ffmpeg can't pre-bind the port. Set to `0` to serve ffmpeg directly. | `1` (enabled) |
| `AIDOT_ACCEPT_STATIC_VIDEO_PT` | Accept camera video on a STATIC RTP payload type (0-95) that the camera announced in its answer but the offer never carried. One A000088 prepends a bare `m=video ... 0` to its answer and then transmits H.264 on payload type 0; aiortc negotiates dynamic payload types only, so those packets are discarded and the camera serves audio with no video. **Off by default** - nothing has confirmed that payload is H.264 rather than H.265, and SRTP hides it - so turn it on against a camera and read the decoded frame count. `1` enables. | `0` (off) |
| `AIDOT_DTLS_VIDEO_GRACE_S` | How long a connected DTLS session may go without a single video frame before it is torn down and re-opened. A session that receives audio and no video passes every other check the serve loop makes - the peer connection is healthy, ffmpeg respawns for each consumer - so without this it is held open indefinitely while the viewer sees "no video". `0` disables the check. | `30` |
| `AIDOT_DTLS_FUTILE_VIDEO_LIMIT` | Consecutive video-less DTLS sessions after which the serve loop stops re-opening. Noticing alone is not enough: a video-less session is otherwise a clean open, so a loop that simply re-opened would clear its backoff each time and wake the camera every 15 s indefinitely. `0` keeps retrying forever. | `5` |
| `AIDOT_LOGIN_RETRY_LIMIT` | Consecutive failed LAN logins after which a device is left alone (it is retried again the next time something asks for it). Applies to every device, camera or not - the devices that hit this are lights. | `6` |
| `AIDOT_LOGIN_RETRY_CAP_S` | Ceiling on the exponential delay between those retries. | `60` |
| `AIDOT_LOGIN_CONNECT_TIMEOUT_S` | Ceiling on one LAN connect+login attempt. A device that completes the TCP handshake and then stops answering is abandoned and its socket closed rather than parking the attempt forever. | `20` |
| `AIDOT_LIVESTREAM_PARAM` | **No-op (ignored).** Formerly requested the cloud `liveStreamParam` pre-connect for battery cameras. That call provisions the camera toward AWS KVS, so it sends its media there instead of to this library and the live view negotiates and then shows nothing - and battery cameras were the only ones it applied to. Setting it (or `start_keepalive(live_stream_param=True)`) logs one warning and changes nothing. | ignored |

### Security hardening

Opt-in knobs that tighten the camera transport. Defaults preserve current
behavior (the firmware's signaling doesn't carry verifiable material, so strict
modes are off until you pin a value); each emits a one-time warning when left at
the permissive default.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_DTLS_PINNED_FP` | Pin the camera's DTLS certificate `sha-256` fingerprint (colon-separated hex). When set, a camera presenting a different cert fails the handshake instead of being accepted. The camera echoes our own fingerprint over signaling, so without a pin the media channel is **not** authenticated against an on-path MITM. | unset (accept-any + warn) |
| `AIDOT_ALLOW_LAN_SERVE` | Silences the warning emitted when decrypted media is served on a non-loopback bind (e.g. `0.0.0.0`), where any host on the LAN can read the unencrypted stream. Set when an exposed bind is intentional. | unset (warn on non-loopback) |
| `AIDOT_SDES_HOLEPUNCH_HOST` | Override the NAT hole-punch target used when the cloud supplies no TURN entry. By default a STUN packet goes to a hardcoded vendor TURN host; set this to a host of your choice, or empty (`AIDOT_SDES_HOLEPUNCH_HOST=`) to disable the hardcoded fallback entirely. | unset (hardcoded vendor host + warn) |
| `AIDOT_CRED_KEY_FILE` | Path to the Fernet key file for stored credentials. Point it outside the config dir (ideally a separate secret store) so the key isn't co-located with the ciphertext. Applies to the default credentials path only (ignored when an explicit `creds_path` is passed). | `$XDG_CONFIG_HOME/aidot/.key` (falls back to `~/.config/aidot/.key`) |
