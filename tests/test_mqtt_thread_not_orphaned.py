"""A cancelled open must not orphan its MQTT stream drain.

The stream signalling parks a drain on `outgoing_q` that only exits when a
`None` sentinel reaches it. `_reap_stream_drain` is the backstop for an open
that was cancelled before a session took ownership - but it can only reap what
it was told about. (The per-open session that once ran beside the persistent
connection, for 3600 s, never registered; it is gone.)
"""

import asyncio
import queue
import time

from aidot_cameras.camera.client import CameraMixin


def test_the_stream_drain_registers_with_the_backstop():
    """The drain on the shared persistent connection and its queue are
    registered, so a cancelled open can be reaped (the queue is how the reaper
    pushes the sentinel that actually releases the drain)."""
    from tests.ast_guards import attr_writes, dtls_open_family, names_in

    phases = [fn for fn in dtls_open_family() if "_pm_stream" in names_in(fn)]
    assert phases, "the MQTT stream setup moved"
    for fn in phases:
        assert {"_stream_mqtt_drain", "_stream_mqtt_outq"} <= attr_writes(fn, "self"), (
            f"{fn.name}: the stream drain is not registered with _reap_stream_drain"
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
        # A worker parked on a blocking get that only a sentinel can release.
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
