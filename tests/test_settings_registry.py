"""The settings registry is the one inventory of AIDOT_* variables, and the one
parser for them.

Two kinds of test here. The parsing tests pin the malformed-value rules that
used to differ per call site (a bare float() stopped the library importing on
an empty value; `nan` made two grace rules fire at once). The inventory tests
walk the package AST and the docs so a variable cannot be added in one
place and forgotten in the other.
"""

from __future__ import annotations

import ast
import os
import re

import pytest

from aidot_cameras import settings

PKG = os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "aidot_cameras"
)
DOCS = os.path.dirname(PKG)

_NAME = re.compile(r"AIDOT_[A-Z0-9_]+")


def _package_trees() -> list[tuple[str, ast.Module]]:
    """(path relative to the repo, parsed module) for every module in the
    package, excluding the vendored tree and the registry itself."""
    out: list[tuple[str, ast.Module]] = []
    for root, _dirs, files in os.walk(PKG):
        if "_vendor" in root:
            continue
        for f in sorted(files):
            if not f.endswith(".py") or f == "settings.py":
                continue
            path = os.path.join(root, f)
            with open(path, encoding="utf-8") as fh:
                out.append((os.path.relpath(path, DOCS), ast.parse(fh.read())))
    return out


def _source_names() -> set[str]:
    """Every AIDOT_* name the package spells as a string literal."""
    return {
        node.value
        for _rel, tree in _package_trees()
        for node in ast.walk(tree)
        if isinstance(node, ast.Constant)
        and isinstance(node.value, str)
        and _NAME.fullmatch(node.value)
    }


_ENV_READS = {"os.environ.get", "_os.environ.get", "os.getenv", "_os.getenv"}
_ENVIRON = {"os.environ", "_os.environ"}


def _raw_read_sites() -> set[tuple[str, str]]:
    """Every os.environ read in the package that names an AIDOT_ variable, as
    (file, name): ``os.environ.get(x)``, ``os.getenv(x)`` and
    ``os.environ[x]``. A literal argument is the name; an identifier is
    resolved through a ``NAME = "AIDOT_..."`` assignment in the same file; an
    argument that is a plain variable (the CLI's helper takes the name as a
    parameter) is recorded as ``<variable>`` so the site is still pinned."""
    sites: set[tuple[str, str]] = set()
    for rel, tree in _package_trees():
        consts = {
            t.id: n.value.value
            for n in ast.walk(tree)
            if isinstance(n, ast.Assign) and isinstance(n.value, ast.Constant)
            for t in n.targets
            if isinstance(t, ast.Name) and isinstance(n.value.value, str)
        }
        for node in ast.walk(tree):
            arg = None
            if isinstance(node, ast.Call) and ast.unparse(node.func) in _ENV_READS:
                arg = node.args[0] if node.args else None
            elif (
                isinstance(node, ast.Subscript) and ast.unparse(node.value) in _ENVIRON
            ):
                arg = node.slice
            if arg is None:
                continue
            if isinstance(arg, ast.Constant):
                if isinstance(arg.value, str) and _NAME.fullmatch(arg.value):
                    sites.add((rel, arg.value))
                # A literal that is not an AIDOT_ name (XDG_CONFIG_HOME) is not ours.
            elif isinstance(arg, ast.Name):
                value = consts.get(arg.id)
                if value and _NAME.fullmatch(value):
                    sites.add((rel, value))
                elif value is None:
                    sites.add((rel, f"<{arg.id}>"))
    return sites


def _documented_names() -> set[str]:
    text = ""
    for rel in (
        "README.md",
        "docs/ENVIRONMENT.md",
        "docs/CAMERAS.md",
        "docs/TESTING.md",
    ):
        with open(os.path.join(DOCS, rel), encoding="utf-8") as fh:
            text += fh.read()
    return set(re.findall(r"`(AIDOT_[A-Z0-9_]+)`", text))


# --------------------------------------------------------------- inventory


def test_every_variable_the_source_reads_is_registered():
    missing = sorted(_source_names() - set(settings.KNOBS))
    assert not missing, f"read in the source but not in settings.KNOBS: {missing}"


def test_every_registered_variable_is_read_somewhere():
    unused = sorted(set(settings.KNOBS) - _source_names())
    assert not unused, f"registered but read nowhere: {unused}"


