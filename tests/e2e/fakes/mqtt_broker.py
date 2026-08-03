"""A real MQTT broker on 127.0.0.1, speaking websockets like the vendor's.

The library connects with paho ``transport="websockets"`` and derives TLS/port
from the URL scheme, so a plain ``ws://127.0.0.1:PORT/mqtt`` (handed over via
the AIDOT_MQTT_URL seam) needs no other change to redirect the whole signaling
plane here.
"""
import asyncio
import contextlib
import socket

from amqtt.broker import Broker


def free_port() -> int:
    """An ephemeral port the OS just handed back (bind-0 then release)."""
    s = socket.socket()
    try:
        s.bind(("127.0.0.1", 0))
        return s.getsockname()[1]
    finally:
        s.close()


class FakeBroker:
    """amqtt broker with a websocket listener, anonymous auth, no $SYS."""

    def __init__(self, port: int | None = None) -> None:
        self.port = port or free_port()
        self.url = f"ws://127.0.0.1:{self.port}/mqtt"
        self._broker: Broker | None = None

    async def start(self) -> "FakeBroker":
        self._broker = Broker({
            "listeners": {
                "default": {
                    "type": "ws",
                    "bind": f"127.0.0.1:{self.port}",
                    "max_connections": 64,
                },
            },
            # Declare the plugin set explicitly.  amqtt's default EntryPoint
            # discovery ALSO loads FileAuthPlugin, which vetoes every login when
            # no password file is configured - and the broker requires every
            # auth plugin to pass, so anonymous connections are refused with no
            # CONNACK at all.
            "plugins": {
                "amqtt.plugins.authentication.AnonymousAuthPlugin": {
                    "allow_anonymous": True,
                },
            },
        })
        await self._broker.start()
        return self

    async def stop(self) -> None:
        if self._broker is not None:
            with contextlib.suppress(Exception):
                await asyncio.wait_for(self._broker.shutdown(), timeout=10)
            self._broker = None

    async def __aenter__(self) -> "FakeBroker":
        return await self.start()

    async def __aexit__(self, *_exc) -> None:
        await self.stop()
