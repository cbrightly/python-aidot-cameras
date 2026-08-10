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
2560x1440 at ~1.1 Mbps - choosing per session, on an offer that advertises both
video codecs. `AIDOT_SDES_VIDEO_PT` pins the offer
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

**2026-08-08: "our offer expresses no preference" was wrong, checked against the
code.** RFC 3264 section 5.1 makes the `m=video` payload-type list a preference
list, most-preferred first, and the offer built at `sdes_open.py` has always
carried `96 97` - H264 first. What is true is weaker and more useful: nothing
ever *chose* that order. It arrived verbatim when the SDES open path was split
out of `client.py` and has never been varied. So the camera is already
disregarding a stated preference in the sessions where it answers H265, which
makes reordering a *weaker* candidate than the pin was, not a stronger one -
recorded here so the next reader does not inherit the stronger framing.

`AIDOT_SDES_VIDEO_PT_ORDER` makes the order settable per run, opt-in and inert
unset, because reordering is the only untried lever that leaves both codecs on
the wire - the one condition under which the hevc profile has ever appeared.
Named payload types lead and the rest are appended, so it cannot narrow the
offer, which is what killed the H265-only attempt. **The knob exists; the
hypothesis is untested on hardware.** The experiment that settles it is two
interleaved arms - `97,96` against unset - alternating rather than blocked
(this camera's bitrate varies 839-3698 Kbps on its own, so blocked arms measure
time of day), the sha and the offer's order receipt recorded per session, and
the `video profile pt=N codec=X` line read from the raw log. It is a categorical
outcome, so 3/3 against 0/3 reads and 2/3 against 1/3 does not. Two kills: hevc
at about the control rate says the camera does not read m-line order as a
constraint, and any session with no video at all says order-preference triggers
the same failure as narrowing and the knob is unsafe rather than merely opt-in.

**2026-08-09: the ordering arm has now been run, and the result is against the
hypothesis but weaker than the kill above assumed.**

Run `31348997269`, `AIDOT_SDES_VIDEO_PT_ORDER=97,96` on the SDES models, with the
receipt present - `SDES: offer video codec order=97 96` on all 8 opens, so this
one did reach the SDP, unlike the first attempt at the pin. Six sessions produced
a profile line and **all six were `pt=96 codec=H264`**. The control is the last
four runs on the shipped order: **23 profile lines, all H264** - lines, not
independent cameras, since a run emits one per session and several are the
snapshot's own session on a camera already counted.

Read carefully, because the kill stated above does not quite apply. It assumed a
control that produces hevc sometimes, so that "hevc at about the control rate"
would be meaningful. The control rate is zero. Six sessions therefore cannot
separate "no effect" from "a small effect", and this arm is blocked rather than
interleaved. It is evidence against the hypothesis, not the kill.

**The one qualitative result is worth more than the counts.** On one open the
camera's answer came back with TWO video m-sections - `m=video 9 RTP/SAVPF 97`
followed by `m=video 9 RTP/SAVPF 96` - where every other answer in the corpus
carries one. So under a 97-first offer this camera will acknowledge H265 in its
answer, and then send H264 anyway. That is a direct observation of the camera
declining a stated preference, which is what the ordering hypothesis needed it
not to do.

**The second kill did trigger, and is confounded.** `L2_F8A3` returned no video
at all on both attempts. That is the unit with an independent, documented stall
- six of the seven stall reports in item 3 are this camera, several of them from
runs on the shipped order - and both of this run's stall reports name ICE causes
(`vetoed-self-ip`, and one with no candidates at all), not a codec cause. So it
does not indict the knob, and it does not clear it either.

`AIDOT_SDES_VIDEO_PT_ORDER` stays: it is opt-in, inert unset, proven to reach the
SDP, and it is the instrument any future arm would use. It should not be
described as promising.

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

#### 2026-08-08 (later): the per-session gate is found

Half the bar is met. **Why any one of these sessions delivers nothing is now
confirmed. Why it repeated 22 times in a row is not.** Both halves are stated
below; do not read the first as covering the second.

