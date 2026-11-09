"""MQTT credentials the way the AiDot app fetches them: ``/v25/commons/mqttConfig2``.

Opt-in (``AIDOT_MQTT_CONFIG2``, off by default) and experimental. Read from the
Android app (``com.leedarson.mqtt.repos.FetchMqttConfigRepos``):

* ``GET {httpServer}/v25/commons/mqttConfig2`` with the query parameters
  ``sessionType=App`` and ``sessionId=<id>``. The app builds them as a JSON
  object and hands it to ``HttpManager.requestHttpGet``, whose retrofit
  interface (``UserApiStores.getHttp``) takes it as a ``@QueryMap``; there is
  no body. The H5 bundle's ``getMqttConfig`` sends the same two ``params``.
* The sessionId is 21 characters drawn from ``[a-zA-Z0-9]``
  (``createRandomStr(21)``), generated once and kept in the app's shared
  preferences, so it is stable per install. The H5 bundle draws its own from
  ``[0-9a-z]``, a subset, which a stored id is checked against just the same.
* The reply carries ``host``, ``clientId``, ``password`` and ``heartbeat``
  (not used here).
  The app connects to ``wss://<host>/mqtt`` with the reply's clientId and
  password, and the username is the logged-in user id from its own prefs: it
  overwrites the reply's ``userId`` with that before using it.

The point of trying it: the clientId comes from the server per sessionId, so
each install may get its own instead of all of them sharing one and evicting
each other from the broker. That is unverified.
"""

from __future__ import annotations

import logging
import secrets
import string
from typing import Any, Optional

from ._private_file import write_private_file

_LOGGER = logging.getLogger(__name__)

MQTT_CONFIG2_PATH = "/v25/commons/mqttConfig2"
SESSION_TYPE = "App"
SESSION_ID_ALPHABET = string.ascii_lowercase + string.ascii_uppercase + string.digits
SESSION_ID_LENGTH = 21
#: Appended to the token file's path for the stored sessionId.
SESSION_ID_FILE_SUFFIX = ".mqtt-session-id"


def new_session_id() -> str:
    """A fresh sessionId in the app's shape: 21 characters of ``[a-zA-Z0-9]``."""
    return "".join(
        secrets.choice(SESSION_ID_ALPHABET) for _ in range(SESSION_ID_LENGTH)
    )


def is_valid_session_id(value: Any) -> bool:
    return (
        isinstance(value, str)
        and len(value) == SESSION_ID_LENGTH
        and all(ch in SESSION_ID_ALPHABET for ch in value)
    )


def load_or_create_session_id(path: str) -> str:
    """Blocking: the sessionId stored at ``path``, creating it on first use.

    A missing, unreadable or malformed file gets a new id. A failed write is
    logged and the new id is still returned, so the caller keeps it for its
    own lifetime. Written 0600 via a temp file and a rename, as the token is.
    """
    try:
        with open(path, encoding="ascii") as fh:
            stored = fh.read().strip()
        if is_valid_session_id(stored):
            return stored
        _LOGGER.debug("ignoring a malformed MQTT sessionId file %s", path)
    except FileNotFoundError:
        pass
    except (OSError, UnicodeDecodeError) as exc:
        _LOGGER.debug("could not read the MQTT sessionId file %s: %s", path, exc)

    sid = new_session_id()
    try:
        write_private_file(
            path, lambda fh: fh.write(sid), prefix=".mqtt-session-", encoding="ascii"
        )
    except OSError as exc:
        _LOGGER.warning(
            "could not store the MQTT sessionId at %s (%s); using it for this run only",
            path,
            exc,
        )
    return sid


def parse_reply(body: Any) -> Optional[dict]:
    """``{"host", "clientId", "password"}`` from a reply, or None.

    Accepts the fields at the top level or under ``data`` (the app's HTTP layer
    hands its callback the unwrapped payload; the raw reply is assumed to wrap
    it the way the other endpoints do). A reply missing the host, the clientId
    or the password is a failure. The app itself rejects only an empty host,
    but the point here is the clientId, and pairing this password with a
    clientId from another source is exactly the mix the broker may refuse.
    """
    if not isinstance(body, dict):
        return None
    data = body.get("data")
    if not isinstance(data, dict):
        data = body

    def _str(key: str) -> str:
        val = data.get(key)
        return val.strip() if isinstance(val, str) else ""

    host, client_id, password = _str("host"), _str("clientId"), _str("password")
    if not host or not client_id or not password:
        return None
    return {"host": host, "clientId": client_id, "password": password}


def broker_url(host: str) -> str:
    """The broker URL for ``host``: ``wss://<host>/mqtt``, as the app builds it.

    A URL that already carries a scheme is used as given, and a bare host that
    already carries a path only gains the scheme.
    """
    if "://" in host:
        return host
    host = host.rstrip("/")
    return f"wss://{host}" if "/" in host else f"wss://{host}/mqtt"
