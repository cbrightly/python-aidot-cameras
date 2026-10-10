# Environment variables: internals

Every `AIDOT_*` variable the library reads that is **not** in the README's
table, in four groups: the streaming knobs, the deeper internals that used to
sit in `CAMERAS.md`, the serve and retry policy, and the endpoint overrides and
measurement seams. Most were added during an investigation of one camera model,
and every default was measured. None is part of the 1.0 API (see
`API-STABILITY.md`): each may be removed in a later release once the behaviour
it tunes has settled, and the CHANGELOG will say so.

Removed in 1.0.0rc44, with the code behind them: the closed bitrate and
quality levers (`AIDOT_SDES_VIDEO_PT_ORDER`, `AIDOT_SDES_OFFER_BANDWIDTH_KBPS`,
`AIDOT_SDES_TMMBR_BPS`, `AIDOT_SDES_TMMBR_AFTER_S`, `AIDOT_SDES_ADAPTIVE`), the
switches that only turned a fix off (`AIDOT_SKIP_DOOMED_SERVE`,
`AIDOT_PUBLISH_SPS_FIX`, `AIDOT_AUDIO_AGC`, `AIDOT_DTLS_DIRECT_SERVE`,
`AIDOT_PUBLISH_TIMESTAMPS`, the no-op `AIDOT_LIVESTREAM_PARAM`) and the
unfinished experiments (`AIDOT_SDES_SKIP_TURN_PREALLOC`,
`AIDOT_BATTERY_WAKE_GATE_S`, `AIDOT_DIRECT_PUBLISH_H265`). Setting any of them
now does nothing. The TURN pre-allocation skip lives on as connection mode
`lan` (`AIDOT_SDES_CONNECTION_MODE`).

