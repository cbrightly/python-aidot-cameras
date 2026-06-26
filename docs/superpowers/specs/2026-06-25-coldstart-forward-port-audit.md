# Cold-start forward-port onto v0.9.2 — audit & decisions

**Date:** 2026-06-25
**Context:** Four cold-start optimizations ("Units 1–4") were built and live-validated
on the v0.5.15 monolithic `device_client.py` lineage (branch `camera-main`).
This branch (`forward-port-coldstart`, off `origin/main` v0.9.2) forward-ports
only what is genuinely missing from the modular `src/aidot/camera/` package.

## Why an audit first

v0.9.2 is the **newer, maintained** continuation of the same camera fork
(latest commit 2026-06-25 vs v0.5.15's 2026-06-09), with the camera code split
into a package. A code-level audit (not a commit-list diff) was run to avoid
re-introducing fixes v0.9.2 already has — or, worse, ones it deliberately
removed. Every v0.5.15-unique fix cluster was checked against v0.9.2:

| v0.5.15 fix | v0.9.2 status |
|---|---|
| SDES out-of-band SPS/PPS (sprop) injection | PRESENT (verbatim) |
| SDES orphan idle-release | PRESENT (+ per-camera override) |
| SDES serve video-only default | SUPERSEDED (silence-base/amix keeps audio) |
| DTLS audio FIFO pad | SUPERSEDED — porting would **regress** (removed as the choppiness cause) |
| DTLS `-fflags +nobuffer` | PRESENT |
| token / MQTT-password log redaction | PRESENT (verbatim) |
| playback tuple-return + cloud base-URL fix | PRESENT (byte-identical) |
| DTLS 15s reconnect gate + token-refresh | PRESENT |
| fast_connect skips (TURN/getIceConfig/livePlay) | PRESENT (DTLS); inert on SDES by design |

**Conclusion:** the cold-start work is the only genuinely-unique remainder.

## The four cold-start units, audited

| Unit | v0.9.2 status | Decision |
|---|---|---|
| **1 — host-only ICE in fast_connect** | PARTIAL — fast_connect already strips TURN (STUN-only); the big ICE win is already banked. Host-only is additive, DTLS-only, and overrides a deliberate "keep STUN (cheap, no allocate)" choice. | **Deferred** — needs a live A/B on v0.9.2 before overriding a shipped decision for an unmeasured delta. |
| **2 — earlier + repeated PLI** | ABSENT — sends a single PLI (delayed 0.5s); a lost PLI stalls until the next natural IDR. | **Ported.** |
| **3 — fast-retry on DC-only decline** | PARTIAL — DC-only *detection* exists, but a decline eats the full 15s gate. | **Ported** (burst only; reuses existing detection). |
| **4 — active serve-ready bind probe** | SUPERSEDED — already a frame-progress wait; a TCP probe would be consumed by `-listen 1` and tear down the serve. | **Dropped** (would regress). |

## What this branch changes

- `src/aidot/exceptions.py` — add `AidotCameraNotReady`.
- `src/aidot/camera/webrtc_open.py` — `_keyframe_prompter` helper + prompter-driven
  PLI with a first-frame signal (Unit 2); `_media_declined` flag + raise
  `AidotCameraNotReady` on a DC-only answer (Unit 3, webrtc side).
- `src/aidot/camera/client.py` — `_retry_policy` helper + `except AidotCameraNotReady`
  fast-retry burst in the DTLS serve loop (Unit 3, serve side).
- `tests/` — `test_keyframe_prompter.py`, `test_retry_policy.py` (pure-helper unit tests).

Full suite: 219 passed, 1 skipped. Ruff clean.

## Live validation (2026-06-26, v0.9.2 + this branch)

All three A000088 DTLS cameras were cold-opened with the worktree code.

- **Unit 3 — real DC-only cold-decline + burst (hardware).** "Deck" was genuinely
  deep-asleep. The full cycle was captured live:
  - attempt 1 `webrtcReq` at 8.2s → **DC-only answer** at 8.5s (`audio_rejected=True
    video_rejected=True ... encoder not ready - fast-retry`) — the Unit 3 detection
    + raise path.
  - serve loop logged `not ready (encoder cold) - retry 3s [burst]` and retried in
    **3s, not the 15s gate**.
  - attempt 2 `webrtcReq` at 16.6s (encoder now warm) → connected 18.1s → serving
    19.3s → first video byte **24.6s** end-to-end.
  - Net: the decline retried at +3s instead of +15s, ~12s saved on this cold start.
- **Unit 2 — PLI prompter (hardware).** On "Bedroom M3 Pro" the prompter found the
  video SSRC and sent a real RTCP PLI (`video track: sent RTCP PLI ... ssrc=[...]`),
  then stopped once the keyframe arrived — the intended behaviour.
- **Happy path (hardware).** "M3 Pro v2" and "Bedroom M3 Pro" (warm) returned full
  media answers and connected ~9.3s / served ~10.3s — no regression from the
  prompter or the Unit 3 wiring.
- **Burst behaviour (deterministic, committed test).**
  `tests/test_dtls_not_ready_burst.py` drives the **real** `_dtls_serve_loop_inner`
  with a synthetic `AidotCameraNotReady` and confirms it fast-retries at 3s x4
  (gate bypassed) then falls back to `retry 15s [gate]` — locking in the serve-loop
  counter management as a regression test.

## Follow-up (not in this branch)

Unit 1 (host-only ICE): live-measure cold-start connect time on v0.9.2 with
`fast_connect` STUN-only vs host-only on a same-subnet camera. Only adopt
host-only if it shows a real, repeatable win — and scope it so a
not-perfectly-on-subnet `fast_connect` user keeps a STUN fallback.
