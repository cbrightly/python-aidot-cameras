"""The SDES webrtcReq-echo wait was 2.0 s of measured dead time.

Only role-reversal models (A001064) ever took it - A001513-class cameras run
with skip_liveplay True and already waited 0.0 s. Across 61 SDES opens logged
over 18 hours on 2026-09-03/04, the 17 that took the wait timed out 17/17 at a
mean of 2.086 s, and `camera webrtcReq echo received` and `webrtcResp sent
(SDES` appear ZERO times in that whole log. The webrtcResp the wait exists to
build was never built once, and every one of those opens streamed anyway.

Same shape, and the same remedy, as the livePlayReq echo wait: shorten it
rather than remove it, so a camera that does echo promptly still gets its
webrtcResp - see test_liveplay_echo_timeout.py.

Replaces test_sdes_echo_wait_timeout.py, which pinned the 2.0 s on the premise
that role-reversal models "need the resulting webrtcResp". The model ->
skip_liveplay mapping it cross-referenced is still covered by
test_sdes_fast_liveplay (A001064 -> False, A001513 -> True); this file locks
skip_liveplay -> wait timeout.
"""

import inspect

from aidot_cameras.camera.sdes_open import (
    _SDES_ECHO_WAIT_S,
    _sdes_echo_wait_timeout,
)


def test_a_camera_that_never_echoes_still_waits_nothing():
    """A001513-class, skip_liveplay True. Unchanged."""
    assert _sdes_echo_wait_timeout(True) == 0.0


def test_the_role_reversal_wait_is_short_enough_not_to_dominate_the_connect():
    """2.0 s was 45 percent of a 4.2 s cold A001064 connect, spent on an echo
    that arrived 0 times in 17."""
    assert _sdes_echo_wait_timeout(False) == _SDES_ECHO_WAIT_S
    assert 0 < _SDES_ECHO_WAIT_S <= 0.5


def test_the_path_is_shortened_not_removed():
    """Zero would delete the webrtcResp branch for every camera, including one
    that echoes fast. The evidence is that the wait times out, not that the
    branch is wrong."""
    assert _sdes_echo_wait_timeout(False) > 0


def test_it_is_overridable_without_a_release():
    """The other measured waits on this path carry an env override so an arm
    can be flipped on the box without shipping a build."""
    src = inspect.getsource(inspect.getmodule(_sdes_echo_wait_timeout))
    assert "AIDOT_SDES_ECHO_WAIT_S" in src


def test_the_timeout_is_reported_rather_than_swallowed():
    """`except TimeoutError: pass` is how 2.0 s of dead time stayed invisible
    for as long as it did. The other waits on this path log an elapsed line."""
    from aidot_cameras.camera.client import CameraMixin

    src = inspect.getsource(CameraMixin._open_sdes_stream_impl)
    assert "webrtcReq-echo elapsed" in src
