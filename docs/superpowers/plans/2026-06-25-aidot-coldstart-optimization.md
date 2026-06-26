# AiDot Camera Cold-Start Optimization Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Reduce DTLS (A000088) camera live-stream cold-start from ~14–70s toward ~5–6s (good path) and ~15–20s (worst case) via four isolated, unit-tested changes.

**Architecture:** Extract four pure helpers from the 13k-line `src/aidot/device_client.py`, TDD each, then wire each into its existing call site. One new exception. No behavior change outside the `fast_connect` path and the DTLS serve loop. Each task is an independent commit, revertable alone.

**Tech Stack:** Python 3.11+, asyncio, aiortc, pytest (repo already uses it; see `tests/`).

## Global Constraints

- Python ≥ 3.11 (repo `pyproject.toml`).
- No new third-party dependencies; no new env vars (defaults hardcoded).
- Run tests with the repo venv: `PYTHONPATH=src .venv/bin/python -m pytest`.
- `fast_connect=False` behavior must remain byte-for-byte unchanged (WAN/relay users).
- Hard-failure and 15s-gate behavior for flaky cameras must remain unchanged except for the clean `AidotCameraNotReady` case.
- Branch: `aidot-coldstart-opt` (already created; spec committed there).
- Commit trailers on every commit:
  `Co-Authored-By: Claude Opus 4.8 <noreply@anthropic.com>`
  `Claude-Session: https://claude.ai/code/session_01Pc5yM3ZKUTtam5FZ7wF59x`

---

### Task 1: Host-only ICE in `fast_connect` (Unit 1)

**Files:**
- Modify: `src/aidot/device_client.py` (helper near other module-level helpers ~line 460; call site at 6465–6485)
- Test: `tests/test_ice_server_selection.py`

**Interfaces:**
- Produces: `_select_ice_servers(ice_servers: list, fast_connect: bool) -> list` — returns `[]` (host-only) when `fast_connect` is True, else returns `ice_servers` unchanged.

- [ ] **Step 1: Write the failing test**

```python
# tests/test_ice_server_selection.py
from aiortc import RTCIceServer
from aidot.device_client import _select_ice_servers


def _servers():
    return [
        RTCIceServer(urls=["stun:stun.l.google.com:19302"]),
        RTCIceServer(urls=["turn:3.230.182.123:5349"], username="u", credential="c"),
    ]


def test_fast_connect_is_host_only():
    assert _select_ice_servers(_servers(), fast_connect=True) == []


def test_non_fast_connect_unchanged():
    srv = _servers()
    assert _select_ice_servers(srv, fast_connect=False) is srv
```

- [ ] **Step 2: Run test to verify it fails**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/test_ice_server_selection.py -v`
Expected: FAIL — `ImportError: cannot import name '_select_ice_servers'`

- [ ] **Step 3: Write minimal implementation**

Add near the other module-level helpers (e.g. just below `_build_stun_binding_success_response`, ~line 460):

```python
def _select_ice_servers(ice_servers: list, fast_connect: bool) -> list:
    """LAN-direct (fast_connect) uses host candidates only: returning an empty
    server list makes aiortc's setLocalDescription skip the STUN/TURN gather
    stall (~5s) and emit the offer immediately. The camera's own host candidate
    wins on-subnet. Non-fast_connect is unchanged (STUN+TURN preserved)."""
    if fast_connect:
        return []
    return ice_servers
```

- [ ] **Step 4: Run test to verify it passes**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/test_ice_server_selection.py -v`
Expected: PASS (2 passed)

- [ ] **Step 5: Wire into the call site**

Replace the existing `if _fast_connect:` STUN-stripping block (currently `device_client.py:6465–6485`, ends just before `pc = RTCPeerConnection(`) with:

```python
        if _fast_connect:
            _ice_servers = _select_ice_servers(_ice_servers, _fast_connect)
            _status(
                f"AIDOT_FAST_CONNECT: host-only ICE "
                f"({len(_ice_servers)} server(s)) - no STUN/TURN gather stall"
            )
```

Leave the camera-facing `IceServerList` construction (~line 7314) untouched.

- [ ] **Step 6: Verify nothing else broke + commit**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/ -q`
Expected: PASS (no regressions)

```bash
git add tests/test_ice_server_selection.py src/aidot/device_client.py
git commit -m "perf(coldstart): host-only ICE in fast_connect (Unit 1)

