# AiDot faux backend (self-hosted / blackhole)

**Status: SCAFFOLD — not deployed. Do not point live cameras at this without reading "Activation" below.**

Goal: run the AiDot cameras + this project's bridge entirely against infrastructure we
control, with AiDot's cloud (`*.arnoo.com`) blackholed. Built from the live-tested
findings recorded in the `blackhole-feasibility` memory.

## Why this is viable (all gates proven on real hardware, 2026-06-18)

- **TLS impersonation works** — the cameras do **not** validate TLS certs. A self-signed
  `*.arnoo.com` cert was accepted (`HANDSHAKE-OK`) on every intercepted `:443` connection.
  So we can transparently terminate every arnoo HTTPS + MQTT-wss endpoint.
- **TURN is redirectable (Case A)** — the bridge (and camera) receive their TURN server from
  `getIceConfigResp` over MQTT, which we own. We hand out our own coturn instead of
  `3.230.182.123`.
- **Remote viewing works** over public internet via srflx/relay; **LAN viewing** works (often
  via relay as the bridge behaves today — so coturn is required, not optional).
- **Camera redirect needs no router** — either AdGuard DNS rewrites (if cameras resolve through
  it) or the L3 ARP/DNAT intercept (works even if cameras hardcode DNS).

## The four planes and how this replaces them

| Plane | Real AiDot | Replacement here |
|---|---|---|
| HTTPS API (auth, discovery, device list, ICE config, storage, telemetry) | `prod-{r}-api.arnoo.com`, `{r}-smarthome.arnoo.com`, `us-storage.arnoo.com`, `prod-us-app-log.arnoo.com` | `app.py` (FastAPI), TLS-terminated with our `*.arnoo.com` cert |
| MQTT broker + signaling | `{r}-mqtt.arnoo.com:8443` (wss) | **HA Mosquitto** + `mosquitto/aidot-wss.conf` listener |
| Cloud-side signaling responder (`getIceConfigReq` → TURN creds) | AiDot cloud service | `mqtt_responder.py` |
| TURN relay | `3.230.182.123` | our **coturn** (`coturn/turnserver.conf`) |

## Components

- `app.py` — FastAPI app implementing the arnoo HTTPS endpoints the bridge + camera call.
  Points MQTT discovery at HA's broker and ICE config at our coturn. Response shapes are
  modeled from the captured inventory; ones still needing validation are marked `TODO-SHAPE`.
- `mqtt_responder.py` — paho client on HA's broker that answers `getIceConfigReq` with our
  coturn, ACKs `lowPowerActiveState`, etc. (the bits the cloud used to do).
- `mosquitto/aidot-wss.conf` — drop into HA Mosquitto's `customize` folder to add the
  `wss://:8443/mqtt` listener the cameras dial.
- `coturn/turnserver.conf` — our relay.
- `certs/gen_certs.sh` — mint the `*.arnoo.com` cert (cameras accept self-signed; we add the
  right SANs so even a hostname-checking client is satisfied).
- `endpoints.md` — the observed cloud-endpoint surface (grows as we capture more).
- `config.example.env` — broker host, coturn host/secret, device-list path.

## Activation (NOT done automatically)

1. Generate certs: `certs/gen_certs.sh`.
2. Stand up coturn (`coturn/turnserver.conf`) and the faux backend (`app.py` behind a TLS
   terminator on :443, and the camera's other arnoo hostnames).
3. Add the Mosquitto wss listener on HA; start `mqtt_responder.py`.
4. Redirect the cameras to all of it, by ONE of:
   - **AdGuard DNS rewrites** (`adguard-rewrites.md`) — only if the cameras resolve through
     AdGuard (verify in AdGuard query log first).
   - **L3 ARP/DNAT** (`/home/chris/arp_dnat_intercept.sh` pattern) — works regardless of DNS.
5. Blackhole `*.arnoo.com` only AFTER confirming the cameras stay healthy talking to us.

## Safety

Everything here is inert until you deploy it. The blackhole step is last and reversible
(remove the DNS rewrites / stop arpspoof). Validate one camera before fleet-wide.
