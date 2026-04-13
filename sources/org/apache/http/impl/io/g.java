package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.io.h;
import org.apache.http.util.a;

/* compiled from: ContentLengthInputStream */
public class g extends InputStream {
    private final long c;
    private long d = 0;
    private boolean f = false;
    private h q = null;

    public g(h in, long contentLength) {
        this.q = (h) a.i(in, "Session input buffer");
        this.c = a.h(contentLength, "Content length");
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
        h hVar = this.q;
        if (hVar instanceof org.apache.http.io.a) {
            return Math.min(((org.apache.http.io.a) hVar).length(), (int) (this.c - this.d));
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
                throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.c + "; received: " + this.d);
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
            int count = this.q.read(b, off, chunk);
            if (count != -1 || this.d >= this.c) {
                if (count > 0) {
                    this.d += (long) count;
                }
                return count;
            }
            throw new ConnectionClosedException("Premature end of Content-Length delimited message body (expected: " + this.c + "; received: " + this.d);
        }
        throw new IOException("Attempted read from closed stream.");
    }

    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    public long skip(long n) {
        int l;
        if (n <= 0) {
            return 0;
        }
        byte[] buffer = new byte[2048];
        long remaining = Math.min(n, this.c - this.d);
        long count = 0;
        while (remaining > 0 && (l = read(buffer, 0, (int) Math.min(2048, remaining))) != -1) {
            count += (long) l;
            remaining -= (long) l;
        }
        return count;
    }
}
