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

> **On the "N commits behind" banner.** GitHub still records this repository as
> a fork of upstream, so it permanently shows as behind. That count means
> nothing here: no upstream file is edited or merged, and upstream arrives as an
> ordinary pip dependency. The number that describes an install is the
> *resolved* upstream version, which is a range rather than a pin -- upstream
> shipped two incompatible shapes of the private API this attaches to and both
> are live, so two people on the same release of this library can be running
> different upstream releases. To see yours:
>
> ```
> python -c "from importlib.metadata import version; print(version('python-aidot'))"
> ```
>
> Home Assistant users get both versions in the integration's **Download
> diagnostics** file (integration 2.17.2 and later).

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
(`async_get_event_video_media`); for a camera storing to a card it lists the
on-device recordings (`async_get_sd_recordings`) but cannot play one back, so
such a camera records normally and nothing here can show you the video. On the
reference fleet that is four of seven cameras. Playback from the card is closed
as not feasible; see `docs/CAMERAS.md`, "On-device (SD card) recordings".

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

**That 2:1 is reachable by another route: the codec.** Ten sessions on this
camera on 2026-08-23 separate perfectly by the video payload type the session
negotiated - H264 (pt=96) at 1597-1685 Kbps, H265 (pt=97) at 766-774 Kbps, no
overlap. An H.265 session costs about half an H.264 one, which is the same
ratio the app's SD tap achieves, and unlike a control byte the camera ignores,
the codec is chosen in our own offer.

Two things stand between that and a saving you can switch on:

- **We cannot select it.** Narrowing the offer to 97 does not select it, it
  removes the option - no video at all in 3 of 3 rounds (see
  `AIDOT_SDES_VIDEO_PT` below). Reordering the preference list without
  narrowing it was then tested directly: eight sessions in blocks of two
  returned H265 in 2 of 4 with the reorder and 2 of 4 without, with receipts
  confirming the reordered list reached the SDP. The camera chooses, roughly
  a coin flip, and neither knob moves it; the reorder knob is gone.
- **H.265 may not suit the consumer.** Browser HEVC support over Media Source
  Extensions is far narrower than H.264, and MSE playback is the reason the
  bitrate matters here at all. Halving the bitrate is no help if the client
  that was failing cannot decode the result.

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

Install from PyPI (the simplest, recommended method). **Pass `--pre`** -- the
current camera line is published as pre-releases, and without it pip resolves
`0.17.2`, which predates the whole camera-streaming line:

```bash
# lights + camera cloud/control only:
pip install --pre python-aidot-cameras
# add live WebRTC streaming, snapshots, and two-way audio:
pip install --pre "python-aidot-cameras[webrtc]"
```

Leaving `--pre` off is not a conservative choice, it is an old one. `0.17.2`
is the newest version pip considers final, and it is from 2026-08-08: SDES
sessions on it stop after about eighty seconds (the data channel never
acknowledged what the camera sent, so the camera dropped the session), and
Home Assistant restarts a stream roughly once a minute on it (a muxing hop
lost its timestamps). Both were fixed in the `1.0.0b` line and neither fix
can reach you without `--pre`.

`0.17.2` stays on PyPI rather than being yanked because yanking it would move
existing installs back to `0.17.1`, which is worse. It will stop being what a
bare `pip install` picks when `1.0.0` is cut final.

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

### What it does

These cameras do not speak RTSP. They stream over WebRTC, negotiated through
AiDot's cloud with a per-session key exchange, which is why you cannot simply
point VLC or Frigate at one and get a picture.

