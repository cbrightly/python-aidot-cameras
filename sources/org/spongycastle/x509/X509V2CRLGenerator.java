package org.spongycastle.x509;

import java.security.cert.CRLException;
import org.spongycastle.asn1.x509.V2TBSCertListGenerator;
import org.spongycastle.asn1.x509.X509ExtensionsGenerator;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;

public class X509V2CRLGenerator {
    private final JcaJceHelper a = new BCJcaJceHelper();
    private V2TBSCertListGenerator b = new V2TBSCertListGenerator();
    private X509ExtensionsGenerator c = new X509ExtensionsGenerator();

    public static class ExtCRLException extends CRLException {
        Throwable cause;

        ExtCRLException(String message, Throwable cause2) {
            super(message);
            this.cause = cause2;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }
}
