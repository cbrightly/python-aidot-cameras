# Full WebRTC Integration Patch

Apply the following changes to `aidot/device_client.py`.

---

## 1. Imports

```python
from aidot.webrtc_ice_fix import apply_filtered_offer, small_delay, filter_sdp_candidates
```

---

## 2. Replace Offer Creation

```python
await apply_filtered_offer(pc)
```

---

## 3. TURN / ICE config fix

Ensure RTCPeerConnection is created like this:

```python
pc = RTCPeerConnection({
    "iceServers": [
        {"urls": ["stun:stun.l.google.com:19302"]}
    ]
})
```

If TURN exists from `getIceConfigReq`, append it dynamically.

---

## 4. Role Reversal Fix

REMOVE:

```python
if answer_fut.done():
    return
```

REPLACE WITH:

```python
# Always accept latest SDP (camera sends real answer later)
```

---

## 5. Timing Fix

Before sending signaling messages:

```python
await small_delay()
```

---

## 6. Candidate Filtering (extra safety)

If manually handling SDP:

```python
sdp = filter_sdp_candidates(sdp)
```

---

## Expected Result

ICE should transition to:

```
checking → connected
```

If not, TURN credentials are missing or camera requires LAN IP injection.

---

## Notes

This camera behaves like:
- ICE-lite
- role-reversal SDP
- sometimes ignores first answer

These fixes align behavior with browser WebRTC.
