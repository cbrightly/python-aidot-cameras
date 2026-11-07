"""Direct publish attaches audio from the camera's NEGOTIATED payload type.

Measured 2026-09-19 on an A001513: the answer said ``m=audio 9 RTP/SAVPF 8``,
video started, the 1 s audio grace ran out and the serve went video-only - and
the first PCMA packet arrived 0.86 s later. The ffmpeg serve needs a packet in
hand before it maps audio (an empty mapped stream stalls its mpegts mux); the
direct publish does not, because go2rtc takes an announced track whose first
packet arrives late. So it attaches audio from the answer and skips the grace.
"""

import ast

from aidot_cameras.camera.sdes_open import audio_pt_from_answer_sdp

ANSWER = (
    "v=0\r\n"
    "m=audio 9 RTP/SAVPF 8\r\na=rtpmap:8 PCMA/8000\r\n"
    "m=video 9 RTP/SAVPF 96\r\na=rtpmap:96 H264/90000\r\n"
    "m=application 9 SCTP webrtc-datachannel\r\n"
)


def test_pcma_from_the_audio_section():
    assert audio_pt_from_answer_sdp(ANSWER) == 8


def test_pcmu_is_served_too():
    assert audio_pt_from_answer_sdp(ANSWER.replace("SAVPF 8", "SAVPF 0")) == 0


def test_pt0_that_is_h265_video_is_never_taken_for_audio():
    """On this fleet PT 0 is sometimes H265 VIDEO; with audio on 8 the answer
    must still yield 8, and a PT claimed by both sections yields nothing."""
    h265_on_0 = (
        "m=audio 9 RTP/SAVPF 8\r\nm=video 9 RTP/SAVPF 0\r\na=rtpmap:0 H265/90000\r\n"
    )
    assert audio_pt_from_answer_sdp(h265_on_0) == 8
    ambiguous = "m=audio 9 RTP/SAVPF 0\r\nm=video 9 RTP/SAVPF 0\r\n"
    assert audio_pt_from_answer_sdp(ambiguous) is None


def test_no_audio_section_or_unserved_codec_gives_none():
    assert audio_pt_from_answer_sdp("m=video 9 RTP/SAVPF 96\r\n") is None
    assert audio_pt_from_answer_sdp("m=audio 9 RTP/SAVPF 111\r\n") is None
    assert audio_pt_from_answer_sdp("") is None
    assert audio_pt_from_answer_sdp(None) is None


def test_the_open_uses_it_at_once_only_for_direct_publish_and_skips_the_grace():
    """Direct publish takes the negotiated audio PT straight away and skips the
    audio grace; the ffmpeg serve waits the grace for a packet on the wire and
    falls back to the answer's PT only if none came (2026-11-06: a relay-only
    A001513's audio trailed its video by 1.14 s and the session served silent)."""
    from tests.ast_guards import calls_to, names_in, sdes_open_family

    family = sdes_open_family()
    assert any(
        calls_to(n, "audio_pt_from_answer_sdp") for fn in family for n in ast.walk(fn)
    ), "the open no longer reads the answer's audio PT"

    def _assigns(node, name):
        return any(
            isinstance(s, ast.Assign)
            and any(getattr(t, "id", None) == name for t in s.targets)
            for s in ast.walk(node)
        )

    # Taken at once only under the direct-publish test.
    at_once = [
        n
        for fn in family
        for n in ast.walk(fn)
        if isinstance(n, ast.If)
        and calls_to(n.test, "_should_direct_publish")
        and _assigns(n, "_answer_apt")
    ]
    assert at_once, "direct publish no longer attaches the answer's audio PT"

    graces = [
        n
        for fn in family
        for n in ast.walk(fn)
        if isinstance(n, ast.If)
        and any(
            isinstance(s, ast.Assign)
            and any(getattr(t, "id", None) == "_apt_deadline" for t in s.targets)
            for s in n.body
        )
    ]
    assert graces and all("_answer_apt" in names_in(g.test) for g in graces)
    # ...and the grace ends in the fallback to the answer's PT, after the wait.
    for g in graces:
        kinds = [type(s).__name__ for s in g.body]
        assert "While" in kinds, "the grace no longer waits for a packet"
        after = g.body[kinds.index("While") + 1 :]
        assert any(_assigns(s, "_answer_apt") for s in after), (
            "the ffmpeg serve no longer falls back to the answer's audio PT"
        )
