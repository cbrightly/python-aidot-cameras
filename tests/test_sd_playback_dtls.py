"""SD playback over DTLS (A000088): the recording's H265 is taken raw.

aiortc has no H265. The camera sends a recording it holds as H265 on a second
video section only a class 0/1 viewer whose offer names two codecs gets (the
iOS app's capture against an A000088, 2026-05-02: audio, H264 PT 127, H265
PT 98, data channel). The offer names H265 beside H264, aiortc keeps its own
three sections, and the H265 packets it would discard are taken at its RTP
router, assembled and recorded as HEVC.

These tests carry the path from encoded HEVC through RTP and back into a
file; it was played live on a carded A000088 on 2026-10-27.
"""

import ast
import asyncio
import io
import queue
import struct
import threading
import time
from fractions import Fraction

import pytest

from aidot_cameras.camera.sd_playback import (
    H265AccessUnits,
    answer_h265_parameter_sets,
    answer_h265_pt,
    offer_with_h265,
    session_mode_is,
)
from tests.ast_guards import function, module_tree

av = pytest.importorskip("av")

OFFER = "\r\n".join(
    [
        "v=0",
        "m=audio 9 UDP/TLS/RTP/SAVPF 8 0",
        "a=mid:0",
        "a=rtpmap:8 PCMA/8000",
        "m=video 9 UDP/TLS/RTP/SAVPF 97 98 99 100",
        "a=mid:1",
        "a=rtpmap:97 H264/90000",
        "a=fmtp:97 packetization-mode=1",
        "a=rtpmap:98 rtx/90000",
        "a=fmtp:98 apt=97",
        "a=sendrecv",
        "m=application 9 UDP/DTLS/SCTP webrtc-datachannel",
        "a=mid:2",
        "",
    ]
)


class TestOffer:
    def test_h265_is_named_in_the_video_section(self):
        sdp, pt = offer_with_h265(OFFER)
        assert pt == 96
        lines = sdp.split("\r\n")
        mline = next(ln for ln in lines if ln.startswith("m=video"))
        assert mline.split()[3:] == ["97", "98", "99", "100", "96"]
        i = lines.index("a=rtpmap:96 H265/90000")
        # Inside the video section, after its own rtpmap/fmtp lines.
        assert lines[i - 1] == "a=fmtp:98 apt=97"
        assert lines.index("m=application 9 UDP/DTLS/SCTP webrtc-datachannel") > i

    def test_the_pt_is_free_across_the_bundle(self):
        # aiortc's audio section offers opus as 96.
        sdp, pt = offer_with_h265(OFFER.replace("SAVPF 8 0", "SAVPF 96 8 0"))
        assert pt == 101

    def test_no_video_section_is_left_alone(self):
        sdp = "v=0\r\nm=audio 9 RTP/AVP 8\r\n"
        assert offer_with_h265(sdp) == (sdp, None)


def test_the_answer_s_h265_payload_type_is_found():
    answer = "\r\n".join(
        [
            "m=audio 9 UDP/TLS/RTP/SAVPF 8",
            "a=rtpmap:8 PCMA/8000",
            "m=video 9 UDP/TLS/RTP/SAVPF 127 124",
            "a=rtpmap:127 H264/90000",
            "m=video 9 UDP/TLS/RTP/SAVPF 98 99",
            "a=rtpmap:98 H265/90000",
            "a=rtpmap:99 rtx/90000",
        ]
    )
    assert answer_h265_pt(answer) == 98
    assert answer_h265_pt(answer.replace("H265", "H264")) is None


def test_the_sd_mode_echo_is_matched():
    yes = type("R", (), {"payload": struct.pack("<III", 0, 0, 2)})()
    no = type("R", (), {"payload": struct.pack("<III", 0, 0, 1)})()
    assert session_mode_is(2)(yes) and not session_mode_is(2)(no)
    assert not session_mode_is(2)(type("R", (), {"payload": b""})())


# -- RFC 7798 round trip -----------------------------------------------------


def _hevc_access_units(n=12):
    enc = av.CodecContext.create("libx265", "w")
    enc.width, enc.height, enc.pix_fmt = 320, 240, "yuv420p"
    enc.time_base = Fraction(1, 15)
    enc.options = {"x265-params": "keyint=5:repeat-headers=1:log-level=none"}
    out = []
    for i in range(n):
        frame = av.VideoFrame(320, 240, "yuv420p")
        for p in frame.planes:
            p.update(bytes([(i * 9) & 0xFF]) * p.buffer_size)
        frame.pts = i
        out += [bytes(p) for p in enc.encode(frame)]
    out += [bytes(p) for p in enc.encode(None)]
    return out


def _nals(annexb):
    parts = annexb.split(b"\x00\x00\x01")
    return [p.rstrip(b"\x00") for p in parts if p.strip(b"\x00")]


