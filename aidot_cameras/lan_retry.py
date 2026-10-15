"""Bounded LAN login retries for every device client, camera or not.

Upstream (`aidot.device_client`) retries a failed TCP:10000 login with no delay
and no ceiling, and reads the login response with no timeout at all.  Both
defects belong to the LIGHT protocol; the devices that hit them in the field are
lights.  So the policy lives here, in a module that imports nothing from
`aidot_cameras.camera` and never will - `LanRetryMixin` is mixed into the plain
`LightDeviceClient` as well as into `CameraDeviceClient`, and the dispatch seam's
guarantee that no camera code RUNS in a light's path has to survive that.

Keeping it in its own module is the enforcement: `device_client` imports the
camera package at module scope, so anything defined there is one edit away from
reaching for a camera symbol.  Here, a camera import would be visible on sight.
"""

import asyncio
import logging
import os
from typing import Optional

_LOGGER = logging.getLogger(__name__)

#: How many consecutive failed LAN logins before this device is left alone, and
#: the ceiling on the delay between them.  Both overridable per install.
_LOGIN_RETRY_LIMIT = int(os.environ.get("AIDOT_LOGIN_RETRY_LIMIT", "6"))
_LOGIN_RETRY_CAP_S = float(os.environ.get("AIDOT_LOGIN_RETRY_CAP_S", "60"))
_LOGIN_RETRY_BASE_S = 1.0

#: Ceiling on one LAN connect+login attempt.  Upstream has no read timeout at
#: all - `grep -cE "wait_for|timeout"` over its device_client returns 0 - so a
#: device that accepts the TCP connection and then stops answering parks
#: `readexactly(8)` forever, inside `connect()`.
_LOGIN_CONNECT_TIMEOUT_S = float(os.environ.get("AIDOT_LOGIN_CONNECT_TIMEOUT_S", "20"))

#: Upstream's device-client logger, which writes one WARNING per connect attempt.
UPSTREAM_DEVICE_LOGGER = "aidot.device_client"

#: Device ids over the ceiling at an address that has not changed since.
#:
#: The ceiling below stops discovery and upstream's own reconnect loop from
#: kicking such a device, but a consumer may still ask for a login on its own
#: schedule - the Home Assistant integration's light coordinator does, every
#: five minutes - and upstream logs a WARNING for each of those attempts.
#: Measured 2026-10-09 on one installation: one light on another subnet, 159
#: lines a day, after the ceiling had announced it was "not trying again".
#: The attempts stay (they are how a light that comes back is noticed without a
#: restart); the per-attempt lines do not.  `SilenceOverCeilingFilter` drops
#: upstream's connect lines for the ids in this set.
_OVER_CEILING: "set[str]" = set()


#: Upstream's per-attempt login lines (python-aidot 0.3.56-0.3.59). A login
#: that is refused (non-200 ack) logs the first two, a WARNING and an ERROR; a
#: device that accepts and closes logs the last one at ERROR.
_PER_ATTEMPT_PREFIXES = (
    "connect device",
    "login result",
    "login error",
    "login read status error",
)


class SilenceOverCeilingFilter(logging.Filter):
    """Drops upstream's per-attempt login lines for a device over the ceiling.

    Only records that start with ``<device id>:`` or ``<device id> `` followed
    by one of `_PER_ATTEMPT_PREFIXES`, at ERROR or below, and only while the id
    is in `_OVER_CEILING`; anything else on the logger, and every record for a
    device under the ceiling, passes untouched.
    """

    def filter(self, record: logging.LogRecord) -> bool:
        if record.levelno > logging.ERROR or not _OVER_CEILING:
            return True
        try:
            message = record.getMessage()
        except Exception:  # a malformed record is not ours to judge
            return True
        for device_id in _OVER_CEILING:
            if not device_id or not message.startswith(device_id):
                continue
            rest = message[len(device_id) :]
            if rest[:1] in (":", " ") and rest[1:].lstrip().startswith(
                _PER_ATTEMPT_PREFIXES
            ):
                return False
        return True


def install_over_ceiling_filter() -> None:
    """Idempotently attach the filter to upstream's device-client logger."""
    logger = logging.getLogger(UPSTREAM_DEVICE_LOGGER)
    if not any(isinstance(f, SilenceOverCeilingFilter) for f in logger.filters):
        logger.addFilter(SilenceOverCeilingFilter())


install_over_ceiling_filter()


