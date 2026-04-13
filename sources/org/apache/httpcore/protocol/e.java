package org.apache.httpcore.protocol;

import org.apache.httpcore.m;
import org.apache.httpcore.util.a;

/* compiled from: HttpCoreContext */
public class e implements d {
    private final d a;

    public static e a(d context) {
        a.g(context, "HTTP context");
        return context instanceof e ? (e) context : new e(context);
    }

    public e(d context) {
        this.a = context;
    }

    public e() {
        this.a = new a();
    }

    public Object getAttribute(String id) {
        return this.a.getAttribute(id);
    }

    public void setAttribute(String id, Object obj) {
        this.a.setAttribute(id, obj);
    }

    public <T> T b(String attribname, Class<T> clazz) {
        a.g(clazz, "Attribute class");
        Object obj = getAttribute(attribname);
        if (obj == null) {
            return null;
        }
        return clazz.cast(obj);
    }

    public m c() {
        return (m) b("http.request", m.class);
    }
}
