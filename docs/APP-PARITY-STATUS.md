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

## The stream-quality control: settled

`SETSTREAMCTRL` (cmd 800) is **not** ignored by the camera, and saying so was
wrong. Measured 2026-08-07 with the response path (`scripts/avio_probe.py`),
which is what made the difference readable at all:

- the camera **acks** it - 801 comes back in 0.01-0.03 s on an A000088;
- and it **records** it - `GETSTREAMCTRL` (802) reads back 5 (MIDDLE) at session
  start, 5 after setting `sd`, and **1 after setting `hd`**.

What does not change is the video. With the quality verified by read-back first,
then recorded and read per frame:

| verified quality | frames | dimensions | bytes/frame |
|------------------|--------|------------|-------------|
| 1 (MAX / `hd`)   | 728    | 1280x720   | 2592 |
| 5 (MIDDLE / `sd`)| 651    | 1280x720   | 2682 |

Every earlier check had been made in the `sd` direction - and `sd` sends 5, the
value the camera is already on, so those checks only ever showed that setting a
camera to its current value changes nothing. The setting also does not survive a
session: each one opens at the camera's own default of 5.

So the camera accepts the command, acknowledges it, reports it back, and encodes
exactly the same video. The integration's resolution select was removed in 2.11.9
for this reason; the command stays in the library because it is correct and a
future firmware may act on it.

## The one real difference still unexplained

Bitrate, and it is model-specific rather than something the app asks for. Direct
library recordings, same method on both, 2026-08-07:

- **A000088 (DTLS)**: ~350 Kbps - inside the app's own 225-500 range.
- **A001064 (SDES)**: 1900-3700 Kbps across two runs.

So "we take 1000-1800 Kbps" is about the A001064 specifically, it is not an
artefact of the Home Assistant or go2rtc path (these numbers are off the library
alone), and it is not the HD/SD control - see above, MAX and MIDDLE produce the
same bytes per frame.

Whatever the app asks for that we do not, it is not that command. Answering it
needs a capture of the app's own session setup against **an A001064**, not
another guess - nine hypotheses have died here now.
