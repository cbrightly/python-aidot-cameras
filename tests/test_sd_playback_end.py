"""A playback ends on the camera's PLAY_END, on either transport.

The app ends a play on a 0x31b reply carrying command 7, sent unasked
(KVSWebRTCChannel sswitch_e -> reciveStreamEnd). An SDES session also ends on
the all-ones frame time on its media; either signal ends it.
"""

import ast
import struct

from aidot_cameras.camera.protocol import AvioResponseRouter, avio_header
from aidot_cameras.camera.sd_playback import (
    FRAME_TIME_END,
    PLAY_END,
    PLAY_START,
    PlaybackClock,
    answers,
    play_end_waiter,
)
from aidot_cameras.camera.sdes import SdesSession
from aidot_cameras.camera.webrtc import WebRTCSession

from tests.ast_guards import calls_to, dtls_open_family, sdes_open_family
from tests.test_sd_playback_clock import _rtp


def _reply(command: int) -> bytes:
    body = struct.pack("<III", command, 1, 60) + bytes(8)
    return avio_header(9, 0x31B, 0, len(body), 0) + body


def _sdes(router=None, clock=None):
    return SdesSession(
        proc=None,
        sdp_path="",
        outgoing_q=None,
        mqtt_fut=None,
        responses=router,
        playback_end_waiter=play_end_waiter(router) if router else None,
        playback_clock=clock,
    )


def _dtls(router=None):
    return WebRTCSession(
        pc=None,
        outgoing_q=None,
        mqtt_fut=None,
        recorder=None,
        track_tasks=[],
        responses=router,
        playback_end_waiter=play_end_waiter(router) if router else None,
    )


def test_play_end_ends_a_dtls_session():
    router = AvioResponseRouter()
    session = _dtls(router)
    assert session.playback_ended is False
    assert router.dispatch(_reply(PLAY_END))
    assert session.playback_ended is True
    # Asked again: the end stays an end.
    assert session.playback_ended is True


def test_play_end_ends_an_sdes_session_without_the_frame_time():
    router = AvioResponseRouter()
    session = _sdes(router, PlaybackClock())
    assert session.playback_ended is False
    router.dispatch(_reply(PLAY_END))
    assert session.playback_ended is True


def test_the_frame_time_still_ends_an_sdes_session_without_play_end():
    clock = PlaybackClock()
    session = _sdes(AvioResponseRouter(), clock)
    clock.observe(_rtp(97, 1, 1000))
    assert session.playback_ended is False
    clock.observe(_rtp(97, 2, 0, FRAME_TIME_END))
    assert session.playback_ended is True


def test_the_play_start_reply_is_not_an_end():
    router = AvioResponseRouter()
    start = router.expect(0x31B, answers(PLAY_START))
    session = _dtls(router)
    session._playback_waiter = start
    router.dispatch(_reply(PLAY_START))
    assert session.playback_ended is False
    router.dispatch(_reply(PLAY_END))
    assert session.playback_ended is True


def test_live_sessions_never_end():
    assert _dtls().playback_ended is False
    assert _sdes().playback_ended is False


def _ctor_calls(family, ctor: str) -> list:
    return [c for tree in family for c in calls_to(tree, ctor)]


def test_both_opens_hand_the_session_an_end_waiter():
    for family, ctor in (
        (dtls_open_family(), "WebRTCSession"),
        (sdes_open_family(), "SdesSession"),
    ):
        calls = _ctor_calls(family, ctor)
        assert calls, ctor
        for call in calls:
            assert any(k.arg == "playback_end_waiter" for k in call.keywords), ctor


def test_the_sdes_open_listens_before_play_start_goes_out():
    src = "\n".join(ast.unparse(t) for t in sdes_open_family())
    assert src.index("play_end_waiter(_avio_responses)") < src.index(
        "_cmd_chan[0](RECORD_PLAYCONTROL_REQ_CMD"
    )
