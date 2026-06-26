"""Open-path latency fix #2: the DTLS serve loop's inter-attempt gate.

The loop used to sleep a hardcoded 15s after a session died before reopening,
*in addition to* the top open-gate (keyed to the previous open's START). After a
long healthy session that top gate is already satisfied, so the extra 15s was
pure dead reconnect latency. The fix removes the bottom sleep and relies solely
on the top gate, now factored into `_open_gate_delay`.

These tests pin the gate math that makes that removal safe: a fast death still
gets spaced ≥ gate seconds from the previous open-start (anti-hammer preserved),
while a healthy long session reopens immediately.

Repo convention: pure helper, plain unit test.
"""
from aidot_cameras.camera.client import _open_gate_delay


def test_first_open_no_gate():
    # last_open_at == 0 (sentinel: no prior open) -> never gate the first open.
    assert _open_gate_delay(0.0, 100.0, 15.0) == 0.0


def test_fast_death_waits_remaining_gate():
    # open at t=100, died/looped at t=105 -> still owe 10s to honour the 15s gate.
    assert _open_gate_delay(100.0, 105.0, 15.0) == 10.0


def test_healthy_long_session_reopens_immediately():
    # open at t=100, healthy until t=400 -> gate long elapsed, reopen now.
    assert _open_gate_delay(100.0, 400.0, 15.0) == 0.0


def test_exactly_at_gate_boundary_no_wait():
    # remaining == 0 is not > 0 -> no sleep.
    assert _open_gate_delay(100.0, 115.0, 15.0) == 0.0


def test_honours_custom_gate():
    # gate is operator-tunable (AIDOT_DTLS_RETRY_GATE_S) - math must use it.
    assert _open_gate_delay(100.0, 102.0, 5.0) == 3.0
