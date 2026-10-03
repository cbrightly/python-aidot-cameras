"""File recording of a DTLS session by copying the camera's own streams.

A DTLS ``output_path`` recording to MPEG-TS uses the same copy mux as Home
Assistant's DTLS serve (``_dtls_av_mux_run``): the camera's H.264 is written
as-is, and its A-law audio as 48 kHz AAC. The encoded frames are teed before
decode by ``CameraMixin._install_encoded_tap`` into the queues this owns, so the
recording competes with nothing - not the ``on_frame`` consumer, not the audio
drain - and nothing is re-encoded.

aiortc's MediaRecorder, used before, decoded and re-encoded with libx264 and
read the same track queues as the other consumers. A code review measured half
the frames lost, a 640x480 crop when audio reached it first, aiortc's false
2**32 timestamp wrap carried into the file, and video held back for 10 s while
no audio arrived. It is still used for containers other than MPEG-TS.
"""

from __future__ import annotations

import asyncio
import logging
import os
import queue
import threading
from typing import Optional

from .protocol import _dtls_av_mux_run

_LOGGER = logging.getLogger(__name__)

#: File suffixes recorded by copying (the copy mux writes MPEG-TS only).
TS_SUFFIXES = (".ts", ".m2ts", ".mts")

#: Frames the taps may queue ahead of the mux thread (as the DTLS serve uses).
_QUEUE_MAX = 600

#: How long stop() waits for the mux thread to write its tail and close.
_JOIN_S = 10.0


def is_ts_path(path: str) -> bool:
    """True when ``path`` names an MPEG-TS file the copy recorder can write."""
    return os.path.splitext(str(path))[1].lower() in TS_SUFFIXES


class TsCopyRecorder:
    """Record a DTLS session's video and audio to an MPEG-TS file, without
    re-encoding. Same ``start()``/``stop()`` contract as aiortc's MediaRecorder.

    The caller installs the encoded taps on the session's receivers, sending
    video to :attr:`vq` and audio to :attr:`aq`.
    """

    def __init__(self, path: str, device_id: Optional[str] = None) -> None:
        self._path = path
        self._device_id = device_id or "?"
        self.vq: "queue.Queue" = queue.Queue(maxsize=_QUEUE_MAX)
        self.aq: "queue.Queue" = queue.Queue(maxsize=_QUEUE_MAX)
        self._stop_flag = threading.Event()
        self._progress = [0.0]
        self._thread: Optional[threading.Thread] = None
        self._state: dict = {}
        self._stopped = False
        # Opened here, before the session connects, so an unwritable path fails
        # while there is nothing to clean up. Held open until the mux thread is
        # done with it - no single block encloses that, so no context manager.
        self._file = open(self._path, "wb")  # noqa: SIM115

    async def start(self) -> None:
        if self._thread is not None or self._stopped:
            return
        self._thread = threading.Thread(
            target=_dtls_av_mux_run,
            args=(self.vq, self.aq, self._file, self._progress, self._stop_flag),
            kwargs={"align_audio_start": True, "state": self._state},
            name="aidot-dtls-record",
            daemon=True,
        )
        self._thread.start()

    async def stop(self) -> None:
        """Write what has been queued, finish the file and close it. Idempotent."""
        if self._stopped:
            return
        self._stopped = True
        if self._thread is None:
            self._close()
            return
        self._stop_flag.set()
        loop = asyncio.get_running_loop()
        await loop.run_in_executor(None, self._thread.join, _JOIN_S)
        if self._thread.is_alive():
            # Still writing its tail: closing the file under it would truncate
            # the recording. Let it finish, then close.
            _LOGGER.warning(
                "camera %s: recording to %s is taking more than %.0f s to"
                " finish; it will be closed when it does",
                self._device_id,
                self._path,
                _JOIN_S,
            )
            loop.run_in_executor(None, self._finish_late)
            return
        self._close()

    def _finish_late(self) -> None:
        self._thread.join()
        self._close()

    def _close(self) -> None:
        if self._state.get("failed"):
            _LOGGER.warning(
                "camera %s: recording to %s did not complete - a write failed",
                self._device_id,
                self._path,
            )
        try:
            self._file.close()
        except Exception:
            _LOGGER.debug("swallowed exception in %s", "stop", exc_info=True)
