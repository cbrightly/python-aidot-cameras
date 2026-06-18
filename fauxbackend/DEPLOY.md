# Deploying the AiDot faux backend

## What's proven (validated tonight on rpi4, component-by-component)

| Plane | How it was validated | Result |
|---|---|---|
| HTTP API (`app.py`) | drove the **real** `AidotClient` against it (`run_integration.sh`) | bridge logged in + enumerated all **42 devices / 7 cameras** |
| TURN relay (coturn) | `turnutils_uclient -u aidot -w aidot` | ALLOCATE + REFRESH succeed; `realm=arnoo.com user=aidot` |
| MQTT signaling (`mqtt_responder.py`) | published a `getIceConfigReq`, read the reply | got `getIceConfigResp` → our coturn |

Not yet done end-to-end: a **real camera** streaming through the stack — that
needs the camera redirected (the supervised step below), which I did not do
unsupervised because it touches the live fleet.

## 0. Pick a host

Any always-on Linux box on the camera LAN with docker (a Pi/VM). It must be able
to bind `:443`, `:8443`, `:3478`, `:1883` (compose uses host networking). **Don't
reuse HA's shared Mosquitto** — this ships a dedicated broker so a camera issue
can't break Zigbee2MQTT/Govee.

## 1. Prepare

```bash
cd fauxbackend
cp config.example.env .env          # set FAUX_HA_HOST/FAUX_TURN_HOST = this host's LAN IP
certs/gen_certs.sh                   # -> certs/arnoo.{crt,key}
# devices.json: capture once from the real cloud (see scripts in the repo / the
# dump_devices pattern). It's gitignored. Without it, device list is empty.
```

For **remote** viewing, set coturn's `external-ip=<WAN_IP>` in `coturn/turnserver.conf`
and port-forward `3478/udp+tcp` (+`49152-65535/udp`) to the host.

## 2. Bring up the stack

```bash
docker compose up -d
docker compose ps          # faux-api, responder, mosquitto, coturn all Up
```

## 3. Point the BRIDGE (this project) at the faux backend

The bridge trusts real CAs, so install our cert as trusted on the bridge host
(or run it with the cert): copy `certs/arnoo.crt` into the system trust store, or
set the integration to use it. Then redirect the arnoo API hosts to this host —
e.g. `/etc/hosts` or your DNS:
```
<host-ip>  prod-us-api.arnoo.com us-smarthome.arnoo.com us-storage.arnoo.com prod-us-app-log.arnoo.com us-mqtt.arnoo.com
```
Validate: `bash run_integration.sh` style check → bridge enumerates cameras.

## 4. Redirect the CAMERAS  ← the supervised step

Cameras don't validate TLS (proven), so they need no cert trust — just point them
at us. Two options:

- **AdGuard DNS rewrites** (`adguard-rewrites.md`) — ONLY if the cameras resolve
  through AdGuard. Verify first: AdGuard → Query Log, search `arnoo`. If empty,
  use the next option.
- **L3 ARP/DNAT** (the `/home/chris/arp_dnat_intercept.sh` pattern, generalized to
  DNAT arnoo `:443/:8443/:3478` → this host instead of the TLS observer). Works
  even if cameras hardcode DNS.

Do **one** camera first. Watch `faux-api` logs (`docker compose logs -f faux-api`)
for its calls and the `[faux-unhandled]` lines — any unmodeled endpoint shows up
there; add it to `app.py`. Then a `coturn` allocation from the camera's IP confirms
relay redirect (and answers the last open question: camera TURN is redirectable).

## 5. Blackhole AiDot (last, reversible)

Only after a camera is confirmed healthy talking to us: blackhole `*.arnoo.com`
upstream (AdGuard blocklist, or firewall). Roll back instantly by removing the
rewrite/blackhole.

## Rollback

- Cameras: remove the AdGuard rewrite / stop the ARP-DNAT → they fall back to the
  real cloud on next DNS/connect.
- Bridge: remove the `/etc/hosts`/DNS entries.
- `docker compose down`.

## Open items (TODO-SHAPE)

`app.py` stubs marked `TODO-SHAPE` (liveStreamParam, batchGetDeviceUserInfo,
playback) are best-guess; the catch-all logs anything the camera/bridge actually
calls so you can firm them up from real traffic. Snapshot upload + telemetry
(`us-storage`, `prod-us-app-log`) are accepted/swallowed.
