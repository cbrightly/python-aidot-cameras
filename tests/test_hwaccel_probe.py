"""A decoder must be proven on this host, never assumed from a list.

`ffmpeg -decoders` reports what the binary was COMPILED with. On a Raspberry Pi 4
that list includes `h264_cuvid` and `hevc_cuvid` with no Nvidia hardware present,
and `h264_v4l2m2m` which fails to open ("No such file or directory") even with
the kernel device present and the user in the `video` group. Choosing from that
list would not accelerate decoding, it would stop decoding working.

So each candidate is made to decode a real clip first, and only a clean run
qualifies it. Verified on the development Pi 4: software `h264` qualified,
`h264_v4l2m2m` and `h264_cuvid` were both correctly rejected.

Probing costs about ten seconds per codec, so the verdict is cached and keyed to
the ffmpeg binary. The key comes from a stat rather than `ffmpeg -version`,
because a cache hit is consulted on a startup path and shelling out cost about
3.4 seconds per process on a loaded Pi.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.camera import hwaccel


@pytest.fixture(autouse=True)
def _isolate(tmp_path, monkeypatch):
    """Per-test cache dir and clean memo state."""
    monkeypatch.setattr(hwaccel, "_SPROP_DIR", str(tmp_path))
    monkeypatch.setattr(hwaccel, "_cache_mem", {})
    monkeypatch.setattr(hwaccel, "_identity_memo", None)
    monkeypatch.setattr(hwaccel, "_decoders_memo", None)
    monkeypatch.delenv("AIDOT_VIDEO_DECODER", raising=False)
    monkeypatch.delenv("AIDOT_DISABLE_HWACCEL", raising=False)


def test_only_a_decoder_that_actually_decodes_is_chosen(monkeypatch):
    """A candidate that is compiled in but cannot decode must be rejected."""
    monkeypatch.setattr(hwaccel, "_compiled_in", lambda name: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    # The "hardware" decoder is present in the build but fails on real input;
    # software works and is slower. The working one must still win.
    monkeypatch.setattr(
        hwaccel, "_try_decoder",
        lambda name, sample: None if name != "h264" else 1.0)
    assert hwaccel.probe_decoder("h264", force=True) == "h264"


def test_a_working_faster_decoder_wins(monkeypatch):
    monkeypatch.setattr(hwaccel, "_compiled_in", lambda name: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    times = {"h264_v4l2m2m": 0.2, "h264": 1.0}
    monkeypatch.setattr(
        hwaccel, "_try_decoder", lambda name, sample: times.get(name))
    assert hwaccel.probe_decoder("h264", force=True) == "h264_v4l2m2m"


def test_verdict_is_cached_and_not_reprobed(monkeypatch):
    monkeypatch.setattr(hwaccel, "_compiled_in", lambda name: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    calls = []

    def _try(name, sample):
        calls.append(name)
        return 1.0 if name == "h264" else None

    monkeypatch.setattr(hwaccel, "_try_decoder", _try)
    assert hwaccel.probe_decoder("h264") == "h264"
    n = len(calls)
    assert n > 0
    # Second and third calls must not probe again.
    hwaccel.probe_decoder("h264")
    monkeypatch.setattr(hwaccel, "_cache_mem", {})   # drop the memo, keep disk
    hwaccel.probe_decoder("h264")
    assert len(calls) == n


def test_upgrading_ffmpeg_invalidates_the_verdict(monkeypatch):
    """The key is tied to the binary, so a different ffmpeg re-probes."""
    monkeypatch.setattr(hwaccel, "_compiled_in", lambda name: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda name, sample: 1.0 if name == "h264" else None)
    monkeypatch.setattr(hwaccel, "_ffmpeg_identity", lambda: "build-A")
    assert hwaccel.probe_decoder("h264") == "h264"

    seen = []
    monkeypatch.setattr(hwaccel, "_cache_mem", {})
    monkeypatch.setattr(hwaccel, "_ffmpeg_identity", lambda: "build-B")
    monkeypatch.setattr(
        hwaccel, "_try_decoder",
        lambda name, sample: (seen.append(name), 1.0 if name == "h264" else None)[1])
    assert hwaccel.probe_decoder("h264") == "h264"
    assert seen, "a different ffmpeg build must be re-probed, not trusted"


def test_disable_switch_stays_on_software(monkeypatch):
    monkeypatch.setenv("AIDOT_DISABLE_HWACCEL", "1")
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda name, sample: pytest.fail("must not probe"))
    assert hwaccel.probe_decoder("h264") == "h264"


def test_explicit_override_is_honoured(monkeypatch):
    monkeypatch.setenv("AIDOT_VIDEO_DECODER", "h264_something")
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda name, sample: pytest.fail("must not probe"))
    assert hwaccel.probe_decoder("h264") == "h264_something"


def test_unknown_codec_passes_through(monkeypatch):
    assert hwaccel.probe_decoder("vp9") == "vp9"


def test_no_sample_falls_back_to_software(monkeypatch):
    """If the probe clip cannot even be built, do not guess - use software."""
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: False)
    assert hwaccel.probe_decoder("h264", force=True) == "h264"


def test_decoder_args_shape():
    assert hwaccel.decoder_args("h264")[:1] == ["-c:v"]


# --------------------------------------------------------------------------- #
# wiring into the ffmpeg argv
# --------------------------------------------------------------------------- #
from aidot_cameras.camera.client import _build_sdes_serve_cmd  # noqa: E402


def _before_input(argv):
    return argv[: argv.index("-i")]


def test_decoder_goes_before_the_input():
    """After ``-i`` ffmpeg reads ``-c:v`` as an ENCODER, which would re-encode
    the drain instead of choosing how to decode it."""
    argv = _build_sdes_serve_cmd(sdp_path="/tmp/x.sdp", video_decoder="h264_v4l2m2m")
    head = _before_input(argv)
    assert "-c:v" in head
    assert head[head.index("-c:v") + 1] == "h264_v4l2m2m"


def test_drain_still_decodes():
    """Do not 'optimise' the drain to -c copy. Decoding is exactly what catches a
    parameter-set mismatch; a copy-only check demuxes happily while every frame
    is undecodable, which is how a camera looked healthy while showing black."""
    argv = _build_sdes_serve_cmd(sdp_path="/tmp/x.sdp", video_decoder="h264")
    assert argv[-3:] == ["-f", "null", "/dev/null"]
    assert "copy" not in argv


@pytest.mark.parametrize(
    ("kwargs", "why"),
    [
        ({"rtsp_push_url": "rtsp://h/s"}, "push is -c copy, nothing decodes"),
        ({"rtsp_push_url": "http://127.0.0.1:1/x.ts"}, "serve is -c:v copy"),
        ({"output_path": "/tmp/o.mp4"}, "recording is -c copy"),
    ],
)
def test_copy_destinations_never_get_a_decoder(kwargs, why, monkeypatch):
    monkeypatch.setenv("AIDOT_ALLOW_LAN_SERVE", "1")
    argv = _build_sdes_serve_cmd(
        sdp_path="/tmp/x.sdp", video_decoder="h264_v4l2m2m", **kwargs)
    assert "-c:v" not in _before_input(argv), why


def test_no_verdict_leaves_ffmpeg_to_choose():
    """A cold cache must not change behaviour - the caller passes None and the
    argv is exactly what it always was."""
    assert _build_sdes_serve_cmd(sdp_path="/tmp/x.sdp") == _build_sdes_serve_cmd(
        sdp_path="/tmp/x.sdp", video_decoder=None)


def test_cached_decoder_never_probes(monkeypatch):
    """It runs on the event loop, so it must not shell out."""
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda name, sample: pytest.fail("must not probe"))
    monkeypatch.setattr(hwaccel, "_make_sample",
                        lambda codec, path: pytest.fail("must not probe"))
    assert hwaccel.cached_decoder("h264") is None  # empty cache, no probing
