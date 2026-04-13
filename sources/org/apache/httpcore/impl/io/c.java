package org.apache.httpcore.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.httpcore.ConnectionClosedException;
import org.apache.httpcore.HttpException;
import org.apache.httpcore.MalformedChunkCodingException;
import org.apache.httpcore.TruncatedChunkException;
import org.apache.httpcore.config.b;
import org.apache.httpcore.e;
import org.apache.httpcore.io.g;
import org.apache.httpcore.message.t;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.d;

/* compiled from: ChunkedInputStream */
public class c extends InputStream {
    private e[] a1 = new e[0];
    private final g c;
    private final d d;
    private final b f;
    private boolean p0 = false;
    private int q;
    private long x;
    private long y;
    private boolean z = false;

    public c(g in, b constraints) {
        this.c = (g) a.g(in, "Session input buffer");
        this.y = 0;
        this.d = new d(16);
        this.f = constraints != null ? constraints : b.c;
        this.q = 1;
    }

    public int available() {
        g gVar = this.c;
        if (gVar instanceof org.apache.httpcore.io.a) {
            return (int) Math.min((long) ((org.apache.httpcore.io.a) gVar).length(), this.x - this.y);
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
            int readLen = this.c.read(b, off, (int) Math.min((long) len, this.x - this.y));
            if (readLen != -1) {
                long j = this.y + ((long) readLen);
                this.y = j;
                if (j >= this.x) {
                    this.q = 3;
                }
                return readLen;
            }
            this.z = true;
            throw new TruncatedChunkException("Truncated chunk (expected size: %,d; actual size: %,d)", Long.valueOf(this.x), Long.valueOf(this.y));
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
            this.a1 = a.c(this.c, this.f.b(), this.f.c(), (t) null);
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
