"""The aidot integration."""

import asyncio
import json
import logging
import base64
import aiohttp
from aiohttp import ClientSession
from typing import Any, Callable, Optional
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives.asymmetric import padding

from .exceptions import AidotAuthFailed, AidotUserOrPassIncorrect
from .device_client import DeviceClient
from .const import (
    APP_ID,
    BASE_URL,
    PUBLIC_KEY_PEM,
    CONF_ACCESS_TOKEN,
    CONF_APP_ID,
    CONF_CODE,
    CONF_COUNTRY,
    CONF_DEVICE_LIST,
    CONF_ID,
    CONF_PASSWORD,
    CONF_PRODUCT,
    CONF_PRODUCT_ID,
    CONF_REFRESH_TOKEN,
    CONF_REGION,
    CONF_TERMINAL,
    CONF_USERNAME,
    DEFAULT_COUNTRY_NAME,
    SUPPORTED_COUNTRYS,
    DEFAULT_COUNTRY_CODE,
    CONF_IS_OWNER,
    ServerErrorCode,
)

_LOGGER = logging.getLogger(__name__)

# App ID used by the AiDot web/mobile client for /commons/userConfig and
# other cloud API calls that use owner+token headers.
_CLOUD_APP_ID = "68"


def rsa_password_encrypt(message: str) -> str:
    """RSA-encrypt the password using the AiDot public key (for loginWithFreeVerification)."""
    public_key = serialization.load_pem_public_key(
        PUBLIC_KEY_PEM, backend=default_backend()
    )
    encrypted = public_key.encrypt(message.encode("utf-8"), padding.PKCS1v15())
    return base64.b64encode(encrypted).decode("utf-8")



