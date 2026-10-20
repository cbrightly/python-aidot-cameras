"""The settings registry is the one inventory of AIDOT_* variables, and the one
parser for them.

Two kinds of test here. The parsing tests pin the malformed-value rules that
used to differ per call site (a bare float() stopped the library importing on
an empty value; `nan` made two grace rules fire at once). The inventory tests
walk the package source and the docs so a variable cannot be added in one
place and forgotten in the other.
"""

from __future__ import annotations

import os
import re

import pytest

from aidot_cameras import settings

PKG = os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "aidot_cameras"
)
DOCS = os.path.dirname(PKG)

_LITERAL = re.compile(r"""["'](AIDOT_[A-Z0-9_]+)["']""")


def _package_sources() -> list[tuple[str, str]]:
    """(path relative to the repo, text) for every module in the package,
    excluding the vendored tree and the registry itself."""
    out: list[tuple[str, str]] = []
    for root, _dirs, files in os.walk(PKG):
        if "_vendor" in root:
            continue
        for f in sorted(files):
            if not f.endswith(".py") or f == "settings.py":
                continue
            path = os.path.join(root, f)
            with open(path, encoding="utf-8") as fh:
                out.append((os.path.relpath(path, DOCS), fh.read()))
    return out


def _source_names() -> set[str]:
    """Every AIDOT_* string literal in the package."""
    names: set[str] = set()
    for _rel, text in _package_sources():
        names.update(_LITERAL.findall(text))
    return names


_RAW_READ = re.compile(
    r"\b_?os\.(?:environ\.get|getenv|environ)\s*[\(\[]\s*([^,\)\]]+)"
)


def _raw_read_sites() -> set[tuple[str, str]]:
    """Every os.environ read in the package that names an AIDOT_ variable, as
    (file, name). A literal argument is the name; an identifier is resolved
    through a `NAME = "AIDOT_..."` assignment in the same file; an argument
    that is a plain variable (the CLI's helper takes the name as a parameter)
    is recorded as `<variable>` so the site is still pinned."""
    sites: set[tuple[str, str]] = set()
    for rel, text in _package_sources():
        for arg in _RAW_READ.findall(text):
            arg = arg.strip()
            lit = _LITERAL.fullmatch(arg)
            if lit:
                sites.add((rel, lit.group(1)))
                continue
            if re.fullmatch(r"[A-Za-z_][A-Za-z0-9_]*", arg):
                assigned = re.search(
                    r"^\s*%s\s*=\s*%s" % (re.escape(arg), _LITERAL.pattern),
                    text,
                    re.M,
                )
                if assigned:
                    sites.add((rel, assigned.group(1)))
                else:
                    sites.add((rel, f"<{arg}>"))
            # A literal that is not an AIDOT_ name (XDG_CONFIG_HOME) is not ours.
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


def test_the_registry_says_which_reads_are_still_raw():
    """Every os.environ read of an AIDOT_ variable that bypasses settings.read
    is pinned here by file and name, so a new raw read fails this test and a
    migrated one has to be struck off. The eight named sites are the
    credential reads and the three measurement seams, which stay raw by design
    and say so in their Knob notes; the ninth is the CLI's tri-state bool
    helper, which takes the name as a parameter and must return None rather
    than a default (see its docstring)."""
    assert _raw_read_sites() == {
        ("aidot_cameras/__main__.py", "<name>"),
        ("aidot_cameras/camera/client.py", "AIDOT_EXPT_PEERID_CLASS"),
        ("aidot_cameras/camera/client.py", "AIDOT_EXPT_PEERID_FILE"),
        ("aidot_cameras/camera/sdes_open.py", "AIDOT_EXPT_CAP_FILE"),
        ("aidot_cameras/cloud_auth.py", "AIDOT_COUNTRY"),
        ("aidot_cameras/cloud_auth.py", "AIDOT_PASSWORD"),
        ("aidot_cameras/cloud_auth.py", "AIDOT_TOKEN_FILE"),
        ("aidot_cameras/cloud_auth.py", "AIDOT_USERNAME"),
        ("aidot_cameras/credentials.py", "AIDOT_COUNTRY"),
        ("aidot_cameras/credentials.py", "AIDOT_CRED_KEY_FILE"),
        ("aidot_cameras/credentials.py", "AIDOT_PASSWORD"),
        ("aidot_cameras/credentials.py", "AIDOT_USERNAME"),
    }
    # The knobs read raw by design say so in the table.
    raw = sorted(n for n, k in settings.KNOBS.items() if k.note.startswith("raw read"))
    assert raw == sorted({n for _f, n in _raw_read_sites() if n.startswith("AIDOT_")})
