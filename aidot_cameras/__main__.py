"""Bridge an AiDot camera into go2rtc (or any RTSP/HTTP consumer), without HA.

AiDot cameras expose no RTSP/ONVIF endpoint - the only way to get video is the
cloud signaling + WebRTC (DTLS/SDES) handshake and SRTP decrypt this library
does via aiortc.  This entrypoint is the thin glue that lets a standalone go2rtc
use them: it authenticates to the AiDot cloud, opens the keepalive session for
one camera, and pushes/serves the decrypted H.264 (+ G711 audio on the RTSP-push
path) to a URL the consumer reads.

Run it as ``aidot-go2rtc`` (console script) or ``python -m aidot``.

Two modes:

  * --list                 Print every camera on the account with its device id
                           and transport (SDES = RTSP-push capable, DTLS =
                           HTTP-pull only).  Use this to fill in go2rtc.yaml.

  * <dev_id> <output_url>   Stream one camera.  Meant to be invoked by go2rtc as
                           an ``exec:`` source - go2rtc substitutes ``{output}``
                           with its own publish URL.

Authentication (in precedence order):

  AIDOT_TOKEN_FILE=/path/to/token.json   A stored login_info dict (the same one
                                         the HA integration persists). Token
                                         rotations are written back to this file
                                         (set_token_fresh_cb), so a standalone
                                         run survives refreshes and restarts.
  AIDOT_USERNAME / AIDOT_PASSWORD / AIDOT_COUNTRY (default US)
                                         Full login. RECOMMENDED for long-running
                                         standalone use: a dedicated login avoids
                                         fighting HA over a shared (rotating)
                                         refresh token. Pair with AIDOT_TOKEN_FILE
                                         to cache the result across restarts.

Optional stream knobs:

  AIDOT_FAST_CONNECT=1        LAN-direct mode (skip TURN relay; same-subnet only)
  AIDOT_SDES_SERVE_AUDIO=1    include audio on the SDES *http-serve* path (off by
                              default - the AAC-under-loss deadlock). The RTSP-push
                              path always carries G711 audio regardless.

go2rtc.yaml example (SDES camera - native RTSP push, video + audio):

    streams:
      frontdoor: exec:aidot-go2rtc 8d2521ea... {output}

For a DTLS camera (A000088) there is no RTSP-push path; pass an http serve URL
(http://127.0.0.1:PORT/name.ts) and add it to go2rtc as an http-mpegts source -
but this process must stay running to keep that serve bound.
"""

from __future__ import annotations

import argparse
import asyncio
import json
import logging
import os
import signal
import sys
import tempfile

import aiohttp

from .client import AidotClient
from .const import CONF_DEVICE_LIST, CONF_ID, CONF_MODEL_ID

_LOGGER = logging.getLogger("aidot.go2rtc")

DEFAULT_COUNTRY = os.environ.get("AIDOT_COUNTRY", "US")


def _env_bool(name: str) -> bool | None:
    """Tri-state env flag: '1' -> True, '0' -> False, unset -> None (library default)."""
    raw = os.environ.get(name)
    if raw is None:
        return None
    return raw == "1"


def _read_token_file(path: str) -> dict:
    """Blocking read of a stored login_info dict (run via executor from async)."""
    with open(path, encoding="utf-8") as fh:
        return json.load(fh)


def _write_token_file(path: str, data: dict) -> None:
    """Atomic, 0600-secure write of login_info to ``path``.

    Serialize into a temp file in the same directory (mkstemp creates it 0600,
    closing the world-readable window os.chmod-after-write leaves) and only then
    os.replace it over ``path`` - so a serialization failure never truncates the
    previously-valid token. On any error the temp file is removed and re-raised.
    """
    directory = os.path.dirname(path) or "."
    fd, tmp = tempfile.mkstemp(dir=directory, prefix=".token-", suffix=".tmp")
    try:
        with os.fdopen(fd, "w", encoding="utf-8") as fh:
            json.dump(data, fh)
        os.replace(tmp, path)
    except BaseException:
        try:
            os.unlink(tmp)
        except OSError:
            pass
        raise


