"""Per-camera DTLS certificate pinning, learned on first use.

A DTLS camera (A000088) echoes our own fingerprint in its SDP, or sends an
empty one, so signaling carries nothing to verify the camera's certificate
against. ``AIDOT_DTLS_PINNED_FP`` pins one fingerprint for the whole process,
which suits the CLI and one camera but not a home with several.

This module keeps one pin per camera instead. The first certificate a camera
presents is recorded; once the same certificate has been presented on
``LEARN_HANDSHAKES`` handshakes in a row the pin is ENFORCED, and from then on
any other certificate fails the handshake. A camera whose certificate changes
before that point is still learning: the new certificate replaces the old one
and the count starts again. So a camera that makes a new certificate for every
session never reaches enforcement and streams exactly as it did before, with a
warning that its certificate cannot be pinned; it is never locked out.

Where the pins live is the caller's choice: anything with the ``DtlsPinStore``
methods, passed as ``CameraClient(..., dtls_pin_store=...)``. A record is a
plain JSON-safe dict, so a host such as Home Assistant can keep it in its own
storage. Without one, each ``CameraClient`` keeps its pins in memory for the
life of the process.

After a camera is reset (and so may come back with a new certificate), call
``CameraDeviceClient.forget_dtls_pin()``: the next handshake starts learning
again.

``AIDOT_DTLS_PINNED_FP`` still works and wins over the store when set.
``AIDOT_DTLS_PIN_LEARN=0`` turns learning off (the previous behaviour: accept
the certificate with a warning).

Only certificate fingerprints are handled here. They are digests of a public
certificate, not key material; even so, logs show only their first 8 hex
digits, enough to tell two certificates apart.
"""

from __future__ import annotations

import logging
import threading
from typing import Any, Optional, Protocol, runtime_checkable

_LOGGER = logging.getLogger(__name__)

#: Handshakes in a row that must present the same certificate before the pin
#: is enforced. A camera that changes certificate between sessions never gets
#: there, so learning cannot lock it out.
LEARN_HANDSHAKES = 3

#: Record keys. Every value is a str, int or bool, so a record round-trips
#: through JSON unchanged.
FINGERPRINT = "fingerprint"
SEEN = "seen"
ENFORCED = "enforced"
CHANGES = "changes"


def normalize_fingerprint(value: str) -> str:
    """Lowercase hex with no separators, for comparison and storage.

    Accepts the colon-separated uppercase hex aiortc emits as well as an
    operator-typed value that differs in case, colons or whitespace.
    """
    return value.replace(":", "").replace(" ", "").strip().lower()


def short_fingerprint(value: Optional[str]) -> str:
    """The first 8 hex digits, for log lines."""
    if not value:
        return "<none>"
    return normalize_fingerprint(value)[:8]


@runtime_checkable
class DtlsPinStore(Protocol):
    """Where per-camera pins are kept.

    Called from inside the DTLS handshake, so every method is synchronous and
    should return quickly: a host with asynchronous storage keeps the records
    in memory and schedules its own save from ``set`` and ``delete``.

    ``get`` returns the record ``set`` last stored for ``device_id`` (a dict
    with the keys ``fingerprint``, ``seen``, ``enforced`` and ``changes``), or
    None. Records are plain JSON-safe dicts; treat them as opaque.
    """

    def get(self, device_id: str) -> Optional[dict]: ...

    def set(self, device_id: str, record: dict) -> None: ...

    def delete(self, device_id: str) -> None: ...


class MemoryDtlsPinStore:
    """The default store: a dict, kept for the life of the process."""

    def __init__(self, records: Optional[dict] = None) -> None:
        self._records: dict = {}
        self._lock = threading.Lock()
        for device_id, record in (records or {}).items():
            self.set(device_id, record)

    def get(self, device_id: str) -> Optional[dict]:
        with self._lock:
            record = self._records.get(str(device_id))
            return dict(record) if isinstance(record, dict) else None

    def set(self, device_id: str, record: dict) -> None:
        with self._lock:
            self._records[str(device_id)] = dict(record)

    def delete(self, device_id: str) -> None:
        with self._lock:
            self._records.pop(str(device_id), None)

    def as_dict(self) -> dict:
        """Every record, by device id (a copy), for a host to save."""
        with self._lock:
            return {k: dict(v) for k, v in self._records.items()}


class DtlsPinMismatch(ValueError):
    """An enforced pin did not match the certificate the camera presented.

    A ``ValueError`` so the handshake fails exactly as an env-pin mismatch
    does. Carries only short fingerprints.
    """


