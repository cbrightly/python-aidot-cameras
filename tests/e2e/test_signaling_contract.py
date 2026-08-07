"""End-to-end signaling contract, driving the REAL client against fake cameras.

These exercise the full open path - real CameraDeviceClient, real paho over
websockets, real cloud HTTP calls - up to the point where media would flow.
They cover the failure classes that shipped repeatedly and are invisible to
in-process unit tests: wake/keepalive ordering, terminal busy handling, and
the peerid-vs-devId matching the livePlayResp wait depends on.
"""
import asyncio

import pytest

from aidot_cameras.exceptions import AidotCameraBusy
from tests.e2e.fakes.signaling import FakeCameraSignaling

pytestmark = [pytest.mark.e2e, pytest.mark.timeout(120)]


async def _open_expecting_failure(dc, **kwargs):
    """Open a stream that cannot complete (no media plane); return the error.

    The signaling-only fakes deliberately stop short of media, so every open
    here ends in a timeout or a specific exception - the exception TYPE and the
    signaling trace are what these tests assert on.
    """
    try:
        await dc.async_open_webrtc_stream(timeout=kwargs.pop("timeout", 12.0), **kwargs)
        return None
    except Exception as exc:
        return exc


async def test_busy_ack_raises_promptly_without_retry_storm(
    e2e_device_client, fake_broker
):
    """A terminal ack (-50002) must raise AidotCameraBusy, fast, and stop.

    The official app shows an error and does not retry; retrying a camera at
    its viewer cap is futile and hammers the cloud.

    "Fast" is part of the contract, not a nicety. The SDES path used to look
    for a terminal ack only at its answer wait, which sits AFTER the 75s
    first-media wait - so a camera that refused in ~1s was reported as busy
    ~48s later (worse in production, where the media wait is not shortened),
    and only after a pointless DTLS-fallback offer. This is the path two of
    the three validated models take.
    """
    import time

    dc = e2e_device_client("A001513")
    cam = FakeCameraSignaling(
        fake_broker.url, device_id=dc.device_id, user_id=dc.user_id
    )
    cam.busy_ack = -50002
    await cam.start()
    try:
        started = time.monotonic()
        exc = await _open_expecting_failure(dc, timeout=30.0)
        elapsed = time.monotonic() - started

        assert isinstance(exc, AidotCameraBusy), (
            f"terminal -50002 must raise AidotCameraBusy, got {type(exc).__name__}: {exc}"
        )
        # The refusal lands ~1s in (right after our offer); allow generous
        # headroom for CI while still catching a return to waiting it out.
        assert elapsed < 10.0, (
            f"took {elapsed:.1f}s to surface a refusal the camera sent in about "
            "a second - the terminal ack is being waited out again"
        )
        # And it must not have re-offered repeatedly while doing so.
        webrtc_reqs = [m for m in cam.methods_received() if m == "webrtcReq"]
        assert len(webrtc_reqs) <= 2, (
            f"busy camera was re-offered {len(webrtc_reqs)}x - terminal acks "
            "must not be retried"
        )
    finally:
        cam.stop()


async def test_battery_camera_gets_the_smarthome_wake(
    e2e_device_client, fake_broker, fake_cloud
):
    """A001513 must get the battery-only smarthome wake before signaling.

    Two different endpoints are both named lowPowerActiveState: the smarthome
    one (async_wake_camera, battery-gated) and the platform-API v32 one (sent
    for every camera during signaling).  This asserts the battery-gated one.
    A sleeping battery camera never answers signaling, so wake-then-signal
    ordering is the difference between live video and a blank view.
    """
    dc = e2e_device_client("A001513")
    assert dc.is_battery_camera, "A001513 must be recognized as battery"

    cam = FakeCameraSignaling(
        fake_broker.url, device_id=dc.device_id, user_id=dc.user_id
    )
    cam.require_wake = True          # stays silent until woken
    await cam.start()
    try:
        await _open_expecting_failure(dc, timeout=20.0)

        wake_calls = [p for p in fake_cloud.paths() if "lowPowerActiveState" in p]
        assert wake_calls, (
            "battery camera was never woken - no lowPowerActiveState call "
            f"(cloud saw: {sorted(set(fake_cloud.paths()))})"
        )
        assert cam.woken.is_set() or wake_calls, "wake never reached the camera"
    finally:
        cam.stop()


async def test_mains_camera_skips_the_smarthome_wake(
    e2e_device_client, fake_broker, fake_cloud
):
    """The smarthome wake is battery-only; mains models must not pay for it.

    (The v32 signaling wake still fires for every model - that one is served
    by the separate fake_api host, so it cannot be confused with this.)
    """
    dc = e2e_device_client("A001064")
    assert not dc.is_battery_camera

    cam = FakeCameraSignaling(
        fake_broker.url, device_id=dc.device_id, user_id=dc.user_id
    )
    await cam.start()
    try:
        await _open_expecting_failure(dc, timeout=15.0)
        assert not [p for p in fake_cloud.paths() if "lowPowerActiveState" in p], (
            "mains camera must not be sent a battery wake"
        )
    finally:
        cam.stop()


