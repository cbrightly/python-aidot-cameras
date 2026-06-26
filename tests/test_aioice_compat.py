"""Guard: the private aioice internals we monkeypatch / read still exist.

We reach into aioice's non-public API in two places:

  * ``protocol._install_highport_nomination_patch`` overrides
    ``Connection.build_request(self, pair, nominate)`` and reads
    ``self._check_list`` / ``self.ice_controlling`` (the A000088 high-port
    nomination fix);
  * ``WebRTCSession._ice_path`` reads ``Connection._nominated`` and each
    ``CandidatePair``'s ``local_candidate`` / ``remote_candidate`` and the
    candidate ``type`` / ``host`` / ``port`` / ``transport`` (stream diagnostics).

aioice has no stability guarantee for these.  This test fails LOUDLY if an
upgrade renames or reshapes them - the alternative is streaming silently
degrading on a live box (the patch no-ops, diagnostics return empty).  When it
fails, re-validate the patch against the new aioice and widen the pin in
pyproject.toml.
"""
import inspect

from aioice import ice

from aidot_cameras.camera.protocol import _install_highport_nomination_patch


def test_connection_internals_used_by_highport_patch():
    conn = ice.Connection(ice_controlling=True)
    # build_request signature the patch wraps
    params = list(inspect.signature(ice.Connection.build_request).parameters)
    assert params == ["self", "pair", "nominate"]
    # instance state the patch + diagnostics read
    assert isinstance(conn._check_list, list)
    assert isinstance(conn._nominated, dict)
    assert isinstance(conn.ice_controlling, bool)


def test_candidate_and_pair_shape_used_by_ice_path():
    cand = ice.Candidate.from_sdp("1 1 udp 2130706431 192.168.1.5 50000 typ host")
    assert cand.type == "host"
    assert cand.host == "192.168.1.5"
    assert cand.port == 50000
    assert cand.transport == "udp"
    # CandidatePair exposes the candidate accessors _ice_path walks:
    # local_candidate is a property; remote_candidate is set from the ctor arg.
    assert isinstance(ice.CandidatePair.local_candidate, property)
    assert "remote_candidate" in inspect.signature(ice.CandidatePair.__init__).parameters


def test_highport_patch_installs_cleanly():
    # Idempotent and self-reporting: returns True when active.
    assert _install_highport_nomination_patch() is True
    assert getattr(ice.Connection, "_aidot_highport_patched", False) is True
    # second call is a no-op that still reports active
    assert _install_highport_nomination_patch() is True
