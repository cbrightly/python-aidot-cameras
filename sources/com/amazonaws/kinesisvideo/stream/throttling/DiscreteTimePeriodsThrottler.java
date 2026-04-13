package com.amazonaws.kinesisvideo.stream.throttling;

public class DiscreteTimePeriodsThrottler implements Throttler {
    private static final int MS_IN_SEC = 1000;
    private final int mDiscreteTimeSegmentMs;
    private long mLastSleepTime = 0;
    private final int mOpsPerDiscreteTimeSegment;
    private int mOpsSinceSleep = 0;

    public DiscreteTimePeriodsThrottler(int targetOpsPerSecond, int discretenessHz) {
        this.mDiscreteTimeSegmentMs = 1000 / discretenessHz;
        this.mOpsPerDiscreteTimeSegment = targetOpsPerSecond / discretenessHz;
    }

    public void throttle() {
        ensureInitialized();
        if (shouldSleep()) {
            sleep(calculateSleepTime());
            reset();
        }
        this.mOpsSinceSleep++;
    }

    private boolean shouldSleep() {
        return this.mOpsSinceSleep + 1 > this.mOpsPerDiscreteTimeSegment;
    }

    private long calculateSleepTime() {
        return (this.mLastSleepTime + ((long) this.mDiscreteTimeSegmentMs)) - getCurrentTimeMs();
    }

    private void sleep(long ms) {
        if (ms > 0) {
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ensureInitialized() {
        if (this.mLastSleepTime == 0) {
            reset();
        }
    }

    private long getCurrentTimeMs() {
        return System.currentTimeMillis();
    }

    private void reset() {
        this.mOpsSinceSleep = 0;
        this.mLastSleepTime = getCurrentTimeMs();
    }
}
