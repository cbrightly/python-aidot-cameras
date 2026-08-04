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
    monkeypatch.setattr(hwaccel, "_hwaccels_memo", None)
    monkeypatch.delenv("AIDOT_VIDEO_DECODER", raising=False)
    monkeypatch.delenv("AIDOT_DISABLE_HWACCEL", raising=False)


def test_only_a_candidate_that_actually_decodes_is_chosen(monkeypatch):
    """A candidate the build advertises but cannot run must be rejected."""
    monkeypatch.setattr(hwaccel, "_available", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    # Everything hardware fails on real input; software works and is slower.
    monkeypatch.setattr(
        hwaccel, "_try_decoder",
        lambda cand, sample: 1.0 if not cand else None)
    assert hwaccel.probe_decoder("h264", force=True) == []


def test_a_working_faster_decoder_wins(monkeypatch):
    monkeypatch.setattr(hwaccel, "_available", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    times = {("-c:v", "h264_v4l2m2m"): 0.2, (): 1.0}
    monkeypatch.setattr(
        hwaccel, "_try_decoder", lambda cand, sample: times.get(tuple(cand)))
    assert hwaccel.probe_decoder("h264", force=True) == ["-c:v", "h264_v4l2m2m"]


def test_an_hwaccel_method_can_win(monkeypatch):
    """VideoToolbox and VAAPI expose NO decoder - ffmpeg lists them as
    encoders - so they are only reachable as -hwaccel. If this form could not
    win, every macOS and VAAPI machine would silently stay on software while
    having working hardware decoding available."""
    monkeypatch.setattr(hwaccel, "_available", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    times = {("-hwaccel", "videotoolbox"): 0.1, (): 1.0}
    monkeypatch.setattr(
        hwaccel, "_try_decoder", lambda cand, sample: times.get(tuple(cand)))
    assert hwaccel.probe_decoder("h264", force=True) == ["-hwaccel", "videotoolbox"]


def test_hwaccel_methods_are_looked_up_in_the_right_list(monkeypatch):
    """-hwaccel names never appear in -decoders, and vice versa. Checking one
    against the other rejects every valid candidate."""
    monkeypatch.setattr(hwaccel, "_list_names",
                        lambda flag: {"h264_v4l2m2m"} if flag == "-decoders"
                        else {"videotoolbox"})
    assert hwaccel._available(["-hwaccel", "videotoolbox"]) is True
    assert hwaccel._available(["-hwaccel", "h264_v4l2m2m"]) is False
    assert hwaccel._available(["-c:v", "h264_v4l2m2m"]) is True
    assert hwaccel._available(["-c:v", "videotoolbox"]) is False
    assert hwaccel._available([]) is True  # software always available


def test_verdict_is_cached_and_not_reprobed(monkeypatch):
    monkeypatch.setattr(hwaccel, "_available", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    calls = []

    def _try(cand, sample):
        calls.append(tuple(cand))
        return 1.0 if not cand else None

    monkeypatch.setattr(hwaccel, "_try_decoder", _try)
    assert hwaccel.probe_decoder("h264") == []
    n = len(calls)
    assert n > 0
    # Second and third calls must not probe again.
    hwaccel.probe_decoder("h264")
    monkeypatch.setattr(hwaccel, "_cache_mem", {})   # drop the memo, keep disk
    hwaccel.probe_decoder("h264")
    assert len(calls) == n


def test_upgrading_ffmpeg_invalidates_the_verdict(monkeypatch):
    """The key is tied to the binary, so a different ffmpeg re-probes."""
    monkeypatch.setattr(hwaccel, "_available", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda cand, sample: 1.0 if not cand else None)
    monkeypatch.setattr(hwaccel, "_ffmpeg_identity", lambda: "build-A")
    assert hwaccel.probe_decoder("h264") == []

    seen = []
    monkeypatch.setattr(hwaccel, "_cache_mem", {})
    monkeypatch.setattr(hwaccel, "_ffmpeg_identity", lambda: "build-B")
    monkeypatch.setattr(
        hwaccel, "_try_decoder",
        lambda cand, sample: (seen.append(tuple(cand)), 1.0 if not cand else None)[1])
    assert hwaccel.probe_decoder("h264") == []
    assert seen, "a different ffmpeg build must be re-probed, not trusted"


def test_disable_switch_stays_on_software(monkeypatch):
    monkeypatch.setenv("AIDOT_DISABLE_HWACCEL", "1")
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda cand, sample: pytest.fail("must not probe"))
    assert hwaccel.probe_decoder("h264") == []


def test_explicit_override_is_honoured(monkeypatch):
    monkeypatch.setenv("AIDOT_VIDEO_DECODER", "h264_something")
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda cand, sample: pytest.fail("must not probe"))
    assert hwaccel.probe_decoder("h264") == ["-c:v", "h264_something"]


def test_override_can_name_an_hwaccel_method(monkeypatch):
    monkeypatch.setenv("AIDOT_VIDEO_DECODER", "hwaccel:vaapi")
    assert hwaccel.probe_decoder("h264") == ["-hwaccel", "vaapi"]
    assert hwaccel.cached_decoder("h264") == ["-hwaccel", "vaapi"]


def test_unknown_codec_passes_through(monkeypatch):
    assert hwaccel.probe_decoder("vp9") == []


def test_no_sample_falls_back_to_software(monkeypatch):
    """If the probe clip cannot even be built, do not guess - use software."""
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: False)
    assert hwaccel.probe_decoder("h264", force=True) == []


def test_software_verdict_is_not_confused_with_unprobed(monkeypatch):
    """[] means "software, proven"; None means "not probed yet". Collapsing the
    two would re-probe forever on any host with no hardware decoding."""
    monkeypatch.setattr(hwaccel, "_available", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda cand, sample: 1.0 if not cand else None)
    assert hwaccel.cached_decoder("h264") is None      # nothing probed yet
    assert hwaccel.probe_decoder("h264") == []         # proven software
    assert hwaccel.cached_decoder("h264") == []        # remembered, not None


def test_a_stale_schema_entry_is_ignored(monkeypatch):
    """An older cache wrote a bare decoder NAME where a list now belongs.
    Reading that back as a verdict would put a string into the ffmpeg argv."""
    monkeypatch.setattr(hwaccel, "_ffmpeg_identity", lambda: "b")
    hwaccel._save_cache({"h264:b": "h264_v4l2m2m"})    # v1-shaped entry
    assert hwaccel.cached_decoder("h264") is None


# --------------------------------------------------------------------------- #
# wiring into the ffmpeg argv
# --------------------------------------------------------------------------- #
from aidot_cameras.camera.client import _build_sdes_serve_cmd  # noqa: E402


def _before_input(argv):
    return argv[: argv.index("-i")]


@pytest.mark.parametrize(
    "dec", [["-c:v", "h264_v4l2m2m"], ["-hwaccel", "videotoolbox"]])
def test_decoder_goes_before_the_input(dec):
    """Both forms are INPUT options. After ``-i`` ffmpeg reads ``-c:v`` as an
    ENCODER, and ``-hwaccel`` has no meaning there at all."""
    argv = _build_sdes_serve_cmd(sdp_path="/tmp/x.sdp", video_decoder=dec)
    head = _before_input(argv)
    assert dec[0] in head
    assert head[head.index(dec[0]) + 1] == dec[1]


def test_drain_still_decodes():
    """Do not 'optimise' the drain to -c copy. Decoding is exactly what catches a
    parameter-set mismatch; a copy-only check demuxes happily while every frame
    is undecodable, which is how a camera looked healthy while showing black."""
    argv = _build_sdes_serve_cmd(sdp_path="/tmp/x.sdp", video_decoder=[])
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
        sdp_path="/tmp/x.sdp", video_decoder=["-hwaccel", "videotoolbox"], **kwargs)
    assert "-hwaccel" not in _before_input(argv), why


