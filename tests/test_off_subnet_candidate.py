"""Detecting a camera whose only ICE candidate is on an unreachable subnet.

Measured on an A001513 advertising 192.168.100.4 to a host on 192.168.0.0/24:
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
        sdes_open, "_local_ipv4_networks",
        lambda: [ipaddress.ip_network(net)])


class TestItSpotsTheUnreachableCase:
    def test_the_measured_case(self):
        """192.168.100.4 offered to a host on 192.168.0.0/24."""
        with _on("192.168.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("192.168.100.4") is True

    def test_a_camera_on_our_own_subnet_is_fine(self):
        with _on("192.168.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("192.168.0.140") is False


class TestItNeverCondemnsWhatItCannotJudge:
    def test_public_addresses_are_never_off_subnet(self):
        """Relay and reflexive candidates are reached via the gateway by
        design - calling those unreachable would condemn the working path."""
        with _on("192.168.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("3.230.182.123") is False
            assert sdes_open._candidate_is_off_subnet("173.53.36.206") is False

    def test_unknown_local_networks_means_no_verdict(self):
        """If we cannot tell what we are on, we must not call anything
        unreachable - a wrong guess would blame a working camera."""
        with mock.patch.object(sdes_open, "_local_ipv4_networks", lambda: []):
            assert sdes_open._candidate_is_off_subnet("192.168.100.4") is False

    def test_garbage_is_not_a_verdict(self):
        with _on("192.168.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("not-an-ip") is False

    def test_loopback_and_link_local_are_left_alone(self):
        with _on("192.168.0.0/24"):
            assert sdes_open._candidate_is_off_subnet("127.0.0.1") is False
            assert sdes_open._candidate_is_off_subnet("169.254.1.1") is False


class TestItOnlyWarnsWhenEveryCandidateIsUnreachable:
    def test_the_guard_requires_all_candidates_off_subnet(self):
        """One reachable candidate means the session can still work, so the
        warning must not fire on a mixed set."""
        import inspect
        src = inspect.getsource(sdes_open)
        assert "if _off and len(_off) == len(_cam_ice_cands):" in src

    def test_the_message_names_the_network_not_the_camera(self):
        import inspect
        src = inspect.getsource(sdes_open)
        assert "different network segment" in src
        assert "rather than broken" in src
