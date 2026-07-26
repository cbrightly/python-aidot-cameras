# Taking a new upstream release

This library **extends** the upstream lights-only library
[`python-aidot`](https://github.com/AiDot-Development-Team/python-AiDot); it does
not fork it.  Upstream ships as a normal pip dependency and owns the `aidot`
import name.  Everything here lives under `aidot_cameras` and attaches to
upstream through a small number of documented seams.

Because no upstream file is edited, taking a new upstream release is a
**dependency bump plus a test run** - never a merge, and never a conflict.

## The procedure

1. Bump the pin in `pyproject.toml`:

   ```toml
   dependencies = [
       "python-aidot==<new version>",
       ...
   ]
   ```

2. Reinstall and run the seam-contract test first - it fails fast and names the
   exact symbol if upstream moved something we depend on:

   ```bash
   pip install -e '.[webrtc]'
   pytest tests/test_upstream_compat.py -v
   ```

3. Run the full suite:

   ```bash
   pytest tests/ -q
   ```

4. If everything is green, you are done: commit the bump. If something failed,
   see "When a seam breaks" below.

## How the extension attaches to upstream

Keep this list in sync when you add or remove a seam; it is what
`tests/test_upstream_compat.py` asserts.

| What we do | Upstream API we rely on |
| --- | --- |
| Dispatch camera vs non-camera devices | `AidotClient.get_device_client(device)` - the single place upstream constructs a device client |
| Camera device client | subclass `DeviceClient`, overriding `async_login`, `close`, `read_data`, `_notify_status_update` |
| Camera status / info | subclass `DeviceStatusData`, `DeviceInformation` |
| Raw cloud records | `DeviceModel.to_dict()`, `UserInformation.to_dict()` |
| Shared crypto | `aidot.utils.crypto`: `aes_encrypt`, `aes_decrypt`, `aes_decrypt_to_json`, `rsa_encrypt` |
| Constants | `aidot.const`: `APP_ID`, `PUBLIC_KEY_PEM`, `API_URL_TEMPLATE`, `DEFAULT_REGION`, `Identity`, `CONF_*` |

**Non-camera devices are upstream's job.** `CameraClient.get_device_client`
returns a plain upstream `DeviceClient` for anything that is not a camera, so
lights run upstream's code with none of ours in the path - except for the
carried overrides below.

## Carried overrides (self-liquidating)

A "carried override" is a fix we needed before upstream shipped it.  Each one is
marked in the source with a `# CARRIED:` comment naming **its own** drop
condition, applies as narrowly as possible, and is **deleted** once that
condition is met - after which those devices fall through to pure upstream.

| Override | Upstream PR | Drop when |
| --- | --- | --- |
| `active_color_mode` on RGBW+CCT bulbs (reports color temp instead of a stale RGB color) | `AiDot-Development-Team/python-AiDot#6` | that PR merges |
| `CameraClient.async_close()` cancels each device client's `_reconnect_timer` | not filed yet - see [Inherited upstream defects](#inherited-upstream-defects) | upstream's `DeviceClient.close()` cancels `_reconnect_timer` itself |

Find every carried site with `grep -rn '# CARRIED:' aidot_cameras/`.  The markers
do **not** all share one drop condition, so read each one and delete only the
sites whose condition is now met - a blanket sweep on the PR #6 merge would take
the reconnect-timer fix with it.  Note that `_carry_active_color_mode` is marked
at both its definition and its call site; both go together.

Prefer carrying only fixes that have also been submitted upstream, so the list
trends to zero instead of growing.  Where that is not possible yet, record the
underlying defect in the section below so the next bump can re-check it.

## Inherited upstream defects

Behavior in the pinned upstream release that this package works around or simply
accepts.  None of these is our code to fix from the outside; each is listed so a
version bump can check whether upstream fixed it and the workaround can go.

| Defect | Where | What it costs us |
| --- | --- | --- |
| `close()` does not cancel an armed reconnect timer.  `reset()` arms `AsyncTimer(callback=async_login, interval=45)` and `close()` only sets `_is_closed`, which prevents arming a *new* timer but not the one already ticking. | `aidot/device_client.py` `reset()` / `close()` | A non-camera device client reconnects about 45s after the account was closed, leaking a TCP connection, a receive task and a ping timer.  Worked around by the carried override in `CameraClient.async_close()` above.  Worth an upstream PR. |
| `update_ip_address(ip)` accepts `None`, overwriting a known IP with it, and calls `asyncio.create_task()` unguarded - a `RuntimeError` when no loop is running. | `aidot/device_client.py` `update_ip_address()` | Callers must never pass an unresolved address and must call it from the loop.  Our discovery callback only forwards addresses it actually resolved, and never calls it for cameras at all (a camera would answer TCP:10000's connect and then never reply). |

## When a seam breaks

A broken seam shows up as an `ImportError` or `AttributeError` naming the symbol,
in one of our modules - not as a merge conflict. To fix it:

1. Read the upstream diff for the symbol that moved
   (`git log`/`git diff` on the upstream repo, or the release notes).
2. Update the single extension module that referenced it. Constants belong in
   `aidot_cameras/const.py`, crypto in `aidot_cameras/crypto.py`, exceptions in
   `aidot_cameras/exceptions.py` - camera modules import from those, so a moved
   upstream name is usually a one-line change in one file.
3. Add or adjust the assertion in `tests/test_upstream_compat.py` so the new
   shape is covered.

If upstream removes a seam entirely (for example, if device clients stop being
constructed in one place), prefer opening an upstream PR that restores a hook
over copying an upstream method body into this package. Whole-method copies
re-create the fork-divergence problem this layout exists to avoid.

## Sending a fix upstream

Fixes to upstream-owned behavior belong upstream. Author them against upstream's
tree, not ours:

```bash
git fetch upstream
git switch -c fix-xyz upstream/main    # branch off upstream
#   ...implement against upstream's layout, commit...
git push <your-upstream-fork> fix-xyz  # then open a PR upstream
```

Then, if we need the fix before it merges, add it here as a carried override and
record it in the table above.
