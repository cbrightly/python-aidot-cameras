# Working conventions

Conventions for anyone (human or assistant) committing to this repo. This is the
PyPI library `python-aidot-cameras`; the companion HACS integration lives in
`cbrightly/hass-aidot-cameras`. See `docs/UPSTREAM-SYNC.md` for the upstream-sync
workflow.

## Authorship & attribution

- Commits: author **and** committer = `Chris Brightly <chris.brightly@gmail.com>`.
- **No AI/assistant references anywhere** — commit messages, PR titles/bodies,
  branch names, code comments, or docs. No `Co-Authored-By`, no "Generated with"
  trailers, no model/session identifiers.
- Branch names are descriptive (`docs-conventions`, `ci-hardening`); never
  `claude/*` or any AI-derived prefix.

## GitHub workflow

- Land changes through **PRs, squash-merged** — that keeps `main` linear and
  clean without ever rewriting history.
- This repo is a **fork**, so `gh` defaults to the upstream parent. Always pass
  `--repo cbrightly/python-aidot-cameras` (or `gh repo set-default` it). Stay on
  the `cbrightly` account.
- Pushing to the `upstream` remote is disabled; to contribute upstream, push to a
  personal fork of it instead.
- `main` is branch-protected (force-push and deletion blocked).

## History is immutable

- **Never rewrite or force-push published history** — no squashing old commits,
  no re-rooting `main`, no renumbering. Tags point at exact shipped commits and
  the upstream ancestry link lives in history; rewriting breaks both.
- The **one exception** is removing genuinely leaked secrets/PII. Even then:
  lift protection deliberately, re-point tags, **rotate any exposed credentials**
  (removal alone is insufficient), and remember that removal from history does
  **not** purge GitHub caches/forks — keep sensitive work in **private** repos.
- Want a cleaner history? It comes from squash-merged PRs going **forward**, not
  retroactive rewrites.

## Versioning & releases

- **Versions only ever increase.** Never renumber downward — PyPI/HACS detect
  updates by monotonic version, so going back breaks the update channel.
- **No no-op releases.** Before cutting a version, confirm the shipped artifact
  changed — for this library that's `src/aidot/` or `README.md`
  (`git diff --stat <last-tag>..HEAD -- src README.md`). Docs-only/CI-only changes
  don't warrant a PyPI release.
- Mark a "first stable / fresh chapter" with a **well-noted stable release**, not
  by renumbering or squashing. Mark unstable old releases as **pre-release**
  (non-destructive) rather than deleting them.
- Publishing: a **published GitHub Release** triggers the PyPI publish. Actions
  are SHA-pinned, the release tag must match `pyproject` version, and the `pypi`
  environment requires manual approval before anything ships. `version` source of
  truth is `pyproject.toml`.

## Upstream sync

- Sync with `git merge upstream/main` **on `main` only**. Never reuse
  `git merge -s ours` (it was a one-time ancestry link; reusing it would silently
  drop real upstream fixes). Full detail in `docs/UPSTREAM-SYNC.md`.
