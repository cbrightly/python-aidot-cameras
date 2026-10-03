"""The vendored aiortc must be exactly what VENDOR.md says it is.

VENDOR.md, ``aidot_cameras/_vendor/__init__.py`` and docs/API-STABILITY.md tell
a reader the vendored copy is upstream aiortc plus a few import rewrites. An
edit made inside it (a behaviour change, however small) silently makes all of
that untrue, and re-vendoring by the documented steps would then drop the edit
without anyone noticing. This pins every vendored source file to a recorded
hash, so such an edit cannot land without the manifest - and, by the message
below, VENDOR.md - being updated with it.

Regenerate the manifest after a deliberate change (and describe the change in
VENDOR.md first)::

    python - <<'EOF'
    import hashlib, pathlib
    root = pathlib.Path("aidot_cameras/_vendor/aiortc")
    for p in sorted(root.rglob("*.py")):
        if "__pycache__" not in p.parts:
            print(hashlib.sha256(p.read_bytes()).hexdigest(), "",
                  p.relative_to(root).as_posix())
    EOF
"""

import hashlib
import pathlib

ROOT = pathlib.Path(__file__).resolve().parents[1]
VENDORED = ROOT / "aidot_cameras" / "_vendor" / "aiortc"
MANIFEST = ROOT / "tests" / "data" / "vendored_aiortc.sha256"


def _recorded() -> dict:
    out = {}
    for line in MANIFEST.read_text().splitlines():
        if line.strip() and not line.startswith("#"):
            digest, rel = line.split(None, 1)
            out[rel.strip()] = digest
    return out


def _actual() -> dict:
    return {
        p.relative_to(VENDORED).as_posix(): hashlib.sha256(p.read_bytes()).hexdigest()
        for p in VENDORED.rglob("*.py")
        if "__pycache__" not in p.parts
    }


def test_vendored_aiortc_matches_its_manifest():
    recorded, actual = _recorded(), _actual()
    changed = sorted(
        f for f in recorded.keys() & actual.keys() if recorded[f] != actual[f]
    )
    added = sorted(actual.keys() - recorded.keys())
    removed = sorted(recorded.keys() - actual.keys())
    assert not (changed or added or removed), (
        "vendored aiortc differs from tests/data/vendored_aiortc.sha256 - "
        f"changed {changed}, added {added}, removed {removed}. If this is "
        "deliberate, describe it in aidot_cameras/_vendor/aiortc/VENDOR.md and "
        "regenerate the manifest (see this module's docstring)."
    )


def test_the_manifest_covers_the_files_vendor_md_names():
    # The two files VENDOR.md says differ from upstream must be pinned too.
    recorded = _recorded()
    for rel in ("contrib/signaling.py", "rate.py"):
        assert rel in recorded
