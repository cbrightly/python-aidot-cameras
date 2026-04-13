package org.apache.http.protocol;

import org.apache.http.i;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.util.a;

/* compiled from: HttpCoreContext */
public class g implements f {
    private final f c;

    public static g a(f context) {
        a.i(context, "HTTP context");
        if (context instanceof g) {
            return (g) context;
        }
        return new g(context);
    }

    public g(f context) {
        this.c = context;
    }

    public g() {
        this.c = new a();
    }

    public Object getAttribute(String id) {
        return this.c.getAttribute(id);
    }

    public void setAttribute(String id, Object obj) {
        this.c.setAttribute(id, obj);
    }

    public <T> T b(String attribname, Class<T> clazz) {
        a.i(clazz, "Attribute class");
        Object obj = getAttribute(attribname);
        if (obj == null) {
            return null;
        }
        return clazz.cast(obj);
    }

    public i c() {
        return (i) b("http.connection", i.class);
    }

    public o d() {
        return (o) b("http.request", o.class);
    }

    public boolean f() {
        Boolean b = (Boolean) b("http.request_sent", Boolean.class);
        return b != null && b.booleanValue();
    }

    public l e() {
        return (l) b("http.target_host", l.class);
    }
}
