# Changelog

All notable changes to `python-aidot-cameras` are documented here. The format is
based on [Keep a Changelog](https://keepachangelog.com/), and this project uses
date-less, incrementing versions published to PyPI via GitHub Releases.

## [0.12.17]

### Fixed
- **SDES cameras negotiated a session and then delivered nothing.** Every
  A001513 and A001064 was affected, on released 0.12.16 and across two accounts;
  DTLS (A000088) was untouched, which is why half the fleet looked healthy and
  this stayed hidden. The harvest that reads the camera's `webrtcResp` gave the
  answer a single event-loop cycle and took it only if it had already resolved -
  but the STUN window ahead of it closes on a fixed schedule about 2.4 s BEFORE
  the answer lands (`webrtcReq` -> answer = 2.9 s measured on an A001513). The
  answer SDP was therefore always empty, and everything downstream behaved as
  though the camera had never replied: no ICE credentials, so no
  `USE-CANDIDATE`, so a **controlled** ICE agent that sits in "Checking" forever
  and never sends SRTP, and no camera SRTP keys for the bridge either. The 75 s
  first-media wait had nothing to wait for. The path now waits for the answer -
  bounded by `_PRE_LAUNCH_ANSWER_WAIT_S`, and `asyncio.shield`ed so a timeout
  cannot cancel the future the real answer await and the DTLS-fallback path
  still consume - and nominates from the answer during the first-media wait as a
  bounded fallback. Raising `_FIRST_MEDIA_WAIT_S` never helped and never could:
  that wait was tracking itself, not the camera.

  Measured: `an A001513 unit` (A001513, battery) NO_MEDIA / 80 s / 0 bytes before, **PASS
  on the first attempt, 7.9 s, 2646 packets / 2.9 MB** after, decoding as h264
  1280x960 with `video_pt=96 audio_pt=8`. A001064 PTZ NO_MEDIA before, **PASS
  16.7 s / 2.5 MB** after.

- **The TURN relay was allocated and advertised as a candidate, but never
  used.** Three separate gaps, each of which alone makes the relay unusable as a
  fallback for a camera that cannot be reached directly. A permission was
  installed only for the candidate in the answer SDP, which is the camera's
  private host address; a TURN permission is matched against the peer's source
  address *as the relay sees it* (RFC 5766 s9), so that authorises an address
  that can never arrive. Permissions now cover every candidate, including the
  srflx and relay addresses that only appear via `iceCandidateReq` trickle, and
  are refreshed inside the permission lifetime - and two of the three allocation
  paths stored a 7-field tuple where the helper requires 8, so on those paths
  the permission was silently never sent at all. Nothing was ever *sent* through
  the allocation either: the Send indication was emitted only while handling
  data that had already arrived through the relay, which cannot bootstrap, and
  since ICE validates a candidate *pair*, a camera that only ever saw checks
  leaving from our host socket never nominated the relay candidate we
  advertised. Connectivity checks now also go out through the allocation, and
  the bridge wraps its replies when the peer reached us relayed. Finally the
  allocation itself was never refreshed, and this TURN server grants 600 s and
  drops it silently on expiry, so a held session lost its relay mid-stream.

  Measured on an A001513 before the send-side change: CreatePermission confirmed
  by the server for the camera's host, srflx and relay addresses, and still zero
  relay-carried inbound packets, because nothing we sent ever left from the
  relay.

- **L2 / battery cameras that stream from a standalone run but never under Home
  Assistant.** The same library code took two different paths because the
  battery-hostile knobs were reachable from a consumer's settings, and a
  standalone run never sets them. Three of those knobs are now decided by the
  library instead of by the caller:
  - **The `liveStreamParam` KVS pre-connect can no longer be turned back on.** It
    provisions the camera's session toward AWS KVS, so the camera sends its media
    there instead of to the SDES bridge: the session negotiates, the bridge binds,
    and no video RTP ever arrives. It is made for **battery cameras only** - i.e.
    exactly the cameras it breaks - and the `-50019` ("not ready") livePlayResp it
    was added to cure is benign (mains cameras emit it too and recover via ICE).
    0.12.15 turned it off by default but left the option/env able to re-enable it,
    which is a foot-gun aimed at one foot: a consumer that surfaces it as a
    setting (Home Assistant surfaces these because HA OS cannot set env vars)
    re-breaks every battery camera on the account, and "negotiates, then shows
    nothing, forever" looks nothing like a settings problem. The decision now
    lives in one place and is "no". `AIDOT_LIVESTREAM_PARAM` and
    `start_keepalive(live_stream_param=...)` are accepted and ignored, logging one
    warning per camera so a stuck install says why in the log.
  - **Adaptive fast-connect is refused for battery cameras.** The saving it chases
    is the TURN relay pre-allocation, which is force-kept for a battery camera
    (its only return path), and fast-liveplay is already on by default - so a
    battery "fast" attempt runs the identical handshake and differs only in being
    given 45 s to open and a 40 s media grace, *inside* the documented 25-70 s
    battery cold-start window. A slow-but-healthy wake was scored as a fast-path
    failure: it latched `_fast_path_unavailable`, escalated the backoff, and burnt
    a camera-side session on a device that frees them slowly.
  - **Battery cameras are detected from their own cloud data, not just a model
    list.** `is_battery_camera` now also resolves from a numeric
    `Battery_remaining` (the signal `lan_control.is_mains_powered` inverts) or
    `batteryMode == 2`, falling back to the model list when the cloud says
    nothing. Every battery protection hangs off this one property - the TURN relay
    pre-allocation, the cloud keep-alive renew, the HTTP wake, the adaptive
    refusal above, `powerType=2` - so an unlisted battery revision lost all of
    them at once, and lost them *only* under a consumer that sets the LAN-direct
    options. Evidence can only add a camera to the battery set, never remove a
    listed one, so mains cameras keep their optimizations. `powerType` is
    deliberately not read as evidence (it is a field we send, and its neighbour
    `p2pCache` reads 2 on every camera).
- **Model ids are matched consistently by substring.** The plain-RTP (TUTK-framed)
  set was compared for exact equality, so a revision suffix - `LK.IPC.A001513-1`,
  the way `A000088-1` already exists on the DTLS side - read as a standard-SRTP
  camera: ffmpeg then tried to decrypt TUTK frames with the announced fake key and
  the bridge never stripped the TUTK header, giving a session that negotiates and
  delivers nothing decodable. The battery-model list and the `powerType` wire value
  were also re-typed inline in three files; both now derive from one definition, so
  a guard cannot drift out of step with the payload the camera is sent.

- **A waking battery camera gets a fast retry instead of an escalating backoff**
  (the follow-up left open in 0.12.16: "a rapid third consecutive session can
  still hit `-50019` (battery wake-readiness)"). The camera answers `livePlayResp`
  with `-50019` and then sends no media - it was not refusing, it had not finished
  waking - and the SDES keepalive loop could not tell that apart from a degraded
  camera, so the pacer escalated (10 s -> 300 s) on a camera that would have been
  ready seconds later. Under a consumer that re-opens per view (HA idle-releases
  after 120 s), that is the difference between a slow first frame and a live view
  that never fills in.

  The wake signal was also being **discarded**: `sdes_fast_liveplay` is on by
  default and skips the `livePlayResp` wait, so on the SDES path - the only path
  battery cameras use - nothing ever read the code. It is now recorded whenever it
  arrives, whether or not anything is waiting for it.

  The retry reuses the DTLS serve loop's `_retry_policy`, bounded to the peerid
  reuse window so the whole burst re-offers on **one** peerid (a fresh peerid
  registers another camera-side session - the 0.12.16 wedge), then hands back to
  the normal pacer. Scoped to battery cameras: on a mains camera `-50019` is
  transient noise and a no-media session really is degraded. `-50019` still never
  aborts an open - it is benign on its own, per 0.12.15; only the retry *delay*
  keys on it, and only after a session has already failed to deliver media.

  **Validated on hardware** (A001513 under Home Assistant): the camera answers
  `-50019` and the loop now logs `camera not ready (waking, livePlayResp -50019)
  and sent no media - fast retry in 3s [1/3]` and re-offers on the same peerid,
  where before the signal was discarded entirely and the pacer escalated. The
  retry behaves as designed and is bounded.

  It does **not** by itself make that camera's live view fill in: on the fleet it
  was measured against, the camera answers `-50019` and sends no media on every
  attempt of the burst. The remaining cause is upstream of this retry and of
  every knob in this release - the open profile resolves correctly for that
  camera (`battery=True (cloud-reported=True) powerType=2 turn-prealloc=kept
  adaptive=False`), and the identical library and configuration streams the same
  camera from a different host on the same subnet. See
  `tests/test_wake_readiness_retry.py`.

### Notes
- The L2 / battery hardening in this release was measured against a live A001513
  under Home Assistant. The open profile confirms every decision now resolves
  correctly for it (`battery=True (cloud-reported=True) powerType=2
  turn-prealloc=kept adaptive=False`), and a mains SDES camera on the same fleet
  still reports `battery=False powerType=1 turn-prealloc=skipped` - so the
  battery detection change does not misclassify mains cameras or alter the
  `powerType` they are sent. The battery live view that still did not fill in
  when that work was written was **not** a host or network difference, as was
  assumed at the time - it was the SDES answer harvest above, which stopped
  every SDES camera on every host from ever being nominated. With that fixed the
  same A001513 streams in 7.9 s.
- **Battery streaming is not universally fixed.** On the reference fleet one
  A001513 (`an A001513 unit`) passes in 7.9 s while a second (`a second A001513 unit`) still returns no
  media, handshaking for ~110 s and absent from `arp-scan` throughout: it is
  genuinely asleep rather than mis-negotiating, and nothing in this release
  wakes a camera that will not wake. That is the remaining battery problem, now
  cleanly isolated from the SDES bug that was masking it.

### Added
- **A one-line open profile per camera, at INFO.** Names the decisions that
  determine whether media can arrive at all - model, transport, battery (and
  whether the cloud reported it), `powerType`, whether the TURN pre-allocation was
  kept or skipped, adaptive on/off. This whole failure class (a session that
  negotiates and then delivers nothing) is usually one of these resolving the
  wrong way, and it was otherwise invisible: the handshake logs look identical
  either way. At INFO so a bug report carries it without having to re-enable DEBUG
  and reproduce.

### Changed
- `tests/test_live_stream_param.py`'s regression lock now calls the **real**
  resolver. It previously re-implemented the gate as a local helper and asserted
  against that copy, so the shipped gate was uncovered and the copy was free to
  drift from it - a flag that must stay off was "locked" by a test that could not
  see it.

## [0.12.16]

### Fixed
- **SDES cameras: honour the camera's own "no free session" refusal.** A terminal
  webrtcResp ack (`-50002` max concurrent streams / `-50015`) was recorded for
  both transports but only ever acted on by the DTLS path. On the SDES path the
  camera saying "full" looked like a generic failure, so the keepalive loop
  retried on the short backoff - and every retry minted a new peerid, i.e. yet
  another camera-side session - which is self-sustaining and can wedge a battery
  camera. The SDES branch now raises `AidotCameraBusy` on a terminal ack and the
  SDES keepalive loop backs off for the full release window, matching the DTLS
  loops.
- **SDES keepalive reuses one peerid across its retries** instead of minting a
  fresh one per attempt. A fresh peerid registers a new camera-side session and
  the camera frees old ones only slowly, so mint-per-retry stacks up sessions on
  a failing loop faster than they drain. The loop now holds one peerid across
  retries (mirroring the official app, which resends within one session) and
  rotates to a fresh one only after a session that delivered media or after a
  small reuse cap. The DTLS/mains path is unchanged (different loop; the new
  `reuse_peer_id` kwarg defaults to None).

Measured on an A001513 (L2): two back-to-back live sessions now both stream
h264 1280x960 + PCMA where previously the second in a row failed.

## [0.12.15]

### Fixed
- **A001513 "L2" battery cameras serve live video again.** The `liveStreamParam`
  pre-connect - an AWS-KVS provisioning call made only for battery cameras -
  provisions the camera's session toward KVS, and its media then goes there
  instead of to the SDES bridge: the session negotiates, the bridge binds, and
  no video RTP ever arrives. It was added to cure a `-50019` ("not ready")
  livePlayResp, but `-50019` is benign - mains cameras emit it too and recover
  via ICE - so it addressed a non-bug at the cost of the battery live view. The
  pre-connect is now **off by default**; re-enable it per camera with
  `start_keepalive(live_stream_param=True)` or `AIDOT_LIVESTREAM_PARAM=1`.
  Measured on an A001513: h264 1280x960 + PCMA, stable across a 180 s probe
  series, where before there was no media at all.
- **Battery cameras no longer skip the TURN relay pre-allocation.** A battery
  camera sleeps, is woken through the cloud, and has no LAN IP, so its media
  returns over the relay. The LAN-direct optimization (`sdes_skip_turn` /
  `AIDOT_SDES_SKIP_TURN_PREALLOC`, which Home Assistant's "LAN-direct"
  connection mode enables) would leave it with no return path. Mains SDES
  cameras still honour the option.
- **SDP payload narrowing works for every payload list.** It replaced the
  literal `" 96 97"`, so it silently stopped narrowing once the plain-RTP video
  line grew a third payload (98, the second H.265 variant). It now rewrites
  whatever list the m-line carries and drops the other codecs' `rtpmap`/`fmtp`.
- **An RTSP push no longer dies when audio is unusable.** Push copies every
  input stream, so an audio line that could not be narrowed (its payload type
  was never observed) made the ANNOUNCE fail with `400 Bad Request` and took the
  whole publish down. Push now maps video only in that case, and the serve
  command is built once - after the first-media wait resolves the real payload
  types - so both the narrowing and the push decision see the observed codecs.

## [0.12.14]

### Fixed
- **DTLS cameras serve live video again.** The serve-wait loop's viewer-check
  block ended in an unconditional `break`, so any positive idle window
  (`stream_idle_s` / `AIDOT_STREAM_IDLE_S` > 0) tore down a HEALTHY ffmpeg after
  a single 0.5 s tick and respawned it forever - ffmpeg died at output-open with
  "Immediate exit requested", go2rtc saw 404s or no data, and every DTLS camera
  went dark. `stream_idle_s=0` skips the block entirely, which is why
  never-release configurations kept working and hid the bug. The break now fires
  only on an actual idle release.
- **The A/V mux no longer lets missing audio hold video hostage.** The mux
  declares an AAC stream up front; when no audio packets arrive, libavformat's
  interleave queue (10 s of stream time by default) buffered EVERY video packet,
  so the serve ffmpeg starved at its input probe and never bound its `-listen`
  port. The muxer now runs with `max_interleave_delta=100ms`, so video flows
  whether or not audio ever shows up.
- **A dead serve pipe now ends its mux thread instead of starving the next one.**
  FFmpeg's mpegts muxer surfaces custom-IO write errors lazily (at trailer
  time), so muxing into a dead pipe appeared to succeed forever. A mux thread
  abandoned by the teardown's 2 s join then drained the SHARED packet queues
  into EPIPE, starving the replacement serve (observed live: 17 leaked threads
  per camera). A write failure recorded at the file-object layer now ends the
  thread within one iteration, and the raw pipe is closed before the container
  so the trailer flush fails fast instead of hanging.

## [0.12.13]

### Fixed
- **A dormant camera no longer leaves a dead stream in go2rtc.** Idle-release
  stopped the serve but left the stream registered, pointing at a source that no
  longer existed - so a viewer attaching while the camera slept got a hard
  `connection refused` on the serve port instead of a clean miss. In PUSH mode
  that source was never dialable at all, because the keepalive publishes INTO
  go2rtc rather than serving over HTTP. Both release paths now deregister, and a
  failure to do so cannot break going dormant.
- **A consumer disconnecting is no longer reported as a failure.** ffmpeg returns
  `AVERROR(EPIPE)` = -32 when its output consumer goes away, and a process exit
  status is an unsigned byte, so it surfaces as **224**. That is the normal end of
  a `-listen 1` serve - go2rtc disconnects and ffmpeg exits - but it was logged at
  WARNING every time, which trains the reader to ignore the log. Real failures
  still warn.

## [0.12.12]

### Fixed
- **No video at all, caused by 0.12.10's own viewer check.** The DTLS watchdog
  loops twice a second, and the viewer check added in 0.12.10 sat inside it -
  opening a fresh HTTP session to go2rtc on every tick. That is two requests per
  second per camera, aimed at the very service that also has to serve the video;
  on a small fleet it was enough to stop go2rtc answering at all, so every camera
  went black while the integration still reported them as streaming.

  The answer is now cached for 10s, so the watchdogs can ask on every tick and
  go2rtc is asked at most once per camera per interval. Idle release is measured
  in minutes, so seconds of staleness cost nothing.

## [0.12.11]

### Fixed
- **A go2rtc stream could be registered as its own source.** In push mode the
  keepalive publishes INTO go2rtc, so `_keepalive_rtsp_url` is go2rtc's own
  address for that stream - and registering it as the stream's source made go2rtc
  its own producer. The stream then lists a producer, nothing feeds it, and every
  consumer gets a connection with no media. Observed live: a camera with two
  producers, one of them its own `rtsp://127.0.0.1:8554/aidot_<id>`, answering
  HTTP 200 with a zero-byte frame. Registration is now skipped when it would
  loop - in push mode the publisher already feeds the stream, so there is nothing
  to register.

  Only reachable by a consumer that passes `go2rtc_url` to `start_keepalive`,
  which the Home Assistant integration began doing in 2.9.8 so the viewer-aware
  idle check could work. Anyone on that combination should take this.

## [0.12.10]

### Fixed
- **The DTLS cameras never released either - 0.12.9 only fixed half of it.** The
  DTLS serve loop decided idleness from pipe-progress staleness, which is the same
  unanswerable question in disguise: the pipe only backs up when nothing drains
  the serve socket, and go2rtc drains it forever as the producer. On a fleet that
  is mostly DTLS cameras the 0.12.9 fix therefore changed nothing. Both loops now
  ask who is watching, and both keep the old heuristic as a fallback when nobody
  can answer.
- **The idle check lied in push mode.** With an RTSP push URL the "serve port" is
  go2rtc's *shared* 8554, where every camera's own publisher is connected - so the
  socket check reported a viewer for every camera, forever. It now answers
  "unknown" there instead of a confident wrong "yes".
- **The SRTP key-restart undid two shipped fixes.** It rebuilds the ffmpeg SDP from
  scratch and (a) wrote `RTP/SAVP` with `a=crypto` for models where the bridge
  forwards PLAIN RTP - making ffmpeg fail authentication on every already-decrypted
  packet, so a working stream dropped to zero bytes mid-session - and (b) discarded
  the payload-type narrowing, so an H.265 camera lost all video and with it the
  PAT/PMT and the entire output. The restart now matches the primary SDP on both.
- **A failed stream slot could be lost permanently.** The serve relay was started
  outside the `try` that releases the slot; `Thread.start()` raises `RuntimeError`
  under thread exhaustion, which escaped before the `try` and burned a permit for
  the life of the process, silently shrinking the cap until nothing could stream.
- **Teardown could skip itself.** `join()` on a thread that never started raises,
  replacing the real exception and skipping the ffmpeg terminate and session stop
  that follow - orphaning ffmpeg on its serve port.
- **Background tasks leaked on every failed handshake.** Only a successful session
  cancelled them, so each failed open left an immortal 10s-tick AVIO heartbeat task
  pinning a closed peer-connection graph. A camera that fails repeatedly grew them
  without bound.
- **The cold-start wait was inside the window it waits for.** 45s sat within the
  documented 25-70s battery cold start, so a camera at the slow end still launched
  with no payload types known. Now 75s.

## [0.12.9]

### Fixed
- **Every camera streamed forever after a single view.** The idle-release check
  asked "is a TCP client connected to the serve port?" - but go2rtc attaches to
  that port as the stream's PRODUCER and stays attached for as long as the stream
  is registered, viewer or no viewer. So a peer was always present, the idle
  window never elapsed, and nothing ever went dormant: open one camera and all of
  them keep decrypting indefinitely, holding a concurrency slot each and draining
  battery models. Measured live: five cameras, nobody watching, still producing
  after 7 minutes against a 5 minute idle window.

  Presence is now asked of go2rtc, which is the only component that knows whether
  anyone is actually watching (`consumers`), falling back to the socket check when
  go2rtc is not in use. Unknown still means "do not release", so a host that can
  answer neither keeps today's behaviour.

## [0.12.8]

### Fixed
- **A camera beyond the concurrency cap never streamed at all.** The cap on
  concurrently-active serves (`AIDOT_MAX_CONCURRENT_STREAMS`, default 3) is a
  host-protection guard, but a camera holds its slot for the life of its serve -
  so on an account with more cameras than the cap, the extras did not queue
  politely, they simply never played, and nothing surfaced an error. Confirmed
  live on a fleet with 4 DTLS cameras: the library logged
  `waiting for a stream slot (cap reached)` for the fourth on every attempt, and
  that was exactly the camera that would not play in Home Assistant.

### Added
- `configure_stream_limits(max_streams)`, exported from the package, so a consumer
  that knows its camera count can raise the cap to fit. The cap only ever grows -
  shrinking would strand a camera already holding a slot - and an explicit
  `AIDOT_MAX_CONCURRENT_STREAMS` still wins, since an operator who capped a small
  host would rather cameras took turns than have the host fall over.

## [0.12.7]

### Fixed
- **Camera audio is now reliably present, not a coin flip.** 0.12.6 narrowed the
  audio payload type but could only do so if the payload type was known before the
  serve launched, which made audio intermittent. Measuring when media actually
  arrives showed the assumption behind that was wrong: audio is not late. Video and
  audio payload types arrive **40-70 ms apart** (the camera answers BUNDLE, so both
  share one 5-tuple) - what failed was that a cold session's media does not start
  for ~21 s, and the launch was gated on a 15 s deadline. It expired first, so the
  serve launched with *both* payload types unknown and the audio line kept
  advertising PCMU on a PCMA camera.

  The launch now waits for the session's first media (sized to the documented
  25-70 s cold-start window) rather than to a deadline shorter than the camera's own
  startup, and the audio grace on top is **tightened from 4 s to 1 s**, since it
  only has to absorb 40-70 ms of jitter. Waiting for first media costs no picture
  latency - ffmpeg cannot produce before media exists, and launching earlier only
  binds the wrong depacketizers.

  Verified live: launch decision at +1.72 s with both payload types known, and a
  capture of 1032192 bytes carrying an `h264 1280x960` track and an `aac` track.

