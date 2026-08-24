"""One place that knows which upstream ``python-aidot`` is installed.

Upstream ships two incompatible shapes of the same private API, and both are
live: Home Assistant core pins ``0.3.56`` while this library was written against
``0.3.55``.  They are not a forward/backward pair - ``0.3.56`` is a **revert** of
the refactor that ``0.3.54`` introduced and ``0.3.55`` carried, so the "old"
shape is also the current one:

    0.3.53   dict-based client, ``aes_utils``, ``login_const``      <- shape A
    0.3.54   typed dataclasses, ``api/``, ``utils/crypto``          <- shape B
    0.3.55   shape B
    0.3.56   shape A again (plus the ``models/`` package)

Because upstream has flip-flopped once, neither shape can be assumed permanent,
so both are supported rather than tracked.  Every difference is resolved here,
once, at import time; no other module in this package may branch on the upstream
version.  If you find yourself adding a second ``try: import aidot...`` anywhere
else, put it here instead.

The detection is by capability (does the name import?), never by parsing a
version string: upstream shipped shape A under both ``0.3.53`` and ``0.3.56``
with a five-day excursion in between, so a version comparison would encode the
excursion rather than the shape.
"""

from enum import IntEnum
from typing import Any, Optional

# --------------------------------------------------------------------------- #
# Shape detection
# --------------------------------------------------------------------------- #

try:  # shape B (0.3.54 - 0.3.55): typed account/device dataclasses + CloudApi
    from aidot.models.auth_model import UserInformation as _UserInformation
    HAS_TYPED_ACCOUNT = True
except ImportError:  # shape A (<=0.3.53, >=0.3.56): plain dicts
    _UserInformation = None
    HAS_TYPED_ACCOUNT = False

#: True when upstream exposes the typed account layer (``user_info``,
#: ``_cloud_api``, ``UserInformation``).  Prefer the named helpers below to
#: branching on this directly.
UPSTREAM_SHAPE = "typed" if HAS_TYPED_ACCOUNT else "dict"

#: Upstream's account record class, or ``None`` on shape A where the account is
#: a plain dict.  Only used as a type annotation and an isinstance guard.
UserInformation = _UserInformation


# --------------------------------------------------------------------------- #
# Constants: four moved from ``aidot.const`` to ``aidot.login_const``
# --------------------------------------------------------------------------- #

try:  # shape B
    from aidot.const import (
        API_URL_TEMPLATE,
        APP_ID,
        DEFAULT_REGION,
        PUBLIC_KEY_PEM,
    )
except ImportError:  # shape A
    from aidot.login_const import (
        API_URL_TEMPLATE,
        APP_ID,
        DEFAULT_REGION,
        PUBLIC_KEY_PEM,
    )


# --------------------------------------------------------------------------- #
# Crypto: ``aidot.utils.crypto`` -> ``aidot.aes_utils``
# --------------------------------------------------------------------------- #

try:  # shape B
    from aidot.utils.crypto import (
        aes_decrypt,
        aes_decrypt_to_json,
        aes_encrypt,
    )
except ImportError:  # shape A
    from aidot.aes_utils import (
        aes_decrypt,
        aes_decrypt_to_json,
        aes_encrypt,
    )


def rsa_encrypt(message: str, public_key: Any = None) -> str:
    """RSA/PKCS1v15 encrypt ``message``, returned base64.

    Implemented here rather than re-exported.  Shape B had
    ``aidot.utils.crypto.rsa_encrypt(message, public_key)``; shape A replaced it
    with ``aidot.client.rsa_password_encrypt(message)``, which bakes the key in
    and takes one argument.  Neither signature is available on both shapes, and
    ``aidot_cameras.crypto.rsa_encrypt`` is public surface the integration repo
    may import, and public surface is not changed to track an upstream rename,
    so the two-argument form is kept and satisfied locally.  ``public_key`` defaults to upstream's
    ``PUBLIC_KEY_PEM``, which is the only key any caller ever passed.
    """
    from cryptography.hazmat.backends import default_backend
    from cryptography.hazmat.primitives import serialization
    from cryptography.hazmat.primitives.asymmetric import padding
    import base64

    if public_key is None:
        public_key = PUBLIC_KEY_PEM
    loaded = serialization.load_pem_public_key(
        public_key.encode() if isinstance(public_key, str) else public_key,
        backend=default_backend(),
    )
    encrypted = loaded.encrypt(message.encode("utf-8"), padding.PKCS1v15())
    return base64.b64encode(encrypted).decode("utf-8")


