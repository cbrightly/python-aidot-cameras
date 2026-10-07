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
``AIDOT_HLS_TS_PORT`` sets it, and only to a request carrying a secret made for
that router (``?auth=``, so another process on the host cannot read a camera
from its port and name). Home Assistant gets the URL in-process, so any free
port does, and a camera's URL then stays the same while the router runs - which
matters because Home Assistant fixes a stream's source when it creates it. The
router is stopped only when the integration's last entry unloads (``shutdown``);
the URLs it handed out die with it.
"""

from __future__ import annotations

import logging
import os
import tempfile
import threading
from typing import Callable, Dict, Optional

from .aac_track import publish_aac_enabled
from .rtsp_publish import direct_publish_enabled
from .ts_fanout import TsRouter
from .ts_tee import TsSession, TsTee

_LOGGER = logging.getLogger(__name__)

ENV_HLS_DIRECT_TS = "AIDOT_HLS_DIRECT_TS"
ENV_HLS_TS_PORT = "AIDOT_HLS_TS_PORT"
#: In the library's state directory (``AIDOT_SPROP_DIR``): the listener's URL
#: for any camera, ``http://127.0.0.1:<port>/{name}.ts?auth=<secret>``, where
#: ``{name}`` is ``aidot_<first 12 of the device id>``.
URL_FILE = "hls-ts-url"

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
    """The camera's path on the listener (its URL adds the listener's secret)."""
    return "/%s.ts" % name


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
        _spawn(lambda: _write_url_file(r))
    return _router


def _url_file() -> str:
    from .protocol import _sprop_dir

    return os.path.join(_sprop_dir(), URL_FILE)


def _spawn(fn: Callable[[], None]) -> None:
    """Run ``fn`` on its own thread (the caller may be Home Assistant's loop)."""
    threading.Thread(target=fn, name="hls-ts-url-file", daemon=True).start()


#: Serializes writing and removing the URL file.
_file_lock = threading.Lock()


def _write_url_file(router: TsRouter) -> None:
    """Leave the listener's URL (with its secret) for the owner's tools.

    A raw capture of a camera's TS is the reference clock for checking that a
    recording kept its sound and picture in step; the secret otherwise hides it
    from everything but Home Assistant. Written beside the library's other
    state, owner-only, and removed when the listener stops - and not written at
    all if it stopped first. Never fatal.
    """
    with _file_lock:
        if _router is not router:
            return
        path = _url_file()
        tmp = None
        try:
            os.makedirs(os.path.dirname(path), exist_ok=True)
            fd, tmp = tempfile.mkstemp(  # owner-only, a name no one else holds
                dir=os.path.dirname(path), prefix="." + URL_FILE + "."
            )
            with os.fdopen(fd, "w") as fh:
                fh.write(router.url("/{name}.ts"))
            os.replace(tmp, path)
            tmp = None
        except OSError:
            _LOGGER.debug("in-sync HLS: could not leave the URL file", exc_info=True)
        finally:
            if tmp is not None:
                try:
                    os.remove(tmp)
                except OSError:
                    pass


def _remove_url_file(router: TsRouter) -> None:
    """Remove the URL file if it is ``router``'s (a newer listener's stays)."""
    with _file_lock:
        path = _url_file()
        try:
            with open(path) as fh:
                mine = fh.read() == router.url("/{name}.ts")
            if mine:
                os.remove(path)
        except OSError:
            pass


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
        _remove_url_file(router)