Drop STUN in LAN-direct mode so setLocalDescription stops stalling ~5s on
Google STUN binding; offer goes out immediately. Camera-facing IceServerList
unchanged; non-fast_connect path unchanged.

Co-Authored-By: Claude Opus 4.8 <noreply@anthropic.com>
Claude-Session: https://claude.ai/code/session_01Pc5yM3ZKUTtam5FZ7wF59x"
```

---

### Task 2: Active serve-ready bind probe (Unit 4)

**Files:**
- Modify: `src/aidot/device_client.py` (helper ~line 460; call site at 4819–4826)
- Test: `tests/test_await_listen_bound.py`

**Interfaces:**
- Produces: `async def _await_listen_bound(host: str, port: int, timeout: float = 5.0) -> bool` — returns True once a TCP connect to `(host, port)` succeeds; False at `timeout`.

- [ ] **Step 1: Write the failing test**

```python
# tests/test_await_listen_bound.py
import asyncio
import socket
import pytest
from aidot.device_client import _await_listen_bound


@pytest.mark.asyncio
async def test_returns_true_when_socket_is_listening():
    srv = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    srv.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    srv.bind(("127.0.0.1", 0))
    srv.listen(1)
    port = srv.getsockname()[1]
    try:
        assert await _await_listen_bound("127.0.0.1", port, timeout=2.0) is True
    finally:
        srv.close()


@pytest.mark.asyncio
async def test_returns_false_when_nothing_listens():
    # Port 1 is privileged/unused for a client connect; expect timeout->False.
    assert await _await_listen_bound("127.0.0.1", 1, timeout=0.5) is False
```

- [ ] **Step 2: Run test to verify it fails**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/test_await_listen_bound.py -v`
Expected: FAIL — `ImportError: cannot import name '_await_listen_bound'`

- [ ] **Step 3: Write minimal implementation**

Add near the other module-level helpers (~line 460):

```python
async def _await_listen_bound(host: str, port: int, timeout: float = 5.0) -> bool:
    """Poll until a TCP connect to (host, port) succeeds, i.e. the ffmpeg
    -listen serve socket is actually bound and accepting. Replaces a blind
    sleep(2.0). Returns False if not bound within `timeout` (caller proceeds
    anyway, no worse than the old guess)."""
    loop = asyncio.get_running_loop()
    deadline = loop.time() + timeout
    while loop.time() < deadline:
        try:
            _r, _w = await asyncio.wait_for(
                asyncio.open_connection(host, port), timeout=0.5
            )
            _w.close()
            try:
                await _w.wait_closed()
            except Exception:
                pass
            return True
        except (OSError, asyncio.TimeoutError):
            await asyncio.sleep(0.1)
    return False
```

- [ ] **Step 4: Run test to verify it passes**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/test_await_listen_bound.py -v`
Expected: PASS (2 passed)

- [ ] **Step 5: Wire into the serve-ready signal**

In `_dtls_serve_loop_inner`, replace the blind wait block (currently `device_client.py:4820–4825`):

```python
                        _p0 = progress[0]
                        for _ in range(40):  # up to ~8s for the first muxed frames
                            await asyncio.sleep(0.1)
                            if progress[0] > _p0:
                                break
                        _host, _port = _dtls_serve_host_port(serve_url)
                        if _port is not None:
                            await _await_listen_bound(_host, _port, timeout=5.0)
```

If a `serve_url`→`(host, port)` parser does not already exist, add this minimal helper near the module helpers (reuse `urllib.parse`):

```python
def _dtls_serve_host_port(serve_url):
    """('http://127.0.0.1:8997/cam.ts') -> ('127.0.0.1', 8997); (None,)->(None,None)."""
    from urllib.parse import urlparse
    if not serve_url:
        return (None, None)
    u = urlparse(serve_url)
    return (u.hostname or "127.0.0.1", u.port)
```

(Note: the `for` poll interval is tightened from 0.2 → 0.1 in the same edit.)

- [ ] **Step 6: Verify + commit**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/ -q`
Expected: PASS

```bash
git add tests/test_await_listen_bound.py src/aidot/device_client.py
git commit -m "perf(coldstart): active serve-ready bind probe (Unit 4)

Replace blind sleep(2.0) with a TCP connect probe of the ffmpeg -listen socket;
signals serve-ready as soon as it actually binds (~2s saved, safer than the guess).

Co-Authored-By: Claude Opus 4.8 <noreply@anthropic.com>
Claude-Session: https://claude.ai/code/session_01Pc5yM3ZKUTtam5FZ7wF59x"
```

