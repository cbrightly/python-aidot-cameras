"""The validator must not invalidate a token another process is holding.

The AiDot cloud issues ONE live token per account: a second login invalidates
the first. `scripts/live_validate.py` called `async_post_login()` on every
invocation, which is fine for the CI gate (one process, one login) and breaks
the moment anything runs it as a child.

Measured 2026-08-23, a between-session campaign that held a client to read and
write a cloud property between sessions:

    arm StreamType=1 rep0: ... pass=True
    async_get ClientError: 401 .../v17/houses {'code': 21026,
                                               'desc': 'Please login again.'}
    async_refresh_token ClientError: 400 .../v17/users/refreshToken
    aidot.exceptions.AidotAuthFailed

The parent's token died the moment the first child logged in, and the refresh
token had been rotated out from under it too, so recovery was impossible. The
campaign lost three of its four arms.

`aidot_cameras/cloud_auth.py` already solves this for the go2rtc CLI: load a
shared `AIDOT_TOKEN_FILE` if there is one, and register the write-back cache so
a rotation reaches every later holder instead of stranding them. The validator
has to go through the same door.

Unset `AIDOT_TOKEN_FILE` must behave exactly as before -- the release gate runs
that way, and a change to how it authenticates is not a change worth making
blind.
"""
import os
import pathlib
import re

_REPO = pathlib.Path(__file__).resolve().parents[1]


def _source() -> str:
    return (_REPO / "scripts" / "live_validate.py").read_text()


def test_the_validator_authenticates_through_the_shared_helper():
    src = _source()
    assert re.search(r"from aidot_cameras\.cloud_auth import .*_make_client", src), (
        "live_validate must authenticate through aidot_cameras.cloud_auth so a "
        "shared AIDOT_TOKEN_FILE is honoured; its own async_post_login() call "
        "invalidates any token another process is holding")
    assert "_make_client(" in src


def test_the_helper_is_the_one_the_cli_uses():
    # Not a copy. A second implementation of the atomic 0600 token write is a
    # security-relevant duplicate, and the two would drift.
    from aidot_cameras import __main__ as cli
    from aidot_cameras import cloud_auth

    assert cli._make_client is cloud_auth._make_client
    assert cli._write_token_file is cloud_auth._write_token_file
    assert cli._install_token_cache is cloud_auth._install_token_cache


def test_a_token_file_is_used_instead_of_logging_in_again(tmp_path, monkeypatch):
    """The whole point: with a token on disk, no password round-trip happens.

    A login is what invalidates the other holder, so "did it log in?" is the
    behaviour under test, not "did it authenticate?".
    """
    import asyncio

    from aidot_cameras import cloud_auth

    token = tmp_path / "token.json"
    token.write_text('{"accessToken": "t", "refreshToken": "r", "id": "u"}')
    monkeypatch.setenv("AIDOT_TOKEN_FILE", str(token))

    logins = []

    class _Client:
        def __init__(self, **kw):
            self.kw = kw

        async def async_post_login(self):
            logins.append(1)

        def set_token_fresh_cb(self, cb):
            self.cb = cb

    monkeypatch.setattr(cloud_auth, "AidotClient", _Client)
    client = asyncio.run(cloud_auth._make_client(session=object()))

    assert logins == [], "a stored token must not be followed by a fresh login"
    assert client.kw.get("token") == {"accessToken": "t", "refreshToken": "r",
                                      "id": "u"}
    assert getattr(client, "cb", None) is not None, (
        "a rotation must be written back, or the next holder finds a dead token")


def test_no_token_file_still_logs_in_with_the_password(tmp_path, monkeypatch):
    # The CI gate's path. Unchanged.
    import asyncio

    from aidot_cameras import cloud_auth

    monkeypatch.delenv("AIDOT_TOKEN_FILE", raising=False)
    monkeypatch.setenv("AIDOT_USERNAME", "u")
    monkeypatch.setenv("AIDOT_PASSWORD", "p")

    logins = []

    class _Client:
        def __init__(self, **kw):
            self.kw = kw

        async def async_post_login(self):
            logins.append(1)

    monkeypatch.setattr(cloud_auth, "AidotClient", _Client)
    asyncio.run(cloud_auth._make_client(session=object()))
    assert logins == [1]


def test_the_repo_ships_no_token_file_path_by_default():
    # A default path would make the gate share state between unrelated runs.
    assert os.environ.get("AIDOT_TOKEN_FILE") in (None, "")


def test_credentials_travel_as_parameters_not_environment():
    """The validator must not publish secrets into os.environ.

    The first version of the token branch did exactly that - three
    setdefault calls so _make_client could re-read the same values - which
    handed the decrypted password to every spawned child (ffmpeg included)
    and carried a dead country assignment, because cloud_auth's old
    module-level country default was bound at import time.
    """
    src = _source()
    assert "os.environ.setdefault(\"AIDOT_USERNAME\"" not in src
    assert "os.environ.setdefault(\"AIDOT_PASSWORD\"" not in src
    assert re.search(r"_make_client\(\s*\n?\s*http,\s*\n\s*username=", src), (
        "credentials must be passed to _make_client as parameters")


def test_an_explicit_country_beats_the_environment(tmp_path, monkeypatch):
    # The dead-shim bug: AIDOT_COUNTRY was snapshotted at cloud_auth import,
    # so env tweaks after import silently did nothing and a creds-file
    # country became US. Parameters must win, and must not depend on import
    # order.
    import asyncio

    from aidot_cameras import cloud_auth

    monkeypatch.delenv("AIDOT_TOKEN_FILE", raising=False)
    monkeypatch.setenv("AIDOT_COUNTRY", "US")

    captured = {}

    class _Client:
        def __init__(self, **kw):
            captured.update(kw)

        async def async_post_login(self):
            pass

    monkeypatch.setattr(cloud_auth, "AidotClient", _Client)
    asyncio.run(cloud_auth._make_client(object(), username="u", password="p",
                                        country="DE"))
    assert captured["country_code"] == "DE"


def test_missing_credentials_raise_instead_of_exiting(monkeypatch):
    # cloud_auth is shared library code now; sys.exit belongs to the CLI.
    # A validator hitting this must reach its own report path, not die with
    # a CLI-worded message.
    import asyncio

    import pytest as _pytest

    from aidot_cameras import cloud_auth

    for var in ("AIDOT_TOKEN_FILE", "AIDOT_USERNAME", "AIDOT_PASSWORD"):
        monkeypatch.delenv(var, raising=False)
    with _pytest.raises(cloud_auth.CloudAuthUnavailable):
        asyncio.run(cloud_auth._make_client(object()))
