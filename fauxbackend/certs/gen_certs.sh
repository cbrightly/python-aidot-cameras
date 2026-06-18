#!/usr/bin/env bash
# Mint the *.arnoo.com cert the cameras + bridge will accept.
# Cameras do NOT validate the CA (proven: self-signed accepted), so this works as
# a plain self-signed cert. We still set the right SANs so a hostname-checking
# client (the bridge / a future firmware) is also satisfied.
set -euo pipefail
cd "$(dirname "$0")"

DAYS=${1:-3650}
openssl req -x509 -newkey rsa:2048 -keyout arnoo.key -out arnoo.crt -days "$DAYS" -nodes \
  -subj "/CN=*.arnoo.com" \
  -addext "subjectAltName=DNS:*.arnoo.com,\
DNS:prod-us-api.arnoo.com,DNS:us-smarthome.arnoo.com,DNS:us-mqtt.arnoo.com,\
DNS:us-storage.arnoo.com,DNS:prod-us-app-log.arnoo.com,\
DNS:prod-eu-api.arnoo.com,DNS:eu-smarthome.arnoo.com,DNS:eu-mqtt.arnoo.com"
chmod 600 arnoo.key
echo "wrote arnoo.crt / arnoo.key"
openssl x509 -in arnoo.crt -noout -subject -ext subjectAltName
