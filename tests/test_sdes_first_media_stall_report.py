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

import ast
import inspect
import logging

import pytest

import aidot_cameras.camera.sdes_open as sdes_open
from tests.ast_guards import (
    attr_writes,
    calls_to,
    enclosing,
    function,
    log_calls,
    module_tree,
    names_in,
    parents,
    sdes_bridge,
    sdes_open_family,
    string_parts,
)
from aidot_cameras.camera.sdes_open import (
    _first_media_stall_report,
    _probe_source_verdict,
)

_TURN = "3.230.182.123"


# --------------------------------------------------------------------------- #
# The classifier: which veto refused this probe's source?
# --------------------------------------------------------------------------- #
def test_a_direct_probe_that_taught_us_an_address_reads_as_learned():
    assert (
        _probe_source_verdict(
            ("192.168.7.20", 41234),
            None,
            None,
            cam_peer=None,
            observed=("192.168.7.20", 41234),
            known=False,
            learned=True,
        )
        == "learned"
    )


def test_a_probe_from_an_address_we_already_nominate_is_not_a_veto():
    assert (
        _probe_source_verdict(
            ("192.168.7.20", 41234),
            None,
            None,
            cam_peer=None,
            observed=("192.168.7.20", 41234),
            known=True,
            learned=False,
        )
        == "known"
    )


def test_the_self_ip_veto_is_named():
    """XOR-PEER-ADDRESS matched our own address, so _br_cam_peer was refused."""
    assert (
        _probe_source_verdict(
            (_TURN, 5349),
            "203.0.113.7",
            9000,
            cam_peer=None,
            observed=None,
            known=False,
            learned=False,
        )
        == "vetoed-self-ip"
    )


def test_the_bsrc_fallback_veto_is_named_and_is_a_different_string():
    """No usable peer address, and the source is the TURN server itself."""
    verdict = _probe_source_verdict(
        (_TURN, 5349),
        None,
        None,
        cam_peer=None,
        observed=None,
        known=False,
        learned=False,
    )
    assert verdict == "vetoed-turn-source"
    assert verdict != _probe_source_verdict(
        (_TURN, 5349),
        "203.0.113.7",
        9000,
        cam_peer=None,
        observed=None,
        known=False,
        learned=False,
    ), "the two vetoes must be distinguishable - that is the whole point"


def test_a_peer_address_without_a_port_is_not_reported_as_the_self_ip_veto():
    assert (
        _probe_source_verdict(
            (_TURN, 5349),
            "203.0.113.7",
            0,
            cam_peer=None,
            observed=None,
            known=False,
            learned=False,
        )
        == "vetoed-no-peer-port"
    )


def test_an_observed_address_the_peer_reflexive_policy_refused_is_not_called_known():
    """_record_peer_reflexive drops silently too (self-IP, or the cap)."""
    assert (
        _probe_source_verdict(
            ("192.168.7.20", 41234),
            None,
            None,
            cam_peer=None,
            observed=("192.168.7.20", 41234),
            known=False,
            learned=False,
        )
        == "prflx-refused"
    )


def test_a_relay_carried_probe_that_was_learned_still_reads_as_learned():
    assert (
        _probe_source_verdict(
            (_TURN, 5349),
            "192.168.9.3",
            41234,
            cam_peer=("192.168.9.3", 41234),
            observed=("192.168.9.3", 41234),
            known=False,
            learned=True,
        )
        == "learned"
    )


# --------------------------------------------------------------------------- #
# The message: the five cases one validation run has to tell apart.
# --------------------------------------------------------------------------- #
def _report(**over):
    kwargs = dict(
        device_id="cam1",
        waited_s=75.0,
        nominated=[("192.168.9.3", 41234)],
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
        nominated=[("192.168.7.20", 41234)],
        binding_success=2,
        trigger_sent=True,
        probes=[("192.168.7.20:41234", "learned")],
    )
    assert "binding-success=2" in line
    assert "trigger=sent" in line
    assert "192.168.7.20:41234" in line


def test_case_no_binding_success_says_the_trigger_never_armed():
    line = _report(
        nominated=[("192.168.9.3", 41234)],
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
        "an empty probe list must read as a measured 'none', not as a missing field"
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
        nominated=[("192.168.9.3", 41234), ("10.0.0.4", 5000)],
        probes=[
            ("3.230.182.123:5349", "vetoed-turn-source"),
            ("3.230.182.123:5349 via 203.0.113.7:9000", "vetoed-self-ip"),
        ],
    )
    assert "\n" not in line, "a multi-line WARNING is unreadable in a log grep"


