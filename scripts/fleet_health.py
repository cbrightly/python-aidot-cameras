"""Fleet health from INFO-level log lines only - no debug logging required.

The serve-loop churn is legible without turning on `webrtc_open: debug`. The
latency column in find_degraded_window.py is a better signal but needs every
webrtcResp logged, which floods the log on a busy fleet. These three counters
are INFO or WARNING and separate the states cleanly on their own:

    healthy (measured 21h on 1.0.0b20) : 6-12 opens/h, 0 failures/h
    degraded (b17 baseline 2026-08-18) : 131.6 opens/h, 47.5 failures/h

An order of magnitude apart, so a rate check is enough to say whether the churn
has returned. Reach for the latency table only once it has.

Usage: fleet_health.py <ha-core-log>   (ANSI already stripped)
"""
import collections
import re
import sys

TS = re.compile(r'^(2026-\d\d-\d\d \d\d:\d\d:\d\d)')
# Failures are the sharp discriminator - healthy is a flat 0, the churn baseline
# ran 22-30 per hour. Opens are noisier: ordinary heavy viewing, or a test
# driver, reached 38/h on a perfectly healthy fleet, so that threshold is set
# well above real use rather than just above the healthy median.
OPENS_PER_HOUR_ALARM = 60      # healthy 6-12, driven 38, churn baseline 110-125
FAILS_PER_HOUR_ALARM = 5       # healthy 0, churn baseline 22-30/h


def main(path):
    cur = None
    per = collections.defaultdict(collections.Counter)
    with open(path, errors='replace') as fh:
        lines = fh.readlines()
    for ln in lines:
        m = TS.match(ln)
        if m:
            cur = m.group(1)
        if cur is None:
            continue
        hour = cur[:13]
        if 'webrtcReq sent' in ln:
            per[hour]['opens'] += 1
        elif 'DTLS serve: open failed' in ln:
            per[hour]['fails'] += 1
        elif 'delivered no video in' in ln:
            per[hour]['novideo'] += 1
    if not per:
        print("no serve-loop activity in this log")
        return 0
    hours = sorted(per)[:-1]        # drop the partial trailing hour
    if not hours:
        hours = sorted(per)
    bad = []
    print(f"{'hour':16s} {'opens':>6s} {'fails':>6s} {'novid':>6s}  state")
    for h in hours:
        c = per[h]
        state = 'healthy'
        if c['opens'] > OPENS_PER_HOUR_ALARM or c['fails'] > FAILS_PER_HOUR_ALARM:
            state = 'CHURNING'
            bad.append(h)
        print(f"{h:16s} {c['opens']:6d} {c['fails']:6d} {c['novideo']:6d}  {state}")
    print(f"\nthresholds: >{OPENS_PER_HOUR_ALARM} opens/h or >{FAILS_PER_HOUR_ALARM} fails/h")
    if bad:
        print(f"CHURNING in {len(bad)} hour(s): {', '.join(bad)}")
        print("Next: enable aidot_cameras.camera.webrtc_open=debug and run"
              " find_degraded_window.py for the answer-latency table.")
        return 1
    print(f"All {len(hours)} hour(s) healthy.")
    return 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1]))
