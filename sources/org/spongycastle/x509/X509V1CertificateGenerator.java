package org.spongycastle.x509;

import org.spongycastle.asn1.x509.V1TBSCertificateGenerator;
import org.spongycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;

public class X509V1CertificateGenerator {
    private final JcaJceHelper a = new BCJcaJceHelper();
    private final CertificateFactory b = new CertificateFactory();
    private V1TBSCertificateGenerator c = new V1TBSCertificateGenerator();
}