# --------------------------------------------------------------------------- #
# Device record: ``DeviceModel`` (shape B) / ``DeviceInformation`` (shape A)
# --------------------------------------------------------------------------- #

try:  # shape B
    from aidot.models.device_model import DeviceModel as DeviceRecord
except ImportError:  # shape A
    from aidot.models.device_model import DeviceInformation as DeviceRecord

#: The typed cloud device record.  Both shapes expose ``from_json`` /
#: ``to_dict`` and the core field set this package reads (``id``, ``modelId``,
#: ``aesKey``, ``password``, ``product``); shape A drops ~20 fields shape B
#: carried and defaults everything to ``None`` rather than ``""``/empty, so
#: never rely on a nested default being present - check for ``None`` first.
DeviceModel = DeviceRecord


# --------------------------------------------------------------------------- #
# DeviceState: upstream's LAN session enum, deleted in shape A
# --------------------------------------------------------------------------- #

try:  # shape B
    from aidot.device_client import DeviceState as _DeviceState
    DEVICE_STATE_IS_UPSTREAMS = True
except ImportError:  # shape A: no state machine, just two booleans
    DEVICE_STATE_IS_UPSTREAMS = False

    class _DeviceState(IntEnum):
        """Stand-in for upstream's deleted ``DeviceState``.

        Shape A tracks the LAN session with ``_connecting`` /
        ``_connect_and_login`` booleans instead of an enum.  The enum is still
        re-exported from ``aidot_cameras.device_client`` because the Home
        Assistant integration annotates and compares against it, and dropping a
        public name outright would break it at import.

        Members and values are copied from 0.3.55 so a stored or hard-coded
        comparison keeps its meaning.  On shape A nothing upstream ever
        *produces* one of these - use ``device_session_authenticated()`` rather
        than comparing a client's private state against it.
        """

        IDLE = 0
        INITIALIZING = 1
        CONNECTING = 2
        CONNECTED = 3
        CONNECTION_FAILED = 4
        AUTHENTICATING = 5
        AUTHENTICATED = 6
        AUTHENTICATION_FAILED = 7
        DISCONNECTED = 8

DeviceState = _DeviceState


def device_session_authenticated(device_client: Any) -> bool:
    """Whether a device client's LAN session is logged in, on either shape.

    Shape B exposes ``_state == DeviceState.AUTHENTICATED``; shape A replaced
    that with the ``connect_and_login`` property.  Consumers that only want the
    answer should call this instead of touching either private attribute.
    """
    if DEVICE_STATE_IS_UPSTREAMS:
        return getattr(device_client, "_state", None) == DeviceState.AUTHENTICATED
    return bool(getattr(device_client, "connect_and_login", False))


# --------------------------------------------------------------------------- #
# Reconnect timer handle: ``_reconnect_timer`` -> ``_reconnect_handle``
# --------------------------------------------------------------------------- #

#: Attribute names a pending reconnect may be parked under, newest first.
#: ``CameraClient.async_close`` cancels whichever exists so a closed account
#: cannot re-open a TCP connection ~45 s later (upstream cancels neither).
RECONNECT_HANDLE_ATTRS = ("_reconnect_handle", "_reconnect_timer")


