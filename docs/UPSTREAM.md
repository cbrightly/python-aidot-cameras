# Taking a new upstream release

This library **extends** the upstream lights-only library
[`python-aidot`](https://github.com/AiDot-Development-Team/python-AiDot); it does
not fork it.  Upstream ships as a normal pip dependency and owns the `aidot`
import name.  Everything here lives under `aidot_cameras` and attaches to
upstream through a small number of documented seams.

Because no upstream file is edited, taking a new upstream release is never a
merge and never a conflict.  It is **not**, however, always just a version bump:
see the warning immediately below before assuming a patch release is routine.

## WARNING: upstream changes its private API inside patch releases

A patch-version bump has now changed the private API this package attaches to
three times. The second one **reverted the first**, and the third added a new
constructor argument on top of the reverted shape:

| version | uploaded | record shape | account back-reference | cloud API |
| --- | --- | --- | --- | --- |
| `<=0.3.53` | | **dict** - `aes_utils`, `login_const`, dict-based client | no | `v17` |
| `0.3.54` | 2026-07-24 08:50 | **typed** - `api/`, `models/auth_model.py`, `utils/crypto.py` | no | `v17` |
| `0.3.55` | 2026-07-24 09:40 | typed | no | `v17` |
| `0.3.56` | 2026-07-29 | **dict again** (plus the `models/` package) | no | `v17` |
| `0.3.57` | 2026-09-23 08:47 (sdist only) | dict | **yes** | **`v35`** |
| `0.3.58` | 2026-09-23 11:11 | dict | yes | `v35` |

So "newer" does not mean "further along": the typed shape existed on PyPI for
five days and upstream then went back.  Home Assistant core pins
`python-aidot==0.3.56` in its own `aidot` integration, which is why this package
declares a **range** rather than a pin - an exact pin on any other version would
be unsatisfiable alongside core's.

**These are independent axes, not a sequence of shapes.** 0.3.58 is the dict
shape *plus* a back-reference, so `HAS_TYPED_ACCOUNT` stays `False` there and
the back-reference has its own flag, `HAS_DEVICE_CLIENT_BACKREF`. Do not model
it as a third value of `UPSTREAM_SHAPE`: that would encode a version rather than
a capability.

### The account back-reference (0.3.57+)

`DeviceClient.__init__` gained a third required positional argument, the owning
`AidotClient`, kept as `self.client`. Before this package supplied it, every
device client construction raised
`TypeError: DeviceClient.__init__() missing 1 required positional argument: 'client'`.
Upstream uses it in exactly one method, `async_set_effect`, which calls
`client.async_execute_diff_command`. Nothing in this package calls it, but both
dispatch paths pass the real account client anyway, because a consumer's light
platform may. `device_client_args` makes `client` a required keyword with no
default so a `None` cannot slip through unnoticed.

**Effects are not wired up.** Supplying the back-reference makes 0.3.57+ work;
it does not add `async_set_effect` support to this package.

### The cloud API version (0.3.57+: v17 -> v35)

The same release moved `API_URL_TEMPLATE` from `.../v17` to `.../v35`.
Upstream's own `AidotClient` builds its account calls from it - login, token
refresh, houses, devices, products. This package's camera layer builds its own
`/v1/...` URLs and does not use it.

v35 was compared with v17 on the live cloud on 2026-09-23, using an existing
token with no login and no refresh: `/houses` identical; `/devices` 40 of 40
with no field or value differing across 17 models including all three camera
models; `/products` byte-identical over 12 interleaved calls; the same error
codes with no token (21027) and a bogus refresh token (21025). **A real login
and a real token refresh on v35 were not exercised** - both rotate the
account's single live token.

`tests/test_upstream_compat.py::test_api_url_template_is_a_version_we_have_verified`
holds the list of verified versions. Add to it only after comparing a new
version with a known-good one.

**Only the dict shape is supported, from 0.3.56.** The typed shape
(0.3.54-0.3.55) was dropped on 2026-09-23 - see "The typed shape is no longer
supported" below - and `pyproject.toml` requires `python-aidot>=0.3.56`. The
typed branches in `_upstream.py` remain but are unreachable.

Every difference is resolved in
`aidot_cameras/_upstream.py`, which detects the shape *by capability* (does the
name import?) and never by parsing a version string - a version comparison would
encode the five-day excursion rather than the shape.  No other module in the
package may branch on the upstream version; if you find yourself writing a
second `try: import aidot...`, put it in `_upstream.py` instead.

`tests/test_upstream_compat.py` asserts against whichever shape is installed,
with `typed_only` / `dict_only` marks for the assertions that apply to one.
Tests with no mark must hold on both.

## The procedure

1. Adjust the range in `pyproject.toml` if the new release falls outside it:

   ```toml
   dependencies = [
       "python-aidot>=0.3.56,<0.4",
       ...
   ]
   ```

   The `<0.4` cap is deliberate but does not protect you on its own - upstream
   has broken this API three times *inside* `0.3.x`, so a patch release can
   break it too. The scheduled upstream-watch workflow is what catches those;
   see "How CI watches upstream" below.

2. Reinstall **in a fresh environment** and run the seam-contract test first -
   it fails fast and names the exact symbol if upstream moved something we
   depend on:

   ```bash
   pip install -e '.[webrtc]'
   pytest tests/test_upstream_compat.py tests/test_upstream_seam_adapter.py -v
   ```

   A fresh environment matters. A venv made before an upstream release keeps
   the older upstream, so its tests stay green while CI - which resolves
   dependencies fresh - fails. That is how the 0.3.57 break first appeared: as
   an unexplained red on an unrelated branch, with every failing test passing
   locally.

3. Run the full suite:

   ```bash
   pytest tests/ -q
   ```

4. If everything is green, you are done: commit the bump. If something failed,
   see "When a seam breaks" below.

## How CI watches upstream

Three separate things, because each catches a different failure:

| What | Where | Catches |
| --- | --- | --- |
| **Upstream version matrix** on the unit tests | `ci.yml`, `test` job | the newest version the range resolves, and `0.3.56` - Home Assistant core's pin and the declared floor. Both gate |
| **Scheduled upstream watch** | `upstream-watch.yml`, daily | a new python-aidot release, on the day it ships. Installs the newest release even past our `<0.4` cap and runs the seam contract, then the unit tier. Gating on the schedule, so a break sends mail; advisory on pull requests |
| **Home Assistant's own pin** | `ci.yml`, `ha-constraints` job | whether we resolve alongside core. Core pins python-aidot in its aidot integration's manifest, not in `package_constraints.txt`, so the job appends that manifest's requirements to the constraints |

Without the matrix, CI only ever tests the newest release the range admits:
once 0.3.57 became the default resolve, nothing would have tested the 0.3.56
path Home Assistant runs. Without the watch, a new release first shows up as an
unexplained failure on whichever branch is pushed next.

GitHub disables scheduled workflows after 60 days without commits. After a
quiet spell, dispatch `upstream-watch.yml` by hand.

## How the extension attaches to upstream

Keep this list in sync when you add or remove a seam; it is what
`tests/test_upstream_compat.py` asserts.

| What we do | Upstream API we rely on |
| --- | --- |
| Dispatch camera vs non-camera devices | `AidotClient.get_device_client(device)` - the single place upstream constructs a device client |
| Device client constructor arguments | `DeviceClient.__init__(device, user_info[, client])` - built by `_upstream.device_client_args` (cameras) and `_upstream.light_device_client_args` (everything else), splatted because the length varies |
| Camera device client | subclass `DeviceClient`, overriding `async_login`, `close`, `_notify_status_update`, and `read_data` **where it exists** (typed shape only) |
| Camera status / info | subclass `DeviceStatusData`, `DeviceInformation` |
| Raw cloud records | typed shape: `DeviceModel.to_dict()`, `UserInformation.to_dict()`.  Dict shape: the raw dicts natively (no round trip) |
| Shared crypto | `aidot.utils.crypto` (typed) / `aidot.aes_utils` (dict): `aes_encrypt`, `aes_decrypt`, `aes_decrypt_to_json`.  `rsa_encrypt` is **ours** - see below |
| Account HTTP | typed shape: `client._cloud_api.{get_houses,get_devices,get_products,refresh_token}`.  Dict shape: `client.async_*` methods |
| Consumer-facing re-exports | `aidot.device_client`: `DeviceClient`, `DeviceState` - handed on under `aidot_cameras.device_client` so a consumer never imports `aidot` itself.  `DeviceState` does not exist on the dict shape and is supplied by `_upstream` |
| Constants | `aidot.const` (+ `aidot.login_const` on the dict shape): `APP_ID`, `PUBLIC_KEY_PEM`, `API_URL_TEMPLATE` (the cloud API version - see above), `DEFAULT_REGION`, `Identity`, `CONF_*` |

**Non-camera devices are upstream's job.** `CameraClient.get_device_client`
returns a plain upstream `DeviceClient` for anything that is not a camera, so
lights run upstream's code with none of ours in the path - except for the
carried overrides below.

## Upstream is LAN-only, by design

This is the single most important thing to know before proposing a "cloud mode"
for lights, plugs or switches.  Upstream's transport model is: **the cloud for
discovery and auth, the LAN for everything else.**

- `aidot.api.cloud_api.CloudApi` exposes only `login`, `refresh_token`,
  `get_houses`, `get_devices`, `get_products`.  There is **no device-control
  endpoint** - it is an inventory and authentication API.
- `DeviceClient.send_dev_attr()` refuses outright unless the LAN session is
  authenticated: `if self._state != DeviceState.AUTHENTICATED: raise
  ConnectionError("Device offline")`.  Every setter (`async_set_cct`,
  `async_set_rgbw`, `async_set_brightness`, `async_turn_on/off`) goes through it.
- `write_request()` frames an AES-encrypted packet over **TCP:10000**.
- `status.online` therefore means "the LAN control channel is up", not "the cloud
  says the device is reachable".  A device that is powered and visible in the
  AiDot app but not reachable over the LAN is correctly reported offline.

Consequence for consumers: a device off the LAN cannot be controlled at all, and
its on/off/colour cannot be read.  Do not paper over that by copying the cloud's
reachability flag onto it - that yields an entity that looks controllable, shows
a fabricated state, and raises `ConnectionError` when touched.  Report state as
unknown instead.

**Could a cloud transport be added?** The camera layer does control cameras over
the account MQTT broker (`iot/v1/c/{deviceId}/device/setDevAttrReq`), and that
topic is device-generic, so cloud *control* of a bulb is plausible.  But a
read-only broker probe over a 300 s window on a real account (67 messages,
`broker connected rc=0`) saw **only camera traffic** - `IPC/iceCandidateReq`,
`IPC/webrtcResp`, `device/setDevAttrResp`, and camera attributes
(`LightOnOff`, `siren_level`, `strobe_*`, `warning_*`).  **No bulb ever published
`OnOff`, `Dimming`, `CCT` or `RGBW`.**  So the cloud carries no light state on
that account, and a cloud mode could fix availability but never on/off or colour
- which is worse than reporting the device unreachable.  Re-run
`tools/cloud_state_probe.py` before revisiting this conclusion.

## Known dual-support gaps

Places where supporting both upstream shapes is not perfectly symmetric.  Each
is deliberate and narrow; none affects the camera path.

| Gap | Which shape | Why it is acceptable |
| --- | --- | --- |
| `CameraDeviceClient.read_data` is not defined | dict shape | The dict shape inlines the decrypt into `receive_data`, so there is no seam and the raw JSON never escapes.  That hook exists only to recover camera-only attribute keys upstream's typed `DeviceAttr` drops - and cameras never reach it anyway, because `async_login` returns early for IPC models and a camera therefore never opens the TCP:10000 session the loop reads from.  On the dict shape `_notify_status_update` finds no stashed payload and degrades to a plain notify. |
| `CameraClient._on_token_refreshed` is not defined | dict shape | There is no such hook to override.  It exists on the typed shape to force the rotated token into the shared `login_info` before `_token_fresh_cb` persists it; the dict shape writes the token straight into `login_info` and fires the callback itself, so the guarantee already holds.  The proactive-refresh rescheduling it also did is covered by `_reschedule_after_refresh`, called from `_do_ensure_token`. |
| `DeviceState` is a local stand-in, not upstream's enum | dict shape | Upstream deleted the enum (the session is `_connecting` / `_connect_and_login` booleans now).  Dropping a public name outright would break the integration at import, so a value-identical `IntEnum` is supplied.  Nothing upstream *produces* one there - call `_upstream.device_session_authenticated(client)` for the answer, which works on both. |
| `rsa_encrypt` is implemented locally | both | The typed shape had `rsa_encrypt(message, public_key)`; the dict shape replaced it with a one-argument `rsa_password_encrypt(message)`.  Neither signature exists on both, and `aidot_cameras.crypto.rsa_encrypt` is public surface the integration repo may import, so the two-argument form is kept and satisfied here. |
| Discovered addresses are per-instance | dict shape | The typed shape needed the sweep to write into the process-wide `Discover.DISCOVERED_DEVICE` class dict, because that is what upstream's `get_device_client` reads.  The dict shape reads `self._discover.discovered_device` off our own object instead, so a per-instance map suffices - and is better: two accounts no longer pool addresses in global state. |

### The typed shape is no longer supported

On the typed shape (0.3.54-0.3.55) the LAN retry policy could not apply to
lights: `LanRetryMixin` wraps upstream's `connect` and replaces
`_schedule_reconnect`, and that shape's client has neither - only `async_login`
and a `_reconnect_timer`. Two tests in
`tests/test_lan_retry_reaches_the_storming_devices.py` failed on 0.3.55 for
this reason. It stayed hidden until 2026-09-23 because every light on that
shape failed even earlier, at construction.

Rather than build LAN retry for a shape that lived five days on PyPI and that
no known install runs (Home Assistant core pins 0.3.56), the floor was raised to
0.3.56 and the 0.3.55 CI arm removed.
`test_upstream_compat.py::test_declared_range_excludes_the_typed_shape` pins
that decision: lowering the floor again means solving the LAN retry gap first.
The rows above that mention the typed shape describe code that is now
unreachable, kept until someone removes those branches.

## Carried overrides (self-liquidating)

A "carried override" is a fix we needed before upstream shipped it.  Each one is
marked in the source with a `# CARRIED:` comment naming **its own** drop
condition, applies as narrowly as possible, and is **deleted** once that
condition is met - after which those devices fall through to pure upstream.

| Override | Upstream PR | Drop when |
| --- | --- | --- |
| `active_color_mode` on RGBW+CCT bulbs (reports color temp instead of a stale RGB color) | `AiDot-Development-Team/python-AiDot#6` | that PR merges |
| `CameraClient.async_close()` cancels each device client's pending reconnect (`_reconnect_timer` on the typed shape, `_reconnect_handle` on the dict shape - see `_upstream.cancel_pending_reconnect`) | not filed yet - see [Inherited upstream defects](#inherited-upstream-defects) | upstream's `DeviceClient.close()` cancels it itself |

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
| `close()` does not cancel an armed reconnect timer.  `reset()` arms a delayed re-login (~45 s) and `close()` only sets its closed flag, which prevents arming a *new* one but not the one already ticking.  **Present on both shapes**, under different attribute names. | `aidot/device_client.py` `reset()` / `close()` | A non-camera device client reconnects about 45s after the account was closed, leaking a TCP connection, a receive task and a ping timer.  Worked around by the carried override in `CameraClient.async_close()` above.  Worth an upstream PR. |
| `update_ip_address(ip)` accepts `None`, overwriting a known IP with it, and calls `asyncio.create_task()` unguarded - a `RuntimeError` when no loop is running. | `aidot/device_client.py` `update_ip_address()` | Callers must never pass an unresolved address and must call it from the loop.  Our discovery callback only forwards addresses it actually resolved, and never calls it for cameras at all (a camera would answer TCP:10000's connect and then never reply). |

## When a seam breaks

A broken seam shows up as an `ImportError` or `AttributeError` naming the symbol,
in one of our modules - not as a merge conflict. A *changed signature* can
instead surface as a `TypeError` from deep inside upstream, naming nothing about
which release moved; `test_device_client_requires_only_arguments_we_know_how_to_supply`
exists to turn that into a failure that names the parameter. To fix it:

1. Read the upstream diff for the symbol that moved
   (`git log`/`git diff` on the upstream repo, or the release notes). Diff the
   **module-level constants** as well as the function signatures: 0.3.57's
   API-version change was a constant, and a signature diff alone missed it.
2. Update the single extension module that referenced it. Constants belong in
   `aidot_cameras/const.py`, crypto in `aidot_cameras/crypto.py`, exceptions in
   `aidot_cameras/exceptions.py` - camera modules import from those, so a moved
   upstream name is usually a one-line change in one file.
3. Add or adjust the assertion in `tests/test_upstream_compat.py` so the new
   shape is covered. Where the change is a new argument or a new arity, also
   cover it in `tests/test_upstream_seam_adapter.py`, which stubs the upstream
   constructors so *both* arities are tested whichever upstream is installed.
4. Never widen an allowed list in those tests just to make it pass. Each entry
   records something that was verified; add one only once it has been.

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
