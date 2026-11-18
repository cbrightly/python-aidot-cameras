"""Per-camera DTLS certificate pins, learned on first use (camera/dtls_pins.py).

What these guard:

* a camera's first certificate is recorded and pinned only after
  LEARN_HANDSHAKES handshakes in a row present it, so a camera that makes a
  new certificate every session is never locked out;
* once enforced, any other certificate (or none) fails the handshake, and the
  stored pin is not replaced by whoever presented it;
* AIDOT_DTLS_PINNED_FP still wins, and AIDOT_DTLS_PIN_LEARN=0 turns learning
  off;
* a refusal is named as such: AidotDtlsPinMismatch from the open and
  ``dtls_pin_mismatch`` in stream health, not a generic ICE or open failure;
* nothing logged carries more than a short fingerprint.
"""

import asyncio
import logging

import pytest

from aidot_cameras import (
    AidotDtlsPinMismatch,
    CameraClient,
    DtlsPinStore,
    MemoryDtlsPinStore,
)
from aidot_cameras import client as client_mod
from aidot_cameras.camera import dtls_pins
from aidot_cameras.camera import stream_health as sh
from aidot_cameras.camera.client import _open_failure_reason
from aidot_cameras.camera.dtls_pins import (
    LEARN_HANDSHAKES,
    DtlsPinMismatch,
    check_learned_pin,
)
from aidot_cameras.camera.webrtc_open import (
    _camera_cert_acceptor,
    _learned_pin_store,
    _warn_unpinned_peer,
)

CERT_A = ":".join(["AB", "CD", "EF", "01"] * 8)
CERT_B = ":".join(["12", "34", "56", "78"] * 8)
A = CERT_A.replace(":", "").lower()
B = CERT_B.replace(":", "").lower()
DEV = "dev-test-1"


def _learn(store, fp=CERT_A, times=LEARN_HANDSHAKES):
    for _ in range(times):
        check_learned_pin(store, DEV, fp)


# --- the rules -----------------------------------------------------------


def test_first_certificate_is_recorded_not_enforced():
    store = MemoryDtlsPinStore()
    check_learned_pin(store, DEV, CERT_A)
    assert store.get(DEV) == {
        "fingerprint": A,
        "seen": 1,
        "enforced": False,
        "changes": 0,
    }


def test_pin_is_enforced_after_enough_matching_handshakes():
    store = MemoryDtlsPinStore()
    _learn(store, times=LEARN_HANDSHAKES - 1)
    assert store.get(DEV)["enforced"] is False
    check_learned_pin(store, DEV, CERT_A)
    assert store.get(DEV)["enforced"] is True


def test_enforced_pin_refuses_another_certificate_and_keeps_the_pin():
    store = MemoryDtlsPinStore()
    _learn(store)
    before = store.get(DEV)
    with pytest.raises(DtlsPinMismatch):
        check_learned_pin(store, DEV, CERT_B)
    assert store.get(DEV) == before
    # The real camera's certificate still passes afterwards.
    check_learned_pin(store, DEV, CERT_A)


def test_enforced_pin_refuses_a_missing_certificate():
    store = MemoryDtlsPinStore()
    _learn(store)
    with pytest.raises(DtlsPinMismatch, match="no certificate"):
        check_learned_pin(store, DEV, None)


def test_a_mismatch_is_a_value_error_so_the_handshake_fails():
    assert issubclass(DtlsPinMismatch, ValueError)


def test_a_change_while_learning_starts_again():
    store = MemoryDtlsPinStore()
    _learn(store, times=LEARN_HANDSHAKES - 1)
    check_learned_pin(store, DEV, CERT_B)
    assert store.get(DEV) == {
        "fingerprint": B,
        "seen": 1,
        "enforced": False,
        "changes": 1,
    }


def test_a_camera_with_a_new_certificate_every_session_is_never_locked_out(caplog):
    store = MemoryDtlsPinStore()
    caplog.set_level(logging.INFO, logger=dtls_pins.__name__)
    for i in range(20):
        fp = ":".join([f"{i:02X}"] * 32)
        check_learned_pin(store, DEV, fp)  # never raises
    assert store.get(DEV)["enforced"] is False
    warnings = [r for r in caplog.records if r.levelno == logging.WARNING]
    assert len(warnings) == 1  # said once, not on every open
    assert "keeps changing" in warnings[0].getMessage()


