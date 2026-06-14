"""TCP binary-protocol playback/live sessions, split out of client.py.

CloudPlaybackSession and LiveStreamSession decode the camera's TCP frame
protocol for recorded-video playback. Behavior-preserving extraction; the
shared framing helpers (_pack_frame/_read_frame/_parse_video_payload) move with
them and are re-exported from client.py.
"""

import asyncio
import json
import logging
import random
import ssl
import struct
import time
from typing import Callable, List, Optional

from ..aes_utils import aes_ecb_decrypt_str_key, aes_ecb_encrypt_str_key
from .constants import (
    _CMD_HB_REQ, _CMD_HB_RES, _CMD_LOGIN_REQ, _CMD_LOGIN_RES,
    _CMD_PARAM, _CMD_STREAM_REQ, _CMD_STREAM_RES, _CMD_SUBCMD,
    _HDR_CONTEXT, _HDR_ENC_TYPE, _HDR_FMT, _HDR_PREFIX_FMT, _HDR_PREFIX_SIZE,
    _HDR_RESERVE, _HDR_RESULT, _HDR_SUFFIX_FMT, _HDR_SUFFIX_SIZE,
    _HDR_VERSION, _SF_HDR_SIZE,
)
from .models import VideoFrame

_LOGGER = logging.getLogger(__name__)


def _pack_frame(cmd: int, payload: bytes, sequence: Optional[int] = None) -> bytes:
    # Build one outbound wire frame: 37-byte header + payload.
    if sequence is None:
        sequence = random.randint(-(2 ** 31), 2 ** 31 - 1)
    ts = int(time.time() * 1000)
    header = struct.pack(
        _HDR_FMT,
        _HDR_VERSION,
        sequence,
        cmd,
        _CMD_SUBCMD,
        _CMD_PARAM,
        len(payload),
        ts,
        _HDR_CONTEXT,
        _HDR_ENC_TYPE,
        _HDR_RESULT,
        _HDR_RESERVE,
    )
    return header + payload


async def _read_frame(reader: asyncio.StreamReader) -> tuple:
    # Read one complete framed response from the playback TCP server.
    # Returns (header_dict, payload_bytes).
    # header_dict keys: cmd, seq, result, timestamp.
    prefix_raw = await reader.readexactly(_HDR_PREFIX_SIZE)
    _version, seq, cmd, _subcmd, _cmd_param, payload_len = struct.unpack(
        _HDR_PREFIX_FMT, prefix_raw
    )
    if payload_len < 0 or payload_len > 4 * 1024 * 1024:
        raise ValueError(f"Implausible payloadLen={payload_len} in TCP frame")
    rest = await reader.readexactly(_HDR_SUFFIX_SIZE + payload_len)
    timestamp, _context, _enc_type, result, _reserve = struct.unpack(
        _HDR_SUFFIX_FMT, rest[:_HDR_SUFFIX_SIZE]
    )
    payload = rest[_HDR_SUFFIX_SIZE:]
    return {"cmd": cmd, "seq": seq, "result": result, "timestamp": timestamp}, payload


def _parse_video_payload(data: bytes) -> List[VideoFrame]:
    # Parse a STREAM_RES payload into VideoFrame objects.
    # Sub-frame layout (17-byte header, big-endian):
    #   padding(2) frameType(1) audioCodec(1) timestamp(8) encType(1) payloadLen(4)
    # Source: LDSPlayer.decodeStream() in the Leedarson Android SDK.
    frames: List[VideoFrame] = []
    offset = 0
    while len(data) - offset >= _SF_HDR_SIZE:
        frame_type    = data[offset + 2]
        audio_codec   = data[offset + 3]
        (timestamp,)  = struct.unpack_from(">q", data, offset + 4)
        enc_type      = data[offset + 12]
        (payload_len,) = struct.unpack_from(">i", data, offset + 13)
        if payload_len < 0:
            break
        end = offset + _SF_HDR_SIZE + payload_len
        if end > len(data):
            break
        if enc_type != 0:
            frames.append(VideoFrame(frame_type, audio_codec, timestamp, True, b""))
        else:
            frames.append(VideoFrame(
                frame_type, audio_codec, timestamp, False,
                data[offset + _SF_HDR_SIZE:end],
            ))
        offset = end
    return frames