def cancel_pending_reconnect(device_client: Any) -> bool:
    """Cancel a device client's pending reconnect, whichever shape it uses.

    Returns True if something was cancelled.  Deliberately not a silent
    ``getattr(x, "_reconnect_timer", None)``: that spelling exists only on shape
    B, so on shape A it would find nothing, cancel nothing, and report success -
    the leak fix would go quiet instead of failing.  Callers that care should
    log the False case.
    """
    for attr in RECONNECT_HANDLE_ATTRS:
        handle = getattr(device_client, attr, None)
        if handle is not None:
            handle.cancel()
            return True
    return False


# --------------------------------------------------------------------------- #
# Account HTTP surface: ``CloudApi`` (shape B) -> client methods (shape A)
# --------------------------------------------------------------------------- #

async def api_get_houses(client: Any) -> Optional[list]:
    """List the account's houses."""
    if HAS_TYPED_ACCOUNT:
        return await client._cloud_api.get_houses()
    return await client.async_get_houses()


async def api_get_devices(client: Any, house_id: str) -> Optional[list]:
    """List one house's devices."""
    if HAS_TYPED_ACCOUNT:
        return await client._cloud_api.get_devices(house_id)
    return await client.async_get_devices(house_id)


async def api_get_products(client: Any, product_ids: str) -> Optional[list]:
    """Fetch product metadata for a comma-joined id list."""
    if HAS_TYPED_ACCOUNT:
        return await client._cloud_api.get_products(product_ids)
    return await client.async_get_products(product_ids)


async def api_refresh_token(client: Any) -> Any:
    """Exchange the refresh token for a new access token.

    Both shapes return the response body and raise ``AidotAuthFailed`` when the
    refresh token itself is rejected; shape B additionally returns ``None`` on
    some non-auth failures, so callers must treat a falsy return as "no token".
    """
    if HAS_TYPED_ACCOUNT:
        return await client._cloud_api.refresh_token()
    return await client.async_refresh_token()


# --------------------------------------------------------------------------- #
# Account fields: ``user_info`` dataclass (shape B) -> plain attrs (shape A)
# --------------------------------------------------------------------------- #

def account_region(client: Any) -> str:
    """The account's API region ("us", "eu", ...)."""
    if HAS_TYPED_ACCOUNT:
        return getattr(client.user_info, "region", "") or ""
    return getattr(client, "_region", "") or ""


def account_refresh_token(client: Any) -> str:
    """The stored refresh token, or "" when there is none."""
    if HAS_TYPED_ACCOUNT:
        return getattr(client.user_info, "refreshToken", "") or ""
    from .const import CONF_REFRESH_TOKEN
    return (client.login_info or {}).get(CONF_REFRESH_TOKEN) or ""


def account_token_ttl(client: Any, default: int = 7200) -> int:
    """Access-token lifetime in seconds, defaulting to the server's 7200."""
    if HAS_TYPED_ACCOUNT:
        raw = getattr(client.user_info, "expiresIn", 0)
    else:
        raw = (client.login_info or {}).get("expiresIn", 0)
    try:
        return int(raw or default)
    except (TypeError, ValueError):
        return default


def account_record(client: Any) -> Any:
    """What to pass as a device client's second constructor argument.

    Shape B wants the ``UserInformation`` dataclass; shape A wants the raw
    ``login_info`` dict.  See ``device_client_args``.
    """
    if HAS_TYPED_ACCOUNT:
        return client.user_info
    return client.login_info


