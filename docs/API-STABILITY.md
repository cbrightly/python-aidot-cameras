# What this library promises

This is the contract a 1.0 release would commit to. It is written down now, ahead
of that release, because "which parts are public" is the kind of question that is
cheap to answer while it is still true and expensive to reconstruct later.

The short version: **if you only use the names below, upgrades will not break
you.** Everything else is an implementation detail that moves without notice.

## Public

Import from the top-level package or the named modules. These keep their
signatures and behaviour across minor versions:

| Surface | What it is |
| --- | --- |
| `aidot_cameras.client.AidotClient` | account session: login, device list, cleanup |
| `aidot_cameras.configure_stream_limits` | fleet-wide concurrency limits |
| `DeviceClient` public methods (`async_*`, `start_keepalive`, `attach_lan_client`) | per-camera control and streaming |
| the session objects returned by `async_open_webrtc_stream` | `stop()`, `async_start_talk`, `async_stop_talk`, `get_stats`, `talk_supported`, `is_alive` |
| `aidot_cameras.camera.models` | `CameraDeviceInformation`, `CameraStatusData` |
| `aidot_cameras.exceptions` | every exception type |
| `aidot_cameras.const` | `CONF_*`, `DEFAULT_COUNTRY_CODE`, `SUPPORTED_COUNTRY_CODES` |
| `aidot_cameras.camera.constants` | published protocol constants (`TALK_PCM_*`) |
| `aidot_cameras.camera.hwaccel.probe_decoder` | decoder capability probe |
| documented `AIDOT_*` environment variables | per-install tuning seams |

Two sessions exist - `WebRTCSession` (DTLS cameras) and `SdesSession` (SDES
cameras). Which one you get depends on the camera, so treat the return value as
"a session" and use only the methods above; the classes are not
interchangeable beyond that.

## Not public

- Anything named with a leading underscore, at any level - module, class,
  function or attribute. Much of the interesting behaviour lives in
  `_open_sdes_stream_impl`, `_bridge_fn` and friends; none of it is stable.
- `aidot_cameras._vendor.*`. A vendored aiortc, pinned and patched. It tracks
  upstream on our schedule and is not part of the contract.
- The internal protocol helpers in `camera.protocol` other than those named
  above, including the AVIO request and response machinery.
- Log message text and log levels. Useful for humans, not for parsing.

## How this is kept honest

The reference consumer is the Home Assistant integration, and it reaches into
**zero** private attributes of this library - measured, not assumed. If a future
change to the integration needs a private name, that is the signal to promote
the name here rather than to reach through.

## Versioning

Semantic versioning. Before 1.0.0 the minor version carries breaking changes;
after it, the major does. New behaviour behind an `AIDOT_*` environment variable
or an optional keyword argument is additive and ships in a minor release.

Deprecations get one minor release of overlap with a runtime warning before the
old name is removed.