Evidence: the raw logs of five fleet validation runs (31275220411, 31279366049,
31281147686, 31282396141, 31283603368), 17 SDES opens, all of them after the
harness started configuring logging, so the library's INFO breadcrumbs are
present for the first time. A sixth run, 31273232752, was read as well and
contributes no rows: it predates that harness fix, carries zero `webrtc:` lines,
and every camera passed, L2_F8A3 included.

**The gate.** Media only ever follows the AVIO LIVING trigger
(`sdes_open.py:3969-4015`, the `SDES: sent trigger` line), and that trigger is
armed by exactly one thing: an inbound STUN Binding Success Response
(`_bpkt[:2] == b'\x01\x01'`) from the camera. Its only other term,
`_use_plain_rtp`, is a model-id constant (`_PLAIN_RTP_MODELS`, true for every
A001513 and A001064) and is identical on both sides of the split.

    open   camera            nominated candidate      trigger  first media
    ----   ---------------   ----------------------   -------  -----------
    x5     A001064 12b144cb  192.168.0.171:<port>     yes      12.2-12.9 s
    x5     A001513 338603b5  54.144.38.43:<port>      yes      4.7-6.3 s
    x3     A001513 b5284fc7  54.144.38.43:<port>      yes      5.6-6.4 s
    x3     A001513 b5284fc7  192.168.100.3:<port>     NO       none
    x1     A001513 b5284fc7  (no ICE creds in answer) NO       none

13 opens sent the trigger and every one delivered first media. 4 did not and not
one delivered a byte. 17 of 17, no exception in either direction.

**Why it never arms on this unit.** USE-CANDIDATE goes only to the candidates
carried in the camera's answer, plus peer-reflexive candidates learned from the
camera's own probes (`sdes_open.py:3083-3099`, `:3788-3800`). On the three
host-only opens the answer carried exactly one candidate, `192.168.100.3`, on a
subnet this host has no route to; the fourth carried no ICE credentials at all.
Nothing we nominated could answer, so no Binding Success came back, so the
trigger never fired, so the camera never started sending.

The camera's own probes did arrive - as TURN Data Indications through our
allocation, 0.5 s after the answer, in both instrumented failures - and were not
learned. A relay-carried probe reaches `_br_obs` (`:3784`) only through
`_br_cam_peer`, which is dropped when `_is_self_peer_ip` matches the
XOR-PEER-ADDRESS; the `_bsrc` fallback is refused because `_bsrc` is the TURN
server and that equals `_hp_host`. VERIFIED: no `learned peer-reflexive` line in
any failing open, and the candidate count stays at 1 across all five permission
installs (`[setup]`, `[trickle host]`, `[trickle srflx]`, `[trickle relay]`,
`[bridge]`), so the camera sent no later candidates and none were dropped.
INFERRED, not verified: which of the two vetoes did it. Both are silent.

**Why one unit.** The cloud device records in these runs carry `ssidName`:
L2_F8A3 is the only camera on the IoT SSID; every other working camera is on the
main one. This is the mirror image of the disproven relay-only story, not a
revival of it. That story said *we* advertise a host candidate the camera cannot
reach. What is measured here is that *the camera* advertises a host candidate
*we* cannot reach, and nothing else. The relay finding still stands: when this
camera's answer does carry its own relay candidate it streams over the relay,
3 of 3 here, first media 5.6-6.4 s. So the remedy is not "move it to the main
SSID" - it is to make the relay path usable when the answer is host-only.

**Why a mains camera hit it once and recovered.** Same mechanism, already on
record in this codebase: `_record_peer_reflexive`'s own docstring
(`sdes_open.py:737-756`) notes the A001064 PTZ advertising `192.168.100.13` as
its only candidate while it sat on that same subnet. That is what peer-reflexive
learning was added for. The PTZ now reports `192.168.0.171` and passes 5 of 5
here, and the learning covers the direct-probe case - just not the relay-carried
one, which is the only path this battery unit has.

**Why the 63-second empty band.** The trigger arms within about a second of the
answer or never. The mechanism has no slow-success state to land in.