---

### Task 3: Earlier + repeated PLI until first keyframe (Unit 2)

**Files:**
- Modify: `src/aidot/device_client.py` (helper ~line 460; call site at 6844–6872)
- Test: `tests/test_keyframe_prompter.py`

**Interfaces:**
- Produces: `async def _keyframe_prompter(send_pli, first_frame: "asyncio.Event", interval: float = 0.7, max_tries: int = 8) -> int` — calls `send_pli()` immediately and after each `interval` until `first_frame` is set or `max_tries` reached; returns number of PLIs sent.

- [ ] **Step 1: Write the failing test**

```python
# tests/test_keyframe_prompter.py
import asyncio
import pytest
from aidot.device_client import _keyframe_prompter


@pytest.mark.asyncio
async def test_stops_when_first_frame_already_set():
    ev = asyncio.Event(); ev.set()
    calls = []
    n = await _keyframe_prompter(lambda: calls.append(1), ev, interval=0.05, max_tries=8)
    assert n == 1 and len(calls) == 1  # fires once, sees frame, stops


@pytest.mark.asyncio
async def test_repeats_until_frame_arrives():
    ev = asyncio.Event()
    calls = []

    async def _set_after_two_intervals():
        await asyncio.sleep(0.12)
        ev.set()

    asyncio.ensure_future(_set_after_two_intervals())
    n = await _keyframe_prompter(lambda: calls.append(1), ev, interval=0.05, max_tries=8)
    assert 2 <= n <= 5 and len(calls) == n


@pytest.mark.asyncio
async def test_bounded_by_max_tries():
    ev = asyncio.Event()  # never set
    calls = []
    n = await _keyframe_prompter(lambda: calls.append(1), ev, interval=0.01, max_tries=4)
    assert n == 4 and len(calls) == 4
```

- [ ] **Step 2: Run test to verify it fails**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/test_keyframe_prompter.py -v`
Expected: FAIL — `ImportError: cannot import name '_keyframe_prompter'`

- [ ] **Step 3: Write minimal implementation**

Add near the module helpers (~line 460):

```python
async def _keyframe_prompter(send_pli, first_frame, interval: float = 0.7,
                             max_tries: int = 8) -> int:
    """Send an RTCP PLI (keyframe request) immediately and then every `interval`
    seconds until `first_frame` is set or `max_tries` PLIs have been sent.
    Handles a lost first PLI instead of waiting for the next natural IDR.
    Returns the number of PLIs sent."""
    sent = 0
    for _ in range(max_tries):
        try:
            res = send_pli()
            if asyncio.iscoroutine(res):
                await res
        except Exception:
            pass
        sent += 1
        if first_frame.is_set():
            return sent
        try:
            await asyncio.wait_for(first_frame.wait(), timeout=interval)
            return sent
        except asyncio.TimeoutError:
            continue
    return sent
```

- [ ] **Step 4: Run test to verify it passes**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/test_keyframe_prompter.py -v`
Expected: PASS (3 passed)

- [ ] **Step 5: Wire into the connect-time PLI site**

At the existing single-PLI site (`device_client.py:6844–6872`), the code already discovers receivers/ssrcs and calls `await _recv._send_rtcp_pli(_ssrc)` once. Wrap that single send in a local `_send_pli()` closure and a first-frame `asyncio.Event` that the on_frame tap sets, then drive it with the prompter as a background task so it doesn't block the handshake:

```python
                # Build a one-shot PLI sender over the discovered receivers/ssrcs.
                async def _send_pli():
                    for _recv, _ssrc in _pli_targets:   # the list this block already builds
                        try:
                            await _recv._send_rtcp_pli(_ssrc)
                        except Exception:
                            pass
                    _status("video track: sent RTCP PLI (keyframe request)")

                # _first_video_frame is an asyncio.Event set by the on_frame tap on
                # the first decoded/encoded video frame (add `.set()` in that tap).
                asyncio.ensure_future(
                    _keyframe_prompter(_send_pli, _first_video_frame,
                                       interval=0.7, max_tries=8)
                )
```

