"""The keepalive arguments 1.0.0rc44 removed are still accepted, and ignored.

``sdes_skip_turn`` and ``sdes_adaptive`` went with the experiment knobs they
drove. Integration releases up to 2.34.4 still pass ``sdes_skip_turn`` (they
also pass ``sdes_connection_mode``, which is what decides now), and a library
upgrade must not make their ``start_keepalive`` raise a TypeError.
"""

import inspect
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot_cameras.camera.client import CameraMixin


def test_the_arguments_are_still_in_the_signature():
    params = inspect.signature(CameraMixin.start_keepalive).parameters
    assert "sdes_skip_turn" in params and "sdes_adaptive" in params


def test_they_change_nothing():
    class _Cam(CameraMixin):
        is_battery_camera = False

    cam = _Cam.__new__(_Cam)
    cam._resolve_sdes_connection_mode = lambda: "auto"
    cam._sdes_skip_turn_opt = True  # what the old argument used to set
    assert cam._resolve_sdes_skip_turn() is False
