package com.sensorsdata.analytics.android.sdk.exceptions;

public class ResponseErrorException extends Exception {
    private int httpCode;

    public ResponseErrorException(String error, int httpCode2) {
        super(error);
        this.httpCode = httpCode2;
    }

    public ResponseErrorException(Throwable throwable, int httpCode2) {
        super(throwable);
        this.httpCode = httpCode2;
    }

    public int getHttpCode() {
        return this.httpCode;
    }
}