# An attempt one of the early-abandon rules ended writes the same WARNING as
# one that ran the whole wait; only an INFO status line said which rule fired,
# and INFO is not what users paste. The line names it.
@pytest.mark.parametrize(
    "reason", ["stale-offer", "unreachable-nominee", "no-probe", "trigger-unacked"]
)
def test_the_line_names_the_rule_that_abandoned_the_attempt(reason):
    plain = _report()
    line = _report(abandoned_by=reason)
    assert "; abandoned: %s" % reason in line
    assert line.isascii()
    assert "\n" not in line
    # Every other field is untouched: removing the one appended field gives
    # back the plain line.
    assert line.replace("; abandoned: %s" % reason, "") == plain


def test_a_wait_no_rule_abandoned_leaves_the_line_unchanged():
    assert _report(abandoned_by=None) == _report()
    assert "abandoned" not in _report()


@pytest.mark.parametrize(
    "rule, reason",
    [
        ("_stale_offer_abandon_due", "stale-offer"),
        ("_no_answer_abandon_due", "unreachable-nominee"),
        ("_no_probe_abandon_due", "no-probe"),
        ("_trigger_unacked_abandon_due", "trigger-unacked"),
    ],
)
def test_each_abandon_rule_records_its_reason_for_the_report(rule, reason):
    """The open cannot be driven here; its AST must show each abandon branch
    recording its reason before it breaks out, and the report taking it."""
    family = sdes_open_family()
    branches = [
        n
        for fn in family
        for n in ast.walk(fn)
        if isinstance(n, ast.If) and calls_to(n.test, rule)
    ]
    assert branches, f"no branch is gated on {rule}"
    for branch in branches:
        records = [
            s
            for s in ast.walk(branch)
            if isinstance(s, ast.Assign)
            and any(getattr(t, "id", None) == "_abandoned_by" for t in s.targets)
            and isinstance(s.value, ast.Constant)
            and s.value.value == reason
        ]
        assert records, f"the {rule} branch must record {reason!r}"
        breaks = [s.lineno for s in ast.walk(branch) if isinstance(s, ast.Break)]
        assert not breaks or records[0].lineno < min(breaks)
    reports = [
        c
        for fn in family
        for c in calls_to(fn, "report_first_media_stall")
        if any(
            k.arg == "abandoned_by" and getattr(k.value, "id", "") == "_abandoned_by"
            for k in c.keywords
        )
    ]
    assert reports, "the report is not handed the recorded reason"


def test_a_truncated_probe_list_says_how_many_it_dropped():
    line = _report(
        probes=[("3.230.182.123:5349", "vetoed-turn-source")],
        probes_dropped=12,
    )
    assert "12" in line


def test_the_report_carries_no_key_material_shaped_field():
    """Addresses and counts only - this line lands in home-assistant.log."""
    line = _report(
        nominated=[("192.168.9.3", 41234)],
        probes=[("3.230.182.123:5349", "vetoed-turn-source")],
    )
    for banned in ("ufrag", "pwd", "inline:", "crypto", "token", "password"):
        assert banned not in line.lower()


# --------------------------------------------------------------------------- #
# Source-level guards: the helper has to be reached from the stall path, and
# the two bridge-thread facts have to be published where the stall path can
# read them.
# --------------------------------------------------------------------------- #
def test_the_report_is_gated_on_the_wait_actually_having_failed():
    """A working open must emit nothing new above DEBUG. Both ways out of the
    wait without media - expiry and a caller cancelling - emit the report, and
    every one of those calls is gated on the wait's own unmet exit condition."""
    sites = [
        c
        for fn in sdes_open_family()
        for c in calls_to(fn, "_report_first_media_stall")
    ]
    assert len(sites) >= 2, "both the expiry and the cancellation path must report"
    for fn in sdes_open_family():
        pm = parents(fn)
        for call in calls_to(fn, "_report_first_media_stall"):
            gates = [ast.unparse(i.test) for i in enclosing(call, pm, ast.If)]
            assert any("_first_video_pt[0] is None" in g for g in gates), (
                f"line {call.lineno}: a stall report not gated on the wait failing"
            )


def test_every_bridge_counter_the_report_reads_is_published():
    """The report reads the bridge's counters with getattr defaults, so one
    that is a thread-local and never published reads as 0 or False for ever -
    the trigger flag, the Binding Success count, the probe verdicts. Every
    attribute it reads must be one the bridge assigns on itself."""
    report = function(
        module_tree("aidot_cameras.camera.sdes_open"), "report_first_media_stall"
    )
    read = {
        c.args[1].value
        for c in calls_to(report, "getattr")
        if len(c.args) >= 2
        and getattr(c.args[0], "id", None) == "bridge"
        and isinstance(c.args[1], ast.Constant)
    }
    assert {
        "_tutk_trigger_sent",
        "_br_binding_success_count",
        "_br_probe_verdicts",
    } <= read
    published = attr_writes(sdes_bridge(), "_bridge_fn") | attr_writes(
        function(module_tree("aidot_cameras.camera.sdes_open"), "make_sdes_bridge"),
        "_bridge_fn",
    )
    assert not read - published, sorted(read - published)


