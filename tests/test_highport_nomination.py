"""Unit tests for the A000088 two-port DTLS nomination fix - deterministic, no network.

Validates (1) the pure decision function `_highport_nomination_decision` and (2) that
`_install_highport_nomination_patch` wraps aioice's build_request so that, once >=2 remote
candidates are known, USE-CANDIDATE is FORCED onto the highest remote port and withheld
from every other - including the ICE-lite case where aioice itself passes nominate=False
(the real A000088 path). Single-candidate peers are left to aioice.

This mirrors the validated force-high prototype: the decision is over ALL remote ports
(not host-filtered, no consecutive requirement). A host-filtered variant momentarily sees
only the lower port and leaks USE-CANDIDATE onto it (measured: ~10% connect vs ~50-87%).

Runs under pytest, or standalone:  python tests/test_highport_nomination.py
"""
import os
import sys
import types

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.device_client import (
    _highport_nomination_decision,
    _install_highport_nomination_patch,
)


def _pair(host, port, component=1):
    return types.SimpleNamespace(
        component=component,
        remote_candidate=types.SimpleNamespace(host=host, port=port),
    )


# --------------------------------------------------------------------------
# 1. Pure decision logic: True=force highest, False=suppress, None=don't override
# --------------------------------------------------------------------------
def test_two_ports_force_highest_suppress_lower():
    cl = [_pair("10.0.0.5", 60500), _pair("10.0.0.5", 60501)]
    assert _highport_nomination_decision(cl, cl[0]) is False  # lower -> suppress
    assert _highport_nomination_decision(cl, cl[1]) is True   # higher -> force


def test_single_port_no_override():
    cl = [_pair("10.0.0.5", 60500)]
    assert _highport_nomination_decision(cl, cl[0]) is None


def test_non_consecutive_still_forces_highest():
    # The fix does NOT require consecutiveness - highest of >=2 ports wins.
    cl = [_pair("10.0.0.5", 60500), _pair("10.0.0.5", 60777)]
    assert _highport_nomination_decision(cl, cl[0]) is False
    assert _highport_nomination_decision(cl, cl[1]) is True


def test_decision_is_over_all_ports_not_host_filtered():
    # A lower-numbered relay/other-host candidate must NOT mask the camera's port:
    # the camera host port (60500) is the global max here, so it is forced.
    cl = [_pair("10.0.0.5", 60500), _pair("3.230.182.123", 41180)]
    assert _highport_nomination_decision(cl, cl[0]) is True    # global highest
    assert _highport_nomination_decision(cl, cl[1]) is False   # relay suppressed


def test_three_ports_only_top_forced():
    cl = [_pair("10.0.0.5", 60500), _pair("10.0.0.5", 60501),
          _pair("10.0.0.5", 60502)]
    assert _highport_nomination_decision(cl, cl[0]) is False
    assert _highport_nomination_decision(cl, cl[1]) is False
    assert _highport_nomination_decision(cl, cl[2]) is True


def test_malformed_pair_is_safe():
    bad = types.SimpleNamespace(remote_candidate=None)
    assert _highport_nomination_decision([bad, bad], bad) is None


# --------------------------------------------------------------------------
# 2. Install + end-to-end build_request behaviour (against real aioice)
# --------------------------------------------------------------------------
def _fake_conn(check_list, controlling=True, tagged=True):
    c = types.SimpleNamespace(
        ice_controlling=controlling,
        _check_list=check_list,
        remote_username="ruser",
        local_username="luser",
        remote_password="rpass",
        _tie_breaker=0x0102030405060708,
    )
    if tagged:
        # Set per DTLS-camera connect in async_open_webrtc_stream; the override
        # acts only on tagged connections.
        c._aidot_highport = True
    return c


def _use_candidate(ice, conn, pair, nominate):
    req = ice.Connection.build_request(conn, pair, nominate=nominate)
    return "USE-CANDIDATE" in req.attributes


