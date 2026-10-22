"""Unit tests for _resolve_sdes_serve_audio - SDES serve audio (default ON).

Locks the opt/env precedence (per-camera kwarg wins over AIDOT_SDES_SERVE_AUDIO,
default on for app-parity) so the wiring is verifiable without a camera.

The default was briefly OFF because mapping audio served no video at all - the
audio m-line advertised PCMU while the bridge sends PCMA, so ffmpeg discarded
every audio packet and the mpegts mux never wrote its PAT/PMT. The audio payload
type is narrowed now, with a video-only fallback when it cannot be characterised,
so audio can no longer cost the picture and the default is back on. The narrowing
itself is covered by test_sdp_payload_type_narrowing.py.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc

_CAM = next(
    v
    for v in vars(cc).values()
    if isinstance(v, type) and "_resolve_sdes_serve_audio" in v.__dict__
)


def _cam():
    return _CAM.__new__(_CAM)


def test_default_on_for_app_parity(monkeypatch):
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
    cam._sdes_audio_opt = False  # start_keepalive(sdes_audio=False)
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
        def setenv(self, k, v):
            os.environ[k] = v

        def delenv(self, k, raising=False):
            os.environ.pop(k, None)

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


def test_gain_default_is_per_path(monkeypatch):
    """The ffmpeg serve keeps -8 on raw A-law; the direct publisher, which
    normalizes the level first and applies this after, asks for 0."""
    monkeypatch.delenv("AIDOT_SDES_AUDIO_GAIN_DB", raising=False)
    assert _cam()._resolve_sdes_audio_gain_db() == -8.0
    assert _cam()._resolve_sdes_audio_gain_db(default=0.0) == 0.0


def test_gain_bad_value_falls_back_to_the_asked_default(monkeypatch):
    monkeypatch.setenv("AIDOT_SDES_AUDIO_GAIN_DB", "loud")
    assert _cam()._resolve_sdes_audio_gain_db(default=0.0) == 0.0


def test_gain_explicit_setting_wins_on_either_path(monkeypatch):
    monkeypatch.delenv("AIDOT_SDES_AUDIO_GAIN_DB", raising=False)
    cam = _cam()
    cam._sdes_audio_gain_opt = -8.0
    assert cam._resolve_sdes_audio_gain_db(default=0.0) == -8.0
    monkeypatch.setenv("AIDOT_SDES_AUDIO_GAIN_DB", "-4")
    assert _cam()._resolve_sdes_audio_gain_db(default=0.0) == -4.0


def test_the_direct_publisher_asks_for_the_zero_default():
    """Measured 2026-10-14: with the -8 trim on raw A-law two cameras published
    digital zero. The direct publisher conditions the level first, so the trim
    it is handed defaults to no offset."""
    import ast

    from tests.ast_guards import calls_to, name_of, sdes_open_family

    family = sdes_open_family()
    publishers = [c for fn in family for c in calls_to(fn, "LoopbackRtpPublisher")]
    assert publishers, "the open no longer builds the direct publisher"
    for pub in publishers:
        gain = {k.arg: k.value for k in pub.keywords}.get("audio_gain_db")
        assert isinstance(gain, ast.Name), "the publisher's trim must be resolved"
        sources = [
            n.value
            for fn in family
            for n in ast.walk(fn)
            if isinstance(n, ast.Assign)
            and any(getattr(t, "id", None) == gain.id for t in n.targets)
        ]
        assert sources and all(
            name_of(s) == "_resolve_sdes_audio_gain_db"
            and [ast.unparse(k) for k in s.keywords] == ["default=0.0"]
            for s in sources
        ), [ast.unparse(s) for s in sources]
