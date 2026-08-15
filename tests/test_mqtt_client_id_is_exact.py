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
                continue          # comments explain the defect; they are not it
            for pat in patterns:
                if pat.search(line):
                    offenders.append(f"{path.name}:{n}: {line.strip()[:90]}")
    assert not offenders, (
        "an MQTT client id is being built by appending to the registered "
        "mqttClientId; the broker refuses that connect with rc=4 and the "
        "caller sees silence:\n  " + "\n  ".join(offenders)
    )


def test_the_attribute_read_prefers_the_shared_connection():
    """Riding the persistent connection is what avoids both failure modes.

    It is the only identity the broker accepts, and using it needs no second
    connect - so no refusal, and no evicting whoever already holds the id.
    """
    src = (SRC / "camera" / "client.py").read_text()
    i = src.index("async def async_get_camera_attributes")
    j = src.index("async def ", i + 10)
    body = src[i:j]
    assert "_get_persistent_mqtt()" in body, (
        "the attribute read must prefer the shared persistent connection"
    )
    assert "_resolve_persistent_mqtt()" not in body, (
        "preferring the shared connection must not be gated on the env var - "
        "off by default means the doomed fallback becomes the normal path"
    )
