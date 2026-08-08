"""No log line may carry SRTP key material.

`_status()` logs, so every one of these lines lands in `home-assistant.log` on
real installs - and users paste that file into public issue reports as a matter
of course. An SDES inline key is base64 of a 30-byte master key + salt, so
printing even its first 8 characters puts roughly 48 bits of real key material
into those reports, and 16 characters roughly 96.

`_srtp_tx_key_note` already learned this and prints a truncated SHA-256 instead
(see `_key_fingerprint`). This test enforces the same rule across the whole
module, because three older sites did not follow it - one of them printing 16
characters of two different keys AND the full packet hex, unconditionally, on
the first ten packets of every SDES session.

The rule has to distinguish two things that look alike in source:

    _our_tx_srtp_key_audio[:16].encode('ascii')   USING a key - fine
    f"key={_our_tx_srtp_key_audio[:8]}"           LOGGING a key - not fine

so it is written against the AST rather than by grepping text, and scoped to
LOGGING CALLS specifically. A first version of this test flagged every f-string
and was wrong: the SDP builders interpolate the key into `a=crypto:... inline:`
lines, which is where the key is supposed to go. Feeding a key to a cipher or an
SDP is using it; putting it through `_status()` or `_LOGGER` is disclosing it.
"""
import ast
import os
import pathlib
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera import sdes_open  # noqa: E402

# Names that hold raw SRTP key material anywhere in the module.
_KEY_NAMES = {
    "_our_tx_srtp_key_audio",
    "_cam_key_audio",
    "srtp_key_audio",
    "_answer_key",
    "_pli_key_b64",
    "_rr_key_b64",
}


def _key_bearing(node: ast.AST) -> set[str]:
    """Key-holding names read anywhere under ``node``."""
    return {
        n.id for n in ast.walk(node)
        if isinstance(n, ast.Name) and n.id in _KEY_NAMES
    }


def _is_log_call(node: ast.AST) -> bool:
    """`_status(...)` or `_LOGGER.info/debug/warning/error/exception(...)`."""
    if not isinstance(node, ast.Call):
        return False
    func = node.func
    if isinstance(func, ast.Name):
        return func.id == "_status"
    if isinstance(func, ast.Attribute):
        return (
            func.attr in {"info", "debug", "warning", "error", "exception"}
            and isinstance(func.value, ast.Name)
            and func.value.id.endswith("LOGGER")
        )
    return False


#: Helpers that take a raw key and return something safe to print. Passing a key
#: INTO one of these is the fix, not the defect, so the walk stops there.
_SANITISERS = {"_key_fingerprint", "_srtp_tx_key_note"}


def _sanitised(node: ast.AST) -> bool:
    return (
        isinstance(node, ast.Call)
        and isinstance(node.func, ast.Name)
        and node.func.id in _SANITISERS
    )


def _keys_reaching(node: ast.AST) -> set[str]:
    """Key names that reach ``node`` without passing through a sanitiser."""
    if _sanitised(node):
        return set()
    if isinstance(node, ast.Name):
        return {node.id} if node.id in _KEY_NAMES else set()
    found: set[str] = set()
    for child in ast.iter_child_nodes(node):
        # `x if _cam_key_audio else y` TESTS the key, it does not print it.
        if isinstance(node, ast.IfExp) and child is node.test:
            continue
        found |= _keys_reaching(child)
    return found


def _leaks(source: str) -> list[tuple[int, str]]:
    """Every logging call that carries a key-bearing name unsanitised."""
    found = []
    for node in ast.walk(ast.parse(source)):
        if not _is_log_call(node):
            continue
        names: set[str] = set()
        for arg in list(node.args) + [kw.value for kw in node.keywords]:
            names |= _keys_reaching(arg)
        if names:
            found.append((node.lineno, ", ".join(sorted(names))))
    return found


def test_no_logging_call_in_the_sdes_module_carries_a_key():
    source = pathlib.Path(sdes_open.__file__).read_text()
    leaks = _leaks(source)
    assert leaks == [], (
        "these logging calls carry raw SRTP key material, and they reach "
        "home-assistant.log: "
        + "; ".join(f"line {ln} ({names})" for ln, names in leaks)
        + ". Print _key_fingerprint(key) instead - it distinguishes keys in a "
        "log without disclosing any of one."
    )


def test_the_fingerprint_is_reachable_from_the_module_not_nested_in_one_helper():
    """A leak site cannot use a fingerprint it cannot reach."""
    assert callable(getattr(sdes_open, "_key_fingerprint", None))


def test_the_fingerprint_distinguishes_keys_without_revealing_them():
    key_a = "b3VyLW93bi1zZW5kLWtleS1oZXJlLTEyMzQ1Ng=="
    key_b = "Y2FtZXJhLWFuc3dlci1rZXktaGVyZS05ODc2NTQ="
    fp_a = sdes_open._key_fingerprint(key_a)
    fp_b = sdes_open._key_fingerprint(key_b)

    assert fp_a != fp_b, "two different keys must be distinguishable in a log"
    assert fp_a == sdes_open._key_fingerprint(key_a), "must be stable"
    # The point of the whole exercise: no run of the key survives into the note.
    for n in range(6, len(key_a)):
        assert key_a[:n] not in fp_a
    assert sdes_open._key_fingerprint("") == "none"
