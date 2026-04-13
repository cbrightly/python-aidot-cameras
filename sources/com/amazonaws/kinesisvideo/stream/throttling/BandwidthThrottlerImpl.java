package com.amazonaws.kinesisvideo.stream.throttling;

import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import java.util.Random;

public class BandwidthThrottlerImpl implements BandwidthThrottler {
    private static final double ACTUAL_PAYLOAD_PERCENTAGE = 1.0d;
    private static final long BITS_IN_A_BYTE = 8;
    private static final long BITS_IN_A_KILOBIT = 1024;
    private static final int DEFAULT_RESET_SUBINTERVAL = 20;
    private static final int MAX_RANDOM_VALUE = 1;
    private static final long ONE_SECOND_IN_MILLIS = 1000;
    private long absoluteMaxBps;
    private final Random randomGenerator = new Random();
    private final ThrottlingParams upstream = new ThrottlingParams();

    public BandwidthThrottlerImpl(long maxBps) {
        setAbsoluteMaxBps(maxBps);
        setUpstreamKbps(maxBps / 1024);
    }

    private static void sleep(long sleepDuration) {
        try {
            Thread.sleep(sleepDuration);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    public void setAbsoluteMaxBps(long maxBps) {
        this.absoluteMaxBps = maxBps;
        ThrottlingParams throttlingParams = this.upstream;
        setMaxBps(throttlingParams, throttlingParams.maxBps);
    }

    public void setUpstreamKbps(long kbps) {
        Preconditions.checkArgument(kbps > 0, String.format("Given bandwidth value %d is not positive.", new Object[]{Long.valueOf(kbps)}));
        setMaxBps(this.upstream, (1024 * kbps) / 8);
    }

    public int getAllowedBytes(int len) {
        while (true) {
            int allowedBytesWrite = getAllowedBytesWrite(len);
            int allowed = allowedBytesWrite;
            if (allowedBytesWrite > 0) {
                return allowed;
            }
            long sleepDuration = timeToNextReset(this.upstream);
            if (sleepDuration > 0) {
                sleep(sleepDuration + ((long) this.randomGenerator.nextInt(1)));
            }
        }
    }

    private void setMaxBps(ThrottlingParams params, long maxBps) {
        long unused = params.maxBps = Math.min(this.absoluteMaxBps, maxBps);
        long unused2 = params.adjustedMaxBps = (long) (((double) params.maxBps) * 1.0d);
        long unused3 = params.nextResetSubIntervals = 20;
        params.reset();
    }

    private long timeToNextReset(ThrottlingParams params) {
        return params.timeToNextReset();
    }

    private int getAllowedBytesWrite(int len) {
        return getAllowedBytesInternal(this.upstream, len);
    }

    private int getAllowedBytesInternal(ThrottlingParams params, int len) {
        int allowed;
        resetCounterIfNecessary(params);
        if (((long) len) > params.remainingBps) {
            allowed = (int) params.remainingBps;
        } else {
            allowed = len;
        }
        long unused = params.remainingBps = params.remainingBps - ((long) allowed);
        return allowed;
    }

    private void resetCounterIfNecessary(ThrottlingParams params) {
        if (params.timeToNextReset() < 0) {
            params.reset();
        }
    }

    public static class ThrottlingParams {
        /* access modifiers changed from: private */
        public long adjustedMaxBps;
        /* access modifiers changed from: private */
        public long maxBps;
        /* access modifiers changed from: private */
        public long nextResetSubIntervals;
        private long nextResetTimestamp;
        /* access modifiers changed from: private */
        public long remainingBps;

        private ThrottlingParams() {
        }

        /* access modifiers changed from: private */
        public long timeToNextReset() {
            return this.nextResetTimestamp - System.currentTimeMillis();
        }

        /* access modifiers changed from: private */
        public void reset() {
            this.remainingBps = this.adjustedMaxBps / this.nextResetSubIntervals;
            this.nextResetTimestamp = System.currentTimeMillis() + (1000 / this.nextResetSubIntervals);
        }
    }
}
