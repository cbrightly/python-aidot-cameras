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

from aidot_cameras.const import CONF_ID, CONF_NAME

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


def _recorder(into):
    async def _fake_sleep(seconds):
        into.append(seconds)
    return _fake_sleep


def _patch_sleep(lv, monkeypatch):
    """Make every wait instantaneous and recorded, in order."""
    slept: list = []
    monkeypatch.setattr(lv.asyncio, "sleep", _recorder(slept))
    return slept


def _patch_attempts(lv, monkeypatch, verdicts):
    """Feed ``_validate_camera`` canned per-device verdicts, no cameras needed."""
    queues = {dev: list(vs) for dev, vs in verdicts.items()}

    async def _fake_attempt(dc, hold, out_dir, attempt, device=None, **kw):
        # **kw so a new optional argument on _attempt - pt_order for the codec
        # campaign was the second - does not fail these tests for a reason that
        # has nothing to do with the cooldown policy they exist to guard.
        queue = queues[dc.device_id]
        return {"attempt": attempt, "verdict": queue.pop(0)}

    monkeypatch.setattr(lv, "_attempt", _fake_attempt)


class _FakeDeviceClient:
    def __init__(self, device_id, model):
        self.device_id = device_id
        self.info = argparse.Namespace(model_id=model)
        self.is_sdes_camera = "A000088" not in model
        self.is_battery_camera = False


def _fleet(models):
    """({'a': 'LK.IPC.A001513', ...}) -> (fake client, {'a': device dict})."""
    devices = {
        key: {CONF_NAME: key, CONF_ID: key} for key in models
    }
    clients = {key: _FakeDeviceClient(key, model) for key, model in models.items()}

    class _FakeClient:
        def get_device_client(self, device):
            return clients[device[CONF_ID]]

    return _FakeClient(), devices


def _run_args():
    return argparse.Namespace(hold=1.0, out_dir="/tmp", cooldown=180.0,
                              json_out="")


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


# --------------------------------------------------------------------------- #
# the cooldown is owed by a DEVICE, not by the run
#
# A camera holds its viewer slot ~120 s after a session, so the wait belongs to
# the camera that just streamed.  The next camera is a different device with its
# own free slot and owes nothing.  These pin that policy: a deadline in the past
# costs zero, a deadline in the future costs exactly the remainder, and the
# same-camera retry path keeps the full wait it has always had.
# --------------------------------------------------------------------------- #

def test_a_device_never_opened_in_this_run_owes_nothing(lv):
    """The map has no entry for it, so the deadline is 0 - i.e. long past."""
    assert lv._residual_wait(0.0, 12345.0) == 0.0


def test_a_device_whose_deadline_has_passed_waits_zero(lv):
    now = 1_000.0
    assert lv._residual_wait(now - 0.5, now) == 0.0
    assert lv._residual_wait(now - 600.0, now) == 0.0


def test_a_device_inside_its_window_waits_only_the_remainder(lv):
    """Not the full cooldown again - only what is left of its own window."""
    now = 1_000.0
    not_before = now - 30.0 + 180.0  # streamed 30 s ago, 180 s cooldown
    assert lv._residual_wait(not_before, now) == pytest.approx(150.0)


def test_the_slotless_shortcut_still_shortens(lv):
    """An ERROR opened no session, so its deadline is the slotless floor."""
    now = 1_000.0
    slotless = now + lv._cooldown_after("ERROR", 180.0)
    held_slot = now + lv._cooldown_after("PASS", 180.0)
    assert lv._residual_wait(slotless, now) == lv.SLOTLESS_COOLDOWN_S
    assert lv._residual_wait(slotless, now) < lv._residual_wait(held_slot, now)


