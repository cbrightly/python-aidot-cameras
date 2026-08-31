# Live validation: the self-hosted runner

Releases of this library have repeatedly broken live streaming on some or all
camera models - 16 releases of 0.12.x in four days, almost all "streaming is
broken" hotfixes. Unit tests cannot catch that class of failure: the cloud
accepts the call, signaling looks healthy, and only the media tells the truth.

So a release cannot reach PyPI until the code in it has streamed **real
cameras**. This document is the setup and the operating procedure for that.

## How the gate fits together

| Trigger | Workflow | Where it runs | Blocks a release? |
| --- | --- | --- | --- |
| every push / PR | `ci.yml` (`test`, `e2e`, ...) | GitHub-hosted | via `publish.yml`'s `test` job |
| release published, manual dispatch | `live-validate.yml` **in the private repo** `python-aidot-cameras-ci` | **self-hosted, on the camera LAN** | yes, via a commit status |
| release published | `publish.yml` -> `live-gate` -> `publish` | GitHub-hosted | **this is the gate** |

`live-gate` does not touch the cameras. It waits (up to 40 min) for a **commit
status** named `live-validation` to appear on the release commit with state
`success`. Anything else - no status at all, a failure, a status on a different
sha, a runner that is switched off - blocks the upload.

### Why the cameras run in a separate private repo

Actions logs on a **public** repository are world-readable, and a live run prints
the camera inventory - names, models, device ids - and uploads a report artifact
containing them. Camera names in this fleet are room names, so they describe the
layout of a house. Six such runs had to be deleted before this moved.

The library stays public; only the runs moved to `python-aidot-cameras-ci`.

```
PRIVATE python-aidot-cameras-ci          PUBLIC python-aidot-cameras
  live-validate.yml
    checks out the public library at
    the sha under test, runs its
    scripts/live_validate.py on the
    LAN runner, logs stay private
        │
        └─ posts commit status ─────────► "live-validation" on that sha
           (LIB_STATUS_TOKEN lives here)        │
                                          live-gate reads it with its own
                                          GITHUB_TOKEN - no secret needed
```

**The direction is deliberate.** Having the public repo poll the private one
would need a token that can read a private repo, stored as a secret in a public
repo. Reporting inward means the public side needs no credential at all, and the
only token in play is scoped to commit statuses on an already-public repo and
lives in the private repo. If it leaked, the worst outcome is a spurious status.

A **filtered** run (`models`/`name`) deliberately posts a *different* context -
`live-validation (partial: ...)` - so validating a subset of the fleet can never
satisfy the gate.

**It fails closed on purpose.** A runner that is offline means an unvalidated
release, which is the exact situation this exists to prevent.

## What gets validated

`scripts/live_validate.py` streams each camera and asks one question: *did
real media arrive?* The per-transport answer differs and both are handled -
DTLS decodes in-process (`on_frame`), while SDES hands media to ffmpeg and
never calls `on_frame`, so its signal is `SdesSession.media_stats()` plus
recorded bytes.

Policy, all of it learned on hardware (see `docs/CAMERAS.md`):

- **Required models gate the release**: A000088, A001513, A001064. Each needs
  **at least one camera that streamed** - not every camera of that model. A
  release breaks a transport/firmware path, and one healthy camera proves that
  path still works, while individual cameras fail for reasons the code under
  test cannot cause (flat battery, unit powered off, an L2 too deeply asleep to
  wake inside the window). Gating on those would train everyone to ignore the
  gate, which is worse than a slightly narrower one.
  A model with **zero** passing cameras still FAILS, and a model **absent** from
  the account still FAILS - validating a subset of the fleet and calling it
  green is how a model-specific break ships.
  Failures that did not gate are printed under "did not gate" and recorded in
  `tolerated_failures`, with per-model counts in `model_coverage`. **Watch
  them**: they are the early warning that a model is degrading while one healthy
  camera masks it. Two cameras of a model failing is a gate failure, so the
  masking only lasts as long as one still works.
