package com.didichuxing.doraemonkit.kit.network.stream;

import androidx.annotation.NonNull;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.OutputStream;

public abstract class OutputStreamProxy extends FilterOutputStream {
    protected final ByteArrayOutputStream mOutputStream = new ByteArrayOutputStream();

    /* access modifiers changed from: protected */
    public abstract void onStreamComplete();

    public OutputStreamProxy(@NonNull OutputStream out) {
        super(out);
    }

    public void flush() {
        super.flush();
        onStreamComplete();
    }

    public void write(@NonNull byte[] b, int off, int len) {
        super.write(b, off, len);
        this.mOutputStream.write(b, off, len);
    }

    public void close() {
        super.close();
    }
}
