package com.amazonaws.mobileconnectors.kinesisvideo.camera;

public class EncodingCancellationToken {
    private volatile boolean mIsEncodingCancelled = false;

    public boolean isEncodingCancelled() {
        return this.mIsEncodingCancelled;
    }

    public void cancelEncoding() {
        this.mIsEncodingCancelled = true;
    }
}
