"""Direct publish attaches audio from the camera's NEGOTIATED payload type.

Measured 2026-09-19 on an A001513: the answer said ``m=audio 9 RTP/SAVPF 8``,
video started, the 1 s audio grace ran out and the serve went video-only - and
the first PCMA packet arrived 0.86 s later. The ffmpeg serve needs a packet in
hand before it maps audio (an empty mapped stream stalls its mpegts mux); the
direct publish does not, because go2rtc takes an announced track whose first
packet arrives late. So it attaches audio from the answer and skips the grace.
"""

import inspect

from aidot_cameras.camera import sdes_open
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


def test_the_open_uses_it_only_for_direct_publish_and_skips_the_grace():
    src = inspect.getsource(sdes_open)
    i = src.index("_answer_apt = None")
    block = src[i : src.index("_vpt = _first_video_pt[0]", i)]
    # gated on the direct publish decision, never on the ffmpeg serve
    assert "_should_direct_publish(" in block
    assert "audio_pt_from_answer_sdp(" in block
    # the grace wait only runs when no negotiated PT was taken
    assert "and _answer_apt is None:" in block
    assert (
        "_apt = _first_audio_pt[0] if _first_audio_pt[0] is not None else _answer_apt"
        in src
    )


def test_key_restart_keeps_the_audio_narrowing():
    """The SRTP key-change relaunch rebuilds the SDP; without the negotiated
    PT the audio line would be left unnarrowed and the publisher would take
    PCMU (first listed) and drop every PCMA packet."""
    src = inspect.getsource(sdes_open)
    assert "else _keep_a" in src[src.index("_build_restart_sdp(\n") :]