async def _await_connect_with_deadline(
    connect_coro, timeout: float, device_id: str, on_timeout
) -> bool:
    """Await a connect attempt, bounded.  True if it finished in time.

    A parked attempt is worse than a failed one.  `connect()`'s
    `finally: self._connecting = False` never runs, so the client still believes
    an attempt is in flight - which wedges both re-entry doors, because the
    retry never spawns and a timer that did fire would hit the in-flight guard
    and return.  The device stops being managed, silently, and its socket stays
    open.  Observed: four of six devices ended that way, and all six emitted
    their single teardown error within 3 ms of each other, one socket having
    been held for 21 minutes.

    ``on_timeout`` closes the connection.  Its failure is logged and swallowed:
    this runs on the failure path already, and letting a close error replace the
    timeout would turn a handled outcome into an unhandled one.  Cancellation of
    the caller is never swallowed - shutdown has to be able to stop this.
    """
    try:
        await asyncio.wait_for(connect_coro, timeout)
        return True
    except asyncio.CancelledError:
        raise
    except TimeoutError:  # asyncio.TimeoutError is an alias since 3.11
        # Past the ceiling this is one more of the attempts the ceiling's own
        # warning already announced; DEBUG, like upstream's lines for it.
        _LOGGER.log(
            logging.DEBUG if device_id in _OVER_CEILING else logging.WARNING,
            "%s: LAN connect/login did not answer within %.0fs - abandoning "
            "this attempt and closing the socket. Override with "
            "AIDOT_LOGIN_CONNECT_TIMEOUT_S.",
            device_id,
            timeout,
        )
        try:
            await on_timeout()
        except asyncio.CancelledError:
            raise
        except Exception:
            _LOGGER.debug(
                "%s: cleanup after connect timeout failed", device_id, exc_info=True
            )
        return False


def _next_login_retry_delay(attempt: int) -> Optional[float]:
    """Seconds to wait before login attempt ``attempt``, or None to give up.

    Upstream retries a failed login with no delay and no ceiling: ``login()``
    logs the error, calls ``reset()``, and ``reset()`` ends in
    ``_schedule_reconnect()``, whose last line is
    ``asyncio.create_task(self.async_login())`` - straight back into
    ``connect()``.  The ``loop.call_later(60, ...)`` on the line above never
    fires, because the next ``reset()`` cancels ``_reconnect_handle`` at its own
    top.  So the period is the device's login round-trip, not a minute.

    Measured on a live run: 8,434 of 8,434 failures were followed by that
    device's next connect within a median of 0.295 ms - about 7.6 attempts per
    second for one light, 15,376 across six devices, sustained until the process
    ended.  The loop never stops on its own.

    Exponential from 1 s, capped, and then it stops.  The first retry stays
    prompt because a single dropped connection is ordinary and recovering from
    it quickly is the behaviour worth keeping; what is not worth keeping is the
    twelve-thousandth attempt.
    """
    if attempt < 0 or attempt >= _LOGIN_RETRY_LIMIT:
        return None
    return min(_LOGIN_RETRY_BASE_S * (2**attempt), _LOGIN_RETRY_CAP_S)


