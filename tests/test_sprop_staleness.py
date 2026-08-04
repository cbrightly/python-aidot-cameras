"""A cached sprop-parameter-sets must never outlive the sets it describes.

`_inject_sprop` writes cached H.264 parameter sets into the ffmpeg-input SDP so
the decoder can initialise out of band, robust to losing the in-band SPS. That
is only safe while the camera keeps sending the SAME sets.

Measured on an A001064: the cache held `seq_parameter_set_id=0` while the live
stream had moved to id 3. ffmpeg initialised from the cached sets, every slice
then referenced an id the decoder did not have, and the session decoded NOTHING
for its whole lifetime - `sps_id 3 out of range`, `non-existing PPS 0
referenced`, `decode_slice_header error`, `no frame!`. A viewer saw a permanent
black frame while go2rtc showed a healthy publisher taking ~2 Mbps.

Proven against that camera: with the stale cache in place ffprobe logged 38
decode errors and no frames; with it removed, the identical stream decoded with
zero errors. A MISSING parameter set only costs the wait for the next in-band
IDR, so when a camera proves unstable the right move is to stop injecting.
"""

import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

import pytest

SDP = "m=video 5000 RTP/AVP 96\r\na=fmtp:96 packetization-mode=1\r\n"
DEV = "cam-under-test"


@pytest.fixture()
def proto(tmp_path, monkeypatch):
    """Reload the module with the cache redirected at a temp dir."""
    monkeypatch.setenv("AIDOT_SPROP_DIR", str(tmp_path))
    import importlib

    import aidot_cameras.camera.protocol as P

    return importlib.reload(P)


def test_stable_sets_are_cached_and_injected(proto):
    """Unchanged behaviour: a camera with steady parameter sets still benefits."""
    proto._save_sprop(DEV, "AAAA,BBBB")
    assert proto._load_sprop(DEV) == "AAAA,BBBB"
    assert "sprop-parameter-sets=AAAA,BBBB" in proto._inject_sprop(SDP, DEV)

    # Seeing the same sets again must not trip the instability guard.
    proto._save_sprop(DEV, "AAAA,BBBB")
    assert not proto._sprop_is_unstable(DEV)
    assert "sprop-parameter-sets=AAAA,BBBB" in proto._inject_sprop(SDP, DEV)


def test_changed_sets_disable_injection_for_that_camera(proto):
    """The defect: sets that change between sessions must stop being injected."""
    proto._save_sprop(DEV, "AAAA,BBBB")
    proto._save_sprop(DEV, "CCCC,DDDD")          # camera changed its sets

    assert proto._sprop_is_unstable(DEV)
    assert proto._load_sprop(DEV) is None
    assert "sprop-parameter-sets" not in proto._inject_sprop(SDP, DEV)


def test_instability_is_sticky(proto):
    """Once unstable, later captures must not silently re-enable injection."""
    proto._save_sprop(DEV, "AAAA,BBBB")
    proto._save_sprop(DEV, "CCCC,DDDD")
    proto._save_sprop(DEV, "EEEE,FFFF")

    assert proto._sprop_is_unstable(DEV)
    assert proto._load_sprop(DEV) is None
    assert "sprop-parameter-sets" not in proto._inject_sprop(SDP, DEV)


def test_other_cameras_are_unaffected(proto):
    """The marker is per device - one bad camera must not disable the rest."""
    proto._save_sprop(DEV, "AAAA,BBBB")
    proto._save_sprop(DEV, "CCCC,DDDD")          # DEV becomes unstable
    proto._save_sprop("other-cam", "1111,2222")

    assert proto._sprop_is_unstable(DEV)
    assert not proto._sprop_is_unstable("other-cam")
    assert "sprop-parameter-sets=1111,2222" in proto._inject_sprop(SDP, "other-cam")
