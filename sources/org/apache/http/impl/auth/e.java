package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import org.apache.http.auth.c;
import org.apache.http.auth.d;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

/* compiled from: DigestSchemeFactory */
public class e implements d, org.apache.http.auth.e {
    private final Charset a;

    public e(Charset charset) {
        this.a = charset;
    }

    public e() {
        this((Charset) null);
    }

    public c b(HttpParams params) {
        return new d();
    }

    public c a(f context) {
        return new d(this.a);
    }
}
