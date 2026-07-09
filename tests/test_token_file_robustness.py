"""Tests for the standalone CLI's token-cache read/write robustness.

Covers the 0.11.3 fixes: the token file is written atomically at 0600 (a
serialization failure must not truncate a previously-valid token), and a
corrupt/partial cache is a caught ValueError (so _make_client can fall back
to username/password login instead of crashing).
"""
import os
import stat

import pytest

from aidot.__main__ import _read_token_file, _write_token_file


def test_write_token_file_round_trips_and_is_0600(tmp_path):
    p = tmp_path / "token.json"
    _write_token_file(str(p), {"accessToken": "abc", "id": "u1"})
    assert _read_token_file(str(p)) == {"accessToken": "abc", "id": "u1"}
    assert stat.S_IMODE(os.stat(p).st_mode) == 0o600


def test_write_token_file_preserves_previous_on_serialization_failure(tmp_path):
    p = tmp_path / "token.json"
    _write_token_file(str(p), {"accessToken": "good"})  # seed a valid token

    # A set is not JSON-serializable, so json.dump raises mid-write. The atomic
    # temp-then-replace write must leave the previously-valid token untouched.
    with pytest.raises(TypeError):
        _write_token_file(str(p), {"broken": {1, 2, 3}})

    assert _read_token_file(str(p)) == {"accessToken": "good"}
    # And it must not leave a stray temp file behind.
    assert [f for f in os.listdir(tmp_path) if f.startswith(".token-")] == []


def test_read_token_file_raises_valueerror_on_corrupt(tmp_path):
    p = tmp_path / "token.json"
    p.write_text("{ not valid json", encoding="utf-8")
    # json.JSONDecodeError is a subclass of ValueError - the exact type
    # _make_client catches to fall back to password login rather than crash.
    with pytest.raises(ValueError):
        _read_token_file(str(p))
