package org.glassfish.grizzly.utils;

public interface ExceptionHandler {

    public enum Severity {
        UNKNOWN,
        CONNECTION,
        TRANSPORT,
        FATAL
    }

    void notifyException(Severity severity, Throwable th);
}
