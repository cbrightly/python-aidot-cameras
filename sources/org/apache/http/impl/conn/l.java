package org.apache.http.impl.conn;

import org.apache.http.impl.e;
import org.apache.http.io.c;
import org.apache.http.io.d;
import org.apache.http.io.h;
import org.apache.http.message.k;
import org.apache.http.message.u;
import org.apache.http.q;
import org.apache.http.r;

/* compiled from: DefaultHttpResponseParserFactory */
public class l implements d<q> {
    public static final l a = new l();
    private final u b;
    private final r c;

    public l(u lineParser, r responseFactory) {
        this.b = lineParser != null ? lineParser : k.b;
        this.c = responseFactory != null ? responseFactory : e.a;
    }

    public l() {
        this((u) null, (r) null);
    }

    public c<q> a(h buffer, org.apache.http.config.c constraints) {
        return new k(buffer, this.b, this.c, constraints);
    }
}
