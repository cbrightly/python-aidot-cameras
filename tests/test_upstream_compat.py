"""Seam contract against the installed upstream ``python-aidot``.

This package extends upstream instead of forking it, so every upstream name it
subclasses, overrides or calls is part of a private API that upstream never
promised to keep.  A version bump that quietly moves or renames one of them
would otherwise surface as a puzzling failure deep inside a camera stream (or,
worse, as a silently skipped override).

**Upstream ships two live shapes and this file asserts against both.**  0.3.56
is a *revert* of the refactor 0.3.54 introduced and 0.3.55 carried, so "old" and
"current" are the same shape and neither can be assumed permanent:

    <=0.3.53, >=0.3.56   dict-based client, ``aes_utils``, ``login_const``
    0.3.54 - 0.3.55      typed dataclasses, ``api/``, ``utils/crypto``

Separately, 0.3.57+ passes the owning ``AidotClient`` to ``DeviceClient`` as a
third argument - an independent axis, flagged by
``_upstream.HAS_DEVICE_CLIENT_BACKREF`` - and moved the cloud API from v17 to
v35.  A run can only see the arity that is installed; the other one is covered
by ``test_upstream_seam_adapter.py``, which stubs both.

Which one is installed is detected by ``aidot_cameras._upstream``, and that
module is the only place in the package allowed to know.  Tests below that
apply to one shape only are marked with the matching skipif; tests with no mark
must hold on both.  Read the file as the checklist to work through when the pin
in pyproject.toml moves.

Keep it importing ``aidot.*`` directly - it is the one test module that
deliberately does NOT target our package, except for ``_upstream`` itself, whose
whole job is to be checked against reality here.
"""

import inspect

import pytest

from aidot_cameras import _upstream

TYPED = _upstream.HAS_TYPED_ACCOUNT

typed_only = pytest.mark.skipif(not TYPED, reason="typed upstream shape only")
dict_only = pytest.mark.skipif(TYPED, reason="dict upstream shape only")

BACKREF = _upstream.HAS_DEVICE_CLIENT_BACKREF

# Orthogonal to the record shape: 0.3.57+ is the dict shape plus this.
backref_only = pytest.mark.skipif(
    not BACKREF, reason="upstream DeviceClient takes the account client"
)


# --------------------------------------------------------------------------- #
# The compat layer itself - it must agree with what is actually installed
# --------------------------------------------------------------------------- #


def test_shape_detection_is_self_consistent():
    """Every capability flag must match the tree that is really importable."""
    import importlib

    def importable(mod, name):
        try:
            return hasattr(importlib.import_module(mod), name)
        except ImportError:
            return False

    assert _upstream.HAS_TYPED_ACCOUNT is importable(
        "aidot.models.auth_model", "UserInformation"
    )
    assert _upstream.DEVICE_STATE_IS_UPSTREAMS is importable(
        "aidot.device_client", "DeviceState"
    )
    assert _upstream.UPSTREAM_SHAPE == ("typed" if TYPED else "dict")

    # Derived independently of the detector: calling
    # _init_takes_account_client here would only compare the flag with itself.
    from aidot.device_client import DeviceClient

    params = list(inspect.signature(DeviceClient.__init__).parameters)
    assert _upstream.HAS_DEVICE_CLIENT_BACKREF is (
        len(params) >= 4 and params[3] == "client"
    ), params


def test_declared_range_excludes_the_typed_shape():
    """The typed shape (0.3.54-0.3.55) is no longer supported.

    It existed on PyPI for five days in July 2026 before 0.3.56 reverted it,
    Home Assistant core pins 0.3.56, and on it the LAN retry policy cannot
    apply to lights at all - that upstream client has neither ``connect`` nor
    ``_schedule_reconnect`` for ``LanRetryMixin`` to wrap.  Declaring it in
    range promised support that did not exist.  Lowering the floor again means
    solving that first.
    """
    import tomllib
    from pathlib import Path

    from packaging.requirements import Requirement

    pyproject = tomllib.loads(
        (Path(__file__).resolve().parents[1] / "pyproject.toml").read_text()
    )
    (spec,) = [
        Requirement(r).specifier
        for r in pyproject["project"]["dependencies"]
        if Requirement(r).name == "python-aidot"
    ]
    assert "0.3.55" not in spec, spec
    assert "0.3.54" not in spec, spec
    assert "0.3.56" in spec, spec  # Home Assistant core's pin
    assert "0.3.58" in spec, spec


