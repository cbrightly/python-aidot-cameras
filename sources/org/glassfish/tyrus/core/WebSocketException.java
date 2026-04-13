package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;

public abstract class WebSocketException extends RuntimeException {
    public abstract CloseReason getCloseReason();

    public WebSocketException(String message) {
        super(message);
    }
}
