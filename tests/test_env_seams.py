"""The test-environment seams must be exact no-ops when unset.

Each knob (added so an end-to-end test can point the whole client at a local
fake cloud) has to preserve today's production URLs / ICE servers with the env
var absent, and take effect when set.  A regression here would silently
redirect real cloud traffic, so both directions are locked.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.constants import (
    aidot_api_base,
    fallback_turn_uris,
    smarthome_base,
    stun_server_uris,
)
from aidot_cameras.camera.sdes import SdesSession


def test_smarthome_base_default(monkeypatch):
    monkeypatch.delenv("AIDOT_SMARTHOME_URL_TEMPLATE", raising=False)
    assert smarthome_base("us") == "https://us-smarthome.arnoo.com:443"
    assert smarthome_base("eu") == "https://eu-smarthome.arnoo.com:443"


def test_smarthome_base_override(monkeypatch):
    monkeypatch.setenv("AIDOT_SMARTHOME_URL_TEMPLATE", "http://127.0.0.1:9001")
    assert smarthome_base("us") == "http://127.0.0.1:9001"
    # {region} still interpolates when the fake wants per-region routing
    monkeypatch.setenv("AIDOT_SMARTHOME_URL_TEMPLATE", "http://127.0.0.1:9001/{region}")
    assert smarthome_base("us") == "http://127.0.0.1:9001/us"


def test_aidot_api_base_default(monkeypatch):
    monkeypatch.delenv("AIDOT_API_BASE_TEMPLATE", raising=False)
    assert aidot_api_base("us") == "https://prod-us-api.arnoo.com"


def test_aidot_api_base_override(monkeypatch):
    monkeypatch.setenv("AIDOT_API_BASE_TEMPLATE", "http://127.0.0.1:8080")
    assert aidot_api_base("us") == "http://127.0.0.1:8080"


def test_device_client_urls_follow_seams(monkeypatch, make_camera_device_client):
    """The properties the camera layer actually builds requests from."""
    dc = make_camera_device_client("A001513")
    monkeypatch.delenv("AIDOT_API_BASE_TEMPLATE", raising=False)
    monkeypatch.delenv("AIDOT_SMARTHOME_URL_TEMPLATE", raising=False)
    region = dc._region
    assert dc._aidot_v21_base == f"https://prod-{region}-api.arnoo.com/v21"
    assert dc._aidot_v32_base == f"https://prod-{region}-api.arnoo.com/v32/api/ipc"
    assert dc._smarthome_base == f"https://{region}-smarthome.arnoo.com:443"

    monkeypatch.setenv("AIDOT_API_BASE_TEMPLATE", "http://localhost:9001")
    monkeypatch.setenv("AIDOT_SMARTHOME_URL_TEMPLATE", "http://localhost:9002")
    assert dc._aidot_v21_base == "http://localhost:9001/v21"
    assert dc._aidot_v32_base == "http://localhost:9001/v32/api/ipc"
    assert dc._smarthome_base == "http://localhost:9002"


def test_stun_servers_default_and_override(monkeypatch):
    monkeypatch.delenv("AIDOT_STUN_SERVERS", raising=False)
    assert stun_server_uris() == ["stun:stun.l.google.com:19302"]

    monkeypatch.setenv("AIDOT_STUN_SERVERS", "stun:127.0.0.1:3478, stun:127.0.0.1:3479")
    assert stun_server_uris() == ["stun:127.0.0.1:3478", "stun:127.0.0.1:3479"]

    # Empty disables entirely (no egress from a hermetic test run).
    monkeypatch.setenv("AIDOT_STUN_SERVERS", "")
    assert stun_server_uris() == []


def test_fallback_turn_default_and_override(monkeypatch):
    monkeypatch.delenv("AIDOT_TURN_SERVERS", raising=False)
    assert fallback_turn_uris() == [
        "stun:3.230.182.123:3478",
        "turn:3.230.182.123:5349",
    ]

    monkeypatch.setenv("AIDOT_TURN_SERVERS", "turn:127.0.0.1:3478")
    assert fallback_turn_uris() == ["turn:127.0.0.1:3478"]

    monkeypatch.setenv("AIDOT_TURN_SERVERS", "")
    assert fallback_turn_uris() == []


def test_mqtt_url_env_short_circuits_fetch(monkeypatch, make_camera_device_client):
    import asyncio

    dc = make_camera_device_client("A001513")
    dc._mqtt_url = None

    def _boom(*_a, **_k):  # the seam must avoid the cloud call entirely
        raise AssertionError("AIDOT_MQTT_URL must not trigger an HTTP fetch")

    monkeypatch.setattr(dc, "_leedarson_headers", _boom)
    monkeypatch.setenv("AIDOT_MQTT_URL", "ws://127.0.0.1:11883/mqtt")
    assert asyncio.run(dc._async_get_mqtt_url()) == "ws://127.0.0.1:11883/mqtt"


def test_mqtt_url_unset_does_not_short_circuit(monkeypatch, make_camera_device_client):
    """With the knob unset the normal cloud fetch path must still be taken."""
    import asyncio

    dc = make_camera_device_client("A001513")
    dc._mqtt_url = None
    monkeypatch.delenv("AIDOT_MQTT_URL", raising=False)

    reached = []

    def _headers():
        reached.append(1)
        raise RuntimeError("stop here - we only assert the fetch was attempted")

    monkeypatch.setattr(dc, "_leedarson_headers", _headers)
    asyncio.run(dc._async_get_mqtt_url())
    assert reached, "unset AIDOT_MQTT_URL must fall through to the cloud fetch"


def test_sdes_media_stats_defaults_and_counts():
    counts = [0, 0]
    progress = [0.0]
    vpt, apt = [None], [None]
    sess = SdesSession.__new__(SdesSession)
    sess._media_counts = counts
    sess._media_progress = progress
    sess._first_video_pt = vpt
    sess._first_audio_pt = apt

    stats = sess.media_stats()
    assert stats == {
        "packets": 0, "bytes": 0, "last_media_monotonic": 0.0,
        "video_pt": None, "audio_pt": None,
    }

    # bridge-thread updates are visible through the shared lists
    counts[0], counts[1] = 12, 3456
    progress[0] = 99.5
    vpt[0], apt[0] = 96, 8
    stats = sess.media_stats()
    assert stats["packets"] == 12
    assert stats["bytes"] == 3456
    assert stats["last_media_monotonic"] == 99.5
    assert stats["video_pt"] == 96
    assert stats["audio_pt"] == 8


def test_sdes_session_media_defaults_when_not_wired():
    """A session built without the new lists must still answer media_stats()."""
    sess = SdesSession(
        proc=object(), sdp_path="/tmp/x.sdp", outgoing_q=object(), mqtt_fut=object(),
    )
    assert sess.media_stats()["packets"] == 0
    assert sess.media_stats()["video_pt"] is None
