"""The CI type check lists only the mypy findings a change adds.

scripts/mypy_new_findings.py reads two mypy runs. A finding that only moved
must not be reported, and a new one must be, even when the same message is
already present elsewhere in the file.
"""

import importlib.util
import os
import sys

import pytest

SCRIPT = os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
    "scripts",
    "mypy_new_findings.py",
)


@pytest.fixture(scope="module")
def mnf():
    spec = importlib.util.spec_from_file_location("mypy_new_findings", SCRIPT)
    mod = importlib.util.module_from_spec(spec)
    sys.modules["mypy_new_findings"] = mod
    spec.loader.exec_module(mod)
    return mod


_BASE = """\
aidot_cameras/a.py:10: error: Cannot find implementation or library stub for module named "av"  [import-not-found]
aidot_cameras/b.py:20: error: Item "None" of "X | None" has no attribute "y"  [union-attr]
aidot_cameras/b.py:30: note: See https://mypy.readthedocs.io
Found 2 errors in 2 files (checked 74 source files)
"""


def test_a_finding_that_only_moved_is_not_new(mnf):
    head = _BASE.replace(":10:", ":15:").replace(":20:", ":99:")
    assert mnf.new_findings(_BASE, head) == []


def test_a_new_finding_is_reported_with_its_line(mnf):
    head = _BASE + (
        'aidot_cameras/c.py:4784: error: Incompatible return value type (got "S", '
        'expected "dict[Any, Any]")  [return-value]\n'
    )
    assert mnf.new_findings(_BASE, head) == [
        (
            "aidot_cameras/c.py",
            4784,
            'Incompatible return value type (got "S", expected "dict[Any, Any]")  '
            "[return-value]",
        )
    ]


def test_one_more_copy_of_an_existing_finding_is_new(mnf):
    extra = 'aidot_cameras/b.py:40: error: Item "None" of "X | None" has no attribute "y"  [union-attr]\n'
    added = mnf.new_findings(_BASE, _BASE + extra)
    assert [line for _, line, _ in added] == [40]


def test_notes_and_the_summary_line_are_not_findings(mnf):
    assert len(mnf.findings(_BASE)) == 2


def test_the_report_counts_and_lists_what_is_new(mnf, tmp_path, capsys):
    base, head = tmp_path / "base.txt", tmp_path / "head.txt"
    base.write_text(_BASE)
    head.write_text(
        _BASE + 'aidot_cameras/d.py:1: error: Name "z" is not defined  [name-defined]\n'
    )
    assert mnf.main(["x", str(base), str(head)]) == 0
    out = capsys.readouterr().out
    assert "1 new finding(s)" in out
    assert 'aidot_cameras/d.py:1: Name "z" is not defined' in out
