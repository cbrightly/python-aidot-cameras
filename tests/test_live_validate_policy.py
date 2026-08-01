"""The release gate's own decision logic must be tested.

scripts/live_validate.py decides whether a release ships. Its pure policy
helpers - which models gate, how a run is summarized - are unit-testable
without a camera, and a mistake in them is silent: it would pass a release
that was never really validated.
"""
import argparse
import importlib.util
import json
import os
import sys

import pytest

SCRIPT = os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
    "scripts", "live_validate.py",
)


@pytest.fixture(scope="module")
def lv():
    spec = importlib.util.spec_from_file_location("live_validate", SCRIPT)
    mod = importlib.util.module_from_spec(spec)
    sys.modules["live_validate"] = mod
    spec.loader.exec_module(mod)
    return mod


def _args(tmp_path):
    return argparse.Namespace(json_out=str(tmp_path / "r.json"))


def _cam(lv, model, verdict, tier=None, name="cam"):
    return {
        "name": name, "device_id": "d", "model": model,
        "tier": tier or lv._classify(model),
        "transport": "SDES", "battery": False,
        "attempts": [], "attempts_used": 1, "verdict": verdict,
    }


def test_model_key_handles_hardware_revisions(lv):
    assert lv._model_key("LK.IPC.A000088") == "A000088"
    assert lv._model_key("LK.IPC.A000088-1") == "A000088"
    assert lv._model_key("LK.IPC.A001513") == "A001513"


def test_required_models_gate_and_unknown_models_do_not(lv):
    assert lv._classify("LK.IPC.A000088") == "required"
    assert lv._classify("LK.IPC.A001513") == "required"
    assert lv._classify("LK.IPC.A001064") == "required"
    # Recognized but never validated on hardware - must never block a release.
    assert lv._classify("LK.IPC.A001108") == "advisory"
    assert lv._classify("LK.IPC.A001360") == "advisory"
    # A model nobody has seen must not gate either.
    assert lv._classify("LK.IPC.SOMETHING_NEW") == "advisory"


def test_all_required_passing_is_a_pass(lv, tmp_path):
    report = {"cameras": [
        _cam(lv, "LK.IPC.A000088", "PASS"),
        _cam(lv, "LK.IPC.A001513", "PASS"),
        _cam(lv, "LK.IPC.A001064", "PASS"),
    ]}
    assert lv._summarize(report, _args(tmp_path)) == 0
    assert report["verdict"] == "PASS"


def test_one_required_no_media_fails(lv, tmp_path):
    report = {"cameras": [
        _cam(lv, "LK.IPC.A000088", "PASS"),
        _cam(lv, "LK.IPC.A001513", "NO_MEDIA", name="L2"),
        _cam(lv, "LK.IPC.A001064", "PASS"),
    ]}
    assert lv._summarize(report, _args(tmp_path)) == 1
    assert report["verdict"] == "FAIL"
    assert report["required_failed"] == ["L2"]


def test_busy_is_not_a_pass(lv, tmp_path):
    """BUSY means nobody validated the camera - it must not ship a release."""
    report = {"cameras": [
        _cam(lv, "LK.IPC.A000088", "PASS"),
        _cam(lv, "LK.IPC.A001513", "BUSY", name="L2"),
        _cam(lv, "LK.IPC.A001064", "PASS"),
    ]}
    assert lv._summarize(report, _args(tmp_path)) == 1
    assert "L2" in report["required_failed"]


def test_missing_required_model_fails_closed(lv, tmp_path):
    """A fleet missing a required model is NOT a validated fleet."""
    report = {"cameras": [
        _cam(lv, "LK.IPC.A000088", "PASS"),
        _cam(lv, "LK.IPC.A001513", "PASS"),
        # no A001064 present
    ]}
    assert lv._summarize(report, _args(tmp_path)) == 1
    assert report["missing_required_models"] == ["A001064"]


def test_one_healthy_camera_covers_its_model(lv, tmp_path):
    """A second camera of a covered model failing must not block the release.

    Cameras fail for reasons the code under test cannot cause - a flat battery,
    a unit powered off, an L2 too deeply asleep to wake in the window.  The gate
    exists to prove each transport/firmware path still works, and one camera
    streaming proves that.
    """
    report = {"cameras": [
        _cam(lv, "LK.IPC.A000088", "PASS"),
        _cam(lv, "LK.IPC.A001513", "PASS", name="L2_good"),
        _cam(lv, "LK.IPC.A001513", "NO_MEDIA", name="L2_asleep"),
        _cam(lv, "LK.IPC.A001064", "PASS"),
    ]}
    assert lv._summarize(report, _args(tmp_path)) == 0
    assert report["verdict"] == "PASS"
    # ...but the failure is still reported, not swallowed.
    assert report["required_failed"] == ["L2_asleep"]
    assert report["tolerated_failures"] == ["L2_asleep"]
    assert report["required_models_failed"] == []
    assert report["model_coverage"]["A001513"] == {"cameras": 2, "passed": 1}


