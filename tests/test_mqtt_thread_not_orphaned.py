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
import queue
import time

from aidot_cameras.camera.client import CameraMixin


def test_both_mqtt_branches_register_with_the_backstop():
    """Whichever transport runs - the shared persistent connection or a
    per-open session - its worker and its queue are registered, so a cancelled
    open can be reaped (the queue is how the reaper pushes the sentinel that
    actually releases the thread)."""
    import ast

    from tests.ast_guards import attr_writes, dtls_open_family, names_in

    forks = [
        n
        for fn in dtls_open_family()
        for n in ast.walk(fn)
        if isinstance(n, ast.If) and "_pm_stream" in names_in(n.test) and n.orelse
    ]
    assert forks, "the MQTT transport fork moved"
    for fork in forks:
        for branch in (fork.body, fork.orelse):
            writes = attr_writes(ast.Module(body=branch, type_ignores=[]), "self")
            assert {"_stream_mqtt_drain", "_stream_mqtt_outq"} <= writes, (
                f"line {branch[0].lineno}: an MQTT branch does not register "
                "its worker and queue with _reap_stream_drain"
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
