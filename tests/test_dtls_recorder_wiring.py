"""Which recorder a DTLS ``output_path`` gets, and how it is fed.

The copy recorder only helps if the session actually uses it and taps the
receivers before decode. This drives ``_attach_file_recorder`` with a fake peer
connection that fires the same ``track`` events aiortc does.
"""

from types import SimpleNamespace

from aidot_cameras.camera.client import CameraMixin
from aidot_cameras.camera.recording import TsCopyRecorder


class _Enc:
    def __init__(self, data, timestamp):
        self.data, self.timestamp = data, timestamp


class _DecoderQueue:
    def __init__(self):
        self.puts = []

    def put(self, task, *a, **k):
        self.puts.append(task)


class _PC:
    def __init__(self):
        self._handlers = []
        self._receivers = []

    def on(self, event):
        def deco(fn):
            self._handlers.append((event, fn))
            return fn

        return deco

    def getReceivers(self):
        return list(self._receivers)

    def fire_track(self, kind):
        track = SimpleNamespace(kind=kind)
        rcv = SimpleNamespace(
            track=track, _RTCRtpReceiver__decoder_queue=_DecoderQueue()
        )
        self._receivers.append(rcv)
        for event, fn in self._handlers:
            if event == "track":
                fn(track)
        return rcv


def _client():
    c = CameraMixin.__new__(CameraMixin)
    c.device_id = "0123456789abcdef"
    return c


_IDR = b"\x00\x00\x01\x65\x88\x84\x00\x10"


def test_ts_recording_copies_and_skips_the_decode_nobody_reads():
    pc = _PC()
    rec = _client()._attach_file_recorder(pc, "/tmp/x.ts", on_frame=None)
    assert isinstance(rec, TsCopyRecorder)
    v = pc.fire_track("video")
    a = pc.fire_track("audio")
    v._RTCRtpReceiver__decoder_queue.put((0, _Enc(_IDR, 1000)))
    a._RTCRtpReceiver__decoder_queue.put((0, _Enc(b"\xd5" * 160, 8000)))
    assert rec.vq.get_nowait() == (_IDR, 1000, True)
    assert rec.aq.get_nowait() == (b"\xd5" * 160, 8000)
    assert v._RTCRtpReceiver__decoder_queue.puts == []  # no on_frame: no decode
    assert len(a._RTCRtpReceiver__decoder_queue.puts) == 1  # audio drain still fed


def test_ts_recording_with_on_frame_still_feeds_the_decoder():
    pc = _PC()
    rec = _client()._attach_file_recorder(pc, "/tmp/x.TS", on_frame=lambda f: None)
    assert isinstance(rec, TsCopyRecorder)
    v = pc.fire_track("video")
    v._RTCRtpReceiver__decoder_queue.put((0, _Enc(_IDR, 1000)))
    assert rec.vq.get_nowait() == (_IDR, 1000, True)
    assert len(v._RTCRtpReceiver__decoder_queue.puts) == 1


def test_only_the_first_video_track_is_recorded():
    pc = _PC()
    rec = _client()._attach_file_recorder(pc, "/tmp/x.ts", on_frame=None)
    pc.fire_track("video")
    second = pc.fire_track("video")
    second._RTCRtpReceiver__decoder_queue.put((0, _Enc(_IDR, 1000)))
    assert rec.vq.empty()


def test_other_containers_keep_the_re_encoding_recorder():
    rec = _client()._attach_file_recorder(_PC(), "/tmp/x.mp4", on_frame=None)
    assert type(rec).__name__ == "MediaRecorder"


def test_no_output_path_no_recorder():
    assert _client()._attach_file_recorder(_PC(), None, on_frame=None) is None
