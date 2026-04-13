package com.yanzhenjie.andserver.error;

@Deprecated
public class BasicException extends HttpException {
    public BasicException(int statusCode, String message) {
        super(statusCode, message);
    }

    public BasicException(int statusCode, String message, Throwable cause) {
        super(statusCode, message, cause);
    }

    public BasicException(int statusCode, Throwable cause) {
        super(statusCode, cause);
    }
}