def test_install_idempotent_and_active():
    os.environ.pop("AIDOT_DISABLE_HIGHPORT_FIX", None)
    assert _install_highport_nomination_patch() is True
    from aioice import ice
    assert getattr(ice.Connection, "_aidot_highport_patched", False) is True
    assert _install_highport_nomination_patch() is True  # idempotent


def test_lite_remote_forces_use_candidate_on_highest_only():
    # The REAL A000088 case: ICE-lite -> aioice passes nominate=False. We must
    # FORCE USE-CANDIDATE onto the highest port and keep it off the lower.
    os.environ.pop("AIDOT_DISABLE_HIGHPORT_FIX", None)
    _install_highport_nomination_patch()
    from aioice import ice
    cl = [_pair("10.0.0.5", 60500), _pair("10.0.0.5", 60501)]
    conn = _fake_conn(cl)
    assert _use_candidate(ice, conn, cl[0], nominate=False) is False  # low stays off
    assert _use_candidate(ice, conn, cl[1], nominate=False) is True   # HIGH forced on


def test_aggressive_remote_suppresses_lower_keeps_highest():
    # Non-lite case: aioice passes nominate=True on both -> suppress the lower.
    os.environ.pop("AIDOT_DISABLE_HIGHPORT_FIX", None)
    _install_highport_nomination_patch()
    from aioice import ice
    cl = [_pair("10.0.0.5", 60500), _pair("10.0.0.5", 60501)]
    conn = _fake_conn(cl)
    assert _use_candidate(ice, conn, cl[0], nominate=True) is False
    assert _use_candidate(ice, conn, cl[1], nominate=True) is True


def test_single_candidate_passthrough():
    # <2 ports -> never override aioice's own decision (both values).
    os.environ.pop("AIDOT_DISABLE_HIGHPORT_FIX", None)
    _install_highport_nomination_patch()
    from aioice import ice
    cl = [_pair("10.0.0.5", 60500)]
    conn = _fake_conn(cl)
    assert _use_candidate(ice, conn, cl[0], nominate=True) is True
    assert _use_candidate(ice, conn, cl[0], nominate=False) is False


def test_noop_when_controlled():
    # If we are NOT controlling, build_request never sets USE-CANDIDATE; confirm
    # our wrapper does not change that.
    os.environ.pop("AIDOT_DISABLE_HIGHPORT_FIX", None)
    _install_highport_nomination_patch()
    from aioice import ice
    cl = [_pair("10.0.0.5", 60500), _pair("10.0.0.5", 60501)]
    conn = _fake_conn(cl, controlling=False)
    assert _use_candidate(ice, conn, cl[1], nominate=True) is False


def test_untagged_connection_is_a_strict_noop():
    # SCOPING GUARANTEE: a connection NOT tagged `_aidot_highport` (every SDES
    # camera and non-camera device) keeps aioice's native nomination untouched -
    # the override never fires on it, even with a forceable consecutive pair.
    os.environ.pop("AIDOT_DISABLE_HIGHPORT_FIX", None)
    _install_highport_nomination_patch()
    from aioice import ice
    cl = [_pair("10.0.0.5", 60500), _pair("10.0.0.5", 60501)]
    conn = _fake_conn(cl, tagged=False)
    # Untagged -> USE-CANDIDATE follows aioice's own `nominate`, not our decision.
    assert _use_candidate(ice, conn, cl[0], nominate=True) is True   # not suppressed
    assert _use_candidate(ice, conn, cl[1], nominate=False) is False  # not forced


def test_disabled_via_env_returns_false():
    os.environ["AIDOT_DISABLE_HIGHPORT_FIX"] = "1"
    try:
        assert _install_highport_nomination_patch() is False
    finally:
        os.environ.pop("AIDOT_DISABLE_HIGHPORT_FIX", None)


if __name__ == "__main__":
    fns = [v for k, v in sorted(globals().items())
           if k.startswith("test_") and callable(v)]
    for fn in fns:
        fn()
        print(f"PASS {fn.__name__}")
    print(f"\nALL {len(fns)} TESTS PASSED")
