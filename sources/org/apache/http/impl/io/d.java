package org.apache.http.impl.io;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import org.apache.http.b;
import org.apache.http.io.a;
import org.apache.http.io.g;
import org.apache.http.io.i;
import org.apache.http.params.HttpParams;
import org.apache.http.util.c;

@Deprecated
/* compiled from: AbstractSessionOutputBuffer */
public abstract class d implements i, a {
    private static final byte[] a = {13, 10};
    private OutputStream b;
    private c c;
    private Charset d;
    private boolean e;
    private int f;
    private o g;
    private CodingErrorAction h;
    private CodingErrorAction i;
    private CharsetEncoder j;
    private ByteBuffer k;

    /* access modifiers changed from: protected */
    public void f(OutputStream outstream, int buffersize, HttpParams params) {
        org.apache.http.util.a.i(outstream, "Input stream");
        org.apache.http.util.a.g(buffersize, "Buffer size");
        org.apache.http.util.a.i(params, "HTTP parameters");
        this.b = outstream;
        this.c = new c(buffersize);
        String charset = (String) params.getParameter("http.protocol.element-charset");
        Charset forName = charset != null ? Charset.forName(charset) : b.b;
        this.d = forName;
        this.e = forName.equals(b.b);
        this.j = null;
        this.f = params.getIntParameter("http.connection.min-chunk-limit", 512);
        this.g = c();
        CodingErrorAction a1 = (CodingErrorAction) params.getParameter("http.malformed.input.action");
        this.h = a1 != null ? a1 : CodingErrorAction.REPORT;
        CodingErrorAction a2 = (CodingErrorAction) params.getParameter("http.unmappable.input.action");
        this.i = a2 != null ? a2 : CodingErrorAction.REPORT;
    }

    /* access modifiers changed from: protected */
    public o c() {
        return new o();
    }

    public int length() {
        return this.c.length();
    }

    /* access modifiers changed from: protected */
    public void d() {
        int len = this.c.length();
        if (len > 0) {
            this.b.write(this.c.buffer(), 0, len);
            this.c.clear();
            this.g.a((long) len);
        }
    }

    public void flush() {
        d();
        this.b.flush();
    }

    public void write(byte[] b2, int off, int len) {
        if (b2 != null) {
            if (len > this.f || len > this.c.capacity()) {
                d();
                this.b.write(b2, off, len);
                this.g.a((long) len);
                return;
            }
            if (len > this.c.capacity() - this.c.length()) {
                d();
            }
            this.c.append(b2, off, len);
        }
    }

    public void g(byte[] b2) {
        if (b2 != null) {
            write(b2, 0, b2.length);
        }
    }

    public void write(int b2) {
        if (this.c.isFull()) {
            d();
        }
        this.c.append(b2);
    }

    public void a(String s) {
        if (s != null) {
            if (s.length() > 0) {
                if (this.e) {
                    for (int i2 = 0; i2 < s.length(); i2++) {
                        write(s.charAt(i2));
                    }
                } else {
                    h(CharBuffer.wrap(s));
                }
            }
            g(a);
        }
    }

    public void b(org.apache.http.util.d charbuffer) {
        if (charbuffer != null) {
            if (this.e) {
                int off = 0;
                int remaining = charbuffer.length();
                while (remaining > 0) {
                    int chunk = Math.min(this.c.capacity() - this.c.length(), remaining);
                    if (chunk > 0) {
                        this.c.append(charbuffer, off, chunk);
                    }
                    if (this.c.isFull()) {
                        d();
                    }
                    off += chunk;
                    remaining -= chunk;
                }
            } else {
                h(CharBuffer.wrap(charbuffer.buffer(), 0, charbuffer.length()));
            }
            g(a);
        }
    }

    private void h(CharBuffer cbuf) {
        if (cbuf.hasRemaining()) {
            if (this.j == null) {
                CharsetEncoder newEncoder = this.d.newEncoder();
                this.j = newEncoder;
                newEncoder.onMalformedInput(this.h);
                this.j.onUnmappableCharacter(this.i);
            }
            if (this.k == null) {
                this.k = ByteBuffer.allocate(1024);
            }
            this.j.reset();
            while (cbuf.hasRemaining()) {
                e(this.j.encode(cbuf, this.k, true));
            }
            e(this.j.flush(this.k));
            this.k.clear();
        }
    }

    private void e(CoderResult result) {
        if (result.isError()) {
            result.throwException();
        }
        this.k.flip();
        while (this.k.hasRemaining()) {
            write(this.k.get());
        }
        this.k.compact();
    }

    public g getMetrics() {
        return this.g;
    }
}
