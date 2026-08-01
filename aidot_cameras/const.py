"""Constants for the camera layer.

Upstream's constants are re-exported explicitly so camera modules have a single
import site, and so a constant that upstream renames or drops fails loudly here
(by name) instead of somewhere deep in the camera code.  Only camera-specific
keys are defined locally.
"""

# Four of these moved between upstream shapes (API_URL_TEMPLATE, APP_ID,
# DEFAULT_REGION, PUBLIC_KEY_PEM live in aidot.login_const on 0.3.56 and in
# aidot.const on 0.3.55).  _upstream resolves that once; the other 31 names
# below are on aidot.const in both, so they are imported directly and still
# fail loudly here, by name, if upstream ever moves one.
from ._upstream import (  # noqa: F401 - deliberate re-export surface
    API_URL_TEMPLATE,
    APP_ID,
    DEFAULT_REGION,
    PUBLIC_KEY_PEM,
)

from aidot.const import (  # noqa: F401 - deliberate re-export surface
    CONF_ACCESS_TOKEN,
    CONF_AES_KEY,
    CONF_APP_ID,
    CONF_ATTR,
    CONF_CCT,
    CONF_COUNTRY,
    CONF_DEVICE_LIST,
    CONF_DIMMING,
    CONF_HARDWARE_VERSION,
    CONF_ID,
    CONF_IDENTITY,
    CONF_IPADDRESS,
    CONF_IS_OWNER,
    CONF_LOGIN_INFO,
    CONF_MAC,
    CONF_MAXVALUE,
    CONF_MINVALUE,
    CONF_MODEL_ID,
    CONF_NAME,
    CONF_ON_OFF,
    CONF_PASSWORD,
    CONF_PAYLOAD,
    CONF_PRODUCT,
    CONF_PRODUCT_ID,
    CONF_PROPERTIES,
    CONF_REFRESH_TOKEN,
    CONF_REGION,
    CONF_RGBW,
    CONF_SERVICE_MODULES,
    CONF_TERMINAL,
    CONF_TOKEN,
    CONF_USERNAME,
    DEFAULT_COUNTRY_CODE,
    DEFAULT_COUNTRY_NAME,
    Identity,
    SUPPORTED_COUNTRY_CODES,
)

BASE_URL = API_URL_TEMPLATE.format(region=DEFAULT_REGION)

# -- Runtime-only login_info keys (camera layer) ------------------------------ #
# The persistent-MQTT client and its lock are stashed on the shared login_info
# dict so every device client reuses one broker connection.  They are live
# runtime objects, never persistable state: anything that serializes login_info
# to disk (this library's CLI, or an integration's config-entry storage) must
# exclude them first - see CameraClient.serializable_login_info().
LOGIN_INFO_PERSISTENT_MQTT_KEY = "_persistent_mqtt"
LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY = "_persistent_mqtt_lock"

# The MQTT password is a CACHE, not state, and must never be persisted.  The
# broker issues a new one on every account login and allows a single connection,
# so a stored copy is stale the moment anything else logs in (the phone app is
# enough).  Persisting it made the failure survive restarts: the credential
# fetch prefers the cached value, so a stale one was reloaded from disk on every
# start and the broker refused it forever (rc=134), which killed camera
# signaling while snapshots kept working.  Keeping it runtime-only means each
# start fetches a fresh one - a single extra HTTP call.
LOGIN_INFO_MQTT_PASSWORD_KEYS = ("mqttPassword", "mqttPwd")

RUNTIME_ONLY_LOGIN_INFO_KEYS = frozenset(
    {LOGIN_INFO_PERSISTENT_MQTT_KEY, LOGIN_INFO_PERSISTENT_MQTT_LOCK_KEY,
     *LOGIN_INFO_MQTT_PASSWORD_KEYS}
)