def test_compat_layer_resolves_every_name_it_promises():
    """``__all__`` is the package-internal surface; none of it may be missing."""
    for name in _upstream.__all__:
        assert hasattr(_upstream, name), name


def test_moved_constants_resolve_wherever_they_live():
    assert "{region}" in _upstream.API_URL_TEMPLATE
    assert isinstance(_upstream.APP_ID, str) and _upstream.APP_ID
    assert isinstance(_upstream.DEFAULT_REGION, str) and _upstream.DEFAULT_REGION
    assert b"BEGIN PUBLIC KEY" in _upstream.PUBLIC_KEY_PEM


#: Cloud API versions verified against the live cloud, and what was verified.
#: Add a version here only after comparing it with a known-good one; never to
#: turn this test green.
VERIFIED_API_URL_TEMPLATES = {
    # <=0.3.56.
    "https://prod-{region}-api.arnoo.com/v17",
    # 0.3.57+.  Compared with v17 on 2026-09-23 using an existing token (no
    # login, no refresh): /houses identical; /devices 40/40 with zero fields or
    # values differing across 17 models including all three camera models;
    # /products byte-identical over 12 interleaved calls; the same error codes
    # with no token (21027) and a bogus refresh token (21025).  A real login
    # and a real token refresh on v35 were NOT exercised - both rotate the
    # account's single live token.
    "https://prod-{region}-api.arnoo.com/v35",
}


def test_api_url_template_is_a_version_we_have_verified():
    """Fail BY NAME when upstream moves the cloud API version.

    Upstream's ``AidotClient`` builds its account calls (login, token refresh,
    houses, devices, products) from this template, so a changed version
    segment silently points all of them at a different API.  The camera layer
    builds its own ``/v1/...`` URLs and does not use it.

    0.3.57 moved it from v17 to v35 and this test is what caught it.  Read a
    failure as "compare the new version with a verified one before adding it",
    not as a string to update.
    """
    assert _upstream.API_URL_TEMPLATE in VERIFIED_API_URL_TEMPLATES, (
        _upstream.API_URL_TEMPLATE
    )


def test_rsa_encrypt_keeps_its_two_argument_signature():
    """Public surface the integration repo may import.

    Upstream deleted ``rsa_encrypt`` in the dict shape in favour of a
    one-argument ``rsa_password_encrypt``, so this is supplied locally; the
    signature must not drift with whatever upstream is installed.
    """
    from aidot_cameras.crypto import rsa_encrypt

    params = list(inspect.signature(rsa_encrypt).parameters)
    assert params[:2] == ["message", "public_key"], params
    out = rsa_encrypt("hunter2")
    assert isinstance(out, str) and out
    # Explicit key argument must still be honoured.
    assert rsa_encrypt("hunter2", _upstream.PUBLIC_KEY_PEM)


def test_aes_helpers_round_trip():
    key = bytearray(b"T54uednca587".ljust(32, b"\x00"))
    blob = _upstream.aes_encrypt(b'{"a": 1}', key)
    assert _upstream.aes_decrypt(blob, key) == '{"a": 1}'
    assert _upstream.aes_decrypt_to_json(blob, key) == {"a": 1}


# --------------------------------------------------------------------------- #
# AidotClient - the account seam (aidot_cameras/client.py)
# --------------------------------------------------------------------------- #


def test_get_device_client_is_the_dispatch_seam():
    """The single construction site our CameraClient overrides.

    If upstream ever builds a DeviceClient anywhere else, cameras would get a
    plain upstream client and every camera feature would vanish.
    """
    from aidot.client import AidotClient

    assert callable(AidotClient.get_device_client)
    params = inspect.signature(AidotClient.get_device_client).parameters
    assert "device" in params, sorted(params)


@pytest.mark.parametrize(
    "name",
    [
        # Overridden by CameraClient.
        "async_post_login",
        "async_get_all_device",
        "setup_discover",
        "async_close",
        # Called on the client by our CLI (aidot_cameras/__main__.py).
        "set_token_fresh_cb",
    ],
)
def test_client_methods_we_override_or_call(name):
    from aidot.client import AidotClient

    assert callable(getattr(AidotClient, name, None)), name


