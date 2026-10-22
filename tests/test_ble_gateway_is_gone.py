"""The BLE-mesh hub relay is gone, and nothing in the package reaches for it.

``aidot_cameras.ble_gateway`` shipped in 0.14.0 as control for BLE-mesh bulbs
behind a ``BleMesh_Hub``. It never had a consumer: no module in this package
imported it, the Home Assistant integration never wired it, and the branch that
would have (``feat/ble-mesh-gateway``) was closed unlanded on 2026-09-15 because
the reference account has no such hub to validate against - the 0.14.0 note
that it "has a mesh hub but no mesh children" is no longer true of the fleet
either. A 564-line module with 20 green tests and zero callers is dead code
shipping in every wheel.

It was never on the API-stability table, so this is a removal, not a
deprecation. Pinned rather than left to a grep so it cannot quietly return.
"""

import importlib
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

PACKAGE = os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "aidot_cameras"
)


def test_the_module_does_not_exist():
    assert not os.path.exists(os.path.join(PACKAGE, "ble_gateway.py"))


def test_it_cannot_be_imported():
    with pytest.raises(ModuleNotFoundError):
        importlib.import_module("aidot_cameras.ble_gateway")


def test_nothing_in_the_package_still_references_it():
    """No import, name or attribute in the package refers to the removed BLE
    gateway (comments and prose do not count)."""
    import ast

    from tests.ast_guards import package_modules

    offenders = []
    for path in package_modules():
        for node in ast.walk(ast.parse(path.read_text())):
            names = []
            if isinstance(node, ast.Name):
                names = [node.id]
            elif isinstance(node, ast.Attribute):
                names = [node.attr]
            elif isinstance(node, (ast.Import, ast.ImportFrom)):
                names = [a.name for a in node.names] + [
                    getattr(node, "module", "") or ""
                ]
            if any("ble_gateway" in n or "BleMesh" in n for n in names):
                offenders.append(f"{path.name}:{node.lineno}")
    assert not offenders, "stale references: %s" % offenders
