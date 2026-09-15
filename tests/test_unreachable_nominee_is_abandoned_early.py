"""A nominated candidate that never answers must not burn the whole wait.

Confirmed on an A001513 (camera b5284...) on 2026-09-15: its answer advertised a
single host candidate, 192.168.0.159, on the host's own /24 (Home Assistant at
192.168.0.114). ping got no reply -- a stale DHCP lease or AP client isolation
-- so the address is on-subnet but not actually reachable. _candidate_is_off_subnet
cannot catch that, because the address IS on our subnet; nothing we nominated
ever answered (zero STUN Binding Success, no media), and the session spent its
whole 75 s first-media budget on a dead address before the retry.

_no_answer_abandon_due cuts that short: once we have nominated a candidate and a
grace has passed with zero Binding Success AND no peer-reflexive candidate
learned from a relay-carried probe, abandon this attempt to the retry (whose
fresh offer is served in seconds).

Two properties are load-bearing and are pinned here:

  * it is measured from NOMINATION, not from the open, so a battery camera that
    is still waking -- whose answer has not been nominated yet -- is never
    clipped; and
  * ANY Binding Success, or a learned peer-reflexive candidate, keeps the wait
    alive, so the relay-observed-peer recovery (ROAD-TO-1.0 item 3) is preserved.
"""

from aidot_cameras.camera.sdes_open import _no_answer_abandon_due


def _due(**over):
    kw = dict(
        nominated_since_s=30.0,
        grace_s=20.0,
        binding_success=0,
        prflx_learned=False,
    )
    kw.update(over)
    return _no_answer_abandon_due(**kw)


def test_it_fires_when_nothing_answered_after_the_grace():
    assert _due() is True


def test_it_does_not_fire_before_the_grace():
    assert _due(nominated_since_s=5.0) is False


def test_a_binding_success_keeps_the_wait_alive():
    # Something answered - this is not the dead-address case.
    assert _due(binding_success=1) is False


def test_a_learned_relay_peer_keeps_the_wait_alive():
    # The relay-observed-peer recovery (item 3) is in progress; do not cut it.
    assert _due(prflx_learned=True) is False


def test_it_never_fires_before_we_have_nominated():
    # A battery camera still waking: its answer is not nominated yet, so there
    # is no dead address to give up on.
    assert _due(nominated_since_s=None) is False


def test_the_grace_is_measured_from_nomination_and_is_inclusive():
    # Exactly at the grace boundary it is due.
    assert _due(nominated_since_s=20.0) is True


def test_setting_the_grace_to_zero_disables_it():
    assert _due(grace_s=0.0, nominated_since_s=999.0) is False


def test_a_negative_grace_also_disables_it():
    assert _due(grace_s=-1.0, nominated_since_s=999.0) is False


def test_both_signals_together_still_keep_it_alive():
    assert _due(binding_success=3, prflx_learned=True) is False


# --------------------------------------------------------------------------- #
# The policy is worthless if the first-media wait never consults it, or consults
# it with the wrong signals. Guard the wiring at the source, the way the other
# bridge-thread guards do.
# --------------------------------------------------------------------------- #
def test_the_wait_loop_wires_the_abandon_with_the_right_signals():
    import inspect

    from aidot_cameras.camera import sdes_open

    src = inspect.getsource(sdes_open)

    # Timed from nomination, seeded from the pre-launch case at setup.
    assert "_nominated_at = _media_wait_started if _early_nominated else None" in src, (
        "the nomination timestamp is gone; the grace would measure from the open"
    )

    # The wait loop calls the policy (a call site, not the definition)...
    calls = [
        i
        for i in range(len(src))
        if src.startswith("_no_answer_abandon_due(", i)
        and not src[:i].rstrip().endswith("def")
    ]
    assert calls, "the first-media wait never calls _no_answer_abandon_due"

    # ...fed the bridge's binding-success count and the learned relay peers,
    # keyed on the env-configurable grace.
    call = src[calls[0] : calls[0] + 500]
    assert "_br_binding_success_count" in call, (
        "the abandon must read the bridge's binding-success count, or it cannot "
        "tell a dead address from a working one"
    )
    assert "prflx" in call, (
        "the abandon must consult the learned relay peer, or it would cut the "
        "relay-observed-peer recovery"
    )
    assert "_UNREACHABLE_NOMINEE_GRACE_S" in call
