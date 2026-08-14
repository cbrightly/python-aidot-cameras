"""Unit tests for the aidot-go2rtc CLI (aidot_cameras.__main__).

No network: only the pure helpers and the argparse validation paths that
fail before any asyncio.run()/cloud call are exercised. The streaming paths
(cmd_list/cmd_stream) are integration-tested live, not here.
"""
import os
import stat
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

from aidot_cameras.__main__ import (
    _env_bool,
    _go2rtc_source,
    _read_token_file,
    _stream_slug,
    _write_token_file,
    main,
)


def test_env_bool_tristate(monkeypatch):
    monkeypatch.setenv("AIDOT_X", "1")
    assert _env_bool("AIDOT_X") is True
    monkeypatch.setenv("AIDOT_X", "0")
    assert _env_bool("AIDOT_X") is False
    monkeypatch.setenv("AIDOT_X", "yes")  # anything but '1' is not-True
    assert _env_bool("AIDOT_X") is False
    monkeypatch.delenv("AIDOT_X", raising=False)
    assert _env_bool("AIDOT_X") is None  # unset -> library default


def test_token_file_roundtrip_and_mode(tmp_path):
    # The AIDOT_TOKEN_FILE auth path: write login_info then read it back
    # unchanged, with 0600 perms. Exercises the executor-offloaded helpers
    # without a cloud round-trip.
    path = str(tmp_path / "token.json")
    info = {"accessToken": "a.b.c", "refreshToken": "r1", "id": 42}
    _write_token_file(path, info)
    assert _read_token_file(path) == info
    assert stat.S_IMODE(os.stat(path).st_mode) == 0o600


def test_main_requires_dev_id_and_output_url():
    # No --list and missing positionals -> argparse error -> SystemExit(2),
    # raised before any network/asyncio.run.
    with pytest.raises(SystemExit) as ei:
        main([])
    assert ei.value.code == 2


def test_main_requires_output_url_when_dev_id_given():
    with pytest.raises(SystemExit) as ei:
        main(["some-dev-id"])  # dev_id but no output_url
    assert ei.value.code == 2


if __name__ == "__main__":
    raise SystemExit(pytest.main([__file__, "-q"]))


def test_stream_slug_is_url_safe_and_falls_back_to_the_id():
    assert _stream_slug("Front Door", "abc") == "front_door"
    assert _stream_slug("Winees Spotlight Cam_183", "abc") == "winees_spotlight_cam_183"
    # A go2rtc stream name lands in a URL path, so punctuation-only or missing
    # names must not produce an empty (or slash-bearing) key.
    assert _stream_slug(None, "7c89a5c1b363") == "camera_7c89a5c1"
    assert _stream_slug("!!!", "7c89a5c1b363") == "camera_7c89a5c1"
    assert _stream_slug("Back/Yard #2", "abc") == "back_yard_2"


def test_go2rtc_source_pairs_each_transport_with_its_output_argument():
    """The output argument is the whole difference between the two transports.

    ``{output}`` on a DTLS camera and ``-`` on an SDES one are both rejected by
    cmd_stream, so a --list that printed the wrong one would hand the user a
    config that cannot work.
    """
    assert _go2rtc_source("dev1", True) == "exec:aidot-go2rtc dev1 {output}"
    assert _go2rtc_source("dev2", False) == "exec:aidot-go2rtc dev2 -"
