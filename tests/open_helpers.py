"""Drive the open path's extracted helpers with fakes.

The opens themselves are thousands of lines that need a camera, but the
pieces that carry their invariants - the MQTT message handler, the trickle
consumer, the nomination from an answer, the stall report - are module
functions that take their collaborators as arguments. These helpers call
them with recording fakes so a test can assert what they did.
"""

from __future__ import annotations

import asyncio
import time as _real_time
import types


def drive_trickle(monkeypatch, lines, *, uc_cands=None):
    """Run ``consume_camera_trickle`` over trickled ``lines`` on a fake clock.

    Returns ``(installed, uc_info, original_cands)``: the ``(ip, port, why)``
    of every permission installed, the nomination dict after the run, and
    the candidate list object the bridge was iterating before it.
    """
    import aidot_cameras.camera.sdes_open as so

    clock = [0.0]
    fake_time = types.SimpleNamespace(
        **{k: getattr(_real_time, k) for k in dir(_real_time) if not k.startswith("_")}
    )
    fake_time.monotonic = lambda: clock[0]
    monkeypatch.setattr(so, "time", fake_time)
    real_sleep = asyncio.sleep

    async def _sleep(d, *a, **k):
        clock[0] += d
        await real_sleep(0)

    monkeypatch.setattr(so.asyncio, "sleep", _sleep)
    installed = []

    def _install(cands, why):
        installed.extend((ip, port, why) for ip, port in cands)

    original = list(uc_cands or [])
    uc_info = {"cands": original, "sent": False, "prflx": []}
    seen = [{"candidate": line} for line in lines]
    asyncio.run(so.consume_camera_trickle(_install, uc_info, seen, timeout=1.0))
    return installed, uc_info, original


class _Loop:
    """Records what the MQTT thread hands to the event loop."""

    def __init__(self) -> None:
        self.calls = []

    def call_soon_threadsafe(self, fn, *args):
        self.calls.append((fn, args))


def mqtt_handler(*, peer_id="pid-1", device_id="dev-1", user_id="user-1"):
    """The DTLS open's MQTT handler, built on fakes.

    Returns ``(handler, owner, loop, ice_cands_seen)``; ``owner`` stands in
    for the client the handler stamps the answer marker on.
    """
    from aidot_cameras.camera.webrtc_open import make_mqtt_message_handler

    owner = types.SimpleNamespace(
        _camera_answer_ice_ready_ts=None, _camera_device_seen_ts=None
    )
    loop = _Loop()
    seen: list = []

    class _Fut:
        def done(self):
            return False

    handler = make_mqtt_message_handler(
        self=owner,
        _spawn_bg=lambda *a, **k: None,
        _status=lambda *a, **k: None,
        _trace=lambda *a, **k: None,
        _wake_keepalive_sent=[False],
        answer_fut=_Fut(),
        camera_offer_fut=_Fut(),
        camera_ready_ev=types.SimpleNamespace(set=lambda: None),
        camera_reconnect_ev=types.SimpleNamespace(set=lambda: None),
        device_id=device_id,
        ice_cands_seen=seen,
        ice_config_fut=_Fut(),
        ice_q=types.SimpleNamespace(put_nowait=lambda c: None),
        liveplay_echo_ev=types.SimpleNamespace(set=lambda: None),
        liveplay_resp_fut=_Fut(),
        loop=loop,
        peer_id=peer_id,
        second_answer_fut=_Fut(),
        terminal_error_fut=_Fut(),
        user_id=user_id,
        webrtc_req_echo_fut=_Fut(),
    )
    return handler, owner, loop, seen
