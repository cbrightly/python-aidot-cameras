"""The LAN-login ceiling has to reach the devices that actually storm.

The 15,376-failure run was six LIGHTS, not a camera.  A bounded-retry policy
that only exists on `CameraDeviceClient` cannot apply to them: `get_device_client`
sends every non-camera down a different construction path, so no camera-class
override is ever in their MRO.  A validation run against a build that carried
exactly that shape produced 26,229 login errors and zero occurrences of either
new log line - the fix was present in the source and unreachable at runtime.

So the unit under test here is NOT the delay policy (tests/test_login_reconnect_backoff.py
already covers that function, and covered it while it was unreachable).  It is
the DISPATCH: that a light which cannot log in gets the bounded behaviour, and
that it gets it without any camera code entering its path.
"""
import asyncio
import os
import pathlib
import sys

import pytest

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from aidot.device_client import DeviceClient as _UpstreamDeviceClient  # noqa: E402

from aidot_cameras import lan_retry  # noqa: E402
from aidot_cameras.client import CameraClient  # noqa: E402
from aidot_cameras.device_client import (  # noqa: E402
    _LOGIN_RETRY_LIMIT,
    CameraDeviceClient,
)


def _device(dev_id, model_id):
    return {
        "id": dev_id,
        "name": dev_id,
        "modelId": model_id,
        "aesKey": ["k" * 16],
        "password": "pw",
        "product": {"serviceModules": [
            {"identity": "control.light.cct",
             "properties": [{"minValue": "2700", "maxValue": "6500"}]},
        ]},
    }


# One of the six devices from the storming run, by model shape: a light.
STORMING_LIGHT = _device("0dc8fcf9bb74", "lk.WIFI-CCTLight-D0001")


def _dispatch(device):
    """Build a network-free client and run the dispatch seam for `device`.

    A running loop is required: the seam ends in `update_ip_address`, which
    spawns a login task.  Discovery stays off because `setup_discover` returns
    early while the account has no user id, so nothing reaches the network.
    """
    async def _run():
        client = CameraClient(None, country_code="US")
        return client.get_device_client(device)

    return asyncio.run(_run())


def test_a_light_that_cannot_log_in_is_eventually_left_alone():
    """The ceiling has to fire for a light, which is what storms.

    Upstream's `_schedule_reconnect` spawns a login task every time, forever.
    Counting spawns is the honest measure: it is what the 15,376 number counted.
    """
    async def _run():
        dc = CameraClient(None, country_code="US").get_device_client(STORMING_LIGHT)
        # No IP: upstream's async_login returns immediately, so a spawned retry
        # task can never reach the network from this test.
        dc._ip_address = None
        spawned = 0
        for _ in range(_LOGIN_RETRY_LIMIT + 5):
            dc._login_task = None
            dc._schedule_reconnect()
            handle = getattr(dc, "_reconnect_handle", None)
            if handle is not None:
                handle.cancel()
            if dc._login_task is None:
                break
            dc._login_task.cancel()
            spawned += 1
        return spawned

    spawned = asyncio.run(_run())
    assert spawned == _LOGIN_RETRY_LIMIT, (
        f"a light spawned {spawned} login retries; the ceiling is "
        f"{_LOGIN_RETRY_LIMIT}. An unbounded count here is the storm."
    )


def test_the_retry_policy_module_never_imports_the_camera_package():
    """The structural half of the guarantee, checked where it can be broken.

    `test_no_camera_code_runs_in_a_lights_path` checks the MRO, which catches a
    camera BASE CLASS.  It cannot catch a camera symbol called from inside one
    of the mixin's methods.  This reads the module's own imports instead, so the
    guarantee fails at the edit rather than at the next live run.
    """
    import ast

    source = pathlib.Path(lan_retry.__file__).read_text()
    offenders = []
    for node in ast.walk(ast.parse(source)):
        if isinstance(node, ast.ImportFrom):
            # level>0 is a relative import; module is None for `from . import x`.
            name = ("." * node.level) + (node.module or "")
            if name.startswith((".camera", "aidot_cameras.camera")):
                offenders.append(name)
        elif isinstance(node, ast.Import):
            offenders += [a.name for a in node.names
                          if a.name.startswith("aidot_cameras.camera")]

    assert offenders == [], (
        f"{lan_retry.__name__} imports the camera package: {offenders}. It is "
        "mixed into every light's client; a camera import here puts camera "
        "code in a light's path."
    )


