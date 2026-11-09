"""One atomic, owner-only file write, for the files that hold a secret."""

from __future__ import annotations

import os
import tempfile
from typing import IO, Callable


def write_private_file(
    path: str, write: Callable[[IO[str]], None], *, prefix: str, encoding: str
) -> None:
    """Write ``path`` 0600 via a temp file in its directory and a rename.

    ``write`` fills the open temp file. mkstemp creates it 0600, closing the
    world-readable window a chmod after the write leaves, and the rename comes
    only after ``write`` returns, so a failure part-way never truncates the
    file already there. On any error the temp file is removed and the error
    re-raised.
    """
    directory = os.path.dirname(path) or "."
    fd, tmp = tempfile.mkstemp(dir=directory, prefix=prefix, suffix=".tmp")
    try:
        with os.fdopen(fd, "w", encoding=encoding) as fh:
            write(fh)
        os.replace(tmp, path)
    except BaseException:
        try:
            os.unlink(tmp)
        except OSError:
            pass
        raise
