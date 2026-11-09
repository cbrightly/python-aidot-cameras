# 1.0.0, and how releases work after it

This file set the 1.0.0 bar and recorded where each item stood, so that "are
we ready" was a matter of record rather than opinion. 1.0.0 is cut (2026-11-09,
from the code released as `1.0.0rc72`); the first section below is now the
release rules that replace the bar, and the rest is the record. The dated working record behind it (per-release tables,
measurements, and every re-dating of the soak clock) is in this file's git
history (`git log -p -- docs/ROAD-TO-1.0.md`) and in
[CHANGELOG.md](../CHANGELOG.md).

## What 1.0.0 asserts

**Not** that there are no known issues. It asserts that the system is
understood and its shape has settled:

- the public surface is written down and the reference consumer stays inside
  it ([API-STABILITY.md](API-STABILITY.md));
- every release passes a gate that streams real cameras before it reaches PyPI
  ([TESTING.md](TESTING.md), [CI-RUNNER.md](CI-RUNNER.md));
- the release that becomes 1.0.0 has run in ordinary use on the reference
  installation, with the scheduled views below getting video.

## Release rules from 1.0.0

Decided 2026-11-09, replacing the two-week bar below. The reasons: the bar was
reset five times in three weeks (every real fix restarts it, so it measured
how long fixes could be put off); the project has a handful of users, for whom
a fix held back costs more than a fix that needs a follow-up; and pre-release
numbering had stopped carrying information (72 release candidates, and the
integration's stable channel sat on 2.37.18 while its users' fresh installs
pulled whichever library pre-release was newest, see below).

- **Plain semantic versions, no pre-release suffixes.** A fix is a patch
  (1.0.1); a feature or a refactor is a minor (1.1.0); only a change that
  breaks a caller of the public surface ([API-STABILITY.md](API-STABILITY.md))
  is a major. The integration numbers itself the same way.
- **The gate stays.** Every library release passes the live gate
  ([CI-RUNNER.md](CI-RUNNER.md)) before PyPI, and each camera's report is
  diffed against the previous run's; the published wheel is checked against
  its tag file for file.
- **The integration is proved on the reference installation before it is
  published**, not after: the library release goes to PyPI first (few install
  it directly), the integration is deployed to the box from its development
  main with a backup taken, Home Assistant is restarted, the log is read and
  the installed files are checked by hash, and only then is the public release
  cut. A change to a stream path (an open, a serve loop, the publish or the
  mux) stays on the box through two of the scheduled view rounds before the
  cut; anything else needs one.
- **The integration pins the library to a minor range**, `>=X.Y.Z,<X.(Y+1)`.
  Until 2.38.0 its requirement was a pre-release floor alone (`>=1.0.0rc62`),
  which under PEP 440 lets pip install any newer pre-release, so a fresh
  install of a year-old stable release would have been given whichever library
  beta was newest. A library minor release therefore always arrives with an
  integration release that moves the range.
- **Fewer releases.** Work is iterated on branches against the gate and
  released when there is something users should have, not per commit.
- **Stability is measured, not counted.** The soak watcher's share of
  scheduled views that got video, per day and since each release, is the
  number to read ([TESTING.md](TESTING.md)); a release does not reset anything
  but its own column. What is kept from the bar: a backup before every deploy,
  review before every commit, and a live check of smooth video and sound on the
  affected models before anything is published.

## The record to 1.0.0

