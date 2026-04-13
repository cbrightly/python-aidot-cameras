package org.apache.http.message;

import org.apache.http.o;
import org.apache.http.t;
import org.apache.http.util.a;
import org.apache.http.v;
import org.apache.http.x;

/* compiled from: BasicHttpRequest */
public class h extends a implements o {
    private final String f;
    private final String q;
    private x x;

    public h(String method, String uri, v ver) {
        this(new n(method, uri, ver));
    }

    public h(x requestline) {
        this.x = (x) a.i(requestline, "Request line");
        this.f = requestline.getMethod();
        this.q = requestline.getUri();
    }

    public v getProtocolVersion() {
        return r().getProtocolVersion();
    }

    public x r() {
        if (this.x == null) {
            this.x = new n(this.f, this.q, t.HTTP_1_1);
        }
        return this.x;
    }

    public String toString() {
        return this.f + ' ' + this.q + ' ' + this.c;
    }
}
