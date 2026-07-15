"""Transient-reconnect resilience of the per-stream MQTT signaling session.

_mqtt_session_sync runs one paho session for the (up to 3600s) lifetime of a
stream on the non-persistent transport. A transient broker drop must NOT end the
receive loop - paho's loop_start auto-reconnects and _on_connect re-subscribes
(clean_session retains nothing). Only a disconnect that happens before the first
successful connect should end the session. Driven with a fake paho client.
"""
import queue
import threading
import time

import aidot.camera.protocol as proto


class _FakeMsg:
    def __init__(self, topic, payload):
        self.topic = topic
        self.payload = payload


def _install_fake_client(monkeypatch, driver):
    """Patch paho's Client with a fake whose loop_start() runs ``driver(client)``
    in a daemon thread, so it can fire callbacks while the receive loop runs."""
    class _FakeClient:
        def __init__(self, *a, **k):
            self.subs = []
            self.on_connect = self.on_message = self.on_disconnect = self.on_log = None
        def ws_set_options(self, **k): pass
        def username_pw_set(self, *a, **k): pass
        def tls_set_context(self, *a, **k): pass
        def connect(self, *a, **k): pass
        def subscribe(self, topic): self.subs.append(topic)
        def publish(self, *a, **k): pass
        def loop_start(self):
            threading.Thread(target=driver, args=(self,), daemon=True).start()
        def loop_stop(self): pass
        def disconnect(self): pass

    monkeypatch.setattr("paho.mqtt.client.Client", _FakeClient)


def test_transient_disconnect_keeps_receiving_and_resubscribes(monkeypatch):
    outgoing = queue.Queue()
    seen_subs = []

    def driver(c):
        c.on_connect(c, None, {}, 0)          # first successful connect
        seen_subs.append(list(c.subs))
        c.on_disconnect(c, None, 0)           # transient drop (already connected)
        time.sleep(0.02)
        c.on_connect(c, None, {}, 0)          # paho auto-reconnect -> resubscribe
        seen_subs.append(list(c.subs))
        # Arrives only AFTER the transient drop: collected only if the receive
        # loop survived the disconnect.
        c.on_message(c, None, _FakeMsg("t/after", b"live"))
        time.sleep(0.02)
        outgoing.put_nowait(None)             # clean stop

    _install_fake_client(monkeypatch, driver)

    messages, status = proto._mqtt_session_sync(
        "wss://broker.example/mqtt", "u", "p", "cid",
        ["t/a", "t/b"], [], 5.0,
        outgoing_queue=outgoing,
    )

    assert ("t/after", "live") in messages               # survived the transient drop
    assert seen_subs[0] == ["t/a", "t/b"]                # subscribed on first connect
    assert seen_subs[1] == ["t/a", "t/b", "t/a", "t/b"]  # resubscribed on reconnect


def test_disconnect_before_connect_ends_session(monkeypatch):
    def driver(c):
        c.on_disconnect(c, None, 7)           # never connected -> end + report failure

    _install_fake_client(monkeypatch, driver)

    messages, status = proto._mqtt_session_sync(
        "wss://broker.example/mqtt", "u", "p", "cid",
        ["t/a"], [], 5.0,
    )
    assert messages == []
    assert status["connected"] is False


def test_terminal_disconnect_ends_session_before_deadline(monkeypatch):
    """A post-connect drop that never reconnects must end the session via the
    reconnect grace, not poll a dead socket until the full duration deadline."""
    monkeypatch.setattr(proto, "_MQTT_RECONNECT_GRACE", 0.15)

    def driver(c):
        c.on_connect(c, None, {}, 0)                         # connected
        c.on_message(c, None, _FakeMsg("t/before", b"x"))   # delivered pre-drop
        time.sleep(0.02)
        c.on_disconnect(c, None, 142)                       # terminal drop; NO reconnect

    _install_fake_client(monkeypatch, driver)

    t0 = time.monotonic()
    messages, status = proto._mqtt_session_sync(
        "wss://broker.example/mqtt", "u", "p", "cid",
        ["t/a"], [], 30.0,          # 30s duration - must NOT wait this long
    )
    elapsed = time.monotonic() - t0

    assert elapsed < 5.0, f"terminal disconnect should end fast, took {elapsed:.1f}s"
    assert ("t/before", "x") in messages                    # pre-drop message still collected
    assert status["disconnected_since"] is not None         # the drop was recorded


def test_reconnect_within_grace_keeps_session(monkeypatch):
    """A drop that reconnects within the grace clears disconnected_since, so the
    receive loop must NOT fail the session fast (guards the transient path)."""
    monkeypatch.setattr(proto, "_MQTT_RECONNECT_GRACE", 0.5)
    outgoing = queue.Queue()

    def driver(c):
        c.on_connect(c, None, {}, 0)
        c.on_disconnect(c, None, 7)                         # blip
        time.sleep(0.05)                                   # < grace
        c.on_connect(c, None, {}, 0)                       # recovered
        c.on_message(c, None, _FakeMsg("t/after", b"live"))
        time.sleep(0.05)
        outgoing.put_nowait(None)                          # clean stop

    _install_fake_client(monkeypatch, driver)

    messages, status = proto._mqtt_session_sync(
        "wss://broker.example/mqtt", "u", "p", "cid",
        ["t/a"], [], 30.0, outgoing_queue=outgoing,
    )
    assert ("t/after", "live") in messages                  # survived the blip
    assert status["disconnected_since"] is None             # cleared on reconnect
