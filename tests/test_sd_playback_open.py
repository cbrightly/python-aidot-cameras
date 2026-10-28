"""An SDES open that plays an SD recording instead of streaming live.

What has to differ from a live open, all measured on an A001064 2026-10-27:
the data channel opens in SESSION_MODE 2 rather than LIVING, the offer names
both video codecs, and the open itself sends PLAY_START once the camera has
answered the mode - in SD mode nothing arrives until it does.

Every path that sends a SESSION_MODE has to say SD, and a path that cannot
play a recording has to say so rather than return the live view.
"""

import ast
import asyncio
import struct

from aidot_cameras.camera.sd_events import SdEvent
from aidot_cameras.camera.sd_playback import (
    PLAY_START,
    SESSION_MODE_LIVING,
    SESSION_MODE_SD,
    SdPlaybackStart,
    playcontrol_payload,
)
from aidot_cameras.camera.protocol import (
    AvioResponseRouter,
    avio_header,
    parse_avio_response,
)
from aidot_cameras.camera.sdes import SdesSession
from aidot_cameras.camera.sdes_open import (
    _sd_playback_start_due,
    _sd_playback_unservable,
    _sd_session_mode,
    _session_mode_echoed,
    _session_mode_req,
)
from aidot_cameras.exceptions import AidotSdPlaybackUnavailable, AidotStreamOpenError
from tests.ast_guards import (
    calls_to,
    enclosing,
    function,
    module_tree,
    name_of,
    parents,
)


def _start():
    rec = SdEvent(2026, 10, 21, 17, 24, 44, 0, 0, 0)
    return SdPlaybackStart(request=playcontrol_payload(PLAY_START, rec))


class TestSessionMode:
    def test_a_live_open_still_says_living(self):
        assert _sd_session_mode(None) == SESSION_MODE_LIVING == 1

    def test_a_playback_open_says_sd(self):
        assert _sd_session_mode(_start()) == SESSION_MODE_SD == 2


class TestWhenPlayStartGoesOut:
    def _due(self, **kw):
        base = dict(already_sent=False, cmd_chan_ready=True, mode_acked=True)
        base.update(kw)
        return _sd_playback_start_due(_start(), **base)

    def test_sent_once_the_channel_is_up_and_the_mode_answered(self):
        assert self._due()

    def test_never_on_a_live_open(self):
        assert not _sd_playback_start_due(
            None, already_sent=False, cmd_chan_ready=True, mode_acked=True
        )

    def test_not_before_the_command_channel_exists(self):
        assert not self._due(cmd_chan_ready=False)

    def test_not_before_the_camera_answered_the_mode(self):
        assert not self._due(mode_acked=False)

    def test_only_once(self):
        assert not self._due(already_sent=True)


class TestOneSessionModeBuilder:
    def test_the_trigger_body_carries_the_mode_in_its_third_word(self):
        frame = parse_avio_response(_session_mode_req(7, 0, 2, avstream=True))
        assert frame.command == 5376
        assert struct.unpack("<7I", frame.payload) == (0, 0, 2, 0, 0, 0, 0)

    def test_the_data_channel_body_is_the_eight_byte_one(self):
        frame = parse_avio_response(_session_mode_req(7, 0, 2, avstream=False))
        assert frame.payload == struct.pack("<IB3x", 0, 2)

    def test_live_still_sends_what_it_did(self):
        live = _session_mode_req(7, 5, 1, avstream=True)
        assert live == avio_header(7, 5376, 5, 28, 0) + struct.pack(
            "<IIIIIII", 0, 0, 1, 0, 0, 0, 0
        )

    def test_no_path_builds_its_own(self):
        tree = module_tree("aidot_cameras.camera.sdes_open")
        builders = [
            c
            for c in calls_to(tree, "avio_header")
            if c.args and getattr(c.args[1], "value", None) == 5376
        ]
        assert len(builders) == 1, [c.lineno for c in builders]


