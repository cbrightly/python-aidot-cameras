package org.glassfish.tyrus.core;

public class HandshakeException extends Exception {
    private final int httpStatusCode;

    public HandshakeException(String message) {
        this(500, message);
    }

    public HandshakeException(int httpStatusCode2, String message) {
        super(message);
        this.httpStatusCode = httpStatusCode2;
    }

    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }
}
