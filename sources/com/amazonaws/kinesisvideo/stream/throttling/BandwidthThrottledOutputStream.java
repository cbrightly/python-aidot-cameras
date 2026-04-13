package com.amazonaws.kinesisvideo.stream.throttling;

import java.io.OutputStream;

public class BandwidthThrottledOutputStream extends OutputStream {
    private final byte[] oneByteBuffer = new byte[1];
    private final OutputStream outputStream;
    private final BandwidthThrottler throttler;

    public BandwidthThrottledOutputStream(OutputStream outputStream2, BandwidthThrottler throttler2) {
        this.outputStream = outputStream2;
        this.throttler = throttler2;
    }

    public void write(int b) {
        byte[] bArr = this.oneByteBuffer;
        bArr[0] = (byte) b;
        write(bArr, 0, 1);
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        int bytesWritten = 0;
        while (bytesWritten < len) {
            int allowedBytesToWrite = this.throttler.getAllowedBytes(len - bytesWritten);
            this.outputStream.write(b, off + bytesWritten, allowedBytesToWrite);
            bytesWritten += allowedBytesToWrite;
        }
    }

    public void flush() {
        this.outputStream.flush();
    }

    public void close() {
        this.outputStream.close();
    }
}
