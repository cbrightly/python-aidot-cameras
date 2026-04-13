package org.apache.http.client.protocol;

import org.apache.http.auth.h;
import org.apache.http.conn.p;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.util.a;

@Deprecated
/* compiled from: RequestProxyAuthentication */
public class i extends e {
    public void b(o request, f context) {
        a.i(request, "HTTP request");
        a.i(context, "HTTP context");
        if (!request.containsHeader("Proxy-Authorization")) {
            p conn = (p) context.getAttribute("http.connection");
            if (conn == null) {
                this.c.debug("HTTP connection not set in the context");
            } else if (!conn.e().b()) {
                h authState = (h) context.getAttribute("http.auth.proxy-scope");
                if (authState == null) {
                    this.c.debug("Proxy auth state not set in the context");
                    return;
                }
                if (this.c.isDebugEnabled()) {
                    org.apache.commons.logging.a aVar = this.c;
                    aVar.debug("Proxy auth state: " + authState.d());
                }
                d(authState, request, context);
            }
        }
    }
}
