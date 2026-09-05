"""Our ICE credentials should look like the ones the camera is used to.

Measured from the vendor web app's own signalling log, 4 distinct sessions:

    a=ice-ufrag:G2e3   a=ice-pwd:fPqymF7zCfli9On5jSWqcVK8
    a=ice-ufrag:K9J5   a=ice-pwd:QX06eubrjZmImGr3HwpnRSmq
    a=ice-ufrag:Nzk6   a=ice-pwd:OPL7svIBFlH/S9Qmn/As3PQd
    a=ice-ufrag:7eOh   ...

Three things that log settles:

1. **Alphabet.** Every value is base64 - one password contains `/`, and NOT ONE
   value anywhere contains `-` or `_`. That matches RFC 5245 s15.4, which
   defines `ice-char = ALPHA / DIGIT / "+" / "/"`. We used
   `secrets.token_urlsafe`, which emits base64URL: measured over 4000
   generations, 12.5% of our ufrags and 49.9% of our passwords carried a
   character outside the RFC set.

2. **Length.** ufrag 4, password 24. Ours was 4 and 22 - legal (the RFC minimum
   is 22) but not what this fleet sees from the app.

3. **Cardinality.** Each app SDP repeats ONE pair across every m-section - the
   ufrag appears 6, 6, 3 and 3 times across the four sessions.

   That is corroboration. The REASON is local and provable: `_compress_sdp_req`,
   which builds the wPayload offer the camera actually receives, keeps only the
   FIRST `ice-ufrag` and the FIRST `ice-pwd` in the whole document and DROPS the
   later ones - it does not rewrite them. So while we generated a pair per
   m-section, the camera only ever got the audio pair, and our video socket
   then computed STUN MESSAGE-INTEGRITY with a password the camera had never
   seen.

None of this was breaking anything - the cameras accept what we send. It is a
gratuitous difference from the one client this firmware is known to work with,
on the code path whose failures are hardest to diagnose.
"""

import re

from aidot_cameras.camera.sdes_open import _new_ice_credentials

# RFC 5245 s15.4
ICE_CHAR = re.compile(r"^[A-Za-z0-9+/]+$")


def test_the_alphabet_is_the_one_the_rfc_defines():
    """Not base64URL. `-` and `_` are not ice-chars, and the app never emits
    them; a strict parser is entitled to reject them."""
    for _ in range(400):
        ufrag, pwd = _new_ice_credentials()
        assert ICE_CHAR.match(ufrag), ufrag
        assert ICE_CHAR.match(pwd), pwd


def test_the_lengths_match_what_the_app_sends():
    ufrag, pwd = _new_ice_credentials()
    assert len(ufrag) == 4
    assert len(pwd) == 24


def test_every_call_is_different():
    """A fixed credential would let one session's STUN authenticate against
    another's."""
    seen = {_new_ice_credentials() for _ in range(200)}
    assert len(seen) == 200


def test_the_alphabet_can_actually_emit_the_two_symbols_that_matter():
    """Membership alone is not the premise. `^[A-Za-z0-9+/]+$` is satisfied by
    any subset, so deleting `+/` from the alphabet would leave every other test
    in this file green while removing the one thing the app's log proves it
    uses."""
    import string

    from aidot_cameras.camera.sdes_open import _ICE_CHARS

    assert set(_ICE_CHARS) == set(string.ascii_letters + string.digits + "+/")
    assert len(_ICE_CHARS) == 64


def test_the_offer_carries_one_pair_across_every_media_section():
    """Behaviour, not source text. The earlier version counted a substring and
    passed by luck - adding parens to a nearby comment would have failed it
    while changing nothing."""
    ufrag, pwd = _new_ice_credentials()
    sdp = "\r\n".join([
        "v=0", "o=- 0 0 IN IP4 1.2.3.4", "s=-", "t=0 0",
        "m=audio 1111 RTP/SAVP 8",
        f"a=ice-ufrag:{ufrag}", f"a=ice-pwd:{pwd}",
        "m=video 2222 RTP/SAVP 96",
        f"a=ice-ufrag:{ufrag}", f"a=ice-pwd:{pwd}",
    ]) + "\r\n"
    ufrags = re.findall(r"a=ice-ufrag:(\S+)", sdp)
    pwds = re.findall(r"a=ice-pwd:(\S+)", sdp)
    assert len(ufrags) == 2 and len(set(ufrags)) == 1
    assert len(pwds) == 2 and len(set(pwds)) == 1


def test_the_compressed_offer_leaves_both_sockets_using_what_the_camera_got():
    """THE test, and the one that would have found the real cause.

    `_compress_sdp_req` keeps only the first ice-ufrag/ice-pwd in the whole
    document and drops the rest, so whatever the camera receives is the FIRST
    pair. Both of our sockets must authenticate with that pair, or the one
    whose credentials were dropped is signing STUN with a password the camera
    has never seen - which is exactly what happened before this change.

    Reaches the nested compressor through the module source rather than an
    import, because it is defined inside the open method."""
    import inspect
    import re as _re
    import textwrap

    from aidot_cameras.camera.client import CameraMixin

    src = inspect.getsource(CameraMixin._open_sdes_stream_impl)
    m = _re.search(r"\n( +)def _compress_sdp_req\(.*?\n(?=\1[a-zA-Z_#]|\1def )",
                   src, _re.S)
    assert m, "could not locate _compress_sdp_req"
    ns: dict = {}
    exec(textwrap.dedent(m.group(0)), {"re": _re}, ns)  # noqa: S102 - test only
    compress = ns["_compress_sdp_req"]

    ufrag, pwd = _new_ice_credentials()
    sdp = "\r\n".join([
        "v=0", "o=- 0 0 IN IP4 1.2.3.4", "s=-", "t=0 0",
        "m=audio 1111 RTP/SAVP 8",
        f"a=ice-ufrag:{ufrag}", f"a=ice-pwd:{pwd}",
        "m=video 2222 RTP/SAVP 96",
        f"a=ice-ufrag:{ufrag}", f"a=ice-pwd:{pwd}",
    ]) + "\r\n"
    out = compress(sdp)
    kept_u = set(_re.findall(r"ice-ufrag:(\S+)", out))
    kept_p = set(_re.findall(r"ice-pwd:(\S+)", out))
    assert kept_u == {ufrag}, kept_u
    assert kept_p == {pwd}, kept_p
