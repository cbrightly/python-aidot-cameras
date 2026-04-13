package org.apache.http.impl.conn;

import org.apache.http.conn.t;
import org.apache.http.l;
import org.apache.http.protocol.f;
import org.apache.http.util.a;

/* compiled from: DefaultProxyRoutePlanner */
public class o extends p {
    private final l b;

    public o(l proxy, t schemePortResolver) {
        super(schemePortResolver);
        this.b = (l) a.i(proxy, "Proxy host");
    }

    /* access modifiers changed from: protected */
    public l b(l target, org.apache.http.o request, f context) {
        return this.b;
    }
}
