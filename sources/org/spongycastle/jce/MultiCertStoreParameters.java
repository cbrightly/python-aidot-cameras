package org.spongycastle.jce;

import java.security.cert.CertStoreParameters;
import java.util.Collection;

public class MultiCertStoreParameters implements CertStoreParameters {
    private Collection c;
    private boolean d;

    public Collection a() {
        return this.c;
    }

    public boolean b() {
        return this.d;
    }

    public Object clone() {
        return this;
    }
}
