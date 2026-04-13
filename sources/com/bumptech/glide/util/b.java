package com.bumptech.glide.util;

import androidx.annotation.NonNull;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: ContentLengthInputStream */
public final class b extends FilterInputStream {
    private final long c;
    private int d;

    @NonNull
    public static InputStream c(@NonNull InputStream other, long contentLength) {
        return new b(other, contentLength);
    }

    private b(@NonNull InputStream in, long contentLength) {
        super(in);
        this.c = contentLength;
    }

    public synchronized int available() {
        return (int) Math.max(this.c - ((long) this.d), (long) this.in.available());
    }

    public synchronized int read() {
        int value;
        value = super.read();
        a(value >= 0 ? 1 : -1);
        return value;
    }

    public int read(byte[] buffer) {
        return read(buffer, 0, buffer.length);
    }

    public synchronized int read(byte[] buffer, int byteOffset, int byteCount) {
        return a(super.read(buffer, byteOffset, byteCount));
    }

    private int a(int read) {
        if (read >= 0) {
            this.d += read;
        } else if (this.c - ((long) this.d) > 0) {
            throw new IOException("Failed to read all expected data, expected: " + this.c + ", but read: " + this.d);
        }
        return read;
    }
}
