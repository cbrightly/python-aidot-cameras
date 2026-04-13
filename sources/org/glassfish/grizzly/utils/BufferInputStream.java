package org.glassfish.grizzly.utils;

import java.io.InputStream;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;

public class BufferInputStream extends InputStream {
    private final Buffer buffer;
    private final boolean isMovingPosition = true;
    private final int limit;
    private int position;

    public BufferInputStream(Buffer buffer2) {
        this.buffer = buffer2;
        this.position = buffer2.position();
        this.limit = buffer2.limit();
    }

    public BufferInputStream(Buffer buffer2, int position2, int limit2) {
        this.buffer = buffer2;
        this.position = position2;
        this.limit = limit2;
    }

    public int read() {
        int i = this.position;
        if (i >= this.limit) {
            return -1;
        }
        Buffer buffer2 = this.buffer;
        this.position = i + 1;
        int result = buffer2.get(i) & 255;
        if (this.isMovingPosition) {
            this.buffer.position(this.position);
        }
        return result;
    }

    public int read(byte[] b, int off, int len) {
        if (this.position >= this.limit) {
            return -1;
        }
        int length = Math.min(len, available());
        int oldPos = this.buffer.position();
        int oldLim = this.buffer.limit();
        if (!this.isMovingPosition) {
            Buffers.setPositionLimit(this.buffer, this.position, this.limit);
        }
        try {
            this.buffer.get(b, off, length);
            this.position += length;
            return length;
        } finally {
            if (!this.isMovingPosition) {
                Buffers.setPositionLimit(this.buffer, oldPos, oldLim);
            }
        }
    }

    public int available() {
        return this.limit - this.position;
    }

    public long skip(long n) {
        int skipped = (int) Math.min(n, (long) available());
        int i = this.position + skipped;
        this.position = i;
        if (this.isMovingPosition) {
            this.buffer.position(i);
        }
        return (long) skipped;
    }
}
