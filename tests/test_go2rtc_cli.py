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
    """Unset must stay None so the library default wins.

    The tri-state matters more than the truthiness: returning False for an unset
    var would push an explicit override into ``start_keepalive`` and silence a
    default the library owns (SDES serve audio is ON by default, not off).
    """
    monkeypatch.setenv("AIDOT_X", "1")
    assert _env_bool("AIDOT_X") is True
    monkeypatch.setenv("AIDOT_X", "0")
    assert _env_bool("AIDOT_X") is False
    monkeypatch.delenv("AIDOT_X", raising=False)
    assert _env_bool("AIDOT_X") is None  # unset -> library default


@pytest.mark.parametrize("raw", ["1", "true", "TRUE", "yes", "on", " On "])
def test_env_bool_accepts_every_spelling_the_library_accepts(monkeypatch, raw):
    """The CLI must not be stricter about truthiness than the library it drives.

    ``AIDOT_FAST_CONNECT=true`` is the spelling the README documents and the one
    ``webrtc_open`` honours (``1/true/yes/on``). Reading only the literal ``"1"``
    turned it into an explicit ``False`` handed to ``start_keepalive``, which
    *disabled* the feature the user had just switched on - worse than ignoring it,
    because the env var also outranked the library's own default.
    """
    monkeypatch.setenv("AIDOT_X", raw)
    assert _env_bool("AIDOT_X") is True


@pytest.mark.parametrize("raw", ["0", "false", "No", "off", " OFF "])
def test_env_bool_falsy_spellings_match_the_library(monkeypatch, raw):
    monkeypatch.setenv("AIDOT_X", raw)
    assert _env_bool("AIDOT_X") is False


@pytest.mark.parametrize("raw", ["", "  ", "2", "enabled"])
def test_env_bool_defers_on_anything_it_cannot_read(monkeypatch, raw):
    """An unrecognised value must not become an override.

    The library's resolvers disagree on convention: AIDOT_FAST_CONNECT is a
    truthy allowlist over a default of off, AIDOT_SDES_SERVE_AUDIO a falsy
    denylist over a default of ON. Folding an unreadable value to False would
    therefore be silently destructive on the second one - ``AIDOT_SDES_SERVE_AUDIO=``
    with no value, which is what an empty systemd `Environment=` or compose entry
    produces, would hand start_keepalive an explicit False and drop audio the
    library was going to serve. Returning None leaves the decision where the
    default lives.
    """
    monkeypatch.setenv("AIDOT_X", raw)
    assert _env_bool("AIDOT_X") is None


@pytest.mark.parametrize("raw", ["", "  ", "2", "enabled", "1", "on", "0", "off"])
def test_env_bool_never_contradicts_the_serve_audio_resolver(monkeypatch, raw):
    """Whatever the CLI decides must agree with the library reading the same var.

    Locks the two readers together on the knob where a disagreement is silent:
    serve audio defaults ON, so a False the library would not have produced costs
    the user their audio with nothing in the log to say why.
    """
    monkeypatch.setenv("AIDOT_SDES_SERVE_AUDIO", raw)
    cli = _env_bool("AIDOT_SDES_SERVE_AUDIO")
    library = raw.strip().lower() not in ("0", "false", "no", "off")
    if cli is not None:
        assert cli is library


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
    assert _stream_slug("Front Gate Cam_9", "abc") == "front_gate_cam_9"
    # A go2rtc stream name lands in a URL path, so punctuation-only or missing
    # names must not produce an empty (or slash-bearing) key.
    assert _stream_slug(None, "0a1b2c3d4e5f") == "camera_0a1b2c3d"
    assert _stream_slug("!!!", "0a1b2c3d4e5f") == "camera_0a1b2c3d"
    assert _stream_slug("Back/Yard #2", "abc") == "back_yard_2"


def test_go2rtc_source_pairs_each_transport_with_its_output_argument():
    """The output argument is the whole difference between the two transports.

    ``{output}`` on a DTLS camera and ``-`` on an SDES one are both rejected by
    cmd_stream, so a --list that printed the wrong one would hand the user a
    config that cannot work.
    """
    assert _go2rtc_source("dev1", True) == "exec:aidot-go2rtc dev1 {output}"
    assert _go2rtc_source("dev2", False) == "exec:aidot-go2rtc dev2 -"
