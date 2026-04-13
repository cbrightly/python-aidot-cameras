package com.yanzhenjie.andserver.error;

import meshsdk.BaseResp;

public class ContentNotAcceptableException extends HttpException {
    public ContentNotAcceptableException() {
        super((int) BaseResp.ERR_WAIT_RESPONSE, "Could not find acceptable representation.");
    }

    public ContentNotAcceptableException(String message, Throwable cause) {
        super(BaseResp.ERR_WAIT_RESPONSE, message, cause);
    }
}
