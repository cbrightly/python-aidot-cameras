package org.apache.http.client.entity;

import java.io.InputStream;

/* compiled from: LazyDecompressingInputStream */
public class d extends InputStream {
    private final InputStream c;
    private final c d;
    private InputStream f;

    public d(InputStream wrappedStream, c inputStreamFactory) {
        this.c = wrappedStream;
        this.d = inputStreamFactory;
    }

    private void a() {
        if (this.f == null) {
            this.f = this.d.a(this.c);
        }
    }

    public int read() {
        a();
        return this.f.read();
    }

    public int read(byte[] b) {
        a();
        return this.f.read(b);
    }

    public int read(byte[] b, int off, int len) {
        a();
        return this.f.read(b, off, len);
    }

    public long skip(long n) {
        a();
        return this.f.skip(n);
    }

    public boolean markSupported() {
        return false;
    }

    public int available() {
        a();
        return this.f.available();
    }

    public void close() {
        try {
            InputStream inputStream = this.f;
            if (inputStream != null) {
                inputStream.close();
            }
        } finally {
            this.c.close();
        }
    }
}