- **Advisory models never gate**: A001108, A001360 (recognized in code, never
  validated on the reference account), and any unknown `LK.IPC.*`.
- **DTLS gets 3 attempts, SDES 2.** An A000088's per-attempt connect is
  probabilistic (~75-87%), so one miss is not a release blocker. The report
  records how many attempts each camera needed - a drifting attempt count is
  an early warning even when the run is green.
- **One camera at a time, and each camera left alone ~3 min after its own
  session.** Two different constraints, deliberately kept apart. Opens are
  strictly sequential because cloud signaling contention is *account-wide* -
  that is the failure mode behind the historic concurrent-cold-open bug, and
  nothing in the harness overlaps opens. The ~3 minute cooldown is a different
  thing: a camera holds its viewer *slot* for ~120 s, so it is owed by the
  camera that just streamed and waited out only when that same camera is
  reopened (between attempts, mostly). The next camera in the fleet is a
  different device whose slot was never taken, so it does not wait.
- **BUSY is not a pass.** A terminal ack (-50002/-50015) means something else
  is watching - most likely your Home Assistant. It is reported distinctly
  from a media failure, and it still fails the gate. It is also reported
  *quickly* (~2 s): the SDES path used to miss the refusal until its
  first-media wait expired and then spend a pointless DTLS-fallback attempt on
  top, so a contended camera cost most of a minute per attempt.

A full-fleet run is expected to take under 10 minutes. It used to take 15-25,
most of it cooldown waited between *different* cameras that owed nothing; the
per-camera cooldown above removed that. The remaining wall clock is handshakes,
the hold on each camera, and the retry cooldowns on cameras that needed a
second attempt. This expectation has not yet been confirmed by a measured run.

## Runner setup

1. **Pick a host on the camera LAN** that is always on - the HA box, a NAS, a
   Pi. It needs Python 3.11+, `ffmpeg`, and `git`.

2. **Register it against the PRIVATE repo** `python-aidot-cameras-ci` -
   Settings -> Actions -> Runners -> New self-hosted runner - and give it the
   labels:

   ```
   self-hosted, aidot-lan
   ```

   The private repo's `live-validate.yml` targets exactly those labels. A
   runner belongs to one repository, so it must not also be registered against
   the public library repo.

3. **Run it as a dedicated unprivileged user**, not as root and not as the
   user that owns your Home Assistant install. Do not attach this runner to
   any other repository.

4. **Lock down what can reach it.** In Settings -> Actions -> General:
   - "Fork pull request workflows from outside collaborators" -> *Require
     approval for all external collaborators*.
   - Confirm self-hosted runners are not usable by fork PRs.

   The private `live-validate.yml` triggers only on `workflow_dispatch`, which a
   fork cannot fire, and the repo is private in any case - the settings above are
   the belt to those braces.

5. **Create the `live-lan` environment in the private repo** (Settings ->
   Environments) and put
   the credentials there, *not* in repo-wide secrets:
   - `AIDOT_USERNAME`, `AIDOT_PASSWORD`
   - optionally the `AIDOT_COUNTRY` variable (defaults to `US`)

   Environment scoping means only jobs that declare `environment: live-lan`
   can read them. Add a required reviewer here if you want a human in the loop
   on every live run.

6. **Smoke-test before wiring the gate**: run the workflow via
   *Run workflow* (dispatch) and confirm a green run plus a `live-report.json`
   artifact listing every camera.

## Stop Home Assistant's AiDot integration before every live run

**Required since 1.0.0b34. A live run while Home Assistant is streaming will
fail, and it will look like a code regression.**