def test_no_verdict_leaves_ffmpeg_to_choose():
    """A cold cache must not change behaviour - the caller passes None and the
    argv is exactly what it always was."""
    assert _build_sdes_serve_cmd(sdp_path="/tmp/x.sdp") == _build_sdes_serve_cmd(
        sdp_path="/tmp/x.sdp", video_decoder=None)


def test_cached_decoder_never_probes(monkeypatch):
    """It runs on the event loop, so it must not shell out."""
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda cand, sample: pytest.fail("must not probe"))
    monkeypatch.setattr(hwaccel, "_make_sample",
                        lambda codec, path: pytest.fail("must not probe"))
    assert hwaccel.cached_decoder("h264") is None  # empty cache, no probing


# --------------------------------------------------------------------------- #
# cost control - what this module is allowed to spend on a small board
# --------------------------------------------------------------------------- #
def test_no_possible_hardware_spawns_nothing(monkeypatch):
    """A machine with no video hardware - a VPS, a container, most cloud
    installs - must be answered for free. Encoding a sample it would never
    decode was the single largest cost here: 14s of wall clock on a Pi 4."""
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: not cand)
    monkeypatch.setattr(hwaccel, "_make_sample",
                        lambda codec, path: pytest.fail("must not encode"))
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda cand, sample: pytest.fail("must not spawn ffmpeg"))
    assert hwaccel.probe_decoder("h264") == []
    assert hwaccel.cached_decoder("h264") == []      # and it is remembered


