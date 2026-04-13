package org.apache.http.impl.io;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import org.apache.http.MessageConstraintException;
import org.apache.http.io.a;
import org.apache.http.io.g;
import org.apache.http.io.h;
import org.apache.http.util.b;
import org.apache.http.util.c;
import org.apache.http.util.d;

/* compiled from: SessionInputBufferImpl */
public class r implements h, a {
    private final o a;
    private final byte[] b;
    private final c c;
    private final int d;
    private final org.apache.http.config.c e;
    private final CharsetDecoder f;
    private InputStream g;
    private int h = 0;
    private int i = 0;
    private CharBuffer j;

    public r(o metrics, int buffersize, int minChunkLimit, org.apache.http.config.c constraints, CharsetDecoder chardecoder) {
        org.apache.http.util.a.i(metrics, "HTTP transport metrcis");
        org.apache.http.util.a.j(buffersize, "Buffer size");
        this.a = metrics;
        this.b = new byte[buffersize];
        this.d = minChunkLimit >= 0 ? minChunkLimit : 512;
        this.e = constraints != null ? constraints : org.apache.http.config.c.c;
        this.c = new c(buffersize);
        this.f = chardecoder;
    }

    public void d(InputStream instream) {
        this.g = instream;
    }

    public boolean i() {
        return this.g != null;
    }

    public int length() {
        return this.i - this.h;
    }

    private int l(byte[] b2, int off, int len) {
        b.c(this.g, "Input stream");
        return this.g.read(b2, off, len);
    }

    public int f() {
        int i2 = this.h;
        if (i2 > 0) {
            int len = this.i - i2;
            if (len > 0) {
                byte[] bArr = this.b;
                System.arraycopy(bArr, i2, bArr, 0, len);
            }
            this.h = 0;
            this.i = len;
        }
        int off = this.i;
        byte[] bArr2 = this.b;
        int l = l(bArr2, off, bArr2.length - off);
        if (l == -1) {
            return -1;
        }
        this.i = off + l;
        this.a.a((long) l);
        return l;
    }

    public boolean h() {
        return this.h < this.i;
    }

    public void e() {
        this.h = 0;
        this.i = 0;
    }

    public int read() {
        while (!h()) {
            if (f() == -1) {
                return -1;
            }
        }
        byte[] bArr = this.b;
        int i2 = this.h;
        this.h = i2 + 1;
        return bArr[i2] & 255;
    }

    public int read(byte[] b2, int off, int len) {
        if (b2 == null) {
            return 0;
        }
        if (h()) {
            int chunk = Math.min(len, this.i - this.h);
            System.arraycopy(this.b, this.h, b2, off, chunk);
            this.h += chunk;
            return chunk;
        } else if (len > this.d) {
            int read = l(b2, off, len);
            if (read > 0) {
                this.a.a((long) read);
            }
            return read;
        } else {
            while (h() == 0) {
                if (f() == -1) {
                    return -1;
                }
            }
            int chunk2 = Math.min(len, this.i - this.h);
            System.arraycopy(this.b, this.h, b2, off, chunk2);
            this.h += chunk2;
            return chunk2;
        }
    }

    public int a(d charbuffer) {
        org.apache.http.util.a.i(charbuffer, "Char array buffer");
        int maxLineLen = this.e.d();
        int noRead = 0;
        boolean retry = true;
        while (retry) {
            int pos = -1;
            int i2 = this.h;
            while (true) {
                if (i2 >= this.i) {
                    break;
                } else if (this.b[i2] == 10) {
                    pos = i2;
                    break;
                } else {
                    i2++;
                }
            }
            if (maxLineLen > 0) {
                if ((this.c.length() + (pos > 0 ? pos : this.i)) - this.h >= maxLineLen) {
                    throw new MessageConstraintException("Maximum line length limit exceeded");
                }
            }
            if (pos == -1) {
                if (h()) {
                    int i3 = this.i;
                    int i4 = this.h;
                    this.c.append(this.b, i4, i3 - i4);
                    this.h = this.i;
                }
                noRead = f();
                if (noRead == -1) {
                    retry = false;
                }
            } else if (this.c.isEmpty()) {
                return k(charbuffer, pos);
            } else {
                retry = false;
                int i5 = this.h;
                this.c.append(this.b, i5, (pos + 1) - i5);
                this.h = pos + 1;
            }
        }
        if (noRead != -1 || !this.c.isEmpty()) {
            return j(charbuffer);
        }
        return -1;
    }

    private int j(d charbuffer) {
        int len = this.c.length();
        if (len > 0) {
            if (this.c.byteAt(len - 1) == 10) {
                len--;
            }
            if (len > 0 && this.c.byteAt(len - 1) == 13) {
                len--;
            }
        }
        if (this.f == null) {
            charbuffer.append(this.c, 0, len);
        } else {
            len = c(charbuffer, ByteBuffer.wrap(this.c.buffer(), 0, len));
        }
        this.c.clear();
        return len;
    }

    private int k(d charbuffer, int position) {
        int pos = position;
        int off = this.h;
        this.h = pos + 1;
        if (pos > off && this.b[pos - 1] == 13) {
            pos--;
        }
        int len = pos - off;
        if (this.f != null) {
            return c(charbuffer, ByteBuffer.wrap(this.b, off, len));
        }
        charbuffer.append(this.b, off, len);
        return len;
    }

    private int c(d charbuffer, ByteBuffer bbuf) {
        if (!bbuf.hasRemaining()) {
            return 0;
        }
        if (this.j == null) {
            this.j = CharBuffer.allocate(1024);
        }
        this.f.reset();
        int len = 0;
        while (bbuf.hasRemaining()) {
            len += g(this.f.decode(bbuf, this.j, true), charbuffer, bbuf);
        }
        int len2 = len + g(this.f.flush(this.j), charbuffer, bbuf);
        this.j.clear();
        return len2;
    }

    private int g(CoderResult result, d charbuffer, ByteBuffer bbuf) {
        if (result.isError()) {
            result.throwException();
        }
        this.j.flip();
        int len = this.j.remaining();
        while (this.j.hasRemaining()) {
            charbuffer.append(this.j.get());
        }
        this.j.compact();
        return len;
    }

    public boolean b(int timeout) {
        return h();
    }

    public g getMetrics() {
        return this.a;
    }
}
