# AiDot camera cold-start optimization — design

**Date:** 2026-06-25
**Scope:** Reduce DTLS (A000088) live-stream cold-start latency in `python-aidot-cameras`.
**Status:** Design approved; pending spec review → implementation plan.

## Problem

Live-stream cold-start on DTLS battery cameras (A000088) is slow and highly
variable — measured **~14s (good path) up to ~70s (deep-sleep path)** on a clean
M1, LAN-direct (`AIDOT_FAST_CONNECT=1`). SDES cameras cold-start ~14s and are out
of scope. Steady-state serving is already cheap (~0.7% of one core, `-c copy`).

### Empirical breakdown (from `profile_coldstart.py`, timestamped logs)

| Phase | Time | Cause |
|---|---|---|
| Camera wake (MQTT signaling) | ~3s | low-power battery wake; physics, not tunable |
| **ICE candidate gather** | **~5s** | aiortc `setLocalDescription` stalls on Google STUN binding even in fast_connect (TURN stripped, STUN kept) |
| WebRTC handshake → connected | ~1-2s | normal |
| **connect → first keyframe** | **~5s** | single PLI fired ~1s after connect; if lost, waits for next natural IDR |
| **serve-ready bind wait** | **~2s** | blind `sleep(2.0)` (`device_client.py:4825`) after first muxed frame before signaling `_serve_ready` so HA can pull |
| **media-decline retries** | **+15s each** | camera returns DC-only answer (encoder not ready); serve loop retries on the 15s gate; repeated declines → ~70s |

Two of these (~5s gather, ~5s keyframe) are paid on *every* cold start; the 15s-gated
media-decline retries cause the ~70s outliers.

## Goals / non-goals

- **Goal:** shave the consistent ~9–12s of fixed/stalled overhead (ICE gather, PLI
  wait, serve-ready bind) AND eliminate the ~70s outliers (worst case → ~15–20s),
  without making a flaky camera worse.
- **Non-goals (YAGNI):** prewarm-on-motion tuning, SDES cold-start, the battery-wake
  time itself, new user-facing config beyond what's already there.

## Architecture

Four focused, **independently-shippable** changes (separate commits, any revertable
alone), each fronted by a small **pure helper** extracted from the 13k-line
`device_client.py` so the logic is unit-testable. New exception
`AidotCameraNotReady(AidotError)` in `exceptions.py`. No behavior change outside the
`fast_connect` path and the DTLS serve loop.

### Unit 1 — Host-only ICE in `fast_connect` (consistent ~5s)

- **Extract** `_build_ice_servers(ice_config, fast_connect, has_turn) -> list[RTCIceServer]`
  from the inline block at `device_client.py:6346–6478`.
- **Change:** today `fast_connect` strips TURN but keeps `stun:stun.l.google.com`, so
  aiortc's `setLocalDescription` stalls ~5s on STUN binding. New behavior: when
  `fast_connect`, return **host-only** (empty server list) → gathering completes in
  ms, offer goes out immediately.
- **Scope guard:** only aiortc's local `RTCConfiguration` goes host-only. The
  camera-facing `IceServerList` in `webrtcReq` is left unchanged, so the camera still
  gathers and offers its host candidate (which wins on-subnet). `fast_connect`'s
  existing "same-subnet LAN-direct only" contract already covers the tradeoff.
- **Risk:** low. `fast_connect=False` path unchanged (STUN+TURN preserved).

### Unit 2 — Earlier, repeated PLI (connect→first-keyframe ~5s)

- **Change:** fire the first PLI **immediately** on `connectionState=connected` (not
  ~1s later), then **repeat every ~0.7s until the first keyframe is tapped** (bounded
  ~8 tries ≈ 5s, then stop). Handles a lost first PLI instead of waiting for a natural
  IDR.
- **Extract** `_keyframe_prompter(send_pli, first_keyframe_event, interval=0.7,
  max_tries=8)` as a small awaitable — unit-testable with a fake `send_pli`.
- **Risk:** low. PLI is a standard keyframe request; bounded so it can't spam.

### Unit 3 — Fast-retry on media-decline (eliminates the ~70s outliers)

- **Detect:** at `device_client.py:8447` the impl already identifies
  `_dc_only_audio/_dc_only_video` (camera declined media = encoder not ready). Today it
  builds a stub answer, lets DTLS co-fail, and the serve loop retries on the 15s gate.
- **Change A:** on the clean decline, **raise `AidotCameraNotReady` immediately** —
  skip the doomed DTLS wait (saves seconds/attempt and labels the cause).
- **Change B:** in `_dtls_serve_loop_inner`, handle `AidotCameraNotReady` with a
  **bounded burst**: 4 attempts at ~3s spacing, **bypassing the 15s `_open_gate` for
  this case only** (a clean decline is not pounding a wedged stack). After the burst,
  **fall back to the existing 15s-gate path** (assume genuinely flaky). Any other
  exception keeps today's behavior **byte-for-byte unchanged**.
