package com.yanzhenjie.andserver.error;

import meshsdk.BaseResp;

public class ParamMissingException extends HttpException {
    public ParamMissingException(String name) {
        super((int) BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing param [%s] for method parameter.", new Object[]{name}));
    }

    public ParamMissingException(String name, Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing param [%s] for method parameter.", new Object[]{name}), cause);
    }

    public ParamMissingException(Throwable cause) {
        super(BaseResp.ERR_MSG_SEND_FAIL, String.format("Missing param [%s] for method parameter.", new Object[]{""}), cause);
    }
}
