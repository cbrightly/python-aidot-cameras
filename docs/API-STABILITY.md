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
| `AidotFfmpegMissing` | `AidotStreamOpenError` | ffmpeg is needed and not installed |
| `AidotSdPlaybackUnavailable` | `AidotStreamOpenError` | an SD recording was asked for on a path that cannot play one (the DTLS transport, or an answer with no data channel) |
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
| `aidot_cameras.camera.models` | `CameraDeviceInformation`, `CameraStatusData` |
| `aidot_cameras.camera.lan_control` | LAN-direct control helpers |
| `aidot_cameras.camera.go2rtc` | the go2rtc bridge helpers |
| `aidot_cameras.exceptions` | every exception type |
| `aidot_cameras.const` | `CONF_*`, `DEFAULT_COUNTRY_CODE`, `SUPPORTED_COUNTRY_CODES` |
| `aidot_cameras.camera.constants` | published protocol constants (`TALK_PCM_*`) |
| `aidot_cameras.camera.hwaccel.probe_decoder` | decoder capability probe |
| the `aidot-go2rtc` command (also `python -m aidot_cameras`) | its arguments (`<dev_id> <output_url>`, `--list`, `-v`) and the meaning of the output URL schemes it accepts. Its log output is not part of the promise |
| the `AIDOT_*` environment variables in the README's tables | per-install tuning seams. The variables in `docs/ENVIRONMENT.md` are not part of this promise: each may go once the behaviour it tunes has settled, and the CHANGELOG says so when one does |

`lan_control`, `go2rtc` and `device_session_authenticated` are imported by the
reference consumer (the Home Assistant integration), which is why they are
listed: they were commitments in practice before this document named them.
`CameraLanClient` appears in the README as a user-facing concept and is covered
by the `lan_control` row.

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
  by three import rewrites and one fix to closing a peer connection mid-ICE -
  the exact delta and the re-vendoring procedure
  are in `aidot_cameras/_vendor/aiortc/VENDOR.md`, and upstream's BSD licence
  ships beside it. It tracks
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
