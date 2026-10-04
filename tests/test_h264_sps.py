"""The published SPS states no frame reordering, so readers stop deriving DTS.

The A000088 never reorders frames, but its SPS states max_num_reorder_frames
= 1. libav believes it and derives DTS from the jittery frame times: Home
Assistant's HLS segments moved picture against sound by up to 170 ms, and a
recording failed on a DTS that went backwards (2026-10-03). The SDES models'
SPS state nothing, which libav reads as no reordering: left alone. The SPS
fixtures below are the cameras' own.
"""

import io

import pytest

from aidot_cameras.camera import h264_sps as h

A000088 = bytes.fromhex("27640033ac131aa05005ba10000003001000000301e0f1625280")
A001513 = bytes.fromhex(
    "27640033ad00ce8050079a6a020203e0000003002000000303c6f207d00bbffff814"
)
A001064 = bytes.fromhex("674d001fe900a00b742000007d20000daf8080")
_SAME = (
    "profile_idc",
    "constraints",
    "level_idc",
    "sps_id",
    "chroma_format_idc",
    "max_num_ref_frames",
    "width_mbs",
    "height_map_units",
    "pic_order_cnt_type",
)


def test_the_a000088_sps_comes_out_stating_no_reorder_and_otherwise_the_same():
    assert h.sps_states_no_reorder(A000088) is False
    fixed = h.fix_sps(A000088)
    assert h.sps_states_no_reorder(fixed) is True
    before, after = h.parse_sps(A000088), h.parse_sps(fixed)
    assert {k: before[k] for k in _SAME} == {k: after[k] for k in _SAME}
    for k in ("num_units_in_tick", "time_scale"):  # the frame rate it declares
        assert before["vui"][k] == after["vui"][k]
    assert after["trailing_ok"]
    assert len(fixed) == len(A000088) - 1  # only the restriction's tail changed
    assert h.fix_sps(fixed) == fixed  # once is enough


@pytest.mark.parametrize("sps", [A001513, A001064], ids=["A001513", "A001064"])
def test_an_sps_that_states_nothing_is_left_alone(sps):
    assert h.parse_sps(sps)["trailing_ok"]  # read to its end...
    assert h.fix_sps(sps) == sps  # ...and not touched


def test_a_stated_restriction_keeps_its_other_values():
    v = h.parse_sps(h.fix_sps(A000088))["vui"]
    assert (v["log2_max_mv_length_horizontal"], v["log2_max_mv_length_vertical"]) == (
        10,
        8,
    )
    assert v["max_dec_frame_buffering"] == 1


def test_an_unreadable_sps_is_left_alone():
    assert h.fix_sps(A001513[:12]) == A001513[:12]
    assert h.fix_sps(b"\x68\xee\x3c\xb0") == b"\x68\xee\x3c\xb0"  # a PPS


def test_emulation_prevention_survives_the_rewrite():
    body = h.fix_sps(A000088)[1:]
    for bad in (b"\x00\x00\x00", b"\x00\x00\x01", b"\x00\x00\x02"):
        assert bad not in body


def test_an_access_unit_changes_only_its_sps():
    pps, idr = b"\x28\xee\x03\x11\x92\x19", b"\x25\x88\x84\x00\x33\xff"
    au = (
        b"\x00\x00\x00\x01"
        + A000088
        + b"\x00\x00\x00\x01"
        + pps
        + b"\x00\x00\x01"
        + idr
    )
    out = h.fix_access_unit(au)
    assert out == (
        b"\x00\x00\x00\x01"
        + h.fix_sps(A000088)
        + b"\x00\x00\x00\x01"
        + pps
        + b"\x00\x00\x01"
        + idr
    )
    p_frame = b"\x00\x00\x00\x01\x21\x9a\x00\x10"
    assert h.fix_access_unit(p_frame) is p_frame


def test_libav_stops_deriving_dts_once_the_sps_says_no_reorder():
    av = pytest.importorskip("av")
    np = pytest.importorskip("numpy")
    import fractions

    # A camera-like stream: P frames only, jittered frame times, and an SPS
    # that claims one frame of reordering (as the A000088's does).
    enc = av.CodecContext.create("libx264", "w")
    enc.width, enc.height, enc.pix_fmt = 320, 240, "yuv420p"
    enc.time_base = fractions.Fraction(1, 90000)
    enc.options = {"bframes": "0", "keyint": "15", "preset": "ultrafast"}
    aus, t = [], 0
    for i in range(45):
        fr = av.VideoFrame.from_ndarray(
            np.full((240, 320, 3), i * 5 % 255, np.uint8), format="rgb24"
        ).reformat(format="yuv420p")
        fr.pts = t
        t += (6000, 3200, 9100, 5800)[i % 4]  # the cameras' frame times jitter
        for p in enc.encode(fr):
            aus.append((bytes(p), p.pts, p.is_keyframe))
    for p in enc.encode(None):
        aus.append((bytes(p), p.pts, p.is_keyframe))

    def restriction(au, reorder):
        """Rewrite the SPS to state ``reorder`` frames, or nothing (None)."""
        out = bytearray()
        for part in au.split(b"\x00\x00\x00\x01"):
            if part and (part[0] & 0x1F) == 7:
                s = h.parse_sps(part)
                w = h._Writer()
                w.copy(h._unescape(part[1:]), 0, s["vui"]["restriction_flag_pos"])
                if reorder is None:
                    w.u(1, 0)
                else:
                    w.u(1, 1)
                    w.u(1, 1)
                    for k in (2, 1, 16, 16):
                        w.ue(k)
                    w.ue(reorder)
                    w.ue(1)
                part = part[:1] + h._escape(w.rbsp())
            if part:
                out += b"\x00\x00\x00\x01" + part
        return bytes(out)

    def dts_gaps(fix, reorder=1):
        buf = io.BytesIO()
        out = av.open(buf, "w", format="mpegts")
        vs = out.add_stream("h264")
        vs.time_base = fractions.Fraction(1, 90000)
        for data, pts, _kf in aus:
            data = restriction(data, reorder)
            if fix:
                data = h.fix_access_unit(data)
            pkt = av.Packet(data)
            pkt.stream, pkt.pts, pkt.dts = vs, pts + 90000, pts + 90000  # PTS only
            pkt.time_base = vs.time_base
            out.mux(pkt)
        out.close()
        c = av.open(io.BytesIO(buf.getvalue()), format="mpegts")
        diffs = [
            None if p.dts is None else p.pts - p.dts
            for p in c.demux(video=0)
            if p.pts is not None
        ]
        c.close()
        return diffs

    assert any(d != 0 for d in dts_gaps(fix=False))  # the defect, reproduced
    assert all(d == 0 for d in dts_gaps(fix=True))
    # Stating nothing (the SDES models) is already read as no reordering.
    assert all(d == 0 for d in dts_gaps(fix=False, reorder=None))
