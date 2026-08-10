"""A camera behind our own NAT is not us, and refusing it stalls the stream.

Seven first-media stall reports name `vetoed-self-ip` (see ROAD-TO-1.0 item 3).
The shape is always the same: the camera's connectivity check reaches us as a
TURN Data Indication whose XOR-PEER-ADDRESS is THIS host's public IP on a port
that is not ours - because the camera sits behind the same NAT, on another
subnet of the same house. `_is_self_peer_ip` compared the address alone, so it
answered "that is us".

Two things then break, and the second is the stall:

  * the camera's address is never learned as a peer-reflexive candidate;
  * the Binding Success Response is never sent back through the relay, because
    the branch that wraps it in a Send Indication is guarded by the same check.

With no response, the camera's check never completes, ICE never succeeds, the
AVIO LIVING trigger is never armed and the camera never sends a byte. That is
exactly `binding-success=0; trigger=not-sent` with no media.

The safety property this check exists for is unchanged and must stay unchanged:
nominating OUR OWN address would have us answer our own check. Our own address
is one ip:PORT. A peer sharing our NAT has the same ip and a different port, so
comparing the pair keeps the guard and drops the false positive - which is also
what ICE means by a transport address.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (
    _is_self_transport_address,
    _record_peer_reflexive,
)

LOCAL = "192.168.0.50"
PUBLIC = "173.53.36.206"
OURS = (41000, 41002)          # the ports our own srflx candidates advertise


def _is_self(ip, port=None):
    return _is_self_transport_address(
        ip, port, local_ip=LOCAL, public_ip=PUBLIC, own_ports=OURS)


def test_our_own_reflexive_address_is_still_refused():
    # The guard's whole purpose. Both advertised ports, exactly as offered.
    assert _is_self(PUBLIC, 41000) is True
    assert _is_self(PUBLIC, 41002) is True


def test_loopback_and_this_hosts_lan_address_are_refused_on_any_port():
    for ip in ("127.0.0.1", "0.0.0.0", LOCAL):
        assert _is_self(ip, 41000) is True
        assert _is_self(ip, 59999) is True


def test_a_camera_sharing_our_public_ip_on_another_port_is_not_us():
    # The measured case: XOR-PEER-ADDRESS is our public IP, port 49887, which
    # is the port the camera advertised on its own host candidate.
    assert _is_self(PUBLIC, 49887) is False
    assert _is_self(PUBLIC, 46836) is False


def test_an_unknown_port_still_refuses_the_public_ip():
    # Call sites that cannot supply a port keep the old, conservative answer.
    # Widening those blind would be guessing, and the guard is the safe default.
    assert _is_self(PUBLIC, None) is True


def test_an_unrelated_address_is_never_us():
    assert _is_self("54.144.38.43", 5349) is False


def test_the_learner_uses_the_port_too():
    # _record_peer_reflexive is where the address becomes a nomination target,
    # so it has to ask the same question the same way.
    advertised = [("192.168.100.3", 34400)]

    learned = _record_peer_reflexive(
        advertised, [], (PUBLIC, 49887), _is_self)
    assert learned == [(PUBLIC, 49887)], "a peer behind our NAT must be learnable"

    refused = _record_peer_reflexive(
        advertised, [], (PUBLIC, 41000), _is_self)
    assert refused == [], "our own advertised address must never be learned"


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
