"""The signalling drain must be cancellable, or a hung open cannot be rescued.

`queue.get()` with no timeout cannot be interrupted: cancelling the await
cancels only the wait, never the worker thread. The thread stays blocked for
the life of the process, and an `asyncio.wait_for` around the enclosing open
then blocks forever - waiting on a cancellation that can never complete.

Measured on an A001064 (2026-08-14): a second consecutive open returned no
success, no error, and did not respond to a 130s hard cap. The drain now
awaits an asyncio queue on the loop, with no worker thread at all; these tests
pin the property that makes the hang impossible, and that producers on any
thread still reach it, in order.
"""

import asyncio
import threading
import time

import pytest

from aidot_cameras.camera.webrtc_open import _drain_outgoing_queue, _SignalQueue


async def _noop_publish(_topic, _payload):
    return None


def test_cancellation_completes_promptly_on_an_idle_queue():
    """The regression: an idle queue must not make the task uncancellable."""

    async def run():
        q = _SignalQueue(asyncio.get_running_loop())  # empty, and stays empty
        task = asyncio.ensure_future(_drain_outgoing_queue(q, _noop_publish))
        await asyncio.sleep(0.05)  # let it wait on the get
        t0 = time.monotonic()
        task.cancel()
        with pytest.raises(asyncio.CancelledError):
            await asyncio.wait_for(task, 5.0)
        return time.monotonic() - t0

    elapsed = asyncio.run(run())
    assert elapsed < 1.0, f"cancellation took {elapsed:.1f}s"


def test_stop_sentinel_returns_immediately_from_another_thread():
    """A session's stop() may run off the loop; its sentinel still lands."""

    async def run():
        q = _SignalQueue(asyncio.get_running_loop())
        task = asyncio.ensure_future(_drain_outgoing_queue(q, _noop_publish))
        await asyncio.sleep(0.05)
        t0 = time.monotonic()
        threading.Thread(target=q.put_nowait, args=(None,)).start()
        await asyncio.wait_for(task, 5.0)
        return time.monotonic() - t0

    elapsed = asyncio.run(run())
    assert elapsed < 1.0, f"stop sentinel took {elapsed:.1f}s"


def test_queued_messages_are_published_in_order():
    """The hand-off must not drop or reorder signalling."""
    seen = []

    async def publish(topic, payload):
        seen.append((topic, payload))

    async def run():
        q = _SignalQueue(asyncio.get_running_loop())
        for i in range(5):
            q.put_nowait((f"topic/{i}", f"payload-{i}"))
        q.put_nowait(None)
        await asyncio.wait_for(_drain_outgoing_queue(q, publish), 5.0)

    asyncio.run(run())
    assert seen == [(f"topic/{i}", f"payload-{i}") for i in range(5)]


def test_a_put_after_the_loop_closed_is_dropped_quietly():
    """A session stopped after the loop shut down must not raise from stop()."""
    loop = asyncio.new_event_loop()
    q = _SignalQueue(loop)
    loop.close()
    q.put_nowait(None)  # no RuntimeError
