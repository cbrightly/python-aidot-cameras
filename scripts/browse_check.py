#!/usr/bin/env python3
"""Does the cloud behave the way the browser now assumes?

Three assumptions carry the day-folder design, and all three are the server's
to keep, not ours:

  * a page is capped at 10 items whatever page_size asks for;
  * paging a single day reaches every event in it - checked against the
    server's own total count, not just against a bigger single request;
  * the plan endpoint answers for every camera.

A camera's own `total` (read straight from the response envelope, which
`async_get_cloud_recordings` normally discards) is the yardstick every
verdict is measured against, so a failed or empty call cannot masquerade as
a passing result.

Read-only. Credentials come from aidot_cameras.credentials.load_credentials.
"""
import asyncio
import time

import aiohttp

from aidot_cameras.client import AidotClient
from aidot_cameras.const import CONF_DEVICE_LIST, CONF_NAME
from aidot_cameras.credentials import load_credentials

MS_DAY = 86_400_000


async def main() -> int:
    creds = load_credentials()
    async with aiohttp.ClientSession() as http:
        client = AidotClient(
            http, country_code=creds.get("country", "US"),
            username=creds["username"], password=creds["password"])
        try:
            await client.async_post_login()
            devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
            cams = [d for d in devices
                    if "IPC" in (getattr(getattr(client.get_device_client(d),
                                                 "info", None), "model_id", "")
                                 or "")]
            now = int(time.time() * 1000)
            print(f"{'camera':30} {'ask30':>6} {'paged':>6} {'pages':>6} "
                  f"{'total':>6}  {'verdict':<14} plan")
            for cam in cams:
                dc = client.get_device_client(cam)
                one = await dc.async_get_cloud_recordings(
                    now - MS_DAY, now, page=1, page_size=30)
                seen, page = [], 1
                while True:
                    batch = await dc.async_get_cloud_recordings(
                        now - MS_DAY, now, page=page, page_size=10)
                    if not batch:
                        break
                    seen.extend(batch)
                    if len(batch) < 10 or page > 40:
                        break
                    page += 1

                # The server's own count, read directly from the envelope
                # that async_get_cloud_recordings discards, so a failed or
                # empty call cannot be mistaken for "nothing to find".
                code, total = None, None
                try:
                    async with http.post(
                        f"{dc._aidot_v32_base}/playback/eventRecordingList",
                        json={"deviceIds": [dc.device_id], "pageNum": 1,
                              "pageSize": 1, "recordSta": now - MS_DAY,
                              "recordEnd": now},
                        headers=dc._aidot_headers(),
                        timeout=aiohttp.ClientTimeout(total=30),
                    ) as resp:
                        body = await resp.json(content_type=None)
                    code = body.get("code") if isinstance(body, dict) else None
                    total = ((body.get("data") or {}).get("total")
                              if isinstance(body, dict) else None)
                except Exception:
                    code, total = None, None

                if code != 200 or total is None:
                    verdict = "ERROR"
                elif total == 0:
                    verdict = "NO EVENTS"
                elif total > 10 and len(one) > 10:
                    verdict = "CAP NOT HELD"
                elif total > 10 and len(seen) < total:
                    verdict = "INCOMPLETE"
                elif total > 10:
                    verdict = "OK"
                else:
                    verdict = "THIN"

                plan = None
                _plan_fn = getattr(dc, "async_get_cloud_plan", None)
                if _plan_fn is not None:
                    plan = await _plan_fn()
                if _plan_fn is None:
                    state = "not implemented yet"
                elif plan:
                    left = plan.get("expiredDays")
                    state = f"{plan.get('packageName', '?').strip()} ({left}d)"
                else:
                    state = "none"
                total_str = "?" if total is None else str(total)
                print(f"{cam.get(CONF_NAME)!r:30} {len(one):>6} {len(seen):>6} "
                      f"{page:>6} {total_str:>6}  {verdict:<14} {state}")
        finally:
            await client.async_cleanup()
    return 0


if __name__ == "__main__":
    raise SystemExit(asyncio.run(main()))
