package org.apache.http.impl.auth;

import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.logging.h;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.b;
import org.apache.http.auth.k;
import org.apache.http.client.c;
import org.apache.http.d;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.q;

/* compiled from: HttpAuthenticator */
public class g {
    private final org.apache.commons.logging.a a;

    public g(org.apache.commons.logging.a log) {
        this.a = log != null ? log : h.n(getClass());
    }

    public g() {
        this((org.apache.commons.logging.a) null);
    }

    public boolean e(l host, q response, c authStrategy, org.apache.http.auth.h authState, f context) {
        if (authStrategy.e(host, response, context)) {
            this.a.debug("Authentication required");
            if (authState.d() != b.SUCCESS) {
                return true;
            }
            authStrategy.b(host, authState.b(), context);
            return true;
        }
        switch (a.a[authState.d().ordinal()]) {
            case 1:
            case 2:
                this.a.debug("Authentication succeeded");
                authState.f(b.SUCCESS);
                authStrategy.a(host, authState.b(), context);
                return false;
            case 3:
                return false;
            default:
                authState.f(b.UNCHALLENGED);
                return false;
        }
    }

    /* compiled from: HttpAuthenticator */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.CHALLENGED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.HANDSHAKE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.SUCCESS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[b.FAILURE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[b.UNCHALLENGED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public boolean d(l host, q response, c authStrategy, org.apache.http.auth.h authState, f context) {
        try {
            if (this.a.isDebugEnabled()) {
                org.apache.commons.logging.a aVar = this.a;
                aVar.debug(host.toHostString() + " requested authentication");
            }
            Map<String, d> c = authStrategy.c(host, response, context);
            if (c.isEmpty()) {
                this.a.debug("Response contains no authentication challenges");
                return false;
            }
            org.apache.http.auth.c authScheme = authState.b();
            switch (a.a[authState.d().ordinal()]) {
                case 1:
                case 2:
                    if (authScheme == null) {
                        this.a.debug("Auth scheme is null");
                        authStrategy.b(host, (org.apache.http.auth.c) null, context);
                        authState.e();
                        authState.f(b.FAILURE);
                        return false;
                    }
                    break;
                case 3:
                    authState.e();
                    break;
                case 4:
                    return false;
                case 5:
                    break;
            }
            if (authScheme != null) {
                d challenge = c.get(authScheme.getSchemeName().toLowerCase(Locale.ROOT));
                if (challenge != null) {
                    this.a.debug("Authorization challenge processed");
                    authScheme.processChallenge(challenge);
                    if (authScheme.isComplete()) {
                        this.a.debug("Authentication failed");
                        authStrategy.b(host, authState.b(), context);
                        authState.e();
                        authState.f(b.FAILURE);
                        return false;
                    }
                    authState.f(b.HANDSHAKE);
                    return true;
                }
                authState.e();
            }
            Queue<org.apache.http.auth.a> d = authStrategy.d(c, host, response, context);
            if (d == null || d.isEmpty()) {
                return false;
            }
            if (this.a.isDebugEnabled()) {
                org.apache.commons.logging.a aVar2 = this.a;
                aVar2.debug("Selected authentication options: " + d);
            }
            authState.f(b.CHALLENGED);
            authState.g(d);
            return true;
        } catch (MalformedChallengeException ex) {
            if (this.a.isWarnEnabled()) {
                org.apache.commons.logging.a aVar3 = this.a;
                aVar3.warn("Malformed challenge: " + ex.getMessage());
            }
            authState.e();
            return false;
        }
    }

    public void c(o request, org.apache.http.auth.h authState, f context) {
        org.apache.http.auth.c authScheme = authState.b();
        org.apache.http.auth.l creds = authState.c();
        switch (a.a[authState.d().ordinal()]) {
            case 1:
                Queue<org.apache.http.auth.a> a2 = authState.a();
                if (a2 == null) {
                    b(authScheme);
                    break;
                } else {
                    while (!a2.isEmpty()) {
                        org.apache.http.auth.a authOption = a2.remove();
                        org.apache.http.auth.c authScheme2 = authOption.a();
                        org.apache.http.auth.l creds2 = authOption.b();
                        authState.h(authScheme2, creds2);
                        if (this.a.isDebugEnabled()) {
                            org.apache.commons.logging.a aVar = this.a;
                            aVar.debug("Generating response to an authentication challenge using " + authScheme2.getSchemeName() + " scheme");
                        }
                        try {
                            request.I(a(authScheme2, creds2, request, context));
                            return;
                        } catch (AuthenticationException ex) {
                            if (this.a.isWarnEnabled()) {
                                org.apache.commons.logging.a aVar2 = this.a;
                                aVar2.warn(authScheme2 + " authentication error: " + ex.getMessage());
                            }
                        }
                    }
                    return;
                }
            case 3:
                b(authScheme);
                if (authScheme.isConnectionBased()) {
                    return;
                }
                break;
            case 4:
                return;
        }
        if (authScheme != null) {
            try {
                request.I(a(authScheme, creds, request, context));
            } catch (AuthenticationException ex2) {
                if (this.a.isErrorEnabled()) {
                    org.apache.commons.logging.a aVar3 = this.a;
                    aVar3.error(authScheme + " authentication error: " + ex2.getMessage());
                }
            }
        }
    }

    private void b(org.apache.http.auth.c authScheme) {
        org.apache.http.util.b.c(authScheme, "Auth scheme");
    }

    private d a(org.apache.http.auth.c authScheme, org.apache.http.auth.l creds, o request, f context) {
        if (authScheme instanceof k) {
            return ((k) authScheme).authenticate(creds, request, context);
        }
        return authScheme.authenticate(creds, request);
    }
}
