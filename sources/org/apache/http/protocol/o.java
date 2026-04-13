package org.apache.http.protocol;

import org.apache.http.p;

/* compiled from: RequestUserAgent */
public class o implements p {
    private final String c;

    public o(String userAgent) {
        this.c = userAgent;
    }

    public o() {
        this((String) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(org.apache.http.o r5, org.apache.http.protocol.f r6) {
        /*
            r4 = this;
            java.lang.String r0 = "HTTP request"
            org.apache.http.util.a.i(r5, r0)
            java.lang.String r0 = "User-Agent"
            boolean r1 = r5.containsHeader(r0)
            if (r1 != 0) goto L_0x0026
            r1 = 0
            org.apache.http.params.HttpParams r2 = r5.getParams()
            if (r2 == 0) goto L_0x001d
            java.lang.String r3 = "http.useragent"
            java.lang.Object r3 = r2.getParameter(r3)
            r1 = r3
            java.lang.String r1 = (java.lang.String) r1
        L_0x001d:
            if (r1 != 0) goto L_0x0021
            java.lang.String r1 = r4.c
        L_0x0021:
            if (r1 == 0) goto L_0x0026
            r5.addHeader(r0, r1)
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.protocol.o.b(org.apache.http.o, org.apache.http.protocol.f):void");
    }
}
