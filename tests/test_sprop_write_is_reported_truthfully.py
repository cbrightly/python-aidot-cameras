"""The sprop cache log must not announce a write that did not happen.

`_save_sprop` has three outcomes and only one of them writes anything: the
camera is already marked unstable and it returns immediately; the in-band sets
differ from the cached ones so it marks the camera unstable and DROPS the cache;
or it actually writes.

The bridge announced `bridge: cached sprop-parameter-sets for <id>` after
calling it, unconditionally. On a camera carrying the unstable marker - which is
the state of the A001064 on the reference fleet - that line asserts a write that
provably did not occur, and it is the ONLY observable the sprop path has. A log
that lies about the one thing it exists to report is worse than no log: it sent
an investigation looking for a cache that was never going to be there.

So `_save_sprop` reports whether it wrote, and the caller is gated on the answer.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest  # noqa: E402

from aidot_cameras.camera import protocol  # noqa: E402

SPROP = "Z2QAH6zZQFAFuwEQAAADABAAAAMDwPGDGWA=,aOvjyyLA"
OTHER = "Z0LgHtoCgPRA,aM4wpIA="


@pytest.fixture
def sprop_dir(tmp_path, monkeypatch):
    monkeypatch.setattr(protocol, "_SPROP_DIR", str(tmp_path))
    return tmp_path


def test_a_real_write_reports_true(sprop_dir):
    assert protocol._save_sprop("cam-fresh", SPROP) is True
    assert protocol._load_sprop("cam-fresh") == SPROP


def test_an_unstable_camera_reports_false(sprop_dir):
    """The A001064's state on the reference fleet: marked, so nothing is cached."""
    protocol._save_sprop("cam-unstable", SPROP)
    # A second, DIFFERENT set marks it unstable and drops the cache.
    protocol._save_sprop("cam-unstable", OTHER)
    assert protocol._sprop_is_unstable("cam-unstable")

    # Every later call is a no-op, and must say so.
    assert protocol._save_sprop("cam-unstable", SPROP) is False
    assert protocol._load_sprop("cam-unstable") is None


def test_the_call_that_marks_a_camera_unstable_also_reports_false(sprop_dir):
    """It drops the cache rather than writing - that is not a write either."""
    assert protocol._save_sprop("cam-changing", SPROP) is True
    assert protocol._save_sprop("cam-changing", OTHER) is False
    assert protocol._load_sprop("cam-changing") is None


def test_an_unwritable_cache_directory_reports_false(sprop_dir, monkeypatch):
    """The feature is inert if the directory is not writable; do not claim a write."""
    def _boom(*_a, **_k):
        raise OSError("read-only file system")

    monkeypatch.setattr(protocol.os, "makedirs", _boom)
    assert protocol._save_sprop("cam-readonly", SPROP) is False


def test_the_bridge_only_announces_a_cache_write_when_one_happened():
    """The log line must be gated on the return value, not merely follow the call.

    Checked against the source because the bridge loop cannot be driven in a
    unit test. The assertion is on the CONTROL FLOW - an unconditional
    `_status(...)` after `_save_sprop(...)` is exactly the defect.
    """
    import ast
    import pathlib

    from aidot_cameras.camera import sdes_open

    src = pathlib.Path(sdes_open.__file__).read_text()
    calls = [
        n for n in ast.walk(ast.parse(src))
        if isinstance(n, ast.Call) and isinstance(n.func, ast.Name)
        and n.func.id == "_save_sprop"
    ]
    assert calls, "no _save_sprop call found in the bridge - has it moved?"

    # Every call site must consume the result: as an `if` test, or assigned to a
    # name that something later branches on. A bare expression-statement call
    # cannot be gating anything.
    bare = []
    for node in ast.walk(ast.parse(src)):
        if isinstance(node, ast.Expr) and isinstance(node.value, ast.Call):
            fn = node.value.func
            if isinstance(fn, ast.Name) and fn.id == "_save_sprop":
                bare.append(node.lineno)
    assert bare == [], (
        f"_save_sprop is called and its result discarded at line(s) {bare}. The "
        "announcement that follows then claims a cache write that did not "
        "happen for an unstable camera."
    )
