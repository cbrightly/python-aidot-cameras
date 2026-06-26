"""Open-path latency fix #1: DTLS signaling-wait skip predicate.

The DTLS open path had two adjacent blocking waits with DIFFERENT guards: the
0.5s livePlayReq-echo wait skipped only on `_fast_connect`, while the 2s
livePlayResp wait below also skipped on the default-on `dtls_fast_liveplay`. So
the default path still ate the 0.5s echo wait that "often just times out". This
unifies both behind one predicate so they can't drift apart again.

Mirrors test_sdes_fast_liveplay's __new__/opt-precedence style; no camera needed.
"""
import os
import sys

sys.path.insert(0, os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

import aidot.camera.client as cc

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_skip_dtls_signaling_wait" in v.__dict__)


def _cam():
    return _CAM.__new__(_CAM)


def test_fast_connect_always_skips(monkeypatch):
    # _fast_connect short-circuits regardless of the dtls flag.
    monkeypatch.setenv("AIDOT_DTLS_FAST_LIVEPLAY", "0")
    assert _cam()._skip_dtls_signaling_wait(True) is True


def test_default_skips_via_dtls_fast_liveplay(monkeypatch):
    # dtls_fast_liveplay is default-ON, so the default path skips the waits.
    monkeypatch.delenv("AIDOT_DTLS_FAST_LIVEPLAY", raising=False)
    assert _cam()._skip_dtls_signaling_wait(False) is True


def test_disabled_dtls_flag_keeps_wait(monkeypatch):
    # With the flag explicitly off and no fast_connect, the waits run.
    monkeypatch.setenv("AIDOT_DTLS_FAST_LIVEPLAY", "off")
    assert _cam()._skip_dtls_signaling_wait(False) is False


def test_per_camera_opt_wins(monkeypatch):
    # _dtls_fast_liveplay_opt overrides env, same precedence as the resolver.
    monkeypatch.setenv("AIDOT_DTLS_FAST_LIVEPLAY", "1")
    cam = _cam()
    cam._dtls_fast_liveplay_opt = False
    assert cam._skip_dtls_signaling_wait(False) is False
