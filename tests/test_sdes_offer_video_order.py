"""The video codec PREFERENCE ORDER in the camera-facing SDES offer.

RFC 3264 section 5.1 makes the ``m=video`` payload-type list a preference list,
most-preferred first.  The offer this library sends carries ``96 97``, so it
does state a preference -- for H264.  The frequently repeated shorthand that our
offer "expresses no preference" is not what the SDP says; what is true is that
nothing ever chose that order.  The line arrived verbatim when the SDES open
path was extracted from ``client.py`` and has never been varied.

Measured on an A001064, the camera answers H264 most of the time and H265
occasionally for an identical request.  Read against the offer that is a camera
which honours our stated first choice most of the time and disregards it some of
the time, which is a reason to expect LESS of reordering than of pinning.  It is
still the only untried lever that leaves both codecs on the wire, and both on
the wire is the only condition under which the efficient H265 profile has ever
appeared -- narrowing to H265 alone returns no video at all, 3 of 3 rounds.

These tests assert the payload-type ORDER in the m-line and rtpmap block that
the production offer builder actually emits, via the same helper it calls, plus
an AST check that the builder really calls it.  The offer is assembled inside a
several-hundred-line async method that cannot be invoked standalone, so a test
that only exercised a copy of the template would pass with the production change
deleted.
"""
import ast
import inspect
import re

import pytest

import aidot_cameras.camera.sdes_open as sdes_open
from aidot_cameras.camera.protocol import _compress_sdp_for_camera
from aidot_cameras.camera.sdes_open import (
    _resolve_sdes_video_pt,
    _resolve_sdes_video_pt_order,
    _SDES_OFFER_VIDEO_PT_ORDER,
    _sdes_offer_video_codec_lines,
    narrow_sdp_payload_types,
)

_ENV = "AIDOT_SDES_VIDEO_PT_ORDER"
_PIN_ENV = "AIDOT_SDES_VIDEO_PT"

#: The literal that stood in the offer before the order became a variable,
#: character for character.  The refactor replaced a contiguous block inside a
#: large string concatenation, so the risk is a dropped or doubled line
#: terminator at the seams, not the codec names.
_SHIPPED_ATTRS = (
    "a=rtpmap:96 H264/90000\r\n"
    "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
    "profile-level-id=42e01f\r\n"
    "a=rtpmap:97 H265/90000\r\n"
    "a=fmtp:97 level-id=93\r\n"
)


def _offer(pt_list: str, attrs: str) -> str:
    """A video m-section shaped exactly like the production offer's."""
    return (
        "v=0\r\n"
        "o=- 1 1 IN IP4 10.0.0.1\r\n"
        "s=-\r\n"
        "t=0 0\r\n"
        "m=audio 40000 RTP/SAVPF 0 8\r\n"
        "c=IN IP4 10.0.0.1\r\n"
        "a=recvonly\r\n"
        "a=mid:0\r\n"
        "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:AAAA\r\n"
        "a=rtpmap:0 PCMU/8000\r\n"
        "a=rtpmap:8 PCMA/8000\r\n"
        "a=rtcp-mux\r\n"
        f"m=video 40002 RTP/SAVPF {pt_list}\r\n"
        "c=IN IP4 10.0.0.1\r\n"
        "a=recvonly\r\n"
        "a=mid:1\r\n"
        "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:BBBB\r\n"
        + attrs
        + "a=rtcp-mux\r\n"
    )


# --------------------------------------------------------------------- #
# Default: off changes nothing
# --------------------------------------------------------------------- #

def test_unset_is_todays_order(monkeypatch):
    monkeypatch.delenv(_ENV, raising=False)
    assert _resolve_sdes_video_pt_order() == (96, 97)
    assert _resolve_sdes_video_pt_order() == _SDES_OFFER_VIDEO_PT_ORDER


def test_default_output_is_byte_identical_to_the_literal_it_replaced():
    """The property that matters most: the SDES offer path is shared by every
    SDES camera and this project's CHANGELOG records fleet-wide blackouts from
    changes to shared paths.  Unset must put the same bytes on the wire."""
    pt_list, attrs = _sdes_offer_video_codec_lines()
    assert pt_list == "96 97"
    assert attrs == _SHIPPED_ATTRS


