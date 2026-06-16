"""Unit tests for _resolve_sdes_fast_liveplay - the P5 flag (EXPERIMENTAL).

P5 skips only the ~2s livePlayResp wait for SDES cameras. This locks the
opt/env precedence (per-camera kwarg wins over AIDOT_SDES_FAST_LIVEPLAY env,
default off) so the wiring is verifiable without a camera. The actual stability
effect is validated by real-world soak (see scripts/sdes_soak_monitor.py), not
here.
"""
import os
import sys

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

import aidot.camera.client as cc

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_resolve_sdes_fast_liveplay" in v.__dict__)


def _cam():
    return _CAM.__new__(_CAM)


def test_default_off(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_FAST_LIVEPLAY", raising=False)
    assert _cam()._resolve_sdes_fast_liveplay() is False


def test_env_truthy_values(monkeypatch):
    for val in ("1", "true", "TRUE", "yes", "on", " On "):
        monkeypatch.setenv("AIDOT_SDES_FAST_LIVEPLAY", val)
        assert _cam()._resolve_sdes_fast_liveplay() is True, val


def test_env_falsey_values(monkeypatch):
    for val in ("0", "false", "no", "off", ""):
        monkeypatch.setenv("AIDOT_SDES_FAST_LIVEPLAY", val)
        assert _cam()._resolve_sdes_fast_liveplay() is False, val


def test_kwarg_option_wins_over_env(monkeypatch):
    monkeypatch.setenv("AIDOT_SDES_FAST_LIVEPLAY", "1")
    cam = _cam()
    cam._sdes_fast_liveplay_opt = False  # start_keepalive(sdes_fast_liveplay=False)
    assert cam._resolve_sdes_fast_liveplay() is False

    cam2 = _cam()
    monkeypatch.delenv("AIDOT_SDES_FAST_LIVEPLAY", raising=False)
    cam2._sdes_fast_liveplay_opt = True
    assert cam2._resolve_sdes_fast_liveplay() is True


if __name__ == "__main__":
    import traceback

    class _MP:
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
