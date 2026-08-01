"""A go2rtc stream must never be registered as its own source.

In PUSH mode the keepalive publishes INTO go2rtc, so the "keepalive rtsp url" is
go2rtc's own address for that stream. Registering it as the stream's source makes
go2rtc its own producer: the stream lists a producer, nothing feeds it, and every
consumer gets a connection with no media.

Seen live after the integration began passing go2rtc_url: one camera's stream had
two producers, one of them `rtsp://127.0.0.1:8554/aidot_12b144cb12da` - its own
address - and go2rtc returned HTTP 200 with a zero-byte frame for it.
"""
from aidot_cameras.camera.client import _is_self_referential_source as loops


def test_push_url_for_the_same_stream_is_self_referential():
    assert loops("rtsp://127.0.0.1:8554/aidot_12b144cb12da",
                 "aidot_12b144cb12da") is True


def test_push_url_for_a_different_stream_is_fine():
    # Publishing camera A into stream B is odd but not a loop.
    assert loops("rtsp://127.0.0.1:8554/aidot_other", "aidot_12b144cb12da") is False


def test_the_http_serve_url_is_a_real_source():
    # PULL mode: go2rtc fetches from the library's own serve. Must still register.
    assert loops("http://127.0.0.1:18931/00000000000000000000000000000001.ts",
                 "aidot_000000000000") is False


def test_rtsps_is_covered_too():
    assert loops("rtsps://127.0.0.1:8554/aidot_x", "aidot_x") is True


def test_trailing_slash_and_case_do_not_hide_it():
    assert loops("RTSP://127.0.0.1:8554/aidot_x/", "aidot_x") is True


def test_missing_values_are_not_self_referential():
    assert loops("", "aidot_x") is False
    assert loops("rtsp://127.0.0.1:8554/aidot_x", "") is False


def test_a_remote_go2rtc_push_url_still_counts():
    # The host does not matter; publishing into the stream you are registering is
    # a loop wherever go2rtc lives.
    assert loops("rtsp://go2rtc.lan:8554/aidot_x", "aidot_x") is True
