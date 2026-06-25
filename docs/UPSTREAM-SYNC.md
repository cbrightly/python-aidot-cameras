# Syncing with upstream (AiDot-Development-Team/python-AiDot)

This repo is a camera-capable fork of the lights-only upstream
[`python-aidot`](https://github.com/AiDot-Development-Team/python-AiDot). It adds
the entire `aidot.camera` subpackage (WebRTC/SDES streaming, two-way audio, PTZ,
LAN control) and also modifies several of upstream's own files in place.

## Current state (the problem)

The fork's git history was rewritten at fork time, so it has **no common
ancestor** with upstream. That breaks integration two ways:

1. **No shared history** → `git merge upstream/main` refuses to run ("unrelated
   histories"). There is no `merge`/`pull` path at all today, only manual
   cherry-pick.
2. **In-place layout divergence** → even cherry-picks conflict, because our
   changes live *inside* upstream's files (and methods), not only in additive
   new files.

Measured against upstream `0.3.53` (PyPI sdist), the divergence splits cleanly:

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

To see how far behind we are at any time (run where upstream git is reachable):

```bash
git fetch upstream
git log eef1630..upstream/main --oneline   # eef1630 = last content-level sync point
```

## Target architecture: re-root + cut divergence

The chosen direction is to **re-establish a shared ancestor with upstream**
(so merges work again) and then **drive the in-place divergence down** (so those
merges stay clean).

### Why not "invert to a dependency"

Depending on upstream as a PyPI package (`python-aidot`, which is published) and
shipping only an extension was evaluated and set aside. The additive
`aidot.camera` subpackage would suit it, but our **in-place edits to upstream's
files** would each have to become a whole-method subclass override or a
monkeypatch — upstream's `AidotClient.get_device_client()` even hardcodes
`DeviceClient(...)` with no factory hook. Whole-method overrides go **silently
stale** when upstream patches that method (you'd drop the fix without a
conflict to review). A *clean* inversion would need upstream to expose
extension seams — i.e. upstream action — which we deliberately do not depend on.
Cutting divergence (below) keeps the door open to revisit this later without
ever needing upstream.

### Step 1 — Re-root onto upstream's history (one-time)

This rewrites `main`'s history and requires a force-push, so it must be a
deliberate, announced operation. **Run it where upstream git is reachable** (a
plain clone outside the scoped sandbox), on a scratch branch first, never
straight onto `main`.

```bash
git remote add upstream https://github.com/AiDot-Development-Team/python-AiDot.git
git fetch upstream

# Replay our commits on top of upstream's history so upstream becomes a real
# ancestor (preserves our commit granularity; resolve conflicts once):
git switch -c reroot main
git rebase --onto upstream/main --root reroot
#   …resolve conflicts. The file layout differs, so expect them in the
#     upstream-derived files above; the aidot/camera/** additions apply clean.

# Validate the tree is unchanged vs the current published main BEFORE adopting it:
git diff main reroot        # should be empty (history changed, content identical)
pytest -q                    # full suite must pass

# Only after review + an announced window:
#   git branch -f main reroot && git push --force-with-lease origin main
```

After this, upstream commits are genuine ancestors and the merge path is normal.

### Step 2 — Normal sync, forever after

```bash
git fetch upstream
git merge upstream/main      # conflicts only in files we still modify in place
```

### Step 3 — Cut divergence (ongoing, makes Step 2 near-clean)

Each conflict in Step 2 comes from a change living *inside* an upstream method.
Move those out so upstream's files trend back toward vanilla:

- Replace in-method hooks with thin, overridable seams. e.g. the
  `if "IPC" in model: return` guard inside `async_login`, the raw-attr line in
  `receive_data`, and the `async_stop_streaming()` call in `close` become small
  `_camera_*` hook methods (no-op in the base, implemented by `CameraMixin`).
- Prefer **new files / subclassing** over editing upstream files wherever a
  change can live in the `aidot.camera` layer instead.
- Goal: upstream-derived files contain only upstream's code plus minimal,
  clearly-marked attach points — so an upstream bump touches only their lines.

The closer divergence gets to zero, the cleaner future merges are — and the
smaller (and safer) a future inversion-to-dependency would become.

## Interim: pulling a single fix before the re-root

Until Step 1 lands, the only option is cherry-pick (layout conflicts expected):

```bash
git fetch upstream
git log eef1630..upstream/main --oneline
git cherry-pick <commit-sha>
#   …upstream's device_client.py logic largely lives in our
#     aidot/camera/client.py + aidot/device_client.py; map by hand if needed.
```

After absorbing upstream up to a new point, update the `eef1630` reference above
so the next sync knows the baseline.

## Pushing a fix TO upstream

Upstream changes must be authored against upstream's tree, not ours:

```bash
git fetch upstream
git switch -c fix-xyz upstream/main           # branch off upstream
#   …re-implement the fix against upstream's layout, commit…
git push <your-upstream-fork> fix-xyz         # then open a PR to AiDot-Development-Team
```

`git format-patch` / `git am` work too when the file layout matches.

## Do not

- `git merge upstream/main` **before** the re-root — no common ancestor; it
  refuses as unrelated histories.
- Run the re-root force-push straight onto `main`, or outside an announced
  window — it rewrites published history.
