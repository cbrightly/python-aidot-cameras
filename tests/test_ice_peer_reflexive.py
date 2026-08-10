"""Peer-reflexive candidate discovery for the SDES ICE path.

The nomination set used to be exactly what the camera advertised in its answer
SDP.  That is wrong whenever the advertised address is not reachable from here:
the camera's own probes arrive from an address we never nominate, so it stays in
ICE "Checking" and never sends SRTP.  These cover the policy that decides what
gets added to the nomination set.
"""
from aidot_cameras.camera.sdes_open import (
    _MAX_PRFLX_CANDS,
    _record_peer_reflexive,
)

ADVERTISED = [("192.168.100.13", 40000)]


def _never_self(_ip, _port=None):
    # The predicate takes a transport address, not an address: a peer behind
    # our own NAT shares our public IP on a different port, and refusing it on
    # the IP alone stalled real streams. See
    # test_self_peer_is_an_address_not_an_ip.py.
    return False


def test_an_unadvertised_source_is_learned():
    """The PTZ case: it advertised a subnet we have no route to.

    Its probes still reached us, from an address it never listed.  That address
    is the only working path, so it has to become nominable.
    """
    out = _record_peer_reflexive(
        ADVERTISED, [], ("203.0.113.7", 51234), _never_self)
    assert out == [("203.0.113.7", 51234)]


def test_an_advertised_source_is_not_duplicated():
    """A directly reachable camera probes from a candidate it advertised.

    Nothing is learned, so the healthy path keeps the exact nomination set it
    had before this existed.
    """
    out = _record_peer_reflexive(
        ADVERTISED, [], ("192.168.100.13", 40000), _never_self)
    assert out == []


def test_the_same_source_is_only_learned_once():
    known = _record_peer_reflexive(
        ADVERTISED, [], ("203.0.113.7", 51234), _never_self)
    again = _record_peer_reflexive(
        ADVERTISED, known, ("203.0.113.7", 51234), _never_self)
    assert again is known, "a repeat probe must not rebind the list"


def test_our_own_address_is_never_learned():
    """TURN self-loop: our own relayed packet coming back to us.

    Nominating ourselves would form a pair that can never carry camera media.
    """
    def _is_self(ip, port=None):
        return (ip, port) == ("192.168.0.110", 3478)

    out = _record_peer_reflexive(
        ADVERTISED, [], ("192.168.0.110", 3478), _is_self)
    assert out == []


def test_discovery_is_bounded():
    """A flapping or spoofed source must not grow the set without limit."""
    learned = []
    for i in range(_MAX_PRFLX_CANDS + 5):
        learned = _record_peer_reflexive(
            ADVERTISED, learned, (f"203.0.113.{i}", 51234), _never_self)
    assert len(learned) == _MAX_PRFLX_CANDS


def test_a_missing_observation_is_ignored():
    """Relay-carried probes have no usable peer address until parsed."""
    for observed in (None, (None, 51234), ("203.0.113.7", None), ("", 0)):
        assert _record_peer_reflexive(
            ADVERTISED, [], observed, _never_self) == []


def test_learning_works_with_no_advertised_candidates():
    """Relay-only battery cams answer late; the set can be empty at probe time."""
    out = _record_peer_reflexive([], [], ("203.0.113.7", 51234), _never_self)
    assert out == [("203.0.113.7", 51234)]


def test_the_caller_gets_a_new_list_not_a_mutation():
    """The bridge thread iterates the old list; it must never change under it."""
    original = [("198.51.100.1", 40000)]
    out = _record_peer_reflexive(
        ADVERTISED, original, ("203.0.113.7", 51234), _never_self)
    assert original == [("198.51.100.1", 40000)], "input was mutated"
    assert out is not original
