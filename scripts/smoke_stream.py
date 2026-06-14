#!/usr/bin/env python3
"""Live smoke test: log in, enumerate cameras, and stream them briefly.

A manual on-hardware validation harness for the camera streaming paths
(DTLS-SRTP and SDES-SRTP). It uses the same public API the Home Assistant
component does, so a green run is strong evidence the library can negotiate and
receive media from real cameras on the LAN.

Credentials are loaded via ``aidot.credentials.load_credentials`` (env vars
``AIDOT_USERNAME`` / ``AIDOT_PASSWORD`` / ``AIDOT_COUNTRY`` or the encrypted
file pair under ``$XDG_CONFIG_HOME/aidot``). No secrets are stored in this file.

Examples::

    # list cameras only
    python scripts/smoke_stream.py --list

    # stream every camera for 16s each and report frames / recorded bytes
    python scripts/smoke_stream.py

    # stream specific cameras by (case-insensitive substring) name
    python scripts/smoke_stream.py --name Deck --name "M3 Pro" --hold 20

Requires the ``[webrtc]`` extra (aiortc/av/...) and, for recording, ffmpeg.
Exit code is non-zero if any selected camera produced no media.
"""

import argparse
import asyncio
import inspect
import os
import time

import aiohttp

from aidot.client import AidotClient
from aidot.const import CONF_DEVICE_LIST, CONF_ID, CONF_NAME
from aidot.credentials import load_credentials


def _is_camera(device_client) -> bool:
    model = getattr(getattr(device_client, "info", None), "model_id", "") or ""
    return "IPC" in model


async def _stop(session) -> None:
    stop = getattr(session, "stop", None)
    if stop is None:
        return
    result = stop()
    if inspect.isawaitable(result):
        await result


async def _stream_one(client, device, hold: float, out_dir: str) -> bool:
    name = device.get(CONF_NAME)
    dc = client.get_device_client(device)
    model = getattr(getattr(dc, "info", None), "model_id", "") or ""
    out = os.path.join(out_dir, f"smoke_{device.get(CONF_ID)[:8]}.ts")
    if os.path.exists(out):
        os.remove(out)
    frames = {"n": 0}
    t0 = time.time()
    print(f"\n>>> {name!r} ({model}) opening stream...")
    try:
        session = await dc.async_open_webrtc_stream(
            on_frame=lambda _f: frames.__setitem__("n", frames["n"] + 1),
            timeout=30.0,
            output_path=out,
            max_seconds=int(hold - 2),
        )
        print(f"    established in {time.time() - t0:.1f}s via "
              f"{type(session).__name__}; holding {hold:.0f}s...")
        await asyncio.sleep(hold)
        await _stop(session)
        await asyncio.sleep(1.0)
        size = os.path.getsize(out) if os.path.exists(out) else 0
        ok = frames["n"] > 0 or size > 20000
        print(f"    {name!r}: on_frame={frames['n']} recorded={size}B "
              f"-> {'PASS' if ok else 'NO MEDIA'}")
        return ok
    except Exception as exc:
        print(f"    {name!r}: ERROR {type(exc).__name__}: {exc}")
        return False


async def _run(args) -> int:
    creds = load_credentials()
    async with aiohttp.ClientSession() as session:
        client = AidotClient(
            session,
            country_code=creds.get("country", "US"),
            username=creds["username"],
            password=creds["password"],
        )
        await client.async_post_login()
        devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
        cameras = [d for d in devices if _is_camera(client.get_device_client(d))]
        print(f"found {len(cameras)} camera(s) of {len(devices)} device(s)")
        for cam in cameras:
            dc = client.get_device_client(cam)
            model = getattr(getattr(dc, "info", None), "model_id", "") or ""
            print(f"  - {cam.get(CONF_NAME)!r:32} {model}")

        if args.list:
            await client.async_cleanup()
            return 0

        selected = cameras
        if args.name:
            wanted = [n.lower() for n in args.name]
            selected = [c for c in cameras
                        if any(w in (c.get(CONF_NAME) or "").lower() for w in wanted)]
        print(f"\nstreaming {len(selected)} camera(s) sequentially")
        results = [(c.get(CONF_NAME), await _stream_one(client, c, args.hold, args.out_dir))
                   for c in selected]

        print("\n==== SUMMARY ====")
        for name, ok in results:
            print(f"  {'PASS' if ok else 'FAIL'}  {name}")
        await client.async_cleanup()
        return 0 if results and all(ok for _, ok in results) else 1


def main() -> int:
    p = argparse.ArgumentParser(description=__doc__,
                                formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--list", action="store_true", help="list cameras and exit")
    p.add_argument("--name", action="append", default=[],
                   help="stream only cameras whose name contains this (repeatable)")
    p.add_argument("--hold", type=float, default=16.0,
                   help="seconds to hold each stream (default 16)")
    p.add_argument("--out-dir", default="/tmp", help="where to write recordings")
    return asyncio.run(_run(p.parse_args()))


if __name__ == "__main__":
    raise SystemExit(main())
