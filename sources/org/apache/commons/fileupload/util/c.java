package org.apache.commons.fileupload.util;

import java.io.FilterInputStream;
import java.io.InputStream;

/* compiled from: LimitedInputStream */
public abstract class c extends FilterInputStream implements a {
    private final long c;
    private long d;
    private boolean f;

    /* access modifiers changed from: protected */
    public abstract void c(long j, long j2);

    public c(InputStream inputStream, long pSizeMax) {
        super(inputStream);
        this.c = pSizeMax;
    }

    private void a() {
        long j = this.d;
        long j2 = this.c;
        if (j > j2) {
            c(j2, j);
        }
    }

    public int read() {
        int res = super.read();
        if (res != -1) {
            this.d++;
            a();
        }
        return res;
    }

    public int read(byte[] b, int off, int len) {
        int res = super.read(b, off, len);
        if (res > 0) {
            this.d += (long) res;
            a();
        }
        return res;
    }

    public boolean isClosed() {
        return this.f;
    }

    public void close() {
        this.f = true;
        super.close();
    }
}
