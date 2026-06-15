#!/usr/bin/env python3
"""Dev harness for the go2rtc serve path (start_keepalive).

Exercises the library's go2rtc integration without Home Assistant: logs in,
finds a camera, starts the persistent keepalive serve, and reports whether the
serve becomes ready and what pull URL it exposes for go2rtc / HA.

- **DTLS cameras (A000088):** pass an ``http(s)://`` serve URL via ``--serve``;
  the library taps aiortc's encoded H.264 and ``-c copy``s it to an
  ``ffmpeg -f mpegts -listen 1`` socket go2rtc pulls.
- **SDES cameras (A001064/A001513):** pass an ``rtsp://`` push URL via
  ``--serve`` (e.g. a go2rtc RTSP server at ``rtsp://127.0.0.1:8554/<name>``);
  the library keeps the stream warm and pushes to it.

Examples::

    # list cameras
    python scripts/go2rtc_serve.py --list

    # DTLS serve for go2rtc to pull (HTTP-listen mpegts)
    python scripts/go2rtc_serve.py --name Deck --serve http://127.0.0.1:18000 --hold 60

    # SDES push to a go2rtc RTSP server
    python scripts/go2rtc_serve.py --name "Rear of Garage" \
        --serve rtsp://127.0.0.1:8554/rear --hold 60

Requires the ``[webrtc]`` extra and ffmpeg. Credentials via
``aidot.credentials.load_credentials`` (env or ~/.config/aidot). No secrets
are stored here.
"""

import argparse
import asyncio
import time

import aiohttp

from aidot.client import AidotClient
from aidot.const import CONF_DEVICE_LIST, CONF_NAME
from aidot.credentials import load_credentials


def _is_camera(device_client) -> bool:
    model = getattr(getattr(device_client, "info", None), "model_id", "") or ""
    return "IPC" in model


async def _run(args) -> int:
    creds = load_credentials()
    async with aiohttp.ClientSession() as session:
        client = AidotClient(
            session,
            country_code=creds.get("country", "US"),
            username=creds["username"],
            password=creds["password"],
        )
        try:
            await client.async_post_login()
            devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
            cameras = [d for d in devices if _is_camera(client.get_device_client(d))]
            print(f"found {len(cameras)} camera(s)")
            for cam in cameras:
                dc = client.get_device_client(cam)
                model = getattr(getattr(dc, "info", None), "model_id", "") or ""
                print(f"  - {cam.get(CONF_NAME)!r:32} {model}")
            if args.list:
                return 0

            target = next(
                (c for c in cameras
                 if args.name.lower() in (c.get(CONF_NAME) or "").lower()), None)
            if target is None:
                print(f"no camera matching {args.name!r}")
                return 2
            dc = client.get_device_client(target)
            kind = "SDES" if dc.is_sdes_camera else "DTLS"
            print(f"\n>>> {target.get(CONF_NAME)!r} ({kind}) start_keepalive serve={args.serve!r}")

            t0 = time.time()
            await dc.start_keepalive(rtsp_push_url=args.serve)
            ready = await dc.async_wait_serve_ready(timeout=args.timeout)
            print(f"    serve_ready={ready} in {time.time() - t0:.1f}s; "
                  f"stream_rtsp_url={dc.stream_rtsp_url!r}")
            print(f"    holding {args.hold:.0f}s (point go2rtc/ffprobe at the URL above)...")
            await asyncio.sleep(args.hold)
            ok = bool(dc.stream_rtsp_url) and (ready or kind == "SDES")
            print(f"    RESULT: {'PASS (serve up)' if ok else 'serve not confirmed'}")
            return 0 if ok else 1
        finally:
            # Always tear down: stop streaming + log out / close MQTT.
            try:
                await dc.async_stop_streaming()
            except Exception:
                pass
            await client.async_cleanup()


def main() -> int:
    p = argparse.ArgumentParser(description=__doc__,
                                formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--list", action="store_true", help="list cameras and exit")
    p.add_argument("--name", default="", help="camera name substring to serve")
    p.add_argument("--serve", default=None,
                   help="go2rtc target: http(s)://host:port (DTLS) or rtsp://host:port/name (SDES)")
    p.add_argument("--hold", type=float, default=60.0, help="seconds to hold the serve")
    p.add_argument("--timeout", type=float, default=30.0, help="serve-ready timeout")
    return asyncio.run(_run(p.parse_args()))


if __name__ == "__main__":
    raise SystemExit(main())
