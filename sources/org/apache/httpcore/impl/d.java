package org.apache.httpcore.impl;

import java.net.Socket;
import org.apache.httpcore.config.a;
import org.apache.httpcore.i;
import org.apache.httpcore.io.c;
import org.apache.httpcore.io.e;
import org.apache.httpcore.m;
import org.apache.httpcore.p;

/* compiled from: DefaultBHttpServerConnectionFactory */
public class d implements i<c> {
    public static final d a = new d();
    private final a b;
    private final org.apache.httpcore.entity.d c;
    private final org.apache.httpcore.entity.d d;
    private final c<m> e;
    private final e<p> f;

    public d(a cconfig, org.apache.httpcore.entity.d incomingContentStrategy, org.apache.httpcore.entity.d outgoingContentStrategy, c<m> requestParserFactory, e<p> responseWriterFactory) {
        this.b = cconfig != null ? cconfig : a.c;
        this.c = incomingContentStrategy;
        this.d = outgoingContentStrategy;
        this.e = requestParserFactory;
        this.f = responseWriterFactory;
    }

    public d(a cconfig) {
        this(cconfig, (org.apache.httpcore.entity.d) null, (org.apache.httpcore.entity.d) null, (c<m>) null, (e<p>) null);
    }

    public d() {
        this((a) null, (org.apache.httpcore.entity.d) null, (org.apache.httpcore.entity.d) null, (c<m>) null, (e<p>) null);
    }

    /* renamed from: b */
    public c a(Socket socket) {
        c conn = new c(this.b.b(), this.b.d(), b.a(this.b), b.b(this.b), this.b.f(), this.c, this.d, this.e, this.f);
        conn.O0(socket);
        return conn;
    }
}
