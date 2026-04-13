package org.apache.http.impl.client;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import org.apache.http.auth.c;
import org.apache.http.client.config.a;
import org.apache.http.l;
import org.apache.http.protocol.f;
import org.apache.http.q;

/* compiled from: ProxyAuthenticationStrategy */
public class d0 extends d {
    public static final d0 e = new d0();

    public /* bridge */ /* synthetic */ void a(l x0, c x1, f x2) {
        super.a(x0, x1, x2);
    }

    public /* bridge */ /* synthetic */ void b(l x0, c x1, f x2) {
        super.b(x0, x1, x2);
    }

    public /* bridge */ /* synthetic */ Map c(l x0, q x1, f x2) {
        return super.c(x0, x1, x2);
    }

    public /* bridge */ /* synthetic */ Queue d(Map x0, l x1, q x2, f x3) {
        return super.d(x0, x1, x2, x3);
    }

    public /* bridge */ /* synthetic */ boolean e(l x0, q x1, f x2) {
        return super.e(x0, x1, x2);
    }

    public d0() {
        super(407, "Proxy-Authenticate");
    }

    /* access modifiers changed from: package-private */
    public Collection<String> f(a config) {
        return config.k();
    }
}
