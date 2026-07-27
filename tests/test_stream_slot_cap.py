"""The concurrent-serve cap must never sit below the camera fleet.

A camera holds its slot for the life of its serve, so a cap lower than the number
of cameras does not merely slow things down - the odd one out waits forever and
simply never streams, with no error surfaced anywhere. Confirmed live on a fleet
of 4 DTLS cameras against the default cap of 3: the library logged "waiting for a
stream slot (cap reached)" for the fourth on every attempt, and that camera was
exactly the one that would not play in Home Assistant.
"""
import asyncio

import pytest

import aidot_cameras.camera.client as cc


@pytest.fixture(autouse=True)
def _reset(monkeypatch):
    monkeypatch.delenv("AIDOT_MAX_CONCURRENT_STREAMS", raising=False)
    cc._STREAM_SLOTS = None
    cc._STREAM_SLOTS_CAP = 0
    yield
    cc._STREAM_SLOTS = None
    cc._STREAM_SLOTS_CAP = 0


def test_default_is_the_host_protection_cap():
    async def go():
        assert cc._stream_slots_default() == 3
        cc._get_stream_slots()
        return cc._STREAM_SLOTS_CAP
    assert asyncio.run(go()) == 3


def test_a_bigger_fleet_raises_the_cap():
    async def go():
        return cc.configure_stream_limits(5)
    assert asyncio.run(go()) == 5


def test_every_camera_in_the_fleet_can_hold_a_slot_at_once():
    # The actual regression: with a cap of 3 and 4 cameras, the fourth blocks.
    async def go():
        cc.configure_stream_limits(4)
        slots = cc._get_stream_slots()
        for _ in range(4):
            await asyncio.wait_for(slots.acquire(), timeout=0.5)
        return True
    assert asyncio.run(go()) is True


def test_the_cap_never_shrinks():
    # Shrinking would strand a camera that already holds a slot.
    async def go():
        cc.configure_stream_limits(6)
        return cc.configure_stream_limits(2)
    assert asyncio.run(go()) == 6


def test_an_explicit_operator_cap_wins(monkeypatch):
    # Someone who capped a small host means it, and would rather cameras took
    # turns than have the host fall over.
    monkeypatch.setenv("AIDOT_MAX_CONCURRENT_STREAMS", "1")
    async def go():
        return cc.configure_stream_limits(8)
    assert asyncio.run(go()) == 1


def test_raising_is_idempotent():
    async def go():
        cc.configure_stream_limits(5)
        cc.configure_stream_limits(5)
        slots = cc._get_stream_slots()
        for _ in range(5):
            await asyncio.wait_for(slots.acquire(), timeout=0.5)
        # A sixth must block: the cap is 5, not 10.
        with pytest.raises(asyncio.TimeoutError):
            await asyncio.wait_for(slots.acquire(), timeout=0.2)
        return True
    assert asyncio.run(go()) is True
