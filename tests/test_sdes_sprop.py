"""Unit tests for the SDES out-of-band SPS/PPS (sprop-parameter-sets) helpers.

A lost in-band SPS wedges ffmpeg ("could not find codec parameters"); injecting
the camera's SPS/PPS into the ffmpeg SDP as sprop-parameter-sets initializes the
decoder out-of-band so the loss no longer matters. These pure helpers capture
the param sets from RTP, format the sprop value, and cache it per camera.
"""
import os
import sys
import tempfile

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

# _SPROP_DIR + sprop helpers live in aidot_cameras.camera.protocol after the split;
# patch the dir there (the module where the helpers read it).
import aidot_cameras.camera.protocol as _proto
from aidot_cameras.device_client import (
    _extract_param_sets_from_rtp,
    _build_sprop,
    _load_sprop,
    _save_sprop,
)

# Real Driveway (A001513) parameter sets captured live 2026-06-08.
SPS = bytes.fromhex("27640033ad00ce8050079a6a020203e0000003002000000303c6f207d00bbffff814")
PPS = bytes.fromhex("28ee3cb0")
SPROP = "J2QAM60AzoBQB5pqAgID4AAAAwAgAAADA8byB9ALv//4FA==,KO48sA=="

RTP = b"\x80\x60\x00\x01\x00\x00\x00\x00\x12\x34\x56\x78"  # V2, no CC/ext, 12B


# ---- _build_sprop ----------------------------------------------------------

def test_build_sprop_matches_live_vector():
    assert _build_sprop(SPS, PPS) == SPROP


# ---- _extract_param_sets_from_rtp -----------------------------------------

def test_single_nal_sps():
    assert _extract_param_sets_from_rtp(RTP + SPS) == {7: SPS}


def test_single_nal_pps():
    assert _extract_param_sets_from_rtp(RTP + PPS) == {8: PPS}


def test_stap_a_carries_both():
    stap = bytes([0x78])  # 0x78 & 0x1f == 24 (STAP-A)
    stap += len(SPS).to_bytes(2, "big") + SPS
    stap += len(PPS).to_bytes(2, "big") + PPS
    assert _extract_param_sets_from_rtp(RTP + stap) == {7: SPS, 8: PPS}


def test_slice_packet_yields_nothing():
    # NAL type 1 (non-IDR slice) - not a parameter set
    assert _extract_param_sets_from_rtp(RTP + bytes([0x61, 0x00, 0x11])) == {}


def test_short_or_empty_packet_safe():
    assert _extract_param_sets_from_rtp(b"") == {}
    assert _extract_param_sets_from_rtp(b"\x80\x60\x00") == {}
    assert _extract_param_sets_from_rtp(RTP) == {}  # header only, no payload


def test_csrc_header_offset_handled():
    # CC=2 -> 12 + 8 bytes of CSRC before the payload
    hdr = b"\x82\x60\x00\x01\x00\x00\x00\x00\x12\x34\x56\x78" + b"\xaa\xaa\xaa\xaa\xbb\xbb\xbb\xbb"
    assert _extract_param_sets_from_rtp(hdr + SPS) == {7: SPS}


# ---- cache roundtrip -------------------------------------------------------

def test_cache_roundtrip(monkeypatch):
    d = tempfile.mkdtemp()
    monkeypatch.setattr(_proto, "_SPROP_DIR", d)
    devid = "abc123"
    assert _load_sprop(devid) is None          # nothing cached yet
    _save_sprop(devid, SPROP)
    assert _load_sprop(devid) == SPROP          # persists + reads back


def test_load_missing_is_none(monkeypatch):
    monkeypatch.setattr(_proto, "_SPROP_DIR", "/nonexistent/path/xyz")
    assert _load_sprop("whatever") is None      # fail-safe, no raise


if __name__ == "__main__":
    import traceback

    class _MP:  # tiny monkeypatch shim so this runs without pytest
        def __init__(self): self._undo = []
        def setattr(self, obj, name, val):
            self._undo.append((obj, name, getattr(obj, name)))
            setattr(obj, name, val)
        def undo(self):
            for obj, name, val in reversed(self._undo):
                setattr(obj, name, val)

    _fail = 0
    for _k, _v in sorted(globals().items()):
        if _k.startswith("test_"):
            mp = _MP()
            try:
                _v(mp) if "monkeypatch" in _v.__code__.co_varnames else _v()
                print(f"PASS {_k}")
            except Exception:
                _fail += 1
                print(f"FAIL {_k}")
                traceback.print_exc()
            finally:
                mp.undo()
    raise SystemExit(1 if _fail else 0)
