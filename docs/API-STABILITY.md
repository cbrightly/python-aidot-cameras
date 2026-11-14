# What this library promises

This is the contract from 1.0.0 on: which parts of the library are public, and
what a release may and may not change about them.

The short version: **if you only use the names below, upgrades within a major
version will not break you.** Everything else is an implementation detail that
moves without notice.

## Public

### Names exported from the package root

Everything in `aidot_cameras.__all__`, importable as
`from aidot_cameras import ...`:

| Name | What it is |
| --- | --- |
| `AidotClient`, `CameraClient` | the account session: login, device list, cleanup. `AidotClient` is an alias of `CameraClient` (also importable from `aidot_cameras.client`) |
| `CameraDeviceClient` | the per-camera client `get_device_client` returns for a camera (see the note on concrete classes below) |
| `DeviceInformation`, `DeviceStatusData` | device information and status records for any device |
| `CameraDeviceInformation`, `CameraStatusData` | the camera-specific records (also in `aidot_cameras.camera.models`) |
| `Discover`, `CameraDiscover` | the LAN discovery sweep. `Discover` is an alias of `CameraDiscover` |
| `SdesSession`, `WebRTCSession` | the two session types `async_open_webrtc_stream` returns (see below) |
| `configure_stream_limits` | fleet-wide concurrency limits |
| `StreamHealth` | the snapshot `CameraDeviceClient.stream_health` returns (see below; also in `aidot_cameras.camera.stream_health`) |
| `StreamSettings` | per-client stream settings: the `stream_settings` keyword of `CameraClient`, and the read-only `stream_settings` attribute of the client and of each `CameraDeviceClient` it creates. A field that is set wins over its `AIDOT_*` variable for that client's cameras; see `docs/ENVIRONMENT.md` |
| `AidotError` and the other exception types in `__all__` | see `aidot_cameras.exceptions` below |

### Exceptions

Every exception type is defined in `aidot_cameras.exceptions` and exported from
the package root. `AidotError` is the base of all of them except upstream's
`AidotOSError`, so `except AidotError` catches whatever the library raises on
purpose.

| Name | Base(s) | Raised when |
| --- | --- | --- |
| `AidotError` | `Exception` | base of the hierarchy (from upstream python-aidot) |
| `AidotAuthFailed`, `AidotAuthTokenExpired`, `AidotNotLogin`, `AidotUserOrPassIncorrect` | `AidotError` | account login and token failures (from upstream) |
| `HTTPError`, `InvalidHost`, `InvalidURL` | `AidotError` | upstream's HTTP-level errors |
| `AidotOSError` | `Exception` | upstream's OS-level error; not an `AidotError` (upstream's choice) |
| `AidotCameraBusy` | `AidotError` | the camera refused the stream with a terminal code; do not retry |
| `AidotCameraNotReady` | `AidotError` | the camera answered but its media pipeline is not up yet; retry soon |
| `AidotCameraNoMedia` | `AidotError` | the camera answered and sent no media; abandon to the retry |
| `AidotCameraWrongCodec` | `AidotCameraNoMedia` | a camera pinned to one codec sent the other |
| `AidotStreamOpenError` | `AidotError`, `RuntimeError` | base of the open-path errors below |
| `AidotMqttUnavailable` | `AidotStreamOpenError` | no MQTT broker URL, or the broker connection failed |
| `AidotPlaintextMqttRefused` | `AidotMqttUnavailable` | the cloud named a non-TLS broker URL and `AIDOT_ALLOW_PLAINTEXT_MQTT` is not set |
| `AidotLivePlayRefused` | `AidotStreamOpenError` | the camera answered `livePlay=0` |
| `AidotNoWebrtcAnswer` | `AidotStreamOpenError` | no `webrtcResp` within the open timeout |
| `AidotNegotiationFailed` | `AidotStreamOpenError` | the camera's SDP could not be applied, or ICE did not connect |
| `AidotIceNotEstablished` | `AidotNegotiationFailed` | ICE did not connect; the camera still holds the session it accepted, so retry no sooner than after a busy refusal |
| `AidotFfmpegMissing` | `AidotStreamOpenError` | ffmpeg is needed and not installed |
| `AidotSdPlaybackUnavailable` | `AidotStreamOpenError` | an SD recording was asked for where it cannot play: a DTLS camera without a `.ts` output_path, an answer with no data channel, or a camera this client streams live |
| `AidotUnknownRegion` | `AidotError`, `ValueError` | the account region is not a known one (see `AIDOT_EXTRA_REGIONS`) |
| `CameraLanError` | `AidotError` | a LAN control operation failed (also in `aidot_cameras.camera.lan_control`) |
| `CameraLanLoginRejected` | `CameraLanError` | the device offered LAN control and refused the login |
| `RtspPublishError` | `AidotError`, `RuntimeError` | the RTSP server refused or dropped a publish (also in `aidot_cameras.camera.rtsp_publish`) |