async def test_wait_until_sleeps_the_remainder_and_says_why(lv, monkeypatch,
                                                            capsys):
    """A wait that happens must be visible in the log, with its reason."""
    slept = []
    monkeypatch.setattr(lv.asyncio, "sleep", _recorder(slept))
    monkeypatch.setattr(lv.time, "monotonic", lambda: 1_000.0)

    waited = await lv._wait_until(1_000.0 + 42.0, "'Kitchen'")

    assert waited == pytest.approx(42.0)
    assert slept == [pytest.approx(42.0)]
    out = capsys.readouterr().out
    assert "42s" in out and "Kitchen" in out


async def test_wait_until_reports_the_wait_it_did_not_take(lv, monkeypatch,
                                                           capsys):
    """A silent speedup cannot be audited from a run's log afterwards.

    A log that simply stopped mentioning cooldowns reads the same whether the
    wait was skipped on purpose or dropped by accident, so the zero case has to
    say so too.
    """
    slept = []
    monkeypatch.setattr(lv.asyncio, "sleep", _recorder(slept))
    monkeypatch.setattr(lv.time, "monotonic", lambda: 1_000.0)

    waited = await lv._wait_until(0.0, "'Kitchen'")

    assert waited == 0.0
    assert slept == [], "a device that owes nothing must not sleep at all"
    assert "Kitchen" in capsys.readouterr().out


async def test_a_fresh_camera_does_not_wait_for_the_previous_one(lv, monkeypatch):
    """The whole point: camera B's slot is not affected by camera A's session."""
    slept = _patch_sleep(lv, monkeypatch)
    _patch_attempts(lv, monkeypatch, {"a": ["PASS"], "b": ["PASS"]})
    client, devices = _fleet({"a": "LK.IPC.A001513", "b": "LK.IPC.A000088"})
    cooldown_until: dict = {}

    await lv._validate_camera(client, devices["a"], _run_args(), cooldown_until)
    assert cooldown_until["a"] > 0, "a streamed camera must record its deadline"
    slept.clear()

    await lv._validate_camera(client, devices["b"], _run_args(), cooldown_until)

    assert slept == [], "a different device must not pay for camera A's slot"


async def test_the_same_device_reopened_waits_out_its_own_window(lv, monkeypatch):
    """The residual path: re-opening THIS camera still respects its slot."""
    slept = _patch_sleep(lv, monkeypatch)
    _patch_attempts(lv, monkeypatch, {"a": ["PASS", "PASS"]})
    client, devices = _fleet({"a": "LK.IPC.A001513"})
    cooldown_until: dict = {}

    await lv._validate_camera(client, devices["a"], _run_args(), cooldown_until)
    slept.clear()
    await lv._validate_camera(client, devices["a"], _run_args(), cooldown_until)

    assert len(slept) == 1, "the same device must wait out its own window"
    assert 0 < slept[0] <= 180.0


async def test_the_same_camera_retry_cooldown_is_unchanged(lv, monkeypatch):
    """Between ATTEMPTS on one camera the full wait stays - same slot.

    This is the wait the speed-up must not touch: attempt 2 reopens the very
    camera attempt 1 just used, inside its own ~120 s slot window.
    """
    slept = _patch_sleep(lv, monkeypatch)
    _patch_attempts(lv, monkeypatch, {"a": ["NO_MEDIA", "PASS"]})
    client, devices = _fleet({"a": "LK.IPC.A001513"})

    entry = await lv._validate_camera(client, devices["a"], _run_args(), {})

    assert entry["attempts_used"] == 2
    assert slept == [180.0], "a same-camera retry must keep the full cooldown"


async def test_the_retry_cooldown_still_shortens_after_a_slotless_error(lv,
                                                                        monkeypatch):
    slept = _patch_sleep(lv, monkeypatch)
    _patch_attempts(lv, monkeypatch, {"a": ["ERROR", "PASS"]})
    client, devices = _fleet({"a": "LK.IPC.A001513"})

    await lv._validate_camera(client, devices["a"], _run_args(), {})

    assert slept == [lv.SLOTLESS_COOLDOWN_S]
