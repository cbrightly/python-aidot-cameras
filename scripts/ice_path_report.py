#!/usr/bin/env python3
"""Instrumented ICE-path probe: classify how a camera stream actually connects.

Wraps ``smoke_stream.py`` (reusing its login + stream logic), captures the
aioice / aidot WebRTC logs, and reports a structured verdict answering the
questions that came out of the "blackhole AiDot" investigation:

  * Did media flow?  (frames / recorded bytes / PASS|FAIL)
  * Which path won?  LAN host-direct  vs  srflx P2P (public IP)  vs  TURN relay
      -> tells you whether the TURN relay is load-bearing for THIS vantage,
         i.e. whether a self-hosted blackhole needs its own coturn here.
  * Was the TURN config delivered by cloud signaling (getIceConfigResp)?
      -> Case A (redirectable): own the broker -> hand out your own coturn.
  * Interface pollution: did the bridge gather non-routable local candidates
      (docker0 172.17/16, tailscale CGNAT 100.64/10)?  On a multi-homed host
      aioice can NOMINATE a dead pair -> "ICE completed" but on_frame=0.
      (HA-OS ships docker0, so this bites real deployments.)

Run it from whatever vantage you want to characterise:
  * on the camera LAN  -> expect (ideally) LAN host-direct, relay-free
  * on a remote host   -> expect srflx P2P or TURN relay

Usage
-----
  PYTHONPATH=src ~/venv/bin/python scripts/ice_path_report.py --name "M3 Pro v2"
  PYTHONPATH=src ~/venv/bin/python scripts/ice_path_report.py --name Deck --hold 20

Same credentials/env as smoke_stream (aidot.credentials / AIDOT_* env).
NB: respect the camera media-slot cooldown (~3 min between live streams) or you
get false NO-MEDIA. Exit code: 0 if media flowed, 1 otherwise.
"""
import argparse
import contextlib
import io
import ipaddress
import logging
import os
import re
import runpy
import sys

# The hardcoded Arnoo/AiDot TURN relay (src/aidot/camera/client.py).
ARNOO_TURN_IP = "3.230.182.123"

_CAND = re.compile(
    r"addIceCandidate \(answer SDP\):\s*\d+\s+\d+\s+udp\s+\d+\s+"
    r"(?P<ip>[0-9.]+)\s+(?P<port>\d+)\s+typ\s+(?P<typ>host|srflx|relay)"
)
# aioice: "Check CandidatePair(('LOCAL', lp) -> ('REMOTE', rp)) ... -> State.SUCCEEDED"
_PAIR = re.compile(
    r"CandidatePair\(\('(?P<local>[0-9.]+)',\s*\d+\)\s*->\s*"
    r"\('(?P<remote>[0-9.]+)',\s*\d+\)\).*?State\.(?P<state>SUCCEEDED|FAILED)"
)
_COMPLETED = re.compile(r"ICE completed")
_MEDIA = re.compile(r"on_frame=(?P<frames>\d+)\s+recorded=(?P<bytes>\d+)B\s*->\s*(?P<verdict>PASS|NO MEDIA)")
_ICECFG = re.compile(r"ICE config from getIceConfigResp")


def _classify_local(ip: str) -> str:
    """Label a gathered local candidate; flag the non-routable polluters."""
    try:
        a = ipaddress.ip_address(ip)
    except ValueError:
        return "other"
    if a in ipaddress.ip_network("172.16.0.0/12"):
        return "docker/RFC1918-172"
    if a in ipaddress.ip_network("100.64.0.0/10"):
        return "tailscale/CGNAT"
    if a in ipaddress.ip_network("192.168.0.0/16"):
        return "LAN-192.168"
    if a in ipaddress.ip_network("10.0.0.0/8"):
        return "RFC1918-10"
    if a.is_loopback:
        return "loopback"
    return "public"


def _run_smoke(names, hold) -> tuple[str, str]:
    """Run smoke_stream in-process, returning (captured_logs, captured_stdout)."""
    log_buf = io.StringIO()
    handler = logging.StreamHandler(log_buf)
    handler.setFormatter(logging.Formatter("%(name)s: %(message)s"))
    root = logging.getLogger()
    prev_level = root.level
    root.setLevel(logging.INFO)
    root.addHandler(handler)

    argv = ["smoke_stream.py"]
    for n in names:
        argv += ["--name", n]
    argv += ["--hold", str(hold)]
    smoke = os.path.join(os.path.dirname(os.path.abspath(__file__)), "smoke_stream.py")

    out_buf = io.StringIO()
    saved_argv = sys.argv
    sys.argv = argv
    try:
        with contextlib.redirect_stdout(out_buf):
            runpy.run_path(smoke, run_name="__main__")
    except SystemExit:
        pass  # smoke_stream exits non-zero on NO MEDIA; we read the buffer instead
    finally:
        sys.argv = saved_argv
        root.removeHandler(handler)
        root.setLevel(prev_level)
    return log_buf.getvalue(), out_buf.getvalue()


