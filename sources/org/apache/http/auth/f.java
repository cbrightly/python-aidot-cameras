package org.apache.http.auth;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.config.b;
import org.apache.http.o;
import org.apache.http.params.HttpParams;

@Deprecated
/* compiled from: AuthSchemeRegistry */
public final class f implements b<e> {
    private final ConcurrentHashMap<String, d> a = new ConcurrentHashMap<>();

    public void c(String name, d factory) {
        org.apache.http.util.a.i(name, "Name");
        org.apache.http.util.a.i(factory, "Authentication scheme factory");
        this.a.put(name.toLowerCase(Locale.ENGLISH), factory);
    }

    public c a(String name, HttpParams params) {
        org.apache.http.util.a.i(name, "Name");
        d factory = this.a.get(name.toLowerCase(Locale.ENGLISH));
        if (factory != null) {
            return factory.b(params);
        }
        throw new IllegalStateException("Unsupported authentication scheme: " + name);
    }

    /* compiled from: AuthSchemeRegistry */
    public class a implements e {
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        public c a(org.apache.http.protocol.f context) {
            return f.this.a(this.a, ((o) context.getAttribute("http.request")).getParams());
        }
    }

    /* renamed from: b */
    public e lookup(String name) {
        return new a(name);
    }
}