**What is NOT established, and it is the rest of the question.** This explains a
failing session; it does not explain 22 consecutive ones. The same unit streamed
on 3 of its 7 opens in this corpus (4 of 8 counting the uninstrumented run) and
on 11 of 26 in the 2026-08-07 sweep. At that rate 22 in a row is on the order of
one in a hundred thousand, so the eight-hour loop needs either a
persistent camera-side state (no relay allocation at all for that window) or a
loop that sustains its own failure. Nothing here decides between them. Note also
that the eight-hour signature above (`[rtsp] dimensions not set`, exit 234) comes
from a Home Assistant log that is not in hand; this corpus shows the same
upstream cause through the file muxer (`[mpegts] frame size not set`, exit 255).
Consistent with, not verified identical to.

**Open sub-question that gates any fix.** A Data Indication reached us while the
only permission installed was for `192.168.100.3`. Either the cloud TURN server
does not enforce inbound permissions - in which case a Send-Indication return
path to the camera's observed address is viable - or the model of the failing
case is incomplete.

**No fix shipped, deliberately.** The change the mechanism points at - learn the
relay-observed peer address, install a permission for it, nominate it through a
Send Indication - sits on the ICE nomination path that all 13 working opens use,
and its safety turns on telling "the camera behind our own NAT" apart from "our
own mapped address", which is precisely what `_is_self_peer_ip` exists to
prevent. Nominating our own address would have us answer our own check and fire
the trigger at ourselves. That cannot be validated without a camera.

**The smallest experiment, and it settles both open points in one run.** Make
the stall self-reporting: at the first-media wait expiry (`sdes_open.py:4689`)
log at WARNING how many candidates were nominated and their addresses, whether
any Binding Success was ever received, whether the trigger was sent, and the
source of every inbound probe. Two of those facts are computed today and thrown
away - `bridge: drop TURN self-loop STUN peer` (`:3858`) is DEBUG and the
`_bsrc == _hp_host` refusal (`:3785`) logs nothing at all. `_tutk_trigger_sent`
and `_br_stun_resp_count` are bridge-thread locals; publish them on `_bridge_fn`
the way `_sprop_done` already is. One validation run then answers which veto is
the blocker and whether a host-only answer is a transient or a state the camera
holds for hours.

**The kill, stated before the sweep and not triggered by any of the 17 opens:**
an open that logs `SDES: sent trigger` and delivers no media, or one that
delivers media without it.

#### 2026-08-09: the self-reporting run happened, and it settles the open question

The stall report shipped and has now fired seven times: 31289188625 (x2),
31292208608 (x2), 31298451465 (x1), 31348997269 (x2). Counted directly from the
device id on each report line, not from a tally - an earlier draft of this
section got that count and the device wrong together. Read them before anything
above.

**1. The veto is VERIFIED, not inferred.** Every one of the reports names
`vetoed-self-ip`. The section above says "INFERRED, not verified: which of the
two vetoes did it. Both are silent." They are not silent any more and the answer
is the first one.

**2. It is the A001513s, exactly as this item always said.** An earlier version
of this section claimed the stalling unit was the A001064 PTZ. That was wrong and
is retracted: it took the device id from a log line that merely sat nearby rather
than from the device list. Verified against the list, `b5284fc7` is **L2_F8A3**
and `338603b5` is **L2_181**, both `LK.IPC.A001513`; the A001064 is `12b144cb`
and appears in none of the reports. The lesson is one this project keeps
relearning - never source an identifier from something that only sits next to
it.

**3. There are TWO failure modes here, and now a third shape.** This is the
point that does contradict the item above, which argues one mechanism. They
differ in the field that matters, so treating them as one defect is what kept
this open:

    b5284fc7 = L2_F8A3 (A001513), 5 of the 7 - the unit this item describes:
      nominated=192.168.100.3:P1, 173.53.36.206:P2, 54.144.38.43:P3
      use-candidate=sent; binding-success=0; trigger=not-sent
      probes=54.144.38.43:5349 via 173.53.36.206:P1 -> vetoed-self-ip
             54.144.38.43:5349 via 173.53.36.206:P2 -> vetoed-self-ip
             54.144.38.43:5349 via 54.144.38.43:P3  -> known

    338603b5 = L2_181 (A001513), once:
      nominated=192.168.0.129:53246, 192.168.0.129:47093
      use-candidate=NOT-SENT; binding-success=0; trigger=not-sent
      probes=192.168.0.129:53246 -> learned; 192.168.0.129:47093 -> learned

    b5284fc7 = L2_F8A3, the sixth of its reports (run 31348997269):
      nominated=none; use-candidate=not-sent; binding-success=0; probes=none

