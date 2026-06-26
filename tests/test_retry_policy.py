"""Unit 3: media-decline fast-retry policy — _retry_policy.

(delay_seconds, bypass_open_gate) for the DTLS serve retry. A clean 'not_ready'
decline gets a bounded fast burst that bypasses the 15s gate, then falls back.
"""
from aidot.device_client import _retry_policy


def test_not_ready_uses_burst_then_falls_back():
    for a in range(1, 5):  # attempts 1..4 -> fast burst, bypass the gate
        assert _retry_policy("not_ready", a) == (3.0, True)
    assert _retry_policy("not_ready", 5) == (15.0, False)  # fall back to gate


def test_hard_failure_unchanged():
    assert _retry_policy("hard_failure", 1) == (15.0, False)
    assert _retry_policy("hard_failure", 9) == (15.0, False)


def test_custom_bounds():
    assert _retry_policy("not_ready", 2, burst_delay=2.0, burst_max=2) == (2.0, True)
    assert _retry_policy("not_ready", 3, burst_delay=2.0, burst_max=2) == (15.0, False)
