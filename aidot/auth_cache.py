"""Encrypted auth cache helpers for CLI tooling."""

from __future__ import annotations

import base64
import json
import os
from pathlib import Path
from typing import Any

from cryptography.fernet import Fernet, InvalidToken

_DEFAULT_CACHE_PATH = ".aidot_auth_cache.enc"
_KEY_SUFFIX = ".key"


class AuthCacheError(Exception):
    """Raised when cache content cannot be loaded or validated."""


def default_cache_path() -> Path:
    """Return the default encrypted cache file path in current directory."""
    return Path.cwd() / _DEFAULT_CACHE_PATH


def _key_path(cache_path: Path) -> Path:
    return cache_path.with_suffix(cache_path.suffix + _KEY_SUFFIX)


def _ensure_permissions(path: Path) -> None:
    if os.name != "nt":
        os.chmod(path, 0o600)


def _create_new_fernet_key(key_file: Path) -> bytes:
    key_file.parent.mkdir(parents=True, exist_ok=True)
    key = Fernet.generate_key()
    key_file.write_bytes(key)
    _ensure_permissions(key_file)
    return key


def _load_or_create_key(cache_path: Path) -> bytes:
    key_file = _key_path(cache_path)
    if key_file.exists():
        key = key_file.read_bytes().strip()
        # sanity-check that the key is valid urlsafe-base64 Fernet key length
        try:
            decoded = base64.urlsafe_b64decode(key)
            if len(decoded) != 32:
                raise ValueError("Invalid key length")
        except Exception as exc:
            raise AuthCacheError(f"Invalid cache key file: {key_file}") from exc
        _ensure_permissions(key_file)
        return key
    return _create_new_fernet_key(key_file)


def load_or_init_cache(path: str | None = None) -> tuple[Path, dict[str, Any], bool]:
    """Load an encrypted cache or initialize an empty one.

    Returns: (cache_path, data, loaded_existing)
    """
    cache_path = Path(path).expanduser().resolve() if path else default_cache_path().resolve()
    key = _load_or_create_key(cache_path)
    fernet = Fernet(key)

    if not cache_path.exists():
        empty = {"version": 1, "login_info": {}}
        save_cache(cache_path, empty)
        return cache_path, empty, False

    payload = cache_path.read_bytes()
    if not payload:
        empty = {"version": 1, "login_info": {}}
        save_cache(cache_path, empty)
        return cache_path, empty, False

    try:
        decrypted = fernet.decrypt(payload)
    except InvalidToken as exc:
        raise AuthCacheError(f"Unable to decrypt auth cache file: {cache_path}") from exc

    try:
        data = json.loads(decrypted.decode("utf-8"))
    except json.JSONDecodeError as exc:
        raise AuthCacheError(f"Auth cache file is not valid JSON: {cache_path}") from exc

    if not isinstance(data, dict):
        raise AuthCacheError(f"Auth cache payload must be a JSON object: {cache_path}")
    if "login_info" not in data or not isinstance(data.get("login_info"), dict):
        raise AuthCacheError("Auth cache must contain a 'login_info' object")

    _ensure_permissions(cache_path)
    return cache_path, data, True


def save_cache(cache_path: Path, data: dict[str, Any]) -> None:
    """Encrypt and write cache payload with restricted permissions."""
    key = _load_or_create_key(cache_path)
    fernet = Fernet(key)
    cache_path.parent.mkdir(parents=True, exist_ok=True)
    payload = json.dumps(data, separators=(",", ":")).encode("utf-8")
    cache_path.write_bytes(fernet.encrypt(payload))
    _ensure_permissions(cache_path)
