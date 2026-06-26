# Deferred Features

This document records functionality that was researched, prototyped, and deliberately
not merged into the active code path.  Each section explains:

- **What it does** - a description of the feature and where in the codebase it lives
- **Why it was written** - the technical motivation and evidence base
- **Why it is deferred** - the specific reason it cannot be activated right now
- **Test results** - the evidence gathered during investigation
- **Re-activation notes** - what would be needed to enable it

---

## 1. TUTK SDK Improvements (liveType=0 cameras)

### What it does

Three improvements to `TutkStreamSession._start_sync` (the TUTK C SDK P2P path in
`src/aidot_cameras/device_client.py`) were researched from the `LdsTutkChannel.java` class in
a decompiled AiDot APK:

#### 1a. License-key initialisation (`TUTK_SDK_Set_License_Key`)

The APK calls `TUTK_SDK_Set_License_Key` before `IOTC_Initialize2`.  Without the key
the SDK operates in demo mode (limited session count and potential frame-count caps).
A license key was extracted from the APK and stored as the module-level constant
`_TUTK_LICENSE_KEY`.

*Code at the time of research (commit 8350553):*

```python
iotc.TUTK_SDK_Set_License_Key.restype  = ctypes.c_int
iotc.TUTK_SDK_Set_License_Key.argtypes = [ctypes.c_char_p]
# …
if self._license_key:
    ret = iotc.TUTK_SDK_Set_License_Key(self._license_key.encode())
    if ret < 0:
        _LOGGER.error("TUTK_SDK_Set_License_Key failed: %d", ret)
        return False
```

#### 1b. Session keepalive (`IOTC_Setup_Session_Alive_Timeout`)

`LdsTutkChannel.java` calls `IOTC_Setup_Session_Alive_Timeout(15)` after
`IOTC_Initialize2` succeeds.  The original `TutkStreamSession` omits this call, which
can cause the server to silently drop the session after the SDK's default idle timeout.

```python
iotc.IOTC_Setup_Session_Alive_Timeout.restype  = None
iotc.IOTC_Setup_Session_Alive_Timeout.argtypes = [ctypes.c_int]
# …
iotc.IOTC_Setup_Session_Alive_Timeout(15)
```

#### 1c. CONNECTION_CHECK_REQ handshake + 24-byte IPCAM_START body

The APK sends IOCtrl 496 (`CONNECTION_CHECK_REQ`) immediately after `avClientStartEx`,
with a 16-byte body containing the clientId.  Some firmware versions withhold video
frames until this handshake is received.  It also pads the IPCAM_START (511) body to
24 bytes with the clientId embedded at offset 8, matching `LdsTutkChannel.java`.

The original `TutkStreamSession` sends only 8-byte all-zeros IPCAM_START and omits
the CONNECTION_CHECK_REQ entirely (sourced from the earlier `TutkManager.java` class).

#### 1d. Per-device credentials (`tutkAccount` / `tutkPassword`)

`batchGetDeviceUserInfo` can return per-device `tutkAccount` / `tutkPassword` fields.
`LdsTutkChannel.java` uses these instead of the hardcoded `admin` / `admin123`
defaults.  The feature adds a `batchGetDeviceUserInfo` call in `async_open_live_stream`
and passes the resulting credentials through `TutkStreamSession.__init__`.

### Why it was written

All four changes were derived from a side-by-side comparison of `TutkManager.java`
(the older protocol implementation that the original code was based on) and
`LdsTutkChannel.java` (the current implementation in the APK).  The APK analysis was
done with a decompiler; the class paths are stable across at least two APK versions.

### Why it is deferred

**None of the cameras on the active Home Assistant instance use the TUTK C SDK path.**

All cameras were queried via the AiDot API
(`POST /v21/devices/batchGetDeviceUserInfo`) from the HA host (June 2026).  Every
device returned `liveType=2`, meaning they use WebRTC (DTLS-SRTP or SDES-SRTP).
None returned a `p2pId` field (the TUTK UID required to invoke `TutkStreamSession`).

Activating the TUTK SDK path requires:
- `liveType=0` in the device properties, or
- A `p2pId` returned by `batchGetDeviceUserInfo` or the `getP2pId` endpoint.

Because none of the connected devices meet either condition, the improved TUTK
initialisation sequence cannot be exercised and could not be validated.

The base `TutkStreamSession` scaffolding (load TUTK native libs, connect P2P via
`IOTC_Connect_ByUID_Parallel`, receive frames via `avRecvFrameData2`) remains in the
codebase for future `liveType=0` camera support.  Only the *incremental improvements*
added in commit `8350553` were reverted.

### Test results

| Test | Result |
|------|--------|
| API query of all connected cameras for `liveType` | All returned `"2"` - WebRTC path |
| API query of all connected cameras for `p2pId` | Field absent on every device |
| HA logs for `TutkStreamSession` code paths | No log entries from TUTK path in 2 weeks of operation |
| Python import of `TutkStreamSession` class | OK (ctypes stubs load; native libs absent on HA host) |

### Re-activation notes

To re-enable for a `liveType=0` camera:

1. Re-apply the changes from commit `8350553` to `TutkStreamSession`, or add them as
   a new commit referencing this document.
2. Obtain `libIOTCAPIs.so` and `libAVAPIs.so` from the TUTK SDK distribution or an
   extracted AiDot APK and place them accessible to the HA Python process.
3. Verify the camera's `batchGetDeviceUserInfo` response returns a `p2pId`.
4. Call `DeviceClient.async_open_live_stream()` - the improved init sequence
   (`TUTK_SDK_Set_License_Key`, `IOTC_Setup_Session_Alive_Timeout`, CONNECTION_CHECK_REQ,
   24-byte IPCAM_START) is the path described in `LdsTutkChannel.java`.

---

## 2. TCP TLS Certificate Validation (`CERT_OPTIONAL` in `LiveStreamSession`)

### What it does

`LiveStreamSession` (the TCP binary playback protocol used for recorded video) opens
an optional TLS connection to the camera's playback server.  When TLS is enabled, the
SSL context is configured with `ssl.CERT_OPTIONAL` rather than `ssl.CERT_NONE`:

```python
ssl_ctx.verify_mode = ssl.CERT_OPTIONAL
```

`CERT_OPTIONAL` validates the certificate chain when the camera presents a verifiable
CA-signed cert, while still accepting self-signed certs.  `CERT_NONE` skips validation
entirely.

### Why it was written

Cameras with CA-signed certs benefit from chain validation without requiring embedded
CA cert pinning.  `CERT_OPTIONAL` is a forward-compatible default: it silently accepts
self-signed certs today, and will validate automatically if future firmware switches to
CA-issued certs.

### Current status: ACTIVE (not reverted)

Unlike the TUTK improvements above, `CERT_OPTIONAL` is **not** a `liveType=0`-only
change.  `LiveStreamSession` is also used for recorded-video playback on the active
cameras.  Reverting to `CERT_NONE` would be a strict security downgrade on live code.

This section is included to explain why `CERT_OPTIONAL` was retained when the other
commit `8350553` changes were reverted.

---

*Last updated: 2026-06-08*
