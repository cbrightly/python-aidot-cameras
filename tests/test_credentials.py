"""Unit tests for aidot_cameras.credentials.

Pure file/env logic - no network or hardware - so the whole priority order
(env vars, then Fernet-encrypted pair, then legacy plain JSON with auto-migration)
is exercised against tmp_path, including the permission hardening and every
error path.
"""

import json
import os
import stat
import sys

import pytest

from aidot_cameras import credentials

_CRED_ENV = ("AIDOT_USERNAME", "AIDOT_PASSWORD", "AIDOT_COUNTRY", "AIDOT_CRED_KEY_FILE")


@pytest.fixture(autouse=True)
def _no_cred_env(monkeypatch):
    """Nothing leaks in from the runner's environment: every file-based test
    starts with the credential env vars unset, and the env-priority tests set
    only what they assert on."""
    for name in _CRED_ENV:
        monkeypatch.delenv(name, raising=False)


def _prefix(tmp_path):
    # creds_path is a path PREFIX: save writes <prefix>.enc / <prefix>.key, and
    # the plain-JSON fallback is <prefix> itself.
    return str(tmp_path / "cred")


# -- Priority 1: environment variables ---------------------------------------


def test_env_vars_take_priority_over_files(tmp_path, monkeypatch):
    monkeypatch.setenv("AIDOT_USERNAME", "env-user")
    monkeypatch.setenv("AIDOT_PASSWORD", "env-pass")
    monkeypatch.setenv("AIDOT_COUNTRY", "EU")
    # A saved file exists, but the env must win and the file must not be read.
    p = _prefix(tmp_path)
    credentials.save_credentials("file-user", "file-pass", "US", creds_path=p)
    data = credentials.load_credentials(creds_path=p)
    assert data == {"username": "env-user", "password": "env-pass", "country": "EU"}


def test_country_defaults_when_only_user_and_pass_are_set(monkeypatch):
    # AIDOT_COUNTRY is unset here; settings supplies its registered default.
    monkeypatch.setenv("AIDOT_USERNAME", "u")
    monkeypatch.setenv("AIDOT_PASSWORD", "p")
    assert credentials.load_credentials()["country"] == "US"


def test_a_username_without_a_password_falls_through_to_files(tmp_path, monkeypatch):
    # Priority 1 needs BOTH; a half-set env must not shadow a saved file.
    monkeypatch.setenv("AIDOT_USERNAME", "env-only")
    p = _prefix(tmp_path)
    credentials.save_credentials("file-user", "file-pass", "JP", creds_path=p)
    assert credentials.load_credentials(creds_path=p)["username"] == "file-user"


# -- Priority 2: Fernet-encrypted pair ---------------------------------------


def test_save_then_load_round_trips(tmp_path):
    p = _prefix(tmp_path)
    enc_path = credentials.save_credentials("alice", "s3cret", "US", creds_path=p)
    assert enc_path == p + ".enc"
    assert os.path.exists(p + ".enc") and os.path.exists(p + ".key")
    assert credentials.load_credentials(creds_path=p) == {
        "username": "alice",
        "password": "s3cret",
        "country": "US",
    }


def test_saved_secret_and_dir_have_tight_permissions(tmp_path):
    p = _prefix(tmp_path)
    credentials.save_credentials("alice", "pw", creds_path=p)
    for suffix in (".enc", ".key"):
        mode = stat.S_IMODE(os.stat(p + suffix).st_mode)
        assert mode == 0o600, f"{suffix} is {oct(mode)}"
    dir_mode = stat.S_IMODE(os.stat(tmp_path).st_mode)
    assert dir_mode & 0o077 == 0, f"config dir is {oct(dir_mode)}"


def test_second_save_reuses_the_existing_key(tmp_path):
    p = _prefix(tmp_path)
    credentials.save_credentials("alice", "pw1", creds_path=p)
    key1 = open(p + ".key", "rb").read()
    credentials.save_credentials("alice", "pw2", creds_path=p)
    assert open(p + ".key", "rb").read() == key1
    assert credentials.load_credentials(creds_path=p)["password"] == "pw2"


def test_a_colocated_key_warns(tmp_path, caplog):
    import logging

    with caplog.at_level(logging.WARNING, logger="aidot_cameras.credentials"):
        credentials.save_credentials("a", "b", creds_path=_prefix(tmp_path))
    assert any("adds little over file permissions" in r.message for r in caplog.records)


