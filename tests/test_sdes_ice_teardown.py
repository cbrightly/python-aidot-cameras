"""Recognise the camera's ICE teardown, so a dead session is not nursed for 30 s.

The A001064 runs Leedarson's fork of the AWS KVS WebRTC SDK C
(`/home/cly/share/lds-work/ipc_sdk/src/lds_kvs_webrtc_sdk/`), whose ICE agent
transitions CONNECTED -> DISCONNECTED -> FAILED and tears the transport down.
Measured on the wire over four stock sessions: while the transport is alive the
camera answers our STUN binding requests about every 2.5 s and sends its own
keepalive indication; at the 80.2 s cliff it stops answering STUN, stops the
indications, and stops media all in the same instant. A session that survives
the cliff keeps all three running.

That distinction is worth acting on. Media stopping ALONE can be a pause the
camera recovers from, which is what the LIVING nudges are for. Media stopping
AND the STUN answers stopping is the transport being gone, and no nudge can
bring it back - only a reopen. Today the watchdog waits 30 s to find that out;
the outage per cliff is about 38 s, every ~2 minutes, all day.

Both clocks must be live or this must say nothing: a session that never carried
media, or a build where the ICE timestamp is not plumbed through, has to fall
back to the ordinary watchdog rather than tear a healthy session down.
"""
import pytest

from aidot_cameras.camera.protocol import sdes_ice_teardown


class TestQuiet:
    def test_healthy_session_is_not_a_teardown(self):
        """Media and answers both flowing."""
        assert sdes_ice_teardown(last_media=100.0, last_ice_answer=100.0, now=100.5) is False

    def test_media_gap_alone_is_not_a_teardown(self):
        """A media pause with STUN still answered is what the nudges are for."""
        assert sdes_ice_teardown(last_media=100.0, last_ice_answer=109.0, now=110.0) is False

    def test_ice_gap_alone_is_not_a_teardown(self):
        """Media still arriving means the transport is plainly alive."""
        assert sdes_ice_teardown(last_media=109.5, last_ice_answer=100.0, now=110.0) is False


class TestTeardown:
    def test_both_gone_is_a_teardown(self):
        assert sdes_ice_teardown(last_media=100.0, last_ice_answer=100.0, now=110.0) is True

    def test_exactly_at_the_thresholds_is_not_yet(self):
        """Strictly greater, so a threshold-length gap does not trip it."""
        assert sdes_ice_teardown(last_media=100.0, last_ice_answer=100.0,
                                 now=106.0, media_gap=3.0, ice_gap=6.0) is False

    def test_just_past_both_thresholds(self):
        assert sdes_ice_teardown(last_media=100.0, last_ice_answer=100.0,
                                 now=106.01, media_gap=3.0, ice_gap=6.0) is True

    def test_thresholds_are_tunable(self):
        assert sdes_ice_teardown(100.0, 100.0, 104.0, media_gap=1.0, ice_gap=2.0) is True


class TestFailsSafe:
    @pytest.mark.parametrize("last_media,last_ice", [(0.0, 100.0), (100.0, 0.0), (0.0, 0.0)])
    def test_an_unstarted_clock_never_reports_teardown(self, last_media, last_ice):
        """Before first media, or with no ICE timestamp plumbed through, the
        ordinary watchdog and grace period must remain in charge."""
        assert sdes_ice_teardown(last_media, last_ice, now=1e9) is False

    def test_the_802s_cliff_as_measured(self):
        """The real shape: media and answers both last seen at the cliff.

        Cliff at t=80.2. The camera answered STUN at 79.9 and sent its last media
        at 80.2. By 86.5 both are long gone and this must fire - well before the
        30 s watchdog would have.
        """
        assert sdes_ice_teardown(last_media=80.2, last_ice_answer=79.9, now=86.5) is True
        assert sdes_ice_teardown(last_media=80.2, last_ice_answer=79.9, now=83.0) is False
