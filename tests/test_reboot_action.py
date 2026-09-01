"""The camera reboot action.

The vendor app's H5 settings bundle calls
``devActionReq({action: "RebootFunc", in: []})``; ``RebootFunc`` also appears in
the native ``NewLivePresenter``. These tests pin the wire shape and, more
importantly, the two behaviours that are easy to get wrong: a reboot must not be
reported as failed just because the camera left before acking, and it must not
be fired at a camera the cloud says is offline.
"""
import asyncio


class _Cam:
    """Minimal stand-in exposing only what async_reboot touches."""

    def __init__(self, online=True, explicit=True, published=True):
        self.device_id = "dev1"
        self.status = type("S", (), {"online": online})()
        self._cloud_online_explicit = explicit
        self._published = published
        self.calls = []

    async def async_trigger_device_action(self, action, params, *,
                                          timeout=4.0, expect_ack=True):
        self.calls.append({"action": action, "in": params,
                           "expect_ack": expect_ack})
        return self._published

    from aidot_cameras.camera.controls import _CameraControlsMixin as _M
    async_reboot = _M.async_reboot


class TestTheWireShape:
    def test_it_sends_RebootFunc_with_an_empty_in(self):
        cam = _Cam()
        assert asyncio.run(cam.async_reboot()) is True
        assert cam.calls == [{"action": "RebootFunc", "in": [],
                              "expect_ack": False}]

    def test_it_does_not_wait_for_an_ack(self):
        """A reboot acks by going away; waiting turns success into False."""
        cam = _Cam()
        asyncio.run(cam.async_reboot())
        assert cam.calls[0]["expect_ack"] is False


class TestTheOfflineGate:
    def test_it_refuses_when_the_cloud_explicitly_says_offline(self):
        cam = _Cam(online=False, explicit=True)
        assert asyncio.run(cam.async_reboot()) is False
        assert cam.calls == []

    def test_it_still_sends_when_offline_is_not_explicit(self):
        """Unknown is not offline - the same rule the rest of the client uses."""
        cam = _Cam(online=False, explicit=False)
        assert asyncio.run(cam.async_reboot()) is True
        assert cam.calls[0]["action"] == "RebootFunc"

    def test_online_cameras_are_not_gated(self):
        cam = _Cam(online=True, explicit=True)
        assert asyncio.run(cam.async_reboot()) is True


class TestItReportsSending:
    def test_a_failed_publish_is_reported(self):
        cam = _Cam(published=False)
        assert asyncio.run(cam.async_reboot()) is False
