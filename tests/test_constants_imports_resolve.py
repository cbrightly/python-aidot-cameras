"""Every name imported from a constants module must actually exist there.

A lazy ``from .camera.constants import X`` inside a function is invisible to the
rest of the suite: the package imports fine, every test passes, ruff and mypy
pass, and it raises ImportError only when that code path runs for real. That is
how a missing ``aidot_api_base`` reached `main` and surfaced on a live run
instead of in CI.

Parsed with ast rather than a regex - the parenthesised multi-line import form
is the common one here, and a regex that mis-handles it either misses names or
invents them.
"""
import ast
import os
import pathlib
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.constants as camera_constants  # noqa: E402

_PKG = pathlib.Path(__file__).resolve().parent.parent / "aidot_cameras"


def _constants_imports():
    """(imported_name, source_file) for every `from ...constants import ...`."""
    for path in sorted(_PKG.rglob("*.py")):
        if "_vendor" in path.parts:
            continue          # vendored aiortc ships byte-identical
        try:
            tree = ast.parse(path.read_text())
        except SyntaxError:                                  # pragma: no cover
            continue
        for node in ast.walk(tree):
            if not isinstance(node, ast.ImportFrom):
                continue
            # Relative imports only: `.constants`, `..camera.constants`.
            if not node.level or not (node.module or "").endswith("constants"):
                continue
            for alias in node.names:
                if alias.name != "*":
                    yield alias.name, str(path.relative_to(_PKG.parent))


def test_every_constants_import_resolves():
    missing = [
        (name, where)
        for name, where in _constants_imports()
        if not hasattr(camera_constants, name)
    ]
    assert not missing, (
        "constants imports that do not resolve - a caller landed without its "
        f"callee: {missing}"
    )


def test_the_scan_actually_finds_imports():
    """Guard the guard: a scan matching nothing would pass vacuously."""
    found = list(_constants_imports())
    assert len(found) >= 5, found
    # and it must yield real identifiers, never punctuation from a bad parse
    assert all(n.isidentifier() for n, _ in found), found