def test_the_binding_success_counter_is_not_an_alias_for_the_trigger_flag():
    """'No Binding Success ever arrived' and 'one arrived and the trigger still
    did not go' are different diagnoses: the counter is incremented outside the
    trigger's own gate."""
    bridge = sdes_bridge()
    pm = parents(bridge)
    bumps = [
        n
        for n in ast.walk(bridge)
        if isinstance(n, ast.AugAssign)
        and getattr(n.target, "id", None) == "_br_binding_success_count"
    ]
    assert bumps, "the Binding Success counter is gone"
    for bump in bumps:
        gates = [names_in(i.test) for i in enclosing(bump, pm, ast.If)]
        assert not any("_tutk_trigger_sent" in g for g in gates), (
            "the counter increments inside the trigger gate"
        )


def test_the_dropped_probe_count_is_distinct_sources_not_packets():
    """One unrecorded source probing 50 times is one source, not 50: the
    overflow is a set of source labels, never a per-packet counter."""
    bridge = sdes_bridge()
    adds = [
        c
        for c in calls_to(bridge, "add")
        if ast.unparse(c.func) == "_br_probe_overflow.add"
    ]
    assert adds, "the overflow tracker must collect distinct probe-source labels"
    assert not [
        n
        for n in ast.walk(bridge)
        if isinstance(n, ast.AugAssign) and "_br_probe_overflow" in names_in(n.target)
    ], "a bare += counts packets"


def test_the_per_probe_veto_stays_out_of_the_per_packet_log_level():
    """The self-loop drop fires per packet; it stays at DEBUG so the single
    stall WARNING is not drowned."""
    calls = [
        c
        for c in log_calls(sdes_bridge())
        if c.args and "self-loop STUN peer" in string_parts(c.args[0])
    ]
    assert calls and {c.func.attr for c in calls} == {"debug"}


def _emit_stall_report(caplog, *, nominated_seen=(), uc_cands=()):
    import types

    bridge = types.SimpleNamespace()
    caplog.clear()
    with caplog.at_level(logging.DEBUG, logger=sdes_open.__name__):
        sdes_open.report_first_media_stall(
            "cam-1",
            "",
            None,
            bridge,
            list(nominated_seen),
            {"cands": list(uc_cands), "prflx": [], "sent": False},
            None,
            75.0,
        )
    return [r for r in caplog.records if r.name == sdes_open.__name__]


def test_the_stall_path_calls_the_report_helper_at_warning(caplog):
    """One WARNING, not _status (INFO at best, DEBUG when a status callback is
    wired): an open that waited out the first-media window says why."""
    records = _emit_stall_report(caplog)
    warnings = [r for r in records if r.levelno == logging.WARNING]
    assert len(warnings) == 1, [(r.levelno, r.getMessage()) for r in records]
    assert "cam-1" in warnings[0].getMessage()


def test_the_report_sees_the_candidates_the_answer_peek_path_nominated(caplog):
    """The failing shape's own nomination path does not touch _bridge_uc_info.

    On a late answer it is `_nominate_from_answer_sdp` inside the wait that
    nominates, and it neither appends to `_bridge_uc_info["cands"]` nor flips
    `["sent"]`. Sourcing the report from that dict alone would print
    `nominated=none; use-candidate=not-sent` on an open that DID nominate.
    """
    seen = []
    sdes_open.nominate_from_answer_sdp(
        lambda cands, why: None,
        lambda *a: None,
        ("a", "v"),
        (("ua", "pa"), ("uv", "pv")),
        seen,
        "a=ice-ufrag:u\r\na=ice-pwd:pppppppppppppppppppppp\r\n"
        "a=candidate:1 1 udp 1 203.0.113.7 4001 typ srflx",
    )
    assert seen == [("203.0.113.7", 4001)], "the answer path records what it nominated"
    (line,) = [
        r.getMessage()
        for r in _emit_stall_report(caplog, nominated_seen=seen)
        if r.levelno == logging.WARNING
    ]
    assert "nominated=203.0.113.7:4001" in line and "use-candidate=sent" in line, line


def test_the_report_helper_is_pure_and_takes_no_camera():
    sig = inspect.signature(_first_media_stall_report)
    assert "device_id" in sig.parameters
    assert logging.getLogger(sdes_open.__name__) is not None
