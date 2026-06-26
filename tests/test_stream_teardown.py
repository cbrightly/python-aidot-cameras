"""Unit test: async_stop_streaming reaps an orphaned persistent-MQTT drain.

If a stream open is cancelled before a WebRTCSession takes ownership of the
drain task, the drain would otherwise block forever on outgoing_q.get with its
handler still registered. async_stop_streaming must cancel it (its finally then
removes the handler). No camera needed.
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

import aidot_cameras.camera.client as cc

_CAM = next(v for v in vars(cc).values()
            if isinstance(v, type) and "async_stop_streaming" in v.__dict__)


def _bare_client():
    c = _CAM.__new__(_CAM)
    c._streaming_active = True
    c._serve_ready = asyncio.Event()
    c._go2rtc_task = None
    c._stream_session = None
    c._stream_task = None
    c._stream_mqtt_drain = None

    async def _noop_deregister():
        return None
    c._deregister_go2rtc = _noop_deregister
    return c


def test_orphaned_drain_is_cancelled():
    async def _run():
        c = _bare_client()
        handler_removed = {"v": False}

        async def _drain():
            try:
                await asyncio.Event().wait()      # blocks forever, like outgoing_q.get
            finally:
                handler_removed["v"] = True       # the real finally calls remove_handler
        c._stream_mqtt_drain = asyncio.ensure_future(_drain())
        await asyncio.sleep(0)                     # let the drain start blocking

        await c.async_stop_streaming()

        assert c._stream_mqtt_drain is None        # cleared
        assert handler_removed["v"] is True        # drain's finally ran (handler removed)
    asyncio.run(_run())


def test_no_drain_is_safe():
    async def _run():
        c = _bare_client()                         # _stream_mqtt_drain is None
        await c.async_stop_streaming()             # must not raise
        assert c._streaming_active is False
    asyncio.run(_run())


if __name__ == "__main__":
    import traceback
    _fail = 0
    for _k, _v in sorted(globals().items()):
        if _k.startswith("test_"):
            try:
                _v()
                print(f"PASS {_k}")
            except Exception:
                _fail += 1
                print(f"FAIL {_k}")
                traceback.print_exc()
    raise SystemExit(1 if _fail else 0)