def test_default_output_matches_the_resolver_with_the_env_unset(monkeypatch):
    monkeypatch.delenv(_ENV, raising=False)
    assert (_sdes_offer_video_codec_lines(_resolve_sdes_video_pt_order())
            == _sdes_offer_video_codec_lines())


# --------------------------------------------------------------------- #
# The order the env asks for is the order that comes out
# --------------------------------------------------------------------- #

@pytest.mark.parametrize("raw", ["97,96", "97 96", " 97 , 96 ", "97"])
def test_h265_first_puts_97_first_in_the_m_line(monkeypatch, raw):
    """Naming a subset is enough: what is named leads, the rest follows."""
    monkeypatch.setenv(_ENV, raw)
    pt_list, attrs = _sdes_offer_video_codec_lines(_resolve_sdes_video_pt_order())
    assert pt_list == "97 96"
    assert attrs == (
        "a=rtpmap:97 H265/90000\r\n"
        "a=fmtp:97 level-id=93\r\n"
        "a=rtpmap:96 H264/90000\r\n"
        "a=fmtp:96 level-asymmetry-allowed=1;packetization-mode=1;"
        "profile-level-id=42e01f\r\n"
    )


def test_the_m_line_of_a_reordered_offer_reads_97_before_96(monkeypatch):
    """The assertion the experiment turns on, made on a whole m-section."""
    monkeypatch.setenv(_ENV, "97,96")
    sdp = _offer(*_sdes_offer_video_codec_lines(_resolve_sdes_video_pt_order()))
    m_line = next(ln for ln in sdp.splitlines() if ln.startswith("m=video"))
    assert m_line.split(" RTP/SAVPF ")[1].split() == ["97", "96"]
    # The audio line is not a preference question and must not move.
    assert "m=audio 40000 RTP/SAVPF 0 8\r\n" in sdp


def test_naming_todays_order_explicitly_is_a_no_op(monkeypatch):
    monkeypatch.setenv(_ENV, "96,97")
    assert _resolve_sdes_video_pt_order() == (96, 97)
    assert _sdes_offer_video_codec_lines((96, 97)) == _sdes_offer_video_codec_lines()


# --------------------------------------------------------------------- #
# It can reorder and it can never narrow
# --------------------------------------------------------------------- #

@pytest.mark.parametrize("raw", [
    "", "   ", "h265", "hevc,h264", "-1", "0", "9.5", "999", "98",
    "97,97,97", "96,96", ",,,", "97,abc,96", "97,999",
])
def test_no_value_can_narrow_or_empty_the_offer(monkeypatch, raw):
    """A payload type dropped from the m-line is a codec the camera may not
    send; drop both and it has nothing to send at all.  Narrowing has its own
    variable, and the one time H265-only was measured it cost the picture in
    3 of 3 rounds -- so this one must be structurally incapable of it."""
    monkeypatch.setenv(_ENV, raw)
    order = _resolve_sdes_video_pt_order()
    assert sorted(order) == sorted(_SDES_OFFER_VIDEO_PT_ORDER)
    assert len(set(order)) == len(order)
    pt_list, attrs = _sdes_offer_video_codec_lines(order)
    assert sorted(pt_list.split()) == ["96", "97"]
    # Every payload type on the m-line carries its own rtpmap: an m-line naming
    # a codec whose parameters were left behind is an offer the camera cannot
    # act on.
    for pt in pt_list.split():
        assert f"a=rtpmap:{pt} " in attrs


def test_garbage_falls_back_to_todays_order_exactly(monkeypatch):
    monkeypatch.setenv(_ENV, "not,a,payload,type")
    assert _resolve_sdes_video_pt_order() == _SDES_OFFER_VIDEO_PT_ORDER


def test_an_empty_order_from_a_caller_falls_back_rather_than_emptying_the_line():
    assert _sdes_offer_video_codec_lines(()) == _sdes_offer_video_codec_lines()
    assert _sdes_offer_video_codec_lines((999,)) == _sdes_offer_video_codec_lines()


