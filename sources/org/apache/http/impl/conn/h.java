package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.commons.logging.a;
import org.apache.http.conn.r;
import org.apache.http.conn.s;
import org.apache.http.d;
import org.apache.http.impl.i;
import org.apache.http.io.c;
import org.apache.http.l;
import org.apache.http.message.u;
import org.apache.http.o;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.f;
import org.apache.http.q;

@Deprecated
/* compiled from: DefaultClientConnection */
public class h extends i implements s, r, f {
    private boolean A4;
    private volatile boolean B4;
    private final Map<String, Object> C4 = new HashMap();
    private final a a2 = org.apache.commons.logging.h.n(getClass());
    private final a p2 = org.apache.commons.logging.h.o("org.apache.http.headers");
    private final a p3 = org.apache.commons.logging.h.o("org.apache.http.wire");
    private volatile Socket p4;
    private l z4;

    public final boolean isSecure() {
        return this.A4;
    }

    public final Socket q() {
        return this.p4;
    }

    public SSLSession S0() {
        if (this.p4 instanceof SSLSocket) {
            return ((SSLSocket) this.p4).getSession();
        }
        return null;
    }

    public void b0(Socket sock, l target) {
        s();
        this.p4 = sock;
        this.z4 = target;
        if (this.B4) {
            sock.close();
            throw new InterruptedIOException("Connection already shutdown");
        }
    }

    public void U(boolean secure, HttpParams params) {
        org.apache.http.util.a.i(params, "Parameters");
        s();
        this.A4 = secure;
        t(this.p4, params);
    }

    public void shutdown() {
        this.B4 = true;
        try {
            super.shutdown();
            if (this.a2.isDebugEnabled()) {
                a aVar = this.a2;
                aVar.debug("Connection " + this + " shut down");
            }
            Socket sock = this.p4;
            if (sock != null) {
                sock.close();
            }
        } catch (IOException ex) {
            this.a2.debug("I/O error shutting down connection", ex);
        }
    }

    public void close() {
        try {
            super.close();
            if (this.a2.isDebugEnabled()) {
                a aVar = this.a2;
                aVar.debug("Connection " + this + " closed");
            }
        } catch (IOException ex) {
            this.a2.debug("I/O error closing connection", ex);
        }
    }

    /* access modifiers changed from: protected */
    public org.apache.http.io.h u(Socket socket, int buffersize, HttpParams params) {
        org.apache.http.io.h inbuffer = super.u(socket, buffersize > 0 ? buffersize : 8192, params);
        if (this.p3.isDebugEnabled()) {
            return new w(inbuffer, new e0(this.p3), HttpProtocolParams.getHttpElementCharset(params));
        }
        return inbuffer;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.io.i v(Socket socket, int buffersize, HttpParams params) {
        org.apache.http.io.i outbuffer = super.v(socket, buffersize > 0 ? buffersize : 8192, params);
        if (this.p3.isDebugEnabled()) {
            return new x(outbuffer, new e0(this.p3), HttpProtocolParams.getHttpElementCharset(params));
        }
        return outbuffer;
    }

    /* access modifiers changed from: protected */
    public c<q> m(org.apache.http.io.h buffer, org.apache.http.r responseFactory, HttpParams params) {
        return new k(buffer, (u) null, responseFactory, params);
    }

    public void O0(Socket socket) {
        t(socket, new BasicHttpParams());
    }

    public void e0(Socket sock, l target, boolean secure, HttpParams params) {
        a();
        org.apache.http.util.a.i(target, "Target host");
        org.apache.http.util.a.i(params, "Parameters");
        if (sock != null) {
            this.p4 = sock;
            t(sock, params);
        }
        this.z4 = target;
        this.A4 = secure;
    }

    public q K0() {
        q response = super.K0();
        if (this.a2.isDebugEnabled()) {
            this.a2.debug("Receiving response: " + response.j());
        }
        if (this.p2.isDebugEnabled()) {
            this.p2.debug("<< " + response.j().toString());
            for (d header : response.v()) {
                this.p2.debug("<< " + header.toString());
            }
        }
        return response;
    }

    public void E0(o request) {
        if (this.a2.isDebugEnabled()) {
            this.a2.debug("Sending request: " + request.r());
        }
        super.E0(request);
        if (this.p2.isDebugEnabled()) {
            this.p2.debug(">> " + request.r().toString());
            for (d header : request.v()) {
                this.p2.debug(">> " + header.toString());
            }
        }
    }

    public Object getAttribute(String id) {
        return this.C4.get(id);
    }

    public void setAttribute(String id, Object obj) {
        this.C4.put(id, obj);
    }
}