@typed_only
def test_on_token_refreshed_hook_exists_on_the_typed_shape():
    """``CameraClient._on_token_refreshed`` is defined only when this exists.

    The dict shape has no such hook and needs none - it writes the rotated
    token straight into ``login_info`` and fires ``_token_fresh_cb`` itself.
    If upstream ever reintroduces the hook on the dict shape, the conditional
    override in client.py should be revisited rather than left dead.
    """
    from aidot.client import AidotClient

    assert callable(getattr(AidotClient, "_on_token_refreshed", None))


@pytest.mark.parametrize(
    "name",
    [
        "_device_clients",  # the client cache our dispatch reads and writes
        "_token_fresh_cb",  # fired by our _do_ensure_token
    ],
)
def test_client_attributes_we_reach_into(name):
    from aidot.client import AidotClient

    client = AidotClient(None, country_code="US")
    assert hasattr(client, name), name


@typed_only
def test_typed_account_attributes():
    from aidot.client import AidotClient

    client = AidotClient(None, country_code="US")
    # The HTTP surface async_get_all_device drives, and the typed account
    # record login_info mirrors.
    assert hasattr(client, "_cloud_api")
    assert hasattr(client, "user_info")


@dict_only
def test_dict_account_attributes():
    from aidot.client import AidotClient

    client = AidotClient(None, country_code="US")
    # Replaces user_info.region / user_info.country on this shape.
    assert hasattr(client, "_region")
    assert hasattr(client, "_base_url")
    assert isinstance(client.login_info, dict)


def test_account_http_calls_used_by_async_get_all_device():
    """The four cloud calls, whichever object they hang off."""
    from aidot.client import AidotClient

    client = AidotClient(None, country_code="US")
    if TYPED:
        target, names = (
            client._cloud_api,
            [
                "get_houses",
                "get_devices",
                "get_products",
                "refresh_token",
            ],
        )
    else:
        target, names = (
            client,
            [
                "async_get_houses",
                "async_get_devices",
                "async_get_products",
                "async_refresh_token",
            ],
        )
    for name in names:
        assert callable(getattr(target, name, None)), name


def test_account_field_helpers_work_on_a_real_client():
    """``_upstream``'s account accessors must not raise on a fresh client."""
    from aidot.client import AidotClient

    client = AidotClient(None, country_code="US")
    assert isinstance(_upstream.account_region(client), str)
    assert isinstance(_upstream.account_refresh_token(client), str)
    assert _upstream.account_token_ttl(client, 7200) == 7200


@dict_only
def test_dict_shape_constructor_indexes_token_keys_directly():
    """Why ``_survivable_token`` exists.

    The dict shape reads four keys off a stored token with ``[]``, so a partial
    entry - which the typed shape tolerated via ``update_from_json`` - raises
    KeyError and the account never loads.  If upstream ever softens this to
    ``.get()``, ``_survivable_token`` can go.
    """
    from aidot.client import AidotClient
    from aidot_cameras.const import CONF_ID

    with pytest.raises(KeyError):
        AidotClient(None, country_code="US", token={CONF_ID: "u1"})


@dict_only
def test_survivable_token_lets_a_partial_entry_load():
    from aidot_cameras.client import CameraClient
    from aidot_cameras.const import CONF_ID

    client = CameraClient(None, country_code="US", token={CONF_ID: "u1"})
    assert client.login_info.get(CONF_ID) == "u1"


# --------------------------------------------------------------------------- #
# DeviceClient - the per-device seam (aidot_cameras/device_client.py)
# --------------------------------------------------------------------------- #


@pytest.mark.parametrize(
    "name",
    [
        # Overridden by CameraDeviceClient (a rename here silently drops the
        # override, leaving upstream's behavior in place).
        "async_login",
        "close",
        "_notify_status_update",
        # Called on the client, not overridden.
        "receive_data",
        "update_ip_address",
        "send_dev_attr",
    ],
)
def test_device_client_methods(name):
    from aidot.device_client import DeviceClient

    assert callable(getattr(DeviceClient, name, None)), name


def test_read_data_seam_flag_matches_reality():
    """``CameraDeviceClient.read_data`` is defined only when this is True.

    The dict shape inlines the decrypt into ``receive_data``, so the raw JSON
    never escapes and the camera-only attribute recovery has nothing to hook.
    See docs/UPSTREAM.md, "Known dual-support gaps".
    """
    from aidot.device_client import DeviceClient
    from aidot_cameras.device_client import CameraDeviceClient

    assert _upstream.HAS_READ_DATA_SEAM is callable(
        getattr(DeviceClient, "read_data", None)
    )
    assert ("read_data" in CameraDeviceClient.__dict__) is _upstream.HAS_READ_DATA_SEAM


