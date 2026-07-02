"""Unit tests for _resolve_idle_secs - the serve idle-release policy (P1).

The no-viewer idle window decides when a warm WebRTC session is torn down. A
per-camera ``stream_idle_s`` (from start_keepalive) overrides AIDOT_STREAM_IDLE_S
(default 120 s); <= 0 means never release (keep mains cameras warm for instant
re-views). No network/camera: drives a bare instance.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot.camera.client as cc

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_resolve_idle_secs" in v.__dict__)


def _cam():
    return _CAM.__new__(_CAM)


def test_default_is_120_when_unset(monkeypatch):
    monkeypatch.delenv("AIDOT_STREAM_IDLE_S", raising=False)
    assert _cam()._resolve_idle_secs() == 120.0


def test_env_override(monkeypatch):
    monkeypatch.setenv("AIDOT_STREAM_IDLE_S", "300")
    assert _cam()._resolve_idle_secs() == 300.0


def test_kwarg_option_wins_over_env(monkeypatch):
    monkeypatch.setenv("AIDOT_STREAM_IDLE_S", "300")
    cam = _cam()
    cam._stream_idle_opt = 45.0  # what start_keepalive(stream_idle_s=45) sets
    assert cam._resolve_idle_secs() == 45.0


def test_zero_means_never_release():
    cam = _cam()
    cam._stream_idle_opt = 0.0
    # The serve loops treat <= 0 as "never release"; the resolver just returns it.
    assert cam._resolve_idle_secs() == 0.0


def test_bad_env_falls_back_to_120(monkeypatch):
    monkeypatch.setenv("AIDOT_STREAM_IDLE_S", "not-a-number")
    assert _cam()._resolve_idle_secs() == 120.0


if __name__ == "__main__":
    import traceback

    class _MP:  # minimal monkeypatch stand-in for direct `python` runs
        def setenv(self, k, v): os.environ[k] = v
        def delenv(self, k, raising=False): os.environ.pop(k, None)

    _fail = 0
    for _k, _v in sorted(globals().items()):
        if _k.startswith("test_"):
            try:
                _v(_MP()) if _v.__code__.co_argcount else _v()
                print(f"PASS {_k}")
            except Exception:
                _fail += 1
                print(f"FAIL {_k}")
                traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
