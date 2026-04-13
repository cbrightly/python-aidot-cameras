package com.alibaba.android.arouter.exception;

public class NoRouteFoundException extends RuntimeException {
    public NoRouteFoundException(String detailMessage) {
        super(detailMessage);
    }
}
