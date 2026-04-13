package org.spongycastle.x509;

import org.spongycastle.asn1.x509.V3TBSCertificateGenerator;
import org.spongycastle.asn1.x509.X509ExtensionsGenerator;
import org.spongycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;

public class X509V3CertificateGenerator {
    private final JcaJceHelper a = new BCJcaJceHelper();
    private final CertificateFactory b = new CertificateFactory();
    private V3TBSCertificateGenerator c = new V3TBSCertificateGenerator();
    private X509ExtensionsGenerator d = new X509ExtensionsGenerator();
}
