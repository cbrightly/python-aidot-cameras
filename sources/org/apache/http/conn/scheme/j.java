package org.apache.http.conn.scheme;

import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.l;
import org.apache.http.util.a;

@Deprecated
/* compiled from: SchemeRegistry */
public final class j {
    private final ConcurrentHashMap<String, f> a = new ConcurrentHashMap<>();

    public final f b(String name) {
        f found = a(name);
        if (found != null) {
            return found;
        }
        throw new IllegalStateException("Scheme '" + name + "' not registered.");
    }

    public final f c(l host) {
        a.i(host, "Host");
        return b(host.getSchemeName());
    }

    public final f a(String name) {
        a.i(name, "Scheme name");
        return this.a.get(name);
    }

    public final f d(f sch) {
        a.i(sch, "Scheme");
        return this.a.put(sch.b(), sch);
    }
}