## [0.12.6]

### Fixed
- **Camera audio works again, and can no longer cost the video.** Root cause: the
  SDP handed to ffmpeg advertises two payload types per media line
  (`m=video ... 96 97` for H.264/H.265, `m=audio ... 0 8` for PCMU/PCMA) because
  the camera picks one per session. ffmpeg binds each depacketizer to the FIRST
  payload type listed and silently discards the rest. The bridge synthesises PCMA
  (pt 8) while the audio line offers PCMU (pt 0) first, so every audio packet was
  discarded - and because the mpegts mux withholds its PAT/PMT until every mapped
  stream has produced a packet, the consumer received ZERO bytes, losing the video
  along with the audio. That is why enabling audio looked like it broke streaming.

  The existing H.265 fix already narrows the video line this way and was never
  applied to audio; on the TUTK-framed path the video payload type was not
  recorded either, so neither line was being narrowed there. Both payload types
  are now recorded and both lines narrowed, and the narrowing is a tested pure
  function rather than a nested closure.
- **Audio can never take the picture down again.** If the audio payload type
  cannot be characterised before the serve launches, the serve starts video-only
  with a warning instead of mapping a stream that would stall the mux. Verified
  live: with audio enabled the served mpegts now carries an H.264 1280x960 track
  AND an AAC track where it previously delivered nothing - 1.2 MB in a single
  capture, more than the 843 KB the video-only path managed - and on a session
  where no audio arrived in time it served 557 KB of video-only rather than zero.
