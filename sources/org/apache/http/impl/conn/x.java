package org.apache.http.impl.conn;

import org.apache.http.b;
import org.apache.http.io.g;
import org.apache.http.io.i;
import org.apache.http.util.d;

@Deprecated
/* compiled from: LoggingSessionOutputBuffer */
public class x implements i {
    private final i a;
    private final e0 b;
    private final String c;

    public x(i out, e0 wire, String charset) {
        this.a = out;
        this.b = wire;
        this.c = charset != null ? charset : b.b.name();
    }

    public void write(byte[] b2, int off, int len) {
        this.a.write(b2, off, len);
        if (this.b.a()) {
            this.b.i(b2, off, len);
        }
    }

    public void write(int b2) {
        this.a.write(b2);
        if (this.b.a()) {
            this.b.f(b2);
        }
    }

    public void flush() {
        this.a.flush();
    }

    public void b(d buffer) {
        this.a.b(buffer);
        if (this.b.a()) {
            String s = new String(buffer.buffer(), 0, buffer.length());
            this.b.h((s + "\r\n").getBytes(this.c));
        }
    }

    public void a(String s) {
        this.a.a(s);
        if (this.b.a()) {
            this.b.h((s + "\r\n").getBytes(this.c));
        }
    }

    public g getMetrics() {
        return this.a.getMetrics();
    }
}
