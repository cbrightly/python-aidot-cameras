"""Shared cloud authentication for the standalone entry points.

Extracted from ``__main__`` so the go2rtc CLI and ``scripts/live_validate.py``
authenticate the same way instead of each calling ``async_post_login``.

That is not tidying. The AiDot cloud issues one live token per account: a
second login invalidates the first. A validator that logs in, then spawns a
child that logs in again, loses its own session -- observed 2026-08-23, a
between-session campaign died with ``AidotAuthFailed`` on
``/v17/houses`` the moment the first child had run. Sharing one token file
(with the write-back cache below, so a rotation reaches every holder) is what
makes a parent and its children coexist.
"""

from __future__ import annotations

import asyncio
import json
import logging
import os
import tempfile

import aiohttp

from .client import AidotClient

_LOGGER = logging.getLogger("aidot.cloud_auth")


def _default_country() -> str:
    """The AIDOT_COUNTRY fallback, read at CALL time.

    This used to be a module-level constant bound at import, which made a
    caller's later os.environ tweak silently ineffective: a creds file with a
    non-US country logged in as US on the password path. Reading it when the
    client is built keeps environment and parameters equivalent.
    """
    return os.environ.get("AIDOT_COUNTRY", "US")


class CloudAuthUnavailable(RuntimeError):
    """No usable credentials: no readable token file and no username/password.

    Raised instead of ``sys.exit`` so library-side callers (the release
    validator among them) keep their own error reporting; the go2rtc CLI
    translates it to an exit in ``__main__``.
    """


def _read_token_file(path: str) -> dict:
    """Blocking read of a stored login_info dict (run via executor from async)."""
    with open(path, encoding="utf-8") as fh:
        return json.load(fh)


def _write_token_file(path: str, data: dict) -> None:
    """Atomic, 0600-secure write of login_info to ``path``.

    Serialize into a temp file in the same directory (mkstemp creates it 0600,
    closing the world-readable window os.chmod-after-write leaves) and only then
    os.replace it over ``path`` - so a serialization failure never truncates the
    previously-valid token. On any error the temp file is removed and re-raised.
    """
    directory = os.path.dirname(path) or "."
    fd, tmp = tempfile.mkstemp(dir=directory, prefix=".token-", suffix=".tmp")
    try:
        with os.fdopen(fd, "w", encoding="utf-8") as fh:
            json.dump(data, fh)
        os.replace(tmp, path)
    except BaseException:
        try:
            os.unlink(tmp)
        except OSError:
            pass
        raise


def _install_token_cache(client: AidotClient, path: str) -> None:
    """Persist rotated tokens to ``path`` so refreshes survive across restarts.

    The library calls this no-arg callback after every successful token refresh
    (client.py), having already updated ``client.login_info``. Mirrors HA's
    coordinator.token_fresh_cb, but writes to our own file instead of an HA
    config entry - so a standalone run never loses auth to a rotation and never
    fights HA over a shared refresh token.
    """
    def _cb() -> None:
        try:
            _write_token_file(path, client.serializable_login_info())
            _LOGGER.debug("Cached refreshed token to %s", path)
        except (OSError, TypeError) as exc:
            # TypeError: a defensive belt-and-suspenders catch alongside
            # serializable_login_info() itself - if a future runtime-only key
            # is ever added to login_info without also being added to
            # RUNTIME_ONLY_LOGIN_INFO_KEYS, this keeps a caching bug from
            # ever propagating out of a callback and interrupting a token
            # refresh that had otherwise already succeeded (confirmed live:
            # this exact TypeError - "Object of type Lock is not JSON
            # serializable" - previously escaped this callback uncaught).
            _LOGGER.warning("Could not cache refreshed token to %s: %s", path, exc)

    client.set_token_fresh_cb(_cb)


async def _make_client(
    session: aiohttp.ClientSession,
    *,
    username: "str | None" = None,
    password: "str | None" = None,
    country: "str | None" = None,
) -> AidotClient:
    """Authenticate to the AiDot cloud, from parameters or the environment.

    Prefers a stored token (AIDOT_TOKEN_FILE) - the same login_info dict the HA
    integration persists - which carries access/refresh tokens so no password
    round-trip is needed.  Falls back to username/password login otherwise.  In
    both cases, if AIDOT_TOKEN_FILE is set we register a write-back cache so
    rotations persist.

    Credentials a caller already holds are passed explicitly; each parameter
    left as None falls back to its environment variable.  The parameters exist
    so a caller never has to publish secrets into ``os.environ`` (where every
    spawned child inherits them) just to reach this function - the shim that
    did exactly that also carried a dead country assignment, because the old
    module-level default was bound at import time.

    Raises :class:`CloudAuthUnavailable` when neither a readable token file
    nor a username/password pair is available.
    """
    loop = asyncio.get_running_loop()
    token_file = os.environ.get("AIDOT_TOKEN_FILE")
    if username is None:
        username = os.environ.get("AIDOT_USERNAME")
    if password is None:
        password = os.environ.get("AIDOT_PASSWORD")
    if country is None:
        country = _default_country()

    if token_file and os.path.exists(token_file):
        try:
            token = await loop.run_in_executor(None, _read_token_file, token_file)
        except (OSError, ValueError) as exc:
            # ValueError covers json.JSONDecodeError (empty/partial/corrupt
            # cache). Fall through to the username/password path rather than
            # crash CLI startup.
            _LOGGER.warning("ignoring unreadable token cache %s: %s", token_file, exc)
        else:
            client = AidotClient(session=session, token=token)
            _install_token_cache(client, token_file)
            return client

    if not username or not password:
        raise CloudAuthUnavailable(
            "Set AIDOT_TOKEN_FILE, or AIDOT_USERNAME and AIDOT_PASSWORD, in the "
            "environment."
        )

    client = AidotClient(
        session=session,
        country_code=country,
        username=username,
        password=password,
    )
    await client.async_post_login()
    # If a token file path was given (but didn't exist yet), seed it now and
    # register the write-back cache so a dedicated-login run persists its
    # rotations across restarts.
    if token_file:
        _install_token_cache(client, token_file)
        try:
            await loop.run_in_executor(
                None, _write_token_file, token_file, client.serializable_login_info()
            )
        except (OSError, TypeError) as exc:
            _LOGGER.warning("Could not seed token cache %s: %s", token_file, exc)
    return client
