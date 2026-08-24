"""ffmpeg SDES stderr log level: quiet the expected NO_MEDIA case, keep real errors.

A camera whose SRTP never reaches us (NO_MEDIA - e.g. media not traversing the
relay) produces the expected "could not find codec parameters / empty output"
ffmpeg stderr on every serve retry. That must log at DEBUG (not spam WARNING).
But a genuine ffmpeg error - whether media was flowing, or an unexpected failure
with no media - still logs at WARNING so real problems are not hidden.
"""
import logging
import types

from aidot_cameras.camera.sdes import SdesSession


def _log_fn(last_media, device_id="dev-abc"):
    obj = types.SimpleNamespace(
        last_media_monotonic=last_media, _device_id=device_id
    )
    return SdesSession._log_ffmpeg_stderr.__get__(obj)


def _stderr_records(caplog):
    return [r for r in caplog.records if "ffmpeg SDES stderr" in r.getMessage()]


def test_no_media_expected_shape_logs_debug(caplog):
    for msg in (b"...Output file is empty, nothing was encoded",
                b"Could not find codec parameters for stream 1 (Video: h264, none)"):
        caplog.clear()
        with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.sdes"):
            _log_fn(0.0)(msg)
        recs = _stderr_records(caplog)
        assert recs and recs[0].levelno == logging.DEBUG, msg


def test_the_log_names_the_camera(caplog):
    """Several SDES cameras stream at once; an unattributed line is near-useless.

    A burst of these lines with no device id could not be pinned to a camera
    during a live investigation, which cost real time and one wrong conclusion.
    """
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.sdes"):
        _log_fn(1.0, device_id="0a1b2c3d4e5f")(b"Error writing trailer")
    recs = _stderr_records(caplog)
    assert recs and "0a1b2c3d4e5f" in recs[0].getMessage()


def test_no_media_unexpected_error_still_warns(caplog):
    # No media, but NOT the expected empty-output shape -> a real ffmpeg failure.
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.sdes"):
        _log_fn(0.0)(b"/tmp/x.sdp: Permission denied")
    recs = _stderr_records(caplog)
    assert recs and recs[0].levelno == logging.WARNING


def test_media_flowed_logs_warning(caplog):
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.sdes"):
        _log_fn(1234.5)(b"Could not find codec parameters for stream 1")
    recs = _stderr_records(caplog)
    assert recs and recs[0].levelno == logging.WARNING


def test_empty_stderr_logs_nothing(caplog):
    with caplog.at_level(logging.DEBUG, logger="aidot_cameras.camera.sdes"):
        _log_fn(0.0)(b"")
    assert _stderr_records(caplog) == []
