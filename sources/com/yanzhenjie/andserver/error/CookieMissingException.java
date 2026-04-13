package com.yanzhenjie.andserver.error;

import meshsdk.BaseResp;

public class CookieMissingException extends HttpException {
    public CookieMissingException(String name) {
        super((int) BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing cookie [%s] for method parameter.", new Object[]{name}));
    }

    public CookieMissingException(String name, Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing cookie [%s] for method parameter.", new Object[]{name}), cause);
    }

    public CookieMissingException(Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing cookie [%s] for method parameter.", new Object[]{""}), cause);
    }
}
