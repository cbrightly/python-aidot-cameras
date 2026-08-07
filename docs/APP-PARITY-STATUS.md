# App parity: where we stand, and where we deliberately do not

The vendor Android application is the reference implementation for the camera
protocol, and an older trace of it (`CAMERA_LIFECYCLE_PARITY.md`, in the legacy
`python-AiDot` tree) lists three open "gaps" against it. **All three are closed.**
That document is from June and has been overtaken; this file records the current
state with citations into the code, because the stale list has now cost two
separate investigations that started implementing things that already exist.

Read the code before acting on any parity claim, including this one.

## The three gaps

| Gap | Claim in the June trace | Actual state |
| --- | --- | --- |
| **A** reconnect backoff | ours 5 s, app 15 s fixed + 15 s gate | **deliberate divergence**, A/B'd |
| **B** offer resend | app resends 2-3x per attempt, we send once | **implemented** |
| **D** terminal ack handling | we retry `-50002`/`-50015`, app treats them as terminal | **implemented** |

### A - reconnect backoff: intentionally 5 s, not a gap

`_streaming_loop` uses `_MIN_DELAY = 5.0` with exponential escalation to 300 s.
The comment at that line records the reasoning: the app's fixed 15 s (f0.java
`I1=15000`) was tried, measured, and produced no benefit while slowing recovery.
Home Assistant users notice a slow reconnect; they do not notice a fast one.

Do not "fix" this to 15 s without new evidence that beats the A/B already done.
The SDES keepalive uses 10 s and the DTLS serve loop already uses 15 s, so the
three loops differ on purpose rather than by accident.

### B - offer resend: implemented

`webrtc_open.py` resends the offer within a single attempt. `_init_reconnect_resends`
gates a resend (bounded at 2) after a camera reconnect, and the echo-reversal
path re-sends `webrtcReq` plus the ICE candidates while polling for a real
`webrtcResp`. The A001064 is called out by name there: its SDES path poisons the
session, the camera resets on our DTLS `webrtcReq`, and the resend gets a real
answer in roughly 240 ms.

### D - terminal acks: implemented

`_WEBRTC_TERMINAL_ACK_CODES = (-50002, -50015)` in `protocol.py`, honoured on
both transports - the SDES branch checks it before an open and before spending a
DTLS fallback, and the keepalive backs off to `_MAX_DELAY` rather than minting a
fresh peerid into a camera that just refused one.

## Parity that was confirmed, not assumed

- **Teardown is dispose-only.** The app sends no `livePlay:0`, no stop command,
  no MQTT unsubscribe; the camera releases on heartbeat loss. An earlier claim
  that we were "missing a camera stop" was retracted after tracing the app.
- **Heartbeat** is command 5156 every 10 s, matching f0.java.
- **Reconnect model** is a full re-offer, not an ICE restart - `restartIce` does
  not appear in the app at all.
- **ICE handling has nothing to copy.** The app passes inbound candidates to
  `addIceCandidate` verbatim: no filtering, no prioritisation, no port selection,
  no re-nomination. Pairing is entirely native libwebrtc.

## The one real difference still unexplained

Bitrate. The app takes 225-500 Kbps on auto quality; we take 1000-1800 Kbps from
the same camera. This is **not** reachable through the HD/SD control: on
2026-08-06 the A001064 was set to `sd` both mid-session and at session start, and
the encode stayed 1280x720 both times with the bitrate unchanged. The camera
ignores `SETSTREAMCTRL` (cmd 800) for resolution, which is also why the
integration's resolution select currently reports a value the camera never
applied.

Whatever the app asks for that we do not, it is not that command. Answering it
needs a capture of the app's own session setup, not another guess - eight
hypotheses have already died here.