class TestTheModeTheCameraEchoes:
    def _resp(self, mode):
        return avio_header(1, 5377, 0, 12, 0) + struct.pack("<III", 0, 0, mode)

    def test_reads_the_mode_from_the_reply(self):
        # The two replies an A001064 sent on 2026-10-27, LIVING then SD.
        assert _session_mode_echoed(self._resp(1)) == 1
        assert _session_mode_echoed(self._resp(2)) == 2

    def test_anything_else_reads_as_none(self):
        assert _session_mode_echoed(b"") is None
        assert _session_mode_echoed(avio_header(1, 5377, 0, 0, 0)) is None
        assert _session_mode_echoed(avio_header(1, 795, 0, 12, 0) + bytes(12)) is None


class TestNoDataChannel:
    def test_an_answer_without_one_cannot_play(self):
        assert _sd_playback_unservable(_start(), "v=0\r\nm=video 9 UDP\r\n")

    def test_an_answer_with_one_can(self):
        sdp = "v=0\r\nm=video 9 UDP\r\nm=application 9 UDP/DTLS/SCTP\r\n"
        assert not _sd_playback_unservable(_start(), sdp)

    def test_a_live_open_or_no_answer_yet_is_never_refused(self):
        assert not _sd_playback_unservable(None, "v=0\r\nm=video 9 UDP\r\n")
        assert not _sd_playback_unservable(_start(), "")

    def test_the_error_is_an_open_error(self):
        assert issubclass(AidotSdPlaybackUnavailable, AidotStreamOpenError)


def _bridge():
    return function(module_tree("aidot_cameras.camera.sdes_open"), "make_sdes_bridge")


def test_the_bridge_is_told_the_mode_by_the_open():
    (call,) = calls_to(
        module_tree("aidot_cameras.camera.sdes_open"), "make_sdes_bridge"
    )
    assert any(k.arg == "_session_mode" for k in call.keywords)


def test_the_stall_nudge_only_runs_on_a_live_session():
    # Its resend would take a recording that ended, or paused, back to live.
    nudges = [
        n
        for n in ast.walk(_bridge())
        if isinstance(n, ast.BoolOp)
        and any(
            isinstance(v, ast.Call) and name_of(v.func) == "_stall_nudge_due"
            for v in n.values
        )
    ]
    assert nudges, "the stall nudge is no longer gated"
    assert all("_session_mode == 1" in ast.unparse(n.values[0]) for n in nudges)


def test_the_session_hands_out_the_reply_and_live_sessions_none():
    router = AvioResponseRouter()
    waiter = router.expect(0x31B)
    reply = avio_header(9, 0x31B, 0, 4, 0) + bytes(4)
    assert router.dispatch(reply)

    async def _read():
        live = SdesSession(proc=None, sdp_path="", outgoing_q=None, mqtt_fut=None)
        played = SdesSession(
            proc=None,
            sdp_path="",
            outgoing_q=None,
            mqtt_fut=None,
            playback_waiter=waiter,
        )
        return await live.playback_reply(0.1), await played.playback_reply(0.1)

    live, played = asyncio.run(_read())
    assert live is None
    assert played.command == 0x31B


def test_the_dtls_phase_open_hands_the_request_to_the_sdes_open():
    calls = calls_to(
        module_tree("aidot_cameras.camera.webrtc_open"), "_open_sdes_stream"
    )
    assert calls, "the SDES branch no longer calls _open_sdes_stream"
    for call in calls:
        assert any(k.arg == "sd_playback" for k in call.keywords), call.lineno


def test_the_dtls_fallback_carries_the_request_so_dtls_can_refuse_it():
    calls = calls_to(
        module_tree("aidot_cameras.camera.webrtc_open"),
        "_async_open_webrtc_stream_impl",
    )
    assert calls, "the SDES-to-DTLS fallback no longer re-enters the impl"
    for call in calls:
        assert any(k.arg == "_sd_playback" for k in call.keywords), call.lineno


