"""One INFO line per live session per minute, saying what the link did.

Zero is the normal reading between reconnects. A steady non-zero loss is
packet loss on the path from the camera; a stall is seconds since the last
media packet. Deltas per period, not running totals, so a trickle is visible
as a trickle.
"""

from __future__ import annotations

import logging

import pytest

from aidot_cameras.camera import health


def _snap(**kw):
    base = {
        "packets": 0,
        "bytes": 0,
        "lost": 0,
        "last_media_monotonic": 0.0,
        "media_path": None,
        "viewer": None,
    }
    base.update(kw)
    return base


def test_nothing_before_the_period_elapses():
    r = health.HealthReporter("dev1", period_s=60.0)
    assert r.tick(100.0, _snap(packets=10)) is None
    assert r.tick(159.0, _snap(packets=20)) is None


def test_a_line_at_the_period_with_deltas_and_rates():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(100.0, _snap(packets=100, bytes=1000, lost=1))
    line = r.tick(
        160.0,
        _snap(
            packets=1300,
            bytes=61000,
            lost=3,
            last_media_monotonic=159.5,
            media_path="direct",
            viewer=True,
        ),
    )
    assert line is not None
    assert line.startswith("camera dev1: health 60s:")
    assert "packets=1200 (20.0/s)" in line
    assert "bytes=60.0KB" in line
    assert "lost=2 (0.2%)" in line
    assert "stall=0.5s" in line
    assert "path=direct" in line
    assert "viewers=present" in line


def test_the_second_period_reports_only_its_own_delta():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, _snap(packets=0))
    r.tick(60.0, _snap(packets=600))
    line = r.tick(120.0, _snap(packets=660))
    assert "packets=60 (1.0/s)" in line


def test_a_counter_that_went_backwards_is_a_reset_not_a_negative_delta():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, _snap(packets=5000, bytes=9000))
    line = r.tick(60.0, _snap(packets=40, bytes=800))
    assert "packets=40" in line
    assert "bytes=800B" in line


def test_loss_that_drops_without_a_reset_reads_as_zero_not_as_its_total():
    # aiortc's packetsLost is expected-minus-received and falls by a few when
    # late packets arrive; reporting 48 lost for a quiet minute would be a lie.
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, _snap(packets=1000, lost=50))
    line = r.tick(60.0, _snap(packets=2000, lost=48))
    assert "lost=0 (0.0%)" in line
    assert "packets=1000" in line


def test_skip_moves_the_anchor_without_a_line():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, _snap())
    r.skip(60.0)  # a failed snapshot: count the period as spent
    assert r.due(61.0) is False
    assert r.due(120.0) is True


def test_no_media_yet_and_unknown_viewers_are_said_plainly():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, _snap())
    line = r.tick(60.0, _snap())
    assert "no media yet" in line
    assert "viewers=unknown" in line
    assert "path=?" in line


def test_absent_viewers():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, _snap())
    assert "viewers=absent" in r.tick(60.0, _snap(viewer=False))


def test_optional_video_counters_are_appended_when_given():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, _snap(frames=0, keyframes=0))
    line = r.tick(60.0, _snap(frames=900, keyframes=30, max_keyframe_gap=44))
    assert "frames=900 (15.0/s)" in line
    assert "keyframes=30" in line
    assert "max_keyframe_gap=44" in line
    assert "max_gap=" not in line


def test_a_zero_period_disables_the_line():
    r = health.HealthReporter("dev1", period_s=0.0)
    assert r.enabled is False
    assert r.due(0.0) is False
    assert r.tick(0.0, _snap()) is None
    assert r.tick(10_000.0, _snap(packets=5)) is None


def test_due_says_when_a_tick_would_anchor_or_report():
    r = health.HealthReporter("dev1", period_s=60.0)
    assert r.due(0.0) is True  # first tick anchors
    r.tick(0.0, _snap())
    assert r.due(30.0) is False
    assert r.due(60.0) is True
    r.tick(60.0, _snap())
    assert r.due(61.0) is False


def test_no_loss_field_when_the_transport_does_not_count_loss():
    r = health.HealthReporter("dev1", period_s=60.0)
    sdes = {
        "packets": 0,
        "bytes": 0,
        "last_media_monotonic": 0.0,
        "media_path": None,
        "viewer": None,
    }
    r.tick(0.0, sdes)
    line = r.tick(60.0, {**sdes, "packets": 50})
    assert "lost=" not in line


