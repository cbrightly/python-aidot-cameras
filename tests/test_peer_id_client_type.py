"""The peer id's shape, and the client class it announces.

The A001064 firmware parses the peer id with `%[^_]_%[^_]_%d_%d_%d`
(`parse_peer_id` at 0x298ef8) and takes its client class from the FIRST
CHARACTER of field 2 - `client_type = field2[0] - '0'` - against
EN_WEBRTC_CLIENT_TYPE_{APP_ANDROID,APP_IOS,WEB,ALEXA,GOOGLE_HOME} = 0..4.

The measurement overrides that once pinned these fields from a file or an
environment variable (AIDOT_EXPT_PEERID_FILE / AIDOT_EXPT_PEERID_CLASS) have
been removed; the last tests here pin that they no longer act.
"""

import re


from aidot_cameras.camera.client import CameraMixin
from aidot_cameras.camera import client as client_mod

SHAPE = re.compile(r"^[0-9a-f]{32}_[0-9a-f]{6}_(\d+)_(\d+)_(\d+)$")
DEV = "1111aaaa2222bbbb3333cccc4444dddd"
OTHER = "5555eeee6666ffff7777000088881111"


def _gen(**kw):
    return CameraMixin.generate_webrtc_peer_id(sdes=True, **kw)


class TestDefault:
    def test_shape_is_unchanged(self, tmp_path, monkeypatch):
        pid = _gen(device_id=DEV)
        assert SHAPE.match(pid), pid

    def test_sdes_and_dtls_defaults_are_untouched(self, tmp_path, monkeypatch):
        assert SHAPE.match(_gen(device_id=DEV)).groups() == ("2", "0", "1")
        assert SHAPE.match(
            CameraMixin.generate_webrtc_peer_id(sdes=False, device_id=DEV)
        ).groups() == ("2", "0", "2")

    def test_two_ids_differ_in_their_random_parts(self, tmp_path, monkeypatch):
        assert _gen(device_id=DEV) != _gen(device_id=DEV)


class TestEveryCallSitePassesADeviceId:
    """Every call site still names the camera. The device id no longer changes
    the peer id (the per-device override that used it is gone), but keeping it
    at every call site keeps the signature honest for whatever needs it next."""

    def test_no_bare_call_survives_in_the_source(self):
        """Every call of generate_webrtc_peer_id in the client and the open
        scopes the peer id to a device."""
        from tests.ast_guards import calls_to, module_tree

        for mod in ("aidot_cameras.camera.client", "aidot_cameras.camera.webrtc_open"):
            calls = calls_to(module_tree(mod), "generate_webrtc_peer_id")
            bare = [
                c.lineno
                for c in calls
                if not any(k.arg == "device_id" for k in c.keywords)
            ]
            assert not bare, (
                f"{mod} calls generate_webrtc_peer_id without a device_id at {bare}"
            )


class TestTheClassCharacterIsWeb:
    """Field 2's first character announces the client class, and we announce
    '2' (WEB).

    The history matters, because the obvious reading of it is wrong. Pinning
    this character to '0' (APP_ANDROID) was tried on 2026-08-31 and reverted:
    against a drained fleet with Home Assistant stopped, three A000088 cameras
    went 0 for 3 over nine attempts, each completing DTLS in under two seconds
    and then receiving no media. That is real - across ten live-validation runs
    the A000088 is 29 of 30 with a random character, so a 3% baseline cannot
    explain 0 of 9.

    But it was recorded as "pinning the class breaks the A000088", which is one
    arm generalised into a rule. The vendor WEB app sends '2', and that arm was
    screened on 2026-09-07 through live-validate - same sha, only the
    environment differing - at **6 of 6 A000088 cameras across two independent
    runs**, nearly all on the first attempt.

    So the class is pinned to WEB, which is what a known-good vendor client
    announces, instead of a random hex digit that was out of range 11 times in
    16. Class 2 is NOT one of the smart-home classes (3 and 4) that skip the
    camera's keepalive watchdog, so this changes what we claim to be and
    nothing about how the session is policed.
    """

    def test_field2_announces_client_class_2(self, tmp_path, monkeypatch):
        firsts = {_gen(device_id=DEV).split("_")[1][0] for _ in range(120)}
        assert firsts == {"2"}, (
            "field 2's first character is the client class; it must announce "
            f"WEB ('2'), got {sorted(firsts)}"
        )

    def test_the_rest_of_field2_stays_random(self, tmp_path, monkeypatch):
        """Pinning all six would make every peer id identical across opens -
        cross-session reuse, which the camera dedups on."""
        tails = {_gen(device_id=DEV).split("_")[1][1:] for _ in range(40)}
        assert len(tails) > 1, "field 2 is fully pinned - that is peer-id reuse"

    def test_field2_is_still_six_hex(self, tmp_path, monkeypatch):
        for _ in range(20):
            f2 = _gen(device_id=DEV).split("_")[1]
            assert len(f2) == 6 and all(c in "0123456789abcdef" for c in f2)


class TestTheMeasurementOverridesAreGone:
    def test_the_scaffolding_is_removed(self):
        for name in (
            "EXPT_PEERID_FILE",
            "EXPT_PEERID_CLASS",
            "_expt_peer_id_class",
            "_expt_peer_id_fields",
        ):
            assert not hasattr(client_mod, name), name

    def test_the_old_environment_variables_change_nothing(self, tmp_path, monkeypatch):
        f = tmp_path / "pid"
        f.write_text(f"{DEV}:4_2_0_1")
        monkeypatch.setenv("AIDOT_EXPT_PEERID_FILE", str(f))
        monkeypatch.setenv("AIDOT_EXPT_PEERID_CLASS", "4")
        for _ in range(20):
            pid = _gen(device_id=DEV)
            assert pid.split("_")[1][0] == "2"
            assert SHAPE.match(pid).groups() == ("2", "0", "1")


class TestPlaybackFields:
    """SD playback asks for the app's client class and the main stream; the
    defaults every live open uses stay byte-for-byte what they were."""

    def test_client_class_is_field_twos_first_character(self):
        pid = _gen(device_id=DEV, client_class=0)
        assert pid.split("_")[1][0] == "0"
        assert SHAPE.match(pid), pid

    def test_the_default_class_is_still_web(self):
        assert _gen(device_id=DEV).split("_")[1][0] == "2"

    def test_stream_replaces_the_trailing_field(self):
        assert SHAPE.match(_gen(device_id=DEV, live_type=1, stream=0)).groups() == (
            "1",
            "0",
            "0",
        )

    def test_a_class_that_is_not_one_digit_is_refused(self):
        import pytest

        with pytest.raises(ValueError):
            _gen(device_id=DEV, client_class=10)
