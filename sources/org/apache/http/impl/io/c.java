package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import org.apache.http.b;
import org.apache.http.io.a;
import org.apache.http.io.g;
import org.apache.http.io.h;
import org.apache.http.params.HttpParams;
import org.apache.http.util.d;

@Deprecated
/* compiled from: AbstractSessionInputBuffer */
public abstract class c implements h, a {
    private InputStream a;
    private byte[] b;
    private org.apache.http.util.c c;
    private Charset d;
    private boolean e;
    private int f;
    private int g;
    private o h;
    private CodingErrorAction i;
    private CodingErrorAction j;
    private int k;
    private int l;
    private CharsetDecoder m;
    private CharBuffer n;

    /* access modifiers changed from: protected */
    public void i(InputStream instream, int buffersize, HttpParams params) {
        org.apache.http.util.a.i(instream, "Input stream");
        org.apache.http.util.a.g(buffersize, "Buffer size");
        org.apache.http.util.a.i(params, "HTTP parameters");
        this.a = instream;
        this.b = new byte[buffersize];
        this.k = 0;
        this.l = 0;
        this.c = new org.apache.http.util.c(buffersize);
        String charset = (String) params.getParameter("http.protocol.element-charset");
        Charset forName = charset != null ? Charset.forName(charset) : b.b;
        this.d = forName;
        this.e = forName.equals(b.b);
        this.m = null;
        this.f = params.getIntParameter("http.connection.max-line-length", -1);
        this.g = params.getIntParameter("http.connection.min-chunk-limit", 512);
        this.h = e();
        CodingErrorAction a1 = (CodingErrorAction) params.getParameter("http.malformed.input.action");
        this.i = a1 != null ? a1 : CodingErrorAction.REPORT;
        CodingErrorAction a2 = (CodingErrorAction) params.getParameter("http.unmappable.input.action");
        this.j = a2 != null ? a2 : CodingErrorAction.REPORT;
    }

    /* access modifiers changed from: protected */
    public o e() {
        return new o();
    }

    public int length() {
        return this.l - this.k;
    }

    /* access modifiers changed from: protected */
    public int f() {
        int i2 = this.k;
        if (i2 > 0) {
            int len = this.l - i2;
            if (len > 0) {
                byte[] bArr = this.b;
                System.arraycopy(bArr, i2, bArr, 0, len);
            }
            this.k = 0;
            this.l = len;
        }
        int off = this.l;
        byte[] bArr2 = this.b;
        int l2 = this.a.read(bArr2, off, bArr2.length - off);
        if (l2 == -1) {
            return -1;
        }
        this.l = off + l2;
        this.h.a((long) l2);
        return l2;
    }

    /* access modifiers changed from: protected */
    public boolean h() {
        return this.k < this.l;
    }

    public int read() {
        while (!h()) {
            if (f() == -1) {
                return -1;
            }
        }
        byte[] bArr = this.b;
        int i2 = this.k;
        this.k = i2 + 1;
        return bArr[i2] & 255;
    }

    public int read(byte[] b2, int off, int len) {
        if (b2 == null) {
            return 0;
        }
        if (h()) {
            int chunk = Math.min(len, this.l - this.k);
            System.arraycopy(this.b, this.k, b2, off, chunk);
            this.k += chunk;
            return chunk;
        } else if (len > this.g) {
            int read = this.a.read(b2, off, len);
            if (read > 0) {
                this.h.a((long) read);
            }
            return read;
        } else {
            while (h() == 0) {
                if (f() == -1) {
                    return -1;
                }
            }
            int chunk2 = Math.min(len, this.l - this.k);
            System.arraycopy(this.b, this.k, b2, off, chunk2);
            this.k += chunk2;
            return chunk2;
        }
    }

    private int l() {
        for (int i2 = this.k; i2 < this.l; i2++) {
            if (this.b[i2] == 10) {
                return i2;
            }
        }
        return -1;
    }

    public int a(d charbuffer) {
        org.apache.http.util.a.i(charbuffer, "Char array buffer");
        int noRead = 0;
        boolean retry = true;
        while (retry) {
            int i2 = l();
            if (i2 == -1) {
                if (h()) {
                    int i3 = this.l;
                    int i4 = this.k;
                    this.c.append(this.b, i4, i3 - i4);
                    this.k = this.l;
                }
                noRead = f();
                if (noRead == -1) {
                    retry = false;
                }
            } else if (this.c.isEmpty()) {
                return k(charbuffer, i2);
            } else {
                retry = false;
                int i5 = this.k;
                this.c.append(this.b, i5, (i2 + 1) - i5);
                this.k = i2 + 1;
            }
            if (this.f > 0 && this.c.length() >= this.f) {
                throw new IOException("Maximum line length limit exceeded");
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
        if (this.e) {
            charbuffer.append(this.c, 0, len);
        } else {
            len = d(charbuffer, ByteBuffer.wrap(this.c.buffer(), 0, len));
        }
        this.c.clear();
        return len;
    }

    private int k(d charbuffer, int position) {
        int off = this.k;
        int i2 = position;
        this.k = i2 + 1;
        if (i2 > off && this.b[i2 - 1] == 13) {
            i2--;
        }
        int len = i2 - off;
        if (!this.e) {
            return d(charbuffer, ByteBuffer.wrap(this.b, off, len));
        }
        charbuffer.append(this.b, off, len);
        return len;
    }

    private int d(d charbuffer, ByteBuffer bbuf) {
        if (!bbuf.hasRemaining()) {
            return 0;
        }
        if (this.m == null) {
            CharsetDecoder newDecoder = this.d.newDecoder();
            this.m = newDecoder;
            newDecoder.onMalformedInput(this.i);
            this.m.onUnmappableCharacter(this.j);
        }
        if (this.n == null) {
            this.n = CharBuffer.allocate(1024);
        }
        this.m.reset();
        int len = 0;
        while (bbuf.hasRemaining()) {
            len += g(this.m.decode(bbuf, this.n, true), charbuffer, bbuf);
        }
        int len2 = len + g(this.m.flush(this.n), charbuffer, bbuf);
        this.n.clear();
        return len2;
    }

    private int g(CoderResult result, d charbuffer, ByteBuffer bbuf) {
        if (result.isError()) {
            result.throwException();
        }
        this.n.flip();
        int len = this.n.remaining();
        while (this.n.hasRemaining()) {
            charbuffer.append(this.n.get());
        }
        this.n.compact();
        return len;
    }

    public g getMetrics() {
        return this.h;
    }
}
