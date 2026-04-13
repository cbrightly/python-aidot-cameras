package org.spongycastle.jce.provider;

import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.x509.X509AttributeCertificate;

public class PrincipalUtils {
    PrincipalUtils() {
    }

    static X500Name e(X509Certificate cert) {
        return X500Name.e(cert.getSubjectX500Principal().getEncoded());
    }

    static X500Name c(X509CRL crl) {
        return X500Name.e(crl.getIssuerX500Principal().getEncoded());
    }

    static X500Name d(X509Certificate cert) {
        return X500Name.e(cert.getIssuerX500Principal().getEncoded());
    }

    static X500Name a(TrustAnchor trustAnchor) {
        return X500Name.e(trustAnchor.getCA().getEncoded());
    }

    static X500Name b(Object cert) {
        if (cert instanceof X509Certificate) {
            return d((X509Certificate) cert);
        }
        return X500Name.e(((X500Principal) ((X509AttributeCertificate) cert).c().b()[0]).getEncoded());
    }
}
