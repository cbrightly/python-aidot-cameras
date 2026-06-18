#!/usr/bin/env bash
# Camera-VLAN traffic capture helper for the "blackhole AiDot" experiment.
#
# Records every packet a single camera sends/receives, plus the camera's DNS
# queries, into a pcap with phase markers.  Feed the result to
# camera_capture_analyze.py to answer the two questions that gate a self-hosted
# faux backend:
#
#   Q1  During REMOTE (cross-VLAN) viewing, does the camera itself open a flow
#       to AiDot's TURN relay (3.230.182.123, ports 3478/5349) or any arnoo
#       host?  -> tells you whether remote viewing needs your own coturn AND
#       whether the camera will accept it (case A) or has the IP baked in
#       (case B, not redirectable by a bridge-side hosts file).
#
#   Q2  At BOOT, does the camera make direct HTTPS calls to *.arnoo.com, or does
#       it only ever talk to the MQTT broker + ICE peer?  -> tells you whether a
#       router-level blackhole will brick the camera into a reboot loop.
#
# Q3 (what ICE config the camera actually acts on) is MQTT-over-WSS = encrypted;
# read it from the BRIDGE logs instead ("ICE config from getIceConfigResp",
# src/aidot/camera/client.py:4833), not from this pcap.
#
# IMPORTANT: this host must physically SEE the camera's traffic.  That means one
# of: (a) it is the camera VLAN's router/gateway (HA box routing the VLAN), or
# (b) you mirror the camera's switch port (SPAN) to this host's NIC.  A host on a
# different switch/VLAN will capture nothing but broadcast.
#
# Usage
# -----
#   # interactive: drop phase markers by typing a label + ENTER; Ctrl-C to stop
#   sudo scripts/camera_capture.sh 192.168.30.50
#
#   # pick the interface explicitly (default: the iface routing to the camera)
#   sudo scripts/camera_capture.sh 192.168.30.50 -i eth0.30
#
#   # fixed-duration, non-interactive (good for an unattended boot capture)
#   sudo scripts/camera_capture.sh 192.168.30.50 -d 120 -p boot
#
#   # custom output dir (default: ./captures)
#   sudo scripts/camera_capture.sh 192.168.30.50 -o /tmp/cam
#
# Then:
#   python3 scripts/camera_capture_analyze.py captures/cam-192.168.30.50-<ts>.pcap
#
# Suggested run book (one camera):
#   1. Power the camera OFF.  Start this script.  Type "boot-start" + ENTER.
#   2. Power the camera ON.  Wait ~90s for it to fully come online.
#   3. Type "idle" + ENTER.  Leave it untouched ~60s (no viewer).
#   4. Open a LAN view (HA/app on the SAME subnet).  Type "lan-view" + ENTER. ~60s.
#   5. Open a REMOTE view (phone on cellular, or HA across the VLAN boundary).
#      Type "remote-view" + ENTER.  Watch it ~60s.
#   6. Ctrl-C.  Run the analyzer.
set -euo pipefail

usage() { sed -n '2,52p' "$0"; exit "${1:-0}"; }

CAM_IP=""
IFACE=""
OUTDIR="./captures"
DURATION=""          # seconds; empty = run until Ctrl-C
PHASE=""             # initial phase label for non-interactive runs
SNAP=512             # enough for DNS answers; payloads are encrypted anyway

# --- args -----------------------------------------------------------------
while [[ $# -gt 0 ]]; do
  case "$1" in
    -i|--iface)    IFACE="$2"; shift 2 ;;
    -o|--outdir)   OUTDIR="$2"; shift 2 ;;
    -d|--duration) DURATION="$2"; shift 2 ;;
    -p|--phase)    PHASE="$2"; shift 2 ;;
    -h|--help)     usage 0 ;;
    -*)            echo "unknown option: $1" >&2; usage 1 ;;
    *)             if [[ -z "$CAM_IP" ]]; then CAM_IP="$1"; shift
                   else echo "unexpected arg: $1" >&2; usage 1; fi ;;
  esac
