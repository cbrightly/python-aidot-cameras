package com.yanzhenjie.andserver.error;

public class ParamValidateException extends HttpException {
    public ParamValidateException(String message) {
        super(403, message);
    }

    public ParamValidateException(String message, Throwable cause) {
        super(403, message, cause);
    }

    public ParamValidateException(Throwable cause) {
        super(403, cause);
    }
}
