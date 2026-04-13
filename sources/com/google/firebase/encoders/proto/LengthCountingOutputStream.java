package com.google.firebase.encoders.proto;

import androidx.annotation.NonNull;
import java.io.OutputStream;

public final class LengthCountingOutputStream extends OutputStream {
    private long length = 0;

    LengthCountingOutputStream() {
    }

    public void write(int b) {
        this.length++;
    }

    public void write(byte[] b) {
        this.length += (long) b.length;
    }

    public void write(@NonNull byte[] b, int off, int len) {
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.length += (long) len;
    }

    /* access modifiers changed from: package-private */
    public long getLength() {
        return this.length;
    }
}
