package com.amazonaws.internal.keyvaluestore;

public class KeyNotFoundException extends Exception {
    private static final long serialVersionUID = 1;

    public KeyNotFoundException(String message, Throwable t) {
        super(message, t);
    }

    public KeyNotFoundException(String message) {
        super(message);
    }

    public KeyNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
