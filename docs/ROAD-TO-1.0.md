# What 1.0.0 is waiting on

The point of this file is to make the 1.0.0 bar checkable, so that "are we
ready" is a matter of record rather than opinion. It states the bar and where
each item stands. The dated working record behind it (per-release tables,
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
- the release that becomes 1.0.0 has run in ordinary use for two weeks without
  a streaming-breaking release replacing it.

Where things stand: the library is at `1.0.0rc61` on PyPI. `1.0.0rc58` is
yanked: in it a successful DTLS open returned `None` instead of its session;
`rc59` fixed that and `rc60` adds a test that holds every phase of both opens
to exporting what later phases read. The Home Assistant integration `2.37.17`
requires `>=1.0.0rc61`.

## Open

### 1. Two weeks with no streaming-breaking release

This is the only open item. The cadence of finds is the evidence: 1.0.0 is
cut once a release has run for two weeks of ordinary use on the reference
installation and nothing streaming-breaking has had to ship in that time.

Two weeks is the bar as originally set. Some later entries in this file said
ten days; that was an inconsistency, not a change of bar.

**Day zero.** The original reference installation has been off the network
since 2026-10-13, so on 2026-10-22 the reference moved to a Home Assistant
Container (2026.10.2) on a Raspberry Pi 4 on the cameras' network, running
`1.0.0rc61` and integration `2.37.17` with their released files (checked by
hash against the wheel and the release). Those days had installs and restarts
(2026-10-23 added the viewing schedule below; on 2026-10-24 the go2rtc that
SDES cameras publish into was found stopped after a host reboot and was made a
boot-persistent service), so they are provocation; the first counting day is
**2026-10-25** and the target is **2026-11-08**. The installed files must match the released ones: a soak
measured on hot-patched files measures nothing, so that check is part of
dating day zero. Live validation during the soak runs on a separate runner,
not on the reference host.

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

Each release's CHANGELOG entry is where its classification goes, with the
reason. When a release resets the clock, the new day zero is the first
ordinary day the reference installation runs it.

**The cut.** 1.0.0 is cut from the release the soak ran on. Any further rc
before then should be a fix the soak found, not planned work. At the cut the
README's `--pre` install instructions change, because a bare `pip install`
will then resolve the camera line.

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
   ordinary WebRTC AVIO channel; no P2P stack is needed. Evidence: CHANGELOG
   `1.0.0b5` (`async_get_sd_recordings`); [CAMERAS.md](CAMERAS.md);
   `scripts/sd_event_probe.py`.
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
  over WebRTC, and every feature looked at so far, SD event listing included,
  is reachable there. Using the vendor's native P2P stack would mean bundling
  proprietary ARM binaries in a public package or reimplementing the protocol
  from them, and `p2pId` is unset on every reference camera. Reopen only on
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
