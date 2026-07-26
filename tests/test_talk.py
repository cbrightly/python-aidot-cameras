"""Unit tests for two-way audio (talk) - deterministic, no camera required.

Validates the live push-to-talk API on WebRTCSession (replaceTrack enable/disable +
SPEAKERSTART/STOP AVIO commands) and the PCMA talk-track frame format.

Runs under pytest, or standalone:  python tests/test_talk.py
"""
import asyncio
import os
import struct
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.device_client import WebRTCSession, _make_talk_audio_track


class _MockDC:
    def __init__(self):
        self.sent = []

    def send(self, data):
        self.sent.append(data)


class _MockSender:
    def __init__(self):
        self.tracks = []

    def replaceTrack(self, track):
        self.tracks.append(track)


def _make_session(track="TALK_TRACK", sender=None):
    sender = sender if sender is not None else _MockSender()
    dc = _MockDC()
    s = WebRTCSession(
        pc=None, outgoing_q=None, mqtt_fut=None, recorder=None, track_tasks=[],
        dc=dc, audio_sender=sender, talk_track=track, talk_holder={"provider": None},
    )
    return s, dc, sender


def _cmd_of(frame: bytes) -> int:
    # 28-byte LE header: seq(I) cmd(I) ts(q) dataLen(I) 0(I) + 4 pad
    return struct.unpack("<IIqII4x", frame[:28])[1]


def test_start_talk_enables_sender_and_sends_speakerstart():
    s, dc, sender = _make_session()
    prov = lambda: b"\x00" * 320
    assert asyncio.run(s.async_start_talk(prov)) is True
    assert s._talk_holder["provider"] is prov            # provider wired
    assert sender.tracks == [s._talk_track]              # replaceTrack(talk_track) = enable
    assert len(dc.sent) == 1
    assert _cmd_of(dc.sent[0]) == 848                    # SPEAKERSTART
    assert dc.sent[0][28:] == b"\x00" * 8                # 8-byte channel=0 payload


def test_stop_talk_sends_speakerstop_and_detaches():
    s, dc, sender = _make_session()
    asyncio.run(s.async_start_talk(lambda: None))
    dc.sent.clear(); sender.tracks.clear()
    assert asyncio.run(s.async_stop_talk()) is True
    assert sender.tracks == [None]                       # replaceTrack(None) = disable
    assert s._talk_holder["provider"] is None
    assert _cmd_of(dc.sent[0]) == 849                    # SPEAKERSTOP


def test_talk_unsupported_without_sender_or_track():
    s = WebRTCSession(pc=None, outgoing_q=None, mqtt_fut=None, recorder=None,
                      track_tasks=[], dc=_MockDC(), audio_sender=None, talk_track=None)
    assert s.talk_supported is False
    assert asyncio.run(s.async_start_talk(lambda: None)) is False


def test_talk_supported_flag():
    s, _, _ = _make_session()
    assert s.talk_supported is True


def test_talk_track_frame_format():
    holder = {"provider": None}
    track = _make_talk_audio_track(
        lambda: holder["provider"]() if holder["provider"] else None
    )
    if track is None:
        print("(av/aiortc track unavailable - skipping frame test)")
        return
    # Silence when provider is None.
    f = asyncio.run(track.recv())
    assert f.samples == 160 and f.sample_rate == 8000   # 20 ms @ 8 kHz
    # Provider PCM is consumed (320 bytes s16le = one 160-sample frame).
    holder["provider"] = lambda: b"\x34\x12" * 160
    f2 = asyncio.run(track.recv())
    assert f2.samples == 160
    assert bytes(f2.planes[0])[:4] == b"\x34\x12\x34\x12"


if __name__ == "__main__":
    fns = [v for k, v in sorted(globals().items()) if k.startswith("test_") and callable(v)]
    for fn in fns:
        fn()
        print(f"PASS {fn.__name__}")
    print(f"\nALL {len(fns)} TESTS PASSED")
