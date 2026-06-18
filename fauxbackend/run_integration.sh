#!/usr/bin/env bash
# Stand up the faux backend with TLS, redirect arnoo API hosts to it via
# /etc/hosts, and drive the real bridge against it. Self-restoring.
set -uo pipefail
cd "$(dirname "$0")"
HERE=$(pwd)
VENV=${FAUX_VENV:-/tmp/faux-venv}
BRIDGE_PY=${BRIDGE_PY:-$HOME/venv/bin/python}
HOSTS=(prod-us-api.arnoo.com us-smarthome.arnoo.com us-storage.arnoo.com prod-us-app-log.arnoo.com)
MARK="# faux-backend-test"
UVPID=""

cleanup() {
  trap - EXIT INT TERM
  [ -n "$UVPID" ] && sudo kill "$UVPID" 2>/dev/null
  sudo pkill -f "uvicorn app:app" 2>/dev/null
  sudo sed -i "/$MARK/d" /etc/hosts
  echo "--- cleaned up (uvicorn stopped, /etc/hosts restored) ---"
}
trap cleanup EXIT INT TERM

echo "=== start faux backend (TLS :443) ==="
sudo "$VENV/bin/uvicorn" app:app --host 0.0.0.0 --port 443 \
  --ssl-keyfile certs/arnoo.key --ssl-certfile certs/arnoo.crt \
  >/tmp/faux-uvicorn.log 2>&1 &
UVPID=$!
sleep 3
grep -qiE "running on|started server" /tmp/faux-uvicorn.log && echo "uvicorn up" || { echo "uvicorn failed:"; tail -5 /tmp/faux-uvicorn.log; exit 1; }

echo "=== redirect arnoo API hosts -> 127.0.0.1 (/etc/hosts) ==="
for h in "${HOSTS[@]}"; do echo "127.0.0.1 $h $MARK" | sudo tee -a /etc/hosts >/dev/null; done
getent hosts prod-us-api.arnoo.com | head -1

echo "=== drive the REAL bridge against the faux backend ==="
PYTHONPATH=../src FAUX_DEVICES="$HERE/devices.json" timeout 90 "$BRIDGE_PY" integration_test.py
RC=$?
echo "=== faux backend saw these requests ==="
grep -E "GET|POST|faux-unhandled" /tmp/faux-uvicorn.log | grep -oE '"(GET|POST) [^"]+"|faux-unhandled[^ ]* [A-Z]+ [^ ]+' | sort | uniq -c | sort -rn | head -25
exit $RC