def _install_token_cache(client: AidotClient, path: str) -> None:
    """Persist rotated tokens to ``path`` so refreshes survive across restarts.

    The library calls this no-arg callback after every successful token refresh
    (client.py), having already updated ``client.login_info``. Mirrors HA's
    coordinator.token_fresh_cb, but writes to our own file instead of an HA
    config entry - so a standalone run never loses auth to a rotation and never
    fights HA over a shared refresh token.
    """
    def _cb() -> None:
        try:
            _write_token_file(path, client.serializable_login_info())
            _LOGGER.debug("Cached refreshed token to %s", path)
        except (OSError, TypeError) as exc:
            # TypeError: a defensive belt-and-suspenders catch alongside
            # serializable_login_info() itself - if a future runtime-only key
            # is ever added to login_info without also being added to
            # RUNTIME_ONLY_LOGIN_INFO_KEYS, this keeps a caching bug from
            # ever propagating out of a callback and interrupting a token
            # refresh that had otherwise already succeeded (confirmed live:
            # this exact TypeError - "Object of type Lock is not JSON
            # serializable" - previously escaped this callback uncaught).
            _LOGGER.warning("Could not cache refreshed token to %s: %s", path, exc)

    client.set_token_fresh_cb(_cb)


async def _make_client(session: aiohttp.ClientSession) -> AidotClient:
    """Authenticate to the AiDot cloud from the environment.

    Prefers a stored token (AIDOT_TOKEN_FILE) - the same login_info dict the HA
    integration persists - which carries access/refresh tokens so no password
    round-trip is needed.  Falls back to username/password login otherwise.  In
    both cases, if AIDOT_TOKEN_FILE is set we register a write-back cache so
    rotations persist.
    """
    loop = asyncio.get_running_loop()
    token_file = os.environ.get("AIDOT_TOKEN_FILE")
    username = os.environ.get("AIDOT_USERNAME")
    password = os.environ.get("AIDOT_PASSWORD")

    if token_file and os.path.exists(token_file):
        try:
            token = await loop.run_in_executor(None, _read_token_file, token_file)
        except (OSError, ValueError) as exc:
            # ValueError covers json.JSONDecodeError (empty/partial/corrupt
            # cache). Fall through to the username/password path rather than
            # crash CLI startup.
            _LOGGER.warning("ignoring unreadable token cache %s: %s", token_file, exc)
        else:
            client = AidotClient(session=session, token=token)
            _install_token_cache(client, token_file)
            return client

    if not username or not password:
        sys.exit(
            "Set AIDOT_TOKEN_FILE, or AIDOT_USERNAME and AIDOT_PASSWORD, in the "
            "environment."
        )

    client = AidotClient(
        session=session,
        country_code=DEFAULT_COUNTRY,
        username=username,
        password=password,
    )
    await client.async_post_login()
    # If a token file path was given (but didn't exist yet), seed it now and
    # register the write-back cache so a dedicated-login run persists its
    # rotations across restarts.
    if token_file:
        _install_token_cache(client, token_file)
        try:
            await loop.run_in_executor(
                None, _write_token_file, token_file, client.serializable_login_info()
            )
        except (OSError, TypeError) as exc:
            _LOGGER.warning("Could not seed token cache %s: %s", token_file, exc)
    return client


async def _cameras(client: AidotClient) -> list[dict]:
    """Return the raw device dicts for every camera on the account."""
    data = await client.async_get_all_device()
    return [
        d for d in data[CONF_DEVICE_LIST]
        if "IPC" in (d.get(CONF_MODEL_ID) or "").upper()
    ]


