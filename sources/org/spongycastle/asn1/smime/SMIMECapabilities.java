package org.spongycastle.asn1.smime;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class SMIMECapabilities extends ASN1Object {
    public static final ASN1ObjectIdentifier a1 = new ASN1ObjectIdentifier("1.3.14.3.2.7");
    public static final ASN1ObjectIdentifier a2 = PKCSObjectIdentifiers.n0;
    public static final ASN1ObjectIdentifier c = PKCSObjectIdentifiers.e1;
    public static final ASN1ObjectIdentifier d = PKCSObjectIdentifiers.f1;
    public static final ASN1ObjectIdentifier f = PKCSObjectIdentifiers.g1;
    public static final ASN1ObjectIdentifier p0 = new ASN1ObjectIdentifier("1.2.840.113533.7.66.10");
    public static final ASN1ObjectIdentifier p1 = PKCSObjectIdentifiers.m0;
    public static final ASN1ObjectIdentifier q = NISTObjectIdentifiers.K;
    public static final ASN1ObjectIdentifier x = NISTObjectIdentifiers.C;
    public static final ASN1ObjectIdentifier y = NISTObjectIdentifiers.u;
    public static final ASN1ObjectIdentifier z = new ASN1ObjectIdentifier("1.3.6.1.4.1.188.7.1.1.2");
    private ASN1Sequence p2;

    public ASN1Primitive toASN1Primitive() {
        return this.p2;
    }
}
