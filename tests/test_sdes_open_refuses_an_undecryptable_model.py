"""An SDES camera outside _PLAIN_RTP_MODELS is refused, not opened blind.

The bridge is the SDES path's only decryptor; the RTP/SAVP branch where ffmpeg
decrypted from the offer's a=crypto keys is gone. The transport is chosen from
the cloud profile (enableSdes), not the model, so a model the library does not
know can still arrive here. It must fail at the head of the open, with an error
that names the model, before an offer goes out.
"""

import asyncio
import queue
import types
from contextlib import ExitStack

import pytest

from aidot_cameras import AidotStreamOpenError
from aidot_cameras.camera.client import CameraMixin
from aidot_cameras.camera.sdes_open import _sdes_open_head


def _camera(model_id):
    return types.SimpleNamespace(
        info=types.SimpleNamespace(model_id=model_id),
        _PLAIN_RTP_MODELS=CameraMixin._PLAIN_RTP_MODELS,
        user_id="u1",
        device_id="dev1",
        live_power_type=1,
    )


def _ps():
    return types.SimpleNamespace(
        _cleanup=ExitStack(), outgoing_q=queue.Queue(), user_id="u1", device_id="dev1"
    )


def test_every_supported_sdes_model_is_one_the_bridge_decrypts():
    assert {"A001064", "A001513"} <= set(CameraMixin._PLAIN_RTP_MODELS)


@pytest.mark.parametrize("model", ["LK.IPC.A001064", "LK.IPC.A001513-1"])
def test_a_supported_model_passes_the_head(model):
    ps = _ps()
    asyncio.run(_sdes_open_head(_camera(model), ps))
    assert ps._use_plain_rtp is True


@pytest.mark.parametrize("model", ["LK.IPC.A001108", ""])
def test_an_unknown_sdes_model_is_refused_with_its_name(model):
    ps = _ps()
    with pytest.raises(AidotStreamOpenError, match="_PLAIN_RTP_MODELS") as exc:
        asyncio.run(_sdes_open_head(_camera(model), ps))
    assert (model or "?") in str(exc.value)
    # The signalling thread is told to stop on the way out, as on any failed
    # open (the wrapper closes the stack when no session was returned).
    ps._cleanup.close()
    assert ps.outgoing_q.get_nowait() is None
