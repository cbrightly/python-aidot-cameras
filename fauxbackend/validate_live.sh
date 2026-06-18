#!/usr/bin/env bash
# FULL LIVE VALIDATION: stand up the faux stack on rpi4, redirect ONE camera to
# it via ARP/DNAT, and observe whether the camera connects to OUR broker / API /
# coturn. Scoped to one camera, time-bounded, fully self-restoring.
#
#   bash validate_live.sh [CAM_IP] [DURATION_S]
set -uo pipefail
cd "$(dirname "$0")"
RPI=192.168.1.206; CAM=${1:-192.168.1.242}; GW=192.168.1.1; IFACE=wlan1; DUR=${2:-210}
CERT=$(pwd)/certs/arnoo.crt; KEY=$(pwd)/certs/arnoo.key
added_rules=(); SPOOF=""

cleanup(){
  trap - EXIT INT TERM
  echo "=== teardown ==="
  [ -n "$SPOOF" ] && sudo kill "$SPOOF" 2>/dev/null
  for ((i=${#added_rules[@]}-1;i>=0;i--)); do sudo iptables ${added_rules[$i]} 2>/dev/null; done
  sudo pkill -f 'uvicorn app:app' 2>/dev/null; sudo pkill -x turnserver 2>/dev/null
  sudo pkill -x mosquitto 2>/dev/null; pkill -f mqtt_responder.py 2>/dev/null
  sudo arpspoof -i $IFACE -t $CAM $GW >/dev/null 2>&1 & local f=$!; sleep 0.4; sudo kill $f 2>/dev/null
  echo "nat now: $(sudo iptables -t nat -S | wc -l) (baseline 13); camera arp: $(ip neigh show $CAM | awk '{print $NF}')"
}
trap cleanup EXIT INT TERM

echo "=== bring up faux stack (target camera $CAM) ==="
sudo FAUX_HA_HOST=$RPI FAUX_TURN_HOST=$RPI FAUX_MQTT_WSS=wss://$RPI:8443/mqtt \
  /tmp/faux-venv/bin/uvicorn app:app --host 0.0.0.0 --port 443 --ssl-keyfile "$KEY" --ssl-certfile "$CERT" \
  >/tmp/faux-uvicorn.log 2>&1 &
mosquitto -c /tmp/mosq-live.conf >/tmp/faux-mosq.log 2>&1 &
sudo turnserver -c coturn/turnserver.conf -v >/tmp/faux-coturn.log 2>&1 &
sleep 2
FAUX_MQTT_HOST=127.0.0.1 FAUX_MQTT_PORT=1883 FAUX_MQTT_USER= FAUX_TURN_HOST=$RPI \
  ~/venv/bin/python mqtt_responder.py >/tmp/faux-responder.log 2>&1 &
sleep 2
echo "  uvicorn:$(grep -qc 'Uvicorn running' /tmp/faux-uvicorn.log && echo up) mosq8443:$(sudo ss -tlnp|grep -c :8443) api443:$(sudo ss -tlnp|grep -c ':443 ') turn3478:$(sudo ss -ulnp|grep -c :3478) responder:$(grep -qc 'faux responder on' /tmp/faux-responder.log && echo up)"

echo "=== DNAT camera cloud traffic -> rpi4 stack ==="
add(){ sudo iptables -t $1 -A "${@:2}"; added_rules+=("-t $1 -D ${*:2}"); }
addF(){ sudo iptables -I "$@"; added_rules+=("-D $*"); }
add nat PREROUTING -i $IFACE -s $CAM -p tcp --dport 8443 -j DNAT --to-destination $RPI:8443
add nat PREROUTING -i $IFACE -s $CAM -p tcp --dport 443  -j DNAT --to-destination $RPI:443
add nat PREROUTING -i $IFACE -s $CAM -p udp --dport 3478 -j DNAT --to-destination $RPI:3478
add nat PREROUTING -i $IFACE -s $CAM -p udp --dport 5349 -j DNAT --to-destination $RPI:3478
add nat POSTROUTING -s $CAM -o $IFACE -j MASQUERADE
addF FORWARD -s $CAM -j ACCEPT
addF FORWARD -d $CAM -j ACCEPT

sudo arpspoof -i $IFACE -r -t $CAM $GW >/tmp/faux-arpspoof.log 2>&1 & SPOOF=$!
echo "redirecting $CAM for ${DUR}s; watching our stack..."
END=$((SECONDS+DUR))
while [ $SECONDS -lt $END ]; do
  sleep 20
  mq=$(grep -ciE 'new client|new connection' /tmp/faux-mosq.log 2>/dev/null)
  api=$(grep -ciE '"(GET|POST)' /tmp/faux-uvicorn.log 2>/dev/null)
  tn=$(grep -ciE 'allocation|realm <arnoo' /tmp/faux-coturn.log 2>/dev/null)
  echo "  [$((END-SECONDS))s left] our-broker conns=$mq  faux-api reqs=$api  coturn=$tn"
done

echo "=== RESULTS ==="
echo "-- did the CAMERA connect to OUR Mosquitto? --"
grep -iE 'new client|new connection|socket error|disconnect' /tmp/faux-mosq.log | grep -v '127.0.0.1' | head -15 || echo "  (none)"
echo "-- camera HTTPS hits on faux API --"
grep -oE '"(GET|POST) [^"]+ HTTP|faux-unhandled.*' /tmp/faux-uvicorn.log | sort | uniq -c | sort -rn | head -12 || echo "  (none)"
echo "-- camera TURN at our coturn --"
grep -iE 'realm <arnoo|allocation|usage' /tmp/faux-coturn.log | tail -6 || echo "  (none)"
