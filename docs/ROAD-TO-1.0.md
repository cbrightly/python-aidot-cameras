# What 1.0.0 is waiting on

Written 2026-08-07, updated 2026-08-08 after an audit-closing day. The point of
this file is to make the bar checkable, so that "are we ready" stops being a
matter of opinion.

What 1.0.0 asserts is **not** that there are no known issues. It asserts that we
understand the system and its shape has settled.

## Already met

- **A clean public surface.** The reference consumer reaches into zero private
  attributes of this library - measured, not assumed. See
  [API-STABILITY.md](API-STABILITY.md).
- **A release gate with teeth.** Unit tests on musl (the runtime target), an
  end-to-end fake-lab tier, install checks against two Home Assistant versions,
  a downstream integration run, and validation against real cameras on a LAN
  runner before anything reaches PyPI.

  Qualified on 2026-08-08, because the gate was partly scoring its own homework:
  its SDES PASS signal is `media_stats.packets`, the very counter that was
  counting undecryptable packets as delivered media, with `recorded_bytes` from
  a `-c copy` pipeline as the fallback - neither requires a packet to have
  decrypted or decoded. And the harness never configured logging, so every INFO
  and DEBUG line the library emitted was discarded in every run. Both are fixed:
  the harness now decodes the recording and reports real frame counts, and the
  aidot loggers are raised. The gate is meaningfully stronger than the sentence
  above claimed when it was written.

- **The audit backlog is closed.** Every item in the 2026-08-08 findings list
  landed: media counted only when the consumer can use it, the SDES receive
  session rebuilt on rekey, the `webrtcResp` future resolved in one loop hop,
  three decorative tests replaced with real ones, snapshot cancellation
  propagated, and a latent SCTP TSN mapping corrected.

- **Local (LAN) control is demonstrably working.** Ten devices across four model
  families return 200 for the owning account. It had appeared broken; the
  devices were rejecting the shared-home account the CI runner deliberately
  uses. No library defect existed. See the READMEs.

- **The coverage holes are decided.** The `liveType=0` / TUTK path is out of
  scope for 1.0.0 with a stated reason, and the `async_set_resolution` ack read
  has coverage that joins the setter to a real session on DTLS. Item 4 below
  keeps its number and records both decisions; it no longer gates anything.

## Open

### 1. The discovery rate is still high

Fourteen releases in the four days to 2026-08-07, and the finds were structural
rather than cosmetic: inbound AVIO on SDES was being decoded, logged and dropped
so the whole response path was dead on that transport; a `-50002` backoff waited
300 s for a camera measured to clear in 8.

**2026-08-08 set this back rather than advancing it.** Roughly ten changes
landed in a day, and three were structural: undecryptable packets counted as
delivered media, so a black stream could report healthy indefinitely and the
abandon ceiling could never fire; the SDES receive session latched behind a
`hasattr` so a camera that rekeyed reached nothing; and four logging sites
printing SRTP key material into logs users paste into public issues.

That is not a settling cadence. The clock has not started.

The bar: **two weeks with no streaming-breaking release.** The cadence is the
evidence.

### 2. An unexplained 5x bitrate difference

An A001064 takes 1900-3700 Kbps where the vendor app takes 225-500 from the same
camera.

**Twelve hypotheses have died here.** The three most recent, all 2026-08-07 and
all with the measurements recorded:

- **RTCP feedback / REMB.** The camera really does negotiate `goog-remb` - its
  answer advertises it on audio PT 8 and video PT 96 - but sending REMB at 500
  Kbps, verified transmitting and naming the video SSRC, moved nothing. The one
  clean interleaved pair had the REMB arm *higher* than its control, 3859 against
  3355 Kbps. Shipped disabled (`AIDOT_REMB_TARGET_BPS=0`).
- **The stream-quality byte.** `SETSTREAMCTRL` was swept across all six
  `AVIOCTRL_QUALITY` values AND the value 16 the app sends for "Auto" (which the
  enum does not name) - fourteen sessions, each followed by a session that sent
  nothing to catch a next-session effect. All seven acked; all fourteen sessions
  came back h264 1280x720, single dimension cluster. The device properties that
  looked like better explanations were checked too: the camera reads
  `StreamType=0`, so it is not in Auto, and `dynamicStream` is a read-only
  capability flag gating app UI, not a mode. See "The stream-quality control" in
  [APP-PARITY-STATUS.md](APP-PARITY-STATUS.md).
- **Narrowing the offer to H265.** An H265-only offer returns no video at all,
  3 of 3 rounds.

