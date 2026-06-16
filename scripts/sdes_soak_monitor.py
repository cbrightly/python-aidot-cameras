#!/usr/bin/env python3
"""Passive soak monitor for SDES cold-start latency and stability.

Validates the experimental P5 flag (AIDOT_SDES_FAST_LIVEPLAY) the only way that
actually works: by watching REAL usage over time, not a synthetic A/B (rapid
synthetic reconnects degrade SDES cameras, which is exactly the failure mode the
library's reconnect gate guards against).

It reads Home Assistant logs (which carry the library's `cold-start[...]` markers
and SDES keepalive messages), accumulates per-camera metrics, and prints a
summary. Run it once with the flag OFF for a while, once with it ON, and compare:

  * cold-start latency  -> did P5 shave time off?           (lower is better)
  * restarts / failures -> did P5 destabilise the session?  (higher is worse)

Usage
-----
  # one-shot over the current HA log buffer (remote box)
  ssh root@homeassistant 'ha core logs' | python scripts/sdes_soak_monitor.py

  # live soak: stream logs and print a summary every 60s and on Ctrl-C
  ssh root@homeassistant 'ha core logs --follow' | python scripts/sdes_soak_monitor.py --follow

  # from a captured file
  python scripts/sdes_soak_monitor.py soak.log

  # friendlier output: map device ids to names (repeatable)
  ... | python scripts/sdes_soak_monitor.py --name b5284fc70d1e=Kitchen --name 12b144cb12da="Garage PTZ"

No camera or cloud access; pure log parsing. Reads stdin if no file is given.
"""
import argparse
import re
import sys
import time
from collections import defaultdict

# library log signatures (see src/aidot/camera/client.py)
_ANSI = re.compile(r"\x1b\[[0-9;]*m")
_TS = re.compile(r"(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})")
_COLD = re.compile(r"cold-start\[([0-9a-f]+)\] (.+?) \+(\d+)ms")
_OPEN_FAIL = re.compile(r"SDES keepalive: stream open failed for ([0-9a-f]+)")
_WATCHDOG = re.compile(r"SDES ([0-9a-f]+): no media in watchdog window - restarting")
_IDLE_REL = re.compile(r"SDES serve: ([0-9a-f]+) idle \(no viewer\)")
_FLAG_ON = re.compile(r"AIDOT_SDES_FAST_LIVEPLAY: skipping livePlayResp")


def _pct(vals, p):
    if not vals:
        return None
    s = sorted(vals)
    return s[min(len(s) - 1, round((p / 100.0) * (len(s) - 1)))]


class Stats:
    def __init__(self):
        self.serving_ms = []      # open -> serving (headline cold-start)
        self.firstmedia_ms = []   # open -> first decrypted media
        self.opens = 0            # webrtcReq markers (one per open attempt)
        self.open_fail = 0        # SDES open failures
        self.watchdog = 0         # no-media-watchdog restarts (instability)
        self.idle_release = 0     # normal no-viewer releases (NOT instability)


def parse(lines, names):
    cams = defaultdict(Stats)
    flag_seen = 0
    t_first = t_last = None
    for raw in lines:
        line = _ANSI.sub("", raw)
        mts = _TS.search(line)
        if mts:
            t_last = mts.group(1)
            if t_first is None:
                t_first = t_last
        if _FLAG_ON.search(line):
            flag_seen += 1
        m = _COLD.search(line)
        if m:
            dev, label, ms = m.group(1), m.group(2), int(m.group(3))
            st = cams[dev]
            if label.startswith("webrtcReq"):
                st.opens += 1
            elif label == "first-media":
                st.firstmedia_ms.append(ms)
            elif label.startswith("serving"):
                st.serving_ms.append(ms)
            continue
        for rgx, attr in ((_OPEN_FAIL, "open_fail"), (_WATCHDOG, "watchdog"),
                          (_IDLE_REL, "idle_release")):
            m = rgx.search(line)
            if m:
                setattr(cams[m.group(1)], attr, getattr(cams[m.group(1)], attr) + 1)
                break
    return cams, flag_seen, t_first, t_last


def report(cams, flag_seen, t_first, t_last, names):
    print("=" * 74)
    print(f"SDES soak summary  |  flag active in this window: "
          f"{'YES' if flag_seen else 'no'} ({flag_seen} skip-logs)")
    if t_first:
        print(f"window: {t_first}  ->  {t_last or '?'}")
    print("=" * 74)
    if not cams:
        print("no SDES cold-start markers found.\n"
              "  - need library >=0.7.19 (cold-start[...] markers) on the box\n"
              "  - did any camera actually stream during the window?")
        return
    hdr = (f"{'camera':24} {'opens':>5} {'cold-start ms (med/p90/max)':>28} "
           f"{'fail':>5} {'churn':>6}")
    print(hdr)
    print("-" * len(hdr))
    for dev in sorted(cams):
        st = cams[dev]
        name = names.get(dev[:12], dev[:12])
        sv = st.serving_ms
        lat = (f"{_pct(sv,50)}/{_pct(sv,90)}/{max(sv)}" if sv else "-")
        # churn = instability-driven restarts (watchdog), NOT normal idle release
        print(f"{name:24} {st.opens:>5} {lat:>28} {st.open_fail:>5} "
              f"{st.watchdog:>6}")
    print("-" * len(hdr))
    print("cold-start ms = open -> ffmpeg serving (lower is better).")
    print("fail = SDES open failures; churn = no-media watchdog restarts "
          "(higher = less stable). idle releases are not counted as churn.")
    print("Compare a flag-OFF window vs a flag-ON window: P5 is good only if "
          "cold-start drops WITHOUT churn/fail rising.")


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("logfile", nargs="?", help="log file (default: stdin)")
    ap.add_argument("--follow", action="store_true",
                    help="stream stdin, print a summary every --interval s")
    ap.add_argument("--interval", type=int, default=60)
    ap.add_argument("--name", action="append", default=[],
                    help="devid12=Name (repeatable) for friendlier output")
    args = ap.parse_args()
    names = dict(n.split("=", 1) for n in args.name if "=" in n)

    if args.logfile:
        with open(args.logfile, encoding="utf-8", errors="replace") as f:
            report(*parse(f, names), names=names)
        return

    if not args.follow:
        report(*parse(sys.stdin, names), names=names)
        return

    # live: accumulate, summarise periodically and on EOF/interrupt
    buf = []
    last = time.monotonic()
    try:
        for line in sys.stdin:
            buf.append(line)
            if time.monotonic() - last >= args.interval:
                report(*parse(buf, names), names=names)
                last = time.monotonic()
    except KeyboardInterrupt:
        pass
    report(*parse(buf, names), names=names)


if __name__ == "__main__":
    main()
