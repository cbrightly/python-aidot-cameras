package org.apache.http.impl.auth;

import org.apache.http.auth.c;
import org.apache.http.auth.d;
import org.apache.http.auth.e;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

/* compiled from: SPNegoSchemeFactory */
public class q implements d, e {
    private final boolean a;
    private final boolean b;

    public q(boolean stripPort, boolean useCanonicalHostname) {
        this.a = stripPort;
        this.b = useCanonicalHostname;
    }

    public q() {
        this(true, true);
    }

    public c b(HttpParams params) {
        return new p(this.a, this.b);
    }

    public c a(f context) {
        return new p(this.a, this.b);
    }
}
