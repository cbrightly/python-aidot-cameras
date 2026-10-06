"""In-sync HLS: a library-muxed MPEG-TS per camera for Home Assistant's stream worker.

When ``AIDOT_HLS_DIRECT_TS`` is on, Home Assistant's HLS view and recordings of
a DTLS camera read the camera's MPEG-TS from a loopback ``TsRouter`` instead of
go2rtc's RTSP. go2rtc re-bases each track for each consumer, so a recording or
view that joins a running stream had its sound 0.1-0.75 s late, differently
every time (measured 2026-10-03); this TS carries one clock, so any joiner is in
step. WebRTC is unchanged.

It is fed by the direct publisher (so ``AIDOT_DIRECT_PUBLISH`` must be on) and
carries the publisher's AAC track (so ``AIDOT_PUBLISH_AAC`` must be on). DTLS
cameras hand it access units; SDES cameras' RTP is rebuilt into access units
(``rtp_h264``). An SDES camera gets a URL only when every session is sure to
feed it - see ``CameraMixin._hls_ts_eligible``.

One router serves every camera from one port, picked at random unless
``AIDOT_HLS_TS_PORT`` sets it, under a secret path prefix made for that router
(so another process on the host cannot read a camera from its port and name): Home Assistant gets the URL in-process, so any
free port does, and a camera's URL then stays the same for the whole process -
which matters because Home Assistant fixes a stream's source when it creates it.
"""

from __future__ import annotations

import logging
import os
import threading
from typing import Dict, Optional

from .aac_track import publish_aac_enabled
from .rtsp_publish import direct_publish_enabled
from .ts_fanout import TsRouter
from .ts_tee import TsSession, TsTee

_LOGGER = logging.getLogger(__name__)

ENV_HLS_DIRECT_TS = "AIDOT_HLS_DIRECT_TS"
ENV_HLS_TS_PORT = "AIDOT_HLS_TS_PORT"

_lock = threading.Lock()
_router: Optional[TsRouter] = None
_tees: Dict[str, TsTee] = {}


def enabled() -> bool:
    """The option, and both things it is built on, are on."""
    on = os.environ.get(ENV_HLS_DIRECT_TS, "").strip().lower() in (
        "1",
        "true",
        "yes",
        "on",
    )
    return on and direct_publish_enabled() and publish_aac_enabled()


def _path(name: str) -> str:
    """The camera's path on the listener: the listener's secret, then its name."""
    return "/%s/%s.ts" % (_get_router().token, name)


def _get_router() -> TsRouter:
    global _router
    if _router is None:
        try:
            port = int(os.environ.get(ENV_HLS_TS_PORT, "0") or 0)
        except ValueError:
            port = 0
        r = TsRouter(port)
        r.start()
        _router = r
        _LOGGER.info("in-sync HLS: serving camera TS on 127.0.0.1:%d", r.port)
    return _router


def tee_for(name: str, device_id: str = "?") -> TsTee:
    """The camera's tee (and channel), created and started on first use."""
    with _lock:
        tee = _tees.get(name)
        if tee is not None and not tee.is_running():
            # Its mux thread stopped (a failed write, a PyAV error): replace it
            # on the same channel, so the camera's URL - which Home Assistant
            # holds - starts carrying media again.
            _LOGGER.warning("camera %s: restarting its in-sync HLS mux", device_id)
            tee.close()
            tee = None
        if tee is None:
            tee = TsTee(_get_router().channel(_path(name)), device_id=device_id)
            tee.start()
            _tees[name] = tee
        return tee


def url_for(name: str) -> str:
    with _lock:
        return _get_router().url(_path(name))


def session_for(name: str, device_id: str = "?") -> TsSession:
    """A new session's input for the camera's tee (one per camera connect)."""
    return tee_for(name, device_id).session()


def consumers(name: str) -> int:
    """How many consumers are reading the camera's TS now (0 if none exist)."""
    with _lock:
        tee = _tees.get(name)
    return tee.stats()["consumers"] if tee is not None else 0


def shutdown() -> None:
    """Stop every tee and the router (process teardown and tests)."""
    global _router
    with _lock:
        tees, router = list(_tees.values()), _router
        _tees.clear()
        _router = None
    for tee in tees:
        tee.close()
    if router is not None:
        router.close()
