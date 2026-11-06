"""A camera that says it sends audio gets longer for its first audio packet.

The ffmpeg serve maps audio only once an audio packet has been seen, and after
the first video packet it waited ``_AUDIO_PT_GRACE_S`` (1 s) for one. Measured
2026-11-06 on an A001513 reachable only through the relay:

    13:19:22.1  first video
    13:19:23.101  no audio observed before the serve launched ... without audio
    13:19:23.237  bridge: first audio RTP pt=8

Audio was 136 ms past the grace, and the session served video only for its whole
life. The camera's answer had named pt 8 all along, so when the answer names an
audio payload type the grace is longer; one that names none keeps the tight one.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import (
    _AUDIO_PT_ANSWERED_GRACE_S,
    _AUDIO_PT_GRACE_S,
    _audio_pt_grace_s,
)

MEASURED_AUDIO_LAG_S = 1.14


def test_an_answer_naming_audio_gets_the_longer_grace():
    assert _audio_pt_grace_s(True) == _AUDIO_PT_ANSWERED_GRACE_S


def test_an_answer_naming_no_audio_keeps_the_tight_grace():
    assert _audio_pt_grace_s(False) == _AUDIO_PT_GRACE_S


def test_the_longer_grace_clears_the_measured_lag_with_room():
    assert _AUDIO_PT_ANSWERED_GRACE_S >= 2 * MEASURED_AUDIO_LAG_S
    # It ends as soon as audio arrives, so it only costs a camera that names
    # audio and then sends none; keep that cost small.
    assert _AUDIO_PT_ANSWERED_GRACE_S <= 5.0


class _Fut:
    def __init__(self, sdp=None, exc=None, done=True):
        self._sdp, self._exc, self._done = sdp, exc, done

    def done(self):
        return self._done

    def cancelled(self):
        return False

    def exception(self):
        return self._exc

    def result(self):
        return {"sdp": self._sdp}


def test_the_answer_check_reads_a_settled_answer_only():
    from aidot_cameras.camera.sdes_open import _answer_names_audio_pt

    assert _answer_names_audio_pt(_Fut("m=audio 9 RTP/SAVPF 8\r\n")) is True
    assert _answer_names_audio_pt(_Fut("m=video 9 RTP/SAVPF 96\r\n")) is False
    assert (
        _answer_names_audio_pt(_Fut("m=audio 9 RTP/SAVPF 8\r\n", done=False)) is False
    )
    assert _answer_names_audio_pt(_Fut(exc=RuntimeError("x"))) is False
    assert _answer_names_audio_pt(None) is False


def test_a_rejected_or_silent_audio_section_keeps_the_tight_grace():
    from aidot_cameras.camera.sdes_open import _answer_names_audio_pt

    rejected = "m=audio 0 RTP/SAVPF 8\r\nm=video 9 RTP/SAVPF 96\r\n"
    inactive = "m=audio 9 RTP/SAVPF 8\r\na=inactive\r\nm=video 9 RTP/SAVPF 96\r\n"
    recvonly = "m=audio 9 RTP/SAVPF 8\r\na=recvonly\r\nm=video 9 RTP/SAVPF 96\r\n"
    sending = "m=audio 9 RTP/SAVPF 8\r\na=sendrecv\r\nm=video 9 RTP/SAVPF 96\r\na=inactive\r\n"
    assert _answer_names_audio_pt(_Fut(rejected)) is False
    assert _answer_names_audio_pt(_Fut(inactive)) is False
    assert _answer_names_audio_pt(_Fut(recvonly)) is False
    assert _answer_names_audio_pt(_Fut(sending)) is True


def test_the_open_waits_the_grace_the_answer_earns():
    """The grace the open waits comes from _audio_pt_grace_s, fed by the answer
    check - not a bare constant."""
    import ast

    from tests.ast_guards import calls_to, sdes_open_family

    deadlines = [
        n
        for fn in sdes_open_family()
        for n in ast.walk(fn)
        if isinstance(n, ast.Assign)
        and any(getattr(t, "id", None) == "_apt_deadline" for t in n.targets)
    ]
    assert deadlines, "the open no longer waits an audio grace"
    assert all(calls_to(d, "_audio_pt_grace_s") for d in deadlines)
    assert any(
        calls_to(n, "_answer_names_audio_pt")
        for fn in sdes_open_family()
        for n in ast.walk(fn)
    )
