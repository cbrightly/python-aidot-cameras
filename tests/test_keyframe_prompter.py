"""Unit 2: earlier + repeated PLI — _keyframe_prompter.

v0.9.2 sends a single PLI once an SSRC is discovered (and delays it 0.5s); a lost
PLI then waits for the next natural IDR.  This helper fires immediately and
repeats until the first decoded frame arrives or max_tries is reached.

Repo convention: no pytest-asyncio; drive coroutines with asyncio.run().
"""
import asyncio

from aidot_cameras.camera.webrtc_open import _keyframe_prompter


def test_stops_when_first_frame_already_set():
    async def _run():
        ev = asyncio.Event(); ev.set()
        calls = []
        n = await _keyframe_prompter(lambda: calls.append(1), ev,
                                     interval=0.05, max_tries=8)
        return n, len(calls)
    n, c = asyncio.run(_run())
    assert n == 1 and c == 1  # fires once, sees frame, stops


def test_repeats_until_frame_arrives():
    async def _run():
        ev = asyncio.Event(); calls = []

        async def _set_later():
            await asyncio.sleep(0.12)
            ev.set()

        asyncio.ensure_future(_set_later())
        n = await _keyframe_prompter(lambda: calls.append(1), ev,
                                     interval=0.05, max_tries=8)
        return n, len(calls)
    n, c = asyncio.run(_run())
    assert 2 <= n <= 5 and c == n


def test_bounded_by_max_tries():
    async def _run():
        ev = asyncio.Event(); calls = []  # never set
        n = await _keyframe_prompter(lambda: calls.append(1), ev,
                                     interval=0.01, max_tries=4)
        return n, len(calls)
    n, c = asyncio.run(_run())
    assert n == 4 and c == 4
