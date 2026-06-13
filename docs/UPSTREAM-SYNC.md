# Syncing with upstream (AiDot-Development-Team/python-AiDot)

This repo is a heavily-diverged fork: it adds the entire `aidot.camera`
subpackage (WebRTC/SDES streaming, two-way audio, PTZ, LAN control) on top of
upstream's lights-only base, and its history has been rewritten. As a result it
has **no common git ancestor** with upstream, so `git merge upstream/main` will
not work (unrelated histories). Sync individual changes by **cherry-pick**
instead. Our last content-level sync point with upstream was `eef1630`.

## One-time setup

```bash
git remote add upstream https://github.com/AiDot-Development-Team/python-AiDot.git
```

## Pulling a fix FROM upstream

```bash
git fetch upstream
git log eef1630..upstream/main --oneline      # what's new upstream since our sync
git cherry-pick <commit-sha>                  # apply a specific fix
#  …resolve conflicts (file layout differs: upstream's device_client.py logic
#    largely lives in our aidot/camera/client.py + aidot/device_client.py)
```

For a run of commits: `git cherry-pick A^..B`. If a fix doesn't map cleanly,
apply it by hand and reference the upstream SHA in the commit message.

After absorbing upstream up to a new point, note it here (update the
`eef1630` reference above) so the next person knows the sync baseline.

## Pushing a fix TO upstream

Upstream changes must be authored against upstream's tree, not ours:

```bash
git fetch upstream
git switch -c fix-xyz upstream/main           # branch off upstream
#  …re-implement the fix against upstream's layout, commit…
git push <your-upstream-fork> fix-xyz         # then open a PR to AiDot-Development-Team
```

`git format-patch` / `git am` work too when the file layout matches.

## Do not

- `git merge upstream/main` — no common ancestor; it would try to merge
  unrelated histories and conflict on essentially every file.
