package org.apache.http.cookie;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.config.b;
import org.apache.http.o;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: CookieSpecRegistry */
public final class l implements b<k> {
    private final ConcurrentHashMap<String, j> a = new ConcurrentHashMap<>();

    public void c(String name, j factory) {
        org.apache.http.util.a.i(name, "Name");
        org.apache.http.util.a.i(factory, "Cookie spec factory");
        this.a.put(name.toLowerCase(Locale.ENGLISH), factory);
    }

    public i a(String name, HttpParams params) {
        org.apache.http.util.a.i(name, "Name");
        j factory = this.a.get(name.toLowerCase(Locale.ENGLISH));
        if (factory != null) {
            return factory.b(params);
        }
        throw new IllegalStateException("Unsupported cookie spec: " + name);
    }

    /* compiled from: CookieSpecRegistry */
    public class a implements k {
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        public i a(f context) {
            return l.this.a(this.a, ((o) context.getAttribute("http.request")).getParams());
        }
    }

    /* renamed from: b */
    public k lookup(String name) {
        return new a(name);
    }
}
