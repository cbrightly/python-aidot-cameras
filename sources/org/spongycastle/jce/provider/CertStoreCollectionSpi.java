package org.spongycastle.jce.provider;

import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CertStoreCollectionSpi extends CertStoreSpi {
    private CollectionCertStoreParameters a;

    public Collection engineGetCertificates(CertSelector selector) {
        List col = new ArrayList();
        Iterator iter = this.a.getCollection().iterator();
        if (selector == null) {
            while (iter.hasNext()) {
                Object obj = iter.next();
                if (obj instanceof Certificate) {
                    col.add(obj);
                }
            }
        } else {
            while (iter.hasNext()) {
                Object next = iter.next();
                if ((next instanceof Certificate) && selector.match((Certificate) next)) {
                    col.add(next);
                }
            }
        }
        return col;
    }

    public Collection engineGetCRLs(CRLSelector selector) {
        List col = new ArrayList();
        Iterator iter = this.a.getCollection().iterator();
        if (selector == null) {
            while (iter.hasNext()) {
                Object obj = iter.next();
                if (obj instanceof CRL) {
                    col.add(obj);
                }
            }
        } else {
            while (iter.hasNext()) {
                Object next = iter.next();
                if ((next instanceof CRL) && selector.match((CRL) next)) {
                    col.add(next);
                }
            }
        }
        return col;
    }
}
