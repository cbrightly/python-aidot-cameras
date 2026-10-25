# Vendored aiortc

Upstream: aiortc 1.14.0 (https://github.com/aiortc/aiortc), BSD 3-Clause.
The upstream licence is kept beside this file as `LICENSE` and is included in
the distribution.

## Why it is vendored

Home Assistant 2026.7 pins `av==17`, and stock aiortc 1.14.0 caps `av<17`, which
makes `python-aidot-cameras[webrtc]` uninstallable there. Vendoring lets the PyAV
ceiling be widened without waiting on an upstream release. This should go away
and depend on stock aiortc again once upstream widens the pin.

## The delta from upstream, exactly

Verified by diffing against the 1.14.0 sdist, not from memory. Four files
differ. Two are mechanical import rewrites required by vendoring, with no
behaviour change:

    contrib/signaling.py   from aiortc import ...        -> from .. import ...
                           from aiortc.sdp import ...    -> from ..sdp import ...
    rate.py                from aiortc.utils import ...  -> from .utils import ...

Two are a behaviour fix, every changed line marked with an `aidot:` comment.
Closing a peer connection while ICE was still checking (a failed or aborted
open) left the connectivity checks running against closed sockets: their STUN
retry timers raised `Exception in callback Transaction.__retry()`, the
fire-and-forget connect task raised `RTCIceTransport is closed` into nobody,
and aioice's check loop, which never saw end-of-candidates, polled every 20 ms
for the life of the process. The fix:

    rtcpeerconnection.py   keep the __connect tasks; close() cancels and
                           awaits them before stopping any transport
    rtcicetransport.py     stop() cancels and awaits the in-flight
                           connectivity checks (aioice Connection._check_list,
                           CandidatePair.task) before and after closing the
                           connection; start() leaves a stopped transport
                           "closed" instead of moving it to failed/completed

On a session that connected, the connect task and the checks have already
finished, so the fix does nothing there. The aioice internals it reads are
held by `tests/test_aioice_compat.py`; the behaviour by
`tests/test_close_mid_ice_leaves_nothing_pending.py`.

One file is not carried over:

    py.typed               not vendored, so the vendored package is untyped

Everything else is byte-identical to the sdist.

## Re-vendoring

    pip download --no-deps --no-binary :all: aiortc==<version>
    tar xzf aiortc-<version>.tar.gz
    diff -rq --exclude=__pycache__ aiortc-<version>/src/aiortc aidot_cameras/_vendor/aiortc

Reapply the three import rewrites and the close fix above (unless upstream
has fixed close mid-ICE itself - run the test named above against the new
version unpatched to find out), refresh `LICENSE` from the sdist, and update
this file. If the delta ever grows past import rewrites, say so here -
a reader needs to know whether they are running upstream code or a fork.
