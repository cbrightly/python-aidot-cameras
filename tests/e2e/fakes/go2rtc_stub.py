"""Stub of the go2rtc HTTP API, with a full request log.

Five distinct go2rtc regressions shipped in one week (a 2 req/s/camera storm
that took go2rtc down, a stream registered as its OWN source, and an
idle-release oracle that counted go2rtc's producer connection as a viewer).
All three are observable only from the go2rtc side, which is what this records.
"""
import time

from aiohttp import web


class FakeGo2rtc:
    def __init__(self) -> None:
        self.requests: list[tuple[float, str, str, dict]] = []
        self.streams: dict[str, list[str]] = {}
        #: what /api/streams reports as consumers, steerable per test
        self.consumers: dict[str, list[dict]] = {}
        #: what /api/streams reports as producers (go2rtc itself pulling)
        self.producers: dict[str, list[dict]] = {}
        self._runner: web.AppRunner | None = None
        self.port: int | None = None

    async def start(self) -> "FakeGo2rtc":
        app = web.Application()
        app.router.add_get("/api/streams", self._get_streams)
        app.router.add_put("/api/streams", self._put_stream)
        app.router.add_delete("/api/streams", self._delete_stream)
        app.router.add_route("*", "/{tail:.*}", self._catchall)
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

    async def __aenter__(self) -> "FakeGo2rtc":
        return await self.start()

    async def __aexit__(self, *_exc) -> None:
        await self.stop()

    # -- handlers ----------------------------------------------------------- #

    def _log(self, request: web.Request) -> None:
        self.requests.append(
            (time.monotonic(), request.method, request.path, dict(request.query))
        )

    async def _get_streams(self, request: web.Request) -> web.Response:
        self._log(request)
        name = request.query.get("src")
        if name is not None:
            return web.json_response({
                "producers": self.producers.get(name, []),
                "consumers": self.consumers.get(name, []),
            })
        return web.json_response({
            n: {"producers": self.producers.get(n, []),
                "consumers": self.consumers.get(n, [])}
            for n in self.streams
        })

    async def _put_stream(self, request: web.Request) -> web.Response:
        self._log(request)
        name = request.query.get("name") or ""
        src = request.query.get("src") or ""
        self.streams.setdefault(name, []).append(src)
        return web.json_response({})

    async def _delete_stream(self, request: web.Request) -> web.Response:
        self._log(request)
        self.streams.pop(request.query.get("src") or "", None)
        return web.json_response({})

    async def _catchall(self, request: web.Request) -> web.Response:
        self._log(request)
        return web.json_response({})

    # -- assertions helpers ------------------------------------------------- #

    def request_count(self, path_needle: str = "") -> int:
        return sum(1 for _t, _m, p, _q in self.requests if path_needle in p)

    def peak_rate_per_s(self, window_s: float = 1.0) -> float:
        """Highest request count observed in any ``window_s`` sliding window."""
        stamps = sorted(t for t, _m, _p, _q in self.requests)
        peak = 0
        for i, t0 in enumerate(stamps):
            n = sum(1 for t in stamps[i:] if t - t0 <= window_s)
            peak = max(peak, n)
        return peak / window_s

    def sources_for(self, name: str) -> list[str]:
        return self.streams.get(name, [])
