package com.yanzhenjie.andserver.error;

import com.yanzhenjie.andserver.util.h;
import meshsdk.BaseResp;

public class ContentNotSupportedException extends HttpException {
    public ContentNotSupportedException(h mediaType) {
        super((int) BaseResp.ERR_PARAM_ERROR, String.format("The content type [%s] is not supported.", new Object[]{mediaType}));
    }

    public ContentNotSupportedException(h mediaType, Throwable cause) {
        super(BaseResp.ERR_PARAM_ERROR, String.format("The content type [%s] is not supported.", new Object[]{mediaType}), cause);
    }
}
