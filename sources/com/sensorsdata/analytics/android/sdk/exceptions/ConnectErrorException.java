package com.sensorsdata.analytics.android.sdk.exceptions;

public class ConnectErrorException extends Exception {
    private int mRetryAfter;

    public ConnectErrorException(String message) {
        super(message);
        this.mRetryAfter = 30000;
    }

    public ConnectErrorException(String message, String strRetryAfter) {
        super(message);
        try {
            this.mRetryAfter = Integer.parseInt(strRetryAfter);
        } catch (NumberFormatException e) {
            this.mRetryAfter = 0;
        }
    }

    public ConnectErrorException(Throwable throwable) {
        super(throwable);
    }

    public int getRetryAfter() {
        return this.mRetryAfter;
    }
}