def test_device_client_requires_only_arguments_we_know_how_to_supply():
    """Fail BY NAME the moment upstream adds a required constructor argument.

    ``test_device_client_constructor_argument_shape`` below checks
    ``params[:3]``, so it stays green when upstream *appends* a required
    argument - which is exactly what 0.3.57 did, adding ``client`` (the
    ``AidotClient`` back-reference) as a third positional.  Every construction
    then raised ``TypeError: DeviceClient.__init__() missing 1 required
    positional argument: 'client'`` from deep inside upstream, naming nothing
    about which upstream release moved.

    This assertion names the parameter instead.  Read a failure as "upstream
    wants something new at construction - teach ``_upstream`` about it and add
    it to the allowed list here", never as "widen the list to make it pass".

    ``KEYWORD_ONLY`` is included deliberately: ``_init_takes_account_client``
    only recognises a POSITIONAL_OR_KEYWORD ``client``, so a required
    keyword-only reshape would slip past the detector and must be caught here.
    """
    from aidot.device_client import DeviceClient

    required = [
        p.name
        for p in inspect.signature(DeviceClient.__init__).parameters.values()
        if p.name != "self"
        and p.default is inspect.Parameter.empty
        and p.kind
        in (
            inspect.Parameter.POSITIONAL_ONLY,
            inspect.Parameter.POSITIONAL_OR_KEYWORD,
            inspect.Parameter.KEYWORD_ONLY,
        )
    ]
    assert required in (
        ["device", "user_info"],  # <=0.3.56
        ["device", "user_info", "client"],  # >=0.3.57, supplied via _upstream
    ), required


def test_device_client_constructor_argument_shape():
    """``__init__(device, user_info)`` - typed models or raw dicts."""
    from aidot.device_client import DeviceClient

    params = list(inspect.signature(DeviceClient.__init__).parameters)
    assert params[:3] == ["self", "device", "user_info"], params
    if BACKREF:
        assert params[3] == "client", params
    annotations = inspect.signature(DeviceClient.__init__).parameters
    device_annotation = annotations["device"].annotation
    if TYPED:
        assert device_annotation is not dict, device_annotation
    else:
        # dict[str, Any] - the raw cloud record, natively.
        assert getattr(device_annotation, "__origin__", None) is dict


@backref_only
def test_the_back_reference_carries_the_method_upstream_calls_on_it():
    """The account client is only useful if it has what upstream calls on it.

    Catches upstream keeping the name ``client`` but changing what it expects
    to find there - which the signature checks above cannot see.
    """
    from aidot.client import AidotClient

    assert callable(getattr(AidotClient, "async_execute_diff_command", None))


def test_device_client_builds_from_whatever_the_shape_wants():
    from aidot.device_client import (
        DeviceClient,
        DeviceInformation,
        DeviceStatusData,
    )

    raw_device = {
        "id": "d1",
        "name": "d1",
        "modelId": "lk.WIFI-RGBWLight-D0006",
        "aesKey": ["k" * 16],
        "password": "pw",
    }
    record = _upstream.DeviceModel.from_json(data=raw_device)
    account_client = object()
    args = _upstream.device_client_args(
        record,
        raw_device,
        _fake_typed_account(),
        {"id": "u1", "region": "us"},
        client=account_client,
    )
    client = DeviceClient(*args)
    if _upstream.HAS_DEVICE_CLIENT_BACKREF:
        # Upstream keeps it as ``self.client`` and calls
        # ``async_execute_diff_command`` on it from ``async_set_effect``.
        assert client.client is account_client
    # EXACT type, not isinstance: CameraClient._carry_active_color_mode swaps
    # this object for the carried DeviceStatusData subclass only when it is
    # upstream's own plain class, and skips (with a warning) otherwise.  An
    # isinstance check stays true for any upstream subclass, so it would keep
    # this suite green while RGBW+CCT bulbs quietly regressed to a stale color.
    # Read a failure here as "upstream now builds its own status subclass -
    # re-check the carried override before relaxing this assertion".
    assert type(client.status) is DeviceStatusData, type(client.status)
    assert isinstance(client.info, DeviceInformation)


def _fake_typed_account():
    """A ``UserInformation`` on the typed shape, a plain dict otherwise."""
    if _upstream.UserInformation is not None:
        return _upstream.UserInformation.from_json(data={"id": "u1", "region": "us"})
    return {"id": "u1", "region": "us"}


