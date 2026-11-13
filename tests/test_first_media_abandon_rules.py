"""The first-media wait has two early-abandon timers.

(a) The battery stale-offer timer, counted from the camera's first sighting
    (test_battery_stale_offer_is_abandoned_early.py).
(b) No media 20 s after the camera's answer - here.

(b) replaced three rules that shared the 20 s and differed only in what they
timed from (nomination, the answer, the LIVING send) and which stall they could
see. Each shape was measured on an A001513 and ran the full 75 s budget while
the next attempt served in seconds:

  * unreachable-nominee (2026-09-15): the answer carried only an address the
    camera dozed behind; nothing answered a connectivity check.
  * no-probe (2026-10-08): the camera answered and never probed, so nothing was
    ever nominated.
  * trigger-unacked (2026-10-08): two Binding Successes, LIVING sent and never
    acked, no media.

The shape still reaches the stall report, so the distinction survives as text.
"""

import ast

import pytest

from aidot_cameras.camera.sdes_open import (
    _NO_MEDIA_GRACE_S,
    _NO_MEDIA_STATUS,
    _NO_PROBE_GRACE_S,
    _no_media_abandon_due,
    _no_media_shape,
    _no_probe_abandon_due,
)


@pytest.mark.parametrize(
    ("since", "pkts", "expect"),
    [
        # No answer yet: a waking battery camera, never clipped.
        (None, 0, False),
        (19.9, 0, False),  # inside the grace
        (20.0, 0, True),  # inclusive at the boundary
        (75.0, 0, True),
        (20.0, 3, False),  # some media (audio) arrived: the wait ends on its own
    ],
)
def test_the_no_media_timer(since, pkts, expect):
    assert (
        _no_media_abandon_due(answered_since_s=since, grace_s=20.0, media_pkts=pkts)
        is expect
    )


@pytest.mark.parametrize("grace", [0.0, -1.0])
def test_a_zero_or_negative_grace_disables_it(grace):
    assert (
        _no_media_abandon_due(answered_since_s=999.0, grace_s=grace, media_pkts=0)
        is False
    )


def test_the_shipped_grace_is_twenty_seconds():
    # Sized from 165 first medias, the slowest 9.5 s after webrtcReq.
    assert _NO_MEDIA_GRACE_S == 20.0


# --- the opt-in fast no-probe abandon -------------------------------------- #


def _no_probe(**over):
    kw = dict(
        answered_since_s=12.0,
        grace_s=10.0,
        no_media_grace_s=20.0,
        media_pkts=0,
        nominated=False,
        probes=0,
        binding_success=0,
    )
    kw.update(over)
    return _no_probe_abandon_due(**kw)


def test_the_default_leaves_the_no_probe_fast_path_off():
    # Shipped equal to the no-media grace, so the fast path never fires and
    # _no_media_abandon_due ends the no-probe shape at its own grace: default
    # behaviour is unchanged.  Lower it on a weak-link camera to opt in.
    assert _NO_PROBE_GRACE_S == _NO_MEDIA_GRACE_S


@pytest.mark.parametrize("grace", [20.0, 25.0])
def test_a_grace_at_or_above_the_no_media_grace_never_fires(grace):
    # Off unless set strictly below the no-media grace: this is what keeps the
    # default a no-op.
    assert _no_probe(grace_s=grace, no_media_grace_s=20.0, answered_since_s=999.0) is False


@pytest.mark.parametrize("grace", [0.0, -1.0])
def test_a_zero_or_negative_grace_disables_the_fast_path(grace):
    assert _no_probe(grace_s=grace, answered_since_s=999.0) is False


@pytest.mark.parametrize(
    ("since", "expect"),
    [
        (None, False),  # no answer yet: a waking battery camera, never clipped
        (9.9, False),  # inside the fast grace
        (10.0, True),  # inclusive at the boundary
        (12.0, True),
    ],
)
def test_the_fast_path_times_the_no_probe_shape_from_the_answer(since, expect):
    assert _no_probe(answered_since_s=since) is expect


def test_any_media_ends_the_wait_instead_of_the_fast_abandon():
    assert _no_probe(media_pkts=1) is False


@pytest.mark.parametrize(
    "over",
    [
        {"nominated": True},  # a candidate nominated: not the no-probe shape
        {"probes": 1},  # a STUN probe seen: ICE is progressing
        {"binding_success": 1},  # a connectivity check answered
    ],
)
def test_a_progressing_open_is_never_clipped_by_the_fast_path(over):
    # Only the dead no-probe shape is abandoned early; a slow-but-progressing
    # open sends probes and gets binding success, so it is never this shape.
    assert _no_probe(**over) is False


def _shape(**over):
    kw = dict(
        nominated=False,
        probes=0,
        binding_success=0,
        prflx_learned=False,
        trigger_sent=False,
        trigger_acked=False,
    )
    kw.update(over)
    return _no_media_shape(**kw)


def test_a_camera_that_never_probed_is_no_probe():
    assert _shape() == "no-probe"


def test_a_nominee_nothing_answered_is_unreachable_nominee():
    assert _shape(nominated=True) == "unreachable-nominee"
    assert _shape(nominated=True, probes=4) == "unreachable-nominee"


def test_a_learned_relay_peer_is_not_an_unreachable_nominee():
    # The relay-observed-peer recovery was in progress: not the dead-address
    # shape, so the report must not call it one.
    assert _shape(nominated=True, prflx_learned=True) == "no-media"


def test_living_without_an_ack_is_trigger_unacked():
    assert (
        _shape(nominated=True, binding_success=2, trigger_sent=True)
        == "trigger-unacked"
    )
    # A Binding Success with no nomination record is a nomination nobody saw
    # (the trickle-fed tick); with LIVING out it is still this shape.
    assert _shape(binding_success=1, trigger_sent=True) == "trigger-unacked"


