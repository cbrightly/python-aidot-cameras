# Design: surviving a camera that ends its own streaming session

Status: proposed, not built. Phase 0 must run before any code is written.

## The problem, stated precisely

One camera model (A001064) ends its own streaming session roughly every 60 to 85
seconds and starts a new one about 20 seconds later. This is not a fault we
introduced and it is very likely not one we can prevent - see "Is it really the
camera" below.

The session lifetime is **highly variable**: 62, 76, 80, 82, 83, ~100, ~120 and
once 243 seconds, under otherwise identical conditions. Any measurement of this
behaviour needs at least three runs per condition and a comparison of
distributions. A single-run A/B here manufactures false positives, and has
already done so once.

## What is actually ours

The camera ending its session is the camera's business. What happens next is
ours, and it is the part users see.

On a session ending, `session.stop()` terminates the ffmpeg process
(`sdes_open.py`, the `_teardown_holder[0] = True; proc.terminate()` path). That
process holds the RTSP publish into go2rtc. Killing it drops the publish, which
drops go2rtc's publisher, which drops **every consumer attached to it** -
including a viewer who is watching at that moment and has done nothing wrong.

So a camera-side event that should cost a brief freeze instead costs the whole
stream, and a viewer must start a new session and pay a cold open.

The architecture makes this avoidable. The bridge decrypts SRTP and forwards
**plain RTP over UDP to `127.0.0.1:<port>`**; ffmpeg reads an SDP pointed at
loopback. **UDP has no end-of-file.** ffmpeg does not exit when media stops - it
exits because we terminate it. Nothing about the transport requires the teardown.

## Phase 0 - two measurements, before any code

Both are cheap. Neither justifies writing code until it is done, and the second
could change the design entirely.

**0a. Does an open view actually die today?** Predicted yes, from the reasoning
above, but predicted is not measured. Attach a viewer, hold it across a recycle,
and record whether it recovers on its own. This sizes the problem: if viewers
somehow survive already, the remaining cost is only the ~20s window for *new*
views and this design is not worth building.

**0b. Does the official application blip?** Open the camera in the AiDot app and
watch for a break every minute or two. This is the decisive one:

- If the app **also** blips, the recycle is firmware, cannot be prevented, and
  masking it (below) is the whole of the available fix.
- If the app **does not** blip, something in its session keeps the camera
  streaming that ours does not, and finding that beats masking the symptom.

Note that app parity has already been checked at the signalling layer and found
complete: the app has exactly three periodic timers, and ours match, including
the 10-second heartbeat (command 5156), which is verified firing and
acknowledged. So 0b is asking whether the difference lies somewhere other than
signalling - most plausibly that the app drives a full WebRTC peer connection
with its own congestion control and feedback, where ours is a hand-rolled SRTP
bridge.

**Do not** resume piecemeal replication of peer-connection behaviour. Eight
hypotheses died that way already, including receiver reports, keepalive renewal,
consent refresh and connection mode. If 0b says the app is clean, the next step
is to capture what the app actually sends, not to guess at it again.

## The design: keep the publish alive across camera sessions

Make the camera's session boundary invisible below the bridge. ffmpeg and the
go2rtc publish become properties of the *serve*, not of a camera session.

1. **Allocate the loopback ports and spawn ffmpeg once per serve.** A camera
   session ending stops the flow of packets into those ports and nothing else.
   No terminate, no respawn, no new publish.

2. **Normalise RTP on the way through.** Each camera session brings its own
   SSRC, sequence numbers and timestamps; ffmpeg must not see them change. The
   bridge rewrites, per forwarded packet:
   - **SSRC** to a value we choose once and keep,
   - **sequence** from a monotonic counter of our own,
   - **timestamp** onto a continuous timeline, advancing across the gap by
     elapsed wall time in 90 kHz units so the output does not claim that twenty
     seconds took no time.

   This is the whole trick: ffmpeg then sees one unbroken RTP stream with a
   quiet stretch in it, which is an ordinary thing for an RTP stream to contain.

3. **During the gap, do nothing.** ffmpeg blocks on a socket that is simply not
   receiving. The publish stays up, go2rtc keeps its publisher, the viewer holds
   the last frame. A freeze is a far better outcome than a dead stream.

4. **On resume, guarantee decodability.** Request an IDR immediately and rely on
   in-band parameter sets. This camera is already marked as one whose parameter
   sets change between sessions, so cached-parameter injection is disabled for
   it and the in-band sets flow - which is exactly what a stitched stream needs.
   The two mechanisms agree by accident here; the comment in `hwaccel`/`protocol`
   about that interaction should say so explicitly, or a later change will break
   one by "cleaning up" the other.

5. **Refuse to stitch when stitching is unsafe.** If the payload type, codec or
   resolution differs from the previous session, the streams are not
   continuous and pretending otherwise produces a corrupt output that looks like
   a decoder bug. Detect it and fall back to today's full restart.

6. **Teach the stall watchdog the difference.** It currently restarts the stream
   on a silent window, which is precisely the behaviour being removed. It needs
   to distinguish "recycling, stitch in progress" from "genuinely dead", with an
   upper bound - if no new session arrives within, say, twice the observed
   reopen time, fall back to the full restart.

## What this does not do

It does not stop the recycle, and it cannot. Each camera has exactly **one media
slot**, so a make-before-break overlap - opening the next session before the
current one ends - is impossible. That was checked and is not a path.

It does not make a view that arrives *during* the gap fast. Such a view still
waits for the camera. It makes an *already-playing* view survive, which is the
common case for anyone actually watching.

Nor does it shorten the gap: the reconnect pacer was cut from 10s to 2s and
measured no better, because the gap is dominated by the camera's own 13-18s
reopen handshake. That patch was reverted and should not be retried.

## Validating it

The go2rtc lab (`scratchpad/lab/`) answers the riskiest question without a
camera or a deploy: **publish, stop feeding for twenty seconds, resume, and see
whether the consumer survives.** If go2rtc drops an idle publisher faster than
the camera returns, the freeze approach fails at the first hurdle and needs a
keepalive - better to learn that in a lab in minutes.

After that, on hardware, with at least three runs per condition:

- viewer survival across a recycle, before and after,
- time to usable picture after a recycle for an attached viewer,
- confirmation that a stitched stream still decodes with zero errors, using the
  `ffprobe` check that caught the parameter-set fault - a stream that demuxes
  happily while decoding nothing is exactly the failure this design could
  introduce.

## Risks worth stating

- **A twenty second freeze may read as "broken" to a user** as surely as a dead
  stream does. Consider surfacing the state rather than pretending all is well,
  and make the behaviour configurable.
- **Timestamp continuity is easy to get subtly wrong**, and the failure mode is
  a stream that plays at the wrong rate or drifts, which is harder to notice
  than an outright break.
- **This adds state to the forwarding path**, which is currently close to
  stateless and is the hottest code in the bridge.
