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


def test_cooldown_is_kept_when_a_slot_may_be_held(lv):
    """PASS/NO_MEDIA/BUSY all mean a session existed - the wait still applies.

    NO_MEDIA matters most here: signaling completed, so the camera opened a
    session on its side even though nothing arrived. Skipping the wait there
    would reopen against a camera still holding its own slot.
    """
    for verdict in ("PASS", "NO_MEDIA", "BUSY"):
        assert lv._cooldown_after(verdict, 180.0) == 180.0, verdict


def test_cooldown_is_skipped_after_a_camera_that_never_answered(lv):
    """ERROR means no session was opened, so there is no slot to release."""
    assert lv._cooldown_after("ERROR", 180.0) == lv.SLOTLESS_COOLDOWN_S
    assert lv._cooldown_after("ERROR", 180.0) < 180.0


def test_short_cooldown_is_not_zero(lv):
    """Back-to-back cloud signaling on one account is its own contention."""
    assert lv.SLOTLESS_COOLDOWN_S > 0


def test_cooldown_respects_a_shorter_configured_value(lv):
    """--cooldown below the slotless floor must not be silently lengthened.

    This only shortens waits. A caller who passes --cooldown 5 for a quick local
    sweep should not get 10 s because a camera errored.
    """
    assert lv._cooldown_after("PASS", 5.0) == 5.0
    assert lv._cooldown_after("ERROR", 5.0) == 5.0, "must never exceed --cooldown"


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


# --------------------------------------------------------------------------- #
# absent-camera early exit
# --------------------------------------------------------------------------- #

def test_slotless_budget_is_smaller_than_the_dtls_budget(lv):
    """The early exit must actually save attempts, or it is decoration."""
    assert lv.SLOTLESS_MAX_ATTEMPTS < lv.ATTEMPTS_DTLS


def test_one_retry_is_still_allowed_for_a_single_missed_response(lv):
    """A camera silent ONCE may just have missed a webrtcResp on a busy account.

    Dropping to a single attempt would turn one unlucky timeout into a failed
    release, so the budget must be >1.
    """
    assert lv.SLOTLESS_MAX_ATTEMPTS > 1


def test_only_the_no_session_verdict_is_treated_as_absent(lv):
    """ERROR alone means no session was opened, so nothing was probabilistic.

    NO_MEDIA and BUSY DID reach the camera - those are exactly the flaky cases
    the retry budget exists for, and they must keep their full allowance.
    """
    assert lv._SLOTLESS_VERDICTS == frozenset({"ERROR"})
    for reached_the_camera in ("PASS", "NO_MEDIA", "BUSY"):
        assert reached_the_camera not in lv._SLOTLESS_VERDICTS


def test_a_camera_that_reached_us_once_keeps_its_full_budget(lv):
    """The early exit requires EVERY attempt to be slotless.

    A camera that returns NO_MEDIA then ERROR is flaky, not absent, and must not
    be cut short - which is why the loop checks all() rather than the last one.
    """
    mixed = [{"verdict": "NO_MEDIA"}, {"verdict": "ERROR"}]
    assert not all(a["verdict"] in lv._SLOTLESS_VERDICTS for a in mixed)

    absent = [{"verdict": "ERROR"}, {"verdict": "ERROR"}]
    assert all(a["verdict"] in lv._SLOTLESS_VERDICTS for a in absent)


def test_an_absent_camera_still_reaches_a_failing_verdict(lv, tmp_path):
    """Skipping attempts must not soften the verdict - absent is still ERROR.

    The saving is wall clock, not leniency: a required model with no working
    camera must still fail the release.
    """
    report = {"cameras": [_cam(lv, "LK.IPC.A000088", "ERROR", name="Deck")]}
    report["cameras"][0]["attempts_used"] = lv.SLOTLESS_MAX_ATTEMPTS
    assert lv._summarize(report, _args(tmp_path)) == 1
    assert report["verdict"] == "FAIL"
    assert report["required_failed"] == ["Deck"]
