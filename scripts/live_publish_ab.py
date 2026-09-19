#!/usr/bin/env python3
"""Live A/B of direct publish (AIDOT_DIRECT_PUBLISH) against real cameras.

The validation gate for docs/DESIGN-direct-publish.md. ``live_validate.py``
records through ``output_path``, which deliberately keeps ffmpeg, so it never
exercises the direct publisher; this does. Run it on the camera LAN against a
go2rtc you control (not HA's bundled one - its API is on a unix socket):

    AIDOT_USERNAME=... AIDOT_PASSWORD=... \\
    python scripts/live_publish_ab.py --go2rtc http://127.0.0.1:1984 \\
        --rtsp-port 8554 --view-s 30 --soak-s 1800 --repeats 2

Per camera it alternates arms (``ffmpeg`` = flag off, ``direct`` = flag on),
waiting out the camera's ~120 s viewer-slot hold between opens, and for each
open measures what the integration relies on:

- ``attach_s``    start_keepalive -> go2rtc shows a pushed producer (the
                  integration's ``_await_publisher_attached``);
- ``first_frame_s`` / ``frames`` / ``fps`` / ``codecs``  a real RTSP consumer
                  (PyAV) reading the stream back from go2rtc;
- during ``--soak-s``: publisher churn (producer disappearing), this
                  process's thread and fd counts, and live ffmpeg children
                  (must be 0 for a direct-publish SDES/DTLS live view).

It stops the session between arms and removes the go2rtc stream it made.
Writes ``--report`` (JSON). Exit 0 when every direct-publish open attached and
decoded at least ``--min-frames``; the ffmpeg arm is the baseline, reported
but never failing the run.

Nothing here touches Home Assistant. Close the HA integration's sessions (or
stop HA) first: the camera allows few viewers and answers -50002 when busy.
On a shared (non-owner) account, as CI uses, set AIDOT_INCLUDE_SHARED_HOUSES=1
or no cameras are found.
"""

from __future__ import annotations

import argparse
import asyncio
import json
import os
import sys
import threading
import time

import aiohttp

from aidot_cameras.camera.go2rtc import Go2rtcClient
from aidot_cameras.cloud_auth import _make_client
from aidot_cameras.const import CONF_DEVICE_LIST, CONF_ID, CONF_NAME
from aidot_cameras.credentials import load_credentials

#: The camera holds a viewer slot ~120 s after a session ends (CAMERAS.md).
SLOT_HOLD_S = 125.0
ARMS = ("ffmpeg", "direct")


def _model(dc) -> str:
    return getattr(getattr(dc, "info", None), "model_id", "") or ""


def _is_camera(dc) -> bool:
    return "IPC" in _model(dc)


def _ffmpeg_children() -> int:
    """Live ffmpeg processes whose parent is this process."""
    me, n = os.getpid(), 0
    for pid in os.listdir("/proc"):
        if not pid.isdigit():
            continue
        try:
            with open(f"/proc/{pid}/stat") as f:
                stat = f.read()
            comm = stat[stat.index("(") + 1 : stat.rindex(")")]
            ppid = int(stat[stat.rindex(")") + 2 :].split()[1])
        except (OSError, ValueError):
            continue
        if ppid == me and comm.startswith("ffmpeg"):
            n += 1
    return n


def _fds() -> int:
    try:
        return len(os.listdir("/proc/self/fd"))
    except OSError:
        return -1


async def _publisher_attached(g2: Go2rtcClient, http, name: str) -> bool:
    try:
        async with http.get(
            f"{g2.base_url}/api/streams", params={"src": name}, timeout=3
        ) as r:
            if r.status != 200:
                return False
            info = await r.json(content_type=None)
    except Exception:
        return False
    return any(not p.get("url") for p in (info or {}).get("producers") or [])


