package com.amazonaws.kinesisvideo.stream.throttling;

import java.io.InputStream;

public class ThrottledInputStream extends InputStream {
    private static final int MS_IN_SEC = 1000;
    private final Throttler mThrottler;
    private final InputStream mUnthrottledInputStream;

    public ThrottledInputStream(InputStream unthrottledInputStream, Throttler throttler) {
        this.mUnthrottledInputStream = unthrottledInputStream;
        this.mThrottler = throttler;
    }

    public int read() {
        this.mThrottler.throttle();
        return this.mUnthrottledInputStream.read();
    }
}