The second is not an ICE-reachability problem at all. Both probe sources were
learned, both are ordinary addresses on this host's own LAN, and then nothing
was nominated. The third is emptier still - no candidates, no probes, nothing to
nominate at all - which points at signaling or the answer rather than at ICE.
Neither is anticipated above.

**What the first mode actually says.** `P1` is the port the camera advertises on
its own host candidate, and it reappears as `173.53.36.206:P1` in the
XOR-PEER-ADDRESS - so the camera's traffic reaches the TURN server from THIS
host's public IP. The camera is behind the same NAT we are. `_is_self_peer_ip`
compares the IP alone (`_ip == _public_ip`), so it refuses the camera's own
reflexive address as if it were ours.

That also names the discriminator the section above said we did not have. Our
own mapped address is one specific ip:PORT; a peer sharing our NAT has the same
IP and a different port, and P1/P2 are never our port. Comparing the pair rather
than the address is both the ICE-correct rule and the safe one.

**It is still not a fix, and here is what is missing.** Removing the veto would
let the address be learned; it would not make it reachable. `173.53.36.206:P2`
is already nominated in all four reports and returns nothing, which is what
hairpin NAT looks like from here. The reachable return path is a Send Indication
back through our own allocation to the camera's server-side address - the change
this item already declined to ship blind - and the veto is what blocks the input
to it, not the whole of it. So the order is: narrow the veto to ip:port, confirm
the verdict changes from `vetoed-self-ip` to `learned` on the next stall, and
only then decide about nomination.

**An honest note on reproducing it.** Five stalls in nine runs, and none in the
four most recent. Any fix here has to be validated against a failure that does
not appear on demand.

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
- **The thumbnail FAIL is the CI account, not the feature - closed
  2026-08-09.** Every fleet run reports `thumbnail=FAIL` on all six live
  cameras. The same call was then run from the OWNING account: six of seven
  cameras returned a CloudFront URL and the seventh, which is offline, returned
  None - so the cloud serves thumbnails and the shared-home member the runner
  signs in as simply gets an empty answer for them. The verdict is left as FAIL
  rather than reclassified, because the call did fail for the identity that made
  it; what changed is that it is now measured from both sides rather than
  explained from one.
- **The SDES snapshot budget was marginal - measured and fixed 2026-08-09.** An
  A001513 timed out at the 25 s budget in one of three runs. Rather than retune
  on that one sample, the probe was made to report elapsed time, and the next
  run gave the distribution: SDES 17.2 / 17.5 / 23.6 s, DTLS 2.8 / 3.0 / 2.9 s.
  So 25 s left the slowest camera 1.4 s of margin, which is what a budget set
  just above the then-known maximum always does - and it is the second time that
  happened here, the first being 10 s. Now 40 s, about 1.7x the slowest sample,
  with a test that asserts headroom rather than a number. DTLS is untouched at
  10 s: it is a different path and an order of magnitude faster.
- **Every press-to-talk on an SDES camera opens a second session.** Added
  2026-08-09. `async_speak` reuses `_stream_session` only when it is
  talk-capable, and none of the three loops that set it open with `talk=True`,
  so on SDES it never is. The live view keeps its session and talk opens another
  one alongside it. Nothing has measured what that costs in viewer slots, and
  the camera holds a slot for about 120 s after a session ends.
- **PTZ still reports success for bytes that left the socket, not for a camera
  that moved.** Added 2026-08-09. On SDES the command goes out through a closure
  that holds the datagram socket, so `sendto` can succeed after the session it
  belongs to has been torn down - the send is fire-and-forget by design and
  there is no acknowledgement to read on the A001064, whose firmware answers 848
  but not 802. The related two-way audio defect, where the same shape of
  reasoning produced a False success, is fixed in 1.0.0b2; this one is stated
  rather than fixed because there is no signal to condition it on. What changed
  is that the harness no longer asks a closed session, so a PASS at least means
  a live one.

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


