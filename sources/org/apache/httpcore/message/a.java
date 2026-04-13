package org.apache.httpcore.message;

import org.apache.httpcore.e;
import org.apache.httpcore.g;
import org.apache.httpcore.l;
import org.apache.httpcore.params.HttpParams;

/* compiled from: AbstractHttpMessage */
public abstract class a implements l {
    protected q a;
    @Deprecated
    protected HttpParams b;

    @Deprecated
    protected a(HttpParams params) {
        this.a = new q();
        this.b = params;
    }

    protected a() {
        this((HttpParams) null);
    }

    public boolean containsHeader(String name) {
        return this.a.containsHeader(name);
    }

    public e[] c(String name) {
        return this.a.getHeaders(name);
    }

    public e u(String name) {
        return this.a.getFirstHeader(name);
    }

    public e[] v() {
        return this.a.getAllHeaders();
    }

    public void f(e header) {
        this.a.addHeader(header);
    }

    public void addHeader(String name, String value) {
        org.apache.httpcore.util.a.g(name, "Header name");
        this.a.addHeader(new b(name, value));
    }

    public void setHeader(String name, String value) {
        org.apache.httpcore.util.a.g(name, "Header name");
        this.a.updateHeader(new b(name, value));
    }

    public void d(e[] headers) {
        this.a.setHeaders(headers);
    }

    public void s(String name) {
        if (name != null) {
            g i = this.a.iterator();
            while (i.hasNext()) {
                if (name.equalsIgnoreCase(i.a().getName())) {
                    i.remove();
                }
            }
        }
    }

    public g i() {
        return this.a.iterator();
    }

    public g o(String name) {
        return this.a.iterator(name);
    }
}
