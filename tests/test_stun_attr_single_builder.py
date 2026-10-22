"""Every STUN attribute the SDES open sends is built by one helper.

Seven hand-rolled copies (the TURN allocate, CreatePermission, Refresh and
Send-indication builders, both STUN responders and the USE-CANDIDATE
USERNAME) packed the same type-length-value with 4-byte padding.
"""

import struct

import pytest

from aidot_cameras.camera.sdes_open import _stun_attr


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
    """Every STUN attribute goes through _stun_attr: nothing else packs the
    `!HH` type/length header of an attribute by hand."""
    import ast

    from tests.ast_guards import calls_to, function, module_tree

    tree = module_tree("aidot_cameras.camera.sdes_open")
    builder = set(ast.walk(function(tree, "_stun_attr")))
    hand_rolled = [
        c.lineno
        for c in calls_to(tree, "pack")
        if c not in builder
        and c.args
        and isinstance(c.args[0], ast.Constant)
        and c.args[0].value == "!HH"
        and len(c.args) == 3
        and isinstance(c.args[2], ast.Call)
        and getattr(c.args[2].func, "id", None) == "len"
    ]
    assert not hand_rolled, f"hand-rolled STUN attribute headers at {hand_rolled}"
    assert calls_to(tree, "_stun_attr"), "the builders call the shared helper"
