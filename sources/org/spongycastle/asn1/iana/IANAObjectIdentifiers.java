package org.spongycastle.asn1.iana;

import androidx.exifinterface.media.ExifInterface;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface IANAObjectIdentifiers {
    public static final ASN1ObjectIdentifier a;
    public static final ASN1ObjectIdentifier b;
    public static final ASN1ObjectIdentifier c;
    public static final ASN1ObjectIdentifier d;
    public static final ASN1ObjectIdentifier e;
    public static final ASN1ObjectIdentifier f;
    public static final ASN1ObjectIdentifier g;
    public static final ASN1ObjectIdentifier h;
    public static final ASN1ObjectIdentifier i;
    public static final ASN1ObjectIdentifier j;
    public static final ASN1ObjectIdentifier k;
    public static final ASN1ObjectIdentifier l;
    public static final ASN1ObjectIdentifier m;
    public static final ASN1ObjectIdentifier n;
    public static final ASN1ObjectIdentifier o;
    public static final ASN1ObjectIdentifier p;
    public static final ASN1ObjectIdentifier q;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1");
        a = aSN1ObjectIdentifier;
        b = aSN1ObjectIdentifier.o("1");
        c = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_2D);
        d = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_3D);
        e = aSN1ObjectIdentifier.o("4");
        ASN1ObjectIdentifier o2 = aSN1ObjectIdentifier.o("5");
        f = o2;
        g = aSN1ObjectIdentifier.o("6");
        h = aSN1ObjectIdentifier.o("7");
        ASN1ObjectIdentifier o3 = o2.o("5");
        i = o3;
        j = o2.o("6");
        k = o3.o("6");
        ASN1ObjectIdentifier o4 = o3.o("8");
        l = o4;
        ASN1ObjectIdentifier o5 = o4.o("1");
        m = o5;
        n = o5.o("1");
        o = o5.o(ExifInterface.GPS_MEASUREMENT_2D);
        p = o5.o(ExifInterface.GPS_MEASUREMENT_3D);
        q = o5.o("4");
    }
}
