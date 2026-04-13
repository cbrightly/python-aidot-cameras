package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.ietf.jgss.GSSCredential;

/* compiled from: KerberosCredentials */
public class m implements l, Serializable {
    private static final long serialVersionUID = 487421613855550713L;
    private final GSSCredential gssCredential;

    public m(GSSCredential gssCredential2) {
        this.gssCredential = gssCredential2;
    }

    public GSSCredential getGSSCredential() {
        return this.gssCredential;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getPassword() {
        return null;
    }
}