Where things stood at the cut: the library was at `1.0.0rc72` on PyPI. `1.0.0rc58` is
yanked: in it a successful DTLS open returned `None` instead of its session;
`rc59` fixed that and `rc60` adds a test that holds every phase of both opens
to exporting what later phases read. `rc62` fixes what the first day of
scheduled viewing found (see below). `rc63` adds playing recordings off a
camera's SD card and numbers every SESSION_MODE request from the client's
counter, which changes every SDES open: it resets the clock below once the
reference installation runs it. `rc64` fixes SD playback only (a recording's
sound written twice on a host that reaches the camera by two routes, and the
SDES-to-DTLS fallback of a playback open), and `rc65` plays a recording made at
a lower frame rate at the right speed, and `rc66` keeps a DTLS playback's
damaged frames and opens its file off the event loop, and `rc67` asks a DTLS
playback's camera to resend what it lost and skips a repeated last frame, and
`rc68` reads every page of a card listing instead of the first 255 records,
and `rc69` lists in windows an A000088 fills in and waits for a slow page;
none of them resets the clock. `rc70` delivers an SDES camera's control-channel
data in order, which changes every SDES session, so it reset the clock.
`rc71` backs off a DTLS camera whose sessions keep failing fast, keeps an SDES
camera's sound when its audio trails its video, ends an SD playback on the
camera's PLAY_END and paces DTLS talk on an absolute schedule; it changes the
serve loop on every A000088, so it resets the clock again once the reference
installation runs it (see below). The Home Assistant integration `2.37.18`
requires `>=1.0.0rc62`; the integration release that plays card recordings
began with 2.38.0b1 (`>=1.0.0rc66`), and 2.38.0b4 requires `>=1.0.0rc70`.

## Open

Nothing. Item 1 below was the last open item; it closed by decision rather than
by the count reaching two weeks (see the release rules above).

### 1. Two weeks with no streaming-breaking release (closed 2026-11-09)

The bar as set: 1.0.0 is cut once a release has run for two weeks of ordinary
use on the reference installation and nothing streaming-breaking has had to
ship in that time. The count was reset by `rc62`, `rc63`, `rc70`, `rc71` and
`rc72` and never reached two weeks; the longest run was eleven days (`rc62`,
2026-10-26 to 2026-11-06, ended by the `rc63` live test and the box going off
the network). 1.0.0 is the `rc72` code, which had passed the gate and run on
the box for a day; what two more weeks would have added is the number the
soak watcher reports anyway, so the two weeks moved from a bar to a reading.

Two weeks was the bar as originally set. Some later entries in this file said
ten days; that was an inconsistency, not a change of bar.

**Day zero.** The original reference installation was off the network from
2026-10-13 to about 2026-10-23, and a Home Assistant Container on a Raspberry
Pi 4 stood in for it from 2026-10-22. When the original came back it ran the
integration too, so two installations drove the same cameras and lights; the
stand-in's AiDot integration was disabled on 2026-10-25 and the original is the
reference again. Days with installs, restarts or two installations are
provocation, not soak.

**Day zero is 2026-10-26; the target is 2026-11-09.** The reference
installation (Home Assistant 2026.10.1 on HA OS) runs `1.0.0rc62` and
integration `2.37.18` since 2026-10-25, with their released files checked by
hash against the wheel and the release; the stand-in's AiDot integration is
disabled, so nothing else drives the devices. 2026-10-25 had installs and
restarts, so it is provocation. The count had been paused since 2026-10-24
for what the first day of scheduled viewing found: battery cameras mostly
failed to open in Home Assistant's own player, which `rc62` fixes. The installed files must match the
released ones: a soak measured on hot-patched files measures nothing, so that
check is part of dating day zero. Live validation during the soak runs on a separate runner,
not on the reference host.

**The `rc62` count has ended.** On 2026-10-28 the reference installation
(by then on Home Assistant 2026.10.4) was given `rc63` and the unreleased
card-playback integration branch for a live test, which is provocation and
puts a clock-resetting release on the box; about two minutes later the host
shut itself down and was off the network for some days. Day zero is re-dated
once the box is back and runs released `rc63` (or later) with a released
integration, checked by hash as above.

**Day zero is 2026-11-10; there is no target.** The soak watcher keeps
counting from this day for its reading, and the count no longer gates a
release (see the rules above). The count that began on 2026-11-07 under `1.0.0rc71` was reset by `rc72`, which changes how the
DTLS serve loop paces a session the camera closes itself on every A000088.
On 2026-11-09 the box was given `1.0.0rc72` and integration 2.38.0b6: the
integration's 35 installed files match the public `v2.38.0b6` release by
hash, and the library installed is `1.0.0rc72`, whose wheel on PyPI matches
its tag file for file. 2026-11-09 had that install and a restart, so it is
provocation; 2026-11-10 is the first ordinary day.

