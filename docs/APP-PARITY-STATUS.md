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

### Extended to every value, and to the other transport, 2026-08-07

The table above covers two values on one model, which left room for the control
working on a value or a camera nobody had tried. It does not.

An A001064 (SDES) was swept across all six `AVIOCTRL_QUALITY` values **and the
one value the app sends that the enum does not name**. The enum was read out of
the vendor APK (`AVIOCTRLDEFs.smali`) rather than assumed, because it is **not**
the stock TUTK ordering and anyone reasoning from the public enum will get it
wrong:

    UNKNOWN = 0    MAX = 1    HIGH = 2    MIN = 3    LOW = 4    MIDDLE = 5

The enum is not the whole story. `HorLiveController.smali:629-631` shows the
app's resolution picker has THREE arms, not two: `0x1`, `0x5`, and `0x10` -
whose label is `_auto_resulution`. `KVSWebRTCChannel.smali:15314` ships that
value on `0x320` (= 800), through the same `SMsgAVIoctrlSetStreamCtrlReq` struct
we use. So the app's "Auto" is quality byte **16**, and it sits outside
`AVIOCTRL_QUALITY`, which stops at `MIDDLE = 5`.

An enum sweep alone would have missed it, and did: the first version of this
section claimed the control was settled "on every value it accepts" while
having tested only 0-5.

Each value was sent, then followed by a second session that sent nothing at all -
because a setting that applies to the *next* session would look identical to one
that does nothing, and every earlier check had only ever looked within the
session that sent the command.

| value | ack | this session | next session |
|-------|-----|--------------|--------------|
| 0 UNKNOWN | 801 in 0.05 s | h264 1280x720 | h264 1280x720 |
| 1 MAX     | 801 in 0.17 s | h264 1280x720 | h264 1280x720 |
| 2 HIGH    | 801 in 0.02 s | h264 1280x720 | h264 1280x720 |
| 3 MIN     | 801 in 0.19 s | h264 1280x720 | h264 1280x720 |
| 4 LOW     | 801 in 0.01 s | h264 1280x720 | h264 1280x720 |
| 5 MIDDLE  | 801 in 0.11 s | h264 1280x720 | h264 1280x720 |
| 16 (app's Auto) | 801 in 0.54 s | h264 1280x720 | h264 1280x720 |

Fourteen sessions, fourteen identical results, no dimension change in any of them -
read per frame, not from the container header, so a switch partway through would
have shown. The "does not survive a session" line above is now measured rather
than inferred: the follow-up sessions confirm it.

**The control is settled as inert on this firmware** - on both transports, on
every value the enum defines, and on the extra value the app itself sends for
Auto. Five of those seven had never been sent to any camera on this fleet.

One loose thread, recorded rather than interpreted: value 16 acked in 0.54 s
against 0.01-0.19 s for the enum values. That is 3-50x slower and might mean the
firmware does real work for it. It is n=1 against noisy latencies, and the video
was unchanged either way, so nothing is built on it.

### Why the app's switch still works

The owner reports the app's Auto/HD/SD switch has a visible effect. It sends the
same command we do, with values we have now all tried, so the command is not how
it does it. The obvious next guess was that the app restarts the stream around
the switch and the renegotiation changes the encode.

**It does not.** Traced through the decompiled app rather than assumed:

    HorLiveController.lambda$showResolutionChoiceBox$1   picker -> 0x10 / 0x1 / 0x5
      -> NewLiveFragment$21 -> NewLivePresenter.setResolution(int, boolean)
         the boolean is needLoading - a UI spinner, nothing more
      -> KVSWebRTCChannel.setResolution(int, SetResolutionRespListener)
         -> SMsgAVIoctrlSetStreamCtrlReq.parseContent(0, (byte) value)
         -> sendCtrl(0x320)                    the same cmd 800 we send

    NewLivePresenter$6.onSuccess()   the ack handler, in full:
         store the value in a field
         log "setResolution, onSuccess"
         hideLoading() and cancel the loading timeout
         getQualitySuc(value)  ->  HorLiveController.setResolution(value)
                                   which is the LABEL setter - the same
                                   sparse-switch that maps 0x1/0x5/0x10 to
                                   the displayed HD / SD / Auto strings
         kvsSetResolutionSuccess(...)          analytics

There is no restart, no re-offer and no renegotiation on that path. The app
sends the command, receives the ack, and **changes the text on screen**.

Which resolves the apparent contradiction without needing the camera to behave
differently for the app than it does for us: the visible effect is the label.
The vendor app has precisely the defect this integration removed its own
resolution select for - a control that reports success while changing nothing.

Stated with its limit: what is proven is that the success path does not restart
the stream. No claim is made that no code path anywhere does.

### The whole command surface, enumerated

Rather than keep asking "is there another command", every control id the app
actually sends was pulled out of `KVSWebRTCChannel` and named against
`AVIOCTRLDEFs.smali`:

| id | name | do we send it |
|----|------|---------------|
| 0x318 / 792 | `USER_IPCAM_LISTEVENT_REQ` | no - event list |
| 0x31a / 794 | `USER_IPCAM_RECORD_PLAYCONTROL` | no - SD playback |
| 0x320 / 800 | `USER_IPCAM_SETSTREAMCTRL_REQ` | yes |
| 0x322 / 802 | `USER_IPCAM_GETSTREAMCTRL_REQ` | yes |
| 0x350 / 848 | SPEAKERSTART | yes |
| 0x351 / 849 | SPEAKERSTOP | yes |
| 0x4b5 / 1205 | `USER_IPCAM_HASLISTEVENT_REQ` | no - event list |
| 0x4b7 / 1207 | `DELLISTEVENT_REQ` | no - event list |
| 0x4b9 / 1209 | `USER_IPCAM_CUSTOM_COMMAND_REQ` | no - see below |
| 0x528/0x52a/0x52e | radar start / stop / SD-card start | no - see below |
| 0x1001 / 4097 | PTZ | yes |
| 0x1424 / 5156 | heartbeat | yes |
| 0x1500 / 5376 | LIVING | yes |

**The radar commands are for hardware nobody here owns - closed 2026-08-11.**
Not "unimplemented", not deferred: unreachable. The cloud device records for all
seven reference cameras were checked for any radar-shaped property and carry
none, and the only `IsSupport*` capability flag any of them advertises at all is
`IsSupportPlayback`. The app's radar surface (`onRadarDataReportListener`,
`RadarDataParse`, the radar map UI) has no counterpart in what these devices
report about themselves. Implementing `0x528`/`0x52a`/`0x52e` would mean writing
a feature that cannot be exercised, let alone validated, on any camera available
to this project. Revisit only if a device turns up that advertises the
capability.

