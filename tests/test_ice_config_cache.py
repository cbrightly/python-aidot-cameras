"""Unit tests for the HTTP ICE-config cache (honors the server ttl).

The config fetch is ~2s on every cold open; caching it until just before the
server-provided ttl (a Unix epoch on each TURN entry) saves that on re-opens.
These lock the ttl handling so a refactor can't silently cache expired TURN
credentials (which would break remote/relay viewing). No network.
"""
import asyncio
import os
import sys
import time

sys.path.insert(0, os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), "src"))

import aidot_cameras.camera.client as cc


def _bare():
    c = cc.CameraMixin.__new__(cc.CameraMixin)
    c._cached_ice_config = None
    c._ice_config_expiry = 0.0
    return c


def test_caches_until_just_before_ttl_capped_at_1h():
    c = _bare()
    cfg = {"dev": [{"id": "x", "ttl": time.time() + 100_000}]}  # ttl far in the future
    c._cache_ice_config(cfg)
    assert c._cached_ice_config is cfg
    # capped at now+3600 (not the full 100k), and clearly in the future
    assert time.time() + 3500 < c._ice_config_expiry <= time.time() + 3600


def test_no_ttl_is_not_cached():
    c = _bare()
    c._cache_ice_config({"dev": [{"id": "x", "uris": ["turn:host:5349"]}]})
    assert c._cached_ice_config is None


def test_expired_ttl_is_not_cached():
    c = _bare()
    c._cache_ice_config({"dev": [{"id": "x", "ttl": time.time() - 100}]})
    assert c._cached_ice_config is None


def test_bool_ttl_ignored():
    # a JSON `true` must not be read as the epoch 1
    c = _bare()
    c._cache_ice_config({"ttl": True, "dev": []})
    assert c._cached_ice_config is None


def test_fresh_cache_short_circuits_http():
    c = _bare()
    sentinel = {"dev": [{"id": "cached"}]}
    c._cached_ice_config = sentinel
    c._ice_config_expiry = time.time() + 300
    # Returns the cached object without touching the network (no _region/_user_info
    # set, so a real fetch would AttributeError before any HTTP).
    assert asyncio.run(c.async_get_ice_config_http()) is sentinel
