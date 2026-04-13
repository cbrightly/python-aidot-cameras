package org.apache.http.client.protocol;

import org.apache.http.auth.h;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.util.a;

@Deprecated
/* compiled from: RequestTargetAuthentication */
public class j extends e {
    public void b(o request, f context) {
        a.i(request, "HTTP request");
        a.i(context, "HTTP context");
        if (!request.r().getMethod().equalsIgnoreCase("CONNECT") && !request.containsHeader("Authorization")) {
            h authState = (h) context.getAttribute("http.auth.target-scope");
            if (authState == null) {
                this.c.debug("Target auth state not set in the context");
                return;
            }
            if (this.c.isDebugEnabled()) {
                org.apache.commons.logging.a aVar = this.c;
                aVar.debug("Target auth state: " + authState.d());
            }
            d(authState, request, context);
        }
    }
}
