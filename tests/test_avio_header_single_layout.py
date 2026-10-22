"""Every AVIO frame we send is packed by one helper.

Ten senders across webrtc.py, webrtc_open.py and sdes_open.py each hand-rolled
``struct.pack("<IIqII4x", ...)`` while protocol.py defined the same layout as a
Struct for parsing (review 2026-10-15). One layout, one place.
"""

import pathlib
import struct

from aidot_cameras.camera.protocol import AVIO_HDR_LEN, avio_header, parse_avio_response

CAMERA = pathlib.Path(__file__).resolve().parent.parent / "aidot_cameras" / "camera"


def test_the_helper_matches_the_wire_layout():
    assert avio_header(7, 5376, 1234567890123, 28) == struct.pack(
        "<IIqII4x", 7, 5376, 1234567890123, 28, 0
    )
    assert len(avio_header(0, 0, 0, 0)) == AVIO_HDR_LEN == 28


def test_a_packed_header_parses_back():
    frame = avio_header(42, 5156, 99, 3) + b"abc"
    resp = parse_avio_response(frame)
    assert resp is not None
    assert (resp.seq, resp.command) == (42, 5156)


def test_no_sender_hand_rolls_the_layout():
    """The 28-byte AVIO header has one layout, in protocol.py: no other camera
    module packs or parses it with its own struct format."""
    import ast

    from tests.ast_guards import package_modules

    offenders = [
        f"{p.name}:{n.lineno}"
        for p in package_modules()
        if p.parent.name == "camera" and p.name != "protocol.py"
        for n in ast.walk(ast.parse(p.read_text()))
        if isinstance(n, ast.Constant)
        and isinstance(n.value, str)
        and "IIqII4x" in n.value
    ]
    assert not offenders, offenders
