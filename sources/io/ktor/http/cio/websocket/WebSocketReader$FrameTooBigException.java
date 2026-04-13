package io.ktor.http.cio.websocket;

import kotlinx.coroutines.g0;
import org.jetbrains.annotations.NotNull;

/* compiled from: WebSocketReader.kt */
public final class WebSocketReader$FrameTooBigException extends Exception implements g0<WebSocketReader$FrameTooBigException> {
    private final long frameSize;

    public WebSocketReader$FrameTooBigException(long frameSize2) {
        this.frameSize = frameSize2;
    }

    public final long getFrameSize() {
        return this.frameSize;
    }

    @NotNull
    public String getMessage() {
        return "Frame is too big: " + this.frameSize;
    }

    @NotNull
    public WebSocketReader$FrameTooBigException createCopy() {
        WebSocketReader$FrameTooBigException it = new WebSocketReader$FrameTooBigException(this.frameSize);
        it.initCause(this);
        return it;
    }
}
