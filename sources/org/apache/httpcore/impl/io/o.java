package org.apache.httpcore.impl.io;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import org.apache.httpcore.MessageConstraintException;
import org.apache.httpcore.config.b;
import org.apache.httpcore.io.a;
import org.apache.httpcore.io.g;
import org.apache.httpcore.util.c;
import org.apache.httpcore.util.d;

/* compiled from: SessionInputBufferImpl */
public class o implements g, a {
    private final l a;
    private final byte[] b;
    private final c c;
    private final int d;
    private final b e;
    private final CharsetDecoder f;
    private InputStream g;
    private int h = 0;
    private int i = 0;
    private CharBuffer j;

    public o(l metrics, int bufferSize, int minChunkLimit, b constraints, CharsetDecoder charDecoder) {
        org.apache.httpcore.util.a.g(metrics, "HTTP transport metrcis");
        org.apache.httpcore.util.a.h(bufferSize, "Buffer size");
        this.a = metrics;
        this.b = new byte[bufferSize];
        this.d = minChunkLimit >= 0 ? minChunkLimit : 512;
        this.e = constraints != null ? constraints : b.c;
        this.c = new c(bufferSize);
        this.f = charDecoder;
    }

    public void c(InputStream inputStream) {
        this.g = inputStream;
    }

    public boolean h() {
        return this.g != null;
    }

    public int length() {
        return this.i - this.h;
    }

    private int k(byte[] b2, int off, int len) {
        org.apache.httpcore.util.b.b(this.g, "Input stream");
        return this.g.read(b2, off, len);
    }

    public int e() {
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
        int readLen = k(bArr2, off, bArr2.length - off);
        if (readLen == -1) {
            return -1;
        }
        this.i = off + readLen;
        this.a.a((long) readLen);
        return readLen;
    }

    public boolean g() {
        return this.h < this.i;
    }

    public void d() {
        this.h = 0;
        this.i = 0;
    }

    public int read() {
        while (!g()) {
            if (e() == -1) {
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
        if (g()) {
            int chunk = Math.min(len, this.i - this.h);
            System.arraycopy(this.b, this.h, b2, off, chunk);
            this.h += chunk;
            return chunk;
        } else if (len > this.d) {
            int readLen = k(b2, off, len);
            if (readLen > 0) {
                this.a.a((long) readLen);
            }
            return readLen;
        } else {
            while (g() == 0) {
                if (e() == -1) {
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
        org.apache.httpcore.util.a.g(charbuffer, "Char array buffer");
        int maxLineLen = this.e.c();
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
                if ((this.c.length() + (pos >= 0 ? pos : this.i)) - this.h >= maxLineLen) {
                    throw new MessageConstraintException("Maximum line length limit exceeded");
                }
            }
            if (pos == -1) {
                if (g()) {
                    int i3 = this.i;
                    int i4 = this.h;
                    this.c.append(this.b, i4, i3 - i4);
                    this.h = this.i;
                }
                noRead = e();
                if (noRead == -1) {
                    retry = false;
                }
            } else if (this.c.isEmpty()) {
                return j(charbuffer, pos);
            } else {
                retry = false;
                int i5 = this.h;
                this.c.append(this.b, i5, (pos + 1) - i5);
                this.h = pos + 1;
            }
        }
        if (noRead != -1 || !this.c.isEmpty()) {
            return i(charbuffer);
        }
        return -1;
    }

    private int i(d charbuffer) {
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
            len = b(charbuffer, ByteBuffer.wrap(this.c.buffer(), 0, len));
        }
        this.c.clear();
        return len;
    }

    private int j(d charbuffer, int position) {
        int pos = position;
        int off = this.h;
        this.h = pos + 1;
        if (pos > off && this.b[pos - 1] == 13) {
            pos--;
        }
        int len = pos - off;
        if (this.f != null) {
            return b(charbuffer, ByteBuffer.wrap(this.b, off, len));
        }
        charbuffer.append(this.b, off, len);
        return len;
    }

    private int b(d charbuffer, ByteBuffer bbuf) {
        if (!bbuf.hasRemaining()) {
            return 0;
        }
        if (this.j == null) {
            this.j = CharBuffer.allocate(1024);
        }
        this.f.reset();
        int len = 0;
        while (bbuf.hasRemaining()) {
            len += f(this.f.decode(bbuf, this.j, true), charbuffer, bbuf);
        }
        int len2 = len + f(this.f.flush(this.j), charbuffer, bbuf);
        this.j.clear();
        return len2;
    }

    private int f(CoderResult result, d charbuffer, ByteBuffer bbuf) {
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
}
