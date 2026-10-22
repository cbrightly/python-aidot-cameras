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


# --------------------------------------------------------------------------- #
# Live-stream open failures.
#
# Each of these replaced a bare ``RuntimeError`` on the open path, so each also
# subclasses ``RuntimeError``: ``except RuntimeError`` written against an
# earlier release keeps catching exactly what it caught before.


class AidotStreamOpenError(AidotError, RuntimeError):
    """A live-stream open failed. The base of the open-path errors below.

    Catch this to handle any of them; catch ``AidotError`` to handle every
    error the library raises.
    """


class AidotMqttUnavailable(AidotStreamOpenError):
    """No usable MQTT broker for the signalling: no broker URL could be
    obtained, or the connection to the broker failed."""


class AidotPlaintextMqttRefused(AidotMqttUnavailable):
    """The cloud named a broker URL without TLS (``ws://``, ``tcp://``,
    ``mqtt://``, ``http://``) and the library refused to send the account's
    MQTT password over it.

    ``AIDOT_ALLOW_PLAINTEXT_MQTT=1`` opts in to the plaintext connection.
    ``scheme`` is the refused URL scheme; the URL itself is not kept.
    """

    def __init__(self, scheme: str) -> None:
        self.scheme = scheme
        super().__init__(
            f"refusing a non-TLS MQTT broker URL ({scheme}://) from the cloud: "
            "it would carry the account's MQTT password in plaintext. Set "
            "AIDOT_ALLOW_PLAINTEXT_MQTT=1 to allow it."
        )


class AidotLivePlayRefused(AidotStreamOpenError):
    """The camera answered ``livePlay=0``: an unambiguous refusal of the
    live-play request."""


class AidotNoWebrtcAnswer(AidotStreamOpenError):
    """The camera sent no ``webrtcResp`` within the open timeout."""


class AidotNegotiationFailed(AidotStreamOpenError):
    """The WebRTC negotiation failed: the camera's SDP could not be applied,
    or ICE did not connect within the timeout."""


class AidotFfmpegMissing(AidotStreamOpenError):
    """ffmpeg is needed for this stream and is not installed."""


# --------------------------------------------------------------------------- #
# Account and endpoint errors.


class AidotUnknownRegion(AidotError, ValueError):
    """The account's region is not one the library knows, or is not shaped
    like a hostname label.

    The region is formatted into the cloud hostnames, so an unknown value is
    refused rather than sent. ``AIDOT_EXTRA_REGIONS`` (comma-separated)
    accepts a region the cloud has added since this release. ``region`` is the
    refused value.
    """

    def __init__(self, region: object) -> None:
        self.region = region
        super().__init__(
            f"unknown AiDot region {region!r}; if the AiDot cloud really uses "
            "it, add it to AIDOT_EXTRA_REGIONS"
        )


# --------------------------------------------------------------------------- #
# Errors defined here and imported back by the modules that raise them, so
# they are AidotError subclasses and reachable from the package root while
# keeping their original import paths and bases.


class CameraLanError(AidotError):
    """A local control operation failed (connect, login, or protocol error).

    Also importable from ``aidot_cameras.camera.lan_control``.
    """


class CameraLanLoginRejected(CameraLanError):
    """The device advertised local control and then refused our login.

    Separate from its parent because the two mean opposite things to a caller.
    A device that does not offer local control is ordinary and should be quiet;
    a device that offers it, accepts the connection, decrypts our request with
    its own key, and STILL answers with a non-200 ack is a defect - either in
    what we send or in the device - and the caller should be able to say so out
    loud rather than logging it at the same level as "not supported here".

    ``ack`` is the device's own code, because the refusals differ by family and
    the number is the only handle on which one happened: A000088 cameras answer
    4352, A001497 lights and A001535 plugs 4354, A001493 lights 400.

    Also importable from ``aidot_cameras.camera.lan_control``.
    """

    def __init__(self, message: str, ack: object = None) -> None:
        super().__init__(message)
        self.ack = ack


class RtspPublishError(AidotError, RuntimeError):
    """The RTSP server refused or dropped the publish.

    Also importable from ``aidot_cameras.camera.rtsp_publish``.
    """
