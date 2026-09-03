"""The livePlayReq-echo wait is latency, and this pins its price.

The broker echoing our own livePlayReq back is what this wait is for. Across
22 h of one deployment it ran 169 times and timed out 169 times - never once
ending early - and not one inbound `livePlayReq` appeared among the 5000+
messages the cameras and broker did send. The success branch has never
executed, and on timeout the open proceeds anyway, so the wait has never
changed behaviour: only delayed it, by 44% of a measured 11.4 s
time-to-first-frame on an A001064.

Re-measured on the fast-liveplay path 2026-09-03, which had been left at 1.5 s
because the evidence above was gathered on the non-fast wait only: 22 runs,
22 timeouts at 1500-1501 ms, zero echoes. Same behaviour, so the same value -
and on a cold battery camera it was 1.25 s of a 5.4 s time-to-first-media.

The wait is kept so a broker that does echo still short-circuits it. Only the
price of its absence changed, from 5.0 s to 0.25 s.
"""
import pytest

from aidot_cameras.camera.sdes_open import (
    _LIVEPLAY_ECHO_S,
    _LIVEPLAY_ECHO_S_FAST,
    _sdes_liveplay_echo_timeout,
)


class TestTheDefault:
    def test_only_the_non_fast_path_moves(self, monkeypatch):
        monkeypatch.delenv("AIDOT_SDES_LIVEPLAY_ECHO_S", raising=False)
        assert _sdes_liveplay_echo_timeout(False) == _LIVEPLAY_ECHO_S == 0.25
        # The fast-liveplay path was held at 1.5 s only because the 169/169
        # evidence was gathered on the non-fast wait. Measured on its own
        # 2026-09-03 it behaves identically - 22 runs, 22 timeouts at
        # 1500-1501 ms, no echo ever - so it moves to the same value.
        assert _sdes_liveplay_echo_timeout(True) == _LIVEPLAY_ECHO_S_FAST == 0.25

    def test_it_is_far_below_the_old_five_seconds(self, monkeypatch):
        """The regression this guards: quietly restoring a multi-second wait for
        a message this fleet has never once received."""
        monkeypatch.delenv("AIDOT_SDES_LIVEPLAY_ECHO_S", raising=False)
        assert _sdes_liveplay_echo_timeout(False) <= 0.5


class TestTheOverride:
    @pytest.mark.parametrize("raw,expected", [("5.0", 5.0), ("0", 0.0),
                                              ("1.5", 1.5), ("0.1", 0.1)])
    def test_a_valid_value_is_honoured(self, monkeypatch, raw, expected):
        """The old behaviour has to remain reachable, or the change cannot be
        measured against it."""
        monkeypatch.setenv("AIDOT_SDES_LIVEPLAY_ECHO_S", raw)
        assert _sdes_liveplay_echo_timeout(False) == expected

    @pytest.mark.parametrize("raw", ["", "  ", "abc", "-1", "-0.5", "1,5", "None"])
    def test_a_bad_value_falls_back_instead_of_raising(self, monkeypatch, raw):
        """This runs on every open. An unparseable knob must not be able to stop
        a camera streaming."""
        monkeypatch.setenv("AIDOT_SDES_LIVEPLAY_ECHO_S", raw)
        assert _sdes_liveplay_echo_timeout(False) == _LIVEPLAY_ECHO_S

    def test_zero_disables_the_wait_entirely(self, monkeypatch):
        monkeypatch.setenv("AIDOT_SDES_LIVEPLAY_ECHO_S", "0")
        assert _sdes_liveplay_echo_timeout(True) == 0.0


class TestItIsWiredIn:
    def test_the_open_path_uses_the_resolver_not_a_literal(self):
        import inspect
        from aidot_cameras.camera import sdes_open
        src = inspect.getsource(sdes_open)
        assert "_echo_timeout = _sdes_liveplay_echo_timeout(" in src, (
            "the echo wait is back to a hardcoded timeout")
        assert "_echo_timeout = 1.5 if" not in src
