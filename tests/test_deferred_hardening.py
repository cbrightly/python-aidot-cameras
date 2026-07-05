"""Tests for the deferred security-review hardening (prep branch).

Covers the parts that don't need real cameras. The transport-level behaviours
(self-scoped DTLS 1.0, cloud-playback TLS on the wire) need on-device validation.
"""

import asyncio
import json
import struct

import pytest

from aidot.aes_utils import aes_encrypt
from aidot.camera.client import _mqtt_publish_delivered
from aidot.camera.lan_control import _MAGIC, CameraLanClient, CameraLanError
from aidot.camera.playback import CloudPlaybackSession


# -- _mqtt_device_cmd no longer reports success on a failed broker connection --

def test_mqtt_publish_delivered_semantics():
    assert _mqtt_publish_delivered(None) is True             # no status: prior behavior
    assert _mqtt_publish_delivered({}) is True               # connected assumed
    assert _mqtt_publish_delivered({"connected": True}) is True
    assert _mqtt_publish_delivered({"connected": False}) is False   # broker refused
    assert _mqtt_publish_delivered({"error": "timeout"}) is False   # connect error


# -- CloudPlaybackSession TLS is opt-in, default off ---------------------------

def _cloud_session(use_tls):
    return CloudPlaybackSession(
        server_ip="10.0.0.5", server_port=443, heartbeat_interval=10,
        task_id=1, client_id="c", start_ts_s=0, on_frame=lambda f: None,
        use_tls=use_tls,
    )


def test_cloud_playback_tls_defaults_off():
    assert _cloud_session(False)._use_tls is False


def test_cloud_playback_tls_opt_in():
    assert _cloud_session(True)._use_tls is True


# -- LAN control: a login we can't complete de-eligibilities the client --------

class _FakeWriter:
    def write(self, _data):
        pass

    async def drain(self):
        pass


def _frame(body: bytes) -> bytes:
    return struct.pack(">HHI", _MAGIC, 2, len(body)) + body


def _lan_client():
    return CameraLanClient(
        {"id": "cam1", "aesKey": ["0123456789abcdef"], "password": "pw",
         "modelId": "LK.IPC.A000088"},
        {"id": "user1"},
        ip="10.0.0.9",
    )


def _reader_with(body: bytes) -> asyncio.StreamReader:
    r = asyncio.StreamReader()
    r.feed_data(_frame(body))
    r.feed_eof()
    return r


def test_login_rejection_marks_ineligible():
    c = _lan_client()
    resp = aes_encrypt(json.dumps({"ack": {"code": 403}}).encode(), c._key)

    async def _run():
        with pytest.raises(CameraLanError, match="rejected"):
            await c._login(_reader_with(resp), _FakeWriter())

    asyncio.run(_run())
    assert c.eligible is False


def test_undecryptable_login_response_marks_ineligible():
    # A spoofing host that lacks the device key returns bytes we can't decrypt.
    c = _lan_client()

    async def _run():
        with pytest.raises(CameraLanError, match="undecryptable"):
            await c._login(_reader_with(b"not-valid-aes-ciphertext-xxxxxxx"), _FakeWriter())

    asyncio.run(_run())
    assert c.eligible is False