def _packetize(au, ts, seq, mtu=300):
    """One access unit as RTP payloads: small NALs aggregated, big ones
    fragmented - every packet type the camera can send."""
    nals = _nals(au)
    pkts, small = [], []
    for nal in nals:
        if len(nal) + 2 <= mtu:
            small.append(nal)
            continue
        if small:
            pkts.append(_ap(small))
            small = []
        hdr, body = nal[:2], nal[2:]
        t = (hdr[0] >> 1) & 0x3F
        fu_ind = bytes([(hdr[0] & 0x81) | (49 << 1), hdr[1]])
        chunks = [body[i : i + mtu] for i in range(0, len(body), mtu)]
        for i, c in enumerate(chunks):
            fu = t | (0x80 if i == 0 else 0) | (0x40 if i == len(chunks) - 1 else 0)
            pkts.append(fu_ind + bytes([fu]) + c)
    if small:
        pkts.append(_ap(small) if len(small) > 1 else small[0])
    return [(seq + i, ts, i == len(pkts) - 1, p) for i, p in enumerate(pkts)]


def _ap(nals):
    return bytes([48 << 1, 1]) + b"".join(struct.pack("!H", len(n)) + n for n in nals)


def test_access_units_survive_rtp_and_back():
    aus = _hevc_access_units()
    units = H265AccessUnits()
    got, seq = [], 1000
    for i, au in enumerate(aus):
        pkts = _packetize(au, 90000 + i * 6000, seq)
        seq += len(pkts)
        for p in pkts:
            got += units.feed(*p)
    assert [_nals(d) for d, _, _ in got] == [_nals(a) for a in aus]
    assert got[0][2] is True  # an IRAP first
    assert [ts for _, ts, _ in got] == [90000 + i * 6000 for i in range(len(aus))]


def test_a_unit_with_a_lost_packet_is_dropped_not_damaged():
    aus = _hevc_access_units(4)
    units = H265AccessUnits()
    got, seq = [], 7
    for i, au in enumerate(aus):
        pkts = _packetize(au, i * 6000, seq, mtu=200)
        seq += len(pkts)
        for j, p in enumerate(pkts):
            if i == 1 and j == 1 and len(pkts) > 2:
                continue  # lost
            got += units.feed(*p)
    assert units.dropped == 1
    assert len(got) == len(aus) - 1


def test_the_recorder_muxes_hevc_named_by_its_first_keyframe():
    from aidot_cameras.camera.protocol import _dtls_av_mux_run

    aus = _hevc_access_units()
    vq, aq, buf = queue.Queue(), queue.Queue(), io.BytesIO()
    stop = threading.Event()
    buf.close = lambda: None
    for i, au in enumerate(aus):
        kf = any(16 <= (n[0] >> 1) & 0x3F <= 21 for n in _nals(au))
        vq.put((au, i * 6000, kf, "hevc"))
        # A stray H264 item must not reach the HEVC stream.
        vq.put((b"\x00\x00\x00\x01\x65junk", i * 6000, True))
    t = threading.Thread(
        target=_dtls_av_mux_run,
        args=(vq, aq, buf, [0.0], stop),
        kwargs={"align_audio_start": True, "video_codec": None},
    )
    t.start()
    time.sleep(1.0)
    stop.set()
    t.join(10)
    data = buf.getvalue()
    assert data
    c = av.open(io.BytesIO(data), format="mpegts")
    (vs,) = c.streams.video
    assert vs.codec_context.name == "hevc"
    frames = sum(1 for _ in c.decode(vs))
    assert frames == len(aus)


def test_eager_mode_is_unchanged_and_lazy_needs_alignment():
    from aidot_cameras.camera.protocol import _dtls_av_mux_run

    with pytest.raises(ValueError):
        _dtls_av_mux_run(
            queue.Queue(),
            queue.Queue(),
            io.BytesIO(),
            [0.0],
            threading.Event(),
            video_codec=None,
        )


# -- the tap -----------------------------------------------------------------


class _Router:
    def __init__(self):
        self.payload_type_table = {127: {"h264"}, 8: {"audio"}}
        self.raw_tap = None


class _Pc:
    def __init__(self, router):
        rx = type("Rx", (), {"transport": type("T", (), {"_rtp_router": router})()})()
        self._t = [type("Tr", (), {"receiver": rx})() for _ in range(3)]

    def getTransceivers(self):
        return self._t


def _pkt(pt, seq, ts, marker, payload):
    return type(
        "P",
        (),
        {
            "payload_type": pt,
            "sequence_number": seq,
            "timestamp": ts,
            "marker": marker,
            "payload": payload,
        },
    )()


