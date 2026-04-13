package org.apache.httpcore.impl.io;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import org.apache.httpcore.io.a;
import org.apache.httpcore.io.h;
import org.apache.httpcore.util.b;
import org.apache.httpcore.util.c;
import org.apache.httpcore.util.d;

/* compiled from: SessionOutputBufferImpl */
public class p implements h, a {
    private static final byte[] a = {13, 10};
    private final l b;
    private final c c;
    private final int d;
    private final CharsetEncoder e;
    private OutputStream f;
    private ByteBuffer g;

    public p(l metrics, int bufferSize, int fragementSizeHint, CharsetEncoder charEncoder) {
        org.apache.httpcore.util.a.h(bufferSize, "Buffer size");
        org.apache.httpcore.util.a.g(metrics, "HTTP transport metrcis");
        this.b = metrics;
        this.c = new c(bufferSize);
        this.d = fragementSizeHint >= 0 ? fragementSizeHint : 0;
        this.e = charEncoder;
    }

    public void c(OutputStream outStream) {
        this.f = outStream;
    }

    public boolean g() {
        return this.f != null;
    }

    public int length() {
        return this.c.length();
    }

    private void h(byte[] b2, int off, int len) {
        b.b(this.f, "Output stream");
        this.f.write(b2, off, len);
    }

    private void e() {
        OutputStream outputStream = this.f;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    private void d() {
        int len = this.c.length();
        if (len > 0) {
            h(this.c.buffer(), 0, len);
            this.c.clear();
            this.b.a((long) len);
        }
    }

    public void flush() {
        d();
        e();
    }

    public void write(byte[] b2, int off, int len) {
        if (b2 != null) {
            if (len > this.d || len > this.c.capacity()) {
                d();
                h(b2, off, len);
                this.b.a((long) len);
                return;
            }
            if (len > this.c.capacity() - this.c.length()) {
                d();
            }
            this.c.append(b2, off, len);
        }
    }

    public void i(byte[] b2) {
        if (b2 != null) {
            write(b2, 0, b2.length);
        }
    }

    public void write(int b2) {
        if (this.d > 0) {
            if (this.c.isFull()) {
                d();
            }
            this.c.append(b2);
            return;
        }
        d();
        this.f.write(b2);
    }

    public void a(String s) {
        if (s != null) {
            if (s.length() > 0) {
                if (this.e == null) {
                    for (int i = 0; i < s.length(); i++) {
                        write(s.charAt(i));
                    }
                } else {
                    j(CharBuffer.wrap(s));
                }
            }
            i(a);
        }
    }

    public void b(d charbuffer) {
        if (charbuffer != null) {
            if (this.e == null) {
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
                j(CharBuffer.wrap(charbuffer.buffer(), 0, charbuffer.length()));
            }
            i(a);
        }
    }

    private void j(CharBuffer cbuf) {
        if (cbuf.hasRemaining()) {
            if (this.g == null) {
                this.g = ByteBuffer.allocate(1024);
            }
            this.e.reset();
            while (cbuf.hasRemaining()) {
                f(this.e.encode(cbuf, this.g, true));
            }
            f(this.e.flush(this.g));
            this.g.clear();
        }
    }

    private void f(CoderResult result) {
        if (result.isError()) {
            result.throwException();
        }
        this.g.flip();
        while (this.g.hasRemaining()) {
            write(this.g.get());
        }
        this.g.compact();
    }
}
