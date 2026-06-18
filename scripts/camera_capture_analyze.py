#!/usr/bin/env python3
"""Analyze a camera_capture.sh pcap for the "blackhole AiDot" experiment.

Reads the pcap via ``tcpdump -r`` (no tshark/scapy dependency) and answers the
two questions that gate a self-hosted faux backend:

  Q1  Does the camera itself dial AiDot's TURN relay (3.230.182.123, STUN/TURN
      ports 3478/5349) during remote viewing?  And does it reach that IP via DNS
      (redirectable to your own coturn) or as a baked-in literal (not
      redirectable by a bridge-side hosts file)?

  Q2  Does the camera make direct cloud calls (HTTPS :443 to *.arnoo.com, or any
      non-LAN endpoint) at boot/idle, i.e. will a router blackhole brick it?

Q3 (what ICE config the camera acts on) is encrypted MQTT-over-WSS - read it
from the bridge logs ("ICE config from getIceConfigResp",
src/aidot/camera/client.py:4833), not from this pcap.

Usage
-----
  python3 scripts/camera_capture_analyze.py captures/cam-192.168.30.50-<ts>.pcap

  # markers file is auto-located (<base>.markers.tsv); override if moved:
  python3 scripts/camera_capture_analyze.py cap.pcap --markers cap.markers.tsv

  # the camera IP is parsed from the filename; pass it if the name was changed:
  python3 scripts/camera_capture_analyze.py cap.pcap --camera 192.168.30.50
"""
import argparse
import ipaddress
import os
import re
import subprocess
import sys
from collections import defaultdict

# The hardcoded AiDot/Arnoo TURN relay the bridge falls back to
# (src/aidot/camera/client.py:4855-4856). A camera flow to this IP during remote
# viewing is the headline finding.
ARNOO_TURN_IP = "3.230.182.123"
STUN_TURN_PORTS = {3478, 3479, 5349, 5350}
GOOGLE_STUN_PORT = 19302
MQTT_WSS_PORT = 8443

# tcpdump -nn -tt line: "<ts> IP <src> > <dst>: <rest>"
_LINE = re.compile(r"^(\d+\.\d+)\s+IP\s+(\S+)\s+>\s+(\S+?):\s*(.*)$")
_DNS_Q = re.compile(r"\b(?:AAAA|A)\?\s+([^\s,]+?)\.?\s")
_DNS_QID = re.compile(r":\s+(\d+)[+*%!]*\s")          # query: "...: 12345+ A? ..."
_DNS_A = re.compile(r"\bA+\s+(\d+\.\d+\.\d+\.\d+)\b")  # answer A/AAAA records
_LEN = re.compile(r"length\s+(\d+)")


def split_host_port(token):
    """'1.2.3.4.5349' -> ('1.2.3.4', 5349); IPv6/odd -> (token, None)."""
    if token.count(".") < 1:
        return token, None
    host, _, port = token.rpartition(".")
    if port.isdigit() and host.count(".") == 3:
        return host, int(port)
    return token, None


def is_local(ip, cam_net):
    try:
        a = ipaddress.ip_address(ip)
    except ValueError:
        return True  # not a v4 literal (v6/garbage) - don't flag as cloud
    if a.is_private or a.is_loopback or a.is_link_local or a.is_multicast:
        return True
    if a.is_reserved or a.is_unspecified:
        return True
    if cam_net and a in cam_net:
        return True
    return False


def run_tcpdump(pcap, extra_filter=None):
    cmd = ["tcpdump", "-nn", "-tt", "-r", pcap]
    if extra_filter:
        cmd.append(extra_filter)
    try:
        p = subprocess.run(cmd, capture_output=True, text=True, check=False)
    except FileNotFoundError:
        sys.exit("error: tcpdump not found on PATH")
    if p.returncode != 0 and not p.stdout:
        sys.exit(f"error: tcpdump failed: {p.stderr.strip()}")
    return p.stdout.splitlines()


def load_markers(path):
    """Return sorted [(epoch_float, label)] from a markers.tsv, or []."""
    out = []
    if not path or not os.path.exists(path):
        return out
    with open(path) as fh:
        for ln in fh:
            parts = ln.rstrip("\n").split("\t")
            if len(parts) >= 3:
                try:
                    out.append((float(parts[0]), parts[2]))
                except ValueError:
                    pass
    return sorted(out)


def phase_at(ts, markers):
    """Label of the last marker at or before ts (or '-' / 'pre-start')."""
    label = "pre-start"
    for mts, lbl in markers:
        if mts <= ts:
            label = lbl
        else:
            break
    return label