- **Audio is on by default again**, since it can no longer cost the picture. The
  0.12.5 warning that enabling it would serve no video is removed, being no longer
  true.

### Known limitation
- **Audio is present only when the camera sends some before the serve launches.**
  It gets a short grace on top of the video payload-type wait, deliberately short
  so a camera that never sends audio does not delay the picture. A session that
  misses that window serves video-only until the next ffmpeg restart re-runs the
  wait. Measured across runs on one battery camera: some sessions carried both
  tracks (1.2 MB in a capture), others video-only (282 KB). Making this
  deterministic needs the serve to be upgraded in place once the payload type
  becomes known, which is not in this release.

## [0.12.5]

### Changed
- **Enabling serve audio now says what it costs.** 0.12.4 made audio opt-in
  because mapping it serves no video on these cameras; opting in was still a
  silent trap. The serve now logs a warning naming the symptom and how to turn it
  back off. No behaviour change - and deliberately not a fix: skipping the audio
  mapping when no audio is observed does not help, because sessions that DO
  observe audio fail the same way. The cause is not yet isolated, so the honest
  move is to warn rather than to ship a fix that does not.

## [0.12.4]

### Fixed
- **Camera audio no longer costs the video.** The mpegts mux writes its PAT/PMT
  only once every mapped stream has produced a packet, and `amix` does not emit
  until every one of its inputs has delivered a frame. On a camera that sends no
  PCMA, the AAC encoder therefore never produced, the PMT was never written, and a
  consumer got an accepted connection followed by zero bytes - no video at all,
  even though signaling was healthy and the library had logged first media. The
  continuous silence base was meant to prevent exactly this and does not. Measured
  live on a battery camera: with audio on, 0 bytes across 45 consecutive attach
  attempts; with audio off, 303 KB of 1280x960 H.264 from the same session.
  Serve audio is now opt-in (`sdes_audio` per camera, or
  `AIDOT_SDES_SERVE_AUDIO=1`), because missing audio is a worse experience while
  no video is a broken integration.

