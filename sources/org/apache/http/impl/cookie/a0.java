package org.apache.http.impl.cookie;

import org.apache.http.cookie.i;
import org.apache.http.cookie.j;
import org.apache.http.cookie.k;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: NetscapeDraftSpecFactory */
public class a0 implements j, k {
    private final i a;

    public a0(String[] datepatterns) {
        this.a = new z(datepatterns);
    }

    public a0() {
        this((String[]) null);
    }

    /* JADX WARNING: type inference failed for: r2v2, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.cookie.i b(org.apache.http.params.HttpParams r4) {
        /*
            r3 = this;
            if (r4 == 0) goto L_0x0020
            r0 = 0
            java.lang.String r1 = "http.protocol.cookie-datepatterns"
            java.lang.Object r1 = r4.getParameter(r1)
            java.util.Collection r1 = (java.util.Collection) r1
            if (r1 == 0) goto L_0x001a
            int r2 = r1.size()
            java.lang.String[] r0 = new java.lang.String[r2]
            java.lang.Object[] r2 = r1.toArray(r0)
            r0 = r2
            java.lang.String[] r0 = (java.lang.String[]) r0
        L_0x001a:
            org.apache.http.impl.cookie.z r2 = new org.apache.http.impl.cookie.z
            r2.<init>((java.lang.String[]) r0)
            return r2
        L_0x0020:
            org.apache.http.impl.cookie.z r0 = new org.apache.http.impl.cookie.z
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.cookie.a0.b(org.apache.http.params.HttpParams):org.apache.http.cookie.i");
    }

    public i a(f context) {
        return this.a;
    }
}
