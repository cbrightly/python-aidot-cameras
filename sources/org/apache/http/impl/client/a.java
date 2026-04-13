package org.apache.http.impl.client;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.logging.h;
import org.apache.http.Header;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.c;
import org.apache.http.client.b;
import org.apache.http.d;
import org.apache.http.protocol.e;
import org.apache.http.protocol.f;
import org.apache.http.q;

@Deprecated
/* compiled from: AbstractAuthenticationHandler */
public abstract class a implements b {
    private static final List<String> a = Collections.unmodifiableList(Arrays.asList(new String[]{"Negotiate", "NTLM", "Digest", "Basic"}));
    private final org.apache.commons.logging.a b = h.n(getClass());

    /* access modifiers changed from: protected */
    public Map<String, d> f(d[] headers) {
        int pos;
        org.apache.http.util.d buffer;
        Map<String, Header> map = new HashMap<>(headers.length);
        for (d header : headers) {
            if (header instanceof c) {
                buffer = ((c) header).getBuffer();
                pos = ((c) header).getValuePos();
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

    /* access modifiers changed from: protected */
    public List<String> d() {
        return a;
    }

    /* access modifiers changed from: protected */
    public List<String> e(q response, f context) {
        return d();
    }

    public org.apache.http.auth.c b(Map<String, d> challenges, q response, f context) {
        org.apache.http.auth.f registry = (org.apache.http.auth.f) context.getAttribute("http.authscheme-registry");
        org.apache.http.util.b.c(registry, "AuthScheme registry");
        Collection<String> authPrefs = e(response, context);
        if (authPrefs == null) {
            authPrefs = a;
        }
        if (this.b.isDebugEnabled()) {
            org.apache.commons.logging.a aVar = this.b;
            aVar.debug("Authentication schemes in the order of preference: " + authPrefs);
        }
        org.apache.http.auth.c authScheme = null;
        Iterator i$ = authPrefs.iterator();
        while (true) {
            if (!i$.hasNext()) {
                break;
            }
            String id = i$.next();
            if (challenges.get(id.toLowerCase(Locale.ENGLISH)) != null) {
                if (this.b.isDebugEnabled()) {
                    org.apache.commons.logging.a aVar2 = this.b;
                    aVar2.debug(id + " authentication scheme selected");
                }
                try {
                    authScheme = registry.a(id, response.getParams());
                    break;
                } catch (IllegalStateException e) {
                    if (this.b.isWarnEnabled()) {
                        org.apache.commons.logging.a aVar3 = this.b;
                        aVar3.warn("Authentication scheme " + id + " not supported");
                    }
                }
            } else if (this.b.isDebugEnabled()) {
                org.apache.commons.logging.a aVar4 = this.b;
                aVar4.debug("Challenge for " + id + " authentication scheme not available");
            }
        }
        if (authScheme != null) {
            return authScheme;
        }
        throw new AuthenticationException("Unable to respond to any of these challenges: " + challenges);
    }
}
