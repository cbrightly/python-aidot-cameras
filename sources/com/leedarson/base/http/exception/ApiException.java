package com.leedarson.base.http.exception;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class ApiException extends Exception {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int code;
    private String msg;

    public ApiException(Throwable throwable, int code2) {
        super(throwable);
        this.code = code2;
    }

    public ApiException(int code2, String msg2) {
        this.code = code2;
        this.msg = msg2;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public String getDetailInfo() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 166, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "code=" + this.code + "  msg=" + this.msg;
    }
}
