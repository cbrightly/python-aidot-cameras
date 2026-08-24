"""Bridge an AiDot camera into go2rtc (or any RTSP/HTTP consumer), without HA.

AiDot cameras expose no RTSP/ONVIF endpoint - the only way to get video is the
cloud signaling + WebRTC (DTLS/SDES) handshake and SRTP decrypt this library
does via aiortc.  This entrypoint is the thin glue that lets a standalone go2rtc
use them: it authenticates to the AiDot cloud, opens the keepalive session for
one camera, and pushes/serves the decrypted H.264 (+ G711 audio on the RTSP-push
path) to a URL the consumer reads.

Run it as ``aidot-go2rtc`` (console script) or ``python -m aidot_cameras``.

Two modes:

  * --list                 Print every camera on the account with its device id
                           and transport, followed by a paste-ready go2rtc
                           ``streams:`` block - each camera already paired with
                           the right output argument, which is the one thing
                           that differs between the transports and the easiest
                           thing to get wrong.

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

A DTLS camera (A000088) has no RTSP-push path, but a stdout producer works the
same lazy way. Pass ``-`` as the output and this process writes MPEG-TS (video
plus AAC audio) to stdout, which go2rtc's exec: source reads directly:

    streams:
      driveway: exec:aidot-go2rtc 7c89a5c1... -

go2rtc then owns the lifecycle - spawn on the first viewer, kill when idle -
exactly as it does with ``{output}`` for an SDES camera. An
``http://host:port/name.ts`` output still works too: the older HTTP-listen
serve, useful for a persistent consumer that is not go2rtc, but that process
must stay running to keep the serve bound.
"""

from __future__ import annotations

import argparse
import asyncio
import logging
import os
import re
import signal
import sys
from typing import Optional

import aiohttp

from .client import AidotClient
from .cloud_auth import (  # noqa: F401  (re-exported for callers and tests)
    _install_token_cache,
    _make_client,
    _read_token_file,
    _write_token_file,
)
from .const import CONF_DEVICE_LIST, CONF_ID, CONF_MODEL_ID, CONF_NAME

_LOGGER = logging.getLogger("aidot.go2rtc")

DEFAULT_COUNTRY = os.environ.get("AIDOT_COUNTRY", "US")


def _env_bool(name: str) -> bool | None:
    """Tri-state env flag: '1' -> True, '0' -> False, unset -> None (library default)."""
    raw = os.environ.get(name)
    if raw is None:
        return None
    return raw == "1"


async def _cameras(client: AidotClient) -> list[dict]:
    """Return the raw device dicts for every camera on the account."""
    data = await client.async_get_all_device()
    return [
        d for d in data[CONF_DEVICE_LIST]
        if "IPC" in (d.get(CONF_MODEL_ID) or "").upper()
    ]


def _stream_slug(name: Optional[str], dev_id: str) -> str:
    """A go2rtc stream key from the camera's name (``Front Door`` -> ``front_door``).

    go2rtc stream names end up in URLs, so keep it to lowercase word characters
    and fall back to the device id when a name is missing or is punctuation
    only.  Pure (unit-testable)."""
    slug = re.sub(r"[^a-z0-9]+", "_", (name or "").lower()).strip("_")
    return slug or f"camera_{dev_id[:8]}"


def _go2rtc_source(dev_id: str, is_sdes: bool) -> str:
    """The ``exec:`` source line for one camera.

    The only difference between the two transports is the output argument:
    SDES cameras RTSP-push into go2rtc's own ``{output}`` URL, DTLS cameras
    write MPEG-TS to stdout (``-``).  Getting that backwards is the single most
    common way to end up with a stream that never starts, which is why --list
    writes the line out rather than describing it."""
    out = "{output}" if is_sdes else "-"
    return f"exec:aidot-go2rtc {dev_id} {out}"


async def cmd_list() -> int:
    """Print device id + transport for every camera, plus paste-ready go2rtc YAML."""
    async with aiohttp.ClientSession() as session:
        client = await _make_client(session)
        cams = await _cameras(client)
        if not cams:
            print("No IPC cameras found on this account.")
            return 1
        print(f"{'device_id':<40}  {'model':<14}  transport")
        print("-" * 72)
        rows = []
        for d in cams:
            dc = client.get_device_client(d)
            transport = "SDES (rtsp-push)" if dc.is_sdes_camera else "DTLS (stdout)"
            print(f"{d[CONF_ID]:<40}  {(d.get(CONF_MODEL_ID) or ''):<14}  {transport}")
            rows.append((_stream_slug(d.get(CONF_NAME), d[CONF_ID]),
                         _go2rtc_source(d[CONF_ID], dc.is_sdes_camera)))
        width = max(len(slug) for slug, _ in rows) + 1
        print("\n# Paste into go2rtc.yaml (rename the streams to taste). go2rtc needs")
        print("# the same AIDOT_* environment variables this command just used.")
        print("streams:")
        for slug, source in rows:
            print(f"  {(slug + ':'):<{width}} {source}")
        print("\n# Then each camera is at rtsp://<go2rtc-host>:8554/<stream-name>")
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

        if output_url == "-":
            # Stdout producer: MPEG-TS on this process's stdout, for a go2rtc
            # exec: source.  SDES cameras already have {output} (RTSP push) and
            # do not take this path, so say which one to use rather than
            # producing an empty stream.
            if dc.is_sdes_camera:
                sys.exit(
                    f"{dev_id} is an SDES camera - '-' (stdout) is the DTLS "
                    "producer path. Use {output} (RTSP push) instead."
                )
            _LOGGER.info(
                "DTLS stdout mode: writing MPEG-TS (video + AAC audio) to "
                "stdout for a go2rtc exec: consumer."
            )
        elif output_url.startswith("rtsp") and not dc.is_sdes_camera:
            _LOGGER.info(
                "DTLS RTSP push: publishing video + AAC audio to %s.", output_url
            )
        elif not output_url.startswith("rtsp") and dc.is_sdes_camera:
            _LOGGER.warning(
                "SDES camera but output %r is not rtsp:// - the library will "
                "HTTP-serve (video only by default) instead of pushing audio+video. "
                "For go2rtc exec, pass {output}.",
                output_url,
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
    # The module docstring above IS the reference for every env var this tool
    # reads, and the README tells users --help will show it. Print it rather
    # than a one-line summary that leaves them hunting through source.
    parser = argparse.ArgumentParser(
        prog="aidot-go2rtc",
        description=__doc__,
        formatter_class=argparse.RawDescriptionHelpFormatter,
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