def test_reconnect_handle_is_cancellable_on_either_spelling():
    """The close-time leak fix must not go quiet when upstream renames this.

    ``cancel_pending_reconnect`` returns False rather than silently succeeding,
    which is the whole point - a ``getattr(x, "_reconnect_timer", None)`` finds
    nothing on the dict shape and reports success.
    """

    class _Handle:
        cancelled = False

        def cancel(self):
            self.cancelled = True

    for attr in _upstream.RECONNECT_HANDLE_ATTRS:
        holder = type("H", (), {})()
        handle = _Handle()
        setattr(holder, attr, handle)
        assert _upstream.cancel_pending_reconnect(holder) is True
        assert handle.cancelled

    assert _upstream.cancel_pending_reconnect(type("H", (), {})()) is False


def test_device_session_authenticated_reads_the_right_signal():
    if TYPED:
        holder = type("H", (), {"_state": _upstream.DeviceState.AUTHENTICATED})()
        assert _upstream.device_session_authenticated(holder) is True
        holder2 = type("H", (), {"_state": _upstream.DeviceState.IDLE})()
        assert _upstream.device_session_authenticated(holder2) is False
    else:
        holder = type("H", (), {"connect_and_login": True})()
        assert _upstream.device_session_authenticated(holder) is True
        holder2 = type("H", (), {"connect_and_login": False})()
        assert _upstream.device_session_authenticated(holder2) is False


# --------------------------------------------------------------------------- #
# Data classes we subclass
# --------------------------------------------------------------------------- #


def test_device_status_data_is_subclassable_and_updates_from_attr():
    """Our DeviceStatusData subclass adds active_color_mode on top of update()."""
    from aidot.device_client import DeviceStatusData
    from aidot.models.device_client_model import DeviceAttr

    class _Sub(DeviceStatusData):
        pass

    status = _Sub()
    status.update(DeviceAttr(OnOff=1, CCT=3000))
    assert status.cct == 3000
    for field in ("online", "on", "rgdb", "rgbw", "cct", "dimming"):
        assert hasattr(status, field), field


def test_device_information_is_subclassable():
    """Our DeviceInformation subclass adds a raw-dict constructor path."""
    from aidot.device_client import DeviceInformation

    class _Sub(DeviceInformation):
        pass

    raw = {"id": "d1", "modelId": "m"}
    # The typed shape's constructor wants the record; the dict shape's wants
    # the dict.  Our subclass accepts both, but upstream's base does not.
    info = _Sub(_upstream.DeviceModel.from_json(data=raw) if TYPED else raw)
    assert info.dev_id == "d1"


def test_device_information_exposes_enable_rgbw():
    """The capability flag CameraClient._carry_active_color_mode gates on."""
    from aidot.device_client import DeviceInformation

    assert hasattr(DeviceInformation, "enable_rgbw")


def test_device_record_round_trips_to_dict():
    """The camera layer works from raw cloud records, recovered via to_dict()."""
    device = _upstream.DeviceModel.from_json(data={"id": "d1", "modelId": "m"})
    assert callable(device.to_dict) and device.to_dict()["id"] == "d1"


@typed_only
def test_typed_account_round_trips_to_dict():
    from aidot.models.auth_model import UserInformation

    user_info = UserInformation.from_json(data={"id": "u1"})
    assert callable(user_info.to_dict) and user_info.to_dict()["id"] == "u1"


# --------------------------------------------------------------------------- #
# Module-level names re-exported by aidot_cameras.crypto / .const
# --------------------------------------------------------------------------- #


@pytest.mark.parametrize(
    "name",
    ["aes_encrypt", "aes_decrypt", "aes_decrypt_to_json", "rsa_encrypt"],
)
def test_crypto_helpers_are_reexported(name):
    """The public surface of aidot_cameras.crypto, whatever upstream renamed."""
    from aidot_cameras import crypto

    assert callable(getattr(crypto, name, None)), name


def test_const_names_reexported():
    from aidot_cameras.const import (
        API_URL_TEMPLATE,
        APP_ID,
        BASE_URL,
        DEFAULT_REGION,
        PUBLIC_KEY_PEM,
        Identity,
    )

    assert isinstance(APP_ID, str) and APP_ID
    # PEM bytes, fed to the RSA login encryption.
    assert b"BEGIN PUBLIC KEY" in PUBLIC_KEY_PEM
    assert "{region}" in API_URL_TEMPLATE
    assert isinstance(DEFAULT_REGION, str) and DEFAULT_REGION
    assert BASE_URL == API_URL_TEMPLATE.format(region=DEFAULT_REGION)
    assert Identity.RGBW and Identity.CCT


