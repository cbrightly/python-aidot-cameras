"""REMB must not be written straight to the socket.

This is the NACK bug, still present in the REMB path.

When the camera reached us through our TURN relay, the address a packet arrived
FROM is the relay. Writing raw SRTCP bytes there gets them parsed as a
malformed STUN message and dropped -- and `socket.sendto` reports success, so
the send is silently inert while the log says it went out. `_send_video_nack`
carries the full explanation and takes a relay-aware `send` callable for
exactly this reason; the RR and the AVIO trigger go out the same way.

REMB was left behind. It called `_bridge_fn._cam_srtp_sock.sendto(...,
_bridge_fn._cam_srtp_src)` directly, so on any relayed camera it never arrived.
That matters for the record as much as for the code: "REMB was measured to do
nothing" is one of the reasons REMB ships off, and on a relayed session that
measurement could not have been valid.

**What is and is not verified.** Live validation runs on a self-hosted runner
on the camera LAN (docs/CI-RUNNER.md), but being on the LAN does not make a
session direct: relayed sessions do occur on this fleet -- one camera has been
observed streaming over the TURN relay with first media at +6.9 s, and the
three battery A001513s report a WAN address and do not answer unicast
discovery. So the relay path is exercised in practice, not hypothetical.

Exercised 2026-08-23 on the fleet's relayed A001513 with
`AIDOT_REMB_TARGET_BPS=500000`. Its media arrives through the CAMERA's own TURN
allocation -- 4821 inbound RTP packets from `3.230.182.123:44960` -- and on the
wire our feedback goes back to that same address: 41 RTCP PT=206 (PLI/REMB)
alongside 158 PT=205 (NACK), with the `sent REMB 500 kbps` receipt in the log
and the session passing. So REMB now transmits on a relayed camera and takes
the identical path the NACKs take.

**What that does NOT prove.** In this topology `_br_cam_peer` is correctly
None: the camera's relay candidate is a real address the TURN server forwards
from, so a direct write to it is right, and the old code would have reached it
too. The case the fix exists for is the other one -- the camera reaching us
through OUR allocation, where `_br_cam_peer` is set and the payload has to be
wrapped in a Send Indication. That session shape has not been observed here, so
the wrapping branch is still covered only by the source guard below.

It also used a literal `0xAB12CD34` where the constant exists. The SRTP TX
policy is keyed `ssrc_value=_CAM_RTCP_SENDER_SSRC`, so a drifting literal is a
packet the camera drops.
"""

import ast

from tests.ast_guards import calls_to, name_of, names_in, sdes_bridge


def _remb_block():
    """The bridge's REMB cadence `if`, as an AST node."""
    found = [
        n
        for n in ast.walk(sdes_bridge())
        if isinstance(n, ast.If)
        and "REMB_TARGET_BPS" in names_in(n.test)
        and calls_to(n, "_send_video_remb")
        and not calls_to(n.test, "_send_video_remb")
    ]
    assert len(found) == 1, "the REMB cadence block has moved or gone"
    return found[0]


def test_remb_goes_out_only_through_the_relay_aware_sender():
    """A raw socket write is silently inert on a TURN-relayed session; REMB
    goes out the bridge's relay-aware sender like the NACK does, and is skipped
    - never sent by socket instead - while that sender does not exist yet."""
    block = _remb_block()
    assert not [
        c for c in ast.walk(block) if isinstance(c, ast.Call) and name_of(c) == "sendto"
    ], "REMB writes to a raw socket"
    walrus = [
        n
        for n in ast.walk(block.test)
        if isinstance(n, ast.NamedExpr)
        and isinstance(n.value, ast.Call)
        and name_of(n.value) == "getattr"
        and [ast.unparse(a) for a in n.value.args]
        == ["_bridge_fn", "'_send_to_cam'", "None"]
    ]
    assert walrus, "the guard must resolve the relay-aware sender with a None default"
    sender = walrus[0].target.id
    sends = calls_to(block, "_send_video_remb")
    assert sends and all(
        isinstance(c.args[0], ast.Name) and c.args[0].id == sender for c in sends
    ), "REMB must go through its helper, with the sender the guard required"


def test_remb_uses_the_keyed_sender_ssrc_constant():
    """The SRTP TX policy is keyed on _CAM_RTCP_SENDER_SSRC; a literal drifts
    and the camera drops the packet."""
    (send,) = calls_to(_remb_block(), "_send_video_remb")
    assert ast.unparse(send.args[2]) == "_CAM_RTCP_SENDER_SSRC"
