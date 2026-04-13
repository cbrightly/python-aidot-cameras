package com.amazonaws.kinesisvideo.stream.throttling;

import java.io.OutputStream;

public class ThrottledOutputStream extends OutputStream {
    private final Throttler mThrottler;
    private final OutputStream mUnthrottledOutputStream;

    public ThrottledOutputStream(OutputStream unthrottledOutputStream, Throttler throttler) {
        this.mUnthrottledOutputStream = unthrottledOutputStream;
        this.mThrottler = throttler;
    }

    public void write(int b) {
        this.mThrottler.throttle();
        this.mUnthrottledOutputStream.write(b);
    }
}