async def cmd_list() -> int:
    """Print device id + transport for every camera, for go2rtc.yaml."""
    async with aiohttp.ClientSession() as session:
        client = await _make_client(session)
        cams = await _cameras(client)
        if not cams:
            print("No IPC cameras found on this account.")
            return 1
        print(f"{'device_id':<40}  {'model':<14}  transport")
        print("-" * 72)
        for d in cams:
            dc = client.get_device_client(d)
            transport = "SDES (rtsp-push)" if dc.is_sdes_camera else "DTLS (http-pull)"
            print(f"{d[CONF_ID]:<40}  {(d.get(CONF_MODEL_ID) or ''):<14}  {transport}")
    return 0


async def cmd_stream(dev_id: str, output_url: str) -> int:
    """Open one camera's keepalive and push/serve it to ``output_url``."""
    async with aiohttp.ClientSession() as session:
        client = await _make_client(session)
        cams = await _cameras(client)
        device = next((d for d in cams if d[CONF_ID] == dev_id), None)
        if device is None:
            sys.exit(f"Camera {dev_id!r} not found. Run with --list to see ids.")

        dc = client.get_device_client(device)

        if not output_url.startswith("rtsp") and dc.is_sdes_camera:
            _LOGGER.warning(
                "SDES camera but output %r is not rtsp:// - the library will "
                "HTTP-serve (video only by default) instead of pushing audio+video. "
                "For go2rtc exec, pass {output}.",
                output_url,
            )
        if output_url.startswith("rtsp") and not dc.is_sdes_camera:
            sys.exit(
                f"{dev_id} is a DTLS camera - the library has no RTSP-push path "
                "for it. Use an http://host:port/name.ts serve URL instead."
            )

        await dc.async_login()
        await dc.start_keepalive(
            rtsp_push_url=output_url,
            fast_connect=_env_bool("AIDOT_FAST_CONNECT"),
            sdes_audio=_env_bool("AIDOT_SDES_SERVE_AUDIO"),
        )
        _LOGGER.info("Streaming %s -> %s", dev_id, output_url)

        # start_keepalive only launches the background task; hold the process open
        # so the consumer keeps reading. Exit cleanly on SIGTERM/SIGINT (go2rtc
        # kills the exec child when no client is watching) so the cloud session is
        # released.
        stop = asyncio.Event()
        loop = asyncio.get_running_loop()
        for sig in (signal.SIGTERM, signal.SIGINT):
            try:
                loop.add_signal_handler(sig, stop.set)
            except NotImplementedError:  # pragma: no cover - non-POSIX
                pass
        try:
            await stop.wait()
        finally:
            _LOGGER.info("Stopping stream for %s", dev_id)
            try:
                await dc.async_stop_streaming()
            except Exception as exc:
                _LOGGER.debug("stop_streaming failed: %s", exc)
    return 0


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(
        prog="aidot-go2rtc",
        description="Bridge an AiDot camera into go2rtc (HA-independent).",
        epilog="Run with --list first to discover device ids and transports.",
    )
    parser.add_argument("dev_id", nargs="?", help="camera device id (see --list)")
    parser.add_argument(
        "output_url",
        nargs="?",
        help="consumer publish/serve URL; pass {output} from the exec: source",
    )
    parser.add_argument("--list", action="store_true", help="list cameras and exit")
    parser.add_argument("-v", "--verbose", action="store_true", help="debug logging")
    args = parser.parse_args(argv)

    logging.basicConfig(
        level=logging.DEBUG if args.verbose else logging.INFO,
        format="%(asctime)s %(levelname)s %(name)s: %(message)s",
        stream=sys.stderr,  # keep stdout clean - the consumer may read it
    )

    if args.list:
        return asyncio.run(cmd_list())
    if not args.dev_id or not args.output_url:
        parser.error("dev_id and output_url are required (or use --list)")
    return asyncio.run(cmd_stream(args.dev_id, args.output_url))


if __name__ == "__main__":
    raise SystemExit(main())
