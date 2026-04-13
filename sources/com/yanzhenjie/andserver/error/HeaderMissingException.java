package com.yanzhenjie.andserver.error;

import meshsdk.BaseResp;

public class HeaderMissingException extends HttpException {
    public HeaderMissingException(String name) {
        super((int) BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing header [%s] for method parameter.", new Object[]{name}));
    }

    public HeaderMissingException(String name, Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing header [%s] for method parameter.", new Object[]{name}), cause);
    }

    public HeaderMissingException(Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing header [%s] for method parameter.", new Object[]{""}), cause);
    }
}
