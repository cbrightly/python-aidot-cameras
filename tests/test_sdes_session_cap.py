"""End a session deliberately, so measuring a rate does not cost hours.

The 80.2 s gate is decided once per session and passed ~18% of the time. Judging
a lever needs ~35 sessions per arm - but session throughput on this camera is
COUPLED TO THE OUTCOME: a session that stalls yields a fresh one every ~140 s,
while one that passes streams for tens of minutes and yields none. So the arm
that works suppresses its own sample size, and a fixed wall-clock run cannot
deliver a fixed n. The 2026-08-30 open-delay trial got 11 sessions in 3 hours
that way.

The gate resolves at 80.2 s, so a session that reaches 90 s has already passed
it. There is no information in the next twenty minutes of it. Capping at ~100 s
makes every session cost the same ~140 s whichever way it goes, lifts throughput
to ~26/hour, and removes the outcome-coupled sampling entirely.

Off by default, and SCOPED TO ONE DEVICE. That scoping is not cosmetic: an
unscoped cap hit every camera on the SDES path, including a battery one
(338603b50fce, battery=True) that normally opens about twice an hour. Capping it
forced a reconnect every ~110 s - roughly 30x its normal wake rate - which is a
real cost to a battery device that has nothing to do with the experiment.

So the cap FAILS CLOSED: a cap value that cannot be attributed to a specific
device caps nothing at all.
"""
import pytest

from aidot_cameras.camera.sdes_open import (
    _sdes_max_session_s,
    _session_cap_reached,
)


CAM = "12b144cb12da4994945bffd4f1acfd0c"
OTHER = "338603b50fce43a61785a53a944ba06e"


class TestCapValue:
    def test_off_by_default(self, tmp_path):
        assert _sdes_max_session_s(CAM, str(tmp_path / "absent")) == 0.0

    def test_read_for_the_named_device(self, tmp_path):
        f = tmp_path / "cap"; f.write_text("%s:100\n" % CAM)
        assert _sdes_max_session_s(CAM, str(f)) == 100.0

    def test_another_device_is_not_capped(self, tmp_path):
        """The battery camera on the same code path must be left alone."""
        f = tmp_path / "cap"; f.write_text("%s:100\n" % CAM)
        assert _sdes_max_session_s(OTHER, str(f)) == 0.0

    def test_a_bare_number_caps_nothing(self, tmp_path):
        """Fail closed: an unattributable cap must not hit the whole fleet."""
        f = tmp_path / "cap"; f.write_text("100\n")
        assert _sdes_max_session_s(CAM, str(f)) == 0.0
        assert _sdes_max_session_s(OTHER, str(f)) == 0.0

    def test_zero_for_the_named_device_means_off(self, tmp_path):
        f = tmp_path / "cap"; f.write_text("%s:0" % CAM)
        assert _sdes_max_session_s(CAM, str(f)) == 0.0

    @pytest.mark.parametrize("raw", ["", "   ", "nonsense", "%s:" % CAM,
                                     "%s:abc" % CAM, ":100", "%s:-5" % CAM])
    def test_unusable_content_caps_nothing(self, tmp_path, raw):
        f = tmp_path / "cap"; f.write_text(raw)
        assert _sdes_max_session_s(CAM, str(f)) == 0.0


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


class TestTheKnobIsOffInAPublishedLibrary:
    """A shipped library must not open a Home Assistant config path on the
    event loop for every session - HA's own blocking-call detector reports it,
    and a transport library has no business knowing where HA keeps its config.
    With no env var set the knob must do NO file I/O at all."""

    def test_no_file_is_opened_when_the_knob_is_unset(self, monkeypatch):
        import aidot_cameras.camera.sdes_open as m
        monkeypatch.setattr(m, "EXPT_CAP_FILE", None)
        opened = []
        real_open = open

        def _spy(*a, **k):
            opened.append(a[0] if a else None)
            return real_open(*a, **k)

        monkeypatch.setattr("builtins.open", _spy)
        assert m._sdes_max_session_s("12b144cb12da4994945bffd4f1acfd0c") == 0.0
        assert opened == [], f"opened {opened!r} with the knob off"

    def test_an_explicit_path_still_works_for_screening(self, tmp_path, monkeypatch):
        import aidot_cameras.camera.sdes_open as m
        monkeypatch.setattr(m, "EXPT_CAP_FILE", None)
        f = tmp_path / "cap"
        f.write_text("12b144cb12da4994945bffd4f1acfd0c:250")
        assert m._sdes_max_session_s(
            "12b144cb12da4994945bffd4f1acfd0c", str(f)) == 250.0