def test_missing_certificate_while_learning_is_accepted_and_not_counted():
    store = MemoryDtlsPinStore()
    check_learned_pin(store, DEV, None)
    assert store.get(DEV) is None


def test_fingerprint_format_does_not_matter():
    store = MemoryDtlsPinStore()
    check_learned_pin(store, DEV, CERT_A)
    check_learned_pin(store, DEV, A)
    check_learned_pin(store, DEV, f"  {A.upper()} ")
    assert store.get(DEV)["enforced"] is True


@pytest.mark.parametrize(
    "bad",
    [
        "not a dict",
        {},
        {"fingerprint": ""},
        {"fingerprint": 5, "enforced": True},
    ],
)
def test_a_malformed_record_starts_learning_again(bad):
    store = MemoryDtlsPinStore()
    store._records[DEV] = bad
    check_learned_pin(store, DEV, CERT_A)
    assert store.get(DEV)["fingerprint"] == A
    assert store.get(DEV)["enforced"] is False


def test_only_a_literal_true_enforces():
    store = MemoryDtlsPinStore({DEV: {"fingerprint": A, "seen": 1, "enforced": "yes"}})
    check_learned_pin(store, DEV, CERT_B)  # not enforced: accepted
    assert store.get(DEV)["fingerprint"] == B


def test_logs_carry_only_short_fingerprints(caplog):
    store = MemoryDtlsPinStore()
    caplog.set_level(logging.DEBUG)
    _learn(store)
    with pytest.raises(DtlsPinMismatch) as info:
        check_learned_pin(store, DEV, CERT_B)
    text = caplog.text + str(info.value)
    assert A not in text and B not in text
    assert A[:8] in text and B[:8] in text


def test_records_are_json_safe():
    import json

    store = MemoryDtlsPinStore()
    _learn(store)
    record = store.get(DEV)
    assert json.loads(json.dumps(record)) == record


def test_memory_store_hands_out_copies():
    store = MemoryDtlsPinStore()
    check_learned_pin(store, DEV, CERT_A)
    store.get(DEV)["enforced"] = True
    assert store.get(DEV)["enforced"] is False
    assert isinstance(store, DtlsPinStore)
    assert store.as_dict() == {DEV: store.get(DEV)}


# --- the handshake hook --------------------------------------------------


class _Fp:
    def __init__(self, algorithm, value):
        self.algorithm = algorithm
        self.value = value


class _Params:
    def __init__(self):
        self.fingerprints = [_Fp("sha-256", "echoed")]


class _Ssl:
    def __init__(self, cert):
        self.cert = cert

    def get_peer_certificate(self, as_cryptography=True):
        return self.cert


class _Transport:
    def __init__(self, cert):
        self._ssl = _Ssl(cert)


def _fp_fn(cert, alg):
    assert alg == "sha-256"
    return cert


def _handshake(acceptor, cert):
    params = _Params()
    acceptor(_Transport(cert), params)
    return params


def test_acceptor_learns_and_then_refuses():
    store = MemoryDtlsPinStore()
    refused = []
    acc = _camera_cert_acceptor("", DEV, _Fp, _fp_fn, store, lambda: refused.append(1))
    for _ in range(LEARN_HANDSHAKES):
        params = _handshake(acc, CERT_A)
        assert params.fingerprints[0].value == CERT_A
    assert store.get(DEV)["enforced"] is True
    with pytest.raises(ValueError):
        _handshake(acc, CERT_B)
    assert refused == [1]


def test_acceptor_counts_one_handshake_per_transport():
    store = MemoryDtlsPinStore()
    acc = _camera_cert_acceptor("", DEV, _Fp, _fp_fn, store)
    transport = _Transport(CERT_A)
    for _ in range(5):
        acc(transport, _Params())
    assert store.get(DEV)["seen"] == 1


