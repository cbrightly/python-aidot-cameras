"""The scripts/ harnesses must stay runnable against the installed package.

Every script silently broke at the 0.12.0 ``aidot`` -> ``aidot_cameras`` rename
(they kept importing ``aidot``, which the rename handed back to the upstream
lights-only dependency) and nothing in CI noticed for 16 releases.  This test
makes script rot loud: each argparse script must exit 0 on ``--help`` (which
executes its module-level imports in a fresh interpreter), and the import-only
probe must import.
"""
import os
import subprocess
import sys

SCRIPTS_DIR = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "scripts")

ARGPARSE_SCRIPTS = [
    "smoke_stream.py",
    "camera_diag.py",
    "sdes_validate.py",
    "go2rtc_serve.py",
    "sdes_soak_monitor.py",
]


def test_argparse_scripts_run_help():
    for name in ARGPARSE_SCRIPTS:
        path = os.path.join(SCRIPTS_DIR, name)
        assert os.path.exists(path), f"scripts/{name} missing"
        proc = subprocess.run(
            [sys.executable, path, "--help"],
            capture_output=True, text=True, timeout=60,
        )
        assert proc.returncode == 0, (
            f"scripts/{name} --help failed (rc={proc.returncode}):\n"
            f"stderr: {proc.stderr[-2000:]}"
        )


def test_audio_mux_probe_imports():
    # Not argparse-based; importing it executes its imports but not its cases
    # (guarded by __main__), which is exactly the rot check we need.
    proc = subprocess.run(
        [sys.executable, "-c",
         "import runpy, sys; sys.argv=['x']; "
         "import importlib.util as u; "
         f"spec = u.spec_from_file_location('audio_mux_probe', {os.path.join(SCRIPTS_DIR, 'audio_mux_probe.py')!r}); "
         "m = u.module_from_spec(spec); spec.loader.exec_module(m)"],
        capture_output=True, text=True, timeout=120,
    )
    assert proc.returncode == 0, f"audio_mux_probe import failed:\n{proc.stderr[-2000:]}"