## [0.12.3]

### Fixed
- **Live video survives a restart again.** The MQTT password is a rotating cache,
  not account state: the broker issues a new one on every account login and
  allows a single connection, so a stored copy is stale the moment anything else
  logs in (the phone app is enough). It was nonetheless persisted with the rest
  of `login_info`, so a dead credential was reloaded from disk on every start and
  the broker refused it forever (`rc=134`) - which killed WebRTC signaling, and
  therefore live video, while snapshots kept working. It is now runtime-only.
- **A missing MQTT password is fetched on demand, not only at login.** Making the
  password runtime-only exposed the other half of the bug: the fetch had a single
  caller, the full login, and a restart from a stored token never reaches it (the
  proactive refresh takes the refresh-token path). Cameras now request one
  whenever it is absent, coalescing concurrent callers into a single request, with
  a floor between attempts after a refusal. Without it the credential resolver
  fell through to its last resort - the access token as an MQTT password - which
  the broker rejects, so the failure looked like working code.
- **A credential refusal now heals the whole account, not one camera.** The cached
  credential is per device client while the credential itself is per account, so
  a refusal on one camera left every sibling holding the dead password and each
  one burned its own refusal in turn. The account-shared value is now the single
  source of truth, and a cache derived from it is treated as stale once it is
  gone.
