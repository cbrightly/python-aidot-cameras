# Design: publish decrypted media straight into go2rtc (no ffmpeg hop)

Status: **A1 and A2 implemented; live A/B passed on seven cameras (H.264),
soak in progress** on `feat/direct-rtsp-publish` (library and integration). Local only - nothing pushed or published until it has passed
live validation on real cameras (see "Validation gate").

## Why

Every serve today puts a process or a socket between the decrypted media and
go2rtc:

| Transport | Today | What that hop has cost (from CHANGELOG / code comments) |
| --- | --- | --- |
| SDES (A001064, A001513, battery models) | bridge -> loopback UDP -> **ffmpeg** (`-c:v copy`, PCMA->AAC over an `anullsrc` base) -> RTSP push | ffmpeg exits (145 after an HA restart), zombie/fd/thread leaks, stderr-drain stalls, arrival-time restamping needed, multi-PT ANNOUNCE rejected, SDP narrowing must be perfect or the publish dies |
| DTLS (A000088) | aiortc tap -> PyAV mux -> MPEG-TS -> `_DirectTsServer` **HTTP listen port** that go2rtc pulls (or ffmpeg for RTSP/stdout) | port must be bound before go2rtc dials (relay, CRC port hashing, "connection refused"), PCMA->AAC transcode + AGC, pull-registration PUT dance, re-PUT evicting publishers |

Both hops exist only to turn RTP we already hold in-process into something
go2rtc can ingest. go2rtc ingests RTP natively over an RTSP publish. So the hop
can go.

This is the "Option A" from the 2026-09-19 architecture review: keep the
protocol work in Python (it is the product, and it is still settling - see
ROAD-TO-1.0 item 1), and fix the *delivery* layer, which is where most of the
operational pain lives. The go2rtc-native-source and Scrypted alternatives were
rejected for now; the standalone-bridge follow-up is in
[`PLAN-bridge-container.md`](PLAN-bridge-container.md).

## Requirements

Functional

1. Publish each camera's H.264/H.265 video and PCMA audio into go2rtc with no
   subprocess, for both transports.
2. Keep every existing consumer working: HA's go2rtc WebRTC provider, HA's HLS
   fallback (`stream` component), the `aidot-go2rtc` CLI, recordings/snapshots.
3. Behind a flag, default **off**, with the ffmpeg path untouched as fallback.

Non-functional

- No regression in cold start (current mains median ~2.9 s to serve bound).
- CPU: at most what `-c copy` ffmpeg costs today; no decode, no encode.
- No new runtime dependency.
- Touch the 9.3k-line SDES open as little as possible: it is the least stable
  code in the repo.

Out of scope

- Browser two-way audio through go2rtc (a published stream has no backchannel;
  `aidot.talk` stays).
- HA's *bundled* go2rtc. It keeps its API on a unix socket, serves RTSP on
  `127.0.0.1:18554`, and does not load `mpegts`/`mp4`/`hls`. The integration
  already assumes a go2rtc it can reach on `127.0.0.1:1984`/`8554`; this design
  does not change that (see "Open questions").

## What go2rtc accepts (v1.9.14, which HA pins)

Verified in source (`pkg/rtsp/server.go`, `internal/rtsp/rtsp.go`,
`pkg/rtsp/conn.go`, `pkg/rtsp/helpers.go`):

- `OPTIONS -> ANNOUNCE -> SETUP... -> RECORD`, **TCP-interleaved only**
  (`RTP/AVP/TCP`; UDP gets 461).
- ANNOUNCE needs `Content-Type: application/sdp`. One receiver per `m=` line,
  **first codec only**, bound to interleaved channel `2*i` - so the publisher
  must send the *i*-th media on channel `2*i`.
- The stream **must already exist** or the connection is silently closed after
  RECORD (200s all the way). The integration already PUTs the stream definition
  before a publish (`_prewarm_stream`, view path).
- Do **not** mark media `a=sendonly` - go2rtc reads direction from its own side
  and would treat it as a backchannel. Omit direction.
