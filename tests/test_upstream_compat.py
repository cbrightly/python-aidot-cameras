"""Seam contract against the pinned upstream ``python-aidot``.

This package extends upstream instead of forking it, so every upstream name it
subclasses, overrides or calls is part of a private API that upstream never
promised to keep.  A version bump that quietly moves or renames one of them
would otherwise surface as a puzzling failure deep inside a camera stream (or,
worse, as a silently skipped override).

Each assertion below is here because ``aidot_cameras`` depends on it; the file
is meant to be read as the checklist to work through when the pin in
pyproject.toml moves.  Keep it importing ``aidot.*`` directly - it is the one
test module that deliberately does NOT target our package.
"""

import inspect

import pytest

from aidot.client import AidotClient
from aidot.const import (
    API_URL_TEMPLATE,
    APP_ID,
    DEFAULT_REGION,
    PUBLIC_KEY_PEM,
    Identity,
)
from aidot.device_client import DeviceClient, DeviceInformation, DeviceStatusData
from aidot.discover import BroadcastProtocol, Discover
from aidot.models.auth_model import UserInformation
from aidot.models.device_model import DeviceModel
from aidot.models.discover_model import DiscoverRequest, DiscoverResponse
from aidot.utils import crypto


# --------------------------------------------------------------------------- #
# AidotClient - the account seam (aidot_cameras/client.py)
# --------------------------------------------------------------------------- #

def test_get_device_client_is_the_dispatch_seam():
    """The single construction site our CameraClient overrides.

    If upstream ever builds a DeviceClient anywhere else, cameras would get a
    plain upstream client and every camera feature would vanish.
    """
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
        "_on_token_refreshed",
        # Called on the client by our CLI (aidot_cameras/__main__.py).
        "set_token_fresh_cb",
    ],
)
def test_client_methods_we_override_or_call(name):
    assert callable(getattr(AidotClient, name, None)), name


@pytest.mark.parametrize(
    "name",
    [
        "_device_clients",   # the client cache our dispatch reads and writes
        "_cloud_api",        # the HTTP surface async_get_all_device drives
        "_token_fresh_cb",   # fired by our _do_ensure_token
        "user_info",         # the typed account record login_info mirrors
    ],
)
def test_client_attributes_we_reach_into(name):
    client = AidotClient(None, country_code="US")
    assert hasattr(client, name), name


@pytest.mark.parametrize(
    "name",
    ["get_houses", "get_devices", "get_products", "refresh_token"],
)
def test_cloud_api_calls_used_by_async_get_all_device(name):
    client = AidotClient(None, country_code="US")
    assert callable(getattr(client._cloud_api, name, None)), name


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
        "read_data",
        "_notify_status_update",
        # Called on the client, not overridden.
        "receive_data",
        "update_ip_address",
        "send_dev_attr",
    ],
)
def test_device_client_methods(name):
    assert callable(getattr(DeviceClient, name, None)), name


def test_device_client_takes_typed_models():
    """``__init__(device: DeviceModel, user_info: UserInformation)``.

    CameraDeviceClient.__init__ calls super() with exactly these two, and keeps
    the raw cloud dicts alongside them for the camera layer.
    """
    params = list(inspect.signature(DeviceClient.__init__).parameters)
    assert params[:3] == ["self", "device", "user_info"], params


def test_device_client_builds_from_typed_models():
    device = DeviceModel.from_json(
        data={"id": "d1", "name": "d1", "modelId": "lk.WIFI-RGBWLight-D0006",
              "aesKey": ["k" * 16], "password": "pw"}
    )
    user_info = UserInformation.from_json(data={"id": "u1", "region": "us"})
    client = DeviceClient(device, user_info)
    assert isinstance(client.status, DeviceStatusData)
    assert isinstance(client.info, DeviceInformation)


# --------------------------------------------------------------------------- #
# Data classes we subclass
# --------------------------------------------------------------------------- #

def test_device_status_data_is_subclassable_and_updates_from_attr():
    """Our DeviceStatusData subclass adds active_color_mode on top of update()."""
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

    class _Sub(DeviceInformation):
        pass

    info = _Sub(DeviceModel.from_json(data={"id": "d1", "modelId": "m"}))
    assert info.dev_id == "d1"


def test_device_information_exposes_enable_rgbw():
    """The capability flag CameraClient._carry_active_color_mode gates on."""
    assert hasattr(DeviceInformation, "enable_rgbw")


def test_typed_models_round_trip_to_dict():
    """The camera layer works from raw cloud records, recovered via to_dict()."""
    device = DeviceModel.from_json(data={"id": "d1", "modelId": "m"})
    user_info = UserInformation.from_json(data={"id": "u1"})
    assert callable(device.to_dict) and device.to_dict()["id"] == "d1"
    assert callable(user_info.to_dict) and user_info.to_dict()["id"] == "u1"


# --------------------------------------------------------------------------- #
# Module-level names re-exported by aidot_cameras.crypto / .const
# --------------------------------------------------------------------------- #

@pytest.mark.parametrize(
    "name",
    ["aes_encrypt", "aes_decrypt", "aes_decrypt_to_json", "rsa_encrypt"],
)
def test_crypto_helpers(name):
    assert callable(getattr(crypto, name, None)), name


def test_const_names_reexported():
    assert isinstance(APP_ID, str) and APP_ID
    # PEM bytes, fed to the RSA login encryption.
    assert b"BEGIN PUBLIC KEY" in PUBLIC_KEY_PEM
    # aidot_cameras.const builds BASE_URL as API_URL_TEMPLATE.format(region=...).
    assert "{region}" in API_URL_TEMPLATE
    assert isinstance(DEFAULT_REGION, str) and DEFAULT_REGION
    assert Identity.RGBW and Identity.CCT


# --------------------------------------------------------------------------- #
# Discovery - aidot_cameras/discover.py
# --------------------------------------------------------------------------- #

def test_discovered_device_map_is_shared_class_state():
    """Our sweep writes into upstream's map so upstream's clients see the IPs.

    CameraDiscover subclasses Discover purely to inherit this attribute by
    reference; if upstream made it per-instance, a plain upstream DeviceClient
    would silently stop being given a LAN address.
    """
    assert isinstance(Discover.DISCOVERED_DEVICE, dict)


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
    assert callable(getattr(BroadcastProtocol, name, None)), name


def test_broadcast_protocol_takes_a_callback_and_exposes_its_state():
    params = list(inspect.signature(BroadcastProtocol.__init__).parameters)
    assert params[:2] == ["self", "callback"], params
    protocol = BroadcastProtocol(None)
    # Read by our send_broadcast / datagram_received overrides.
    for name in ("_is_closed", "aes_key", "_discover_cb"):
        assert hasattr(protocol, name), name


def test_discover_request_and_response_models():
    request = DiscoverRequest.from_params(userId="u1")
    assert request.to_dict()
    response = DiscoverResponse.from_json(data=request.to_dict())
    assert hasattr(response, "payload")
