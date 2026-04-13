package org.spongycastle.asn1.cms;

import androidx.exifinterface.media.ExifInterface;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public interface CMSObjectIdentifiers {
    public static final ASN1ObjectIdentifier i = PKCSObjectIdentifiers.A0;
    public static final ASN1ObjectIdentifier j = PKCSObjectIdentifiers.B0;
    public static final ASN1ObjectIdentifier k = PKCSObjectIdentifiers.C0;
    public static final ASN1ObjectIdentifier l = PKCSObjectIdentifiers.D0;
    public static final ASN1ObjectIdentifier m = PKCSObjectIdentifiers.E0;
    public static final ASN1ObjectIdentifier n = PKCSObjectIdentifiers.F0;
    public static final ASN1ObjectIdentifier o = PKCSObjectIdentifiers.i1;
    public static final ASN1ObjectIdentifier p = PKCSObjectIdentifiers.k1;
    public static final ASN1ObjectIdentifier r = PKCSObjectIdentifiers.l1;
    public static final ASN1ObjectIdentifier s = PKCSObjectIdentifiers.m1;
    public static final ASN1ObjectIdentifier t;
    public static final ASN1ObjectIdentifier u;
    public static final ASN1ObjectIdentifier v;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.16");
        t = aSN1ObjectIdentifier;
        u = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_2D);
        v = aSN1ObjectIdentifier.o("4");
    }
}
