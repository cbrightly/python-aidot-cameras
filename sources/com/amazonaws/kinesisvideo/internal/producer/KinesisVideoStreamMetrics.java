package com.amazonaws.kinesisvideo.internal.producer;

public class KinesisVideoStreamMetrics {
    private static final long NANOS_IN_TIME_UNIT = 100;
    private double currentFrameRate = 0.0d;
    private long currentTransferRate = 0;
    private long currentViewDuration = 0;
    private long currentViewSize = 0;
    private long overallViewDuration = 0;
    private long overallViewSize = 0;

    public void setMetrics(long overallViewSize2, long currentViewSize2, long overallViewDuration2, long currentViewDuration2, double currentFrameRate2, long currentTransferRate2) {
        this.overallViewSize = overallViewSize2;
        this.currentViewSize = currentViewSize2;
        this.overallViewDuration = overallViewDuration2;
        this.currentViewDuration = currentViewDuration2;
        this.currentFrameRate = currentFrameRate2;
        this.currentTransferRate = currentTransferRate2;
    }

    public long getCurrentViewDurationInTimeUnits() {
        return this.currentViewDuration;
    }

    public long getCurrentViewDurationInMillis() {
        return this.currentViewDuration / 10000;
    }

    public long getOverallViewDurationInTimeUnits() {
        return this.overallViewDuration;
    }

    public long getOverallViewDurationInMillis() {
        return this.overallViewDuration / 10000;
    }

    public long getCurrentViewSize() {
        return this.currentViewSize;
    }

    public long getOverallViewSize() {
        return this.overallViewSize;
    }

    public double getCurrentFrameRate() {
        return this.currentFrameRate;
    }

    public long getCurrentTransferRate() {
        return this.currentTransferRate;
    }
}
