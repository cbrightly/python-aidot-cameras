package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

/* compiled from: ThresholdingOutputStream */
public abstract class d extends OutputStream {
    private final int c;
    private long d;
    private boolean f;

    /* access modifiers changed from: protected */
    public abstract OutputStream c();

    /* access modifiers changed from: protected */
    public abstract void i();

    public d(int threshold) {
        this.c = threshold;
    }

    public void write(int b) {
        a(1);
        c().write(b);
        this.d++;
    }

    public void write(byte[] b) {
        a(b.length);
        c().write(b);
        this.d += (long) b.length;
    }

    public void write(byte[] b, int off, int len) {
        a(len);
        c().write(b, off, len);
        this.d += (long) len;
    }

    public void flush() {
        c().flush();
    }

    public void close() {
        try {
            flush();
        } catch (IOException e) {
        }
        c().close();
    }

    public boolean g() {
        return this.d > ((long) this.c);
    }

    /* access modifiers changed from: protected */
    public void a(int count) {
        if (!this.f && this.d + ((long) count) > ((long) this.c)) {
            this.f = true;
            i();
        }
    }
}