class LanRetryMixin:
    """Bound the LAN login loop for whatever client this is mixed into.

    Mix it in ahead of `aidot.device_client.DeviceClient`, so every `super()`
    call below lands on upstream.  Upstream calls `_schedule_reconnect` and
    `connect` through `self`, so an override here intercepts every path into
    them, including the `loop.call_later(60, self._schedule_reconnect)` re-arm,
    whose bound method is resolved through this MRO too.

    Nothing here knows what kind of device it is holding.  That is the point:
    the six devices that produced the storm were lights, and a policy that only
    a camera class carries cannot reach them.
    """

    #: Consecutive failed logins so far.  Class-level so it reads correctly
    #: before the first failure, and reset on any login that gets through.
    _login_attempt: int = 0

    #: Ceiling on one connect+login attempt.  A class attribute rather than a
    #: bare module read so a subclass (or a test) can shorten it in place.
    _login_connect_timeout_s: float = _LOGIN_CONNECT_TIMEOUT_S

    #: The delayed retry task armed by `_schedule_reconnect`, or None.  Its
    #: own attribute, deliberately not upstream's `_login_task`: upstream's
    #: `update_ip_address()` overwrites `_login_task` with a fresh in-flight
    #: login task, without cancelling whatever was already parked there -
    #: which would silently orphan a sleeping retry rather than cancel it.
    #: (`connect()` itself never touches `_login_task`; only
    #: `update_ip_address()` and upstream's own `_schedule_reconnect()` do,
    #: and this mixin's override of the latter never calls super().)
    _lan_retry_task: Optional[asyncio.Task] = None

    @property
    def lan_address(self) -> Optional[str]:
        """The LAN address the login is tried at, or None before discovery."""
        return getattr(self, "_ip_address", None)

    @property
    def lan_login_failures(self) -> int:
        """Consecutive failed LAN logins; 0 after a login that got through."""
        return int(getattr(self, "_connect_failures", 0) or 0)

    @property
    def lan_login_over_ceiling(self) -> bool:
        """True once the consecutive failures reach AIDOT_LOGIN_RETRY_LIMIT.

        An address change resets the count, see update_ip_address.
        """
        # A logged-in device is never over it, and a count of zero never is:
        # with AIDOT_LOGIN_RETRY_LIMIT set to 0 or below, `0 >= limit` used to
        # mark every working light as over the ceiling.
        failures = self.lan_login_failures
        if failures <= 0 or getattr(self, "_connect_and_login", False):
            return False
        return failures >= _LOGIN_RETRY_LIMIT

    async def async_login(self) -> None:
        """Log in, and let a login that got through clear the failure count.

        The ceiling counts CONSECUTIVE failures, so a device that drops
        occasionally and recovers must never accumulate its way to being
        abandoned.  Upstream sets `_connect_and_login` only on the success path,
        which makes it the honest signal to reset against.
        """
        await super().async_login()
        if getattr(self, "_connect_and_login", False):
            self._login_attempt = 0
            self._connect_failures = 0
            self._gave_up_logged = False
            _OVER_CEILING.discard(getattr(self, "device_id", None))

    async def connect(self, ip_address) -> None:
        """Bound the attempt, so a silent device cannot park it forever.

        Upstream reads the login response with no timeout, so a device that
        completes the TCP handshake and then says nothing leaves this coroutine
        parked inside `readexactly`, with `_connecting` still True and the
        socket still open.  See _await_connect_with_deadline.

        On timeout we close through `reset()`, which is also what puts the
        device back on the retry path - now a bounded one.
        """
        ok = await _await_connect_with_deadline(
            super().connect(ip_address),
            self._login_connect_timeout_s,
            getattr(self, "device_id", "?"),
            self.reset,
        )
        if not ok:
            # wait_for cancelled the attempt and its finally cleared this; a
            # reset() in between may have raced it. Make the flag honest
            # either way.
            self._connecting = False
        device_id = getattr(self, "device_id", None)
        if getattr(self, "_connect_and_login", False):
            self._connect_failures = 0
            self._login_attempt = 0
            self._gave_up_logged = False
            _OVER_CEILING.discard(device_id)
            return
        # Upstream's connect() swallows its own failure (one WARNING per try)
        # and schedules nothing, so nothing counted these: a light whose
        # discovered address was on another subnet was kicked by every poll,
        # 230 warnings a day. Count them here; update_ip_address stops kicking
        # at the ceiling until the address changes or a login gets through,
        # and a consumer that keeps asking (the integration's five-minute
        # light poll) still gets its attempt, quietly - see _OVER_CEILING.
        failures = getattr(self, "_connect_failures", 0) + 1
        self._connect_failures = failures
        if failures == _LOGIN_RETRY_LIMIT:
            if device_id:
                _OVER_CEILING.add(device_id)
            _LOGGER.warning(
                "%s: LAN login failed %d times in a row at %s; not trying it "
                "again from discovery until its address changes, and any other "
                "attempt is no longer logged until then or until a login gets "
                "through. Set AIDOT_LOGIN_RETRY_LIMIT to change the ceiling.",
                device_id or "?",
                failures,
                ip_address,
            )
        elif failures > _LOGIN_RETRY_LIMIT:
            if device_id:
                _OVER_CEILING.add(device_id)
            _LOGGER.debug(
                "%s: LAN login at %s still failing (%d in a row); the attempt "
                "was made and upstream's line for it dropped.",
                device_id or "?",
                ip_address,
                failures,
            )

    def update_ip_address(self, ip) -> None:
        """Push a (re)discovered address, unless it is the one that keeps failing."""
        if ip is None:
            return
        current = getattr(self, "_ip_address", None)
        if ip != current:
            # A new address is a fresh start: the failure count, the backoff
            # (a stale _login_attempt left the new address no retries and
            # reported the old count) and the once-only giving-up line.
            self._connect_failures = 0
            self._login_attempt = 0
            self._gave_up_logged = False
            _OVER_CEILING.discard(getattr(self, "device_id", None))
        elif getattr(
            self, "_connect_failures", 0
        ) >= _LOGIN_RETRY_LIMIT and not getattr(self, "_connect_and_login", False):
            _LOGGER.debug(
                "%s: not trying the LAN login at %s again (failed %d times)",
                getattr(self, "device_id", "?"),
                ip,
                self._connect_failures,
            )
            return
        # Upstream's own update_ip_address (below, via super()) spawns a fresh
        # `_login_task` with a plain `asyncio.create_task`, but ONLY when it
        # is not mid-connect and not logged in - `if self._connecting is not
        # True and self._connect_and_login is not True`.  Discovery calls this
        # on every broadcast, same address or not, so under that same
        # condition a `_lan_retry_task` from _schedule_reconnect that is still
        # asleep would be overwritten rather than cancelled: it fires anyway a
        # few seconds later, a second login attempt for one discovery update.
        #
        # The gate below mirrors upstream's own condition exactly, rather than
        # cancelling unconditionally: a sleeping retry that has already woken
        # and is mid-`connect()` (`_connecting` True, a socket open) must NOT
        # be cancelled here.  `task.cancel()` only schedules a CancelledError
        # at the next suspension point - it is not visible to `_connecting`
        # synchronously - so `super().update_ip_address(ip)` below would still
        # see `_connecting` True and spawn no replacement login, while the
        # cancelled task's `finally: self._connecting = False` (inside
        # upstream's `connect()`) runs later without closing `self.writer`:
        # a non-closed light left with no login, no retry, and a leaked
        # socket until the next discovery cycle.  Only cancel when upstream's
        # own check says it is about to supersede this with a fresh login.
        pending = getattr(self, "_lan_retry_task", None)
        if (
            pending is not None
            and pending is not asyncio.current_task()
            and not pending.done()
            and not getattr(self, "_connecting", False)
            and not getattr(self, "_connect_and_login", False)
        ):
            pending.cancel()
        super().update_ip_address(ip)

    def _schedule_reconnect(self) -> None:
        """Back off between failed logins, and eventually stop.

        Upstream's version re-arms a 60 s timer that never fires and then
        immediately spawns the next login, so a device that cannot log in is
        retried at its own round-trip rate forever - measured at ~7.6/s per
        device, 15,376 attempts in one 25-minute run across six lights.

        This replaces the immediate respawn with an exponential delay and a
        ceiling.  It deliberately does NOT call super(): the whole of upstream's
        body is the defect - the timer that never fires and the task that fires
        at once.
        """
        if getattr(self, "_is_close", False):
            return
        attempt = getattr(self, "_login_attempt", 0)
        delay = _next_login_retry_delay(attempt)
        if delay is None:
            # Once until a login gets through or the address changes: past the
            # ceiling every failed attempt that closes through reset() lands
            # here again, and the line said nothing new after the first time.
            _LOGGER.log(
                logging.DEBUG
                if getattr(self, "_gave_up_logged", False)
                else logging.WARNING,
                "%s: giving up LAN login after %d consecutive failures; it will "
                "be retried when something asks for this device again. Set "
                "AIDOT_LOGIN_RETRY_LIMIT to change the ceiling.",
                getattr(self, "device_id", "?"),
                attempt,
            )
            self._gave_up_logged = True
            return
        self._login_attempt = attempt + 1

        async def _retry() -> None:
            try:
                await asyncio.sleep(delay)
                # Close can land while this is asleep: a caller that closes
                # without going through `_upstream.cancel_pending_reconnect`
                # must still not log a closed client back in.  Checked AFTER
                # the sleep, not before scheduling: a close between here and
                # the schedule call is exactly what the cancel on close is for.
                if getattr(self, "_is_close", False):
                    return
                await self.async_login()
            except asyncio.CancelledError:
                raise
            except Exception:
                _LOGGER.debug(
                    "%s: delayed LAN login retry failed",
                    getattr(self, "device_id", "?"),
                    exc_info=True,
                )

        # Cancel a still-pending retry from an earlier failure before
        # replacing it: reset() can call back in here (an address change, or
        # another dropped connection) while the previous delayed retry is
        # still asleep, and overwriting it without cancelling would let both
        # reach `async_login()`.  Never the currently running task though:
        # `_retry()`'s own `async_login()` can fail and end in
        # `reset()` -> `_schedule_reconnect()` on the SAME task (this call
        # frame), in which case `previous` IS `asyncio.current_task()` and
        # cancelling it would cut the retry we are in the middle of arming.
        #
        # Tracked under our OWN attribute, `_lan_retry_task`, rather than
        # upstream's `_login_task`: upstream's `update_ip_address` overwrites
        # `_login_task` with a fresh in-flight login task, with no cancel of
        # whatever was there - a delayed retry parked under the same name
        # would then be orphaned (unreachable to cancel) but still fire on
        # its own a few seconds later.  See `update_ip_address` below for the
        # matching cancel on the discovery path, and
        # `_upstream.cancel_pending_reconnect` for the one on close.
        previous = getattr(self, "_lan_retry_task", None)
        if (
            previous is not None
            and previous is not asyncio.current_task()
            and not previous.done()
        ):
            previous.cancel()
        self._lan_retry_task = asyncio.create_task(_retry())
