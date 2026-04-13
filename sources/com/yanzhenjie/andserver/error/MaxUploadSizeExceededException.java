package com.yanzhenjie.andserver.error;

import meshsdk.BaseResp;

public class MaxUploadSizeExceededException extends HttpException {
    private final long mMaxSize;

    public MaxUploadSizeExceededException(long maxUploadSize) {
        this(maxUploadSize, (Throwable) null);
    }

    public MaxUploadSizeExceededException(long maxSize, Throwable ex) {
        super(BaseResp.ERR_DOWNLOAD_IMPORT_FAIL, "Maximum upload size of " + maxSize + " bytes exceeded", ex);
        this.mMaxSize = maxSize;
    }

    public long getMaxSize() {
        return this.mMaxSize;
    }
}
