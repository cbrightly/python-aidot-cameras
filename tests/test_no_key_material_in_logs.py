"""No log line may carry secret material -- anywhere in the package.

`_status()` logs, so every one of these lines lands in `home-assistant.log` on
real installs - and users paste that file into public issue reports as a matter
of course. An SDES inline key is base64 of a 30-byte master key + salt, so
printing even its first 8 characters puts roughly 48 bits of real key material
into those reports, and 16 characters roughly 96.

`_srtp_tx_key_note` already learned this and prints a truncated SHA-256 instead
(see `_key_fingerprint`). This test enforces the same rule, because three older
sites in the SDES module did not follow it - one of them printing 16 characters
of two different keys AND the full packet hex, unconditionally, on the first ten
packets of every SDES session.

The rule has to distinguish two things that look alike in source:

    _our_tx_srtp_key_audio[:16].encode('ascii')   USING a key - fine
    f"key={_our_tx_srtp_key_audio[:8]}"           LOGGING a key - not fine

so it is written against the AST rather than by grepping text, and scoped to
LOGGING CALLS specifically. A first version of this test flagged every f-string
and was wrong: the SDP builders interpolate the key into `a=crypto:... inline:`
lines, which is where the key is supposed to go. Feeding a key to a cipher or an
SDP is using it; putting it through `_status()` or `_LOGGER` is disclosing it.

SRTP keys are not the only secrets this package handles, so the walk now covers
every module under `aidot_cameras/` (the vendored `aiortc` copy included - it is
shipped code that runs on user installs, and it is clean today) and the name set
covers the other classes too: the device/hub `password`, the device `aesKey`,
account access and refresh tokens, and the MQTT credential. Those are reached by
attribute and by dict key far more often than by bare local name, so the walk
matches `self.password`, `d["mqttPassword"]` and `d.get("aesKey")` as well as a
plain identifier.

WHAT THIS RULE CANNOT SEE: a secret logged inside a container that is not itself
named after a secret - `_LOGGER.debug("response: %s", body)` where `body` is a
decoded API response with a secret-bearing field. That class is real (see the
PR that widened this test) and it is not enforceable by naming alone.
"""
import ast
import os
import pathlib
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera import sdes_open  # noqa: E402

_PACKAGE = pathlib.Path(__file__).resolve().parent.parent / "aidot_cameras"

# Names that hold raw secret material anywhere in the package.
_KEY_NAMES = {
    # SRTP key material (SDES).
    "_our_tx_srtp_key_audio",
    "_cam_key_audio",
    "srtp_key_audio",
    "_answer_key",
    "_pli_key_b64",
    "_rr_key_b64",
    # Device / hub / account passwords.
    "password",
    "passwd",
    "pwd",
    "_password",
    "hub_password",
    "_hub_password",
    "device_password",
    "devicePassword",
    # The MQTT credential: runtime-only, rotates on every account login.
    "mqttPassword",
    "mqttPwd",
    "mqqtPwd",
    "mqtt_password",
    # The per-device AES key.
    "aesKey",
    "aes_key",
    "_aes_key",
    "_key",
    # Cloud auth.
    "token",
    "_token",
    "accessToken",
    "access_token",
    "refreshToken",
    "refresh_token",
    "id_token",
    "credential",
    "secret",
    "clientSecret",
    "client_secret",
    # Per-device streaming credentials batchGetDeviceUserInfo can return
    # (docs/DEFERRED_FEATURES.md); no site reads them by name yet, and the set
    # should already know them when one does.
    "tutkPassword",
    "tutkAccount",
}


def _is_log_call(node: ast.AST) -> bool:
    """`_status(...)`, `print(...)` or `_LOGGER.info/debug/...(...)`."""
    if not isinstance(node, ast.Call):
        return False
    func = node.func
    if isinstance(func, ast.Name):
        return func.id in {"_status", "print"}
    if isinstance(func, ast.Attribute):
        return (
            func.attr in {"info", "debug", "warning", "error", "exception"}
            and isinstance(func.value, ast.Name)
            and func.value.id.endswith("LOGGER")
        )
    return False


#: Helpers that take a raw secret and return something safe to print. Passing a
#: secret INTO one of these is the fix, not the defect, so the walk stops there.
_SANITISERS = {"_key_fingerprint", "_srtp_tx_key_note"}

#: Builtins that reduce a secret to a scalar fact about it. `bool(pwd)` says
#: whether a password was fetched and `len(pwd)` how long it is; neither
#: discloses a character of one, and both are load-bearing diagnostics here.
_SUMMARISERS = {"bool", "len"}


