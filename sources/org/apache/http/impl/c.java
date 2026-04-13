package org.apache.http.impl;

import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.apache.http.h;
import org.apache.http.impl.io.j;
import org.apache.http.impl.io.l;
import org.apache.http.io.d;
import org.apache.http.io.e;
import org.apache.http.io.f;
import org.apache.http.k;
import org.apache.http.o;
import org.apache.http.q;
import org.apache.http.util.a;

/* compiled from: DefaultBHttpClientConnection */
public class c extends b implements h {
    private final e<o> a1;
    private final org.apache.http.io.c<q> p0;

    public c(int buffersize, int fragmentSizeHint, CharsetDecoder chardecoder, CharsetEncoder charencoder, org.apache.http.config.c constraints, org.apache.http.entity.e incomingContentStrategy, org.apache.http.entity.e outgoingContentStrategy, f<o> requestWriterFactory, d<q> responseParserFactory) {
        super(buffersize, fragmentSizeHint, chardecoder, charencoder, constraints, incomingContentStrategy, outgoingContentStrategy);
        this.a1 = (requestWriterFactory != null ? requestWriterFactory : j.a).a(n());
        this.p0 = (responseParserFactory != null ? responseParserFactory : l.a).a(m(), constraints);
    }

    /* access modifiers changed from: protected */
    public void z(q response) {
    }

    /* access modifiers changed from: protected */
    public void x(o request) {
    }

    public void O0(Socket socket) {
        super.O0(socket);
    }

    public boolean a0(int timeout) {
        j();
        try {
            return a(timeout);
        } catch (SocketTimeoutException e) {
            return false;
        }
    }

    public void E0(o request) {
        a.i(request, "HTTP request");
        j();
        this.a1.a(request);
        x(request);
        s();
    }

    public void H(k request) {
        a.i(request, "HTTP request");
        j();
        org.apache.http.j entity = request.a();
        if (entity != null) {
            OutputStream outstream = v(request);
            entity.writeTo(outstream);
            outstream.close();
        }
    }

    public q K0() {
        j();
        q response = this.p0.a();
        z(response);
        if (response.j().getStatusCode() >= 200) {
            t();
        }
        return response;
    }

    public void G0(q response) {
        a.i(response, "HTTP response");
        j();
        response.l(u(response));
    }

    public void flush() {
        j();
        i();
    }
}
