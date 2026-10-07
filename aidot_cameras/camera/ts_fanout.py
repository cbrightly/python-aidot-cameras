"""Serve each camera's muxed MPEG-TS to any number of HTTP consumers.

Home Assistant's HLS stream worker reads a camera from here (see ``ts_tee``)
instead of from go2rtc's RTSP: go2rtc re-bases every track for every consumer,
so a consumer that joins a running stream gets audio 0.1-0.75 s behind the
picture, a different amount each time (measured 2026-10-03). The TS written here
carries one clock for both tracks, so any joiner stays in step.

Joining is the delicate part, learned on ``_DirectTsServer`` (protocol.py): a
consumer that starts mid-GOP references parameter sets it never received and
gets no picture. So the last PAT and PMT are cached, and a new consumer is held
until the writer signals a keyframe (``mark_keyframe``); it then receives the
tables followed by media from that keyframe on.

Unlike ``_DirectTsServer`` this serves several consumers at once, and none can
hold up the writer or another consumer: each has its own bounded buffer and
sender thread. A consumer that falls more than ``MAX_BUFFER`` behind is put back
to "wait for the next keyframe" rather than slowing anything down.
"""

from __future__ import annotations

import collections
import hmac
import logging
import secrets
import select
import socket
import threading
from typing import Deque, List, Optional
from urllib.parse import parse_qs

_LOGGER = logging.getLogger(__name__)

TS_PACKET = 188

_HTTP_OK = (
    b"HTTP/1.0 200 OK\r\n"
    b"Content-Type: video/mp2t\r\n"
    b"Cache-Control: no-cache\r\n"
    b"Connection: close\r\n\r\n"
)


class _Consumer:
    def __init__(self, sock: socket.socket) -> None:
        self.sock = sock
        self.synced = False
        self.started = False  # ever sent media (synced can be reset; this is not)
        self.chunks: Deque[bytes] = collections.deque()
        self.queued = 0
        self.dead = False
        self.cv = threading.Condition()
        self.thread: Optional[threading.Thread] = None