def _read_back(url: str, view_s: float, hold_s: float = 0.0) -> dict:
    """A real consumer: decode video from go2rtc for ``view_s`` seconds, then
    stay attached for ``hold_s`` more, counting packets without decoding.

    Staying attached matters for a soak: with no viewer, the library's idle
    release ends the session, which would read as publisher churn. The hold
    also records the longest gap between video packets (``max_gap_s``) - a
    stall a viewer would see."""
    import av

    out: dict = {"frames": 0, "hold_packets": 0, "max_gap_s": 0.0}
    t0 = time.monotonic()
    try:
        c = av.open(url, options={"rtsp_transport": "tcp", "timeout": "15000000"})
    except Exception as exc:
        out["error"] = repr(exc)
        return out
    try:
        out["codecs"] = sorted(s.codec_context.name for s in c.streams)
        video = c.streams.video[0]
        for _frame in c.decode(video):
            if out["frames"] == 0:
                out["first_frame_s"] = round(time.monotonic() - t0, 2)
            out["frames"] += 1
            if time.monotonic() - t0 >= view_s:
                break
        span = time.monotonic() - t0 - out.get("first_frame_s", 0)
        out["fps"] = round(out["frames"] / span, 2) if span > 0 else 0
        if hold_s > 0:
            last = time.monotonic()
            end = last + hold_s
            for _pkt in c.demux(video):
                now = time.monotonic()
                out["max_gap_s"] = max(out["max_gap_s"], round(now - last, 2))
                last = now
                out["hold_packets"] += 1
                if now >= end:
                    break
    except Exception as exc:
        out["error"] = repr(exc)
    finally:
        c.close()
    out.setdefault("fps", 0)
    return out


async def _one_open(dc, g2, http, arm, args, soak_s: float) -> dict:
    name = f"aidot_{dc.device_id[:12]}"
    rtsp_url = g2.rtsp_url(name, args.rtsp_port)
    if arm == "direct":
        os.environ["AIDOT_DIRECT_PUBLISH"] = "1"
    else:
        os.environ.pop("AIDOT_DIRECT_PUBLISH", None)
    res: dict = {
        "arm": arm,
        "threads_before": threading.active_count(),
        "fds_before": _fds(),
    }
    # The integration's order: the stream must exist before a publish, with an
    # inert placeholder source (go2rtc cannot create an empty stream).
    await g2.ensure_stream(name, "http://127.0.0.1:1/placeholder.ts")
    t0 = time.monotonic()
    try:
        await dc.start_keepalive(
            rtsp_push_url=rtsp_url, go2rtc_url=g2.base_url, go2rtc_register=False
        )
        deadline = t0 + args.attach_budget_s
        while time.monotonic() < deadline:
            if await _publisher_attached(g2, http, name):
                res["attach_s"] = round(time.monotonic() - t0, 2)
                break
            await asyncio.sleep(0.25)
        if "attach_s" not in res:
            res["error"] = f"no publisher within {args.attach_budget_s:.0f}s"
            return res
        res["ffmpeg_children_live"] = _ffmpeg_children()
        reader = asyncio.get_running_loop().run_in_executor(
            None, _read_back, rtsp_url, args.view_s, soak_s
        )
        if soak_s:
            # Sample while the reader holds the stream (it runs view_s longer).
            await asyncio.sleep(args.view_s)
            res["soak"] = await _soak(g2, http, name, soak_s)
        res.update(await reader)
        if args.idle_check:
            res["idle_release"] = await _idle_release(dc, g2, http, name, args)
    finally:
        await dc.async_stop_streaming()
        await asyncio.sleep(3)
        res["threads_after"] = threading.active_count()
        res["fds_after"] = _fds()
        res["ffmpeg_children_after"] = _ffmpeg_children()
        await g2.remove_stream(name)
    return res


async def _idle_release(dc, g2, http, name, args) -> dict:
    """With the viewer gone and the session NOT stopped, time how long the
    library takes to release it on its own - the idle release that decides how
    long a camera (a battery camera especially) stays awake after a view.

    Released = go2rtc no longer lists a pushed producer AND the client reports
    no live stream URL. Expected: the idle window (AIDOT_STREAM_IDLE_S, default
    120 s) plus the watchdog's polling tick.
    """
    t0 = time.monotonic()
    deadline = t0 + args.idle_budget_s
    while time.monotonic() < deadline:
        attached = await _publisher_attached(g2, http, name)
        if not attached and getattr(dc, "stream_rtsp_url", None) is None:
            return {"released_after_s": round(time.monotonic() - t0, 1)}
        await asyncio.sleep(2)
    return {
        "released_after_s": None,
        "note": f"still up after {args.idle_budget_s:.0f}s",
    }


