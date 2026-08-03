"""Fixtures wiring the real client stack to the fake environment.

Every e2e test gets: a fake MQTT broker, a fake cloud, the AIDOT_* seams
pointed at them, and STUN/TURN disabled so a run makes no external egress.
"""
import os
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))))

# Skip this whole tier when its extra deps are absent, instead of failing
# COLLECTION for everyone. Marker filtering (-m "not e2e") happens after
# collection, so without this a plain `pytest tests/` in an environment with
# only the unit-tier deps dies here rather than running the unit tests - which
# is exactly how the unit CI job broke.
pytest.importorskip("amqtt", reason="e2e tier needs the fake MQTT broker (amqtt)")

from tests.e2e.fakes.cloud_http import FakeCloud
from tests.e2e.fakes.go2rtc_stub import FakeGo2rtc
from tests.e2e.fakes.mqtt_broker import FakeBroker

pytestmark = pytest.mark.e2e


@pytest.fixture
async def fake_broker():
    broker = FakeBroker()
    await broker.start()
    try:
        yield broker
    finally:
        await broker.stop()


@pytest.fixture
async def fake_cloud(fake_broker):
    """The smarthome host ({region}-smarthome.arnoo.com in production)."""
    cloud = FakeCloud(mqtt_url=fake_broker.url)
    await cloud.start()
    try:
        yield cloud
    finally:
        await cloud.stop()


@pytest.fixture
async def fake_api(fake_broker):
    """The platform-API host (prod-{region}-api.arnoo.com in production).

    Separate from the smarthome host on purpose: both expose a
    ``lowPowerActiveState`` endpoint, and only the smarthome one is
    battery-gated.  Separate ports keep "which wake fired" unambiguous.
    """
    api = FakeCloud(mqtt_url=fake_broker.url)
    await api.start()
    try:
        yield api
    finally:
        await api.stop()


@pytest.fixture
async def fake_go2rtc():
    stub = FakeGo2rtc()
    await stub.start()
    try:
        yield stub
    finally:
        await stub.stop()


@pytest.fixture
def fakelab_env(monkeypatch, fake_broker, fake_cloud, fake_api):
    """Point the library at the fake environment; block all real egress.

    Returns the (broker, cloud, api) triple for convenience.
    """
    monkeypatch.setenv("AIDOT_MQTT_URL", fake_broker.url)
    monkeypatch.setenv("AIDOT_API_BASE_TEMPLATE", fake_api.base_url)
    monkeypatch.setenv("AIDOT_SMARTHOME_URL_TEMPLATE", fake_cloud.base_url)
    # No STUN/TURN: hermetic, and the SDES STUN-responder window early-exits.
    monkeypatch.setenv("AIDOT_STUN_SERVERS", "")
    monkeypatch.setenv("AIDOT_TURN_SERVERS", "")
    monkeypatch.setenv("AIDOT_SDES_HOLEPUNCH_HOST", "")
    # One account-level MQTT connection is the production default; per-open
    # connections make the fake broker's session bookkeeping noisier.  The knob
    # is DISABLE-on-falsey (client.py: persistent unless the value is one of
    # 0/false/no/off), so "0" would have selected the per-open topology this
    # comment is trying to avoid - and left the account-level path production
    # actually uses untested by the whole tier.
    monkeypatch.setenv("AIDOT_PERSISTENT_MQTT", "1")
    # Keep the tier fast: these are the production waits sized for real
    # firmware on a real network, and nothing here is more than a few ms away.
    # Shrunk through the SAME knobs production uses - no internals patched.
    monkeypatch.setenv("AIDOT_MAX_CONCURRENT_OPENS", "4")
    monkeypatch.setenv("AIDOT_MAX_CONCURRENT_STREAMS", "8")
    # _FIRST_MEDIA_WAIT_S (75s) is sized for real firmware cold-start on a real
    # network; against a fake on loopback it is pure dead time and would make
    # the tier unusable on every PR.  Patched here rather than turned into an
    # env knob on purpose: setting this too low in production is exactly the
    # regression that shipped a launch deadline inside the cold-start window.
    # The production value itself is asserted in tests/test_media_wait_floor.py.
    import aidot_cameras.camera.sdes_open as _sdes_open
    monkeypatch.setattr(_sdes_open, "_FIRST_MEDIA_WAIT_S", 6.0, raising=True)
    return fake_broker, fake_cloud, fake_api


@pytest.fixture
def e2e_device_client(fakelab_env, make_camera_device_client):
    """A real CameraDeviceClient wired to the fake environment."""
    broker, cloud, api = fakelab_env

    def _make(profile: str = "A001513", **kwargs):
        dc = make_camera_device_client(profile, **kwargs)
        # Skip the credential-strategy ladder: the fake broker is anonymous.
        dc._smarthome_auth = {
            "mqttUser": "fake-mqtt-user",
            "mqttPassword": "fake-mqtt-password",
            "raw": {"ip": cloud.public_ip},
        }
        # The cloud must know this device, or the client warns about a missing
        # numeric userId and skips the topic subscriptions keyed on it.
        # batchGetDeviceUserInfo is served by the platform-API host.
        api.device_user_info.append({
            "deviceId": dc.device_id,
            "userId": 4242,
            "userUuid": "cam-user-uuid",
            "localIp": "127.0.0.1",
        })
        return dc

    return _make
