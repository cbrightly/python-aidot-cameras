package org.apache.http.impl.auth;

import org.apache.http.auth.l;
import org.apache.http.d;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.ietf.jgss.Oid;

/* compiled from: SPNegoScheme */
public class p extends f {
    public p(boolean stripPort, boolean useCanonicalHostname) {
        super(stripPort, useCanonicalHostname);
    }

    public String getSchemeName() {
        return "Negotiate";
    }

    public d authenticate(l credentials, o request, f context) {
        return super.authenticate(credentials, request, context);
    }

    /* access modifiers changed from: protected */
    public byte[] c(byte[] input, String authServer, l credentials) {
        return b(input, new Oid("1.3.6.1.5.5.2"), authServer, credentials);
    }

    public String getRealm() {
        return null;
    }

    public boolean isConnectionBased() {
        return true;
    }
}
