package org.apache.http.client.protocol;

import java.util.Queue;
import org.apache.commons.logging.h;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.b;
import org.apache.http.auth.c;
import org.apache.http.auth.k;
import org.apache.http.auth.l;
import org.apache.http.d;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.protocol.f;

@Deprecated
/* compiled from: RequestAuthenticationBase */
public abstract class e implements p {
    final org.apache.commons.logging.a c = h.n(getClass());

    /* access modifiers changed from: package-private */
    public void d(org.apache.http.auth.h authState, o request, f context) {
        c authScheme = authState.b();
        l creds = authState.c();
        switch (a.a[authState.d().ordinal()]) {
            case 1:
                return;
            case 2:
                c(authScheme);
                if (authScheme.isConnectionBased()) {
                    return;
                }
                break;
            case 3:
                Queue<org.apache.http.auth.a> a2 = authState.a();
                if (a2 == null) {
                    c(authScheme);
                    break;
                } else {
                    while (!a2.isEmpty()) {
                        org.apache.http.auth.a authOption = a2.remove();
                        c authScheme2 = authOption.a();
                        l creds2 = authOption.b();
                        authState.h(authScheme2, creds2);
                        if (this.c.isDebugEnabled()) {
                            org.apache.commons.logging.a aVar = this.c;
                            aVar.debug("Generating response to an authentication challenge using " + authScheme2.getSchemeName() + " scheme");
                        }
                        try {
                            request.I(a(authScheme2, creds2, request, context));
                            return;
                        } catch (AuthenticationException ex) {
                            if (this.c.isWarnEnabled()) {
                                org.apache.commons.logging.a aVar2 = this.c;
                                aVar2.warn(authScheme2 + " authentication error: " + ex.getMessage());
                            }
                        }
                    }
                    return;
                }
        }
        if (authScheme != null) {
            try {
                request.I(a(authScheme, creds, request, context));
            } catch (AuthenticationException ex2) {
                if (this.c.isErrorEnabled()) {
                    org.apache.commons.logging.a aVar3 = this.c;
                    aVar3.error(authScheme + " authentication error: " + ex2.getMessage());
                }
            }
        }
    }

    /* compiled from: RequestAuthenticationBase */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.FAILURE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.CHALLENGED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private void c(c authScheme) {
        org.apache.http.util.b.c(authScheme, "Auth scheme");
    }

    private d a(c authScheme, l creds, o request, f context) {
        org.apache.http.util.b.c(authScheme, "Auth scheme");
        if (authScheme instanceof k) {
            return ((k) authScheme).authenticate(creds, request, context);
        }
        return authScheme.authenticate(creds, request);
    }
}
