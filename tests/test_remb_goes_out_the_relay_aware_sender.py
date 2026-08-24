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
import pathlib
import re

_SRC = (pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"
        / "camera" / "sdes_open.py")


def _remb_block() -> str:
    """The REMB cadence block: its guard through to the TMMBR block's start.

    Sliced anchor-to-structural-terminator rather than anchor+N characters -
    a fixed-width window goes quietly vacuous when comments grow, and a
    vacuous window makes the negative assertions below pass without guarding
    anything.
    """
    src = _SRC.read_text()
    start = src.index("if (REMB_TARGET_BPS > 0")
    end = src.index("_tmmbr_bps = getattr(", start)
    return src[start:end]


def test_remb_does_not_write_to_the_raw_socket():
    block = _remb_block()
    assert "_cam_srtp_sock.sendto" not in block, (
        "a raw socket write is silently inert on a TURN-relayed session; "
        "REMB must go out the bridge's relay-aware sender like the NACK does")


def test_remb_uses_the_relay_aware_sender():
    assert "_send_to_cam" in _remb_block()


def test_remb_does_not_fall_back_to_the_socket_when_the_sender_is_missing():
    """A fallback would reproduce the bug on exactly the sessions it breaks.

    `_send_to_cam` is published from the video-packet branch, so it can be
    absent for the first tick or two. Skipping a REMB until it exists costs a
    second; falling back to `sendto` costs correctness on every relayed camera.
    """
    block = _remb_block()
    assert re.search(
        r"_remb_send\s*:=\s*getattr\(\s*\n?\s*_bridge_fn,\s*'_send_to_cam',"
        r"\s*None\s*\)+\s*\)?\s*is not None", block), (
        "the guard must resolve the relay-aware sender with a None default "
        "and require it, not fall back to a socket")


def test_remb_uses_the_keyed_sender_ssrc_constant():
    block = _remb_block()
    assert "_CAM_RTCP_SENDER_SSRC" in block
    assert "_send_video_remb(" in block, (
        "REMB must go through its helper like NACK and TMMBR do; the inline "
        "copy is where the raw-socket bug hid")
    assert "0xAB12CD34" not in block, (
        "the SRTP TX policy is keyed on _CAM_RTCP_SENDER_SSRC; a literal here "
        "drifts and the camera drops the packet")


def test_the_nack_path_is_still_the_reference_implementation():
    # If the NACK ever regresses to a raw socket, this whole rationale is gone.
    src = _SRC.read_text()
    start = src.index("def _send_video_nack(")
    assert "sendto" not in src[start:start + 1500]
