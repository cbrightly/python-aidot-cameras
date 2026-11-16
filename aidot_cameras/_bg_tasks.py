"""Fire-and-forget asyncio tasks that cannot be garbage-collected mid-flight."""

from __future__ import annotations

import asyncio
import logging
from typing import Callable

# Strong refs to fire-and-forget tasks: asyncio only keeps weak refs, so a
# discarded task can be garbage-collected mid-flight. Discarded on completion.
_BG_TASKS: set = set()


def spawn_bg_for(logger_name: str) -> Callable:
    """The ``_spawn_bg`` of one module: its failures log under ``logger_name``."""
    logger = logging.getLogger(logger_name)

    def _spawn_bg(coro):
        _t = asyncio.ensure_future(coro)
        _BG_TASKS.add(_t)

        def _done(task):
            _BG_TASKS.discard(task)
            # Retrieve the exception so a failing background task doesn't surface
            # as an unhandled "Task exception was never retrieved" at GC time;
            # log it.
            if not task.cancelled():
                exc = task.exception()
                if exc is not None:
                    logger.debug("aidot background task failed: %r", exc, exc_info=exc)

        _t.add_done_callback(_done)
        return _t

    return _spawn_bg
