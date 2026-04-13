package com.amazonaws.kinesisvideo.internal.producer;

public class ReadResult {
    public static final long INVALID_UPLOAD_HANDLE_VALUE = -1;
    private boolean isEndOfStream = false;
    private int readBytes = 0;
    private long uploadHandle = -1;

    public void setReadResult(long uploadHandle2, int readBytes2, boolean isEndOfStream2) {
        this.uploadHandle = uploadHandle2;
        this.readBytes = readBytes2;
        this.isEndOfStream = isEndOfStream2;
    }

    public long getUploadHandle() {
        return this.uploadHandle;
    }

    public int getReadBytes() {
        return this.readBytes;
    }

    public boolean isEndOfStream() {
        return this.isEndOfStream;
    }
}
