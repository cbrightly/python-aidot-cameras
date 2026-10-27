"""Live check: does async_open_sd_playback stream a recording smoothly?

1. A short live session lists the card (async_get_sd_recordings).
2. After the cooldown (one media slot per camera), async_open_sd_playback plays
   the chosen record through the library's own pipeline into an MPEG-TS file.
3. The file is decoded and its packet timing measured: video frames, frame
   rate, largest frame gap; audio seconds against wall time, largest gap.

Nothing else may hold the camera's media slot while this runs: stop Home
Assistant's AiDot entry first.

  AIDOT_TOKEN_FILE=~/.config/aidot/token.json \
  python scripts/sd_playback_validate.py --device 12b144cb --out /tmp/sdplay

Measured on an A001064 2026-10-27: HEVC 2560x1440 at 20 fps, audio
continuous, 0 decode errors once the serve kept the clip's opening packets;
the first served frame matches the camera's own first frame. Short holes in
both tracks near a clip's start (0.4-0.5 s, same offsets on every play) are in
the recordings.
"""

import argparse
import asyncio
import itertools
import json
import os
import statistics
import subprocess
import sys
import time

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

import aiohttp

import live_validate as LV
from aidot_cameras.cloud_auth import _make_client
from aidot_cameras.const import CONF_DEVICE_LIST
from aidot_cameras.credentials import load_credentials


def _gaps(path):
    """Per codec type: packet count, span, median and largest PTS gap."""
    r = subprocess.run(
        [
            "ffprobe",
            "-v",
            "error",
            "-show_entries",
            "packet=stream_index,pts_time:stream=index,codec_type,codec_name",
            "-of",
            "json",
            path,
        ],
        capture_output=True,
        text=True,
        timeout=120,
    )
    data = json.loads(r.stdout or "{}")
    kinds = {
        s["index"]: f"{s.get('codec_type')}/{s.get('codec_name')}"
        for s in data.get("streams", [])
    }
    pts = {}
    for p in data.get("packets", []):
        t = p.get("pts_time")
        if t is not None:
            pts.setdefault(kinds.get(p["stream_index"], "?"), []).append(float(t))
    out = {}
    for kind, ts in pts.items():
        ts.sort()
        d = [b - a for a, b in itertools.pairwise(ts)]
        out[kind] = {
            "packets": len(ts),
            "span_s": round(ts[-1] - ts[0], 3) if ts else 0,
            "median_gap_ms": round(statistics.median(d) * 1000, 1) if d else None,
            "max_gap_ms": round(max(d) * 1000, 1) if d else None,
            "gaps_over_250ms": sum(1 for g in d if g > 0.25),
        }
    return out


async def run(args):
    creds = load_credentials()
    os.makedirs(args.out, exist_ok=True)
    log = []

    def status(m):
        log.append(f"[{time.monotonic() - t0:8.3f}] {m}")

    t0 = time.monotonic()
    async with aiohttp.ClientSession() as http:
        client = await _make_client(
            http,
            username=creds["username"],
            password=creds["password"],
            country=creds.get("country", "US"),
        )
        devs = (await client.async_get_all_device())[CONF_DEVICE_LIST]
        dev = next((d for d in devs if d.get("id", "").startswith(args.device)), None)
        if dev is None:
            raise SystemExit(f"no device whose id starts with {args.device!r}")
        dc = client.get_device_client(dev)

        live = await dc.async_open_webrtc_stream(
            timeout=45.0, max_seconds=60, status_callback=status
        )
        try:
            await asyncio.sleep(3)
            listing = await dc.async_get_sd_recordings(session=live, days=args.days)
        finally:
            await live.stop()
        recs = list(listing.records) if listing else []
        print(
            f"listing: answered={getattr(listing, 'answered', None)} records={len(recs)}"
        )
        if not recs:
            return None, log
        if not -len(recs) <= args.index < len(recs):
            raise SystemExit(
                f"--index {args.index}: the card lists {len(recs)} records"
            )
        rec = recs[args.index]
        print(
            f"playing {rec.isoformat()} event={rec.event}; cooldown {args.cooldown:.0f}s"
        )
        await asyncio.sleep(args.cooldown)

        ts_path = os.path.join(args.out, "playback.ts")
        opened = time.monotonic()
        session = await dc.async_open_sd_playback(
            rec,
            output_path=ts_path,
            max_seconds=args.seconds + 30,
            status_callback=status,
        )
        first_media_s = time.monotonic() - opened
        try:
            reply = await session.playback_reply()
            await asyncio.sleep(args.seconds)
            stats = session.media_stats()
        finally:
            await session.stop()
        # From the open to the stop: what the recording can at most hold,
        # including the wait for the PLAY_START reply.
        wall = time.monotonic() - opened

    from aidot_cameras.camera.sd_playback import decode_playcontrol_reply

    result = {
        "record": rec.isoformat(),
        "open_to_first_media_s": round(first_media_s, 2),
        "play_reply": str(
            decode_playcontrol_reply(bytes(reply.payload)) if reply else None
        ),
        "media_stats": stats,
        "decode": await LV._decode_probe(ts_path),
        "audio": await LV._audio_probe(ts_path, audio_pt=8),
        "timing": _gaps(ts_path),
        "wall_s": round(wall, 2),
    }
    return result, log


def main():
    p = argparse.ArgumentParser()
    p.add_argument("--device", required=True)
    p.add_argument("--out", required=True)
    p.add_argument("--seconds", type=float, default=30.0)
    p.add_argument("--days", type=int, default=7)
    p.add_argument("--index", type=int, default=-1)
    p.add_argument("--cooldown", type=float, default=180.0)
    args = p.parse_args()
    result, log = asyncio.run(run(args))
    if result is None:
        sys.exit(2)
    with open(os.path.join(args.out, "result.json"), "w") as fh:
        json.dump(result, fh, indent=1, default=str)
    with open(os.path.join(args.out, "log.txt"), "w") as fh:
        fh.write("\n".join(log))
    print(json.dumps(result, indent=1, default=str))
    sys.exit(0)


if __name__ == "__main__":
    main()
