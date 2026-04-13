package com.android.volley.toolbox;

import java.io.ByteArrayOutputStream;

/* compiled from: PoolingByteArrayOutputStream */
public class p extends ByteArrayOutputStream {
    private final d c;

    public p(d pool, int size) {
        this.c = pool;
        this.buf = pool.a(Math.max(size, 256));
    }

    public void close() {
        this.c.b(this.buf);
        this.buf = null;
        super.close();
    }

    public void finalize() {
        this.c.b(this.buf);
    }

    private void a(int i) {
        int i2 = this.count;
        if (i2 + i > this.buf.length) {
            byte[] newbuf = this.c.a((i2 + i) * 2);
            System.arraycopy(this.buf, 0, newbuf, 0, this.count);
            this.c.b(this.buf);
            this.buf = newbuf;
        }
    }

    public synchronized void write(byte[] buffer, int offset, int len) {
        a(len);
        super.write(buffer, offset, len);
    }

    public synchronized void write(int oneByte) {
        a(1);
        super.write(oneByte);
    }
}
