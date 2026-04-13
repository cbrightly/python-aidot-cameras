package org.apache.http.impl.cookie;

import org.apache.http.cookie.i;
import org.apache.http.cookie.j;
import org.apache.http.cookie.k;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: BestMatchSpecFactory */
public class l implements j, k {
    private final i a;

    public l(String[] datepatterns, boolean oneHeader) {
        this.a = new k(datepatterns, oneHeader);
    }

    public l() {
        this((String[]) null, false);
    }

    /* JADX WARNING: type inference failed for: r2v3, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.cookie.i b(org.apache.http.params.HttpParams r5) {
        /*
            r4 = this;
            if (r5 == 0) goto L_0x0027
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
            r2 = 0
            java.lang.String r3 = "http.protocol.single-cookie-header"
            boolean r2 = r5.getBooleanParameter(r3, r2)
            org.apache.http.impl.cookie.k r3 = new org.apache.http.impl.cookie.k
            r3.<init>(r0, r2)
            return r3
        L_0x0027:
            org.apache.http.impl.cookie.k r0 = new org.apache.http.impl.cookie.k
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.cookie.l.b(org.apache.http.params.HttpParams):org.apache.http.cookie.i");
    }

    public i a(f context) {
        return this.a;
    }
}
