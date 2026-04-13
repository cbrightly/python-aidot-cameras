package org.apache.http.impl.client;

import org.apache.http.config.b;
import org.apache.http.config.e;
import org.apache.http.impl.cookie.b0;
import org.apache.http.impl.cookie.q0;
import org.apache.http.impl.cookie.r;
import org.apache.http.impl.cookie.u;

/* compiled from: CookieSpecRegistries */
public final class k {
    public static e<org.apache.http.cookie.k> b(org.apache.http.conn.util.e publicSuffixMatcher) {
        org.apache.http.cookie.k defaultProvider = new r(publicSuffixMatcher);
        org.apache.http.cookie.k laxStandardProvider = new q0(q0.c.RELAXED, publicSuffixMatcher);
        return e.b().c("default", defaultProvider).c("best-match", defaultProvider).c("compatibility", defaultProvider).c("standard", laxStandardProvider).c("standard-strict", new q0(q0.c.STRICT, publicSuffixMatcher)).c("netscape", new b0()).c("ignoreCookies", new u());
    }

    public static b<org.apache.http.cookie.k> a(org.apache.http.conn.util.e publicSuffixMatcher) {
        return b(publicSuffixMatcher).a();
    }
}
