package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import java.io.FilterInputStream;
import java.io.InputStream;

/* compiled from: MarkEnforcingInputStream */
public class g extends FilterInputStream {
    private int c = Integer.MIN_VALUE;

    public g(@NonNull InputStream in) {
        super(in);
    }

    public synchronized void mark(int readLimit) {
        super.mark(readLimit);
        this.c = readLimit;
    }

    public int read() {
        if (a(1) == -1) {
            return -1;
        }
        int result = super.read();
        c(1);
        return result;
    }

    public int read(@NonNull byte[] buffer, int byteOffset, int byteCount) {
        int toRead = (int) a((long) byteCount);
        if (toRead == -1) {
            return -1;
        }
        int read = super.read(buffer, byteOffset, toRead);
        c((long) read);
        return read;
    }

    public synchronized void reset() {
        super.reset();
        this.c = Integer.MIN_VALUE;
    }

    public long skip(long byteCount) {
        long toSkip = a(byteCount);
        if (toSkip == -1) {
            return 0;
        }
        long read = super.skip(toSkip);
        c(read);
        return read;
    }

    public int available() {
        int i = this.c;
        if (i == Integer.MIN_VALUE) {
            return super.available();
        }
        return Math.min(i, super.available());
    }

    private long a(long targetByteCount) {
        int i = this.c;
        if (i == 0) {
            return -1;
        }
        if (i == Integer.MIN_VALUE || targetByteCount <= ((long) i)) {
            return targetByteCount;
        }
        return (long) i;
    }

    private void c(long bytesRead) {
        int i = this.c;
        if (i != Integer.MIN_VALUE && bytesRead != -1) {
            this.c = (int) (((long) i) - bytesRead);
        }
    }
}
