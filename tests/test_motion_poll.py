"""Unit tests for cloud-event motion polling - deterministic, no network.

Validates the prime/dedup logic of `_motion_poll_loop`: the first poll marks the existing
backlog as seen WITHOUT firing, subsequent polls fire the callback only on genuinely-new
events (deduped by eventUuid), in eventTime order.

Runs under pytest, or standalone:  python tests/test_motion_poll.py
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.device_client import DeviceClient


class _FakeDC:
    """Minimal stand-in exposing only what _motion_poll_loop touches."""
    device_id = "dev-test"

    def __init__(self, scripted_polls):
        self._scripts = scripted_polls
        self._call = 0
        self._motion_active = True
        self._motion_cb = None
        self._motion_seen: dict = {}  # insertion-ordered set (dict keys); see CameraMixin
        self._motion_interval = 0.01  # fast for the test

    async def async_get_cloud_recordings(self, *a, **k):
        idx = min(self._call, len(self._scripts) - 1)
        self._call += 1
        if self._call >= len(self._scripts) + 1:  # let the loop run each script once, then stop
            self._motion_active = False
        return list(self._scripts[idx])


def _ev(uid, t):
    return {"eventUuid": uid, "eventTime": t, "picUrl": f"http://x/{uid}.jpg"}


def _run(scripts):
    fired = []
    fake = _FakeDC(scripts)
    fake._motion_cb = lambda ev: fired.append(ev["eventUuid"])
    asyncio.run(DeviceClient._motion_poll_loop(fake, 600))
    return fired


def test_first_poll_primes_without_firing():
    # Backlog [a,b] on first poll must NOT fire; nothing new after.
    fired = _run([[_ev("a", 1), _ev("b", 2)], [_ev("a", 1), _ev("b", 2)]])
    assert fired == []


def test_new_event_fires_after_prime():
    fired = _run([
        [_ev("a", 1), _ev("b", 2)],            # prime
        [_ev("a", 1), _ev("b", 2), _ev("c", 3)],  # c is new -> fire
        [_ev("c", 3)],                          # nothing new
    ])
    assert fired == ["c"]


def test_dedup_does_not_refire():
    fired = _run([
        [_ev("a", 1)],                          # prime
        [_ev("a", 1), _ev("b", 2)],             # b new
        [_ev("a", 1), _ev("b", 2)],             # b already seen -> no refire
        [_ev("b", 2)],
    ])
    assert fired == ["b"]


def test_multiple_new_events_fire_in_time_order():
    fired = _run([
        [],                                     # prime (empty backlog)
        [_ev("z", 30), _ev("y", 20), _ev("x", 10)],  # 3 new, out of order
    ])
    assert fired == ["x", "y", "z"]            # sorted by eventTime ascending


if __name__ == "__main__":
    fns = [v for k, v in sorted(globals().items()) if k.startswith("test_") and callable(v)]
    for fn in fns:
        fn()
        print(f"PASS {fn.__name__}")
    print(f"\nALL {len(fns)} TESTS PASSED")
