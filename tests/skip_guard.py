"""Fail the run when a test skips for a reason the suite does not expect.

A skipped test ran nothing. A test that needs a tool (ffmpeg, PyAV, numpy)
skips quietly when the tool is missing, and a green run then proves nothing
about what that test covers: the integration's clip test skipped in CI for
want of ffmpeg, and a clip losing its first seconds passed (2026-10-07).

With ``AIDOT_FAIL_ON_SKIP`` set - CI sets it - every skip is checked against
``EXPECTED_SKIP_REASONS``, the reasons that are a deliberate choice of which
test applies to the installed upstream (the account back-reference tests, which
skip on python-aidot 0.3.56). Any other skip fails the run, with the tests named. Unset,
the guard does nothing.
"""

from __future__ import annotations

import os

ENV = "AIDOT_FAIL_ON_SKIP"

#: Reasons a skip may give in any environment. Matched as prefixes.
EXPECTED_SKIP_REASONS = (
    # tests/test_upstream_compat.py and friends: the account back-reference
    # tests skip on python-aidot 0.3.56, which does not take one.
    "upstream takes no account client",
    "upstream DeviceClient takes the account client",
)


def _reason(report) -> str:
    longrepr = report.longrepr
    if isinstance(longrepr, tuple) and len(longrepr) == 3:
        reason = str(longrepr[2])
    else:
        reason = str(longrepr)
    return reason.removeprefix("Skipped: ")


def pytest_sessionfinish(session, exitstatus) -> None:
    if not os.environ.get(ENV):
        return
    reporter = session.config.pluginmanager.get_plugin("terminalreporter")
    skipped = reporter.stats.get("skipped", []) if reporter else []
    unexpected = [
        (report.nodeid, reason)
        for report in skipped
        if not (reason := _reason(report)).startswith(EXPECTED_SKIP_REASONS)
    ]
    if not unexpected:
        return
    lines = [
        "",
        f"{ENV} is set and {len(unexpected)} test(s) skipped for a reason the "
        "suite does not expect (a missing tool?):",
        *(f"  {nodeid}: {reason}" for nodeid, reason in unexpected),
    ]
    reporter.write_line("\n".join(lines), red=True, bold=True)
    session.exitstatus = 1