Add `_first_video_frame = asyncio.Event()` where the session/taps are set up, and call `_first_video_frame.set()` inside the existing video on_frame tap on the first video frame. (Reuse the existing `_pli_targets`/ssrc discovery already present at 6844–6872; do not duplicate it.)

- [ ] **Step 6: Verify + commit**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/ -q`
Expected: PASS

```bash
git add tests/test_keyframe_prompter.py src/aidot/device_client.py
git commit -m "perf(coldstart): earlier + repeated PLI until first keyframe (Unit 2)

Fire PLI immediately on connect and repeat (0.7s x8) until the first video frame
is tapped, instead of a single PLI that, if lost, waits for the next natural IDR.

Co-Authored-By: Claude Opus 4.8 <noreply@anthropic.com>
Claude-Session: https://claude.ai/code/session_01Pc5yM3ZKUTtam5FZ7wF59x"
```

---

### Task 4: Fast-retry on media-decline (Unit 3)

**Files:**
- Modify: `src/aidot/exceptions.py` (new exception)
- Modify: `src/aidot/device_client.py` (raise at 8447; helper ~line 460; serve-loop retry at 4707–4749)
- Test: `tests/test_retry_policy.py`

**Interfaces:**
- Produces: `class AidotCameraNotReady(AidotError)` in `exceptions.py`.
- Produces: `_retry_policy(failure_kind: str, burst_attempt: int, *, burst_delay: float = 3.0, burst_max: int = 4, base_gate: float = 15.0) -> tuple[float, bool]` — returns `(delay_seconds, bypass_open_gate)`.

- [ ] **Step 1: Write the failing test**

```python
# tests/test_retry_policy.py
from aidot.device_client import _retry_policy


def test_not_ready_uses_burst_then_falls_back():
    # attempts 1..4 -> fast burst, bypass the 15s gate
    for a in range(1, 5):
        assert _retry_policy("not_ready", a) == (3.0, True)
    # attempt 5 -> fall back to the 15s gate, no bypass
    assert _retry_policy("not_ready", 5) == (15.0, False)


def test_hard_failure_unchanged():
    assert _retry_policy("hard_failure", 1) == (15.0, False)
    assert _retry_policy("hard_failure", 9) == (15.0, False)


def test_custom_bounds():
    assert _retry_policy("not_ready", 2, burst_delay=2.0, burst_max=2) == (2.0, True)
    assert _retry_policy("not_ready", 3, burst_delay=2.0, burst_max=2) == (15.0, False)
```

- [ ] **Step 2: Run test to verify it fails**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/test_retry_policy.py -v`
Expected: FAIL — `ImportError: cannot import name '_retry_policy'`

- [ ] **Step 3: Write minimal implementation**

Add to `src/aidot/exceptions.py` (after `AidotCameraBusy`):

```python
class AidotCameraNotReady(AidotError):
    """Camera responded but declined media (encoder not ready yet) — a DC-only
    WebRTC answer. Distinct from AidotCameraBusy and from hard failures so the
    serve loop can fast-retry instead of waiting the full 15s gate."""
```

Add to `device_client.py` near the module helpers (~line 460):

```python
def _retry_policy(failure_kind: str, burst_attempt: int, *,
                  burst_delay: float = 3.0, burst_max: int = 4,
                  base_gate: float = 15.0) -> "tuple[float, bool]":
    """(delay_seconds, bypass_open_gate) for the DTLS serve retry.

    A clean 'not_ready' decline (camera awake but encoder cold) gets a bounded
    fast burst (burst_delay x burst_max) that bypasses the 15s inter-attempt
    gate, then falls back to the normal gate. Every other failure keeps the
    unchanged 15s behavior."""
    if failure_kind == "not_ready" and burst_attempt <= burst_max:
        return (burst_delay, True)
    return (base_gate, False)
```

Import the exception at the top of `device_client.py` (extend the existing line `from .exceptions import AidotCameraBusy`):

```python
from .exceptions import AidotCameraBusy, AidotCameraNotReady
```

- [ ] **Step 4: Run test to verify it passes**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/test_retry_policy.py -v`
Expected: PASS (3 passed)

- [ ] **Step 5: Raise on the clean decline**

At the DC-only detection (`device_client.py:8447–8453`), after the existing `_status(...)` "DC-only answer" log, raise instead of returning the doomed stub answer:

```python
                if _dc_only_audio or _dc_only_video:
                    _status(
                        f"DC-only answer: audio_rejected={_dc_only_audio}"
                        f" video_rejected={_dc_only_video} → {_bundle_str}"
                        " (camera declined media; encoder not ready - fast retry)"
                    )
                    raise AidotCameraNotReady(self.device_id)
