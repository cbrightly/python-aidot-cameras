package com.squareup.okhttp.internal.tls;

import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

/* compiled from: CertificateChainCleaner */
public final class b {
    private final f a;

    public b(f trustRootIndex) {
        this.a = trustRootIndex;
    }

    public List<Certificate> a(List<Certificate> chain) {
        Deque<Certificate> queue = new ArrayDeque<>(chain);
        List<Certificate> result = new ArrayList<>();
        result.add(queue.removeFirst());
        boolean foundTrustedCertificate = false;
        for (int c = 0; c < 9; c++) {
            X509Certificate toVerify = (X509Certificate) result.get(result.size() - 1);
            X509Certificate trustedCert = this.a.a(toVerify);
            if (trustedCert != null) {
                if (result.size() > 1 || !toVerify.equals(trustedCert)) {
                    result.add(trustedCert);
                }
                if (b(trustedCert, trustedCert)) {
                    return result;
                }
                foundTrustedCertificate = true;
            } else {
                Iterator<Certificate> i = queue.iterator();
                while (i.hasNext()) {
                    X509Certificate signingCert = (X509Certificate) i.next();
                    if (b(toVerify, signingCert)) {
                        i.remove();
                        result.add(signingCert);
                    }
                }
                if (foundTrustedCertificate) {
                    return result;
                }
                throw new SSLPeerUnverifiedException("Failed to find a trusted cert that signed " + toVerify);
            }
        }
        throw new SSLPeerUnverifiedException("Certificate chain too long: " + result);
    }

    private boolean b(X509Certificate toVerify, X509Certificate signingCert) {
        if (!toVerify.getIssuerDN().equals(signingCert.getSubjectDN())) {
            return false;
        }
        try {
            toVerify.verify(signingCert.getPublicKey());
            return true;
        } catch (GeneralSecurityException e) {
            return false;
        }
    }
}
