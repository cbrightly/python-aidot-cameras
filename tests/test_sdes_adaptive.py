"""Unit tests for the SDES adaptive fast-with-fallback controller.

The adaptive controller (default on) makes "fast by default" safe regardless of
camera reachability: the SDES keepalive loop tries the fast path first (skip the
livePlay waits + TURN relay pre-allocation, short timeout/grace) and falls back
to the full relay path if a fast attempt delivers no media. These tests lock the
decision state-machine and the resolver precedence so the wiring is verifiable
without a camera; the real media/stability effect is validated by live soak.
"""
import os
import sys

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

import aidot.camera.client as cc

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_resolve_sdes_adaptive" in v.__dict__)


def _cam():
    return _CAM.__new__(_CAM)


# --- _resolve_sdes_adaptive: opt-in, default OFF --------------------------- #

def test_adaptive_default_off(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_ADAPTIVE", raising=False)
    assert _cam()._resolve_sdes_adaptive() is False


def test_adaptive_env_enables(monkeypatch):
    for val in ("1", "true", "TRUE", "yes", "on", " On "):
        monkeypatch.setenv("AIDOT_SDES_ADAPTIVE", val)
        assert _cam()._resolve_sdes_adaptive() is True, val


def test_adaptive_env_falsey_stays_off(monkeypatch):
    for val in ("0", "false", "no", "off", "", "anything"):
        monkeypatch.setenv("AIDOT_SDES_ADAPTIVE", val)
        assert _cam()._resolve_sdes_adaptive() is False, val


def test_adaptive_kwarg_wins_over_env(monkeypatch):
    monkeypatch.setenv("AIDOT_SDES_ADAPTIVE", "1")
    cam = _cam()
    cam._sdes_adaptive_opt = False  # start_keepalive(sdes_adaptive=False)
    assert cam._resolve_sdes_adaptive() is False


# --- decision state machine ------------------------------------------------ #

def test_next_fast_only_when_adaptive_and_not_failed():
    assert _CAM._adaptive_next_fast(True, False) is True
    assert _CAM._adaptive_next_fast(True, True) is False   # latched off
    assert _CAM._adaptive_next_fast(False, False) is False  # adaptive off


def test_after_attempt_latches_on_fast_nomedia():
    # a fast attempt that delivered no media latches fast_failed
    assert _CAM._adaptive_after_attempt(True, False, False) is True
    # a fast attempt that delivered media does NOT latch
    assert _CAM._adaptive_after_attempt(True, True, False) is False
    # a full attempt (use_fast False) never latches, regardless of media
    assert _CAM._adaptive_after_attempt(False, False, False) is False
    # once latched, it stays latched (no oscillation back to fast)
    assert _CAM._adaptive_after_attempt(False, True, True) is True


def test_full_lifecycle_fast_then_fallback_sticks():
    adaptive, failed = True, False
    # attempt 1: fast, no media -> latch
    use_fast = _CAM._adaptive_next_fast(adaptive, failed)
    assert use_fast is True
    failed = _CAM._adaptive_after_attempt(use_fast, False, failed)
    assert failed is True
    # attempt 2: must be full now, and stays full even after a healthy session
    use_fast = _CAM._adaptive_next_fast(adaptive, failed)
    assert use_fast is False
    failed = _CAM._adaptive_after_attempt(use_fast, True, failed)
    assert failed is True


# --- per-attempt override feeds the fast resolvers ------------------------- #

def test_override_drives_skip_turn_resolver(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_SKIP_TURN_PREALLOC", raising=False)
    cam = _cam()
    cam._fast_attempt_override = True
    assert cam._resolve_sdes_skip_turn() is True
    cam._fast_attempt_override = False
    assert cam._resolve_sdes_skip_turn() is False


def test_explicit_opt_beats_override(monkeypatch):
    # an explicit user opt must win over the adaptive per-attempt override
    cam = _cam()
    cam._sdes_skip_turn_opt = False
    cam._fast_attempt_override = True
    assert cam._resolve_sdes_skip_turn() is False


def test_cache_makes_next_view_skip_fast():
    # A camera that previously failed the fast path latches _fast_path_unavailable,
    # so the next keepalive loop starts already-failed and skips the fast attempt
    # (no repeated ~40s fast timeout per fresh view). A fresh camera tries fast.
    cached = _cam()
    cached._fast_path_unavailable = True
    init_failed = bool(getattr(cached, "_fast_path_unavailable", False))
    assert init_failed is True
    assert _CAM._adaptive_next_fast(True, init_failed) is False

    fresh = _cam()
    init_fresh = bool(getattr(fresh, "_fast_path_unavailable", False))
    assert init_fresh is False
    assert _CAM._adaptive_next_fast(True, init_fresh) is True


def test_override_respects_role_reversal_exclusion(monkeypatch):
    from types import SimpleNamespace
    monkeypatch.delenv("AIDOT_SDES_FAST_LIVEPLAY", raising=False)
    cam = _cam()
    cam.info = SimpleNamespace(model_id="LK.IPC.A001064")
    cam._fast_attempt_override = True  # adaptive would try fast...
    # ...but the role-reversal model is excluded from fast-liveplay regardless
    assert cam._resolve_sdes_fast_liveplay() is False
