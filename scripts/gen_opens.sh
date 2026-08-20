#!/bin/zsh
# Generate serve-loop opens on demand: reload the AiDot config entry, which
# tears every session down and re-opens it - the same cold open the serve loop
# performs after a failure. ~6 DTLS opens per reload.
#
# This samples the camera's ANSWER-LATENCY distribution, which is what the
# timeout change acts on and is a per-open property the camera decides without
# any knowledge of our timeout. It does NOT sample the churn RATE, which needs
# organic load.
HA=$(cat ~/.config/aidot/ha_token)
ENTRY=01KWQFGKBQC4Q95RASV2B02ST2
N=${1:-16}          # reloads
GAP=${2:-100}       # seconds between reloads
for i in $(seq 1 $N); do
  R=$(curl -s -m 120 -X POST -H "Authorization: Bearer $HA" \
      "http://homeassistant.local:8123/api/config/config_entries/entry/$ENTRY/reload")
  echo "$(date '+%H:%M:%S') reload $i/$N -> $R"
  sleep $GAP
done
