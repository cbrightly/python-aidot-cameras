"""The codec-order campaign has to be balanced, interleaved, and receipted.

Item 2's 5x bitrate gap has one untried lever left - the offer's video codec
order - and the experiment it asks for is two arms, `97,96` against unset,
*alternating* rather than blocked, because the A001064's own bitrate varies
839-3698 Kbps between sessions and blocked arms would measure time of day.

Three ways that experiment can quietly fail to be the experiment:

  * the loop stops on the first PASS, so a working camera only ever sees the
    first arm and the design is blocked after all;
  * the empty control arm is dropped as falsy, so both arms are pins;
  * the order never reaches the SDP. That is not hypothetical - the one time
    this project pinned the codec it "looked like a confirmed result for two
    sessions before a missing receipt showed it had never reached the SDP".

These lock the first two directly and the third by carrying the offer's own
receipt per attempt.
"""
import logging
import os
import sys

sys.path.insert(0, os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "scripts"))

from live_validate import _ReceiptCollector, _apply_pt_order, _parse_arms

_KEY = "AIDOT_SDES_VIDEO_PT_ORDER"


def test_the_empty_control_arm_survives_parsing():
    # "|97,96" is control-then-pin. Dropping the empty leading arm would make
    # both arms pins and delete the control.
    assert _parse_arms("|97,96") == ["", "97,96"]


def test_arms_are_separated_by_a_pipe_because_an_arm_contains_commas():
    assert _parse_arms("97,96|96,97") == ["97,96", "96,97"]


def test_no_spec_means_no_campaign():
    assert _parse_arms("") == []


def test_applying_an_arm_sets_the_variable_the_library_reads():
    old = os.environ.get(_KEY)
    try:
        _apply_pt_order("97,96")
        assert os.environ[_KEY] == "97,96"
    finally:
        os.environ.pop(_KEY, None)
        if old is not None:
            os.environ[_KEY] = old


def test_the_control_arm_clears_the_variable_rather_than_setting_it_empty():
    # An empty string is not the same as unset: the library treats "" as "no
    # preference expressed" only if it never sees the variable at all, and a
    # control arm that leaves a stale pin behind measures the previous arm.
    old = os.environ.get(_KEY)
    try:
        os.environ[_KEY] = "97,96"
        _apply_pt_order("")
        assert _KEY not in os.environ
    finally:
        os.environ.pop(_KEY, None)
        if old is not None:
            os.environ[_KEY] = old


def _record(msg: str) -> logging.LogRecord:
    return logging.LogRecord(
        name="aidot_cameras.camera.webrtc_open", level=logging.INFO,
        pathname=__file__, lineno=1, msg="%s", args=(msg,), exc_info=None)


def test_the_offer_receipt_is_captured_so_a_null_result_can_be_trusted():
    # handle(), not emit(). emit() skips the handler's own level check, and
    # calling it directly is what let this ship collecting nothing: the receipt
    # is an INFO line and the collector inherited a WARNING level, so the first
    # real campaign returned six receipts in the log and None in the artifact.
    c = _ReceiptCollector()
    c.handle(_record("webrtc: SDES: offer video codec order=97 96"))
    assert c.drain() == ["97 96"]


def test_the_collector_accepts_info_because_the_receipt_is_an_info_line():
    c = _ReceiptCollector()
    assert c.level <= logging.INFO, (
        "a handler whose level is above the line it collects silently collects "
        "nothing, and a campaign cannot tell that from a null result")


def test_unrelated_lines_are_not_mistaken_for_a_receipt():
    c = _ReceiptCollector()
    c.handle(_record("webrtc: SDES: sent RTCP PLI"))
    assert c.drain() == []


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
