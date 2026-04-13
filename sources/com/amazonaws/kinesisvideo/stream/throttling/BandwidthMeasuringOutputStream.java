package com.amazonaws.kinesisvideo.stream.throttling;

import java.io.OutputStream;

public class BandwidthMeasuringOutputStream extends OutputStream {
    private final OpsPerSecondMeasurer mOpsPerSecondMeasurer;
    private final OutputStream mOutputStream;

    public BandwidthMeasuringOutputStream(OutputStream outputStream, OpsPerSecondMeasurer opsPerSecondMeasurer) {
        this.mOutputStream = outputStream;
        this.mOpsPerSecondMeasurer = opsPerSecondMeasurer;
    }

    public void write(int b) {
        this.mOpsPerSecondMeasurer.recordOperation();
        this.mOutputStream.write(b);
    }
}
