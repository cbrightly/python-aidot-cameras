"""Find the degraded window in a day of HA core logs, and describe it.

The serve-loop churn is not something we can induce - an ABAB dose-response at
4.3x our own open rate moved answer latency not at all (0.39-0.41s medians).
So the remaining approach is to catch the degraded state under organic load and
ask what ELSE is true while it is happening.

Buckets the log by hour and reports, per hour, the answer-latency distribution
plus the signals worth correlating against it. A healthy hour looks like the
2026-08-18 evening baseline: median ~0.42s, max ~1.5s, everything answered.

Usage:  find_degraded_window.py <ha-core-log>            # ANSI already stripped
"""
import collections
import datetime
import re
import statistics
import sys

TS = re.compile(r'^(2026-\d\d-\d\d \d\d:\d\d:\d\d\.\d{3})')
HEALTHY_MEDIAN_S = 0.42     # measured, n=187 across two healthy arms
DEGRADED_MEDIAN_S = 11.73   # measured, the b17 churning window


def main(path):
    cur = None
    sent, rx = {}, {}
    per_hour = collections.defaultdict(lambda: collections.Counter())
    with open(path, errors='replace') as fh:
        lines = fh.readlines()
    for ln in lines:
        m = TS.match(ln)
        if m:
            cur = datetime.datetime.strptime(m.group(1), '%Y-%m-%d %H:%M:%S.%f')
        if cur is None:
            continue
        h = cur.replace(minute=0, second=0, microsecond=0)
        g = re.search(r'webrtcReq sent  peerid=(\S+)', ln)
        if g:
            sent.setdefault(g.group(1), cur); per_hour[h]['opens'] += 1; continue
        if 'webrtc rx' in ln and 'webrtcResp' in ln:
            p = re.search(r'"peerid":"([^"]+)"', ln)
            if p:
                rx.setdefault(p.group(1), cur)
            continue
        for key, needle in (('open_fail', 'DTLS serve: open failed'),
                            ('no_video', 'delivered no video in'),
                            ('reconnect', 'quickConn'),
                            ('mqtt_reconn', 'MQTT connected'),
                            ('turn_stale', 'stale nonce'),
                            ('terminal', 'terminal ack')):
            if needle in ln:
                per_hour[h][key] += 1

    lat_by_hour = collections.defaultdict(list)
    for p, t in sent.items():
        r = rx.get(p)
        if r is not None and r >= t:
            lat_by_hour[t.replace(minute=0, second=0, microsecond=0)].append(
                (r - t).total_seconds())

    if not per_hour:
        print("no serve-loop opens in this log"); return
    print(f"{'hour':16s} {'opens':>6s} {'ans':>5s} {'med':>7s} {'p90':>7s} "
          f"{'max':>7s} {'fail':>5s} {'novid':>6s} {'state'}")
    degraded = []
    for h in sorted(per_hour):
        c = per_hour[h]
        d = sorted(lat_by_hour.get(h, []))
        if not d:
            print(f"{h:%Y-%m-%d %H:00} {c['opens']:6d} {0:5d} "
                  f"{'-':>7s} {'-':>7s} {'-':>7s} {c['open_fail']:5d} "
                  f"{c['no_video']:6d}  (no answers seen)")
            continue
        med = statistics.median(d)
        p90 = d[max(0, int(.9 * len(d)) - 1)]
        # Halfway to the measured degraded median, on a log scale, is a
        # deliberately loose gate - the two states differ by ~28x, so anything
        # ambiguous is worth a human look rather than a silent pass.
        state = 'DEGRADED' if med > HEALTHY_MEDIAN_S * 4 else 'healthy'
        if state == 'DEGRADED':
            degraded.append((h, med, len(d), c))
        print(f"{h:%Y-%m-%d %H:00} {c['opens']:6d} {len(d):5d} {med:7.2f} "
              f"{p90:7.2f} {d[-1]:7.2f} {c['open_fail']:5d} {c['no_video']:6d}  {state}")

    print(f"\nreference: healthy median {HEALTHY_MEDIAN_S}s (n=187),"
          f" degraded median {DEGRADED_MEDIAN_S}s (2026-08-18 16:35-17:31)")
    if not degraded:
        print("\nNo degraded hour in this log. The state did not recur, so there"
              "\nis nothing new to explain - do not read the healthy hours as a fix.")
        return
    print(f"\n{len(degraded)} degraded hour(s). What to compare against the healthy"
          "\nhours, since our own open rate is already ruled out:")
    for h, med, n, c in degraded:
        print(f"  {h:%Y-%m-%d %H:00}  median {med:.2f}s over {n} answers; "
              f"opens {c['opens']}, fails {c['open_fail']}, "
              f"quickConn {c['reconnect']}, mqtt-reconnects {c['mqtt_reconn']}, "
              f"turn-stale {c['turn_stale']}, terminal-acks {c['terminal']}")
    print("\nThe open rate is NOT the discriminator (ABAB dose-response, 4.3x, no"
          "\neffect). Look at the reconnect/MQTT/TURN columns and at what the"
          "\ncameras or the cloud were doing, not at how hard we were retrying.")


if __name__ == '__main__':
    main(sys.argv[1])
