# Documents in this directory

Which of these describe the library as it is, and which record how it got here.
Documents that only recorded superseded work have been removed; they remain in
git history, and the CHANGELOG still names them where it cites them.

## Current - read these

| File | What it is |
| --- | --- |
| [CAMERAS.md](CAMERAS.md) | The supported-camera table (one for both projects), transport details per model, and the behaviours that look like faults and are not. |
| [ENVIRONMENT.md](ENVIRONMENT.md) | Every `AIDOT_*` variable that is not on the README's short list; none is part of the 1.0 API. |
| [API-STABILITY.md](API-STABILITY.md) | What the library promises across releases, and what it does not. |
| [TESTING.md](TESTING.md) | The test tiers (unit, e2e against fakes, live against real cameras) and how CI runs them. |
| [CI-RUNNER.md](CI-RUNNER.md) | The self-hosted runner that validates each release against real cameras. |
| [UPSTREAM.md](UPSTREAM.md) | How a new `python-aidot` release is taken in, and the two record shapes the code supports. |
| [ROAD-TO-1.0.md](ROAD-TO-1.0.md) | What 1.0.0 asserts, the one open item (the two-week soak) and how its clock is set, and the closed items with where their evidence lives. |

## Design notes - current behaviour, written when it was decided

| File | Decision it records |
| --- | --- |
| [DESIGN-direct-publish.md](DESIGN-direct-publish.md) | Publishing decrypted media straight into go2rtc, without an ffmpeg process in the live path (on by default since 1.0.0rc44; `AIDOT_DIRECT_PUBLISH=0` restores the ffmpeg path). |

## Background - dated, kept for context

| File | Why it is kept |
| --- | --- |
| [APP-PARITY-STATUS.md](APP-PARITY-STATUS.md) | Where the library stood against the official app on 2026-08-23, and what it deliberately does not copy. |
| [lan-control-plan.md](lan-control-plan.md) | The plan the LAN control client was built from (July 2026). |
| [PLAN-bridge-container.md](PLAN-bridge-container.md) | A standalone bridge container, considered in September 2026 and not built. |