def test_the_tap_records_the_answer_s_h265_and_nothing_else():
    from aidot_cameras.camera.webrtc_open import _install_sd_h265_tap

    rec = type("Rec", (), {"vq": queue.Queue()})()
    state = {"pt": 98, "units": H265AccessUnits(), "recorder": rec, "dropped": 0}
    router = _Router()
    assert _install_sd_h265_tap(_Pc(router), state, lambda m: None) == 1
    idr = bytes([19 << 1, 1]) + b"x" * 10
    router.raw_tap(_pkt(127, 1, 0, True, b"\x65h264"))
    router.raw_tap(_pkt(8, 1, 0, True, b"audio"))
    router.raw_tap(_pkt(98, 1, 3000, True, idr))
    item = rec.vq.get_nowait()
    assert item == (b"\x00\x00\x00\x01" + idr, 3000, True, "hevc")
    assert rec.vq.empty()


def test_with_no_answer_the_tap_learns_the_unrouted_video_pt():
    from aidot_cameras.camera.webrtc_open import _install_sd_h265_tap

    rec = type("Rec", (), {"vq": queue.Queue()})()
    lines = []
    state = {"pt": None, "units": H265AccessUnits(), "recorder": rec, "dropped": 0}
    router = _Router()
    _install_sd_h265_tap(_Pc(router), state, lines.append)
    vps = bytes([32 << 1, 1, 0x0C])
    router.raw_tap(_pkt(8, 1, 0, True, vps))  # routed (audio)
    router.raw_tap(_pkt(127, 1, 0, True, vps))  # routed to aiortc
    router.raw_tap(_pkt(0, 1, 0, True, b"\xd5\xd5\xd5"))  # not an H265 NAL
    router.raw_tap(_pkt(0, 2, 0, True, bytes([1 << 1, 1, 0])))  # a mid-GOP slice
    # An A000088 answered its extra video section on PT 0 (2026-10-27).
    router.raw_tap(_pkt(0, 3, 0, True, vps))
    assert state["pt"] == 0
    assert lines == ["SD playback: taking pt=0 as the recording's H265"]


def test_a_live_open_taps_nothing():
    from aidot_cameras.camera.webrtc_open import _install_sd_h265_tap

    router = _Router()
    assert _install_sd_h265_tap(_Pc(router), None, print) == 0
    assert router.raw_tap is None


def test_the_vendored_router_shows_the_tap_every_packet_before_routing():
    from aidot_cameras._vendor.aiortc.rtcdtlstransport import RtpRouter

    assert RtpRouter().raw_tap is None
    fn = function(
        module_tree("aidot_cameras._vendor.aiortc.rtcdtlstransport"), "_handle_rtp_data"
    )
    src = ast.unparse(fn)
    assert src.index("raw_tap") < src.index("route_rtp(packet)")


def test_the_dtls_session_gets_the_controls():
    from aidot_cameras.camera.sd_playback import _PlaybackControls
    from aidot_cameras.camera.webrtc import WebRTCSession

    assert issubclass(WebRTCSession, _PlaybackControls)
    s = WebRTCSession(
        pc=None, outgoing_q=None, mqtt_fut=None, recorder=None, track_tasks=[]
    )
    assert asyncio.run(s.async_sd_pause()) is None  # live: sends nothing


def test_a_recording_held_as_h264_is_muxed_as_h264():
    # The recording's codec is the camera's encoder setting (2 H264, 4 H265);
    # an H264 one comes through aiortc's own track as three-element items.
    from aidot_cameras.camera.protocol import _dtls_av_mux_run

    enc = av.CodecContext.create("libx264", "w")
    enc.width, enc.height, enc.pix_fmt = 320, 240, "yuv420p"
    enc.time_base = Fraction(1, 15)
    enc.options = {"bf": "0", "g": "5"}
    aus = []
    for i in range(10):
        f = av.VideoFrame(320, 240, "yuv420p")
        f.pts = i
        aus += [(bytes(p), p.is_keyframe) for p in enc.encode(f)]
    aus += [(bytes(p), p.is_keyframe) for p in enc.encode(None)]
    vq, aq, buf, stop = queue.Queue(), queue.Queue(), io.BytesIO(), threading.Event()
    buf.close = lambda: None
    for i, (au, kf) in enumerate(aus):
        vq.put((au, i * 6000, kf))
    t = threading.Thread(
        target=_dtls_av_mux_run,
        args=(vq, aq, buf, [0.0], stop),
        kwargs={"align_audio_start": True, "video_codec": None},
    )
    t.start()
    time.sleep(1.0)
    stop.set()
    t.join(10)
    c = av.open(io.BytesIO(buf.getvalue()), format="mpegts")
    (vs,) = c.streams.video
    assert vs.codec_context.name == "h264"
    assert sum(1 for p in c.demux(vs) if p.size) == len(aus)


