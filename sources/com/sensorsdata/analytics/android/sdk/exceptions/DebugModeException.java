package com.sensorsdata.analytics.android.sdk.exceptions;

public class DebugModeException extends RuntimeException {
    public DebugModeException(String error) {
        super(error);
    }

    public DebugModeException(Throwable throwable) {
        super(throwable);
    }
}