def test_a_relocated_key_does_not_warn(tmp_path, caplog):
    import logging

    enc = str(tmp_path / "enc" / "credentials.enc")
    key = str(tmp_path / "key" / "secret.key")
    with caplog.at_level(logging.WARNING, logger="aidot_cameras.credentials"):
        credentials._save_encrypted("a", "b", "US", enc, key)
    assert not any("adds little" in r.message for r in caplog.records)


def test_encrypted_payload_missing_a_field_is_rejected(tmp_path):
    # A well-formed token whose JSON lacks "password" must not load.
    from cryptography.fernet import Fernet

    p = _prefix(tmp_path)
    key = Fernet.generate_key()
    credentials._write_secret(p + ".key", key)
    token = Fernet(key).encrypt(json.dumps({"username": "only"}).encode())
    credentials._write_secret(p + ".enc", token)
    with pytest.raises(ValueError, match="missing field 'password'"):
        credentials.load_credentials(creds_path=p)


def test_an_undecryptable_token_raises_a_clear_error(tmp_path):
    p = _prefix(tmp_path)
    credentials.save_credentials("a", "b", creds_path=p)
    # Replace the key so the ciphertext can no longer be decrypted.
    from cryptography.fernet import Fernet

    credentials._write_secret(p + ".key", Fernet.generate_key())
    with pytest.raises(ValueError, match="Failed to decrypt"):
        credentials.load_credentials(creds_path=p)


# -- Priority 3: legacy plain JSON -------------------------------------------


def test_plain_json_loads_and_is_migrated_to_encrypted(tmp_path):
    p = _prefix(tmp_path)
    with open(p, "w") as f:
        json.dump({"username": "legacy", "password": "pw", "country": "US"}, f)
    data = credentials.load_credentials(creds_path=p)
    assert data["username"] == "legacy"
    # Auto-migrated: the plain file is gone and the encrypted pair now exists.
    assert not os.path.exists(p)
    assert os.path.exists(p + ".enc") and os.path.exists(p + ".key")
    assert credentials.load_credentials(creds_path=p)["username"] == "legacy"


def test_plain_json_missing_a_field_is_rejected(tmp_path):
    p = _prefix(tmp_path)
    with open(p, "w") as f:
        json.dump({"username": "no-pass"}, f)
    with pytest.raises(ValueError, match="missing 'password'"):
        credentials.load_credentials(creds_path=p)


def test_nothing_anywhere_raises_file_not_found(tmp_path):
    with pytest.raises(FileNotFoundError, match="No credentials found"):
        credentials.load_credentials(creds_path=_prefix(tmp_path))


# -- delete ------------------------------------------------------------------


def test_delete_removes_every_form(tmp_path):
    p = _prefix(tmp_path)
    credentials.save_credentials("a", "b", creds_path=p)
    with open(p, "w") as f:  # a leftover legacy plain file too
        f.write("{}")
    credentials.delete_credentials(creds_path=p)
    for path in (p, p + ".enc", p + ".key"):
        assert not os.path.exists(path)


def test_delete_is_a_no_op_when_nothing_is_stored(tmp_path):
    credentials.delete_credentials(creds_path=_prefix(tmp_path))  # must not raise


# -- _write_secret hardening -------------------------------------------------


def test_write_secret_tightens_a_preexisting_loose_file(tmp_path):
    target = tmp_path / "loose"
    target.write_bytes(b"old")
    os.chmod(target, 0o644)
    credentials._write_secret(str(target), b"new")
    assert stat.S_IMODE(os.stat(target).st_mode) == 0o600
    assert target.read_bytes() == b"new\n"


# -- cryptography missing ----------------------------------------------------


def test_a_clear_error_when_cryptography_is_absent(tmp_path, monkeypatch):
    # Simulate the optional dependency being uninstalled: a None entry in
    # sys.modules makes `from cryptography.fernet import ...` raise ImportError.
    monkeypatch.setitem(sys.modules, "cryptography.fernet", None)
    with pytest.raises(ImportError, match="cryptography"):
        credentials.save_credentials("a", "b", creds_path=_prefix(tmp_path))
