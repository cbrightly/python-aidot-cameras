# Environment variables: internals

Every `AIDOT_*` variable the library reads that is **not** in the README's
tables, in four groups: the streaming knobs, the deeper internals, the ffmpeg
serve and the recordings, and the endpoint overrides and test seams.
The code's own copy of this inventory is `aidot_cameras/settings.py`: every
variable, its default and how it parses, grouped the same way, and a test that
fails when a variable is missing from these tables or a row names no variable.
Most were added during an investigation of one camera model, and every default
was measured. None is part of the public API (see
[API-STABILITY.md](API-STABILITY.md)): each may be removed in a later release
once the behaviour it tunes has settled, and the CHANGELOG will say so.

## Settings given to the client

Four of the README's streaming variables can be given per account client
instead: `StreamSettings` (exported from the package root), passed as
`CameraClient(..., stream_settings=...)` and handed to every camera that client
creates.

| Field | Variable |
| --- | --- |
| `direct_publish` (bool) | `AIDOT_DIRECT_PUBLISH` |
| `publish_aac` (bool) | `AIDOT_PUBLISH_AAC` |
| `hls_direct_ts` (bool) | `AIDOT_HLS_DIRECT_TS` |
| `sdes_video_pt` (`96`, `97` or `"none"`) | `AIDOT_SDES_VIDEO_PT` |

