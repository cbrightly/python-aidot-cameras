# Releasing

The routine for a library release and the integration release that carries it.
The rules behind it (plain versions, the integration proved on the reference
installation before it is published, the library pinned to a minor range) are
in [ROAD-TO-1.0.md](ROAD-TO-1.0.md) under "Release rules from 1.0.0". This
file is the ordered list; every step matters, and the ones that fail silently
are marked.

## 0. Before the release commit

- Review the diff (`/code-review` or a second reader) and act on what it
  finds. No commit goes in unreviewed.
- Live-check smooth video and sound on every camera model the change touches
  (`scripts/live_validate.py`); compare distributions over at least three
  runs, not one.
- Lint the whole tree, tests included: `ruff check .` and `ruff format --check`.
- ASCII only in anything committed or published.
- **Docs review.** A release that changes behaviour and leaves the words
  behind ships two versions of the truth. Check each of these against the
  diff and fix what it changed:
  - the library `README.md` and `docs/` (`CAMERAS.md` for camera behaviour,
    `ENVIRONMENT.md` for every setting touched - a test holds its table to
    `settings.py`, but not the prose -, `API-STABILITY.md` for anything on
    the public surface, `APP-PARITY-STATUS.md` for anything measured against
    the app, `TESTING.md` and `CI-RUNNER.md` for the gate);
  - docstrings and comments on the code changed, including the dated
    measurements they cite;
  - the integration's `README.md`, `strings.json` / translations for new
    entities or options, `services.yaml`, and its `CHANGELOG.md`;
  - the public wiki (`https://github.com/cbrightly/hass-aidot-cameras.wiki.git`),
    which users read before the README: `Configuration-options`,
    `Supported-devices`, `Known-limitations`, `Resolution`, `Services`,
    `Troubleshooting` and whichever page covers the feature; it is a separate
    git repository, so it is pushed on its own;
  - the `CHANGELOG.md` entry itself: the version heading, and a classification
    line saying what the release changes on a default path.

## 1. Library

1. Bump `pyproject.toml`; name the `CHANGELOG.md` section; land on `main`.
2. Live gate: `gh workflow run live-validate.yml --repo
   cbrightly/python-aidot-cameras-ci -f lib_ref=<full sha>` (about 30 min).
   It posts the `live-validation` commit status the publish workflow waits on.
   **Diff each camera's `live-report.json` against the previous run**: the gate
   is a coverage check (every model streamed), not a regression detector.
3. `gh release create vX.Y.Z --repo cbrightly/python-aidot-cameras --target
   <sha> --title X.Y.Z --notes-file <changelog section> --latest`. Without
   `--repo`, gh hits the upstream fork.
4. Approve the `pypi` environment on the publish run
   (`gh api -X POST .../actions/runs/<id>/pending_deployments`).
5. Confirm PyPI serves it (`pip download --no-deps --no-cache-dir
   python-aidot-cameras==X.Y.Z`; the simple index can lag the JSON API by
   minutes) and **diff the wheel against the tag**: only
   `_vendor/aiortc/VENDOR.md` may differ (excluded by design). A tagged release
   once shipped an import that was not in the tag.
6. Watch the `CI` run on `main` after the push, and the `Downstream integration
   tests` job in particular: it runs the public integration's suite against
   this library (pinned to a release tag in `ci.yml`; bump the pin when the
   integration ships).

## 2. Integration

1. Bump `manifest.json` (`version`, and the library requirement to
   `>=X.Y.Z,<X.(Y+1)` - a test holds the cap); name the `CHANGELOG.md`
   section; land on the development repository's `main`.
2. **Deploy to the reference installation first**, from that commit, with the
   deploy approval given each time:
   - package `custom_components/aidot` (`COPYFILE_DISABLE=1 tar ...` on macOS,
     or the install carries `._*` resource forks);
   - back up `/config/custom_components/aidot` and
     `/config/.storage/core.config_entries` into `/config/aidot-backups/`;
   - install, `ha core restart`, then read `ha core logs`: the library version
     the container installed, options migrated, entity counts, every camera
     serving, no `aidot` ERROR or Traceback;
   - check the installed files against the commit by hash.
3. A change to a stream path (an open, a serve loop, the publish or the mux)
   stays on the box through two of the scheduled view rounds (09:05, 14:05,
   20:05 local); anything else needs one. Read the log for those windows.
4. `scripts/publish-public.sh` (dry run, then `--yes`) from a checkout whose
   HEAD is the development `main` that is on the box. It force-pushes the
   public snapshot and cuts the GitHub release; a plain `X.Y.Z` publishes as
   Latest, a pre-release suffix as a pre-release.
5. Watch the public repository's `Release check` workflow.
6. Push the wiki changes from step 0.

## 3. Afterwards

- The soak watcher keeps counting; a release does not reset anything but its
  own column. Note the release and the day it reached the box in
  `ROAD-TO-1.0.md` only when it changes what that file says.
- Record the outcome where the next person will look (the CHANGELOG is the
  record; a release that needed a follow-up says so in the follow-up's entry).
