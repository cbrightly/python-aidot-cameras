"""The camera side of the AiDot MQTT signaling contract.

``FakeCameraSignaling`` subscribes to the topics a real camera watches, decodes
the app's requests, and emits the responses the firmware would - matching the
field-level details the client actually keys on:

- ``livePlayResp`` is matched by the client on the echoed **peerid**, not devId
  (docs/CAMERAS.md:46-63; matching on devId is a bug that made the wait always
  time out).
- publishes go to ``iot/v1/s/{userId}/IPC/...``; responses come back on
  ``iot/v1/c/{userId}/...``, routed by the ``devId`` in the body.
- terminal acks -50002 / -50015 mean "camera busy" and must not be retried.
- role-reversal models echo our offer back as their OWN ``webrtcReq`` and wait
  for us to answer with ``webrtcResp``.

Subclasses (sdes_camera / dtls_camera) add the media plane.
"""
import json
import random
import threading
import time

import paho.mqtt.client as paho


class FakeCameraSignaling:
    """One fake camera on the fake broker."""

    #: set True to answer webrtcReq with a terminal busy ack instead of media
    busy_ack: int | None = None
    #: role-reversal firmware (A001064): camera re-offers instead of answering
    role_reversal: bool = False
    #: require a wake call before answering livePlayReq (battery models)
    require_wake: bool = False

    def __init__(self, broker_url: str, *, device_id: str, user_id: str,
                 client_id: str | None = None) -> None:
        self.broker_url = broker_url
        self.device_id = device_id
        self.user_id = user_id
        self.client_id = client_id or f"fakecam-{device_id}"
        self.received: list[tuple[str, dict]] = []
        self.published: list[tuple[str, dict]] = []
        self.woken = threading.Event()
        self._client: paho.Client | None = None
        self._connected = threading.Event()
        self._lock = threading.Lock()

    # -- lifecycle ---------------------------------------------------------- #

    async def start(self, timeout: float = 10.0) -> "FakeCameraSignaling":
        """Connect to the fake broker.

        Async because the broker runs on the caller's event loop: a blocking
        wait here would starve the very coroutine that has to complete our
        handshake.
        """
        import asyncio
        from urllib.parse import urlparse

        parsed = urlparse(self.broker_url)
        client = paho.Client(
            callback_api_version=paho.CallbackAPIVersion.VERSION2,
            client_id=self.client_id,
            transport="websockets",
        )
        client.ws_set_options(path=parsed.path or "/mqtt")
        client.on_connect = self._on_connect
        client.on_message = self._on_message
        # connect_async + loop_start, never the blocking connect(): the fake
        # broker runs on the caller's event loop, and paho's synchronous
        # handshake would block that loop against the broker that has to answer
        # it.  paho's own network thread has no such conflict.
        client.connect_async(parsed.hostname, parsed.port, keepalive=30)
        client.loop_start()
        self._client = client
        deadline = asyncio.get_running_loop().time() + timeout
        while not self._connected.is_set():
            if asyncio.get_running_loop().time() > deadline:
                raise RuntimeError("fake camera could not reach the fake broker")
            await asyncio.sleep(0.02)
        # Let the SUBSCRIBE issued in on_connect reach the broker before the
        # test starts publishing at us.
        await asyncio.sleep(0.2)
        return self

    def stop(self) -> None:
        if self._client is not None:
            self._client.loop_stop()
            try:
                self._client.disconnect()
            except Exception:
                pass
            self._client = None

    async def __aenter__(self) -> "FakeCameraSignaling":
        return await self.start()

    async def __aexit__(self, *_exc) -> None:
        self.stop()

    # -- plumbing ----------------------------------------------------------- #

    def _on_connect(self, client, _ud, _flags, _rc, _props=None) -> None:
        # A real camera watches the account's IPC request channel.
        client.subscribe(f"iot/v1/s/{self.user_id}/#")
        client.subscribe(f"iot/v1/s/{self.device_id}/#")
        self._connected.set()

    def _publish(self, method: str, payload: dict, *, topic: str | None = None,
                 ack: dict | None = None) -> None:
        body = {
            "method": method,
            "service": "IPC",
            "devId": self.device_id,
            "srcAddr": f"0.{self.device_id}",
            "seq": f"cam{random.randint(1000000, 9999999)}",
            "tst": int(time.time() * 1000),
            "payload": {"devId": self.device_id, "dstAddr": self.user_id, **payload},
        }
        if ack is not None:
            # Ack codes ride in a TOP-LEVEL "ack" object, not the payload -
            # _terminal_webrtc_ack() reads msg["ack"]["code"].
            body["ack"] = ack
        topic = topic or f"iot/v1/c/{self.user_id}/IPC/{method}"
        self.published.append((topic, body))
        assert self._client is not None
        self._client.publish(topic, json.dumps(body))

    def _on_message(self, _client, _ud, msg) -> None:
        try:
            body = json.loads(msg.payload.decode())
        except Exception:
            return
        with self._lock:
            self.received.append((msg.topic, body))
        method = body.get("method") or ""
        inner = body.get("payload") or {}
        # Only react to traffic aimed at this camera.
        if inner.get("devId") not in (None, self.device_id) and \
                body.get("devId") not in (None, self.device_id):
            return
        try:
            self.handle(method, inner, body)
        except Exception:  # a fake that throws must not wedge the paho thread
            import traceback
            traceback.print_exc()

    def methods_received(self) -> list[str]:
        with self._lock:
            return [b.get("method") or "" for _t, b in self.received]

    # -- the contract ------------------------------------------------------- #

    def handle(self, method: str, inner: dict, body: dict) -> None:
        if method == "lowPowerActiveStateReq":
            self.woken.set()
            self._publish("lowPowerActiveStateResp", {"result": 1})
            return

        if method == "getIceConfigReq":
            # The client unwraps {app: [...], dev: [...]} (the Arnoo shape).
            # Empty lists = no STUN/TURN, which is what a hermetic run wants.
            self._publish("getIceConfigResp", {"app": [], "dev": []})
            return

        if method == "livePlayReq":
            if self.require_wake and not self.woken.is_set():
                # Asleep battery camera: silence until woken over HTTP/MQTT.
                return
            peer_id = inner.get("peerid")
            # The client matches this on the echoed peerid (NOT devId).
            self._publish("livePlayResp", {"peerid": peer_id, "result": 0})
            return

        if method == "webrtcReq":
            self.on_webrtc_req(inner, body)
            return

        if method == "iceCandidateReq":
            self.on_ice_candidate(inner, body)
            return

    def on_webrtc_req(self, inner: dict, body: dict) -> None:
        """Answer the app's offer.  Subclasses add the media plane."""
        if self.busy_ack is not None:
            # Terminal ack: the client must raise AidotCameraBusy, not retry.
            self._publish(
                "webrtcResp",
                {"peerid": inner.get("peerid")},
                ack={"code": self.busy_ack, "desc": "max streams reached"},
            )
            return
        answer_sdp = self.build_answer(inner)
        if answer_sdp is None:
            return
        if self.role_reversal:
            # A001064: the camera re-offers by echoing a webrtcReq of its own;
            # the client must reply with a webrtcResp before ICE proceeds.
            self._publish("webrtcReq", {
                "peerid": inner.get("peerid"),
                "offer": {"type": "offer", "sdp": answer_sdp},
                "trackId": 0,
            })
            return
        self._publish("webrtcResp", {
            "peerid": inner.get("peerid"),
            "offer": {"type": "answer", "sdp": answer_sdp},
            "trackId": 0,
        })

    def build_answer(self, inner: dict) -> str | None:
        """Return the answer SDP for the app's offer (None = stay silent)."""
        return None

    def on_ice_candidate(self, inner: dict, body: dict) -> None:
        return
