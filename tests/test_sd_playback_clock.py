"""SD playback puts a recording on its own clock.

An A001064 night recording made at 5 fps went out stamped and paced at the
live 20 fps (2026-10-29): 60 s of video in 15 s, its sound sent in real time
and cut off when the video ran out. Every packet carries the frame's time in
the recording in an RTP header extension once the offer names transport-cc
and rtp-stream-id, as the vendor app's does; the library offers both, keeps
them through the wPayload filter, reads the time off each packet and moves
the written file onto it.
"""

import os
import shutil
import subprocess

import pytest

from aidot_cameras.camera.sd_playback import (
    EXT_FRAME_TIME,
    EXT_FRAME_TIME_ID,
    EXT_TRANSPORT_CC,
    EXT_TRANSPORT_CC_ID,
    FRAME_TIME_END,
    PlaybackClock,
    frame_time_ms,
    offer_with_frame_times,
    retime_argv,
    rtp_extension,
)
from aidot_cameras.camera.sdes_open import _MediaHold, compress_sdp_req

T0 = 1_792_988_619_319  # 2026-10-26T04:23:39.319Z, a played night clip


def _value(ms, channel=0x14D4):
    return channel.to_bytes(2, "big") + b"\xf0" + ms.to_bytes(8, "big")


def _rtp(pt, seq, ts, ms=None, *, two_byte=False):
    hdr = bytes([0x80, pt]) + seq.to_bytes(2, "big") + ts.to_bytes(4, "big")
    hdr += (0x1259).to_bytes(4, "big")
    if ms is None:
        return hdr + b"payload"
    if two_byte:
        ext = bytes([EXT_TRANSPORT_CC_ID, 2]) + seq.to_bytes(2, "big")
        ext += bytes([EXT_FRAME_TIME_ID, 11]) + _value(ms)
        profile = b"\x10\x00"
    else:
        ext = bytes([(EXT_TRANSPORT_CC_ID << 4) | 1]) + seq.to_bytes(2, "big")
        ext += bytes([(EXT_FRAME_TIME_ID << 4) | 10]) + _value(ms)
        profile = b"\xbe\xde"
    ext += b"\x00" * (-len(ext) % 4)
    hdr = bytes([0x90]) + hdr[1:]
    return hdr + profile + (len(ext) // 4).to_bytes(2, "big") + ext + b"payload"


def test_the_frame_time_is_read_in_either_extension_form():
    for two in (False, True):
        pkt = _rtp(97, 7, 1000, T0, two_byte=two)
        assert frame_time_ms(rtp_extension(pkt, EXT_FRAME_TIME_ID)) == T0
        assert rtp_extension(pkt, EXT_TRANSPORT_CC_ID) == (7).to_bytes(2, "big")
    assert rtp_extension(_rtp(97, 7, 1000), EXT_FRAME_TIME_ID) is None
    assert frame_time_ms(None) is None
    assert frame_time_ms(b"short") is None


def test_a_night_clip_is_stretched_to_the_recording_and_its_sound_placed():
    # Measured: 200 ms of recording per frame, 50 ms of RTP (4500 ticks);
    # the sound starts 6 s into the clip and runs in real time.
    clock = PlaybackClock()
    for i in range(301):
        clock.observe(_rtp(97, i, 1000 + i * 4500, T0 + i * 200))
    for i in range(200):
        clock.observe(_rtp(8, i, 50 + i * 512, T0 + 6002 + i * 64))
    timing = clock.timing()
    assert timing["video"] == (pytest.approx(4.0), 0.0)
    assert timing["audio"] == (pytest.approx(1.0), pytest.approx(6.002))
    assert not clock.ended
    assert clock.observe(_rtp(97, 999, 0, FRAME_TIME_END)) is True
    assert clock.ended


def test_no_frame_times_means_nothing_to_correct():
    clock = PlaybackClock()
    clock.observe(_rtp(97, 1, 1000))
    assert clock.timing() == {}


def test_the_playback_offer_names_both_extensions_in_each_media_section():
    sdp = (
        "v=0\r\nm=audio 9 RTP/SAVPF 8\r\na=mid:0\r\na=rtpmap:8 PCMA/8000\r\n"
        "m=video 9 RTP/SAVPF 96 97\r\na=mid:1\r\n"
        "m=application 9 SCTP webrtc-datachannel\r\na=mid:2\r\n"
    )
    out = offer_with_frame_times(sdp)
    for mid in ("a=mid:0", "a=mid:1"):
        after = out.split(mid + "\r\n")[1]
        assert after.startswith(
            f"a=extmap:{EXT_TRANSPORT_CC_ID} {EXT_TRANSPORT_CC}\r\n"
            f"a=extmap:{EXT_FRAME_TIME_ID} {EXT_FRAME_TIME}\r\n"
        )
    assert "a=rtcp-fb:97 transport-cc" in out
    assert out.split("a=mid:2")[1].count("extmap") == 0


def test_the_wpayload_filter_keeps_the_two_extmaps_once_as_the_app_does():
    sdp = offer_with_frame_times(
        "v=0\r\ns=-\r\nm=audio 9 RTP/SAVPF 8\r\na=mid:0\r\na=rtpmap:8 PCMA/8000\r\n"
        "a=extmap:9 urn:ietf:params:rtp-hdrext:ssrc-audio-level\r\n"
        "m=video 9 RTP/SAVPF 97\r\na=mid:1\r\na=rtpmap:97 H265/90000\r\n"
    )
    out = compress_sdp_req("dev", sdp)
    assert out.count(EXT_TRANSPORT_CC) == 1 and out.count(EXT_FRAME_TIME) == 1
    assert "ssrc-audio-level" not in out
    # A live offer names neither, and comes out as it always did.
    assert "extmap" not in compress_sdp_req("dev", sdp.replace("a=extmap", "a=x"))


class _Sock:
    def __init__(self):
        self.sent = []

    def sendto(self, pkt, addr):
        self.sent.append(pkt)


def test_the_hold_reads_the_clock_and_forwards_nothing_after_the_end():
    sock, lines = _Sock(), []
    hold = _MediaHold()
    hold.release()
    hold.send(sock, _rtp(97, 1, 1000, T0), 5000, log=lines.append)
    hold.send(sock, _rtp(97, 2, 5500, T0 + 200), 5000, log=lines.append)
    for seq in (3, 4):
        hold.send(sock, _rtp(97, seq, 0, FRAME_TIME_END), 5000, log=lines.append)
    assert len(sock.sent) == 2
    assert hold.clock.ended
    assert lines.count("bridge: the camera marked the end of the recording") == 1


def test_a_playback_serve_keeps_the_camera_s_timestamps():
    from aidot_cameras.camera import client

    def argv(**kw):
        return client._build_sdes_serve_cmd(
            sdp_path="in.sdp", output_path="out.ts", **kw
        )

    if not client._SERVE_ARRIVAL_TS:
        pytest.skip("arrival stamping is off in this environment")
    assert "-use_wallclock_as_timestamps" in argv()
    assert "-use_wallclock_as_timestamps" not in argv(keep_probe_packets=True)


@pytest.mark.skipif(shutil.which("ffmpeg") is None, reason="needs ffmpeg")
def test_the_retime_command_stretches_video_and_places_the_data_stream(tmp_path):
    src, dst = tmp_path / "in.ts", tmp_path / "out.ts"
    subprocess.run(
        [
            "ffmpeg",
            "-loglevel",
            "error",
            "-y",
            "-f",
            "lavfi",
            "-i",
            "testsrc=size=64x64:rate=20:duration=3",
            "-f",
            "lavfi",
            "-i",
            "sine=frequency=440:sample_rate=8000:duration=3",
            "-c:v",
            "libx264",
            "-c:a",
            "pcm_alaw",
            "-f",
            "mpegts",
            str(src),
        ],
        check=True,
    )
    timing = {"video": (4.0, 0.0), "audio": (1.0, 2.0)}
    subprocess.run(retime_argv("ffmpeg", str(src), str(dst), timing), check=True)
    out = subprocess.run(
        [
            "ffprobe",
            "-v",
            "error",
            "-show_entries",
            "stream=codec_type,start_time,duration",
            "-of",
            "csv=p=0",
            str(dst),
        ],
        capture_output=True,
        text=True,
        check=True,
    ).stdout.split()
    rows = {r.split(",")[0]: [float(x) for x in r.split(",")[1:]] for r in out}
    assert rows["video"][1] == pytest.approx(12.0, abs=0.3)  # 3 s x4
    # MPEG-TS has no type for A-law: like an SDES capture, it is a data stream.
    sound = rows.get("audio") or rows["data"]
    assert sound[0] - rows["video"][0] == pytest.approx(2.0, abs=0.1)
    assert os.path.getsize(dst) > 0


def test_a_stray_second_video_type_does_not_decide_the_timing():
    clock = PlaybackClock()
    for i in range(50):
        clock.observe(_rtp(97, i, 1000 + i * 4500, T0 + i * 200))
    for i in range(3):  # a few packets of another type, its own pace
        clock.observe(_rtp(96, i, 5000 + i * 90000, T0 + i * 1000))
    assert clock.timing()["video"][0] == pytest.approx(4.0)


def test_the_offer_finds_sections_by_media_type_not_by_mid():
    sdp = (
        "v=0\r\nm=video 9 RTP/SAVPF 97\r\na=mid:20\r\n"
        "m=audio 9 RTP/SAVPF 8\r\na=mid:video\r\n"
        "m=application 9 SCTP webrtc-datachannel\r\na=mid:1\r\n"
    )
    out = offer_with_frame_times(sdp)
    assert out.count(f"a=extmap:{EXT_FRAME_TIME_ID}") == 2
    assert "a=rtcp-fb:97 transport-cc" in out
    assert "a=rtcp-fb:8 transport-cc" not in out
    assert out.split("m=application")[1].count("extmap") == 0