done

[[ -n "$CAM_IP" ]] || { echo "error: camera IP is required" >&2; usage 1; }
command -v tcpdump >/dev/null || { echo "error: tcpdump not found" >&2; exit 1; }

# --- privilege ------------------------------------------------------------
if [[ "$(id -u)" -ne 0 ]]; then
  if command -v sudo >/dev/null; then
    echo "* re-running under sudo (tcpdump needs root)…" >&2
    exec sudo -E bash "$0" "$CAM_IP" \
      ${IFACE:+-i "$IFACE"} -o "$OUTDIR" \
      ${DURATION:+-d "$DURATION"} ${PHASE:+-p "$PHASE"}
  fi
  echo "error: must run as root (tcpdump). Re-run with sudo." >&2; exit 1
fi

# --- interface autodetect -------------------------------------------------
if [[ -z "$IFACE" ]]; then
  # the iface the kernel would use to reach the camera; best guess for "sees it"
  IFACE="$(ip route get "$CAM_IP" 2>/dev/null \
            | grep -oE 'dev [^ ]+' | awk '{print $2}' | head -n1 || true)"
  [[ -n "$IFACE" ]] || { echo "error: could not autodetect interface; pass -i" >&2; exit 1; }
  echo "* autodetected interface: $IFACE (override with -i)" >&2
fi

# --- output paths ---------------------------------------------------------
mkdir -p "$OUTDIR"
TS="$(date +%Y%m%d-%H%M%S)"
BASE="$OUTDIR/cam-${CAM_IP}-${TS}"
PCAP="${BASE}.pcap"
MARKERS="${BASE}.markers.tsv"
: >"$MARKERS"

# capture filter: everything to/from the camera (this also grabs its DNS
# queries, since those are sourced from the camera IP).
FILTER="host ${CAM_IP}"

echo "============================================================"
echo " camera : $CAM_IP"
echo " iface  : $IFACE"
echo " pcap   : $PCAP"
echo " markers: $MARKERS"
echo " filter : $FILTER"
[[ -n "$DURATION" ]] && echo " stop   : after ${DURATION}s" || echo " stop   : Ctrl-C"
echo "============================================================"

# --- start tcpdump in the background --------------------------------------
# -tt epoch ts in the file is irrelevant (pcap stores real ts); the analyzer
# re-reads with its own format.  -U flushes per-packet so a Ctrl-C loses nothing.
tcpdump -i "$IFACE" -s "$SNAP" -U -w "$PCAP" "$FILTER" &
TD_PID=$!

mark() {  # mark <label>
  local now iso
  now="$(date +%s.%N)"; iso="$(date -Is)"
  printf '%s\t%s\t%s\n' "$now" "$iso" "$1" >>"$MARKERS"
  echo "  ↳ marked [$1] @ $iso"
}

cleanup() {
  trap - INT TERM EXIT
  echo
  echo "* stopping capture…"
  kill "$TD_PID" 2>/dev/null || true
  wait "$TD_PID" 2>/dev/null || true
  local pkts="?"
  pkts="$(tcpdump -nn -r "$PCAP" 2>/dev/null | wc -l || echo '?')"
  echo "* captured $pkts packets -> $PCAP"
  echo
  echo "next: python3 scripts/camera_capture_analyze.py \"$PCAP\""
}
trap cleanup INT TERM EXIT

# give tcpdump a moment to attach before the user does anything
sleep 1
[[ -n "$PHASE" ]] && mark "$PHASE"

if [[ -n "$DURATION" ]]; then
  echo "* capturing for ${DURATION}s…"
  sleep "$DURATION"
  exit 0
fi

# interactive marker loop: each line you type becomes a timestamped phase marker
echo "* capturing. Type a phase label + ENTER to mark it (e.g. boot, idle,"
echo "  lan-view, remote-view). Ctrl-C (or Ctrl-D) to stop."
while IFS= read -r line; do
  [[ -z "$line" ]] && line="mark"
  mark "$line"
done
# Ctrl-D falls through to cleanup via EXIT trap
