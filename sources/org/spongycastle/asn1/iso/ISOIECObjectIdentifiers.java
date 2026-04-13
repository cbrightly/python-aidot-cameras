package org.spongycastle.asn1.iso;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface ISOIECObjectIdentifiers {
    public static final ASN1ObjectIdentifier a;
    public static final ASN1ObjectIdentifier b;
    public static final ASN1ObjectIdentifier c;
    public static final ASN1ObjectIdentifier d;
    public static final ASN1ObjectIdentifier e;
    public static final ASN1ObjectIdentifier f;
    public static final ASN1ObjectIdentifier g;
    public static final ASN1ObjectIdentifier h;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.0.10118");
        a = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier o = aSN1ObjectIdentifier.o("3.0");
        b = o;
        c = o.o("49");
        d = o.o("50");
        e = o.o("55");
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = new ASN1ObjectIdentifier("1.0.18033.2");
        f = aSN1ObjectIdentifier2;
        g = aSN1ObjectIdentifier2.o("1.2");
        h = aSN1ObjectIdentifier2.o("2.4");
    }
}
