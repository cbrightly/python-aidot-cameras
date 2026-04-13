package org.apache.httpcore.protocol;

import java.util.List;
import org.apache.httpcore.o;
import org.apache.httpcore.p;
import org.apache.httpcore.r;

/* compiled from: ImmutableHttpProcessor */
public final class m implements h {
    private final o[] a;
    private final r[] b;

    public m(List<o> requestInterceptors, List<r> responseInterceptors) {
        if (requestInterceptors != null) {
            this.a = (o[]) requestInterceptors.toArray(new o[requestInterceptors.size()]);
        } else {
            this.a = new o[0];
        }
        if (responseInterceptors != null) {
            this.b = (r[]) responseInterceptors.toArray(new r[responseInterceptors.size()]);
        } else {
            this.b = new r[0];
        }
    }

    public void b(org.apache.httpcore.m request, d context) {
        for (o requestInterceptor : this.a) {
            requestInterceptor.b(request, context);
        }
    }

    public void a(p response, d context) {
        for (r responseInterceptor : this.b) {
            responseInterceptor.a(response, context);
        }
    }
}