def test_the_dtls_open_refuses_playback_without_a_ts_file():
    # Over DTLS the recording is taken raw into the MPEG-TS recorder, so there
    # is nothing to hand on_frame or a serve: a .ts output_path or nothing.
    tree = module_tree("aidot_cameras.camera.webrtc_open")
    up = parents(tree)
    raises = [
        n
        for n in ast.walk(tree)
        if isinstance(n, ast.Raise) and name_of(n.exc) == "AidotSdPlaybackUnavailable"
    ]
    assert len(raises) == 1
    tests = " ".join(ast.unparse(i.test) for i in enclosing(raises[0], up, ast.If))
    # The transport the open chose (use_sdes honours force_sdes), and nothing
    # that would need the H265 decoded or served.
    for needed in (
        "_sd_playback",
        "use_sdes",
        "is_ts_path",
        "on_frame",
        "rtsp_push_url",
    ):
        assert needed in tests, tests


class TestNoPinOnPlayback:
    """The open's codec pin is off for playback, wherever it is read.

    Read from the global pin, the open asked for the recording, received its
    H265 (pt 97) and then rejected it as the wrong codec against a pin of 96
    (A001064, 2026-10-27).
    """

    def test_playback_pins_nothing(self, monkeypatch):
        from aidot_cameras.camera import sdes_open

        monkeypatch.setattr(sdes_open, "_resolve_sdes_video_pt", lambda: 96)
        assert sdes_open._pinned_video_pt(_start()) is None
        assert sdes_open._pinned_video_pt(None) == 96

    def test_the_open_reads_the_pin_only_through_the_helper(self):
        tree = module_tree("aidot_cameras.camera.sdes_open")
        up = parents(tree)
        for call in calls_to(tree, "_resolve_sdes_video_pt"):
            fns = enclosing(call, up, (ast.FunctionDef, ast.AsyncFunctionDef))
            assert fns and fns[0].name == "_pinned_video_pt", call.lineno

    def test_a_playback_session_never_feeds_the_live_ts(self):
        tree = module_tree("aidot_cameras.camera.sdes_open")
        kws = [
            k
            for c in calls_to(tree, "LoopbackRtpPublisher")
            for k in c.keywords
            if k.arg == "ts_session"
        ]
        assert kws
        for k in kws:
            v = k.value
            assert isinstance(v, ast.IfExp), ast.unparse(v)
            # `None if <playback> else <the TS>`: the playback branch is None.
            assert ast.unparse(v.test) == "ps.sd_playback is not None", ast.unparse(v)
            assert isinstance(v.body, ast.Constant) and v.body.value is None

    def test_a_playback_open_does_not_count_on_the_ts(self):
        src = ast.unparse(module_tree("aidot_cameras.camera.sdes_open"))
        assert "ts_expected=ps.sd_playback is None and self._hls_ts_expected()" in src


def test_a_reply_that_comes_after_a_timed_out_wait_is_still_handed_out():
    # The A001064 answers PLAY_START 6.1-6.2 s after it, with the recording
    # already playing; a caller that asked sooner must not lose it.
    router = AvioResponseRouter()
    session = SdesSession(
        proc=None,
        sdp_path="",
        outgoing_q=None,
        mqtt_fut=None,
        playback_waiter=router.expect(0x31B),
    )

    async def _run():
        early = await session.playback_reply(0.05)
        router.dispatch(avio_header(9, 0x31B, 0, 4, 0) + bytes(4))
        late = await session.playback_reply(0.5)
        again = await session.playback_reply(0.05)
        return early, late, again

    early, late, again = asyncio.run(_run())
    assert early is None
    assert late.command == 0x31B and again.command == 0x31B


def test_other_waits_still_stop_listening_on_timeout():
    # Keepalive commands are asked again and again; a registration left on
    # each unanswered one would grow for the session's life.
    router = AvioResponseRouter()
    waiter = router.expect(0x31B)
    assert asyncio.run(waiter.result(0.05)) is None
    assert not router.dispatch(avio_header(9, 0x31B, 0, 4, 0) + bytes(4))
