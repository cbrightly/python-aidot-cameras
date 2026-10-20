"""Test helpers for building records and stubs that suit the installed upstream.

Upstream has changed its account/device API inside patch releases (see
``aidot_cameras/_upstream.py`` and docs/UPSTREAM.md).  Only the dict shape
(0.3.56+) is supported now, but the account back-reference that 0.3.57 added is
still a live difference, so tests build records and stubs through the helpers
here rather than hardcoding today's spelling - a future rename then lands in
one place.
"""

from aidot_cameras import _upstream

#: The typed cloud device record class, whatever upstream calls it.
DeviceModel = _upstream.DeviceModel


def device_record(data: dict):
    """The typed device record for ``data``.

    It exists so tests have a single name to import.
    """
    return DeviceModel.from_json(data=data)


def account_record(data: dict):
    """The account record upstream's ``DeviceClient`` expects as ``user_info``.

    Upstream takes the plain account dict.
    """
    return dict(data)


def set_access_token(client, token: str) -> None:
    """Write an access token onto an account client.

    ``login_info`` is the single source of truth for the account's tokens.
    """
    from aidot_cameras.const import CONF_ACCESS_TOKEN

    client.login_info[CONF_ACCESS_TOKEN] = token


def set_refresh_token(client, token: str) -> None:
    """Write a refresh token onto an account client."""
    from aidot_cameras.const import CONF_REFRESH_TOKEN

    client.login_info[CONF_REFRESH_TOKEN] = token


def make_upstream_device_client(raw_device: dict, raw_account: dict, client=None):
    """Build a PLAIN upstream ``DeviceClient`` from raw cloud dicts.

    Tests that exercise the non-camera path construct upstream's own class, so
    they need whatever *it* takes: the raw dicts, plus the account
    back-reference on >=0.3.57.  ``_upstream.device_client_args`` already
    encodes that decision, so reuse it rather than branching again here - and
    **splat** the result, because its length varies by upstream.

    ``client`` defaults to ``None`` because these fixtures never reach
    ``async_set_effect``, the one upstream method that uses it.  Production
    code has no such default: the seam requires it.
    """
    from aidot.device_client import DeviceClient

    return DeviceClient(
        *_upstream.device_client_args(
            device_record(raw_device),
            raw_device,
            account_record(raw_account),
            raw_account,
            client=client,
        )
    )


def arm_reconnect(device_client, callback, delay: float = 0.05) -> None:
    """Arm a pending reconnect on ``device_client`` the way upstream does.

    ``_reconnect_handle`` holding an ``asyncio.TimerHandle`` from
    ``loop.call_later``.  The name comes from ``_upstream`` rather than being
    hardcoded here: the point of the test using this is that the attribute name
    has changed before, so a stale one would silently arm nothing and the
    assertion would pass vacuously.
    """
    import asyncio

    (attr,) = _upstream.RECONNECT_HANDLE_ATTRS
    loop = asyncio.get_running_loop()
    setattr(device_client, attr, loop.call_later(delay, callback))


def stub_account_http(client, fake_api) -> None:
    """Point an account client's cloud listing calls at ``fake_api``.

    ``fake_api`` must expose ``get_houses`` / ``get_devices`` /
    ``get_products``; each is bound across to the client's own
    ``async_get_*`` method.

    Tests that build a client with ``object.__new__`` need this - the real
    methods reach for ``self._base_url`` on a half-built object.
    """
    client.async_get_houses = fake_api.get_houses
    client.async_get_devices = fake_api.get_devices
    client.async_get_products = fake_api.get_products


def patch_refresh_token_call(client, fake):
    """Replace the client's token-refresh HTTP call (``async_refresh_token``)."""
    client.async_refresh_token = fake
