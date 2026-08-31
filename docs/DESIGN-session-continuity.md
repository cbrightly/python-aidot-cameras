# Design: surviving a camera that ends its own streaming session

> ## RESOLVED in 1.0.0b34 - the camera was not ending the session, we were
>
> This document was written against "the A001064 ends its own streaming session
> roughly every 60 to 85 seconds". It does, and the cause was in this library.
>
> SCTP puts acknowledgement on the receiver. We never sent a SACK, so the
> camera's control-channel retransmissions ran to exhaustion and it ABORTed the
> association at 61.4 s (sd 0.10, n=47). Our keepalive could no longer reach it,
> and its own 20 s watchdog then closed the session: 80.2 - 60.08 = 20.1 s
> against a 20.000 s constant in the camera. Sending a SACK ends it. Sessions now
> run unbounded, and PTZ, two-way audio and SD listing keep working for the whole
> session instead of dying with the channel about a minute in.
>
> Nothing below needs to be built. Keep it for the architectural notes and as a
> record of how the problem looked from the outside; read the 1.0.0b34 changelog
> entry for what it actually was.

Status: proposed, not built, and **largely superseded**. Both Phase 0
measurements came back against it:

- the vendor app does not show the interruption at all, so the recycle is not
  firmware-inevitable and preventing it beats masking it;
- an already-open view already survives a recycle unaided, so the specific harm
  this document set out to prevent does not occur.

What remains real is the *duration* of the stalls - about 56% of a five minute
viewing had no picture. Keep this document for its architectural notes, which
are still accurate, but do not build the stitching described below on the
strength of the original argument. It was aimed at a failure that does not
happen.

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

**0a. RESULT: an open view does NOT die. It stalls and recovers by itself.**
Measured over a 300 second hold on the affected camera: 2634 frames delivered,
eleven stalls, ten of which recovered unaided - including gaps of 17, 28 and 49
seconds. Only the last was still stalled when the window ended.

**This refutes the premise this document was written on.** The reasoning above -
that terminating ffmpeg drops the publish, drops go2rtc's publisher, and takes
every attached viewer with it - is wrong about the outcome. Something downstream
re-establishes: the viewer's session survives the camera's session ending. The
code path reads as though it should be fatal and empirically is not, which is
exactly why this had to be measured rather than reasoned about.

**So the problem is not survival, it is duration.** Roughly 167 of those 300
seconds had no picture - about 56% of the window - in stalls ranging from 1 to 57
seconds. A viewer is not losing the stream; they are watching a frozen picture
for long stretches. That is a different fix: shorten the gap, not preserve the
session.

(!) The publisher sampling in that run is not evidence of anything: it polled
go2rtc on port 1984 (the AlexxIT instance) while Home Assistant's WebRTC path
serves from the bundled instance, so it reported `pub=NO` throughout a viewing
that was plainly delivering frames. Point it at the instance actually serving
before using it to attribute a stall.

**0b. RESULT: the official application does NOT blip.** Reported from real use:
the camera "has been behaving just fine in the app" across ordinary viewing, with
no break every minute or two. Treat this as strong evidence rather than a
controlled measurement - it was not instrumented, and a very brief reconnect
could pass unnoticed - but it is the observation this whole design was waiting
on, and it points the opposite way to what was assumed.

**This means the recycle is NOT an unavoidable property of the camera.** The
hardware can clearly sustain a continuous session; something about how the app
asks for it differs from how we do. Masking the gap is therefore the wrong
primary goal. The continuity work below remains worth having as a safety net -
terminating ffmpeg and taking every viewer down with it is bad behaviour
regardless of why the session ended - but it is no longer the fix.

**The leading candidate is bitrate, and the numbers are not close.** Measured on
the same camera on the same night:

| source | bitrate |
|---|---|
| the app, "auto" quality | **225 - 500 Kbps** |
| our validation run | 1,886 Kbps |
| our earlier hold | 1,600 Kbps |

We take four to eight times what the app takes, and we are the one that gets
recycled. The camera was confirmed set to `hd`.

(!) **The project guide lists "low-quality stream profile" as a dead hypothesis.
It is not dead - it was closed on faulty reasoning.** The check that killed it
established only that the camera was set to `hd`, i.e. it confirmed its own
premise. It never tested whether the camera behaves differently on `sd`. That
test is still owed: set `sd`, measure session lifetime at least three times, and
compare the distribution against the `hd` baseline of 62, 76, 80, 82, 83, ~100,
~120 and 243 seconds.

Also worth noting: neither side negotiates any RTCP feedback (our offer carries
no `a=rtcp-fb`, and neither does the camera's answer), so there is no mechanism
by which the camera could adapt its rate downward for us mid-session. If the app
runs at a quarter of our bitrate, it is far more likely asking for something
different up front than adapting on the fly. That is a signalling difference to
capture off the wire, not to guess at - eight guesses have already died here.

**Superseded framing, kept because the reasoning still applies if the above
fails:**

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
