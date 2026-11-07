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


def test_the_wait_rule_keeps_the_ffmpeg_serve_waiting():
    """The ffmpeg serve maps one audio type and drops the other, so it always
    waits the grace for a packet on the wire (2026-11-06: a relay-only
    A001513's audio trailed its video by 1.14 s). The direct publisher skips
    it only when the answer names a type to fall back on."""
    from aidot_cameras.camera.sdes_open import _AUDIO_PT_GRACE_S, _audio_pt_wait_s

    assert _audio_pt_wait_s(direct_publish=False, answer_apt=8) == _AUDIO_PT_GRACE_S
    assert _audio_pt_wait_s(direct_publish=False, answer_apt=None) == (
        _AUDIO_PT_GRACE_S
    )
    assert _audio_pt_wait_s(direct_publish=True, answer_apt=None) == (_AUDIO_PT_GRACE_S)
    assert _audio_pt_wait_s(direct_publish=True, answer_apt=0) == 0.0
    assert _audio_pt_wait_s(direct_publish=True, answer_apt=8) == 0.0


def test_recordings_snapshots_and_h265_keep_ffmpeg():
    from aidot_cameras.camera.sdes_open import _should_direct_publish

    url = "rtsp://127.0.0.1:8554/cam"
    assert not _should_direct_publish(url, "/tmp/x.mp4", None)
    assert not _should_direct_publish(url, None, 5.0)
    assert not _should_direct_publish(url, None, None, 97)


def test_the_open_decides_the_serve_before_the_audio_wait():
    """The open reads the answer's audio PT, decides direct publish (video PT
    included) BEFORE the audio wait, gates the wait on _audio_pt_wait_s with
    that decision, and falls back to the answer's PT after the wait."""
    from tests.ast_guards import calls_to, function, module_tree, names_in

    impl = function(
        module_tree("aidot_cameras.camera.sdes_open"), "_open_sdes_stream_impl"
    )
    assert calls_to(impl, "audio_pt_from_answer_sdp"), (
        "the open no longer reads the answer's audio PT"
    )
    decide = [
        n
        for n in ast.walk(impl)
        if isinstance(n, ast.Assign)
        and any(getattr(t, "id", None) == "_direct_publish" for t in n.targets)
    ]
    waits = calls_to(impl, "_audio_pt_wait_s")
    assert len(decide) == 1 and len(waits) == 1
    assert decide[0].lineno < waits[0].lineno
    kw = {k.arg: k.value for k in waits[0].keywords}
    assert getattr(kw.get("direct_publish"), "id", None) == "_direct_publish"
    # The decision includes the video PT, so an H.265 session takes the
    # ffmpeg rule.
    assert "_keep_v" in names_in(decide[0].value)

    loops = [
        n
        for n in ast.walk(impl)
        if isinstance(n, ast.If)
        and "_apt_wait" in names_in(n.test)
        and any(isinstance(s, ast.While) for s in n.body)
    ]
    assert len(loops) == 1, "the audio wait is no longer gated on the rule"
    fallback = [
        n
        for n in ast.walk(impl)
        if isinstance(n, ast.Assign)
        and any(getattr(t, "id", None) == "_answer_apt" for t in n.targets)
        and getattr(n.value, "id", None) == "_answer_pt"
    ]
    assert fallback and all(f.lineno > loops[0].lineno for f in fallback), (
        "the serve no longer falls back to the answer's audio PT after the wait"
    )