async def _soak(g2, http, name, soak_s) -> dict:
    samples, lost, t_end = [], 0, time.monotonic() + soak_s
    was_up = True
    while time.monotonic() < t_end:
        await asyncio.sleep(10)
        up = await _publisher_attached(g2, http, name)
        if was_up and not up:
            lost += 1
        was_up = up
        samples.append(
            {
                "t": round(soak_s - (t_end - time.monotonic())),
                "up": up,
                "threads": threading.active_count(),
                "fds": _fds(),
                "ffmpeg": _ffmpeg_children(),
            }
        )
    return {
        "publisher_lost": lost,
        "uptime_pct": round(
            100 * sum(s["up"] for s in samples) / max(1, len(samples)), 1
        ),
        "threads_max": max((s["threads"] for s in samples), default=0),
        "fds_max": max((s["fds"] for s in samples), default=0),
        "ffmpeg_max": max((s["ffmpeg"] for s in samples), default=0),
        "samples": samples,
    }


def _passes(res: dict, min_frames: int) -> bool:
    ok = "attach_s" in res and "error" not in res and res.get("frames", 0) >= min_frames
    if "idle_release" in res:
        # A session the library never lets go of is the battery-drain failure.
        ok = ok and res["idle_release"].get("released_after_s") is not None
    if res.get("arm") == "direct":
        # The point of the arm: no ffmpeg anywhere in a live view.
        ok = (
            ok
            and res.get("ffmpeg_children_live", 1) == 0
            and res.get("soak", {}).get("ffmpeg_max", 0) == 0
        )
    return ok


def _write_report(path: str, report: dict) -> None:
    with open(path, "w") as f:
        json.dump(report, f, indent=2)


async def _camera(client, cam, g2, http, args, gate) -> tuple:
    """Every open for one camera, in order; returns ``(entry, ok)``.

    Opens of the SAME camera stay sequential and SLOT_HOLD_S apart (the
    camera's viewer-slot hold). Different cameras may overlap, up to
    ``--parallel`` at once - one login is shared, and the library's own stream
    cap still applies.
    """
    dc = client.get_device_client(cam)
    battery = bool(getattr(dc, "is_battery_camera", False))
    soak_s = args.soak_s
    if soak_s and battery and args.battery_soak_s is not None:
        soak_s = min(soak_s, args.battery_soak_s)
    entry = {
        "camera": cam["_label"],
        "model": _model(dc),
        "battery": battery,
        "soak_s": soak_s,
        "opens": [],
    }
    if args.show_names:
        entry["name"] = cam.get(CONF_NAME)
        entry["device_id"] = cam.get(CONF_ID)
    tag = f"[{entry['camera']}]"
    ok = True
    arms = tuple(a for a in ARMS if a in args.arms)
    async with gate:
        print(f"== {tag} ({entry['model']}{', battery' if battery else ''})")
        for rep in range(args.repeats):
            for arm in arms if rep % 2 == 0 else tuple(reversed(arms)):
                if entry["opens"]:
                    print(f"   {tag} waiting {SLOT_HOLD_S:.0f}s for the viewer slot")
                    await asyncio.sleep(SLOT_HOLD_S)
                res = await _one_open(dc, g2, http, arm, args, soak_s)
                res["pass"] = _passes(res, args.min_frames)
                entry["opens"].append(res)
                soak = res.get("soak") or {}
                print(
                    f"   {tag} {arm:6} attach={res.get('attach_s')}s"
                    f" first_frame={res.get('first_frame_s')}s"
                    f" frames={res.get('frames')} fps={res.get('fps')}"
                    f" codecs={res.get('codecs')}"
                    f" ffmpeg={res.get('ffmpeg_children_live')}"
                    + (
                        f" soak={soak_s:.0f}s lost={soak.get('publisher_lost')}"
                        f" up={soak.get('uptime_pct')}%"
                        f" max_gap={res.get('max_gap_s')}s"
                        f" threads<={soak.get('threads_max')}"
                        f" fds<={soak.get('fds_max')}"
                        if soak
                        else ""
                    )
                    + (
                        f" idle_release={res['idle_release'].get('released_after_s')}s"
                        if "idle_release" in res
                        else ""
                    )
                    + f" {'PASS' if res['pass'] else 'FAIL'}"
                    + (f" error={res['error']}" if "error" in res else "")
                )
                if arm == "direct" and not res["pass"]:
                    ok = False
    return entry, ok


