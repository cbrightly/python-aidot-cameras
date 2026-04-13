package org.spongycastle.jce.provider;

import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreSpi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.spongycastle.jce.MultiCertStoreParameters;

public class MultiCertStoreSpi extends CertStoreSpi {
    private MultiCertStoreParameters a;

    public Collection engineGetCertificates(CertSelector certSelector) {
        boolean searchAllStores = this.a.b();
        List allCerts = searchAllStores ? new ArrayList() : Collections.EMPTY_LIST;
        for (CertStore store : this.a.a()) {
            Collection certs = store.getCertificates(certSelector);
            if (searchAllStores) {
                allCerts.addAll(certs);
            } else if (!certs.isEmpty()) {
                return certs;
            }
        }
        return allCerts;
    }

    public Collection engineGetCRLs(CRLSelector crlSelector) {
        boolean searchAllStores = this.a.b();
        List allCRLs = searchAllStores ? new ArrayList() : Collections.EMPTY_LIST;
        for (CertStore store : this.a.a()) {
            Collection crls = store.getCRLs(crlSelector);
            if (searchAllStores) {
                allCRLs.addAll(crls);
            } else if (!crls.isEmpty()) {
                return crls;
            }
        }
        return allCRLs;
    }
}
