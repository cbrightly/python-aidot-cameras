# AdGuard DNS rewrites (redirect cameras + bridge to the faux backend)

Use these ONLY if the cameras actually resolve through AdGuard. **Verify first:**
AdGuard → Query Log, search `arnoo`. If no arnoo lookups appear, the cameras
bypass AdGuard (router→ISP, or hardcoded `8.8.8.8`) — use the L3 ARP/DNAT path
instead (`/home/chris/arp_dnat_intercept.sh` pattern), which needs no DNS.

In AdGuard → Filters → DNS rewrites, add (answer = HA / faux-backend IP):

| Domain | Answer | Serves |
|---|---|---|
| `*.arnoo.com` | `192.168.1.162` | broad catch-all (simplest) |

…or, if you prefer to keep some hosts real, rewrite only:

| Domain | Answer |
|---|---|
| `prod-us-api.arnoo.com` | `192.168.1.162` |
| `us-smarthome.arnoo.com` | `192.168.1.162` |
| `us-mqtt.arnoo.com` | `192.168.1.162` |
| `us-storage.arnoo.com` | `192.168.1.162` |
| `prod-us-app-log.arnoo.com` | `192.168.1.162` |

The faux backend must then answer on `192.168.1.162` for:
- `:443` (TLS, arnoo cert) → `app.py`
- `:8443` (wss MQTT) → HA Mosquitto `aidot-wss.conf`
- coturn `:3478`

Roll back = delete the rewrites. Validate one camera before fleet-wide; blackhole
`*.arnoo.com` upstream only after the cameras are confirmed healthy talking to us.