A camera holds its viewer slot for ~120 s and serves a limited number of
concurrent viewers. If Home Assistant is streaming the fleet, CI's session
handshakes normally and then receives no media - `NO_MEDIA handshake=1.9s
frames=0 bytes=0` - and the slot that attempt consumed makes the next two
attempts fail signalling outright with `no webrtcResp received within 45.0s`.
Three A000088 cameras failed exactly this way on 2026-08-31 while the same build
was streaming all three under Home Assistant at that moment.

This is contention for the CAMERA, not for the account. It is unaffected by CI
using a second account with the house shared to it, because the limit belongs to
the device.

**Why this got worse.** Before 1.0.0b34 the A001064 tore its own session down
every 80.2 s and the rest of the fleet churned with it, so Home Assistant was
constantly between sessions and CI could slip into the gaps. That bug is fixed:
sessions now persist for hours. Home Assistant no longer lets go, so the overlap
is permanent rather than occasional.

Disable the integration, wait for the slots to lapse, run, then re-enable:

    # Settings -> Devices & Services -> AiDot -> three dots -> Disable
    # or, over the websocket API:
    #   {"type": "config_entries/disable", "entry_id": "<id>", "disabled_by": "user"}
    # confirm it let go - both should be zero:
    docker exec homeassistant sh -c 'ps -o args | grep -c "[f]fmpeg.*aidot"'
    docker logs homeassistant --since 60s 2>&1 | grep -c "sdes-turn-prealloc"
    # wait ~150 s for viewer slots to lapse, then dispatch the run
    # re-enable afterwards with disabled_by: null

### Never run two live runs back to back

**A failed run wedges the fleet, and the next run inherits it.** Every attempt
mints a fresh peer id, and a fresh peer id is a NEW camera-side session; the
camera releases old ones only slowly - **~3-4 minutes measured**. A full-fleet
run that fails burns up to three attempts on each of seven cameras, so it can
leave twenty undrained sessions behind it.

Measured 2026-08-31. A contended run failed at 17:24 having made ~20 attempts. A
second run was dispatched at 17:32 - eight minutes later, with the integration
disabled and a 150 s wait for viewer slots. It overran 40 minutes and was
cancelled. The 150 s was calibrated for the ~120 s viewer slot and is irrelevant
to the per-session drain, which is an order of magnitude longer in aggregate.

The two failure shapes in the first run's stall reports tell them apart:

    probes=none          binding-success=0  trigger=not-sent    camera never reached us
    binding-success=2    trigger=sent(unacked)  inbound-media=0 ICE fine, camera wedged

**Before re-running after any failure, leave the fleet alone for 15 minutes**
with the integration disabled. Confirm the cameras are healthy first by
re-enabling Home Assistant briefly and watching them stream, then disable it
again and wait out the drain. A run started into a wedged fleet tells you
nothing and wedges it further.

Note also that a cancelled run's log is DISCARDED by GitHub, so a run you stop
early costs you the per-camera evidence as well as the time. Let a live run
finish.

Leaving it disabled costs live view, motion notifications and recordings, so
re-enable it as soon as the run completes - pass or fail.

The durable fix is to give the runner its own cameras, so a release gate never
depends on someone's house being quiet. Until then this step is manual and
mandatory.

## Which account should CI use?

**This needs deciding before the gate goes live, and it needs an experiment -
it is not safely knowable from the code.**

The problem: logging in **rotates the account's MQTT password**, and the
broker allows **one connection per account**. A CI login on your main account
can therefore kick your Home Assistant's camera signaling off the broker (the
`rc=134` failure mode), and HA reconnecting can kick CI. Camera signaling
dies; snapshots keep working, so it looks like a streaming bug.

### The experiment (do this first)

1. Create a second AiDot account.
2. Share the cameras to it from the app.
3. On the runner host, with the secondary account's credentials in the
   environment:

   ```bash
   export AIDOT_INCLUDE_SHARED_HOUSES=1            # see below - without this you get nothing
   python scripts/live_validate.py --list          # does it see the cameras?
   python scripts/live_validate.py --model A001513 # can it actually stream one?
   ```

