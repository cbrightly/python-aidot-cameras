"""A cancelled snapshot has to stop, not carry on to the end of its budget.

The SDES branch of ``async_snapshot`` waits on the recording session under
``wait_for``.  That wait used to catch ``CancelledError`` in the same handler as
``TimeoutError`` and ``pass`` past it, so a cancellation delivered while the
recording was in flight was consumed by the snapshot: the call went on to spawn
an ffmpeg frame-extract with its own 15s budget and then returned a plain
True/False, and the caller that cancelled it saw a normal result.

The two are not the same event.  A timeout means "the camera did not deliver in
time, salvage whatever landed in the temp file"; a cancellation means "the
caller is gone -- shut down".  Swallowing the second is what makes a shutdown
have to wait for a snapshot that nobody is waiting for any more.

These tests pin both halves: cancellation propagates (and still tears the
session down on the way out), timeout still salvages.
"""
import asyncio

import pytest


class _HangingSession:
    """A recording session that never finishes on its own."""

    def __init__(self):
        self.started = asyncio.Event()
        self.stopped = False

    async def wait_done(self):
        self.started.set()
        await asyncio.Event().wait()

    async def stop(self):
        self.stopped = True


class _TimingOutSession:
    """A recording session whose wait expires the way a slow camera's does."""

    def __init__(self):
        self.started = asyncio.Event()
        self.stopped = False

    async def wait_done(self):
        self.started.set()
        raise TimeoutError

    async def stop(self):
        self.stopped = True


def _with_session(cam, session):
    async def _open(**_kwargs):
        return session

    cam.async_open_webrtc_stream = _open
    return session


async def test_cancelling_an_sdes_snapshot_raises_cancellederror(
    make_camera_device_client, tmp_path
):
    """The caller that cancels must see the cancellation, not a return value."""
    cam = make_camera_device_client("A001064")
    assert cam.is_sdes_camera
    session = _with_session(cam, _HangingSession())

    task = asyncio.create_task(cam.async_snapshot(str(tmp_path / "snap.jpg")))
    await asyncio.wait_for(session.started.wait(), timeout=5)
    task.cancel()

    with pytest.raises(asyncio.CancelledError):
        await task


async def test_a_cancelled_snapshot_still_stops_the_session(
    make_camera_device_client, tmp_path
):
    """Propagating must not skip the teardown -- the stream would outlive us."""
    cam = make_camera_device_client("A001064")
    session = _with_session(cam, _HangingSession())

    task = asyncio.create_task(cam.async_snapshot(str(tmp_path / "snap.jpg")))
    await asyncio.wait_for(session.started.wait(), timeout=5)
    task.cancel()
    with pytest.raises(asyncio.CancelledError):
        await task

    assert session.stopped is True


async def test_a_timed_out_sdes_snapshot_still_returns_false(
    make_camera_device_client, tmp_path
):
    """The timeout half is unchanged: salvage the temp file, report the result.

    Nothing was recorded here, so the salvage finds an empty file and reports
    False -- without raising, which is the point.
    """
    cam = make_camera_device_client("A001064")
    session = _with_session(cam, _TimingOutSession())

    assert await cam.async_snapshot(str(tmp_path / "snap.jpg")) is False
    assert session.stopped is True
