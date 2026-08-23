"""`sprop_is_unstable` is public API for stream-server consumers.

A server that caches its decoder configuration when a track is first published
(go2rtc's fMP4 `avcC`) serves a stale one to every later session of a camera
that changes its SPS. Consumers need to know which cameras those are so they
can recreate the stream definition first; that is what this exposes.
"""

from aidot_cameras.camera import protocol


def test_public_helper_tracks_the_marker(tmp_path, monkeypatch):
    monkeypatch.setattr(protocol, "_SPROP_DIR", str(tmp_path))
    dev = "cam-changes-its-sps"
    assert protocol.sprop_is_unstable(dev) is False
    open(protocol._sprop_unstable_path(dev), "w").write("changed\n")
    assert protocol.sprop_is_unstable(dev) is True


def test_public_helper_matches_the_private_one(tmp_path, monkeypatch):
    monkeypatch.setattr(protocol, "_SPROP_DIR", str(tmp_path))
    for dev in ("a", "b"):
        assert protocol.sprop_is_unstable(dev) == protocol._sprop_is_unstable(dev)
    open(protocol._sprop_unstable_path("b"), "w").write("x")
    assert protocol.sprop_is_unstable("b") == protocol._sprop_is_unstable("b") is True


def test_unknown_camera_is_not_unstable(tmp_path, monkeypatch):
    # Fail safe: never claim instability we have not observed, or every camera
    # would pay a stream-definition rebuild it does not need.
    monkeypatch.setattr(protocol, "_SPROP_DIR", str(tmp_path))
    assert protocol.sprop_is_unstable("never-seen") is False
