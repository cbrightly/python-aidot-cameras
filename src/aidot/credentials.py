"""Credential storage for AiDot CLI tools.

Priority order:
  1. Environment variables: AIDOT_USERNAME, AIDOT_PASSWORD, AIDOT_COUNTRY
     Works in Docker containers, Home Assistant addons, CI systems.
  2. Fernet-encrypted file pair at ~/.config/aidot/:
       credentials.enc  - ciphertext (0600)
       .key             - random symmetric key (0600)
     Cross-platform, no OS-specific APIs required.
  3. Plain JSON file at ~/.config/aidot/credentials.json (0600)
     Legacy fallback; auto-migrated to encrypted on first load.
"""

import json
import logging
import os
import stat

_LOGGER = logging.getLogger(__name__)

def _xdg_config_home() -> str:
    """Return the XDG base config dir, honoring XDG_CONFIG_HOME (per the spec)."""
    xdg = os.environ.get("XDG_CONFIG_HOME")
    if xdg:
        return xdg
    return os.path.expanduser("~/.config")


_CONFIG_DIR = os.path.join(_xdg_config_home(), "aidot")
_DEFAULT_CREDS_FILE = os.path.join(_CONFIG_DIR, "credentials.json")
_ENC_FILE = os.path.join(_CONFIG_DIR, "credentials.enc")
_KEY_FILE = os.environ.get("AIDOT_CRED_KEY_FILE") or os.path.join(_CONFIG_DIR, ".key")


# -- Public API ---------------------------------------------------------------

def load_credentials(creds_path: str | None = None) -> dict:
    """Return {"username": ..., "password": ..., "country": ...}.

    Checks environment variables first, then encrypted file, then plain JSON.
    Raises FileNotFoundError / ValueError if nothing is found.
    """
    # Priority 1: environment variables
    env_user = os.environ.get("AIDOT_USERNAME")
    env_pass = os.environ.get("AIDOT_PASSWORD")
    if env_user and env_pass:
        return {
            "username": env_user,
            "password": env_pass,
            "country":  os.environ.get("AIDOT_COUNTRY", "US"),
        }

    # Priority 2: Fernet-encrypted file
    enc_path = (creds_path + ".enc") if creds_path else _ENC_FILE
    key_path = (creds_path + ".key") if creds_path else _KEY_FILE
    if os.path.exists(enc_path) and os.path.exists(key_path):
        try:
            return _load_encrypted(enc_path, key_path)
        except Exception as exc:
            raise ValueError(f"Failed to decrypt {enc_path}: {exc}") from exc

    # Priority 3: plain JSON (legacy)
    plain = creds_path or _DEFAULT_CREDS_FILE
    if os.path.exists(plain):
        data = _load_plain(plain)
        # Auto-migrate to encrypted storage
        try:
            _save_encrypted(data["username"], data["password"],
                            data.get("country", "US"), enc_path, key_path)
            os.unlink(plain)
        except Exception:
            pass
        return data

    raise FileNotFoundError(
        "No credentials found. Options:\n"
        "  - Set AIDOT_USERNAME / AIDOT_PASSWORD / AIDOT_COUNTRY env vars\n"
        "  - Run with --save-credentials to store them encrypted\n"
        f"  - Place credentials.json in {_CONFIG_DIR}"
    )


def save_credentials(
    username: str,
    password: str,
    country: str = "US",
    creds_path: str | None = None,
) -> str:
    """Encrypt and store credentials. Returns the path where they were saved."""
    enc_path = (creds_path + ".enc") if creds_path else _ENC_FILE
    key_path = (creds_path + ".key") if creds_path else _KEY_FILE
    _save_encrypted(username, password, country, enc_path, key_path)
    return enc_path


def delete_credentials(creds_path: str | None = None) -> None:
    """Remove stored credentials (encrypted and/or plain JSON)."""
    enc_path = (creds_path + ".enc") if creds_path else _ENC_FILE
    key_path = (creds_path + ".key") if creds_path else _KEY_FILE
    plain    = creds_path or _DEFAULT_CREDS_FILE
    for path in (enc_path, key_path, plain):
        if os.path.exists(path):
            try:
                os.unlink(path)
            except Exception:
                pass


# -- Internal helpers ---------------------------------------------------------

def _fernet():
    try:
        from cryptography.fernet import Fernet
        return Fernet
    except ImportError as exc:
        raise ImportError(
            "The 'cryptography' package is required for encrypted credentials. "
            "Install it with: pip install cryptography"
        ) from exc


def _load_encrypted(enc_path: str, key_path: str) -> dict:
    Fernet = _fernet()
    with open(key_path, "rb") as f:
        key = f.read().strip()
    with open(enc_path, "rb") as f:
        token = f.read().strip()
    plaintext = Fernet(key).decrypt(token)
    data = json.loads(plaintext)
    for field in ("username", "password"):
        if field not in data:
            raise ValueError(f"Encrypted credentials missing field {field!r}")
    return data


def _save_encrypted(
    username: str, password: str, country: str,
    enc_path: str, key_path: str,
) -> None:
    Fernet = _fernet()
    # Generate or reuse key - _write_secret handles makedirs
    if os.path.exists(key_path):
        with open(key_path, "rb") as f:
            key = f.read().strip()
    else:
        key = Fernet.generate_key()
        _write_secret(key_path, key)

    payload = json.dumps({"username": username, "password": password,
                          "country": country}).encode()
    _write_secret(enc_path, Fernet(key).encrypt(payload))

    # Co-locating the key with the ciphertext means the encryption adds little
    # over file permissions: anyone who can read one can read the other.  Warn
    # and point at AIDOT_CRED_KEY_FILE to relocate the key off this directory.
    if os.path.dirname(os.path.abspath(key_path)) == os.path.dirname(os.path.abspath(enc_path)):
        _LOGGER.warning(
            "credentials: key %s sits in the same directory as ciphertext %s, "
            "so encryption adds little over file permissions; set "
            "AIDOT_CRED_KEY_FILE to a path outside this directory (ideally a "
            "separate secret store) to harden at-rest protection.",
            key_path, enc_path,
        )


def _load_plain(path: str) -> dict:
    with open(path) as f:
        data = json.load(f)
    for field in ("username", "password"):
        if field not in data:
            raise ValueError(f"Credentials file {path!r} missing {field!r}")
    return data


def _write_secret(path: str, data: bytes) -> None:
    """Write binary data to path, created atomically with 0600 permissions."""
    d = os.path.dirname(os.path.abspath(path))
    os.makedirs(d, exist_ok=True)
    try:
        os.chmod(d, 0o700)  # restrict the config dir too (best-effort)
    except OSError:
        pass
    # Create with 0600 from the start so the secret is never briefly
    # world/group-readable under the process umask (a chmod-after-open leaves a
    # window where another local user could read the key/ciphertext).
    fd = os.open(path, os.O_WRONLY | os.O_CREAT | os.O_TRUNC, 0o600)
    with os.fdopen(fd, "wb") as f:
        f.write(data)
        f.write(b"\n")
    # If the file pre-existed with looser perms, O_CREAT's mode was ignored;
    # tighten it explicitly.
    os.chmod(path, stat.S_IRUSR | stat.S_IWUSR)
