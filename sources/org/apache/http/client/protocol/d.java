package org.apache.http.client.protocol;

import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.auth.b;
import org.apache.http.auth.c;
import org.apache.http.client.g;
import org.apache.http.conn.routing.e;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.protocol.f;

/* compiled from: RequestAuthCache */
public class d implements p {
    private final a c = h.n(getClass());

    public void b(o request, f context) {
        c authScheme;
        c authScheme2;
        org.apache.http.util.a.i(request, "HTTP request");
        org.apache.http.util.a.i(context, "HTTP context");
        a clientContext = a.g(context);
        org.apache.http.client.a authCache = clientContext.h();
        if (authCache == null) {
            this.c.debug("Auth cache not set in the context");
            return;
        }
        g credsProvider = clientContext.n();
        if (credsProvider == null) {
            this.c.debug("Credentials provider not set in the context");
            return;
        }
        e route = clientContext.o();
        if (route == null) {
            this.c.debug("Route info not set in the context");
            return;
        }
        l target = clientContext.e();
        if (target == null) {
            this.c.debug("Target host not set in the context");
            return;
        }
        if (target.getPort() < 0) {
            target = new l(target.getHostName(), route.e().getPort(), target.getSchemeName());
        }
        org.apache.http.auth.h targetState = clientContext.t();
        if (!(targetState == null || targetState.d() != b.UNCHALLENGED || (authScheme2 = authCache.a(target)) == null)) {
            a(target, authScheme2, targetState, credsProvider);
        }
        l proxy = route.c();
        org.apache.http.auth.h proxyState = clientContext.q();
        if (proxy != null && proxyState != null && proxyState.d() == b.UNCHALLENGED && (authScheme = authCache.a(proxy)) != null) {
            a(proxy, authScheme, proxyState, credsProvider);
        }
    }

    private void a(l host, c authScheme, org.apache.http.auth.h authState, g credsProvider) {
        String schemeName = authScheme.getSchemeName();
        if (this.c.isDebugEnabled()) {
            a aVar = this.c;
            aVar.debug("Re-using cached '" + schemeName + "' auth scheme for " + host);
        }
        org.apache.http.auth.l creds = credsProvider.b(new org.apache.http.auth.g(host, org.apache.http.auth.g.b, schemeName));
        if (creds != null) {
            if ("BASIC".equalsIgnoreCase(authScheme.getSchemeName())) {
                authState.f(b.CHALLENGED);
            } else {
                authState.f(b.SUCCESS);
            }
            authState.h(authScheme, creds);
            return;
        }
        this.c.debug("No credentials for preemptive authentication");
    }
}
