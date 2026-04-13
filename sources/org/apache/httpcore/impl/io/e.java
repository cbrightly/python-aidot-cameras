package org.apache.httpcore.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.httpcore.ConnectionClosedException;
import org.apache.httpcore.io.g;
import org.apache.httpcore.util.a;

/* compiled from: ContentLengthInputStream */
public class e extends InputStream {
    private final long c;
    private long d = 0;
    private boolean f = false;
    private g q = null;

    public e(g in, long contentLength) {
        this.q = (g) a.g(in, "Session input buffer");
        this.c = a.f(contentLength, "Content length");
    }

    public void close() {
        if (!this.f) {
            try {
                if (this.d < this.c) {
                    do {
                    } while (read(new byte[2048]) >= 0);
                }
            } finally {
                this.f = true;
            }
        }
    }

    public int available() {
        g gVar = this.q;
        if (gVar instanceof org.apache.httpcore.io.a) {
            return Math.min(((org.apache.httpcore.io.a) gVar).length(), (int) (this.c - this.d));
        }
        return 0;
    }

    public int read() {
        if (this.f) {
            throw new IOException("Attempted read from closed stream.");
        } else if (this.d >= this.c) {
            return -1;
        } else {
            int b = this.q.read();
            if (b != -1) {
                this.d++;
            } else if (this.d < this.c) {
                throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: %,d; received: %,d)", Long.valueOf(this.c), Long.valueOf(this.d));
            }
            return b;
        }
    }

    public int read(byte[] b, int off, int len) {
        if (!this.f) {
            long j = this.d;
            long j2 = this.c;
            if (j >= j2) {
                return -1;
            }
            int chunk = len;
            if (((long) len) + j > j2) {
                chunk = (int) (j2 - j);
            }
            int readLen = this.q.read(b, off, chunk);
            if (readLen != -1 || this.d >= this.c) {
                if (readLen > 0) {
                    this.d += (long) readLen;
                }
                return readLen;
            }
            throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: %,d; received: %,d)", Long.valueOf(this.c), Long.valueOf(this.d));
        }
        throw new IOException("Attempted read from closed stream.");
    }

    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    public long skip(long n) {
        int readLen;
        if (n <= 0) {
            return 0;
        }
        byte[] buffer = new byte[2048];
        long remaining = Math.min(n, this.c - this.d);
        long count = 0;
        while (remaining > 0 && (readLen = read(buffer, 0, (int) Math.min(2048, remaining))) != -1) {
            count += (long) readLen;
            remaining -= (long) readLen;
        }
        return count;
    }
}
