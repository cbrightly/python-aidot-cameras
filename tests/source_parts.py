"""Which methods were split into phase and region coroutines, and where.

The two stream opens and the two serve loops are orchestrators over phase and
region coroutines (``_sdes_open_*``, ``_dtls_open_*``, ``_sdes_keepalive_*``,
``_dtls_serve_loop_*``) that share one state object. The structural guards on
that split (``test_phase_state_is_complete``,
``test_phase_returns_reach_the_caller``) walk it from this table: method name
-> (module holding its parts, the parts' name prefix).
"""

PARTS = {
    "_open_sdes_stream_impl": ("aidot_cameras.camera.sdes_open", "_sdes_open_"),
    "_async_open_webrtc_stream_impl": (
        "aidot_cameras.camera.webrtc_open",
        "_dtls_open_",
    ),
    "_sdes_keepalive_loop_inner": ("aidot_cameras.camera.client", "_sdes_keepalive_"),
    "_dtls_serve_loop_inner": ("aidot_cameras.camera.client", "_dtls_serve_loop_"),
}
