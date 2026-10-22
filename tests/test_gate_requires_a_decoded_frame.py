"""A camera passes the gate only if a frame came out of a decoder.

The older signals cannot tell "video a viewer could watch" from "bytes of the
right shape": `media_stats.packets` counts what the bridge forwarded, and
`recorded_bytes` measures a file written by a `-c copy` pipeline that never
looks inside a packet. That is exactly the defect where undecryptable packets
counted as delivered media and a black stream reported healthy indefinitely.

Promoted from advisory on 2026-08-09 against 19 recorded attempts across three
fleet runs: every PASS had decoded frames (46-262), every NO_MEDIA had zero. So
gating changes no historical verdict - it closes a hole rather than moving a bar.

The case that matters most here is the probe that could not RUN. If ffmpeg or
ffprobe is missing on the runner, every camera would fail at once and the gate
would be reporting on its own environment rather than the fleet. That must not
be a failure, which is why the probe reports `decode_error` separately from
`decoded_frames = 0` in the first place.
"""

import importlib.util
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest  # noqa: E402

_SPEC = importlib.util.spec_from_file_location(
    "lv_gate",
    os.path.join(
        os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
        "scripts",
        "live_validate.py",
    ),
)
lv = importlib.util.module_from_spec(_SPEC)
_SPEC.loader.exec_module(lv)


def test_frames_out_of_the_decoder_pass():
    assert lv._passes({"decoded_frames": 231}, media_ok=True) is True


def test_zero_decoded_frames_fail_even_when_the_counters_are_happy():
    """The whole point: packets and bytes can both be satisfied by ciphertext."""
    result = {
        "decoded_frames": 0,
        "media_stats": {"packets": 4033},
        "recorded_bytes": 3_500_000,
    }
    assert lv._passes(result, media_ok=True) is False


def test_a_probe_that_could_not_run_does_not_fail_the_camera():
    """Otherwise a runner without ffmpeg fails the whole fleet at once."""
    for reason in (
        "ffmpeg not found",
        "ffprobe not found",
        "no recording",
        "probe exceeded 60s",
    ):
        assert lv._passes({"decode_error": reason}, media_ok=True) is True, reason


def test_a_probe_that_could_not_run_is_not_confused_with_zero_frames():
    """They mean opposite things and the gate must not collapse them."""
    could_not_run = {"decode_error": "ffprobe not found", "decoded_frames": 0}
    really_zero = {"decoded_frames": 0}
    assert lv._passes(could_not_run, media_ok=True) is True
    assert lv._passes(really_zero, media_ok=True) is False


def test_no_decode_data_at_all_keeps_the_old_behaviour():
    """A report from before the probe existed must still be readable."""
    assert lv._passes({"media_stats": {"packets": 900}}, media_ok=True) is True


@pytest.mark.parametrize(
    "result",
    [
        {"decoded_frames": 231},
        {"decode_error": "ffmpeg not found"},
        {},
    ],
)
def test_no_media_still_fails_whatever_the_probe_says(result):
    """The decode check ADDS a condition; it never rescues a medialess attempt."""
    assert lv._passes(result, media_ok=False) is False


@pytest.mark.parametrize("decoded, verdict", [(25, "PASS"), (0, "NO_MEDIA")])
async def test_the_verdict_site_uses_the_gate(tmp_path, monkeypatch, decoded, verdict):
    """A gate nothing calls is not a gate: an attempt whose session streamed
    and recorded is judged by what came out of the decoder."""
    from test_live_validate_policy import _gate

    dc, _events, _made = _gate(lv, monkeypatch)

    async def _probe(path, timeout=60.0):
        return {"decoded_frames": decoded, "decode_errors": 0}

    monkeypatch.setattr(lv, "_decode_probe", _probe)
    result = await lv._attempt(dc, 0, str(tmp_path), 1)
    assert result["recorded_bytes"] > 0, "the attempt streamed and recorded"
    assert result["verdict"] == verdict, result
