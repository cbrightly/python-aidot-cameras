"""Unit tests for the SDES keepalive media-liveness watchdog.

The SDES ffmpeg reads RTP over UDP with no input timeout, so when a battery
camera tears the session down it never exits and wait_done() would hang forever.
SdesSession.is_stalled() is the pure policy the keepalive loop uses to decide
when to tear down and reconnect.  No camera/network needed.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.device_client import SdesSession

_W = 30.0   # watchdog
_G = 60.0   # start grace


def test_media_flowing_not_stalled():
    # last media 5s ago, well inside the 30s watchdog
    assert SdesSession.is_stalled(last_media=1000.0, started_at=900.0,
                                  now=1005.0, watchdog=_W, grace=_G) is False


def test_media_stalled_after_watchdog():
    # last media 31s ago -> camera stopped sending -> reconnect
    assert SdesSession.is_stalled(last_media=1000.0, started_at=900.0,
                                  now=1031.0, watchdog=_W, grace=_G) is True


def test_media_never_started_within_grace():
    # no media yet (0.0), only 40s since open -> still waiting, not stalled
    assert SdesSession.is_stalled(last_media=0.0, started_at=1000.0,
                                  now=1040.0, watchdog=_W, grace=_G) is False


def test_media_never_started_past_grace():
    # no media 61s after open -> dead session -> reconnect
    assert SdesSession.is_stalled(last_media=0.0, started_at=1000.0,
                                  now=1061.0, watchdog=_W, grace=_G) is True


def test_exact_boundaries_are_not_stalled():
    # strictly-greater-than comparison: exactly at the threshold is not stalled
    assert SdesSession.is_stalled(1000.0, 900.0, 1030.0, _W, _G) is False  # ==watchdog
    assert SdesSession.is_stalled(0.0, 1000.0, 1060.0, _W, _G) is False    # ==grace


if __name__ == "__main__":
    import traceback
    _fail = 0
    for _k, _v in sorted(globals().items()):
        if _k.startswith("test_"):
            try:
                _v()
                print(f"PASS {_k}")
            except Exception:
                _fail += 1
                print(f"FAIL {_k}")
                traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