- H.264 without `sprop-parameter-sets` is fine (SPS/PPS taken in-band). H.265
  needs all of vps/sps/pps in fmtp or the fmtp is dropped.
- PCMA PT 8 is recognised with or without an rtpmap.
- Idle read deadline while publishing: **15 s**; any RTP/RTCP/OPTIONS resets it.
  `GET_PARAMETER` is never answered - use `OPTIONS` for keepalive.
- No RTCP SR required. SSRC is not checked. Timestamps pass through to WebRTC
  consumers unchanged, so the publisher owns timestamp sanity.
- When a publisher disconnects, its producer is removed; a consumer pulling via
  a *dialled* RTSP producer (which is how HA's own `camera.x` stream reads our
  `aidot_x` stream) is re-dialled with `Replace` - so a publisher restart is
  survivable for viewers.

Audio for consumers: go2rtc's WebRTC hands PCMA straight to browsers (no
transcode). HA's `stream` component (HLS fallback, recorder) keeps only
`aac`/`mp3` (`homeassistant/components/stream/const.py: AUDIO_CODECS`) and drops
other audio, keeping video.

## High-level design

```
                    SDES                                   DTLS
 camera ==SRTP==> sdes bridge thread            aiortc (ICE/DTLS/SRTP, NACK, jitter)
                  (decrypt, PT fix, NACK)          | encoded-frame tap (AU + ts)
                        | plain RTP, loopback UDP  v
                        v                       vq / aq queues
              +-----------------------+            |
              | LoopbackRtpPublisher  |            v
              | (Popen-compatible)    |   +-----------------------+
              | UDP rx -> Timeline    |   | DtlsRtpPublishRun     |
              | -> gain -> RTSP push  |   | H.264 FU-A packetizer |
              +-----------+-----------+   | PCMA packetizer       |
                          |               | Timeline -> RTSP push |
                          |               +-----------+-----------+
                          +---------- RtspPublisher --+
                                (OPTIONS/ANNOUNCE/SETUP/RECORD,
                                 $-framed interleaved RTP, OPTIONS keepalive)
                                          |
                                          v
                           go2rtc  rtsp://127.0.0.1:8554/aidot_<id12>
                                          |
                     HA camera.x stream (dialled RTSP producer) -> WebRTC / HLS
```

New module `aidot_cameras/camera/rtsp_publish.py` (no new dependencies):

| Component | Responsibility |
| --- | --- |
| `RtspPublisher` | Blocking RTSP client for publish. Handshake with 5 s per-request timeout, `$`-framed send, background reader that drains/handles server replies, OPTIONS keepalive every 5 s, TEARDOWN on close. Thread-safe `send_rtp(track, pkt)`. Raises/flags on any socket error - it never reconnects on its own (the caller's lifecycle decides). |
| `RtpTimeline` | Per-track output timestamp/sequence owner. Passes camera deltas through when they are sane; on a backward step or a forward jump beyond a bound, substitutes the arrival-clock delta. Continuous sequence numbers. Keeps packets of one frame on one timestamp. |
| `publish_sdp_from_serve_sdp()` | Turns the already-narrowed loopback SDP (the file ffmpeg reads today) into an ANNOUNCE body: one PT per `m=`, rtpmap/fmtp (incl. injected sprop) kept, ports/crypto/rtcp-mux/direction dropped, `a=control:trackID=N` added. |
| `RtpReorderBuffer` | Per-track sequence reordering in front of the timeline - the replacement for the serve ffmpeg's `-reorder_queue_size 500 -max_delay 500000`, with the same defaults. go2rtc does not reorder a publisher's packets, and NACK retransmits arrive late by design. In-order packets pass with no added latency. |
| `LoopbackRtpPublisher` | **Drop-in for the SDES ffmpeg `Popen`.** Binds *every* loopback port the serve SDP names (the open waits on both; unannounced media is read and discarded), reorders, then forwards via `RtpTimeline` (+ optional PCMA gain via `aidot_cameras.g711`) into `RtspPublisher`. Used only when the bridge itself decrypts (`_use_plain_rtp`, i.e. `_PLAIN_RTP_MODELS`); any other SDES model keeps ffmpeg, which decrypts SRTP from the SDP's `a=crypto`. Implements `poll/wait/terminate/kill/returncode/pid/stderr`, so `_proc_holder`, the bridge break, `SdesSession.is_alive/wait_done/stop`, the key-restart relaunch and `_reap` work unchanged. Exits non-zero on publish failure or on the serve input timeout (the same no-input exit ffmpeg gives today, which is what drives the keepalive's reconnect). |
| `dtls_rtp_publish_run()` | Alternative to `_dtls_av_mux_run` for the DTLS serve loop when the destination is `rtsp://`. Consumes the same `vq`/`aq`, starts on a keyframe, applies the existing `is_resent_video_frame` high-water drop, packetizes H.264 (single NAL / FU-A, 1200 B MTU) and PCMA (160-sample frames as tapped), and publishes. Updates `progress` exactly like the mux so the serve-ready, stall and idle logic is unchanged. |

