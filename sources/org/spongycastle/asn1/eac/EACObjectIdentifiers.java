package org.spongycastle.asn1.eac;

import androidx.exifinterface.media.ExifInterface;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface EACObjectIdentifiers {
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
    public static final ASN1ObjectIdentifier r;
    public static final ASN1ObjectIdentifier s;
    public static final ASN1ObjectIdentifier t;
    public static final ASN1ObjectIdentifier u;
    public static final ASN1ObjectIdentifier v;
    public static final ASN1ObjectIdentifier w;
    public static final ASN1ObjectIdentifier x;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("0.4.0.127.0.7");
        a = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier o2 = aSN1ObjectIdentifier.o("2.2.1");
        b = o2;
        c = o2.o("1");
        d = o2.o(ExifInterface.GPS_MEASUREMENT_2D);
        ASN1ObjectIdentifier o3 = aSN1ObjectIdentifier.o("2.2.3");
        e = o3;
        ASN1ObjectIdentifier o4 = o3.o("1");
        f = o4;
        g = o4.o("1");
        ASN1ObjectIdentifier o5 = o3.o(ExifInterface.GPS_MEASUREMENT_2D);
        h = o5;
        i = o5.o("1");
        ASN1ObjectIdentifier o6 = aSN1ObjectIdentifier.o("2.2.2");
        j = o6;
        ASN1ObjectIdentifier o7 = o6.o("1");
        k = o7;
        l = o7.o("1");
        m = o7.o(ExifInterface.GPS_MEASUREMENT_2D);
        n = o7.o(ExifInterface.GPS_MEASUREMENT_3D);
        o = o7.o("4");
        p = o7.o("5");
        q = o7.o("6");
        ASN1ObjectIdentifier o8 = o6.o(ExifInterface.GPS_MEASUREMENT_2D);
        r = o8;
        s = o8.o("1");
        t = o8.o(ExifInterface.GPS_MEASUREMENT_2D);
        u = o8.o(ExifInterface.GPS_MEASUREMENT_3D);
        v = o8.o("4");
        w = o8.o("5");
        x = aSN1ObjectIdentifier.o("3.1.2.1");
    }
}
