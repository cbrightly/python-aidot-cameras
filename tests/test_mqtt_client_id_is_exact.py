"""Never decorate the MQTT client id.

The broker binds the credential to the EXACT registered `mqttClientId`. A
suffixed connect is refused with CONNACK rc=4, "Bad user name or password" -
not a degraded session, no session at all: no subscribe, no messages, nothing
to receive.

`async_get_camera_attributes` shipped with a `-cmd` suffix and so could never
work. It cost three separate investigations, each of which blamed the camera,
because the helper in use returns messages and drops the transport status.
This test is cheaper than the fourth investigation.
"""

import pathlib
import re

SRC = pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"


def _python_files():
    return [p for p in SRC.rglob("*.py") if "_vendor" not in p.parts]


def test_no_client_id_is_built_by_appending_to_the_registered_one():
    offenders = []
    # A client id assembled from the registered one plus anything else.
    patterns = [
        re.compile(r'f"\{_?base_cid\}[^"]+"'),
        re.compile(r'f"\{[a-z_]*client_?id\}[^"]+"'),
        re.compile(r'(?:_base_cid|client_id|mqtt_cid)\s*\+\s*["\']'),
    ]
    for path in _python_files():
        for n, line in enumerate(path.read_text().splitlines(), 1):
            if line.lstrip().startswith("#"):
                continue  # comments explain the defect; they are not it
            for pat in patterns:
                if pat.search(line):
                    offenders.append(f"{path.name}:{n}: {line.strip()[:90]}")
    assert not offenders, (
        "an MQTT client id is being built by appending to the registered "
        "mqttClientId; the broker refuses that connect with rc=4 and the "
        "caller sees silence:\n  " + "\n  ".join(offenders)
    )


def test_the_attribute_read_prefers_the_shared_connection(
    monkeypatch, make_camera_device_client
):
    """Riding the persistent connection is what avoids both failure modes.

    It is the only identity the broker accepts, and using it needs no second
    connect - so no refusal, and no evicting whoever already holds the id. The
    preference must not be gated on the persistent-MQTT setting: off by default
    would make the doomed fallback the normal path. When there is no shared
    connection, the fallback connects with the exact registered id.
    """
    import asyncio

    from aidot_cameras.camera import client as client_mod

    cam = make_camera_device_client("A001064")
    asked, sessions = [], []

    class _Pm:
        async def request(self, *, publish_items, subscribe_topics, timeout):
            asked.append(publish_items)
            return [], {}

    async def _creds():
        return "u", "p", "app-user-1"

    async def _url():
        return "mqtts://broker.invalid:8883"

    async def _session(url, user, pwd, client_id, **kw):
        sessions.append(client_id)
        return [], {"connected": True}

    pm = _Pm()

    async def _get_pm():
        return pm

    cam._mqtt_credentials = _creds
    cam._async_get_mqtt_url = _url
    cam._resolve_persistent_mqtt = lambda: False  # the setting is off
    cam._get_persistent_mqtt = _get_pm
    monkeypatch.setattr(client_mod, "_mqtt_session_with_status", _session)

    asyncio.run(cam.async_get_camera_attributes(timeout=0.1))
    assert len(asked) == 1 and sessions == [], "rode the shared connection"

    pm = None
    asyncio.run(cam.async_get_camera_attributes(timeout=0.1))
    assert sessions == ["app-user-1"], "the fallback uses the exact registered id"