### Why the SDES seam is the loopback, not the bridge

The bridge (`_bridge_fn`, inside the 6.6k-line `_open_sdes_stream_impl`
closure) is where every camera quirk lives and is still churning. Everything it
emits is already plain, PT-corrected RTP on two loopback UDP ports described by
a narrowed SDP file. Replacing the *reader* of those ports - behind a `Popen`
duck type - changes about 30 lines of the open (the launch site and the
key-restart relaunch) and none of the bridge. The loopback hop costs two
`sendto`/`recvfrom` per packet on a ~1 Mbit/s stream, which is noise.

A later cleanup can hand packets over in-process (a `put()` instead of
`sendto`) once the design has soaked; it is deliberately not phase A1.

### Timestamps

Today ffmpeg is run with `-use_wallclock_as_timestamps 1` for SDES because the
A001513 steps its RTP timestamp ~1.7 s backwards about every 30 s, and DTLS has
its own `_correct_ts` + `is_resent_video_frame` + `video_pts_dts` stack. RTP
consumers (browsers' jitter buffers) need monotonic, rate-consistent
timestamps, not DTS/PTS pairs, so the publisher needs less than the mux did:

- **Video**: packets sharing an input timestamp share an output timestamp.
  A new input timestamp advances the output by the input delta if
  `0 < delta <= 3 s` (90 kHz); otherwise (backward, zero-after-marker, or a
  jump) by the arrival-clock delta, minimum 1 tick. Logged once per session
  with a counter so live runs show how often repair fires.
- **Audio**: same rule at 8 kHz, with the arrival-clock fallback rounded to the
  160-sample frame.
- Sequence numbers are renumbered per track, continuous across the session.
- DTLS keeps the existing high-water "resent frame" drop before packetizing.

### Audio

Publish **PCMA as-is**. For WebRTC viewers this is strictly better than today
(no AAC encode, no 48 kHz resample, no `anullsrc` mix; browsers take PCMA
natively). Gain: SDES applies `AIDOT_SDES_AUDIO_GAIN_DB` today inside ffmpeg;
the publisher applies it on the A-law bytes through a 256-entry lookup table
(`g711` decode -> scale/clip -> encode), so the knob keeps working. DTLS AGC is
not carried over in A1 (noted as a gap; re-evaluate after listening tests).

Trade-off: HA's HLS fallback and recorder drop PCMA and keep video only. Phase
A3 restores AAC *only for those consumers*, lazily, via go2rtc: the stream
definition gains an `ffmpeg:aidot_x#audio=aac` source and the HLS branch of
`stream_source()` (already detected via `_STREAM_SOURCE_HLS`) returns the
`?video&audio=aac` URL, so go2rtc starts that transcode only while an HLS
consumer exists. WebRTC viewers never pay for it.

## Integration (hass-aidot-cameras)

- Option in the "streaming" options step: **Direct publish (no ffmpeg)**,
  default off; sets `AIDOT_DIRECT_PUBLISH=1` like the other env-bridged options.
- With it on, **DTLS cameras switch to push mode too** (`_sdes_push_enabled`
  becomes "push enabled" for both transports). That removes, for those cameras,
  the pull serve port, the CRC port hash, the `_serve_url` registration and the
  `_await_serve_listening`/`async_wait_serve_ready` waits in favour of the one
  `_await_publisher_attached` path SDES already uses.
- The stream definition PUT before publishing is unchanged (go2rtc requires the
  stream to exist).

## Failure handling

| Failure | Behaviour |
| --- | --- |
| go2rtc RTSP not listening | `_await_rtsp_publish_target` (existing) waits up to 20 s; then handshake failure -> publisher exits 1 -> bridge breaks -> keepalive reconnect with its existing pacing. Same as ffmpeg today. |
| Stream missing (go2rtc closes after RECORD) | First `send` fails -> exit 1 -> same path. Logged distinctly ("go2rtc closed the publish: does stream X exist?"). |
| go2rtc restarts mid-session | Socket error -> exit 1 -> reconnect. Viewers' dialled producer re-dials. |
| Camera stops sending | No packet for the serve input timeout (mains 30 s / battery 10 s, existing `_resolve_serve_input_timeout_s`) -> exit 1, as ffmpeg's input timeout does today. OPTIONS keepalive keeps go2rtc from timing us out first. |
| Stopped while still connecting | `close()` is sticky: a handshake that completes after it tears itself down, so no orphaned producer is left in go2rtc. |
| Teardown | `terminate()` sets the stop event, sends TEARDOWN, closes sockets; `returncode = -15` so `_classify_ffmpeg_exit` classifies it as the expected teardown exit. |
| Codec change between sessions (A001064 H.264/H.265) | Each session ANNOUNCEs its own narrowed SDP; nothing persists across sessions in A1. |

## Rollout plan

| Phase | Scope | Exit criterion |
| --- | --- | --- |
| **A1** | Library: `rtsp_publish.py`, SDES launch-site swap, DTLS publish runner, `AIDOT_DIRECT_PUBLISH` (default off), CLI honours it. Unit tests + e2e against `FakeRtspSink` and a real go2rtc 1.9.14 binary on loopback. | Suite green; synthetic RTP round-trips through a real go2rtc to an RTSP reader with correct codecs. |
| **A2** | Integration: option, DTLS push routing when enabled, tests. | HA test suite green. |
| **Live** | On the camera LAN box: each model (A000088, A001064, A001513) - cold start, 30 min soak, HA WebRTC view, HLS fallback, idle release, go2rtc restart, camera power-cycle. Compare against flag-off baseline. | See "Validation gate". |
| **A3** | HLS/recorder AAC via lazy go2rtc `ffmpeg:` source; decide DTLS AGC. | HLS has audio; WebRTC unaffected. |
| **A4** | Default on; ffmpeg push path kept one release as fallback, then removed along with `_ServeRelay`, CRC ports and the pull registration for push cameras. | One release with no regressions reported. |

## Validation gate (before any push or publish)

Run `scripts/live_publish_ab.py` on the camera LAN, against a go2rtc you
control, with HA's own sessions closed (the camera answers -50002 when busy):

```bash
python scripts/live_publish_ab.py --go2rtc http://127.0.0.1:1984 --rtsp-port 8554 \
    --view-s 30 --repeats 2                 # A/B per camera, ~10 min per camera
python scripts/live_publish_ab.py --go2rtc http://127.0.0.1:1984 --rtsp-port 8554 \
    --arms direct --view-s 30 --soak-s 1800 --parallel 3   # soaks, 3 cameras at once
```

Soak standard: **30 min on one mains camera per transport** (DTLS, SDES) is
what catches slow growth - threads, fds, publisher churn - which is a property
of the code path, not the camera. Every other camera gets a short soak: the
time-based failures this stack has had (the A001513's backward timestamp step
every ~30 s, the old 80 s SCTP cliff, the camera's 20 s watchdog, go2rtc's
15 s idle cut) all recur within minutes. Battery cameras are capped at 5 min
(`--battery-soak-s`, default 300): a long session is not how they are used
and it costs battery. `--parallel` overlaps different cameras (opens of one
camera stay sequential and slot-hold apart); it needs a single `--arms`
value, because the direct/ffmpeg switch is process-wide.

`live_validate.py` (the release gate) is not enough on its own: it records
through `output_path`, which keeps ffmpeg by design, so it never runs the
direct publisher. Then, with the integration's option on, check HA itself
(WebRTC view with audio, HLS fallback with video).

Measured against the same box with the flag off:

- cold start to publisher attached: not worse than baseline median;
- 30 min soak per camera: no publisher churn beyond baseline, zero leaked
  threads/fds (`/proc/<pid>/task`, `/proc/<pid>/fd` before/after), no ffmpeg
  processes for published cameras;
- HA WebRTC view plays video + audio on each model; HLS fallback plays video;
- timestamp repair counter reported per session (expect non-zero on A001513).

## Live results

A Raspberry Pi 4 (aarch64, Debian 13) on the camera LAN, go2rtc 1.9.14, Home Assistant's
AiDot entry disabled for the run. Each camera opened once per arm, 30 s viewed
by a real RTSP consumer reading back from go2rtc.

**Pass 1 (2026-09-19, H.264 pinned):** 14/14 opens passed.

| Camera | Model | Publisher attached, ffmpeg -> direct | First frame | Frames in 30 s |
| --- | --- | --- | --- | --- |
| Battery #1 | A001513 | 9.2 -> 5.3 s | 1.7 -> 2.2 s | 446 -> 471 |
| Battery #2 | A001513 | 8.3 -> 6.7 s | 2.6 -> 2.2 s | 462 -> 494 |
| PTZ | A001064 | 4.7 -> 2.5 s | 1.9 -> 1.5 s | 604 -> 614 |
| Battery #3 | A001513 | 8.0 -> 6.7 s | 1.6 -> 3.8 s | 545 -> 548 |
| Mains #1 | A000088 | 7.0 -> 1.8 s | 3.8 -> 0.5 s | 418 -> 465 |
| Mains #2 | A000088 | 6.6 -> 1.8 s | 3.4 -> 0.5 s | 423 -> 467 |
| Mains #3 | A000088 | 6.8 -> 1.5 s | 3.9 -> 0.8 s | 415 -> 461 |

Direct opens ran no ffmpeg, published with 0 late and 0 lost packets, repaired
2-4 timestamps per 30 s on the SDES cameras, and left thread and fd counts
flat. Two A001513 opens went video-only: one camera sent no audio in either
arm, the other's first audio packet arrived 0.86 s after the 1 s grace. The
second case is why audio is now attached from the negotiated answer.

**Pass 2 (2026-09-19):** H.265-first offer on the A001064, both arms, twice:
4/4 passed, but the camera answered H.264 every time, so H.265 publishing is
still unexercised. Direct attach 2.5-2.8 s vs ffmpeg 4.4-5.2 s. Soaks: see
below once complete.

## Open questions

1. **Which go2rtc does the live box run?** The integration talks to
   `127.0.0.1:1984`/`8554`, which is not HA's bundled server (unix-socket API,
   RTSP on 18554). Supporting the bundled server needs the stream-create call to
   go through HA's own go2rtc client; worth doing independently of this design.
2. Should the DTLS runner keep the mux's AGC? Needs a listening test.
3. H.265 publishing is unexercised: the A001064 answered H.264 in 4 of 4
   H.265-first offers on 2026-09-19. go2rtc drops an H.265 fmtp without
   vps/sps/pps, and the publisher sends none, so parameter sets must arrive
   in-band - to be confirmed on a session that actually negotiates H.265.
4. A persistent per-camera publisher across camera sessions (continuous
   timeline, no re-ANNOUNCE) would hide reconnects from viewers entirely - but
   the A001064's H.264/H.265 flip forces a re-ANNOUNCE anyway. Revisit after A4.

## Revisit as it grows

- In-process hand-off from the SDES bridge (drop the loopback hop).
- If Option B (bridge container) happens, `RtspPublisher` is the piece it
  reuses unchanged, pointed at its own go2rtc/MediaMTX.
