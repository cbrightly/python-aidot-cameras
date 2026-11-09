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

import re


_CLIENT_ID = re.compile(r"^_?(base_cid|[a-z_]*client_?id|mqtt_cid)$")


def _is_client_id(node) -> bool:
    import ast

    name = getattr(node, "id", None) or getattr(node, "attr", None) or ""
    return isinstance(node, (ast.Name, ast.Attribute)) and bool(_CLIENT_ID.match(name))


def test_no_client_id_is_built_by_appending_to_the_registered_one():
    """No f-string or concatenation in the package builds a client id from the
    registered one plus anything else."""
    import ast

    from tests.ast_guards import package_modules

    offenders = []
    for path in package_modules():
        for node in ast.walk(ast.parse(path.read_text())):
            # The registered id followed by anything: f"{client_id}-cmd" or
            # client_id + "-cmd". An id quoted inside a message is not one.
            if isinstance(node, ast.JoinedStr):
                head = node.values[0] if node.values else None
                if (
                    isinstance(head, ast.FormattedValue)
                    and _is_client_id(head.value)
                    and len(node.values) > 1
                ):
                    offenders.append(f"{path.name}:{node.lineno}")
            elif isinstance(node, ast.BinOp) and isinstance(node.op, ast.Add):
                if _is_client_id(node.left) and isinstance(
                    node.right, (ast.Constant, ast.JoinedStr)
                ):
                    offenders.append(f"{path.name}:{node.lineno}")
    assert not offenders, (
        "an MQTT client id is being built by appending to the registered "
        "mqttClientId; the broker refuses that connect with rc=4: "
        + ", ".join(offenders)
    )


def test_the_attribute_read_uses_only_the_shared_connection(
    monkeypatch, make_camera_device_client
):
    """Riding the persistent connection is what avoids both failure modes.

    It is the only identity the broker accepts, and using it needs no second
    connect - so no refusal, and no evicting whoever already holds the id.
    With no shared connection there is no fallback connect at all: one with
    the exact id would evict the connection that holds it.
    """
    import asyncio

    import aidot_cameras.camera.protocol as proto

    cam = make_camera_device_client("A001064")
    asked = []

    class _Pm:
        async def request(self, *, publish_items, subscribe_topics, timeout):
            asked.append(publish_items)
            return [], {}

    async def _creds():
        return "u", "p", "app-user-1"

    async def _url():
        return "mqtts://broker.invalid:8883"

    pm = _Pm()

    async def _get_pm():
        return pm

    def _no_connect(*a, **k):
        raise AssertionError("opened a second MQTT connection")

    cam._mqtt_credentials = _creds
    cam._async_get_mqtt_url = _url
    cam._get_persistent_mqtt = _get_pm
    monkeypatch.setattr(proto._PersistentMqtt, "__init__", _no_connect)

    asyncio.run(cam.async_get_camera_attributes(timeout=0.1))
    assert len(asked) == 1, "rode the shared connection"

    pm = None
    asyncio.run(cam.async_get_camera_attributes(timeout=0.1))
    assert len(asked) == 1, "no connection, no fallback connect"
