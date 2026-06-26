"""Unit tests for _wait_or_event - the interruptible connect-poll cadence.

The WebRTC open loop polled `connected_ev` then `sleep(0.1)` at the tail, so a
connection completing mid-sleep cost up to ~100ms of dead time before the loop
re-tested. _wait_or_event keeps the 0.1s cadence when the event is unset but
wakes immediately once it fires. The loop body (ICE-candidate draining etc.) is
unchanged - only the trailing sleep became interruptible.

Repo convention: no pytest-asyncio; drive with asyncio.run().
"""
import asyncio

from aidot.camera.webrtc_open import _wait_or_event


def test_returns_true_immediately_when_event_already_set():
    async def _run():
        ev = asyncio.Event(); ev.set()
        loop = asyncio.get_event_loop()
        t0 = loop.time()
        r = await _wait_or_event(ev, 1.0)
        return r, loop.time() - t0
    r, dt = asyncio.run(_run())
    assert r is True and dt < 0.05  # did not wait out the 1.0s


def test_returns_false_after_full_timeout_when_unset():
    async def _run():
        ev = asyncio.Event()
        loop = asyncio.get_event_loop()
        t0 = loop.time()
        r = await _wait_or_event(ev, 0.1)
        return r, loop.time() - t0
    r, dt = asyncio.run(_run())
    assert r is False and 0.08 <= dt < 0.3  # waited ~the timeout


def test_wakes_early_when_event_set_mid_wait():
    async def _run():
        ev = asyncio.Event()

        async def _set_later():
            await asyncio.sleep(0.05)
            ev.set()

        asyncio.ensure_future(_set_later())
        loop = asyncio.get_event_loop()
        t0 = loop.time()
        r = await _wait_or_event(ev, 5.0)   # long timeout
        return r, loop.time() - t0
    r, dt = asyncio.run(_run())
    assert r is True and dt < 0.5  # woke at ~0.05s, not 5.0s
