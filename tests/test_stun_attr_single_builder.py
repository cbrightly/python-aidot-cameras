"""Every STUN attribute the SDES open sends is built by one helper.

Seven hand-rolled copies (the TURN allocate, CreatePermission, Refresh and
Send-indication builders, both STUN responders and the USE-CANDIDATE
USERNAME) packed the same type-length-value with 4-byte padding.
"""

import pathlib
import struct

import pytest

from aidot_cameras.camera.sdes_open import _stun_attr

SRC = (
    pathlib.Path(__file__).resolve().parent.parent
    / "aidot_cameras"
    / "camera"
    / "sdes_open.py"
)


def _previous(t, v):
    # The formula every copy used, verbatim.
    _p = (-len(v)) % 4
    return struct.pack("!HH", t, len(v)) + v + b"\x00" * _p


@pytest.mark.parametrize("length", range(0, 10))
@pytest.mark.parametrize("attr_type", [0x0006, 0x0008, 0x0012, 0x0013, 0x0019, 0x802A])
def test_bytes_match_the_previous_builders(attr_type, length):
    value = bytes(range(1, length + 1))
    assert _stun_attr(attr_type, value) == _previous(attr_type, value)


def test_the_result_is_padded_to_a_word():
    assert len(_stun_attr(0x0006, b"abcde")) == 4 + 8


def test_no_hand_rolled_copy_remains():
    src = SRC.read_text()
    assert src.count('pack("!HH", _t, len(_v))') == 0
    assert src.count("_stun_attr(") >= 20, "the builders call the shared helper"
