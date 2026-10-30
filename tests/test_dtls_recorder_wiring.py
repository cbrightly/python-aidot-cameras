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


def test_an_unwritable_ts_path_records_nothing_and_says_so(caplog, tmp_path):
    # Failing late - once the session is connected - leaked the connected peer
    # and the camera's viewer slot. Fail before the offer, and keep the session.
    with caplog.at_level("WARNING"):
        rec = _client()._attach_file_recorder(
            _PC(), str(tmp_path / "no-such-dir" / "x.ts"), on_frame=None
        )
    assert rec is None
    assert any("will not be recorded" in r.getMessage() for r in caplog.records)


def test_tapping_a_receiver_twice_for_different_queues_is_flagged(caplog):
    import queue

    rcv = SimpleNamespace(_RTCRtpReceiver__decoder_queue=_DecoderQueue())
    first, second = queue.Queue(), queue.Queue()
    assert CameraMixin._install_encoded_tap(rcv, first, True)
    with caplog.at_level("WARNING"):
        CameraMixin._install_encoded_tap(rcv, second, True, serve=True)
    assert any("already tapped" in r.getMessage() for r in caplog.records)


def test_the_recording_file_is_opened_off_the_event_loop(tmp_path, monkeypatch):
    # Home Assistant flagged "Detected blocking call to open ... .part.ts"
    # on every DTLS card-recording fetch (2026-10-29): the recorder opened its
    # file in its constructor, on the loop. The open phase now opens it in an
    # executor and hands it over.
    import asyncio
    import threading

    from aidot_cameras.camera import webrtc_open
    from aidot_cameras.camera.recording import TsCopyRecorder

    loop_thread, opened_on = [], []
    real_open = open

    def _spy(*a, **k):
        opened_on.append(threading.current_thread())
        return real_open(*a, **k)

    async def main():
        loop_thread.append(threading.current_thread())
        monkeypatch.setattr("builtins.open", _spy)
        fh = await webrtc_open._open_ts_output(str(tmp_path / "clip.part.ts"))
        monkeypatch.setattr("builtins.open", real_open)
        assert await webrtc_open._open_ts_output(str(tmp_path / "clip.mp4")) is None
        assert await webrtc_open._open_ts_output(None) is None
        return fh

    fh = asyncio.run(main())
    assert fh is not None and opened_on and opened_on[0] is not loop_thread[0]
    rec = TsCopyRecorder(str(tmp_path / "clip.part.ts"), file=fh)
    assert rec._file is fh
    fh.close()
