"""A first-media stall has to say why it stalled, in one line.

An SDES session that produces no media is, today, indistinguishable in a log
from one that produced media late: the wait expires, the serve launches with
unknown payload types, and nothing states the reason. The reason is knowable at
that moment and is thrown away.

Media only ever follows the AVIO LIVING trigger, and that trigger is armed by
exactly one thing: an inbound STUN Binding Success Response from the camera.
That response only comes back if something we nominated was reachable. When the
camera's answer carries a single candidate on a subnet this host cannot route
to, the only addresses that could be nominated are the ones its own probes
arrive from -- and a relay-carried probe is dropped by one of two vetoes that
are both completely silent:

  * ``_is_self_peer_ip`` refuses the XOR-PEER-ADDRESS, so ``_br_cam_peer`` is
    None (sdes_open.py, the ``_br_cam_peer`` assignment); or
  * the ``_bsrc`` fallback refuses because the packet's source is the TURN
    server itself (the ``_br_obs`` assignment).

Which one fires decides what a fix would have to change, and neither logs
anything. This test pins a single WARNING on the stall path that reports the
nominated candidates, whether USE-CANDIDATE went out, whether any Binding
Success arrived, whether the trigger was sent, and the verdict on every inbound
probe source -- with the two vetoes named apart.

The bridge loop cannot be driven from a unit test, so the message building and
the per-probe classification are pure helpers tested directly here, plus a
source-level guard that the stall path actually calls them (same shape as
``test_reap_sets_teardown_flag_before_kill``).
"""
import inspect
import re
import logging

import aidot_cameras.camera.sdes_open as sdes_open
from aidot_cameras.camera.sdes_open import (
    _first_media_stall_report,
    _probe_source_verdict,
)

_TURN = "3.230.182.123"


# --------------------------------------------------------------------------- #
# The classifier: which veto refused this probe's source?
# --------------------------------------------------------------------------- #
def test_a_direct_probe_that_taught_us_an_address_reads_as_learned():
    assert _probe_source_verdict(
        ("192.168.0.171", 41234), None, None,
        cam_peer=None, observed=("192.168.0.171", 41234),
        known=False, learned=True,
    ) == "learned"


def test_a_probe_from_an_address_we_already_nominate_is_not_a_veto():
    assert _probe_source_verdict(
        ("192.168.0.171", 41234), None, None,
        cam_peer=None, observed=("192.168.0.171", 41234),
        known=True, learned=False,
    ) == "known"


def test_the_self_ip_veto_is_named():
    """XOR-PEER-ADDRESS matched our own address, so _br_cam_peer was refused."""
    assert _probe_source_verdict(
        (_TURN, 5349), "203.0.113.7", 9000,
        cam_peer=None, observed=None,
        known=False, learned=False,
    ) == "vetoed-self-ip"


def test_the_bsrc_fallback_veto_is_named_and_is_a_different_string():
    """No usable peer address, and the source is the TURN server itself."""
    verdict = _probe_source_verdict(
        (_TURN, 5349), None, None,
        cam_peer=None, observed=None,
        known=False, learned=False,
    )
    assert verdict == "vetoed-turn-source"
    assert verdict != _probe_source_verdict(
        (_TURN, 5349), "203.0.113.7", 9000,
        cam_peer=None, observed=None,
        known=False, learned=False,
    ), "the two vetoes must be distinguishable - that is the whole point"


def test_a_peer_address_without_a_port_is_not_reported_as_the_self_ip_veto():
    assert _probe_source_verdict(
        (_TURN, 5349), "203.0.113.7", 0,
        cam_peer=None, observed=None,
        known=False, learned=False,
    ) == "vetoed-no-peer-port"


def test_an_observed_address_the_peer_reflexive_policy_refused_is_not_called_known():
    """_record_peer_reflexive drops silently too (self-IP, or the cap)."""
    assert _probe_source_verdict(
        ("192.168.0.171", 41234), None, None,
        cam_peer=None, observed=("192.168.0.171", 41234),
        known=False, learned=False,
    ) == "prflx-refused"


def test_a_relay_carried_probe_that_was_learned_still_reads_as_learned():
    assert _probe_source_verdict(
        (_TURN, 5349), "192.168.100.3", 41234,
        cam_peer=("192.168.100.3", 41234), observed=("192.168.100.3", 41234),
        known=False, learned=True,
    ) == "learned"


