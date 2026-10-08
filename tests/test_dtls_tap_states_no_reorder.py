"""Every DTLS copy consumer gets an SPS that states no frame reordering.

The A000088's SPS states max_num_reorder_frames = 1 though it never reorders,
and libav then derives DTS from jittery frame times (see h264_sps). The tap is
the one place every copy consumer reads from - the go2rtc serve, the direct
publisher, the in-sync TS and file recordings - so the SPS is fixed there. The
decoder still gets the camera's own bytes.
"""

from aidot_cameras.camera import h264_sps
from aidot_cameras.camera.client import CameraMixin

_SPS = bytes.fromhex("27640033ac131aa05005ba10000003001000000301e0f1625280")
_PPS = bytes.fromhex("28ee03119219")
_IDR = b"\x25\x88\x84\x00\x33\xff"
_KEY = b"\x00\x00\x00\x01" + _SPS + b"\x00\x00\x00\x01" + _PPS + b"\x00\x00\x01" + _IDR
_DELTA = b"\x00\x00\x00\x01\x21\x9a\x00\x10"


class _Enc:
    def __init__(self, data, timestamp):
        self.data, self.timestamp = data, timestamp


class _Q:
    def __init__(self):
        self.puts = []

    def put(self, task, *a, **k):
        self.puts.append(task)


class _Rcv:
    def __init__(self, qd):
        self._RTCRtpReceiver__decoder_queue = qd


class _Out:
    def __init__(self):
        self.items = []

    def put_nowait(self, item):
        self.items.append(item)


def _tap(serve):
    qd, out = _Q(), _Out()
    assert CameraMixin._install_encoded_tap(_Rcv(qd), out, True, serve=serve)
    qd.put((0, _Enc(_KEY, 1000)))
    qd.put((0, _Enc(_DELTA, 4000)))
    return qd, out


def test_the_copy_consumers_get_the_fixed_sps():
    for serve in (True, False):
        _qd, out = _tap(serve)
        assert out.items[0] == (h264_sps.fix_access_unit(_KEY), 1000, True)
        assert h264_sps.fix_access_unit(_KEY) != _KEY
        assert out.items[1] == (_DELTA, 4000, False)


def test_the_decoder_still_gets_the_cameras_bytes():
    qd, _out = _tap(serve=False)
    assert qd.puts[0][1].data == _KEY