def _documented_table_rows() -> set[str]:
    """The AIDOT_* names that head a table row in the two reference tables."""
    rows: set[str] = set()
    for rel in ("README.md", "docs/ENVIRONMENT.md"):
        with open(os.path.join(DOCS, rel), encoding="utf-8") as fh:
            rows.update(re.findall(r"^\| `(AIDOT_[A-Z0-9_]+)`", fh.read(), re.M))
    return rows


def test_every_registered_variable_is_documented():
    undocumented = sorted(set(settings.KNOBS) - _documented_names())
    assert not undocumented, f"in settings.KNOBS but in no docs table: {undocumented}"
    # And the other way: a table row that names no knob is a stale row.
    unregistered = sorted(_documented_table_rows() - set(settings.KNOBS))
    assert not unregistered, f"a docs table row but not a knob: {unregistered}"


def test_registry_entries_are_well_formed():
    for name, knob in settings.KNOBS.items():
        assert knob.name == name
        assert name.startswith("AIDOT_")
        assert knob.kind in settings.KINDS, (name, knob.kind)
        if knob.kind in ("float", "int"):
            assert isinstance(knob.default, (int, float)), name
        if knob.kind in settings.BOOL_KINDS:
            assert isinstance(knob.default, bool), name
        if knob.kind in ("bool_on", "bool_on_blank_off"):
            assert knob.default is True, name
        if knob.kind in ("bool_off", "bool_one", "bool_nonempty"):
            assert knob.default is False, name


# ----------------------------------------------------------------- parsing


@pytest.mark.parametrize(
    "raw, expected",
    [
        (None, 20.0),  # unset
        ("", 20.0),  # blank: used to raise at import
        ("junk", 20.0),
        ("nan", 20.0),  # non-finite: used to fire two rules at once
        ("inf", 20.0),
        ("-inf", 20.0),
        ("12.5", 12.5),
        ("0", 0.0),  # zero is a value, not "unset"
    ],
)
def test_read_float(raw, expected):
    env = {} if raw is None else {"AIDOT_X_FLOAT": raw}
    knob = settings.Knob("AIDOT_X_FLOAT", 20.0, "float")
    assert settings.read_knob(knob, env) == expected


def test_read_float_clamps_to_minimum():
    knob = settings.Knob("AIDOT_X_FLOAT", 20.0, "float", minimum=0.0)
    assert settings.read_knob(knob, {"AIDOT_X_FLOAT": "-5"}) == 0.0
    # No minimum: negative passes through (some knobs use <= 0 as "disabled").
    assert (
        settings.read_knob(settings.Knob("AIDOT_Y", 20.0, "float"), {"AIDOT_Y": "-5"})
        == -5.0
    )


@pytest.mark.parametrize(
    "raw, expected",
    [
        (None, 5),
        ("", 5),
        ("junk", 5),
        ("7", 7),
        ("7.9", 5),  # an int knob does not silently truncate a float
        ("0", 0),
    ],
)
def test_read_int(raw, expected):
    env = {} if raw is None else {"AIDOT_X_INT": raw}
    knob = settings.Knob("AIDOT_X_INT", 5, "int")
    assert settings.read_knob(knob, env) == expected


def test_read_int_clamps_to_minimum():
    knob = settings.Knob("AIDOT_X_INT", 5, "int", minimum=1)
    assert settings.read_knob(knob, {"AIDOT_X_INT": "0"}) == 1


@pytest.mark.parametrize(
    "raw, expected",
    [
        (None, True),
        ("", True),  # an empty value in a unit file keeps the default
        ("junk", True),
        ("1", True),
        ("0", False),
        ("false", False),
        ("No", False),
        (" off ", False),
    ],
)
def test_read_bool_default_on_is_a_falsy_denylist(raw, expected):
    env = {} if raw is None else {"AIDOT_X_ON": raw}
    knob = settings.Knob("AIDOT_X_ON", True, "bool_on")
    assert settings.read_knob(knob, env) is expected


@pytest.mark.parametrize(
    "raw, expected",
    [
        (None, False),
        ("", False),
        ("junk", False),
        ("0", False),
        ("1", True),
        ("true", True),
        ("YES", True),
        (" on ", True),
    ],
)
def test_read_bool_default_off_is_a_truthy_allowlist(raw, expected):
    env = {} if raw is None else {"AIDOT_X_OFF": raw}
    knob = settings.Knob("AIDOT_X_OFF", False, "bool_off")
    assert settings.read_knob(knob, env) is expected


