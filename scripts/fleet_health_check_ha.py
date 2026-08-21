#!/usr/bin/env python3
"""Daily AiDot fleet health check, run inside the Home Assistant container.

Fetches Home Assistant's own log through the supervisor API - so no file
logging has to be enabled and nothing extra is written to the SD card - and
reports whether the serve-loop churn has returned.

The churn is legible from INFO-level lines alone, which is why this does not
need `aidot_cameras.camera.webrtc_open: debug`:

    healthy  (21h measured on library 1.0.0b20) : 6-12 opens/h,  0 failures/h
    degraded (baseline 2026-08-18)              : 110-125 opens/h, 22-30 failures/h

Exit code 0 = all hours healthy, 1 = at least one hour churning, 2 = could not
tell (no log, or no serve-loop activity). "No activity" is deliberately NOT
reported as healthy; a quiet fleet is not evidence of a working one.
"""
import collections
import os
import re
import sys
import urllib.request

TS = re.compile(r'^(2026-\d\d-\d\d \d\d:\d\d:\d\d)')
ANSI = re.compile(r'\x1b\[[0-9;]*m')
OPENS_ALARM = 60
FAILS_ALARM = 5


def fetch(lines=120000):
    tok = os.environ.get("SUPERVISOR_TOKEN")
    if not tok:
        return None
    req = urllib.request.Request(
        f"http://supervisor/core/logs?lines={lines}",
        headers={"Authorization": f"Bearer {tok}"})
    with urllib.request.urlopen(req, timeout=120) as r:
        return r.read().decode("utf-8", "replace")


def main():
    try:
        raw = fetch()
    except Exception as exc:
        print(f"could not fetch the log: {exc}")
        return 2
    if not raw:
        print("could not fetch the log: no SUPERVISOR_TOKEN")
        return 2

    cur = None
    per = collections.defaultdict(collections.Counter)
    for ln in ANSI.sub("", raw).splitlines():
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
        print("no serve-loop activity in the log - cannot tell healthy from idle")
        return 2

    hours = sorted(per)
    hours = hours[:-1] or hours          # drop the partial trailing hour
    bad = [h for h in hours
           if per[h]['opens'] > OPENS_ALARM or per[h]['fails'] > FAILS_ALARM]
    o = [per[h]['opens'] for h in hours]
    f = sum(per[h]['fails'] for h in hours)
    if bad:
        print(f"CHURN RETURNED in {len(bad)} of {len(hours)} hours: "
              + ", ".join(bad))
        for h in bad:
            print(f"  {h}  opens={per[h]['opens']} fails={per[h]['fails']}"
                  f" novideo={per[h]['novideo']}")
        print("Next: set aidot_cameras.camera.webrtc_open=debug, let it run, then"
              " use find_degraded_window.py for the answer-latency table"
              " (~0.42s healthy, ~11.7s degraded). Do not judge on one short"
              " window - the defect is bursty.")
        return 1
    print(f"healthy: {len(hours)} hours, opens/h {min(o)}-{max(o)}, "
          f"{f} open failures total")
    return 0


if __name__ == "__main__":
    sys.exit(main())
