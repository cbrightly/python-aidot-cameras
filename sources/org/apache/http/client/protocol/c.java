package org.apache.http.client.protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.config.Lookup;
import org.apache.http.config.b;
import org.apache.http.conn.routing.e;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.k;
import org.apache.http.d;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.protocol.f;
import org.apache.http.util.j;

/* compiled from: RequestAddCookies */
public class c implements p {
    private final a c = h.n(getClass());

    public void b(o request, f context) {
        String policy;
        int i;
        d header;
        org.apache.http.client.config.a config;
        e route;
        o oVar = request;
        f fVar = context;
        org.apache.http.util.a.i(oVar, "HTTP request");
        org.apache.http.util.a.i(fVar, "HTTP context");
        String method = request.r().getMethod();
        if (!method.equalsIgnoreCase("CONNECT")) {
            a clientContext = a.g(context);
            org.apache.http.client.f cookieStore = clientContext.m();
            if (cookieStore == null) {
                this.c.debug("Cookie store not specified in HTTP context");
                return;
            }
            b<k> l = clientContext.l();
            if (l == null) {
                this.c.debug("CookieSpec registry not specified in HTTP context");
                return;
            }
            l targetHost = clientContext.e();
            if (targetHost == null) {
                this.c.debug("Target host not set in the context");
                return;
            }
            e route2 = clientContext.o();
            if (route2 == null) {
                this.c.debug("Connection route not set in the context");
                return;
            }
            org.apache.http.client.config.a config2 = clientContext.s();
            String policy2 = config2.f();
            if (policy2 == null) {
                policy = "default";
            } else {
                policy = policy2;
            }
            if (this.c.isDebugEnabled()) {
                this.c.debug("CookieSpec selected: " + policy);
            }
            URI requestURI = null;
            if (oVar instanceof org.apache.http.client.methods.p) {
                requestURI = ((org.apache.http.client.methods.p) oVar).t();
            } else {
                try {
                    requestURI = new URI(request.r().getUri());
                } catch (URISyntaxException e) {
                }
            }
            String path = requestURI != null ? requestURI.getPath() : null;
            String hostName = targetHost.getHostName();
            int port = targetHost.getPort();
            if (port < 0) {
                port = route2.e().getPort();
            }
            if (port >= 0) {
                String str = method;
                i = port;
            } else {
                String str2 = method;
                i = 0;
            }
            if (!j.c(path)) {
                String str3 = path;
            } else {
                String str4 = path;
                path = "/";
            }
            l lVar = targetHost;
            org.apache.http.cookie.f cookieOrigin = new org.apache.http.cookie.f(hostName, i, path, route2.isSecure());
            k provider = l.lookup(policy);
            if (provider != null) {
                b<k> bVar = l;
                Lookup<CookieSpecProvider> registry = provider.a(clientContext);
                List<org.apache.http.cookie.c> cookies = cookieStore.getCookies();
                List<Cookie> matchedCookies = new ArrayList<>();
                Date now = new Date();
                boolean expired = false;
                for (org.apache.http.cookie.c cookie : cookies) {
                    k provider2 = provider;
                    a clientContext2 = clientContext;
                    Date now2 = now;
                    List<org.apache.http.cookie.c> list = cookies;
                    if (cookie.isExpired(now2)) {
                        route = route2;
                        config = config2;
                        if (this.c.isDebugEnabled()) {
                            this.c.debug("Cookie " + cookie + " expired");
                        }
                        expired = true;
                    } else if (registry.b(cookie, cookieOrigin)) {
                        route = route2;
                        if (this.c.isDebugEnabled()) {
                            config = config2;
                            this.c.debug("Cookie " + cookie + " match " + cookieOrigin);
                        } else {
                            config = config2;
                        }
                        matchedCookies.add(cookie);
                    } else {
                        route = route2;
                        config = config2;
                    }
                    route2 = route;
                    provider = provider2;
                    cookies = list;
                    config2 = config;
                    now = now2;
                    clientContext = clientContext2;
                }
                a aVar = clientContext;
                List<org.apache.http.cookie.c> list2 = cookies;
                org.apache.http.client.config.a aVar2 = config2;
                Date now3 = now;
                e eVar = route2;
                if (expired) {
                    cookieStore.clearExpired(now3);
                }
                if (!matchedCookies.isEmpty()) {
                    for (d header2 : registry.e(matchedCookies)) {
                        oVar.I(header2);
                    }
                }
                if (registry.getVersion() > 0 && (header = registry.d()) != null) {
                    oVar.I(header);
                }
                fVar.setAttribute("http.cookie-spec", registry);
                fVar.setAttribute("http.cookie-origin", cookieOrigin);
            } else if (this.c.isDebugEnabled()) {
                a aVar3 = this.c;
                StringBuilder sb = new StringBuilder();
                b<k> bVar2 = l;
                sb.append("Unsupported cookie policy: ");
                sb.append(policy);
                aVar3.debug(sb.toString());
            } else {
                b<k> bVar3 = l;
            }
        }
    }
}
