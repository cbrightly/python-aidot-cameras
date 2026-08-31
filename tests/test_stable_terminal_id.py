"""The peer id's terminal field must be stable, not minted fresh every open.

The camera parses the peer id as `<session>_<terminal_id>_%d_%d_%d` and compares
the terminal id against its existing sessions - `rtc_session_check_same_terminal_id`,
`rtc session terminal id is same`, and the paired
`new_peer_id:%s new_terminal_id:%s ... is same` / `old_peer_id:%s old_terminal_id:%s`
logs. So the terminal id is how the camera decides that a new request comes from
a client it already has a session with.

We were generating six fresh random hex digits on every open: **329 distinct
terminal ids across 836 opens in 19 hours**. Every reconnect therefore looked
like a brand-new client, and the camera's "same terminal" path - which is
presumably how an app's reconnect replaces its previous session rather than
adding one - could never fire for us.

That is worth fixing on its own merits whatever it turns out to do to the 80.2 s
cliff: a client that reconnects every two minutes should look like one client
reconnecting, not like hundreds of different ones.
"""
import re

from aidot_cameras.camera.client import CameraMixin, _stable_terminal_id

HEX6 = re.compile(r"^[0-9a-f]{6}$")


class TestStability:
    """CORRECTED 2026-08-31 from the decompiled vendor app.

    `KVSWebRTCChannel` builds its peer id as

        String.format("%s_%s_%d_%d_%d",
                      DeviceIdUtils.getDeviceId(app),   // FIELD 1 - stable per install
                      "0" + createRandomStr(5),         // FIELD 2 - random per open
                      playCmd, 0, !isDefaultHD)

    So the STABLE identity is field 1, and field 2 is random per open - the
    opposite of what the first version of this fix assumed. Our field 1 was a
    fresh 32-hex value every open and our field 2 was fresh 6-hex; stabilising
    field 2 pinned the wrong one and left the real identity random.
    """

    def test_shape(self):
        assert HEX6.match(_stable_terminal_id())

    def test_same_within_a_process(self):
        assert _stable_terminal_id() == _stable_terminal_id()

    def test_field_one_is_the_stable_identity(self):
        """Field 1 carries the install identity, as the app's device id does."""
        a = CameraMixin.generate_webrtc_peer_id(sdes=True)
        b = CameraMixin.generate_webrtc_peer_id(sdes=True)
        assert a.split("_")[0] == b.split("_")[0]

    def test_field_two_stays_random_per_open(self):
        """The app randomises field 2 every open; so must we."""
        a = CameraMixin.generate_webrtc_peer_id(sdes=True)
        b = CameraMixin.generate_webrtc_peer_id(sdes=True)
        assert a.split("_")[1] != b.split("_")[1]

    def test_a_seed_makes_it_deterministic(self):
        assert _stable_terminal_id(seed="abc") == _stable_terminal_id(seed="abc")
        assert _stable_terminal_id(seed="abc") != _stable_terminal_id(seed="xyz")
        assert HEX6.match(_stable_terminal_id(seed="abc"))
