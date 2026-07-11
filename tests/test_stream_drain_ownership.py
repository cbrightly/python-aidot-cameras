"""Persistent-MQTT signaling-drain ownership on the camera stream path.

The DeviceClient keeps a single backstop slot (_stream_mqtt_drain/_stream_mqtt_outq)
for a drain whose open was cancelled before a WebRTC/SdesSession took ownership.
A successful open hands ownership to the session (which reaps the drain on stop)
and clears the slot - so a *concurrent* open on the same camera (e.g. a snapshot
during a live view) no longer reaps, and kills, the live session's drain.
"""
import asyncio
import types

from aidot.camera.client import CameraMixin


class _FakeFut:
    def __init__(self):
        self.cancelled_flag = False

    def done(self):
        return False

    def cancel(self):
        self.cancelled_flag = True

    def __await__(self):
        if False:
            yield
        return None


class _RecordingQueue:
    def __init__(self):
        self.puts = []

    def put_nowait(self, item):
        self.puts.append(item)


def _client_with_drain():
    obj = types.SimpleNamespace(
        _stream_mqtt_drain=_FakeFut(),
        _stream_mqtt_outq=_RecordingQueue(),
        device_id="cam",
    )
    obj._reap_stream_drain = CameraMixin._reap_stream_drain.__get__(obj)
    obj._release_stream_drain_to_session = (
        CameraMixin._release_stream_drain_to_session.__get__(obj)
    )
    return obj


def test_handoff_makes_a_concurrent_reap_a_noop():
    obj = _client_with_drain()
    fut, q = obj._stream_mqtt_drain, obj._stream_mqtt_outq

    obj._release_stream_drain_to_session()          # session took ownership
    assert obj._stream_mqtt_drain is None

    asyncio.run(obj._reap_stream_drain())           # a concurrent open reaps
    assert q.puts == []                             # live drain NOT sentinel-killed
    assert not fut.cancelled_flag                   # live drain NOT cancelled


def test_backstop_still_reaps_an_orphaned_drain():
    # Without a hand-off (open cancelled mid-handshake) the slot still holds the
    # drain, and the reap must release it: sentinel to the queue + cancel.
    obj = _client_with_drain()
    fut, q = obj._stream_mqtt_drain, obj._stream_mqtt_outq

    asyncio.run(obj._reap_stream_drain())
    assert q.puts == [None]                          # orphan drain released
    assert fut.cancelled_flag
    assert obj._stream_mqtt_drain is None            # slot cleared