4. While that runs, **watch Home Assistant**: does its camera signaling stay
   up, or does it drop and reconnect?

**A shared account sees nothing without the seam.** Cameras shared from another
account live in a house whose `isOwner` is false, and `async_get_all_device()`
skips those - so `--list` reports `found 0 camera(s) of 0 device(s)` on an
account the cloud is perfectly willing to return every camera for. That reads
exactly like "sharing does not work" and it is not; set
`AIDOT_INCLUDE_SHARED_HOUSES=1` (the private live-validate job does) before
concluding anything. The empty *owned* house a new account gets is why
`get_houses()` returns two homes rather than one.

### Recording the outcome

| Question | Answer | Date |
| --- | --- | --- |
| Secondary account enumerates the cameras? | **Yes** - 7 cameras / 19 devices, with `AIDOT_INCLUDE_SHARED_HOUSES=1`. All three required models present: A000088 x4, A001513 x2, A001064 x1. Returns 0 without the seam | 2026-07-31 |
| Secondary account streams SDES (A001513)? | **Yes** - an A001513 PASS on the first attempt, handshake 7.9 s, 2646 packets / 2.9 MB, decodes as h264 1280x960 + PCMA. (Failed on 2026-07-31 only because of the answer-harvest bug below, which was not account-related.) | 2026-08-01 |
| Secondary account streams SDES (A001064, mains)? | **Yes** - the A001064 PTZ PASS, handshake 16.7 s, 2.5 MB | 2026-08-01 |
| Secondary account streams DTLS (A000088)? | **Yes** - an A000088 PASS, 139 frames, handshake 2.3 s, `host->relay`. Attempt 1 failed `AidotCameraNotReady` and attempt 2 succeeded, which is the documented per-attempt DTLS probability, not a fault | 2026-07-31 |
| Main account's HA signaling survived? | **Yes** - the AiDot config entry stayed `loaded` on the main account across ~6 secondary-account logins; one routine `21026 "Please login again"` token refresh, handled | 2026-07-31 |

**Verdict: use the secondary account.** It enumerates every camera (with
`AIDOT_INCLUDE_SHARED_HOUSES=1`), streams all three required models, and six
logins on it never disturbed Home Assistant.

The SDES failures recorded here on 2026-07-31 were **not** an account problem.
They were a shipped defect in <=0.12.16: the harvest of the camera's `webrtcResp`
gave it one event-loop cycle and took it only `if answer_fut.done()`, but the STUN
window ahead of it closes ~2.4 s BEFORE the answer lands. So the answer SDP was
always empty - no ICE credentials, therefore no USE-CANDIDATE, therefore a
controlled agent stuck in ICE "Checking" that never sends SRTP, and no camera SRTP
keys for the bridge. Fixed by awaiting the answer before parsing it. Every SDES
camera went from 0 bytes to streaming.

**Three traps this experiment produced, all convincing and all wrong:**

- A `--name`/`--model` filtered run **always** reports `overall: FAIL`, because the
  required-model check still runs against the filtered set and reports the rest as
  `missing_required_models`. Read the per-camera verdict on targeted runs.
- Runs emit many `Login failed, code: 4354` / `Connection reset by peer` lines for
  **non-camera** device ids (lights, plugs). Unrelated - a camera PASSes in the
  same run that produces dozens of them.
- Raising `_FIRST_MEDIA_WAIT_S` looks like the fix for a slow camera and is not.
  The "late ICE creds parsed" log line tracks the *wait*, not the camera: at a 75 s
  wait it fires at +81 s, at 150 s it fires at +152 s, and nothing else changes.

Enumeration says nothing about streaming: a shared account can list a camera
and still lack what it takes to open one. The last three rows need the real
runs.

**If yes** - use the secondary account's credentials as the `live-lan`
secrets. This is the preferred outcome: CI and HA never contend.