# --------------------------------------------------------------------------- #
# Discovery - aidot_cameras/discover.py
# --------------------------------------------------------------------------- #


def test_discovered_device_map_reaches_upstreams_device_clients():
    """Our sweep's addresses must be visible to a plain upstream DeviceClient.

    Typed shape: via the process-wide ``Discover.DISCOVERED_DEVICE`` class dict,
    which ``AidotClient.get_device_client`` reads - so CameraDiscover subclasses
    Discover purely to inherit that attribute by reference.

    Dict shape: upstream reads ``self._discover.discovered_device``, i.e.
    straight off our own instance, so a per-instance map is enough (and is
    better - two accounts no longer pool addresses in global state).
    """
    from aidot.discover import Discover
    from aidot_cameras.discover import CameraDiscover

    sweep = CameraDiscover({"id": "u1"}, None)
    sweep._discover_callback("d1", {"ipAddress": "192.0.2.10"})

    assert sweep.discovered_device["d1"] == "192.0.2.10"
    if _upstream.HAS_SHARED_DISCOVERY_MAP:
        assert Discover.DISCOVERED_DEVICE["d1"] == "192.0.2.10"
        Discover.DISCOVERED_DEVICE.pop("d1", None)
    else:
        # Nothing global to leak into.
        assert not hasattr(Discover, "DISCOVERED_DEVICE")


@pytest.mark.parametrize(
    "name",
    [
        # Overridden by InterfaceBroadcastProtocol.
        "send_broadcast",
        "datagram_received",
        # Inherited unchanged.
        "connection_made",
        "error_received",
        "connection_lost",
        "close",
    ],
)
def test_broadcast_protocol_methods(name):
    from aidot.discover import BroadcastProtocol

    assert callable(getattr(BroadcastProtocol, name, None)), name


def test_broadcast_protocol_construction_matches_the_shape():
    """``(callback)`` on the typed shape, ``(callback, user_id)`` on the dict one."""
    from aidot.discover import BroadcastProtocol
    from aidot_cameras.discover import InterfaceBroadcastProtocol

    params = list(inspect.signature(BroadcastProtocol.__init__).parameters)
    assert params[1] == "callback", params
    assert _upstream.broadcast_protocol_args(None, "u1") == (
        (None, "u1") if "user_id" in params else (None,)
    )

    # Our subclass must build on either shape and expose what it overrides.
    protocol = InterfaceBroadcastProtocol(None, "u1", broadcast_addr="192.0.2.255")
    for name in ("_is_closed", "aes_key", "_discover_cb", "user_id"):
        assert hasattr(protocol, name), name
    assert protocol.user_id == "u1"


def test_discover_request_and_response_models():
    from aidot.models.discover_model import DiscoverRequest, DiscoverResponse

    request = DiscoverRequest.from_params(userId="u1")
    assert request.to_dict()
    response = DiscoverResponse.from_json(data=request.to_dict())
    assert hasattr(response, "payload")


def test_device_state_is_reexported_and_stays_value_compatible():
    """Public surface the Home Assistant integration annotates against.

    On the typed shape it must BE upstream's enum, so a comparison against a
    live client's ``_state`` is meaningful.  On the dict shape upstream deleted
    it, so a value-identical stand-in is supplied rather than letting a public
    name vanish - consumers wanting the answer should call
    ``_upstream.device_session_authenticated`` instead.
    """
    from aidot_cameras.device_client import DeviceState

    if _upstream.DEVICE_STATE_IS_UPSTREAMS:
        from aidot.device_client import DeviceState as _upstream_enum

        assert DeviceState is _upstream_enum
    for name in ("IDLE", "AUTHENTICATED"):
        assert hasattr(DeviceState, name), name
    # Values are load-bearing: a consumer may have persisted or hard-coded one.
    assert int(DeviceState.IDLE) == 0
    assert int(DeviceState.AUTHENTICATED) == 6


def test_configure_stream_limits_is_public():
    # The Home Assistant integration calls this to size the concurrent-serve cap
    # to its camera count; a cap below the fleet silently starves a camera.
    import aidot_cameras

    assert callable(getattr(aidot_cameras, "configure_stream_limits", None))
