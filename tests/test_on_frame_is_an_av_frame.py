"""on_frame receives an av.VideoFrame, and the annotation must say so.

The DTLS path hands the caller whatever `track.recv()` returned - an aiortc/PyAV
frame - straight through (camera/protocol.py). The library's own snapshot code
proves it, calling `frame.to_ndarray(format="rgb24")`, a method the dataclass in
camera/models.py does not have.

Annotating the callback with that dataclass is worse than leaving it untyped,
because the package ships py.typed: a consumer's type checker then blesses
`frame.data` and `frame.timestamp` (which raise or read empty at runtime) and
rejects `frame.to_ndarray()` (which is what actually works). That misdirection
cost real debugging time - reading `timestamp` off a live frame returns -1 and
looks exactly like a dead stream.
"""

import ast
import os
import pathlib
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

_SRC = pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras" / "camera"


def _annotation_of(path, func_name, arg_name):
    tree = ast.parse(path.read_text())
    for node in ast.walk(tree):
        if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef)) and node.name == func_name:
            args = node.args.args + node.args.kwonlyargs
            for a in args:
                if a.arg == arg_name and a.annotation is not None:
                    return ast.unparse(a.annotation)
    return None


def test_the_models_dataclass_cannot_describe_the_frame_we_hand_over():
    """Guards the premise: models.VideoFrame has no to_ndarray, so it is wrong."""
    from aidot_cameras.camera.models import VideoFrame

    assert not hasattr(VideoFrame, "to_ndarray"), (
        "if models.VideoFrame ever grows to_ndarray, revisit this test"
    )


def test_open_webrtc_stream_does_not_annotate_on_frame_as_the_dataclass():
    path = _SRC / "webrtc_open.py"
    ann = _annotation_of(path, "_async_open_webrtc_stream_impl", "on_frame")
    assert ann is not None, "on_frame lost its annotation"
    bare = ann.replace("'", "").replace('"', "")

    # The dataclass is imported as the bare name `VideoFrame`, so an annotation
    # naming exactly that is the wrong one. An alias is fine as long as it comes
    # from av - which the next assertion checks, so the two together cannot be
    # satisfied by pointing at the dataclass under another name.
    inner = bare.split("[")[-1].split("]")[0].strip()
    assert inner != "VideoFrame", (
        f"on_frame is annotated {ann!r}, which resolves to the library dataclass"
    )

    src = path.read_text()
    assert f"from av import VideoFrame as {inner}" in src or "from av import VideoFrame" in src, (
        f"on_frame is annotated {inner!r} but that name is not imported from av"
    )