| Variable | Effect | Default |
| --- | --- | --- |
| `AIDOT_MAX_CONCURRENT_OPENS` | Caps how many stream opens run concurrently across all cameras. | `2` |
| `AIDOT_FAST_CONNECT` | Enable LAN-direct "fast connect" (STUN-only, skips several cloud signaling waits) when truthy. On-LAN only - off-subnet/strict-NAT viewers must leave it off. | unset (off) |
| `AIDOT_SDES_FAST_LIVEPLAY` | Don't block on the `livePlayResp` wait for eligible SDES cameras (~4.5 s faster cold start). Role-reversal models (A001064 PTZ) always excluded for correctness. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_SDES_LIVEPLAY_ECHO_S` | How long to wait for the broker to echo our own `livePlayReq` back before sending `webrtcReq`, in seconds (`0` disables the wait). It is pure latency: across 22 h of one deployment the wait ran 169 times and timed out 169 times, never once ending early, with no inbound `livePlayReq` among 5000+ messages the cameras and broker did send. The code proceeds on timeout anyway, so it never changed behaviour, only delayed it. Measured on an A001064: time to first media 11534 ms -> 6819 ms. The fast-liveplay path kept 1.5 s until it was measured on its own - 22 runs, 22 timeouts at 1500-1501 ms, no echo ever - and now shares the same value; that was 1.25 s of a 5.4 s cold connect. Still honoured, so a broker that does echo short-circuits it. | 0.25 s |
| `AIDOT_ABANDONED_MEDIA_GRACE_S` | Extra seconds to wait for the first packet after the **battery** stale-offer backstop ends the wait, in seconds (`0` disables). The serve SDP is built from what has actually been observed, so ending the wait a moment before the first packets arrive costs that session its audio for the whole session - measured 2026-09-03 with media arriving 4.2 s after the wait ended. Read per open and clamped, so `0` takes effect without a restart and a malformed value falls back to the default. | `8` |
| `AIDOT_BATTERY_STALE_OFFER_GRACE_S` | Backstop for a **battery** attempt that stalls anyway, in seconds (`0` disables it). When the camera was silent as the first-media wait began, has since turned up, and has still sent no media after this long, the attempt is abandoned to the retry instead of holding the full 75 s window - a stalled camera answers and then sends nothing, while the retry's fresh offer is served in about 5 s. Measured from the camera's first sighting rather than its latest message, because a camera emitting an event every 5 s while its handshake goes nowhere would otherwise push the decision out until it had gone back to sleep. Sized clear of the slowest healthy open (10.7 s) so a merely slow attempt is never mistaken for a stalled one. | `15` |
| `AIDOT_SDES_SERVE_AUDIO` | Include the camera's audio in what an SDES camera serves, on both the RTSP-push and http-serve paths. **On by default** for parity with the official app; set to `0`/`false`/`no`/`off` for video only, which is what a consumer that cannot cope with the audio wants. The standalone CLI reads this; inside Home Assistant the per-camera **Camera audio** switch is the same setting and should be used instead. | enabled (on) |
| `AIDOT_SDES_NACK` | Ask the camera to resend video RTP packets that never arrived (RTCP Generic NACK). A camera losing packets on a weak link otherwise delivers truncated H.264 slices, which a browser's WebRTC decoder conceals but Media Source Extensions treats as fatal. Measured on an A001064 at ~1-2% loss: 98.4% of losses recovered against none at all without it. Costs nothing on a clean link, where no requests are generated. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_SDES_ECHO_WAIT_S` | How long to wait for the broker to echo our own `webrtcReq` back before carrying on, in seconds, for the role-reversal models that build a `webrtcResp` from that echo (A001513-class cameras never took this wait and are unaffected). Like `AIDOT_SDES_LIVEPLAY_ECHO_S` it was pure latency: across 18 h of one deployment, of 61 SDES opens the 17 that took the wait timed out 17 times out of 17 at a mean of 2.086 s, the `webrtcResp` it exists to build was never sent once, and all 17 streamed anyway. Measured on an A001064: time to first media 4195 ms -> 2489 ms. **This value is the wait for a camera never seen to echo.** An echo observed at any point, including after the wait has already expired, is remembered for that camera and every later open on it waits `2.0` s again - so a fleet whose echo band is not empty pays the miss once, says so in the log, and is never shortened again. Setting this explicitly overrides both. Read on every open and clamped at 0, so a malformed or negative value falls back to the default instead of raising. | `0.25` (`2.0` once an echo has been seen) |
| `AIDOT_PINNED_CODEC_RETRIES` | With `AIDOT_HLS_DIRECT_TS` and the H.264 pin on, how many times in a row a session that sends the other codec is abandoned and re-opened before it is served as it came. The in-sync TS is promised on the strength of the pin and only an H.264 session feeds it; the A001064 answered H.265 in 15 of 107 pinned opens (2026-08-26). `0` serves every session as it comes. | `2` |
| `AIDOT_DTLS_FAST_LIVEPLAY` | The DTLS (A000088) analogue: skip the `livePlayReq`-echo and `livePlayResp` waits (the dominant LAN cold-start cost) while keeping the full ICE/TURN/DTLS handshake, so remote/relay viewing is unaffected. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_HLS_TS_PORT` | Port of the loopback listener for `AIDOT_HLS_DIRECT_TS`. One listener serves every camera, by path (`/aidot_<first 12 of the device id>.ts?auth=<secret>`). | `0` (any free port) |
| `AIDOT_PUBLISH_GAP_WARN_S` | Seconds without a frame to publish before the direct publisher says so in the log. The line splits the gap into its causes - seconds idle waiting for a frame to arrive, seconds spent inside the publish, and how many frames were dropped over that gap (waiting for a keyframe, or a presentation time already served) - alongside the queue depth. Each session also records its largest gap. `0` disables the line. | `1.0` |
| `AIDOT_SERVE_RELAY` | Hold the public stream port via an internal relay that proxies to ffmpeg, so the first (cold) view connects instead of failing while ffmpeg can't pre-bind the port. Set to `0` to serve ffmpeg directly. Not involved in a direct publish, which has no port to hold. | `1` (enabled) |
| `AIDOT_DTLS_VIDEO_GRACE_S` | How long a connected DTLS session may go without a single video frame before it is torn down and re-opened. A session that receives audio and no video passes every other check the serve loop makes - the peer connection is healthy, ffmpeg respawns for each consumer - so without this it is held open indefinitely while the viewer sees "no video". `0` disables the check. | `30` |
| `AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S` | How long one WebRTC open attempt for a served DTLS camera may take before it is abandoned and retried. Raised from 30 s because the camera's own offer-resend fires at 30 s, so the attempt used to die at the instant its last resend went out; answers measured arriving at 30.7-99.5 s were discarded as a result. | `75` |
| `AIDOT_DTLS_SERVE_ICE_WAIT_S` | Separate budget for the ICE half of that open, clamped to `AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S`. The open is two sequential waits - signalling then ICE - so without its own budget the ICE wait inherits the timeout above and doubles the worst case while holding the global open gate. | `30` |
| `AIDOT_DTLS_FUTILE_VIDEO_LIMIT` | Consecutive video-less DTLS sessions after which the serve loop stops re-opening. Noticing alone is not enough: a video-less session is otherwise a clean open, so a loop that simply re-opened would clear its backoff each time and wake the camera every 15 s indefinitely. `0` keeps retrying forever. | `5` |
| `AIDOT_LOGIN_RETRY_CAP_S` | Ceiling on the exponential delay between the LAN login retries that `AIDOT_LOGIN_RETRY_LIMIT` (README) counts. | `60` |
| `AIDOT_LOGIN_CONNECT_TIMEOUT_S` | Ceiling on one LAN connect+login attempt. A device that completes the TCP handshake and then stops answering is abandoned and its socket closed rather than parking the attempt forever. | `20` |
| `AIDOT_DTLS_PINNED_FP` | Pin the camera's DTLS certificate `sha-256` fingerprint (colon-separated hex). When set, a camera presenting a different cert fails the handshake instead of being accepted. The camera echoes our own fingerprint over signaling, so without a pin the media channel is **not** authenticated against an on-path MITM. | unset (accept-any + warn) |
| `AIDOT_SDES_HOLEPUNCH_HOST` | Override the NAT hole-punch target used when the cloud supplies no TURN entry. By default a STUN packet goes to a hardcoded vendor TURN host; set this to a host of your choice, or empty (`AIDOT_SDES_HOLEPUNCH_HOST=`) to disable the hardcoded fallback entirely. | unset (hardcoded vendor host + warn) |

## Deeper internals

Finer-grained knobs read by the camera client; the defaults are tuned to work
out of the box.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_STREAM_IDLE_S` | Seconds of stream idle before an idle release. | `120` |
| `AIDOT_SDES_IDLE_RELEASE` | Set to `0` to disable idle release for SDES streams. | `1` (enabled) |
| `AIDOT_BATTERY_UNKNOWN_VIEWER_RELEASE_S` | On a battery camera, how long a stream may run while its viewers cannot be counted (a go2rtc the library cannot query, or one that stops answering) before it is released anyway, in seconds since the last known viewer (or since the open, if none was ever seen). Without it such a camera streamed until restart. Mains cameras are unaffected: an unknown viewer state never releases them. `0` or negative disables the cap; a malformed value falls back to the default. | `300` |
| `AIDOT_STREAM_STARTUP_GRACE_S` | How long a session waits for its first viewer before the idle window applies, in seconds since the open. Until a viewer has been seen, the idle window is at least this long, so a short `stream_idle_s` cannot end a view that is still connecting (a stream worker or recording that retries late on a slow battery wake). Once a viewer has been seen, the idle window alone applies. A session nobody ever watches still ends on its own after this long. `0` or negative means no grace; a malformed value falls back to the default. | `60` |
| `AIDOT_ICE_DISCONNECT_S` | ICE-disconnect debounce, in seconds, before tearing down. | `8` |
| `AIDOT_DTLS_RETRY_GATE_S` | Minimum spacing, in seconds, between DTLS open retries. | `15` |
| `AIDOT_BUSY_RETRY_S` | Delay, in seconds, before retrying when a camera reports busy. | `45` |
| `AIDOT_BUSY_BACKOFF_S` | How long to wait after a camera answers an open with "no free session" (`-50002` / `-50015`) before the next attempt. Measured on an A001064: a reopen 2 s after a close is refused and one at 8 s succeeds, so the old 300 s wait was mistaking a camera that clears in seconds for one that needs a rest. Still a real wait: retrying at once would hammer a camera that genuinely has none free, and on a battery model risks a wake-then-sleep loop. | `20` |
| `AIDOT_OFFLINE_RECHECK_S` | While a device is cloud-offline, how often the paused keepalive retry re-checks the online flag. | `30` |
| `AIDOT_OFFLINE_PROBE_S` | While a device is cloud-offline, how often one real open attempt still probes it (guards against a stale cloud flag). | `600` |
| `AIDOT_FUTILE_KEEPALIVE_LIMIT` | Consecutive background keepalive sessions that deliver no media before the keepalive stops reopening a battery camera. Seen on an A001513: 22 opens over about 8 hours with no media, because the loop escalated its backoff but never stopped, and a unit was drained to 5% that way. `0` disables the ceiling. The DTLS analogue is `AIDOT_DTLS_FUTILE_VIDEO_LIMIT` above. | `5` |
| `AIDOT_DTLS_SLOW_PROBE_THRESHOLD` | After this many consecutive failed opens of a served DTLS camera (a camera gone from the network, say), the serve loop widens its retry interval and stops warning on every attempt. Resets the moment an open succeeds. | `5` |
| `AIDOT_DTLS_SLOW_PROBE_INTERVAL_S` | The widened retry interval, in seconds. | `600` |
| `AIDOT_DTLS_SLOW_PROBE_LOG_EVERY` | While slow-probing, one INFO summary every this many attempts instead of a WARNING per attempt. | `6` |
| `AIDOT_DTLS_SLOW_PROBE_CHUNK_S` | Sleep increment inside the slow-probe wait, so a `stop()` is not held up by the whole interval. | `5` |
| `AIDOT_GOP_PLI_S` | Interval, in seconds, between PLI (keyframe) requests. | `2.0` |
| `AIDOT_STALL_PLI_S` | If muxed frames stall for this many seconds (a dropped GOP on a jittery link), request an IDR keyframe immediately instead of waiting out the full `AIDOT_GOP_PLI_S` cadence. Mains DTLS cameras only; `0` disables. | `1.0` |
| `AIDOT_SDES_PLI_GAPS` | Comma-separated second offsets for the early PLI burst on SDES cameras, to pull the first keyframe in faster on cold start. | `0,1.5,2,3` |
| `AIDOT_SDES_STALL_NUDGE` | Mid-session stall nudge on SDES cameras: when inbound media stops with no teardown signal, re-send the AVIO LIVING message (the one that starts media on a fresh session) on the live session, a few times, spaced out, before the input timeout and the keepalive reopen take over. The A001064 stops transmitting this way and answers at once afterwards. `0` turns it off. | `1` (on) |
| `AIDOT_SDES_STALL_NUDGE_AFTER_S` | Seconds without media before the nudge fires. | `2.5` |
| `AIDOT_SDES_UNREACHABLE_NOMINEE_GRACE_S` | How long a nominated ICE candidate has to produce any inbound STUN Binding Success before the attempt is abandoned to the retry. The trigger arms within about a second of the answer or never, so a pair that has answered nothing for this long will not start media in this attempt; seen on an A001513 whose answer carried only an address it then dozed behind, which otherwise ran the whole 75 s budget. Timed from nomination, so a slow battery wake is never clipped. `0` restores the full-budget wait. | `20` |
| `AIDOT_SDES_AUDIO_GAIN_DB` | Gain (dB) applied when SDES audio is served. | `-8` |
| `AIDOT_AUDIO_TARGET_DBFS` | Target loudness (dBFS) for two-way audio normalization. | `-15` |
| `AIDOT_AUDIO_MAXGAIN_DB` | Maximum gain (dB) applied by the audio normalizer. | `30` |
| `AIDOT_AUDIO_MINGAIN_DB` | Minimum gain (dB) applied by the audio normalizer. | `-12` |
| `AIDOT_AUDIO_GATE_DBFS` | Noise-gate threshold (dBFS) for two-way audio. | `-45` |
| `AIDOT_FAST_CONNECT_HOST_ONLY` | Within `AIDOT_FAST_CONNECT`, narrows only the local `RTCPeerConnection` to host candidates (skips the ~5 s srflx gather stall). **On-subnet only** - drops srflx/relay fallback. Opt-in. | unset (off) |
| `AIDOT_SPROP_DIR` | The library's state directory: captured SPS/PPS (sprop) parameter sets, and the `hls-ts-url` file. Set to a writable path if the default location is read-only. | `<package dir>` |
| `AIDOT_REMB_TARGET_BPS` | Send REMB (receiver-estimated bandwidth) at this bitrate on SDES sessions. The camera advertises `goog-remb` and the sender is kept and tested as an instrument; on the reference fleet it did not change the encoder's rate. `0` sends none. | `0` (off) |
| `AIDOT_INCLUDE_SHARED_HOUSES` | Also list houses this account does not own (the cameras a shared-home member sees). The live-validation account is such a member and sets it; unset, those houses are skipped. | `0` |

