package org.apache.http.impl.client;

import java.net.URI;
import org.apache.http.client.i;
import org.apache.http.client.j;
import org.apache.http.client.methods.h;
import org.apache.http.client.methods.p;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.q;
import org.glassfish.grizzly.http.server.Constants;

@Deprecated
/* compiled from: DefaultRedirectStrategyAdaptor */
public class s implements j {
    private final i a;

    public s(i handler) {
        this.a = handler;
    }

    public boolean b(o request, q response, f context) {
        return this.a.isRedirectRequested(response, context);
    }

    public p a(o request, q response, f context) {
        URI uri = this.a.getLocationURI(response, context);
        if (request.r().getMethod().equalsIgnoreCase(Constants.HEAD)) {
            return new org.apache.http.client.methods.i(uri);
        }
        return new h(uri);
    }

    public i c() {
        return this.a;
    }
}