**If no** (sharing does not carry the camera functions) - fall back to the
main account plus a cached token:

- Log in once on the runner host and set `AIDOT_TOKEN_FILE=/path/token.json`
  in the environment so live runs reuse the session instead of re-logging-in.
- Accept the residual risk: a token refresh can still rotate the credential.
  Schedule releases when nobody is watching cameras, and expect HA to
  reconnect once around a live run.
- Either way, expect BUSY verdicts if someone opens a camera in HA or the
  phone app mid-run. Re-run the workflow; do not "fix" it by loosening the
  gate.

## Emergency override

There is exactly one way to publish without a green live run, and it is
deliberately awkward and auditable.

Set the repository **variable** `LIVE_GATE_OVERRIDE_SHA` to the **exact
commit SHA** of the release you are publishing:

```
Settings -> Secrets and variables -> Actions -> Variables
LIVE_GATE_OVERRIDE_SHA = <the release commit sha>
```

- Setting a repo variable requires admin and shows up in the audit log.
- It is single-use by construction: the next release has a different SHA, so a
  stale value can never silently disable the gate.
- The publish run prints a loud `::warning::` recording that it happened.

Clear the variable afterwards anyway.

Legitimate uses are narrow: the runner is dead and a security fix must ship,
or the vendor cloud is down and no validation is possible. "The gate is
annoying today" is not one - if the cameras cannot be validated, the release
has not been shown to work, which is the entire premise.

## Failure drills

Run these once, when you set the gate up, so you know it actually holds:

1. **No validation.** Publish a throwaway prerelease without triggering
   live-validate. `live-gate` must fail at its 3-minute grace ("no
   live-validation run ever claimed <sha>") and `publish` must never start.
   This replaces the older "stop the runner" phrasing: with validation in a
   separate repo, simply not running it is the same condition and needs no
   service surgery.
2. **Override.** Set `LIVE_GATE_OVERRIDE_SHA` to that prerelease's SHA and
   re-run. The publish must proceed and must log the warning. Unset it.
3. **Real failure.** Point the harness at a camera you have powered off
   (`--name`/`--model`), and confirm it goes red with `ERROR`/`NO_MEDIA` rather
   than passing.
4. **Contention** - *see the caveat below; this one does not behave as written.*

### Drill 4 does not produce BUSY on this hardware

Run 2026-08-01: an A001064 was held open by a library session, and a validation
run was started against the same camera. The run **passed** (13.5 s, 4.6 MB)
while the *incumbent* session lost its media (`Connection timed out` in its
ffmpeg log).

So this camera does **last-one-wins**: a new viewer evicts the existing one
rather than being refused. The `BUSY` path is real - the code handles the
terminal acks - but a second library session does not provoke one. The refusal
seems to need a different condition (the phone app, or a hard viewer limit).

Two consequences:

- Do not expect `BUSY` from this drill. Expect a pass, and expect whoever was
  watching to be dropped.
- **A live validation run will kick you off a camera you are viewing.** That is
  the operational cost of validating against a fleet someone lives with -
  schedule releases accordingly.

## Reading a report

`live-report.json` (uploaded as an artifact on every run, pass or fail):

```jsonc
{
  "verdict": "PASS",                    // gate result
  "ref": "<sha validated>",
  "cameras": [
    {
      "name": "a fourth A000088", "model": "LK.IPC.A000088", "tier": "required",
      "transport": "DTLS", "battery": false,
      "verdict": "PASS", "attempts_used": 2,   // needed a retry - watch this
      "attempts": [ /* per-attempt handshake_s, frames, ice_pair, rtp loss */ ]
    }
  ],
  "required_failed": [],
  "missing_required_models": []
}
```

Things worth noticing even in a green run: `attempts_used` creeping up on a
model, `handshake_s` growing, `rtp[].loss_pct` rising, or an `ice_pair` that
switched from a host pair to a relay pair.