The count before that, kept for the record: day zero 2026-11-07 under `rc71`
(target 2026-11-21). The count that began on
2026-11-03 under `1.0.0rc70` and integration 2.38.0b4 was reset by `rc71`,
which changes the DTLS serve loop on every A000088. On 2026-11-06 the box
was given `1.0.0rc71` and integration 2.38.0b5 (Home Assistant 2026.10.4 on
HA OS): the integration's 35 installed files match the public `v2.38.0b5`
release by hash, and the library installed is `1.0.0rc71`, whose wheel on
PyPI matches its tag file for file. 2026-11-06 had that install and a
restart, so it is provocation; 2026-11-07 is the first ordinary day.

The earlier count, kept for the record: day zero 2026-11-03 under `rc70`.
Once the box was back on the network it was given the card-playback releases
as they shipped (integration 2.38.0b1 to 2.38.0b3 on library `rc66` to
`rc69`), with live tests between them, so every day up to and including
2026-11-02 was provocation; 2026-11-03 was the first ordinary day of that
count.

**2026-11-04 counts.** For half an hour from 09:13 that morning the bedroom
A000088 misbehaved on its own: its sessions died within seconds, then opens
stopped connecting and were refused as busy, and at 09:46 every one of its
Home Assistant entities went unavailable for ten minutes - the camera off the
cloud. No other camera was affected, and that camera had shown nothing of the
kind the day before. The watcher reported the refusals as a streaming finding,
which it does for any refusal; the reading is a camera fault, not the
library, so the day counts. What the library did make worse - reopening every
15 s and retrying straight into the busy refusal - is fixed on a branch and
held until the soak ends, because it changes the serve loop and that would
reset the clock.

**What counts as soak.** Ordinary use with nobody driving the box. Ordinary
use includes one fixed viewing schedule, set before the first counting day so
that it cannot be tuned to the result: every camera is viewed for 60 s through
Home Assistant's own stream path (`camera.record`), one camera after another,
at 09:05, 14:05 and 20:05 local time. Without it a battery camera, which
streams only while viewed, would never open during the soak. Viewing a camera
by hand is ordinary use too. A day with
releases being installed, Home Assistant restarts, forced opens or probes is
provocation, not soak, and does not count towards the two weeks. Days the box
is off the network count as neither, and push the target out.

**What resets the clock.** Any streaming-breaking release, meaning a fix to
broken core behaviour on a default path. Since `1.0.0rc44` that includes
direct publish, the AAC track, the in-sync MPEG-TS and the H.264 pin, all of
which are on by default; a path that is on by default is core however it got
there. A rewrite of the opens or the serve loops also resets it, because the
soak has to run on the program that ships.

**What does not reset it.** A new opt-in feature; logging, log levels and
observability lines; packaging and dependency floors; the light and LAN-login
control path; diagnostics and repairs that do not touch media.

**What is tallied, not counted.** A battery A001513 that answers but never
delivers first media (`SDES first media never arrived`, `nominated=none` or no
binding success, abandoned after 75 s) is a known class, shipped on purpose
when the item that investigated it closed on 2026-08-11: one of its modes was
found and fixed, the others look camera-side, a stalled session is bounded and
retried, and the effect is a slow first frame rather than a dead camera. The
soak watcher therefore tallies these stalls per camera each day instead of
treating each as a reset. It is not a free pass. The class reopens, and the
stall then counts against the soak, on a stall that does not clear on retry,
or on any of these shapes repeating on a mains camera; the watcher raises a
stall on the mains PTZ, the A000088s or any camera it does not list at once.

**What is measured, not counted.** Whether each scheduled view got video is
tracked as its own figure. A stall that clears on retry is tallied and not
counted, but the stream it delays can come up after the 60 s view is over:
on 2026-11-03 the kitchen A001513's 20:05 view stalled, cleared on its first
retry and still produced no video in the view. The soak watcher records every
view as `ok` (at least 50 s of video), `short` (the first frame came late) or
`missed` (no video), and reports the share that got video each day and since
day zero. It does not reset the clock; it is the number to read when deciding
whether a camera's late opens are a slow first frame or a view nobody saw.

Each release's CHANGELOG entry is where its classification goes, with the
reason. When a release resets the clock, the new day zero is the first
ordinary day the reference installation runs it.

**The cut.** 1.0.0 was cut on 2026-11-09 from the `rc72` code, unchanged
but for the version. With it the README's `--pre` install instructions went,
because a bare `pip install` now resolves the camera line.

