"""The constructor adapter, exercised against BOTH upstream arities at once.

``test_upstream_compat.py`` asserts against the upstream that happens to be
installed, so it can only ever see one arity per run.  This file stubs the
constructor shapes instead, so the 2-argument and 3-argument paths are both
covered on 0.3.55, 0.3.56 and 0.3.58 alike - which matters because the arity
we are NOT running is the one that rots unnoticed.

Nothing here imports ``aidot``.
"""

import inspect

import pytest

from aidot_cameras import _upstream


# --------------------------------------------------------------------------- #
# Stand-ins for the upstream constructors we must bind to
# --------------------------------------------------------------------------- #


class _TwoArg:
    def __init__(self, device, user_info): ...


class _ThreeArg:
    """0.3.57+: the account client arrives third, stored as ``self.client``."""

    def __init__(self, device, user_info, client): ...


class _KeywordOnlyClient:
    """A reshape we must NOT absorb - see the detection test."""

    def __init__(self, device, user_info, *, client=None): ...


class _SomeOtherThirdArg:
    """A different third argument must not be fed our account client."""

    def __init__(self, device, user_info, session): ...


_RECORD = {"id": "d1", "name": "d1", "modelId": "lk.WIFI-RGBWLight-D0006"}
_ACCOUNT = {"id": "u1", "region": "us"}


# --------------------------------------------------------------------------- #
# Detection: by signature, never by version
# --------------------------------------------------------------------------- #


def test_detection_reads_the_signature_not_a_version():
    """A capability probe, like every other flag in ``_upstream``.

    Upstream shipped the dict shape under both 0.3.53 and 0.3.56 with a typed
    excursion in between, so a version comparison encodes the excursion rather
    than the capability.  ``docs/UPSTREAM.md`` forbids it outright.
    """
    assert _upstream._init_takes_account_client(_TwoArg.__init__) is False
    assert _upstream._init_takes_account_client(_ThreeArg.__init__) is True


def test_a_reshaped_third_argument_is_refused_rather_than_absorbed():
    """Matching on arity alone would splat our client into anything.

    Both of these must read False so construction raises a loud ``TypeError``
    that names the parameter, instead of us silently supplying an account
    client to something that wanted a session - or silently stopping supplying
    one because it moved to keyword-only.
    """
    assert _upstream._init_takes_account_client(_SomeOtherThirdArg.__init__) is False
    assert _upstream._init_takes_account_client(_KeywordOnlyClient.__init__) is False


# --------------------------------------------------------------------------- #
# The adapter binds to whichever constructor is installed
# --------------------------------------------------------------------------- #


@pytest.mark.parametrize(
    "wants_backref, stub, expected_len",
    [(False, _TwoArg, 2), (True, _ThreeArg, 3)],
    ids=["two-arg", "three-arg"],
)
def test_args_bind_to_whichever_constructor_upstream_ships(
    monkeypatch, wants_backref, stub, expected_len
):
    """``.bind()`` proves the tuple would actually construct that signature."""
    monkeypatch.setattr(_upstream, "HAS_DEVICE_CLIENT_BACKREF", wants_backref)
    sentinel = object()

    args = _upstream.device_client_args(
        None, _RECORD, _ACCOUNT, _ACCOUNT, client=sentinel
    )

    assert len(args) == expected_len
    inspect.signature(stub.__init__).bind(object(), *args)
    if wants_backref:
        assert args[2] is sentinel


def test_the_account_client_cannot_be_forgotten():
    """Keyword-only and no default, on purpose.

    Upstream stores the back-reference unchecked, so a ``None`` that slipped
    through would not surface until something called ``async_set_effect`` and
    got ``'NoneType' object has no attribute 'async_execute_diff_command'`` -
    an upstream traceback for our bug.  Fail at our own boundary instead.
    """
    with pytest.raises(TypeError):
        _upstream.device_client_args(None, _RECORD, _ACCOUNT, _ACCOUNT)
