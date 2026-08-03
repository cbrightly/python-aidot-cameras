"""Fake AiDot cloud HTTP API (smarthome + platform), on 127.0.0.1.

Serves the endpoints the camera layer calls during a stream open.  Pointed at
via the AIDOT_SMARTHOME_URL_TEMPLATE / AIDOT_API_BASE_TEMPLATE seams, so the
library's own ad-hoc aiohttp sessions reach it with no patching.

Every request is recorded in ``self.requests`` so tests can assert on call
ORDER (battery wake before signaling) and call VOLUME (no request storms).
"""
import asyncio
import time

from aiohttp import web


class FakeCloud:
    """Both cloud hosts in one app; each handler returns the real wire shape."""

    def __init__(self, *, mqtt_url: str, public_ip: str = "203.0.113.7") -> None:
        self.mqtt_url = mqtt_url
        self.public_ip = public_ip
        self.requests: list[tuple[float, str, str]] = []  # (monotonic, method, path)
        # Tests mutate these to steer the fake cloud's answers.
        self.ice_config: dict = {"app": [], "dev": []}
        self.device_user_info: list[dict] = []
        self.wake_delay_s: float = 0.0
        self._runner: web.AppRunner | None = None
        self.port: int | None = None

    # -- lifecycle ---------------------------------------------------------- #

    async def start(self) -> "FakeCloud":
        app = web.Application(middlewares=[self._record])
        app.router.add_route("*", "/{tail:.*}", self._dispatch)
        self._runner = web.AppRunner(app)
        await self._runner.setup()
        site = web.TCPSite(self._runner, "127.0.0.1", 0)
        await site.start()
        self.port = self._runner.addresses[0][1]
        return self

    async def stop(self) -> None:
        if self._runner is not None:
            await self._runner.cleanup()
            self._runner = None

    @property
    def base_url(self) -> str:
        return f"http://127.0.0.1:{self.port}"

    async def __aenter__(self) -> "FakeCloud":
        return await self.start()

    async def __aexit__(self, *_exc) -> None:
        await self.stop()

    # -- request accounting ------------------------------------------------- #

    @web.middleware
    async def _record(self, request: web.Request, handler):
        self.requests.append((time.monotonic(), request.method, request.path))
        return await handler(request)

    def paths(self) -> list[str]:
        """Just the paths, in arrival order (for ordering assertions)."""
        return [p for _t, _m, p in self.requests]

    def count(self, needle: str) -> int:
        return sum(1 for p in self.paths() if needle in p)

    # -- routing ------------------------------------------------------------ #

    async def _dispatch(self, request: web.Request) -> web.Response:
        path = request.path
        if path.endswith("/commonController/getServerUrlConfig"):
            return web.json_response({
                "code": 0,
                "data": {
                    "mqttServerUrl": self.mqtt_url,
                    "ip": self.public_ip,
                    "mqttUser": "fake-mqtt-user",
                    "mqttPassword": "fake-mqtt-password",
                },
            })
        if path.endswith("/user/getUser"):
            return web.json_response({
                "code": 0,
                "data": {"mqttUser": "fake-mqtt-user",
                         "mqttPassword": "fake-mqtt-password"},
            })
        if path.endswith("/commons/userConfig"):
            return web.json_response({
                "code": 0,
                "data": {"mqttClientId": "app-user-1",
                         "mqttPassword": "fake-mqtt-password"},
            })
        if path.endswith("/devices/batchGetDeviceUserInfo"):
            return web.json_response({"code": 0, "data": self.device_user_info})
        if "/v29/api/webrtc/iceConfig" in path:
            return web.json_response(self.ice_config)
        if path.endswith("/lowPowerActiveState"):
            # Battery wake.  Optional delay lets a test prove the client waits.
            if self.wake_delay_s:
                await asyncio.sleep(self.wake_delay_s)
            return web.json_response({"code": 0, "data": {"result": 1}})
        if path.endswith("/liveStreamParam"):
            return web.json_response({"code": 0, "data": {"kvs": "unused-by-default"}})
        if path.endswith("/setKeepAliveTime"):
            return web.json_response({"code": 0, "data": {}})
        # Unknown endpoints answer benignly: an unmodelled call must not crash
        # the client under test, but IS visible in self.requests.
        return web.json_response({"code": 0, "data": {}})