- **A refused MQTT client is shut down instead of retrying forever.** It was only
  dropped from the account cache; paho's own retry loop kept re-offering the
  rejected password to the broker for the life of the process, silently, because
  the refusal is reported once. Retiring it also releases callers that would
  otherwise wait out a full timeout on a connection that could never open.
- **The MQTT password no longer reaches storage inside the raw userConfig blob.**
  That response is kept in `login_info` for its client id and is persisted; the
  credential is now stripped out of it.
- **A broker refusal also clears the cached server URL**, so the one response that
  can carry a server-issued password is re-read on the recovery path instead of
  being skipped.

### Added
- `DeviceState` is re-exported from `aidot_cameras.device_client`, so a consumer
  can tell whether a device client's LAN session is authenticated without
  importing `aidot` directly - which for consumers is an undeclared dependency.

## [0.12.2]

### Fixed
- **A rotated MQTT password no longer breaks camera signaling permanently.** The
  broker issues a new MQTT password on every account login and allows one
  connection at a time, so anything else logging in (the phone app is enough)
  invalidates the cached one. The cached copy lives in `login_info`, where it
  short-circuits the credential fetch, so every reconnect reused the dead
  password and the broker refused it forever (`rc=134`) - which silently killed
  WebRTC signaling and therefore live video, while snapshots kept working. A
  credential refusal now clears the cached password and drops the persistent
  client so the next use fetches a fresh one.
