"""The signalling drain must be cancellable, or a hung open cannot be rescued.

`queue.get()` with no timeout cannot be interrupted: cancelling the await
cancels only the wait, never the worker thread. The thread stays blocked for
the life of the process, and an `asyncio.wait_for` around the enclosing open
then blocks forever - waiting on a cancellation that can never complete.

Measured on an A001064 (2026-08-14): a second consecutive open returned no
success, no error, and did not respond to a 130s hard cap. These tests pin the
property that makes that impossible.
"""
import asyncio
import queue
import time

import pytest

from aidot_cameras.camera.webrtc_open import _drain_outgoing_queue


async def _noop_publish(_topic, _payload):
    return None


def test_cancellation_completes_promptly_on_an_idle_queue():
    """The regression: an idle queue must not make the task uncancellable."""
    async def run():
        loop = asyncio.get_running_loop()
        q = queue.Queue()                       # empty, and stays empty
        task = asyncio.ensure_future(
            _drain_outgoing_queue(loop, q, _noop_publish, poll_s=0.1))
        await asyncio.sleep(0.25)               # let it block on the get
        t0 = time.monotonic()
        task.cancel()
        with pytest.raises(asyncio.CancelledError):
            await asyncio.wait_for(task, 5.0)
        return time.monotonic() - t0
    elapsed = asyncio.run(run())
    assert elapsed < 3.0, (
        f"cancellation took {elapsed:.1f}s - an unbounded queue.get() is back, "
        "and with it an open that no caller-side timeout can break"
    )


def test_stop_sentinel_still_returns_immediately():
    """Polling must not slow the normal stop path."""
    async def run():
        loop = asyncio.get_running_loop()
        q = queue.Queue()
        task = asyncio.ensure_future(
            _drain_outgoing_queue(loop, q, _noop_publish, poll_s=5.0))
        await asyncio.sleep(0.1)
        t0 = time.monotonic()
        q.put(None)                             # the sentinel
        await asyncio.wait_for(task, 5.0)
        return time.monotonic() - t0
    elapsed = asyncio.run(run())
    assert elapsed < 2.0, f"stop sentinel took {elapsed:.1f}s"


def test_queued_messages_are_published_in_order():
    """The poll must not drop or reorder signalling."""
    seen = []

    async def publish(topic, payload):
        seen.append((topic, payload))

    async def run():
        loop = asyncio.get_running_loop()
        q = queue.Queue()
        for i in range(5):
            q.put((f"topic/{i}", f"payload-{i}"))
        q.put(None)
        await asyncio.wait_for(
            _drain_outgoing_queue(loop, q, publish, poll_s=0.1), 10.0)
    asyncio.run(run())
    assert seen == [(f"topic/{i}", f"payload-{i}") for i in range(5)]
