package com.bumptech.glide.util;

import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;

/* compiled from: ExceptionPassthroughInputStream */
public final class c extends InputStream {
    @GuardedBy("POOL")
    private static final Queue<c> c = j.e(0);
    private InputStream d;
    private IOException f;

    @NonNull
    public static c c(@NonNull InputStream toWrap) {
        c result;
        Queue<c> queue = c;
        synchronized (queue) {
            result = queue.poll();
        }
        if (result == null) {
            result = new c();
        }
        result.g(toWrap);
        return result;
    }

    c() {
    }

    /* access modifiers changed from: package-private */
    public void g(@NonNull InputStream toWrap) {
        this.d = toWrap;
    }

    public int available() {
        return this.d.available();
    }

    public void close() {
        this.d.close();
    }

    public void mark(int readLimit) {
        this.d.mark(readLimit);
    }

    public boolean markSupported() {
        return this.d.markSupported();
    }

    public int read() {
        try {
            return this.d.read();
        } catch (IOException e) {
            this.f = e;
            throw e;
        }
    }

    public int read(byte[] buffer) {
        try {
            return this.d.read(buffer);
        } catch (IOException e) {
            this.f = e;
            throw e;
        }
    }

    public int read(byte[] buffer, int byteOffset, int byteCount) {
        try {
            return this.d.read(buffer, byteOffset, byteCount);
        } catch (IOException e) {
            this.f = e;
            throw e;
        }
    }

    public synchronized void reset() {
        this.d.reset();
    }

    public long skip(long byteCount) {
        try {
            return this.d.skip(byteCount);
        } catch (IOException e) {
            this.f = e;
            throw e;
        }
    }

    @Nullable
    public IOException a() {
        return this.f;
    }

    public void release() {
        this.f = null;
        this.d = null;
        Queue<c> queue = c;
        synchronized (queue) {
            queue.offer(this);
        }
    }
}