- **go2rtc stream registrations no longer leak.** go2rtc answers the register
  call with 400 when it cannot immediately validate the source (a camera that
  has not started producing yet) but still keeps the stream registered. That was
  treated as failure, leaving the pull URL unset - and deregistration was gated
  on that URL, so every such attempt left a dead stream behind with a producer
  nothing was feeding.

## [0.12.1]

### Changed
- **Devices this library cannot build a client for are summarized, not warned
  about individually.** An account with Zigbee sub-devices, remotes or other
  accessories that carry no usable `aesKey` logged one WARNING per device on
  every device-list refresh (21 of them on a real account) for devices that were
  never supported here. Each is now DEBUG, with a single INFO line naming the
  models and counts.

### Documented
- **`docs/UPSTREAM.md` now records that upstream is LAN-only by design**: its
  cloud API is inventory plus auth with no control endpoint, and every setter
  refuses unless the LAN session is authenticated. Includes the read-only broker
  probe evidence showing the cloud carries no bulb state, so consumers do not
  mistake cloud reachability for controllability.

## [0.12.0]

### Changed

- **BREAKING: this package now extends upstream `python-aidot` instead of forking
  it, and its import name is `aidot_cameras`.** Upstream ships as a pinned
  dependency (`python-aidot==0.3.55`) and owns the `aidot` import name again; no
  upstream file is vendored or edited here. Code that imported `aidot` /
  `aidot.camera` from this distribution must import `aidot_cameras` /
  `aidot_cameras.camera` instead. The camera API itself is unchanged.
- **Non-camera devices are handled entirely by upstream.**
  `AidotClient.get_device_client()` is the single dispatch seam: cameras get this
  package's client, and lights, plugs and switches fall through to upstream's own
  `DeviceClient` with none of this package's code in their call graph.
- **Taking a new upstream release is now a dependency bump plus a test run**,
  not a merge. `tests/test_upstream_compat.py` is a seam contract that fails fast
  and names the exact upstream symbol if a release moves something this package
  builds on. See `docs/UPSTREAM.md`.
- The RGBW+CCT `active_color_mode` fix is carried as a narrow, marked override
  applied only to those bulbs while
  `AiDot-Development-Team/python-AiDot#6` is open; it is removed once that merges,
  after which those bulbs also run pure upstream code.

## [0.11.14]

### Fixed
- **RGBW+CCT bulbs no longer report a stale color while resting in white/CCT
  mode.** These bulbs report state as deltas - a color-temperature change carries
  only CCT, a color change carries only RGBW - but the login-sync (getDevAttr)
  returns both the retained RGBW register and CCT together, which is ambiguous.
  The device status now records the active color mode from unambiguous
  single-field deltas and leaves it unchanged on the ambiguous both-present sync,
  so a bulb set to a color temperature is no longer surfaced as its last RGB
  color. Exposed as `DeviceStatusData.active_color_mode` for the Home Assistant
  integration to map to color_temp vs rgbw.

## [0.11.13]

### Fixed
- **The SDES bridge no longer breaks its own stream during a key-restart.** When
  a camera's answer SRTP key differs from the offered key, the SDES ffmpeg bridge
  restarts ffmpeg. The bridge's process-observe loop could see the OLD
  (terminated) process exit during the restart window and break - tearing down
  the bridge, closing the loopback sockets, and starving the freshly restarted
  ffmpeg, which turned a fast key-restart into a 40-60s reconnect and a visible
  stream break. The observe loop now consults the teardown flag (added in
  0.11.12) and does not break on an expected local teardown; it resumes once the
  new process is live, and still breaks promptly on a genuine ffmpeg crash.

### Changed
- **The DTLS serve-open timeout is now tunable.** The per-attempt WebRTC open
  timeout for a served DTLS camera was hard-pinned at 30 s. It is now
  configurable via `AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S` (default 30, unchanged), so
  operators can fail faster on a known-dead camera. A malformed value falls back
  to the default.

## [0.11.12]

### Fixed
- **An idle DTLS camera no longer floods the log or burns CPU.** A DTLS camera
  (model IPC.A000088) that the cloud still reports online but that never answers
  WebRTC was retried indefinitely; each attempt drove the vendored H.264 decoder
  over corrupt frames, emitting one "H264Decoder() failed to decode" WARNING per
  frame (172 in one 11-minute capture) plus a steady stream of `aioice.ice` INFO.
  The serve path no longer feeds that decoder at all - the served H.264 already
  comes from a pre-decode tap and is muxed as a copy, so the decode was pure
  discarded work - and a lightweight keyframe/gap canary at DEBUG replaces it for
  link-health visibility.
- **`aioice.ice` / `aioice.turn` are capped at WARNING instead of INFO.** The
  prior INFO cap could not suppress the flood, which is itself INFO-level ICE
  connectivity-check state transitions; aioice emits nothing at WARNING or above,
  so the flood is silenced with no loss of a real warning. Set the logger level
  explicitly to restore ICE INFO for debugging (the NOTSET-guarded cap respects
  it).
- **The vendored H.264 decode-failure WARNING is rate-limited.** On the live-view
  path, where decode still runs, a corrupt stream no longer emits one WARNING per
  frame; the first passes through and subsequent failures collapse into a
  periodic summary carrying the suppressed count.
- **Expected ffmpeg teardown no longer logs a spurious WARNING.** A
  locally-initiated SDES bridge teardown SIGKILLs an ffmpeg that will not exit on
  a dead UDP input; that signal death (-9) is now logged at DEBUG when it follows
  a local teardown, while genuinely unexpected exits stay at WARNING.

