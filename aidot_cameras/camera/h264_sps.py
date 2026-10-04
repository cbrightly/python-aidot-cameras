"""State "no frame reordering" in a camera's H.264 SPS, so readers stop guessing DTS.

The A000088 sends High profile H.264 with no B-frames (171 P and 6 I slices
in two captures, none B), but its SPS states ``max_num_reorder_frames = 1``.
libav (Home Assistant's stream worker, and go2rtc's consumers through it)
believes it, delays decode by a frame and derives each packet's DTS from the
presentation times around it. The camera's frame times jitter, so the derived
DTS jitter too: Home Assistant's HLS segments moved picture against sound by up
to 170 ms per segment, and a recording failed outright on a DTS that went
backwards (measured 2026-10-03, on the go2rtc path and the library's TS alike).
An SPS that states nothing (the SDES models') is read as no reordering and is
not affected.

``fix_sps`` rewrites a stated non-zero ``max_num_reorder_frames`` to 0. That is
true of every stream this library publishes or records, whatever the camera
claims: each one drops any frame whose presentation time was already served,
so decode order is presentation order by construction. Every other bit is
copied. An SPS that states nothing, already states 0, or that this parser
cannot read to its end, is returned unchanged.

Off with ``AIDOT_PUBLISH_SPS_FIX=0``.
"""

from __future__ import annotations

import functools
import os
from typing import List, Optional

ENV_SPS_FIX = "AIDOT_PUBLISH_SPS_FIX"

_HIGH_PROFILES = {100, 110, 122, 244, 44, 83, 86, 118, 128, 138, 139, 134, 135}


def enabled() -> bool:
    return os.environ.get(ENV_SPS_FIX, "1").strip().lower() not in (
        "0",
        "false",
        "no",
        "off",
    )


# -- RBSP <-> EBSP ----------------------------------------------------------- #


def _unescape(ebsp: bytes) -> bytes:
    out = bytearray()
    zeros = 0
    for b in ebsp:
        if zeros >= 2 and b == 3:
            zeros = 0
            continue
        out.append(b)
        zeros = zeros + 1 if b == 0 else 0
    return bytes(out)


def _escape(rbsp: bytes) -> bytes:
    out = bytearray()
    zeros = 0
    for b in rbsp:
        if zeros >= 2 and b <= 3:
            out.append(3)
            zeros = 0
        out.append(b)
        zeros = zeros + 1 if b == 0 else 0
    return bytes(out)


# -- bits -------------------------------------------------------------------- #


class _Reader:
    def __init__(self, data: bytes) -> None:
        self.data = data
        self.pos = 0

    def u(self, n: int) -> int:
        v = 0
        for _ in range(n):
            i = self.pos >> 3
            if i >= len(self.data):
                raise ValueError("SPS ended early")
            v = (v << 1) | ((self.data[i] >> (7 - (self.pos & 7))) & 1)
            self.pos += 1
        return v

    def ue(self) -> int:
        zeros = 0
        while self.u(1) == 0:
            zeros += 1
            if zeros > 31:
                raise ValueError("bad exp-Golomb code")
        return (1 << zeros) - 1 + self.u(zeros)

    def se(self) -> int:
        k = self.ue()
        return (k + 1) // 2 if k & 1 else -(k // 2)


class _Writer:
    def __init__(self) -> None:
        self.bits: List[int] = []

    def u(self, n: int, v: int) -> None:
        for i in range(n - 1, -1, -1):
            self.bits.append((v >> i) & 1)

    def ue(self, v: int) -> None:
        v += 1
        n = v.bit_length()
        self.u(n - 1, 0)
        self.u(n, v)

    def copy(self, data: bytes, start: int, end: int) -> None:
        for p in range(start, end):
            self.bits.append((data[p >> 3] >> (7 - (p & 7))) & 1)

    def rbsp(self) -> bytes:
        bits = [*self.bits, 1]  # rbsp_stop_one_bit
        bits += [0] * (-len(bits) % 8)
        return bytes(
            int("".join(map(str, bits[i : i + 8])), 2) for i in range(0, len(bits), 8)
        )


# -- parse ------------------------------------------------------------------- #


def _scaling_list(r: _Reader, size: int) -> None:
    last = nxt = 8
    for _ in range(size):
        if nxt != 0:
            nxt = (last + r.se() + 256) % 256
        last = nxt if nxt != 0 else last


def _hrd(r: _Reader) -> None:
    cnt = r.ue() + 1
    r.u(4)
    r.u(4)
    for _ in range(cnt):
        r.ue()
        r.ue()
        r.u(1)
    r.u(5)
    r.u(5)
    r.u(5)
    r.u(5)


