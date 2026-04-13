package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import java.io.OutputStream;

/* compiled from: BufferedOutputStream */
public final class c extends OutputStream {
    @NonNull
    private final OutputStream c;
    private byte[] d;
    private b f;
    private int q;

    public c(@NonNull OutputStream out, @NonNull b arrayPool) {
        this(out, arrayPool, 65536);
    }

    @VisibleForTesting
    c(@NonNull OutputStream out, b arrayPool, int bufferSize) {
        this.c = out;
        this.f = arrayPool;
        this.d = (byte[]) arrayPool.e(bufferSize, byte[].class);
    }

    public void write(int b) {
        byte[] bArr = this.d;
        int i = this.q;
        this.q = i + 1;
        bArr[i] = (byte) b;
        c();
    }

    public void write(@NonNull byte[] b) {
        write(b, 0, b.length);
    }

    public void write(@NonNull byte[] b, int initialOffset, int length) {
        int writtenSoFar = 0;
        do {
            int remainingToWrite = length - writtenSoFar;
            int currentOffset = initialOffset + writtenSoFar;
            int i = this.q;
            if (i != 0 || remainingToWrite < this.d.length) {
                int totalBytesToWriteToBuffer = Math.min(remainingToWrite, this.d.length - i);
                System.arraycopy(b, currentOffset, this.d, this.q, totalBytesToWriteToBuffer);
                this.q += totalBytesToWriteToBuffer;
                writtenSoFar += totalBytesToWriteToBuffer;
                c();
            } else {
                this.c.write(b, currentOffset, remainingToWrite);
                return;
            }
        } while (writtenSoFar < length);
    }

    public void flush() {
        a();
        this.c.flush();
    }

    private void a() {
        int i = this.q;
        if (i > 0) {
            this.c.write(this.d, 0, i);
            this.q = 0;
        }
    }

    private void c() {
        if (this.q == this.d.length) {
            a();
        }
    }

    /* JADX INFO: finally extract failed */
    public void close() {
        try {
            flush();
            this.c.close();
            release();
        } catch (Throwable th) {
            this.c.close();
            throw th;
        }
    }

    private void release() {
        byte[] bArr = this.d;
        if (bArr != null) {
            this.f.put(bArr);
            this.d = null;
        }
    }
}
