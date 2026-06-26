"""Unit test for _sdes_echo_wait_timeout - the SDES webrtcReq-echo wait gate.

The 2.0s webrtcReq-echo wait only pays off for role-reversal models (A001064),
which echo our offer and need the resulting webrtcResp. They run with
skip_liveplay=False (hard-excluded from sdes_fast_liveplay). For A001513-class
cameras (skip_liveplay=True, the default) the echo never arrives and the
webrtcResp branch is dead, so the wait is pure dead time (~2s) and is zeroed.

The model -> skip_liveplay mapping itself is covered by test_sdes_fast_liveplay
(A001064 -> False, A001513 -> True); this locks skip_liveplay -> wait timeout.
"""
from aidot.camera.sdes_open import _sdes_echo_wait_timeout


def test_skip_liveplay_zeros_the_wait():
    assert _sdes_echo_wait_timeout(True) == 0.0


def test_no_skip_keeps_full_wait():
    assert _sdes_echo_wait_timeout(False) == 2.0
