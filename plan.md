# Plan: Fix Camera User Info ICE Failure (LK.IPC.A001064)

## Problem Summary

Camera `LK.IPC.A001064` ("Rear of Garage PTZ") fails WebRTC ICE after 30s.
The log shows ICE going from `checking` → `closed` without ever receiving STUN
probes from the camera or a real `webrtcResp` with camera ICE candidates.

## Root Cause Analysis

### Confirmed observations from the log

1. `batchGetDeviceUserInfo` returns keys `['deviceId', 'userId', 'userUuid']`
2. We extract `userId` (numeric: `1348043005373399042`) but **never extract or use `userUuid`**
3. Camera only echoes our `webrtcReq` — no real `webrtcResp` or `iceCandidateReq` arrives
4. The echo-only path fires; synthetic ICE candidates are added at **wrong ports**:
   - `192.168.1.217:60705` and `192.168.1.217:47324` (our aiortc ports, not camera's)
5. ICE probes those wrong ports, gets no response, exhausts retransmissions → closed

### Most likely root cause: missing `userUuid` MQTT subscription

When the camera sends its real `webrtcResp` (answer with its own SDP and ICE candidates),
it publishes to `iot/v1/c/{camera_userUuid}/IPC/webrtcResp`.

Currently we subscribe to:
- `iot/v1/c/{app_uuid}/#`
- `iot/v1/c/{device_id}/#`
- `iot/v1/c/{numeric_userId}/#`

If `camera_userUuid` ≠ `app_uuid` and ≠ `device_id` and ≠ `numeric_userId`,
the camera's real response is silently dropped — we only see broker echoes of our own messages.

This explains:
- `answer_fut` never fires (no real `webrtcResp`)
- `camera_offer_fut` never fires (no real `webrtcReq` from camera)
- `second_answer_fut` never fires
- We fall into echo-only path with wrong synthetic ports
- ICE fails

### Secondary issue: synthetic candidates use wrong ports

When we do use the echo-only path, `_rr_cam_ports` is populated from the echo SDP,
which contains **our** aiortc-allocated ports (60705, 47324), not the camera's ports.
The camera will never respond to STUN at those ports.

## Proposed Fixes

### Fix 1 (Primary): Subscribe to `userUuid` MQTT topics

**File**: `aidot/device_client.py`

In `async_open_webrtc_stream`, after extracting `_numeric_uid_raw`:

```python
# Extract camera's userUuid for additional MQTT topic subscription
_cam_user_uuid = (_cam_user_info or {}).get("userUuid") or None
if _cam_user_uuid:
    _LOGGER.info("async_open_webrtc_stream: camera userUuid=%s", _cam_user_uuid)
```

Then in the `sub_topics` list construction (around line 2700-2718), add:

```python
if _cam_user_uuid and _cam_user_uuid not in (user_id, device_id, numeric_user_id or ""):
    sub_topics += [
        f"iot/v1/c/{_cam_user_uuid}/#",
        f"iot/v1/cb/{_cam_user_uuid}/#",
        f"lds/v1/c/{_cam_user_uuid}/#",
    ]
    _LOGGER.info(
        "async_open_webrtc_stream: subscribing to camera userUuid topics (%s)",
        _cam_user_uuid,
    )
```

Also pass `_cam_user_uuid` through to `_open_sdes_stream` for the same subscription.

**Expected result**: If the camera publishes its `webrtcResp` or `webrtcReq` to
topics containing its `userUuid`, we will now receive it. `answer_fut` or
`camera_offer_fut` resolves, providing real ICE candidates → ICE succeeds.

### Fix 2 (Secondary): Log `userUuid` diagnostically even if it matches known IDs

Always log `userUuid` value in DEBUG to help diagnose future issues:

```python
_LOGGER.debug(
    "batchGetDeviceUserInfo: device=%s  userId=%s  userUuid=%s",
    self.device_id, _numeric_uid_raw, (_cam_user_info or {}).get("userUuid"),
)
```

### Fix 3 (Secondary): Improve echo-only synthetic candidate ports

If after Fix 1 the camera still doesn't send its own SDP, improve the fallback
synthetic candidate injection:

Instead of using aiortc's dynamic ports (from echo SDP), use the SDES-path
ports when available. The SDES attempt's audio/video ports (e.g., 38917/35242)
may be closer to the camera's actual listening ports than aiortc's (60705/47324).

However, this is speculative. If Fix 1 works (camera's real SDP arrives), the
correct camera ports are used directly and this fallback is irrelevant.

### Fix 4 (Tertiary): Add timeout extension for `second_answer_fut`

After sending `webrtcResp` + `iceCandidateReq` in the role-reversal path,
explicitly wait 3s for `second_answer_fut` before starting the main ICE loop.
Currently `second_answer_fut` is processed inside the 30s ICE loop, but the
loop exits early when ICE fails — if the camera sends its real SDP just after
ICE closes, it's never processed.

This is a minor improvement if Fix 1 does not fully solve the issue.

## Implementation Order

1. **Fix 1**: Extract and subscribe to `userUuid` topics (most impactful)
2. **Fix 2**: Add diagnostic logging for `userUuid`
3. **Fix 3** and **Fix 4**: Only if Fix 1 doesn't resolve ICE

## Files to Modify

- `aidot/device_client.py`:
  - Around line 2635: extract `_cam_user_uuid` from `_cam_user_info`
  - Around line 2700-2718: add `_cam_user_uuid` topics to `sub_topics`
  - Around line 4337 (`_open_sdes_stream`): accept and use `cam_user_uuid` param

## Testing

Run `python test_camera.py` against camera `12b144cb12da4994945bffd4f1acfd0c`
and check:
- Does `webrtcResp` or `webrtcReq` from camera now arrive (non-echo)?
- Does ICE succeed (`ICE connectionState → connected`)?
- Are frames received via ffmpeg/aiortc?
