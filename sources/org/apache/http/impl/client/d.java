package org.apache.http.impl.client;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.Header;
import org.apache.http.auth.AuthOption;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.c;
import org.apache.http.client.g;
import org.apache.http.config.Lookup;
import org.apache.http.l;
import org.apache.http.protocol.e;
import org.apache.http.protocol.f;
import org.apache.http.q;

/* compiled from: AuthenticationStrategyImpl */
public abstract class d implements c {
    private static final List<String> a = Collections.unmodifiableList(Arrays.asList(new String[]{"Negotiate", "Kerberos", "NTLM", "Digest", "Basic"}));
    private final a b = h.n(getClass());
    private final int c;
    private final String d;

    /* access modifiers changed from: package-private */
    public abstract Collection<String> f(org.apache.http.client.config.a aVar);

    d(int challengeCode, String headerName) {
        this.c = challengeCode;
        this.d = headerName;
    }

    public boolean e(l authhost, q response, f context) {
        org.apache.http.util.a.i(response, "HTTP response");
        return response.j().getStatusCode() == this.c;
    }

    public Map<String, org.apache.http.d> c(l authhost, q response, f context) {
        int pos;
        org.apache.http.util.d buffer;
        org.apache.http.util.a.i(response, "HTTP response");
        org.apache.http.d[] headers = response.c(this.d);
        Map<String, Header> map = new HashMap<>(headers.length);
        for (org.apache.http.d header : headers) {
            if (header instanceof org.apache.http.c) {
                buffer = ((org.apache.http.c) header).getBuffer();
                pos = ((org.apache.http.c) header).getValuePos();
            } else {
                String s = header.getValue();
                if (s != null) {
                    org.apache.http.util.d buffer2 = new org.apache.http.util.d(s.length());
                    buffer2.append(s);
                    buffer = buffer2;
                    pos = 0;
                } else {
                    throw new MalformedChallengeException("Header value is null");
                }
            }
            while (pos < buffer.length() && e.a(buffer.charAt(pos))) {
                pos++;
            }
            int beginIndex = pos;
            while (pos < buffer.length() && !e.a(buffer.charAt(pos))) {
                pos++;
            }
            map.put(buffer.substring(beginIndex, pos).toLowerCase(Locale.ROOT), header);
        }
        return map;
    }

    public Queue<org.apache.http.auth.a> d(Map<String, org.apache.http.d> challenges, l authhost, q response, f context) {
        org.apache.http.client.protocol.a clientContext;
        Map<String, org.apache.http.d> map = challenges;
        f fVar = context;
        org.apache.http.util.a.i(map, "Map of auth challenges");
        org.apache.http.util.a.i(authhost, "Host");
        org.apache.http.util.a.i(response, "HTTP response");
        org.apache.http.util.a.i(fVar, "HTTP context");
        org.apache.http.client.protocol.a clientContext2 = org.apache.http.client.protocol.a.g(context);
        Queue<AuthOption> options = new LinkedList<>();
        Lookup<AuthSchemeProvider> registry = clientContext2.i();
        if (registry == null) {
            this.b.debug("Auth scheme registry not set in the context");
            return options;
        }
        g credsProvider = clientContext2.n();
        if (credsProvider == null) {
            this.b.debug("Credentials provider not set in the context");
            return options;
        }
        Collection<String> authPrefs = f(clientContext2.s());
        if (authPrefs == null) {
            authPrefs = a;
        }
        if (this.b.isDebugEnabled()) {
            a aVar = this.b;
            aVar.debug("Authentication schemes in the order of preference: " + authPrefs);
        }
        for (String id : authPrefs) {
            org.apache.http.d challenge = map.get(id.toLowerCase(Locale.ROOT));
            if (challenge != null) {
                org.apache.http.auth.e authSchemeProvider = registry.lookup(id);
                if (authSchemeProvider != null) {
                    clientContext = clientContext2;
                    org.apache.http.auth.c authScheme = authSchemeProvider.a(fVar);
                    authScheme.processChallenge(challenge);
                    org.apache.http.auth.l credentials = credsProvider.b(new org.apache.http.auth.g(authhost.getHostName(), authhost.getPort(), authScheme.getRealm(), authScheme.getSchemeName()));
                    if (credentials != null) {
                        options.add(new org.apache.http.auth.a(authScheme, credentials));
                    }
                } else if (this.b.isWarnEnabled()) {
                    a aVar2 = this.b;
                    aVar2.warn("Authentication scheme " + id + " not supported");
                    map = challenges;
                    clientContext2 = clientContext2;
                } else {
                    map = challenges;
                }
            } else {
                clientContext = clientContext2;
                if (this.b.isDebugEnabled()) {
                    a aVar3 = this.b;
                    aVar3.debug("Challenge for " + id + " authentication scheme not available");
                }
            }
            map = challenges;
            l lVar = authhost;
            q qVar = response;
            fVar = context;
            clientContext2 = clientContext;
        }
        return options;
    }

    public void a(l authhost, org.apache.http.auth.c authScheme, f context) {
        org.apache.http.util.a.i(authhost, "Host");
        org.apache.http.util.a.i(authScheme, "Auth scheme");
        org.apache.http.util.a.i(context, "HTTP context");
        org.apache.http.client.protocol.a clientContext = org.apache.http.client.protocol.a.g(context);
        if (g(authScheme)) {
            org.apache.http.client.a authCache = clientContext.h();
            if (authCache == null) {
                authCache = new e();
                clientContext.v(authCache);
            }
            if (this.b.isDebugEnabled()) {
                a aVar = this.b;
                aVar.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + authhost);
            }
            authCache.b(authhost, authScheme);
        }
    }

    /* access modifiers changed from: protected */
    public boolean g(org.apache.http.auth.c authScheme) {
        if (authScheme == null || !authScheme.isComplete()) {
            return false;
        }
        String schemeName = authScheme.getSchemeName();
        if (schemeName.equalsIgnoreCase("Basic") || schemeName.equalsIgnoreCase("Digest")) {
            return true;
        }
        return false;
    }

    public void b(l authhost, org.apache.http.auth.c authScheme, f context) {
        org.apache.http.util.a.i(authhost, "Host");
        org.apache.http.util.a.i(context, "HTTP context");
        org.apache.http.client.a authCache = org.apache.http.client.protocol.a.g(context).h();
        if (authCache != null) {
            if (this.b.isDebugEnabled()) {
                a aVar = this.b;
                aVar.debug("Clearing cached auth scheme for " + authhost);
            }
            authCache.c(authhost);
        }
    }
}