### Changed
- **Retries for a persistently-unreachable idle DTLS camera now back off
  further.** After a threshold of consecutive failed opens (default 5,
  `AIDOT_DTLS_SLOW_PROBE_THRESHOLD`) the retry cadence widens to a slow probe and
  the per-attempt WARNING becomes a periodic summary; a successful open resets it
  immediately. This covers the idle-but-cloud-online case the existing offline
  pause did not.

## [0.11.11]

### Fixed
- **MQTT signaling session now ends promptly on a terminal disconnect.** The
  per-stream MQTT session (`_mqtt_session_sync`) only terminated its receive loop
  on a disconnect that happened *before* the first connect; a drop *after*
  connecting was always treated as transient. So a permanent post-connect
  disconnect - the account's persistent client reclaiming the same clientId
  (rc=142 "session taken over"), revoked credentials, or the broker going away -
  left the loop polling a dead socket until the full duration deadline, silently
  starving the camera of signaling (answer SDP, ICE, renewals). The loop now
  records when a post-connect drop begins, clears it on a successful reconnect,
  and ends the session with a WARNING once the disconnect has persisted past a
  short reconnect grace (`_MQTT_RECONNECT_GRACE`, 20 s) - transient blips still
  ride through paho's auto-reconnect unharmed.

### Changed
- **Removed the self-defeating `aioice` force-DEBUG on the DTLS path.** Every DTLS
  open set the `aioice` logger to DEBUG, which both flooded the log and was
  already neutralized by the package-init cap that holds `aioice.ice` /
  `aioice.turn` at INFO. Dropped it at the source; to debug ICE, set the `aioice`
  logger level explicitly (that overrides the NOTSET-guarded cap).

## [0.11.10]

### Changed
- **Capped the external `aioice` per-packet loggers too.** 0.11.7 capped the
  vendored aiortc RTP loggers, but `aioice` (the ICE/STUN library, a real
  dependency - not vendored) logs every STUN/TURN packet at DEBUG, so an active
  WebRTC connection still flooded the log when `aidot` DEBUG was enabled. On a
  microSD Home Assistant host that log I/O can starve the recorder. `aioice.ice`
  and `aioice.turn` are now capped at INFO by default (explicit user level
  respected); useful ICE connection-state DEBUG still flows.

## [0.11.9]

### Changed
- **A camera that never delivers media no longer spams the log.** When an SDES
  serve completes signaling but no SRTP media ever arrives (NO_MEDIA - e.g. a
  camera whose media never traverses the TURN relay), ffmpeg emits its expected
  "could not find codec parameters / output file is empty" stderr on every serve
  retry. That stderr now logs at debug while no media was received; it stays at
  WARNING when media WAS flowing (a genuine mid-stream ffmpeg error worth
  surfacing).

## [0.11.8]

### Changed
- **A camera that the cloud reports offline no longer drips WARNINGs.** The serve
  and keepalive loops already throttle open retries for a cloud-offline device
  (the offline pause), but still logged each failed probe at WARNING - so a
  dead/unpowered camera produced a steady trickle of "open failed ... (retry ...)"
  warnings. Those open failures now log at DEBUG while the device is explicitly
  cloud-offline; a genuine failure on an online camera still warns.

## [0.11.7]

### Fixed
- **No more blocking file read on the event loop.** The SDES SDP builder read the
  sprop cache from disk (`_inject_sprop` -> `_load_sprop` -> `open()`) as an
  eagerly-evaluated argument to `run_in_executor`, so the blocking `open()` ran on
  the event loop - which Home Assistant flags as "a blocking call ... causing
  stability issues". The read now runs inside the executor at all three SDP-write
  sites.

### Changed
- **Capped the vendored aiortc per-packet loggers.** `aiortc`'s RTP receiver and
  sender log every media packet at DEBUG; enabling DEBUG on the parent `aidot`
  logger to diagnose the integration turned that into thousands of lines per
  second (in one capture, ~99% of all log lines). `aidot._vendor.aiortc.rtcrtp`
  `receiver`/`sender` are now capped at INFO by default (an explicit user level is
  respected), so `aidot` DEBUG stays useful and the diagnostically valuable aiortc
  DEBUG (DTLS, ICE, SCTP/DCEP) still flows.
- **Demoted a benign warning to debug.** "ignoring ASCII-encoded IP ... from
  device dict" is a handled cloud quirk (the camera's LAN IP comes from WebRTC
  signaling, not that field) and is not actionable, so it no longer logs at
  WARNING.

## [0.11.6]

### Fixed
- **A cancelled SDES cold open no longer leaks sockets, a thread and a temp
  file.** Opening an SDES (battery) camera reserves two UDP sockets, starts a
  bridge thread, launches ffmpeg and writes a temp SDP, then hands them to the
  `SdesSession` that owns their teardown. If the 25-70 s handshake was cancelled
  (e.g. Home Assistant abandons a slow cold open) before that hand-off, none of
  it had an owner, so every abandoned attempt leaked two file descriptors, a
  thread and a `/tmp` SDP - eventually `OSError: Too many open files`. The open
  is now split into a wrapper that releases each resource (via an `ExitStack`)
  unless the session was actually returned.
- **Expired-token recovery on event-video and thumbnail fetches.** The cloud
  recording-list and MQTT-URL calls already refreshed the auth token and retried
  once on a `21026` ("please login again") error, but `getEventVideoUrl` and the
  latest-thumbnail fetch did not - so a snapshot or media request that happened
  to land on an expired token failed silently. They now use the same
  refresh-and-retry path.
- **Truncated H.264 parameter sets are dropped instead of cached corrupt.** When
  a STAP-A aggregation packet advertises a NAL longer than the packet itself
  (frame loss or relay truncation), the SPS/PPS parser bounded the slice to the
  packet so a short, corrupt parameter set is no longer cached (which could keep
  a camera showing no video until the cache was cleared).
