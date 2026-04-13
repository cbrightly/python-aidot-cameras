package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpException;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.TruncatedChunkException;
import org.apache.http.config.c;
import org.apache.http.d;
import org.apache.http.io.h;
import org.apache.http.message.u;
import org.apache.http.util.a;

/* compiled from: ChunkedInputStream */
public class e extends InputStream {
    private d[] a1;
    private final h c;
    private final org.apache.http.util.d d;
    private final c f;
    private boolean p0;
    private int q;
    private long x;
    private long y;
    private boolean z;

    public e(h in, c constraints) {
        this.z = false;
        this.p0 = false;
        this.a1 = new d[0];
        this.c = (h) a.i(in, "Session input buffer");
        this.y = 0;
        this.d = new org.apache.http.util.d(16);
        this.f = constraints != null ? constraints : c.c;
        this.q = 1;
    }

    public e(h in) {
        this(in, (c) null);
    }

    public int available() {
        h hVar = this.c;
        if (hVar instanceof org.apache.http.io.a) {
            return (int) Math.min((long) ((org.apache.http.io.a) hVar).length(), this.x - this.y);
        }
        return 0;
    }

    public int read() {
        if (this.p0) {
            throw new IOException("Attempted read from closed stream.");
        } else if (this.z) {
            return -1;
        } else {
            if (this.q != 2) {
                c();
                if (this.z) {
                    return -1;
                }
            }
            int b = this.c.read();
            if (b != -1) {
                long j = this.y + 1;
                this.y = j;
                if (j >= this.x) {
                    this.q = 3;
                }
            }
            return b;
        }
    }

    public int read(byte[] b, int off, int len) {
        if (this.p0) {
            throw new IOException("Attempted read from closed stream.");
        } else if (this.z) {
            return -1;
        } else {
            if (this.q != 2) {
                c();
                if (this.z) {
                    return -1;
                }
            }
            int bytesRead = this.c.read(b, off, (int) Math.min((long) len, this.x - this.y));
            if (bytesRead != -1) {
                long j = this.y + ((long) bytesRead);
                this.y = j;
                if (j >= this.x) {
                    this.q = 3;
                }
                return bytesRead;
            }
            this.z = true;
            throw new TruncatedChunkException("Truncated chunk ( expected size: " + this.x + "; actual size: " + this.y + ")");
        }
    }

    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    private void c() {
        if (this.q != Integer.MAX_VALUE) {
            try {
                long a = a();
                this.x = a;
                if (a >= 0) {
                    this.q = 2;
                    this.y = 0;
                    if (a == 0) {
                        this.z = true;
                        g();
                        return;
                    }
                    return;
                }
                throw new MalformedChunkCodingException("Negative chunk size");
            } catch (MalformedChunkCodingException ex) {
                this.q = Integer.MAX_VALUE;
                throw ex;
            }
        } else {
            throw new MalformedChunkCodingException("Corrupt data stream");
        }
    }

    private long a() {
        switch (this.q) {
            case 1:
                break;
            case 3:
                this.d.clear();
                if (this.c.a(this.d) == -1) {
                    throw new MalformedChunkCodingException("CRLF expected at end of chunk");
                } else if (this.d.isEmpty()) {
                    this.q = 1;
                    break;
                } else {
                    throw new MalformedChunkCodingException("Unexpected content at the end of chunk");
                }
            default:
                throw new IllegalStateException("Inconsistent codec state");
        }
        this.d.clear();
        if (this.c.a(this.d) != -1) {
            int separator = this.d.indexOf(59);
            if (separator < 0) {
                separator = this.d.length();
            }
            String s = this.d.substringTrimmed(0, separator);
            try {
                return Long.parseLong(s, 16);
            } catch (NumberFormatException e) {
                throw new MalformedChunkCodingException("Bad chunk header: " + s);
            }
        } else {
            throw new ConnectionClosedException("Premature end of chunk coded message body: closing chunk expected");
        }
    }

    private void g() {
        try {
            this.a1 = a.c(this.c, this.f.c(), this.f.d(), (u) null);
        } catch (HttpException ex) {
            IOException ioe = new MalformedChunkCodingException("Invalid footer: " + ex.getMessage());
            ioe.initCause(ex);
            throw ioe;
        }
    }

    public void close() {
        if (!this.p0) {
            try {
                if (!this.z && this.q != Integer.MAX_VALUE) {
                    do {
                    } while (read(new byte[2048]) >= 0);
                }
            } finally {
                this.z = true;
                this.p0 = true;
            }
        }
    }
}