def test_no_camera_code_runs_in_a_lights_path():
    """The guarantee the dispatch seam's docstring exists to protect.

    Bounding a light's retries must not be paid for by attaching the camera
    surface to it.  Nothing on a light's MRO may come from the camera package.
    """
    dc = _dispatch(STORMING_LIGHT)
    assert not isinstance(dc, CameraDeviceClient)
    camera_classes = [
        cls for cls in type(dc).__mro__
        if cls.__module__.startswith("aidot_cameras.camera")
    ]
    assert camera_classes == [], (
        f"camera classes reached a light's MRO: {camera_classes}"
    )


def test_a_discovered_address_still_reaches_a_light():
    """The light branch no longer calls upstream's factory, so it owns this.

    Upstream's `get_device_client` ends by pushing the discovered LAN IP into
    the client; a light with no IP never logs in at all.  Our branch reproduces
    that, and reads the address through `CameraDiscover.discovered_device`,
    which resolves to the shared class dict or the per-instance one depending on
    which upstream is installed - so this holds on both shapes.
    """
    async def _run():
        client = CameraClient(None, country_code="US")
        client.login_info = {"id": "u1"}
        client.setup_discover()
        assert client._discover is not None, "discovery did not start"
        try:
            client._discover.discovered_device[STORMING_LIGHT["id"]] = "192.0.2.10"
            dc = client.get_device_client(STORMING_LIGHT)
            return dc._ip_address
        finally:
            client._discover.close()
            client._discover = None

    assert asyncio.run(_run()) == "192.0.2.10"


def test_a_light_that_recovers_starts_its_next_failure_run_from_zero():
    """Without this, an intermittent light climbs to the ceiling and stops.

    The ceiling counts CONSECUTIVE failures.  A device that drops once an hour
    and recovers must never accumulate its way to being abandoned.
    """
    async def _run():
        dc = CameraClient(None, country_code="US").get_device_client(STORMING_LIGHT)
        dc._ip_address = "192.0.2.10"
        dc._login_attempt = _LOGIN_RETRY_LIMIT - 1

        async def _connects(ip_address):
            dc._connect_and_login = True

        dc.connect = _connects
        await dc.async_login()
        return dc._login_attempt

    assert asyncio.run(_run()) == 0


def test_a_light_that_stops_answering_does_not_park_the_attempt():
    """A parked connect wedges the device permanently - worse than a failure.

    `connect()`'s `finally: self._connecting = False` never runs while the read
    is parked, so the in-flight guard blocks every future login attempt and the
    socket stays open. Four of six devices ended a live run in that state.
    """
    async def _never_answers(self, ip_address):
        """Upstream's connect, parked exactly where the real one parks.

        The real park is inside `readexactly(8)` after a successful TCP
        handshake, with `_connecting` already True and `finally` not yet run.
        """
        self._connecting = True
        await asyncio.Event().wait()

    async def _run():
        dc = CameraClient(None, country_code="US").get_device_client(STORMING_LIGHT)
        with pytest.MonkeyPatch.context() as mp:
            mp.setattr(_UpstreamDeviceClient, "connect", _never_answers)
            # The deadline is a class attribute so a caller (and this test) can
            # shorten it without reaching into module globals.
            mp.setattr(type(dc), "_login_connect_timeout_s", 0.05, raising=False)
            await asyncio.wait_for(dc.connect("192.0.2.10"), 2.0)
        return dc._connecting

    connecting = asyncio.run(_run())
    assert connecting is False, (
        "the abandoned attempt left _connecting True; every future login is "
        "now blocked by the in-flight guard"
    )
