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
camera. Nine hypotheses have died here; the remaining path is a capture of the
app's own session setup against that model specifically.

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

### 4. Coverage holes

- The `liveType=0` / TUTK path is researched but deferred and untested - see
  [DEFERRED_FEATURES.md](DEFERRED_FEATURES.md).
- `async_set_resolution`'s acknowledgement read has unit tests but has never been
  exercised against a live camera.

The bar: **tested, or explicitly out of scope for 1.0** and said so here.

## Out of scope for 1.0.0

- **The go2rtc add-on.** A private experiment in moving off HACS distribution,
  never yet run with a real camera, and marked not for public release. 1.0.0
  covers the library and the Home Assistant integration; the add-on is a
  separate line of work and should not gate this one.
