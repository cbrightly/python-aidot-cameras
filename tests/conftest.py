"""Shared fixtures for the test suite.

Provides factory fixtures for the raw cloud records the camera layer runs on
(`raw_device` / `login_info`), parameterizable by camera model profile, so
tests stop hand-rolling `Cls.__new__` stubs for the common cases.

Model profiles mirror docs/CAMERAS.md:

- ``A000088``  M3 Pro, DTLS-SRTP, mains
- ``A001513``  "L2", SDES-SRTP, battery (woken on demand)
- ``A001064``  PTZ, SDES-SRTP, mains (role-reversal handshake)
- ``A001108``  battery SDES, recognized in code but never validated live
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

# (model_id, properties) per profile.  Transport selection is driven by the
# cloud device-dict `properties` (enableSdes / isDTLS), NOT the model id; the
# model id drives per-model behavior (battery wake, role reversal, plain RTP).
MODEL_PROFILES = {
    "A000088": ("LK.IPC.A000088", {"isDTLS": "1"}),
    "A001513": ("LK.IPC.A001513", {"enableSdes": "1"}),
    "A001064": ("LK.IPC.A001064", {"enableSdes": "1"}),
    "A001108": ("LK.IPC.A001108", {"enableSdes": "1"}),
}


@pytest.fixture
def raw_device():
    """Factory for a camera's raw cloud device record.

    Usage::

        dev = raw_device("A001513")                  # battery SDES profile
        dev = raw_device("A000088", name="Deck")     # DTLS profile
        dev = raw_device("A001513", properties={...})  # override properties
    """
    counter = [0]

    def _make(profile: str = "A001513", **overrides) -> dict:
        model_id, props = MODEL_PROFILES[profile]
        counter[0] += 1
        dev = {
            "id": f"dev-{profile.lower()}-{counter[0]}",
            "name": f"Camera {profile} {counter[0]}",
            "modelId": model_id,
            # Real cameras report aesKey as a truthy list holding None.
            "aesKey": [None],
            "properties": dict(props),
        }
        props_override = overrides.pop("properties", None)
        if props_override:
            dev["properties"].update(props_override)
        dev.update(overrides)
        return dev

    return _make


@pytest.fixture
def login_info():
    """Factory for the account-shared login_info dict the camera layer mutates.

    The returned dict is the SAME object every device client must share (token
    refresh and the persistent-MQTT cache are account-level state).
    """

    def _make(**overrides) -> dict:
        info = {
            "id": "user-1",
            "accessToken": "test-access-token",
            "region": "us",
            "mqttClientId": "app-user-1",
        }
        info.update(overrides)
        return info

    return _make


@pytest.fixture
def make_camera_device_client(raw_device, login_info):
    """Factory building a real CameraDeviceClient from a model profile.

    Constructs through the public constructor (typed models + raw records),
    exactly as CameraClient does, so tests exercise real initialization
    instead of `Cls.__new__` stubs.

    Both upstream shapes are live, so the account argument is resolved through
    ``_upstream`` rather than imported directly: the typed shape (0.3.54-0.3.55)
    wants a ``UserInformation``, while the dict shape (<=0.3.53, >=0.3.56) has
    no such class at all and takes the raw login dict natively.  Importing
    ``aidot.models.auth_model`` here raises ModuleNotFoundError on every
    dict-shape install - including the one Home Assistant pins.
    """
    from aidot_cameras._upstream import (
        HAS_TYPED_ACCOUNT,
        DeviceModel,
        UserInformation,
    )
    from aidot_cameras.device_client import CameraDeviceClient

    def _make(profile: str = "A001513", *, login: dict | None = None, **dev_overrides):
        dev = raw_device(profile, **dev_overrides)
        user = login if login is not None else login_info()
        account = UserInformation.from_json(data=user) if HAS_TYPED_ACCOUNT else user
        return CameraDeviceClient(
            DeviceModel.from_json(data=dev),
            account,
            raw_device=dev,
            login_info=user,
        )

    return _make
