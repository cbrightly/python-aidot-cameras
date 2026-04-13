package org.spongycastle.crypto.tls;

import java.io.InputStream;

public class ByteQueueInputStream extends InputStream {
    private ByteQueue c = new ByteQueue();

    public int read() {
        if (this.c.b() == 0) {
            return -1;
        }
        return this.c.i(1, 0)[0] & 255;
    }

    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len) {
        int bytesToRead = Math.min(this.c.b(), len);
        this.c.h(b, off, bytesToRead, 0);
        return bytesToRead;
    }

    public long skip(long n) {
        int bytesToRemove = Math.min((int) n, this.c.b());
        this.c.g(bytesToRemove);
        return (long) bytesToRemove;
    }

    public int available() {
        return this.c.b();
    }

    public void close() {
    }
}
