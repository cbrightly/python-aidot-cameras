"""Unit tests for the persistent MQTT connection, the only MQTT transport.

``AIDOT_PERSISTENT_MQTT`` used to switch it off in favour of a connection per
operation. That path connected with the account's one clientId and so evicted
the persistent connection; it is gone, and the knob is accepted, ignored and
warned about once. Also locks the basic reuse semantics of the
``_PersistentMqtt`` collector routing. The live connection behaviour is
validated on hardware, not here.
"""

import logging
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.client as cc
from aidot_cameras.camera.protocol import _PersistentMqtt


def _warnings(caplog):
    return [
        r
        for r in caplog.records
        if r.levelno == logging.WARNING and "AIDOT_PERSISTENT_MQTT" in r.getMessage()
    ]


def test_turning_the_knob_off_warns_once_and_changes_nothing(monkeypatch, caplog):
    monkeypatch.setattr(cc, "_PERSISTENT_MQTT_KNOB_WARNED", False)
    monkeypatch.setenv("AIDOT_PERSISTENT_MQTT", "0")
    with caplog.at_level(logging.WARNING):
        cc._warn_persistent_mqtt_knob()
        cc._warn_persistent_mqtt_knob()
    assert len(_warnings(caplog)) == 1


def test_the_knob_left_on_or_unset_says_nothing(monkeypatch, caplog):
    monkeypatch.setattr(cc, "_PERSISTENT_MQTT_KNOB_WARNED", False)
    with caplog.at_level(logging.WARNING):
        monkeypatch.delenv("AIDOT_PERSISTENT_MQTT", raising=False)
        cc._warn_persistent_mqtt_knob()
        for val in ("1", "true", "on", "anything"):
            monkeypatch.setenv("AIDOT_PERSISTENT_MQTT", val)
            cc._warn_persistent_mqtt_knob()
    assert _warnings(caplog) == []


def test_there_is_no_switch_left_to_read():
    # The resolver and the per-operation session it chose are gone.
    cam_classes = [v for v in vars(cc).values() if isinstance(v, type)]
    assert not any("_resolve_persistent_mqtt" in c.__dict__ for c in cam_classes)
    assert not hasattr(cc, "_mqtt_session_with_status")


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
