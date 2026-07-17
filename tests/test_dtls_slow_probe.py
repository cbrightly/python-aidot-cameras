"""Slow-probe throttle for a DTLS camera that repeatedly fails to open while
the cloud never reports it offline (e.g. idle-but-cloud-online, or the
offline flag was simply never populated).  Without this, the DTLS serve loop
retries forever at the pacer's 300s cap, burning a 30s open timeout every
attempt and dripping a WARNING each time (see CameraMixin._dtls_serve_loop_inner
and the module-level _in_slow_probe / _probe_interval / _should_log_slow_probe
helpers).  After AIDOT_DTLS_SLOW_PROBE_THRESHOLD consecutive open failures
(keyed on the ReconnectPacer's attempt count, not the cloud-offline flag) the
loop widens its retry interval and downgrades the log to a periodic summary,
resetting the moment an open succeeds (ReconnectPacer.reset() -> attempt 0).

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""
import asyncio
import time
from types import SimpleNamespace

import aidot.camera.client as camera_client
from aidot.camera.client import (
    CameraMixin,
    _in_slow_probe,
    _probe_interval,
    _should_log_slow_probe,
)
from aidot.camera.protocol import ReconnectPacer

_THRESHOLD = 5
_NORMAL_DELAY = 40.0
_SLOW_INTERVAL = 600.0


# --- _in_slow_probe: pure threshold decision --------------------------------- #

def test_below_threshold_is_not_slow_probe():
    for attempt in range(_THRESHOLD):
        assert _in_slow_probe(attempt, _THRESHOLD) is False


def test_at_and_above_threshold_is_slow_probe():
    assert _in_slow_probe(_THRESHOLD, _THRESHOLD) is True
    assert _in_slow_probe(_THRESHOLD + 1, _THRESHOLD) is True
    assert _in_slow_probe(_THRESHOLD + 500, _THRESHOLD) is True


# --- _probe_interval: pure interval decision --------------------------------- #

def test_probe_interval_follows_normal_pacer_below_threshold():
    for attempt in range(_THRESHOLD):
        assert _probe_interval(
            attempt, _THRESHOLD, _NORMAL_DELAY, _SLOW_INTERVAL
        ) == _NORMAL_DELAY


def test_probe_interval_widens_at_threshold():
    assert _probe_interval(
        _THRESHOLD, _THRESHOLD, _NORMAL_DELAY, _SLOW_INTERVAL
    ) == _SLOW_INTERVAL
    assert _probe_interval(
        _THRESHOLD + 3, _THRESHOLD, _NORMAL_DELAY, _SLOW_INTERVAL
    ) == _SLOW_INTERVAL


def test_probe_interval_never_shrinks_below_slow_interval():
    # Defensive: even if the pacer's own delay somehow exceeded the slow
    # interval, the effective interval must never be LESS than slow_interval
    # once in slow-probe.
    assert _probe_interval(
        _THRESHOLD, _THRESHOLD, normal_delay=900.0, slow_interval=_SLOW_INTERVAL
    ) == 900.0


# --- _should_log_slow_probe: periodic summary decision ----------------------- #

def test_should_not_log_below_threshold():
    for attempt in range(_THRESHOLD):
        assert _should_log_slow_probe(attempt, _THRESHOLD, 3) is False


def test_logs_on_slow_probe_entry_then_periodically():
    # threshold=5, log_every=3: offsets 0,1,2,3,4,5,6 from threshold ->
    # log at offset 0, 3, 6 (attempts 5, 8, 11).
    log_every = 3
    logged = [
        a for a in range(_THRESHOLD, _THRESHOLD + 7)
        if _should_log_slow_probe(a, _THRESHOLD, log_every)
    ]
    assert logged == [5, 8, 11]


# --- ReconnectPacer.attempt + reset clears slow-probe state ----------------- #

def test_pacer_exposes_attempt_count():
    p = ReconnectPacer(15.0, 300.0)
    assert p.attempt == 0
    p.fail_delay()
    assert p.attempt == 1
    p.fail_delay()
    p.fail_delay()
    assert p.attempt == 3


def test_pacer_reset_clears_slow_probe_state():
    p = ReconnectPacer(15.0, 300.0)
    for _ in range(_THRESHOLD + 2):
        p.fail_delay()
    assert _in_slow_probe(p.attempt, _THRESHOLD) is True
    p.reset()
    assert p.attempt == 0
    assert _in_slow_probe(p.attempt, _THRESHOLD) is False


# --- CameraMixin._slow_probe_sleep: teardown-responsive chunked sleep ------- #

def _run(coro):
    return asyncio.run(coro)


def test_slow_probe_sleep_chunks_not_one_blocking_call(monkeypatch):
    # Assert the sleep structure: many small asyncio.sleep calls, none longer
    # than the chunk size - never one blocking sleep(interval) call.
    monkeypatch.setattr(camera_client, "_SLOW_PROBE_SLEEP_CHUNK_S", 0.05)
    stub = SimpleNamespace(_streaming_active=True)
    calls = []
    _real_sleep = asyncio.sleep

    async def _spy_sleep(d):
        calls.append(d)
        await _real_sleep(d)

    monkeypatch.setattr(camera_client.asyncio, "sleep", _spy_sleep)
    t0 = time.monotonic()
    _run(CameraMixin._slow_probe_sleep(stub, 0.22))
    took = time.monotonic() - t0
    assert took >= 0.2
    assert len(calls) >= 2, calls  # more than one chunk - never one long sleep
    assert all(c <= 0.05 + 1e-9 for c in calls), calls


def test_slow_probe_sleep_exits_when_streaming_stops(monkeypatch):
    monkeypatch.setattr(camera_client, "_SLOW_PROBE_SLEEP_CHUNK_S", 0.05)
    stub = SimpleNamespace(_streaming_active=True)

    async def scenario():
        async def stop():
            await asyncio.sleep(0.12)
            stub._streaming_active = False

        t0 = time.monotonic()
        await asyncio.gather(
            CameraMixin._slow_probe_sleep(stub, 5.0), stop()
        )
        return time.monotonic() - t0

    took = _run(scenario())
    # far short of the 5s slow-probe interval - teardown was not delayed
    assert took < 1.0


def test_slow_probe_sleep_returns_immediately_when_already_stopped():
    stub = SimpleNamespace(_streaming_active=False)
    t0 = time.monotonic()
    _run(CameraMixin._slow_probe_sleep(stub, 5.0))
    assert time.monotonic() - t0 < 0.5
