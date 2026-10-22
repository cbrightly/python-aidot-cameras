"""The session-cap predicate, and the removal of the measurement knob behind it.

The cap ended one device's SDES session after a set media time so a measuring
run sampled a rate rather than a handful of long sessions. Its value came from
a file named by AIDOT_EXPT_CAP_FILE; that measurement scaffolding is gone, so
the cap is always 0 (off). The predicate is still called by the keepalive loop
and stays inert at 0.
"""

import aidot_cameras.camera.sdes_open as sdes_open_mod
from aidot_cameras.camera.sdes_open import _session_cap_reached


class TestCapReached:
    def test_never_reached_when_off(self):
        assert _session_cap_reached(1000.0, 999999.0, 0.0) is False

    def test_not_reached_before_first_media(self):
        """No media yet means no session to cap - the open must not be cut short."""
        assert _session_cap_reached(None, 1000.0, 100.0) is False

    def test_not_reached_below_the_cap(self):
        assert _session_cap_reached(1000.0, 1099.9, 100.0) is False

    def test_reached_exactly_on_the_cap(self):
        assert _session_cap_reached(1000.0, 1100.0, 100.0) is True

    def test_reached_after_the_cap(self):
        assert _session_cap_reached(1000.0, 1200.0, 100.0) is True

    def test_a_cap_below_the_gate_would_cut_before_the_decision(self):
        """Guard the experiment's own logic: 80.2 s is the gate, so a cap must
        sit above it or every session reads as a stall."""
        gate = 80.2
        assert _session_cap_reached(0.0, gate, 100.0) is False
        assert _session_cap_reached(0.0, gate, 60.0) is True


class TestTheMeasurementKnobIsGone:
    def test_the_scaffolding_is_removed(self):
        assert not hasattr(sdes_open_mod, "EXPT_CAP_FILE")
        assert not hasattr(sdes_open_mod, "_sdes_max_session_s")

    def test_the_keepalive_loop_no_longer_reads_a_cap(self):
        import inspect

        from aidot_cameras.camera import client as client_mod

        src = inspect.getsource(client_mod)
        assert "_sdes_max_session_s" not in src
