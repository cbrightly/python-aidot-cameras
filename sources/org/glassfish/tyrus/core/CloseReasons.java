package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;

public enum CloseReasons {
    NORMAL_CLOSURE(CloseReason.a.NORMAL_CLOSURE, "Normal closure."),
    GOING_AWAY(CloseReason.a.GOING_AWAY, "Going away."),
    PROTOCOL_ERROR(CloseReason.a.PROTOCOL_ERROR, "Protocol error."),
    CANNOT_ACCEPT(CloseReason.a.CANNOT_ACCEPT, "Cannot accept."),
    RESERVED(CloseReason.a.RESERVED, "Reserved."),
    NO_STATUS_CODE(CloseReason.a.NO_STATUS_CODE, "No status code."),
    CLOSED_ABNORMALLY(CloseReason.a.CLOSED_ABNORMALLY, "Closed abnormally."),
    NOT_CONSISTENT(CloseReason.a.NOT_CONSISTENT, "Not consistent."),
    VIOLATED_POLICY(CloseReason.a.VIOLATED_POLICY, "Violated policy."),
    TOO_BIG(CloseReason.a.TOO_BIG, "Too big."),
    NO_EXTENSION(CloseReason.a.NO_EXTENSION, "No extension."),
    UNEXPECTED_CONDITION(CloseReason.a.UNEXPECTED_CONDITION, "Unexpected condition."),
    SERVICE_RESTART(CloseReason.a.SERVICE_RESTART, "Service restart."),
    TRY_AGAIN_LATER(CloseReason.a.TRY_AGAIN_LATER, "Try again later."),
    TLS_HANDSHAKE_FAILURE(CloseReason.a.TLS_HANDSHAKE_FAILURE, "TLS handshake failure.");
    
    private final CloseReason closeReason;

    private CloseReasons(CloseReason.CloseCode closeCode, String reasonPhrase) {
        this.closeReason = new CloseReason(closeCode, reasonPhrase);
    }

    public CloseReason getCloseReason() {
        return this.closeReason;
    }
}
