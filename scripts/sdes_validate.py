#!/usr/bin/env python3
"""Automated A/B validator for the experimental SDES fast-liveplay flag (P5).

Speeds up validation: instead of waiting hours for organic camera opens, this
drives a few GENTLE, well-spaced opens (long holds + recovery gaps - SDES cameras
degrade on rapid reconnects, so this never hammers), alternating the flag per
open via the kwarg, and reports a structured comparison:

  signaling cost (echo + livePlayResp waits)  DETERMINISTIC - the real signal P5 changes
  established time                             noisy (battery wake) - context only
  media / stall                                did the stream hold without stalling?

Stability caveat: a stall on a BATTERY camera (A001513) is usually its normal
~60-72 s teardown, not flag-induced churn - use a MAINS SDES camera (A001064,
e.g. the PTZ) to read stability cleanly. The signaling cost is clean on any SDES.

Run with HA stopped or cameras otherwise idle to avoid contention. Uses the
public library API, like smoke_stream.py - no extra setup.

  python scripts/sdes_validate.py --name Driveway --cycles 4 --hold 95 --gap 45
  python scripts/sdes_validate.py --name "Garage PTZ" --cycles 4   # clean stability
"""
import argparse
import asyncio
import logging
import os
import re
import time

import aiohttp

from aidot.client import AidotClient
from aidot.const import CONF_DEVICE_LIST, CONF_NAME
from aidot.credentials import load_credentials


class _Collector(logging.Handler):
    """Captures the library's signaling-wait[...] INFO lines for the current open."""

    def __init__(self):
        super().__init__()
        self.lines = []

    def emit(self, record):
        try:
            msg = record.getMessage()
            if "signaling-wait[" in msg:
                self.lines.append(msg)
        except Exception:
            pass


async def _cycle(dc, flag, hold, tag, coll):
    coll.lines.clear()
    dc._sdes_fast_liveplay_opt = flag
    out = f"/tmp/sdesval_{tag}.ts"
    if os.path.exists(out):
        os.remove(out)
    rec = {"tag": tag, "flag": flag, "established": None, "echo_ms": None,
           "resp_ms": None, "skipped": False, "media": False,
           "stall_at": None, "bytes": 0, "error": None}
    t0 = time.time()
    sess = None
    try:
        sess = await dc.async_open_webrtc_stream(
            on_frame=lambda _f: None, timeout=45.0,
            output_path=out, max_seconds=hold)
        rec["established"] = round(time.time() - t0, 1)
        last_sz, last_grow, start = 0, time.time(), time.time()
        while time.time() - start < hold:
            await asyncio.sleep(5)
            sz = os.path.getsize(out) if os.path.exists(out) else 0
            if sz > last_sz + 2000:
                last_grow, last_sz = time.time(), sz
            if sz > 20000:
                rec["media"] = True
            if rec["media"] and rec["stall_at"] is None and time.time() - last_grow > 15:
                rec["stall_at"] = round(time.time() - start)
        rec["bytes"] = os.path.getsize(out) if os.path.exists(out) else 0
    except Exception as exc:
        rec["error"] = f"{type(exc).__name__}: {exc}"
    finally:
        if sess is not None:
            try:
                await sess.stop()
            except Exception:
                pass
    for ln in coll.lines:
        m = re.search(r"livePlayReq-echo elapsed=(\d+)ms", ln)
        if m:
            rec["echo_ms"] = int(m.group(1))
        m = re.search(r"livePlayResp elapsed=(\d+)ms", ln)
        if m:
            rec["resp_ms"] = int(m.group(1))
        if "livePlayResp skipped" in ln:
            rec["skipped"] = True
    return rec


def _summary(recs):
    print("\n" + "=" * 78)
    print(f"{'cycle':14} {'estab':>6} {'echo':>7} {'resp':>10} {'media':>6} "
          f"{'stall@':>7} {'bytes':>9}")
    print("-" * 78)
    for r in recs:
        resp = "skipped" if r["skipped"] else (f"{r['resp_ms']}ms" if r["resp_ms"] is not None else "-")
        echo = f"{r['echo_ms']}ms" if r["echo_ms"] is not None else "-"
        stall = f"{r['stall_at']}s" if r["stall_at"] is not None else "-"
        est = f"{r['established']}s" if r["established"] is not None else (r["error"] or "?")
        print(f"{r['tag']:14} {est:>6} {echo:>7} {resp:>10} "
              f"{'yes' if r['media'] else 'NO':>6} {stall:>7} {r['bytes']:>9}")
    print("-" * 78)

    def agg(flag):
        rs = [r for r in recs if r["flag"] is flag and r["error"] is None]
        if not rs:
            return None
        sig = [(r["echo_ms"] or 0) + (0 if r["skipped"] else (r["resp_ms"] or 0)) for r in rs]
        media = sum(1 for r in rs if r["media"])
        stalls = sum(1 for r in rs if r["stall_at"] is not None)
        return (len(rs), round(sum(sig) / len(sig)), media, stalls)

    off, on = agg(False), agg(True)
    print("VERDICT (deterministic signal = signaling cost):")
    for label, a in (("flag OFF", off), ("flag ON ", on)):
        if a:
            n, sig, media, stalls = a
            print(f"  {label}: n={n}  signaling~{sig}ms  media {media}/{n}  stalls {stalls}/{n}")
    if off and on:
        saved = off[1] - on[1]
        print(f"  => signaling saved by flag: ~{saved}ms "
              f"({'GOOD' if saved > 500 else 'negligible'})")
        if on[3] > off[3]:
            print("  => WARNING: more stalls with flag ON (possible churn - watch on a mains camera)")
        else:
            print("  => no extra stalls with flag ON in this run (stability needs a longer/mains soak)")


async def _run(args):
    creds = load_credentials()
    coll = _Collector()
    lg = logging.getLogger("aidot.camera.client")
    lg.setLevel(logging.INFO)
    lg.addHandler(coll)
    async with aiohttp.ClientSession() as session:
        client = AidotClient(session, country_code=creds.get("country", "US"),
                             username=creds["username"], password=creds["password"])
        await client.async_post_login()
        devs = (await client.async_get_all_device())[CONF_DEVICE_LIST]
        dc = next((client.get_device_client(d) for d in devs
                   if args.name.lower() in (d.get(CONF_NAME) or "").lower()), None)
        if dc is None:
            raise SystemExit(f"camera not found: {args.name!r}")
        recs = []
        for i in range(args.cycles):
            flag = bool(i % 2)  # alternate OFF, ON, OFF, ON, ...
            tag = f"{'ON' if flag else 'OFF'}#{i // 2 + 1}"
            print(f"\n>>> cycle {i + 1}/{args.cycles}  {tag}  (hold {args.hold}s)", flush=True)
            r = await _cycle(dc, flag, args.hold, tag, coll)
            print(f"    {r}", flush=True)
            recs.append(r)
            if i < args.cycles - 1:
                print(f"    --- recovery gap {args.gap}s ---", flush=True)
                await asyncio.sleep(args.gap)
        _summary(recs)


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--name", required=True, help="camera name substring (SDES)")
    ap.add_argument("--cycles", type=int, default=4, help="opens, alternating flag")
    ap.add_argument("--hold", type=int, default=95, help="seconds to hold each open (>90 clears churn window)")
    ap.add_argument("--gap", type=int, default=45, help="recovery gap between opens")
    asyncio.run(_run(ap.parse_args()))


if __name__ == "__main__":
    main()
