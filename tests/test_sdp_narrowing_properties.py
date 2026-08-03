"""Payload-type narrowing must hold for ANY payload list, not the fixtures.

tests/test_sdp_payload_type_narrowing.py pins the specific shapes seen in the
field. This file asserts the same invariants across generated m-lines of every
length and ordering, because the original bug was exactly a fixture-shaped
blind spot: the implementation replaced the literal ``" 96 97"``, so it kept
passing its two-payload tests and silently stopped narrowing the day a camera
offered a third video payload.

Invariants, for every generated SDP:
  1. the kept payload is the ONLY one on its m-line
  2. no rtpmap/fmtp line survives for a dropped payload
  3. the other media line is untouched
  4. narrowing is idempotent
"""
import itertools
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.sdes_open import narrow_sdp_payload_types

VIDEO_PTS = ["96", "97", "98"]
AUDIO_PTS = ["0", "8"]


def _build_sdp(video_pts, audio_pts) -> str:
    lines = [
        "v=0",
        "o=- 0 0 IN IP4 127.0.0.1",
        "s=aidot",
        "c=IN IP4 127.0.0.1",
        "t=0 0",
        f"m=video 5004 RTP/AVP {' '.join(video_pts)}",
    ]
    names = {"96": "H264/90000", "97": "H265/90000", "98": "H265/90000"}
    for pt in video_pts:
        lines.append(f"a=rtpmap:{pt} {names[pt]}")
        lines.append(f"a=fmtp:{pt} packetization-mode=1")
    lines.append(f"m=audio 5006 RTP/AVP {' '.join(audio_pts)}")
    anames = {"0": "PCMU/8000", "8": "PCMA/8000"}
    for pt in audio_pts:
        lines.append(f"a=rtpmap:{pt} {anames[pt]}")
    return "\r\n".join(lines) + "\r\n"


def _m_line(sdp: str, kind: str) -> str:
    return next(ln for ln in sdp.splitlines() if ln.startswith(f"m={kind}"))


def _listed_pts(sdp: str, kind: str) -> list:
    return _m_line(sdp, kind).partition(" RTP/")[2].split()[1:]


def _attr_pts(sdp: str, attr: str) -> set:
    out = set()
    for ln in sdp.splitlines():
        if ln.startswith(f"a={attr}:"):
            out.add(ln.split(":", 1)[1].replace(";", " ").split()[0])
    return out


def _video_orderings():
    """Every non-empty ordering of 1..3 video payload types."""
    for n in (1, 2, 3):
        yield from itertools.permutations(VIDEO_PTS, n)


def test_kept_video_payload_is_the_only_one_listed():
    for video in _video_orderings():
        sdp = _build_sdp(list(video), AUDIO_PTS)
        for keep in video:
            out = narrow_sdp_payload_types(sdp, keep_video=int(keep))
            assert _listed_pts(out, "video") == [keep], (
                f"m=video should list only {keep}; "
                f"got {_listed_pts(out, 'video')} from {list(video)}"
            )


def test_dropped_video_payloads_lose_their_rtpmap_and_fmtp():
    for video in _video_orderings():
        sdp = _build_sdp(list(video), AUDIO_PTS)
        for keep in video:
            out = narrow_sdp_payload_types(sdp, keep_video=int(keep))
            dropped = {p for p in video if p != keep}
            # Audio pts legitimately remain, so only check the video ones.
            surviving = (_attr_pts(out, "rtpmap") | _attr_pts(out, "fmtp")) & set(VIDEO_PTS)
            assert not (surviving & dropped), (
                f"rtpmap/fmtp survived for dropped {surviving & dropped} "
                f"(kept {keep} from {list(video)})"
            )
            assert keep in surviving, f"kept payload {keep} lost its rtpmap"


def test_narrowing_one_line_leaves_the_other_untouched():
    for video in _video_orderings():
        for audio in itertools.permutations(AUDIO_PTS, 2):
            sdp = _build_sdp(list(video), list(audio))
            v_only = narrow_sdp_payload_types(sdp, keep_video=int(video[0]))
            assert _listed_pts(v_only, "audio") == list(audio), (
                "narrowing video must not touch the audio line"
            )
            a_only = narrow_sdp_payload_types(sdp, keep_audio=int(audio[0]))
            assert _listed_pts(a_only, "video") == list(video), (
                "narrowing audio must not touch the video line"
            )


def test_narrowing_is_idempotent():
    for video in _video_orderings():
        sdp = _build_sdp(list(video), AUDIO_PTS)
        once = narrow_sdp_payload_types(sdp, keep_video=int(video[0]), keep_audio=8)
        twice = narrow_sdp_payload_types(once, keep_video=int(video[0]), keep_audio=8)
        assert once == twice, "narrowing an already-narrowed SDP must be a no-op"


def test_both_lines_narrow_together():
    for video in _video_orderings():
        for audio in itertools.permutations(AUDIO_PTS, 2):
            sdp = _build_sdp(list(video), list(audio))
            out = narrow_sdp_payload_types(
                sdp, keep_video=int(video[-1]), keep_audio=int(audio[-1])
            )
            assert _listed_pts(out, "video") == [video[-1]]
            assert _listed_pts(out, "audio") == [audio[-1]]


def test_a_payload_the_camera_did_not_offer_leaves_the_sdp_alone():
    """Never silently produce an m-line advertising a codec that wasn't offered."""
    sdp = _build_sdp(["96", "97"], AUDIO_PTS)
    out = narrow_sdp_payload_types(sdp, keep_video=99)
    assert _listed_pts(out, "video") == ["96", "97"]
