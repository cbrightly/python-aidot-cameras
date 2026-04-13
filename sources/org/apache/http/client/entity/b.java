package org.apache.http.client.entity;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

/* compiled from: DeflateInputStream */
public class b extends InputStream {
    private final InputStream c;

    public b(InputStream wrapped) {
        PushbackInputStream pushback = new PushbackInputStream(wrapped, 2);
        int i1 = pushback.read();
        int i2 = pushback.read();
        if (i1 == -1 || i2 == -1) {
            throw new ZipException("Unexpected end of stream");
        }
        pushback.unread(i2);
        pushback.unread(i1);
        boolean nowrap = true;
        int b1 = i1 & 255;
        int compressionInfo = (b1 >> 4) & 15;
        int b2 = i2 & 255;
        if ((b1 & 15) == 8 && compressionInfo <= 7 && ((b1 << 8) | b2) % 31 == 0) {
            nowrap = false;
        }
        this.c = new a(pushback, new Inflater(nowrap));
    }

    public int read() {
        return this.c.read();
    }

    public int read(byte[] b) {
        return this.c.read(b);
    }

    public int read(byte[] b, int off, int len) {
        return this.c.read(b, off, len);
    }

    public long skip(long n) {
        return this.c.skip(n);
    }

    public int available() {
        return this.c.available();
    }

    public void mark(int readLimit) {
        this.c.mark(readLimit);
    }

    public void reset() {
        this.c.reset();
    }

    public boolean markSupported() {
        return this.c.markSupported();
    }

    public void close() {
        this.c.close();
    }

    /* compiled from: DeflateInputStream */
    public static class a extends InflaterInputStream {
        private boolean c = false;

        public a(InputStream in, Inflater inflater) {
            super(in, inflater);
        }

        public void close() {
            if (!this.c) {
                this.c = true;
                this.inf.end();
                super.close();
            }
        }
    }
}
