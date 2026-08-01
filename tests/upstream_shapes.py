"""Test helpers for building records that suit whichever upstream is installed.

Upstream ships two incompatible shapes of its account/device records and both
are live (see ``aidot_cameras/_upstream.py`` and docs/UPSTREAM.md).  Tests that
construct a ``CameraDeviceClient`` need the pair upstream actually expects:

* typed shape (0.3.54 - 0.3.55): ``DeviceModel`` + ``UserInformation``
* dict shape (<=0.3.53, >=0.3.56): the raw cloud dicts, natively

Importing ``aidot.models.auth_model`` directly - as several test modules used to
- is an ImportError on the dict shape at *collection* time, which takes the
whole module down rather than failing one assertion.  Build records through the
helpers here instead.
"""

from aidot_cameras import _upstream

#: True when upstream exposes the typed account/device dataclasses.
TYPED = _upstream.HAS_TYPED_ACCOUNT

#: The typed cloud device record class, whatever upstream calls it this week.
DeviceModel = _upstream.DeviceModel


def device_record(data: dict):
    """The typed device record for ``data``.

    Both shapes have one (``DeviceModel`` / ``DeviceInformation``) and both
    expose ``from_json`` / ``to_dict``, so this needs no branch - it exists so
    tests have a single name to import.
    """
    return DeviceModel.from_json(data=data)


def account_record(data: dict):
    """The account record upstream's ``DeviceClient`` expects as ``user_info``.

    A ``UserInformation`` on the typed shape; the plain dict on the dict shape,
    where upstream deleted the dataclass entirely.
    """
    if _upstream.UserInformation is not None:
        return _upstream.UserInformation.from_json(data=data)
    return dict(data)


def set_access_token(client, token: str) -> None:
    """Write an access token onto an account client, on either shape.

    The typed shape stores it on the ``user_info`` dataclass and only mirrors it
    into ``login_info`` when the property getter runs; the dict shape keeps
    ``login_info`` as the single source of truth.
    """
    from aidot_cameras.const import CONF_ACCESS_TOKEN

    if TYPED:
        client.user_info.accessToken = token
    else:
        client.login_info[CONF_ACCESS_TOKEN] = token


def set_refresh_token(client, token: str) -> None:
    """Write a refresh token onto an account client, on either shape."""
    from aidot_cameras.const import CONF_REFRESH_TOKEN

    if TYPED:
        client.user_info.refreshToken = token
    else:
        client.login_info[CONF_REFRESH_TOKEN] = token


def make_upstream_device_client(raw_device: dict, raw_account: dict):
    """Build a PLAIN upstream ``DeviceClient`` from raw cloud dicts.

    Tests that exercise the non-camera path construct upstream's own class, so
    they need whatever *it* takes: typed models on the typed shape, the raw
    dicts on the dict shape.  ``_upstream.device_client_args`` already encodes
    that decision, so reuse it rather than branching again here.
    """
    from aidot.device_client import DeviceClient

    device, account = _upstream.device_client_args(
        device_record(raw_device),
        raw_device,
        account_record(raw_account),
        raw_account,
    )
    return DeviceClient(device, account)


def arm_reconnect(device_client, callback, delay: float = 0.05) -> None:
    """Arm a pending reconnect on ``device_client`` the way its shape does.

    Typed shape: ``_reconnect_timer`` holding an ``aidot.utils.AsyncTimer``.
    Dict shape:  ``_reconnect_handle`` holding an ``asyncio.TimerHandle`` from
    ``loop.call_later`` (upstream deleted ``AsyncTimer`` along with
    ``aidot/utils/``).

    Both objects expose ``.cancel()``, which is all
    ``_upstream.cancel_pending_reconnect`` needs - the point of the test using
    this is that the *attribute name* differs, so a hardcoded one would silently
    arm nothing on the other shape and the assertion would pass vacuously.
    """
    import asyncio

    if TYPED:
        from aidot.utils import AsyncTimer

        timer = AsyncTimer(callback=callback, interval=delay)
        timer.start()
        device_client._reconnect_timer = timer
    else:
        loop = asyncio.get_running_loop()
        device_client._reconnect_handle = loop.call_later(delay, callback)


def stub_account_http(client, fake_api) -> None:
    """Point an account client's four cloud calls at ``fake_api``.

    ``fake_api`` must expose ``get_houses`` / ``get_devices`` / ``get_products``
    (the typed shape's ``CloudApi`` method names).  On the typed shape it is
    installed as ``client._cloud_api`` verbatim; on the dict shape, where those
    calls became ``client.async_get_*`` methods on the client itself, each is
    bound across.

    Tests that build a client with ``object.__new__`` need this - stubbing
    ``_cloud_api`` alone leaves the dict shape reaching for ``self._base_url``
    on a half-built object.
    """
    client._cloud_api = fake_api
    if not TYPED:
        client.async_get_houses = fake_api.get_houses
        client.async_get_devices = fake_api.get_devices
        client.async_get_products = fake_api.get_products


def patch_refresh_token_call(client, fake):
    """Replace the client's token-refresh HTTP call with ``fake``.

    Typed shape: ``client._cloud_api.refresh_token``.
    Dict shape:  ``client.async_refresh_token``.
    """
    if TYPED:
        client._cloud_api.refresh_token = fake
    else:
        client.async_refresh_token = fake
