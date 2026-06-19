"""TUTK IOTC/AV native-SDK streaming session, split out of client.py.

This is the liveType=0 (TUTK C SDK P2P) path. The connected camera fleet is
WebRTC (liveType=2), so this class is currently unused at runtime but retained
for future liveType=0 / p2pId camera support (see docs/DEFERRED_FEATURES.md).
Behavior-preserving extraction; re-exported from client.py.
"""

import asyncio
import ctypes
import logging
import threading
import time
from typing import Callable, Optional

from .models import VideoFrame

_LOGGER = logging.getLogger(__name__)


class TutkStreamSession:
    """TUTK IOTC P2P live-stream session."""

    _IOTYPE_INNER_SND_DATA_DELAY = 255   # TutkManager.java: sent before START
    _IOTYPE_USER_IPCAM_START     = 511   # AVIOCTRLDEFs: IOTYPE_USER_IPCAM_START
    _IOTYPE_USER_IPCAM_STOP      = 767   # AVIOCTRLDEFs: IOTYPE_USER_IPCAM_STOP
    _IOTYPE_USER_IPCAM_AUDIOSTART = 768  # AVIOCTRLDEFs: IOTYPE_USER_IPCAM_AUDIOSTART

    def __init__(
        self,
        uid: str,
        on_frame: Callable[["VideoFrame"], None],
        iotc_lib_path: str = "libIOTCAPIs.so",
        av_lib_path: str = "libAVAPIs.so",
    ) -> None:
        self._uid           = uid
        self._on_frame      = on_frame
        self._iotc_lib_path = iotc_lib_path
        self._av_lib_path   = av_lib_path
        self._thread: Optional[threading.Thread] = None
        self._stop_event    = threading.Event()
        self._sid           = -1
        self._av_index      = -1

    async def start(self) -> bool:
        """Load native libs, connect P2P, and start the frame-receive thread."""
        return await asyncio.get_running_loop().run_in_executor(
            None, self._start_sync)

    def _start_sync(self) -> bool:

        try:
            iotc = ctypes.CDLL(self._iotc_lib_path)
            av   = ctypes.CDLL(self._av_lib_path)
        except OSError as exc:
            _LOGGER.error(
                "TutkStreamSession: cannot load TUTK native libraries "
                "(%s, %s): %s. "
                "Obtain them from the TUTK SDK or an extracted AiDot APK.",
                self._iotc_lib_path, self._av_lib_path, exc,
            )
            return False

        # --- Declare function signatures ------------------------------------ #
        iotc.IOTC_Initialize2.restype  = ctypes.c_int
        iotc.IOTC_Initialize2.argtypes = [ctypes.c_int]

        iotc.IOTC_Get_SessionID.restype  = ctypes.c_int
        iotc.IOTC_Get_SessionID.argtypes = []

        iotc.IOTC_Set_Max_Session_Number.restype  = None
        iotc.IOTC_Set_Max_Session_Number.argtypes = [ctypes.c_int]

        iotc.IOTC_Connect_ByUID_Parallel.restype  = ctypes.c_int
        iotc.IOTC_Connect_ByUID_Parallel.argtypes = [ctypes.c_char_p, ctypes.c_int]

        iotc.IOTC_Session_Close.restype  = None
        iotc.IOTC_Session_Close.argtypes = [ctypes.c_int]

        # TutkManager.java: AVAPIs.avInitialize(32)
        av.avInitialize.restype  = ctypes.c_int
        av.avInitialize.argtypes = [ctypes.c_int]

        av.avClientStart2.restype  = ctypes.c_int
        av.avClientStart2.argtypes = [
            ctypes.c_int,                        # nSID
            ctypes.c_char_p,                     # account
            ctypes.c_char_p,                     # password
            ctypes.c_int,                        # timeout_ms
            ctypes.POINTER(ctypes.c_int),        # srvType[] (out)
            ctypes.c_int,                        # reserved=0
            ctypes.POINTER(ctypes.c_int),        # nSend[] (out)
        ]

        av.avClientStop.restype  = ctypes.c_int
        av.avClientStop.argtypes = [ctypes.c_int]

        av.avSendIOCtrl.restype  = ctypes.c_int
        av.avSendIOCtrl.argtypes = [
            ctypes.c_int,    # nAVIndex
            ctypes.c_uint,   # nIOCtrlType
            ctypes.c_char_p, # cabIOCtrlData
            ctypes.c_int,    # nIOCtrlDataSize
        ]

        # FRAMEINFO_t - TUTK SDK v3.x layout (codec_id, flags, onlineNum,
        # frameSize, frameNo, timestamp). Adjust if your SDK version differs.
        class FrameInfo(ctypes.Structure):
            _fields_ = [
                ("codec_id",   ctypes.c_uint),
                ("flags",      ctypes.c_uint),
                ("onlineNum",  ctypes.c_uint),
                ("frameSize",  ctypes.c_uint),
                ("frameNo",    ctypes.c_uint),
                ("timestamp",  ctypes.c_uint),
            ]

        av.avRecvFrameData2.restype  = ctypes.c_int
        av.avRecvFrameData2.argtypes = [
            ctypes.c_int,                        # nAVIndex
            ctypes.c_char_p,                     # abFrameData
            ctypes.c_int,                        # nFrameDataMaxSize (by value)
            ctypes.POINTER(ctypes.c_int),        # pnActualFrameSize (out)
            ctypes.POINTER(ctypes.c_int),        # pnExpectedFrameSize (out)
            ctypes.c_char_p,                     # pFrameInfo (byte buffer)
            ctypes.c_int,                        # nFrameInfoBufSize (by value)
            ctypes.POINTER(ctypes.c_int),        # pnActualFrameInfoSize (out)
            ctypes.POINTER(ctypes.c_int),        # pnFrameIndex (out)
        ]

        # --- Initialize IOTC (idempotent) ----------------------------------- #
        # TutkManager.java: IOTC_Initialize2(0) then IOTC_Set_Max_Session_Number(10)
        # then avInitialize(32)
        ret = iotc.IOTC_Initialize2(0)
        if ret < 0:
            _LOGGER.debug("IOTC_Initialize2 returned %d (may already be initialized)", ret)
        else:
            iotc.IOTC_Set_Max_Session_Number(10)

        ret = av.avInitialize(32)
        if ret < 0:
            _LOGGER.debug("avInitialize returned %d (may already be initialized)", ret)

        # --- Connect P2P ---------------------------------------------------- #
        sid = iotc.IOTC_Get_SessionID()
        if sid < 0:
            _LOGGER.error("TUTK: IOTC_Get_SessionID failed: %d", sid)
            return False

        _LOGGER.debug("TUTK: connecting to uid=%s (sid=%d)", self._uid, sid)
        ret = iotc.IOTC_Connect_ByUID_Parallel(self._uid.encode(), sid)
        if ret < 0:
            _LOGGER.error(
                "TUTK: IOTC_Connect_ByUID_Parallel(%s) failed: %d",
                self._uid, ret,
            )
            return False
        self._sid = ret

        # --- Start AV client ------------------------------------------------ #
        # TutkManager.java: avClientStart2(nSID, account, pwd, 2000, srvType, 0, nSend)
        # Credentials: "admin" / "admin123" (hardcoded in TutkManager.java)
        srv_type = ctypes.c_int(0)
        n_send   = ctypes.c_int(0)
        av_index = av.avClientStart2(
            self._sid, b"admin", b"admin123", 2000,
            ctypes.byref(srv_type), 0, ctypes.byref(n_send))
        if av_index < 0:
            _LOGGER.error("TUTK: avClientStart2 failed: %d", av_index)
            iotc.IOTC_Session_Close(self._sid)
            self._sid = -1
            return False
        self._av_index = av_index

        # --- Send IOCtrl commands per TutkManager.java ---------------------- #
        # 1. IOTYPE_INNER_SND_DATA_DELAY (255) - 2-byte body
        av.avSendIOCtrl(self._av_index, self._IOTYPE_INNER_SND_DATA_DELAY,
                        (ctypes.c_uint8 * 2)(), 2)
        # 2. IOTYPE_USER_IPCAM_START (511) - 8-byte body (all zeros = default stream)
        av.avSendIOCtrl(self._av_index, self._IOTYPE_USER_IPCAM_START,
                        (ctypes.c_uint8 * 8)(), 8)
        # 3. IOTYPE_USER_IPCAM_AUDIOSTART (768) - 8-byte body
        av.avSendIOCtrl(self._av_index, self._IOTYPE_USER_IPCAM_AUDIOSTART,
                        (ctypes.c_uint8 * 8)(), 8)

        # --- Launch frame-receive thread ------------------------------------ #
        self._thread = threading.Thread(
            target=self._recv_loop,
            args=(av, iotc, FrameInfo),
            daemon=True,
            name=f"tutk-recv-{self._uid[:8]}",
        )
        self._thread.start()
        return True

    def _recv_loop(self, av, iotc, FrameInfo) -> None:

        # avRecvFrameData2 signature (from AVAPIs.java / TUTK SDK):
        #   (nAVIndex, abFrameData, nFrameDataMaxSize,
        #    *pnActualFrameSize, *pnExpectedFrameSize,
        #    pFrameInfo, nFrameInfoBufSize,
        #    *pnActualFrameInfoSize, *pnFrameIndex)
        BUF_SIZE     = 131072   # 128 KB - matches TutkManager.VIDEO_BUF_SIZE (100000)
        frame_buf    = ctypes.create_string_buffer(BUF_SIZE)
        info_buf     = ctypes.create_string_buffer(ctypes.sizeof(FrameInfo))
        actual_sz    = ctypes.c_int(0)
        expected_sz  = ctypes.c_int(0)
        actual_info  = ctypes.c_int(0)
        frame_idx    = ctypes.c_int(0)

        _LOGGER.debug("TUTK: recv loop started (avIndex=%d)", self._av_index)

        while not self._stop_event.is_set():
            ret = av.avRecvFrameData2(
                self._av_index,
                frame_buf,
                BUF_SIZE,
                ctypes.byref(actual_sz),
                ctypes.byref(expected_sz),
                info_buf,
                ctypes.sizeof(FrameInfo),
                ctypes.byref(actual_info),
                ctypes.byref(frame_idx),
            )
            if ret == -20012:
                # AV_ER_DATA_NOREADY - no frame yet; brief sleep per TutkManager (2ms)
                time.sleep(0.002)
                continue
            if ret < 0:
                _LOGGER.error("TUTK: avRecvFrameData2 returned %d - stopping", ret)
                break

            raw      = bytes(frame_buf.raw[: actual_sz.value])
            fi       = FrameInfo.from_buffer_copy(info_buf)
            vf  = VideoFrame(
                frame_type   = fi.codec_id,
                audio_codec  = 0,
                timestamp    = fi.timestamp,
                is_encrypted = False,
                data         = raw,
            )
            try:
                self._on_frame(vf)
            except Exception as exc:
                _LOGGER.error("TUTK: on_frame callback raised: %s", exc)

        # Teardown
        if self._av_index >= 0:
            try:
                av.avClientStop(self._av_index)
            except Exception:
                _LOGGER.debug("swallowed exception in %s", '_recv_loop', exc_info=True)
        if self._sid >= 0:
            try:
                iotc.IOTC_Session_Close(self._sid)
            except Exception:
                _LOGGER.debug("swallowed exception in %s", '_recv_loop', exc_info=True)
        _LOGGER.debug("TUTK: recv loop exited")

    async def stop(self) -> None:
        """Signal the receive thread to stop and wait for it."""
        self._stop_event.set()
        if self._thread is not None:
            await asyncio.get_running_loop().run_in_executor(
                None, lambda: self._thread.join(timeout=5.0)
            )
