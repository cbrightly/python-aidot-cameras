package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;

public class MessageTooBigException extends WebSocketException {
    private static final CloseReason CLOSE_REASON = CloseReasons.TOO_BIG.getCloseReason();
    private static final long serialVersionUID = -1636733948291376261L;

    MessageTooBigException(String message) {
        super(message);
    }

    public CloseReason getCloseReason() {
        return CLOSE_REASON;
    }
}