def test_software_is_not_timed_when_no_hardware_qualifies(monkeypatch):
    """When every hardware candidate fails - the common case on small boards -
    software wins by default, so timing it changes nothing and costs a spawn."""
    monkeypatch.setattr(hwaccel, "_available", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    tried = []

    def _try(cand, sample):
        tried.append(tuple(cand))
        return                           # nothing decodes here

    monkeypatch.setattr(hwaccel, "_try_decoder", _try)
    assert hwaccel.probe_decoder("h264") == []
    assert () not in tried, "software must not be timed when nothing beat it"


def test_software_is_timed_when_hardware_qualifies(monkeypatch):
    """If a hardware candidate did qualify, software must still be measured -
    hardware is not always faster. On an Apple M1 VideoToolbox decodes H.264
    about three times slower than software."""
    monkeypatch.setattr(hwaccel, "_available", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_plausible", lambda cand: True)
    monkeypatch.setattr(hwaccel, "_make_sample", lambda codec, path: True)
    times = {("-hwaccel", "videotoolbox"): 0.9, (): 0.3}
    monkeypatch.setattr(hwaccel, "_try_decoder",
                        lambda cand, sample: times.get(tuple(cand)))
    assert hwaccel.probe_decoder("h264") == [], "slower hardware must not win"


def test_impossible_hardware_is_ruled_out_without_spawning(monkeypatch):
    """Only the impossible is ruled out, never the merely unlikely."""
    monkeypatch.setattr(hwaccel.glob, "glob", lambda pat: [])
    assert hwaccel._plausible(["-c:v", "h264_cuvid"]) is False   # no /dev/nvidia*
    assert hwaccel._plausible(["-hwaccel", "vaapi"]) is False    # no /dev/dri
    assert hwaccel._plausible([]) is True                        # software
    monkeypatch.setattr(hwaccel.glob, "glob", lambda pat: ["/dev/video10"])
    assert hwaccel._plausible(["-c:v", "h264_v4l2m2m"]) is True   # device present


def test_only_h264_is_warmed_by_default():
    """H.265 is not ingested, and probing it doubled the cost of this module."""
    import inspect
    default = inspect.signature(hwaccel.warm_decoder_cache).parameters["codecs"].default
    assert default == ("h264",)
