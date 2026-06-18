"""Camera connection diagnostics: handshake, frame timeline, ICE path, RTP health.

A maintained on-hardware probe for diagnosing *quality* problems (buffering,
stutter, slow start) on a real camera, beyond the pass/fail that
``smoke_stream.py`` reports.  For each selected camera it opens a live stream
and reports:

  * handshake time and time-to-first-frame (cold-start latency),
  * a per-second decoded-frame-rate timeline + the largest inter-frame gaps
    (the signature of a jittery link: zero-second stalls then catch-up bursts),
  * the nominated ICE candidate pair - host/srflx/relay/prflx - which tells
    relay-vs-direct routing apart,
  * inbound RTP packet loss / jitter from ``session.get_stats()`` (audio is
    reliable; the *video* packet count is undercounted because this path
    decrypts/bridges video through a custom track outside aiortc's RTP
    receiver - trust the fps timeline above for video health),
  * the camera's cloud-reported Wi-Fi RSSI (dBm).

It uses the same public API the Home Assistant component does.  Credentials load
via ``aidot.credentials.load_credentials`` (env ``AIDOT_USERNAME`` /
``AIDOT_PASSWORD`` / ``AIDOT_COUNTRY`` or the encrypted file pair under
``$XDG_CONFIG_HOME/aidot``).  No secrets are stored here.

NOTE - media-slot contention: a camera holds its single live-stream slot for
~120 s after a session.  Probing the SAME camera in rapid succession can fail to
establish or return no media; space repeats ~3 min apart (different cameras are
independent).

Examples::

    # diagnose one camera for 45 s
    python scripts/camera_diag.py --name Deck --hold 45

    # compare two cameras (run sequentially)
    python scripts/camera_diag.py --name Deck --name "Bedroom M3 Pro"

    # list cameras and exit
    python scripts/camera_diag.py --list

Requires the ``[webrtc]`` extra (aiortc/av/...).  Exit code is non-zero if any
selected camera produced no media.
"""

from __future__ import annotations

import argparse
import asyncio
import sys
import time

import aiohttp

from aidot.client import AidotClient
from aidot.const import CONF_DEVICE_LIST, CONF_NAME
from aidot.credentials import load_credentials


def _is_camera(device_client) -> bool:
    model = getattr(getattr(device_client, "info", None), "model_id", "") or ""
    return "IPC" in model


def _fmt_timeline(rel: list[float], dur: float) -> str:
    buckets: dict[int, int] = {}
    for r in rel:
        buckets[int(r)] = buckets.get(int(r), 0) + 1
    return " ".join(f"{buckets.get(s, 0):2d}" for s in range(int(dur) + 1))


async def _diag_one(client, device, hold: float) -> bool:
    name = device.get(CONF_NAME)
    dc = client.get_device_client(device)
    model = getattr(getattr(dc, "info", None), "model_id", "") or ""
    # Cloud-reported Wi-Fi signal (refreshed from the device-list properties).
    status = dc.update_status_from_device(device)
    rssi = getattr(status, "wifi_rssi", None)

    ts: list[float] = []
    t0 = time.time()
    session = None
    print(f"\n>>> {name!r} ({model})")
    print(f"    wifi_rssi: {rssi} dBm" if rssi is not None else "    wifi_rssi: unknown")
    try:
        session = await dc.async_open_webrtc_stream(
            on_frame=lambda _f: ts.append(time.time()),
            timeout=30.0,
            output_path=None,
            max_seconds=int(hold),
        )
        established = time.time() - t0
        print(f"    handshake: {established:.1f}s via {type(session).__name__}")
        await asyncio.sleep(hold)

        # Connection health (best-effort; SDES sessions have no .get_stats).
        stats = None
        if hasattr(session, "get_stats"):
            try:
                stats = await session.get_stats()
            except Exception as exc:  # pragma: no cover - defensive
                print(f"    get_stats error: {exc}")

        if ts:
            rel = [t - ts[0] for t in ts]
            dur = rel[-1] or 0.1
            print(f"    frames={len(ts)} span={dur:.1f}s avg_fps={len(ts)/dur:.1f} "
                  f"first_frame=+{ts[0]-t0:.1f}s")
            print(f"    fps/s: {_fmt_timeline(rel, dur)}")
            gaps = sorted(((rel[i+1]-rel[i], rel[i]) for i in range(len(rel)-1)),
                          reverse=True)[:3]
            if gaps:
                print("    largest gaps: " + ", ".join(
                    f"{g:.2f}s@t={at:.1f}s" for g, at in gaps))
        else:
            print("    frames=0 (NO MEDIA)")

        if stats:
            ice = stats.get("ice")
            if ice:
                for p in ice:
                    print(f"    ICE comp{p['component']}: "
                          f"{p['local_type']} {p['local']} <-> "
                          f"{p['remote_type']} {p['remote']} ({p['transport']})")
            else:
                print(f"    ICE: no nominated pair{' - ' + stats['ice_error'] if stats.get('ice_error') else ''}")
            for s in stats.get("inbound", []):
                print(f"    RTP {s['kind']:5}: recv={s['packets_received']} "
                      f"lost={s['packets_lost']} ({s['loss_pct']}%) jitter={s['jitter']}")
            if stats.get("error"):
                print(f"    stats error: {stats['error']}")

        ok = len(ts) > 0
        print(f"    => {'PASS' if ok else 'NO MEDIA'}")
        return ok
    except Exception as exc:
        print(f"    ERROR {type(exc).__name__}: {exc}")
        return False
    finally:
        if session is not None:
            try:
                await session.stop()
            except Exception:
                pass


async def _run(args) -> int:
    creds = load_credentials()
    async with aiohttp.ClientSession() as http:
        client = AidotClient(
            http,
            country_code=creds.get("country", "US"),
            username=creds["username"],
            password=creds["password"],
        )
        try:
            await client.async_post_login()
            devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
            cameras = [d for d in devices if _is_camera(client.get_device_client(d))]
            print(f"found {len(cameras)} camera(s) of {len(devices)} device(s)")
            for cam in cameras:
                dc = client.get_device_client(cam)
                model = getattr(getattr(dc, "info", None), "model_id", "") or ""
                print(f"  - {cam.get(CONF_NAME)!r:32} {model}")
            if args.list:
                return 0

            selected = cameras
            if args.name:
                wanted = [n.lower() for n in args.name]
                selected = [c for c in cameras
                            if any(w in (c.get(CONF_NAME) or "").lower() for w in wanted)]
            if not selected:
                print("no cameras matched --name"); return 1
            print(f"\ndiagnosing {len(selected)} camera(s) sequentially")
            results = [(c.get(CONF_NAME), await _diag_one(client, c, args.hold))
                       for c in selected]

            print("\n==== SUMMARY ====")
            for name, ok in results:
                print(f"  {'PASS' if ok else 'NO MEDIA'}  {name}")
            return 0 if results and all(ok for _, ok in results) else 1
        finally:
            await client.async_cleanup()


def main() -> int:
    p = argparse.ArgumentParser(
        description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--list", action="store_true", help="list cameras and exit")
    p.add_argument("--name", action="append", default=[],
                   help="diagnose only cameras whose name contains this (repeatable)")
    p.add_argument("--hold", type=float, default=45.0,
                   help="seconds to hold each stream (default 45)")
    args = p.parse_args()
    return asyncio.run(_run(args))


if __name__ == "__main__":
    sys.exit(main())