def device_client_args(
    device_record: Any,
    raw_device: dict,
    typed_account: Any,
    login_info: Optional[dict],
) -> tuple:
    """The ``(device, user_info)`` pair upstream's ``DeviceClient`` expects.

    Shape B: ``(DeviceModel, UserInformation)`` - typed dataclasses.
    Shape A: ``(dict, dict)`` - the raw cloud records, natively.

    Shape A taking raw dicts is why this package carried ``raw_device`` /
    ``login_info`` alongside the typed models in the first place: the typed
    round trip drops every camera-only field.  On shape A the originals go
    straight through and nothing is lost.

    Takes the four values rather than the account client, because the only
    caller (``CameraDeviceClient.__init__``) is handed them directly and has no
    client reference.  ``typed_account`` may be a ``UserInformation`` or, from
    an older caller, already a dict - on shape A a dict ``login_info`` wins and
    a dict ``typed_account`` is the fallback, so both call styles work.
    """
    if HAS_TYPED_ACCOUNT:
        return (device_record, typed_account)
    account = login_info if isinstance(login_info, dict) else None
    if account is None:
        account = typed_account if isinstance(typed_account, dict) else {}
        if not isinstance(typed_account, dict) and hasattr(typed_account, "to_dict"):
            account = typed_account.to_dict()
    return (raw_device, account)


#: True when upstream's ``DeviceClient`` exposes a ``read_data`` seam - one
#: decrypted frame at a time, which this package hooks to recover camera-only
#: attribute keys that upstream's typed ``DeviceAttr`` model drops.  Shape A
#: inlines the read into ``receive_data``, so there is no seam and the raw JSON
#: never escapes.  See ``CameraDeviceClient._notify_status_update``.
try:
    from aidot.device_client import DeviceClient as _DeviceClient
    HAS_READ_DATA_SEAM = callable(getattr(_DeviceClient, "read_data", None))
except ImportError:  # pragma: no cover - upstream is a hard dependency
    HAS_READ_DATA_SEAM = False


# --------------------------------------------------------------------------- #
# Discovery: static ``Discover`` (shape B) -> instance ``Discover`` (shape A)
# --------------------------------------------------------------------------- #

#: True when upstream's ``Discover`` keeps discovered addresses in the
#: process-wide ``DISCOVERED_DEVICE`` class dict.  Shape A made discovery
#: instance-scoped (``self._discover.discovered_device``), which is the shape
#: this package's ``CameraDiscover`` already presents - so on shape A the map
#: no longer has to be shared through the base class to reach upstream's
#: ``get_device_client``.
try:
    from aidot.discover import Discover as _Discover
    HAS_SHARED_DISCOVERY_MAP = isinstance(
        getattr(_Discover, "DISCOVERED_DEVICE", None), dict
    )
except ImportError:  # pragma: no cover - upstream is a hard dependency
    HAS_SHARED_DISCOVERY_MAP = False


def broadcast_protocol_args(callback: Any, user_id: str) -> tuple:
    """Positional args for upstream's ``BroadcastProtocol.__init__``.

    Shape B takes ``(callback)`` and has no notion of the account id; shape A
    takes ``(callback, user_id)`` and uses it to build the request payload.
    """
    import inspect
    from aidot.discover import BroadcastProtocol

    params = list(inspect.signature(BroadcastProtocol.__init__).parameters)
    return (callback, user_id) if "user_id" in params else (callback,)


#: Names this module intends as its public surface within the package.
__all__ = [
    "API_URL_TEMPLATE",
    "APP_ID",
    "DEFAULT_REGION",
    "DEVICE_STATE_IS_UPSTREAMS",
    "HAS_READ_DATA_SEAM",
    "HAS_SHARED_DISCOVERY_MAP",
    "HAS_TYPED_ACCOUNT",
    "PUBLIC_KEY_PEM",
    "RECONNECT_HANDLE_ATTRS",
    "UPSTREAM_SHAPE",
    "DeviceModel",
    "DeviceRecord",
    "DeviceState",
    "UserInformation",
    "account_record",
    "account_refresh_token",
    "account_region",
    "account_token_ttl",
    "aes_decrypt",
    "aes_decrypt_to_json",
    "aes_encrypt",
    "api_get_devices",
    "api_get_houses",
    "api_get_products",
    "api_refresh_token",
    "broadcast_protocol_args",
    "cancel_pending_reconnect",
    "device_client_args",
    "device_session_authenticated",
    "rsa_encrypt",
]
