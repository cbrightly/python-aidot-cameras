"""A cancelled open must not orphan its MQTT session thread.

Both signalling transports park a worker thread that only exits when a `None`
sentinel reaches `outgoing_q`. `_reap_stream_drain` is the backstop for an open
that was cancelled before a session took ownership - but it can only reap what
it was told about, and the non-persistent branch never registered.

That mattered because the non-persistent session runs for 3600 s. A cancelled
open orphaned a thread, and its MQTT connection, for a full hour. Executor
workers are finite; enough orphans and every `run_in_executor` in the process
blocks - which a caller experiences as an open that never returns, the same
symptom as the uncancellable drain fixed alongside this.
"""
import asyncio
import inspect
import queue
import time

from aidot_cameras.camera import webrtc_open
from aidot_cameras.camera.client import CameraMixin


def test_both_mqtt_branches_register_with_the_backstop():
    """Whichever transport runs, something must be able to reap its thread."""
    src = inspect.getsource(webrtc_open)
    start = src.index("_pm_stream = (await self._get_persistent_mqtt()")
    end = src.index("# Wait for MQTT to be connected and subscribed", start)
    both = src[start:end]
    assert both.count("self._stream_mqtt_drain = ") == 2, (
        "one of the two MQTT branches does not register its worker with "
        "_reap_stream_drain; a cancelled open will orphan that thread"
    )
    assert both.count("self._stream_mqtt_outq = outgoing_q") == 2, (
        "the queue must be registered too - without it the reaper has no way "
        "to push the sentinel that actually releases the thread"
    )


def test_the_backstop_releases_a_blocked_worker():
    """The reaper must free a thread parked on a blocking get, not just cancel."""
    class _Cam(CameraMixin):
        def __init__(self):
            self.device_id = "test"

    async def run():
        loop = asyncio.get_running_loop()
        q = queue.Queue()
        cam = _Cam()
        # Exactly the shape of the non-persistent branch: a worker parked on a
        # blocking get that only a sentinel can release.
        cam._stream_mqtt_drain = loop.run_in_executor(None, lambda: q.get())
        cam._stream_mqtt_outq = q
        t0 = time.monotonic()
        await asyncio.wait_for(cam._reap_stream_drain(), 10.0)
        return time.monotonic() - t0

    elapsed = asyncio.run(run())
    assert elapsed < 5.0, (
        f"reaping took {elapsed:.1f}s - the worker was not released, so a "
        "cancelled open still leaks its MQTT thread"
    )
