package org.spongycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.X509CertPairStoreSelector;
import org.spongycastle.x509.X509CertStoreSelector;
import org.spongycastle.x509.X509CertificatePair;
import org.spongycastle.x509.X509StoreSpi;
import org.spongycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCerts extends X509StoreSpi {
    private LDAPStoreHelper a;

    public Collection a(Selector selector) {
        if (!(selector instanceof X509CertStoreSelector)) {
            return Collections.EMPTY_SET;
        }
        X509CertStoreSelector xselector = (X509CertStoreSelector) selector;
        Set set = new HashSet();
        if (xselector.getBasicConstraints() > 0) {
            set.addAll(this.a.q(xselector));
            set.addAll(b(xselector));
        } else if (xselector.getBasicConstraints() == -2) {
            set.addAll(this.a.x(xselector));
        } else {
            set.addAll(this.a.x(xselector));
            set.addAll(this.a.q(xselector));
            set.addAll(b(xselector));
        }
        return set;
    }

    private Collection b(X509CertStoreSelector xselector) {
        Set set = new HashSet();
        X509CertPairStoreSelector ps = new X509CertPairStoreSelector();
        ps.c(xselector);
        ps.d(new X509CertStoreSelector());
        Set<X509CertificatePair> crossCerts = new HashSet<>(this.a.t(ps));
        Set forward = new HashSet();
        Set reverse = new HashSet();
        for (X509CertificatePair pair : crossCerts) {
            if (pair.a() != null) {
                forward.add(pair.a());
            }
            if (pair.b() != null) {
                reverse.add(pair.b());
            }
        }
        set.addAll(forward);
        set.addAll(reverse);
        return set;
    }
}
