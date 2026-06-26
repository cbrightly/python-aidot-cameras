"""Guard against NameError-class regressions in device_client.py.

`ast.parse`/`py_compile` only catch syntax errors, not undefined names used at
runtime (e.g. a variable referenced in one method but defined only in another).
That class of bug shipped once (0.5.5/0.5.6: `_fast_connect` referenced in
`_open_sdes_stream` but defined only in `_async_open_webrtc_stream_impl`,
breaking every SDES camera open). This test runs pyflakes and fails on any
undefined name except a small allowlist of intentional closure patterns.
"""

import pathlib
import subprocess
import sys

import pytest

_SRC = (
    pathlib.Path(__file__).resolve().parent.parent
    / "src" / "aidot_cameras" / "device_client.py"
)

# Names pyflakes can't resolve because they're bound in an inner runtime scope
# (closure) and referenced from a sibling scope; verified working in production
# and already marked `# noqa: F821` in the source.
_ALLOWLIST = {"_enc_c8_sctp"}


def test_device_client_has_no_undefined_names():
    try:
        proc = subprocess.run(
            [sys.executable, "-m", "pyflakes", str(_SRC)],
            capture_output=True, text=True,
        )
    except FileNotFoundError:  # pragma: no cover
        pytest.skip("pyflakes not available")
    if "No module named pyflakes" in proc.stderr:  # pragma: no cover
        pytest.skip("pyflakes not installed")

    undefined = [
        line for line in proc.stdout.splitlines()
        if "undefined name" in line
        and not any(name in line for name in _ALLOWLIST)
    ]
    assert not undefined, "Undefined names (NameError risk):\n" + "\n".join(undefined)