def test_everything_acted_on_and_still_no_media_is_no_media():
    assert (
        _shape(nominated=True, binding_success=2, trigger_sent=True, trigger_acked=True)
        == "no-media"
    )
    # Probes seen but nothing nominated or answered yet.
    assert _shape(probes=2, binding_success=1) == "no-media"


def test_every_shape_has_a_status_line_naming_the_grace():
    for shape in ("unreachable-nominee", "no-probe", "trigger-unacked", "no-media"):
        line = _NO_MEDIA_STATUS[shape] % 20.0
        assert "20s" in line and "abandoning this attempt to the retry" in line
        assert line.isascii()


def test_the_three_old_status_lines_are_kept_verbatim():
    """These went to the log as INFO lines before the rules were merged."""
    assert _NO_MEDIA_STATUS["unreachable-nominee"] % 20.0 == (
        "nothing answered the nominated candidate(s) in 20s - no STUN binding"
        " success and no relay-learned peer, so the address is not reachable;"
        " abandoning this attempt to the retry"
    )
    assert _NO_MEDIA_STATUS["no-probe"] % 20.0 == (
        "the camera answered 20s ago and has sent no STUN probe since - nothing"
        " to nominate, so no trigger can arm; abandoning this attempt to the retry"
    )
    assert _NO_MEDIA_STATUS["trigger-unacked"] % 20.0 == (
        "LIVING went out 20s ago on a path the camera answered, and neither an"
        " ack nor a media packet followed; abandoning this attempt to the retry"
    )


def test_the_wait_loop_times_the_rule_from_the_answer():
    """Wired with the answer timestamp, the configurable grace, and both media
    counters (TUTK-framed media leaves its branch before _br_media_pkts)."""
    from tests.ast_guards import calls_to, names_in, sdes_open_family

    calls = [
        c for fn in sdes_open_family() for c in calls_to(fn, "_no_media_abandon_due")
    ]
    assert calls, "the first-media wait never calls _no_media_abandon_due"
    for call in calls:
        kw = {k.arg: names_in(k.value) | {ast.unparse(k.value)} for k in call.keywords}
        assert "_answered_at" in kw["answered_since_s"]
        assert "_NO_MEDIA_GRACE_S" in kw["grace_s"]
        media = " ".join(kw["media_pkts"])
        assert "_br_media_pkts" in media and "_br_tutk_media" in media


def test_the_bridge_stamps_when_living_is_first_sent():
    """The shape reads this stamp: initialised to None, then set once from the
    monotonic clock where the trigger goes out."""
    from tests.ast_guards import sdes_bridge

    stamps = [
        n
        for n in ast.walk(sdes_bridge())
        if isinstance(n, ast.Assign)
        and any(
            isinstance(t, ast.Attribute) and t.attr == "_br_trigger_sent_ts"
            for t in n.targets
        )
    ]
    values = sorted(ast.unparse(n.value) for n in stamps)
    assert "None" in values, "the stamp is initialised per bridge"
    assert any(v.endswith(".monotonic()") for v in values), values


def test_tutk_framed_media_is_counted_for_the_no_media_rule():
    """TUTK SFrame media leaves the branch before _br_media_pkts is counted,
    so the branch keeps its own count - from zero, advancing from itself -
    and the loop adds it in."""
    from tests.ast_guards import attr_writes, calls_to, module_tree, sdes_bridge

    bridge = sdes_bridge()
    assert "_br_tutk_media" in attr_writes(bridge, "_bridge_fn")
    advances = [
        n
        for n in ast.walk(bridge)
        if isinstance(n, ast.Assign)
        and any(getattr(t, "attr", None) == "_br_tutk_media" for t in n.targets)
        and isinstance(n.value, ast.BinOp)
    ]
    assert advances, "the TUTK media count no longer advances"
    readers = [
        c
        for c in calls_to(module_tree("aidot_cameras.camera.sdes_open"), "getattr")
        if len(c.args) >= 2
        and ast.unparse(c.args[1]) == "'_br_tutk_media'"
        and c not in set(ast.walk(bridge))
    ]
    assert readers, "nothing outside the bridge reads the TUTK media count"


@pytest.mark.parametrize(
    "name",
    [
        "AIDOT_SDES_ECHO_WAIT_S",
        "AIDOT_SDES_UNREACHABLE_NOMINEE_GRACE_S",
        "AIDOT_SDES_ANSWER_PROBE_GRACE_S",
        "AIDOT_SDES_TRIGGER_GRACE_S",
    ],
)
def test_a_removed_knob_is_named_once_per_process(name, monkeypatch, caplog):
    import logging

    from aidot_cameras import settings

    assert settings.KNOBS[name].deprecated
    monkeypatch.setattr(settings, "_deprecated_warned", set())
    with caplog.at_level(logging.WARNING, logger=settings._LOGGER.name):
        settings.warn_deprecated_once({name: "5"})
        settings.warn_deprecated_once({name: "5"})
    lines = [r.getMessage() for r in caplog.records if "deprecated" in r.getMessage()]
    assert len(lines) == 1 and name in lines[0]


def test_nothing_is_logged_when_no_removed_knob_is_set(monkeypatch, caplog):
    import logging

    from aidot_cameras import settings

    monkeypatch.setattr(settings, "_deprecated_warned", set())
    with caplog.at_level(logging.WARNING, logger=settings._LOGGER.name):
        settings.warn_deprecated_once({})
    assert not caplog.records
    assert settings._deprecated_warned == set()


def test_the_open_checks_for_removed_knobs():
    from tests.ast_guards import calls_to, sdes_open_family

    assert [
        c for fn in sdes_open_family() for c in calls_to(fn, "warn_deprecated_once")
    ]
