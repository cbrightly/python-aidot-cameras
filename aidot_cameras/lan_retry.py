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
_LOGIN_CONNECT_TIMEOUT_S = float(
    os.environ.get("AIDOT_LOGIN_CONNECT_TIMEOUT_S", "20"))


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
        _LOGGER.warning(
            "%s: LAN connect/login did not answer within %.0fs - abandoning "
            "this attempt and closing the socket. Override with "
            "AIDOT_LOGIN_CONNECT_TIMEOUT_S.", device_id, timeout,
        )
        try:
            await on_timeout()
        except asyncio.CancelledError:
            raise
        except Exception:
            _LOGGER.debug("%s: cleanup after connect timeout failed",
                          device_id, exc_info=True)
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
    return min(_LOGIN_RETRY_BASE_S * (2 ** attempt), _LOGIN_RETRY_CAP_S)


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
            # reset() cleared it, but the parked attempt never ran connect()'s
            # own finally, so make the in-flight flag honest either way.
            self._connecting = False

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
            _LOGGER.warning(
                "%s: giving up LAN login after %d consecutive failures; it will "
                "be retried when something asks for this device again. Set "
                "AIDOT_LOGIN_RETRY_LIMIT to change the ceiling.",
                getattr(self, "device_id", "?"), attempt,
            )
            return
        self._login_attempt = attempt + 1

        async def _retry() -> None:
            try:
                await asyncio.sleep(delay)
                await self.async_login()
            except asyncio.CancelledError:
                raise
            except Exception:
                _LOGGER.debug(
                    "%s: delayed LAN login retry failed",
                    getattr(self, "device_id", "?"), exc_info=True,
                )

        self._login_task = asyncio.create_task(_retry())
