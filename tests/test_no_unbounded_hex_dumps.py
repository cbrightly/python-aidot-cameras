"""No log line may dump an unbounded buffer as hex.

Two separate problems, one shape.  ``bridge: TUTK decrypt -> plain_all=...``
put a camera's DECRYPTED payload into the log in full; at INFO, which is where
it landed under Home Assistant, that is a privacy problem in a user's log
rather than merely noise.  And an unbounded dump is a poor diagnostic anyway:
what identifies a frame is its header, so a slice says the same thing in a
line a human can read.

The invariant: every ``.hex()`` in the two open paths is taken of a SLICE.
Enforced statically because there is no seam to reach these sites from a test
- they live inside the bridge thread's packet loop, several closures deep in a
5k-line function - and the property is about what the source can ever emit,
not about one execution of it.
"""

import ast
import pathlib

import pytest

_SRC_DIR = (
    pathlib.Path(__file__).resolve().parent.parent / "aidot_cameras" / "camera"
)
_MODULES = ("sdes_open.py", "webrtc_open.py")


def _unbounded_hex_calls(path):
    """(lineno, source segment) for each ``.hex()`` not taken of a slice."""
    src = path.read_text()
    tree = ast.parse(src)
    out = []
    for node in ast.walk(tree):
        if not isinstance(node, ast.Call):
            continue
        fn = node.func
        if not (isinstance(fn, ast.Attribute) and fn.attr == "hex"):
            continue
        # `_pkt[:32].hex()` is bounded; `_pkt.hex()` is not.  A Subscript
        # receiver is the bound - whether it is a slice or a single index,
        # the result cannot grow with the packet.
        if isinstance(fn.value, ast.Subscript):
            continue
        out.append((node.lineno, ast.get_source_segment(src, node) or "?"))
    return out


@pytest.mark.parametrize("module", _MODULES)
def test_no_unbounded_hex_dump(module):
    path = _SRC_DIR / module
    # Guard against the check going vacuous on a moved or renamed module.
    assert path.exists(), f"{path} is missing - this test would pass on nothing"

    offenders = _unbounded_hex_calls(path)
    assert not offenders, (
        f"{module} dumps an unbounded buffer as hex at "
        + ", ".join(f"line {ln} ({seg})" for ln, seg in offenders)
        + " - take a slice, e.g. `buf[:32].hex()`"
    )


def test_the_guard_can_actually_fail():
    """A detector that cannot fire certifies nothing."""
    tree_src = "x = _buf.hex()\ny = _buf[:8].hex()\n"
    path = pathlib.Path(pytest.__file__)  # any real path; we parse a string
    del path
    tree = ast.parse(tree_src)
    found = [
        n.lineno for n in ast.walk(tree)
        if isinstance(n, ast.Call)
        and isinstance(n.func, ast.Attribute)
        and n.func.attr == "hex"
        and not isinstance(n.func.value, ast.Subscript)
    ]
    assert found == [1]
