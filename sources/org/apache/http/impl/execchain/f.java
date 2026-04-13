package org.apache.http.impl.execchain;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.HttpException;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.c;
import org.apache.http.client.methods.g;
import org.apache.http.client.methods.n;
import org.apache.http.client.methods.p;
import org.apache.http.client.utils.d;
import org.apache.http.conn.routing.b;
import org.apache.http.l;
import org.apache.http.o;

/* compiled from: ProtocolExec */
public class f implements b {
    private final a a = h.n(getClass());
    private final b b;
    private final org.apache.http.protocol.h c;

    public f(b requestExecutor, org.apache.http.protocol.h httpProcessor) {
        org.apache.http.util.a.i(requestExecutor, "HTTP client request executor");
        org.apache.http.util.a.i(httpProcessor, "HTTP protocol processor");
        this.b = requestExecutor;
        this.c = httpProcessor;
    }

    /* access modifiers changed from: package-private */
    public void b(n request, b route) {
        URI uri = request.t();
        if (uri != null) {
            try {
                request.k(d.f(uri, route));
            } catch (URISyntaxException ex) {
                throw new ProtocolException("Invalid URI: " + uri, ex);
            }
        }
    }

    public c a(b route, n request, org.apache.http.client.protocol.a context, g execAware) {
        String userinfo;
        org.apache.http.util.a.i(route, "HTTP route");
        org.apache.http.util.a.i(request, "HTTP request");
        org.apache.http.util.a.i(context, "HTTP context");
        o original = request.f();
        URI uri = null;
        if (original instanceof p) {
            uri = ((p) original).t();
        } else {
            String uriString = original.r().getUri();
            try {
                uri = URI.create(uriString);
            } catch (IllegalArgumentException ex) {
                if (this.a.isDebugEnabled()) {
                    a aVar = this.a;
                    aVar.debug("Unable to parse '" + uriString + "' as a valid URI; " + "request URI and Host header may be inconsistent", ex);
                }
            }
        }
        request.k(uri);
        b(request, route);
        l virtualHost = (l) request.getParams().getParameter("http.virtual-host");
        if (virtualHost != null && virtualHost.getPort() == -1) {
            int port = route.e().getPort();
            if (port != -1) {
                virtualHost = new l(virtualHost.getHostName(), port, virtualHost.getSchemeName());
            }
            if (this.a.isDebugEnabled()) {
                a aVar2 = this.a;
                aVar2.debug("Using virtual host" + virtualHost);
            }
        }
        l target = null;
        if (virtualHost != null) {
            target = virtualHost;
        } else if (!(uri == null || !uri.isAbsolute() || uri.getHost() == null)) {
            target = new l(uri.getHost(), uri.getPort(), uri.getScheme());
        }
        if (target == null) {
            target = request.h();
        }
        if (target == null) {
            target = route.e();
        }
        if (!(uri == null || (userinfo = uri.getUserInfo()) == null)) {
            org.apache.http.client.g credsProvider = context.n();
            if (credsProvider == null) {
                credsProvider = new org.apache.http.impl.client.g();
                context.w(credsProvider);
            }
            credsProvider.a(new org.apache.http.auth.g(target), new org.apache.http.auth.p(userinfo));
        }
        context.setAttribute("http.target_host", target);
        context.setAttribute("http.route", route);
        context.setAttribute("http.request", request);
        this.c.b(request, context);
        c response = this.b.a(route, request, context, execAware);
        try {
            context.setAttribute("http.response", response);
            this.c.a(response, context);
            return response;
        } catch (RuntimeException ex2) {
            response.close();
            throw ex2;
        } catch (IOException ex3) {
            response.close();
            throw ex3;
        } catch (HttpException ex4) {
            response.close();
            throw ex4;
        }
    }
}
