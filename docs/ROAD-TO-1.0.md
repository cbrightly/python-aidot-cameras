# What 1.0.0 is waiting on

Written 2026-08-07. The point of this file is to make the bar checkable, so that
"are we ready" stops being a matter of opinion.

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

## Open

### 1. The discovery rate is still high

Fourteen releases in the four days to 2026-08-07, and the finds were structural
rather than cosmetic: inbound AVIO on SDES was being decoded, logged and dropped
so the whole response path was dead on that transport; a `-50002` backoff waited
300 s for a camera measured to clear in 8.

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

**This is one defect wearing three names.** It is also the `camera.kitchen`
no-media case, and the subject of the relay-only investigation: the same unit
streams fine from other hosts on the same LAN, and the one difference found is
that it sits on a different SSID from the Home Assistant host while we advertise
a host candidate it cannot reach. The loop was stopped in 0.17.1 - a battery
camera's keepalive now gives up after five futile sessions - but stopping the
loop is not fixing the cause, and the give-up explicitly does not claim to.

### 4. Coverage holes

- The `liveType=0` / TUTK path is researched but deferred and untested - see
  [DEFERRED_FEATURES.md](DEFERRED_FEATURES.md).
- `async_set_resolution`'s acknowledgement read is now exercised live: a
  2026-08-07 sweep sent all six `AVIOCTRL_QUALITY` values to an A001064 over
  twelve sessions and read every reply, acks landing in 0.01-0.19 s. The
  remaining gap is the DTLS models, where the read has unit tests only.

The bar: **tested, or explicitly out of scope for 1.0** and said so here.

## Out of scope for 1.0.0

- **The go2rtc add-on.** A private experiment in moving off HACS distribution,
  never yet run with a real camera, and marked not for public release. 1.0.0
  covers the library and the Home Assistant integration; the add-on is a
  separate line of work and should not gate this one.
