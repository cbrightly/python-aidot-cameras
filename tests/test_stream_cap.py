"""Unit tests for the concurrent-stream cap (AIDOT_MAX_CONCURRENT_STREAMS).

The cap limits how many cameras serve at once so a small host (Raspberry Pi)
isn't overwhelmed by N decode + mux + AAC pipelines.  No camera/network needed.
"""
import asyncio
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

# stream-cap singleton lives in the camera module after the camera extraction
import aidot.camera.client as dc


def _fresh_slots(n):
    dc._STREAM_SLOTS = None
    os.environ["AIDOT_MAX_CONCURRENT_STREAMS"] = str(n)
    try:
        return dc._get_stream_slots()
    finally:
        os.environ.pop("AIDOT_MAX_CONCURRENT_STREAMS", None)


def test_default_is_three_and_singleton():
    dc._STREAM_SLOTS = None
    os.environ.pop("AIDOT_MAX_CONCURRENT_STREAMS", None)
    s1 = dc._get_stream_slots()
    s2 = dc._get_stream_slots()
    assert s1 is s2                      # one global cap shared across cameras
    assert s1._value == 3                # default


def test_env_tunable():
    assert _fresh_slots(5)._value == 5
    assert _fresh_slots(1)._value == 1


def test_bad_env_falls_back_to_default():
    dc._STREAM_SLOTS = None
    os.environ["AIDOT_MAX_CONCURRENT_STREAMS"] = "not-a-number"
    try:
        assert dc._get_stream_slots()._value == 3
    finally:
        os.environ.pop("AIDOT_MAX_CONCURRENT_STREAMS", None)


def test_cap_blocks_beyond_limit_then_frees():
    async def _run():
        sem = _fresh_slots(2)
        await sem.acquire()
        await sem.acquire()
        assert sem.locked()
        # a 3rd acquire must block until a slot frees
        try:
            await asyncio.wait_for(sem.acquire(), timeout=0.2)
            raise AssertionError("3rd acquire should have blocked")
        except TimeoutError:
            pass
        sem.release()
        await asyncio.wait_for(sem.acquire(), timeout=0.2)  # now proceeds
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
