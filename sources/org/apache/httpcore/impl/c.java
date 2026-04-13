package org.apache.httpcore.impl;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.httpcore.impl.entity.a;
import org.apache.httpcore.impl.io.h;
import org.apache.httpcore.impl.io.j;
import org.apache.httpcore.io.b;
import org.apache.httpcore.io.d;
import org.apache.httpcore.io.e;
import org.apache.httpcore.k;
import org.apache.httpcore.m;
import org.apache.httpcore.p;
import org.apache.httpcore.s;

/* compiled from: DefaultBHttpServerConnection */
public class c extends a implements s {
    private final d<p> a1;
    private final b<m> p0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(int bufferSize, int fragmentSizeHint, CharsetDecoder charDecoder, CharsetEncoder charEncoder, org.apache.httpcore.config.b constraints, org.apache.httpcore.entity.d incomingContentStrategy, org.apache.httpcore.entity.d outgoingContentStrategy, org.apache.httpcore.io.c<m> requestParserFactory, e<p> responseWriterFactory) {
        super(bufferSize, fragmentSizeHint, charDecoder, charEncoder, constraints, incomingContentStrategy != null ? incomingContentStrategy : a.a, outgoingContentStrategy);
        org.apache.httpcore.config.b bVar = constraints;
        this.p0 = (requestParserFactory != null ? requestParserFactory : h.a).a(j(), constraints);
        this.a1 = (responseWriterFactory != null ? responseWriterFactory : j.a).a(l());
    }

    /* access modifiers changed from: protected */
    public void u(m request) {
    }

    /* access modifiers changed from: protected */
    public void v(p response) {
    }

    public void O0(Socket socket) {
        super.O0(socket);
    }

    public m R0() {
        i();
        m request = this.p0.a(q());
        u(request);
        o();
        return request;
    }

    public void V0(k request) {
        org.apache.httpcore.util.a.g(request, "HTTP request");
        i();
        request.b(s(request));
    }

    public void B(p response) {
        org.apache.httpcore.util.a.g(response, "HTTP response");
        i();
        this.a1.a(response);
        v(response);
        if (response.j().getStatusCode() >= 200) {
            r();
        }
    }

    public void A(p response) {
        org.apache.httpcore.util.a.g(response, "HTTP response");
        i();
        org.apache.httpcore.j entity = response.a();
        if (entity != null) {
            OutputStream outStream = t(response);
            entity.writeTo(outStream);
            outStream.close();
        }
    }

    public void flush() {
        i();
        g();
    }
}
