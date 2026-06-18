#!/usr/bin/env python3
"""Drive the REAL aidot bridge against the faux backend (HTTP plane).

Proves the faux backend speaks the bridge's protocol: login → device enumeration
→ MQTT/ICE config, all served by app.py instead of arnoo's cloud.

Prereqs (the runner script sets these up):
  * faux backend serving TLS on :443 (uvicorn app:app --ssl-*)
  * /etc/hosts: prod-us-api.arnoo.com + us-smarthome.arnoo.com → 127.0.0.1
  * ssl=False here so the bridge accepts the faux cert (real deploys install the
    cert as trusted on the bridge host instead).

Run via run_integration.sh. Exit 0 if the bridge enumerates cameras via faux.
"""
import asyncio
import sys

import aiohttp

from aidot.client import AidotClient
from aidot.const import CONF_DEVICE_LIST, CONF_NAME


async def main() -> int:
    conn = aiohttp.TCPConnector(ssl=False)
    async with aiohttp.ClientSession(connector=conn) as s:
        c = AidotClient(s, country_code="US",
                        username="faux@example.com", password="fauxpassword")
        try:
            await c.async_post_login()
            print("✓ async_post_login() succeeded against faux backend")
        except Exception as e:
            print(f"✗ login failed: {type(e).__name__}: {e}")
            return 1
        try:
            dl = (await c.async_get_all_device())[CONF_DEVICE_LIST]
        except Exception as e:
            print(f"✗ device enumeration failed: {type(e).__name__}: {e}")
            await c.async_cleanup()
            return 1
        cams = []
        for d in dl:
            dc = c.get_device_client(d)
            model = getattr(getattr(dc, "info", None), "model_id", "") or ""
            if "IPC" in model:
                cams.append((d.get(CONF_NAME), model))
        print(f"✓ bridge enumerated {len(dl)} devices, {len(cams)} cameras VIA FAUX BACKEND:")
        for name, model in cams:
            print(f"    - {name!r:28} {model}")
        await c.async_cleanup()
        return 0 if cams else 1


if __name__ == "__main__":
    sys.exit(asyncio.run(main()))