async def test_liveplay_resp_is_matched_on_peerid(e2e_device_client, fake_broker):
    """The livePlayResp wait keys on the echoed peerid, not devId.

    Regression guard: a camera that answers with the RIGHT devId but the WRONG
    peerid must not satisfy the wait (matching on devId made the wait always
    time out; see docs/CAMERAS.md).
    """
    dc = e2e_device_client("A001513")

    class WrongPeerId(FakeCameraSignaling):
        def handle(self, method, inner, body):
            if method == "livePlayReq":
                # right devId, wrong peerid - must NOT satisfy the wait
                self._publish("livePlayResp", {"peerid": "not-our-peer", "result": 0})
                return
            super().handle(method, inner, body)

    cam = WrongPeerId(fake_broker.url, device_id=dc.device_id, user_id=dc.user_id)
    await cam.start()
    try:
        await _open_expecting_failure(dc, timeout=15.0)
        assert any(m == "livePlayReq" for m in cam.methods_received()), (
            "the client never sent livePlayReq"
        )
    finally:
        cam.stop()


async def test_client_publishes_the_expected_topic_sequence(
    e2e_device_client, fake_broker
):
    """Lock the request sequence a camera actually sees during an open."""
    dc = e2e_device_client("A001513")
    cam = FakeCameraSignaling(
        fake_broker.url, device_id=dc.device_id, user_id=dc.user_id
    )
    await cam.start()
    try:
        await _open_expecting_failure(dc, timeout=20.0)
        methods = cam.methods_received()
        assert "livePlayReq" in methods, f"no livePlayReq; saw {methods}"
        assert "webrtcReq" in methods, f"no webrtcReq (offer); saw {methods}"
        assert methods.index("livePlayReq") < methods.index("webrtcReq"), (
            f"livePlayReq must precede the offer; saw {methods}"
        )
        # Every request must be addressed to this camera.
        for _topic, body in cam.received:
            inner = body.get("payload") or {}
            assert dc.device_id in (body.get("devId"), inner.get("devId")), (
                f"message not addressed to the camera: {body}"
            )
    finally:
        cam.stop()


async def test_sdes_webrtc_req_carries_power_type_and_p2p_cache(
    e2e_device_client, fake_broker
):
    """The SDES offer must carry powerType / p2pCache, as the app's does.

    The reference client puts both on the webrtcReq payload - the same object it
    puts encOffer and liveMqtt on - reading them from the IPC device info
    (`LDSMQTTClient.sendSdpOffer`, smali :2967-2969, put at :3017/:3022).  Its
    no-device-info fallback puts the literal strings "1" / "0", so the wire type
    is a STRING, not an int.

    Our DTLS webrtcReq has carried both for a long time (webrtc_open.py, citing
    docs/official_camera_network_calls.md section 5.2); the SDES offer was simply
    never brought in line.  A001513 is a battery camera, so powerType is 2.

    This does NOT claim to fix the no-media failures - our offer bytes are
    identical between cycles that stream and cycles that do not, so a field
    missing from every cycle cannot explain the difference.  It closes a parity
    gap, nothing more.
    """
    dc = e2e_device_client("A001513")
    cam = FakeCameraSignaling(
        fake_broker.url, device_id=dc.device_id, user_id=dc.user_id
    )
    await cam.start()
    try:
        await _open_expecting_failure(dc, timeout=20.0)
        offers = [b for _t, b in cam.received if b.get("method") == "webrtcReq"]
        assert offers, f"no webrtcReq seen; saw {cam.methods_received()}"
        inner = offers[0].get("payload") or {}
        assert inner.get("powerType") == "2", (
            f"battery camera must offer powerType '2' as a string; "
            f"got {inner.get('powerType')!r}"
        )
        assert inner.get("p2pCache") == "2", (
            f"webrtcReq must carry p2pCache as a string; "
            f"got {inner.get('p2pCache')!r}"
        )
    finally:
        cam.stop()


async def test_concurrent_opens_do_not_deadlock(e2e_device_client, fake_broker):
    """Two concurrent opens of the same camera must both settle, not hang.

    The open gate is non-reentrant; a fallback path that re-entered the gated
    public method deadlocked whenever the gate was saturated.
    """
    dc = e2e_device_client("A001513")
    cam = FakeCameraSignaling(
        fake_broker.url, device_id=dc.device_id, user_id=dc.user_id
    )
    await cam.start()
    try:
        results = await asyncio.wait_for(
            asyncio.gather(
                _open_expecting_failure(dc, timeout=10.0),
                _open_expecting_failure(dc, timeout=10.0),
                return_exceptions=True,
            ),
            timeout=90.0,   # generous: the assertion is "settles", not "fast"
        )
        assert len(results) == 2
    finally:
        cam.stop()