def test_every_camera_of_a_model_failing_still_fails(lv, tmp_path):
    """Coverage is per model: zero passing cameras of a model gates the release."""
    report = {"cameras": [
        _cam(lv, "LK.IPC.A000088", "PASS"),
        _cam(lv, "LK.IPC.A001513", "NO_MEDIA", name="L2_a"),
        _cam(lv, "LK.IPC.A001513", "BUSY", name="L2_b"),
        _cam(lv, "LK.IPC.A001064", "PASS"),
    ]}
    assert lv._summarize(report, _args(tmp_path)) == 1
    assert report["verdict"] == "FAIL"
    assert report["required_models_failed"] == ["A001513"]
    # A gating failure is not "tolerated" - that list is only the masked ones.
    assert report["tolerated_failures"] == []
    assert report["model_coverage"]["A001513"] == {"cameras": 2, "passed": 0}


def test_completed_run_is_not_marked_partial(lv, tmp_path):
    """A run that reached _summarize is finished; partial must be False.

    An interim report written mid-loop carries partial=True so a run killed by
    the job timeout is readable as "unfinished" rather than as a fleet result.
    """
    report = {"cameras": [
        _cam(lv, "LK.IPC.A000088", "PASS"),
        _cam(lv, "LK.IPC.A001513", "PASS"),
        _cam(lv, "LK.IPC.A001064", "PASS"),
    ], "partial": True}
    lv._summarize(report, _args(tmp_path))
    assert report["partial"] is False
    assert json.loads((tmp_path / "r.json").read_text())["partial"] is False


def test_report_write_is_atomic_and_leaves_no_temp(lv, tmp_path):
    """Interim writes must never leave truncated JSON for the artifact upload."""
    report = {"cameras": [_cam(lv, "LK.IPC.A000088", "PASS")]}
    lv._write_report(report, _args(tmp_path), quiet=True)
    out = tmp_path / "r.json"
    assert json.loads(out.read_text())["cameras"][0]["verdict"] == "PASS"
    assert not (tmp_path / "r.json.tmp").exists(), "temp file left behind"


def test_recording_path_clears_a_stale_file_it_owns(lv, tmp_path):
    stale = tmp_path / "live_abcdef12_1.ts"
    stale.write_bytes(b"old")
    out = lv._recording_path(str(tmp_path), "abcdef1234", 1)
    assert out == str(stale)
    assert not stale.exists(), "a removable stale file should be cleared"


def test_recording_path_falls_back_when_the_stale_file_is_not_ours(lv, tmp_path,
                                                                  monkeypatch):
    """/tmp is shared and sticky: another user's leftover must not abort the run.

    This is not hypothetical - it killed a live-validation run on the
    self-hosted runner, which could not delete recordings a manual run had left
    in /tmp under a different user.
    """
    stale = tmp_path / "live_abcdef12_1.ts"
    stale.write_bytes(b"someone else's")

    def _denied(_path):
        raise PermissionError(1, "Operation not permitted")

    monkeypatch.setattr(lv.os, "remove", _denied)

    out = lv._recording_path(str(tmp_path), "abcdef1234", 1)

    assert out != str(stale), "must not reuse a path it cannot clear"
    assert str(os.getpid()) in out, "fallback should be process-unique"
    assert stale.read_bytes() == b"someone else's", "must not touch their file"


def test_advisory_failure_does_not_block(lv, tmp_path):
    report = {"cameras": [
        _cam(lv, "LK.IPC.A000088", "PASS"),
        _cam(lv, "LK.IPC.A001513", "PASS"),
        _cam(lv, "LK.IPC.A001064", "PASS"),
        _cam(lv, "LK.IPC.A001108", "NO_MEDIA", name="untested-battery"),
    ]}
    assert lv._summarize(report, _args(tmp_path)) == 0
    assert report["verdict"] == "PASS"
    assert report["advisory_failed"] == ["untested-battery"]


def test_empty_run_fails(lv, tmp_path):
    """No cameras validated must never read as success."""
    report = {"cameras": []}
    assert lv._summarize(report, _args(tmp_path)) == 1


def test_report_is_written_and_machine_readable(lv, tmp_path):
    out = tmp_path / "r.json"
    report = {"cameras": [_cam(lv, "LK.IPC.A001513", "PASS")]}
    lv._summarize(report, argparse.Namespace(json_out=str(out)))
    written = json.loads(out.read_text())
    assert written["verdict"] in ("PASS", "FAIL")
    assert written["cameras"][0]["model"] == "LK.IPC.A001513"


def test_media_seen_accepts_sdes_counters_without_on_frame(lv):
    """SDES never calls on_frame; media_stats packets must count as media."""
    class FakeSdesSession:
        def media_stats(self):
            return {"packets": 120, "bytes": 90_000, "video_pt": 96}

    ok, evidence = lv._media_seen(FakeSdesSession(), frames=0, out_path=None)
    assert ok, "SDES media must be recognized from the bridge counters"
    assert evidence["media_stats"]["packets"] == 120


def test_media_seen_reports_no_media_when_nothing_arrived(lv):
    class Silent:
        def media_stats(self):
            return {"packets": 0, "bytes": 0, "video_pt": None}

    ok, _ = lv._media_seen(Silent(), frames=0, out_path=None)
    assert not ok


def test_media_seen_accepts_dtls_frames(lv):
    ok, evidence = lv._media_seen(object(), frames=42, out_path=None)
    assert ok
    assert evidence["frames"] == 42
