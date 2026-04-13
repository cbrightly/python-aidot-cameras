package org.apache.http.impl.conn;

import org.apache.http.io.b;
import org.apache.http.io.g;
import org.apache.http.io.h;
import org.apache.http.util.d;

@Deprecated
/* compiled from: LoggingSessionInputBuffer */
public class w implements h, b {
    private final h a;
    private final b b;
    private final e0 c;
    private final String d;

    public w(h in, e0 wire, String charset) {
        this.a = in;
        this.b = in instanceof b ? (b) in : null;
        this.c = wire;
        this.d = charset != null ? charset : org.apache.http.b.b.name();
    }

    public boolean b(int timeout) {
        return this.a.b(timeout);
    }

    public int read(byte[] b2, int off, int len) {
        int l = this.a.read(b2, off, len);
        if (this.c.a() && l > 0) {
            this.c.e(b2, off, l);
        }
        return l;
    }

    public int read() {
        int l = this.a.read();
        if (this.c.a() && l != -1) {
            this.c.b(l);
        }
        return l;
    }

    public int a(d buffer) {
        int l = this.a.a(buffer);
        if (this.c.a() && l >= 0) {
            String s = new String(buffer.buffer(), buffer.length() - l, l);
            this.c.d((s + "\r\n").getBytes(this.d));
        }
        return l;
    }

    public g getMetrics() {
        return this.a.getMetrics();
    }

    public boolean c() {
        b bVar = this.b;
        if (bVar != null) {
            return bVar.c();
        }
        return false;
    }
}
