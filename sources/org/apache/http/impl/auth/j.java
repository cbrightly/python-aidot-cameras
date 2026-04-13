package org.apache.http.impl.auth;

import org.apache.http.auth.c;
import org.apache.http.auth.d;
import org.apache.http.auth.e;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

/* compiled from: KerberosSchemeFactory */
public class j implements d, e {
    private final boolean a;
    private final boolean b;

    public j(boolean stripPort, boolean useCanonicalHostname) {
        this.a = stripPort;
        this.b = useCanonicalHostname;
    }

    public j() {
        this(true, true);
    }

    public c b(HttpParams params) {
        return new i(this.a, this.b);
    }

    public c a(f context) {
        return new i(this.a, this.b);
    }
}
