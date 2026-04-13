package com.amazonaws.kinesisvideo.util;

public final class StreamInfoConstants {
    public static final boolean ABSOLUTE_TIMECODES = true;
    public static final int DEFAULT_BITRATE = 2000000;
    public static final long DEFAULT_BUFFER_DURATION = 400000000;
    public static final long DEFAULT_GOP_DURATION = 20000000;
    public static final long DEFAULT_REPLAY_DURATION = 200000000;
    public static final long DEFAULT_STALENESS_DURATION = 200000000;
    public static final int DEFAULT_TIMESCALE = 10000;
    public static final int FRAMERATE_30 = 30;
    public static final int FRAME_RATE_25 = 25;
    public static final boolean KEYFRAME_FRAGMENTATION = true;
    public static final long MAX_LATENCY = 1200000000;
    public static final long MAX_LATENCY_ZERO = 0;
    public static final boolean NOT_ADAPTIVE = false;
    public static final String NO_KMS_KEY_ID = null;
    public static final long NO_RETENTION = 0;
    public static final boolean RECALCULATE_METRICS = true;
    public static final boolean RECOVER_ON_FAILURE = true;
    public static final boolean RELATIVE_FRAGMENT_TIMECODES = false;
    public static final boolean RELATIVE_TIMECODES = false;
    public static final boolean REQUEST_FRAGMENT_ACKS = true;
    public static final long RETENTION_ONE_HOUR = 36000000000L;
    public static final boolean SDK_GENERATES_TIMECODES = false;
    public static final boolean USE_FRAME_TIMECODES = true;
    public static final int VERSION_ZERO = 0;

    private StreamInfoConstants() {
        throw new UnsupportedOperationException();
    }
}
