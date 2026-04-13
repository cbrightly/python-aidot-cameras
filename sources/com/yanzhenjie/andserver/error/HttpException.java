package com.yanzhenjie.andserver.error;

public class HttpException extends RuntimeException {
    private int mStatusCode;

    public HttpException(int statusCode, String message) {
        super(message);
        this.mStatusCode = statusCode;
    }

    public HttpException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.mStatusCode = statusCode;
    }

    public HttpException(int statusCode, Throwable cause) {
        super(cause);
        this.mStatusCode = statusCode;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }
}
