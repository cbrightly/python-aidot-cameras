"""Account client for AiDot accounts that include cameras.

``CameraClient`` extends upstream's ``aidot.client.AidotClient``.  The design
rule is that our code must not be in the path for a device we did not add
support for: a light, plug or switch goes through ``super().get_device_client``
and ends up holding a plain upstream ``DeviceClient``, so no camera code RUNS
in its path.  (The camera package is still imported - importing this module
imports it unconditionally - so the guarantee is about execution, not imports.)

``get_device_client`` is the single dispatch seam - upstream constructs device
clients nowhere else - so it is the only place that decides between a camera
client and upstream's.  Everything else here is account-level behavior the
camera layer and the CLI depend on and upstream does not provide:

* a stable, mutable ``login_info`` dict (see the property below);
* ``serializable_login_info()`` for persisting it;
* ``async_ensure_mqtt_credential()``, which fetches the MQTT password cameras
  need (never persisted - it rotates on every account login);
* ``async_ensure_token()``, the refresh hook camera HTTP calls invoke on a
  21026 "Please login again";
* ``async_get_all_device()`` returning cameras (upstream filters them out);
* discovery and persistent-MQTT teardown.
"""

import asyncio
import json
import logging
import os
import random
from typing import Any, Optional

import aiohttp
from aiohttp import ClientSession

from aidot.client import AidotClient as _UpstreamAidotClient
from aidot.device_client import DeviceClient
from aidot.device_client import DeviceStatusData as _UpstreamDeviceStatusData

# Upstream ships two incompatible shapes of this API and both are live; every
# difference is resolved in _upstream, never inline here.  See that module.
from ._upstream import (
    HAS_TYPED_ACCOUNT,
    DeviceModel,
    account_record,
    account_refresh_token,
    account_region,
    account_token_ttl,
    api_get_devices,
    api_get_houses,
    api_get_products,
    api_refresh_token,
    cancel_pending_reconnect,
)

from .const import (
    CONF_ACCESS_TOKEN,
    CONF_COUNTRY,
    CONF_DEVICE_LIST,
    CONF_ID,
    CONF_IPADDRESS,
    CONF_IS_OWNER,
    CONF_LOGIN_INFO,
    CONF_MODEL_ID,
    CONF_PASSWORD,
    CONF_PRODUCT,
    CONF_PRODUCT_ID,
    CONF_REGION,
    CONF_USERNAME,
    DEFAULT_COUNTRY_NAME,
    DEFAULT_REGION,
    LOGIN_INFO_PERSISTENT_MQTT_KEY,
    LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY,
    RUNTIME_ONLY_LOGIN_INFO_KEYS,
)
from .device_client import CameraDeviceClient
# CARRIED: drop when python-aidot#6 merges - this is upstream's DeviceStatusData
# plus the active_color_mode tracker (see aidot_cameras/device_client.py).
from .device_client import DeviceStatusData as _CarriedStatusData
from .discover import CameraDiscover
from .exceptions import AidotAuthFailed

_LOGGER = logging.getLogger(__name__)

# App ID used by the AiDot web/mobile client for /commons/userConfig and
# other cloud API calls that use owner+token headers.
_CLOUD_APP_ID = "68"

# Every key /commons/userConfig has been seen to return the MQTT password under.
# The typo'd "mqqtPwd" is the vendor's, not ours - see the read below.
_MQTT_PASSWORD_RESPONSE_KEYS = ("mqttPassword", "mqqtPwd", "mqttPwd")

# Strong refs to fire-and-forget tasks: asyncio only keeps weak refs, so a
# discarded task can be garbage-collected mid-flight (see camera/client.py).
_BG_TASKS: set = set()


def _spawn_bg(coro):
    _t = asyncio.ensure_future(coro)
    _BG_TASKS.add(_t)

    def _done(task):
        _BG_TASKS.discard(task)
        # Retrieve the exception so a failing background task doesn't surface as
        # an unhandled "Task exception was never retrieved" at GC time; log it.
        if not task.cancelled():
            exc = task.exception()
            if exc is not None:
                _LOGGER.debug("aidot background task failed: %r", exc, exc_info=exc)

    _t.add_done_callback(_done)
    return _t


def _model_is_camera(model_id: "Optional[str]") -> bool:
    """True for an AiDot IPC (camera) model id, e.g. ``LK.IPC.A001513``."""
    return "IPC" in (model_id or "").upper()


def _is_camera_device(device: dict[str, Any]) -> bool:
    """Camera test against a raw cloud device dict (before any client exists)."""
    return _model_is_camera(device.get(CONF_MODEL_ID))


def _is_camera(device_client: "DeviceClient") -> bool:
    """Camera test against a live device client.

    Cameras get their LAN IP from WebRTC signaling, never from the UDP sweep;
    pushing a discovered IP would trigger light-protocol logins on TCP:10000
    (which can hang - the camera accepts but never answers).
    """
    return _model_is_camera(
        getattr(getattr(device_client, "info", None), "model_id", "")
    )


