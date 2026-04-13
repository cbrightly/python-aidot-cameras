package org.apache.http.impl.client;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.p;
import org.apache.http.message.a;
import org.apache.http.message.n;
import org.apache.http.o;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.v;
import org.apache.http.x;

@Deprecated
/* compiled from: RequestWrapper */
public class f0 extends a implements p {
    private final o f;
    private URI q;
    private String x;
    private v y;
    private int z;

    public f0(o request) {
        org.apache.http.util.a.i(request, "HTTP request");
        this.f = request;
        z(request.getParams());
        u0(request.v());
        if (request instanceof p) {
            this.q = ((p) request).t();
            this.x = ((p) request).getMethod();
            this.y = null;
        } else {
            x requestLine = request.r();
            try {
                this.q = new URI(requestLine.getUri());
                this.x = requestLine.getMethod();
                this.y = request.getProtocolVersion();
            } catch (URISyntaxException ex) {
                throw new ProtocolException("Invalid request URI: " + requestLine.getUri(), ex);
            }
        }
        this.z = 0;
    }

    public void w() {
        this.c.clear();
        u0(this.f.v());
    }

    public String getMethod() {
        return this.x;
    }

    public v getProtocolVersion() {
        if (this.y == null) {
            this.y = HttpProtocolParams.getVersion(getParams());
        }
        return this.y;
    }

    public URI t() {
        return this.q;
    }

    public void y(URI uri) {
        this.q = uri;
    }

    public x r() {
        v ver = getProtocolVersion();
        String uritext = null;
        URI uri = this.q;
        if (uri != null) {
            uritext = uri.toASCIIString();
        }
        if (uritext == null || uritext.isEmpty()) {
            uritext = "/";
        }
        return new n(getMethod(), uritext, ver);
    }

    public boolean n() {
        return false;
    }

    public o h() {
        return this.f;
    }

    public boolean q() {
        return true;
    }

    public int f() {
        return this.z;
    }

    public void k() {
        this.z++;
    }
}