def test_a_lost_last_packet_drops_its_own_unit_not_the_next():
    aus = _hevc_access_units(5)
    units = H265AccessUnits()
    got, seq = [], 50
    for i, au in enumerate(aus):
        pkts = _packetize(au, i * 6000, seq, mtu=200)
        seq += len(pkts)
        for j, p in enumerate(pkts):
            if i == 1 and j == len(pkts) - 1 and len(pkts) > 1:
                continue  # unit 1 loses its marker packet
            got += units.feed(*p)
    assert units.dropped == 1
    assert [ts for _, ts, _ in got] == [0, 12000, 18000, 24000]
    assert [_nals(d) for d, _, _ in got] == [_nals(aus[i]) for i in (0, 2, 3, 4)]


def test_out_of_band_parameter_sets_lead_a_keyframe_without_them():
    import base64

    vps, sps, pps = bytes([64, 1, 9]), bytes([66, 1, 8]), bytes([68, 1, 7])
    answer = (
        "a=rtpmap:0 H265/90000\r\n"
        f"a=fmtp:0 sprop-vps={base64.b64encode(vps).decode()};"
        f"sprop-sps={base64.b64encode(sps).decode()};"
        f"sprop-pps={base64.b64encode(pps).decode()}\r\n"
    )
    sets = answer_h265_parameter_sets(answer, 0)
    assert sets == [vps, sps, pps]
    units = H265AccessUnits(sets)
    idr = bytes([19 << 1, 1, 0xAA])
    ((data, _, kf),) = units.feed(1, 0, True, idr)
    assert kf and _nals(data) == [vps, sps, pps, idr]
    trail = bytes([1 << 1, 1, 0xBB])
    ((data, _, kf),) = units.feed(2, 6000, True, trail)
    assert not kf and _nals(data) == [trail]


def test_learning_does_not_take_an_h264_fu_a():
    from aidot_cameras.camera.sd_playback import starts_h265_stream

    # H264 FU-A, NRI 2, start of an IDR: passes a bare 2-byte H265 check.
    assert not starts_h265_stream(bytes([0x5C, 0x85, 0x88, 0x84]))
    assert starts_h265_stream(bytes([32 << 1, 1, 0x0C]))  # VPS
    ap = bytes([48 << 1, 1]) + struct.pack("!H", 3) + bytes([32 << 1, 1, 0x0C])
    assert starts_h265_stream(ap)
    assert starts_h265_stream(bytes([49 << 1, 1, 0x80 | 19, 0]))  # FU start, IDR
    assert not starts_h265_stream(bytes([49 << 1, 1, 19, 0]))  # FU middle


def test_the_sd_mode_echo_has_one_parser():
    from aidot_cameras.camera.protocol import avio_header
    from aidot_cameras.camera.sd_playback import session_mode_of
    from aidot_cameras.camera.sdes_open import _session_mode_echoed

    body = struct.pack("<III", 0, 0, 2)
    assert (
        session_mode_of(body)
        == 2
        == _session_mode_echoed(avio_header(1, 5377, 0, 12, 0) + body)
    )
    assert session_mode_of(b"\x00" * 8) is None


def test_a_lost_head_drops_the_new_unit_not_the_one_already_emitted():
    aus = _hevc_access_units(5)
    units = H265AccessUnits()
    got, seq = [], 300
    for i, au in enumerate(aus):
        pkts = _packetize(au, i * 6000, seq, mtu=200)
        seq += len(pkts)
        for j, p in enumerate(pkts):
            if i == 1 and j == 0:
                continue  # unit 1 loses its first packet, after 0's marker
            got += units.feed(*p)
    assert units.dropped == 1
    assert [ts for _, ts, _ in got] == [0, 12000, 18000, 24000]


def test_a_gap_wider_than_the_lost_marker_drops_both_units():
    aus = _hevc_access_units(5)
    units = H265AccessUnits()
    got, seq = [], 900
    for i, au in enumerate(aus):
        pkts = _packetize(au, i * 6000, seq, mtu=200)
        seq += len(pkts)
        for j, p in enumerate(pkts):
            if (i == 0 and j == len(pkts) - 1) or (i == 1 and j == 0):
                continue  # 0's marker and 1's head, back to back
            got += units.feed(*p)
    assert units.dropped == 2
    assert [ts for _, ts, _ in got] == [12000, 18000, 24000]


def test_a_truncated_aggregation_packet_breaks_its_unit():
    units = H265AccessUnits()
    vps = bytes([32 << 1, 1, 0x0C])
    ap = bytes([48 << 1, 1]) + struct.pack("!H", 3) + vps + struct.pack("!H", 1200)
    ap += bytes([19 << 1, 1]) + b"\x00" * 298
    assert units.feed(1, 0, True, ap) == []
    assert units.dropped == 1
