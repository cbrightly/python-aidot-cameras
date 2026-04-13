package org.apache.httpcore;

public class UnsupportedHttpVersionException extends ProtocolException {
    private static final long serialVersionUID = -1348448090193107031L;

    public UnsupportedHttpVersionException() {
    }

    public UnsupportedHttpVersionException(String message) {
        super(message);
    }
}
