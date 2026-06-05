#!/usr/bin/env bash
#
# cleanup-branches.sh - delete stale/merged remote branches on origin.
#
# Run this from a clone where you have push rights (e.g. your local machine):
#   ./cleanup-branches.sh           # interactive: prints the plan, asks to confirm
#   ./cleanup-branches.sh --dry-run # show what would be deleted, change nothing
#   ./cleanup-branches.sh --yes     # skip the confirmation prompt
#
# KEEPS: main, both backup/* branches, and the recent active branches
#        (try/camera-support, wip/tutk-lan-control). Edit the lists below if needed.

set -euo pipefail

REMOTE="origin"

# Branches to delete - merged into main or stale/superseded experiments.
DELETE_BRANCHES=(
  wip/fix-camera-user-info                       # content identical to main (fully merged)
  codex/diagnose-test_camera.py-stream-issues    # junk: 22k files / +2.6M lines (committed venv/deps)
  codex/add-cli-switch-for-authorization-cache   # old codex experiment (May 2)
  fix-srcaddr                                     # old webrtc fix, superseded by squash to main
  fix/webrtc-ice-patch                            # old webrtc fix, superseded by squash to main
  wip/add-camera-snapshots                        # old WIP (Mar 25), superseded
  wip/add-camera-support                          # old WIP (Mar 11), superseded
)

# Branches that must NEVER be deleted - guard against accidental edits above.
PROTECTED=(
  main
  backup/full-history-pre-squash
  backup/squashed-2commit
  try/camera-support
  wip/tutk-lan-control
)

DRY_RUN=false
ASSUME_YES=false
for arg in "$@"; do
  case "$arg" in
    --dry-run) DRY_RUN=true ;;
    --yes|-y)  ASSUME_YES=true ;;
    -h|--help) grep -E '^#( |$)' "$0" | sed 's/^# \{0,1\}//'; exit 0 ;;
    *) echo "Unknown option: $arg" >&2; exit 2 ;;
  esac
done

echo "Fetching and pruning $REMOTE ..."
git fetch --prune "$REMOTE"

# Safety: refuse if any branch is on both lists.
for b in "${DELETE_BRANCHES[@]}"; do
  for p in "${PROTECTED[@]}"; do
    if [[ "$b" == "$p" ]]; then
      echo "ERROR: '$b' is in both DELETE and PROTECTED lists. Aborting." >&2
      exit 1
    fi
  done
done

# Keep only branches that still exist on the remote.
to_delete=()
for b in "${DELETE_BRANCHES[@]}"; do
  if git show-ref --verify --quiet "refs/remotes/$REMOTE/$b"; then
    to_delete+=("$b")
  else
    echo "  (skip) $b - not present on $REMOTE"
  fi
done

if [[ ${#to_delete[@]} -eq 0 ]]; then
  echo "Nothing to delete. All target branches are already gone."
  exit 0
fi

echo
echo "The following ${#to_delete[@]} remote branch(es) on '$REMOTE' will be DELETED:"
for b in "${to_delete[@]}"; do echo "  - $b"; done
echo
echo "Keeping (protected): ${PROTECTED[*]}"
echo

if $DRY_RUN; then
  echo "[--dry-run] No changes made."
  exit 0
fi

if ! $ASSUME_YES; then
  read -r -p "Proceed with deletion? [y/N] " reply
  case "$reply" in
    [yY]|[yY][eE][sS]) ;;
    *) echo "Aborted."; exit 0 ;;
  esac
fi

git push "$REMOTE" --delete "${to_delete[@]}"
echo
echo "Done. Remaining remote branches:"
git branch -r --list "$REMOTE/*"