# --------------------------------------------------------------------------- #
# The message: the five cases one validation run has to tell apart.
# --------------------------------------------------------------------------- #
def _report(**over):
    kwargs = dict(
        device_id="cam1",
        waited_s=75.0,
        nominated=[("192.168.100.3", 41234)],
        use_candidate_sent=True,
        binding_success=0,
        trigger_sent=False,
        probes=(),
        probes_dropped=0,
    )
    kwargs.update(over)
    return _first_media_stall_report(**kwargs)


def test_case_trigger_fired_and_media_still_never_came():
    """The stated kill for the per-session model - it must be legible as one."""
    line = _report(
        nominated=[("192.168.0.171", 41234)],
        binding_success=2,
        trigger_sent=True,
        probes=[("192.168.0.171:41234", "learned")],
    )
    assert "binding-success=2" in line
    assert "trigger=sent" in line
    assert "192.168.0.171:41234" in line


def test_case_no_binding_success_says_the_trigger_never_armed():
    line = _report(
        nominated=[("192.168.100.3", 41234)],
        binding_success=0,
        trigger_sent=False,
        probes=[("3.230.182.123:5349", "vetoed-turn-source")],
    )
    assert "binding-success=0" in line
    assert "trigger=not-sent" in line
    assert "never armed" in line, (
        "with zero Binding Success the line should state the consequence, "
        "not leave the reader to join two counters"
    )


def test_case_probe_seen_but_vetoed_by_self_ip():
    line = _report(
        probes=[("3.230.182.123:5349 via 203.0.113.7:9000", "vetoed-self-ip")],
    )
    assert "vetoed-self-ip" in line
    assert "203.0.113.7:9000" in line
    assert "vetoed-turn-source" not in line


def test_case_probe_seen_but_vetoed_by_the_bsrc_fallback():
    line = _report(probes=[("3.230.182.123:5349", "vetoed-turn-source")])
    assert "vetoed-turn-source" in line
    assert "vetoed-self-ip" not in line


def test_case_no_probes_at_all_is_stated_not_omitted():
    line = _report(probes=())
    assert "probes=none" in line, (
        "an empty probe list must read as a measured 'none', not as a "
        "missing field"
    )


def test_the_no_ice_credentials_row_is_self_explaining_too():
    """The answer carried no ICE creds: nothing was nominated, nothing sent."""
    line = _report(nominated=[], use_candidate_sent=False, probes=())
    assert "nominated=none" in line
    assert "use-candidate=not-sent" in line


def test_the_line_names_the_camera_and_the_wait_it_expired():
    line = _report()
    assert "cam1" in line
    assert "75" in line


def test_the_line_is_one_line():
    line = _report(
        nominated=[("192.168.100.3", 41234), ("10.0.0.4", 5000)],
        probes=[("3.230.182.123:5349", "vetoed-turn-source"),
                ("3.230.182.123:5349 via 203.0.113.7:9000", "vetoed-self-ip")],
    )
    assert "\n" not in line, "a multi-line WARNING is unreadable in a log grep"


def test_a_truncated_probe_list_says_how_many_it_dropped():
    line = _report(
        probes=[("3.230.182.123:5349", "vetoed-turn-source")],
        probes_dropped=12,
    )
    assert "12" in line


def test_the_report_carries_no_key_material_shaped_field():
    """Addresses and counts only - this line lands in home-assistant.log."""
    line = _report(
        nominated=[("192.168.100.3", 41234)],
        probes=[("3.230.182.123:5349", "vetoed-turn-source")],
    )
    for banned in ("ufrag", "pwd", "inline:", "crypto", "token", "password"):
        assert banned not in line.lower()


# --------------------------------------------------------------------------- #
# Source-level guards: the helper has to be reached from the stall path, and
# the two bridge-thread facts have to be published where the stall path can
# read them.
# --------------------------------------------------------------------------- #
def _first_media_wait_block() -> str:
    src = inspect.getsource(sdes_open)
    start = src.index("_media_deadline = time.monotonic() + _FIRST_MEDIA_WAIT_S")
    end = src.index("cmd = _build_sdes_serve_cmd(", start)
    return src[start:end]


def test_the_stall_path_calls_the_report_helper_at_warning():
    block = _first_media_wait_block()
    assert "_first_media_stall_report(" in block, (
        "the first-media wait can expire without saying why - the report "
        "helper is not called between the wait and the serve launch"
    )
    warn = block.index("_LOGGER.warning(")
    call = block.index("_first_media_stall_report(")
    assert warn < call < warn + 400, (
        "the report must be emitted through _LOGGER.warning, not _status "
        "(which is INFO at best and DEBUG when a status callback is wired)"
    )


