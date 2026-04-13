package org.spongycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.util.Selector;
import org.spongycastle.x509.X509CRLStoreSelector;
import org.spongycastle.x509.X509StoreSpi;
import org.spongycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCRLs extends X509StoreSpi {
    private LDAPStoreHelper a;

    public Collection a(Selector selector) {
        if (!(selector instanceof X509CRLStoreSelector)) {
            return Collections.EMPTY_SET;
        }
        X509CRLStoreSelector xselector = (X509CRLStoreSelector) selector;
        Set set = new HashSet();
        if (xselector.d()) {
            set.addAll(this.a.u(xselector));
        } else {
            set.addAll(this.a.u(xselector));
            set.addAll(this.a.l(xselector));
            set.addAll(this.a.n(xselector));
            set.addAll(this.a.p(xselector));
            set.addAll(this.a.s(xselector));
        }
        return set;
    }
}
