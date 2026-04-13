package org.apache.http.impl.conn;

import org.apache.http.conn.scheme.e;
import org.apache.http.conn.scheme.f;
import org.apache.http.conn.scheme.j;
import org.apache.http.conn.scheme.k;
import org.apache.http.conn.ssl.g;
import org.apache.http.l;

@Deprecated
/* compiled from: SchemeRegistryFactory */
public final class b0 {
    public static j a() {
        j registry = new j();
        registry.d(new f(l.DEFAULT_SCHEME_NAME, 80, (k) e.h()));
        registry.d(new f("https", 443, (k) g.l()));
        return registry;
    }
}