The open-path errors replaced bare `RuntimeError` raises, and `RtspPublishError`
was a `RuntimeError` before it was an `AidotError`, which is why each keeps
`RuntimeError` as a base: `except RuntimeError` written against an earlier
release still catches them.

### Modules and members

| Surface | What it is |
| --- | --- |
| `DeviceClient` public methods (`async_*`, `start_keepalive`, `attach_lan_client`) | per-camera control and streaming |
| `aidot_cameras.device_client.device_session_authenticated` | whether a device client's LAN session is logged in; a module-level function, so the row above does not cover it |
| `CameraDeviceClient.has_live_session` | whether a stream session is up right now, answered without sending anything |
| `lan_address`, `lan_login_failures`, `lan_login_over_ceiling` on every device client (`LanRetryMixin`) | the LAN login state, read-only: the address in use, consecutive failures, and whether the retry ceiling has been reached |
| the session objects returned by `async_open_webrtc_stream` | `stop()`, `async_start_talk`, `async_stop_talk`, `talk_supported`, `is_alive`; statistics as `get_stats()` on a `WebRTCSession` and `media_stats()` on an `SdesSession` |
| the session `async_open_sd_playback` returns (an `SdesSession`, or a `WebRTCSession` on a DTLS camera) | everything in the row above, plus `playback_reply()` (the camera's answer to PLAY_START, its payload read with `decode_playcontrol_reply`) and `async_sd_pause()`, `async_sd_resume()`, `async_sd_seek(offset_s)` (each a `PlaycontrolReply`). All four answer `None` on a live session or when the camera does not answer in time. On an `SdesSession`, `playback_ended` turns True once the camera marks the end of the recording |
| `aidot_cameras.camera.sd_playback` | `decode_playcontrol_reply` and the `PlaycontrolReply` it returns (`command`, `result_channel`, `clip_seconds`, the clip's start time); the `PLAY_*` command numbers |
| `aidot_cameras.camera.models` | `CameraDeviceInformation`, `CameraStatusData` |
| `aidot_cameras.camera.lan_control` | LAN-direct control helpers |
| `aidot_cameras.camera.go2rtc` | the go2rtc bridge helpers: `Go2rtcClient`, `prefer_go2rtc`, `DEFAULT_BASE_URL`, `DEFAULT_RTSP_PORT`, and the one write path `async_ensure_stream(client, name, source=None, *, recreate=False)` / `async_remove_stream(client, name)` with `EnsureResult`, `EnsureOutcome` and `PLACEHOLDER_SOURCE` |
| `aidot_cameras.exceptions` | every exception type |
| `aidot_cameras.const` | `CONF_*`, `DEFAULT_COUNTRY_CODE`, `SUPPORTED_COUNTRY_CODES` |
| `aidot_cameras.camera.constants` | published protocol constants (`TALK_PCM_*`) |
| `aidot_cameras.camera.hwaccel.probe_decoder` | decoder capability probe |
| `aidot_cameras.camera.hwaccel.probe_encoder(codec="h264")`, `cached_encoder(codec="h264")`, `EncoderPlan`, `SOFTWARE_ENCODER` | the hardware-encoder probe. `probe_encoder` returns an `EncoderPlan(input_args, output_args)` for an encoder proven by a test encode with those same arguments on this host: `input_args` go before `-i` (VAAPI's `-vaapi_device <render node>`), `output_args` after it (VAAPI's `-vf format=nv12,hwupload`, then `-c:v <name>`). `SOFTWARE_ENCODER`, both empty, means software and the caller names the encoder; `encoder` and `is_software` read the plan. VAAPI is preferred over QSV. `probe_encoder` blocks and caches its verdict on disk; `cached_encoder` never probes and answers `None` until a verdict is known |
| `CameraDeviceInformation.firmware_version` | the firmware version the cloud reports for the camera (`firmwareVersion`, or `deviceInfo.firmwareVersion`), or `None` |
| `CameraDeviceClient.async_get_firmware_info(*, timeout=8.0)` and `aidot_cameras.camera.models.FirmwareInfo` | the read-only firmware version query: `FirmwareInfo(installed_version, latest_version, release_notes)` with `update_available`, or `None` when unknown. Returns when the reply arrives; `timeout` bounds the wait for one. Installs nothing and wakes nothing: the cloud answers by itself, whatever the camera's state. `release_notes` is `None` until the version list is known to carry any |
| `CameraDeviceClient.cloud_reports_offline` | True only when the cloud has explicitly reported the camera offline; `status.online` alone is False before the cloud has said anything |
| `CameraDeviceClient.stream_health` | what the camera's background stream (`start_keepalive`, `async_start_streaming`) is doing, as a `StreamHealth`: `state`, `last_connect_s` (seconds from the start of the open that succeeded to first media, or `None`), `last_end_reason` (or `None`), `retry_delay_s` (set only while `retrying`: the longest the loop will wait before it reopens, which for a camera the cloud reports offline is the offline hold, not the backoff) and `failures` (opens and sessions without media since the last one with). On a DTLS camera "media" is usable video; audio alone is not `streaming`. After `async_stop_streaming` the state is `idle` until the stream is started again. Read-only; reading it sends nothing |
| `CameraDeviceClient.add_stream_health_listener(callback)` | calls `callback()` (no arguments, on the event loop) whenever `stream_health` changes, and not when a report from the loop leaves it equal; returns a function that removes the listener. A power-switch change that arrives in a status update is not announced, only one made through `async_set_power` |
| `aidot_cameras.camera.stream_health` | `StreamHealth`, `STREAM_STATES` (`idle`, `connecting`, `waking`, `streaming`, `retrying`, `dormant`, `off`) and `STREAM_END_REASONS`, with a `STATE_*` / `END_*` constant for each value. A minor release may add an end reason; the states are fixed for the major line |
| the `aidot-go2rtc` command (also `python -m aidot_cameras`) | its arguments (`<dev_id> <output_url>`, `--list`, `-v`) and the meaning of the output URL schemes it accepts. Its log output is not part of the promise |
| the `AIDOT_*` environment variables in the README's tables | per-install tuning seams. The four `StreamSettings` covers are read after a client's own setting and before the default. The variables in `docs/ENVIRONMENT.md` are not part of this promise: each may go once the behaviour it tunes has settled, and the CHANGELOG says so when one does |

`lan_control`, `go2rtc`, `sd_playback.decode_playcontrol_reply` and
`device_session_authenticated` are imported by the reference consumer (the Home
Assistant integration), which is why they are listed: they were commitments in
practice before this document named them.
`CameraLanClient` appears in the README as a user-facing concept and is covered
by the `lan_control` row.

`async_ensure_stream` promises three things. Every write it sends to one go2rtc
server (by base URL, in one event loop) is serialised with every other write
through the module, `Go2rtcClient.ensure_stream`/`remove_stream` included, and
its own DELETE and PUT run with no other write in between; the stream-list
check after a rejected PUT is a read and is not serialised. It never raises.
Its `outcome` is `REGISTERED` (PUT accepted), `UNCHANGED` (PUT rejected,
stream present: go2rtc still serves it from its old definition) or `FAILED`
(stream absent); `ok` is true for the first two.

Two of these carry a promise about what they DO NOT say, and callers depend on it:

- **`CameraStatusData.sd_card_present`** is a tri-state. `True` and `False` are
  readings; **`None` means no camera reported anything** and is not evidence that
  a model cannot report. Four of seven cameras measured 2026-08-12 carried
  neither source key, including a model whose siblings report normally. Code that
  collapses `None` into `False` will tell someone with a working camera that
  their card slot is empty.
- **`async_get_sd_recordings`** answers `None` (no session, nothing sent),
  `answered=False` (asked, camera silent - NOT a statement about the card), or
  `answered=True` (only now does an empty `records` mean an empty card).

`has_live_session` is best-effort in one direction: a torn-down session reads as
live on a transport that does not publish `is_alive`, so a caller that acts on it
must still handle the listing failing anyway.

Two sessions exist - `WebRTCSession` (DTLS cameras) and `SdesSession` (SDES
cameras). Which one you get depends on the camera, so treat the return value as
"a session" and use only the methods above; the classes are not
interchangeable beyond that.

## Not public

- Anything named with a leading underscore, at any level - module, class,
  function or attribute. Much of the interesting behaviour lives in
  `_open_sdes_stream_impl`, `_bridge_fn` and friends; none of it is stable.
- `aidot_cameras._vendor.*`. A vendored aiortc 1.14.0, differing from upstream
  by import rewrites, a fix to closing a peer connection mid-ICE, and the RTP
  router hook SD playback takes H265 from - the exact delta and the
  re-vendoring procedure are in `aidot_cameras/_vendor/aiortc/VENDOR.md`, and
  upstream's BSD licence ships beside it. It tracks
  upstream on our schedule and is not part of the contract.
- The internal protocol helpers in `camera.protocol` other than those named
  above, including the AVIO request and response machinery.
- Log message text and log levels. Useful for humans, not for parsing.
- The **concrete class** `get_device_client` returns. Annotate against
  `DeviceClient` and use `isinstance`, never `type(x) is`. Non-cameras get a
  `LightDeviceClient` (0.17.2; before that, upstream's `DeviceClient` itself) and
  cameras get a `CameraDeviceClient`; both are subclasses of `DeviceClient`, so
  every `isinstance` check and every method in the tables above is unaffected.

## How this is kept honest

The reference consumer is the Home Assistant integration, and it reaches into
**zero** private attributes of this library - measured, not assumed. If a future
change to the integration needs a private name, that is the signal to promote
the name here rather than to reach through.

## Versioning

Semantic versioning. From 1.0.0 on, a breaking change to anything listed under
"Public" needs a new major version. New behaviour behind an `AIDOT_*`
environment variable or an optional keyword argument is additive and ships in
a minor release; fixes ship in a patch release.

Deprecations get at least one minor release of overlap with a runtime warning
before the old name is removed in the next major.

Deprecated now:

| Name | Since | What happens |
| --- | --- | --- |
| `AIDOT_SDES_ECHO_WAIT_S`, `AIDOT_SDES_UNREACHABLE_NOMINEE_GRACE_S`, `AIDOT_SDES_ANSWER_PROBE_GRACE_S`, `AIDOT_SDES_TRIGGER_GRACE_S` | [Unreleased] | accepted and ignored, with one warning per process when any is set; the SDES open has no `webrtcReq`-echo wait, and the three first-media rules are one timer, `AIDOT_SDES_NO_MEDIA_GRACE_S` (docs/ENVIRONMENT.md) |
| `AIDOT_DTLS_SLOW_PROBE_THRESHOLD`, `AIDOT_DTLS_SLOW_PROBE_INTERVAL_S`, `AIDOT_DTLS_SLOW_PROBE_LOG_EVERY` | [Unreleased] | accepted and ignored, with one warning per process when any is set; the DTLS serve loop paces failed opens on one backoff, 15 s up to 600 s and 540-600 s at the cap (docs/ENVIRONMENT.md) |
