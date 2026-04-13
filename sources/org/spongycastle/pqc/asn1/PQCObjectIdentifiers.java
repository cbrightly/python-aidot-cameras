package org.spongycastle.pqc.asn1;

import androidx.exifinterface.media.ExifInterface;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.bc.BCObjectIdentifiers;

public interface PQCObjectIdentifiers {
    public static final ASN1ObjectIdentifier A = BCObjectIdentifiers.A;
    public static final ASN1ObjectIdentifier B = BCObjectIdentifiers.B;
    public static final ASN1ObjectIdentifier C = BCObjectIdentifiers.C;
    public static final ASN1ObjectIdentifier D = BCObjectIdentifiers.D;
    public static final ASN1ObjectIdentifier E = BCObjectIdentifiers.E;
    public static final ASN1ObjectIdentifier F = BCObjectIdentifiers.F;
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
    public static final ASN1ObjectIdentifier m = new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.4.1");
    public static final ASN1ObjectIdentifier n = new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.4.2");
    public static final ASN1ObjectIdentifier o = new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.4.2.1");
    public static final ASN1ObjectIdentifier p = new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.4.2.2");
    public static final ASN1ObjectIdentifier q = new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.4.2.3");
    public static final ASN1ObjectIdentifier r = BCObjectIdentifiers.s;
    public static final ASN1ObjectIdentifier s = BCObjectIdentifiers.t;
    public static final ASN1ObjectIdentifier t = BCObjectIdentifiers.u;
    public static final ASN1ObjectIdentifier u = BCObjectIdentifiers.v;
    public static final ASN1ObjectIdentifier v = BCObjectIdentifiers.H;
    public static final ASN1ObjectIdentifier w = BCObjectIdentifiers.w;
    public static final ASN1ObjectIdentifier x = BCObjectIdentifiers.x;
    public static final ASN1ObjectIdentifier y = BCObjectIdentifiers.y;
    public static final ASN1ObjectIdentifier z = BCObjectIdentifiers.z;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.5.3.2");
        a = aSN1ObjectIdentifier;
        b = aSN1ObjectIdentifier.o("1");
        c = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_2D);
        d = aSN1ObjectIdentifier.o(ExifInterface.GPS_MEASUREMENT_3D);
        e = aSN1ObjectIdentifier.o("4");
        f = aSN1ObjectIdentifier.o("5");
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = new ASN1ObjectIdentifier("1.3.6.1.4.1.8301.3.1.3.3");
        g = aSN1ObjectIdentifier2;
        h = aSN1ObjectIdentifier2.o("1");
        i = aSN1ObjectIdentifier2.o(ExifInterface.GPS_MEASUREMENT_2D);
        j = aSN1ObjectIdentifier2.o(ExifInterface.GPS_MEASUREMENT_3D);
        k = aSN1ObjectIdentifier2.o("4");
        l = aSN1ObjectIdentifier2.o("5");
    }
}