**What was found instead, and it is the first real lever:** this camera serves
*two* profiles for an identical request - h264 1280x720 at 2.5-4.0 Mbps, and hevc
2560x1440 at ~1.1 Mbps - choosing per session, because our offer advertises both
video codecs and expresses no preference. `AIDOT_SDES_VIDEO_PT` pins the offer
and makes the choice deterministic (96 gave h264 720p in 4 of 4). The efficient
hevc profile appears only when both codecs are offered; what selects it is still
unknown, which is why the negotiated profile is now logged at INFO on every
session.

**2026-08-08, later: the corpus exists now, and the first sample is in.** Run
`31285146195` produced three profile lines - one per SDES camera - where every
previous run produced zero:

    12b144cb12da (A001064)   video profile pt=96 codec=H264
    338603b50fce (A001513)   video profile pt=96 codec=H264
    b5284fc70d1e (A001513)   video profile pt=96 codec=H264

Three of three chose H264. No hevc instance appeared, so this run cannot answer
what selects it - the question needs a corpus large enough to contain both
outcomes for the same camera. Collection is proven to work; the campaign has not
been run. That is the honest state, and it is a different state from "blocked".

**2026-08-08: that logging was never reaching anyone.** The validation harness
did not configure logging, so the root logger fell back to WARNING-and-above and
every one of those INFO lines was discarded - four runs after the instrumentation
shipped produced zero of them. The standing advice not to open a fourteenth
hypothesis until a corpus accumulated was waiting on data that could never
arrive. Fixed; the first run after that fix is the first that can produce the
record this item asks for.

Note the profile also differs by model: an A001513's h264 is 1280x960, not
1280x720, so codec does not imply resolution across a mixed fleet.

The bar: **explained, or documented as accepted** with the measurements. Shipping
1.0 with an unexplained 5x resource difference on a supported model is a stretch.

### 3. A camera stuck in a failing retry loop