def test_the_line_is_logged_at_info_on_the_given_logger(caplog):
    lg = logging.getLogger("test.health")
    r = health.HealthReporter("dev1", period_s=60.0, logger=lg)
    with caplog.at_level(logging.INFO, logger="test.health"):
        r.tick(0.0, _snap())
        r.tick(60.0, _snap(packets=12))
    msgs = [rec for rec in caplog.records if rec.name == "test.health"]
    assert len(msgs) == 1
    assert msgs[0].levelno == logging.INFO
    assert "camera dev1: health 60s:" in msgs[0].getMessage()


def test_loss_percentage_handles_zero_received():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, _snap())
    line = r.tick(60.0, _snap(lost=3))
    assert "lost=3 (100.0%)" in line


def test_dtls_snapshot_sums_inbound_and_reads_the_ice_path():
    stats = {
        "ice": [{"component": 1, "local_type": "host", "remote_type": "host"}],
        "inbound": [
            {"kind": "audio", "packets_received": 100, "packets_lost": 1},
            {
                "kind": "video",
                "packets_received": 900,
                "packets_lost": -3,
            },  # aiortc can go negative
        ],
    }
    canary = {"frames": 450, "keyframes": 15, "gap": 0, "max_gap": 44, "unwrapped": 0}
    snap = health.dtls_snapshot(stats, canary, 12.5, True)
    assert snap["packets"] == 1000
    assert snap["lost"] == 1
    assert snap["media_path"] == "direct"
    assert (
        snap["frames"] == 450
        and snap["keyframes"] == 15
        and snap["max_keyframe_gap"] == 44
    )
    assert snap["last_media_monotonic"] == 12.5 and snap["viewer"] is True
    assert "bytes" not in snap


def test_dtls_snapshot_relay_and_unknowns():
    relay = {"ice": [{"remote_type": "relay"}], "inbound": []}
    assert health.dtls_snapshot(relay, None, 0.0, None)["media_path"] == "relay"
    nothing = health.dtls_snapshot(
        {"ice": None, "inbound": [], "error": "x"}, None, 0.0, None
    )
    assert (
        nothing["media_path"] is None
        and nothing["packets"] == 0
        and "frames" not in nothing
    )


def test_a_dtls_line_has_no_bytes_field():
    r = health.HealthReporter("dev1", period_s=60.0)
    r.tick(0.0, health.dtls_snapshot({"ice": None, "inbound": []}, None, 0.0, None))
    line = r.tick(
        60.0,
        health.dtls_snapshot(
            {"ice": None, "inbound": [{"packets_received": 60, "packets_lost": 0}]},
            None,
            59.0,
            False,
        ),
    )
    assert "bytes=" not in line
    assert "packets=60 (1.0/s) lost=0 (0.0%)" in line


@pytest.mark.parametrize(
    "raw, expected",
    [
        (None, 60.0),
        ("", 60.0),
        ("junk", 60.0),
        ("nan", 60.0),
        ("0", 0.0),
        ("-1", 0.0),
        ("30", 30.0),
    ],
)
def test_period_knob(raw, expected):
    env = {} if raw is None else {"AIDOT_HEALTH_LINE_S": raw}
    assert health.health_line_period_s(env) == expected


# --------------------------------------------------------------- wiring
# The two loops are not driven end to end in the unit tier (they need a
# session); as the neighbouring loop tests do, check the source for the calls.


def test_the_sdes_keepalive_loop_ticks_a_reporter_every_poll():
    import inspect

    import aidot_cameras.camera.client as cc

    src = inspect.getsource(cc.CameraMixin._sdes_keepalive_loop_inner)
    made = src.index("_health = HealthReporter(self.device_id)")
    wait = src.index("_fin, _ = await asyncio.wait({_done}, timeout=5.0)")
    tick = src.index("_health.tick(", wait)
    assert made < wait < tick, "the reporter is made once and ticked after every poll"
    assert "session.media_stats()" in src[tick : tick + 300]
    assert "_health_viewer = _present" in src


def test_the_dtls_serve_loop_reports_on_its_own_cadence():
    import inspect

    import aidot_cameras.camera.client as cc

    src = inspect.getsource(cc.CameraMixin._dtls_serve_loop_inner)
    made = src.index("_health_dtls = HealthReporter(self.device_id)")
    due = src.index("if _health_dtls.due(_now):")
    assert made < due
    body = src[due : due + 900]
    assert "_dtls_snapshot(" in body
    assert "progress[0]" in body and "_watching_dtls" in body
    assert "except Exception" in body, "a failed report must never end the session"
    assert "_watching_dtls = _watching" in src


def test_fmt_bytes():
    assert health._fmt_bytes(0) == "0B"
    assert health._fmt_bytes(999) == "999B"
    assert health._fmt_bytes(60000) == "60.0KB"
    assert health._fmt_bytes(2_000_000) == "2.0MB"
    assert health._fmt_bytes(3_000_000_000) == "3.0GB"