- **Extract** `_retry_policy(failure_kind, attempt_in_burst) -> (delay, bypass_gate)`
  as a pure function — the whole risk surface becomes a tested table.
- **Defaults:** burst = 3s × 4, hardcoded (per "minimal new knobs" decision). No new
  env vars.
- **Net:** a deep-asleep camera reconnects as soon as its encoder is ready (~3s
  granularity), worst case ~15–20s instead of ~70s.
- **Risk:** medium, fully isolated to the clean-decline case + unit-tested policy.

### Unit 4 — Active serve-ready detection (consistent ~2s)

- **Change:** the DTLS serve signals `_serve_ready` (which gates HA's URL fetch) after
  a blind `sleep(2.0)` "let ffmpeg analyze input + bind" (`device_client.py:4825`).
  Replace with an **active bind probe**: poll the ffmpeg `-listen` socket until it
  accepts a TCP connection, then signal ready.
- **Why it can't just be deleted:** if HA pulls before ffmpeg binds, it gets
  connection-refused and waits ~40s for its next retry. The active probe is *safer*
  than the 2s guess — it confirms the bind instead of hoping 2s was enough.
- **Extract** `_await_listen_bound(host, port, timeout=5.0) -> bool` — unit-testable
  against a throwaway listening socket.
- **Also (minor, free):** tighten the 0.3s tap-install poll (`:4781`) and 0.2s
  first-muxed-frame poll (`:4822`) to ~0.1s. Sub-second each; same safety bounds.
- **Risk:** low. Falls back to signaling ready at `timeout` if the probe never
  succeeds (no worse than today).

## Testing

**Unit (pytest, in existing `tests/`):**
- `_build_ice_servers`: `fast_connect=True` → host-only (no `stun:`/`turn:`);
  `fast_connect=False` → STUN+TURN preserved (no WAN/relay regression).
- `_retry_policy` (safety-critical): `not_ready` attempts 1–4 → `(~3s, bypass=True)`;
  attempt 5 → `(15s, gate)`; `hard_failure`/`busy` → today's values unchanged.
- `_keyframe_prompter`: fires immediately, repeats at interval, stops on keyframe,
  honors `max_tries`.
- `_await_listen_bound`: returns True once a throwaway listening socket accepts;
  returns False at timeout when nothing listens (signals ready anyway, no regression).

**Live validation (`profile_coldstart.py`, Deck/DTLS, several runs each side):**
- Good path: ICE-gather stall ~5s → <1s.
- Outlier path: bounded-burst recovery, worst case ~15–20s (not ~70s).
- User confirms in real Home Assistant.

## Rollback / safety

- Three independent commits; revert any one alone.
- Unit 3 gated entirely to the clean-decline case; hard failures and the 15s gate for
  flaky cameras unchanged.
- No new required config; `fast_connect` keeps its opt-in contract.

## New surface area

- `exceptions.py`: `class AidotCameraNotReady(AidotError)`.
- Helpers: `_build_ice_servers`, `_keyframe_prompter`, `_retry_policy`,
  `_await_listen_bound`.
- No new env vars.

## Expected impact (cumulative, good path)

~14s → ~5–6s good path (Units 1+2+4 remove ~5+5+2s of fixed/stalled time, minus
irreducible ~3s wake + ~1-2s handshake + real keyframe latency). Outlier path
~70s → ~15–20s (Unit 3). Numbers to be confirmed live with `profile_coldstart.py`.

## Validation results (2026-06-25, live on Deck / A000088, DTLS)

Implemented on branch `aidot-coldstart-opt` (4 units + 1 fix), 95 unit tests pass.

**Confirmed wins (reproducible across runs):**
- **Host-only ICE (Unit 1):** `webrtcReq` now goes out at ~3.0–3.5s (right after
  camera wake) vs ~7.8s before — the ~5s Google-STUN gather stall is gone. WebRTC
  reaches `connected` at **~4.4–4.9s vs ~9s baseline**.
- **Repeated PLI (Unit 2):** PLIs observed firing every 0.5s until first frame /
  bound, as designed.
- **Regression found & fixed:** the first host-only implementation also emptied the
  *camera-facing* IceServerList (`IceServerList×0`), and the camera then never
  answered (`no webrtcResp in 30s`). Fixed by keeping `_ice_servers` STUN-only for
  the camera and using a separate host-only list only for the local aiortc config.
  Post-fix the camera answers ~0.4s after the offer.

**Not client-addressable (dominant residual):**
- After WebRTC connects, the camera's **encoder deep-sleep warmup** can withhold
  video for ~5s (warm) up to ~22–26s (deep sleep) before any RTP flows. PLIs are
  sent but ignored until the encoder spins up. Observed total cold-start ~27–30s in
  these (heavily-re-woken) runs; within the variable baseline range (14.5–70s).

**Not exercised in these runs:** Unit 3 (media-decline fast-retry) needs a DC-only
decline, which didn't occur; Unit 4 (serve-ready bind probe) runs after frames flow.
Both are unit-tested. Recommend confirming in real Home Assistant over time.
