"""Every ``AIDOT_*`` environment variable the library reads, in one place.

Two things live here and nowhere else:

* **The inventory.** :data:`KNOBS` names every variable, its default, how it
  parses and, where it has one, the floor it is clamped to. A test walks the
  package source for ``AIDOT_...`` literals and fails on any name missing
  from this table, and fails on any name in this table missing from the README
  or ``docs/ENVIRONMENT.md``. Adding a knob means adding it here and
  documenting it, or the suite says so.

* **The parser.** :func:`read` turns a variable into a value with one set of
  rules for a malformed one. Before this there were four: a bare ``float()``
  that stopped the library importing on an empty value, a try/except that fell
  back on ``ValueError`` only, a finite-check that also caught ``nan`` (which
  had made two grace rules fire at once), and an ``if raw:`` that treated
  ``"0"`` as set. The union of their defensive cases is the rule now: unset,
  unparseable or non-finite is the default; below the floor is the floor. A
  rejected value is named once at DEBUG, so a typo leaves a trace.

What this deliberately is **not**: a snapshot taken once at start-up. Several
knobs are documented as "read per open, so ``0`` takes effect without a
restart", and Home Assistant users rely on that. Callers that read at import
time keep doing so; callers that read per open keep doing so. Only the lookup
and the parsing moved.

The boolean knobs keep their conventions on purpose (see ``__main__``): one
that defaults on turns off only on a recognised falsy token (``bool_on``); one
that defaults off turns on only on a recognised truthy token (``bool_off``).
Three older shapes are registered as what they are rather than quietly
changed: ``bool_zero`` (anything but the literal ``"0"`` is on, so
``AIDOT_SERVE_RELAY=false`` has meant "on" since it shipped), ``bool_one``
(only the literal ``"1"`` is on) and ``bool_nonempty`` (any non-empty value
is on).

``env`` is injectable everywhere so a test never has to touch ``os.environ``.

Credentials are knobs like any other, marked ``secret``. A secret knob's value
is never logged by this module (a rejected value is named as ``<redacted>``)
and :func:`snapshot`, the one supported way to dump the effective settings for
a diagnostic, reports only whether it is set.
"""

from __future__ import annotations

import logging
import math
import os
from dataclasses import dataclass
from typing import Any, Mapping, Optional

_LOGGER = logging.getLogger(__name__)

KINDS = (
    "float",
    "int",
    "bool_on",
    "bool_off",
    "bool_zero",
    "bool_one",
    "bool_nonempty",
    "bool_on_blank_off",
    "str",
    "list",
)
BOOL_KINDS = (
    "bool_on",
    "bool_off",
    "bool_zero",
    "bool_one",
    "bool_nonempty",
    "bool_on_blank_off",
)

_FALSY = ("0", "false", "no", "off")
_TRUTHY = ("1", "true", "yes", "on")


@dataclass(frozen=True)
class Knob:
    """One environment variable.

    ``kind`` picks the parser; ``default`` is what an unset, unparseable or
    non-finite value reads as; ``minimum`` (numeric kinds only) is a floor the
    value is clamped to, for knobs where a negative would be a bug rather than
    a setting. ``note`` records anything a reader of the table should know
    that the kind does not say, including where a call site still parses the
    raw value itself.
    """

    name: str
    default: Any
    kind: str
    minimum: Optional[float] = None
    #: A value below this reads as the default (not clamped): for knobs whose
    #: shipped rule is "a negative is a mistake, keep the default".
    reject_below: Optional[float] = None
    note: str = ""
    #: A credential (or the path to one). Its value never appears in a log
    #: line from this module or in :func:`snapshot`.
    secret: bool = False


REDACTED = "<redacted>"


def _rejected(knob: Knob, raw: str, why: str) -> None:
    shown = REDACTED if knob.secret else repr(raw)
    _LOGGER.debug("%s=%s %s; using %r", knob.name, shown, why, knob.default)


def _parse_float(raw: str, knob: Knob) -> float:
    try:
        v = float(raw)
    except (TypeError, ValueError):
        _rejected(knob, raw, "is not a number")
        return float(knob.default)
    if not math.isfinite(v):
        _rejected(knob, raw, "is not finite")
        return float(knob.default)
    if knob.reject_below is not None and v < knob.reject_below:
        _rejected(knob, raw, f"is below {knob.reject_below:g}")
        return float(knob.default)
    if knob.minimum is not None and v < knob.minimum:
        return float(knob.minimum)
    return v