class CloudPlaybackSession:
    # Manages a single cloud-playback TCP session for a Leedarson/AiDot camera.
    # Use DeviceClient.async_open_cloud_playback() to obtain an instance.

    def __init__(
        self,
        server_ip: str,
        server_port: int,
        heartbeat_interval: int,
        task_id: int,
        client_id: str,
        start_ts_s: int,
        on_frame: Callable[[VideoFrame], None],
    ) -> None:
        self._server_ip   = server_ip
        self._server_port = server_port
        self._hb_interval = heartbeat_interval
        self._task_id     = task_id
        self._client_id   = client_id
        self._start_ts    = start_ts_s
        self._on_frame    = on_frame
        self._reader: Optional[asyncio.StreamReader] = None
        self._writer: Optional[asyncio.StreamWriter] = None
        self._running  = False
        self._paused   = False
        self._hb_task: Optional[asyncio.Task] = None
        self._rx_task: Optional[asyncio.Task] = None

    async def _connect_and_login(self) -> bool:
        try:
            self._reader, self._writer = await asyncio.open_connection(
                self._server_ip, self._server_port
            )
        except OSError as exc:
            _LOGGER.error(
                "Cloud playback: TCP connect to %s:%d failed: %s",
                self._server_ip, self._server_port, exc,
            )
            return False

        login_body = json.dumps({
            "clientId":  self._client_id,
            "heartbeat": self._hb_interval,
            "taskId":    self._task_id,
        }).encode("utf-8")

        seq = random.randint(-(2 ** 31), 2 ** 31 - 1)
        self._writer.write(_pack_frame(_CMD_LOGIN_REQ, login_body, seq))
        await self._writer.drain()

        try:
            hdr, resp_payload = await asyncio.wait_for(
                _read_frame(self._reader), timeout=10.0
            )
        except TimeoutError:
            _LOGGER.error("Cloud playback: login response timed out")
            return False
        except Exception as exc:
            _LOGGER.error("Cloud playback: login read error: %s", exc)
            return False

        if hdr["cmd"] != _CMD_LOGIN_RES:
            _LOGGER.error(
                "Cloud playback: unexpected login response cmd=0x%04x", hdr["cmd"]
            )
            return False

        try:
            body_obj = json.loads(resp_payload)
            if body_obj.get("code") != 200:
                _LOGGER.error(
                    "Cloud playback: login rejected code=%s body=%s",
                    body_obj.get("code"), body_obj,
                )
                return False
        except (json.JSONDecodeError, ValueError):
            pass  # some firmware sends no JSON body - treat as success

        _LOGGER.debug(
            "Cloud playback: login OK task=%d server=%s:%d",
            self._task_id, self._server_ip, self._server_port,
        )
        return True

    async def _request_stream_batch(self) -> None:
        if self._writer is None:
            return
        body = json.dumps({
            "begin":     self._start_ts,
            "type":      1,
            "framenums": 10,
            "speed":     1,
        }).encode("utf-8")
        self._writer.write(_pack_frame(_CMD_STREAM_REQ, body))
        await self._writer.drain()

    async def _heartbeat_loop(self) -> None:
        while self._running:
            await asyncio.sleep(self._hb_interval)
            if not self._running or self._writer is None:
                break
            try:
                self._writer.write(_pack_frame(_CMD_HB_REQ, b"{}"))
                await self._writer.drain()
            except Exception as exc:
                _LOGGER.warning("Cloud playback: heartbeat write failed: %s", exc)
                break

    async def _receive_loop(self) -> None:
        while self._running:
            if self._paused:
                await asyncio.sleep(0.2)
                continue
            try:
                hdr, payload = await asyncio.wait_for(
                    _read_frame(self._reader),
                    timeout=30.0,
                )
            except TimeoutError:
                _LOGGER.warning("Cloud playback: receive timeout")
                break
            except asyncio.IncompleteReadError:
                if self._running:
                    _LOGGER.info("Cloud playback: server closed TCP connection")
                break
            except Exception as exc:
                if self._running:
                    _LOGGER.warning("Cloud playback: receive error: %s", exc)
                break

            if hdr["cmd"] == _CMD_HB_RES:
                continue

            if hdr["cmd"] != _CMD_STREAM_RES:
                _LOGGER.debug(
                    "Cloud playback: ignoring unexpected cmd=0x%04x", hdr["cmd"]
                )
                continue

            result = hdr["result"]
            if result == 200:
                for frame in _parse_video_payload(payload):
                    try:
                        self._on_frame(frame)
                    except Exception:
                        _LOGGER.exception("Cloud playback: exception in on_frame callback")
                if self._running and not self._paused:
                    await self._request_stream_batch()
            elif result == -15528:
                # End-of-stream sentinel from LDSOpenSDK.java receiveDataTask
                _LOGGER.info("Cloud playback: end of stream reached")
                break
            else:
                _LOGGER.warning("Cloud playback: unexpected stream result=%d", result)

    async def start(self) -> bool:
        self._running = True
        if not await self._connect_and_login():
            self._running = False
            return False
        await self._request_stream_batch()
        self._hb_task = asyncio.create_task(
            self._heartbeat_loop(), name="aidot-cloud-hb"
        )
        self._rx_task = asyncio.create_task(
            self._receive_loop(), name="aidot-cloud-rx"
        )
        return True

    async def pause(self) -> None:
        self._paused = True

    async def resume(self) -> None:
        self._paused = False
        if self._running and self._writer is not None:
            await self._request_stream_batch()

    async def stop(self) -> None:
        self._running = False
        self._paused  = False
        for task in (self._hb_task, self._rx_task):
            if task and not task.done():
                task.cancel()
                try:
                    await task
                except asyncio.CancelledError:
                    pass
        self._hb_task = None
        self._rx_task = None
        if self._writer is not None:
            try:
                self._writer.close()
                await self._writer.wait_closed()
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception", 'stop', exc_info=True)
            self._writer = None
            self._reader = None