def report(log: str, stdout: str) -> bool:
    """Parse captured logs, print the verdict. Returns True if media flowed."""
    # camera-advertised candidates by type
    cands = {m.group("typ"): m.group("ip") for m in _CAND.finditer(log)}
    # nominated pair = last SUCCEEDED pair before the first "ICE completed"
    completed_at = None
    cm = _COMPLETED.search(log)
    if cm:
        completed_at = cm.start()
    nominated = None
    for m in _PAIR.finditer(log):
        if m.group("state") != "SUCCEEDED":
            continue
        if completed_at is not None and m.start() > completed_at:
            break
        nominated = (m.group("local"), m.group("remote"))
    # gathered local candidate types (pollution check)
    local_ips = {m.group("local") for m in _PAIR.finditer(log)}
    pollution = sorted({_classify_local(ip) for ip in local_ips}
                       & {"docker/RFC1918-172", "tailscale/CGNAT", "RFC1918-10"})
    media = _MEDIA.search(stdout) or _MEDIA.search(log)

    print("\n" + "=" * 64)
    print("ICE PATH REPORT")
    print("=" * 64)

    # Case A signal
    print(f"\nTURN via cloud signaling (getIceConfigResp): "
          f"{'YES — redirectable (Case A)' if _ICECFG.search(log) else 'not seen'}")

    # camera candidates
    print("\nCamera advertised candidates:")
    for typ in ("host", "srflx", "relay"):
        ip = cands.get(typ)
        tag = "  <- Arnoo TURN" if ip == ARNOO_TURN_IP else ""
        print(f"  {typ:6} {ip or '(none)'}{tag}")

    # winning path
    path = "unknown"
    if nominated:
        local, remote = nominated
        if remote == cands.get("relay") or remote == ARNOO_TURN_IP:
            path = "TURN RELAY  (relay-dependent)"
        elif remote == cands.get("host"):
            path = "LAN host-direct  (relay-free)"
        elif remote == cands.get("srflx"):
            path = "srflx P2P  (direct public-IP, relay-free)"
        else:
            path = f"other (remote {remote})"
        print(f"\nNominated pair: local {local}  ->  remote {remote}")
    print(f"WINNING PATH:  {path}")

    # pollution
    if pollution:
        print(f"\n⚠  Non-routable local candidates gathered: {', '.join(pollution)}")
        print("   aioice can nominate a dead pair from these on a multi-homed host")
        print("   (on_frame=0 despite 'ICE completed'). Flush/exclude them for a")
        print("   clean test:  ip addr flush dev docker0  (link-down is not enough).")

    # media
    if media:
        flowed = media.group("verdict") == "PASS"
        print(f"\nMEDIA: on_frame={media.group('frames')} "
              f"recorded={media.group('bytes')}B -> {media.group('verdict')}")
    else:
        flowed = False
        print("\nMEDIA: no result line parsed (stream may have errored before media)")

    # one-line takeaway for the blackhole question
    print("\nBlackhole takeaway:")
    if "RELAY" in path:
        print("  This vantage RELIES on the TURN relay -> a self-hosted blackhole")
        print("  must run its own coturn and advertise it via getIceConfig.")
    elif "relay-free" in path:
        print("  This vantage connected WITHOUT the relay -> LAN/direct viewing can")
        print("  survive a blackhole here even without coturn (signaling still needed).")
    else:
        print("  Path inconclusive; re-run after the camera media-slot cooldown.")
    print()
    return flowed


def main() -> int:
    ap = argparse.ArgumentParser(
        description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--name", action="append", default=[], required=True,
                    help="camera name substring (repeatable; first match is probed)")
    ap.add_argument("--hold", type=float, default=18.0, help="seconds to hold (default 18)")
    args = ap.parse_args()

    log, stdout = _run_smoke(args.name, args.hold)
    print(stdout, end="")  # surface smoke_stream's normal output
    return 0 if report(log, stdout) else 1


if __name__ == "__main__":
    raise SystemExit(main())
