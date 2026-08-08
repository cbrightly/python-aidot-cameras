"""A LAN connect that never answers must not park forever.

Upstream `aidot.device_client` has no read timeout anywhere - `grep -cE
"wait_for|timeout"` over that file returns 0.  `login()` does
`await self.reader.readexactly(8)`, so a device that accepts the TCP connection
and then stops answering parks that coroutine indefinitely, INSIDE `connect()`.

Two things follow, and both were observed on one live run:

* `connect()`'s `finally: self._connecting = False` never runs, so the client
  believes a connection attempt is still in flight.  That wedges both re-entry
  doors: the retry never spawns, and a timer that did fire would hit the
  in-flight guard and return.  The device is silently abandoned.
* the socket stays open.  Four of six devices ended that way, and all six
  emitted their single `login read status error` within 3 ms of each other at
  process teardown - six sockets held open, one for 21 minutes.

So the failure mode is not a loop, it is a leak plus a device that quietly stops
being managed.  Bounding the attempt is what turns it back into an ordinary
failure that the retry policy can handle.
"""
import asyncio
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.device_client import (  # noqa: E402
    _LOGIN_CONNECT_TIMEOUT_S,
    _await_connect_with_deadline,
)


def test_there_is_a_deadline_and_it_is_finite():
    assert 0 < _LOGIN_CONNECT_TIMEOUT_S < 300


@pytest.mark.asyncio
async def test_a_connect_that_answers_is_left_alone():
    cleaned = []

    async def quick():
        return None

    ok = await _await_connect_with_deadline(
        quick(), 5.0, "dev", lambda: cleaned.append("cleanup") or _noop())
    assert ok is True
    assert cleaned == []


@pytest.mark.asyncio
async def test_a_connect_that_never_answers_is_abandoned_and_cleaned_up():
    """The bug: this used to wait forever, holding the socket."""
    cleaned = []

    async def hangs():
        await asyncio.sleep(3600)

    ok = await _await_connect_with_deadline(
        hangs(), 0.05, "dev", lambda: cleaned.append("cleanup") or _noop())
    assert ok is False
    assert cleaned == ["cleanup"], "the socket must be closed on timeout"


@pytest.mark.asyncio
async def test_the_hung_coroutine_is_actually_cancelled():
    """Not merely abandoned: if it kept running, the socket would still leak."""
    state = {"cancelled": False}

    async def hangs():
        try:
            await asyncio.sleep(3600)
        except asyncio.CancelledError:
            state["cancelled"] = True
            raise

    await _await_connect_with_deadline(hangs(), 0.05, "dev", _noop)
    await asyncio.sleep(0)
    assert state["cancelled"] is True


@pytest.mark.asyncio
async def test_a_failing_cleanup_does_not_mask_the_timeout():
    """Cleanup runs on the failure path; it must not raise into the caller and
    turn a handled timeout into an unhandled exception on the media path."""
    async def hangs():
        await asyncio.sleep(3600)

    async def bad_cleanup():
        raise RuntimeError("close failed")

    ok = await _await_connect_with_deadline(hangs(), 0.05, "dev", bad_cleanup)
    assert ok is False


@pytest.mark.asyncio
async def test_cancellation_of_the_caller_is_not_swallowed():
    """Shutdown must still be able to stop this."""
    async def hangs():
        await asyncio.sleep(3600)

    task = asyncio.ensure_future(
        _await_connect_with_deadline(hangs(), 30.0, "dev", _noop))
    await asyncio.sleep(0.01)
    task.cancel()
    with pytest.raises(asyncio.CancelledError):
        await task


async def _noop() -> None:
    return None
