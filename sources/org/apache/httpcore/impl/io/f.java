package org.apache.httpcore.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.httpcore.io.h;
import org.apache.httpcore.util.a;

/* compiled from: ContentLengthOutputStream */
public class f extends OutputStream {
    private final h c;
    private final long d;
    private long f;
    private boolean q;

    public f(h out, long contentLength) {
        this.c = (h) a.g(out, "Session output buffer");
        this.d = a.f(contentLength, "Content length");
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
