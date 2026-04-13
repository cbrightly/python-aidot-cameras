package org.spongycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.X509AttributeCertStoreSelector;
import org.spongycastle.x509.X509StoreSpi;
import org.spongycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPAttrCerts extends X509StoreSpi {
    private LDAPStoreHelper a;

    public Collection a(Selector selector) {
        if (!(selector instanceof X509AttributeCertStoreSelector)) {
            return Collections.EMPTY_SET;
        }
        X509AttributeCertStoreSelector xselector = (X509AttributeCertStoreSelector) selector;
        Set set = new HashSet();
        set.addAll(this.a.k(xselector));
        set.addAll(this.a.m(xselector));
        set.addAll(this.a.o(xselector));
        return set;
    }
}
