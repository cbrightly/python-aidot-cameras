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

## Follow-up — Unit 1 host-only ICE A/B (RESOLVED 2026-06-26)

Live A/B on M3 Pro v2 (warm, on-subnet), measuring `setLocalDescription`
ICE-gather time and total connect time across four local-pc ICE configs.
The camera-facing webrtcReq `IceServerList` was held constant (STUN) in every
arm — only the **local** `RTCPeerConnection`'s `iceServers` were narrowed, via
`AIDOT_FAST_CONNECT_PC_ICE`. 3 rounds, 8s cooldown between opens (rapid reopen
without cooldown causes camera-side recovery flakiness that is not ICE-related).

| local-pc ICE | servers | gather | connect | connected |
|--------------|---------|--------|---------|-----------|
| STUN (current default) | 9 (Google + 8× Arnoo STUN) | ~5018ms | ~7.2s | 3/3 |
| dedupe (unique URLs)   | 2 (Google + 1 Arnoo)      | ~5020ms | ~7.1s | 3/3 |
| google (single STUN)   | 1                          | ~5020ms | ~7.1s | 3/3 |
| host-only              | 0 (host candidates only)   | **~16ms** | **~2.2s** | 3/3 |

**Findings.**

1. **Where outbound STUN is unanswered, `fast_connect` eats a ~5s gather stall
   on every open.** On this test network every DTLS open paid ~5s in
   `setLocalDescription`. This is environment-specific, not universal (see
   finding 2) — but it is exactly the on-subnet / STUN-blocked case host-only
   targets.
2. **The 5s is a fixed gather timeout, not server count or a slow server.**
   Deduping to 2 servers and even a single fast Google STUN both still take
   ~5020ms — refuting the "duplicate/slow-server" hypothesis. Confirmed by
   reading the installed aioice: `Connection._gather_candidates` runs the srflx
   query under `asyncio.wait(tasks, timeout=5)` (aioice/ice.py:1041). It returns
   **early** when the STUN binding response arrives, but waits the **full 5s**
   when it does not. So on a network where STUN answers fast, gather is fast and
   there is no stall (and host-only saves little); the ~5020ms here means the
   srflx query is unanswered/blocked, after which aioice completes gathering with
   host candidates anyway.
3. **srflx contributes nothing to the connection on-subnet.** Connect time is
   identical (~2s of real work via **host** candidates) across all srflx arms;
   the 5s is pure dead weight here. Only `host-only` removes it (~5s faster).
4. **No reliability penalty on-subnet with cooldown.** All arms connected 3/3.
   The earlier 1/4 host-only result was a rapid-reopen (no-cooldown) artifact,
   not a host-only defect.

**Decision.** Ship host-only as an **opt-in, default-off** flag
(`AIDOT_FAST_CONNECT_PC_ICE=host`, legacy alias `AIDOT_FAST_CONNECT_HOST_ONLY=1`),
default stays STUN-only. Rationale: the ~5s win is real and repeatable on-subnet,
but host-only drops srflx/relay fallback, so an off-subnet / strict-NAT
`fast_connect` user MUST keep STUN+TURN. Opt-in/default-off respects the
maintainer's "keep STUN (cheap, no allocate)" note while giving on-subnet home
users (the common AiDot case) the win. The `dedupe`/`google` modes are NOT
shipped — they showed no benefit. The `_pc_ice_servers` / `_ice_servers`
separation (local-pc narrowing without touching the camera IceServerList) is the
load-bearing fix and is covered by a regression test.
