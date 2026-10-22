"""Detecting a camera whose only ICE candidate is on an unreachable subnet.

Measured on an A001513 advertising 10.0.100.4 to a host on 10.0.0.0/24:
STUN binding successes still accumulate (18 of them - they come back by other
paths), the data channel never establishes, and the session spends its whole
75 s first-media budget on a path that never had a chance. The symptom reads as
"first media never arrived", which sends people looking at the camera.
"""

from unittest import mock

from aidot_cameras.camera import sdes_open


def _on(net):
    import ipaddress

    return mock.patch.object(
        sdes_open, "_local_ipv4_networks", lambda: [ipaddress.ip_network(net)]
    )


class TestItSpotsTheUnreachableCase:
    def test_the_measured_case(self):
        """10.0.100.4 offered to a host on 10.0.0.0/24."""
        with _on("10.0.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("10.0.100.4") is True

    def test_a_camera_on_our_own_subnet_is_fine(self):
        with _on("10.0.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("10.0.0.140") is False


class TestItNeverCondemnsWhatItCannotJudge:
    def test_public_addresses_are_never_off_subnet(self):
        """Relay and reflexive candidates are reached via the gateway by
        design - calling those unreachable would condemn the working path."""
        with _on("10.0.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("3.230.182.123") is False
            assert sdes_open._candidate_is_off_subnet("8.8.8.8") is False

    def test_unknown_local_networks_means_no_verdict(self):
        """If we cannot tell what we are on, we must not call anything
        unreachable - a wrong guess would blame a working camera."""
        with mock.patch.object(sdes_open, "_local_ipv4_networks", lambda: []):
            assert sdes_open._candidate_is_off_subnet("10.0.100.4") is False

    def test_garbage_is_not_a_verdict(self):
        with _on("10.0.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("not-an-ip") is False

    def test_loopback_and_link_local_are_left_alone(self):
        with _on("10.0.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("127.0.0.1") is False
            assert sdes_open._candidate_is_off_subnet("169.254.1.1") is False


class TestItOnlyWarnsWhenEveryCandidateIsUnreachable:
    def test_the_guard_requires_all_candidates_off_subnet(self):
        """One reachable candidate means the session can still work, so the
        warning fires only when the off-subnet list is every candidate."""
        import ast

        from tests.ast_guards import calls_to, log_calls, sdes_open_family

        warns = [
            n
            for fn in sdes_open_family()
            for n in ast.walk(fn)
            if isinstance(n, ast.If)
            and any(c.func.attr == "warning" for c in log_calls(n))
            and calls_to(n.test, "len")
            and "_off" in {getattr(x, "id", None) for x in ast.walk(n.test)}
        ]
        assert len(warns) == 1, "the off-subnet warning moved"
        test = warns[0].test
        assert isinstance(test, ast.BoolOp) and isinstance(test.op, ast.And)
        compare = [v for v in test.values if isinstance(v, ast.Compare)]
        assert compare and isinstance(compare[0].ops[0], ast.Eq)
        sides = {ast.unparse(compare[0].left), ast.unparse(compare[0].comparators[0])}
        assert "len(_off)" in sides and any("_cam_ice_cands" in s for s in sides)
