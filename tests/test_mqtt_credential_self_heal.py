"""The broker rotates the MQTT password on every account login and allows one
connection at a time, so a cached password goes stale whenever anything else
logs in (the phone app is enough).  paho would retry the dead password forever,
so a credential refusal has to invalidate the cache instead.
"""
import aidot_cameras.camera.protocol as proto


def _pm(on_auth_failure=None):
    return proto._PersistentMqtt(
        "wss://broker.example:8443/mqtt", "user", "stale-pwd", "app-user",
        on_auth_failure=on_auth_failure,
    )


def test_auth_refusal_codes_cover_mqtt3_and_mqtt5():
    # 4/5 are MQTT 3.1.1 bad-user-pass / not-authorized; 134/135 are the MQTT 5
    # equivalents (0x86/0x87) - the AiDot broker answers with 134.
    for rc in (4, 5, 134, 135):
        assert rc in proto._PersistentMqtt._AUTH_REFUSAL_RCS, rc


def test_credential_refusal_invokes_the_callback_once():
    seen = []
    pm = _pm(on_auth_failure=seen.append)
    pm._on_connect(None, None, {}, 134)
    pm._on_connect(None, None, {}, 134)   # repeated refusal must not re-notify
    assert seen == [134]


def test_transient_refusal_does_not_invalidate_credentials():
    # rc=3 is "server unavailable" - retrying the same password is correct.
    seen = []
    pm = _pm(on_auth_failure=seen.append)
    pm._on_connect(None, None, {}, 3)
    assert seen == []


def test_successful_connect_does_not_invalidate_credentials():
    seen = []
    pm = _pm(on_auth_failure=seen.append)
    pm._on_connect(None, None, {}, 0)
    assert seen == []
    assert pm._connected.is_set()


def test_callback_exception_is_contained():
    def boom(rc):
        raise RuntimeError("callback exploded")

    pm = _pm(on_auth_failure=boom)
    pm._on_connect(None, None, {}, 134)   # must not propagate into paho's loop
