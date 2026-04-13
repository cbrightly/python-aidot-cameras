package org.apache.http.impl.entity;

import java.io.OutputStream;
import org.apache.http.entity.e;
import org.apache.http.impl.io.f;
import org.apache.http.impl.io.h;
import org.apache.http.impl.io.q;
import org.apache.http.io.i;
import org.apache.http.j;
import org.apache.http.n;
import org.apache.http.util.a;

@Deprecated
/* compiled from: EntitySerializer */
public class b {
    private final e a;

    public b(e lenStrategy) {
        this.a = (e) a.i(lenStrategy, "Content length strategy");
    }

    /* access modifiers changed from: protected */
    public OutputStream a(i outbuffer, n message) {
        long len = this.a.a(message);
        if (len == -2) {
            return new f(outbuffer);
        }
        if (len == -1) {
            return new q(outbuffer);
        }
        return new h(outbuffer, len);
    }

    public void b(i outbuffer, n message, j entity) {
        a.i(outbuffer, "Session output buffer");
        a.i(message, "HTTP message");
        a.i(entity, "HTTP entity");
        OutputStream outstream = a(outbuffer, message);
        entity.writeTo(outstream);
        outstream.close();
    }
}