### 6. SD-card recordings cannot be retrieved

Added 2026-08-09, found while verifying the features the gate never touched.

`IsSupportPlayback` is not a model capability - it says where a camera's
recordings live. Measured across the reference fleet:

    A001064, A001513 x2    IsSupportPlayback=1, no SD card   -> cloud
    A000088 x4             IsSupportPlayback=0, SDcardStatus=1 -> SD card

The library has exactly one retrieval path, `async_open_cloud_playback`, and
`async_get_cloud_recordings` to list for it. There is no SD-card equivalent. So
four of the seven cameras on the reference account record continuously to a card
and **nothing in this package can play any of it back**.

That is a feature gap rather than a defect - nothing regressed, it was never
built - but the product describes playback without qualifying it by storage, so
either the capability or the description has to change.

The bar: **implemented, or the docs say which cameras it applies to.** The
second is cheap and honest; the first needs the vendor's SD retrieval protocol.

#### 2026-08-09: that protocol has now been looked at, and it is reachable

Scoping only - nothing is implemented and nothing has been sent to a camera.
What changed is that "has not been looked at" is no longer true, so the estimate
below is from the decompiled client rather than from nothing.

**The command ids are already enumerated** in `docs/APP-PARITY-STATUS.md`, from
`AVIOCTRLDEFs`, and three of them are this feature:

    0x318 / 792   USER_IPCAM_LISTEVENT_REQ       list recordings in a time range
    0x4b5 / 1205  USER_IPCAM_HASLISTEVENT_REQ    does this range hold anything
    0x31a / 794   USER_IPCAM_RECORD_PLAYCONTROL  play / pause / seek / stop

**The request layout is readable.** `AVIOCTRLDEFs$SMsgAVIoctrlListEventReq`
carries `channel:I`, `startutctime:[B`, `endutctime:[B`, `event:B`, `status:B`,
with the two times built by `STimeDay.parseContent(year, month, day, wday, hour,
minute, second)` - and `STimeDay`'s own fields (`year:S` plus six bytes) fix that
at 8 bytes each.

**The transport already exists.** These are ordinary AVIO IOCtrl commands, so
they ride the same channel as PTZ and SPEAKERSTART, and `AvioRequestMixin`
already sends a command and waits for a specific response id on both transports.
Nothing new is needed to ask the question.

**The transport question is now answered, and the answer is favourable.** The
app's SD surface is `LdsTutkChannel` - `getSDRecordList`, `getSDTimeList`,
`sdRecordSeekPlay`, `sdRecordPause`, `sdRecordResume`, `sdRecordRelease` - and
that name is misleading. It is the app's camera-channel FACADE, not the native
TUTK stack: the same class carries `setIsDTLS`, and its `getSDRecordList` body
does one thing, `sendCtrl(0x318, ...)`. `sendCtrl` is the same generic control
send that carries PTZ `0x1001` and SPEAKERSTART `0x350`, both of which this
package already sends on both transports.

So SD listing rides the ordinary AVIO control channel that already works here.
It is NOT behind the `liveType=0` / TUTK path this project put out of scope for
1.0.0, which was the risk that made the estimate open-ended.

**One thing is genuinely unknown, and it is the work:** the RESPONSE layout.
There is no `...ListEventResp` class beside the request ones, so the reply has
to be read off the wire. Whether playback media then arrives on the existing
SRTP path or elsewhere follows from the same experiment - `RECORD_PLAYCONTROL`
is `sendCtrl` too, so the command goes out the way PTZ does, and what comes back
is the question.

**The cheap first step, if this is picked up:** send `HASLISTEVENT_REQ` to an
A000088 with a wide time range and log the reply bytes. It is one command on a
channel that already works, it changes nothing on the camera, and it either
returns a parseable answer - in which case the response format follows from a
handful of ranges - or it does not, which is equally decisive.

## Out of scope for 1.0.0

- **The go2rtc add-on.** A private experiment in moving off HACS distribution,
  never yet run with a real camera, and marked not for public release. 1.0.0
  covers the library and the Home Assistant integration; the add-on is a
  separate line of work and should not gate this one.
