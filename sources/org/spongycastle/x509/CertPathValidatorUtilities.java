package org.spongycastle.x509;

import org.spongycastle.asn1.x509.Extension;

public class CertPathValidatorUtilities {
    protected static final PKIXCRLUtil a = new PKIXCRLUtil();
    protected static final String b = Extension.B4.s();
    protected static final String c = Extension.z.s();
    protected static final String d = Extension.C4.s();
    protected static final String e = Extension.x.s();
    protected static final String f = Extension.z4.s();
    protected static final String g = Extension.f.s();
    protected static final String h = Extension.H4.s();
    protected static final String i = Extension.p3.s();
    protected static final String j = Extension.p2.s();
    protected static final String k = Extension.E4.s();
    protected static final String l = Extension.G4.s();
    protected static final String m = Extension.A4.s();
    protected static final String n = Extension.D4.s();
    protected static final String o = Extension.p0.s();
    protected static final String[] p = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    CertPathValidatorUtilities() {
    }
}
