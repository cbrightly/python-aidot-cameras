package org.apache.http.impl.cookie;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.cookie.d;
import org.apache.http.cookie.i;

/* compiled from: AbstractCookieSpec */
public abstract class b implements i {
    private final Map<String, d> a;

    public b() {
        this.a = new ConcurrentHashMap(10);
    }

    protected b(org.apache.http.cookie.b... handlers) {
        this.a = new ConcurrentHashMap(handlers.length);
        for (org.apache.http.cookie.b handler : handlers) {
            this.a.put(handler.c(), handler);
        }
    }

    /* access modifiers changed from: protected */
    public d f(String name) {
        return this.a.get(name);
    }

    /* access modifiers changed from: protected */
    public Collection<d> g() {
        return this.a.values();
    }
}