- **Fire-and-forget client tasks are held by a strong reference.** The ICE-config
  prefetch and the sync `cleanup()` path scheduled work with a bare
  `create_task`; asyncio keeps only a weak reference, so either could be
  garbage-collected mid-flight. They now go through the same `_spawn_bg` helper
  the rest of the library uses.
- **A snapshot taken during a live view no longer interrupts that view.** On the
  default persistent-MQTT transport a second concurrent open on the same camera
  (a snapshot while streaming) reaped the single signaling-drain slot the live
  stream was using, freezing it until the ~30 s watchdog restarted it. Each
  session already reaps its own drain on stop, so ownership is now handed to the
  session on a successful open and the backstop slot is cleared; a concurrent
  open can no longer reap a live session's drain, while a genuinely orphaned
  (cancelled mid-handshake) drain is still released.
- **A transient MQTT broker drop no longer ends a stream's signaling early.** On
  the non-default per-stream transport (`AIDOT_PERSISTENT_MQTT` off) the signaling
  session runs for the whole lifetime of a stream, so a brief broker blip - which
  paho reconnects from automatically - used to tear the stream down. The receive
  loop now ends only on a disconnect that happens before the first successful
  connect; a later drop is left to paho's auto-reconnect, and subscriptions are
  re-established on every connect so the reconnected client is not deaf.

### Changed
- Replaced a non-ASCII token in an internal docstring with its ASCII gloss.

## [0.11.5]

### Added
- **Battery cameras stay awake for the whole live view (app parity).** A battery
  camera's low-power timer returns it to sleep ~25 s after the last cloud
  keep-alive, even mid-view, so a one-shot keep-alive at open let the stream drop
  partway through. A battery-only renew loop now re-issues `setKeepAliveTime`
  every 20 s (inside the 25 s window, so no sleep gap) for the duration of a
  battery stream, matching the official app; mains cameras never sleep and are
  skipped. Validated live on an A001513/L2 (renewals on the 20 s cadence). See
  the battery section of [`docs/CAMERAS.md`](docs/CAMERAS.md).

### Fixed
- **Keep-alive renew loop is single-instance and cleaned up on every exit path.**
  The renew task is battery-gated (mains cameras no longer schedule a throwaway
  no-op task); a re-view within the renew window cancels any still-running loop
  before starting a new one (no orphaned loops left POSTing `setKeepAliveTime`);
  the SDES/DTLS idle-release paths cancel it; and the `async_start_streaming`
  entry point starts it too, so a battery camera streamed via that path is
  renewed as well.

## [0.11.4]

### Changed
- **Lowered the `paho-mqtt` floor from `>=2.0` to `>=1.6.1`.** The MQTT client
  already ran on both paho generations - each `Client()` site falls back when
  `CallbackAPIVersion` is absent, and the callbacks accept a v1 `int` or a v2
  `ReasonCode` - so this only widens what can satisfy the dependency, useful
  where another package pins `paho-mqtt==1.6.1` (e.g. Home Assistant's
  custom-component test tooling). Validated live on 1.6.1: a real camera streams
  end-to-end. 2.x environments are unaffected (pip still resolves the highest).

### Fixed
- **Disconnect reason code is now truthful under paho 1.x.** The signaling MQTT
  client's `on_disconnect` used a fixed 5-arg (v2) signature, so paho 1.x's
  3-arg call landed the reason code in the wrong slot and logged it as `None`.
  It now reads the reason code from whichever position the installed paho passes.

## [0.11.3]

Hardening pass from a broad correctness + security review of the core modules.

### Security
- **MQTT broker password no longer logged.** `getServerUrlConfig`'s
  no-`mqttServerUrl` fallback logged the entire response body (which contains
  `mqttPassword`) at WARNING; it now logs only the non-sensitive key names,
  matching the redaction the adjacent debug log already used.
- **Token cache is written atomically at 0600.** The standalone CLI wrote the
  token file with umask-default perms before `chmod 0600` (a brief
  world-readable window) and truncated the existing file before serializing (a
  serialization failure destroyed the valid token). It now writes to a `0600`
  temp file and `os.replace`s it into place, so perms are never widened and the
  previous token survives any write failure.

### Fixed
- **Corrupt token cache no longer crashes the CLI.** `_make_client` now catches
  a bad/partial token file and falls back to username/password login instead of
  raising an uncaught `json.JSONDecodeError` at startup.
- **DTLS `async_snapshot` honors its bool contract.** Its open call was outside
  the try/except, so a busy-camera open raised out of a method documented to
  return `True`/`False`; it now returns `False` like the SDES branch.
- **SDES restart after an SRTP key change no longer produces a dead stream.**
  The ffmpeg-restart path now updates the shared process holder, so the bridge
  thread does not mistake the restarted ffmpeg for an exited one and starve it.
- **LAN attribute reads fail cleanly.** `async_get_attributes` raises
  `CameraLanError` (not an unguarded `IndexError`) when a camera logs in but
  never answers the attribute query, preserving the cloud-fallback contract.
- **Motion dedup evicts oldest, not arbitrary.** The `_motion_seen` memory
  bound is now insertion-ordered, so trimming keeps the most-recent uids and
  in-window events stay deduplicated.
- **Resource leaks on error paths closed.** Several terminal raises / loop exits
  now release what they held: the connect-per-stream MQTT session thread + its
  broker connection (DTLS media-declined and livePlay=0 refusals in both the
  DTLS and SDES open paths), and the cloud-playback TCP socket (login-failure
  returns and receive-loop exit now tear down via a shared cleanup).

### Changed
- Harmonized the persistent-MQTT `NO_MEDIA` soak figure between README and
  `docs/CAMERAS.md` (`~57% -> ~11-19%` across soaks).

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
