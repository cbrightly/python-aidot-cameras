"""Unit tests for the device-user-info cache.

`async_get_device_user_info` (batchGetDeviceUserInfo) is re-fetched on every open
and, on a warm reopen (auth/url/ice-config all cached), is the ONLY remaining
uncached HTTP round-trip (~0.2-0.5s). The extracted numeric userId/uuid are
account-static; the LAN-IP field is vestigial. So a short fixed-TTL cache is
safe. These lock the cache-store + short-circuit without a network. No network.
"""
import asyncio
import os
import sys
import time

sys.path.insert(0, os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

import aidot.camera.client as cc


def _bare():
    c = cc.CameraMixin.__new__(cc.CameraMixin)
    c._cached_device_user_info = None
    c._device_user_info_expiry = 0.0
    c.device_id = "TESTDEV"
    return c


def test_store_caches_extracted_item_with_ttl():
    c = _bare()
    item = {"userId": 42, "userUuid": "u"}
    assert c._store_device_user_info(item) is item
    assert c._cached_device_user_info is item
    # bounded by the fixed TTL (300s), comfortably in the future
    assert time.time() + 250 < c._device_user_info_expiry <= time.time() + 300


def test_store_does_not_cache_falsy():
    # a failed/empty extraction must not poison the cache (fail-safe to re-fetch)
    c = _bare()
    assert c._store_device_user_info(None) is None
    assert c._cached_device_user_info is None


def test_fresh_cache_short_circuits_http():
    c = _bare()
    sentinel = {"userId": 7}
    c._cached_device_user_info = sentinel
    c._device_user_info_expiry = time.time() + 120
    # No _aidot_v21_base/_aidot_headers set, so a real fetch would error first;
    # returning the sentinel proves it never hit the network.
    assert asyncio.run(c.async_get_device_user_info()) is sentinel


def test_expired_cache_does_not_short_circuit():
    c = _bare()
    c._cached_device_user_info = {"userId": 7}
    c._device_user_info_expiry = time.time() - 1  # expired
    # Proceeds past the cache to the fetch path; no HTTP attrs set -> the method's
    # own except returns None. The key point: it does NOT return the stale item.
    assert asyncio.run(c.async_get_device_user_info()) is None
