"""A session must say which path its media actually took.

The connection mode is a preference; the camera decides where to send from,
and without a per-session receipt the two are indistinguishable.  The
2026-08-24 fleet run needed a wire capture to establish that six cameras
streamed direct and one rode the TURN relay - a fact the bridge knew the
moment the first packet arrived, and threw away.

Classification is by source address: media arriving FROM the TURN server is
relayed (that covers both shapes - the camera's own allocation on the vendor
TURN, which is where the relayed A001513's media comes from, and a Data
Indication through ours); anything else is direct.  The TURN address set is
per-session, from the ICE entries the open actually used.
"""

from aidot_cameras.camera.sdes_open import _classify_media_path


def test_media_from_the_lan_is_direct():
    assert _classify_media_path("192.168.7.20", {"203.0.113.9"}) == "direct"


def test_media_from_the_turn_server_is_relay():
    # The relayed unit's media arrives from the TURN server's address - the
    # camera's own allocation port, not ours, so PORT must not participate.
    assert _classify_media_path("203.0.113.9", {"203.0.113.9"}) == "relay"


def test_no_turn_servers_means_direct_by_construction():
    # lan mode skips the pre-allocation entirely; nothing can be relayed.
    assert _classify_media_path("203.0.113.9", set()) == "direct"


def test_unknown_source_is_not_guessed():
    assert _classify_media_path(None, {"203.0.113.9"}) is None


def test_media_stats_carries_the_path():
    """The session snapshot exposes it exactly like video_pt: a shared ref
    the bridge stamps, None until the first media packet."""
    from aidot_cameras.camera.sdes import SdesSession

    path_ref = [None]
    import inspect

    sig = inspect.signature(SdesSession.__init__)
    assert "media_path" in sig.parameters, (
        "SdesSession must accept the media_path shared ref the bridge stamps"
    )

    # Snapshot logic without a live ffmpeg: build the instance bare.
    s = SdesSession.__new__(SdesSession)
    s._media_counts = [3, 900]
    s._media_progress = [1.0]
    s._first_video_pt = [96]
    s._first_audio_pt = [8]
    s._media_path = path_ref
    stats = s.media_stats()
    assert stats["media_path"] is None
    path_ref[0] = "relay"
    assert s.media_stats()["media_path"] == "relay"


def test_the_bridge_stamps_it_where_the_media_address_is_captured():
    """The bridge learns the source once, at first media: the media path is
    classified in the same block that captures the camera's media address,
    because later sites race the first stats read."""
    import ast

    from tests.ast_guards import calls_to, sdes_bridge

    blocks = []
    for node in ast.walk(sdes_bridge()):
        for field in ("body", "orelse"):
            stmts = getattr(node, field, None)
            if not isinstance(stmts, list):
                continue
            captures = [
                s
                for s in stmts
                if isinstance(s, ast.Assign)
                and any(getattr(t, "attr", None) == "_cam_srtp_src" for t in s.targets)
            ]
            if captures:
                blocks.append(stmts)
    assert blocks, "the media address capture moved"
    for stmts in blocks:
        here = ast.Module(body=stmts, type_ignores=[])
        assert calls_to(here, "_classify_media_path"), (
            "the media path must be classified where the address is captured"
        )


def test_turn_ips_come_from_the_uris_the_entries_actually_carry():
    # The ICE entries carry "Uris" lists, not host fields - an extraction
    # reading the wrong key returns an empty set, and every relayed session
    # then classifies as direct. Exactly the silent-instrumentation failure
    # the b=AS receipt lesson was about.
    from aidot_cameras.camera.sdes_open import _turn_entry_ips

    entries = [
        {
            "Uris": ["stun:203.0.113.9:3478", "turn:203.0.113.9:5349?transport=udp"],
            "Username": "u",
            "Password": "p",
        },
        {"Uris": ["turn:198.51.100.4:3478"]},
    ]
    assert _turn_entry_ips(entries) == {"203.0.113.9", "198.51.100.4"}
    assert _turn_entry_ips([]) == set()
    assert _turn_entry_ips(None) == set()