An A001513 was observed relaunching its serve roughly every 95 s across an
eight-hour window, each attempt dying the same way:

    Could not find codec parameters for stream 1 (Video: h264, none): unspecified size
    [rtsp] dimensions not set
    [out#0/rtsp] Could not write header (incorrect codec parameters ?)

22 launches, 22 "no audio observed before the serve launched", 21 exits with
code 234. Another camera of the same model showed none of it, and a mains camera
hit the same failure once and recovered - so this is one unit in a loop, not a
fleet property. It is a battery camera, so the loop costs charge.

The bar: **root cause found and fixed.** The serve is being launched before
ffmpeg can establish the video dimensions; why that never resolves on this one
unit is the open question.

**2026-08-08 added a sharp clue.** Across 15 sessions the first-media outcome is
bimodal with a 63-second empty band: the slowest SDES pass was 16.8 s and the
fastest no-media was 80.1 s, with nothing in between. Media arrives fast or it
never arrives - so whatever fails here fails at setup, not by degrading.

**This is one defect wearing three names.** It is also the `camera.kitchen`
no-media case, and the subject of the relay-only investigation: the same unit
streams fine from other hosts on the same LAN, and the one difference found is
that it sits on a different SSID from the Home Assistant host while we advertise
a host candidate it cannot reach. The loop was stopped in 0.17.1 - a battery
camera's keepalive now gives up after five futile sessions - but stopping the
loop is not fixing the cause, and the give-up explicitly does not claim to.

### 4. Coverage holes - closed 2026-08-08

The bar was: **tested, or explicitly out of scope for 1.0** and said so here.
Both holes are decided below, so this item is closed. It keeps its number
because the working notes refer to it by number.

#### The `liveType=0` / TUTK path: out of scope for 1.0.0

Not "we ran out of time". The path cannot be entered, and the part of it that
could be tested is not the part that is uncertain:

- **No device can reach it.** `async_open_live_stream` needs a TUTK UID. Every
  camera queried reports `liveType=2` with no `p2pId` field, and none of the
  three sources `async_get_p2p_uid` tries has ever returned one (the smarthome
  `getP2pId` endpoint answers 200 with a null body). So the call refuses at its
  first guard. See [DEFERRED_FEATURES.md](DEFERRED_FEATURES.md) for the fleet
  query.
- **What is past the guard is a foreign SDK.** `TutkStreamSession._start_sync`
  is ctypes against `libIOTCAPIs.so` and `libAVAPIs.so`, which this package does
  not ship, has never loaded, and cannot obtain. A test would have to mock every
  one of those C entry points, which asserts our reading of the SDK rather than
  the SDK - and the whole reason this is deferred is that the reading is
  unvalidated. Passing tests there would make the path look supported.
- **The interesting code is not even in the tree.** The four improvements the
  deferral is about (license key, session-alive timeout, `CONNECTION_CHECK_REQ`,
  the 24-byte `IPCAM_START`) were reverted; only the base scaffolding remains.
- **Nothing calls it.** The Home Assistant integration opens streams through
  `async_open_webrtc_stream`; `async_open_live_stream` is reachable only by a
  consumer who goes looking for it.

What *is* reachable is the refusal, and that now has a test:
`tests/test_tutk_path_is_out_of_reach.py` asserts that a WebRTC camera - DTLS
and SDES alike - never constructs a `TutkStreamSession`, and that the error
names `async_open_webrtc_stream` as the call to use. It asserts non-construction
rather than the `None` return on purpose: with the guard deleted the call still
returns `None`, because loading the absent native libraries fails and the
failure is swallowed, so a test on the return value alone passes against the
broken code.

Re-activating this needs a `liveType=0` camera in hand. At that point the tests
to write are integration tests against a device, not mocks written in advance.

#### The `async_set_resolution` ack read: DTLS was misdescribed here

The line this section used to carry - "the remaining gap is the DTLS models,
where the read has unit tests only" - was wrong, and two places in this repo
already said so. DTLS was the **first** transport the read was confirmed on:

- `CHANGELOG.md` 0.16.0: "An A000088 over DTLS answers SPEAKERSTART (848) with
  851 in 0.38 s, **SETSTREAMCTRL (800) with 801 in 0.01 s**, and GETSTREAMCTRL
  (802) with 803."
- [APP-PARITY-STATUS.md](APP-PARITY-STATUS.md), measured 2026-08-07 with
  `scripts/avio_probe.py`: 801 comes back in 0.01-0.03 s on an A000088, and 802
  reads the value back - 5 at session start, 5 after `sd`, **1 after `hd`**.

A read-back that changes with what was set is stronger evidence than an ack
alone, and it is on the DTLS model. The A001064 sweep extended the result to
SDES and to every value; it did not fill a DTLS hole, because there was not one.

**The real gap was in the tests, and it was a different gap.** Every existing
test of this call stubs `async_avio_request` (`test_resolution_persists.py`,
`test_control_verdicts.py`), and every existing test of the transport calls
`async_avio_request` directly with a payload of its own
(`test_avio_request.py`). Nothing joined the two: no test asserted that the
bytes the setter builds are the bytes a real `WebRTCSession` puts on the
DataChannel, or that a reply arriving at that session's receive entry point is
the one the setter reads. `tests/test_dtls_resolution_ack.py` closes that with
a real session, the real router and frames in the camera's header layout,
reached through `async_set_resolution`: the frame on the wire decodes as 800
with the quality byte where the camera looks for it; a 801 is read and reported;
an answer that arrives inside `dc.send` is still heard; silence is not dressed
up as an ack and leaves no registration behind; an unprompted 804 is not
mistaken for the reply; and a session whose DataChannel is not open returns
promptly instead of holding a service call for the ack budget.

Two residuals, stated rather than fixed:

- **The DataChannel message handlers themselves are not unit-tested.** They are
  closures inside the WebRTC open sequence, and extracting them to a testable
  helper is a change to a shipped path that this work is not the occasion for.
  They are not unexercised, though: the same router and the same handlers carry
  talk's SPEAKERSTART/851 ack, measured 0.01-0.38 s on an A000088, on every
  session where anyone presses to talk.
- **When the DataChannel is not open, the "no ack" log line still says
  "sent".** `_avio_cmd` returned False and nothing left the host. It is a log
  wording issue on a path whose return value is unaffected, and changing it was
  out of scope here; recorded so it is a decision rather than an oversight.

### 5. No standard for keeping secrets out of logs

Added 2026-08-08. Four logging sites on the SDES path were printing real SRTP
key material - one of them the decoded master key AND salt in full hex, another
16 characters of two keys plus the full packet hex, unconditionally, on the
first ten packets of every session. These lines reach `home-assistant.log`,
which users paste into public issue reports.

All four now print a truncated SHA-256 fingerprint, and an AST guard over the
module fails if any logging call carries key material again. But nothing was
watching before, and "we looked once" is not a standard.

The bar: **the guard extended beyond `sdes_open`, or a stated reason it does not
need to be.** The same classes of secret (device passwords, aesKeys, tokens)
exist elsewhere in the package.

## Out of scope for 1.0.0

- **The go2rtc add-on.** A private experiment in moving off HACS distribution,
  never yet run with a real camera, and marked not for public release. 1.0.0
  covers the library and the Home Assistant integration; the add-on is a
  separate line of work and should not gate this one.
