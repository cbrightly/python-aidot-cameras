"""The bridge's SRTP receive session must follow the camera's key.

For every SDES model this library supports (`_PLAIN_RTP_MODELS` = A001064,
A001513) the bridge is the ONLY decryptor: the ffmpeg SDP is RTP/AVP with no
a=crypto, so a wrong key in the bridge means no media at all, not merely a
degraded stream.

Two defects made that session permanent once built:

- It was built behind `if not hasattr(_bridge_fn, '_srtp_rx_sess')`, and the
  first statement inside the guard set the attribute. So the guard was False on
  every subsequent packet whether or not construction had succeeded - a session
  that failed to build (pylibsrtp missing, bad key) was latched off for the
  lifetime of the stream, and a session that built with the wrong key was
  latched on.
- The key it built from was `_cam_key_audio or srtp_key_audio`. The
  second-webrtcResp handler adopts the camera's real key into `srtp_key_audio`
  but leaves `_cam_key_audio` at the value parsed from the first answer, so on
  echo-reversal cameras the `or` keeps resolving to the stale key. Un-latching
  alone does not fix those cameras; the session would rebuild from the same
  wrong key forever.

The helper below owns the decision. It rebuilds when the key changes, leaves the
session unset when construction raises so the next packet retries, and treats a
missing SRTP module as permanent (an ImportError cannot become true later in the
process, and retrying it per packet re-walks sys.path at frame rate).
"""
import ast
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import aidot_cameras.camera.sdes_open as so  # noqa: E402
from aidot_cameras.camera.sdes_open import _ensure_srtp_rx_session  # noqa: E402


class _Holder:
    """Stands in for `_bridge_fn`, which the bridge uses as an attribute bag."""


class _FakeSession:
    def __init__(self, key):
        self.key = key


def _source() -> str:
    return so.__loader__.get_source("aidot_cameras.camera.sdes_open")


# --- behaviour of the session helper -------------------------------------


def test_rebuilds_the_session_when_the_camera_key_changes():
    holder = _Holder()
    built = []

    def build(key):
        built.append(key)
        return _FakeSession(key)

    first = _ensure_srtp_rx_session(holder, "keyA", build)
    second = _ensure_srtp_rx_session(holder, "keyB", build)

    assert built == ["keyA", "keyB"]
    assert first.key == "keyA"
    assert second.key == "keyB"
    assert holder._srtp_rx_sess is second


def test_reuses_the_session_while_the_key_is_unchanged():
    holder = _Holder()
    built = []

    def build(key):
        built.append(key)
        return _FakeSession(key)

    first = _ensure_srtp_rx_session(holder, "keyA", build)
    second = _ensure_srtp_rx_session(holder, "keyA", build)

    assert built == ["keyA"]
    assert second is first


def test_a_failed_build_is_retried_on_the_next_packet():
    holder = _Holder()
    attempts = []

    def build(key):
        attempts.append(key)
        if len(attempts) == 1:
            raise ValueError("transient")
        return _FakeSession(key)

    assert _ensure_srtp_rx_session(holder, "keyA", build) is None
    assert getattr(holder, "_srtp_rx_sess", None) is None

    recovered = _ensure_srtp_rx_session(holder, "keyA", build)

    assert attempts == ["keyA", "keyA"]
    assert recovered.key == "keyA"


def test_a_missing_srtp_module_is_not_retried_on_every_packet():
    holder = _Holder()
    attempts = []

    def build(key):
        attempts.append(key)
        raise ImportError("No module named 'pylibsrtp'")

    for _ in range(5):
        assert _ensure_srtp_rx_session(holder, "keyA", build) is None

    assert attempts == ["keyA"]


def test_an_unknown_key_builds_nothing():
    holder = _Holder()

    def build(key):
        raise AssertionError("must not build without a key")

    assert _ensure_srtp_rx_session(holder, "", build) is None


def test_the_first_build_is_reported_as_first_and_a_rebuild_is_not():
    holder = _Holder()
    reported = []

    _ensure_srtp_rx_session(
        holder, "keyA", _FakeSession, on_built=reported.append
    )
    _ensure_srtp_rx_session(
        holder, "keyB", _FakeSession, on_built=reported.append
    )

    assert reported == [True, False]


def test_a_build_failure_is_reported_to_the_caller():
    holder = _Holder()
    errors = []

    def build(key):
        raise ValueError("bad key")

    _ensure_srtp_rx_session(holder, "keyA", build, on_error=errors.append)

    assert len(errors) == 1
    assert isinstance(errors[0], ValueError)


# --- wiring into the bridge ----------------------------------------------
# The RX block sits ~1300 lines inside `_bridge_fn`, which is itself nested in
# `_open_sdes_stream_impl`; there is no seam to call it from a test. The
# source-level idiom of tests/test_key_restart_sdp.py, which guards the sibling
# restart block in the same region, is used instead.


def test_the_bridge_no_longer_latches_the_session_on_a_hasattr_probe():
    src = _source()
    assert "hasattr(_bridge_fn, '_srtp_rx_sess')" not in src
    assert "_ensure_srtp_rx_session(" in src


def test_the_bridge_builds_from_the_negotiated_key_not_the_first_answer():
    """`_cam_key_audio` is never re-parsed from the camera's real answer."""
    src = _source()
    assert "_cam_key_audio or srtp_key_audio" not in src

    keys = []
    for node in ast.walk(ast.parse(src)):
        if not isinstance(node, ast.Call):
            continue
        fn = node.func
        if isinstance(fn, ast.Name) and fn.id == "_ensure_srtp_rx_session":
            keys.append(node.args[1])

    assert len(keys) == 1
    assert isinstance(keys[0], ast.Name)
    assert keys[0].id == "srtp_key_audio"
