package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import java.io.FilterInputStream;
import java.io.InputStream;

/* compiled from: ExifOrientationStream */
public final class g extends FilterInputStream {
    private static final byte[] c;
    private static final int d;
    private static final int f;
    private final byte q;
    private int x;

    static {
        byte[] bArr = {-1, -31, 0, 28, 69, 120, 105, 102, 0, 0, 77, 77, 0, 0, 0, 0, 0, 8, 0, 1, 1, 18, 0, 2, 0, 0, 0, 1, 0};
        c = bArr;
        int length = bArr.length;
        d = length;
        f = length + 2;
    }

    public g(InputStream in, int orientation) {
        super(in);
        if (orientation < -1 || orientation > 8) {
            throw new IllegalArgumentException("Cannot add invalid orientation: " + orientation);
        }
        this.q = (byte) orientation;
    }

    public boolean markSupported() {
        return false;
    }

    public void mark(int readLimit) {
        throw new UnsupportedOperationException();
    }

    public int read() {
        int result;
        int i;
        int i2 = this.x;
        if (i2 < 2 || i2 > (i = f)) {
            result = super.read();
        } else if (i2 == i) {
            result = this.q;
        } else {
            result = c[i2 - 2] & 255;
        }
        if (result != -1) {
            this.x++;
        }
        return result;
    }

    public int read(@NonNull byte[] buffer, int byteOffset, int byteCount) {
        int read;
        int read2 = this.x;
        int i = f;
        if (read2 > i) {
            read = super.read(buffer, byteOffset, byteCount);
        } else if (read2 == i) {
            buffer[byteOffset] = this.q;
            read = 1;
        } else if (read2 < 2) {
            read = super.read(buffer, byteOffset, 2 - read2);
        } else {
            read = Math.min(i - read2, byteCount);
            System.arraycopy(c, this.x - 2, buffer, byteOffset, read);
        }
        if (read > 0) {
            this.x += read;
        }
        return read;
    }

    public long skip(long byteCount) {
        long skipped = super.skip(byteCount);
        if (skipped > 0) {
            this.x = (int) (((long) this.x) + skipped);
        }
        return skipped;
    }

    public void reset() {
        throw new UnsupportedOperationException();
    }
}
