"""`trigger=sent` records that WE transmitted, which the kill showed is not enough.

Run 31485643934, L2_F8A3 attempt 1: `binding-success=6; trigger=sent;
inbound-media=0; decrypt-failed=0`. So ICE completed, the trigger went out, and
not one RTP packet arrived - and the new counters rule out the reading that we
received media and could not decrypt it. The camera sent nothing.

Which leaves two possibilities, and they are ours and theirs respectively:

  * the trigger never reached the camera - it is SCTP DATA on a channel whose
    transport address we choose, so this is ours to fix; or
  * it arrived, the camera accepted it, and still sent nothing.

`0x1500` is `E_CMD_AVIO_CTRL_SESSION_MODE_REQ` in the vendor's own definitions
and `0x1501` is `..._RESP`, so the camera is expected to answer it, and its
answers already arrive on the channel this package parses. Recording whether
that answer came back separates the two without another hypothesis.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import _first_media_stall_report


def _report(**over):
    kwargs = dict(
        device_id="cam1", waited_s=75.0, nominated=[("10.0.0.1", 5000)],
        use_candidate_sent=True, binding_success=6, trigger_sent=True,
        probes=[],
    )
    kwargs.update(over)
    return _first_media_stall_report(**kwargs)


def test_an_answered_trigger_is_reported_as_acked():
    line = _report(trigger_acked=True)
    assert "trigger=sent(acked)" in line


def test_an_unanswered_trigger_is_reported_as_unacked():
    # The reading that matters: we transmitted and the camera never confirmed,
    # which points at the channel rather than at the camera.
    line = _report(trigger_acked=False)
    assert "trigger=sent(unacked)" in line


def test_a_trigger_that_was_never_sent_carries_no_ack_claim():
    # Saying "unacked" about a command that never left would invent a finding.
    line = _report(trigger_sent=False, trigger_acked=False)
    assert "trigger=not-sent" in line
    assert "acked" not in line


def test_the_ack_state_is_omitted_when_it_was_not_recorded():
    # Same rule as everywhere else in this line: absent, not guessed.
    line = _report()
    assert "trigger=sent;" in line
    assert "acked" not in line


def test_the_line_is_still_one_line():
    assert "\n" not in _report(trigger_acked=False)


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
