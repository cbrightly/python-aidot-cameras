"""The TUTK (`liveType=0`) path is out of scope for 1.0, and this is its border.

`DeviceClient.async_open_live_stream` is public, so a consumer can call it. What
it can never do on any camera seen so far is reach `TutkStreamSession`: every
device queried returns `liveType=2` and no `p2pId`, and without a UID the call
refuses. Everything past that refusal is ctypes into `libIOTCAPIs.so` and
`libAVAPIs.so` - libraries this package does not ship, has never loaded, and
cannot obtain - so a test of it would assert our reading of a C SDK rather than
the SDK. See docs/DEFERRED_FEATURES.md and item 4 of docs/ROAD-TO-1.0.md.

That makes the refusal itself the entire reachable surface, and it is what these
tests cover. They assert that nothing TUTK-shaped is *constructed*, not merely
that the call returns None: with the guard deleted the call still ends in None,
because loading the absent native libraries fails and the failure is swallowed.
A test on the return value alone would pass against the broken code.
"""
import logging

import pytest

from aidot_cameras.camera import client as client_mod


class _Detonator:
    """Stands in for TutkStreamSession; being built at all is the failure."""

    def __init__(self, *args, **kwargs):
        raise AssertionError(
            "reached the TUTK session on a WebRTC camera - the guard that keeps "
            "the deferred path unreachable is gone"
        )


@pytest.fixture
def no_p2p_uid(monkeypatch):
    """Every camera queried so far: the cloud returns no UID for any source."""

    def _apply(cam):
        async def _none():
            return None

        monkeypatch.setattr(cam, "async_get_p2p_uid", _none)
        monkeypatch.setattr(client_mod, "TutkStreamSession", _Detonator)
        return cam

    return _apply


@pytest.mark.parametrize("profile", ["A000088", "A001513", "A001064"])
async def test_a_webrtc_camera_never_reaches_the_tutk_session(
    profile, make_camera_device_client, no_p2p_uid
):
    """DTLS and SDES alike stop at the guard, with the native libs untouched."""
    cam = no_p2p_uid(make_camera_device_client(profile))

    assert await cam.async_open_live_stream(lambda frame: None) is None


async def test_the_refusal_says_which_call_to_use_instead(
    make_camera_device_client, no_p2p_uid, caplog
):
    """The one actionable thing the guard produces is this redirection.

    A caller who has reached here has asked for live video and got None; without
    naming the call that works, the next step is reading the source of a
    deferred feature.
    """
    # The record a real camera returns: the fleet query that settled this found
    # liveType=2 on every device (docs/DEFERRED_FEATURES.md).
    cam = no_p2p_uid(
        make_camera_device_client("A000088", properties={"liveType": "2"}))

    with caplog.at_level(logging.ERROR, logger="aidot_cameras.camera.client"):
        assert await cam.async_open_live_stream(lambda frame: None) is None

    assert any(
        "async_open_webrtc_stream" in r.getMessage() for r in caplog.records
    ), f"no redirection to the supported call: {[r.getMessage() for r in caplog.records]}"
