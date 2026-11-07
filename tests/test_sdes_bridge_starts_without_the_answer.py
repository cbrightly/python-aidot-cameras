"""The SDES open starts its bridge without waiting for the camera's answer.

It used to wait up to 8 s for the webrtcResp before the bridge started, and
nothing answered the camera's binding requests while it did. Now an answer in
hand is applied before the bridge starts and a later one is applied in the
first-media wait, which nominates from it and hands its credentials to the
bridge's nomination tick.
"""

import ast
import types

from aidot_cameras.camera.sdes_open import _adopt_answer_sdp

_ANSWER = "\r\n".join(
    [
        "v=0",
        "m=audio 9 RTP/SAVPF 8",
        "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:CAMaudioKEYCAMaudioKEYCAMaudioKEYCAMaudio",
        "a=ice-ufrag:CAMu",
        "a=ice-pwd:CAMpwdCAMpwdCAMpwd",
        "a=candidate:1 1 udp 2130706431 10.0.0.9 4000 typ host",
        "m=video 9 RTP/SAVPF 96",
        "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:CAMvideoKEYCAMvideoKEYCAMvideoKEYCAMvideo",
        "m=application 9 UDP/DTLS/SCTP webrtc-datachannel",
        "a=crypto:1 AES_CM_128_HMAC_SHA1_80 inline:DCkey",
        "",
    ]
)


def _ps():
    lines = []
    sctp = {"state": "CLOSED"}
    ps = types.SimpleNamespace(
        _pre_launch_answer_sdp="",
        _dc_probe_fp="present",
        _dc_answer_has_app=False,
        _cam_key_audio="",
        srtp_key_audio="OURaudio",
        srtp_key_video="OURvideo",
        _sctp=sctp,
        _status=lines.append,
    )
    return ps, lines, sctp


def test_the_answer_sets_the_keys_and_the_data_channel_flag():
    ps, lines, sctp = _ps()
    _adopt_answer_sdp(ps, _ANSWER)
    assert ps._pre_launch_answer_sdp == _ANSWER
    assert ps._dc_answer_has_app is True
    assert ps._cam_key_audio.startswith("CAMaudioKEY")
    assert ps.srtp_key_audio == ps._cam_key_audio
    assert ps.srtp_key_video.startswith("CAMvideoKEY")
    # The bridge holds this dict: never rebound by a late adoption.
    assert ps._sctp is sctp
    assert any("DC probe ACCEPTED" in ln for ln in lines)
    assert not any("DCkey" in ln for ln in lines), "the key must be masked"


def test_an_answer_without_a_data_channel_or_keys_changes_nothing_else():
    ps, lines, _ = _ps()
    _adopt_answer_sdp(ps, "v=0\r\nm=audio 9 RTP/SAVPF 8\r\n")
    assert ps._dc_answer_has_app is False
    assert ps.srtp_key_audio == "OURaudio" and ps.srtp_key_video == "OURvideo"
    assert any("DC probe REJECTED" in ln for ln in lines)


def _tree():
    from tests.ast_guards import module_tree

    return module_tree("aidot_cameras.camera.sdes_open")


def test_the_harvest_does_not_wait_for_the_answer():
    from tests.ast_guards import calls_to, function

    harvest = function(_tree(), "_sdes_open_harvest_answer")
    for name in ("wait", "wait_for", "shield", "_sdes_await_answer_or_terminal"):
        assert not calls_to(harvest, name), f"the harvest calls {name}"
    sleeps = calls_to(harvest, "sleep")
    assert sleeps and all(
        isinstance(c.args[0], ast.Constant) and c.args[0].value == 0 for c in sleeps
    )
    assert calls_to(harvest, "_adopt_answer_sdp")


def test_a_late_answer_is_applied_for_the_bridge():
    """Applied by one closure, and the credentials go to the dict the bridge's
    nomination tick reads."""
    from tests.ast_guards import calls_to, function

    impl = function(_tree(), "_open_sdes_stream_impl")
    adopt = function(impl, "_adopt_late_answer")
    assert calls_to(adopt, "_adopt_answer_sdp")
    assert calls_to(adopt, "_warn_if_every_candidate_off_subnet")
    writes = {
        sub.slice.value
        for n in ast.walk(adopt)
        if isinstance(n, ast.Assign)
        for sub in n.targets
        if isinstance(sub, ast.Subscript)
        and getattr(sub.value, "id", None) == "_bridge_uc_info"
        and isinstance(sub.slice, ast.Constant)
    }
    assert {"ufrag", "pwd", "cands"} <= writes


def test_it_is_applied_in_the_wait_after_it_and_after_the_serve_starts():
    """Video can end the wait in the same 0.1 s tick the answer lands in, and a
    camera that streams before it answers ends it with no answer at all; both
    still need the camera's SRTP key adopted."""
    from tests.ast_guards import calls_to, function, names_in

    impl = function(_tree(), "_open_sdes_stream_impl")
    (main,) = [
        n
        for n in ast.walk(impl)
        if isinstance(n, ast.While)
        and {"_first_video_pt", "_media_deadline"} <= names_in(n.test)
    ]
    in_loop = calls_to(main, "_adopt_late_answer")
    assert in_loop
    after = [
        c for c in calls_to(impl, "_adopt_late_answer") if c.lineno > main.end_lineno
    ]
    # The final (WARNING) stall report, not the ones in the cancel handlers.
    stall = max(c.lineno for c in calls_to(impl, "_report_first_media_stall"))
    serve = min(c.lineno for c in calls_to(impl, "_spawn_serve"))
    assert any(main.end_lineno < c.lineno < stall for c in after)
    assert any(c.lineno > serve for c in after)
