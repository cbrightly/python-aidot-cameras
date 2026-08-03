"""A whole fleet must work, not just one camera.

Two shipped regressions were purely a function of fleet size, and every test in
the suite opened one or two cameras:

- the concurrent-stream cap defaulted to 3 against a fleet of 4, and a camera
  holds its slot for the life of its serve - so the 4th camera NEVER played and
  nothing surfaced an error
- the open gate is not reentrant, and a fallback path that re-entered it
  deadlocked whenever the gate was saturated (two such cameras at once)

These drive several cameras through the real client at once.
"""
import asyncio

import pytest

from tests.e2e.fakes.signaling import FakeCameraSignaling

pytestmark = [pytest.mark.e2e, pytest.mark.timeout(180)]


async def _open_settling(dc, timeout=10.0):
    try:
        await dc.async_open_webrtc_stream(timeout=timeout)
        return "opened"
    except Exception as exc:
        return type(exc).__name__


async def test_a_four_camera_fleet_all_get_to_signal(
    e2e_device_client, fake_broker, monkeypatch
):
    """Every camera in the fleet must reach the camera, not just the first N.

    The cap bug was silent: cameras beyond the limit simply never played, with
    no error anywhere. So the assertion is per-camera reachability - each fake
    camera must have SEEN an offer.
    """
    monkeypatch.setenv("AIDOT_MAX_CONCURRENT_OPENS", "2")
    monkeypatch.setenv("AIDOT_MAX_CONCURRENT_STREAMS", "3")   # deliberately < fleet

    clients = [e2e_device_client("A001513") for _ in range(4)]
    cams = [
        FakeCameraSignaling(fake_broker.url, device_id=dc.device_id,
                            user_id=dc.user_id, client_id=f"fleetcam-{i}")
        for i, dc in enumerate(clients)
    ]
    for cam in cams:
        await cam.start()
    try:
        results = await asyncio.wait_for(
            asyncio.gather(*(_open_settling(dc) for dc in clients),
                           return_exceptions=True),
            timeout=150.0,
        )
        assert len(results) == 4

        unreached = [
            i for i, cam in enumerate(cams)
            if "webrtcReq" not in cam.methods_received()
        ]
        assert not unreached, (
            f"camera(s) {unreached} of 4 were never offered a stream - a fleet "
            "larger than the concurrency cap must still get every camera on "
            "the air, not silently strand the extras"
        )
    finally:
        for cam in cams:
            cam.stop()


async def test_a_saturated_open_gate_does_not_deadlock(
    e2e_device_client, fake_broker, monkeypatch
):
    """With the gate at 1, concurrent opens must serialize - never wedge."""
    monkeypatch.setenv("AIDOT_MAX_CONCURRENT_OPENS", "1")

    clients = [e2e_device_client("A001513") for _ in range(3)]
    cams = [
        FakeCameraSignaling(fake_broker.url, device_id=dc.device_id,
                            user_id=dc.user_id, client_id=f"gatecam-{i}")
        for i, dc in enumerate(clients)
    ]
    for cam in cams:
        await cam.start()
    try:
        # asyncio.wait_for is the assertion: a deadlock shows up as a timeout
        # here rather than as a hung job.
        results = await asyncio.wait_for(
            asyncio.gather(*(_open_settling(dc, timeout=8.0) for dc in clients),
                           return_exceptions=True),
            timeout=150.0,
        )
        assert len(results) == 3, "every open must settle under a saturated gate"
    finally:
        for cam in cams:
            cam.stop()
