package com.amazonaws.kinesisvideo.internal.producer;

public class KinesisVideoMetrics {
    private long contentStoreAllocatedSize = 0;
    private long contentStoreAvailableSize = 0;
    private long contentStoreSize = 0;
    private long totalContentViewSize = 0;
    private long totalFrameRate = 0;
    private long totalTransferRate = 0;

    public void setMetrics(long contentStoreSize2, long contentStoreAllocatedSize2, long contentStoreAvailableSize2, long totalContentViewSize2, long totalFrameRate2, long totalTransferRate2) {
        this.contentStoreSize = contentStoreSize2;
        this.contentStoreAllocatedSize = contentStoreAllocatedSize2;
        this.contentStoreAvailableSize = contentStoreAvailableSize2;
        this.totalContentViewSize = totalContentViewSize2;
        this.totalFrameRate = totalFrameRate2;
        this.totalTransferRate = totalTransferRate2;
    }

    public long getContentStoreSize() {
        return this.contentStoreSize;
    }

    public long getContentStoreAvailableSize() {
        return this.contentStoreAvailableSize;
    }

    public long getContentStoreAllocatedSize() {
        return this.contentStoreAllocatedSize;
    }

    public long getTotalContentViewSize() {
        return this.totalContentViewSize;
    }

    public long getTotalFrameRate() {
        return this.totalFrameRate;
    }

    public long getTotalTransferRate() {
        return this.totalTransferRate;
    }
}
