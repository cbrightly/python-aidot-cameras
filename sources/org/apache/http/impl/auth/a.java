package org.apache.http.impl.auth;

import java.util.Locale;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.j;
import org.apache.http.auth.k;
import org.apache.http.auth.l;
import org.apache.http.c;
import org.apache.http.o;
import org.apache.http.protocol.e;
import org.apache.http.protocol.f;
import org.apache.http.util.d;
import org.glassfish.tyrus.spi.UpgradeResponse;

/* compiled from: AuthSchemeBase */
public abstract class a implements k {
    protected j challengeState;

    /* access modifiers changed from: protected */
    public abstract void parseChallenge(d dVar, int i, int i2);

    @Deprecated
    public a(j challengeState2) {
        this.challengeState = challengeState2;
    }

    public a() {
    }

    public void processChallenge(org.apache.http.d header) {
        int pos;
        d buffer;
        org.apache.http.util.a.i(header, "Header");
        String authheader = header.getName();
        if (authheader.equalsIgnoreCase(UpgradeResponse.WWW_AUTHENTICATE)) {
            this.challengeState = j.TARGET;
        } else if (authheader.equalsIgnoreCase("Proxy-Authenticate")) {
            this.challengeState = j.PROXY;
        } else {
            throw new MalformedChallengeException("Unexpected header name: " + authheader);
        }
        if (header instanceof c) {
            buffer = ((c) header).getBuffer();
            pos = ((c) header).getValuePos();
        } else {
            String s = header.getValue();
            if (s != null) {
                d buffer2 = new d(s.length());
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
        String s2 = buffer.substring(beginIndex, pos);
        if (s2.equalsIgnoreCase(getSchemeName())) {
            parseChallenge(buffer, pos, buffer.length());
            return;
        }
        throw new MalformedChallengeException("Invalid scheme identifier: " + s2);
    }

    public org.apache.http.d authenticate(l credentials, o request, f context) {
        return authenticate(credentials, request);
    }

    public boolean isProxy() {
        j jVar = this.challengeState;
        return jVar != null && jVar == j.PROXY;
    }

    public j getChallengeState() {
        return this.challengeState;
    }

    public String toString() {
        String name = getSchemeName();
        if (name != null) {
            return name.toUpperCase(Locale.ROOT);
        }
        return super.toString();
    }
}
