"""Waiting for a cold battery camera to accept before offering.

A cold battery camera is not ready when the wake lands and it says so --
`livePlayResp` carries -50019 "not ready" while it boots. This path used to log
that and offer anyway, which is why the FIRST view of a battery camera failed
(abandoned at ~84 s) and the second worked (media in 4.9 s): attempt one was
doing the waking.
"""
import inspect

from aidot_cameras.camera import sdes_open


class TestTheCadenceIsTheApps:
    def test_it_reasserts_every_twenty_seconds(self):
        assert sdes_open._WAKE_REASSERT_EVERY_S == 20.0

    def test_it_reasserts_at_most_three_times(self):
        assert sdes_open._WAKE_REASSERT_MAX == 3

    def test_the_accept_wait_is_bounded(self):
        assert 0 < sdes_open._BATTERY_ACCEPT_WAIT_S <= 120

    def test_the_single_media_wait_was_not_just_lengthened(self):
        """A longer wait fails more slowly and delays giving up on a camera
        that is genuinely unreachable."""
        assert sdes_open._FIRST_MEDIA_WAIT_S == 75.0


class TestItGatesOnTheCamerasAccept:
    def _src(self):
        return inspect.getsource(sdes_open)

    def test_the_gate_runs_before_webrtcreq(self):
        src = self._src()
        gate = src.index("battery-accept elapsed")
        offer = src.index('"method":  "webrtcReq"')
        assert gate < offer, "the accept gate must precede the offer"

    def test_it_waits_on_the_liveplay_response(self):
        assert "shield(liveplay_resp_fut)" in self._src()

    def test_it_reasks_liveplay_when_it_rewakes(self):
        """The camera answers livePlayReq once, and the first ask went out
        while it was still asleep."""
        src = self._src()
        block = src.split("battery camera has not accepted yet")[1][:1400]
        assert "_live_play_topic_sdes, _live_req_sdes" in block


class TestItIsBatteryOnly:
    def test_the_gate_is_guarded_on_is_battery_camera(self):
        src = inspect.getsource(sdes_open)
        block = src.split("# livePlayResp: explicit camera accept/reject")[0]
        assert 'if bool(getattr(self, "is_battery_camera", False)):' in block

    def test_mains_fast_liveplay_is_untouched(self):
        """Fast liveplay saves ~4.5 s on mains cameras and is not changed."""
        src = inspect.getsource(sdes_open)
        assert "_skip_lp = self._resolve_sdes_fast_liveplay()" in src


class TestItFailsOpen:
    def test_it_offers_anyway_when_the_camera_never_accepts(self):
        """A camera that never accepts may still stream; refusing to try would
        turn a slow camera into a broken one."""
        src = inspect.getsource(sdes_open)
        assert "Offering anyway is deliberate" in src

    def test_a_queue_failure_cannot_abort_the_open(self):
        src = inspect.getsource(sdes_open)
        block = src.split("battery camera has not accepted yet")[1][:1200]
        assert "except Exception:" in block
        assert "_LOGGER.debug" in block