`aidot-go2rtc` does that negotiation for you and hands the result out as
something ordinary: an MPEG-TS stream on stdout, an HTTP endpoint, or an RTSP
push into [go2rtc](https://github.com/AlexxIT/go2rtc). One camera per process.
From there anything that reads RTSP or TS can consume it.

### Why you would use it

- **You want these cameras in something that is not Home Assistant** -- Frigate,
  a standalone go2rtc, an NVR, a browser, `ffmpeg` writing to disk.
- **You want the streaming stack outside Home Assistant's Python environment,**
  so its dependencies cannot collide with Home Assistant's pins.
- **You are debugging.** `--list` shows every camera and which transport it uses,
  and a single camera can be streamed in isolation with the env knobs below,
  which is far easier to reason about than the same thing inside Home Assistant.

### When you do not need it

**If you use Home Assistant, install the integration instead** -- it does all of
this for you, and running both at once means two clients competing for the same
camera. A camera serves a limited number of concurrent viewers and holds a slot
for about 120 s after one leaves, so the loser gets a clean handshake and then no
media at all. The limit belongs to the device, so a second AiDot account with the
house shared to it does not avoid it.

### Installing

Installing the package provides the `aidot-go2rtc` console script; for an
isolated tool install use pipx or uv:

```bash
pipx install "python-aidot-cameras[webrtc]"
# or:
uv tool install "python-aidot-cameras[webrtc]"

export AIDOT_USERNAME=... AIDOT_PASSWORD=...   # or AIDOT_TOKEN_FILE, see below
aidot-go2rtc --list                  # discover cameras + their transport

# Stream one camera. The second argument is WHERE the media should go:
aidot-go2rtc <device_id> -                              # to stdout
aidot-go2rtc <device_id> '{output}'                     # go2rtc fills this in
aidot-go2rtc <device_id> http://127.0.0.1:8555/cam.ts   # serve, then pull
```

**Which form to use.** `--list` tells you each camera's transport, and that
decides it:

| your camera | use | why |
| --- | --- | --- |
| **DTLS** (mains, e.g. A000088) | `'{output}'` or `-` | `{output}` is published by the library itself (direct publish, on by default): the camera's 8 kHz A-law plus an in-process AAC track, no ffmpeg. `-` writes the muxed MPEG-TS with its 48 kHz AAC to stdout instead |
| **SDES** (the A001513 and A001064 families) | `'{output}'` | these stream by pushing RTSP; there is nothing to read from stdout |
| either, with `AIDOT_DIRECT_PUBLISH=0` | `'{output}'` | the push goes through ffmpeg instead, which has to transcode audio down to 8 kHz G.711. A session that negotiates H.265 takes this path whatever the setting |
| either, if you want to pull | an `http://` URL | the process serves there and waits for a consumer to connect |

`'{output}'` is a placeholder that go2rtc substitutes with the stream's own push
URL when it launches the process from an `exec:` source -- quote it so your
shell does not eat the braces. It is not something to type at a shell: run by
hand the literal text is taken as a publish target and the push fails. To try
the push path by hand, put a real `rtsp://` URL there instead. Both transports
can push, so `{output}` works on any camera; the table is about which gives the
better result, not what is possible.

### Wiring it into go2rtc

`--list` prints a paste-ready `streams:` block for every camera on the account,
so you rarely have to write this by hand. It looks like this:

```yaml
streams:
  front_door: exec:aidot-go2rtc 1234abcd... {output}
  driveway:   exec:aidot-go2rtc 5678efgh... -
```

go2rtc launches one process per stream, on demand, and substitutes `{output}`
with that stream's push URL.

The process needs the same `AIDOT_*` environment variables you used above. It is
launched as a child of go2rtc, so the practical answer is to put them where
go2rtc itself gets its environment: export them in the shell before starting it,
or set them in its service unit or container definition. If a stream fails
immediately with an authentication error, that is the thing to check first.

**ffmpeg must be on PATH.** pip cannot install a system binary, and the
library still needs one: recordings, snapshots, the `-` stdout form, `http://`
pull serves and any session that negotiates H.265 run through an ffmpeg
subprocess, and it is the DTLS fallback when a serve port cannot be bound. A
live `{output}` push does not use it (next paragraph), and a DTLS `-` serve
sends the muxed MPEG-TS straight to the consumer, because that hop was the only
component in the chain that lost timestamps.

**Live pushes skip ffmpeg: direct publish is on by default** (since 1.0.0rc44;
`AIDOT_DIRECT_PUBLISH=0` restores the ffmpeg path). An `rtsp://` / `{output}`
push is published into go2rtc by the library
itself - no ffmpeg process per stream - for **both** transports, so a DTLS
camera can use `{output}` too. It takes H.264 sessions only; a session that
negotiates H.265 keeps the ffmpeg serve. Audio goes out as the camera's own
G.711 A-law plus an in-process AAC track (see "Audio" under
[Getting an RTSP URL](#getting-an-rtsp-url)).
ffmpeg is still used for recordings and snapshots; see
[`docs/DESIGN-direct-publish.md`](docs/DESIGN-direct-publish.md).

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
    output_path="/tmp/live.ts",  # record, or
    rtsp_push_url="rtsp://127.0.0.1:8554/cam",  # push to go2rtc
)
```

Two-way (push-to-talk) audio:

```python
session = await device_client.async_open_webrtc_stream(..., talk=True)
await session.async_start_talk(
    pcm_provider
)  # provider() -> 320B s16le PCM (20ms @ 8kHz), or None
# ... speak ...
await session.async_stop_talk()
```

See [`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md) for the full camera API (streaming,
snapshots, recordings, motion polling, two-way audio, and LAN-direct media).

## Camera controls and state

Controls are on the camera device client. Writes are confirmed by reading the
value back on each model that offers them, because this firmware acknowledges
writes it then ignores.

```python
await device_client.async_set_motion_detection(True)
await device_client.async_set_night_vision("auto")  # auto | on | off
await device_client.async_set_speaker_volume(50)
await device_client.async_reboot()  # see below
```

### Reboot

```python
sent = await device_client.async_reboot()
```

`async_reboot` sends the same `devActionReq({action: "RebootFunc", in: []})` the
vendor app's camera settings page sends. Two things to know:

* **It reports that the request was SENT, not that the camera rebooted.** A
  reboot succeeds by taking the camera off the network, so waiting for an
  acknowledgement would report a working reboot as a failure. Do not read
  `True` as "it came back".
* **It refuses when the cloud explicitly reports the camera offline**, mirroring
  the app, which only offers the button on a reachable device.

The camera is away for a few seconds afterwards, during which a viewer sees a
session that connects and delivers nothing.

### Sound detection

The cameras can listen for glass breaking, a smoke alarm, a baby crying and a
dog barking.

```python
flags = await device_client.async_get_sound_detection()
# {'sound_enable': False, 'all_sound': True, 'glass_Break': False,
#  'smoke_T3': False, 'smoke_T4': False, 'baby_cry': False}

await device_client.async_set_sound_detection("glass_Break", True)
```

The keys come from the camera, not from a fixed table, so a model reporting a
detector not listed above still works. A write is read-modify-write: the current
list is fetched and sent back with one flag changed, rather than against a
payload schema invented here.

**`None` means unknown, never "all off".** A camera that does not answer -- a
battery camera asleep, or a model without the feature -- returns `None`, and
treating that as a set of disabled detectors would report a state the camera
never claimed. A write refuses outright when there is no current list to echo.

### WiFi and SD card

```python
await device_client.async_get_wifi_info()  # {'ssid': ..., 'rssi': 63}
await device_client.async_get_sd_card_info()  # {'present': True, 'total': 29838,
#  'used': 28848, 'raw': [...]}
```

Only confirmed fields are named. `SDcardBaseInfo` answers positionally and its
fourth value was nearly called "free" -- but on a card reading 29838 total and
28848 used it returns 3, which is not a remainder (990) and looks like a
percentage. It stays unnamed in `raw` rather than shipping a field that reports
3 MB free on a 30 GB card. The units of `total` and `used` are unconfirmed too,
so the numbers pass through exactly as the camera reports them.

### Reading anything else the camera answers

```python
out = await device_client.async_query_device_action("getWorkMode")
```

`async_query_device_action` sends a read-only `devActionReq` and returns the
camera's `out` payload. The cameras answer more actions than this library wraps
-- `getWorkMode`, `getAutoAlarm`, `getRoiHuman` (human/vehicle/package
detection), `getRoiPrivacy` and others. `None` means no reply, which is not the
same as "unsupported".

**Do not use it to send actions that change something** unless you know the
payload shape. `SDcardFormatFunc` is one of the actions the cameras accept.

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
$ pip install --pre "python-aidot-cameras[webrtc]"
$ export AIDOT_USERNAME='you@example.com' AIDOT_PASSWORD='...' AIDOT_COUNTRY=US
$ aidot-go2rtc --list
1a2b3c4d...  LK.IPC.A000088  DTLS (stdout)
5e6f7a8b...  LK.IPC.A001064  SDES (rtsp-push)
```

**2. Add one line per camera to `go2rtc.yaml`.** Both forms make go2rtc the
parent process, so it starts the stream on the first viewer and kills it when
the last one leaves - nothing talks to your cameras while nobody is watching:

```yaml
streams:
  driveway:  exec:aidot-go2rtc 1a2b3c4d... -          # DTLS camera (stdout form; {output} works too)
  frontdoor: exec:aidot-go2rtc 5e6f7a8b... {output}   # SDES camera
```

`{output}` works for both transports: the library publishes the stream into
go2rtc itself (direct publish, on by default since 1.0.0rc44). A DTLS camera
can also take `-`, which hands go2rtc the muxed MPEG-TS on stdout; an SDES
camera cannot, and `--list` told you which is which. (`{output}` is a
placeholder go2rtc fills in with its own publish URL. It is not something you
type at a shell; `-` is the form for that.) go2rtc needs the same `AIDOT_*` environment
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
- **Audio.** A `{output}` push (direct publish, the default) carries the
  camera's own **PCMA (G.711 A-law, 8 kHz)** first - WebRTC viewers play it
  natively and nothing is transcoded - and, unless `AIDOT_PUBLISH_AAC=0`, an
  **AAC-LC 48 kHz mono** track encoded in-process; an AAC-only consumer (for
  example Home Assistant's HLS player) selects it with `?audio=aac`. The `-`
  form carries AAC at 48 kHz mono, resampled from the camera's 8 kHz A-law,
  because 8 kHz AAC plays silent in a lot of browsers and browsers on the MSE
  path have no mapping for G.711 at all. Audio is on by default; see
  `AIDOT_SDES_SERVE_AUDIO` to turn it off. No ffmpeg transcode
  source is needed for the AAC - see
  [`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md#an-aac-transcoding-source-makes-every-player-crawl)
  for why the old `ffmpeg:<stream>#audio=aac` advice made playback crawl.
  The AAC track is on by default since 1.0.0rc44. A camera whose audio is
  mu-law (PCMU) gets no AAC track either way.
- **A stream that starts with no video** and picks it up a few seconds later is
  normal: the mux waits for a keyframe so the first GOP is decodable. A stream
  that stays audio-only is a camera that never sent one - retry the view.
- **Skipping go2rtc.** `aidot-go2rtc <id> http://127.0.0.1:8555/cam.ts` serves
  MPEG-TS on a listening socket instead, for a consumer that pulls rather than
  spawns. That process has to stay running to keep the port bound, so it holds
  a camera session whether or not anyone is watching. Prefer the `exec:` forms.
- **Pushing to your own RTSP server** rather than go2rtc: `rtsp_push_url=`
  from Python, or an `rtsp://` output on the CLI. Both transports support it,
  and by default the library publishes it directly, with the audio described
  above. Only on the `AIDOT_DIRECT_PUBLISH=0` ffmpeg path does a DTLS push
  transcode audio to G.711 A-law - ffmpeg's RTSP muxer will not accept the AAC
  that MPEG-TS carries.
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

The ones below are the ones a user may set. The rest - tuning knobs added
during investigations, most of them for one camera model, all of them with
defaults that were measured - are listed in
[docs/ENVIRONMENT.md](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/ENVIRONMENT.md),
with the release each may be removed in.

The library reads the following environment variables.

### Credentials

Used by the credential helper (`aidot_cameras.credentials`); they take priority over any
stored credentials file. See [`aidot_cameras/credentials.py`](https://github.com/cbrightly/python-aidot-cameras/blob/main/aidot_cameras/credentials.py).

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_USERNAME` | AiDot account username/email. Used with `AIDOT_PASSWORD`. | (none) |
| `AIDOT_PASSWORD` | AiDot account password. Used with `AIDOT_USERNAME`. | (none) |
| `AIDOT_COUNTRY` | Account region/country code. | `US` |

### Camera streaming / tuning

The most useful knobs read by the camera client (`aidot_cameras.camera.client`). Defaults
are chosen to work out of the box; override only when tuning. Every other
knob is in
[`docs/ENVIRONMENT.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/ENVIRONMENT.md).

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_VIDEO_DECODER` | Force a video decoder instead of measuring one: a decoder name (`h264_v4l2m2m`), or an acceleration method prefixed with `hwaccel:` (`hwaccel:videotoolbox`). The prefix is required because the two are not interchangeable - VideoToolbox and VAAPI have no decoder to name. | (measured) |
| `AIDOT_DISABLE_HWACCEL` | Keep to software decoding and skip the measurement entirely. | (unset) |
| `AIDOT_MAX_CONCURRENT_STREAMS` | Caps how many cameras stream at once. | `3` |
| `AIDOT_SDES_CONNECTION_MODE` | Which media path the SDES offer proposes: `auto` (default - every reachable candidate, and ICE priority prefers the LAN with the relay as last resort; measured on the full fleet, six of seven cameras stream direct and only the unit with no route to us rides the relay), `lan` (no relay at all, though battery cameras keep the relay regardless because a cloud-woken camera has no other path back), or `relay` (EXPERIMENTAL, and on the reference fleet it does not steer: even with `c=`/`m=` moved to the relay allocation and the WAN permission pre-installed, an A001064 and an A001513 both dialed our host address directly, learning it from our own ICE probes - forcing the relay for real would mean suppressing every direct-path outbound, which is unbuilt). Per-open `sdes_connection_mode` beats the env. Every session reports the path its media actually took as `media_stats().media_path` (`direct`/`relay`). | unset (`auto`) |
| `AIDOT_SDES_VIDEO_PT` | Pin the SDES offer to ONE video codec by payload type, so the camera cannot choose. The offer advertises 96 (H264) and 97 (H265) and the camera decides which to send; on an A001064 that means the same request comes back h264 1280x720 most sessions and hevc 2560x1440 occasionally, at a third of the bitrate. Set to `96` for H264 only (measured h264 720p in 4 of 4 sessions). **Do not set it to `97`** - an H265-only offer returned no video at all in 3 of 3 rounds; narrowing to H265 removes the option rather than selecting it. | `96` (H.264; `none` leaves the camera its choice) |
| `AIDOT_PERSISTENT_MQTT` | Reuse ONE account-level persistent MQTT connection for commands, attribute fetches, and stream-open signaling (matching the official app) instead of connecting per operation. **On by default** (live soaks cut SDES NO_MEDIA from ~57% to ~11-19%); set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_DIRECT_PUBLISH` | **On by default** (since 1.0.0rc44; set `0` to turn it off). Publish a live `rtsp://` push into go2rtc from the library itself instead of through ffmpeg, for both transports: SDES hands the bridge's plain RTP straight to an RTSP publisher, DTLS packetizes the tapped H.264 and A-law directly. No ffmpeg process per stream, audio is PCMA plus an in-process AAC track, packets put back in order before publishing, and audio attached from the camera's negotiated answer rather than waiting to observe a packet. Recordings, snapshots and `-`/`http://` serves are unchanged. Takes H.264 sessions only - an H.265 session keeps the ffmpeg serve. Live-validated with H.264 on A000088, A001064 and A001513 (see `docs/DESIGN-direct-publish.md`). Truthy (`1`/`true`/`yes`/`on`) enables. | `1` (on) |
| `AIDOT_PUBLISH_AAC` | Whether a direct publish adds an AAC-LC 48 kHz mono track after the A-law one, encoded in-process, so an AAC-only consumer (Home Assistant's HLS player, `camera.record`) has sound; select it with go2rtc's `?audio=aac`. Costs one AAC encode per streaming camera for as long as its direct publish runs. A camera whose audio is mu-law (PCMU) gets no AAC track regardless. **On by default** (since 1.0.0rc44); set `0`/`false`/`no`/`off` to turn it off. | `1` (on) |
| `AIDOT_HLS_DIRECT_TS` | **On by default** (since 1.0.0rc44; set `0` to turn it off). Give each DTLS camera a library-muxed MPEG-TS on a loopback listener (`CameraMixin.hls_ts_url()`), for Home Assistant's HLS view and recordings to read instead of go2rtc's RTSP. go2rtc re-bases each track for each consumer, so a reader that joins a running stream had its sound 0.1-0.75 s late, differently each time; this TS carries the publisher's video and AAC on one clock, so every reader gets them in the same step, whenever it joins. Needs `AIDOT_DIRECT_PUBLISH` and `AIDOT_PUBLISH_AAC`. An SDES camera gets a URL only on a model whose media the bridge decrypts (A001064, A001513) with its offer pinned to H.264 (`AIDOT_SDES_VIDEO_PT=96`), so that every session feeds it; pulled (non-publishing) sessions get none. When a camera's SPS changes (the A001064 changes it between sessions), the TS's readers are disconnected so they rebuild their decoder. Each camera's URL carries a secret made per listener, as `?auth=<secret>` (Home Assistant masks that query parameter when it logs a stream's URL); a request without it gets 404. For the owner's own tools, the listener's URL for any camera (`http://127.0.0.1:<port>/{name}.ts?auth=<secret>`, where `{name}` is `aidot_<first 12 of the device id>`) is left in `hls-ts-url` in the library's state directory (`AIDOT_SPROP_DIR`), owner-readable only, while the listener runs. A reader of the TS counts as a viewer. Truthy (`1`/`true`/`yes`/`on`) enables. | `1` (on) |
| `AIDOT_LOGIN_RETRY_LIMIT` | Consecutive failed LAN logins after which a device is left alone: discovery and the library's own retry loop stop kicking it, and when something else still asks for a login (Home Assistant's light poll does, every five minutes) the attempt is made quietly - upstream's per-attempt warning is dropped - until the device's address changes or a login gets through. One warning marks the moment. Applies to every device, camera or not - the devices that hit this are lights on another subnet. | `6` |

### Security hardening

Opt-in knobs that tighten the camera transport. Defaults preserve current
behavior (the firmware's signaling doesn't carry verifiable material, so strict
modes are off until you pin a value); each emits a one-time warning when left at
the permissive default.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_ALLOW_LAN_SERVE` | Silences the warning emitted when decrypted media is served on a non-loopback bind (e.g. `0.0.0.0`), where any host on the LAN can read the unencrypted stream. Set when an exposed bind is intentional. | unset (warn on non-loopback) |
| `AIDOT_CRED_KEY_FILE` | Path to the Fernet key file for stored credentials. Point it outside the config dir (ideally a separate secret store) so the key isn't co-located with the ciphertext. Applies to the default credentials path only (ignored when an explicit `creds_path` is passed). | `$XDG_CONFIG_HOME/aidot/.key` (falls back to `~/.config/aidot/.key`) |
| `AIDOT_PLAYBACK_TLS_VERIFY` | Set to `1` to require full certificate and hostname verification on the TLS connection to a camera's playback server. The camera presents a self-signed certificate, so this needs a trust anchor it chains to; unset, verification is skipped and a one-time warning is logged. | unset (skip verification, warn once) |