**None of them touch the encoder.** The generic escape hatch, `CUSTOM_COMMAND`,
was the last candidate and it is not one either: in `KVSWebRTCChannel` its only
caller is `getThumbnais(List, GetThumbnaiRespListener)`, which packs a list of
event timestamps little-endian and asks for thumbnails.

So there is no command in the vendor app's repertoire that selects the video
profile. Whatever chooses it is internal to the firmware.

### And why the app's kb/s number appears to respond

`TrafficStatsTextView` renders a live throughput figure from
`android.net.TrafficStats.getTotalRxBytes` / `getUidRxBytes`, re-posted every
1000 ms. That is device and process network traffic, not the encoder's bitrate,
and it moves continuously on its own.

This camera's bitrate genuinely varies more than fourfold session to session -
839 to 3698 Kbps measured in one afternoon with nothing changed. A per-second
counter watched across any UI action will move, and would have moved had nothing
been pressed. It is the same trap the A/B arms were interleaved to avoid.

Two device properties were checked before concluding that, because both looked
like better explanations:

- **`StreamType`** is the app's stored selection ("0" -> MAX, "1" -> MIDDLE,
  "2" -> Auto). The A001064 that varies its profile reads **`StreamType=0`**, so
  it is not sitting in Auto - which kills the obvious "it is in adaptive mode"
  reading before it costs a session.
- **`dynamicStream`** looked stronger still: it is `1` on the A001064 that
  varies and `0` on both A001513s that do not, a perfect correlation across the
  fleet. But `PropsBean.isSupportDynamicStream()` is just
  `"1".equals(dynamicStream)`, and its only callers gate app UI - it decides
  whether the picker shows three arms or two. It is a read-only capability flag,
  never written back to the cloud. It reports that a camera can adapt; it does
  not control whether it does.

So the profile choice is internal to the firmware and is not exposed through any
property or command the vendor app uses. The only lever found that does move
this camera is the offer's video codec list - see the codec-pinning note in the
0.17.1 changelog.

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
