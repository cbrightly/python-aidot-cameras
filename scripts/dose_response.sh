#!/bin/zsh
# Dose-response: does the camera's answer latency climb with OUR open rate?
#
# ABAB, not a ramp. A monotonic ramp confounds dose with elapsed time - if the
# path degrades on its own the ramp shows a false dose-response. Alternating
# blocks let a time trend be separated from a dose effect: a real effect tracks
# the dose on BOTH cycles, a time trend does not.
#
# A = low dose  (reload every 200s ~=  108 opens/h)
# B = high dose (reload every  40s ~=  540 opens/h, ~4x the organic churn rate)
HA=$(cat ~/.config/aidot/ha_token)
ENTRY=01KWQFGKBQC4Q95RASV2B02ST2
BLOCK=${1:-720}          # seconds per block
reload() {
  curl -s -m 120 -X POST -H "Authorization: Bearer $HA" \
    "http://homeassistant.local:8123/api/config/config_entries/entry/$ENTRY/reload" >/dev/null
}
for cycle in 1 2; do
  for arm in A B; do
    [[ $arm == A ]] && GAP=200 || GAP=40
    echo "BLOCK $arm cycle$cycle start $(date '+%Y-%m-%d %H:%M:%S') gap=${GAP}s"
    END=$(( $(date +%s) + BLOCK ))
    while [ $(date +%s) -lt $END ]; do
      reload
      echo "  $(date '+%H:%M:%S') reload ($arm)"
      sleep $GAP
    done
    echo "BLOCK $arm cycle$cycle end   $(date '+%Y-%m-%d %H:%M:%S')"
  done
done
echo "DONE $(date '+%Y-%m-%d %H:%M:%S')"
