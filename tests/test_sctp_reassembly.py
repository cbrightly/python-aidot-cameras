"""A fragmented SCTP reply must be reassembled before it is parsed.

RFC 4960 s3.3.1 puts B (0x02, begins) and E (0x01, ends) flags in the DATA
chunk header. The camera's SD listing reply is ~2.8 KB and arrives as several
~1.2 KB fragments. We handed every fragment to `parse_avio_response` as a whole
frame: the first was correctly REJECTED (its declared payload length overruns
the fragment) and the rest decoded as junk commands - `cmd=0`, `cmd=304939521`,
`cmd=489688837`, all observed live. The reply sat on the wire while Home
Assistant told the user "the camera did not answer when asked what it holds".

aiortc reassembles for the DTLS path, which is why SD listing worked there and
never on SDES.
"""
from aidot_cameras.camera.sdes_open import (
    _SCTP_REASSEMBLY_CAP,
    _sctp_reassemble,
)

B, E, BE = 0x02, 0x01, 0x03


class TestWholeMessages:
    def test_a_chunk_with_both_flags_passes_straight_through(self):
        buf = {}
        assert _sctp_reassemble(BE, 0, b"hello", buf) == b"hello"
        assert buf == {}, "a whole message must leave no state behind"

    def test_unordered_and_other_flag_bits_do_not_confuse_it(self):
        """U (0x04) rides in the same byte and says nothing about framing."""
        assert _sctp_reassemble(BE | 0x04, 0, b"x", {}) == b"x"


class TestFragmentedMessages:
    def test_two_fragments_are_joined(self):
        buf = {}
        assert _sctp_reassemble(B, 0, b"AAA", buf) is None
        assert _sctp_reassemble(E, 0, b"BBB", buf) == b"AAABBB"
        assert buf == {}

    def test_a_middle_fragment_is_held(self):
        buf = {}
        assert _sctp_reassemble(B, 0, b"1", buf) is None
        assert _sctp_reassemble(0, 0, b"2", buf) is None
        assert _sctp_reassemble(0, 0, b"3", buf) is None
        assert _sctp_reassemble(E, 0, b"4", buf) == b"1234"

    def test_the_real_shape_two_1172_byte_fragments(self):
        """The SD listing reply as actually observed on the wire."""
        buf = {}
        a, b = b"\xaa" * 1172, b"\xbb" * 1172
        assert _sctp_reassemble(B, 0, a, buf) is None
        out = _sctp_reassemble(E, 0, b, buf)
        assert out == a + b and len(out) == 2344

    def test_streams_are_kept_apart(self):
        buf = {}
        assert _sctp_reassemble(B, 0, b"s0-", buf) is None
        assert _sctp_reassemble(B, 1, b"s1-", buf) is None
        assert _sctp_reassemble(E, 1, b"end1", buf) == b"s1-end1"
        assert _sctp_reassemble(E, 0, b"end0", buf) == b"s0-end0"


class TestItRefusesToGuess:
    def test_a_continuation_with_no_beginning_is_dropped(self):
        """Passing it on would hand the AVIO parser a body with no header."""
        assert _sctp_reassemble(E, 0, b"orphan", {}) is None
        assert _sctp_reassemble(0, 0, b"orphan", {}) is None

    def test_a_new_beginning_abandons_a_half_built_message(self):
        buf = {}
        _sctp_reassemble(B, 0, b"stale", buf)
        assert _sctp_reassemble(B, 0, b"fresh", buf) is None
        assert _sctp_reassemble(E, 0, b"!", buf) == b"fresh!"

    def test_a_stream_that_never_ends_is_capped_not_grown_forever(self):
        buf = {}
        _sctp_reassemble(B, 0, b"x", buf)
        assert _sctp_reassemble(0, 0, b"y" * (_SCTP_REASSEMBLY_CAP + 1), buf) is None
        assert buf == {}, "the oversized stream must be discarded, not retained"

    def test_an_empty_fragment_does_not_end_a_message_early(self):
        buf = {}
        assert _sctp_reassemble(B, 0, b"body", buf) is None
        assert _sctp_reassemble(0, 0, b"", buf) is None
        assert _sctp_reassemble(E, 0, b"", buf) == b"body"


class TestItIsWiredIntoTheReceivePath:
    def test_the_data_branch_reassembles_before_it_parses(self):
        import inspect
        import re
        from aidot_cameras.camera import sdes_open
        src = inspect.getsource(sdes_open)
        m = re.search(r"elif _pd_ct8 == 0x00 and _sct == 'DONE':(.*?)_sc_answered",
                      src, re.S)
        assert m, "the inbound SCTP DATA branch has moved or gone"
        branch = m.group(1)
        assert "_sctp_reassemble" in branch, (
            "fragments are parsed individually again - a large SD listing reply "
            "will be dropped and reported as 'the camera did not answer'")
        assert branch.index("_sctp_reassemble") < branch.index("_sc_cmd"), (
            "reassembly must happen before the command id is read")
