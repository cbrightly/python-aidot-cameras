package com.google.firebase.messaging;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public final class ByteStreams {
    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_ARRAY_LEN = 2147483639;
    private static final int TO_BYTE_ARRAY_DEQUE_SIZE = 20;

    static byte[] createBuffer() {
        return new byte[8192];
    }

    private static int saturatedCast(long value) {
        if (value > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        if (value < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int) value;
    }

    private ByteStreams() {
    }

    private static byte[] toByteArrayInternal(InputStream in, Queue<byte[]> bufs, int totalLen) {
        int bufSize = Math.min(8192, Math.max(128, Integer.highestOneBit(totalLen) * 2));
        while (totalLen < MAX_ARRAY_LEN) {
            byte[] buf = new byte[Math.min(bufSize, MAX_ARRAY_LEN - totalLen)];
            bufs.add(buf);
            int off = 0;
            while (off < buf.length) {
                int r = in.read(buf, off, buf.length - off);
                if (r == -1) {
                    return combineBuffers(bufs, totalLen);
                }
                off += r;
                totalLen += r;
            }
            bufSize = saturatedCast(((long) bufSize) * ((long) (bufSize < 4096 ? 4 : 2)));
        }
        if (in.read() == -1) {
            return combineBuffers(bufs, MAX_ARRAY_LEN);
        }
        throw new OutOfMemoryError("input is too large to fit in a byte array");
    }

    private static byte[] combineBuffers(Queue<byte[]> bufs, int totalLen) {
        if (bufs.isEmpty()) {
            return new byte[0];
        }
        byte[] result = bufs.remove();
        if (result.length == totalLen) {
            return result;
        }
        int remaining = totalLen - result.length;
        byte[] result2 = Arrays.copyOf(result, totalLen);
        while (remaining > 0) {
            byte[] buf = bufs.remove();
            int bytesToCopy = Math.min(remaining, buf.length);
            System.arraycopy(buf, 0, result2, totalLen - remaining, bytesToCopy);
            remaining -= bytesToCopy;
        }
        return result2;
    }

    public static byte[] toByteArray(InputStream in) {
        return toByteArrayInternal(in, new ArrayDeque(20), 0);
    }

    public static InputStream limit(InputStream in, long limit) {
        return new LimitedInputStream(in, limit);
    }

    public static final class LimitedInputStream extends FilterInputStream {
        private long left;
        private long mark = -1;

        LimitedInputStream(InputStream in, long limit) {
            super(in);
            this.left = limit;
        }

        public int available() {
            return (int) Math.min((long) this.in.available(), this.left);
        }

        public synchronized void mark(int readLimit) {
            this.in.mark(readLimit);
            this.mark = this.left;
        }

        public int read() {
            if (this.left == 0) {
                return -1;
            }
            int result = this.in.read();
            if (result != -1) {
                this.left--;
            }
            return result;
        }

        public int read(byte[] b, int off, int len) {
            long j = this.left;
            if (j == 0) {
                return -1;
            }
            int result = this.in.read(b, off, (int) Math.min((long) len, j));
            if (result != -1) {
                this.left -= (long) result;
            }
            return result;
        }

        public synchronized void reset() {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            } else if (this.mark != -1) {
                this.in.reset();
                this.left = this.mark;
            } else {
                throw new IOException("Mark not set");
            }
        }

        public long skip(long n) {
            long skipped = this.in.skip(Math.min(n, this.left));
            this.left -= skipped;
            return skipped;
        }
    }
}
