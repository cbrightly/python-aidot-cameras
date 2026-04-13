package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import org.apache.http.auth.d;
import org.apache.http.auth.e;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;

/* compiled from: BasicSchemeFactory */
public class c implements d, e {
    private final Charset a;

    public c(Charset charset) {
        this.a = charset;
    }

    public c() {
        this((Charset) null);
    }

    public org.apache.http.auth.c b(HttpParams params) {
        return new b();
    }

    public org.apache.http.auth.c a(f context) {
        return new b(this.a);
    }
}