def test_the_three_legacy_bool_shapes_are_kept_as_they_are():
    zero = settings.Knob("AIDOT_Z", True, "bool_zero")
    assert settings.read_knob(zero, {}) is True
    assert settings.read_knob(zero, {"AIDOT_Z": "0"}) is False
    assert (
        settings.read_knob(zero, {"AIDOT_Z": "false"}) is True
    )  # has meant on since it shipped
    zero_off = settings.Knob("AIDOT_Z", False, "bool_zero")
    assert settings.read_knob(zero_off, {}) is False
    assert settings.read_knob(zero_off, {"AIDOT_Z": "1"}) is True
    one = settings.Knob("AIDOT_O", False, "bool_one")
    assert settings.read_knob(one, {"AIDOT_O": "1"}) is True
    assert settings.read_knob(one, {"AIDOT_O": "true"}) is False
    nonempty = settings.Knob("AIDOT_N", False, "bool_nonempty")
    assert settings.read_knob(nonempty, {"AIDOT_N": ""}) is False
    assert settings.read_knob(nonempty, {"AIDOT_N": "0"}) is True


def test_reject_below_reads_as_the_default_where_minimum_clamps():
    rej = settings.Knob("AIDOT_R", 8.0, "float", reject_below=0.0)
    assert settings.read_knob(rej, {"AIDOT_R": "-1"}) == 8.0
    assert settings.read_knob(rej, {"AIDOT_R": "0"}) == 0.0
    rej_i = settings.Knob("AIDOT_R", 2, "int", reject_below=0)
    assert settings.read_knob(rej_i, {"AIDOT_R": "-3"}) == 2


def test_blank_turns_nack_off_as_it_always_has():
    knob = settings.KNOBS["AIDOT_SDES_NACK"]
    assert settings.read_knob(knob, {"AIDOT_SDES_NACK": ""}) is False
    assert settings.read_knob(knob, {"AIDOT_SDES_NACK": "off"}) is False
    assert settings.read_knob(knob, {"AIDOT_SDES_NACK": "junk"}) is True
    assert settings.read_knob(knob, {}) is True


def test_read_or_none_distinguishes_unset_from_any_value():
    assert settings.read_or_none("AIDOT_PINNED_CODEC_RETRIES", {}) is None
    assert (
        settings.read_or_none(
            "AIDOT_PINNED_CODEC_RETRIES", {"AIDOT_PINNED_CODEC_RETRIES": "4"}
        )
        == 4
    )
    # A malformed value is the default, not None: the variable IS set.
    assert (
        settings.read_or_none(
            "AIDOT_PINNED_CODEC_RETRIES", {"AIDOT_PINNED_CODEC_RETRIES": "x"}
        )
        == 2
    )


def test_a_rejected_value_is_named_once_at_debug(caplog):
    import logging

    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.settings"):
        settings.read("AIDOT_BUSY_BACKOFF_S", {"AIDOT_BUSY_BACKOFF_S": "20s"})
        settings.read(
            "AIDOT_FUTILE_KEEPALIVE_LIMIT", {"AIDOT_FUTILE_KEEPALIVE_LIMIT": "nan"}
        )
        settings.read("AIDOT_STREAM_IDLE_S", {"AIDOT_STREAM_IDLE_S": "inf"})
    msgs = [
        r.getMessage() for r in caplog.records if r.name == "aidot_cameras.settings"
    ]
    assert len(msgs) == 3
    assert "AIDOT_BUSY_BACKOFF_S='20s' is not a number; using 20.0" in msgs[0]
    assert "is not an integer" in msgs[1]
    assert "is not finite" in msgs[2]


def test_read_str_and_list():
    assert settings.read_knob(settings.Knob("AIDOT_S", "", "str"), {}) == ""
    assert (
        settings.read_knob(settings.Knob("AIDOT_S", "", "str"), {"AIDOT_S": " x "})
        == " x "
    )
    lk = settings.Knob("AIDOT_L", ["a", "b"], "list")
    assert settings.read_knob(lk, {}) == ["a", "b"]
    assert settings.read_knob(lk, {"AIDOT_L": ""}) == []  # "" disables, as today
    assert settings.read_knob(lk, {"AIDOT_L": " c , d ,"}) == ["c", "d"]


