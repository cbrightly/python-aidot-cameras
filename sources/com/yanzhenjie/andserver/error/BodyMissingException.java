package com.yanzhenjie.andserver.error;

import meshsdk.BaseResp;

public class BodyMissingException extends HttpException {
    public BodyMissingException() {
        super((int) BaseResp.ERR_MSG_SEND_FAIL, "RequestBody is missing.");
    }

    public BodyMissingException(Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, "RequestBody is missing.", cause);
    }
}
