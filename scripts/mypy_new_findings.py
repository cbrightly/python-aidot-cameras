#!/usr/bin/env python3
"""Print the mypy findings a change adds, against a baseline run.

The type check is informational and its full output runs to hundreds of
findings, most of them missing stubs. 1.0.0rc58 shipped a DTLS open that
returned ``None`` instead of its session, and mypy had said so
("got WebRTCSession, expected dict") in a log nobody could read for the noise.
Read against the previous run, that change added 35 findings, that one among
them.

Findings are compared by file, message and code, without line numbers, so code
that only moves does not show up as new. A finding that appears more often
than before counts as new for each extra time.

Usage: mypy_new_findings.py BASE.txt HEAD.txt
"""

import re
import sys
from collections import Counter

_FINDING = re.compile(r"^(?P<file>[^:\s]+\.py):(?P<line>\d+): error: (?P<msg>.*)$")


def findings(text: str) -> list[tuple[str, int, str]]:
    """(file, line, message) for each error line of a mypy run."""
    out = []
    for raw in text.splitlines():
        m = _FINDING.match(raw.strip())
        if m:
            out.append((m["file"], int(m["line"]), m["msg"]))
    return out


def new_findings(base: str, head: str) -> list[tuple[str, int, str]]:
    """The head findings that the base run did not have, in head order."""
    seen = Counter((f, msg) for f, _, msg in findings(base))
    added = []
    for f, line, msg in findings(head):
        if seen[(f, msg)]:
            seen[(f, msg)] -= 1
        else:
            added.append((f, line, msg))
    return added


def main(argv: list[str]) -> int:
    if len(argv) != 3:
        print(__doc__.strip().splitlines()[-1], file=sys.stderr)
        return 2
    with open(argv[1]) as fh:
        base = fh.read()
    with open(argv[2]) as fh:
        head = fh.read()
    added = new_findings(base, head)
    print(f"### mypy: {len(added)} new finding(s) against the baseline")
    if added:
        print()
        print("```")
        for f, line, msg in added:
            print(f"{f}:{line}: {msg}")
        print("```")
    return 0


if __name__ == "__main__":
    sys.exit(main(sys.argv))