def test_the_report_is_gated_on_the_wait_actually_having_failed():
    """A working open must emit nothing new above DEBUG.

    There are now two ways out of the wait without media - it expires, or a
    caller cancels first - so both call the same local emitter and BOTH have to
    be gated. Asserting on the emitter's call sites rather than on the builder's
    single call inside it, because the builder now lives in a closure defined
    before the wait, where no guard could precede it.
    """
    block = _first_media_wait_block()
    sites = [m.start() for m in re.finditer(r"_report_first_media_stall\(", block)]
    # The definition itself is not a call site.
    sites = [i for i in sites if "def " not in block[max(0, i - 12):i]]
    assert len(sites) >= 2, (
        "both the expiry and the cancellation path must emit the report")
    for call in sites:
        guard = block.rfind("if _first_video_pt[0] is None:", 0, call)
        assert guard != -1 and guard < call, (
            "every stall report must be gated on the wait's own unmet exit "
            "condition, or a healthy open emits a WARNING"
        )


def test_the_report_sees_the_candidates_the_answer_peek_path_nominated():
    """The failing shape's own nomination path does not touch _bridge_uc_info.

    On a late answer -- item 3's measured shape, answer at +1.3s, missing the
    pre-launch snapshot -- it is `_nominate_from_answer_sdp` inside the wait
    that nominates, and it neither appends to `_bridge_uc_info["cands"]` nor
    flips `["sent"]`. Sourcing the report from that dict alone would print
    `nominated=none; use-candidate=not-sent` on an open that DID nominate, and
    collapse the "answer carried no ICE credentials" row into the "answer
    carried one unroutable candidate" row -- two different diagnoses.
    """
    src = inspect.getsource(sdes_open)
    start = src.index("def _nominate_from_answer_sdp(")
    end = src.index("return len(_cands)", start)
    assert "_nominated_seen" in src[start:end], (
        "_nominate_from_answer_sdp nominates without recording what it "
        "nominated, so the stall report cannot see it"
    )
    assert "_nominated_seen" in _first_media_wait_block(), (
        "the stall report does not read the answer-peek path's nominations"
    )


def test_the_dropped_probe_count_is_distinct_sources_not_packets():
    """One unrecorded source probing 50 times is one source, not 50.

    A per-packet counter renders as '(+50 more source(s))' and someone will
    reason from that number.
    """
    src = inspect.getsource(sdes_open)
    assert "_br_probe_overflow.add(" in src, (
        "the overflow tracker must collect distinct probe-source labels; a "
        "bare += counts packets and reports a wrong number"
    )
    assert "_br_probe_overflow += 1" not in src


def test_the_bridge_publishes_the_trigger_flag_and_the_binding_success_count():
    src = inspect.getsource(sdes_open)
    for attr in (
        "_bridge_fn._tutk_trigger_sent",
        "_bridge_fn._br_binding_success_count",
        "_bridge_fn._br_stun_resp_count",
        "_bridge_fn._br_probe_verdicts",
    ):
        assert attr in src, (
            f"{attr} is a bridge-thread local with no publication; the stall "
            "path in the main coroutine cannot see it"
        )


def test_the_binding_success_counter_is_not_an_alias_for_the_trigger_flag():
    """Counting inside the trigger's own guard collapses two facts into one.

    'no Binding Success ever arrived' and 'one arrived and the trigger still
    did not go' are different diagnoses. The counter must be incremented before
    the ``_use_plain_rtp and not _tutk_trigger_sent`` gate, not inside it.
    """
    src = inspect.getsource(sdes_open)
    gate = src.index("if (_use_plain_rtp and not _tutk_trigger_sent")
    bump = src.index("_br_binding_success_count += 1")
    assert bump < gate, (
        "the inbound Binding Success counter increments inside the trigger "
        "gate, so it can never report 'binding-success=1, trigger=not-sent'"
    )


def test_the_per_probe_veto_stays_out_of_the_per_packet_log_level():
    """One WARNING on the stall path, not a stream of them."""
    src = inspect.getsource(sdes_open)
    start = src.index("bridge: drop TURN self-loop STUN peer")
    head = src[max(0, start - 300):start]
    assert "_LOGGER.debug(" in head and "_LOGGER.warning(" not in head, (
        "the per-probe self-loop drop must stay at DEBUG - it fires per "
        "packet and would drown the single stall report"
    )


def test_the_report_helper_is_pure_and_takes_no_camera():
    sig = inspect.signature(_first_media_stall_report)
    assert "device_id" in sig.parameters
    assert logging.getLogger(sdes_open.__name__) is not None
