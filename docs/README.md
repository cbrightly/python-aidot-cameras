# Documents in this directory

Which of these describe the library as it is, and which record how it got here.
Nothing is deleted: the historical ones are cited from the CHANGELOG and from
code comments, and they explain decisions that are still in force.

## Current - read these

| File | What it is |
| --- | --- |
| [CAMERAS.md](CAMERAS.md) | The supported-camera table (one for both projects), transport details per model, and the behaviours that look like faults and are not. |
| [ENVIRONMENT.md](ENVIRONMENT.md) | Every `AIDOT_*` variable that is not on the README's short list; none is part of the 1.0 API. |
| [API-STABILITY.md](API-STABILITY.md) | What the library promises across releases, and what it does not. |
| [TESTING.md](TESTING.md) | The test tiers (unit, e2e against fakes, live against real cameras) and how CI runs them. |
| [CI-RUNNER.md](CI-RUNNER.md) | The self-hosted runner that validates each release against real cameras. |
| [UPSTREAM.md](UPSTREAM.md) | How a new `python-aidot` release is taken in, and the two record shapes the code supports. |
| [ROAD-TO-1.0.md](ROAD-TO-1.0.md) | What 1.0.0 is waiting on, item by item, and the soak clock it runs on. |
| [DEFERRED_FEATURES.md](DEFERRED_FEATURES.md) | Things the cameras can do that the library deliberately does not expose yet, with the reason for each. |

## Design notes - current behaviour, written when it was decided

| File | Decision it records |
| --- | --- |
| [DESIGN-direct-publish.md](DESIGN-direct-publish.md) | Publishing decrypted media straight into go2rtc, without an ffmpeg process in the live path (on by default since 1.0.0rc44; `AIDOT_DIRECT_PUBLISH=0` restores the ffmpeg path). |
| [DESIGN-session-continuity.md](DESIGN-session-continuity.md) | Surviving a camera that ends its own streaming session. |

## Historical - kept for the record

| File | Why it is kept |
| --- | --- |
| [1.0.0-READINESS.md](1.0.0-READINESS.md) | The feature-by-feature audit of 2026-08-24 that set the 1.0 bar. Status has moved on; see ROAD-TO-1.0.md. |
| [APP-PARITY-STATUS.md](APP-PARITY-STATUS.md) | Where the library stood against the official app on 2026-08-23, and what it deliberately does not copy. |
| [2026-06-25-coldstart-forward-port-audit.md](2026-06-25-coldstart-forward-port-audit.md) | The audit of the cold-start code brought forward onto v0.9.2. |
| [lan-control-plan.md](lan-control-plan.md) | The plan the LAN control client was built from (July 2026). |
| [PLAN-bridge-container.md](PLAN-bridge-container.md) | A standalone bridge container, considered in September 2026 and not built. |