def _parse_int(raw: str, knob: Knob) -> int:
    try:
        v = int(raw)
    except (TypeError, ValueError):
        _rejected(knob, raw, "is not an integer")
        return int(knob.default)
    if knob.reject_below is not None and v < knob.reject_below:
        _rejected(knob, raw, f"is below {knob.reject_below:g}")
        return int(knob.default)
    if knob.minimum is not None and v < knob.minimum:
        return int(knob.minimum)
    return v


def read_knob(knob: Knob, env: Optional[Mapping[str, str]] = None) -> Any:
    """The value of ``knob`` from ``env`` (default ``os.environ``)."""
    _env = os.environ if env is None else env
    raw = _env.get(knob.name)
    if raw is None:
        return knob.default
    kind = knob.kind
    if kind == "float":
        return _parse_float(raw, knob)
    if kind == "int":
        return _parse_int(raw, knob)
    if kind == "bool_on":
        return raw.strip().lower() not in _FALSY
    if kind == "bool_on_blank_off":
        return raw.strip().lower() not in (*_FALSY, "")
    if kind == "bool_off":
        return raw.strip().lower() in _TRUTHY
    if kind == "bool_zero":
        return raw != "0"
    if kind == "bool_one":
        return raw == "1"
    if kind == "bool_nonempty":
        return bool(raw)
    if kind == "list":
        return [u.strip() for u in raw.split(",") if u.strip()]
    if kind == "str":
        return raw
    raise ValueError(f"unknown knob kind {kind!r} for {knob.name}")


def read(name: str, env: Optional[Mapping[str, str]] = None) -> Any:
    """The value of the registered knob ``name``. Unknown names raise
    ``KeyError``: an unregistered read is a programming error, not a default."""
    return read_knob(KNOBS[name], env)


def read_or_none(name: str, env: Optional[Mapping[str, str]] = None) -> Any:
    """Like :func:`read`, but ``None`` when the variable is unset, for the
    call sites whose "unset" means something no value can (a default that
    depends on the camera, or on what has been observed)."""
    _env = os.environ if env is None else env
    if _env.get(name) is None:
        return None
    return read_knob(KNOBS[name], _env)


def tristate(name: str, env: Optional[Mapping[str, str]] = None) -> Optional[bool]:
    """A registered boolean knob as True, False or None ("no opinion").

    A recognised truthy token is True and a recognised falsy one is False;
    unset, blank or anything else is None. For a caller that must defer to
    the default wherever it lives rather than apply one (the CLI's per-camera
    options; see ``__main__._env_bool``). Unknown names raise ``KeyError``."""
    knob = KNOBS[name]
    if knob.kind not in BOOL_KINDS:
        raise ValueError(f"{name} is not a boolean knob")
    _env = os.environ if env is None else env
    val = (_env.get(name) or "").strip().lower()
    if val in _TRUTHY:
        return True
    if val in _FALSY:
        return False
    return None


def snapshot(env: Optional[Mapping[str, str]] = None) -> "dict[str, Any]":
    """The effective value of every knob that is set, for a diagnostic dump.

    Unset knobs are left out (their value is the documented default). A secret
    knob that is set reads as ``"<redacted>"``, never as its value."""
    _env = os.environ if env is None else env
    out: "dict[str, Any]" = {}
    for name, knob in KNOBS.items():
        if _env.get(name) is None:
            continue
        out[name] = REDACTED if knob.secret else read_knob(knob, _env)
    return out


def default(name: str) -> Any:
    return KNOBS[name].default


def is_set(name: str, env: Optional[Mapping[str, str]] = None) -> bool:
    """Whether ``name`` is set to a non-empty value, whatever it parses as."""
    _env = os.environ if env is None else env
    return bool(_env.get(name))


def _registry(*knobs: Knob) -> "dict[str, Knob]":
    out: "dict[str, Knob]" = {}
    for k in knobs:
        if k.name in out:
            raise ValueError(f"duplicate knob {k.name}")
        out[k.name] = k
    return out