def parse_sps(nal: bytes) -> dict:
    """The fields this module needs, and where the VUI pieces sit (bit offsets
    into the unescaped payload after the NAL header byte)."""
    if not nal or (nal[0] & 0x1F) != 7:
        raise ValueError("not an SPS NAL unit")
    data = _unescape(nal[1:])
    r = _Reader(data)
    s: dict = {"profile_idc": r.u(8), "constraints": r.u(8), "level_idc": r.u(8)}
    s["sps_id"] = r.ue()
    chroma = 1
    if s["profile_idc"] in _HIGH_PROFILES:
        chroma = r.ue()
        if chroma == 3:
            r.u(1)
        r.ue()
        r.ue()
        r.u(1)
        if r.u(1):
            for i in range(8 if chroma != 3 else 12):
                if r.u(1):
                    _scaling_list(r, 16 if i < 6 else 64)
    s["chroma_format_idc"] = chroma
    r.ue()  # log2_max_frame_num_minus4
    poc = r.ue()
    s["pic_order_cnt_type"] = poc
    if poc == 0:
        r.ue()
    elif poc == 1:
        r.u(1)
        r.se()
        r.se()
        for _ in range(r.ue()):
            r.se()
    s["max_num_ref_frames"] = r.ue()
    r.u(1)
    s["width_mbs"] = r.ue() + 1
    s["height_map_units"] = r.ue() + 1
    if not r.u(1):  # frame_mbs_only_flag
        r.u(1)
    r.u(1)
    if r.u(1):  # frame_cropping_flag
        for _ in range(4):
            r.ue()
    s["vui_flag_pos"] = r.pos
    s["vui"] = None
    if r.u(1):
        vui: dict = {}
        if r.u(1):  # aspect_ratio_info_present_flag
            if r.u(8) == 255:
                r.u(16)
                r.u(16)
        if r.u(1):  # overscan_info_present_flag
            r.u(1)
        if r.u(1):  # video_signal_type_present_flag
            r.u(3)
            r.u(1)
            if r.u(1):
                r.u(8)
                r.u(8)
                r.u(8)
        if r.u(1):  # chroma_loc_info_present_flag
            r.ue()
            r.ue()
        if r.u(1):  # timing_info_present_flag
            vui["num_units_in_tick"] = r.u(32)
            vui["time_scale"] = r.u(32)
            r.u(1)
        nal_hrd = r.u(1)
        if nal_hrd:
            _hrd(r)
        vcl_hrd = r.u(1)
        if vcl_hrd:
            _hrd(r)
        if nal_hrd or vcl_hrd:
            r.u(1)  # low_delay_hrd_flag
        r.u(1)  # pic_struct_present_flag
        vui["restriction_flag_pos"] = r.pos
        if r.u(1):
            vui["mv_over_boundaries"] = r.u(1)
            for name in (
                "max_bytes_per_pic_denom",
                "max_bits_per_mb_denom",
                "log2_max_mv_length_horizontal",
                "log2_max_mv_length_vertical",
                "max_num_reorder_frames",
                "max_dec_frame_buffering",
            ):
                vui[name] = r.ue()
        s["vui"] = vui
    # What follows must be exactly the RBSP trailing bits.
    s["end_pos"] = r.pos
    rest = [_bit(data, p) for p in range(r.pos, len(data) * 8)]
    while rest and rest[-1] == 0:
        rest.pop()
    s["trailing_ok"] = rest == [1]
    return s


def _bit(data: bytes, p: int) -> int:
    return (data[p >> 3] >> (7 - (p & 7))) & 1


# -- rewrite ----------------------------------------------------------------- #


def _restriction(w: _Writer, vui: dict) -> None:
    w.u(1, 1)  # bitstream_restriction_flag
    w.u(1, vui["mv_over_boundaries"])
    w.ue(vui["max_bytes_per_pic_denom"])
    w.ue(vui["max_bits_per_mb_denom"])
    w.ue(vui["log2_max_mv_length_horizontal"])
    w.ue(vui["log2_max_mv_length_vertical"])
    w.ue(0)  # max_num_reorder_frames: the point of all this
    w.ue(vui["max_dec_frame_buffering"])


@functools.lru_cache(maxsize=64)
def fix_sps(nal: bytes) -> bytes:
    """The SPS with ``max_num_reorder_frames = 0`` stated; unchanged if it
    already states a reorder depth or cannot be read with certainty."""
    try:
        s = parse_sps(nal)
    except (ValueError, IndexError):
        return nal
    if not s["trailing_ok"]:
        return nal
    vui = s["vui"] or {}
    if not vui.get("max_num_reorder_frames"):
        return nal  # states nothing (read as none) or already none
    data = _unescape(nal[1:])
    w = _Writer()
    w.copy(data, 0, vui["restriction_flag_pos"])
    _restriction(w, vui)
    out = nal[:1] + _escape(w.rbsp())
    # Never hand on something we cannot read back to the same picture format.
    try:
        t = parse_sps(out)
    except (ValueError, IndexError):
        return nal
    same = all(
        t[k] == s[k]
        for k in (
            "profile_idc",
            "constraints",
            "level_idc",
            "sps_id",
            "max_num_ref_frames",
            "width_mbs",
            "height_map_units",
            "pic_order_cnt_type",
        )
    )
    if (
        not same
        or not t["trailing_ok"]
        or (t["vui"] or {}).get("max_num_reorder_frames") != 0
    ):
        return nal
    return out


def fix_access_unit(au: bytes) -> bytes:
    """An Annex B access unit with every SPS fixed (the same object if none)."""
    if b"\0\0\1" not in au:
        return au
    i = 0
    changed = False
    out = bytearray()
    # Walk start codes keeping the original framing byte for byte.
    starts = []
    n = len(au)
    while True:
        j = au.find(b"\0\0\1", i)
        if j < 0:
            break
        k = j - 1 if j > 0 and au[j - 1] == 0 else j
        starts.append((k, j + 3))
        i = j + 3
    if not starts:
        return au
    out += au[: starts[0][0]]
    for idx, (sc, body) in enumerate(starts):
        end = starts[idx + 1][0] if idx + 1 < len(starts) else n
        nal = au[body:end]
        if nal and (nal[0] & 0x1F) == 7:
            fixed = fix_sps(bytes(nal))
            if fixed != nal:
                changed = True
                nal = fixed
        out += au[sc:body]
        out += nal
    return bytes(out) if changed else au


def sps_states_no_reorder(nal: bytes) -> Optional[bool]:
    """Whether an SPS states ``max_num_reorder_frames == 0`` (None: unreadable)."""
    try:
        vui = parse_sps(nal)["vui"]
    except (ValueError, IndexError):
        return None
    return bool(vui) and vui.get("max_num_reorder_frames") == 0