def test_env_pin_wins_over_the_store():
    store = MemoryDtlsPinStore()
    _learn(store, fp=CERT_B)  # store says B
    refused = []
    acc = _camera_cert_acceptor(
        CERT_A, DEV, _Fp, _fp_fn, store, lambda: refused.append(1)
    )
    assert _handshake(acc, CERT_A).fingerprints[0].value == CERT_A
    with pytest.raises(ValueError, match="mismatch"):
        _handshake(acc, CERT_B)
    assert refused == [1]


def test_unreadable_certificate_fails_closed_once_enforced():
    store = MemoryDtlsPinStore()
    _learn(store)
    refused = []

    def _boom(cert, alg):
        raise RuntimeError("digest failed")

    acc = _camera_cert_acceptor("", DEV, _Fp, _boom, store, lambda: refused.append(1))
    with pytest.raises(RuntimeError):
        _handshake(acc, CERT_A)
    assert refused == [1]


def test_unreadable_certificate_while_learning_is_accepted():
    store = MemoryDtlsPinStore()

    def _boom(cert, alg):
        raise RuntimeError("digest failed")

    acc = _camera_cert_acceptor("", DEV, _Fp, _boom, store)
    _handshake(acc, CERT_A)  # no raise: the previous accept-any behaviour
    assert store.get(DEV) is None


def test_no_store_keeps_the_previous_accept_any_behaviour():
    acc = _camera_cert_acceptor("", DEV, _Fp, _fp_fn, None)
    assert _handshake(acc, CERT_A).fingerprints[0].value == CERT_A
    assert _handshake(acc, CERT_B).fingerprints[0].value == CERT_B


class _Cam:
    device_id = DEV

    def __init__(self, store):
        self.dtls_pin_store = store


def test_learning_can_be_turned_off(monkeypatch):
    store = MemoryDtlsPinStore()
    monkeypatch.delenv("AIDOT_DTLS_PIN_LEARN", raising=False)
    assert _learned_pin_store(_Cam(store)) is store
    monkeypatch.setenv("AIDOT_DTLS_PIN_LEARN", "0")
    assert _learned_pin_store(_Cam(store)) is None
    assert _learned_pin_store(_Cam(None)) is None
    assert _learned_pin_store(_Cam(object())) is None


def test_open_warning_is_quiet_while_learning(caplog):
    caplog.set_level(logging.DEBUG)
    _warn_unpinned_peer(DEV, MemoryDtlsPinStore())
    assert not [r for r in caplog.records if r.levelno >= logging.WARNING]
    caplog.clear()
    _warn_unpinned_peer(DEV, None)
    assert [r for r in caplog.records if r.levelno == logging.WARNING]


# --- naming the failure ----------------------------------------------------


def test_open_failure_reason_names_a_pin_mismatch():
    assert _open_failure_reason(AidotDtlsPinMismatch("x")) == sh.END_DTLS_PIN_MISMATCH
    assert sh.END_DTLS_PIN_MISMATCH in sh.STREAM_END_REASONS


def _mixin():
    import aidot_cameras.camera.client as cc

    cls = next(
        v
        for v in vars(cc).values()
        if isinstance(v, type) and "async_open_webrtc_stream" in v.__dict__
    )
    cam = cls.__new__(cls)
    cam.device_id = DEV
    return cam


def test_an_open_refused_on_the_pin_raises_the_pin_error():
    cam = _mixin()

    async def _impl(*args, _abort_holder=None, **kwargs):
        cam._dtls_pin_refused = True  # what the handshake hook does
        raise RuntimeError("ICE failed")

    cam._async_open_webrtc_stream_impl = _impl
    with pytest.raises(AidotDtlsPinMismatch) as info:
        asyncio.run(cam.async_open_webrtc_stream())
    assert isinstance(info.value.__cause__, RuntimeError)


def test_the_flag_is_cleared_for_the_next_open():
    cam = _mixin()
    cam._dtls_pin_refused = True
    session = object()

    async def _impl(*args, _abort_holder=None, **kwargs):
        raise RuntimeError("ICE failed")

    cam._async_open_webrtc_stream_impl = _impl
    with pytest.raises(RuntimeError):
        asyncio.run(cam.async_open_webrtc_stream())

    async def _ok(*args, _abort_holder=None, **kwargs):
        return session

    cam._async_open_webrtc_stream_impl = _ok
    cam._note_direct_session = lambda s: None
    assert asyncio.run(cam.async_open_webrtc_stream()) is session


