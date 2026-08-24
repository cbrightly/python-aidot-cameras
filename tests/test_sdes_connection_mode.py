"""One knob for the media path: auto (default), lan, or relay.

The pieces existed but did not compose into a choice a user could make:

* the offer already prefers the LAN by ICE priority (host 2130706431 >
  srflx 1694498815 > relay 16777215), and the fleet behaves that way - in the
  2026-08-24 full-fleet run six of seven cameras streamed direct and only the
  one unit with no route to us rode the TURN relay;
* ``sdes_skip_turn`` / ``AIDOT_SDES_SKIP_TURN_PREALLOC`` already implement
  "LAN only" (no relay pre-allocation at all);
* nothing could FORCE the relay, and nothing reported which path a session
  actually took.

``sdes_connection_mode`` composes them:

    auto   - today's behaviour, exactly.  All candidates offered, camera picks,
             LAN wins by priority when reachable.
    lan    - no relay.  Routed through the same resolver as sdes_skip_turn so
             the battery force-keep applies: a battery camera is woken through
             the cloud and the relay is its only path back (validated live on
             an A001513 - with the relay it streams, without it nothing).
    relay  - the offer carries ONLY the relay candidate, so an ICE-speaking
             camera has no host/srflx to nominate.  The relay address must
             still never appear in c=/m= - TURN drops every camera packet
             without a CreatePermission for the camera's (unknown) public
             address, so steering is candidate-lines-only.  Non-ICE firmware
             that dials the c=/m= address directly cannot be steered; that is
             a documented limit, not a bug here.
"""
# (no pytest fixtures needed; plain asserts)

from aidot_cameras.camera.sdes_open import _sdes_offer_candidate_lines


def _client_with(mode=None, env=None, monkeypatch=None, battery=False):
    import aidot_cameras.camera.client as cc

    cls = next(v for v in vars(cc).values()
               if isinstance(v, type) and "_resolve_sdes_connection_mode" in v.__dict__)
    # is_battery_camera is a read-only property on the mixin; override it on a
    # throwaway subclass rather than reconstructing the evidence chain.
    sub = type("_ModeProbe", (cls,),
               {"is_battery_camera": property(lambda self: self._batt)})
    cam = sub.__new__(sub)
    cam._batt = battery
    if mode is not None:
        cam._sdes_connection_mode_opt = mode
    if monkeypatch is not None:
        if env is None:
            monkeypatch.delenv("AIDOT_SDES_CONNECTION_MODE", raising=False)
        else:
            monkeypatch.setenv("AIDOT_SDES_CONNECTION_MODE", env)
    return cam


# -- the resolver -------------------------------------------------------------

def test_the_default_is_auto(monkeypatch):
    assert _client_with(monkeypatch=monkeypatch)._resolve_sdes_connection_mode() == "auto"


def test_the_per_open_option_wins_over_the_environment(monkeypatch):
    cam = _client_with(mode="relay", env="lan", monkeypatch=monkeypatch)
    assert cam._resolve_sdes_connection_mode() == "relay"


def test_the_environment_is_read_when_no_option_is_set(monkeypatch):
    assert _client_with(env="lan", monkeypatch=monkeypatch)._resolve_sdes_connection_mode() == "lan"


def test_nonsense_is_auto_not_an_error(monkeypatch):
    # This is resolved while opening a stream; a typo must not stop video.
    assert _client_with(env="fast", monkeypatch=monkeypatch)._resolve_sdes_connection_mode() == "auto"
    assert _client_with(mode="LAN!", monkeypatch=monkeypatch)._resolve_sdes_connection_mode() == "auto"


def test_lan_mode_skips_the_relay_via_the_existing_resolver(monkeypatch):
    # Composition, not a parallel code path: lan must produce the same skip
    # the sdes_skip_turn lever produces, so every behaviour hanging off that
    # resolver (instrumentation, adaptive interplay) stays consistent.
    cam = _client_with(mode="lan", monkeypatch=monkeypatch)
    assert cam._resolve_sdes_skip_turn() is True


def test_relay_mode_forces_the_preallocation_on(monkeypatch):
    # Forcing the relay while skipping its allocation would offer nothing at
    # all; relay mode must defeat even an explicit skip_turn=True.
    cam = _client_with(mode="relay", monkeypatch=monkeypatch)
    cam._sdes_skip_turn_opt = True
    assert cam._resolve_sdes_skip_turn() is False


def test_a_battery_camera_never_loses_the_relay_to_lan_mode(monkeypatch):
    # The force-keep that already guards sdes_skip_turn must guard the mode
    # too: a battery camera is woken through the cloud and the relay is its
    # only way back (validated live on an A001513).
    cam = _client_with(mode="lan", monkeypatch=monkeypatch, battery=True)
    assert cam._resolve_sdes_skip_turn() is False


# -- the candidate lines ------------------------------------------------------

_RELAY = ("203.0.113.9", 40100)


