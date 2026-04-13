package com.amazonaws.kinesisvideo.stream.throttling;

import com.amazonaws.kinesisvideo.common.function.Consumer;

public class OpsPerSecondMeasurer {
    private static final int MS_IN_SEC = 1000;
    private final Consumer<Long> mCallback;
    private long mLastMeasurementTimeMs = 0;
    private int mOpsSinceLastMeasurement = 0;

    public OpsPerSecondMeasurer(Consumer<Long> callback) {
        this.mCallback = callback;
    }

    public void recordOperation() {
        ensureInitialized();
        this.mOpsSinceLastMeasurement++;
        tryRecordMeasurementAndReset();
    }

    private void tryRecordMeasurementAndReset() {
        long elapsedTimeMs = System.currentTimeMillis() - this.mLastMeasurementTimeMs;
        if (elapsedTimeMs > 1000) {
            this.mCallback.accept(Long.valueOf(getOpsPerSecond(elapsedTimeMs)));
            reset();
        }
    }

    private long getOpsPerSecond(long elapsedTimeMs) {
        return ((long) (this.mOpsSinceLastMeasurement * 1000)) / elapsedTimeMs;
    }

    private void reset() {
        this.mOpsSinceLastMeasurement = 0;
        this.mLastMeasurementTimeMs = System.currentTimeMillis();
    }

    private void ensureInitialized() {
        if (this.mLastMeasurementTimeMs == 0) {
            this.mLastMeasurementTimeMs = System.currentTimeMillis();
        }
    }
}
