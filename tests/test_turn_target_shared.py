"""The three TURN allocations in the SDES open share one target pick.

Pre-offer, echo-fallback and post-offer each chose our TURN entry and parsed
its URI inline. The three versions are copied below verbatim (as functions)
and checked against the shared helpers on every shape of input the cloud has
sent or could send.
"""

import hashlib
import re

import pytest

from aidot_cameras.camera.sdes_open import _turn_relay_record, _turn_target


def _parse(entry):
    uri = next((str(u) for u in (entry.get("Uris") or []) if "turn:" in str(u)), "")
    m = re.search(r"turns?:([^:?]+)(?::(\d+))?", uri)
    if not m:
        return None
    return (
        m.group(1),
        int(m.group(2) or 5349),
        (entry.get("Username") or "").encode(),
        str(entry.get("Password") or "").encode(),
    )


def _previous_pre_offer(entries, user_id):
    if not entries:  # guarded by `if _sdes_turn_entries` at the call site
        return None
    entry = next((e for e in entries if e.get("Username") == user_id), entries[0])
    return _parse(entry)


def _previous_echo_fallback(entries, user_id):
    entry = next(
        (e for e in entries if e.get("Username") == user_id),
        entries[0] if entries else None,
    )
    if not entry:
        return None
    return _parse(entry)


def _previous_post_offer(entries, user_id):
    entry = None
    for e in entries:
        if e.get("Username") == user_id:
            entry = e
            break
    if entry is None and entries:
        entry = entries[0]
    if not entry:
        return None
    return _parse(entry)


ME = "user-1"
CASES = [
    [],
    [{"Username": ME, "Password": "pw", "Uris": ["turn:1.2.3.4:3478?transport=udp"]}],
    [{"Username": "other", "Password": "x", "Uris": ["turn:9.9.9.9:5000"]}],
    [
        {"Username": "other", "Password": "x", "Uris": ["turn:9.9.9.9:5000"]},
        {"Username": ME, "Password": "pw", "Uris": ["stun:1.1.1.1", "turns:t.example"]},
    ],
    [{"Username": ME, "Password": 1234, "Uris": ["turn:h"]}],
    [{"Username": ME, "Uris": []}],
    [{"Username": None, "Password": None, "Uris": None}],
    [{}],
    [{"Username": ME, "Password": "pw", "Uris": ["stun:only"]}],
]


@pytest.mark.parametrize("entries", CASES)
def test_target_matches_all_three_previous_versions(entries):
    got = _turn_target(entries, ME)
    for prev in (_previous_pre_offer, _previous_echo_fallback, _previous_post_offer):
        assert got == prev(entries, ME), prev.__name__


def test_relay_record_matches_the_previous_tuple():
    host, port, user, pw = "turn.host", 3478, b"user-1", b"pw"
    alloc = ("54.1.2.3", 51234, b"realm", b"nonce")
    key = hashlib.md5(user + b":" + b"realm" + b":" + pw).digest()
    assert _turn_relay_record(alloc, host, port, user, pw) == (
        "54.1.2.3",
        51234,
        b"realm",
        b"nonce",
        host,
        port,
        key,
        user,
    )
