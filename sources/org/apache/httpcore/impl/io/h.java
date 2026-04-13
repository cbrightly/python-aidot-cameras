package org.apache.httpcore.impl.io;

import org.apache.httpcore.impl.f;
import org.apache.httpcore.io.b;
import org.apache.httpcore.io.c;
import org.apache.httpcore.io.g;
import org.apache.httpcore.m;
import org.apache.httpcore.message.j;
import org.apache.httpcore.message.t;
import org.apache.httpcore.n;

/* compiled from: DefaultHttpRequestParserFactory */
public class h implements c<m> {
    public static final h a = new h();
    private final t b;
    private final n c;

    public h(t lineParser, n requestFactory) {
        this.b = lineParser != null ? lineParser : j.b;
        this.c = requestFactory != null ? requestFactory : f.a;
    }

    public h() {
        this((t) null, (n) null);
    }

    public b<m> a(g buffer, org.apache.httpcore.config.b constraints) {
        return new g(buffer, this.b, this.c, constraints);
    }
}
