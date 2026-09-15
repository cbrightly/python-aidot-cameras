"""`nominated=none` has two causes and they want different investigations.

The A001064 has been in a persistent no-media state since 2026-08-10, and its
reports all read `nominated=none; use-candidate=not-sent; probes=none`. Nothing
was nominated - but the report does not say WHY there was nothing to nominate,
and there are two answers:

  * the camera never answered at all, which is a signaling problem; or
  * it answered with no ICE candidates, which is the camera's own gathering.

One is investigated at MQTT and the cloud, the other at the camera. Reading the
wrong one first is how a fortnight goes missing, and the existing corpus already
contains a `(no ICE creds in answer)` row that nothing since has been able to
tell apart from a lost answer.

So the report carries what the answer was: absent, or present with a candidate
count. The count matters on its own - an answer with credentials and zero
candidates is a different camera state from one with three that are all
unreachable, and only the second is an ICE problem.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import _first_media_stall_report


def _report(**over):
    kwargs = dict(
        device_id="cam1",
        waited_s=75.0,
        nominated=[],
        use_candidate_sent=False,
        binding_success=0,
        trigger_sent=False,
        probes=[],
    )
    kwargs.update(over)
    return _first_media_stall_report(**kwargs)


def test_an_answer_that_never_arrived_is_named_as_absent():
    line = _report(answer_cands=None)
    assert "answer=none" in line
    assert "never answered" in line, (
        "with no answer at all the line should say so - that is a signaling "
        "failure and does not belong to ICE"
    )


def test_an_answer_with_no_candidates_is_distinguished_from_no_answer():
    line = _report(answer_cands=0)
    assert "answer=0-candidates" in line
    assert "answer=none" not in line
    assert "never answered" not in line, (
        "the camera did answer - saying otherwise sends the reader to the "
        "wrong subsystem"
    )


def test_an_answer_with_candidates_reports_how_many():
    line = _report(answer_cands=3, nominated=[("10.0.0.1", 5000)])
    assert "answer=3-candidates" in line


def test_the_absent_answer_note_is_not_emitted_when_media_simply_stalled():
    # A session that answered, nominated and still failed must not be labelled
    # a signaling failure.
    line = _report(
        answer_cands=3,
        nominated=[("10.0.0.1", 5000)],
        use_candidate_sent=True,
        binding_success=4,
        trigger_sent=True,
    )
    assert "never answered" not in line


def test_the_field_is_omitted_rather_than_guessed_when_unknown():
    # Callers that cannot determine it must not have a zero invented for them:
    # "we did not look" and "there were none" are the confusion this whole
    # module keeps having to undo.
    line = _report()
    assert "answer=" not in line


def test_the_line_is_still_one_line():
    assert "\n" not in _report(answer_cands=0)


# --------------------------------------------------------------------------- #
# The derivation, not just the rendering. The field's whole purpose is to keep
# "never answered" and "answered with nothing" apart, so the code that decides
# which one to report is where the mistake would actually be made.
# --------------------------------------------------------------------------- #
class _Fut:
    """The answer future, in the states the stall path can find it in."""

    def __init__(self, *, done=True, cancelled=False, exc=None, result=None):
        self._done, self._cancelled = done, cancelled
        self._exc, self._result = exc, result

    def done(self):
        return self._done

    def cancelled(self):
        return self._cancelled

    def exception(self):
        return self._exc

    def result(self):
        return self._result


def _derive(pre_launch, fut):
    from aidot_cameras.camera.sdes_open import _stall_answer_candidates

    return _stall_answer_candidates(pre_launch, fut)


def test_an_answer_that_arrived_with_an_empty_sdp_is_not_called_absent():
    # The trap: an SDP string that is present but empty is falsy, and treating
    # falsy as "no answer" claims the camera never replied when it did. That is
    # the exact error this field exists to prevent, made by the field itself.
    assert _derive("", _Fut(result={"sdp": ""})) == 0


def test_an_answer_with_no_candidate_lines_counts_zero():
    assert _derive("", _Fut(result={"sdp": "v=0\r\na=ice-ufrag:x\r\n"})) == 0


def test_candidates_are_counted_from_the_late_answer_too():
    sdp = "a=candidate:1 1 udp 1 10.0.0.1 1 typ host\r\na=candidate:2 1 udp 1 10.0.0.2 2 typ srflx\r\n"
    assert _derive("", _Fut(result={"sdp": sdp})) == 2


def test_the_pre_launch_snapshot_is_preferred_when_present():
    assert _derive("a=candidate:1 1 udp 1 10.0.0.1 1 typ host\r\n", None) == 1


def test_a_cancelled_answer_wait_means_the_camera_never_answered():
    assert _derive("", _Fut(done=False, cancelled=True)) is None


def test_an_answer_future_that_failed_is_reported_as_unknown():
    # An exception is not evidence the camera stayed silent - it could be ours.
    assert _derive("", _Fut(exc=RuntimeError("boom"))) == -1


def test_a_future_that_has_not_resolved_is_unknown_not_absent():
    assert _derive("", _Fut(done=False)) == -1


def test_no_future_at_all_is_unknown():
    assert _derive("", None) == -1


# --------------------------------------------------------------------------- #
# The other half of "what the answer carried": whether it had ICE credentials.
# A 0-candidate answer with credentials (the camera ran ICE and gathered
# nothing) and a 0-candidate answer with none (a malformed or empty answer) both
# render `answer=0-candidates` on the count alone, yet they want different
# subsystems. The creds flag keeps them apart, the way the count keeps
# `answer=none` apart from `answer=0-candidates`.
# --------------------------------------------------------------------------- #
def test_an_answer_with_creds_but_no_candidate_says_the_camera_gathered_nothing():
    line = _report(answer_cands=0, answer_has_creds=True)
    assert "answer=0-candidates (creds present)" in line
    assert "credentials but no candidate" in line, (
        "creds and zero candidates is the camera's own gathering, and the "
        "line should send the reader there rather than to ICE reachability"
    )


def test_an_answer_with_no_creds_is_named_a_malformed_answer():
    line = _report(answer_cands=0, answer_has_creds=False)
    assert "answer=0-candidates (no creds)" in line
    assert "no ICE credentials" in line
    assert "credentials but no candidate" not in line, (
        "the two 0-candidate shapes must not both render the same why"
    )


def test_candidates_without_creds_are_still_flagged_uncredentialled():
    # Three candidates but no ufrag/pwd is un-nominatable too, and reads
    # misleadingly as a usable answer if the creds are not called out.
    line = _report(
        answer_cands=3, answer_has_creds=False, nominated=[("10.0.0.1", 5000)]
    )
    assert "answer=3-candidates (no creds)" in line
    assert "no ICE credentials" in line


def test_a_healthy_shaped_answer_carries_no_extra_why():
    # Creds and candidates present: the failure is elsewhere, so the answer
    # field must not editorialise about the answer.
    line = _report(
        answer_cands=3, answer_has_creds=True, nominated=[("10.0.0.1", 5000)]
    )
    assert "answer=3-candidates (creds present)" in line
    assert "credentials but no candidate" not in line
    assert "no ICE credentials" not in line


def test_the_creds_flag_is_omitted_when_unknown():
    # The default path (no creds information) must render exactly as before, so
    # an old caller and the existing corpus are unchanged.
    line = _report(answer_cands=0)
    assert "answer=0-candidates" in line
    assert "(creds present)" not in line
    assert "(no creds)" not in line


def test_the_creds_annotated_line_is_still_one_line():
    assert "\n" not in _report(answer_cands=0, answer_has_creds=True)
    assert "\n" not in _report(answer_cands=0, answer_has_creds=False)


def test_the_creds_flag_never_prints_key_material():
    # This line reaches home-assistant.log; "creds present" is a boolean fact,
    # never the credential itself.
    line = _report(answer_cands=0, answer_has_creds=True)
    for banned in ("ufrag", "pwd", "inline:", "crypto", "token", "password"):
        assert banned not in line.lower()


def _derive_creds(pre_launch, fut):
    from aidot_cameras.camera.sdes_open import _stall_answer_has_creds

    return _stall_answer_has_creds(pre_launch, fut)


def test_creds_present_when_the_answer_carries_ufrag_and_pwd():
    sdp = "v=0\r\na=ice-ufrag:abcd\r\na=ice-pwd:0123456789abcdef\r\n"
    assert _derive_creds("", _Fut(result={"sdp": sdp})) is True


def test_creds_absent_when_the_answer_carries_only_one_half():
    # ufrag without pwd cannot nominate, so it is not "creds present".
    assert _derive_creds("", _Fut(result={"sdp": "a=ice-ufrag:abcd\r\n"})) is False


def test_an_empty_sdp_answer_has_no_creds_but_is_not_unknown():
    # It arrived; it simply carried nothing. False, never None - the same trap
    # the candidate count already guards against.
    assert _derive_creds("", _Fut(result={"sdp": ""})) is False


def test_creds_are_read_from_the_pre_launch_snapshot_when_present():
    sdp = "a=ice-ufrag:xy\r\na=ice-pwd:zzzzzzzzzzzzzzzz\r\n"
    assert _derive_creds(sdp, None) is True


def test_a_cancelled_wait_means_the_creds_are_unknown():
    assert _derive_creds("", _Fut(done=False, cancelled=True)) is None


def test_a_failed_future_leaves_the_creds_unknown():
    assert _derive_creds("", _Fut(exc=RuntimeError("boom"))) is None


def test_an_unresolved_future_leaves_the_creds_unknown():
    assert _derive_creds("", _Fut(done=False)) is None


def test_no_future_at_all_leaves_the_creds_unknown():
    assert _derive_creds("", None) is None


def test_the_count_and_creds_helpers_agree_on_which_answer_they_read():
    # Both must prefer the pre-launch snapshot, or one describes the late answer
    # while the other describes the snapshot and the line contradicts itself.
    pre = "a=candidate:1 1 udp 1 10.0.0.1 1 typ host\r\n"  # no creds
    late = _Fut(result={"sdp": "a=ice-ufrag:x\r\na=ice-pwd:yyyyyyyyyyyyyyyy\r\n"})
    assert _derive(pre, late) == 1  # count from the snapshot
    assert _derive_creds(pre, late) is False  # creds from the same snapshot


if __name__ == "__main__":
    import traceback

    _fns = [v for k, v in sorted(globals().items()) if k.startswith("test_")]
    _fail = 0
    for _fn in _fns:
        try:
            _fn()
            print(f"PASS {_fn.__name__}")
        except Exception:
            _fail += 1
            print(f"FAIL {_fn.__name__}")
            traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
