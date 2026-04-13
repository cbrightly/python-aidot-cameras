package org.apache.http.impl.client;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.auth.AuthOption;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.b;
import org.apache.http.client.g;
import org.apache.http.d;
import org.apache.http.l;
import org.apache.http.protocol.f;
import org.apache.http.q;

@Deprecated
/* compiled from: AuthenticationStrategyAdaptor */
public class c implements org.apache.http.client.c {
    private final a a = h.n(getClass());
    private final b b;

    public c(b handler) {
        this.b = handler;
    }

    public boolean e(l authhost, q response, f context) {
        return this.b.c(response, context);
    }

    public Map<String, d> c(l authhost, q response, f context) {
        return this.b.a(response, context);
    }

    public Queue<org.apache.http.auth.a> d(Map<String, d> challenges, l authhost, q response, f context) {
        org.apache.http.util.a.i(challenges, "Map of auth challenges");
        org.apache.http.util.a.i(authhost, "Host");
        org.apache.http.util.a.i(response, "HTTP response");
        org.apache.http.util.a.i(context, "HTTP context");
        Queue<AuthOption> options = new LinkedList<>();
        g credsProvider = (g) context.getAttribute("http.auth.credentials-provider");
        if (credsProvider == null) {
            this.a.debug("Credentials provider not set in the context");
            return options;
        }
        try {
            org.apache.http.auth.c authScheme = this.b.b(challenges, response, context);
            authScheme.processChallenge(challenges.get(authScheme.getSchemeName().toLowerCase(Locale.ROOT)));
            org.apache.http.auth.l credentials = credsProvider.b(new org.apache.http.auth.g(authhost.getHostName(), authhost.getPort(), authScheme.getRealm(), authScheme.getSchemeName()));
            if (credentials != null) {
                options.add(new org.apache.http.auth.a(authScheme, credentials));
            }
            return options;
        } catch (AuthenticationException ex) {
            if (this.a.isWarnEnabled()) {
                this.a.warn(ex.getMessage(), ex);
            }
            return options;
        }
    }

    public void a(l authhost, org.apache.http.auth.c authScheme, f context) {
        org.apache.http.client.a authCache = (org.apache.http.client.a) context.getAttribute("http.auth.auth-cache");
        if (g(authScheme)) {
            if (authCache == null) {
                authCache = new e();
                context.setAttribute("http.auth.auth-cache", authCache);
            }
            if (this.a.isDebugEnabled()) {
                a aVar = this.a;
                aVar.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + authhost);
            }
            authCache.b(authhost, authScheme);
        }
    }

    public void b(l authhost, org.apache.http.auth.c authScheme, f context) {
        org.apache.http.client.a authCache = (org.apache.http.client.a) context.getAttribute("http.auth.auth-cache");
        if (authCache != null) {
            if (this.a.isDebugEnabled()) {
                a aVar = this.a;
                aVar.debug("Removing from cache '" + authScheme.getSchemeName() + "' auth scheme for " + authhost);
            }
            authCache.c(authhost);
        }
    }

    private boolean g(org.apache.http.auth.c authScheme) {
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        String schemeName = authScheme.getSchemeName();
        if (schemeName.equalsIgnoreCase("Basic") || schemeName.equalsIgnoreCase("Digest")) {
            return true;
        }
        return false;
    }

    public b f() {
        return this.b;
    }
}