def test_auto_offers_all_reachable_candidates():
    lines = _sdes_offer_candidate_lines(
        "auto", "192.168.7.2", 41000, "198.51.100.7", _RELAY)
    assert "typ host" in lines and "typ srflx" in lines and "typ relay" in lines
    # LAN preference is the priority ordering, and it is load-bearing.
    assert lines.index("typ host") < lines.index("typ srflx") < lines.index("typ relay")
    assert "2130706431" in lines and "16777215" in lines


def test_lan_offers_no_relay_candidate():
    lines = _sdes_offer_candidate_lines(
        "lan", "192.168.7.2", 41000, "198.51.100.7", None)
    assert "typ relay" not in lines
    assert "typ host" in lines


def test_relay_offers_only_the_relay_candidate():
    lines = _sdes_offer_candidate_lines(
        "relay", "192.168.7.2", 41000, "198.51.100.7", _RELAY)
    assert "typ relay" in lines
    assert "typ host" not in lines and "typ srflx" not in lines


def test_relay_mode_with_no_allocation_falls_back_to_auto():
    # The pre-allocation can fail (TURN briefly unreachable).  An offer with
    # zero candidates is a session that cannot start; a stream on the wrong
    # path beats no stream.
    lines = _sdes_offer_candidate_lines(
        "relay", "192.168.7.2", 41000, "198.51.100.7", None)
    assert "typ host" in lines and "typ srflx" in lines


def test_candidate_lines_end_with_crlf_and_nothing_else():
    # These lines are spliced into a hand-built SDP for firmware that parses
    # linearly; a missing CRLF corrupts the following attribute.
    for mode in ("auto", "lan", "relay"):
        lines = _sdes_offer_candidate_lines(
            mode, "192.168.7.2", 41000, "198.51.100.7", _RELAY)
        assert lines == "" or lines.endswith("\r\n")
        assert "\n\n" not in lines and "\r\r" not in lines


def test_the_offer_builder_uses_the_helper():
    """Source guard: the tests above pass with the production offer untouched.

    The video and audio m-sections must both take their candidate block from
    the helper, or the mode steers one media stream and not the other.
    """
    import pathlib

    src = (pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"
           / "camera" / "sdes_open.py").read_text()
    assert src.count("_sdes_offer_candidate_lines(") >= 3, (
        "expected the offer builder to emit audio and video candidate blocks "
        "through _sdes_offer_candidate_lines (definition + at least two calls)")


# -- the c=/m= endpoint (relay forcing that firmware cannot ignore) -----------
#
# Measured 2026-08-24: with only the relay candidate in the offer, both an
# A001064 and an A001513 still dialed the c=/m= address directly - this
# fleet's firmware nominates by dialing c=, not by reading candidate lines.
# So candidate-only "relay" mode is a control that lies, and the one honest
# lever is c=/m= itself. That requires the TURN permission for OUR OWN public
# address to exist BEFORE the camera dials (its packets reach the TURN server
# from the house WAN address), which is also the likely reason the old
# "relay in c= drops every packet" observation predates the permission
# machinery: the permission came too late or never.

from aidot_cameras.camera.sdes_open import _sdes_offer_media_endpoint


def test_auto_keeps_the_direct_endpoint():
    ip, port, is_relay = _sdes_offer_media_endpoint(
        "auto", "198.51.100.7", 41000, _RELAY, "198.51.100.7")
    assert (ip, port, is_relay) == ("198.51.100.7", 41000, False)


def test_relay_mode_moves_c_and_m_to_the_allocation():
    ip, port, is_relay = _sdes_offer_media_endpoint(
        "relay", "198.51.100.7", 41000, _RELAY, "198.51.100.7")
    assert (ip, port) == _RELAY
    assert is_relay is True


def test_relay_mode_without_an_allocation_stays_direct():
    ip, port, is_relay = _sdes_offer_media_endpoint(
        "relay", "198.51.100.7", 41000, None, "198.51.100.7")
    assert (ip, port, is_relay) == ("198.51.100.7", 41000, False)


def test_relay_mode_without_a_public_ip_stays_direct():
    # The permission that lets the camera's packets through names OUR public
    # address; without one the relayed path is a black hole, and a stream on
    # the wrong path beats no stream.
    ip, port, is_relay = _sdes_offer_media_endpoint(
        "relay", "192.168.7.2", 41000, _RELAY, None)
    assert (ip, port, is_relay) == ("192.168.7.2", 41000, False)


def test_the_offer_uses_the_endpoint_helper_and_prearms_the_permission():
    """Source guard: relay-in-c= without the early permission is a black hole."""
    import pathlib

    src = (pathlib.Path(__file__).resolve().parents[1] / "aidot_cameras"
           / "camera" / "sdes_open.py").read_text()
    assert src.count("_sdes_offer_media_endpoint(") >= 3, (
        "audio and video endpoints must both come from the helper")
    # The permission for OUR public address, installed at the setup phase and
    # gated to relay mode (indiscriminate installs caused TURN self-loop
    # storms - the allocation helper carries the warning).
    j = src.index('if _relay_in_c and _public_ip:')
    window = src[j:j + 300]
    assert "_turn_install_permissions" in window and "relay-mode WAN" in window, (
        "relay-in-c= must install the permission for our public address, or "
        "the relayed path is a black hole")
