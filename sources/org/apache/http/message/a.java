package org.apache.http.message;

import org.apache.http.d;
import org.apache.http.g;
import org.apache.http.n;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

/* compiled from: AbstractHttpMessage */
public abstract class a implements n {
    protected r c;
    @Deprecated
    protected HttpParams d;

    @Deprecated
    protected a(HttpParams params) {
        this.c = new r();
        this.d = params;
    }

    protected a() {
        this((HttpParams) null);
    }

    public boolean containsHeader(String name) {
        return this.c.containsHeader(name);
    }

    public d[] c(String name) {
        return this.c.getHeaders(name);
    }

    public d u(String name) {
        return this.c.getFirstHeader(name);
    }

    public d[] v() {
        return this.c.getAllHeaders();
    }

    public void I(d header) {
        this.c.addHeader(header);
    }

    public void addHeader(String name, String value) {
        org.apache.http.util.a.i(name, "Header name");
        this.c.addHeader(new b(name, value));
    }

    public void setHeader(String name, String value) {
        org.apache.http.util.a.i(name, "Header name");
        this.c.updateHeader(new b(name, value));
    }

    public void u0(d[] headers) {
        this.c.setHeaders(headers);
    }

    public void s(String name) {
        if (name != null) {
            g i = this.c.iterator();
            while (i.hasNext()) {
                if (name.equalsIgnoreCase(i.a().getName())) {
                    i.remove();
                }
            }
        }
    }

    public g i() {
        return this.c.iterator();
    }

    public g o(String name) {
        return this.c.iterator(name);
    }

    @Deprecated
    public HttpParams getParams() {
        if (this.d == null) {
            this.d = new BasicHttpParams();
        }
        return this.d;
    }

    @Deprecated
    public void z(HttpParams params) {
        this.d = (HttpParams) org.apache.http.util.a.i(params, "HTTP parameters");
    }
}