# --------------------------------------------------------------------- #
# Composition with the pin, which narrows
# --------------------------------------------------------------------- #

def test_the_pin_wins_over_the_order(monkeypatch):
    """Production orders first, then narrows.  With both set the pin decides
    and the order is moot -- asserted rather than assumed, because the two
    knobs pull in opposite directions and only one can be on the wire."""
    monkeypatch.setenv(_ENV, "97,96")
    monkeypatch.setenv(_PIN_ENV, "96")
    sdp = _offer(*_sdes_offer_video_codec_lines(_resolve_sdes_video_pt_order()))
    assert "m=video 40002 RTP/SAVPF 97 96\r\n" in sdp
    out = narrow_sdp_payload_types(sdp, keep_video=_resolve_sdes_video_pt())
    assert "m=video 40002 RTP/SAVPF 96\r\n" in out
    assert "a=rtpmap:97 H265/90000" not in out


def test_ordering_alone_leaves_the_pin_unset(monkeypatch):
    monkeypatch.setenv(_ENV, "97,96")
    monkeypatch.delenv(_PIN_ENV, raising=False)
    assert _resolve_sdes_video_pt() is None


# --------------------------------------------------------------------- #
# The order has to survive to the wire
# --------------------------------------------------------------------- #

def test_the_order_survives_sdp_compression(monkeypatch):
    """The offer travels twice: in full as ``offer.sdp`` and compressed into
    ``wPayload.offer.sdp``, which is what newer firmware parses.  An order that
    the compressor normalised away would never reach the camera.

    This exercises ``protocol._compress_sdp_for_camera``.  The SDES path runs a
    closure of the same shape (``_compress_sdp_req``, defined inside the open
    method and not importable); its video branch keeps the first rtpmap per
    codec in encounter order and passes ``m=`` lines through verbatim, the same
    two properties asserted here.
    """
    monkeypatch.setenv(_ENV, "97,96")
    sdp = _offer(*_sdes_offer_video_codec_lines(_resolve_sdes_video_pt_order()))
    out = _compress_sdp_for_camera(sdp)
    assert "m=video 40002 RTP/SAVPF 97 96\r\n" in out
    video = out.split("m=video")[1]
    assert video.index("a=rtpmap:97 H265/90000") < video.index("a=rtpmap:96 H264/90000")


# --------------------------------------------------------------------- #
# The production builder really uses it
# --------------------------------------------------------------------- #

def _offer_assignment_source() -> str:
    """The source of the ``sdes_offer_sdp = (...)`` assignment, and only it."""
    tree = ast.parse(inspect.getsource(sdes_open))
    src = inspect.getsource(sdes_open)
    found = [
        n for n in ast.walk(tree)
        if isinstance(n, ast.Assign)
        and any(isinstance(t, ast.Name) and t.id == "sdes_offer_sdp"
                for t in n.targets)
        and isinstance(n.value, ast.BinOp)
    ]
    assert len(found) == 1, (
        f"expected exactly one sdes_offer_sdp template assignment, found "
        f"{len(found)} - this guard inspects a single, specific offer"
    )
    return ast.get_source_segment(src, found[0]) or ""


def test_the_offer_builder_takes_its_video_codec_list_from_the_helper():
    """Not "the name appears in the module": the offer's own m=video line must
    interpolate the computed list and the codec block must come from the
    helper.  A hard-coded ``96 97`` back in the template is the state this
    change exists to leave, and it would leave every test above passing while
    the wire never changed."""
    seg = _offer_assignment_source()
    assert "{_video_pt_list}" in seg
    assert "_video_codec_attrs" in seg
    assert re.search(r"m=video[^\n]*\b96 97\b", seg) is None
    assert "a=rtpmap:96 H264/90000" not in seg
    assert "a=rtpmap:97 H265/90000" not in seg


def test_the_offer_builder_asks_the_environment_for_the_order():
    src = inspect.getsource(sdes_open)
    head = src[:src.index("sdes_offer_sdp = (")]
    assert "_resolve_sdes_video_pt_order()" in head.rsplit("_talk_state", 1)[-1]
