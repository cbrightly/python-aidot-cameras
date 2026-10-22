"""Every error the open path raises is an AidotError, and stays a RuntimeError.

The DTLS and SDES opens used to raise bare ``RuntimeError`` for an MQTT
failure, a livePlay refusal, a missing webrtcResp, a failed negotiation and a
missing ffmpeg, so ``except AidotError`` could not catch them. They now raise
AidotError subclasses that ALSO subclass RuntimeError, so ``except
RuntimeError`` written against an earlier release still catches them.
``CameraLanError`` and ``RtspPublishError`` sat outside AidotError too; they
are AidotError subclasses now and keep their old bases and import paths.
"""

import ast
import inspect

import pytest

import aidot_cameras
from aidot_cameras import exceptions as exc

OPEN_ERRORS = [
    exc.AidotStreamOpenError,
    exc.AidotMqttUnavailable,
    exc.AidotPlaintextMqttRefused,
    exc.AidotLivePlayRefused,
    exc.AidotNoWebrtcAnswer,
    exc.AidotNegotiationFailed,
    exc.AidotFfmpegMissing,
]


@pytest.mark.parametrize("cls", OPEN_ERRORS, ids=lambda c: c.__name__)
def test_open_errors_are_both(cls):
    assert issubclass(cls, exc.AidotError)
    assert issubclass(cls, RuntimeError)
    assert issubclass(cls, exc.AidotStreamOpenError)


def test_lan_and_publish_errors_joined_the_hierarchy():
    from aidot_cameras.camera import lan_control, rtsp_publish

    assert lan_control.CameraLanError is exc.CameraLanError
    assert lan_control.CameraLanLoginRejected is exc.CameraLanLoginRejected
    assert rtsp_publish.RtspPublishError is exc.RtspPublishError
    assert issubclass(exc.CameraLanError, exc.AidotError)
    assert issubclass(exc.CameraLanLoginRejected, exc.CameraLanError)
    assert issubclass(exc.RtspPublishError, exc.AidotError)
    assert issubclass(exc.RtspPublishError, RuntimeError)
    e = exc.CameraLanLoginRejected("refused", ack=4352)
    assert e.ack == 4352 and str(e) == "refused"


def test_the_unknown_region_error_is_a_value_error():
    assert issubclass(exc.AidotUnknownRegion, exc.AidotError)
    assert issubclass(exc.AidotUnknownRegion, ValueError)


@pytest.mark.parametrize(
    "name",
    [
        "AidotStreamOpenError",
        "AidotMqttUnavailable",
        "AidotPlaintextMqttRefused",
        "AidotLivePlayRefused",
        "AidotNoWebrtcAnswer",
        "AidotNegotiationFailed",
        "AidotFfmpegMissing",
        "AidotUnknownRegion",
        "CameraLanError",
        "CameraLanLoginRejected",
        "RtspPublishError",
    ],
)
def test_exported_from_the_root(name):
    assert name in aidot_cameras.__all__
    assert getattr(aidot_cameras, name) is getattr(exc, name)


@pytest.mark.parametrize("modname", ["webrtc_open", "sdes_open"])
def test_no_bare_runtime_error_is_raised_on_the_open_path(modname):
    import importlib

    mod = importlib.import_module(f"aidot_cameras.camera.{modname}")
    tree = ast.parse(inspect.getsource(mod))
    bare = [
        node.lineno
        for node in ast.walk(tree)
        if isinstance(node, ast.Raise)
        and node.exc is not None
        and (
            (
                isinstance(node.exc, ast.Call)
                and getattr(node.exc.func, "id", "") == "RuntimeError"
            )
            or getattr(node.exc, "id", "") == "RuntimeError"
        )
    ]
    assert not bare, f"{modname} raises a bare RuntimeError at lines {bare}"


def test_api_stability_lists_every_exported_exception():
    import os

    path = os.path.join(
        os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
        "docs",
        "API-STABILITY.md",
    )
    with open(path, encoding="utf-8") as fh:
        doc = fh.read()
    missing = [
        n
        for n in aidot_cameras.__all__
        if inspect.isclass(getattr(aidot_cameras, n))
        and issubclass(getattr(aidot_cameras, n), BaseException)
        and f"`{n}`" not in doc
    ]
    assert not missing, f"not listed in docs/API-STABILITY.md: {missing}"
