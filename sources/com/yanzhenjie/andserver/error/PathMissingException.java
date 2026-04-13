package com.yanzhenjie.andserver.error;

import meshsdk.BaseResp;

public class PathMissingException extends HttpException {
    public PathMissingException(String name) {
        super((int) BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing param [%s] for path parameter.", new Object[]{name}));
    }

    public PathMissingException(String name, Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing param [%s] for path parameter.", new Object[]{name}), cause);
    }

    public PathMissingException(Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing param [%s] for path parameter.", new Object[]{""}), cause);
    }
}
