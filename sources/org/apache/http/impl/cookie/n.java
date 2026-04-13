package org.apache.http.impl.cookie;

import org.apache.http.cookie.i;
import org.apache.http.cookie.j;
import org.apache.http.cookie.k;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: BrowserCompatSpecFactory */
public class n implements j, k {
    private final a a;
    private final i b;

    /* compiled from: BrowserCompatSpecFactory */
    public enum a {
        SECURITYLEVEL_DEFAULT,
        SECURITYLEVEL_IE_MEDIUM
    }

    public n(String[] datepatterns, a securityLevel) {
        this.a = securityLevel;
        this.b = new m(datepatterns, securityLevel);
    }

    public n() {
        this((String[]) null, a.SECURITYLEVEL_DEFAULT);
    }

    /* JADX WARNING: type inference failed for: r2v3, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.cookie.i b(org.apache.http.params.HttpParams r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0022
            r0 = 0
            java.lang.String r1 = "http.protocol.cookie-datepatterns"
            java.lang.Object r1 = r5.getParameter(r1)
            java.util.Collection r1 = (java.util.Collection) r1
            if (r1 == 0) goto L_0x001a
            int r2 = r1.size()
            java.lang.String[] r0 = new java.lang.String[r2]
            java.lang.Object[] r2 = r1.toArray(r0)
            r0 = r2
            java.lang.String[] r0 = (java.lang.String[]) r0
        L_0x001a:
            org.apache.http.impl.cookie.m r2 = new org.apache.http.impl.cookie.m
            org.apache.http.impl.cookie.n$a r3 = r4.a
            r2.<init>(r0, r3)
            return r2
        L_0x0022:
            org.apache.http.impl.cookie.m r0 = new org.apache.http.impl.cookie.m
            r1 = 0
            org.apache.http.impl.cookie.n$a r2 = r4.a
            r0.<init>(r1, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.cookie.n.b(org.apache.http.params.HttpParams):org.apache.http.cookie.i");
    }

    public i a(f context) {
        return this.b;
    }
}
