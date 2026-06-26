"""AiDot exception hierarchy."""


class AidotError(Exception):
    """Base exception for all AiDot errors."""


class InvalidURL(AidotError):
    """Invalid URL."""


class HTTPError(AidotError):
    """HTTP request failed."""


class InvalidHost(AidotError):
    """Invalid host."""


class AidotAuthTokenExpired(AidotError):
    """Auth token is invalid or expired."""


class AidotAuthFailed(AidotError):
    """Authentication failed."""


class AidotNotLogin(AidotError):
    """Client is not logged in."""


class AidotUserOrPassIncorrect(AidotError):
    """Username or password is incorrect."""


class AidotOSError(AidotError):
    """OS-level error from the AiDot library."""


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
