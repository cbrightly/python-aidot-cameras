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
import re
import time
from typing import Optional

from . import settings

_LOGGER = logging.getLogger(__name__)

#: How many consecutive failed LAN logins before this device is left alone, and
#: the ceiling on the delay between them.  Both overridable per install.
_LOGIN_RETRY_LIMIT = settings.read("AIDOT_LOGIN_RETRY_LIMIT")
_LOGIN_RETRY_CAP_S = settings.read("AIDOT_LOGIN_RETRY_CAP_S")
_LOGIN_RETRY_BASE_S = 1.0

#: Ceiling on one LAN connect+login attempt.  Upstream has no read timeout at
#: all - `grep -cE "wait_for|timeout"` over its device_client returns 0 - so a
#: device that accepts the TCP connection and then stops answering parks
#: `readexactly(8)` forever, inside `connect()`.
_LOGIN_CONNECT_TIMEOUT_S = settings.read("AIDOT_LOGIN_CONNECT_TIMEOUT_S")

#: How long a LAN session has to stay up after its login before the login
#: counts as having worked.  A device that accepts the login and then closes
#: the socket is no better off than one that refused it: measured 2026-10-24 on
#: the reference installation, four lights logged in, were closed by the light
#: about a second later, and were logged back in one second after that - the
#: backoff below never grew, because every login that got through reset it.
#: 54,000 upstream ERROR lines in one day.  See `LanRetryMixin.reset`.
_LAN_STABLE_SESSION_S = settings.read("AIDOT_LAN_STABLE_SESSION_S")

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

#: Device ids whose last LAN session was closed by the device before it had
#: lasted `_LAN_STABLE_SESSION_S`, until a session does last that long or the
#: address changes.  Upstream logs every one of those closes at ERROR, from
#: its receive loop and again from reset(); `SilenceOverCeilingFilter` drops
#: those lines for the ids in this set, and the mixin says once what is going
#: on instead.
_FLAPPING: "set[str]" = set()


#: Upstream's per-attempt login lines (python-aidot 0.3.56-0.3.59). A login
#: that is refused (non-200 ack) logs the first two, a WARNING and an ERROR; a
#: device that accepts and closes logs the last one at ERROR.
_PER_ATTEMPT_PREFIXES = (
    "connect device",
    "login result",
    "login error",
    "login read status error",
)


#: Upstream's lines for a LAN session that ended (python-aidot 0.3.56-0.3.59):
#: the receive loop's ``<id>:read status error <exc>``, then reset()'s close
#: lines, and the ping, send and login paths that hit the same dead socket.
#: All at ERROR, once per close; none of them is a fault on its own, because
#: the client logs back in by itself.
_SESSION_END_PREFIXES = (
    "read status error",
    "writer/reader close error",
    "writer close error",
    "ping error",
    "send action error",
    "login read status error",
    "Device unresponsive",
)

_DEVICE_LINE = re.compile(r"(?P<id>[^\s:]+)[: ]\s*(?P<rest>.*)", re.DOTALL)