def _sanitised(node: ast.AST) -> bool:
    return (
        isinstance(node, ast.Call)
        and isinstance(node.func, ast.Name)
        and node.func.id in _SANITISERS | _SUMMARISERS
    )


def _named_secret(node: ast.AST) -> str | None:
    """The secret this node names directly, if any.

    Three spellings reach the same value: a bare local (`pwd`), an attribute
    (`self.password`) and a dict lookup by either syntax (`d["aesKey"]`,
    `d.get("aesKey")`).
    """
    if isinstance(node, ast.Name) and node.id in _KEY_NAMES:
        return node.id
    if isinstance(node, ast.Attribute) and node.attr in _KEY_NAMES:
        return "." + node.attr
    if isinstance(node, ast.Subscript):
        index = node.slice
        if (
            isinstance(index, ast.Constant)
            and isinstance(index.value, str)
            and index.value in _KEY_NAMES
        ):
            return "[%s]" % index.value
    if (
        isinstance(node, ast.Call)
        and isinstance(node.func, ast.Attribute)
        and node.func.attr == "get"
        and node.args
        and isinstance(node.args[0], ast.Constant)
        and isinstance(node.args[0].value, str)
        and node.args[0].value in _KEY_NAMES
    ):
        return "get(%s)" % node.args[0].value
    return None


def _keys_reaching(node: ast.AST) -> set[str]:
    """Secret names that reach ``node`` without passing through a sanitiser."""
    if _sanitised(node):
        return set()
    named = _named_secret(node)
    if named is not None:
        return {named}
    found: set[str] = set()
    for child in ast.iter_child_nodes(node):
        # `x if _cam_key_audio else y` TESTS the key, it does not print it.
        if isinstance(node, ast.IfExp) and child is node.test:
            continue
        found |= _keys_reaching(child)
    return found


def _leaks(source: str) -> list[tuple[int, str]]:
    """Every logging call that carries a secret-bearing name unsanitised."""
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


def _modules() -> list[pathlib.Path]:
    mods = sorted(_PACKAGE.rglob("*.py"))
    assert len(mods) > 20, f"the walk found only {len(mods)} modules - wrong root?"
    return mods


def test_no_logging_call_in_the_package_carries_a_secret():
    leaks = []
    for path in _modules():
        for lineno, names in _leaks(path.read_text()):
            leaks.append(f"{path.relative_to(_PACKAGE.parent)}:{lineno} ({names})")
    assert leaks == [], (
        "these logging calls carry raw secret material, and they reach "
        "home-assistant.log: "
        + "; ".join(leaks)
        + ". Print _key_fingerprint(value) instead - it distinguishes secrets "
        "in a log without disclosing any of one - or log bool()/len() of it."
    )


def test_the_guard_still_covers_the_module_it_was_written_for():
    """The widening must not have quietly dropped the original scope."""
    assert pathlib.Path(sdes_open.__file__).resolve() in _modules()


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


# The false-positive classes below are the ones that cost real time to work out.
# Each is a shape that looks like a leak and is not; a rule that flags any of
# them is wrong and will be worked around rather than obeyed.
_NOT_LEAKS = [
    "cipher.update(_our_tx_srtp_key_audio[:16].encode('ascii'))",
    "sdp += f'a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:{srtp_key_audio}'",
    "_status('x' if _cam_key_audio else 'y')",
    "_status(f'key={_key_fingerprint(_cam_key_audio)}')",
    "_status(_srtp_tx_key_note('PLI', _pli_key_b64, _answer_key, _cam_key_audio))",
    "_LOGGER.debug('hasPwd=%s', bool(auth['mqttPassword']))",
    "_LOGGER.info('mqttPassword stored (len=%d)', len(pwd))",
    "_LOGGER.debug('keys=%s', sorted(body.keys()))",
]

_LEAKS = [
    "_status(f'cam_key={_cam_key_audio[:8]}')",
    "_LOGGER.debug('pw=%s', self.password)",
    "_LOGGER.warning('auth=%s', auth['mqttPassword'])",
    "_LOGGER.debug('aes=%s', device.get('aesKey'))",
    "print(f'token={refreshToken}')",
]


def test_the_rule_knows_which_shapes_are_not_disclosures():
    for src in _NOT_LEAKS:
        assert _leaks(src) == [], f"false positive on: {src}"


def test_the_rule_catches_a_disclosure_in_each_spelling():
    for src in _LEAKS:
        assert _leaks(src), f"missed a real leak: {src}"
