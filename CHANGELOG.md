# Changelog

All notable changes to `python-aidot-cameras` are documented here. The format is
based on [Keep a Changelog](https://keepachangelog.com/), and this project uses
date-less, incrementing patch versions published to PyPI via GitHub Releases.

## [0.7.20]

### Fixed
- **Cold-start instrumentation now covers the SDES `webrtcReq`.** The SDES open
  has its own `webrtcReq` publish point that the 0.7.19 markers missed, so SDES
  cameras logged `first-media`/`serving` but not `webrtcReq`. Added
  `cold-start[<device>] webrtcReq (sdes) +<ms>` so the SDES cold-start timeline
  is complete.

## [0.7.19]

### Added
- **Cold-start instrumentation.** `_cold_phase()` logs greppable
  `cold-start[<device>] <phase> +<ms>` markers (webrtcReq → first-media →
  serving) on both serve paths, so a cold connect's timeline is measurable
  without a debugger. Best-effort: never raises, no-op when no open is in flight.
- **Warm-hold option.** `start_keepalive(stream_idle_s=...)` overrides
  `AIDOT_STREAM_IDLE_S` (default 120 s); `<= 0` keeps the warm WebRTC session
  forever so re-views are instant — intended for mains cameras (it holds a
  concurrent-stream slot + continuous decrypt for the camera's lifetime, so stay
  within `AIDOT_MAX_CONCURRENT_STREAMS`, default 3). Default behaviour unchanged.

### Changed
- **Denser SDES startup PLI burst** so the first decodable keyframe arrives
  sooner on a cold open: keyframe requests now ramp 0 / 1.5 / 3.5 / 6.5 s then
  the same 30 s safety PLI (was 3 PLIs at a flat 5 s → first IDR up to ~10 s).
  Tunable/revertable via `AIDOT_SDES_PLI_GAPS`. (#52)

## [0.7.18]

### Fixed
- **Cold-start blank video: the first view of an idle camera (or the first view
  after an HA restart) could fail to load.** go2rtc pulls the library's local
  `ffmpeg -f mpegts -listen 1` socket before ffmpeg has bound it — ffmpeg only
  opens its `-listen` output after probing input, which needs the ~16–25 s WebRTC
  handshake to deliver the first frames. go2rtc hit `ECONNREFUSED`, retried for
  ~200 ms, then gave up, so the card stayed blank until a second attempt. The
  library now holds the public serve port for the whole session via a small relay
  (`_ServeRelay`) and proxies to ffmpeg on an internal port: an early pull
  connects and waits instead of being refused, and the public listener survives
  ffmpeg restarts (go2rtc reconnects). Wired into both serve paths (DTLS + SDES).
  Default on; disable with `AIDOT_SERVE_RELAY=0` or `start_keepalive(
  serve_relay=False)`. Falls back to serving ffmpeg directly on the public port
  if the relay bind fails, so a port clash never breaks streaming.
  Live-validated on an A001513 (SDES) camera. (#50, fixes #49)

## [0.7.17]

### Internal
- Consolidated reconnect pacing behind a single `ReconnectPacer` (in
  `camera/protocol.py`) wired into all three reconnect loops (SDES keepalive,
  JPEG streaming, DTLS serve), replacing per-loop attempt-counter bookkeeping
  with one escalate/reset policy. The open-fail and end-of-session delays are
  unchanged; this is a behavior-preserving refactor with unit coverage for the
  pacer's escalate/reset transitions.
- Deduplicated the cloud IPC POST path into `_async_post_ok()` and the owner-id
  fallback into `_owner_id()`, shared by the wake and `liveStreamParam` calls.
- Promoted the `liveStreamParam` gate to a `live_stream_param` keyword on
  `start_keepalive` (still falling back to `AIDOT_LIVESTREAM_PARAM`). (#47)

## [0.7.16]

### Documentation
- Documented the `AIDOT_LIVESTREAM_PARAM` environment variable in the README's
  environment-variable reference — the knob added in 0.7.15 to gate the
  `liveStreamParam` cloud pre-connect that provisions battery cameras (set `0` to
  disable). No code changes. (#45)

## [0.7.15]

### Fixed
- **Battery cameras (A001513/A001108/A001360, e.g. the L2 models) produced no live
  media.** They rejected every MQTT `livePlayReq` with code `-50019` ("not ready")
  and never ran ICE — even fresh-rebooted, app-closed, and uncontended — while
  mains cameras of the same model streamed fine. Root cause: the official app
  performs a cloud pre-connect the library skipped (`KVSPreConnectStrategy.
  fetchKvsParams` → `POST /api/ipc/liveStream/liveStreamParam`) that provisions the
  live-stream session and brings the camera online before signaling. The library
  now makes that call for battery cameras at the start of the open path; the
  existing MQTT/SDES signaling then succeeds and decrypted RTP flows. Best-effort,
  gated on `is_battery_camera` (mains/DTLS unaffected), disable via
  `AIDOT_LIVESTREAM_PARAM=0`. (#43)

> Validated live on an A001513 battery camera: stream opens in ~16s and media flows
> for the full session; previously persistent `-50019` / zero media. 5 unit tests
> lock the request shape.

## [0.7.14]

### Changed
- **Jittered reconnect backoff** (`next_backoff` in `camera/protocol.py`): the SDES
  keepalive, JPEG streaming, and DTLS serve loops now use equal-jitter exponential
  backoff (with a hard floor at each loop's existing minimum) instead of a lockstep
  `delay *= 2`. Randomized spread stops a degraded camera — or a fleet reconnecting
  at once — from synchronizing into reconnect storms / cloud rate-limiting. The
  loops also escalate backoff only when a session opens but never delivers media
  (the camera-degradation case) and reset after a session that streamed; the
  decrypted-RTP liveness watchdog still drives *when* to restart. (#41)

> 7 new unit tests (`tests/test_backoff.py`). Validated live: all three DTLS
> cameras and a mains SDES camera re-establish through the rewired loops.

## [0.7.13]

### Fixed
- **SDES H.265 streaming recorded 0-byte video** (#39): SDES cameras stream H.264
  (pt=96) *or* H.265 (pt=97), varying per session, but the generated SDP listed
  both (`m=video ... 96 97`) — so ffmpeg bound its depacketizer to the first
  payload type (H.264) and silently dropped the camera's H.265 packets. The
  bridge now records the camera's actual video payload type on the first video
  RTP packet and narrows the ffmpeg SDP to that single codec before launch
  (falling back to the dual-codec SDP if no video is observed). This fixes the
  ffmpeg-fallback path for installs without go2rtc; go2rtc handles H.265 natively
  on the preferred path (0.7.12, #37).

> Validated live against a real SDES (A001513) camera: ~490 KB recorded on the
> first attempt with the camera answering H.265.

## [0.7.12]

### Added
- **Prefer-go2rtc serve with ffmpeg fallback** (`aidot/camera/go2rtc.py`): when a
  go2rtc server is reachable, the camera's local serve URL is registered as a
  go2rtc stream so go2rtc handles low-latency WebRTC delivery and native
  H.264/H.265 depacketization; when go2rtc is absent the existing ffmpeg serve
  (e.g. Home Assistant HLS) is used unchanged. `start_keepalive(go2rtc_url=...)`
  registers the stream, `stream_rtsp_url` prefers the go2rtc pull URL, and the
  stream is deregistered on stop. Best-effort throughout — go2rtc errors never
  break the fallback path. (#37)
- `scripts/go2rtc_serve.py`: dev harness that drives the go2rtc serve path
  without Home Assistant.

### Fixed
- **SDES streaming no-media on late ICE nomination**: a late USE-CANDIDATE now
  nominates both the audio *and* video sockets (previously only one), so video
  RTP reaches the bridge. Validated live (5.5 MB recording). (#36)

### Changed
- Finished decomposing the monolithic `camera/client.py` into focused modules
  (data models + TUTK session, playback/WebRTC sessions, SDES session,
  device-control setter mixin, stateless protocol helpers, SDP transforms).
  Behavior-preserving: all existing imports keep working via re-exports. (#29–#35)

> Validated live end-to-end through a real go2rtc server (camera → library serve
> → go2rtc → ffprobe: H.264 1280x720 + AAC) in addition to the mocked unit suite
> (`tests/test_go2rtc.py`, 8 cases).

## [0.7.11]

### Changed
- Began splitting the monolithic `camera/client.py`: wire/protocol constants now
  live in `aidot/camera/constants.py` and are re-imported into `client.py`
  (behavior-preserving; all existing imports keep working).
- Renamed the worst cryptic private STUN/TURN helper closures for readability
  (`_mi_ta` → `_stun_message_integrity`, `_br_a`/`_si_a` → `_build_stun_attr`,
  `_rr_accept_cam_cert` → `_accept_camera_cert`). No behavior change.

### Fixed
- `GETSTREAMCTRL_CMD` is re-exported from `aidot.camera.client` again (it is the
  public pair of `SETSTREAMCTRL_CMD`); the constants extraction had dropped it.
- Corrected a debug-log function-name label that the rename pass had rewritten.

### Added
- `scripts/smoke_stream.py`: manual on-hardware smoke test that logs in,
  enumerates cameras, and briefly streams them (DTLS + SDES paths), reporting
  frames/recorded bytes per camera.

### Internal
- Moved `cleanup-branches.sh` into `scripts/`.

> Validated live against real cameras (DTLS A000088 and SDES A001064) in addition
> to the mocked unit suite.

## [0.7.10]

### Added
- PEP 561 `py.typed` marker so consumers' type checkers use the library's hints.
- README "Environment variables" reference documenting every `AIDOT_*` knob.
- Non-blocking `mypy` and coverage reporting in CI.

### Changed
- `camera/client.py`: added diagnosable `debug(..., exc_info=True)` logging to 93
  previously-silent exception handlers; proactive ffmpeg-presence guards with
  actionable errors; `XDG_CONFIG_HOME` support for the sprop cache.
- `credentials.py` honors `XDG_CONFIG_HOME`; `discover.py` resolves network tools
  via `PATH` and degrades gracefully instead of raising.
- Translated remaining non-English source comments to English; added docstrings
  to the public `DeviceClient` / `AidotClient` / `Discover` APIs.
- Modernized tooling: ruff-based pre-commit (replacing a stale 2019 config) and
  `ruff check` in CI.

### Fixed
- Corrected the distribution name / repository URLs in the README install
  commands and project metadata.
