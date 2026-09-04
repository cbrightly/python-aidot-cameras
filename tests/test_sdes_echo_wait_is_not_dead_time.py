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

import pytest

from aidot_cameras.camera.sdes_open import _sdes_echo_wait_timeout


@pytest.fixture(autouse=True)
def _no_operator_override(monkeypatch):
    """These pin the SHIPPED default, so the env must not reach them.

    Without this the suite goes red for anyone following the README's own
    instruction to set AIDOT_SDES_ECHO_WAIT_S=2.0 - the tests below would be
    reading that operator's box rather than this repo's default.
    """
    monkeypatch.delenv("AIDOT_SDES_ECHO_WAIT_S", raising=False)


def test_a_camera_that_never_echoes_still_waits_nothing():
    """A001513-class, skip_liveplay True. Unchanged."""
    assert _sdes_echo_wait_timeout(True) == 0.0


def test_the_role_reversal_wait_is_a_quarter_second():
    """A literal, not a restatement of the implementation: the deleted test
    pinned 2.0 and that is what made this change visible."""
    assert _sdes_echo_wait_timeout(False) == 0.25


def test_the_path_is_shortened_not_removed():
    """Zero would delete the webrtcResp branch for every camera, including one
    that echoes fast. The evidence is that the wait times out, not that the
    branch is wrong."""
    assert _sdes_echo_wait_timeout(False) > 0


def test_an_operator_can_restore_the_old_wait_without_a_build(monkeypatch):
    """The README tells them to set exactly this."""
    monkeypatch.setenv("AIDOT_SDES_ECHO_WAIT_S", "2.0")
    assert _sdes_echo_wait_timeout(False) == 2.0
    assert _sdes_echo_wait_timeout(True) == 0.0


def test_the_override_is_read_per_call_not_frozen_at_import(monkeypatch):
    """Frozen at import, an operator changing it on an HA box sees nothing
    until a full restart - and the sibling knob does not behave that way."""
    monkeypatch.setenv("AIDOT_SDES_ECHO_WAIT_S", "1.0")
    assert _sdes_echo_wait_timeout(False) == 1.0
    monkeypatch.setenv("AIDOT_SDES_ECHO_WAIT_S", "0.5")
    assert _sdes_echo_wait_timeout(False) == 0.5


@pytest.mark.parametrize("bad", ["abc", "", "1,5", "None", "-1"])
def test_a_malformed_or_negative_value_falls_back_rather_than_biting(monkeypatch, bad):
    """Parsed at import this was an ImportError: one typo in an HA env var took
    down every camera, not one open. Negative reached asyncio.wait_for as a
    negative timeout AND suppressed its own diagnostic."""
    monkeypatch.setenv("AIDOT_SDES_ECHO_WAIT_S", bad)
    assert _sdes_echo_wait_timeout(False) == 0.25


def test_a_malformed_value_cannot_break_the_import(monkeypatch):
    """The failure mode this replaced, asserted directly."""
    import importlib

    monkeypatch.setenv("AIDOT_SDES_ECHO_WAIT_S", "not-a-number")
    importlib.reload(importlib.import_module("aidot_cameras.camera.sdes_open"))


def test_the_timeout_is_reported_rather_than_swallowed():
    """`except TimeoutError: pass` is how 2.0 s of dead time stayed invisible
    for as long as it did. The other waits on this path log an elapsed line."""
    import inspect

    from aidot_cameras.camera.client import CameraMixin

    src = inspect.getsource(CameraMixin._open_sdes_stream_impl)
    assert "webrtcReq-echo elapsed" in src