# --- the client and the camera ---------------------------------------------


def test_client_refuses_a_store_without_the_methods():
    with pytest.raises(TypeError):
        CameraClient(None, country_code="US", dtls_pin_store={"x": 1})


def _device_clients(monkeypatch, raw_device, **kwargs):
    async def _no_prefetch(_dc):
        return None

    monkeypatch.setattr(client_mod, "_prefetch_ice_config", _no_prefetch)

    async def run():
        c = CameraClient(None, country_code="US", **kwargs)
        c.login_info = {"id": "u1"}
        dev = raw_device("A000088")
        return c, c.get_device_client(dict(dev))

    return asyncio.run(run())


def test_each_client_gets_its_own_memory_store(monkeypatch, raw_device):
    c1, dc1 = _device_clients(monkeypatch, raw_device)
    c2, dc2 = _device_clients(monkeypatch, raw_device)
    assert isinstance(c1.dtls_pin_store, MemoryDtlsPinStore)
    assert dc1.dtls_pin_store is c1.dtls_pin_store
    assert c1.dtls_pin_store is not c2.dtls_pin_store


def test_a_host_store_reaches_the_camera_and_forget_clears_it(monkeypatch, raw_device):
    class HostStore:
        def __init__(self):
            self.data = {}
            self.saves = 0

        def get(self, device_id):
            return self.data.get(device_id)

        def set(self, device_id, record):
            self.data[device_id] = dict(record)
            self.saves += 1

        def delete(self, device_id):
            self.data.pop(device_id, None)
            self.saves += 1

    host = HostStore()
    _c, dc = _device_clients(monkeypatch, raw_device, dtls_pin_store=host)
    assert dc.dtls_pin_store is host
    assert dc.dtls_pin is None
    for _ in range(LEARN_HANDSHAKES):
        check_learned_pin(host, str(dc.device_id), CERT_A)
    assert dc.dtls_pin["enforced"] is True
    dc._dtls_pin_refused = True
    dc.forget_dtls_pin()
    assert dc.dtls_pin is None
    assert dc._dtls_pin_refused is False


def test_a_failing_host_store_never_fails_the_handshake():
    class Broken:
        def get(self, device_id):
            raise ValueError("storage unavailable")

        def set(self, device_id, record):
            raise OSError("disk full")

        def delete(self, device_id):
            raise OSError("disk full")

    acc = _camera_cert_acceptor("", DEV, _Fp, _fp_fn, Broken())
    assert _handshake(acc, CERT_A).fingerprints[0].value == CERT_A

    class WriteOnlyBroken(MemoryDtlsPinStore):
        def set(self, device_id, record):
            raise OSError("disk full")

    check_learned_pin(WriteOnlyBroken(), DEV, CERT_A)


def _signaled(value):
    params = _Params()
    params.fingerprints = [_Fp("sha-256", value)]
    return params


def test_a_signaled_fingerprint_is_still_checked_while_learning():
    # An answer that carried a real fingerprint was checked by aiortc before
    # the learned pin replaced its validator; that check stays.
    store = MemoryDtlsPinStore()
    acc = _camera_cert_acceptor("", DEV, _Fp, _fp_fn, store, verify_signaled=True)
    params = _signaled(CERT_A)
    acc(_Transport(CERT_A), params)
    assert store.get(DEV)["fingerprint"] == A
    with pytest.raises(ValueError, match="signaled"):
        acc(_Transport(CERT_B), _signaled(CERT_A))
    with pytest.raises(ValueError, match="signaled"):
        acc(_Transport(None), _signaled(CERT_A))


def test_the_empty_fingerprint_quirk_skips_the_signaled_check():
    store = MemoryDtlsPinStore()
    acc = _camera_cert_acceptor("", DEV, _Fp, _fp_fn, store, verify_signaled=False)
    zero = ":".join(["00"] * 32)
    assert _handshake(acc, CERT_A).fingerprints[0].value == CERT_A
    acc(_Transport(CERT_A), _signaled(zero))  # placeholder never matches; ignored