def _valid_record(record: Any) -> Optional[dict]:
    """``record`` as a clean dict, or None when it is missing or malformed.

    A malformed record (a host's storage edited by hand, an older format) is
    treated as no record: learning starts again rather than the open failing
    on a stored value nobody can see.
    """
    if not isinstance(record, dict):
        return None
    fp = record.get(FINGERPRINT)
    if not isinstance(fp, str) or not normalize_fingerprint(fp):
        return None
    seen = record.get(SEEN, 0)
    changes = record.get(CHANGES, 0)
    return {
        FINGERPRINT: normalize_fingerprint(fp),
        SEEN: seen if isinstance(seen, int) and seen > 0 else 1,
        ENFORCED: record.get(ENFORCED) is True,
        CHANGES: changes if isinstance(changes, int) and changes > 0 else 0,
    }


def _save(store: DtlsPinStore, device_id: str, record: dict) -> None:
    try:
        store.set(device_id, record)
    except Exception:
        _LOGGER.debug(
            "camera %s: DTLS pin store write failed", device_id, exc_info=True
        )


def check_learned_pin(
    store: DtlsPinStore, device_id: str, presented_fp: Optional[str]
) -> None:
    """Check ``presented_fp`` against ``device_id``'s pin and update the record.

    Returns when the handshake may go on; raises :class:`DtlsPinMismatch`
    (a ``ValueError``) when it must fail. The rules:

    * no record: record this certificate, seen once; accept.
    * same certificate: count it, and enforce the pin at ``LEARN_HANDSHAKES``.
    * different certificate, pin enforced: refuse, and leave the record as it
      is, so whoever is presenting the new certificate cannot replace the pin.
    * different certificate, still learning: replace the record and start
      counting again; accept.
    * no certificate: refuse when enforced, else accept and record nothing.

    A store that fails to read or write is logged at DEBUG and treated as
    holding nothing: the handshake goes on unpinned, as before pinning
    existed, rather than failing on a host's storage error.

    Call it once per handshake.
    """
    device_id = str(device_id)
    try:
        record = _valid_record(store.get(device_id))
    except Exception:
        _LOGGER.debug("camera %s: DTLS pin store read failed", device_id, exc_info=True)
        return

    if presented_fp is None:
        if record is not None and record[ENFORCED]:
            _LOGGER.warning(
                "camera %s: refusing the stream: the camera presented no DTLS "
                "certificate, but its certificate is pinned (%s...)",
                device_id,
                short_fingerprint(record[FINGERPRINT]),
            )
            raise DtlsPinMismatch(
                "DTLS peer presented no certificate; cannot verify against "
                "the camera's pin"
            )
        return

    fp = normalize_fingerprint(presented_fp)

    if record is None:
        _save(store, device_id, {FINGERPRINT: fp, SEEN: 1, ENFORCED: False, CHANGES: 0})
        _LOGGER.info(
            "camera %s: recorded its DTLS certificate (%s...); it is pinned "
            "after %d handshakes in a row present the same one",
            device_id,
            short_fingerprint(fp),
            LEARN_HANDSHAKES,
        )
        return

    if fp == record[FINGERPRINT]:
        if record[ENFORCED]:
            return
        record[SEEN] += 1
        if record[SEEN] >= LEARN_HANDSHAKES:
            record[ENFORCED] = True
            _LOGGER.info(
                "camera %s: DTLS certificate pinned (%s...); a different "
                "certificate now fails the handshake",
                device_id,
                short_fingerprint(fp),
            )
        _save(store, device_id, record)
        return

    if record[ENFORCED]:
        _LOGGER.warning(
            "camera %s: refusing the stream: its DTLS certificate (%s...) does "
            "not match the pinned one (%s...). Someone on the network path may "
            "be intercepting the stream. If the camera was reset or replaced, "
            "clear its pin (forget_dtls_pin) and it is learned again on the "
            "next connect.",
            device_id,
            short_fingerprint(fp),
            short_fingerprint(record[FINGERPRINT]),
        )
        raise DtlsPinMismatch(
            "DTLS fingerprint mismatch: camera presented "
            f"{short_fingerprint(fp)}..., pinned {short_fingerprint(record[FINGERPRINT])}..."
        )

    changes = record[CHANGES] + 1
    _save(
        store, device_id, {FINGERPRINT: fp, SEEN: 1, ENFORCED: False, CHANGES: changes}
    )
    if changes == 1:
        _LOGGER.info(
            "camera %s: DTLS certificate changed before it was pinned "
            "(%s... -> %s...); learning again",
            device_id,
            short_fingerprint(record[FINGERPRINT]),
            short_fingerprint(fp),
        )
    elif changes == 2:
        # Said once per record, not once per open: after the second change
        # this camera very likely makes a new certificate every session.
        _LOGGER.warning(
            "camera %s: DTLS certificate keeps changing between sessions, so "
            "it cannot be pinned and stays unverified. Streams keep working; "
            "AIDOT_DTLS_PINNED_FP does not help a camera like this either.",
            device_id,
        )