class AidotClient:
    _base_url: str = BASE_URL
    _region: str = "us"
    session: Optional[ClientSession] = None
    username: str = ""
    password: str = ""
    country_name: str = DEFAULT_COUNTRY_NAME
    country_code: str = DEFAULT_COUNTRY_CODE
    _device_clients: dict[str, DeviceClient]

    def __init__(
        self,
        session: Optional[ClientSession],
        country_code: str | None = None,
        username: str | None = None,
        password: str | None = None,
        token: dict | None = None,
    ) -> None:
        self.session = session
        self.username = username
        self.password = password
        self.country_code = country_code
        self.login_info: dict[str, Any] = {}
        self._token_fresh_cb: Optional[Callable] = None
        self._device_clients = {}
        # Single-flight guard so a burst of camera 21026s (all 7 pollers at once)
        # coalesces into ONE token refresh / re-login instead of 7 concurrent ones.
        self._ensure_token_inflight: "Optional[asyncio.Future]" = None
        for item in SUPPORTED_COUNTRYS:
            if item["id"] == self.country_code:
                self.country_name = item["name"]
                self._region = item["region"].lower()
                self._base_url = f"https://prod-{self._region}-api.arnoo.com/v17"
                break
        if token is not None:
            self.login_info = token.copy()
            self.username = token[CONF_USERNAME]
            self.password = token[CONF_PASSWORD]
            self._region = token[CONF_REGION]
            self.country_name = token[CONF_COUNTRY]
            self._base_url = f"https://prod-{self._region}-api.arnoo.com/v17"

    def set_token_fresh_cb(self, callback) -> None:
        self._token_fresh_cb = callback

    def get_identifier(self) -> str:
        return f"{self._region}-{self.username}"

    def update_password(self, password: str) -> None:
        self.password = password

    async def async_post_login(self) -> dict[str, Any]:
        """Login via loginWithFreeVerification (RSA-encrypted password)."""
        url = f"{self._base_url}/users/loginWithFreeVerification"
        headers = {CONF_APP_ID: APP_ID, CONF_TERMINAL: "app"}
        data = {
            "countryKey": f"region:{self.country_name.strip()}",
            "username":   self.username,
            "password":   rsa_password_encrypt(self.password),
            "terminalId": "gvz3gjae10l4zii00t7y0",
            "webVersion": "0.5.0",
            "area":       "Asia/Shanghai",
            "UTC":        "UTC+8",
        }

        response_data: dict = {}
        try:
            response = await self.session.post(url, headers=headers, json=data)
            response_data = await response.json(content_type=None)
            _LOGGER.debug("async_post_login HTTP=%d response: %s", response.status, response_data)
            app_code = response_data.get(CONF_CODE)
            if app_code == ServerErrorCode.USER_PWD_INCORRECT:
                raise AidotUserOrPassIncorrect
            if response.status >= 400:
                raise aiohttp.ClientResponseError(
                    response.request_info, response.history,
                    status=response.status,
                    message=f"HTTP {response.status}: {response_data}",
                )
            # Update in place (clear + update) rather than rebinding, so any
            # DeviceClient that captured this dict reference sees the refreshed
            # token instead of holding a stale copy (camera HTTP calls otherwise
            # 21026 "Please login again" after a re-login).
            self.login_info.clear()
            self.login_info.update(response_data)
            self.login_info[CONF_PASSWORD] = self.password
            self.login_info[CONF_REGION] = self._region
            self.login_info[CONF_COUNTRY] = self.country_name
            self.login_info[CONF_USERNAME] = self.username
            # Fetch MQTT password from /commons/userConfig (separate call required).
            await self._async_fetch_user_config()
            return self.login_info
        except AidotUserOrPassIncorrect:
            raise
        except aiohttp.ClientError as e:
            _LOGGER.info("async_post_login ClientError %s  response=%s", e, response_data)
            raise

    async def _async_fetch_user_config(self) -> None:
        """Fetch /commons/userConfig and store mqttPassword in login_info.

        The MQTT password for wss://{region}-mqtt.arnoo.com:8443/mqtt is returned
        by this endpoint (changes on each login; only one MQTT connection allowed at once).
        """
        user_id = self.login_info.get(CONF_ID) or ""
        token   = self.login_info.get(CONF_ACCESS_TOKEN) or ""
        if not user_id or not token:
            _LOGGER.warning("_async_fetch_user_config: missing id or accessToken")
            return

        base = f"https://prod-{self._region}-api.arnoo.com"
        url  = f"{base}/commons/userConfig"
        headers = {
            "appid":    _CLOUD_APP_ID,
            "owner":    user_id,
            "token":    token,
            "terminal": "app",
            "locale":   "en-US",
            "accept":   "application/json, text/plain, */*",
        }
        try:
            async with self.session.get(url, headers=headers,
                                        timeout=aiohttp.ClientTimeout(total=10)) as resp:
                body = await resp.json(content_type=None)
            _LOGGER.debug("userConfig response: %s", body)
            # The MQTT password field may be named mqttPassword or similar.
            # Store the full response data alongside login_info for DeviceClient.
            data = body if isinstance(body, dict) else {}
            if isinstance(data.get("data"), dict):
                data = data["data"]
            # Always store the raw response for DeviceClient inspection.
            self.login_info["_userConfigRaw"] = data
            # Password may be at top level OR nested under 'mqtt' subkey.
            mqtt_block = data.get("mqtt") or {}
            if isinstance(mqtt_block, str):
                try:
                    mqtt_block = json.loads(mqtt_block)
                except Exception:
                    mqtt_block = {}
            pwd = (data.get("mqttPassword")
                   or data.get("mqqtPwd")
                   or data.get("mqttPwd")
                   or mqtt_block.get("password")
                   or "")
            if pwd:
                self.login_info["mqttPassword"] = pwd
                _LOGGER.info("_async_fetch_user_config: mqttPassword stored (len=%d)", len(pwd))
            else:
                _LOGGER.warning(
                    "_async_fetch_user_config: mqttPassword not found in response. "
                    "keys=%s  body=%s", list(data.keys()), body
                )
            # Also extract MQTT clientId if provided.
            client_id = data.get("mqttClientId") or mqtt_block.get("clientId") or ""
            if client_id:
                self.login_info["mqttClientId"] = client_id
                _LOGGER.info("_async_fetch_user_config: mqttClientId stored: %s", client_id)
        except Exception as exc:
            _LOGGER.warning("_async_fetch_user_config failed: %s", exc)

    async def async_refresh_token(self) -> dict[str, Any]:
        url = f"{self._base_url}/users/refreshToken"
        headers = {"appid": _CLOUD_APP_ID, "terminal": "app"}
        data = {
            CONF_REFRESH_TOKEN: self.login_info[CONF_REFRESH_TOKEN],
        }

        response_data: dict = {}
        try:
            response = await self.session.post(url, headers=headers, json=data)
            response_data = await response.json()
            response.raise_for_status()
            self.login_info[CONF_ACCESS_TOKEN] = response_data[CONF_ACCESS_TOKEN]
            if response_data[CONF_REFRESH_TOKEN] is not None:
                self.login_info[CONF_REFRESH_TOKEN] = response_data[CONF_REFRESH_TOKEN]
            _LOGGER.debug("refresh token ok  code=%s", response_data.get(CONF_CODE))
            if self._token_fresh_cb:
                self._token_fresh_cb()
            return response_data
        except aiohttp.ClientError as e:
            _LOGGER.info("async_refresh_token ClientError %s  code=%s", e, response_data.get(CONF_CODE))
            if response_data.get(CONF_CODE) == ServerErrorCode.LOGIN_INVALID:
                raise AidotAuthFailed
            return None

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
        except Exception as exc:  # noqa: BLE001
            fut.set_exception(exc)
            raise
        finally:
            if self._ensure_token_inflight is fut:
                self._ensure_token_inflight = None

    async def _do_ensure_token(self) -> bool:
        """Refresh the token (refresh-token first, then headless full re-login).

        Updates ``login_info`` in place so DeviceClients see the new token, and
        fires the token-fresh callback so HA persists it. Returns True on success.
        """
        try:
            if self.login_info.get(CONF_REFRESH_TOKEN):
                if await self.async_refresh_token() is not None:
                    return True
        except AidotAuthFailed:
            pass
        except Exception as exc:  # noqa: BLE001
            _LOGGER.debug("async_ensure_token: refresh failed: %s", exc)
        try:
            await self.async_post_login()
            if self._token_fresh_cb:
                self._token_fresh_cb()
            return True
        except Exception as exc:  # noqa: BLE001
            _LOGGER.warning("async_ensure_token: re-login failed: %s", exc)
            return False

    async def async_session_get(
        self, params: str, headers: dict[str, str] | None = None,
        _retry: bool = True,
    ) -> dict[str, Any]:
        url = f"{self._base_url}{params}"
        token = self.login_info[CONF_ACCESS_TOKEN]
        if token is None:
            raise AidotAuthFailed()
        user_id = self.login_info.get(CONF_ID) or ""
        if headers is None:
            headers = {
                "appid":    _CLOUD_APP_ID,
                "owner":    user_id,
                "token":    token,
                "terminal": "app",
                "locale":   "en-US",
            }
        response_data = {}
        try:
            response = await self.session.get(url, headers=headers)
            response_data = await response.json()
            response.raise_for_status()
            return response_data
        except aiohttp.ClientError as e:
            _LOGGER.info("async_get ClientError %s %s", e, response_data)
            code = response_data.get(CONF_CODE)
            if code == ServerErrorCode.TOKEN_EXPIRED:
                if not _retry:
                    raise AidotAuthFailed
                await self.async_refresh_token()
                return await self.async_session_get(params, _retry=False)
            elif code in (ServerErrorCode.LOGIN_INVALID, 21027, 21041):
                self.login_info[CONF_ACCESS_TOKEN] = None
                raise AidotAuthFailed
            raise

    async def async_get_products(self, product_ids: str) -> list[dict[str, Any]]:
        """Get device list."""
        params = f"/products/{product_ids}"
        return await self.async_session_get(params)

    async def async_get_devices(self, house_id: str) -> list[dict[str, Any]]:
        """Get device list."""
        params = f"/devices?houseId={house_id}"
        return await self.async_session_get(params)

    async def async_get_houses(self) -> list[dict[str, Any]]:
        """Get house list."""
        params = "/houses"
        return await self.async_session_get(params)

    async def async_get_all_device(self) -> dict[str, Any]:
        final_device_list: list[dict[str, Any]] = []
        houses = await self.async_get_houses()
        for house in houses:
            if house.get(CONF_IS_OWNER) is False:
                continue
            # get device_list
            device_list = await self.async_get_devices(house[CONF_ID])
            if device_list:
                final_device_list.extend(device_list)

        # get product_list
        productIds = ",".join([item[CONF_PRODUCT_ID] for item in final_device_list])
        product_list = await self.async_get_products(productIds)

        for product in product_list:
            for device in final_device_list:
                if device[CONF_PRODUCT_ID] == product[CONF_ID]:
                    device[CONF_PRODUCT] = product

        # Share the full device ID list with every DeviceClient so that
        # batchGetDeviceUserInfo is called with all IDs (the server may return
        # empty results when only a single device ID is sent).
        all_ids = [d.get(CONF_ID) for d in final_device_list if d.get(CONF_ID)]
        for dc in self._device_clients.values():
            dc._all_device_ids = all_ids

        return {CONF_DEVICE_LIST: final_device_list}

    def get_device_client(self, device: dict[str, Any]) -> DeviceClient:
        device_id = device.get(CONF_ID)
        device_client: DeviceClient = self._device_clients.get(device_id)
        if device_client is None:
            device_client = DeviceClient(device, self.login_info)
            # Let the camera HTTP calls force a token refresh on 21026
            # ("Please login again") and retry, like async_session_get does.
            device_client.set_token_refresh_cb(self.async_ensure_token)
            self._device_clients[device_id] = device_client
            asyncio.get_running_loop().create_task(device_client.ping_task())
        # NOTE: no LAN-broadcast discovery here.  For APK parity, the camera's LAN
        # IP comes from the WebRTC signaling host candidate (iceCandidateReq), which
        # the camera advertises and we add verbatim - the official app does the same
        # and never does a UDP discovery sweep.
        return device_client

    async def remove_device_client(self, dev_id: str) -> None:
        device_client: DeviceClient = self._device_clients.get(dev_id)
        if device_client is not None:
            await device_client.close()
            del self._device_clients[dev_id]

    def cleanup(self) -> None:
        for client in self._device_clients.values():
            asyncio.get_running_loop().create_task(client.close())
        self._device_clients.clear()

    async def async_cleanup(self) -> None:
        for client in self._device_clients.values():
            await client.close()
        self._device_clients.clear()
