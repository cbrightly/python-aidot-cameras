"""A pinned SDES camera that sends the other codec is re-opened, not served.

The in-sync HLS TS is promised to Home Assistant before a session exists,
on the strength of the H.264 pin (``_hls_ts_eligible``). The A001064 answers
from its own template: on 2026-08-26, 15 of 107 pinned opens sent H.265. Such
a session kept the ffmpeg serve, which never feeds the TS, so Home Assistant's
HLS view and recordings read a stream nothing wrote - for the whole session,
and the integration keeps the Stream because the session is live.

A camera that answered H.264 86% of the time will on a retry; so the attempt
is abandoned to the retry, a bounded number of times, and only then served as
it came.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.camera.sdes_open import (
    _pinned_codec_retry_limit,
    _should_retry_pinned_codec,
)
from aidot_cameras.exceptions import AidotCameraNoMedia, AidotCameraWrongCodec


@pytest.mark.parametrize(
    ("observed", "pinned", "ts_expected", "mismatches", "retry"),
    [
        (97, 96, True, 0, True),  # the case: H.265 against an H.264 pin
        (97, 96, True, 1, True),
        (97, 96, True, 2, False),  # the third mismatch in a row is served
        (96, 96, True, 0, False),  # the camera kept its promise
        (97, None, True, 0, False),  # no pin: either codec was offered
        (97, 96, False, 0, False),  # nothing waits on the TS: serve as before
        (None, 96, True, 0, False),  # no video observed: the no-media path
        (0, 96, True, 0, False),  # not a video payload type
    ],
)
def test_when_a_pinned_camera_that_feeds_the_ts_is_reopened(
    observed, pinned, ts_expected, mismatches, retry
):
    assert (
        _should_retry_pinned_codec(
            observed, pinned, ts_expected=ts_expected, mismatches=mismatches, limit=2
        )
        is retry
    )


def test_the_retry_limit_is_tunable_and_never_raises(monkeypatch):
    monkeypatch.delenv("AIDOT_PINNED_CODEC_RETRIES", raising=False)
    assert _pinned_codec_retry_limit() == 2
    monkeypatch.setenv("AIDOT_PINNED_CODEC_RETRIES", "5")
    assert _pinned_codec_retry_limit() == 5
    monkeypatch.setenv("AIDOT_PINNED_CODEC_RETRIES", "0")
    assert _pinned_codec_retry_limit() == 0  # serve as it comes, always
    monkeypatch.setenv("AIDOT_PINNED_CODEC_RETRIES", "many")
    assert _pinned_codec_retry_limit() == 2
    monkeypatch.setenv("AIDOT_PINNED_CODEC_RETRIES", "-3")
    assert _pinned_codec_retry_limit() == 2


def test_the_abandon_is_a_no_media_abandon_that_names_both_codecs():
    # Any caller that already retries a no-media abandon retries this one; the
    # keepalive loop tells them apart (a camera that sent media is not one
    # that sent none, and must not count towards the futile-keepalive stop).
    err = AidotCameraWrongCodec(observed_pt=97, pinned_pt=96)
    assert isinstance(err, AidotCameraNoMedia)
    assert err.observed_pt == 97 and err.pinned_pt == 96
    assert "97" in str(err) and "96" in str(err)


def _loop_source() -> str:
    import inspect

    from aidot_cameras.camera.client import CameraMixin

    return inspect.getsource(CameraMixin._sdes_keepalive_loop_inner)


def test_the_keepalive_retries_a_wrong_codec_without_no_media_accounting():
    import re

    src = _loop_source()
    assert "except AidotCameraWrongCodec" in src
    assert src.index("except AidotCameraWrongCodec") < src.index(
        "except AidotCameraNoMedia"
    ), "the more specific handler must come first, or it is never reached"
    i = src.index("            except AidotCameraWrongCodec")
    m = re.search(r"\n            except ", src[i + 1 :])
    branch = src[i : i + 1 + m.start()]
    assert "_next_no_media_streak" not in branch
    assert "_should_abandon_keepalive" not in branch
    assert "continue" in branch


def test_the_camera_knows_whether_a_ts_reader_is_expected(monkeypatch):
    from aidot_cameras.camera.client import CameraMixin

    class _Cam(CameraMixin):
        is_sdes_camera = True

    cam = _Cam.__new__(_Cam)
    cam.info = type("Info", (), {"model_id": "LK.IPC.A001064"})()
    for k in ("AIDOT_HLS_DIRECT_TS", "AIDOT_DIRECT_PUBLISH", "AIDOT_PUBLISH_AAC"):
        monkeypatch.setenv(k, "1")
    monkeypatch.setenv("AIDOT_SDES_VIDEO_PT", "96")
    assert cam._hls_ts_expected() is True
    monkeypatch.setenv("AIDOT_HLS_DIRECT_TS", "0")
    assert cam._hls_ts_expected() is False
