"""Minimal async client for a go2rtc server's REST API.

Lets the camera integration **prefer go2rtc** when it's reachable - register a
camera's local serve URL as a go2rtc stream so go2rtc handles low-latency
WebRTC delivery and native H.264/H.265 depacketization - and **fall back** to
the plain ffmpeg serve (e.g. Home Assistant's built-in HLS) when go2rtc is
absent.

go2rtc REST API (v1.x), verified against go2rtc 1.9.9:

==========================  ============================================
``GET /api``                availability probe (200 + JSON info)
``GET /api/streams``        list -> ``{name: {producers, consumers}}``
``PUT /api/streams?name=&src=``   add / replace a stream (200)
``DELETE /api/streams?src=<name>``  remove a stream (200)
==========================  ============================================
"""

import logging
from typing import Optional

import aiohttp

_LOGGER = logging.getLogger(__name__)

# go2rtc is co-located with Home Assistant by default; HA exposes its API here.
DEFAULT_BASE_URL = "http://127.0.0.1:1984"
DEFAULT_RTSP_PORT = 8554


class Go2rtcClient:
    """Thin async wrapper over the go2rtc REST API (best-effort, never raises)."""

    def __init__(
        self,
        session: aiohttp.ClientSession,
        base_url: str = DEFAULT_BASE_URL,
        timeout: float = 5.0,
    ) -> None:
        self._session = session
        self._base = base_url.rstrip("/")
        self._timeout = aiohttp.ClientTimeout(total=timeout)

    @property
    def base_url(self) -> str:
        return self._base

    async def available(self) -> bool:
        """True if the go2rtc API answers ``GET /api`` with 200."""
        try:
            async with self._session.get(
                f"{self._base}/api", timeout=self._timeout
            ) as resp:
                return resp.status == 200
        except Exception as exc:
            _LOGGER.debug("go2rtc: not available at %s (%s)", self._base, exc)
            return False

    async def list_streams(self) -> dict:
        """Return the current streams dict (empty on error)."""
        try:
            async with self._session.get(
                f"{self._base}/api/streams", timeout=self._timeout
            ) as resp:
                if resp.status == 200:
                    return await resp.json()
                _LOGGER.debug("go2rtc: list_streams http=%s", resp.status)
        except Exception as exc:
            _LOGGER.debug("go2rtc: list_streams error: %s", exc)
        return {}

    async def has_stream(self, name: str) -> bool:
        return name in (await self.list_streams())

    async def ensure_stream(self, name: str, source: str) -> bool:
        """Register (or replace) ``name`` -> ``source``. Returns True on 200."""
        try:
            async with self._session.put(
                f"{self._base}/api/streams",
                params={"name": name, "src": source},
                timeout=self._timeout,
            ) as resp:
                if resp.status == 200:
                    _LOGGER.info("go2rtc: registered stream %r -> %s", name, source)
                    return True
                _LOGGER.warning(
                    "go2rtc: add stream %r failed http=%s", name, resp.status
                )
        except Exception as exc:
            _LOGGER.warning("go2rtc: add stream %r error: %s", name, exc)
        return False

    async def remove_stream(self, name: str) -> bool:
        """Remove ``name`` from go2rtc. Returns True on 200."""
        try:
            async with self._session.delete(
                f"{self._base}/api/streams",
                params={"src": name},
                timeout=self._timeout,
            ) as resp:
                return resp.status == 200
        except Exception as exc:
            _LOGGER.debug("go2rtc: remove stream %r error: %s", name, exc)
        return False

    def rtsp_url(self, name: str, rtsp_port: int = DEFAULT_RTSP_PORT) -> str:
        """The RTSP pull URL go2rtc serves a registered stream at."""
        host = self._base.split("://", 1)[-1].split("/", 1)[0].split(":", 1)[0]
        return f"rtsp://{host}:{rtsp_port}/{name}"


async def prefer_go2rtc(
    session: aiohttp.ClientSession,
    name: str,
    source: str,
    base_url: str = DEFAULT_BASE_URL,
    rtsp_port: int = DEFAULT_RTSP_PORT,
) -> Optional[str]:
    """Prefer-go2rtc-with-ffmpeg-fallback decision.

    If go2rtc is reachable at ``base_url``, register ``source`` under ``name``
    and return the go2rtc RTSP pull URL (low-latency / native codec path).
    If go2rtc is unavailable or registration fails, return ``None`` so the
    caller falls back to serving ``source`` directly (e.g. HA HLS).
    """
    client = Go2rtcClient(session, base_url)
    if not await client.available():
        _LOGGER.debug("go2rtc unavailable - falling back to direct serve for %r", name)
        return None
    if not await client.ensure_stream(name, source):
        return None
    return client.rtsp_url(name, rtsp_port)
