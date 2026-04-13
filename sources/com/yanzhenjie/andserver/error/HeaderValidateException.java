package com.yanzhenjie.andserver.error;

public class HeaderValidateException extends HttpException {
    public HeaderValidateException(String message) {
        super(403, message);
    }

    public HeaderValidateException(String message, Throwable cause) {
        super(403, message, cause);
    }

    public HeaderValidateException(Throwable cause) {
        super(403, cause);
    }
}
