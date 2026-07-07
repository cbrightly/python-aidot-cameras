# Changelog

All notable changes to `python-aidot-cameras` are documented here. The format is
based on [Keep a Changelog](https://keepachangelog.com/), and this project uses
date-less, incrementing versions published to PyPI via GitHub Releases.

## [0.11.2]

### Fixed
- **A token refresh could crash instead of persisting.** `login_info` is also
  used as the account-shared cache for the persistent-MQTT connection and
  its guarding `asyncio.Lock` (`_get_persistent_mqtt` - one connection per
  account, the default since 2026-06-17). Anything that serialized
  `login_info` directly - this library's own standalone CLI, or a consuming
  integration's config-entry storage - hit `TypeError: Object of type Lock
  is not JSON serializable` once that connection existed, silently failing
  to persist a freshly rotated token. Confirmed live: a standalone
  `aidot-go2rtc` run's token-refresh callback threw exactly this.
  `AidotClient.serializable_login_info()` is a new JSON-safe view (excludes
  the two runtime-only keys) that the CLI's own token cache now uses; any
  other caller persisting `login_info` should switch to it too.

## [0.11.1]

### Fixed
- **Keepalive retries no longer chase cloud-offline cameras.** When an open
  fails and the cloud has explicitly reported the device offline, the
  reconnect loops now hold - re-checking the online flag every 30 s
  (`AIDOT_OFFLINE_RECHECK_S`) and probing one real open only every 600 s
  (`AIDOT_OFFLINE_PROBE_S`) - instead of retrying on the normal backoff
  cadence. Each retry against a dead camera consumed an open-gate slot for
  the full 30 s signaling timeout; observed live (HA 2026.7.1), two unpowered
  A000088s cycling that way pushed a healthy camera's cold open past two
  minutes. Retries resume within one recheck of the device coming back
  online. First attempts are never delayed, and devices whose online state
  was never reported by the cloud are unaffected.

## [0.11.0]

### Fixed
- **Relay-only SDES (battery) cameras stream again.** These cameras (e.g.
  A001513, whose real LAN IP the cloud does not expose) answer with their ICE
  credentials after the initial STUN nomination window and send no connectivity
  probes of their own, so neither the in-window nor the probe-gated deferred
  `USE-CANDIDATE` send ever nominated their relay candidates - no SRTP media
  flowed and the serve failed with *"Could not find codec parameters (h264,
  none)"*. The ungated periodic `USE-CANDIDATE` re-sender now falls back to the
  late-parsed credentials, so relay-only cameras are nominated without needing a
  probe. Verified live on an A001513: 0/2 streaming before, 3/3 after. (#112)
- **`_mqtt_device_cmd` no longer reports success on a refused broker
  connection.** A failed MQTT connection now surfaces as a failure instead of a
  phantom success. Validated live: `setDevAttrReq` toggles return `True` only on
  a real seq-matched camera ACK. (#107)
- **Background stream tasks retrieve and log their exceptions** instead of
  producing *"exception was never retrieved"* warnings. (#107)

### Changed
- **The DTLS 1.0 downgrade is self-scoped to this library's sessions.** The
  OpenSSL minimum-version override for DTLS-1.0 camera firmware now applies only
  to peer connections whose certificate carries the library's tag, so other
  aiortc peer connections in the same process keep the DTLS 1.2 floor. Validated
  live on A000088 (DTLS 1.2) and A001513 (SDES + SCTP datachannel): both connect
  and stream, 0 DTLS errors. (#107)
- **A LAN login that cannot complete with the device's AES key marks the host
  ineligible** and falls back to cloud control (spoofed/broken hosts no longer
  wedge local control). (#107)

### Added
- **Opt-in TLS for `CloudPlaybackSession`** via `use_tls=` (default `False`,
  current plaintext behavior unchanged), with a warn-once when disabled. (#107)

## [0.10.3]

### Fixed
- **Installable under Home Assistant 2026.7 (PyAV 17).** HA 2026.7 pins `av==17`,
  but stock `aiortc` caps `av<17`, so `python-aidot-cameras[webrtc]` was
  unsatisfiable there - Home Assistant reported *"Requirements for aidot not
  found"* and the integration failed to load (confirmed on a live HA 2026.7.1
  box). `aiortc` is now **vendored** at `aidot/_vendor/aiortc` (byte-identical to
  aiortc 1.14.0) so its `av<17` packaging cap no longer gates installation, and
  the `[webrtc]` extra depends on aiortc's own runtime deps with the PyAV ceiling
  widened to `<18`. aiortc runs unchanged on av 17 (verified: import + H.264
  encode round-trip), so streaming behavior is unaffected. Validated under HA
  2026.7.1's exact `package_constraints.txt` (`av==17.0.1`): the previous release
  is *"unsatisfiable"*, this one installs and imports cleanly. Revert to the stock
  `aiortc` dependency once upstream widens the pin.

## [0.10.2]

### Changed
- **Repository re-aligned to a flat `aidot/` layout** (from `src/aidot/`) to match
  the upstream project's paths, and **downstream git ancestry with upstream
  (`AiDot-Development-Team/python-AiDot`) was established**, so future upstream
  changes can be merged directly (`git fetch upstream && git merge upstream/main`).

  Marker release only: **no functional or API change.** The published wheel ships
  the identical `aidot/` package (imports and behaviour are the same as 0.10.1);
  this release exists to record the maintenance/layout milestone in the changelog.

## [0.10.1]

### Fixed
- **Deadlock opening a stream on the SDES->DTLS fallback.** When a camera declared
  SDES but actually required DTLS, the fallback re-entered the *public*
  `async_open_webrtc_stream` while already holding the non-reentrant open-gate
  permit - hanging forever whenever the gate was saturated (two such cameras
  opening at once, or `AIDOT_MAX_CONCURRENT_OPENS=1`). It now calls the ungated
  impl under the permit it already holds.
- **DTLS fingerprint pinning failed open.** With `AIDOT_DTLS_PINNED_FP` set, the
  pin was silently skipped when the camera presented a real (non-empty)
  fingerprint - the check was gated on the empty-fingerprint workaround - and it
  accepted any certificate when the peer cert was missing or the digest raised.
  The pin is now enforced whenever it is set (independent of the workaround) and
  **fails closed**: a missing cert or a fingerprint error fails the handshake.
  Comparison is now colon/whitespace/case-insensitive. Unit tests added.
- **Secrets no longer written to logs.** The login response (access/refresh
  tokens), the userConfig body (MQTT password), and device-list bodies /
  per-device records (device `aesKey`/`password`) were logged on error or at
  DEBUG; they now emit only status codes / redacted identifiers.
- **`async_snapshot` no longer blocks the event loop.** It ran ffmpeg
  synchronously (and a no-timeout ffmpeg on the Pillow-missing path), freezing
  every camera/keepalive/MQTT drain for up to the timeout. It now uses an async
  subprocess with a timeout and offloads the blocking JPEG encode to an executor.

### Security / hardening
- Cap the LAN-control frame body size (an unbounded 32-bit length was a
  memory-exhaustion vector from a hostile/malfunctioning LAN peer).
- Sanitize the cloud-supplied `devid` before using it as the sprop cache
  filename (path-traversal hardening).
- Coerce cloud attribute values defensively so a malformed value can no longer
  crash the status-refresh path.
- Write credential/key files atomically at mode `0600` (no chmod-after-create
  window) and restrict the config directory to `0700`.

### CI
- Gate PyPI publishing on a green test run and run `twine check` before upload,
  so a red build can no longer publish.

## [0.10.0]

### Security
- **CSPRNG for media-keying material.** The SDES/DTLS pre-shared key carried over
  signaling is now generated using the `secrets` module instead of the
  predictable Mersenne-Twister `random`, removing a key-predictability weakness.
- **SDP-rewrite DoS guard.** The serve-port SDP rewrite now validates `m=audio`/
  `m=video` section shape before indexing, so a malformed/truncated SDP can no
  longer crash the rewrite.
- **Credential key separation.** `AIDOT_CRED_KEY_FILE` lets you store the Fernet
  key for encrypted credentials outside the config directory, so the key is no
  longer necessarily co-located with the ciphertext (a warning is emitted when it
  is). Honors `XDG_CONFIG_HOME`; applies to the default credentials path.

### Added
- **Opt-in transport hardening** (all default to prior behavior and emit a
  one-time warning when left permissive):
  - `AIDOT_DTLS_PINNED_FP` - pin the camera's DTLS `sha-256` fingerprint; a
    mismatching certificate fails the handshake instead of being accepted.
  - `AIDOT_PLAYBACK_TLS_VERIFY=1` - require full certificate + hostname
    verification on the TCP playback/live-stream TLS connection.
  - `AIDOT_ALLOW_LAN_SERVE` - acknowledge/silence the warning emitted when
    decrypted media is served on a non-loopback bind.
  - `AIDOT_SDES_HOLEPUNCH_HOST` - override the NAT hole-punch host used when the
    cloud supplies no TURN entry (set empty to disable the hardcoded fallback).
- **Supported-cameras documentation** - a model/transport/power table in
  `docs/CAMERAS.md` (and a short list in the README), covering A000088 (DTLS),
  A001513 (SDES, battery / "L2"), and A001064 (SDES, PTZ).

### Removed
- Dropped the write-only `_last_batch_response` attribute (a dead store with no
  readers).

## [0.9.3]

### Changed
- **The high-port ICE nomination fix is now unconditional.** The A000088/DTLS
  connect path forces USE-CANDIDATE onto the highest remote port (measured
  ~87% connect success versus ~10% without). This was previously gated behind a
  `AIDOT_DISABLE_HIGHPORT_FIX` debug escape hatch; the gate and its environment
  variable have been removed, since disabling the fix only ever degraded connect
  reliability. The override still self-scopes to tagged DTLS connections, so SDES
  cameras and non-camera devices are untouched.

### Documentation
- Decluttered the README environment-variable table down to the knobs most
  worth tuning. The finer-grained internal knobs (audio normalization,
  keyframe/PLI cadence, retry timing, SDES audio, idle release, the sprop cache
  path) now live under a dedicated "Advanced tuning environment variables"
  section in [`docs/CAMERAS.md`](docs/CAMERAS.md).

## [0.9.2]

### Fixed
- **Choppy DTLS camera audio under packet loss.** The DTLS A/V mux timestamped
  audio purely by accumulated decoded-sample count (`a_pts += fr.samples`) and
  discarded the PCMA RTP timestamp it had already captured. The video path locks
  to its 90 kHz RTP timestamps, but audio did not - so any lost audio packet made
  the lost time *vanish* and the remaining samples concatenate, compressing the
  audio timeline. Over a live stream this runs audio progressively ahead of the
  video and forces the player's jitter buffer to resync, heard as choppy audio.
  (A zero-loss lab capture sounded fine, which is why this hid behind earlier
  fixes.) The mux now anchors audio to its RTP clock and conceals a detected gap
  (>= 20 ms) with silence through the resampler - mirroring the video path - so
  audio stays time-locked and lost time becomes a brief honest silence instead of
  permanent A/V drift. Lossless streams are unaffected (the gap is zero, a no-op).
  An offline regression probe that drives the real mux with synthetic loss is at
  `scripts/audio_mux_probe.py`.

## [0.9.1]

Logging and packaging hygiene; no behavioural change to streaming.

### Fixed
- **Swallowed-exception debug logs now identify the camera.** Every
  `_LOGGER.debug("camera %s: swallowed exception", ...)` site across the camera
  modules was filling `%s` with the *function-name literal* instead of a device
  id, so logs read `camera stop: swallowed exception` and never said which
  camera. The 70 sites where a device id is in scope now log
  `camera <id>: swallowed exception in <func>` (via `getattr(self, "device_id",
  "?")` - these are except blocks, so they must never raise); the 35 sites with
  no device id in scope (the `SdesSession`/`WebRTCSession` classes carry none,
  plus module-level functions and non-`self` closures) drop the misleading
  `camera %s` framing and log `swallowed exception in <func>`.

### Changed
- **Core runtime dependencies now have conservative lower bounds**
  (`aiohttp>=3.9`, `cryptography>=42.0`, `pycryptodome>=3.20`, `dacite>=1.8`),
  so a fresh install can't resolve an ancient release. No upper caps - under
  Home Assistant, HA core pins `aiohttp`/`cryptography` itself and a cap would
  fight its resolver.

## [0.9.0]

Observability, connection-speed, and a large internal decomposition. Motivated
by diagnosing a buffering camera down to a marginal Wi-Fi link, then trimming
cold-start latency toward app-parity. All streaming changes were live-validated
on real DTLS and SDES cameras.

### Added
- **`WebRTCSession.get_stats()`** - a best-effort connection-health snapshot: the
  nominated ICE candidate pair (host/srflx/relay/prflx - the relay-vs-direct
  signal) plus inbound RTP packet loss / jitter. Fully guarded against
  aiortc/aioice internal drift.
- **`CameraStatus.wifi_rssi`** - parses the cloud `networkRssi` (dBm) into camera
  status, so a marginal camera link is visible without a packet capture.
- **`scripts/camera_diag.py`** - a maintained on-hardware probe: handshake time,
  time-to-first-frame, per-second decoded-fps timeline + gaps, nominated ICE
  path, RTP health, and Wi-Fi RSSI.

### Changed
- **DTLS fast-liveplay (default on).** Skips the up-to-2s `livePlayResp` wait on
  the DTLS open path (the official app never waits for it; it usually times out),
  while keeping the full ICE/TURN/DTLS handshake - so remote/relay viewing is
  unaffected, unlike the broader `fast_connect`. ~2s off a cold LAN open
  (12.8s->10.7s measured). Disable via `AIDOT_DTLS_FAST_LIVEPLAY=0` or per-camera
  `_dtls_fast_liveplay_opt`.
- **HTTP ICE config is cached** until just before its server-provided `ttl`
  (capped at 1h), saving the ~2s `iceConfig` fetch on a re-open after the warm
  session lapses. Honoring the explicit ttl guarantees no expired TURN
  credentials are reused.
- **Stall-triggered keyframe (PLI)** on the DTLS serve loop: a one-shot PLI when
  muxed frames stall (a dropped GOP on a jittery link), re-armed only when frames
  resume so a no-consumer freeze can't spam the camera. `AIDOT_STALL_PLI_S`.
- **Offline-device LAN errors downgraded to debug.** Connect-failed / read-status
  / unresponsive-keepalive messages on the light control channel are expected for
  an offline/powered-off/stale-IP device (the reconnect chain retries and
  availability is reflected in entity state), so they no longer log at
  ERROR/WARNING.

### Internal
- **`camera/client.py` decomposed 10,575 -> 3,646 lines (-65%).** The two
  ~3.5k-line stream-open state machines moved to mixin modules
  (`webrtc_open.py`, `sdes_open.py`) that `CameraMixin` inherits - behaviour-
  preserving (each method body byte-identical to the original), validated by
  ruff, the full suite, and live DTLS + SDES streams.
- **aioice compatibility guard** (`test_aioice_compat.py`) + an `aioice>=0.9,<0.12`
  pin: the high-port nomination patch and stream diagnostics read private aioice
  internals, so a breaking bump now fails loudly instead of silently degrading.

## [0.8.0]

Milestone release. The cloud TURN relay path is now empirically validated for a
genuinely-remote client, and the default-on persistent-MQTT and SDES-teardown
paths are hardened against the executor-thread leaks and silent command drops
found in a pre-release review.

### Fixed
- **Persistent-MQTT stream drain no longer leaks an executor thread.** The drain
  blocks an executor thread on `outgoing_q.get` until a `None` sentinel arrives.
  Cancelling the drain future (the previous teardown behaviour, 0.7.36) cannot
  interrupt that blocked thread, so a stream open cancelled mid-handshake - or a
  second open that replaced a prior one without an intervening
  `async_stop_streaming` - left the thread (and its handler on the shared
  connection) pinned forever, eventually exhausting the shared default
  `ThreadPoolExecutor`. Teardown and every new open now reap via
  `_reap_stream_drain`, which pushes the `outgoing_q` sentinel to release the
  thread before cancelling, and tracks the queue so a replacing open reaps the
  prior drain too.
- **Persistent-MQTT command/attribute requests fall back to a per-op connect.**
  When the persistent connection is momentarily down, `request()` returns an empty
  result with an error status; the command path previously treated that as a
  successful fire-and-forget send, silently dropping e.g. a PTZ or `setDevAttr`
  command. It now falls back to a fresh `_mqtt_session` connect (matching the
  `pm is None` path), so a transient outage degrades gracefully instead of losing
  the command or returning stale "no attributes".
- **`_request_sync` no longer raises on a concurrent `close()`.** It snapshots
  `self._client` under the lock and guards the publish, so a shutdown/reload
  nulling the client (or a paho publish error) returns an error tuple rather than
  letting an `AttributeError` escape `request()`.
- **Exactly one persistent connection per account under concurrency.**
  `_get_persistent_mqtt` now serializes get-or-create behind a per-account
  `asyncio.Lock`; the prior double-checked re-check could still let two concurrent
  first-callers each build a `_PersistentMqtt`, colliding on the single authorized
  client_id.
- **SDES ffmpeg teardown reaps the killed child and frees the stderr reader.** On
  the `kill()` path `SdesSession.stop()` now `wait()`s to reap the SIGKILL'd child
  (a raw `Popen` has no asyncio child-watcher, so it otherwise lingered as a zombie
  under reconnect churn), and on a stderr-read timeout it closes the pipe so the
  executor thread blocked in `stderr.read()` on a wedged ffmpeg is released instead
  of leaked.
- **Removed a duplicate `CONF_LOGIN_INFO` definition** in `const.py` (a dead
  `"login_info"` shadowed by `"loginInfo"`); no behaviour change - the effective
  value was always `"loginInfo"`, which the v1.1.3 login_info migration expects.

### Validated
- **Cloud TURN relay delivers media to a genuinely-remote client.** Verified from
  an off-site host on a different public IP: with only the relay candidate offered
  (host/srflx stripped), the camera delivered media through the cloud TURN relay
  (`nominated local=relay`), confirming the relay-default connection mode
  empirically rather than by app-parity inference alone.

## [0.7.36]

### Fixed
- **Orphaned persistent-MQTT stream drain is reaped on teardown.** When a stream
  open is cancelled before a `WebRTCSession` takes ownership of the persistent-MQTT
  drain (the session normally stops it via the `outgoing_q` sentinel), the drain
  would block on `outgoing_q.get` indefinitely with its handler still registered
  on the shared connection. It's now tracked and cancelled in
  `async_stop_streaming` (its `finally` removes the handler), so repeated
  cancel-during-open no longer accumulates drains/handlers. (#74)

## [0.7.35]

### Added
- **Per-camera served-audio gain (`start_keepalive(sdes_audio_gain_db=...)`).**
  The SDES served-audio gain (default `-8` dB) can now be set per camera by the
  caller, in addition to the `AIDOT_SDES_AUDIO_GAIN_DB` env - so a Home Assistant
  install (which can't set env vars) can expose it as an option. New
  `_resolve_sdes_audio_gain_db` resolver (opt wins over env; bad value falls back
  to the default). (#73)

## [0.7.34]

### Changed
- **SDES (battery) camera audio is now ON by default (`AIDOT_SDES_SERVE_AUDIO`).**
  Matching the official app, the SDES serve includes audio. A continuous
  `anullsrc` silence base is `amix`'d under the camera PCMA so the AAC encoder is
  fed from t=0 and the mpegts PMT writes promptly; any gaps are filled with
  silence, so audio from battery cameras streams smoothly. New
  `_resolve_sdes_serve_audio` resolver (per-camera `sdes_audio` opt wins over the
  `AIDOT_SDES_SERVE_AUDIO` env; falsy `{0,false,no,off}` disables). File recording
  (snapshots, diagnostics) is unaffected - always a plain `-c copy`. Soak-validated
  across the battery fleet (video + audio on every open). `AIDOT_SDES_AUDIO_GAIN_DB`
  (default `-8`) trims the hot mic. (#72)

## [0.7.33]

### Fixed
- **SDES teardown no longer hangs on a wedged ffmpeg.** `SdesSession.stop()` read
  ffmpeg's drained stderr with a blocking `proc.stderr.read()` on the event loop;
  if the killed ffmpeg hadn't fully exited (zombie / stuck in uninterruptible I/O
  - the no-media degradation case), the read never reached EOF and hung the whole
  teardown, wedging `async_cleanup` / `async_stop_streaming` / `close()`. It now
  runs in the executor under a 2 s timeout, so a wedged ffmpeg can't stall the
  close. (#71)
- **Cameras no longer hammer the light-protocol TCP:10000 login.** That control
  channel is lights-only - cameras use the separate `CameraLanClient` for local
  control and get their LAN IP from WebRTC signaling. When a discovered IP slipped
  the `_is_camera` gate, `async_login` would log in to a port the camera doesn't
  serve, fail with `login read status error 0 bytes read`, and re-fire every
  broadcast tick. Cameras are now excluded at `async_login` (the single chokepoint
  for the discovery and reconnect-chain paths), and `update_ip_address` is
  throttled to the same 30 s window the reconnect chain uses. Lights are
  unaffected. (#71)

## [0.7.32]

### Changed
- **SDES fast-liveplay is now ON by default (`AIDOT_SDES_FAST_LIVEPLAY`).** The
  official app never waits for/parses `livePlayResp` - it fires `livePlayReq` and
  goes straight to the WebRTC offer/ICE. We now match that by default (~4.5 s
  faster SDES cold start) instead of blocking on the echo/ack. Role-reversal
  models (`_NO_FAST_LIVEPLAY_MODELS`, e.g. A001064) remain hard-excluded for
  correctness. Disable via `AIDOT_SDES_FAST_LIVEPLAY` in `{0,false,no,off}` or
  `_sdes_fast_liveplay_opt=False`. With persistent-MQTT-by-default this brings the
  SDES signaling path to app-parity. (#70)

## [0.7.31]

### Fixed
- **Two-way talk now releases the camera speaker on teardown, so the next talk
  session isn't blocked.** Previously the DTLS `stop()` closed the PeerConnection
  without sending SPEAKERSTOP when talk was active (stall/error/`async_speak`
  paths), and even the clean path closed the transport immediately after
  SPEAKERSTOP without letting it flush - leaving the camera's speaker/talk channel
  bound to the dead session, so the app's (or HA's) next push-to-talk got `851`
  "mic occupied". Now: DTLS `stop()` sends SPEAKERSTOP(849), idles the track, and
  waits a short flush window before closing the PC whenever talk was active
  (idempotent); SDES `stop()` additionally settles after the bridge sends
  SPEAKERSTOP so the camera processes the release over the still-live SCTP before
  teardown. (#69)

## [0.7.30]

### Changed
- **Persistent MQTT connection reuse is now ON by default
  (`AIDOT_PERSISTENT_MQTT`).** This is exactly how the official app behaves - one
  persistent connection per login session for commands, attributes, and stream
  signaling - and the live soak validated it cuts SDES `NO_MEDIA` from ~57% to
  ~19% with no regression (battery cameras roughly doubled their media-delivery
  rate). It is also safer than connect-per-op, which can collide on the single
  authorized client_id. Disable with `AIDOT_PERSISTENT_MQTT` in `{0,false,no,off}`
  or per-camera `_persistent_mqtt_opt=False` (the explicit opt always wins). No
  behaviour change for callers that already set the flag. (#68)

## [0.7.29]

### Changed
- **Persistent MQTT now also carries the stream-open signaling
  (`AIDOT_PERSISTENT_MQTT`) - Phase 2.** When enabled, the WebRTC stream open no
  longer spins up its own connect-per-stream MQTT session; it subscribes and
  registers its handler on the SAME account-level persistent connection that
  commands/attributes use (the stream's `mqtt_cid` IS the authorized
  `mqttClientId`, so it is literally the same connection), drains its outgoing
  queue through it, and does NOT tear the connection down on stop - matching the
  app, which keeps one connection for everything. This removes the per-open
  connect churn that was rate-limiting the cloud account.
  **Soak-validated (live, 7-camera round-robin):** SDES `NO_MEDIA` dropped from
  **57% -> 11%** (n=63 -> n=28) and the connect RuntimeErrors went 6 -> ~0, with no
  per-camera regression - including the previously-worst battery camera going
  from ~15% to ~71% media delivery. The flag is still opt-in (default off);
  `_PersistentMqtt` gained `add_handler`/`remove_handler`/`publish`/`subscribe`
  for the long-lived stream consumer. (#67)

## [0.7.28]

### Added
- **Persistent MQTT connection reuse for commands + attributes
  (`AIDOT_PERSISTENT_MQTT`, opt-in, default off) - Phase 1.** Historically every
  device command (PTZ/settings) and every attribute fetch opened and tore down its
  own cloud MQTT WebSocket; the official app instead keeps ONE persistent
  connection per login session (LDSBaseMqttServiceImpl) and reuses it for
  everything. When enabled, the new `_PersistentMqtt` holds one account-level
  connection (the broker binds auth to the single authorized client_id, so there
  can only be one), subscribes once, replays subscriptions on reconnect, and routes
  `_mqtt_device_cmd` + `async_get_camera_attributes` through it - cutting the
  per-command and per-5-min-attribute-poll connect churn that can trip cloud
  rate-limiting across multiple cameras. The stream-open signaling path is
  unchanged (it uses per-session client_ids) and is a later phase. Connection is
  closed on `AidotClient.async_close`. Validated live: a real device reply
  (`lowPowerActiveStateResp`) collected through a single shared connection
  (`connects=1`); per-op fallback retained when off. (#66)

## [0.7.27]

### Added
- **Adaptive fast-with-fallback for SDES (`AIDOT_SDES_ADAPTIVE`, opt-in, default
  off).** When enabled, the SDES keepalive loop tries the fast path first (skip the
  livePlay waits + TURN relay pre-allocation, with a short 45 s open timeout / 40 s
  media grace) and, if that attempt delivers no media, falls back to the full,
  patient relay path for the rest of the loop. This makes a fast connect safe
  regardless of camera reachability: a LAN-direct camera gets the fast connect; a
  strict-NAT / non-LAN camera loses one fast attempt then connects via the relay. A
  **per-device cache** (`_fast_path_unavailable`) latches a camera that failed the
  fast path so later views skip straight to the full path - bounding the
  fast-timeout penalty to once per camera per session. Role-reversal models keep
  their `sdes_fast_liveplay` exclusion. Enable with `AIDOT_SDES_ADAPTIVE=1` or
  `start_keepalive(sdes_adaptive=True)`. **Default off pending real-world
  fast-failure-rate data** - a fast *failure* costs ~40 s (the grace) before
  fallback while success saves ~7 s, so the failure rate must be characterised on
  real fleets before this becomes a default. Live-validated on the dev-box LAN:
  fast success on mains A001064 (first-media 16.1 s) and battery A001513 (9.96 s)
  with 0 false fallbacks / 0 churn, and the fallback confirmed delivering media
  (11 s) when a fast attempt failed. (#65)

## [0.7.26]

### Added
- **`AIDOT_SDES_SKIP_TURN_PREALLOC` (experimental, opt-in, default off):** skips
  the blocking TURN relay pre-allocation on the SDES path for LAN-direct cameras.
  Before building the offer the SDES path does two synchronous RFC-5766 Allocate
  round-trips (audio + video) to the cloud TURN server so the offer can carry a
  relay address - but on a LAN the camera's host candidate wins and that relay is
  never used. Measured live, the pre-allocation costs ~2-3 s normally and **~4 s
  when the Allocate times out** (which it does from networks that can't reach the
  TURN server), all for `allocated=0`. Skipping it removes that dead wait. The
  cost is now always instrumented (`signaling-wait[...] sdes-turn-prealloc
  elapsed=...`), and `_fast_connect` is unchanged (still force-off for SDES) - this
  flag skips *only* the relay pre-allocation, leaving the SCTP-arming handshake
  intact. Validated live on A001513 (skip on: 9.8 MB over a 130 s hold, 0 SCTP
  churn, healthy heartbeats throughout). Per-camera `sdes_skip_turn` (via
  `start_keepalive`) or the env var; off by default pending a broader soak. (#64)

## [0.7.25]

### Fixed
- **livePlayResp was never matched, so its wait always timed out.** The handler
  matched the response on `devId`, but the camera's livePlayResp payload carries
  no `devId` - it echoes back our exact `peerid`. The wait therefore always ran
  to its full timeout (and the camera's reject `code` was never read). The match
  now keys on the echoed `peerid` (falling back to `devId`), so the wait returns
  the instant the response arrives (live: `elapsed=0ms arrived=True`).
- **Spurious aborts on transient/unknown livePlay codes.** With the response now
  actually parsed, the previously-dead reject path could fire on any non-OK code
  - including `-50019` ("not ready"), which battery cameras emit routinely and
  recover from via ICE. All three reject sites (one SDES, two DTLS) now fast-fail
  *only* on an unambiguous refusal (`livePlay=0`); other non-OK codes (incl.
  `-50019`) are logged as transient and the handshake proceeds. Validated live:
  A001513 SDES (1.67 MB) and A000088 DTLS (frames received) both stream. (#61)

## [0.7.24]

### Fixed
- **SDES fast-liveplay degraded the A001064 (role-reversal PTZ) camera and is now
  excluded from it.** That model's handshake has the camera echo our offer back
  as its own webrtcReq before doing ICE, so it must be armed *before* our
  webrtcReq - and the flag sends webrtcReq ~4.5 s earlier, which dropped its media
  reliability (a live A/B showed 2/2 media with the flag off vs 1/2 with it on;
  the flag's soak validation covered only the A001513 battery cameras). The flag
  now never applies to role-reversal models (`_NO_FAST_LIVEPLAY_MODELS`,
  currently `LK.IPC.A001064`) regardless of the option/env - they keep the full
  livePlay waits. A001513 cameras still get the ~4.5 s saving. (#60)

## [0.7.23]

### Changed
- **SDES fast-liveplay (`AIDOT_SDES_FAST_LIVEPLAY` / `sdes_fast_liveplay`) is now
  validated in soak** and relabelled from "experimental/unvalidated, may
  destabilise" to **validated (opt-in, default off)**. A 3-hour live soak - 15
  SDES opens across battery cameras, the flag engaging on every one, **0 churn /
  0 fail**, with the ~4.5 s signaling saving holding throughout - confirmed it
  doesn't break the handshake or cause near-term churn (it keeps the full
  ICE/TURN/SCTP handshake; only the always-timing-out echo/livePlayResp acks are
  shortened). **Kept off by default** pending broader multi-day use: per-open and
  near-term stability are proven, long-haul sustained stability is not yet.
  Docs/labels only - no behaviour change. (#59)

## [0.7.22]

### Fixed
- **The experimental SDES fast-liveplay flag (0.7.21) was dead code for SDES
  cameras** - its gate lived inside the DTLS-only `if not use_sdes:` block, so it
  never ran for the SDES cameras it targets (explaining the earlier "no effect").
  Moved the gate into the SDES open path where the SDES livePlay waits actually
  live, so it now engages.

### Changed (experimental)
- **`AIDOT_SDES_FAST_LIVEPLAY` now shortens the always-timing-out SDES signaling,
  not just the livePlayResp wait.** Instrumentation (see below) showed that for
  the SDES cameras measured, both the livePlayReq-echo wait (5 s) and the
  livePlayResp wait (1 s) **always time out** (echo/resp never arrive) yet
  streaming succeeds - ~6 s of dead padding. With the flag on, the echo wait is
  capped at 1.5 s and the livePlayResp wait is skipped: **~6 s -> ~1.5 s of
  signaling (a deterministic ~4.5 s saving)**, with the full ICE/TURN/SCTP
  handshake untouched. Still EXPERIMENTAL/off by default - stability over a real
  soak is unverified; enable to test and watch for SDES session churn.

### Added
- **`signaling-wait[<device>] <name> elapsed=<ms>` instrumentation** for the SDES
  (and DTLS) livePlay/iceConfig waits, so the actual cost of each wait is
  measurable from the logs (the `sdes_soak_monitor.py` validation tool reads
  these alongside the `cold-start[...]` markers). (#57)

## [0.7.21]

### Added (experimental)
- **`AIDOT_SDES_FAST_LIVEPLAY` / `start_keepalive(sdes_fast_liveplay=...)`** - an
  **experimental, unvalidated** opt-in that skips *only* the ~2 s `livePlayResp`
  blocking wait for SDES cameras, keeping the full ICE/TURN/SCTP handshake (the
  part whose skipping destabilises SDES - which is why full `fast_connect` stays
  forced off for SDES). Theory: shaves ~2 s off the SDES cold start without the
  SCTP churn that full fast-connect causes. **Off by default.** A clean synthetic
  A/B was not achievable (SDES cameras degrade on rapid reconnects and the
  available test cameras are battery / role-reversal), so this needs a real-world
  soak before any default change - **may destabilise SDES; enable at your own
  risk and watch for session churn.** (#55)

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
  `cold-start[<device>] <phase> +<ms>` markers (webrtcReq -> first-media ->
  serving) on both serve paths, so a cold connect's timeline is measurable
  without a debugger. Best-effort: never raises, no-op when no open is in flight.
- **Warm-hold option.** `start_keepalive(stream_idle_s=...)` overrides
  `AIDOT_STREAM_IDLE_S` (default 120 s); `<= 0` keeps the warm WebRTC session
  forever so re-views are instant - intended for mains cameras (it holds a
  concurrent-stream slot + continuous decrypt for the camera's lifetime, so stay
  within `AIDOT_MAX_CONCURRENT_STREAMS`, default 3). Default behaviour unchanged.

### Changed
- **Denser SDES startup PLI burst** so the first decodable keyframe arrives
  sooner on a cold open: keyframe requests now ramp 0 / 1.5 / 3.5 / 6.5 s then
  the same 30 s safety PLI (was 3 PLIs at a flat 5 s -> first IDR up to ~10 s).
  Tunable/revertable via `AIDOT_SDES_PLI_GAPS`. (#52)

## [0.7.18]

### Fixed
- **Cold-start blank video: the first view of an idle camera (or the first view
  after an HA restart) could fail to load.** go2rtc pulls the library's local
  `ffmpeg -f mpegts -listen 1` socket before ffmpeg has bound it - ffmpeg only
  opens its `-listen` output after probing input, which needs the ~16-25 s WebRTC
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
  environment-variable reference - the knob added in 0.7.15 to gate the
  `liveStreamParam` cloud pre-connect that provisions battery cameras (set `0` to
  disable). No code changes. (#45)

## [0.7.15]

### Fixed
- **Battery cameras (A001513/A001108/A001360, e.g. the L2 models) produced no live
  media.** They rejected every MQTT `livePlayReq` with code `-50019` ("not ready")
  and never ran ICE - even fresh-rebooted, app-closed, and uncontended - while
  mains cameras of the same model streamed fine. Root cause: the official app
  performs a cloud pre-connect the library skipped (`KVSPreConnectStrategy.
  fetchKvsParams` -> `POST /api/ipc/liveStream/liveStreamParam`) that provisions the
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
  `delay *= 2`. Randomized spread stops a degraded camera - or a fleet reconnecting
  at once - from synchronizing into reconnect storms / cloud rate-limiting. The
  loops also escalate backoff only when a session opens but never delivers media
  (the camera-degradation case) and reset after a session that streamed; the
  decrypted-RTP liveness watchdog still drives *when* to restart. (#41)

> 7 new unit tests (`tests/test_backoff.py`). Validated live: all three DTLS
> cameras and a mains SDES camera re-establish through the rewired loops.

## [0.7.13]

### Fixed
- **SDES H.265 streaming recorded 0-byte video** (#39): SDES cameras stream H.264
  (pt=96) *or* H.265 (pt=97), varying per session, but the generated SDP listed
  both (`m=video ... 96 97`) - so ffmpeg bound its depacketizer to the first
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
  stream is deregistered on stop. Best-effort throughout - go2rtc errors never
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
  Behavior-preserving: all existing imports keep working via re-exports. (#29-#35)

> Validated live end-to-end through a real go2rtc server (camera -> library serve
> -> go2rtc -> ffprobe: H.264 1280x720 + AAC) in addition to the mocked unit suite
> (`tests/test_go2rtc.py`, 8 cases).

## [0.7.11]

### Changed
- Began splitting the monolithic `camera/client.py`: wire/protocol constants now
  live in `aidot/camera/constants.py` and are re-imported into `client.py`
  (behavior-preserving; all existing imports keep working).
- Renamed the worst cryptic private STUN/TURN helper closures for readability
  (`_mi_ta` -> `_stun_message_integrity`, `_br_a`/`_si_a` -> `_build_stun_attr`,
  `_rr_accept_cam_cert` -> `_accept_camera_cert`). No behavior change.

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
