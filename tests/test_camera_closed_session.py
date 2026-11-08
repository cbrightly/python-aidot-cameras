"""A DTLS session the camera closes itself after serving video is not paced.

Measured 2026-11-08 on an A000088 viewed from outside its network: about one
session in three ends 19.5-20.4 s after the DTLS handshake with a close_notify
from the camera, while consent checks are still answered. The serve loop used
to count that as a short (failed) session: 30-45 s with no picture per close,
and dormant after AIDOT_DTLS_FUTILE_VIDEO_LIMIT in a row. It now reopens on
the open gate, as after a session that lasted - but only when the camera sent
the close, video was seen, and the session lived _CAMERA_CLOSE_MIN_S.
"""

from __future__ import annotations

import types

import pytest
from OpenSSL import SSL

from aidot_cameras._vendor.aiortc import rtcdtlstransport as dtls
from aidot_cameras.camera import client as cc
from aidot_cameras.camera.client import _camera_closed_session, _session_end_wait
from aidot_cameras.camera.protocol import ReconnectPacer

from tests.test_dtls_serve_backoff import _canary_run, _close_pc_after, _paced

# --------------------------------------------------------------------------- #
# Detecting the camera's close                                                 #
# --------------------------------------------------------------------------- #


def _dtls_pair():
    """A connected DTLS client/server pair over memory BIOs, built the way
    aiortc builds its own contexts."""
    profiles = [dtls.SRTP_AES128_CM_SHA1_80]
    ours = SSL.Connection(
        dtls.RTCCertificate.generateCertificate()._create_ssl_context(
            srtp_profiles=profiles
        ),
        None,
    )
    cam = SSL.Connection(
        dtls.RTCCertificate.generateCertificate()._create_ssl_context(
            srtp_profiles=profiles
        ),
        None,
    )
    ours.set_accept_state()
    cam.set_connect_state()
    for _ in range(20):
        for conn in (ours, cam):
            try:
                conn.do_handshake()
            except SSL.WantReadError:
                pass
        _pump(cam, ours)
        _pump(ours, cam)
    return ours, cam


def _pump(src, dst) -> None:
    try:
        dst.bio_write(src.bio_read(65536))
    except SSL.WantReadError:
        pass


def _pc_over(conn):
    transport = types.SimpleNamespace(_ssl=conn)
    return types.SimpleNamespace(
        getTransceivers=lambda: [
            types.SimpleNamespace(receiver=types.SimpleNamespace(transport=transport))
        ],
        sctp=None,
    )


def test_a_close_notify_from_the_camera_is_seen():
    ours, cam = _dtls_pair()
    cam.shutdown()
    _pump(cam, ours)
    with pytest.raises(SSL.ZeroReturnError):  # what aiortc's receive loop sees
        ours.recv(1500)
    assert _camera_closed_session(_pc_over(ours))


def test_our_own_close_is_not_the_camera_closing():
    ours, _cam = _dtls_pair()
    ours.shutdown()  # what RTCDtlsTransport.stop does
    assert not _camera_closed_session(_pc_over(ours))


def test_a_live_session_is_not_closed():
    ours, _cam = _dtls_pair()
    assert not _camera_closed_session(_pc_over(ours))


def test_the_data_channel_transport_is_read_too():
    ours, cam = _dtls_pair()
    cam.shutdown()
    _pump(cam, ours)
    with pytest.raises(SSL.ZeroReturnError):
        ours.recv(1500)
    pc = types.SimpleNamespace(
        getTransceivers=lambda: [],
        sctp=types.SimpleNamespace(transport=types.SimpleNamespace(_ssl=ours)),
    )
    assert _camera_closed_session(pc)


@pytest.mark.parametrize(
    "pc",
    [
        None,
        types.SimpleNamespace(),
        types.SimpleNamespace(getTransceivers=lambda: [], sctp=None),
        types.SimpleNamespace(
            getTransceivers=lambda: [
                types.SimpleNamespace(
                    receiver=types.SimpleNamespace(
                        transport=types.SimpleNamespace(_ssl=None)
                    )
                )
            ],
            sctp=None,
        ),
    ],
    ids=["no pc", "no transceivers api", "nothing to read", "no ssl yet"],
)
def test_anything_unreadable_is_not_a_camera_close(pc):
    """aiortc's internals moving must fall back to the old pacing."""
    assert _camera_closed_session(pc) is False


# --------------------------------------------------------------------------- #
# The pacing                                                                   #
# --------------------------------------------------------------------------- #


def _pace(pacer, lived, *, video=True, closed=True):
    return _session_end_wait(
        pacer,
        lived,
        no_video=not video,
        threshold_s=30.0,
        limit=3,
        camera_closed=closed,
    )


def _pacer():
    return ReconnectPacer(30.0, 45.0, rand=lambda: 1.0)


