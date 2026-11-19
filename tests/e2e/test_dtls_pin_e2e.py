"""Per-camera DTLS certificate pinning over a REAL handshake.

tests/test_dtls_learned_pin.py proves the pin rules against a simulated
handshake. These run the real thing: the real client open path against
``FakeDtlsCamera``, which completes ICE and DTLS on this host with a
certificate the test holds and then sends H.264. Its answer carries an empty
fingerprint, as the A000088's does, so the learned pin is the only check on
the camera's certificate - the case pinning exists for.

What they guard, through a ``MemoryDtlsPinStore``:

1. the same certificate over several opens is recorded, then enforced after
   ``LEARN_HANDSHAKES``;
2. a reboot that keeps the certificate changes nothing;
3. a new certificate after enforcement is refused - ``AidotDtlsPinMismatch``
   from the open, ``dtls_pin_mismatch`` in stream health, the pin untouched -
   and ``forget_dtls_pin()`` lets the camera's new certificate be learned;
4. a camera with a new certificate every session is never locked out: it
   keeps learning and ``changes`` counts up.

A refused handshake does not fail the open at once: the client waits out its
ICE budget (the connection stays "connecting"), so the refused opens here are
given a short timeout.
"""

import asyncio
import time

import pytest

from aidot_cameras import AidotDtlsPinMismatch, MemoryDtlsPinStore
from aidot_cameras.camera import stream_health as sh
from aidot_cameras.camera.dtls_pins import LEARN_HANDSHAKES
from tests.e2e.fakes.dtls_camera import FakeDtlsCamera

pytestmark = [pytest.mark.e2e, pytest.mark.timeout(60)]

#: An open that connects does so in well under a second on loopback.
OPEN_TIMEOUT_S = 10.0
#: An open whose handshake is refused runs this long (see the docstring).
REFUSED_OPEN_TIMEOUT_S = 4.0


@pytest.fixture
async def dtls_camera(e2e_device_client, fake_broker):
    """(device client with a MemoryDtlsPinStore, its FakeDtlsCamera)."""
    dc = e2e_device_client("A000088")
    assert not dc.is_sdes_camera, "A000088 must take the DTLS path"
    dc.dtls_pin_store = MemoryDtlsPinStore()
    cam = FakeDtlsCamera(fake_broker.url, device_id=dc.device_id, user_id=dc.user_id)
    await cam.start()
    try:
        yield dc, cam
    finally:
        await cam.aclose()


async def _open(dc, frames=None, timeout=OPEN_TIMEOUT_S):
    """One real open, stopped again; returns how many frames it decoded."""
    got = [] if frames is None else frames
    session = await dc.async_open_webrtc_stream(on_frame=got.append, timeout=timeout)
    try:
        if frames is not None:
            deadline = time.monotonic() + 5.0
            while not got and time.monotonic() < deadline:
                await asyncio.sleep(0.05)
    finally:
        await session.stop()
    return len(got)


async def _enforce(dc, cam):
    for _ in range(LEARN_HANDSHAKES):
        await _open(dc)
    record = dc.dtls_pin
    assert record["enforced"] is True
    assert record["fingerprint"] == cam.fingerprint
    return record


async def test_same_certificate_is_learned_then_enforced(dtls_camera):
    dc, cam = dtls_camera
    assert dc.dtls_pin is None

    frames: list = []
    assert await _open(dc, frames) > 0, "the camera's H.264 never decoded"
    assert dc.dtls_pin == {
        "fingerprint": cam.fingerprint,
        "seen": 1,
        "enforced": False,
        "changes": 0,
    }

    for n in range(2, LEARN_HANDSHAKES + 1):
        await _open(dc)
        record = dc.dtls_pin
        assert record["seen"] == n
        assert record["enforced"] is (n >= LEARN_HANDSHAKES)

    # Enforced, the same certificate keeps connecting.
    await _open(dc)
    assert dc.dtls_pin == {
        "fingerprint": cam.fingerprint,
        "seen": LEARN_HANDSHAKES,
        "enforced": True,
        "changes": 0,
    }
    # Every open was a handshake with the camera's one certificate.
    assert cam.sessions == [cam.fingerprint] * (LEARN_HANDSHAKES + 1)


async def test_reboot_keeping_the_certificate_changes_nothing(dtls_camera):
    dc, cam = dtls_camera
    before = await _enforce(dc, cam)
    fingerprint = cam.fingerprint

    await cam.reboot(new_certificate=False)
    await _open(dc)

    assert cam.fingerprint == fingerprint
    assert dc.dtls_pin == before


async def test_new_certificate_after_enforcement_is_refused(dtls_camera):
    dc, cam = dtls_camera
    pinned = await _enforce(dc, cam)

    await cam.reboot(new_certificate=True)
    assert cam.fingerprint != pinned["fingerprint"]

    with pytest.raises(AidotDtlsPinMismatch):
        await dc.async_open_webrtc_stream(timeout=REFUSED_OPEN_TIMEOUT_S)
    # Refused on a real handshake that presented the new certificate...
    assert cam.sessions[-1] == cam.fingerprint
    # ...and whoever presented it did not get to replace the pin.
    assert dc.dtls_pin == pinned

    # The background stream names the refusal in its health. The streaming
    # loop opens with the default 30 s budget; a refused open lasts all of it,
    # so it is shortened here - the open path itself is unchanged.
    real_open = dc.async_open_webrtc_stream

    async def _short_open(*args, **kwargs):
        kwargs["timeout"] = REFUSED_OPEN_TIMEOUT_S
        return await real_open(*args, **kwargs)

    dc.async_open_webrtc_stream = _short_open
    await dc.async_start_streaming()
    try:
        deadline = time.monotonic() + 3 * REFUSED_OPEN_TIMEOUT_S
        while (
            dc.stream_health.last_end_reason != sh.END_DTLS_PIN_MISMATCH
            and time.monotonic() < deadline
        ):
            await asyncio.sleep(0.1)
        assert dc.stream_health.last_end_reason == sh.END_DTLS_PIN_MISMATCH, (
            f"stream health says {dc.stream_health.last_end_reason!r}"
        )
    finally:
        await dc.async_stop_streaming()
        del dc.async_open_webrtc_stream
    assert dc.dtls_pin == pinned

    # The owner says the camera was reset: its new certificate is learned.
    dc.forget_dtls_pin()
    assert dc.dtls_pin is None
    await _open(dc)
    assert dc.dtls_pin == {
        "fingerprint": cam.fingerprint,
        "seen": 1,
        "enforced": False,
        "changes": 0,
    }


async def test_new_certificate_every_session_is_never_locked_out(dtls_camera):
    dc, cam = dtls_camera
    opens = LEARN_HANDSHAKES + 2
    for n in range(opens):
        if n:
            await cam.reboot(new_certificate=True)
        await _open(dc)
        assert dc.dtls_pin == {
            "fingerprint": cam.fingerprint,
            "seen": 1,
            "enforced": False,
            "changes": n,
        }
    assert len(set(cam.sessions)) == opens