#: The inventory. Grouped as docs/ENVIRONMENT.md groups them; the README's
#: user-facing table is the first group. Defaults match the call sites they
#: replaced, measured or not. Every read goes through this module.
KNOBS: "dict[str, Knob]" = _registry(
    # --- credentials and the account (README) ------------------------------
    Knob("AIDOT_USERNAME", None, "str", secret=True),
    Knob("AIDOT_PASSWORD", None, "str", secret=True),
    Knob(
        "AIDOT_COUNTRY",
        "US",
        "str",
        note="read per login; an empty value is passed through as empty",
    ),
    Knob("AIDOT_TOKEN_FILE", None, "str", secret=True, note="the CLI's token cache"),
    Knob("AIDOT_CRED_KEY_FILE", None, "str", secret=True, note="Fernet key path"),
    Knob(
        "AIDOT_INCLUDE_SHARED_HOUSES",
        False,
        "bool_zero",
        note="any value but 0 enables",
    ),
    # --- camera streaming / tuning (README) ---------------------------------
    Knob("AIDOT_VIDEO_DECODER", None, "str", note="a decoder name or hwaccel:<method>"),
    Knob("AIDOT_DISABLE_HWACCEL", False, "bool_off"),
    Knob("AIDOT_MAX_CONCURRENT_STREAMS", 3, "int", minimum=1),
    Knob("AIDOT_MAX_CONCURRENT_OPENS", 2, "int", minimum=1),
    Knob(
        "AIDOT_SDES_CONNECTION_MODE",
        None,
        "str",
        note="auto | lan | relay; anything else is auto",
    ),
    Knob("AIDOT_SDES_VIDEO_PT", "96", "str", note="sdes_open.py parses 96 | 97 | none"),
    Knob("AIDOT_PERSISTENT_MQTT", True, "bool_on"),
    Knob("AIDOT_DIRECT_PUBLISH", True, "bool_on"),
    Knob("AIDOT_PUBLISH_AAC", True, "bool_on"),
    Knob("AIDOT_HLS_DIRECT_TS", True, "bool_on"),
    Knob("AIDOT_LOGIN_RETRY_LIMIT", 6, "int"),
    # --- security hardening (README) ----------------------------------------
    Knob("AIDOT_ALLOW_LAN_SERVE", False, "bool_off"),
    Knob("AIDOT_DTLS_PINNED_FP", None, "str"),
    Knob(
        "AIDOT_ALLOW_PLAINTEXT_MQTT",
        False,
        "bool_off",
        note="accept a non-TLS MQTT broker URL from the cloud",
    ),
    Knob(
        "AIDOT_EXTRA_REGIONS",
        [],
        "list",
        note="regions accepted beyond us, eu, jp; each must be a hostname label",
    ),
    Knob(
        "AIDOT_SDES_HOLEPUNCH_HOST",
        None,
        "str",
        note='"" disables the vendor fallback',
    ),
    # --- streaming knobs (ENVIRONMENT.md) -----------------------------------
    Knob(
        "AIDOT_FAST_CONNECT",
        False,
        "bool_off",
        note="DTLS only; per-camera option wins; the CLI also reads it as a tri-state (__main__.py)",
    ),
    Knob("AIDOT_FAST_CONNECT_HOST_ONLY", False, "bool_one"),
    Knob("AIDOT_SDES_FAST_LIVEPLAY", True, "bool_on"),
    Knob("AIDOT_DTLS_FAST_LIVEPLAY", True, "bool_on"),
    Knob(
        "AIDOT_SDES_LIVEPLAY_ECHO_S",
        0.25,
        "float",
        reject_below=0.0,
        note="unset: 0.25 on either liveplay path",
    ),
    Knob(
        "AIDOT_SDES_ECHO_WAIT_S",
        None,
        "str",
        note="sdes_open.py parses; unset means 0.25, or 2.0 once an echo was seen",
    ),
    Knob("AIDOT_ABANDONED_MEDIA_GRACE_S", 8.0, "float", reject_below=0.0),
    Knob("AIDOT_BATTERY_STALE_OFFER_GRACE_S", 15.0, "float"),
    Knob("AIDOT_SDES_UNREACHABLE_NOMINEE_GRACE_S", 20.0, "float"),
    Knob("AIDOT_SDES_ANSWER_PROBE_GRACE_S", 20.0, "float"),
    Knob("AIDOT_SDES_TRIGGER_GRACE_S", 20.0, "float"),
    Knob("AIDOT_PINNED_CODEC_RETRIES", 2, "int", reject_below=0),
    Knob(
        "AIDOT_SDES_SERVE_AUDIO",
        True,
        "bool_on",
        note="the CLI also reads it as a tri-state (__main__.py)",
    ),
    Knob(
        "AIDOT_SDES_NACK", True, "bool_on_blank_off", note="an empty value turns it off"
    ),
    Knob("AIDOT_HLS_TS_PORT", 0, "int", minimum=0),
    Knob("AIDOT_PUBLISH_GAP_WARN_S", 1.0, "float", minimum=0.0),
    Knob("AIDOT_SERVE_RELAY", True, "bool_zero"),
    Knob("AIDOT_DTLS_VIDEO_GRACE_S", 30.0, "float"),
    Knob("AIDOT_DTLS_SERVE_OPEN_TIMEOUT_S", 75.0, "float"),
    Knob("AIDOT_DTLS_SERVE_ICE_WAIT_S", 30.0, "float"),
    Knob("AIDOT_DTLS_FUTILE_VIDEO_LIMIT", 5, "int"),
    Knob("AIDOT_LOGIN_RETRY_CAP_S", 60.0, "float"),
    Knob("AIDOT_LOGIN_CONNECT_TIMEOUT_S", 20.0, "float"),
    Knob("AIDOT_LAN_STABLE_SESSION_S", 60.0, "float", minimum=0.0),
    # --- deeper internals (ENVIRONMENT.md) ----------------------------------
    Knob("AIDOT_STREAM_IDLE_S", 120.0, "float"),
    Knob("AIDOT_BATTERY_UNKNOWN_VIEWER_RELEASE_S", 300.0, "float"),
    Knob("AIDOT_STREAM_STARTUP_GRACE_S", 60.0, "float"),
    Knob("AIDOT_ICE_DISCONNECT_S", 8.0, "float"),
    Knob("AIDOT_DTLS_RETRY_GATE_S", 15.0, "float"),
    Knob("AIDOT_BUSY_RETRY_S", 45.0, "float"),
    Knob("AIDOT_BUSY_BACKOFF_S", 20.0, "float"),
    Knob("AIDOT_OFFLINE_RECHECK_S", 30.0, "float"),
    Knob("AIDOT_OFFLINE_PROBE_S", 600.0, "float"),
    Knob("AIDOT_FUTILE_KEEPALIVE_LIMIT", 5, "int"),
    Knob("AIDOT_DTLS_SLOW_PROBE_THRESHOLD", 5, "int"),
    Knob("AIDOT_DTLS_SLOW_PROBE_INTERVAL_S", 600.0, "float"),
    Knob("AIDOT_DTLS_SLOW_PROBE_LOG_EVERY", 6, "int"),
    # Floored: at 0 or below the slow-probe wait slept 0 s per step and spun.
    Knob("AIDOT_DTLS_SLOW_PROBE_CHUNK_S", 5.0, "float", minimum=0.1),
    Knob("AIDOT_GOP_PLI_S", 2.0, "float"),
    Knob("AIDOT_STALL_PLI_S", 1.0, "float"),
    Knob(
        "AIDOT_SDES_PLI_GAPS", ["0", "1.5", "2", "3"], "list", note="each entry a float"
    ),
    Knob("AIDOT_SDES_STALL_NUDGE", True, "bool_zero"),
    Knob("AIDOT_SDES_STALL_NUDGE_AFTER_S", 2.5, "float"),
    Knob("AIDOT_SDES_AUDIO_GAIN_DB", -8.0, "float"),
    Knob("AIDOT_AUDIO_TARGET_DBFS", -15.0, "float"),
    Knob("AIDOT_AUDIO_MAXGAIN_DB", 30.0, "float"),
    Knob("AIDOT_AUDIO_MINGAIN_DB", -12.0, "float"),
    Knob("AIDOT_AUDIO_GATE_DBFS", -45.0, "float"),
    Knob(
        "AIDOT_SPROP_DIR",
        None,
        "str",
        note="the library's state directory",
    ),
    Knob("AIDOT_REMB_TARGET_BPS", 0, "int"),
    Knob("AIDOT_HEALTH_LINE_S", 60.0, "float", minimum=0.0, note="0 disables"),
    # --- the ffmpeg serve and the recordings (ENVIRONMENT.md) ---------------
    Knob("AIDOT_SERVE_REORDER_QUEUE", 500, "int"),
    Knob(
        "AIDOT_SERVE_MAX_DELAY_US",
        500000,
        "int",
        note="also the SDES publisher's reorder budget",
    ),
    Knob(
        "AIDOT_SERVE_INPUT_TIMEOUT_S",
        None,
        "str",
        note="client.py parses; unset means 30 s mains / 10 s battery",
    ),
    Knob("AIDOT_SERVE_ARRIVAL_TS", True, "bool_on"),
    Knob(
        "AIDOT_REORDER_SLACK_TICKS", 180000, "int", note="negative reads as the default"
    ),
    Knob("AIDOT_FFMPEG", "ffmpeg", "str"),
    # --- endpoints and test seams (ENVIRONMENT.md) --------------------------
    Knob("AIDOT_API_BASE_TEMPLATE", None, "str"),
    Knob("AIDOT_SMARTHOME_URL_TEMPLATE", None, "str"),
    Knob("AIDOT_MQTT_URL", None, "str"),
    Knob("AIDOT_STUN_SERVERS", ["stun:stun.l.google.com:19302"], "list"),
    Knob(
        "AIDOT_TURN_SERVERS",
        ["stun:3.230.182.123:3478", "turn:3.230.182.123:5349"],
        "list",
    ),
)
