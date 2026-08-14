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

Verified by diffing against the 1.14.0 sdist, not from memory. Two files differ,
and both differences are mechanical import rewrites required by vendoring - no
behaviour is changed:

    contrib/signaling.py   from aiortc import ...        -> from .. import ...
                           from aiortc.sdp import ...    -> from ..sdp import ...
    rate.py                from aiortc.utils import ...  -> from .utils import ...

One file is not carried over:

    py.typed               not vendored, so the vendored package is untyped

Everything else is byte-identical to the sdist.

## Re-vendoring

    pip download --no-deps --no-binary :all: aiortc==<version>
    tar xzf aiortc-<version>.tar.gz
    diff -rq --exclude=__pycache__ aiortc-<version>/src/aiortc aidot_cameras/_vendor/aiortc

Reapply the three import rewrites above, refresh `LICENSE` from the sdist, and
update this file. If the delta ever grows past import rewrites, say so here -
a reader needs to know whether they are running upstream code or a fork.
