package org.spongycastle.asn1.icao;

import androidx.exifinterface.media.ExifInterface;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public interface ICAOObjectIdentifiers {
    public static final ASN1ObjectIdentifier A;
    public static final ASN1ObjectIdentifier B;
    public static final ASN1ObjectIdentifier C;
    public static final ASN1ObjectIdentifier D;
    public static final ASN1ObjectIdentifier E;
    public static final ASN1ObjectIdentifier F;
    public static final ASN1ObjectIdentifier G;
    public static final ASN1ObjectIdentifier H;
    public static final ASN1ObjectIdentifier I;
    public static final ASN1ObjectIdentifier w;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.23.136");
        w = aSN1ObjectIdentifier;
        ASN1ObjectIdentifier o = aSN1ObjectIdentifier.o("1");
        A = o;
        ASN1ObjectIdentifier o2 = o.o("1");
        B = o2;
        C = o2.o("1");
        D = o2.o(ExifInterface.GPS_MEASUREMENT_2D);
        E = o2.o(ExifInterface.GPS_MEASUREMENT_3D);
        F = o2.o("4");
        G = o2.o("5");
        ASN1ObjectIdentifier o3 = o2.o("6");
        H = o3;
        I = o3.o("1");
    }
}
