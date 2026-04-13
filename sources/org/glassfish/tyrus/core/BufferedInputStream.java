package org.glassfish.tyrus.core;

import java.io.InputStream;

public class BufferedInputStream extends InputStream {
    private final InputStreamBuffer buffer;

    public BufferedInputStream(InputStreamBuffer buffer2) {
        this.buffer = buffer2;
    }

    public int read() {
        return this.buffer.getNextByte();
    }

    public void close() {
        this.buffer.finishReading();
    }
}
