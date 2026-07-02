"""Unit tests for the persistent-MQTT flag wiring (Phase 1).

Locks the opt/env precedence of ``_resolve_persistent_mqtt`` (per-camera kwarg
wins over AIDOT_PERSISTENT_MQTT env, default off) and the basic reuse semantics
of the ``_PersistentMqtt`` collector routing. The live connection behaviour is
validated on hardware, not here.
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot.camera.client as cc
from aidot.camera.protocol import _PersistentMqtt

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "_resolve_persistent_mqtt" in v.__dict__)


def _cam():
    return _CAM.__new__(_CAM)


def test_default_on(monkeypatch):
    # default ON (matches the app's single persistent connection)
    monkeypatch.delenv("AIDOT_PERSISTENT_MQTT", raising=False)
    assert _cam()._resolve_persistent_mqtt() is True


def test_env_disables(monkeypatch):
    for val in ("0", "false", "FALSE", "no", "off", " Off "):
        monkeypatch.setenv("AIDOT_PERSISTENT_MQTT", val)
        assert _cam()._resolve_persistent_mqtt() is False, val


def test_env_truthy_or_unknown_stays_on(monkeypatch):
    for val in ("1", "true", "yes", "on", "anything"):
        monkeypatch.setenv("AIDOT_PERSISTENT_MQTT", val)
        assert _cam()._resolve_persistent_mqtt() is True, val


def test_kwarg_off_wins_over_default(monkeypatch):
    # an explicit opt=False (e.g. user turned the HA toggle off) overrides default-on
    monkeypatch.delenv("AIDOT_PERSISTENT_MQTT", raising=False)
    cam = _cam()
    cam._persistent_mqtt_opt = False
    assert cam._resolve_persistent_mqtt() is False


def test_collector_routing_fans_out_messages():
    # _on_message must fan every message out to every registered collector queue,
    # so concurrent request()s each see the traffic (matching is per-collector).
    import queue
    pm = _PersistentMqtt("wss://h:8443/mqtt", "u", "p", "cid")
    q1, q2 = queue.Queue(), queue.Queue()
    pm._collectors = [q1, q2]

    class _Msg:
        topic = "iot/v1/cb/dev/x"
        payload = b'{"k":1}'
    pm._on_message(None, None, _Msg())
    assert q1.get_nowait() == ("iot/v1/cb/dev/x", '{"k":1}')
    assert q2.get_nowait() == ("iot/v1/cb/dev/x", '{"k":1}')


def test_subscriptions_tracked_for_replay():
    # subscribe topics must be remembered so they can be replayed on reconnect
    pm = _PersistentMqtt("wss://h:8443/mqtt", "u", "p", "cid")
    pm._subscribe_sync(["a/#", "b/#", "a/#"])  # no client yet -> just tracked
    assert pm._subs == {"a/#", "b/#"}
