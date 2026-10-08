"""Exceptions for the camera layer.

Upstream's exception hierarchy is re-exported so callers have one import site;
only the camera-specific errors are defined here.
"""

from aidot.exceptions import (  # noqa: F401 - deliberate re-export surface
    AidotAuthFailed,
    AidotAuthTokenExpired,
    AidotError,
    AidotNotLogin,
    AidotOSError,
    AidotUserOrPassIncorrect,
    HTTPError,
    InvalidHost,
    InvalidURL,
)


class AidotCameraBusy(AidotError):
    """Camera refused the live stream with a TERMINAL ack code - retrying is futile.

    Raised when a ``webrtcResp`` carries ``ack.code`` in the terminal set:
      -50002  WEBRTC_ERROR_EN_RTC_ERR_CODE_SESSION_EXCEED (max concurrent streams)
      -50015  LIVE_SD_MAX_CONNECT_ERROR (SD-card / connection cap)

    The official app treats both as terminal (shows an error, does NOT retry -
    decompiled LiveCameraView.java:765). Callers should surface the error rather
    than burning their retry budget hammering a camera that already said no.
    """

    def __init__(self, code: int, desc: str = "") -> None:
        self.code = code
        self.desc = desc
        msg = f"camera refused stream: ack code {code}"
        if desc:
            msg += f" ({desc})"
        super().__init__(msg)


class AidotCameraNotReady(AidotError):
    """Camera answered cleanly but declined media (encoder not ready yet) - a
    DC-only WebRTC answer that rejects both audio and video, leaving only the
    data channel.  Distinct from AidotCameraBusy (terminal) and from hard
    failures: the camera is awake and answering, its media pipeline just isn't
    up.  The DTLS serve loop fast-retries this in a bounded burst instead of
    waiting the full 15s inter-attempt gate."""


class AidotCameraNoMedia(AidotError):
    """The camera was present and sent no media - abandon this attempt, retry now.

    The opposite of [[AidotCameraBusy]], which means stop retrying. This is
    raised when the stale-offer backstop ended the first-media wait, the grace
    that follows it expired, and nothing has been observed: building a serve SDP
    from nothing produces a video-only stream that then receives no media at
    all, so the attempt is abandoned in favour of the retry, whose fresh offer
    is what actually gets served.

    Callers pacing a retry must treat this as a session that ended without
    media, NOT as an open failure: the open-failure backoff escalates, and this
    case wants the fast not-ready retry it would have got had the doomed serve
    been launched and died.
    """

    def __init__(self, waited_s: float) -> None:
        self.waited_s = waited_s
        super().__init__(
            f"camera sent no media in {waited_s:.0f}s after answering - "
            "abandoning this attempt to the retry"
        )


class AidotCameraWrongCodec(AidotCameraNoMedia):
    """A pinned camera sent the other codec - abandon this attempt, retry now.

    The in-sync HLS TS is promised to Home Assistant on the strength of the
    H.264 pin, and only an H.264 session feeds it. The A001064 answers from
    its own template and sent H.265 in 15 of 107 pinned opens (2026-08-26);
    served as it came, that session left Home Assistant reading a stream
    nothing wrote. A camera that keeps its promise most of the time will on a
    retry. A no-media abandon for callers that pace retries, but the camera
    DID send media: the keepalive loop retries it without the no-media
    accounting that stops a battery camera's keepalive.
    """

    def __init__(self, observed_pt: int, pinned_pt: int) -> None:
        AidotError.__init__(
            self,
            "camera sent video payload type %d against an offer pinned to %d"
            % (observed_pt, pinned_pt),
        )
        self.waited_s = 0.0  # what a no-media abandon reports; media did come
        self.observed_pt = observed_pt
        self.pinned_pt = pinned_pt
