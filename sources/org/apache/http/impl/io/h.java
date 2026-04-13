package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.i;
import org.apache.http.util.a;

/* compiled from: ContentLengthOutputStream */
public class h extends OutputStream {
    private final i c;
    private final long d;
    private long f = 0;
    private boolean q = false;

    public h(i out, long contentLength) {
        this.c = (i) a.i(out, "Session output buffer");
        this.d = a.h(contentLength, "Content length");
    }

    public void close() {
        if (!this.q) {
            this.q = true;
            this.c.flush();
        }
    }

    public void flush() {
        this.c.flush();
    }

    public void write(byte[] b, int off, int len) {
        if (!this.q) {
            long j = this.f;
            long j2 = this.d;
            if (j < j2) {
                long max = j2 - j;
                int chunk = len;
                if (((long) chunk) > max) {
                    chunk = (int) max;
                }
                this.c.write(b, off, chunk);
                this.f += (long) chunk;
                return;
            }
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(int b) {
        if (this.q) {
            throw new IOException("Attempted write to closed stream.");
        } else if (this.f < this.d) {
            this.c.write(b);
            this.f++;
        }
    }
}
