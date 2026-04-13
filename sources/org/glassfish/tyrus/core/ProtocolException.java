package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;

public class ProtocolException extends WebSocketException {
    private final String reasonPhrase;

    public ProtocolException(String reasonPhrase2) {
        super(reasonPhrase2);
        this.reasonPhrase = reasonPhrase2;
    }

    public CloseReason getCloseReason() {
        return new CloseReason(CloseReason.a.PROTOCOL_ERROR, this.reasonPhrase);
    }
}
