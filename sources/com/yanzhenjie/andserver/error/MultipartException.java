package com.yanzhenjie.andserver.error;

import meshsdk.BaseResp;

public class MultipartException extends HttpException {
    public MultipartException(String msg) {
        super((int) BaseResp.ERR_MSG_SEND_FAIL, msg);
    }

    public MultipartException(String msg, Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, msg, cause);
    }
}