## Closed

Each item keeps its number because code comments and tests refer to items by
number. The full record of each is in this file's git history.

2. **The bitrate gap with the vendor app** - closed 2026-08-23 as "does not
   work here". The mid-session SD command is byte-identical to the app's, is
   acked, and has no measurable effect on our sessions. An H.265 session costs
   about half an H.264 one, but reordering the offer did not make the camera
   choose it. Evidence: [APP-PARITY-STATUS.md](APP-PARITY-STATUS.md);
   CHANGELOG `1.0.0b27`.
3. **A camera stuck in a failing retry loop** - closed 2026-08-11. A battery
   camera's keepalive gives up after five futile sessions
   (`AIDOT_FUTILE_KEEPALIVE_LIMIT`), and stalled first-media attempts are
   abandoned to the retry early. Evidence: CHANGELOG `0.17.1`, `1.0.0rc21`,
   `1.0.0rc45`; [ENVIRONMENT.md](ENVIRONMENT.md).
4. **Coverage holes** - closed 2026-08-08. The `async_set_resolution` ack read
   is confirmed live on DTLS and SDES cameras and joined to a real session by
   `tests/test_dtls_resolution_ack.py`; the `liveType=0` / TUTK path is out of
   scope (below). Evidence: CHANGELOG `0.16.0`;
   [APP-PARITY-STATUS.md](APP-PARITY-STATUS.md).
5. **Keeping secrets out of logs** - closed 2026-08-11.
   `tests/test_no_key_material_in_logs.py` walks the whole package, and a
   `RedactSdpFilter` masks key material in any SDP that reaches a log.
   Evidence: CHANGELOG `1.0.0rc53`, `1.0.0rc54`.
6. **SD-card recordings** - closed 2026-08-11. SD event listing works over the
   ordinary WebRTC AVIO channel; no P2P stack is needed. Playing a recording
   followed in `1.0.0rc63` (`async_open_sd_playback`), on the same channel.
   Evidence: CHANGELOG `1.0.0b5` (`async_get_sd_recordings`) and `1.0.0rc63`;
   [CAMERAS.md](CAMERAS.md); `scripts/sd_event_probe.py`,
   `scripts/sd_playback_validate.py`.
7. **The quality levers** - closed 2026-09-04. The mid-session `sd` and `hd`
   commands are delivered and acked and do nothing measurable (35 sessions per
   arm against a control arm). The slow levers are out of scope (below).
   Evidence: `scripts/live_validate.py --quality-arms`.

Also met: the reference consumer reaches into zero private attributes of this
library ([API-STABILITY.md](API-STABILITY.md)), and local (LAN) control works
for the owning account
([README](../README.md#local-lan-control-and-account-ownership)).

## Out of scope for 1.0.0

- **The `liveType=0` / TUTK P2P transport.** Every supported camera streams
  over WebRTC, and every feature looked at so far, SD event listing and
  playback included, is reachable there. Using the vendor's native P2P stack
  would mean bundling proprietary ARM binaries in a public package or
  reimplementing the protocol from them, and `p2pId` is unset on every reference camera. Reopen only on
  evidence that a feature is unreachable on the WebRTC path.

- **The slow quality levers: Auto, adaptive bitrate, the encoder ramp, TMMBR /
  REMB response.** Moved here 2026-09-04 when item 7 closed. The TMMBR and
  bandwidth knobs went in `rc44`; `AIDOT_REMB_TARGET_BPS` is the one
  instrument left, and a future measurement needs new ones. The mid-session
  commands a viewer triggers were measured and do nothing above ~11 percent;
  these four are a different shape, acting over minutes rather than at a point,
  and no instrument for them exists yet - two 12-second windows around a command
  is not it. 1.0.0 asserts that the system is understood and its shape has
  settled, not that every lever has been measured. Whether these do anything is
  a real question and it stays open as work; it is not a 1.0.0 question.

- **The go2rtc add-on.** A private experiment in moving off HACS distribution,
  never yet run with a real camera, and marked not for public release. 1.0.0
  covers the library and the Home Assistant integration; the add-on is a
  separate line of work and should not gate this one.
