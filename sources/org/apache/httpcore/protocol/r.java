package org.apache.httpcore.protocol;

import org.apache.httpcore.m;
import org.apache.httpcore.util.a;

/* compiled from: UriHttpRequestHandlerMapper */
public class r implements k {
    private final s<j> a;

    /* JADX WARNING: type inference failed for: r2v0, types: [org.apache.httpcore.protocol.s<org.apache.httpcore.protocol.j>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected r(org.apache.httpcore.protocol.s<org.apache.httpcore.protocol.j> r2) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.String r0 = "Pattern matcher"
            java.lang.Object r0 = org.apache.httpcore.util.a.g(r2, r0)
            org.apache.httpcore.protocol.s r0 = (org.apache.httpcore.protocol.s) r0
            r1.a = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.httpcore.protocol.r.<init>(org.apache.httpcore.protocol.s):void");
    }

    public r() {
        this(new s());
    }

    public void c(String pattern, j handler) {
        a.g(pattern, "Pattern");
        a.g(handler, "Handler");
        this.a.c(pattern, handler);
    }

    /* access modifiers changed from: protected */
    public String b(m request) {
        String uriPath = request.r().getUri();
        int index = uriPath.indexOf(63);
        if (index != -1) {
            return uriPath.substring(0, index);
        }
        int index2 = uriPath.indexOf(35);
        if (index2 != -1) {
            return uriPath.substring(0, index2);
        }
        return uriPath;
    }

    public j a(m request) {
        a.g(request, "HTTP request");
        return this.a.a(b(request));
    }
}
