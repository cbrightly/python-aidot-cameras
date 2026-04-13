package com.sensorsdata.analytics.android.sdk.exceptions;

public class InvalidDataException extends Exception {
    public InvalidDataException(String error) {
        super(error);
    }

    public InvalidDataException(Throwable throwable) {
        super(throwable);
    }
}