class LiveStreamSession:

    def __init__(
        self,
        server_ip: str,
        server_port: int,
        session_id: str,
        aes_key: str,
        heartbeat_interval: int,
        use_tls: bool,
        on_frame: Callable[["VideoFrame"], None],
    ) -> None:
        self._server_ip         = server_ip
        self._server_port       = int(server_port)
        self._session_id        = session_id
        self._aes_key           = aes_key
        self._heartbeat_secs    = max(1, int(heartbeat_interval))
        self._use_tls           = use_tls
        self._on_frame          = on_frame
        self._reader: Optional[asyncio.StreamReader]  = None
        self._writer: Optional[asyncio.StreamWriter]  = None
        self._task:   Optional[asyncio.Task]          = None
        self._closed  = False

    # -- Public interface ---------------------------------------------------- #

    async def start(self) -> bool:
        # Open the TLS (or plain) TCP connection and perform the login handshake.
        # Returns True on success, False on failure.

        try:
            if self._use_tls:
                ssl_ctx = ssl.create_default_context()
                ssl_ctx.check_hostname = False
                ssl_ctx.verify_mode    = ssl.CERT_NONE
            else:
                ssl_ctx = None

            self._reader, self._writer = await asyncio.wait_for(
                asyncio.open_connection(
                    self._server_ip, self._server_port, ssl=ssl_ctx
                ),
                timeout=10,
            )
        except Exception as exc:
            _LOGGER.error(
                "LiveStreamSession: TCP connect to %s:%d failed: %s",
                self._server_ip, self._server_port, exc,
            )
            return False

        # LOGIN -- carry sessionId as credential, AES-encrypt the JSON payload.
        try:
            login_body_raw = json.dumps({
                "sessionId": self._session_id,
                "clientId":  "live-stream",
            }).encode("utf-8")
            login_enc = aes_ecb_encrypt_str_key(login_body_raw, self._aes_key)
            self._writer.write(_pack_frame(_CMD_LOGIN_REQ, login_enc))
            await self._writer.drain()

            hdr, payload = await asyncio.wait_for(_read_frame(self._reader), timeout=10)
            if hdr["cmd"] != _CMD_LOGIN_RES:
                _LOGGER.error(
                    "LiveStreamSession: expected LOGIN_RES (0x%04x), got 0x%04x",
                    _CMD_LOGIN_RES, hdr["cmd"],
                )
                await self._cleanup()
                return False

            # Decrypt and log the login response (best-effort -- ignore on error)
            try:
                resp_plain = aes_ecb_decrypt_str_key(payload, self._aes_key)
                _LOGGER.debug("LiveStreamSession: LOGIN_RES: %s", resp_plain[:200])
            except Exception:
                _LOGGER.debug("LiveStreamSession: LOGIN_RES payload not AES-encrypted")

        except Exception as exc:
            _LOGGER.error("LiveStreamSession: login handshake failed: %s", exc)
            await self._cleanup()
            return False

        # STREAM_REQ -- request the live feed.
        # No taskId needed; the sessionId from MQTT already identifies the stream.
        try:
            stream_body_raw = json.dumps({"sessionId": self._session_id}).encode("utf-8")
            stream_enc = aes_ecb_encrypt_str_key(stream_body_raw, self._aes_key)
            self._writer.write(_pack_frame(_CMD_STREAM_REQ, stream_enc))
            await self._writer.drain()
        except Exception as exc:
            _LOGGER.error("LiveStreamSession: STREAM_REQ failed: %s", exc)
            await self._cleanup()
            return False

        # Start background receive/heartbeat task.
        self._task = asyncio.get_running_loop().create_task(self._receive_loop())
        return True

    async def stop(self) -> None:
        # Gracefully stop the session.
        self._closed = True
        if self._task and not self._task.done():
            self._task.cancel()
            try:
                await self._task
            except asyncio.CancelledError:
                pass
        await self._cleanup()

    # -- Internals ----------------------------------------------------------- #

    async def _receive_loop(self) -> None:
        assert self._reader is not None
        assert self._writer is not None

        hb_interval = self._heartbeat_secs
        last_hb     = time.monotonic()

        try:
            while not self._closed:
                # Send heartbeat if due.
                if time.monotonic() - last_hb >= hb_interval:
                    try:
                        hb_enc = aes_ecb_encrypt_str_key(b"{}", self._aes_key)
                        self._writer.write(_pack_frame(_CMD_HB_REQ, hb_enc))
                        await self._writer.drain()
                        last_hb = time.monotonic()
                    except Exception as exc:
                        _LOGGER.warning("LiveStreamSession: heartbeat error: %s", exc)
                        break

                # Read next frame with a deadline matching the heartbeat interval.
                try:
                    hdr, payload = await asyncio.wait_for(
                        _read_frame(self._reader),
                        timeout=hb_interval * 2,
                    )
                except TimeoutError:
                    _LOGGER.warning("LiveStreamSession: receive timeout -- reconnect?")
                    break

                if hdr["cmd"] == _CMD_HB_RES:
                    continue

                if hdr["cmd"] != _CMD_STREAM_RES:
                    _LOGGER.debug(
                        "LiveStreamSession: unexpected cmd=0x%04x", hdr["cmd"]
                    )
                    continue

                # End-of-stream sentinel (result == -15528 from LDSOpenSDK.java)
                if hdr.get("result") == -15528:
                    _LOGGER.info("LiveStreamSession: end-of-stream sentinel received")
                    break

                # AES-decrypt the payload, then parse video sub-frames.
                try:
                    plain = aes_ecb_decrypt_str_key(payload, self._aes_key)
                except Exception:
                    # Some servers send unencrypted frames; fall back gracefully.
                    plain = payload

                for frame in _parse_video_payload(plain):
                    try:
                        self._on_frame(frame)
                    except Exception as exc:
                        _LOGGER.warning(
                            "LiveStreamSession: on_frame callback raised: %s", exc
                        )

        except asyncio.CancelledError:
            pass
        except Exception as exc:
            if not self._closed:
                _LOGGER.error("LiveStreamSession: receive loop error: %s", exc)
        finally:
            await self._cleanup()

    async def _cleanup(self) -> None:
        if self._writer:
            try:
                self._writer.close()
                await self._writer.wait_closed()
            except Exception:
                _LOGGER.debug("camera %s: swallowed exception", '_cleanup', exc_info=True)
            self._writer = None
            self._reader = None
