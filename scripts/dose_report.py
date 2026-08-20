"""Score an ABAB dose-response run: answer latency per block.

Reads block boundaries from the runner's own log so the blocks are defined by
what actually happened, not by arithmetic on a start time.

Reports median and p90 answer latency per block, then the two comparisons that
matter and are NOT the same question:
  - dose effect : B vs A, required to hold on BOTH cycles
  - time trend  : cycle 2 vs cycle 1 within the same arm
A ramp cannot separate these; that is why the run alternates.
"""
import datetime
import re
import statistics
import sys

TS = re.compile(r'^(2026-\d\d-\d\d \d\d:\d\d:\d\d\.\d{3})')

def blocks(path):
    out = []
    with open(path) as fh:
        lines = fh.readlines()
    for ln in lines:
        m = re.match(r'BLOCK (\w) cycle(\d) start (\S+ \S+)', ln)
        if m:
            out.append({"arm": m.group(1), "cycle": int(m.group(2)),
                        "t0": datetime.datetime.strptime(m.group(3),
                                                         '%Y-%m-%d %H:%M:%S')})
        m = re.match(r'BLOCK (\w) cycle(\d) end   (\S+ \S+)', ln)
        if m and out:
            out[-1]["t1"] = datetime.datetime.strptime(m.group(3),
                                                       '%Y-%m-%d %H:%M:%S')
    return [b for b in out if "t1" in b]

def latencies(log, t0, t1):
    cur = None; sent = {}; rx = {}
    with open(log, errors='replace') as fh:
        lines = fh.readlines()
    for ln in lines:
        m = TS.match(ln)
        if m:
            cur = datetime.datetime.strptime(m.group(1), '%Y-%m-%d %H:%M:%S.%f')
        if cur is None or not (t0 <= cur <= t1):
            continue
        g = re.search(r'webrtcReq sent  peerid=(\S+)', ln)
        if g:
            sent.setdefault(g.group(1), cur); continue
        if 'webrtc rx' in ln and 'webrtcResp' in ln:
            p = re.search(r'"peerid":"([^"]+)"', ln)
            if p:
                rx.setdefault(p.group(1), cur)
    d = sorted(round((rx[p] - t).total_seconds(), 2)
               for p, t in sent.items() if p in rx and rx[p] >= t)
    return len(sent), d

def main(halog, runlog):
    res = {}
    print(f"{'block':10s} {'opens':>6s} {'answered':>9s} {'median':>8s} {'p90':>8s} {'max':>8s}")
    for b in blocks(runlog):
        n_sent, d = latencies(halog, b["t0"], b["t1"])
        key = f'{b["arm"]}{b["cycle"]}'
        if not d:
            print(f"{key:10s} {n_sent:6d} {0:9d}        -        -        -")
            continue
        med = statistics.median(d)
        p90 = d[max(0, int(.9 * len(d)) - 1)]
        res[key] = med
        print(f"{key:10s} {n_sent:6d} {len(d):9d} {med:8.2f} {p90:8.2f} {d[-1]:8.2f}")
    print()
    if {"A1", "B1", "A2", "B2"} <= res.keys():
        c1 = res["B1"] - res["A1"]
        c2 = res["B2"] - res["A2"]
        print(f"dose effect (B-A):  cycle1 {c1:+.2f}s   cycle2 {c2:+.2f}s"
              f"   -> {'consistent' if c1*c2 > 0 else 'INCONSISTENT'}")
        print(f"time trend (c2-c1): armA {res['A2']-res['A1']:+.2f}s"
              f"   armB {res['B2']-res['B1']:+.2f}s")
        print()
        if c1 > 0.5 and c2 > 0.5:
            print("READ: latency tracks OUR open rate on both cycles"
                  " - the churn is at least partly self-sustaining.")
        elif max(res.values()) < 2.0:
            print("READ: no degradation at any dose. Open rate is not the driver"
                  " at these rates; the degraded state comes from elsewhere.")
        else:
            print("READ: mixed - check the time-trend row before concluding.")

if __name__ == '__main__':
    main(sys.argv[1], sys.argv[2])