```

(This is inside the `_aiortc_answer` builder; raising propagates out of `async_open_webrtc_stream`, skipping the doomed DTLS wait.)

- [ ] **Step 6: Handle it in the serve loop**

In `_dtls_serve_loop_inner`, add a `not_ready` burst counter before the `while self._streaming_active:` loop:

```python
        _not_ready_burst = 0
```

Add a handler alongside the existing `except AidotCameraBusy` / `except Exception` (around `device_client.py:4725–4749`), BEFORE the generic `except Exception`:

```python
            except AidotCameraNotReady:
                _not_ready_burst += 1
                _delay, _bypass = _retry_policy("not_ready", _not_ready_burst)
                if _bypass:
                    _last_open_at = 0.0  # allow the next open to skip the 15s gate
                else:
                    _not_ready_burst = 0  # fell back; reset for next cold cycle
                _LOGGER.info(
                    "DTLS serve: %s not ready (encoder cold) - retry in %.0fs",
                    self.device_id, _delay,
                )
                try:
                    await asyncio.sleep(_delay)
                except asyncio.CancelledError:
                    return
                continue
```

On any successful open, reset the burst (add `_not_ready_burst = 0` right after `retry_delay = _MIN_DELAY` at the post-success line ~4751).

- [ ] **Step 7: Verify + commit**

Run: `PYTHONPATH=src .venv/bin/python -m pytest tests/ -q`
Expected: PASS

```bash
git add src/aidot/exceptions.py tests/test_retry_policy.py src/aidot/device_client.py
git commit -m "perf(coldstart): fast-retry on media-decline (Unit 3)

Raise AidotCameraNotReady on a clean DC-only decline (encoder cold) and have the
DTLS serve loop fast-retry it (3s x4, bypassing the 15s gate) before falling back
to the gate. Hard failures and the gate for flaky cameras unchanged.

Co-Authored-By: Claude Opus 4.8 <noreply@anthropic.com>
Claude-Session: https://claude.ai/code/session_01Pc5yM3ZKUTtam5FZ7wF59x"
```

---

### Task 5: Live before/after validation

**Files:**
- Use: `scratchpad/profile_coldstart.py` (already built this session)

- [ ] **Step 1: Capture 3 cold starts on `main` (baseline)** — `git stash` the working changes is not needed; check out the pre-branch commit or note prior numbers (~14s good / up to ~70s outlier already recorded).
- [ ] **Step 2: Capture 3 cold starts on `aidot-coldstart-opt`** for Deck (DTLS):
  `PYTHONPATH=src .venv/bin/python scratchpad/profile_coldstart.py Deck`
- [ ] **Step 3: Confirm acceptance:**
  - good path: ICE-gather stall <1s (was ~5s); serve-ready no longer adds a flat 2s; first byte materially lower than ~14s.
  - outlier path: on a decline, recovery steps ~3s (not 15s); worst case well under 70s.
- [ ] **Step 4: Record numbers in the design doc's validation section; note residual irreducible time (battery wake ~3s + handshake).**

---

## Self-Review

- **Spec coverage:** Unit 1→Task 1, Unit 2→Task 3, Unit 3→Task 4, Unit 4→Task 2, validation→Task 5. Exception `AidotCameraNotReady`→Task 4. All spec items covered.
- **Placeholder scan:** test code and helper bodies are complete; wiring steps show exact before/after. The only deliberately non-verbatim spots are the existing `_pli_targets`/ssrc discovery (Task 3 Step 5) and on_frame tap, which already exist at 6844–6872 — the step reuses them rather than restating the surrounding 30 lines.
- **Type consistency:** `_select_ice_servers(list,bool)->list`, `_await_listen_bound(str,int,float)->bool`, `_keyframe_prompter(callable,Event,float,int)->int`, `_retry_policy(str,int,*,float,int,float)->(float,bool)`, `AidotCameraNotReady` — names/signatures consistent across tasks.
- **Risk ordering:** Tasks 1,2 (low) → 3 (low) → 4 (medium, gated + tested). Each independently revertable.
