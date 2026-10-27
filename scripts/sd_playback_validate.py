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
            # Room for the first-bytes wait, the reply and --controls (~45 s).
            max_seconds=args.seconds + 120,
            status_callback=status,
        )
        open_s = time.monotonic() - opened
        controls = {}
        try:
            # SDES returns on the first video packet; DTLS once connected, so
            # first media is when the recording file first grows.
            first_bytes_s = None
            give_up = time.monotonic() + 30
            while True:
                if _size(ts_path) > 0:
                    first_bytes_s = time.monotonic() - opened
                    break
                if time.monotonic() > give_up:
                    break
                await asyncio.sleep(0.1)
            reply = await session.playback_reply()
            await asyncio.sleep(args.seconds)
            if args.controls:
                controls = await _exercise_controls(session, ts_path, args.seek)
            stats = await _stats(session)
        finally:
            await session.stop()
        # From the open to the stop: what the recording can at most hold,
        # including the wait for the PLAY_START reply.
        wall = time.monotonic() - opened

    from aidot_cameras.camera.sd_playback import decode_playcontrol_reply

    result = {
        "record": rec.isoformat(),
        "transport": "SDES" if dc.is_sdes_camera else "DTLS",
        "open_returned_s": round(open_s, 2),
        "first_bytes_s": None if first_bytes_s is None else round(first_bytes_s, 2),
        "play_reply": str(
            decode_playcontrol_reply(bytes(reply.payload)) if reply else None
        ),
        "media_stats": stats,
        "decode": await LV._decode_probe(ts_path),
        "audio": await LV._audio_probe(ts_path, audio_pt=8),
        "timing": _gaps(ts_path),
        "wall_s": round(wall, 2),
        "controls": controls,
        # The tap's own account on DTLS: H265 payload type, dropped frames.
        "playback_status": [ln for ln in log if "SD playback" in ln],
        "clock_crops": _clock_crops(ts_path, args.out),
    }
    return result, log


def _size(path):
    try:
        return os.path.getsize(path)
    except OSError:
        return 0


async def _stats(session):
    """media_stats() on an SdesSession, await get_stats() on a WebRTCSession."""
    for name in ("media_stats", "get_stats"):
        fn = getattr(session, name, None)
        if fn is not None:
            try:
                out = fn()
                if asyncio.iscoroutine(out):
                    out = await out
                return out
            except Exception as exc:
                return {"error": repr(exc)}
    return None


async def _exercise_controls(session, ts_path, seek_s):
    """Pause 5 s, resume 5 s, seek to ``seek_s``, run 6 s: each command's reply
    and how much media arrived meanwhile (a pause should bring ~0).

    Counted in packets where the session counts them (SDES media_stats): the
    SDES file is written by ffmpeg in 256 KB chunks, too coarse for 5 s. On
    DTLS the raw H265 reaches no aiortc receiver to be counted, so it is the
    recording's bytes, which the recorder writes as they come.
    """
    out = {"unit": "packets" if hasattr(session, "media_stats") else "bytes"}

    def level():
        if out["unit"] == "packets":
            try:
                return int(session.media_stats().get("packets") or 0)
            except Exception:
                return 0
        return _size(ts_path)

    async def grew(seconds):
        a = level()
        await asyncio.sleep(seconds)
        return level() - a

    t = time.monotonic()
    out["pause"] = str(await session.async_sd_pause())
    out["pause_reply_s"] = round(time.monotonic() - t, 2)
    await asyncio.sleep(1)
    out["paused_5s"] = await grew(5)
    out["resume"] = str(await session.async_sd_resume())
    await asyncio.sleep(1)
    out["resumed_5s"] = await grew(5)
    out["seek"] = str(await session.async_sd_seek(seek_s))
    out["after_seek_6s"] = await grew(6)
    return out


def _clock_crops(ts_path, out_dir):
    """The bottom-left strip of the first and last frames, where the cameras
    burn in their clock - for reading the clip's real start and end."""
    crop = "crop=iw/3:ih/10:0:ih*9/10,scale=480:-1"
    made = {}
    for name, before_input in (("first", []), ("last", ["-sseof", "-3"])):
        dest = os.path.join(out_dir, f"clock_{name}.jpg")
        if os.path.exists(dest):
            os.remove(dest)  # never report an earlier run's crop as this one
        # "last": decode the final 3 s and keep overwriting one image, so
        # whatever decoded last is what remains.
        tail = ["-update", "1"] if name == "last" else ["-frames:v", "1"]
        subprocess.run(
            [
                "ffmpeg",
                "-v",
                "error",
                "-y",
                *before_input,
                "-i",
                ts_path,
                "-vf",
                crop,
                *tail,
                dest,
            ],
            capture_output=True,
            text=True,
            timeout=120,
        )
        if _size(dest) > 0:
            made[name] = dest
    return made


def main():
    p = argparse.ArgumentParser()
    p.add_argument("--device", required=True)
    p.add_argument("--out", required=True)
    p.add_argument("--seconds", type=float, default=30.0)
    p.add_argument("--days", type=int, default=7)
    p.add_argument("--index", type=int, default=-1)
    p.add_argument("--cooldown", type=float, default=180.0)
    p.add_argument(
        "--controls",
        action="store_true",
        help="after --seconds, pause 5 s, resume 5 s, seek to --seek, run 6 s;"
        " reports media that arrived in each window",
    )
    p.add_argument("--seek", type=float, default=20.0, help="seek target, seconds")
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
