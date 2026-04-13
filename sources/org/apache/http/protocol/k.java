package org.apache.http.protocol;

import java.util.List;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.q;
import org.apache.http.s;

/* compiled from: ImmutableHttpProcessor */
public final class k implements h {
    private final p[] c;
    private final s[] d;

    public k(p[] requestInterceptors, s[] responseInterceptors) {
        if (requestInterceptors != null) {
            int l = requestInterceptors.length;
            p[] pVarArr = new p[l];
            this.c = pVarArr;
            System.arraycopy(requestInterceptors, 0, pVarArr, 0, l);
        } else {
            this.c = new p[0];
        }
        if (responseInterceptors != null) {
            int l2 = responseInterceptors.length;
            s[] sVarArr = new s[l2];
            this.d = sVarArr;
            System.arraycopy(responseInterceptors, 0, sVarArr, 0, l2);
            return;
        }
        this.d = new s[0];
    }

    public k(List<p> requestInterceptors, List<s> responseInterceptors) {
        if (requestInterceptors != null) {
            this.c = (p[]) requestInterceptors.toArray(new p[requestInterceptors.size()]);
        } else {
            this.c = new p[0];
        }
        if (responseInterceptors != null) {
            this.d = (s[]) responseInterceptors.toArray(new s[responseInterceptors.size()]);
        } else {
            this.d = new s[0];
        }
    }

    public k(p... requestInterceptors) {
        this(requestInterceptors, (s[]) null);
    }

    public void b(o request, f context) {
        for (p requestInterceptor : this.c) {
            requestInterceptor.b(request, context);
        }
    }

    public void a(q response, f context) {
        for (s responseInterceptor : this.d) {
            responseInterceptor.a(response, context);
        }
    }
}