def main():
    ap = argparse.ArgumentParser(description=__doc__,
                                 formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("pcap")
    ap.add_argument("--markers", help="markers .tsv (default: <base>.markers.tsv)")
    ap.add_argument("--camera", help="camera IP (default: parsed from filename)")
    args = ap.parse_args()

    if not os.path.exists(args.pcap):
        sys.exit(f"error: no such file: {args.pcap}")

    # camera IP: from --camera or filename "cam-<ip>-<ts>.pcap"
    cam_ip = args.camera
    if not cam_ip:
        m = re.search(r"cam-(\d+\.\d+\.\d+\.\d+)-", os.path.basename(args.pcap))
        cam_ip = m.group(1) if m else None
    if not cam_ip:
        sys.exit("error: could not determine camera IP; pass --camera")
    try:
        cam_net = ipaddress.ip_network(cam_ip + "/24", strict=False)
    except ValueError:
        cam_net = None

    markers_path = args.markers
    if not markers_path:
        guess = re.sub(r"\.pcap$", ".markers.tsv", args.pcap)
        markers_path = guess if os.path.exists(guess) else None
    markers = load_markers(markers_path)

    lines = run_tcpdump(args.pcap)
    if not lines:
        sys.exit("error: pcap is empty (wrong interface? host didn't see camera traffic?)")

    # --- DNS: build qid->qname, then resolved ip -> {hostnames} -------------
    qid_to_name = {}
    ip_to_hosts = defaultdict(set)
    queried_hosts = set()

    # --- flows keyed by remote endpoint (the non-camera side) --------------
    # key: (remote_ip, remote_port, proto) -> stats
    flows = defaultdict(lambda: {"pkts": 0, "bytes": 0, "out": 0, "in": 0,
                                 "first": None, "last": None})
    first_ts = last_ts = None

    for ln in lines:
        m = _LINE.match(ln)
        if not m:
            continue
        ts = float(m.group(1))
        src_tok, dst_tok, rest = m.group(2), m.group(3), m.group(4)
        first_ts = ts if first_ts is None else first_ts
        last_ts = ts
        src_ip, src_port = split_host_port(src_tok)
        dst_ip, dst_port = split_host_port(dst_tok)

        # DNS bookkeeping (port 53 either direction)
        if dst_port == 53 and src_ip == cam_ip:
            qid_m = _DNS_QID.search(": " + rest)
            name_m = _DNS_Q.search(rest + " ")
            if name_m:
                host = name_m.group(1).rstrip(".")
                queried_hosts.add(host)
                if qid_m:
                    qid_to_name[qid_m.group(1)] = host
            continue
        if src_port == 53 and dst_ip == cam_ip:
            qid_m = re.match(r"\s*(\d+)", rest)
            name = qid_to_name.get(qid_m.group(1)) if qid_m else None
            for ans in _DNS_A.findall(rest):
                if name:
                    ip_to_hosts[ans].add(name)
                else:
                    ip_to_hosts[ans]  # touch, mark as "resolved (name unknown)"
            continue

        # flow accounting - identify the remote (non-camera) endpoint
        if src_ip == cam_ip:
            remote_ip, remote_port, outbound = dst_ip, dst_port, True
        elif dst_ip == cam_ip:
            remote_ip, remote_port, outbound = src_ip, src_port, False
        else:
            continue  # not involving the camera (broadcast noise)

        proto = "UDP" if rest.startswith("UDP") or " UDP" in rest[:8] else \
                ("TCP" if "Flags" in rest or "seq" in rest or "ack" in rest else "?")
        key = (remote_ip, remote_port, proto)
        f = flows[key]
        f["pkts"] += 1
        f["out" if outbound else "in"] += 1
        lm = _LEN.search(rest)
        if lm:
            f["bytes"] += int(lm.group(1))
        f["first"] = ts if f["first"] is None else f["first"]
        f["last"] = ts

    # --- classify --------------------------------------------------------------
    def host_label(ip):
        hosts = ip_to_hosts.get(ip)
        if hosts is None:
            return None             # never appeared in a DNS answer
        return ", ".join(sorted(h for h in hosts if h)) or "(resolved, name n/a)"

    def classify(ip, port):
        if ip == ARNOO_TURN_IP:
            return "★ ARNOO TURN (hardcoded relay)"
        if port in STUN_TURN_PORTS:
            return "STUN/TURN relay"
        if port == GOOGLE_STUN_PORT:
            return "Google STUN"
        if port == MQTT_WSS_PORT:
            return "MQTT/WSS broker"
        if port == 443:
            return "HTTPS (cloud REST?)"
        if port == 123:
            return "NTP"
        if port == 53:
            return "DNS"
        return "other"

    externals = []
    locals_ = []
    for (ip, port, proto), f in flows.items():
        rec = (ip, port, proto, f)
        (locals_ if is_local(ip, cam_net) else externals).append(rec)
    externals.sort(key=lambda r: (r[0] != ARNOO_TURN_IP, r[0], r[1] or 0))

    # ===================== REPORT =====================
    span = (last_ts - first_ts) if (first_ts and last_ts) else 0
    print("=" * 72)
    print(f"camera        : {cam_ip}")
    print(f"pcap          : {args.pcap}")
    print(f"packets parsed : {sum(f['pkts'] for f in flows.values())}"
          f"   span: {span:.0f}s   flows: {len(flows)}")
    print(f"markers file  : {markers_path or '(none)'}")
    print("=" * 72)

    # phase timeline
    if markers:
        print("\nPHASE TIMELINE")
        base = markers[0][0]
        for mts, lbl in markers:
            print(f"  +{mts - base:6.0f}s   {lbl}")

    # DNS
    print("\nDNS QUERIES BY CAMERA")
    if queried_hosts:
        for h in sorted(queried_hosts):
            tag = "  <-- ARNOO" if "arnoo" in h.lower() else \
                  ("  <-- TURN/STUN?" if re.search(r"turn|stun|relay", h.lower()) else "")
            print(f"  {h}{tag}")
    else:
        print("  (none captured) - camera used no DNS, or its resolver is "
              "elsewhere.\n  NB: zero DNS + external flows = BAKED-IN IPs (case B).")

    # external endpoints
    print("\nEXTERNAL (non-LAN) ENDPOINTS THE CAMERA TALKED TO")
    if not externals:
        print("  (none) - camera only spoke to LAN hosts. Strong signal it can")
        print("  run fully self-hosted. Verify the remote-view phase actually ran.")
    else:
        print(f"  {'remote ip':<16} {'port':>5} {'pr':<3} {'pkts':>5} "
              f"{'first-phase':<13} dns / classification")
        print("  " + "-" * 86)
        for ip, port, proto, f in externals:
            label = host_label(ip)
            dns = label if label is not None else "NO-DNS (baked-in?)"
            phase = phase_at(f["first"], markers) if markers else "-"
            cls = classify(ip, port)
            print(f"  {ip:<16} {str(port or '?'):>5} {proto:<3} {f['pkts']:>5} "
                  f"{phase:<13} {dns}  [{cls}]")

    if locals_:
        print(f"\n  (+ {len(locals_)} LAN/local flows: bridge, gateway, mDNS, etc.)")

    # ===================== VERDICTS =====================
    print("\n" + "=" * 72)
    print("VERDICTS")
    print("=" * 72)

    turn_flows = [(ip, port, proto, f) for (ip, port, proto), f in flows.items()
                  if ip == ARNOO_TURN_IP
                  or (port in STUN_TURN_PORTS and not is_local(ip, cam_net))]

    # Q1
    print("\nQ1  Does the camera dial AiDot TURN during remote viewing?")
    if not turn_flows:
        print("    NO TURN/relay flow from the camera in this capture.")
        print("    => If a remote-view phase ran and still no relay, host/STUN")
        print("       candidates sufficed. Re-test with bridge+camera on strictly")
        print("       separate NATs to be sure remote relay truly isn't needed.")
    else:
        for ip, port, proto, f in turn_flows:
            phase = phase_at(f["first"], markers) if markers else "-"
            baked = ip_to_hosts.get(ip) is None
            print(f"    YES -> {ip}:{port}/{proto}  (phase: {phase}, {f['pkts']} pkts)")
            if ip == ARNOO_TURN_IP:
                print("    This is the hardcoded Arnoo relay (client.py:4855).")
            if baked:
                print("    *** BAKED-IN: no DNS query resolved this IP (CASE B). A")
                print("        bridge-side hosts file CANNOT redirect it. To self-host")
                print("        remote viewing you must DNAT this IP on the camera VLAN")
                print("        to your coturn, or accept LAN-only when blackholed.")
            else:
                print(f"    Reached via DNS ({host_label(ip)}) => CASE A: redirectable.")
                print("        Point that hostname at your coturn in the faux DNS.")

    # Q2
    boot_phases = {"boot", "boot-start", "idle"}
    cloud_at_boot = []
    for ip, port, proto, f in externals:
        phase = phase_at(f["first"], markers) if markers else "-"
        if (port == 443 or port == MQTT_WSS_PORT) and \
           (not markers or phase.lower() in boot_phases):
            cloud_at_boot.append((ip, port, phase, host_label(ip)))
    print("\nQ2  Does the camera phone home at boot/idle (would a blackhole brick it)?")
    if not cloud_at_boot:
        print("    No external :443/:8443 flows during boot/idle phases.")
        print("    => A router blackhole likely will NOT brick it. Confirm the boot")
        print("       phase was actually captured (camera powered on during capture).")
    else:
        for ip, port, phase, label in cloud_at_boot:
            arn = "  <-- ARNOO" if (label and "arnoo" in label.lower()) else ""
            print(f"    {ip}:{port}  phase={phase}  {label or 'NO-DNS'}{arn}")
        print("    => Camera contacts cloud early. Blackhole CAREFULLY: stage it and")
        print("       watch for reboot loops. May need the faux backend reachable")
        print("       (DNAT arnoo -> faux) before cutting the real internet.")

    # Q3 reminder
    print("\nQ3  What ICE config does the camera ACT on? (not in this pcap)")
    print("    MQTT signaling is WSS-encrypted. Read the bridge log line")
    print("    'ICE config from getIceConfigResp' (client.py:4833) during the same")
    print("    session to see the servers the camera was handed.")
    print()


if __name__ == "__main__":
    main()
