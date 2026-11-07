# python-aidot-cameras

[![PyPI version](https://img.shields.io/pypi/v/python-aidot-cameras)](https://pypi.org/project/python-aidot-cameras/)
[![Python versions](https://img.shields.io/pypi/pyversions/python-aidot-cameras)](https://pypi.org/project/python-aidot-cameras/)
[![License: MIT](https://img.shields.io/pypi/l/python-aidot-cameras)](https://github.com/cbrightly/python-aidot-cameras/blob/main/LICENSE)

Control AiDot / Leedarson WiFi lights **and cameras** from Python: live WebRTC
video (DTLS-SRTP and SDES-SRTP cameras), snapshots, PTZ, camera controls, cloud
recordings, the recordings on a camera's own SD card, motion events and two-way
(push-to-talk) audio. It also ships `aidot-go2rtc`, a command that turns a
camera into an ordinary RTSP or MPEG-TS stream for go2rtc, Frigate or an NVR.

- **Home Assistant users:** install the companion integration,
  [`cbrightly/hass-aidot-cameras`](https://github.com/cbrightly/hass-aidot-cameras),
  instead. It installs this library and manages go2rtc for you; you need nothing
  on this page.
- **Upstream:** this extends the lights-only
  [`python-aidot`](https://github.com/AiDot-Development-Team/python-AiDot), which
  it installs as a dependency rather than forking. Upstream owns the `aidot`
  import name and handles lights, plugs and switches; everything here lives
  under `aidot_cameras`. GitHub still shows this repository as a fork "N commits
  behind" upstream; that count means nothing, as no upstream file is edited.
  How a new upstream release is taken in: [`docs/UPSTREAM.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/UPSTREAM.md).

Requires Python 3.11 or later. The supported cameras are listed in
[`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md#supported-cameras):
the M3 Pro (A000088, DTLS), the L2 battery camera (A001513, SDES) and the PTZ
(A001064, SDES) are tested live; other `LK.IPC.*` models fall through to the
generic SDES path.

## Install

**Pass `--pre`.** The camera line is published as 1.0.0 release candidates;
without `--pre`, pip resolves `0.17.2` (2026-08-08), which predates them and
has known faults fixed since (SDES sessions that stop after about eighty
seconds, and a Home Assistant stream restart about once a minute). That stays
true until 1.0.0 is final.

```bash
# lights + camera cloud/control only:
pip install --pre python-aidot-cameras
# add live streaming, snapshots and two-way audio (aiortc, av, ...):
pip install --pre "python-aidot-cameras[webrtc]"
# the CLI as an isolated tool:
pipx install --pip-args=--pre "python-aidot-cameras[webrtc]"
uv tool install --prerelease=allow "python-aidot-cameras[webrtc]"
```

The latest unreleased code:
`pip install "python-aidot-cameras[webrtc] @ git+https://github.com/cbrightly/python-aidot-cameras"`.

**ffmpeg must be on PATH** (`apt install ffmpeg`, `brew install ffmpeg`); pip
cannot install it. Recordings, snapshots, the CLI's `-` and `http://` forms and
any session that negotiates H.265 run through an ffmpeg subprocess. A live
`rtsp://` push does not (see "Direct publish" below).

To see which upstream `python-aidot` an install resolved (it is a range, not a
pin, and Home Assistant pins its own):
`python -c "from importlib.metadata import version; print(version('python-aidot'))"`.
Home Assistant users get both versions in the integration's **Download
diagnostics** file.

## How it works

```
  AiDot cloud                       python-aidot-cameras                      consumers
  -----------                       --------------------                      ---------
  MQTT signaling  <--------------->  open a session: offer, answer,
  ICE / TURN                         ICE, keys (DTLS or SDES)
                                              |
  camera  ====SRTP over the LAN====>  decrypt, reorder, count   ---(RTSP push)---> go2rtc ---> WebRTC view, rtsp:// URL
          (relay only when there is       |                                                   (Home Assistant, Frigate,
           no route back to us)           +---(MPEG-TS, loopback)-------------------------->  HA HLS view, camera.record
                                          +---(JPEG)----------------------------------------> snapshots
                                          +---(.ts / .mp4 copy)-----------------------------> recordings
                                          +<--(PCM, 20 ms)----------------------------------- two-way audio
```

The cameras have no RTSP, ONVIF or local HTTP endpoint. The cloud brokers the
WebRTC handshake and never carries the media: on the same network the camera
sends straight to this library, and only a camera with no route back rides the
vendor's relay. The library decrypts once and fans the camera's own packets
out without re-encoding.

## Quick start (Python)

Import from `aidot_cameras`, never from `aidot`: upstream's `AidotClient` is
lights-only, and importing it gives a client with no camera surface and no
error to say why.

```python
import aiohttp
from aidot_cameras.client import AidotClient
from aidot_cameras.const import CONF_DEVICE_LIST, CONF_NAME

async with aiohttp.ClientSession() as http:
    client = AidotClient(http, country_code="US", username=..., password=...)
    await client.async_post_login()
    devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
    cam = next(d for d in devices if d.get(CONF_NAME) == "Front Door")
    device_client = client.get_device_client(cam)  # a CameraDeviceClient
```

`get_device_client` returns a `CameraDeviceClient` for a camera; the lights
clients (`DeviceClient`, `LightDeviceClient`) carry none of the camera surface,
so a `hasattr` probe against the wrong one fails silently.

Open a live stream:

```python
# DTLS cameras (A000088): decoded frames in-process
session = await device_client.async_open_webrtc_stream(on_frame=cb, timeout=30.0)

# any camera: record to a file, or push to an RTSP server such as go2rtc
session = await device_client.async_open_webrtc_stream(
    output_path="/tmp/live.ts",  # or rtsp_push_url="rtsp://127.0.0.1:8554/cam"
)
await session.stop()
```

`on_frame` receives a PyAV `av.VideoFrame` and is **DTLS-only**: SDES cameras
(A001513, A001064) are decoded out of process, so it never fires for them and
no error is raised.

Two-way (push-to-talk) audio, on both transports:

```python
session = await device_client.async_open_webrtc_stream(..., talk=True)
await session.async_start_talk(pcm_provider)  # provider() -> 320 B s16le PCM (20 ms @ 8 kHz), or None
await session.async_stop_talk()
# or, open + play + tear down in one call:
await device_client.async_speak(pcm_provider, max_seconds=30)
```

If you persist the login, serialize `client.serializable_login_info()`, not
`client.login_info` (which holds a live lock once the persistent MQTT
connection exists).

The full camera API (streaming, recordings, motion polling, talk, SD playback)
is in [`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md);
what is public and stable is in
[`docs/API-STABILITY.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/API-STABILITY.md).

## Standalone use: `aidot-go2rtc` and an RTSP URL

`aidot-go2rtc` does the cloud handshake for one camera per process and hands
the result out as an RTSP push (into go2rtc or any RTSP server), MPEG-TS on
stdout, or an HTTP endpoint. Use it for Frigate, a standalone go2rtc, an NVR or
debugging. **If you run Home Assistant, use the integration instead**: running
both means two clients competing for a camera that serves a limited number of
viewers (and holds a slot for about 120 s after one leaves), so the loser gets
a clean handshake and no media. A second AiDot account with the house shared to
it does not avoid that; the limit is the device's.

**1. Sign in and list your cameras.**

```console
$ export AIDOT_USERNAME='you@example.com' AIDOT_PASSWORD='...' AIDOT_COUNTRY=US
$ aidot-go2rtc --list
1a2b3c4d...  LK.IPC.A000088  DTLS (stdout)
5e6f7a8b...  LK.IPC.A001064  SDES (rtsp-push)
```

`--list` also prints a paste-ready go2rtc `streams:` block. A dedicated login
is recommended for long-running use, so it does not fight Home Assistant or the
phone app over a rotating refresh token; set `AIDOT_TOKEN_FILE` to cache the
login across restarts. `aidot-go2rtc --help` lists every option.

**2. Add one line per camera to `go2rtc.yaml`.** go2rtc starts the process on
the first viewer and stops it when the last one leaves, so nothing talks to a
camera nobody is watching:

```yaml
streams:
  driveway:  exec:aidot-go2rtc 1a2b3c4d... -          # DTLS camera
  frontdoor: exec:aidot-go2rtc 5e6f7a8b... {output}   # SDES camera (works for DTLS too)
```

| Output argument | Works on | What you get |
| --- | --- | --- |
| `{output}` | any camera | go2rtc fills in its own publish URL. The library publishes into it directly (no ffmpeg): the camera's G.711 A-law plus an in-process AAC track. Quote it in a shell; run by hand, the literal text fails, so give a real `rtsp://` URL there instead. |
| `-` | DTLS only | MPEG-TS with 48 kHz AAC on stdout. An SDES camera given `-` exits with a message telling you to use `{output}`. |
| `http://127.0.0.1:8555/cam.ts` | any camera | the process serves MPEG-TS and waits for a consumer to pull. It must keep running to hold the port, so it holds a camera session whether or not anyone watches; prefer the `exec:` forms. |

go2rtc must have the same `AIDOT_*` variables in its environment (its shell,
service unit or container definition). A stream that fails at once with an
authentication error usually means they are missing.

**3. Use the URL** go2rtc serves, and check it first:

```console
$ ffprobe rtsp://127.0.0.1:8554/driveway
Stream #0:0: Video: h264 (High), yuv420p, 1280x720, 15 fps
Stream #0:1: Audio: aac (LC), 48000 Hz, mono
```

Worth knowing:

- **Direct publish** (on by default; `AIDOT_DIRECT_PUBLISH=0` restores ffmpeg)
  publishes an `rtsp://` / `{output}` push from the library itself, for both
  transports and H.264 sessions; a session that negotiates H.265 keeps the
  ffmpeg serve. Design and measurements:
  [`docs/DESIGN-direct-publish.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/DESIGN-direct-publish.md).
- **Audio.** A push carries the camera's own PCMA (G.711 A-law, 8 kHz) first,
  which WebRTC viewers play untranscoded, and (unless `AIDOT_PUBLISH_AAC=0`)
  an AAC-LC 48 kHz mono track for AAC-only consumers such as Home Assistant's
  HLS player, selected with go2rtc's `?audio=aac`. A camera whose audio is
  mu-law (PCMU) gets no AAC track. Do not add an `ffmpeg:<stream>#audio=aac`
  source in go2rtc: it makes every player crawl
  ([why](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md#an-aac-transcoding-source-makes-every-player-crawl)).
  A DTLS push is always published this way: `AIDOT_DIRECT_PUBLISH=0` applies
  to SDES pushes only.
- **A stream that starts with no video** and picks it up a few seconds later is
  normal: the mux waits for a keyframe. One that stays audio-only is a camera
  that never sent one; retry the view.
- **From Python:** `rtsp_push_url=` pushes, `output_path=` records.
  `Go2rtcClient.rtsp_url(name)` builds the URL above, and
  `go2rtc.async_ensure_stream(client, name, source)` registers a stream with a
  running go2rtc without editing YAML (`async_remove_stream` drops it). Use
  these rather than the client's raw `ensure_stream`: they queue every write to
  one go2rtc behind one lock (go2rtc 1.9.9 tears its config file on
  overlapping writes) and tell a rejected PUT on a stream go2rtc still holds
  (`UNCHANGED`) from a real failure. `source=None` defines a placeholder for a
  stream you will publish into; `recreate=True` deletes it first.

## Camera features

Controls are on the camera device client. Writes are confirmed by reading the
value back where the model offers it, because this firmware acknowledges writes
it then ignores.

```python
await device_client.async_set_motion_detection(True)
await device_client.async_set_night_vision("auto")  # auto | on | off
await device_client.async_set_speaker_volume(50)
```

### Reboot

`sent = await device_client.async_reboot()` sends the reboot request the AiDot
app's camera settings page sends. It reports that the request was **sent**, not
that the camera rebooted (a reboot succeeds by dropping off the network), and
it refuses when the cloud reports the camera offline. For a few seconds after,
a viewer gets a session that connects and delivers nothing.

### Sound detection

```python
flags = await device_client.async_get_sound_detection()
# {'sound_enable': False, 'all_sound': True, 'glass_Break': False,
#  'smoke_T3': False, 'smoke_T4': False, 'baby_cry': False}
await device_client.async_set_sound_detection("glass_Break", True)
```

The keys come from the camera, so a detector not listed here still works. A
write is read-modify-write and refuses when there is no current list to echo.
**`None` means unknown, never "all off"**: a sleeping battery camera or a model
without the feature returns `None`.

### Motion events

Cameras do not push motion to a passive subscriber, so motion is polled from the
cloud event list:

```python
await device_client.async_start_motion_polling(callback, interval=30.0)
await device_client.async_stop_motion_polling()
```

### Recordings

Whether a camera records to the cloud or to its own SD card is per camera, not
per model (the device reports it as `IsSupportPlayback`).

- **Cloud:** `async_get_cloud_recordings` lists clips, and
  `async_get_event_video_media` resolves a playable HLS URL for one.
- **SD card:** `async_get_sd_recordings` lists what the card holds and
  `async_open_sd_playback` plays one, on all three tested models. An SDES camera
  serves it like a live stream; a DTLS camera (A000088) records it to a `.ts`
  file.

```python
listing = await device_client.async_get_sd_recordings(session=live_session, days=7)
await device_client.async_stop_streaming()  # the camera has one media slot
session = await device_client.async_open_sd_playback(
    listing.records[-1], output_path="/tmp/clip.ts"
)
await session.async_sd_seek(20)  # also async_sd_pause() / async_sd_resume()
await session.stop()
```

A listing never opens a session of its own: it rides the one you pass (or the
client's current one) and returns `None` when there is none. `answered=False`
means the camera did not reply, which says nothing about the card; only
`answered=True` with no records means an empty card. Details, the three
outcomes and the playback caveats: [`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md#on-device-sd-card-recordings).

### WiFi, SD card and other reads

```python
await device_client.async_get_wifi_info()  # {'ssid': ..., 'rssi': 63}
await device_client.async_get_sd_card_info()  # {'present': True, 'total': 29838, 'used': 28848, 'raw': [...]}
out = await device_client.async_query_device_action("getWorkMode")
```

Only confirmed fields are named: the units of `total` and `used` are
unconfirmed, so they pass through as the camera reports them, and a fourth
value that looks like a percentage stays unnamed in `raw`.
`async_query_device_action` sends a read-only action and returns the camera's
`out` payload (`getWorkMode`, `getAutoAlarm`, `getRoiHuman`, `getRoiPrivacy`
and others); `None` means no reply, not "unsupported". **Do not use it for
actions that change something** unless you know the payload shape:
`SDcardFormatFunc` is one the cameras accept.

## Local (LAN) control and account ownership

Local control over the devices' TCP port 10000 (lights, plugs and this
package's `CameraLanClient`) is accepted **only for the account that owns the
devices**. A member of a shared home gets a full device list from the cloud,
credentials included, and is then refused at the device. If local control never
engages, check which account you signed in as first: everything still works
through the cloud. Camera live video does not use this path.

The refusal is easy to misread, because the code varies by firmware family:

| Model | Ack |
| --- | --- |
| `LK.light.A001493` | 400 `not equal abort user id or password` (it is the user id that does not match) |
| `LK.light.A001497`, `LK.plug.A001535` | 4354 `fail` |
| `LK.IPC.A000088` | 4352 `fail` |

## Troubleshooting

### Reading the health line

Every live session logs one INFO line a minute (`AIDOT_HEALTH_LINE_S`, `0`
turns it off). A DTLS camera's looks like this:

```
health 20s: packets=1686 (84.1/s) lost=0 (0.0%) stall=0.0s path=direct viewers=present frames=300 (15.0/s) resent=207 keyframes=7 max_keyframe_gap=44
```

Each figure covers that period alone:

| Field | Meaning |
| --- | --- |
| `packets` | RTP packets that arrived, with the rate. SDES cameras also report `bytes`; DTLS cameras report `lost`. |
| `stall` | Seconds since the last media packet. Rising with `lost=0` means the camera stopped sending, not that the link dropped packets. A DTLS session with no video for `AIDOT_DTLS_VIDEO_GRACE_S` (30 s) is re-opened on its own. |
| `path` | `direct` (over the LAN) or `relay` (the vendor's TURN relay: a camera with no route back, or on another subnet). |
| `viewers` | `present`, `absent`, or `unknown` when the consumer cannot be counted. A battery camera nobody watches is released after its idle window. |
| `frames`, `keyframes`, `max_keyframe_gap`, `resent` | DTLS only: pictures served, keyframes among them, the longest run between keyframes since the session started, and frames the camera sent again (about 40%; every consumer drops the repeats). |

So a pasted log says whether the link was lossy, the picture froze, or nobody
was watching. A report that fails is dropped and never ends the session.

### Other behaviour that is not a fault

These are covered in
[`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md):
the A001064 using about a quarter more bandwidth than the AiDot app, a failed
go2rtc registration that silently downgrades to HLS, a battery camera's first
open after a long sleep, and the SD playback limits.

## Environment variables

The variables below are the ones a user may set, and they are part of the
public surface. The rest (tuning knobs, each with a measured default) are in
[`docs/ENVIRONMENT.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/ENVIRONMENT.md);
they are not part of that surface and may be removed once the behaviour they
tune has settled.

### Credentials

Read by the credential helper ([`aidot_cameras/credentials.py`](https://github.com/cbrightly/python-aidot-cameras/blob/main/aidot_cameras/credentials.py))
and the CLI; they take priority over a stored credentials file.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_USERNAME` | AiDot account username/email. Used with `AIDOT_PASSWORD`. | (none) |
| `AIDOT_PASSWORD` | AiDot account password. Used with `AIDOT_USERNAME`. | (none) |
| `AIDOT_COUNTRY` | Account region/country code. | `US` |
| `AIDOT_TOKEN_FILE` | JSON file that caches the login across restarts; token rotations are written back to it. Share one file between every process on the account, since a second login invalidates the first's token. | (none) |
| `AIDOT_INCLUDE_SHARED_HOUSES` | Also list houses this account does not own (the cameras a shared-home member sees). Unset, those houses are skipped. | `0` |

### Streaming

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_VIDEO_DECODER` | Force a video decoder instead of measuring one: a decoder name (`h264_v4l2m2m`), or an acceleration method prefixed with `hwaccel:` (`hwaccel:videotoolbox`). The prefix is required: VideoToolbox and VAAPI have no decoder to name. How the decoder is otherwise measured: [`docs/CAMERAS.md`](https://github.com/cbrightly/python-aidot-cameras/blob/main/docs/CAMERAS.md#choosing-a-video-decoder). | (measured) |
| `AIDOT_DISABLE_HWACCEL` | Keep to software decoding and skip the measurement. | (unset) |
| `AIDOT_MAX_CONCURRENT_STREAMS` | How many cameras may stream at once. | `3` |
| `AIDOT_MAX_CONCURRENT_OPENS` | How many stream opens may run at once across all cameras. | `2` |
| `AIDOT_SDES_CONNECTION_MODE` | Which media path an SDES offer proposes. `auto`: every reachable candidate, LAN preferred, relay as last resort (on the reference fleet six of seven cameras stream direct). `lan`: no relay, except that battery cameras always keep it, as a cloud-woken camera has no other path back. `relay`: EXPERIMENTAL and does not steer in practice (the cameras still dial the host address directly). A per-open `sdes_connection_mode` beats this. The path a session took is `media_stats().media_path` (`direct`/`relay`). | unset (`auto`) |
| `AIDOT_SDES_VIDEO_PT` | Pin the SDES offer to one video codec. The camera otherwise chooses: an A001064 sends H.264 1280x720 most sessions and H.265 2560x1440 occasionally. `96` pins H.264; `none` leaves the camera its choice. **Do not set `97`**: an H.265-only offer returned no video at all. | `96` (H.264) |
| `AIDOT_PERSISTENT_MQTT` | **Deprecated, ignored.** Commands, attribute fetches and stream-open signaling always share one account-level MQTT connection, as the official app does. The per-operation connection that `0` used to select reused the account's client id and so evicted that shared connection; it is gone. Still accepted; `0`/`false`/`no`/`off` logs one warning. | (ignored) |
| `AIDOT_DIRECT_PUBLISH` | Publish a live `rtsp://` push into go2rtc from the library itself instead of through ffmpeg, for both transports: no ffmpeg process per stream, packets put back in order, audio PCMA plus an in-process AAC track, attached from the camera's answer. H.264 sessions only (H.265 keeps ffmpeg); recordings, snapshots and `-`/`http://` serves are unchanged. Live-validated on A000088, A001064 and A001513. `0` turns it off for SDES pushes; a DTLS push has no ffmpeg path to fall back to and is always published directly. | `1` (on) |
| `AIDOT_PUBLISH_AAC` | Add an AAC-LC 48 kHz mono track, encoded in-process, after the A-law one on a direct publish, so AAC-only consumers (Home Assistant's HLS player, `camera.record`) have sound; select it with go2rtc's `?audio=aac`. Costs one AAC encode per streaming camera. PCMU cameras get none. `0` turns it off. | `1` (on) |
| `AIDOT_HLS_DIRECT_TS` | Give each eligible camera a library-muxed MPEG-TS on a loopback listener (`CameraMixin.hls_ts_url()`) with video and AAC on one clock, for Home Assistant's HLS view and recordings to read instead of go2rtc's RTSP (through go2rtc a reader joining a running stream had its sound 0.1-0.75 s late). Needs `AIDOT_DIRECT_PUBLISH` and `AIDOT_PUBLISH_AAC`. Eligible: DTLS cameras, and a mains SDES camera whose media the bridge decrypts (A001064) with its offer pinned to H.264; never a battery camera (its packet loss can keep the TS empty past Home Assistant's open timeout), and never a pulled session. When a camera's SPS changes, readers are disconnected to rebuild their decoder. Each URL carries a per-listener secret as `?auth=<secret>` (a request without it gets 404); for your own tools the listener URL (`http://127.0.0.1:<port>/{name}.ts?auth=<secret>`, `{name}` being `aidot_` plus the first 12 characters of the device id) is written, owner-readable, to `hls-ts-url` in `AIDOT_SPROP_DIR` while it runs. A reader counts as a viewer. `0` turns it off. | `1` (on) |
| `AIDOT_LOGIN_RETRY_LIMIT` | Consecutive failed LAN logins after which a device is left alone: discovery and the retry loop stop kicking it, and a login something else still asks for (Home Assistant's five-minute light poll) is made quietly, until the device's address changes or a login succeeds. One warning marks the moment. A login the device accepts and closes within `AIDOT_LAN_STABLE_SESSION_S` counts as a failure. Applies to every device; in practice, lights on another subnet. | `6` |

`AIDOT_DIRECT_PUBLISH`, `AIDOT_PUBLISH_AAC`, `AIDOT_HLS_DIRECT_TS` and
`AIDOT_SDES_VIDEO_PT` can also be given to one account client, as
`CameraClient(..., stream_settings=StreamSettings(...))`. A value set there wins
for that client's cameras; one left unset is read from the variable, then the
default. Two clients in one process keep their own.

### Security hardening

Defaults keep current behaviour (the firmware's signaling carries no verifiable
material, so strict modes are off until you pin a value), and the library warns
when one is left permissive. `AIDOT_ALLOW_PLAINTEXT_MQTT` and
`AIDOT_EXTRA_REGIONS` are the exceptions: strict by default, loosened when set.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_ALLOW_PLAINTEXT_MQTT` | Accept a non-TLS MQTT broker URL from the cloud (`ws://`, `tcp://`, `mqtt://`, `http://`). The connection carries the account's MQTT password, so by default such a URL is refused: control calls fail with an error in the log and a stream open raises `AidotPlaintextMqttRefused`. A URL with no scheme is treated as `wss://`. Does not affect `AIDOT_MQTT_URL`. | unset (refuse) |
| `AIDOT_EXTRA_REGIONS` | Comma-separated account regions to accept beyond `us`, `eu` and `jp`; an unknown region is refused with `AidotUnknownRegion`. Each entry must be a hostname label. | unset |
| `AIDOT_ALLOW_LAN_SERVE` | Silence the warning when decrypted media is served on a non-loopback bind (e.g. `0.0.0.0`), where any LAN host can read it. | unset (warn) |
| `AIDOT_CRED_KEY_FILE` | Fernet key file for stored credentials; point it outside the config dir. Default credentials path only (ignored with an explicit `creds_path`). | `$XDG_CONFIG_HOME/aidot/.key` (else `~/.config/aidot/.key`) |
| `AIDOT_DTLS_PINNED_FP` | Pin DTLS cameras' certificate to this `sha-256` fingerprint (case and `:` ignored). Set, any other certificate, or none, fails the handshake. | unset (not independently verified; warns) |
| `AIDOT_SDES_HOLEPUNCH_HOST` | NAT hole-punch target when the cloud supplies no TURN entry. Unset uses a hardcoded vendor TURN host; empty disables that fallback. | unset (vendor host + warn) |

**Without `AIDOT_DTLS_PINNED_FP`, a DTLS camera's (A000088's) certificate is
not independently verified.** The camera echoes our own fingerprint back, or
sends an empty one, in which case whatever it presents is accepted with a
warning; a fingerprint it does send arrives over the same cloud signaling. The
media is encrypted but not authenticated against something standing in for the
camera. With a pin, every DTLS open checks the presented certificate and fails
closed, logging the fingerprint on a mismatch. The pin applies to every DTLS
camera the process opens, so it suits a one-camera process (the CLI) or
cameras that share a certificate. SDES cameras key their media through
signaling and have no certificate to check.
