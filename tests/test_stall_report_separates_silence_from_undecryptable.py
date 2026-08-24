""""The camera sent nothing" and "we could not read what it sent" are different.

Run 31448429413 produced a stall this project had written down in advance as a
kill: `binding-success=4; trigger=sent` and no media for the full 75 s. The
per-session model said media follows the LIVING trigger and the trigger follows
an inbound Binding Success, and that had held for 17 opens in both directions.
It is still necessary and it is not sufficient - something after the trigger can
fail too.

The report as it stood cannot say which something, because it reports only what
happened up to the trigger. A session where the camera sent nothing and a
session where the camera sent media that every SRTP unprotect rejected produce
byte-identical lines: media counters are gated on the packet being readable, by
design, so undecryptable packets are correctly not counted as media and then
leave no trace anywhere else.

Those two want opposite investigations - one starts at the camera, the other at
our keys - so the report now carries the raw inbound media count and the
decrypt-failure count, and says so in words when the second is non-zero.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import _first_media_stall_report


def _report(**over):
    kwargs = dict(
        device_id="cam1", waited_s=75.0,
        nominated=[("192.168.7.21", 46846)],
        use_candidate_sent=True, binding_success=4, trigger_sent=True,
        probes=[("192.168.7.21:46846", "learned")],
    )
    kwargs.update(over)
    return _first_media_stall_report(**kwargs)


def test_the_counts_are_reported_even_when_both_are_zero():
    # A measured zero is a finding; a missing field is not. The whole point of
    # this line is that every fact it needs is present at the moment it fires.
    line = _report(media_pkts=0, decrypt_fails=0)
    assert "inbound-media=0" in line
    assert "decrypt-failed=0" in line


def test_silence_after_the_trigger_reads_as_the_camera_sending_nothing():
    line = _report(media_pkts=0, decrypt_fails=0)
    assert "inbound-media=0" in line
    assert "decrypt" not in line.split("inbound-media=0")[1].split(";")[0]
    # No claim about our keys - nothing arrived to test them against.
    assert "could not be decrypted" not in line


def test_media_that_all_failed_to_decrypt_is_named_as_such():
    # The opposite finding, and the one the counters exist for: the camera DID
    # send, so the question moves from "why is it silent" to "why can we not
    # read it".
    line = _report(media_pkts=412, decrypt_fails=412)
    assert "inbound-media=412" in line
    assert "decrypt-failed=412" in line
    assert "could not be decrypted" in line, (
        "when every inbound packet failed to decrypt the line must say so - "
        "the counters alone leave the reader to join them")


def test_a_partial_decrypt_failure_is_not_reported_as_total():
    line = _report(media_pkts=412, decrypt_fails=9)
    assert "inbound-media=412" in line
    assert "decrypt-failed=9" in line
    assert "could not be decrypted" not in line, (
        "some packets decrypted, so 'we cannot read it' is the wrong reading")


def test_the_line_is_still_one_line():
    line = _report(media_pkts=412, decrypt_fails=412)
    assert "\n" not in line


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
