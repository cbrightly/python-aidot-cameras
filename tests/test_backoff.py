"""Unit tests for the jittered exponential-backoff helper.

next_backoff() is the pure reconnect-delay policy the camera keepalive/serve
loops use so a degraded camera (or a fleet reconnecting at once) is met with
spread-out, escalating retries instead of a synchronized storm. Randomness is
injected so the bounds are deterministic. No camera/network needed.
"""
import os
import sys

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

from aidot.camera.protocol import next_backoff

_BASE = 10.0
_CAP = 300.0


def _lo():  # rand() -> 0.0 : the low end of the jitter window
    return 0.0


def _hi():  # rand() -> ~1.0 : the high end of the jitter window
    return 1.0


def test_attempt_zero_is_exactly_base():
    # First retry must preserve the loops' existing minimum spacing.
    assert next_backoff(0, base=_BASE, cap=_CAP, rand=_lo) == _BASE
    assert next_backoff(0, base=_BASE, cap=_CAP, rand=_hi) == _BASE


def test_floor_is_always_base():
    # Low end of the window never dips below base, at any attempt.
    for a in range(0, 12):
        assert next_backoff(a, base=_BASE, cap=_CAP, rand=_lo) >= _BASE


def test_ceiling_grows_exponentially_until_cap():
    # rand()->1 gives the ceiling: base*2**attempt, capped at cap.
    assert next_backoff(1, base=_BASE, cap=_CAP, rand=_hi) == 20.0
    assert next_backoff(2, base=_BASE, cap=_CAP, rand=_hi) == 40.0
    assert next_backoff(3, base=_BASE, cap=_CAP, rand=_hi) == 80.0


def test_ceiling_saturates_at_cap():
    # 10*2**5 = 320 > cap(300) -> saturates at cap.
    assert next_backoff(5, base=_BASE, cap=_CAP, rand=_hi) == _CAP
    assert next_backoff(50, base=_BASE, cap=_CAP, rand=_hi) == _CAP


def test_delay_within_bounds_for_real_rng():
    # With the real RNG every draw stays in [base, min(cap, base*2**attempt)].
    for a in range(0, 10):
        ceiling = min(_CAP, _BASE * (2 ** a))
        for _ in range(200):
            d = next_backoff(a, base=_BASE, cap=_CAP)
            assert _BASE <= d <= ceiling


def test_large_attempt_does_not_overflow():
    # A long-lived failing camera can drive attempt very high; the exponent is
    # clamped so base*2**attempt never overflows float64.
    assert next_backoff(5000, base=_BASE, cap=_CAP, rand=_hi) == _CAP
    assert next_backoff(5000, base=_BASE, cap=_CAP, rand=_lo) == _BASE


def test_negative_attempt_treated_as_zero():
    assert next_backoff(-3, base=_BASE, cap=_CAP, rand=_lo) == _BASE


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
