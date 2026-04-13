package org.spongycastle.jce.provider;

import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.spongycastle.jcajce.PKIXCRLStoreSelector;
import org.spongycastle.util.Store;
import org.spongycastle.util.StoreException;

public class PKIXCRLUtil {
    PKIXCRLUtil() {
    }

    public Set b(PKIXCRLStoreSelector crlselect, Date validityDate, List certStores, List pkixCrlStores) {
        Set<X509CRL> initialSet = new HashSet<>();
        try {
            initialSet.addAll(a(crlselect, pkixCrlStores));
            initialSet.addAll(a(crlselect, certStores));
            Set finalSet = new HashSet();
            for (X509CRL crl : initialSet) {
                if (crl.getNextUpdate().after(validityDate)) {
                    X509Certificate cert = crlselect.c();
                    if (cert == null) {
                        finalSet.add(crl);
                    } else if (crl.getThisUpdate().before(cert.getNotAfter())) {
                        finalSet.add(crl);
                    }
                }
            }
            return finalSet;
        } catch (AnnotatedException e) {
            throw new AnnotatedException("Exception obtaining complete CRLs.", e);
        }
    }

    private final Collection a(PKIXCRLStoreSelector crlSelect, List crlStores) {
        Set crls = new HashSet();
        AnnotatedException lastException = null;
        boolean foundValidStore = false;
        for (Object obj : crlStores) {
            if (obj instanceof Store) {
                try {
                    crls.addAll(((Store) obj).a(crlSelect));
                    foundValidStore = true;
                } catch (StoreException e) {
                    lastException = new AnnotatedException("Exception searching in X.509 CRL store.", e);
                }
            } else {
                try {
                    crls.addAll(PKIXCRLStoreSelector.b(crlSelect, (CertStore) obj));
                    foundValidStore = true;
                } catch (CertStoreException e2) {
                    lastException = new AnnotatedException("Exception searching in X.509 CRL store.", e2);
                }
            }
        }
        if (foundValidStore || lastException == null) {
            return crls;
        }
        throw lastException;
    }
}
