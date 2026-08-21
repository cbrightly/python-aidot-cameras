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
Also appends one dated line per run to /config/fleet_health.log so the result is
visible without waiting for something to go wrong, and so a run that did NOT
happen is visible as a gap. Trimmed to the last 90 lines.
"""
import collections
import datetime
import os
import re
import sys
import urllib.request

LOGBOOK = "/config/fleet_health.log"
STATE_JSON = "/config/fleet_health.json"
LOGBOOK_KEEP = 90          # about three months of daily lines

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


def publish(status, headline, detail=""):
    """Write the machine-readable state the dashboard sensor reads.

    The sensor's STATE is one short word so the card can colour and icon it;
    everything a human wants to read lives in attributes. Putting the whole
    sentence in the state made the card an unreadable wall of text and blew past
    the 255-character state limit as soon as several hours were listed.
    """
    import json
    payload = {
        "status": status,                       # healthy | churning | unknown
        "headline": headline,                   # one short human line
        "detail": detail[:900],                 # the full report, for the card
        "checked_at": datetime.datetime.now().strftime("%Y-%m-%d %H:%M"),
    }
    try:
        with open(STATE_JSON, "w") as fh:
            json.dump(payload, fh)
    except OSError:
        pass


def record(line):
    """One dated line per run, so a healthy day is visible and a missing day is
    visible as a gap. A monitor that only speaks up on failure cannot be
    distinguished from a monitor that has stopped running."""
    stamp = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")
    try:
        try:
            with open(LOGBOOK) as fh:
                keep = fh.read().splitlines()[-(LOGBOOK_KEEP - 1):]
        except FileNotFoundError:
            keep = []
        keep.append(f"{stamp}  {line}")
        with open(LOGBOOK, "w") as fh:
            fh.write("\n".join(keep) + "\n")
    except OSError:
        pass                                    # never fail the check over this


def main():
    try:
        raw = fetch()
    except Exception as exc:
        msg = f"UNKNOWN - could not fetch the log: {exc}"
        print(msg); record(msg)
        publish("unknown", "Could not read the log", str(exc))
        return 2
    if not raw:
        msg = "UNKNOWN - no SUPERVISOR_TOKEN"
        print(msg); record(msg)
        publish("unknown", "No supervisor token", "The check could not authenticate.")
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
        msg = "UNKNOWN - no serve-loop activity, cannot tell healthy from idle"
        print(msg); record(msg)
        publish("unknown", "No camera activity to judge",
                "Nothing was streaming, so this is not the same as healthy.")
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
        worst = max(per[h]['opens'] for h in bad)
        nf = sum(per[h]['fails'] for h in bad)
        record(f"CHURNING - {len(bad)}/{len(hours)} hours, worst opens/h "
               f"{worst}, {nf} failures")
        publish("churning",
                f"{len(bad)} of {len(hours)} hours affected",
                f"Peak {worst} reconnects/hour and {nf} failed opens. "
                f"Alarms above {OPENS_ALARM}/hour or {FAILS_ALARM} failures; "
                f"a quiet fleet sits at 6-12 with none failing. "
                f"Hours: {', '.join(h[11:] + ':00' for h in bad)}.")
        return 1
    msg = (f"healthy - {len(hours)} hours, opens/h {min(o)}-{max(o)}, "
           f"{f} open failures")
    print(msg); record(msg)
    publish("healthy",
            f"All {len(hours)} hours normal",
            f"{min(o)}-{max(o)} camera reconnects per hour, {f} failed opens. "
            f"Alarms above {OPENS_ALARM}/hour or {FAILS_ALARM} failures.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