## The ffmpeg serve and the recordings

The ffmpeg serve carries recordings, snapshots, `-` and `http://` serves, H.265
sessions and any camera with `AIDOT_DIRECT_PUBLISH=0`.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_SERVE_REORDER_QUEUE` | Packets the serve's RTP demuxer holds to put a burst back in order. A keyframe on an A001064 is 146-190 KB, roughly 130 packets, so anything much smaller cannot ride out a burst that arrives out of order. | `500` |
| `AIDOT_SERVE_MAX_DELAY_US` | How long, in microseconds, the demuxer waits for a missing packet before moving on. Bounded on purpose: waiting forever turns loss into a stall. The SDES publisher's reorder budget reads the same variable (capped at 0.5 s) so the two cannot drift apart. | `500000` |
| `AIDOT_SERVE_INPUT_TIMEOUT_S` | How long the serve's input may go silent before ffmpeg gives up. Unset, a mains camera gets 30 s to ride out a transmit gap and a battery camera keeps ffmpeg's 10 s, because its stops are real sleeps that only a reopen ends; set, the value applies to every camera. | unset |
| `AIDOT_SERVE_ARRIVAL_TS` | Stamp the serve's input by arrival time instead of trusting the camera's RTP clock. The A001513 sends in-order packets stamped about 1.7 s in the past every 30 s, which read as backward decode times (6-7 warnings per streaming minute). `0` trusts the camera's stamps again. | `1` |
| `AIDOT_REORDER_SLACK_TICKS` | How far the DTLS A/V mux shifts video presentation ahead of decode, in 90 kHz ticks; audio is shifted by the same time so the two start together. | `180000` (2 s) |
| `AIDOT_FFMPEG` | The ffmpeg binary the decoder-capability probe (`hwaccel.probe_decoder`) runs. | `ffmpeg` |

## Endpoints, test seams and measurement scaffolding

Unset in every normal installation.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_API_BASE_TEMPLATE` | Override for the platform API base; `{region}` is substituted. | the vendor's `prod-{region}-api` host |
| `AIDOT_SMARTHOME_URL_TEMPLATE` | Override for the smart-home API base; `{region}` is substituted. | the vendor's `{region}-smarthome` host |
| `AIDOT_MQTT_URL` | Point the whole client at a local MQTT broker (`ws://127.0.0.1:PORT/mqtt`) without any cloud call. A test seam. | unset |
| `AIDOT_STUN_SERVERS` | Comma-separated STUN URIs for ICE gathering; an empty value (`AIDOT_STUN_SERVERS=`) disables STUN. | a public STUN server |
| `AIDOT_TURN_SERVERS` | TURN relay URIs appended when the cloud's ICE config carries none; an empty value disables the hardcoded vendor fallback. | the vendor's relay |
| `AIDOT_EXPT_CAP_FILE` | Name a file holding `<device_id>:<seconds>` and that one SDES camera's sessions are capped at that length, so a measurement arm whose sessions would otherwise run for tens of minutes keeps producing samples. Scoped to one device on purpose and fails closed: anything it cannot attribute caps nothing. No I/O at all when unset. | unset |
| `AIDOT_EXPT_PEERID_FILE` | Name a file holding peer-id fields to announce instead of the library's own, for a measurement arm. No I/O when unset. | unset |
| `AIDOT_EXPT_PEERID_CLASS` | The same for the peer id's client class alone, as an environment value, because the live-validation harness can only pass environment variables. | unset |
