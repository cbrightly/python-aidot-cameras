"""Unit tests for _resolve_sdes_serve_audio - SDES serve audio (default ON).

Locks the opt/env precedence (per-camera kwarg wins over AIDOT_SDES_SERVE_AUDIO,
default on for app-parity) so the wiring is verifiable without a camera. The
silence-mix's real-world stability is covered by live soak.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot.camera.client as cc

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_resolve_sdes_serve_audio" in v.__dict__)


def _cam():
    return _CAM.__new__(_CAM)


def test_default_on(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_SERVE_AUDIO", raising=False)
    assert _cam()._resolve_sdes_serve_audio() is True


def test_env_disables(monkeypatch):
    for val in ("0", "false", "no", "off", " Off "):
        monkeypatch.setenv("AIDOT_SDES_SERVE_AUDIO", val)
        assert _cam()._resolve_sdes_serve_audio() is False, val


def test_env_truthy_or_unknown_stays_on(monkeypatch):
    for val in ("1", "true", "yes", "on", "", "anything"):
        monkeypatch.setenv("AIDOT_SDES_SERVE_AUDIO", val)
        assert _cam()._resolve_sdes_serve_audio() is True, val


def test_kwarg_option_wins_over_env(monkeypatch):
    monkeypatch.setenv("AIDOT_SDES_SERVE_AUDIO", "1")
    cam = _cam()
    cam._sdes_audio_opt = False           # start_keepalive(sdes_audio=False)
    assert cam._resolve_sdes_serve_audio() is False

    cam2 = _cam()
    monkeypatch.setenv("AIDOT_SDES_SERVE_AUDIO", "0")
    cam2._sdes_audio_opt = True
    assert cam2._resolve_sdes_serve_audio() is True


def test_gain_default(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_AUDIO_GAIN_DB", raising=False)
    assert _cam()._resolve_sdes_audio_gain_db() == -8.0


def test_gain_env(monkeypatch):
    monkeypatch.setenv("AIDOT_SDES_AUDIO_GAIN_DB", "-3.5")
    assert _cam()._resolve_sdes_audio_gain_db() == -3.5


def test_gain_opt_wins(monkeypatch):
    monkeypatch.setenv("AIDOT_SDES_AUDIO_GAIN_DB", "-3")
    cam = _cam()
    cam._sdes_audio_gain_opt = 2.0
    assert cam._resolve_sdes_audio_gain_db() == 2.0


def test_gain_bad_value_falls_back(monkeypatch):
    monkeypatch.setenv("AIDOT_SDES_AUDIO_GAIN_DB", "loud")
    assert _cam()._resolve_sdes_audio_gain_db() == -8.0


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