class TsChannel:
    """One camera's MPEG-TS, fanned out to every consumer of its path.
    ``write``/``flush``/``mark_keyframe`` make it the muxer's sink."""

    #: Bytes a consumer may fall behind before it is resynced at a keyframe.
    MAX_BUFFER = 4 * 1024 * 1024

    def __init__(self, path: str) -> None:
        self.path = path
        self._lock = threading.Lock()
        self._consumers: List[_Consumer] = []
        self._closed = threading.Event()
        self._tail = b""
        self._pat: Optional[bytes] = None
        self._pmt: Optional[bytes] = None
        self._pmt_pid: Optional[int] = None
        self._kf_pending = False
        self.resyncs = 0

    def consumer_count(self) -> int:
        with self._lock:
            return sum(1 for c in self._consumers if not c.dead)

    def started_count(self) -> int:
        """Consumers that have been sent media (not still waiting to start)."""
        with self._lock:
            return sum(1 for c in self._consumers if not c.dead and c.started)

    def pending_bytes(self) -> int:
        with self._lock:
            return sum(c.queued for c in self._consumers)

    # -- consumers ---------------------------------------------------------- #

    def add_consumer(self, sock: socket.socket) -> None:
        """Take over an accepted connection whose HTTP response is already sent."""
        c = _Consumer(sock)
        c.thread = threading.Thread(
            target=self._send_loop, args=(c,), name="ts-fanout-send", daemon=True
        )
        with self._lock:
            if self._closed.is_set():
                _close(sock)
                return
            self._consumers.append(c)
        c.thread.start()

    def _send_loop(self, c: _Consumer) -> None:
        while True:
            chunk = None
            with c.cv:
                if not c.chunks and not c.dead:
                    c.cv.wait(0.5)
                if c.dead:
                    break
                if c.chunks:
                    chunk = c.chunks.popleft()
                    c.queued -= len(chunk)
            if chunk is None:
                # Nothing to send: is the consumer still there? A send failure
                # used to be the only sign it had gone, so with no writes a
                # departed consumer stayed counted - a "viewer" keeping the
                # camera awake - for as long as the stream was quiet.
                if _peer_gone(c.sock):
                    break
                continue
            try:
                c.sock.sendall(chunk)
            except OSError:
                break
        self._drop(c)

    def _drop(self, c: _Consumer) -> None:
        with c.cv:
            c.dead = True
            c.chunks.clear()
            c.queued = 0
            c.cv.notify_all()
        with self._lock:
            if c in self._consumers:
                self._consumers.remove(c)
        _close(c.sock)

    # -- writing ------------------------------------------------------------ #

    def mark_keyframe(self) -> None:
        """The next ``write`` begins a video keyframe: a consumer can start there."""
        self._kf_pending = True

    @staticmethod
    def _pid(pkt: bytes) -> int:
        return ((pkt[1] & 0x1F) << 8) | pkt[2]

    def _learn_pmt_pid(self, pat: bytes) -> None:
        try:
            i = 4
            if pat[3] & 0x20:
                i += 1 + pat[4]
            i += 1 + pat[i]  # pointer_field
            if pat[i] != 0x00:
                return
            self._pmt_pid = ((pat[i + 10] & 0x1F) << 8) | pat[i + 11]
        except (IndexError, ValueError):
            return

    def write(self, b: bytes) -> int:
        data = self._tail + bytes(b) if self._tail else bytes(b)
        out = bytearray()
        i, n = 0, len(data)
        while i + TS_PACKET <= n:
            if data[i] != 0x47:
                j = data.find(b"\x47", i + 1)
                if j < 0:
                    i = n
                    break
                i = j
                continue
            pkt = data[i : i + TS_PACKET]
            i += TS_PACKET
            pid = self._pid(pkt)
            if pid == 0:
                self._pat = pkt
                self._learn_pmt_pid(pkt)
            elif self._pmt_pid is not None and pid == self._pmt_pid:
                self._pmt = pkt
            out += pkt
        self._tail = data[i:] if i < n else b""
        if not out:
            return len(b)  # no whole packet yet: keep any keyframe signal for it
        keyframe, self._kf_pending = self._kf_pending, False
        payload = bytes(out)
        head = (self._pat or b"") + (self._pmt or b"")
        with self._lock:
            consumers = list(self._consumers)
        for c in consumers:
            if c.dead:
                continue
            if not c.synced:
                if not keyframe:
                    continue
                self._queue(c, head + payload, start=True)
            else:
                self._queue(c, payload)
        return len(b)

    def _queue(self, c: _Consumer, chunk: bytes, start: bool = False) -> None:
        with c.cv:
            if c.dead:
                return
            if c.queued + len(chunk) > self.MAX_BUFFER:
                # Too far behind: forget what is queued and start over at the
                # next keyframe, rather than hold up the writer or the others.
                c.chunks.clear()
                c.queued = 0
                c.synced = False
                self.resyncs += 1
                return
            c.chunks.append(chunk)
            c.queued += len(chunk)
            if start:
                c.synced = c.started = True
            c.cv.notify()

    def flush(self) -> None:
        return None

    def disconnect_all(self) -> int:
        """Disconnect every consumer now; new ones are still accepted. Returns
        how many were connected."""
        with self._lock:
            consumers = list(self._consumers)
        for c in consumers:
            try:
                c.sock.shutdown(socket.SHUT_RDWR)
            except OSError:
                pass
            self._drop(c)
        return len(consumers)

    def close(self) -> None:
        """Disconnect every consumer; the channel accepts no more."""
        self._closed.set()
        with self._lock:
            consumers = list(self._consumers)
        for c in consumers:
            try:
                c.sock.shutdown(socket.SHUT_RDWR)
            except OSError:
                pass
            self._drop(c)