def _aes_key_is_null(device: Any) -> bool:
    """True when the record carries an aesKey list whose first entry is null.

    Upstream's ``DeviceClient.__init__`` does ``aesKey[0].encode()`` guarded only
    by the truthiness of the list, so ``[None]`` - a real shape for a device that
    was never provisioned with a key - raises AttributeError.  A missing or empty
    aesKey is fine; only this shape is dangerous.
    """
    aes_key = getattr(device, "aesKey", None) if not isinstance(device, dict) else (
        device.get("aesKey")
    )
    return isinstance(aes_key, list) and bool(aes_key) and aes_key[0] is None


def _upstream_can_build(device: dict[str, Any]) -> bool:
    """True when upstream's DeviceClient constructor accepts this record.

    Upstream never meets a null-aesKey record because its own
    ``async_get_all_device`` filters those out; ours must keep cameras, so the
    one precondition upstream relies on is re-checked here for everything else.
    """
    return not _aes_key_is_null(device)


def _include_shared_houses() -> bool:
    """Whether to enumerate houses this account does not own.

    Off by default, matching upstream: a home shared *to* this account is
    someone else's, and listing its devices would change what every Home
    Assistant user with a shared home sees.  Read at call time, never cached
    at import - unset means byte-identical behavior.

    The live-validation harness sets it because the CI account is deliberately
    a secondary one with the cameras shared to it; without this the cloud
    returns the shared house and every camera in it, and the filter below
    throws all of them away.
    """
    return os.environ.get("AIDOT_INCLUDE_SHARED_HOUSES", "0") != "0"


def _without_mqtt_password(data: Any) -> Any:
    """A copy of a userConfig response with every MQTT password removed.

    The password is runtime-only by design (it rotates on every account login),
    so it must not survive inside a nested blob that IS persisted.
    """
    if not isinstance(data, dict):
        return data
    out = {k: v for k, v in data.items() if k not in _MQTT_PASSWORD_RESPONSE_KEYS}
    mqtt_block = out.get("mqtt")
    if isinstance(mqtt_block, dict):
        out["mqtt"] = {
            k: v for k, v in mqtt_block.items() if k not in ("password", "pwd")
        }
    elif isinstance(mqtt_block, str):
        # An opaque JSON string that may embed the password; nothing reads it.
        out.pop("mqtt", None)
    return out


def _survivable_token(
    token: "Optional[dict]",
    username: "Optional[str]",
    password: "Optional[str]",
) -> "Optional[dict]":
    """A stored token upstream's constructor can read without raising.

    Upstream's dict shape indexes four keys directly -
    ``token[CONF_USERNAME]``, ``[CONF_PASSWORD]``, ``[CONF_REGION]``,
    ``[CONF_COUNTRY]`` - so a stored entry missing any of them raises KeyError
    inside ``super().__init__`` and the account never loads at all.  (The typed
    shape used ``update_from_json``, which tolerates a partial dict, so entries
    written by it are not guaranteed to carry all four.)

    That is exactly the cross-shape upgrade path: a config entry persisted while
    the typed upstream was installed, read back after a bump to the dict shape.
    Fill only what is missing, from the explicit arguments where available, and
    leave everything else untouched - a KeyError here is unrecoverable for the
    user, whereas a placeholder region is corrected by the next successful
    login.
    """
    if not isinstance(token, dict):
        return token
    stored = token
    if stored.get(CONF_ID) is None and stored.get(CONF_LOGIN_INFO) is not None:
        stored = stored.get(CONF_LOGIN_INFO) or {}
        if not isinstance(stored, dict):
            return token
    required = {
        CONF_USERNAME: username,
        CONF_PASSWORD: password,
        CONF_REGION: DEFAULT_REGION,
        CONF_COUNTRY: DEFAULT_COUNTRY_NAME,
    }
    missing = {k: v for k, v in required.items() if k not in stored}
    if not missing:
        return token
    _LOGGER.warning(
        "stored account entry is missing %s; filling defaults so the entry "
        "still loads (corrected on next login)",
        ", ".join(sorted(missing)),
    )
    patched = dict(stored)
    patched.update({k: v for k, v in missing.items() if v is not None})
    # Any key still absent would raise; a placeholder is survivable.
    for key in required:
        patched.setdefault(key, "")
    return patched


async def _prefetch_ice_config(dc: "CameraDeviceClient") -> None:
    """Background task: warm the HTTP ICE config cache for a camera."""
    try:
        await dc.async_get_ice_config_http()
    except Exception:
        pass  # best-effort; errors are logged inside async_get_ice_config_http