def test_a_camera_close_after_20s_reopens_at_once_and_resets_the_pacer():
    p = _pacer()
    assert _pace(p, 5.0, closed=False) == 30.0
    assert _pace(p, 20.0) == 0.0
    assert p.attempt == 0


def test_camera_closes_in_a_row_never_go_dormant():
    p = _pacer()
    assert [_pace(p, 20.0) for _ in range(10)] == [0.0] * 10


def test_a_camera_close_inside_the_floor_is_still_short():
    """A camera that closes every session at once still meets the backoff."""
    p = _pacer()
    lived = cc._CAMERA_CLOSE_MIN_S - 0.5
    assert [_pace(p, lived) for _ in range(3)] == [30.0, 45.0, None]


def test_a_camera_close_without_video_is_still_short():
    assert _pace(_pacer(), 20.0, video=False) == 30.0


def test_a_20s_session_that_dropped_is_still_short():
    assert _pace(_pacer(), 20.0, closed=False) == 30.0


# --------------------------------------------------------------------------- #
# Through the real serve cycle                                                 #
# --------------------------------------------------------------------------- #


def _closing_camera(monkeypatch, make_camera_device_client, canary):
    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "30")
    monkeypatch.setattr(cc, "_CAMERA_CLOSE_MIN_S", 0.1)
    monkeypatch.setattr(cc, "_camera_closed_session", lambda pc: True)
    run, cam = _canary_run(monkeypatch, make_camera_device_client, canary)
    _close_pc_after(cam, 0.3)
    return run


def test_the_serve_loop_reopens_after_a_camera_close_with_video(
    monkeypatch, make_camera_device_client, caplog
):
    run = _closing_camera(
        monkeypatch, make_camera_device_client, {"frames": 40, "keyframes": 2}
    )
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    msgs = [r.getMessage() for r in caplog.records]
    assert not _paced(caplog), msgs
    assert any(
        "closed its session" in m and "camera closed the session" in m for m in msgs
    ), msgs
    assert run.opens == 2


def test_the_serve_loop_still_paces_a_camera_close_without_video(
    monkeypatch, make_camera_device_client, caplog
):
    monkeypatch.setenv("AIDOT_DTLS_VIDEO_GRACE_S", "10")
    run = _closing_camera(
        monkeypatch, make_camera_device_client, {"frames": 40, "keyframes": 0}
    )
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    msgs = [r.getMessage() for r in caplog.records]
    assert _paced(caplog), msgs
    assert any("camera closed the session" in m for m in msgs), msgs


# --------------------------------------------------------------------------- #
# A stdout serve outlives the session                                          #
# --------------------------------------------------------------------------- #


def _stdout_run(monkeypatch, make_camera_device_client, *, reader_gone):
    """A stdout serve (the go2rtc exec: source) whose session's PC closes.

    The mux writes its final flush after the stop flag, as the real one does;
    with ``reader_gone`` its writes fail before the cycle ends instead."""
    from tests.keepalive_driver import DtlsServeRun, FakeDtlsSession

    monkeypatch.setenv("AIDOT_DTLS_SHORT_SESSION_S", "0.1")  # it lasted
    cam = make_camera_device_client("A000088")
    cam._stream_idle_opt = 0.0
    run = DtlsServeRun(monkeypatch, cam, [FakeDtlsSession()], url="-", timeout=1.5)

    def _mux(vq, aq, wfile, progress, stop, *, state=None, **kw):
        import time

        try:
            while not stop.is_set():
                if reader_gone:
                    raise BrokenPipeError
                progress[0] = time.monotonic()
                stop.wait(0.002)
            wfile.write(b"G" * 188)  # the final flush
            failed = False
        except (OSError, ValueError):
            failed = True
        if state is not None:
            state["failed"] = failed

    monkeypatch.setattr(cc, "_dtls_av_mux_run", _mux)
    _close_pc_after(cam, 0.3)
    return run


def test_a_session_end_does_not_release_a_stdout_serve(
    monkeypatch, make_camera_device_client, caplog
):
    """The cycle closes its stdout duplicate before joining the mux, so the
    mux's last write fails; that is the teardown, not the reader leaving."""
    run = _stdout_run(monkeypatch, make_camera_device_client, reader_gone=False)
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    msgs = [r.getMessage() for r in caplog.records]
    assert not any("no longer read" in m for m in msgs), msgs
    assert run.opens == 2


def test_a_reader_that_leaves_still_releases_a_stdout_serve(
    monkeypatch, make_camera_device_client, caplog
):
    run = _stdout_run(monkeypatch, make_camera_device_client, reader_gone=True)
    with caplog.at_level("INFO", logger="aidot_cameras.camera.client"):
        run.run()
    msgs = [r.getMessage() for r in caplog.records]
    assert any("no longer read" in m for m in msgs), msgs
    assert run.opens == 1
