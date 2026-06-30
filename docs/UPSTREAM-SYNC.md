# Syncing with upstream (AiDot-Development-Team/python-AiDot)

This repo is a camera-capable fork of the lights-only upstream
[`python-aidot`](https://github.com/AiDot-Development-Team/python-AiDot). It adds
the entire `aidot.camera` subpackage (WebRTC/SDES streaming, two-way audio, PTZ,
LAN control) and also modifies several of upstream's own files in place.

## Status: ancestry link needs re-establishing

`main`'s history was recently re-launched as a single clean linear history. That
curation dropped the previous ancestry-link merge commits, so `main` no longer
records upstream as a parent and `git merge upstream/main` will again refuse to
run ("unrelated histories") until the link is re-created. Content is unaffected —
our tree is still synced through upstream's HEAD `eef1630`; only the git *link* is
missing again.

Re-establish it **non-destructively** with a one-time merge that records upstream
as a parent without changing our tree:

```bash
git remote add upstream https://github.com/AiDot-Development-Team/python-AiDot  # if not present
git fetch upstream
git merge -s ours --allow-unrelated-histories upstream/main
```

The `ours` strategy keeps our tree byte-for-byte and only records the ancestry
(valid precisely because the content is already synced to `eef1630`). It is a
normal commit on `main` — no force-push. After this one-time link, the "Normal
sync, going forward" workflow below applies.

## Normal sync, going forward

The `upstream` remote is not configured in a fresh clone — add it once before any
of the commands below:

```bash
git remote add upstream https://github.com/AiDot-Development-Team/python-AiDot
```

Because upstream is already a real ancestor (the merge-base is `eef1630`), syncing
is then an ordinary merge:

```bash
git fetch upstream
git log main..upstream/main --oneline   # new upstream commits since our last sync
git merge upstream/main                  # brings in only those new commits
```

The merge-base is `eef1630`, so a future `git merge` applies only commits upstream
adds *after* it — conflicting only in the upstream-derived files we still modify
in place (see the divergence map below). The additive `aidot/camera/**` subpackage
is never touched by upstream and never conflicts.

To pull a single fix in isolation instead of a full merge, cherry-pick it:

```bash
git cherry-pick <upstream-sha>
```

## Fork operations & release safety

This repo is a GitHub fork of upstream and publishes to PyPI, so a few operational
guards keep work on this account and prevent footguns:

- **Sync upstream only on `main`.** `git merge upstream/main` works on `main`
  (upstream is an ancestor there). Run it on any other branch and git reports
  "refusing to merge unrelated histories" — that branch just lacks the link
  commit. Switch to `main` first.
- **Run `git merge -s ours upstream/main` exactly once to re-establish the link**
  (see Status above — the recent history re-launch dropped it), valid only because
  content is already synced to `eef1630`. After that one time, never re-use it:
  re-running would silently mark genuine upstream fixes as merged and drop them.
  The ongoing workflow is a plain `git merge upstream/main`.
- **If you add an `upstream` remote, `gh` may default to the parent repo.** With
  `upstream` configured, `gh pr …` can target `AiDot-Development-Team` unless
  pinned. Set `gh repo set-default cbrightly/python-aidot-cameras`; pass
  `--repo cbrightly/...` if in doubt.
- **When you add `upstream`, disable pushes to it** (`git remote set-url --push
  upstream DISABLE`) so nothing lands on the upstream account by accident. To
  contribute a fix upstream, push to your own fork of it instead (see below).
- **`main` is branch-protected** against force-push and deletion, so normal pushes
  and merges are the day-to-day path. Intentional history maintenance (the kind
  that re-launched `main`) requires the maintainer to temporarily lift the
  force-push guard and restore it immediately after — it is not a routine action.
- **Don't cut a no-op release.** The published wheel ships only `src/aidot/` plus
  `README.md`. Before bumping the version, confirm one of those actually changed
  since the last tag (`git diff --stat <last-tag>..HEAD -- src README.md`) —
  otherwise the new version is byte-identical to the last and just burns a PyPI
  number (which can't be reused). A new GitHub *Release* is what triggers the
  PyPI publish.
- **Publishing requires manual approval.** A published `v*` Release starts the
  workflow, but it pauses at the `pypi` environment for a reviewer approval before
  anything reaches PyPI — approve the deployment in the Actions run (self-review is
  allowed, so a solo maintainer can approve their own). Doing nothing publishes
  nothing.

## Divergence map (what still conflicts on a merge)

Measured against upstream `0.3.53`. Our changes split into a purely additive
subpackage and in-place edits to upstream's own files; only the latter conflict.

| area | upstream | ours | nature |
| --- | --- | --- | --- |
| `aidot/camera/**` | — | ~14,750 | **purely additive** — upstream never touches these |
| `device_client.py` | 431 | 528 | ~357 changed lines, incl. hooks *inside* `async_login` (IPC guard), `receive_data` (raw-attr update), `close` (`async_stop_streaming`) |
| `client.py` | 304 | 548 | +244 |
| `discover.py` | 149 | 267 | +118 (unicast probing) |
| `aes_utils.py` | 27 | 70 | +43 |
| `const.py` / `exceptions.py` | 229 / 37 | 245 / 58 | +16 / +21 (camera keys / exceptions) |
| `models/**` | — | new | entirely ours |
| `login_const.py` | 16 | 16 | identical |

## Cut divergence (makes future merges near-clean)

Every merge conflict comes from a change living *inside* an upstream method. To
shrink that surface, move those out so upstream's files trend back toward vanilla:

- Replace in-method hooks with thin, overridable seams. e.g. the
  `if "IPC" in model: return` guard inside `async_login`, the raw-attr line in
  `receive_data`, and the `async_stop_streaming()` call in `close` become small
  `_camera_*` hook methods (no-op in the base, implemented by `CameraMixin`).
- Prefer **new files / subclassing** over editing upstream files wherever a
  change can live in the `aidot.camera` layer instead.
- Goal: upstream-derived files contain only upstream's code plus minimal,
  clearly-marked attach points — so an upstream bump touches only their lines.

The smaller the divergence, the cleaner future merges are. (This is also what a
future "depend on upstream as a package" inversion would need — it was evaluated
and set aside because, today, our in-place edits would require fragile
whole-method overrides or upstream-side extension hooks.)

## Pushing a fix TO upstream

Upstream changes must be authored against upstream's tree, not ours:

```bash
git fetch upstream
git switch -c fix-xyz upstream/main           # branch off upstream
#   …re-implement the fix against upstream's layout, commit…
git push <your-upstream-fork> fix-xyz         # then open a PR to AiDot-Development-Team
```

`git format-patch` / `git am` work too when the file layout matches.

## Notes

- The `-s ours` link is correct only because our content was synced through
  `eef1630`. If you ever fall behind upstream's HEAD, sync the content first
  (merge/cherry-pick) before relying on the ancestry — never use `-s ours` to
  paper over unmerged upstream changes; it would silently mark them as merged.
- Re-running the link is unnecessary: once `upstream/main` is an ancestor, plain
  `git merge upstream/main` is the workflow.
