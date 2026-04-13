package org.apache.http.impl.execchain;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.HttpException;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectException;
import org.apache.http.client.j;
import org.apache.http.client.methods.c;
import org.apache.http.client.methods.n;
import org.apache.http.client.methods.p;
import org.apache.http.conn.routing.b;
import org.apache.http.conn.routing.d;
import org.apache.http.k;
import org.apache.http.l;
import org.apache.http.o;

/* compiled from: RedirectExec */
public class g implements b {
    private final a a = h.n(getClass());
    private final b b;
    private final j c;
    private final d d;

    public g(b requestExecutor, d routePlanner, j redirectStrategy) {
        org.apache.http.util.a.i(requestExecutor, "HTTP client request executor");
        org.apache.http.util.a.i(routePlanner, "HTTP route planner");
        org.apache.http.util.a.i(redirectStrategy, "HTTP redirect strategy");
        this.b = requestExecutor;
        this.d = routePlanner;
        this.c = redirectStrategy;
    }

    public c a(b route, n request, org.apache.http.client.protocol.a context, org.apache.http.client.methods.g execAware) {
        c response;
        org.apache.http.client.protocol.a aVar = context;
        org.apache.http.util.a.i(route, "HTTP route");
        org.apache.http.util.a.i(request, "HTTP request");
        org.apache.http.util.a.i(aVar, "HTTP context");
        List<URI> redirectLocations = context.r();
        if (redirectLocations != null) {
            redirectLocations.clear();
        }
        org.apache.http.client.config.a config = context.s();
        int maxRedirects = config.i() > 0 ? config.i() : 50;
        int redirectCount = 0;
        n currentRequest = request;
        b currentRoute = route;
        while (true) {
            response = this.b.a(currentRoute, currentRequest, aVar, execAware);
            try {
                if (!config.s() || !this.c.b(currentRequest.f(), response, aVar)) {
                    return response;
                }
                if (redirectCount < maxRedirects) {
                    redirectCount++;
                    o a2 = this.c.a(currentRequest.f(), response, aVar);
                    if (!a2.i().hasNext()) {
                        a2.u0(request.f().v());
                    }
                    currentRequest = n.q(a2);
                    if (currentRequest instanceof k) {
                        h.a((k) currentRequest);
                    }
                    URI uri = currentRequest.t();
                    l newTarget = org.apache.http.client.utils.d.a(uri);
                    if (newTarget != null) {
                        if (!currentRoute.e().equals(newTarget)) {
                            org.apache.http.auth.h targetAuthState = context.t();
                            if (targetAuthState != null) {
                                p pVar = a2;
                                this.a.debug("Resetting target auth state");
                                targetAuthState.e();
                            } else {
                                o redirect = a2;
                            }
                            org.apache.http.auth.h proxyAuthState = context.q();
                            if (proxyAuthState != null) {
                                org.apache.http.auth.c authScheme = proxyAuthState.b();
                                if (authScheme == null || !authScheme.isConnectionBased()) {
                                } else {
                                    org.apache.http.auth.c cVar = authScheme;
                                    this.a.debug("Resetting proxy auth state");
                                    proxyAuthState.e();
                                }
                            }
                        } else {
                            o redirect2 = a2;
                        }
                        currentRoute = this.d.a(newTarget, currentRequest, aVar);
                        if (this.a.isDebugEnabled()) {
                            this.a.debug("Redirecting to '" + uri + "' via " + currentRoute);
                        }
                        org.apache.http.util.g.a(response.a());
                        response.close();
                        b bVar = route;
                        n nVar = request;
                    } else {
                        o oVar = a2;
                        throw new ProtocolException("Redirect URI does not specify a valid host name: " + uri);
                    }
                } else {
                    throw new RedirectException("Maximum redirects (" + maxRedirects + ") exceeded");
                }
            } catch (RuntimeException ex) {
                response.close();
                throw ex;
            } catch (IOException ex2) {
                response.close();
                throw ex2;
            } catch (HttpException e) {
                HttpException ex3 = e;
                try {
                    org.apache.http.util.g.a(response.a());
                } catch (IOException ioex) {
                    this.a.debug("I/O error while releasing connection", ioex);
                } catch (Throwable th) {
                    response.close();
                    throw th;
                }
                response.close();
                throw ex3;
            }
        }
        return response;
    }
}
