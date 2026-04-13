package org.apache.http.client.protocol;

import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.conn.routing.e;
import org.apache.http.o;
import org.apache.http.p;

/* compiled from: RequestClientConnControl */
public class f implements p {
    private final a c = h.n(getClass());

    public void b(o request, org.apache.http.protocol.f context) {
        org.apache.http.util.a.i(request, "HTTP request");
        if (request.r().getMethod().equalsIgnoreCase("CONNECT")) {
            request.setHeader("Proxy-Connection", "Keep-Alive");
            return;
        }
        e route = a.g(context).o();
        if (route == null) {
            this.c.debug("Connection route not set in the context");
            return;
        }
        if ((route.a() == 1 || route.b()) && !request.containsHeader("Connection")) {
            request.addHeader("Connection", "Keep-Alive");
        }
        if (route.a() == 2 && !route.b() && !request.containsHeader("Proxy-Connection")) {
            request.addHeader("Proxy-Connection", "Keep-Alive");
        }
    }
}