class SilenceOverCeilingFilter(logging.Filter):
    """Keeps upstream's per-attempt and per-close lines to one per outage.

    Records that start with ``<device id>:`` or ``<device id> ``, at ERROR or
    below:

    * dropped while the id is in `_OVER_CEILING` and the rest starts with one
      of `_PER_ATTEMPT_PREFIXES`;
    * dropped while the id is in `_FLAPPING` and the rest starts with one of
      `_SESSION_END_PREFIXES`;
    * otherwise a session-end line is let through at WARNING instead of
      ERROR: a dropped LAN session is retried at once and is not an error
      until the retries run out, which the mixin reports.  Which of them
      comes first (the receive loop's, or a ping that hit the dead socket)
      is a race, so all of them are treated alike.

    Anything else on the logger passes untouched.
    """

    def filter(self, record: logging.LogRecord) -> bool:
        if record.levelno > logging.ERROR:
            return True
        try:
            message = record.getMessage()
        except Exception:  # a malformed record is not ours to judge
            return True
        match = _DEVICE_LINE.match(message)
        if match is None:
            return True
        device_id, rest = match.group("id"), match.group("rest")
        if device_id in _OVER_CEILING and rest.startswith(_PER_ATTEMPT_PREFIXES):
            return False
        if rest.startswith(_SESSION_END_PREFIXES):
            if device_id in _FLAPPING:
                return False
            if record.levelno > logging.WARNING:
                record.levelno = logging.WARNING
                record.levelname = logging.getLevelName(logging.WARNING)
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
    #: before the first failure, and reset once a session has stayed up for
    #: `_lan_stable_session_s` (see `_mark_session_stable`) or the address
    #: changes.
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

    #: How long a session must last after its login to count as working.  A
    #: class attribute for the same reason as `_login_connect_timeout_s`.
    _lan_stable_session_s: float = _LAN_STABLE_SESSION_S

    #: `time.monotonic()` at the login that opened the current session, until
    #: the session is judged: by `_mark_session_stable` once it has lasted
    #: `_lan_stable_session_s`, or by `reset()` if it ends sooner.  None when
    #: there is no session, or it has already been judged stable.
    _session_started_at: Optional[float] = None

    #: The timer that runs `_mark_session_stable`, or None.
    _session_stable_handle: Optional[asyncio.TimerHandle] = None

    #: Consecutive sessions the device closed before they were stable.
    _short_sessions: int = 0

    #: True while reset() is tearing down a connection that is already gone.
    _reset_is_duplicate: bool = False

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

    @property
    def lan_session_unstable(self) -> bool:
        """True while the device keeps closing its LAN session soon after login.

        Set by the first session that ends before `AIDOT_LAN_STABLE_SESSION_S`,
        cleared by one that lasts that long or by an address change.  Unlike
        `lan_login_over_ceiling` the login itself works here; something on the
        device's side ends the session, most often another controller taking
        the light's only LAN session.
        """
        return int(getattr(self, "_short_sessions", 0) or 0) > 0

    async def async_login(self) -> None:
        """Log in, and let a login that got through clear the failure count.

        The failure count (`lan_login_failures`, the discovery gate) counts
        CONSECUTIVE refused or failed logins, so it clears on any login that
        gets through.  The retry backoff does not clear here: a login that
        gets through and is then closed by the device must not restart the
        backoff at one second, or the backoff never grows - see `reset`.
        It clears once the session has lasted `_lan_stable_session_s`.
        """
        await super().async_login()
        if getattr(self, "_connect_and_login", False):
            self._connect_failures = 0
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
            _OVER_CEILING.discard(device_id)
            self._start_session_clock()
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
            self._short_sessions = 0
            _OVER_CEILING.discard(getattr(self, "device_id", None))
            _FLAPPING.discard(getattr(self, "device_id", None))
        elif not getattr(self, "_connect_and_login", False) and (
            getattr(self, "_connect_failures", 0) >= _LOGIN_RETRY_LIMIT
            or getattr(self, "_short_sessions", 0) >= _LOGIN_RETRY_LIMIT
        ):
            _LOGGER.debug(
                "%s: not trying the LAN login at %s again (failed %d times, "
                "closed early %d times)",
                getattr(self, "device_id", "?"),
                ip,
                getattr(self, "_connect_failures", 0),
                getattr(self, "_short_sessions", 0),
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

    def _start_session_clock(self) -> None:
        """Start timing the session a login just opened."""
        self._cancel_session_clock()
        self._session_started_at = time.monotonic()
        try:
            loop = asyncio.get_running_loop()
        except RuntimeError:  # no loop: nothing can time it, reset() judges it
            return
        self._session_stable_handle = loop.call_later(
            max(0.0, float(self._lan_stable_session_s)), self._mark_session_stable
        )

    def _cancel_session_clock(self) -> None:
        handle = getattr(self, "_session_stable_handle", None)
        if handle is not None:
            handle.cancel()
        self._session_stable_handle = None

    def _mark_session_stable(self) -> None:
        """The session has lasted long enough: the login worked, clear it all.

        This, not the login itself, is what resets the backoff, the giving-up
        line and the early-close count.
        """
        self._session_stable_handle = None
        if self._session_started_at is None or not getattr(
            self, "_connect_and_login", False
        ):
            return
        self._session_started_at = None
        device_id = getattr(self, "device_id", None)
        if getattr(self, "_short_sessions", 0) > 0:
            _LOGGER.info(
                "%s: LAN session has stayed up for %.0fs after %d early "
                "close(s); treating the light as reachable again.",
                device_id or "?",
                self._lan_stable_session_s,
                self._short_sessions,
            )
        self._login_attempt = 0
        self._connect_failures = 0
        self._gave_up_logged = False
        self._short_sessions = 0
        _OVER_CEILING.discard(device_id)
        _FLAPPING.discard(device_id)

    def _note_short_session(self, lasted: float) -> None:
        """Count a session the device closed before it was stable.

        Counting is all it takes for the backoff: the login that opened the
        session no longer reset `_login_attempt`, so the `_schedule_reconnect`
        that upstream's reset() calls next waits longer than the last one, and
        gives up at the same ceiling a refused login reaches.
        """
        device_id = getattr(self, "device_id", None)
        self._short_sessions = getattr(self, "_short_sessions", 0) + 1
        if device_id:
            _FLAPPING.add(device_id)
        if self._short_sessions == 1:
            _LOGGER.warning(
                "%s: the light closed its LAN session %.1fs after a successful "
                "login. Retrying with backoff; further early closes are not "
                "logged until a session lasts %.0fs. A light that keeps doing "
                "this usually has another controller holding its LAN session. "
                "Set AIDOT_LAN_STABLE_SESSION_S to change the threshold.",
                device_id or "?",
                lasted,
                self._lan_stable_session_s,
            )
        else:
            _LOGGER.debug(
                "%s: LAN session closed %.1fs after login (%d in a row)",
                device_id or "?",
                lasted,
                self._short_sessions,
            )

    async def reset(self) -> None:
        """Judge the session that is ending, then let upstream tear it down.

        Upstream's reset() closes the socket and ends in `_schedule_reconnect`,
        so the verdict has to be in before it runs.  A session that ended
        before `_lan_stable_session_s` is an early close (see
        `_note_short_session`); a session that lasted was already cleared by
        `_mark_session_stable`, so its drop gets the prompt one-second retry.
        No session at all (a refused login, a connect timeout) is not counted
        here - `connect()` counts those.
        """
        started = getattr(self, "_session_started_at", None)
        self._session_started_at = None
        self._cancel_session_clock()
        if started is not None and not getattr(self, "_is_close", False):
            self._note_short_session(time.monotonic() - started)
        # One dead socket is usually reported more than once: the receive
        # loop's reset() and the ping or send that hit the same socket each
        # call reset().  Only the first has a connection to tear down; the
        # rest would each re-arm the retry one backoff step further, so a
        # single close cost two or three steps.  A reset with no session, no
        # socket, no attempt in flight and the first reset's retry still
        # asleep is one of those: let it tear down (a no-op) without
        # scheduling.
        pending = getattr(self, "_lan_retry_task", None)
        duplicate = (
            started is None
            and getattr(self, "writer", None) is None
            and not getattr(self, "_connecting", False)
            and pending is not None
            and not pending.done()
            and pending is not asyncio.current_task()
        )
        self._reset_is_duplicate = duplicate
        try:
            await super().reset()
        finally:
            self._reset_is_duplicate = False

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
        if getattr(self, "_reset_is_duplicate", False):
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
                "%s: giving up LAN login after %d consecutive failures (failed "
                "logins and sessions the light closed early); it will be "
                "retried when something asks for this device again. Set "
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