One precedence rule, wherever a camera reads one of them (`settings.read` with
the camera's settings as `overrides`): a field that is set, then the variable
if it is set, then the default. A field left `None` has no opinion, so a client
given nothing, or an empty `StreamSettings()`, reads exactly as before. The
variables stay supported; they are process-wide, so two clients that need
different values must use the fields. A value the field does not accept raises
when the `StreamSettings` is built, not at use.

## Streaming knobs

| Variable | Effect | Default |
| --- | --- | --- |
| `AIDOT_FAST_CONNECT` | Enable LAN-direct "fast connect" (STUN-only, skips several cloud signaling waits) when truthy. On-LAN only - off-subnet/strict-NAT viewers must leave it off. | unset (off) |
| `AIDOT_FAST_CONNECT_HOST_ONLY` | Within `AIDOT_FAST_CONNECT`, narrows only the local `RTCPeerConnection` to host candidates (skips the ~5 s srflx gather stall). **On-subnet only** - drops srflx/relay fallback. Opt-in. | unset (off) |
| `AIDOT_SDES_FAST_LIVEPLAY` | Don't block on the `livePlayResp` wait for eligible SDES cameras (~4.5 s faster cold start). Role-reversal models (A001064 PTZ) always excluded for correctness. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_SDES_LIVEPLAY_ECHO_S` | How long to wait for the broker to echo our own `livePlayReq` back before sending `webrtcReq`, in seconds (`0` disables the wait). It is pure latency: across 22 h of one deployment the wait ran 169 times and timed out 169 times, never once ending early, with no inbound `livePlayReq` among 5000+ messages the cameras and broker did send. The code proceeds on timeout anyway, so it never changed behaviour, only delayed it. Measured on an A001064: time to first media 11534 ms -> 6819 ms. The fast-liveplay path kept 1.5 s until it was measured on its own - 22 runs, 22 timeouts at 1500-1501 ms, no echo ever - and now shares the same value; that was 1.25 s of a 5.4 s cold connect. Still honoured, so a broker that does echo short-circuits it. | 0.25 s |
| `AIDOT_ABANDONED_MEDIA_GRACE_S` | Extra seconds to wait for the first packet after the **battery** stale-offer backstop ends the wait, in seconds (`0` disables). The serve SDP is built from what has actually been observed, so ending the wait a moment before the first packets arrive costs that session its audio for the whole session - measured 2026-09-03 with media arriving 4.2 s after the wait ended. Read per open and clamped, so `0` takes effect without a restart and a malformed value falls back to the default. | `8` |
| `AIDOT_BATTERY_STALE_OFFER_GRACE_S` | Backstop for a **battery** attempt that stalls anyway, in seconds (`0` disables it). When the camera was silent as the first-media wait began, has since turned up, and has still sent no media after this long, the attempt is abandoned to the retry instead of holding the full 75 s window - a stalled camera answers and then sends nothing, while the retry's fresh offer is served in about 5 s. Measured from the camera's first sighting rather than its latest message, because a camera emitting an event every 5 s while its handshake goes nowhere would otherwise push the decision out until it had gone back to sleep. Sized clear of the slowest healthy open (10.7 s) so a merely slow attempt is never mistaken for a stalled one. | `15` |
| `AIDOT_SDES_NO_MEDIA_GRACE_S` | After the camera's answer, how long with no media at all before the attempt is abandoned to the retry. The second of the two first-media timers, beside the battery stale-offer one. It replaced three rules that each waited 20 s from a different event (`AIDOT_SDES_UNREACHABLE_NOMINEE_GRACE_S`, `AIDOT_SDES_ANSWER_PROBE_GRACE_S`, `AIDOT_SDES_TRIGGER_GRACE_S`, all now deprecated and ignored); the stall WARNING still names which of those shapes the attempt was in (`abandoned: unreachable-nominee`, `no-probe` or `trigger-unacked`), or `abandoned: no-media` when none fits. Measured 2026-09-22 to 10-09: 165 first medias, the slowest 9.5 s after `webrtcReq`. Timed from the answer, never the open, so a waking battery camera is not clipped. `0` restores the full-budget wait; a malformed or non-finite value is the default. | `20` |
| `AIDOT_SDES_UNREACHABLE_NOMINEE_GRACE_S` | Deprecated, ignored; a warning is logged once when it is set. Replaced by `AIDOT_SDES_NO_MEDIA_GRACE_S`. | - |
| `AIDOT_SDES_ANSWER_PROBE_GRACE_S` | Deprecated, ignored, as `AIDOT_SDES_UNREACHABLE_NOMINEE_GRACE_S`. | - |
| `AIDOT_SDES_TRIGGER_GRACE_S` | Deprecated, ignored, as `AIDOT_SDES_UNREACHABLE_NOMINEE_GRACE_S`. | - |
| `AIDOT_SDES_ECHO_WAIT_S` | Deprecated, ignored; a warning is logged once when it is set. The SDES open no longer waits for the broker to echo our `webrtcReq` and never sends a `webrtcResp`, as the official app does not. | - |
| `AIDOT_SDES_SERVE_AUDIO` | Include the camera's audio in what an SDES camera serves, on both the RTSP-push and http-serve paths. **On by default** for parity with the official app; set to `0`/`false`/`no`/`off` for video only, which is what a consumer that cannot cope with the audio wants. The standalone CLI reads this; inside Home Assistant use the integration's **SDES camera audio** option, or the per-camera **Camera audio (in HA stream)** switch, instead. | enabled (on) |
| `AIDOT_SDES_NACK` | Ask the camera to resend video RTP packets that never arrived (RTCP Generic NACK). A camera losing packets on a weak link otherwise delivers truncated H.264 slices, which a browser's WebRTC decoder conceals but Media Source Extensions treats as fatal. Measured on an A001064 at ~1-2% loss: 98.4% of losses recovered against none at all without it. Costs nothing on a clean link, where no requests are generated. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_PINNED_CODEC_RETRIES` | With `AIDOT_HLS_DIRECT_TS` and the H.264 pin on, for a camera that gets the in-sync TS (not a battery camera), how many times in a row a session that sends the other codec is abandoned and re-opened before it is served as it came. The in-sync TS is promised on the strength of the pin and only an H.264 session feeds it; the A001064 answered H.265 in 15 of 107 pinned opens (2026-08-26). `0` serves every session as it comes. | `2` |
| `AIDOT_DTLS_FAST_LIVEPLAY` | The DTLS (A000088) analogue: skip the `livePlayReq`-echo and `livePlayResp` waits (the dominant LAN cold-start cost) while keeping the full ICE/TURN/DTLS handshake, so remote/relay viewing is unaffected. **On by default**; set to `0`/`false`/`no`/`off` to disable. | enabled (on) |
| `AIDOT_HLS_TS_PORT` | Port of the loopback listener for `AIDOT_HLS_DIRECT_TS`. One listener serves every camera, by path (`/aidot_<first 12 of the device id>.ts?auth=<secret>`). | `0` (any free port) |
| `AIDOT_PUBLISH_GAP_WARN_S` | Seconds without a frame to publish before the direct publisher says so in the log. The line splits the gap into its causes - seconds idle waiting for a frame to arrive, seconds spent inside the publish, and how many frames were dropped over that gap (waiting for a keyframe, or a presentation time already served) - alongside the queue depth. Each session also records its largest gap. `0` disables the line. | `1.0` |
| `AIDOT_SERVE_RELAY` | Hold the public stream port via an internal relay that proxies to ffmpeg, so the first (cold) view connects instead of failing while ffmpeg can't pre-bind the port. Set to `0` to serve ffmpeg directly. SDES serves only: a DTLS pull serve holds its own port for the whole stream, and a direct publish has no port to hold. | `1` (enabled) |
| `AIDOT_DTLS_VIDEO_GRACE_S` | How long a connected DTLS session may go without a single video frame before it is torn down and re-opened. A session that receives audio and no video passes every other check the serve loop makes - the peer connection is healthy, ffmpeg respawns for each consumer - so without this it is held open indefinitely while the viewer sees "no video". `0` disables the check. For the short-session pacing (`AIDOT_DTLS_SHORT_SESSION_S`), a session with no video canary to read counts as having delivered video unless this check declares it video-less: one that ends before the check rules (sooner than this grace, or with the check off) is not counted short, since unknown means do not back off. | `30` |
| `AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S` | How long one WebRTC open attempt for a served DTLS camera may take before it is abandoned and retried. Raised from 30 s because the library re-publishes its offer (`webrtcReq`) at 15 s and again at 30 s when the camera has not answered, so the attempt used to die at the instant its last resend went out; answers measured arriving at 30.7-99.5 s were discarded as a result. | `75` |
| `AIDOT_DTLS_SERVE_ICE_WAIT_S` | Separate budget for the ICE half of that open, clamped to `AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S`. The open is two sequential waits - signalling then ICE - so without its own budget the ICE wait inherits the timeout above and doubles the worst case while holding the global open gate. | `30` |
| `AIDOT_DTLS_FUTILE_VIDEO_LIMIT` | Consecutive failed DTLS sessions after which the serve loop stops re-opening and goes dormant. A session fails when it ends sooner than `AIDOT_DTLS_SHORT_SESSION_S` or is known to have delivered no video. Noticing alone is not enough: such a session is otherwise a clean open, so a loop that simply re-opened would wake the camera after every session-end wait (30-45 s) indefinitely. A fresh live view reopens a dormant camera. The count is kept on the camera, not the serve, so a camera still failing after that goes dormant again after one more failed session; a session that lasts with video starts the count again. `0` keeps retrying forever. | `5` |
| `AIDOT_LOGIN_RETRY_CAP_S` | Ceiling on the exponential delay between the LAN login retries that `AIDOT_LOGIN_RETRY_LIMIT` (README) counts. | `60` |
| `AIDOT_LOGIN_CONNECT_TIMEOUT_S` | Ceiling on one LAN connect+login attempt. A device that completes the TCP handshake and then stops answering is abandoned and its socket closed rather than parking the attempt forever. | `20` |
| `AIDOT_LAN_STABLE_SESSION_S` | How long a LAN session must stay up after a successful login before it counts as working. A device that closes the session sooner (for example because another controller took the light's only LAN session) is retried on the same backoff and ceiling as a failed login, instead of being logged back in every second. | `60` |

## Deeper internals

Finer-grained knobs read by the camera client; the defaults are tuned to work
out of the box.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_STREAM_IDLE_S` | Seconds of stream idle before an idle release. | `120` |
| `AIDOT_BATTERY_UNKNOWN_VIEWER_RELEASE_S` | On a battery camera, how long a stream may run while its viewers cannot be counted (a go2rtc the library cannot query, or one that stops answering) before it is released anyway, in seconds since the last known viewer (or since the stream was started, if none was ever seen), counted across reopened sessions: a stream whose sessions keep ending on their own is also stopped once this has passed. Without it such a camera streamed until restart. Mains cameras are unaffected: an unknown viewer state never releases them. `0` or negative disables the cap; a malformed value falls back to the default. | `300` |
| `AIDOT_STREAM_STARTUP_GRACE_S` | How long a session waits for its first viewer before the idle window applies, in seconds since the open. Until a viewer has been seen, the idle window is at least this long, so a short `stream_idle_s` cannot end a view that is still connecting (a stream worker or recording that retries late on a slow battery wake). Once a viewer has been seen, the idle window alone applies. A session nobody ever watches still ends on its own after this long. `0` or negative means no grace; a malformed value falls back to the default. | `60` |
| `AIDOT_ICE_DISCONNECT_S` | ICE-disconnect debounce, in seconds, before tearing down. | `8` |
| `AIDOT_DTLS_RETRY_GATE_S` | Minimum spacing, in seconds, between DTLS open retries. | `15` |
| `AIDOT_BUSY_RETRY_S` | How long the DTLS serve loop waits after an open the camera refused as busy: a flat wait that does not move the failed-open backoff, so the serve comes back within this long of another viewer letting go. It is also the floor on the wait after an open whose ICE never connected: the camera still holds the session it accepted for that offer and refuses a sooner retry as busy (`-50002`); measured on an A000088 on 2026-11-04, every retry 15 s after such a failure was refused. That wait is the failed-open backoff (15 s up to 600 s) or this, whichever is longer, and nothing but a stop cuts it short, not even the camera coming back online. Only the serve loop's own retries get it; other open paths retry as before. | `45` |
| `AIDOT_DTLS_SHORT_SESSION_S` | A served DTLS session that ends on its own sooner than this many seconds after its open succeeded (measured to when its connection went bad), or known to have delivered no video, is a failed session. A session with no video canary to read counts as having delivered video unless the presence watchdog (`AIDOT_DTLS_VIDEO_GRACE_S`) declared it video-less, including one that ends before the watchdog rules. After one the serve loop waits before reopening: 30 s after the first, then jittered between 30 and 45 s, never longer, so that typically a viewer's pull held by the serve relay is still waiting when the reopen lands (see `_SHORT_SESSION_CAP_S` in camera/client.py); after `AIDOT_DTLS_FUTILE_VIDEO_LIMIT` in a row it goes dormant. A session that lasts this long and delivered video starts that backoff again. While it waits the loop keeps its concurrent-stream slot (`AIDOT_MAX_CONCURRENT_STREAMS`) and does not report the serve ready. Idle release stays the serve cycle's. Kept apart from the failed-open backoff. Read at each session end. | `30` |
| `AIDOT_BUSY_BACKOFF_S` | How long to wait after a camera answers an open with "no free session" (`-50002` / `-50015`) before the next attempt. Measured on an A001064: a reopen 2 s after a close is refused and one at 8 s succeeds, so the old 300 s wait was mistaking a camera that clears in seconds for one that needs a rest. Still a real wait: retrying at once would hammer a camera that genuinely has none free, and on a battery model risks a wake-then-sleep loop. | `20` |
| `AIDOT_OFFLINE_RECHECK_S` | While a device is cloud-offline, how often the paused SDES or JPEG keepalive retry re-checks the online flag. The DTLS serve loop waits its 600 s backoff cap instead, ending early once the cloud reports the camera back. | `30` |
| `AIDOT_OFFLINE_PROBE_S` | While a device is cloud-offline, how often one real SDES or JPEG open attempt still probes it (guards against a stale cloud flag). Not read by the DTLS serve loop. | `600` |
| `AIDOT_FUTILE_KEEPALIVE_LIMIT` | Consecutive background keepalive sessions that deliver no media before the keepalive stops reopening a battery camera. Seen on an A001513: 22 opens over about 8 hours with no media, because the loop escalated its backoff but never stopped, and a unit was drained to 5% that way. `0` disables the ceiling. The DTLS analogue is `AIDOT_DTLS_FUTILE_VIDEO_LIMIT` above. | `5` |
| `AIDOT_DTLS_SLOW_PROBE_THRESHOLD` | Deprecated, ignored; a warning is logged once when it is set. The DTLS serve loop now retries failed opens on one backoff from 15 s up to 600 s, waits 540-600 s each time at the cap, and drops its WARNING to an occasional INFO line there. | - |
| `AIDOT_DTLS_SLOW_PROBE_INTERVAL_S` | Deprecated, ignored, as `AIDOT_DTLS_SLOW_PROBE_THRESHOLD`. | - |
| `AIDOT_DTLS_SLOW_PROBE_LOG_EVERY` | Deprecated, ignored, as `AIDOT_DTLS_SLOW_PROBE_THRESHOLD`. | - |
| `AIDOT_DTLS_SLOW_PROBE_CHUNK_S` | Sleep increment inside the DTLS serve loop's long waits (the failed-open backoff, up to 600 s, and the session-end wait), so a `stop()` is not held up by the whole interval. Floor 0.1 s: a smaller value, including `0`, reads as `0.1`. | `5` |
| `AIDOT_GOP_PLI_S` | Interval, in seconds, between PLI (keyframe) requests. | `2.0` |
| `AIDOT_STALL_PLI_S` | If muxed frames stall for this many seconds (a dropped GOP on a jittery link), request an IDR keyframe immediately instead of waiting out the full `AIDOT_GOP_PLI_S` cadence. Mains DTLS cameras only; `0` disables. | `1.0` |
| `AIDOT_SDES_PLI_GAPS` | Comma-separated second offsets for the early PLI burst on SDES cameras, to pull the first keyframe in faster on cold start. | `0,1.5,2,3` |
| `AIDOT_SDES_STALL_NUDGE` | Mid-session stall nudge on SDES cameras: when inbound media stops with no teardown signal, re-send the AVIO LIVING message (the one that starts media on a fresh session) on the live session, a few times, spaced out, before the input timeout and the keepalive reopen take over. The A001064 stops transmitting this way and answers at once afterwards. `0` turns it off. | `1` (on) |
| `AIDOT_SDES_STALL_NUDGE_AFTER_S` | Seconds without media before the nudge fires. | `2.5` |
| `AIDOT_SDES_AUDIO_GAIN_DB` | Trim (dB) on an SDES camera's served audio. On the direct publish (the default since 1.0.0rc44) it is an offset applied after the level normalizer below, so `0` serves the camera at the same loudness as an A000088; the per-camera `sdes_audio_gain_db` option sets it the same way. On the ffmpeg serve it is applied to the camera's raw A-law with no normalizer and defaults to `-8`. | `0` direct publish, `-8` ffmpeg serve |
| `AIDOT_AUDIO_TARGET_DBFS` | Target loudness (dBFS) of the level normalizer every served path runs on the camera's audio: both direct publishers (DTLS always; SDES since the release after 1.0.0rc51) and, since the release after 1.0.0rc54, the PyAV mux behind the ffmpeg serve, which used to run its own copy of the same algorithm. | `-15` |
| `AIDOT_AUDIO_MAXGAIN_DB` | Most the normalizer may lift the camera's audio (dB). | `30` |
| `AIDOT_AUDIO_MINGAIN_DB` | Most the normalizer may lower it (dB). | `-12` |
| `AIDOT_AUDIO_GATE_DBFS` | Below this RMS level the normalizer fades its gain down instead of lifting the A-law quantization floor into clicks. A quiet room reads quiet by design: measured 2026-10-14, an A001513 sending -70 dBFS comes out at -64 dBFS RMS under the default and at -40 with the gate at `-60`. | `-45` |
| `AIDOT_SPROP_DIR` | The library's state directory: captured SPS/PPS (sprop) parameter sets, and the `hls-ts-url` file. Set to a writable path if the default location is read-only. | `<package dir>` |
| `AIDOT_REMB_TARGET_BPS` | Send REMB (receiver-estimated bandwidth) at this bitrate on SDES sessions. The camera advertises `goog-remb` and the sender is kept and tested as an instrument; on the reference fleet it did not change the encoder's rate. `0` sends none. | `0` (off) |
| `AIDOT_HEALTH_LINE_S` | Seconds between the one-line health report each live session logs at INFO: packets and rate for the period, bytes where the transport counts them (SDES), packets lost where it counts those (DTLS), seconds since the last media packet, the path the media took (`direct` / `relay`), whether a viewer is present and, on DTLS cameras, the pictures served (after the camera's re-sent frames are dropped), re-sent frames, keyframes and the longest run of pictures between keyframes. Deltas per period, so a steady trickle of loss reads as a rate rather than disappearing into a session total; a counter that goes backwards without the session restarting reads as 0, and a restarted session reports its absolute counts. `0` disables the line; a negative value is `0`; a malformed or non-finite value is the default. | `60` |
| `AIDOT_MQTT_CONFIG2` | Experimental. Fetch the MQTT broker credentials the way the AiDot app does: `GET /v25/commons/mqttConfig2` with `sessionType=App` and a per-install `sessionId` (21 characters of `[a-zA-Z0-9]`, generated once). The reply's clientId, password and broker host (`wss://<host>/mqtt`) are used; the username stays the account's user id. The sessionId is kept in `<AIDOT_TOKEN_FILE>.mqtt-session-id` when a token file is set, otherwise in login_info as `mqttSessionId` (persisted wherever login_info is, such as a Home Assistant config entry). If the call fails or the reply lacks the host, clientId or password, the usual `/commons/userConfig` chain runs instead. `getServerUrlConfig` is still called for its `ip` field (the SDES srflx candidate). One INFO line says which endpoint supplied the credentials. The hope, unverified, is a separate clientId per install so Home Assistant, the CLI and the phone stop evicting each other from the broker. `1`/`true`/`yes`/`on` enables. | unset (off) |

## The ffmpeg serve and the recordings

The ffmpeg serve carries SDES recordings, snapshots, `http://` serves, H.265
sessions and any SDES camera with `AIDOT_DIRECT_PUBLISH=0`. A DTLS camera runs
no ffmpeg: its `-` and `http://` serves are the PyAV mux's MPEG-TS as written,
and its `rtsp://` push is always the direct publish.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_SERVE_REORDER_QUEUE` | Packets the serve's RTP demuxer holds to put a burst back in order. A keyframe on an A001064 is 146-190 KB, roughly 130 packets, so anything much smaller cannot ride out a burst that arrives out of order. | `500` |
| `AIDOT_SERVE_MAX_DELAY_US` | How long, in microseconds, the demuxer waits for a missing packet before moving on. Bounded on purpose: waiting forever turns loss into a stall. The SDES publisher's reorder budget reads the same variable (capped at 0.5 s) so the two cannot drift apart. | `500000` |
| `AIDOT_SERVE_INPUT_TIMEOUT_S` | How long the serve's input may go silent before ffmpeg gives up. Unset, a mains camera gets 30 s to ride out a transmit gap and a battery camera keeps ffmpeg's 10 s, because its stops are real sleeps that only a reopen ends; set, the value applies to every camera. | unset |
| `AIDOT_SERVE_ARRIVAL_TS` | Stamp the serve's input by arrival time instead of trusting the camera's RTP clock. The A001513 sends in-order packets stamped about 1.7 s in the past every 30 s, which read as backward decode times (6-7 warnings per streaming minute). `0` trusts the camera's stamps again. | `1` |
| `AIDOT_REORDER_SLACK_TICKS` | How far the DTLS A/V mux shifts video presentation ahead of decode, in 90 kHz ticks; audio is shifted by the same time so the two start together. | `180000` (2 s) |
| `AIDOT_FFMPEG` | The ffmpeg binary the decoder-capability probe (`hwaccel.probe_decoder`) runs. | `ffmpeg` |

## Endpoints and test seams

Unset in every normal installation.

| Variable | Purpose | Default |
| --- | --- | --- |
| `AIDOT_API_BASE_TEMPLATE` | Override for the platform API base; `{region}` is substituted. | the vendor's `prod-{region}-api` host |
| `AIDOT_SMARTHOME_URL_TEMPLATE` | Override for the smart-home API base; `{region}` is substituted. | the vendor's `{region}-smarthome` host |
| `AIDOT_MQTT_URL` | Point the whole client at a local MQTT broker (`ws://127.0.0.1:PORT/mqtt`) without any cloud call. A test seam. You set it yourself, so the refusal of a non-TLS broker URL from the cloud (see `AIDOT_ALLOW_PLAINTEXT_MQTT` in the README) does not apply to it. | unset |
| `AIDOT_STUN_SERVERS` | Comma-separated STUN URIs for ICE gathering; an empty value (`AIDOT_STUN_SERVERS=`) disables STUN. | a public STUN server |
| `AIDOT_TURN_SERVERS` | TURN relay URIs appended when the cloud's ICE config carries none; an empty value disables the hardcoded vendor fallback. | the vendor's relay |