def test_read_by_name_uses_the_registry_and_an_injected_env(monkeypatch):
    monkeypatch.setenv("AIDOT_BUSY_BACKOFF_S", "99")
    # The injected env wins over os.environ: tests never have to touch the
    # process environment to exercise a knob.
    assert settings.read("AIDOT_BUSY_BACKOFF_S", {"AIDOT_BUSY_BACKOFF_S": "7"}) == 7.0
    # Without one, os.environ is read.
    assert settings.read("AIDOT_BUSY_BACKOFF_S") == 99.0
    # Unknown names are a programming error, not a silent default.
    with pytest.raises(KeyError):
        settings.read("AIDOT_NOT_A_KNOB")


@pytest.mark.parametrize(
    "raw,expected",
    [("0", 0.1), ("-3", 0.1), ("0.01", 0.1), ("nan", 5.0), ("0.5", 0.5), ("5", 5.0)],
)
def test_the_slow_probe_step_cannot_be_zero(raw, expected):
    """``_slow_probe_sleep`` sleeps in steps of this size until its interval
    is up; a step of 0 or less made it spin on ``asyncio.sleep(0)``."""
    env = {"AIDOT_DTLS_SLOW_PROBE_CHUNK_S": raw}
    assert settings.read("AIDOT_DTLS_SLOW_PROBE_CHUNK_S", env) == expected


def test_default_by_name():
    assert settings.default("AIDOT_BUSY_BACKOFF_S") == 20.0


# ------------------------------------------- the migrated call sites agree


def test_import_time_constants_come_from_the_registry():
    """The table and the code cannot disagree on a default once the code reads
    the table. Spot-check one constant per module that has import-time reads."""
    from aidot_cameras import lan_retry
    from aidot_cameras.camera import client as camera_client
    from aidot_cameras.camera import protocol, sdes_open

    assert camera_client._BUSY_BACKOFF_S == settings.default("AIDOT_BUSY_BACKOFF_S")
    assert camera_client._SLOW_PROBE_THRESHOLD == settings.default(
        "AIDOT_DTLS_SLOW_PROBE_THRESHOLD"
    )
    assert camera_client._SERVE_REORDER_QUEUE == settings.default(
        "AIDOT_SERVE_REORDER_QUEUE"
    )
    assert camera_client._DTLS_SERVE_OPEN_TIMEOUT_S == settings.default(
        "AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S"
    )
    assert sdes_open._BATTERY_STALE_OFFER_GRACE_S == settings.default(
        "AIDOT_BATTERY_STALE_OFFER_GRACE_S"
    )
    assert sdes_open._TRIGGER_GRACE_S == settings.default("AIDOT_SDES_TRIGGER_GRACE_S")
    assert lan_retry._LOGIN_RETRY_LIMIT == settings.default("AIDOT_LOGIN_RETRY_LIMIT")
    assert protocol.REMB_TARGET_BPS == settings.default("AIDOT_REMB_TARGET_BPS")


def test_per_call_helpers_read_the_registry_with_an_injected_env():
    from aidot_cameras.camera import protocol

    assert protocol._battery_unknown_release_s({}) == 300.0
    assert (
        protocol._battery_unknown_release_s(
            {"AIDOT_BATTERY_UNKNOWN_VIEWER_RELEASE_S": "nan"}
        )
        == 300.0
    )
    assert (
        protocol._battery_unknown_release_s(
            {"AIDOT_BATTERY_UNKNOWN_VIEWER_RELEASE_S": "-1"}
        )
        == -1.0
    )
    assert (
        protocol._stream_startup_grace_s({"AIDOT_STREAM_STARTUP_GRACE_S": "5"}) == 5.0
    )
    assert protocol._reorder_slack({"AIDOT_REORDER_SLACK_TICKS": "-9"}) == 180000
    assert protocol._reorder_slack({"AIDOT_REORDER_SLACK_TICKS": "9"}) == 9


def test_no_aidot_variable_is_read_around_the_registry():
    """Every AIDOT_ read goes through settings: the credentials, the CLI's
    tri-state helper and the AGC included. A raw read would skip the parser,
    the inventory and the secret redaction, so none is allowed."""
    assert _raw_read_sites() == set()
    assert not [n for n, k in settings.KNOBS.items() if k.note.startswith("raw read")]