class TsRouter:
    """One loopback listener for every camera, routed by request path.

    A per-camera port could collide (two cameras hashing to one port served one
    camera's media to the other's viewer). One listener has no collisions, and a
    camera's URL stays the same across its sessions, which matters because Home
    Assistant fixes a stream's source URL when it creates the stream.

    Every request must carry the listener's secret as ``?auth=<token>`` (see
    ``url``); anything else gets the same 404 as an unknown path.
    """

    def __init__(self, port: int = 0, *, host: str = "127.0.0.1") -> None:
        self._port = port
        self._host = host
        #: A secret for this listener. It is loopback-only, but anything else on
        #: the host (an add-on sharing the host network) could otherwise read a
        #: camera's video from its port and its well-known stream name. It goes
        #: in the query, not the path: Home Assistant logs a stream's URL (at
        #: ERROR when it cannot open it) and masks an ``auth`` query parameter
        #: there, but nothing in the path. Never logged here.
        self.token = secrets.token_urlsafe(18)
        self._listen: Optional[socket.socket] = None
        self._lock = threading.Lock()
        self._channels: dict = {}
        self._closed = threading.Event()

    @property
    def port(self) -> int:
        return self._port

    def url(self, path: str) -> str:
        """The URL a consumer reads ``path`` from, with the listener's secret."""
        return "http://%s:%d%s?auth=%s" % (self._host, self._port, path, self.token)

    def _authorized(self, query: str) -> bool:
        given = parse_qs(query).get("auth", [""])[0]
        return hmac.compare_digest(given.encode(), self.token.encode())

    def start(self) -> None:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        s.bind((self._host, self._port))
        s.listen(16)
        s.settimeout(0.5)
        self._port = s.getsockname()[1]
        self._listen = s
        threading.Thread(
            target=self._accept_loop, name="ts-router-accept", daemon=True
        ).start()

    def channel(self, path: str) -> TsChannel:
        """The channel for ``path`` (``/<name>.ts``), created on first use."""
        with self._lock:
            ch = self._channels.get(path)
            if ch is None:
                ch = self._channels[path] = TsChannel(path)
            return ch

    def _accept_loop(self) -> None:
        while not self._closed.is_set():
            try:
                cli, _ = self._listen.accept()
            except TimeoutError:
                continue
            except OSError:
                if self._closed.is_set():
                    return
                # EMFILE, ECONNABORTED, ...: transient. Returning here used to
                # stop every camera's TS for the life of the process.
                _LOGGER.debug("ts-router: accept failed", exc_info=True)
                self._closed.wait(0.1)
                continue
            threading.Thread(
                target=self._handshake, args=(cli,), name="ts-router-hs", daemon=True
            ).start()

    def _handshake(self, cli: socket.socket) -> None:
        try:
            cli.settimeout(5.0)
            req = cli.recv(4096)
            parts = req.split(b"\r\n", 1)[0].split()
            target = parts[1].decode("ascii", "replace") if len(parts) > 1 else ""
            path, _, query = target.partition("?")
            with self._lock:
                ch = self._channels.get(path)
            if ch is None or not self._authorized(query):
                cli.sendall(b"HTTP/1.0 404 Not Found\r\nConnection: close\r\n\r\n")
                _close(cli)
                return
            cli.sendall(_HTTP_OK)
            cli.settimeout(None)
        except OSError:
            _close(cli)
            return
        ch.add_consumer(cli)

    def close(self) -> None:
        self._closed.set()
        if self._listen is not None:
            _close(self._listen)
        with self._lock:
            channels = list(self._channels.values())
        for ch in channels:
            ch.close()


def _peer_gone(sock: socket.socket) -> bool:
    """True when the consumer has closed its end (or the socket has failed).

    Consumers send nothing after their request, so a readable socket means EOF;
    anything it does send is read and ignored.
    """
    try:
        readable, _, _ = select.select([sock], [], [], 0)
        if not readable:
            return False
        return sock.recv(4096) == b""
    except (OSError, ValueError):
        return True


def _close(sock: socket.socket) -> None:
    try:
        sock.close()
    except OSError:
        pass