class CameraClient(_UpstreamAidotClient):
    """AiDot cloud account client with camera support."""

    _discover: "Optional[CameraDiscover]" = None

    def __init__(
        self,
        session: Optional[ClientSession],
        country_code: str | None = None,
        username: str | None = None,
        password: str | None = None,
        token: dict | None = None,
    ) -> None:
        # These must exist BEFORE super().__init__: upstream's constructor ends
        # with self.setup_discover(), and our override reads self.login_info.
        self._login_info: dict[str, Any] = {}
        self._discover = None
        self._refresh_task: "Optional[asyncio.Task]" = None
        # Single-flight guard so a burst of camera 21026s (all pollers at once)
        # coalesces into ONE token refresh / re-login instead of many.
        self._ensure_token_inflight: "Optional[asyncio.Future]" = None
        # Same single-flight idiom for the MQTT credential fetch - see
        # async_ensure_mqtt_credential().
        self._user_config_inflight: "Optional[asyncio.Future]" = None
        # Upstream keeps the session inside CloudApi only; the camera-side
        # /commons/userConfig fetch needs it directly.
        self.session = session

        super().__init__(
            session,
            country_code=country_code,
            username=username,
            password=password,
            token=_survivable_token(token, username, password),
        )

        if token is not None:
            stored = token
            if (
                stored.get(CONF_ID) is None
                and stored.get(CONF_LOGIN_INFO) is not None
            ):
                stored = stored.get(CONF_LOGIN_INFO) or {}
            # UserInformation is a strict dataclass: update_from_json() drops
            # any key it has no field for, which is every camera-only key
            # (mqttClientId, _userConfigRaw).  Carry those across a restart on
            # the login_info dict instead.  The MQTT password is deliberately
            # NOT among them - it is runtime-only, so it is neither written nor
            # read back, and a camera fetches a fresh one on first use.
            self._login_info.update(
                {
                    k: v
                    for k, v in stored.items()
                    if k not in RUNTIME_ONLY_LOGIN_INFO_KEYS
                }
            )
            # Stored token: schedule a proactive refresh shortly after startup.
            # Exact remaining TTL is unknown, so pass a short synthetic TTL
            # (120s -> 78-138s delay) to catch tokens already near expiry.
            _LOGGER.info(
                "CameraClient: stored token loaded, scheduling startup "
                "proactive refresh"
            )
            self._schedule_proactive_refresh(120)

    # ---------------------------------------------------------------- #
    # login_info: a stable, mutable, camera-aware account dict
    # ---------------------------------------------------------------- #

    @property
    def login_info(self) -> dict[str, Any]:
        """The account's login info as ONE dict object, stable for this client.

        The camera layer stores camera-only keys here (mqttClientId, and the
        rotating mqttPassword, which never leaves memory - see
        ``serializable_login_info``) and shares ONE dict across every device
        client on the account so a single persistent-MQTT connection and its
        guarding lock can be cached on it (see ``camera/client.py``'s
        ``_get_persistent_mqtt``).  Both upstream shapes break that, differently:

        * typed shape (0.3.55): ``login_info`` is ``user_info.to_dict()``, a
          fresh ``asdict()`` copy per access, and ``UserInformation`` silently
          drops any key it has no dataclass field for.
        * dict shape (0.3.56): ``login_info`` is a real dict, but upstream
          **rebinds** it wholesale - ``self.login_info = token.copy()`` in
          ``__init__`` and ``self.login_info = response_data`` in
          ``async_post_login`` - which would swap the shared object out from
          under every device client mid-session and strand the live MQTT
          connection on an orphaned dict.

        So keep our own dict and fold upstream's fields into it in place.  The
        object identity never changes for the life of the client, on either
        shape, and rotated tokens still show up.
        """
        login_info = getattr(self, "_login_info", None)
        if login_info is None:
            login_info = {}
            object.__setattr__(self, "_login_info", login_info)
        user_info = getattr(self, "user_info", None)
        if user_info is not None and hasattr(user_info, "to_dict"):
            login_info.update(user_info.to_dict())
        return login_info

    @login_info.setter
    def login_info(self, value: dict[str, Any]) -> None:
        """Absorb an upstream assignment WITHOUT changing the dict's identity.

        Upstream's dict shape assigns to ``login_info`` twice in the normal
        lifecycle, and the second one (``async_post_login``) lands mid-session
        with cameras already holding a reference to the shared dict.  Rebinding
        ``self._login_info`` there would leave the persistent-MQTT connection,
        its ``asyncio.Lock`` and ``mqttClientId`` attached to a dict nothing
        reads any more, and the next camera command would open a SECOND broker
        connection - which the broker answers by dropping the first (it allows
        one per account).

        Updating in place instead keeps one object forever.  The live MQTT
        objects are carried across explicitly because they are, by design,
        absent from anything upstream assigns.

        The MQTT *password* is deliberately NOT carried across.  It is a cache,
        not state: the broker issues a new one on every account login, so a copy
        that survives a re-login is stale by definition, and the one place this
        setter fires mid-session (``async_post_login``) is exactly a re-login.
        Keeping it would recreate the confirmed-live failure where a stale
        credential is preferred over fetching a fresh one and the broker refuses
        every connection forever (rc=134).  Dropping it means the next camera
        that needs the broker calls ``async_ensure_mqtt_credential``, which is
        one HTTP request.
        """
        # The backing dict may not exist yet: upstream's constructor assigns to
        # login_info, and a caller (or a test) may set it on an instance built
        # without going through __init__.  Establish it rather than raising -
        # this setter is the first thing to touch it in that case.
        current = getattr(self, "_login_info", None)
        if current is None:
            current = {}
            object.__setattr__(self, "_login_info", current)

        if value is None:
            current.clear()
            return
        if value is current:
            return
        preserved = {
            k: current[k]
            for k in (
                LOGIN_INFO_PERSISTENT_MQTT_KEY,
                LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY,
            )
            if k in current
        }
        current.clear()
        current.update(value)
        current.update(preserved)

    def serializable_login_info(self) -> dict[str, Any]:
        """A JSON-safe copy of ``login_info`` for persisting to disk/config storage.

        ``login_info`` doubles as the account-shared cache for the
        persistent-MQTT connection and its guarding ``asyncio.Lock`` - live
        runtime objects that raise ``TypeError: Object of type Lock is not JSON
        serializable`` if ever handed to ``json.dump`` directly.  Confirmed
        live: a token-refresh callback that persisted the raw ``login_info``
        dict hit exactly this once a persistent MQTT connection was active.

        Anything that persists ``login_info`` - this library's own standalone
        CLI, or an integration's config-entry storage - should call this instead
        of serializing ``login_info`` directly.

        The MQTT password is stripped here rather than only where it is fetched.
        Confirmed live on a real account: an entry written by an earlier version
        still had the credential nested inside ``_userConfigRaw``, and that blob
        is loaded back verbatim from the stored token, so sanitizing only on the
        way in would carry an already-leaked password forward for the life of the
        install.  Stripping on the way out is the one choke point every persist
        goes through.
        """
        out = {
            k: v
            for k, v in self.login_info.items()
            if k not in RUNTIME_ONLY_LOGIN_INFO_KEYS
        }
        if "_userConfigRaw" in out:
            out["_userConfigRaw"] = _without_mqtt_password(out["_userConfigRaw"])
        return out

    # ---------------------------------------------------------------- #
    # token lifecycle
    # ---------------------------------------------------------------- #

    if HAS_TYPED_ACCOUNT:
        def _on_token_refreshed(self) -> None:
            """Sync the rotated token into login_info, then run upstream's hook.

            The typed shape writes the new token to ``user_info.accessToken``, a
            dataclass field; the camera layer reads it out of the shared
            ``login_info`` dict, which only picks it up when the property getter
            runs.  Every refresh funnels through this callback - the reactive
            one behind ``CloudApi.get()``'s 401 retry as well as our
            ``async_ensure_token()`` - so this is the one place that guarantees
            a camera never retries with the token that just expired.

            The sync must happen BEFORE ``super()``, which fires
            ``_token_fresh_cb``: that callback is what persists the account, and
            it reads ``login_info``.

            Defined conditionally because upstream's dict shape has no such
            hook, and there it needs none: ``async_refresh_token`` writes the
            new token straight into ``self.login_info`` - our shared dict, via
            the property - and calls ``_token_fresh_cb`` itself, so the sync
            this method exists to guarantee has already happened by then.  What
            IS lost on that shape is the proactive-refresh rescheduling below;
            ``_reschedule_after_refresh`` covers it from the callers instead.
            """
            _ = self.login_info  # the getter IS the sync; see the property above
            super()._on_token_refreshed()
            self._schedule_proactive_refresh(self._token_ttl())

    def _reschedule_after_refresh(self) -> None:
        """Re-arm the proactive refresh timer after a token rotation.

        On the typed shape ``_on_token_refreshed`` already does this and this is
        a no-op second call (``_schedule_proactive_refresh`` cancels any pending
        task first, so re-arming is idempotent).  On the dict shape there is no
        refresh hook to hang it on, so this is the only thing that keeps the
        proactive cycle running past the first rotation - without it a
        long-running account would refresh once and then only ever discover
        expiry reactively, as a 21026 mid-stream.
        """
        self._schedule_proactive_refresh(self._token_ttl())

    def _token_ttl(self) -> int:
        """Access-token lifetime in seconds, defaulting to the server's 7200."""
        return account_token_ttl(self, 7200)

    def _schedule_proactive_refresh(self, expires_in_secs: int) -> None:
        """Schedule a proactive token refresh at 90% of the token's TTL.

        Upstream refreshes only reactively, when a cloud GET comes back
        TOKEN_EXPIRED.  A camera session can hold a stream open for hours
        without issuing such a GET, so it would only discover the expiry as a
        21026 mid-stream; refreshing ahead of time avoids that stall.
        """
        if self._refresh_task and not self._refresh_task.done():
            self._refresh_task.cancel()
        delay = max(60.0, expires_in_secs * 0.9 + random.uniform(-30, 30))
        _LOGGER.debug(
            "Proactive token refresh scheduled in %.0f s (TTL=%d s)",
            delay, expires_in_secs,
        )

        async def _refresh_after_delay():
            try:
                await asyncio.sleep(delay)
                await self.async_ensure_token()
                _LOGGER.debug("Proactive token refresh complete")
            except asyncio.CancelledError:
                pass
            except Exception as exc:
                _LOGGER.warning("Proactive token refresh failed: %s", exc)

        try:
            loop = asyncio.get_running_loop()
            self._refresh_task = loop.create_task(_refresh_after_delay())
        except RuntimeError:
            pass  # no running loop (e.g. called from sync test context)

    async def async_ensure_token(self) -> bool:
        """Force a fresh access token for camera/smarthome HTTP calls.

        Single-flight: concurrent callers (a burst of camera 21026s) share one
        in-flight refresh instead of each starting their own re-login.
        """
        inflight = self._ensure_token_inflight
        if inflight is not None and not inflight.done():
            return await inflight
        fut = asyncio.get_running_loop().create_future()
        self._ensure_token_inflight = fut
        try:
            result = await self._do_ensure_token()
            fut.set_result(result)
            return result
        except Exception as exc:
            fut.set_exception(exc)
            raise
        finally:
            if self._ensure_token_inflight is fut:
                self._ensure_token_inflight = None
            # When nobody else joined this single flight, the future's exception
            # is never consumed and asyncio logs "exception was never retrieved"
            # at GC time.  The caller already got it re-raised above; retrieve it
            # here so the future dies clean.
            if fut.done() and not fut.cancelled():
                fut.exception()

    async def _do_ensure_token(self) -> bool:
        """Refresh the token (refresh-token first, then headless full re-login).

        On the typed shape both paths update ``user_info``, which is a
        dataclass, so neither one reaches ``login_info`` - the dict every device
        client holds - on its own; ``_on_token_refreshed`` does that sync for
        the refresh path and ``_async_fetch_user_config`` for the re-login path.
        On the dict shape upstream writes straight into ``login_info`` and the
        sync is already done.  Either way this method only picks which path to
        run and reports whether a token is now in hand.
        """
        try:
            if account_refresh_token(self):
                if await api_refresh_token(self) is not None:
                    self._reschedule_after_refresh()
                    return True
        except AidotAuthFailed:
            pass
        except Exception as exc:
            _LOGGER.debug("async_ensure_token: refresh failed: %s", exc)
        try:
            await self.async_post_login()
            if self._token_fresh_cb:
                self._token_fresh_cb()
            return True
        except Exception as exc:
            _LOGGER.warning("async_ensure_token: re-login failed: %s", exc)
            return False

    async def async_post_login(self) -> dict[str, Any]:
        """Upstream login, plus the camera-only MQTT credential fetch."""
        await super().async_post_login()
        # The MQTT password is NOT in the login response; it needs a second
        # call.  Cameras cannot reach the broker without it.
        await self._async_fetch_user_config()
        self._schedule_proactive_refresh(self._token_ttl())
        return self.login_info

    async def async_ensure_mqtt_credential(self) -> None:
        """Fetch a fresh MQTT password, coalescing concurrent callers into one call.

        Camera clients call this whenever the shared ``login_info`` holds no MQTT
        password, which is the normal state after a restart from a stored token
        (the password is never persisted) and after the broker refused the one we
        had.  Several cameras can hit that at once - a stream open racing a
        command publish - and only the ``_get_persistent_mqtt`` path holds a lock,
        so without coalescing they would each fire their own ``userConfig``
        request and each rotate the credential out from under the others.
        """
        fut = self._user_config_inflight
        if fut is not None:
            await asyncio.shield(fut)
            return
        loop = asyncio.get_running_loop()
        fut = self._user_config_inflight = loop.create_future()
        try:
            await self._async_fetch_user_config()
            if not fut.done():
                fut.set_result(None)
        except BaseException as exc:
            if not fut.done():
                fut.set_exception(exc)
            raise
        finally:
            if self._user_config_inflight is fut:
                self._user_config_inflight = None
            # Nobody may have joined this flight, in which case the exception is
            # never retrieved and asyncio logs about it at GC time.  Consume it.
            if fut.done() and not fut.cancelled():
                fut.exception()

    async def _async_fetch_user_config(self) -> None:
        """Fetch /commons/userConfig and store mqttPassword in login_info.

        The MQTT password for wss://{region}-mqtt.arnoo.com:8443/mqtt is
        returned by this endpoint (it changes on each login; only one MQTT
        connection is allowed at a time).
        """
        login_info = self.login_info
        user_id = login_info.get(CONF_ID) or ""
        token = login_info.get(CONF_ACCESS_TOKEN) or ""
        if not user_id or not token:
            _LOGGER.warning("_async_fetch_user_config: missing id or accessToken")
            return
        if self.session is None:
            _LOGGER.warning("_async_fetch_user_config: no HTTP session")
            return

        # Imported here, not at module scope: aidot_cameras.camera.__init__
        # re-exports camera.client, which imports back through device_client -
        # a top-level import would close that cycle during package init.
        from .camera.constants import aidot_api_base

        region = account_region(self)
        url = f"{aidot_api_base(region)}/commons/userConfig"
        headers = {
            "appid": _CLOUD_APP_ID,
            "owner": user_id,
            "token": token,
            "terminal": "app",
            "locale": "en-US",
            "accept": "application/json, text/plain, */*",
        }
        try:
            async with self.session.get(
                url, headers=headers, timeout=aiohttp.ClientTimeout(total=10)
            ) as resp:
                body = await resp.json(content_type=None)
            _LOGGER.debug(
                "userConfig response keys=%s",
                list(body.keys()) if isinstance(body, dict) else type(body).__name__,
            )
            data = body if isinstance(body, dict) else {}
            if isinstance(data.get("data"), dict):
                data = data["data"]
            # Always store the raw response for the camera clients to inspect -
            # minus the credential.  Only mqttClientId is ever read back out of
            # it, and this dict IS persisted (it is not a runtime-only key), so
            # keeping the password here would write it to storage one level below
            # the key that was deliberately stripped.
            login_info["_userConfigRaw"] = _without_mqtt_password(data)
            # Password may be at top level OR nested under an 'mqtt' subkey.
            mqtt_block = data.get("mqtt") or {}
            if isinstance(mqtt_block, str):
                try:
                    mqtt_block = json.loads(mqtt_block)
                except Exception:
                    mqtt_block = {}
            pwd = (
                data.get("mqttPassword")
                or data.get("mqqtPwd")
                or data.get("mqttPwd")
                or mqtt_block.get("password")
                or ""
            )
            if pwd:
                login_info["mqttPassword"] = pwd
                _LOGGER.info(
                    "_async_fetch_user_config: mqttPassword stored (len=%d)", len(pwd)
                )
            else:
                # Do NOT log `body`: the userConfig response often still carries
                # the live mqttPassword under an unexpected key, which is exactly
                # when this branch fires. Log the key names only.
                _LOGGER.warning(
                    "_async_fetch_user_config: mqttPassword not found in response. "
                    "keys=%s",
                    list(data.keys()),
                )
            client_id = data.get("mqttClientId") or mqtt_block.get("clientId") or ""
            if client_id:
                login_info["mqttClientId"] = client_id
                _LOGGER.info(
                    "_async_fetch_user_config: mqttClientId stored: %s", client_id
                )
        except Exception as exc:
            _LOGGER.warning("_async_fetch_user_config failed: %s", exc)

    # ---------------------------------------------------------------- #
    # device listing
    # ---------------------------------------------------------------- #

    async def async_get_all_device(self) -> dict[str, Any]:
        """Return every owned device, with its product metadata merged in.

        Overridden because upstream's version keeps only ``type == "light"``
        devices that carry an aesKey - which drops every camera on the account -
        and returns a dict keyed by device id rather than the
        ``{CONF_DEVICE_LIST: [...]}`` shape the CLI and integrations consume.
        The HTTP calls themselves are still upstream's ``CloudApi``.

        Houses this account does not own are skipped unless
        ``AIDOT_INCLUDE_SHARED_HOUSES`` is set - see
        [[_include_shared_houses]].
        """
        final_device_list: list[dict[str, Any]] = []
        # Devices upstream's client cannot construct (no usable aesKey): Zigbee
        # sub-devices, remotes, and other accessories that were never supported
        # here.  They are counted and reported once, not warned about
        # individually - an account with a dozen Zigbee sensors would otherwise
        # log a dozen WARNINGs on every refresh for devices nothing was ever
        # going to use.
        unbuildable: dict[str, int] = {}
        houses = await api_get_houses(self) or []
        include_shared = _include_shared_houses()
        for house in houses:
            if house.get(CONF_IS_OWNER) is False and not include_shared:
                continue
            device_list = await api_get_devices(self, house[CONF_ID]) or []
            for device in device_list:
                if _is_camera_device(device) or _upstream_can_build(device):
                    final_device_list.append(device)
                else:
                    model = device.get(CONF_MODEL_ID) or "unknown"
                    unbuildable[model] = unbuildable.get(model, 0) + 1
                    _LOGGER.debug(
                        "skipping device %s (%s): no usable aesKey, upstream's "
                        "device client cannot be built for it",
                        device.get(CONF_ID), model,
                    )
        if unbuildable:
            _LOGGER.info(
                "skipped %d device(s) with no usable aesKey (not supported here): %s",
                sum(unbuildable.values()),
                ", ".join(f"{m} x{n}" for m, n in sorted(unbuildable.items())),
            )

        product_ids = ",".join(
            sorted(
                {
                    d[CONF_PRODUCT_ID]
                    for d in final_device_list
                    if d.get(CONF_PRODUCT_ID)
                }
            )
        )
        if product_ids:
            product_list = await api_get_products(self, product_ids) or []
            product_map = {p[CONF_ID]: p for p in product_list}
            for device in final_device_list:
                product = product_map.get(device.get(CONF_PRODUCT_ID))
                if product is not None:
                    device[CONF_PRODUCT] = product

        # Share the full device ID list with every CAMERA client so that
        # batchGetDeviceUserInfo is called with all IDs (the server may return
        # empty results when only a single device ID is sent).  Non-camera
        # clients are pure upstream and have no such field.
        all_ids = [d.get(CONF_ID) for d in final_device_list if d.get(CONF_ID)]
        for device_client in self._device_clients.values():
            if _is_camera(device_client):
                device_client._all_device_ids = all_ids

        return {CONF_DEVICE_LIST: final_device_list}

    # ---------------------------------------------------------------- #
    # THE dispatch seam
    # ---------------------------------------------------------------- #

    def get_device_client(self, device: dict[str, Any]) -> "DeviceClient":
        """Return the cached client for ``device``, creating one if needed.

        This is the only place device clients are constructed, upstream or
        here, and therefore the only place that decides which class to use.
        Non-cameras take the ``super()`` path and get a plain upstream
        ``DeviceClient``: no camera code RUNS in their path and no camera
        behavior is attached to them.  (Importing this module still pulls the
        camera package in - the guarantee is about execution, not imports.)
        """
        if not _is_camera_device(device):
            device_client = super().get_device_client(device)
            # CARRIED: drop when python-aidot#6 merges
            self._carry_active_color_mode(device_client)
            return device_client

        # Camera path.  Cache key and model parsing match upstream exactly.
        _device: DeviceModel = DeviceModel.from_json(data=device)
        if _aes_key_is_null(_device):
            # Cameras legitimately come back with aesKey [None]; upstream's
            # constructor would raise on it (see _aes_key_is_null).  Blank it on
            # OUR copy of the typed model only - the camera layer reads the key
            # from the raw dict, which is passed through untouched below - so the
            # inherited constructor takes its "no key" path, as the pre-inversion
            # client's own `if key_string is not None` guard did.
            _device.aesKey = []
        device_client = self._device_clients.get(_device.id)
        if device_client is None:
            # raw_device / login_info are NOT optional in practice: DeviceModel
            # and UserInformation are closed dataclasses, so a to_dict() round
            # trip drops every camera field (the whole `properties` block) and
            # detaches the account-shared login_info dict the camera layer
            # mutates in place.  Pass both originals through.
            device_client = CameraDeviceClient(
                _device,
                account_record(self),
                raw_device=device,
                login_info=self.login_info,
            )
            # Let the camera HTTP calls force a token refresh on 21026
            # ("Please login again") and retry.
            device_client.set_token_refresh_cb(self.async_ensure_token)
            # And let them fetch an MQTT password on demand.  It is never
            # persisted (it rotates on every account login), so on a restart from
            # a stored token nothing has fetched one yet.
            device_client.set_mqtt_credential_refresh_cb(
                self.async_ensure_mqtt_credential
            )
            self._device_clients[_device.id] = device_client
            # Pre-warm the ICE config cache so stream open does not block on it.
            _spawn_bg(_prefetch_ice_config(device_client))

        # Started lazily here as well: __init__ cannot start discovery when the
        # client is constructed outside a running event loop (stored-token path).
        if self._discover is None:
            self.setup_discover()

        # Deliberately NO update_ip_address() here, which is the one thing this
        # branch drops from upstream's version.  Cameras do not answer the
        # broadcast sweep; their LAN IP comes from the WebRTC signaling host
        # candidate (iceCandidateReq).  Pushing a swept IP would make the
        # inherited update_ip_address kick off a light-protocol login on
        # TCP:10000, which a camera accepts and then never answers.
        return device_client

    # CARRIED: drop when python-aidot#6 merges
    def _carry_active_color_mode(self, device_client: "DeviceClient") -> None:
        """Give RGBW+CCT bulbs - and only them - the PR #6 color-mode tracker.

        Upstream's ``DeviceClient.__init__`` binds its own module-global
        ``DeviceStatusData``, which has no ``active_color_mode``; subclassing or
        re-exporting the class cannot change what a plain upstream client
        constructs.  So the status object is explicitly reassigned after
        construction, which leaves the device client itself exactly upstream's.
        Safe here: upstream's ``get_device_client`` has just returned and,
        within this synchronous call, nothing else can hold a reference to the
        status it built.

        ``enable_rgbw`` is upstream's own RGBW+CCT capability flag (it sets
        ``enable_cct`` alongside it for Identity.RGBW), so plain CCT-only and
        dimmer-only devices are untouched and stay 100% upstream.
        """
        info = getattr(device_client, "info", None)
        if info is None or not getattr(info, "enable_rgbw", False):
            return
        status = getattr(device_client, "status", None)
        if status is None or isinstance(status, _CarriedStatusData):
            return  # already carried (get_device_client is called repeatedly)
        if type(status) is not _UpstreamDeviceStatusData:
            # Upstream started using its own subclass: leave it alone rather
            # than silently dropping whatever it added.  Warn, not debug - this
            # means RGBW+CCT bulbs lost the color-mode tracking and will report
            # a stale color, which is otherwise invisible at runtime.
            _LOGGER.warning(
                "active_color_mode: unexpected status class %s, not carried",
                type(status).__name__,
            )
            return
        carried = _CarriedStatusData()
        carried.__dict__.update(status.__dict__)
        device_client.status = carried

    # ---------------------------------------------------------------- #
    # discovery + teardown
    # ---------------------------------------------------------------- #

    def setup_discover(self) -> None:
        """Start the LAN discovery sweep once login info is available.

        Replaces upstream's body rather than extending it: upstream drives
        discovery through ``Discover.set_user_info``, which starts a
        process-wide timer with no stop hook and no camera gate.  Calling both
        would double-broadcast, so ``super().setup_discover()`` is deliberately
        not called.  Discovered addresses still land in the same
        ``Discover.DISCOVERED_DEVICE`` map upstream reads (see discover.py).
        """
        if self.login_info.get(CONF_ID) is None:
            return
        if self._discover is not None:
            return

        def _discover_callback(dev_id: str, event: dict[str, str]) -> None:
            device_ip = event[CONF_IPADDRESS]
            device_client = self._device_clients.get(dev_id)
            if device_client is not None and not _is_camera(device_client):
                device_client.update_ip_address(device_ip)

        try:
            self._discover = CameraDiscover(self.login_info, _discover_callback)
            self._discover.start_repeat_broadcast()
        except RuntimeError:
            # No running event loop (sync construction); retried on next call.
            self._discover = None

    async def async_close(self) -> None:
        """Close the client and release resources."""
        if self._refresh_task is not None and not self._refresh_task.done():
            self._refresh_task.cancel()
        self._refresh_task = None
        if self._discover is not None:
            self._discover.close()
            self._discover = None
        # CARRIED: drop when upstream cancels its reconnect timer on close
        # Upstream's DeviceClient.reset() arms a delayed re-login whenever a
        # connection drops, and close() only sets its closed flag - which stops
        # reset() from arming a NEW one but never cancels the one already
        # ticking.  So a light re-opens its TCP connection about 45 seconds
        # after the account was closed, leaking a socket, a receive task and a
        # ping timer past integration unload.  Cameras are shielded by their own
        # async_login gate; plain upstream device clients are not.  Cancel
        # before super(), which clears the cache.
        #
        # The handle is spelled `_reconnect_timer` on the typed shape and
        # `_reconnect_handle` on the dict shape, so this goes through
        # cancel_pending_reconnect rather than a getattr that would silently
        # find nothing on one of them and report success.
        for device_client in self._device_clients.values():
            try:
                cancel_pending_reconnect(device_client)
            except Exception:
                _LOGGER.debug("reconnect timer cancel failed", exc_info=True)
        # Upstream closes and clears the device clients.
        await super().async_close()
        # Close the account-shared persistent MQTT connection, if one was opened.
        # It is stashed on the shared login_info by the camera command/attr
        # paths; closing it here stops its background paho loop.
        pm = self._login_info.pop(LOGIN_INFO_PERSISTENT_MQTT_KEY, None)
        # Drop the lock too, so the asyncio.Lock does not linger on the
        # account-shared login_info after close.
        self._login_info.pop(LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY, None)
        if pm is not None:
            try:
                pm.close()
            except Exception:
                _LOGGER.debug("persistent mqtt close failed", exc_info=True)

    def cleanup(self) -> None:
        """Sync entry point: fire-and-forget async_close()."""
        _spawn_bg(self.async_close())


# Back-compat alias: the CLI and integrations import ``AidotClient`` from the
# library's client module.  Rebinding the name here shadows the upstream class
# for importers of THIS module only; the base class above is bound to the real
# upstream one under its private alias.
AidotClient = CameraClient


__all__ = [
    "AidotClient",
    "CameraClient",
    "_is_camera",
    "_is_camera_device",
    "_spawn_bg",
]
