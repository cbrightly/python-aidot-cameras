"""Identical accounting for both arms, timestamp-anchored.

Counts only lines that BEGIN with a timestamp, then attributes every
continuation line (SDP dumps etc.) to the last timestamp seen. A naive
`awk '$0>="2026-08-18 21:05"'` passes every continuation line whose first
character sorts above '2' - which is most of them - and silently inflates
every count. That mistake is why the first interim reads looked wrong.
"""
import datetime
import re
import sys

TS = re.compile(r'^(2026-\d\d-\d\d \d\d:\d\d:\d\d\.\d{3})')

def run(path, label, start, end, timeout_s):
    t0 = datetime.datetime.strptime(start, '%Y-%m-%d %H:%M:%S')
    t1 = datetime.datetime.strptime(end,   '%Y-%m-%d %H:%M:%S')
    cur = None
    sent, rx = {}, {}
    fails, novideo = [], []
    with open(path, errors='replace') as fh:
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
        if 'DTLS serve: open failed' in ln:
            fails.append(cur); continue
        if 'delivered no video in' in ln:
            novideo.append(cur); continue
        if 'webrtc rx' in ln and 'webrtcResp' in ln:
            p = re.search(r'"peerid":"([^"]+)"', ln)
            if p:
                rx.setdefault(p.group(1), cur)
    hours = (t1 - t0).total_seconds() / 3600.0
    intime = late = never = 0
    recovered = []
    for p, t in sent.items():
        r = rx.get(p)
        if r is None or r < t:
            never += 1; continue
        d = (r - t).total_seconds()
        if d <= timeout_s:
            intime += 1
            if d > 30.0:
                recovered.append(round(d, 1))
        else:
            late += 1
    n = len(sent) or 1
    print(f"--- {label}  ({start} .. {end}, {hours:.2f} h, timeout {timeout_s:.0f}s)")
    print(f"    opens                 {len(sent):4d}   ({len(sent)/hours:.1f}/h)")
    print(f"      answered in time    {intime:4d}   ({100*intime/n:.0f}%)")
    if timeout_s > 30:
        # Printed directly under "answered in time" because it is a subset of
        # THAT bucket. Under "never answered" it reads as a subset of the
        # opposite one, which is how the first treatment-arm output was
        # misread ("never answered 2 / of which RECOVERED 2").
        print(f"        of those, RECOVERED {len(recovered):4d}"
              f"   (answered in 30-{timeout_s:.0f}s; a 30s build fails these)"
              f" {sorted(recovered)}")
    print(f"      answered too late   {late:4d}")
    print(f"      never answered      {never:4d}")
    print(f"    logged open failures  {len(fails):4d}   ({len(fails)/hours:.1f}/h)")
    print(f"    connected-but-no-video{len(novideo):4d}   ({len(novideo)/hours:.1f}/h)")

if __name__ == '__main__':
    run(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], float(sys.argv[5]))
