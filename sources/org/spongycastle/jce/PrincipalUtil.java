package org.spongycastle.jce;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x509.TBSCertificateStructure;
import org.spongycastle.asn1.x509.X509Name;

public class PrincipalUtil {
    public static X509Principal a(X509Certificate cert) {
        try {
            return new X509Principal(X509Name.k(TBSCertificateStructure.e(ASN1Primitive.h(cert.getTBSCertificate())).f()));
        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString());
        }
    }

    public static X509Principal b(X509Certificate cert) {
        try {
            return new X509Principal(X509Name.k(TBSCertificateStructure.e(ASN1Primitive.h(cert.getTBSCertificate())).g()));
        } catch (IOException e) {
            throw new CertificateEncodingException(e.toString());
        }
    }
}
