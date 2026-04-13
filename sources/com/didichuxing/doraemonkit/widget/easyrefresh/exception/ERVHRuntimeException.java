package com.didichuxing.doraemonkit.widget.easyrefresh.exception;

public class ERVHRuntimeException extends RuntimeException {
    public ERVHRuntimeException() {
    }

    public ERVHRuntimeException(Throwable throwable) {
        super(throwable);
    }

    public ERVHRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ERVHRuntimeException(String detailMessage) {
        super(detailMessage);
    }
}
