"""Nothing in this repo may name a private working-note file.

This library is published to PyPI and the repository is public, so a comment
citing a local note by filename leaks a private artefact name to every user who
reads the source. Two such references shipped before this guard existed, in a
comment and in a docstring - places a commit-message or PR review never looks.

Scope is the packaged module plus the repo's prose - what a user reads. Tests are
deliberately excluded: a test named ``test_feedback_for_the_kept_video_codec``
matches the same shape as a note filename and is not a leak, and no pattern that
still catches a real reference can tell the two apart. So this guard covers the
place the leaks actually happened - comments and docstrings in shipped code -
and does not pretend to cover test identifiers.

The pattern is assembled from fragments on purpose: spelled out literally, this
file would match itself and the guard could never pass.
"""
import os
import re
import sys

sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

REPO = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SELF = os.path.basename(__file__)

# "project_", "feedback_", "reference_" followed by a snake_case tail - the
# shape of a working-note filename.
_KINDS = "|".join(("pro" + "ject", "feed" + "back", "refer" + "ence"))
NOTE_NAME = re.compile(r"(?:%s)_[a-z]+_[a-z_]{3,}" % _KINDS)

TEXT_SUFFIXES = (".py", ".md", ".toml", ".cfg", ".txt", ".yaml", ".yml", ".rst")


# Walked from the filesystem rather than `git ls-files`: CI runs the unit tier
# inside an Alpine container that does not own the checkout, and git refuses to
# read a repository owned by another user (exit 128, "dubious ownership"). The
# scan does not need git - it needs the files a user reads, which are on disk.
SKIP_DIRS = {".git", ".venv", "venv", "node_modules", "__pycache__", ".pytest_cache",
             ".mypy_cache", ".ruff_cache", "build", "dist"}


def _text_files():
    for root, dirs, files in os.walk(REPO):
        dirs[:] = sorted(d for d in dirs if d not in SKIP_DIRS and not d.endswith(".egg-info"))
        for fn in sorted(files):
            if not fn.endswith(TEXT_SUFFIXES):
                continue
            rel = os.path.relpath(os.path.join(root, fn), REPO)
            if fn == SELF:
                continue  # the guard names the shape it forbids
            if rel.startswith("tests" + os.sep):
                continue  # see the module docstring
            yield rel


def test_no_working_note_filenames_anywhere_in_the_repo():
    offenders = []
    for rel in _text_files():
        path = os.path.join(REPO, rel)
        try:
            text = open(path, encoding="utf-8", errors="replace").read()
        except OSError:
            continue
        for i, line in enumerate(text.splitlines(), 1):
            m = NOTE_NAME.search(line)
            if m:
                offenders.append("%s:%d: %s" % (rel, i, m.group(0)))

    assert not offenders, (
        "private working-note filenames are on the public surface:\n  "
        + "\n  ".join(offenders)
    )


def test_the_guard_can_actually_fire():
    """A scanner that matches nothing would pass this suite forever."""
    planted = "pro" + "ject" + "_aidot_some_local_note"
    assert NOTE_NAME.search("# see %s for why" % planted)
    assert NOTE_NAME.search("    docstring mentioning %s." % planted)
    # And does not fire on ordinary prose or identifiers.
    assert not NOTE_NAME.search("the project is public")
    assert not NOTE_NAME.search("reference counting keeps this alive")
