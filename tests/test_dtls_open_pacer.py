"""The DTLS serve loop's failed-open pacer, its chunked wait, and the knobs
it replaced.

One ReconnectPacer paces every failed open, 15 s up to 600 s (see
_DTLS_RETRY_BASE_S in camera/client.py); the loop drives it in
tests/test_dtls_serve_backoff.py. Pinned here: the pacer's attempt count and
reset, the chunked wait the loop sleeps in (stop-aware, with an early end that
never cuts a floor), and the one warning for the three slow-probe knobs that
are still accepted and ignored.

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""

import asyncio
import time
from types import SimpleNamespace

import pytest

import aidot_cameras.camera.client as camera_client
from aidot_cameras.camera.client import CameraMixin
from aidot_cameras.camera.protocol import ReconnectPacer


# --- ReconnectPacer.attempt + reset ------------------------------------------ #


def test_pacer_exposes_attempt_count():
    p = ReconnectPacer(15.0, 600.0)
    assert p.attempt == 0
    p.fail_delay()
    assert p.attempt == 1
    p.fail_delay()
    p.fail_delay()
    assert p.attempt == 3


def test_pacer_reset_starts_again_at_the_base():
    p = ReconnectPacer(15.0, 600.0, rand=lambda: 1.0)
    for _ in range(8):
        p.fail_delay()
    p.reset()
    assert p.attempt == 0
    assert p.fail_delay() == 15.0


# --- CameraMixin._chunked_wait: teardown-responsive chunked sleep ------- #


def _run(coro):
    return asyncio.run(coro)


def test_chunked_wait_chunks_not_one_blocking_call(monkeypatch):
    # Assert the sleep structure: many small asyncio.sleep calls, none longer
    # than the chunk size - never one blocking sleep(interval) call.
    monkeypatch.setattr(camera_client, "_WAIT_CHUNK_S", 0.05)
    stub = SimpleNamespace(_streaming_active=True)
    calls = []
    _real_sleep = asyncio.sleep

    async def _spy_sleep(d):
        calls.append(d)
        await _real_sleep(d)

    monkeypatch.setattr(camera_client.asyncio, "sleep", _spy_sleep)
    t0 = time.monotonic()
    _run(CameraMixin._chunked_wait(stub, 0.22))
    took = time.monotonic() - t0
    assert took >= 0.2
    assert len(calls) >= 2, calls  # more than one chunk - never one long sleep
    assert all(c <= 0.05 + 1e-9 for c in calls), calls


def test_chunked_wait_exits_when_streaming_stops(monkeypatch):
    monkeypatch.setattr(camera_client, "_WAIT_CHUNK_S", 0.05)
    stub = SimpleNamespace(_streaming_active=True)

    async def scenario():
        async def stop():
            await asyncio.sleep(0.12)
            stub._streaming_active = False

        t0 = time.monotonic()
        await asyncio.gather(CameraMixin._chunked_wait(stub, 5.0), stop())
        return time.monotonic() - t0

    took = _run(scenario())
    # far short of the 5s slow-probe interval - teardown was not delayed
    assert took < 1.0


def test_chunked_wait_returns_immediately_when_already_stopped():
    stub = SimpleNamespace(_streaming_active=False)
    t0 = time.monotonic()
    _run(CameraMixin._chunked_wait(stub, 5.0))
    assert time.monotonic() - t0 < 0.5


def test_the_early_end_never_cuts_the_floor(monkeypatch):
    monkeypatch.setattr(camera_client, "_WAIT_CHUNK_S", 0.02)
    stub = SimpleNamespace(_streaming_active=True)
    t0 = time.monotonic()
    _run(CameraMixin._chunked_wait(stub, 5.0, until=lambda: True, min_s=0.15))
    took = time.monotonic() - t0
    assert 0.15 <= took < 1.0


def test_the_early_end_is_checked_each_step(monkeypatch):
    monkeypatch.setattr(camera_client, "_WAIT_CHUNK_S", 0.02)
    stub = SimpleNamespace(_streaming_active=True)
    flag = {"back": False}

    async def scenario():
        async def back():
            await asyncio.sleep(0.1)
            flag["back"] = True

        t0 = time.monotonic()
        await asyncio.gather(
            CameraMixin._chunked_wait(stub, 5.0, until=lambda: flag["back"]),
            back(),
        )
        return time.monotonic() - t0

    assert _run(scenario()) < 1.0


# --- the three slow-probe knobs: accepted, ignored, one warning ------------- #


@pytest.fixture
def fresh_warning(monkeypatch):
    monkeypatch.setattr(camera_client, "_deprecated_knobs_warned", False)


def test_a_deprecated_knob_that_is_set_warns_once(fresh_warning, caplog):
    env = {
        "AIDOT_DTLS_SLOW_PROBE_THRESHOLD": "3",
        "AIDOT_DTLS_SLOW_PROBE_INTERVAL_S": "60",
    }
    with caplog.at_level("WARNING", logger="aidot_cameras.camera.client"):
        camera_client._warn_deprecated_knobs(env)
        camera_client._warn_deprecated_knobs(env)
    lines = [r.getMessage() for r in caplog.records if "deprecated" in r.getMessage()]
    assert len(lines) == 1, lines
    assert "AIDOT_DTLS_SLOW_PROBE_THRESHOLD" in lines[0]
    assert "AIDOT_DTLS_SLOW_PROBE_INTERVAL_S" in lines[0]
    assert "AIDOT_DTLS_SLOW_PROBE_LOG_EVERY" not in lines[0]


def test_no_deprecated_knob_set_says_nothing(fresh_warning, caplog):
    with caplog.at_level("WARNING", logger="aidot_cameras.camera.client"):
        camera_client._warn_deprecated_knobs({})
    assert not caplog.records
    assert camera_client._deprecated_knobs_warned is False
