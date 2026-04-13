package com.leedarson.base.http.exception;

import com.meituan.robust.ChangeQuickRedirect;

public class ServerException extends RuntimeException {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int code;
    private String msg;

    public ServerException(int code2, String msg2) {
        this.code = code2;
        this.msg = msg2;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