def _environ_uses() -> "list[tuple[str, int, str]]":
    """Every use of os.environ / os.getenv in the package's CODE (comments and
    docstrings excluded), as (file, line, the use with the call it feeds).
    Catches an alias too: ``env = os.environ`` followed by
    ``env.get("AIDOT_...")`` is found at the alias."""
    out = []
    for rel, tree in _package_trees():
        parent = {c: p for p in ast.walk(tree) for c in ast.iter_child_nodes(p)}
        for node in ast.walk(tree):
            hit = (
                isinstance(node, ast.Attribute)
                and node.attr in ("environ", "getenv")
                and isinstance(node.value, ast.Name)
                and node.value.id in ("os", "_os")
            ) or (isinstance(node, ast.Name) and node.id in ("environ", "getenv"))
            if not hit:
                continue
            use = node
            up = parent.get(use)
            if isinstance(up, ast.Attribute) and isinstance(parent.get(up), ast.Call):
                use = parent[up]
            elif isinstance(up, ast.Call) and up.func is node:
                use = up
            out.append((rel, node.lineno, ast.unparse(use)))
    return out


def test_only_settings_touches_the_environment_for_aidot_names():
    """The one allowed environment read outside settings.py is the XDG base
    directory (not an AIDOT_ name, and defined by the XDG spec)."""
    stray = [u for u in _environ_uses() if "XDG_CONFIG_HOME" not in u[2]]
    assert not stray, f"environment read outside settings.py: {stray}"


def test_secret_knobs_are_the_credentials():
    secret = sorted(n for n, k in settings.KNOBS.items() if k.secret)
    assert secret == [
        "AIDOT_CRED_KEY_FILE",
        "AIDOT_PASSWORD",
        "AIDOT_TOKEN_FILE",
        "AIDOT_USERNAME",
    ]


def test_snapshot_redacts_secrets_and_reports_set_knobs():
    env = {
        "AIDOT_USERNAME": "someone@example.invalid",
        "AIDOT_PASSWORD": "hunter2-not-real",
        "AIDOT_TOKEN_FILE": "/somewhere/token.json",
        "AIDOT_COUNTRY": "DE",
        "AIDOT_HEALTH_LINE_S": "30",
    }
    snap = settings.snapshot(env)
    assert snap == {
        "AIDOT_USERNAME": settings.REDACTED,
        "AIDOT_PASSWORD": settings.REDACTED,
        "AIDOT_TOKEN_FILE": settings.REDACTED,
        "AIDOT_COUNTRY": "DE",
        "AIDOT_HEALTH_LINE_S": 30.0,
    }
    dumped = repr(snap)
    for secret in ("someone@example.invalid", "hunter2-not-real", "token.json"):
        assert secret not in dumped


def test_a_rejected_secret_value_is_not_logged(caplog):
    import logging

    knob = settings.Knob("AIDOT_FAKE_SECRET", 1, "int", secret=True)
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.settings"):
        assert settings.read_knob(knob, {"AIDOT_FAKE_SECRET": "s3cret-value"}) == 1
    assert "s3cret-value" not in caplog.text
    assert settings.REDACTED in caplog.text


def test_credentials_keep_their_semantics():
    assert settings.read("AIDOT_COUNTRY", {}) == "US"
    assert settings.read("AIDOT_COUNTRY", {"AIDOT_COUNTRY": ""}) == ""
    assert settings.read("AIDOT_USERNAME", {}) is None
    assert settings.read("AIDOT_PASSWORD", {"AIDOT_PASSWORD": " p "}) == " p "


def test_tristate_reader():
    assert settings.tristate("AIDOT_FAST_CONNECT", {}) is None
    assert settings.tristate("AIDOT_FAST_CONNECT", {"AIDOT_FAST_CONNECT": "Yes"})
    assert (
        settings.tristate("AIDOT_FAST_CONNECT", {"AIDOT_FAST_CONNECT": "off"}) is False
    )
    assert settings.tristate("AIDOT_FAST_CONNECT", {"AIDOT_FAST_CONNECT": "2"}) is None

    with pytest.raises(KeyError):
        settings.tristate("AIDOT_NOT_A_KNOB", {})
    with pytest.raises(ValueError):
        settings.tristate("AIDOT_HEALTH_LINE_S", {})