async def _run(args) -> int:
    creds = load_credentials()
    report: dict = {
        "started_utc": time.strftime("%Y-%m-%dT%H:%M:%SZ", time.gmtime()),
        "go2rtc": args.go2rtc,
        "cameras": [],
    }
    async with aiohttp.ClientSession() as http:
        g2 = Go2rtcClient(http, args.go2rtc)
        if not await g2.available():
            print(f"go2rtc API not reachable at {args.go2rtc}", file=sys.stderr)
            return 2
        client = await _make_client(
            http,
            username=creds["username"],
            password=creds["password"],
            country=creds.get("country", "US"),
        )
        devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
        cams = [d for d in devices if _is_camera(client.get_device_client(d))]
        # Labelled before any --name filter, so an index is stable across runs.
        # Anonymous labels by default ("A001513 #2"): the output is what ends
        # up pasted into docs and issues, and camera names are the user's own
        # room/location names. --show-names is for a private debugging session.
        seen: dict = {}
        for cam in cams:
            key = _model(client.get_device_client(cam)).rsplit(".", 1)[-1] or "camera"
            seen[key] = seen.get(key, 0) + 1
            cam["_label"] = f"{key} #{seen[key]}"
            if args.show_names:
                cam["_label"] += f" ({cam.get(CONF_NAME)})"
        if args.model:
            want_m = [m.upper() for m in args.model]
            cams = [
                c
                for c in cams
                if any(m in _model(client.get_device_client(c)).upper() for m in want_m)
            ]
        if args.name:
            want = [n.lower() for n in args.name]
            cams = [
                c
                for c in cams
                if any(w in (c.get(CONF_NAME) or "").lower() for w in want)
            ]
        gate = asyncio.Semaphore(max(1, args.parallel))
        results = await asyncio.gather(
            *(_camera(client, cam, g2, http, args, gate) for cam in cams)
        )
        report["cameras"] = [entry for entry, _ok in results]
        ok = all(_ok for _entry, _ok in results)
    report["verdict"] = "PASS" if ok and report["cameras"] else "FAIL"
    await asyncio.get_running_loop().run_in_executor(
        None, _write_report, args.report, report
    )
    print(f"\n{report['verdict']} - report: {args.report}")
    return 0 if report["verdict"] == "PASS" else 1


def main() -> int:
    p = argparse.ArgumentParser(
        description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter
    )
    p.add_argument("--go2rtc", default="http://127.0.0.1:1984", help="go2rtc API base")
    p.add_argument("--rtsp-port", type=int, default=8554)
    p.add_argument("--name", action="append", help="camera name substring (repeatable)")
    p.add_argument(
        "--model",
        action="append",
        help="model id substring, e.g. A001513 (repeatable); selects cameras without"
        " naming them",
    )

    p.add_argument("--repeats", type=int, default=1, help="A/B pairs per camera")
    p.add_argument("--view-s", type=float, default=30.0)
    p.add_argument("--soak-s", type=float, default=0.0)
    p.add_argument("--attach-budget-s", type=float, default=75.0)
    p.add_argument("--min-frames", type=int, default=60)
    p.add_argument(
        "--arms",
        default="ffmpeg,direct",
        type=lambda v: tuple(a.strip() for a in v.split(",") if a.strip()),
        help="which arms to run (a soak usually wants just: direct)",
    )
    p.add_argument(
        "--battery-soak-s",
        type=float,
        default=300.0,
        help="cap the soak for battery cameras (default 300); a long session is"
        " not how they are used and costs battery",
    )
    p.add_argument(
        "--parallel",
        type=int,
        default=1,
        help="cameras run at once (opens of one camera stay sequential); needs a"
        " single --arms value, because the direct/ffmpeg switch is process-wide",
    )
    p.add_argument(
        "--idle-check",
        action="store_true",
        help="after the view, leave the session running with no viewer and time"
        " how long the library takes to release it (the battery-drain question)",
    )
    p.add_argument(
        "--idle-budget-s",
        type=float,
        default=240.0,
        help="give up waiting for the idle release after this long (a FAIL)",
    )
    p.add_argument(
        "--show-names",
        action="store_true",
        help="print and record camera names and device ids (default: model + index"
        " only, so the output can be shared without the camera owner's details)",
    )
    p.add_argument("--report", default="/tmp/aidot-publish-ab.json")
    args = p.parse_args()
    if args.parallel > 1 and len(args.arms) != 1:
        p.error("--parallel > 1 needs exactly one --arms value (e.g. --arms direct)")
    return asyncio.run(_run(args))


if __name__ == "__main__":
    sys.exit(main())
