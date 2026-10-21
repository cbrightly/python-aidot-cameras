"""A "swallowed exception in %s" line must name the function it is in.

These DEBUG lines are the only trace of an exception the code chose to ignore,
and the name is how a reader finds the site. The 2026-10-15 review found about
thirty that named a function the code had since moved out of (a `stop` whose
body became `_stop_once`, a coroutine split into closures). This walks every
module and checks each literal name against the nearest enclosing function.
"""

import ast
import pathlib

PKG = pathlib.Path(__file__).resolve().parent.parent / "aidot_cameras"


def _sites():
    for path in sorted(PKG.rglob("*.py")):
        if "_vendor" in path.parts:
            continue
        tree = ast.parse(path.read_text())
        yield from _walk(tree, [], path)


def _walk(node, stack, path):
    for child in ast.iter_child_nodes(node):
        if isinstance(child, (ast.FunctionDef, ast.AsyncFunctionDef)):
            yield from _walk(child, [*stack, child.name], path)
            continue
        if isinstance(child, ast.Call):
            args = child.args
            for i, arg in enumerate(args):
                if (
                    isinstance(arg, ast.Constant)
                    and isinstance(arg.value, str)
                    and "swallowed exception in %s" in arg.value
                ):
                    idx = i + arg.value.count("%s")
                    if idx < len(args) and isinstance(args[idx], ast.Constant):
                        yield (
                            path,
                            args[idx].lineno,
                            args[idx].value,
                            (stack[-1] if stack else "<module>"),
                        )
                    break
        yield from _walk(child, stack, path)


def test_there_are_sites_to_check():
    """Guard the guard: a refactor of the log format must not make this vacuous."""
    assert len(list(_sites())) > 50


def test_every_swallowed_exception_line_names_its_own_function():
    wrong = [
        f"{path.relative_to(PKG.parent)}:{line}: says {name!r}, is in {actual!r}"
        for path, line, name, actual in _sites()
        if str(name).split(".")[-1] != actual
    ]
    assert not wrong, "\n".join(wrong)
